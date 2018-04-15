package com.avocarrot.json2view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by avocarrot on 17/12/2014.
 */
@RunWith(AndroidJUnit4.class)
public class TestSampleCase {

    private JSONObject jsonObject;
    private View view2test;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        jsonObject = Utils.readJson("sample.json", context);
        assertNotNull("Cannot parse json", jsonObject);
        view2test = DynamicView.createView(context, jsonObject);
        assertNotNull("Cannot create dynamic View", view2test);

    }

    @Test
    public void testView() {
        assertTrue("Parent View is not Relative Layout", view2test instanceof RelativeLayout);
        RelativeLayout relative = (RelativeLayout) view2test;
        assertTrue("expecting Linear Layout", relative.getChildAt(0) instanceof LinearLayout);
        LinearLayout linearLayout = (LinearLayout) relative.getChildAt(0);
        assertEquals("expecting 4 children", linearLayout.getChildCount(), 4);
        assertEquals(linearLayout.getChildAt(0).getClass(), TextView.class);
        assertEquals(linearLayout.getChildAt(1).getClass(), TextView.class);
        assertEquals(linearLayout.getChildAt(2).getClass(), ImageView.class);
        assertEquals(linearLayout.getChildAt(3).getClass(), TextView.class);
    }
}
