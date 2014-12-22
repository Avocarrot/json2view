package com.avocarrot.json2view;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by avocarrot on 17/12/2014.
 */
public class TestSampleCase extends InstrumentationTestCase {

    JSONObject jsonObject;
    View view2test;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();
        jsonObject = Utils.readJson("sample.json", context);
        assertNotNull("Cannot parse json", jsonObject);
        view2test = DynamicView.createView(context, jsonObject);
        assertNotNull("Cannot create dynamic View", view2test);

    }

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
