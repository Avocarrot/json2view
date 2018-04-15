package com.avocarrot.json2view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;

import com.avocarrot.json2view.test.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.avocarrot.json2view.DynamicProperty.NAME;
import static com.avocarrot.json2view.DynamicProperty.TYPE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by avocarrot on 17/12/2014.
 */
@RunWith(AndroidJUnit4.class)
public class TestImageViewPropertiesCase {

    private DynamicViewJsonBuilder dummyJsonObj;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        dummyJsonObj =
                new DynamicViewJsonBuilder()
                        .setWidget("ImageView");

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

    /* test set src of imageView */
    @Test
    public void testSrc() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.REF).setValue("sample").build())
                        .build());
        assertTrue((((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap()).sameAs(Utils.readDrawable(R.drawable.sample, context)));
        /* load other drawable and check */
        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.REF).setValue("sample").build())
                        .build());
        assertFalse((((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap()).sameAs(Utils.readAssetAsImage("sample2.png", context)));
    }

    /* test set src of imageView */
    @Test
    public void testSrcBase64() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.BASE64).setValue(Utils.readBase64("sample2.png", context)).build())
                        .build());
        assertTrue((((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap()).sameAs(Utils.readAssetAsImage("sample2.png", context)));
    }

}
