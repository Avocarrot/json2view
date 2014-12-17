package com.avocarrot.json2view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by avocarrot on 11/12/2014.
 * Every Property of a View is a Dynaic Property
 */
public class DynamicProperty {

    /**
     * possible types that we handle
     **/
    public enum TYPE {
        NO_VALID,
        STRING,
        DIMEN,
        INTEGER,
        COLOR,
        REF,
        BOOLEAN,
        BASE64
    }

    /**
     * possible property name that we handle
     **/
    public enum NAME {
        NO_VALID,
        ID,
        LAYOUT_WIDTH,
        LAYOUT_HEIGHT,
        PADDING_LEFT,
        PADDING_RIGHT,
        PADDING_TOP,
        PADDING_BOTTOM,
        PADDING,
        BACKGROUND,
        /* textView */
        TEXT,
        TEXTCOLOR,
        TEXTSIZE,
        ELLIPSIZE,
        MAXLINES,
        GRAVITY,
        DRAWABLETOP,
        DRAWABLEBOTTOM,
        DRAWABLELEFT,
        DRAWABLERIGHT,
        /* imageView */
        SRC,
        SCALETYPE,
        ADJUSTVIEWBOUNDS,
        /* layout */
        LAYOUT_ABOVE,
        LAYOUT_ALIGNBASELINE,
        LAYOUT_ALIGNBOTTOM,
        LAYOUT_ALIGNEND,
        LAYOUT_ALIGNLEFT,
        LAYOUT_ALIGNPARENTBOTTOM,
        LAYOUT_ALIGNPARENTEND,
        LAYOUT_ALIGNPARENTLEFT,
        LAYOUT_ALIGNPARENTRIGHT,
        LAYOUT_ALIGNPARENTSTART,
        LAYOUT_ALIGNPARENTTOP,
        LAYOUT_ALIGNRIGHT,
        LAYOUT_ALIGNSTART,
        LAYOUT_ALIGNTOP,
        LAYOUT_ALIGNWITHPARENTIFMISSING,
        LAYOUT_BELOW,
        LAYOUT_CENTERHORIZONTAL,
        LAYOUT_CENTERINPARENT,
        LAYOUT_CENTERVERTICAL,
        LAYOUT_TOENDOF,
        LAYOUT_TOLEFTOF,
        LAYOUT_TORIGHTOF,
        LAYOUT_TOSTARTOF,
        LAYOUT_GRAVITY,
        ORIENTATION
    }

    public NAME name;
    public TYPE type;
    private Object value;

    /**
     * @param v value to convert as string
     * @return Value as object depends on the type
     */
    private Object convertValue(String v) {
        if (TextUtils.isEmpty(v))
            return null;
        switch (type) {
            case INTEGER: {
                return Integer.parseInt(v);
            }
            case DIMEN: {
                if (v.endsWith("dp"))
                    return DynamicHelper.dpToPx(Float.parseFloat(v.replaceAll("dp", "")));
                else if (v.endsWith("sp"))
                    return Float.parseFloat(v.replaceAll("sp", ""));
                else if (v.endsWith("px"))
                    return Integer.parseInt(v.replaceAll("px", ""));
                else if (v.endsWith("%"))
                    return (int)(Float.parseFloat(v.replaceAll("%", ""))/100f * DynamicHelper.deviceWidth());
                else if (v.equalsIgnoreCase("match_parent"))
                    return ViewGroup.LayoutParams.MATCH_PARENT;
                else if (v.equalsIgnoreCase("wrap_content"))
                    return ViewGroup.LayoutParams.WRAP_CONTENT;
                else
                    return Integer.parseInt(v);
            }
            case COLOR: {
                if (v.startsWith("0x")) {
                    return (int) Long.parseLong(v.substring(2), 16);
                }
                return Color.parseColor(v);
            }
            case BOOLEAN: {
                if (v.equalsIgnoreCase("true")) {
                    return true;
                } else if (v.equalsIgnoreCase("true")) {
                    return false;
                }
                return Integer.parseInt(v) == 1;
            }
            case BASE64: {
                try {
                    InputStream stream = new ByteArrayInputStream(Base64.decode(v, Base64.DEFAULT));
                    return BitmapFactory.decodeStream(stream);
                }
                catch (Exception e) {
                    return null;
                }
            }
        }
        return v;
    }

    /**
     * create property and parse json
     * @param jsonObject : json to parse
     */
    public DynamicProperty(JSONObject jsonObject) {
        super();
        try {
            name = NAME.valueOf(jsonObject.getString("name").toUpperCase().trim());
        } catch (Exception e) {
            name = NAME.NO_VALID;
        }
        try {
            type = TYPE.valueOf(jsonObject.getString("type").toUpperCase().trim());
        } catch (Exception e) {
            type = TYPE.NO_VALID;
        }
        try {
            value = convertValue(jsonObject.getString("value"));
        } catch (Exception e) {}
    }

    /**
     * @param clazz
     * @param varName
     * @return search in clazz of possible variable name (varName) and return its value
     */
    public Object getValueInt(Class clazz, String varName) {
        if (this==null)
            return null;

        java.lang.reflect.Field fieldRequested = null;

        try {
            fieldRequested = clazz.getField(varName);
            if (fieldRequested!=null) {
                return fieldRequested.get(clazz);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    /** next function just cast value and return the object **/

    public int getValueColor() {
        if (type == TYPE.COLOR) return Integer.class.cast(value);
        return -1;
    }
    public String getValueString() {
        return String.class.cast(value);
    }
    public int getValueInt() {
        return Integer.class.cast(value);
    }
    public int getValueDimen() {
        return Integer.class.cast(value);
    }
    public float getValueFloat() {
        return Float.class.cast(value);
    }
    public Boolean getValueBoolean() {
        return Boolean.class.cast(value);
    }
    public Bitmap getValueBitmap() {
        return (Bitmap)value;
    }
    public Drawable getValueDrawable() {
        return new BitmapDrawable(Resources.getSystem(), getValueBitmap());
    }

}
