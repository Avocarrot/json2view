package com.avocarrot.json2view;

import android.content.Context;
import android.os.Build;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.GridView;

import static com.avocarrot.json2view.DynamicProperty.NAME;
import static com.avocarrot.json2view.DynamicProperty.TYPE;

/**
 * Created by avocarrot on 17/12/2014.
 */
public class TestGridViewPropertiesCase extends InstrumentationTestCase {

    DynamicViewJsonBuilder dummyJsonObj;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();
        dummyJsonObj =
                new DynamicViewJsonBuilder()
                        .setWidget("GridView");

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
    public void testNumColumn3() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.NUMCOLUMNS).setType(TYPE.INTEGER).setValue(3).build())
                        .build());

        assertTrue(view instanceof GridView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            GridView gv = (GridView) view;
            gv.measure(10, 10);
            assertEquals(3, gv.getNumColumns());
        }

        view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.NUMCOLUMNS).setType(TYPE.STRING).setValue("3").build())
                        .build());

        assertTrue(view instanceof GridView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            GridView gv = (GridView) view;
            gv.measure(10, 10);
            assertEquals(3, gv.getNumColumns());
        }
    }

}