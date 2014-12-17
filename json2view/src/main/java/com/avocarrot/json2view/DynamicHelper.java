package com.avocarrot.json2view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by avocarrot on 11/12/2014.
 * Helper function that apply properties in views
 */
public class DynamicHelper {

    /**
     * apply dynamic properties that are not relative with layout in view
     * @param view
     * @param properties
     **/
    public static String applyStyleProperties(View view, List<DynamicProperty> properties) {
        String id = "";
        for (DynamicProperty dynProp : properties) {
            switch (dynProp.name) {
                case ID: {
                    id = dynProp.getValueString();
                } break;
                case BACKGROUND: {
                    applyBackground(view, dynProp);
                } break;
                case TEXT: {
                    applyText(view, dynProp);
                } break;
                case TEXTCOLOR: {
                    applyTextColor(view, dynProp);
                } break;
                case TEXTSIZE: {
                    applyTextSize(view, dynProp);
                } break;
                case PADDING: {
                    applyPadding(view, dynProp);
                } break;
                case ELLIPSIZE: {
                    applyEllipsize(view, dynProp);
                } break;
                case MAXLINES: {
                    applyMaxLines(view, dynProp);
                } break;
                case ORIENTATION: {
                    applyOrientation(view, dynProp);
                } break;
                case GRAVITY: {
                    applyGravity(view, dynProp);
                } break;
                case SRC: {
                    applySrc(view, dynProp);
                } break;
                case SCALETYPE: {
                    applyScaleType(view, dynProp);
                } break;
                case ADJUSTVIEWBOUNDS: {
                    applyAdjustBounds(view, dynProp);
                } break;
                case DRAWABLELEFT: {
                    applyCompoundDrawable(view, dynProp, 0);
                } break;
                case DRAWABLETOP: {
                    applyCompoundDrawable(view, dynProp, 1);
                } break;
                case DRAWABLERIGHT: {
                    applyCompoundDrawable(view, dynProp, 2);
                } break;
                case DRAWABLEBOTTOM: {
                    applyCompoundDrawable(view, dynProp, 3);
                } break;
            }
        }
        return id;
    }

    /**
     * apply dynamic properties for layout in view
     * @param view
     * @param properties : layout properties to apply
     * @param viewGroup : parent view
     * @param ids : hashmap of ids <String, Integer> (string as setted in json, int that we use in layout)
     **/
    public static void applyLayoutProperties(View view, List<DynamicProperty> properties, ViewGroup viewGroup, HashMap<String, Integer> ids) {
        if (viewGroup==null)
            return;
        ViewGroup.LayoutParams params = null;
        try {
            /* find parent viewGroup and create LayoutParams of that class */
            String layoutParamsClassname = viewGroup.getClass().getName() + "$LayoutParams";
            Class layoutParamsClass = Class.forName(layoutParamsClassname);
            /* create the actual layoutParams object */
            params = (ViewGroup.LayoutParams) layoutParamsClass.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(new Object[]{ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (params==null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (DynamicProperty dynProp : properties) {
            switch (dynProp.name) {
                case LAYOUT_HEIGHT: {
                    params.height = dynProp.getValueInt();
                } break;
                case LAYOUT_WIDTH: {
                    params.width = dynProp.getValueInt();
                } break;
                case LAYOUT_ABOVE: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ABOVE, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_BELOW: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.BELOW, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_TOLEFTOF: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.LEFT_OF, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_TORIGHTOF: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.RIGHT_OF, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_TOSTARTOF: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.START_OF, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_TOENDOF: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.END_OF, ids.get(dynProp.getValueString()));
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTTOP: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTBOTTOM: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTLEFT: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTRIGHT: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTSTART: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_START);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_ALIGNPARENTEND: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.ALIGN_PARENT_END);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_CENTERHORIZONTAL: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.CENTER_HORIZONTAL);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_CENTERVERTICAL: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.CENTER_VERTICAL);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_CENTERINPARENT: {
                    try {
                        ((RelativeLayout.LayoutParams)params).addRule(RelativeLayout.CENTER_IN_PARENT);
                    } catch (Exception e) {}
                } break;
                case LAYOUT_GRAVITY: {
                    try {
                        switch (dynProp.type) {
                            case INTEGER: {
                                ((LinearLayout.LayoutParams)params).gravity = dynProp.getValueInt();
                            }
                            break;
                            case STRING: {
                                ((LinearLayout.LayoutParams)params).gravity = (Integer) dynProp.getValueInt(Gravity.class, dynProp.getValueString().toUpperCase());
                            }
                            break;
                        }
                    } catch (Exception e) {}
                } break;
            }
        }

        view.setLayoutParams(params);
    }

    /*** View Properties ***/

    /**
     * apply background in view. possible type :
     * - COLOR
     * - REF => search for that drawable in resources
     * - BASE64 => convert base64 to bitmap and apply in view
     **/
    public static void applyBackground(View view, DynamicProperty property) {
        if (view!=null) {
            switch (property.type) {
                case COLOR: {
                    view.setBackgroundColor(property.getValueColor());
                } break;
                case REF: {
                    view.setBackgroundResource(getDrawableId(view.getContext(), property.getValueString()));
                } break;
                case BASE64: {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                        view.setBackground(property.getValueDrawable());
                    else
                        view.setBackgroundDrawable(property.getValueDrawable());
                } break;
            }
        }
    }

    /**
     * apply padding in view
     **/
    public static void applyPadding(View view, DynamicProperty property) {
        if (view!=null) {
            switch (property.type) {
                case DIMEN: {
                    int padding = property.getValueInt();
                    view.setPadding(padding, padding, padding, padding);
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
     **/
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
     **/
    public static void applyTextSize(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            switch (property.type) {
                case DIMEN: {
                    ((TextView) view).setTextSize(property.getValueFloat());
                }
                break;
            }
        }
    }

    /**
     * apply ellipsize property in textView
     **/
    public static void applyEllipsize(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            ((TextView) view).setEllipsize(TextUtils.TruncateAt.valueOf(property.getValueString().toUpperCase().trim()));
        }
    }

    /**
     * apply maxLines property in textView
     **/
    public static void applyMaxLines(View view, DynamicProperty property) {
        if (view instanceof TextView) {
            ((TextView) view).setMaxLines(property.getValueInt());
        }
    }

    /**
     * apply gravity property in textView
     * - INTEGER => valus of gravity in @link(Gravity.java)
     * - STRING => name of variable in @lin(Gravity.java)
     **/
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
     **/
    public static void applyCompoundDrawable(View view, DynamicProperty property, int position) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Drawable[] d = textView.getCompoundDrawables();
            d[position] = property.getValueDrawable();
            switch (property.type) {
                case REF: {
                    try {
                        d[position] = view.getContext().getResources().getDrawable(getDrawableId(view.getContext(), property.getValueString()));
                    } catch (Exception e) {}
                } break;
                case BASE64: {
                    d[position] = property.getValueDrawable();
                } break;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(d[0], d[1], d[2], d[3]);
        }
    }


    /*** ImageView Properties ***/

    /**
     * apply src property in imageView
     * - REF => name of drawable
     * - BASE64 => decode value as base64 image
     **/
    public static void applySrc(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case REF: {
                    ((ImageView) view).setImageResource(getDrawableId(view.getContext(), property.getValueString()));
                } break;
                case BASE64: {
                    ((ImageView) view).setImageBitmap(property.getValueBitmap());
                } break;
            }
        }
    }

    /**
     * apply scaleType property in ImageView
     **/
    public static void applyScaleType(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case STRING: {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.valueOf(property.getValueString().toUpperCase()));
                } break;
            }
        }
    }

    /**
     * apply adjustBounds property in ImageView
     **/
    public static void applyAdjustBounds(View view, DynamicProperty property) {
        if (view instanceof ImageView) {
            switch (property.type) {
                case BOOLEAN: {
                    ((ImageView) view).setAdjustViewBounds(property.getValueBoolean());
                } break;
            }
        }
    }

    /*** LinearLayout Properties ***/

    /**
     * apply orientation property in LinearLayout
     * - INTEGER => 0:Horizontal , 1:Vertical
     * - STRING
     **/
    public static void applyOrientation(View view, DynamicProperty property) {
        if (view instanceof LinearLayout) {
            switch (property.type) {
                case INTEGER: {
                    ((LinearLayout) view).setOrientation(property.getValueInt() == 0 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
                } break;
                case STRING: {
                    ((LinearLayout) view).setOrientation( property.getValueString().equalsIgnoreCase("HORIZONTAL") ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL );
                } break;
            }
        }
    }



    /**
     * return the id (from the R.java autogenerated class) of the drawable that pass its name as argument
     **/
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * return the id (from the R.java autogenerated class) of the string that pass its name as argument
     **/
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    /**
     * convert densityPixel to pixel
     **/
    public static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * convert scalePixel to pixel
     **/
    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * convert pixel to densityPixel
     **/
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * return device Width
     */
    public static int deviceWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * get ViewHolder class and make reference for evert @link(DynamicViewId) to the actual view
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
            }

        }

    }


}
