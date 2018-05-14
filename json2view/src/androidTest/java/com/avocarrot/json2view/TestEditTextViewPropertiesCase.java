package com.avocarrot.json2view;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.EditText;

public class TestEditTextViewPropertiesCase extends InstrumentationTestCase {

    DynamicViewJsonBuilder dummyJsonObj;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();
        dummyJsonObj =
                new DynamicViewJsonBuilder()
                        .setWidget("EditText");

        assertNotNull("Cannot create dynamic jsonObject", dummyJsonObj);
    }

    public void testFocusable() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(DynamicProperty.NAME.FOCUSABLE).setType(DynamicProperty.TYPE.BOOLEAN).setValue(true).build())
                        .build());
        assertEquals(((EditText) view).isFocusable(), true);
    }

    public void testFocusuableInTouchMode() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(DynamicProperty.NAME.FOCUSABLE_IN_TOUCH_MODE).setType(DynamicProperty.TYPE.BOOLEAN).setValue(true).build())
                        .build());
        assertEquals(((EditText) view).isFocusableInTouchMode(), true);
    }
}
