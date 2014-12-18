package com.avocarrot.json2view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by avocarrot on 11/12/2014.
 * parse the json as a tree and create View with its dynamicProperties
 */
public class DynamicView {

    static int mCurrentId = 13;

    /**
     * @param jsonObject : json object
     * @param holderClass : class that will be created as an holder and attached as a tag in the View
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject, Class holderClass) {

        HashMap<String, Integer> ids = new HashMap<>();

        View container = createViewInternal(context, jsonObject, ids);

        if (container.getTag() != null)
            DynamicHelper.applyLayoutProperties(container, (List<DynamicProperty>) container.getTag(), null, ids);

        /* clear tag from properties */
        container.setTag(null);

        if (holderClass!= null) {

            try {
                Object holder = holderClass.getConstructor().newInstance();
                DynamicHelper.parseDynamicView(holder, container, ids);
                container.setTag(holder);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return container;

    }

    /**
     * @param jsonObject : json object
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject) {
        return createView(context, jsonObject, null);
    }

    /**
     * use internal to parse the json as a tree to create View
     * @param jsonObject : json object
     * @param ids : the hashMap where we keep ids as string from json to ids as int in the layout
     * @return the view that created
     */
    private static View createViewInternal (Context context, JSONObject jsonObject, HashMap<String, Integer> ids) {

        View view = null;

        ArrayList<DynamicProperty> properties;

        try {
            /* Create the View Object. If not full package is available try to create a view from android.widget */
            String widget = jsonObject.getString("widget");
            if (!widget.contains(".")) {
                widget = "android.widget." + widget;
            }
            Class viewClass = Class.forName(widget);
            /* create the actual view object */
            view = (View) viewClass.getConstructor(Context.class).newInstance(new Object[] { context });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (view==null) return null;

        try {

            /* default Layout in case the user not set it */
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            /* iterrate json and get all properties in array */
            properties = new ArrayList<>();
            JSONArray jArray = jsonObject.getJSONArray("properties");
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    DynamicProperty p = new DynamicProperty(jArray.getJSONObject(i));
                    if (p.isValid())
                        properties.add(p);
                }
            }

            /* keep properties obj as a tag */
            view.setTag(properties);

            /* add and integer as a universal id  and keep it in a hashmap */
            String id = DynamicHelper.applyStyleProperties(view, properties);
            if (!TextUtils.isEmpty(id)) {
                ids.put(id, mCurrentId);
                view.setId( mCurrentId );
                mCurrentId++;
            }

            /* if view is type of ViewGroup check for its children view in json */
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;

                /* parse the aray to get the children views */
                List<View> views = new ArrayList<>();
                JSONArray jViews = jsonObject.getJSONArray("views");
                if (jViews != null) {
                    int count=jViews.length();
                    for (int i=0;i<count;i++) {
                        /* create every child add it in viewGroup and set its tag with its properties */
                        View dynamicChildView = DynamicView.createViewInternal(context, jViews.getJSONObject(i), ids);
                        views.add(dynamicChildView);
                        viewGroup.addView(dynamicChildView);
                    }
                }
                /* after create all the children apply layout properties
                * we need to do this after al children creation to have create all possible ids */
                for(View v : views) {
                    DynamicHelper.applyLayoutProperties(v, (List<DynamicProperty>) v.getTag(), viewGroup, ids);
                    /* clear tag from properties */
                    v.setTag(null);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

}
