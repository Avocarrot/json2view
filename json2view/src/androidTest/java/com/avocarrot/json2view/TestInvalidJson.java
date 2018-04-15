package com.avocarrot.json2view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by avocarrot on 17/12/2014.
 */
@RunWith(AndroidJUnit4.class)
public class TestInvalidJson {

    private View view2test;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();

        JSONObject jsonObject = Utils.readJson("error.json", context);
        assertNotNull("Cannot parse json", jsonObject);
        view2test = DynamicView.createView(context, jsonObject);
        assertNotNull("Cannot create dynamic View", view2test);
    }

    @Test
    public void testView() {
        assertTrue("Parent View is not Relative Layout", view2test instanceof RelativeLayout);
    }
}
