package com.avocarrot.json2view;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONObject;

/**
 * Created by avocarrot on 17/12/2014.
 */
public class TestInvalidJson extends InstrumentationTestCase {

    JSONObject jsonObject;
    View view2test;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();

        jsonObject = Utils.readJson("error.json", context);
        assertNotNull("Cannot parse json", jsonObject);
        view2test = DynamicView.createView(context, jsonObject);
        assertNotNull("Cannot create dynamic View", view2test);

    }

    public void testView() {
        assertTrue("Parent View is not Relative Layout", view2test instanceof RelativeLayout);
    }

}
