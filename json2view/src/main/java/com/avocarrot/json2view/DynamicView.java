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
    static int INTERNAL_TAG_ID = 0x7f020000;

    /**
     * @param jsonObject : json object
     * @param holderClass : class that will be created as an holder and attached as a tag in the View
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject, Class holderClass) {
        return createView(context, jsonObject, null, holderClass);
    }

    /**
     * @param jsonObject : json object
     * @param parent : parent viewGroup
     * @param holderClass : class that will be created as an holder and attached as a tag in the View, If contains HashMap ids will replaced with idsMap
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject, ViewGroup parent, Class holderClass) {

        if (jsonObject==null)
            return null;

        HashMap<String, Integer> ids = new HashMap<>();

        View container = createViewInternal(context, jsonObject, parent, ids);

        if (container==null)
            return null;

        if (container.getTag(INTERNAL_TAG_ID) != null)
            DynamicHelper.applyLayoutProperties(container, (List<DynamicProperty>) container.getTag(INTERNAL_TAG_ID), parent, ids);

        /* clear tag from properties */
        container.setTag(INTERNAL_TAG_ID, null);

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
     * @param parent : parent viewGroup
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject, ViewGroup parent) {
        return createView(context, jsonObject, parent, null);
    }

    /**
     * @param jsonObject : json object
     * @return the view that created
     */
    public static View createView (Context context, JSONObject jsonObject) {
        return createView(context, jsonObject, null, null);
    }

    /**
     * use internal to parse the json as a tree to create View
     * @param jsonObject : json object
     * @param ids : the hashMap where we keep ids as string from json to ids as int in the layout
     * @return the view that created
     */
    private static View createViewInternal (Context context, JSONObject jsonObject, ViewGroup parent, HashMap<String, Integer> ids) {

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
            ViewGroup.LayoutParams params = DynamicHelper.createLayoutParams(parent);
            view.setLayoutParams(params);

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
            view.setTag(INTERNAL_TAG_ID, properties);

            /* add and integer as a universal id  and keep it in a hashmap */
            String id = DynamicHelper.applyStyleProperties(view, properties);
            if (!TextUtils.isEmpty(id)) {
                /* to target older versions we cannot use View.generateViewId();  */
                ids.put(id, mCurrentId);
                view.setId( mCurrentId );
                mCurrentId++;
            }

            /* if view is type of ViewGroup check for its children view in json */
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;

                /* parse the aray to get the children views */
                List<View> views = new ArrayList<>();
                JSONArray jViews = jsonObject.optJSONArray("views");
                if (jViews != null) {
                    int count=jViews.length();
                    for (int i=0;i<count;i++) {
                        /* create every child add it in viewGroup and set its tag with its properties */
                        View dynamicChildView = DynamicView.createViewInternal(context, jViews.getJSONObject(i), parent, ids);
                        if (dynamicChildView!=null) {
                            views.add(dynamicChildView);
                            viewGroup.addView(dynamicChildView);
                        }
                    }
                }
                /* after create all the children apply layout properties
                * we need to do this after al children creation to have create all possible ids */
                for(View v : views) {
                    DynamicHelper.applyLayoutProperties(v, (List<DynamicProperty>) v.getTag(INTERNAL_TAG_ID), viewGroup, ids);
                    /* clear tag from properties */
                    v.setTag(INTERNAL_TAG_ID, null);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

}
