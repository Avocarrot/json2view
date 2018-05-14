package com.avocarrot.json2view;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.CompoundButton;

public class TestCompundButtonViewPropertiesCase extends InstrumentationTestCase {

    DynamicViewJsonBuilder dummyJsonObj;
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getInstrumentation().getContext();
        dummyJsonObj =
                new DynamicViewJsonBuilder()
                        .setWidget("Switch");

        assertNotNull("Cannot create dynamic jsonObject", dummyJsonObj);
    }

    public void testChecked() {
        View view = DynamicView.createView(
                context,
                dummyJsonObj
                        .addProperty(new DynamicPropertyJsonBuilder().setName(DynamicProperty.NAME.CHECKED).setType(DynamicProperty.TYPE.BOOLEAN).setValue(true).build())
                        .build());
        assertEquals(((CompoundButton) view).isChecked(), true);
    }
}
