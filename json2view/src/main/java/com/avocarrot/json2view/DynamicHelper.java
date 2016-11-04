package com.avocarrot.json2view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by avocarrot on 11/12/2014.
 * Helper function that apply properties in views
 */
public class DynamicHelper {

    /**
     * apply dynamic properties that are not relative with layout in view
     *
     * @param view
     * @param properties
     */
    public static String applyStyleProperties(View view, List<DynamicProperty> properties) {
        String id = "";
        for (DynamicProperty dynProp : properties) {
            switch (dynProp.name) {
                case ID: {
                    id = dynProp.getValueString();
                }
                break;
                case BACKGROUND: {
                    applyBackground(view, dynProp);
                }
                break;
                case TEXT: {
                    applyText(view, dynProp);
                }
                break;
                case TEXTCOLOR: {
                    applyTextColor(view, dynProp);
                }
                break;
                case TEXTSIZE: {
                    applyTextSize(view, dynProp);
                }
                break;
                case TEXTSTYLE: {
                    applyTextStyle(view, dynProp);
                }
                break;
                case PADDING: {
                    applyPadding(view, dynProp);
                }
                break;
                case PADDING_LEFT: {
                    applyPadding(view, dynProp, 0);
                }
                break;
                case PADDING_TOP: {
                    applyPadding(view, dynProp, 1);
                }
                break;
                case PADDING_RIGHT: {
                    applyPadding(view, dynProp, 2);
                }
                break;
                case PADDING_BOTTOM: {
                    applyPadding(view, dynProp, 3);
                }
                break;
                case MINWIDTH: {
                    applyMinWidth(view, dynProp);
                }
                break;
                case MINHEIGTH: {
                    applyMinHeight(view, dynProp);
                }
                break;
                case ELLIPSIZE: {
                    applyEllipsize(view, dynProp);
                }
                break;
                case MAXLINES: {
                    applyMaxLines(view, dynProp);
                }
                break;
                case ORIENTATION: {
                    applyOrientation(view, dynProp);
                }
                break;
                case SUM_WEIGHT: {
                    applyWeightSum(view, dynProp);
                }
                break;
                case GRAVITY: {
                    applyGravity(view, dynProp);
                }
                break;
                case SRC: {
                    applySrc(view, dynProp);
                }
                break;
                case SCALETYPE: {
                    applyScaleType(view, dynProp);
                }
                break;
                case ADJUSTVIEWBOUNDS: {
                    applyAdjustBounds(view, dynProp);
                }
                break;
                case DRAWABLELEFT: {
                    applyCompoundDrawable(view, dynProp, 0);
                }
                break;
                case DRAWABLETOP: {
                    applyCompoundDrawable(view, dynProp, 1);
                }
                break;
                case DRAWABLERIGHT: {
                    applyCompoundDrawable(view, dynProp, 2);
                }
                break;
                case DRAWABLEBOTTOM: {
                    applyCompoundDrawable(view, dynProp, 3);
                }
                break;
                case ENABLED: {
                    applyEnabled(view, dynProp);
                }
                break;
                case SELECTED: {
                    applySelected(view, dynProp);
                }
                break;
                case CLICKABLE: {
                    applyClickable(view, dynProp);
                }
                break;
                case SCALEX: {
                    applyScaleX(view, dynProp);
                }
                break;
                case SCALEY: {
                    applyScaleY(view, dynProp);
                }
                break;
                case TAG: {
                    applyTag(view, dynProp);
                }
                break;
                case FUNCTION: {
                    applyFunction(view, dynProp);
                }
                break;
                case VISIBILITY:{
                    applyVisibility(view, dynProp);
                }
                break;
            }
        }
        return id;
    }

    /**
     * apply dynamic properties for layout in view
     *
     * @param view
     * @param properties : layout properties to apply
     * @param viewGroup  : parent view
     * @param ids        : hashmap of ids <String, Integer> (string as setted in json, int that we use in layout)
     */
    public static void applyLayoutProperties(View view, List<DynamicProperty> properties, ViewGroup viewGroup, HashMap<String, Integer> ids) {
        if (viewGroup == null)
            return;
        ViewGroup.LayoutParams params = createLayoutParams(viewGroup);

        for (DynamicProperty dynProp : properties) {
            try {
                switch (dynProp.name) {
                    case LAYOUT_HEIGHT: {
                        params.height = dynProp.getValueInt();
                    }
                    break;
                    case LAYOUT_WIDTH: {
                        params.width = dynProp.getValueInt();
                    }
                    break;
                    case LAYOUT_MARGIN: {
                        if (params instanceof ViewGroup.MarginLayoutParams) {
                            ViewGroup.MarginLayoutParams p = ((ViewGroup.MarginLayoutParams) params);
                            p.bottomMargin = p.topMargin = p.leftMargin = p.rightMargin = dynProp.getValueInt();
                        }
                    }
                    break;
                    case LAYOUT_MARGINLEFT: {
                        if (params instanceof ViewGroup.MarginLayoutParams) {
                            ((ViewGroup.MarginLayoutParams) params).leftMargin = dynProp.getValueInt();
                        }
                    }
                    break;
                    case LAYOUT_MARGINTOP: {
                        if (params instanceof ViewGroup.MarginLayoutParams) {
                            ((ViewGroup.MarginLayoutParams) params).topMargin = dynProp.getValueInt();
                        }
                    }
                    break;
                    case LAYOUT_MARGINRIGHT: {
                        if (params instanceof ViewGroup.MarginLayoutParams) {
                            ((ViewGroup.MarginLayoutParams) params).rightMargin = dynProp.getValueInt();
                        }
                    }
                    break;
                    case LAYOUT_MARGINBOTTOM: {
                        if (params instanceof ViewGroup.MarginLayoutParams) {
                            ((ViewGroup.MarginLayoutParams) params).bottomMargin = dynProp.getValueInt();
                        }
                    }
                    break;
                    case LAYOUT_ABOVE: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ABOVE, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_BELOW: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.BELOW, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_TOLEFTOF: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.LEFT_OF, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_TORIGHTOF: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.RIGHT_OF, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_TOSTARTOF: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.START_OF, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_TOENDOF: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.END_OF, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNBASELINE: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_BASELINE, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNLEFT: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_LEFT, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNTOP: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_TOP, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNRIGHT: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_RIGHT, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNBOTTOM: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_BOTTOM, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNSTART: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_START, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNEND: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_END, ids.get(dynProp.getValueString()));
                    }
                    break;
                    case LAYOUT_ALIGNWITHPARENTIFMISSING: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).alignWithParent = dynProp.getValueBoolean();
                    }
                    break;
                    case LAYOUT_ALIGNPARENTTOP: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    }
                    break;
                    case LAYOUT_ALIGNPARENTBOTTOM: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    }
                    break;
                    case LAYOUT_ALIGNPARENTLEFT: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    }
                    break;
                    case LAYOUT_ALIGNPARENTRIGHT: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    }
                    break;
                    case LAYOUT_ALIGNPARENTSTART: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_START);
                    }
                    break;
                    case LAYOUT_ALIGNPARENTEND: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_END);
                    }
                    break;
                    case LAYOUT_CENTERHORIZONTAL: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_HORIZONTAL);
                    }
                    break;
                    case LAYOUT_CENTERVERTICAL: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_VERTICAL);
                    }
                    break;
                    case LAYOUT_CENTERINPARENT: {
                        if (params instanceof RelativeLayout.LayoutParams)
                            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_IN_PARENT);
                    }
                    break;
                    case LAYOUT_GRAVITY: {
                        switch (dynProp.type) {
                            case INTEGER: {
                                if (params instanceof LinearLayout.LayoutParams)
                                    ((LinearLayout.LayoutParams) params).gravity = dynProp.getValueInt();
                            }
                            break;
                            case STRING: {
                                if (params instanceof LinearLayout.LayoutParams)
                                    ((LinearLayout.LayoutParams) params).gravity = (Integer) dynProp.getValueInt(Gravity.class, dynProp.getValueString().toUpperCase());
                            }
                            break;
                        }
                    }
                    break;
                    case LAYOUT_WEIGHT: {
                        switch (dynProp.type) {
                            case FLOAT: {
                                if (params instanceof LinearLayout.LayoutParams)
                                    ((LinearLayout.LayoutParams) params).weight = dynProp.getValueFloat();
                            }
                            break;
                        }
                    }
                    break;
                }
            } catch (Exception e) {
            }
        }

        view.setLayoutParams(params);
    }

    public static ViewGroup.LayoutParams createLayoutParams(ViewGroup viewGroup) {
        ViewGroup.LayoutParams params = null;
        if (viewGroup!=null) {
            try {
                /* find parent viewGroup and create LayoutParams of that class */
                Class layoutClass = viewGroup.getClass();
                while (!classExists(layoutClass.getName() + "$LayoutParams")) {
                    layoutClass = layoutClass.getSuperclass();
                }
                String layoutParamsClassname = layoutClass.getName() + "$LayoutParams";
                Class layoutParamsClass = Class.forName(layoutParamsClassname);
                /* create the actual layoutParams object */
                params = (ViewGroup.LayoutParams) layoutParamsClass.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(new Object[]{ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (params == null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return params;
    }

    /*** View Properties ***/

    /**
     * apply background in view. possible type :
     * - COLOR
     * - REF => search for that drawable in resources
     * - BASE64 => convert base64 to bitmap and apply in view
     */
    public static void applyBackground(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case COLOR: {
                    view.setBackgroundColor(property.getValueColor());
                }
                break;
                case REF: {
                    view.setBackgroundResource(getDrawableId(view.getContext(), property.getValueString()));
                }
                break;
                case BASE64: {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                        view.setBackground(property.getValueBitmapDrawable());
                    else
                        view.setBackgroundDrawable(property.getValueBitmapDrawable());
                }
                break;
                case DRAWABLE: {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                        view.setBackground(property.getValueGradientDrawable());
                    else
                        view.setBackgroundDrawable(property.getValueGradientDrawable());
                }
                break;
            }
        }
    }

    /**
     * apply padding in view
     */
    public static void applyPadding(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case DIMEN: {
                    int padding = property.getValueInt();
                    view.setPadding(padding, padding, padding, padding);
                }
                break;
            }
        }
    }

    /**
     * apply padding in view
     */
    public static void applyPadding(View view, DynamicProperty property, int position) {
        if (view != null) {
            switch (property.type) {
                case DIMEN: {
                    int[] padding = new int[] {
                      view.getPaddingLeft(),
                      view.getPaddingTop(),
                      view.getPaddingRight(),
                      view.getPaddingBottom()
                    };
                    padding[position] = property.getValueInt();
                    view.setPadding(padding[0], padding[1], padding[2], padding[3]);
                }
                break;
            }
        }
    }

    /**
     * apply minimum Width in view
     */
    public static void applyMinWidth(View view, DynamicProperty property) {
        if (view != null) {
            if (property.type == DynamicProperty.TYPE.DIMEN) {
                view.setMinimumWidth(property.getValueInt());
            }
        }
    }

    /**
     * apply minimum Height in view
     */
    public static void applyMinHeight(View view, DynamicProperty property) {
        if (view != null) {
            if (property.type == DynamicProperty.TYPE.DIMEN) {
                view.setMinimumHeight(property.getValueInt());
            }
        }
    }

    /**
     * apply enabled in view
     */
    public static void applyEnabled(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case BOOLEAN: {
                    view.setEnabled(property.getValueBoolean());
                }
                break;
            }
        }
    }

    /**
     * apply selected in view
     */
    public static void applySelected(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case BOOLEAN: {
                    view.setSelected(property.getValueBoolean());
                }
                break;
            }
        }
    }
    /**
     * apply clickable in view
     */
    public static void applyClickable(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case BOOLEAN: {
                    view.setClickable(property.getValueBoolean());
                }
                break;
            }
        }
    }

    /**
     * apply selected in view
     */
    public static void applyScaleX(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case BOOLEAN: {
                    view.setScaleX(property.getValueFloat());
                }
                break;
            }
        }
    }

    /**
     * apply selected in view
     */
    public static void applyScaleY(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case BOOLEAN: {
                    view.setScaleY(property.getValueFloat());
                }
                break;
            }
        }
    }

    /**
     *  apply visibility in view
     */
    private static void applyVisibility(View view, DynamicProperty property) {
        if (view != null) {
            switch (property.type) {
                case STRING: {
                    switch (property.getValueString()){
                        case "gone":{
                            view.setVisibility(View.GONE);
                        }
                        break;
                        case "visible":{
                            view.setVisibility(View.VISIBLE);
                        }
                        break;
                        case "invisible":{
                            view.setVisibility(View.INVISIBLE);
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    /*** TextView Properties ***/

    /**
     * apply text (used only in TextView)
     * - STRING : the actual string to set in textView
     * - REF : the name of string resource to apply in textView
     */
    public static void applyText(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case STRING: {
                    ((TextView) view).setText(property.getValueString());
                }
                break;
                case REF: {
                    ((TextView) view).setText(getStringId(view.getContext(), property.getValueString()));
                }
                break;
            }
        }
    }

    /**
     * apply the color in textView
     */
    public static void applyTextColor(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case COLOR: {
                    ((TextView) view).setTextColor(property.getValueColor());
                }
                break;
            }
        }
    }

    /**
     * apply the textSize in textView
     */
    public static void applyTextSize(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case DIMEN: {
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, property.getValueFloat());
                }
                break;
            }
        }
    }
    /**
     * apply the textStyle in textView
     */
    public static void applyTextStyle(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case INTEGER: {
                    ((TextView) view).setTypeface(null, property.getValueInt());
                }
                break;
            }
        }
    }

    /**
     * apply ellipsize property in textView
     */
    public static void applyEllipsize(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            ((TextView) view).setEllipsize(TextUtils.TruncateAt.valueOf(property.getValueString().toUpperCase().trim()));
        }
    }

    /**
     * apply maxLines property in textView
     */
    public static void applyMaxLines(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            ((TextView) view).setMaxLines(property.getValueInt());
        }
    }

    /**
     * apply gravity property in textView
     * - INTEGER => valus of gravity in @link(Gravity.java)
     * - STRING => name of variable in @lin(Gravity.java)
     */
    public static void applyGravity(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case INTEGER: {
                    ((TextView) view).setGravity(property.getValueInt());
                }
                break;
                case STRING: {
                    ((TextView) view).setGravity((Integer) property.getValueInt(Gravity.class, property.getValueString().toUpperCase()));
                }
                break;
            }
        }
    }

    /**
     * apply compound property in textView
     * position 0:left, 1:top, 2:right, 3:bottom
     * - REF : drawable to load as compoundDrawable
     * - BASE64 : decode as base64 and set as CompoundDrawable
     */
    public static void applyCompoundDrawable(View view, DynamicProperty property, int position) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Drawable[] d = textView.getCompoundDrawables();
            switch (property.type) {
                case REF: {
                    try {
                        d[position] = view.getContext().getResources().getDrawable(getDrawableId(view.getContext(), property.getValueString()));
                    } catch (Exception e) {}
                }
                break;
                case BASE64: {
                    d[position] = property.getValueBitmapDrawable();
                }
                break;
                case DRAWABLE: {
                    d[position] = property.getValueGradientDrawable();
                }
                break;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(d[0], d[1], d[2], d[3]);
        }
    }


    /*** ImageView Properties ***/

    /**
     * apply src property in imageView
     * - REF => name of drawable
     * - BASE64 => decode value as base64 image
     */
    public static void applySrc(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case REF: {
                    ((ImageView) view).setImageResource(getDrawableId(view.getContext(), property.getValueString()));
                }
                break;
                case BASE64: {
                    ((ImageView) view).setImageBitmap(property.getValueBitmap());
                }
                break;
            }
        }
    }

    /**
     * apply scaleType property in ImageView
     */
    public static void applyScaleType(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case STRING: {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.valueOf(property.getValueString().toUpperCase()));
                }
                break;
            }
        }
    }

    /**
     * apply adjustBounds property in ImageView
     */
    public static void applyAdjustBounds(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case BOOLEAN: {
                    ((ImageView) view).setAdjustViewBounds(property.getValueBoolean());
                }
                break;
            }
        }
    }

    /*** LinearLayout Properties ***/

    /**
     * apply orientation property in LinearLayout
     * - INTEGER => 0:Horizontal , 1:Vertical
     * - STRING
     */
    public static void applyOrientation(View view, DynamicProperty property) {
        if (view instanceof LinearLayout) {
            switch (property.type) {
                case INTEGER: {
                    ((LinearLayout) view).setOrientation(property.getValueInt() == 0 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
                }
                break;
                case STRING: {
                    ((LinearLayout) view).setOrientation(property.getValueString().equalsIgnoreCase("HORIZONTAL") ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
                }
                break;
            }
        }
    }

    /**
     * apply WeightSum property in LinearLayout
     */
    public static void applyWeightSum(View view, DynamicProperty property) {
        if ((view instanceof LinearLayout) && (property.type == DynamicProperty.TYPE.FLOAT)) {
            ((LinearLayout) view).setWeightSum(property.getValueFloat());
        }
    }

    /**
     * add string as tag
     */
    public static void applyTag(View view, DynamicProperty property) {
        view.setTag(property.getValueString());
    }


    /**
     * apply generic function in View
     */
    public static void applyFunction(View view, DynamicProperty property) {

        if (property.type == DynamicProperty.TYPE.JSON) {
            try {
                JSONObject json = property.getValueJSON();

                String functionName = json.getString("function");
                JSONArray args = json.getJSONArray("args");

                Class[] argsClass;
                Object[] argsValue;
                if (args==null) {
                    argsClass = new Class[0];
                    argsValue = new Object[0];
                } else {
                    try {
                        List<Class> classList = new ArrayList<>();
                        List<Object> valueList= new ArrayList<>();

                        int i=0;
                        int count = args.length();
                        for (; i<count ; i++) {
                            JSONObject argJsonObj = args.getJSONObject(i);
                            boolean isPrimitive = argJsonObj.has("primitive");
                            String className = argJsonObj.getString( isPrimitive ? "primitive" : "class");
                            String classFullName = className;
                            if (!classFullName.contains("."))
                                classFullName = "java.lang." + className;
                            Class clazz = Class.forName(classFullName);
                            if (isPrimitive) {
                                Class primitiveType = (Class)clazz.getField("TYPE").get(null);
                                classList.add( primitiveType );
                            } else {
                                classList.add( clazz );
                            }

                            try {
                                valueList.add( getFromJSON(argJsonObj, "value", clazz) );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        argsClass = classList.toArray(new Class[classList.size()]);
                        argsValue = valueList.toArray(new Object[valueList.size()]);
                    } catch (Exception e) {
                        argsClass = new Class[0];
                        argsValue = new Object[0];
                    }
                }

                try {
                    view.getClass().getMethod(functionName, argsClass).invoke(view, argsValue);
                } catch (SecurityException e) {
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * return the id (from the R.java autogenerated class) of the drawable that pass its name as argument
     */
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * return the id (from the R.java autogenerated class) of the string that pass its name as argument
     */
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    /**
     * convert densityPixel to pixel
     */
    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * convert scalePixel to pixel
     */
    public static float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * convert pixel to densityPixel
     */
    public static float pxToDp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * convert pixel to scaledDensityPixel
     */
    public static float pxToSp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * convert densityPixel to scaledDensityPixel
     */
    public static float dpToSp(float dp) {
        return (int) ( dpToPx(dp) / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * return device Width
     */
    public static int deviceWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * get ViewHolder class and make reference for evert @link(DynamicViewId) to the actual view
     * if target contains HashMap<String, Integer> will replaced with the idsMap
     */
    public static void parseDynamicView(Object target, View container, HashMap<String, Integer> idsMap) {

        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DynamicViewId.class)) {
                /* if variable is annotated with @DynamicViewId */
                final DynamicViewId dynamicViewIdAnnotation = field.getAnnotation(DynamicViewId.class);
                /* get the Id of the view. if it is not set in annotation user the variable name */
                String id = dynamicViewIdAnnotation.id();
                if (id.equalsIgnoreCase(""))
                    id = field.getName();
                if (idsMap.containsKey(id)) {
                    try {
                        /* get the view Id from the Hashmap and make the connection to the real View */
                        field.set(target, container.findViewById(idsMap.get(id)));
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else if ((field.getName().equalsIgnoreCase("ids")) && (field.getType() == idsMap.getClass())) {
                try {
                    field.set(target, idsMap);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static Object getFromJSON(JSONObject json, String name, Class clazz) throws JSONException {
        if ((clazz == Integer.class)||(clazz == Integer.TYPE)) {
            return json.getInt(name);
        } else if ((clazz == Boolean.class)||(clazz == Boolean.TYPE)) {
            return json.getBoolean(name);
        } else if ((clazz == Double.class)||(clazz == Double.TYPE)) {
            return json.getDouble(name);
        } else if ((clazz == Float.class)||(clazz == Float.TYPE)) {
            return (float)json.getDouble(name);
        } else if ((clazz == Long.class)||(clazz == Long.TYPE)) {
            return json.getLong(name);
        } else if (clazz == String.class) {
            return json.getString(name);
        } else if (clazz == JSONObject.class) {
            return json.getJSONObject(name);
        } else {
            return json.get(name);

        }
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

}
