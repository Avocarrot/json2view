package com.avocarrot.json2view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.ImageView;

import static com.avocarrot.json2view.DynamicProperty.NAME;
import static com.avocarrot.json2view.DynamicProperty.TYPE;

/**
 * Created by avocarrot on 17/12/2014.
 */
public class TestImageViewPropertiesCase extends InstrumentationTestCase {

    DynamicViewJsonBuilder dummyJsonObj;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();
        dummyJsonObj =
            new DynamicViewJsonBuilder()
                .setWidget("ImageView");

        assertNotNull("Cannot create dynamic jsonObject", dummyJsonObj);

    }

    /* test set padding as integer */
    public void testDummyView() {
        View view = DynamicView.createView(
            context,
            dummyJsonObj
                .build());
        assertNotNull("Cannot create dynamic View", view);
    }

    /* test set src of imageView */
    public void testSrc() {
        View view = DynamicView.createView(
            context,
            dummyJsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.REF).setValue("sample").build())
                .build());
        assertTrue( ( ((BitmapDrawable)((ImageView)view).getDrawable()).getBitmap() ).sameAs(Utils.readDrawable(com.avocarrot.json2view.test.R.drawable.sample, context)));
        /* load other drawable and check */
        view = DynamicView.createView(
            context,
            dummyJsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.REF).setValue("sample").build())
                .build());
        assertFalse( ( ((BitmapDrawable)((ImageView)view).getDrawable()).getBitmap() ).sameAs(Utils.readAssetAsImage("sample2.png", context)));
    }

    /* test set src of imageView */
    public void testSrcBase64() {
        View view = DynamicView.createView(
            context,
            dummyJsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.SRC).setType(TYPE.BASE64).setValue(Utils.readBase64("sample2.png", context)).build())
                .build());
        assertTrue( ( ((BitmapDrawable)((ImageView)view).getDrawable()).getBitmap() ).sameAs(Utils.readAssetAsImage("sample2.png", context)));
    }

}
