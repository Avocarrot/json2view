package com.avocarrot.json2view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.avocarrot.json2view.test.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.avocarrot.json2view.DynamicProperty.NAME;
import static com.avocarrot.json2view.DynamicProperty.TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by avocarrot on 17/12/2014.
 */
@RunWith(AndroidJUnit4.class)
public class TestViewPropertiesCase {

    private DynamicViewJsonBuilder dummyJsonObj;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        dummyJsonObj =
                new DynamicViewJsonBuilder()
                        .setWidget("TextView");

        assertNotNull("Cannot create dynamic jsonObject", dummyJsonObj);
    }

    /* test set padding as integer */
    @Test
    public void testDummyView() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .build());
        assertNotNull("Cannot create dynamic View", view);
    }

    /* test set visibility */
    @Test
    public void testVisibility() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.VISIBILITY).setType(TYPE.STRING).setValue("gone").build())
                        .build());
        assertEquals(view.getVisibility(), View.GONE);

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.VISIBILITY).setType(TYPE.STRING).setValue("visible").build())
                        .build());
        assertEquals(view.getVisibility(), View.VISIBLE);

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.VISIBILITY).setType(TYPE.STRING).setValue("invisible").build())
                        .build());
        assertEquals(view.getVisibility(), View.INVISIBLE);
    }

    /* test set padding as integer */
    @Test
    public void testPaddingInteger() {
        int padding = 10;
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING).setType(TYPE.DIMEN).setValue(padding).build())
                        .build());
        assertEquals(view.getPaddingTop(), padding);
        assertEquals(view.getPaddingBottom(), padding);
        assertEquals(view.getPaddingLeft(), padding);
        assertEquals(view.getPaddingRight(), padding);
    }

    /* test set padding as dp value */
    @Test
    public void testPaddingDp() {
        float padding = 7.5f;
        int paddindDP = (int) DynamicHelper.dpToPx(padding);
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING).setType(TYPE.DIMEN).setValue(padding + "dp").build())
                        .build());
        assertEquals(view.getPaddingTop(), paddindDP);
        assertEquals(view.getPaddingBottom(), paddindDP);
        assertEquals(view.getPaddingLeft(), paddindDP);
        assertEquals(view.getPaddingRight(), paddindDP);
    }

    /* test padding and override value for specific view bound */
    @Test
    public void testPaddingOverrideValues() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING).setType(TYPE.DIMEN).setValue(2).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING_LEFT).setType(TYPE.DIMEN).setValue(4).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING_TOP).setType(TYPE.DIMEN).setValue(6).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING_RIGHT).setType(TYPE.DIMEN).setValue(8).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING_BOTTOM).setType(TYPE.DIMEN).setValue(10).build())
                        .build());
        assertEquals(view.getPaddingLeft(), 4);
        assertEquals(view.getPaddingTop(), 6);
        assertEquals(view.getPaddingRight(), 8);
        assertEquals(view.getPaddingBottom(), 10);
    }

    /* test padding for specific view bound and other keep its global value */
    @Test
    public void testPadding() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING).setType(TYPE.DIMEN).setValue(2).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.PADDING_BOTTOM).setType(TYPE.DIMEN).setValue(10).build())
                        .build());
        assertEquals(view.getPaddingLeft(), 2);
        assertEquals(view.getPaddingTop(), 2);
        assertEquals(view.getPaddingRight(), 2);
        assertEquals(view.getPaddingBottom(), 10);
    }

    /* test minimum Width & Height */
    @Test
    @UiThreadTest
    public void testMinDimensions() {
        final View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.MINWIDTH).setType(TYPE.DIMEN).setValue(250).build())
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.MINHEIGTH).setType(TYPE.DIMEN).setValue(100).build())
                        .build());
        assertEquals(view.getMinimumWidth(), 250);
        assertEquals(view.getMeasuredHeight(), 100);
    }

    /* test text Color */
    @Test
    public void testTextColor() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXTCOLOR).setType(TYPE.COLOR).setValue("0xFF123456").build())
                        .build());
        assertEquals(((TextView) view).getCurrentTextColor(), 0xFF123456);
    }

    /* test text value */
    @Test
    public void testTextValue() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXT).setType(TYPE.STRING).setValue("TEXT VALUE :)").build())
                        .build());
        assertEquals(((TextView) view).getText(), "TEXT VALUE :)");
        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXT).setType(TYPE.REF).setValue("test_string").build())
                        .build());
        assertEquals(((TextView) view).getText(), context.getString(R.string.test_string));
    }

    /* test text size */
    @Test
    public void testTextSize() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXTSIZE).setType(TYPE.DIMEN).setValue("12.5dp").build())
                        .build());
        assertEquals(((TextView) view).getTextSize(), DynamicHelper.dpToPx(12.5f), 0.0);
        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXTSIZE).setType(TYPE.DIMEN).setValue("14.2sp").build())
                        .build());
        assertEquals(((TextView) view).getTextSize(), DynamicHelper.spToPx(14.2f), 0.0);
    }

    /* test background Color */
    @Test
    public void testBackgroundColor() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.BACKGROUND).setType(TYPE.COLOR).setValue("red").build())
                        .build());
        assertEquals(((ColorDrawable) view.getBackground()).getColor(), 0xFFFF0000);

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.BACKGROUND).setType(TYPE.COLOR).setValue("#ff00FF").build())
                        .build());
        assertEquals(((ColorDrawable) view.getBackground()).getColor(), 0xFFFF00FF);

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.BACKGROUND).setType(TYPE.COLOR).setValue("0xFF00FF00").build())
                        .build());
        assertEquals(((ColorDrawable) view.getBackground()).getColor(), 0xFF00FF00);
    }

    /* test TextView ellipsize property */
    @Test
    public void testEllipsize() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.ELLIPSIZE).setType(TYPE.STRING).setValue(TextUtils.TruncateAt.MIDDLE.toString()).build())
                        .build());
        assertEquals(((TextView) view).getEllipsize(), TextUtils.TruncateAt.MIDDLE);
    }

    /* test TextView Gravity Property */
    @Test
    public void testTextGravity() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.GRAVITY).setType(TYPE.INTEGER).setValue(17).build())
                        .build());
        assertEquals(((TextView) view).getGravity(), 17);

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.GRAVITY).setType(TYPE.STRING).setValue("CENTER").build())
                        .build());
        assertEquals(((TextView) view).getGravity(), Gravity.CENTER);
    }

    /* test TextView maxLines Property */
    /* getMaxLines is available after JELLY_BEAN instead setMaxLines that is available from android-1
     * so need to run test in emuator with at least JELLY_BEAN */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testTextMaxLines() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.MAXLINES).setType(TYPE.INTEGER).setValue(3).build())
                        .build());
        assertEquals(((TextView) view).getMaxLines(), 3);
    }

    @Test
    public void testTag() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TAG).setType(TYPE.STRING).setValue("TAG_VALUE").build())
                        .build());
        assertEquals(view.getTag(), "TAG_VALUE");
    }

}
