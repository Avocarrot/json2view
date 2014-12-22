package com.avocarrot.json2view;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import static com.avocarrot.json2view.DynamicProperty.NAME;
import static com.avocarrot.json2view.DynamicProperty.TYPE;

/**
 * Created by avocarrot on 17/12/2014.
 * Test Layout for Relative Layout container
 */
public class TestLayoutPropertiesCase extends ActivityInstrumentationTestCase2<TestActivity> {

    TestActivity activity;

    public TestLayoutPropertiesCase() {
        super(TestActivity.class);
    }

    Context context;
    DynamicViewJsonBuilder jsonObj0, jsonObj1, container;
    private static final String CONTAINER_ID = "ContainerId";
    private static final String ID0 = "ViewId0";
    private static final String ID1 = "ViewId1_withExtraText!";
    private static final String TEXT0 = "Sample Text";
    private static final String TEXT1 = "Sample Text \n Multiline for different Size";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        activity = getActivity();

        context = getInstrumentation().getContext();
        container = new DynamicViewJsonBuilder().setWidget("RelativeLayout");
        jsonObj0 = new DynamicViewJsonBuilder().setWidget("TextView");
        jsonObj1 = new DynamicViewJsonBuilder().setWidget("TextView");

        assertNotNull("Cannot create dynamic jsonObject", jsonObj0);
        assertNotNull("Cannot create dynamic jsonObject", jsonObj1);

    }

    public void testAlignedLeftTo() {
        final View view = createContainer(container, createViewWithId(jsonObj0, ID0, TEXT0), createViewWithProperty(jsonObj1, ID1, NAME.LAYOUT_ALIGNLEFT, ID0, TEXT1));
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertLeftAligned(h.v0, h.v1);
    }

    public void testAlignedRightTo() {
        final View view = createContainer(container, createViewWithId(jsonObj0, ID0, TEXT0), createViewWithProperty(jsonObj1, ID1, NAME.LAYOUT_ALIGNRIGHT, ID0, TEXT1));
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertRightAligned(h.v0, h.v1);
    }

    public void testAlignedTopTo() {
        final View view = createContainer(container, createViewWithId(jsonObj0, ID0, TEXT0), createViewWithProperty(jsonObj1, ID1, NAME.LAYOUT_ALIGNTOP, ID0, TEXT1));
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertTopAligned(h.v0, h.v1);
    }

    public void testAlignedBottomTo() {
        final View view = createContainer(container, createViewWithId(jsonObj0, ID0, TEXT0), createViewWithProperty(jsonObj1, ID1, NAME.LAYOUT_ALIGNBOTTOM, ID0, TEXT1));
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertBottomAligned(h.v0, h.v1);
    }
    public void testAlignedBaselineTo() {
        final View view = createContainer(container, createViewWithId(jsonObj0, ID0, TEXT0), createViewWithProperty(jsonObj1, ID1, NAME.LAYOUT_ALIGNBASELINE, ID0, TEXT1));
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertBaselineAligned(h.v0, h.v1);
    }
    public void testAlignedParentLeft() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_ALIGNPARENTLEFT, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertLeftAligned(h.v0, h.container);
    }
    public void testAlignedParentTop() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_ALIGNPARENTTOP, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertTopAligned(h.v0, h.container);
    }
    public void testAlignedParentRight() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_ALIGNPARENTRIGHT, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertRightAligned(h.v0, h.container);
    }
    public void testAlignedParentBottom() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_ALIGNPARENTBOTTOM, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertBottomAligned(h.v0, h.container);
    }
    public void testAlignedParentCenterHorizontal() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_CENTERHORIZONTAL, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertHorizontalCenterAligned(h.container, h.v0);
    }
    public void testAlignedParentCenterCertical() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_CENTERVERTICAL, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertVerticalCenterAligned(h.container, h.v0);
    }
    public void testAlignedParentCenter() {
        final View view = createContainer(container, createViewWithProperty(jsonObj0, ID0, NAME.LAYOUT_CENTERINPARENT, ID0, TEXT0), null);
        Holder h = (Holder) view.getTag();
        ViewAsserts.assertHorizontalCenterAligned(h.container, h.v0);
        ViewAsserts.assertVerticalCenterAligned(h.container, h.v0);
    }


    JSONObject createViewWithProperty(DynamicViewJsonBuilder jsonObj, String viewId, NAME name, Object value, String text) {
        return
            jsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.ID).setType(TYPE.STRING).setValue(viewId).build())
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXT).setType(TYPE.STRING).setValue(text).build())
                .addProperty(new DynamicPropertyJsonBuilder().setName(name).setType(TYPE.REF).setValue(value).build())
                .build();
    }

    JSONObject createViewWithId(DynamicViewJsonBuilder jsonObj, String viewId, String text) {
        return
            jsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.ID).setType(TYPE.STRING).setValue(viewId).build())
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.TEXT).setType(TYPE.STRING).setValue(text).build())
                .build();
    }

    View createContainer(DynamicViewJsonBuilder jsonObj, JSONObject child0, JSONObject child1) {

        DynamicViewJsonBuilder dvjb =
            jsonObj
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.ID).setType(TYPE.STRING).setValue(CONTAINER_ID).build())
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.LAYOUT_WIDTH).setType(TYPE.DIMEN).setValue("MATCH_PARENT").build())
                .addProperty(new DynamicPropertyJsonBuilder().setName(NAME.LAYOUT_HEIGHT).setType(TYPE.DIMEN).setValue("MATCH_PARENT").build())
                .addView(child0);
        if (child1!=null)
             dvjb.addView(child1);

        /* create View */
        final View toReturn =
            DynamicView.createView(
                context,
                dvjb.build(),
                Holder.class
            );
        /* add it in the activity to apply layout*/
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setContentView(toReturn);
            }
        });
        /* sleep until above runOnUiCode executed */
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        return toReturn;
    }

    public static class Holder {
        @DynamicViewId(id = ID0)
        TextView v0;
        @DynamicViewId(id = ID1)
        TextView v1;
        @DynamicViewId(id = CONTAINER_ID)
        View container;
    }

}
