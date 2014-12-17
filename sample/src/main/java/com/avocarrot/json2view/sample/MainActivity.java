package com.avocarrot.json2view.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.avocarrot.json2view.DynamicView;
import com.avocarrot.json2view.DynamicViewId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            /* create dynamic view and return the view with the holder class attached as tag */
            View sampleView = DynamicView.createView(this, new JSONObject(readFile("sample.json", this)), SampleViewHolder.class);
            ((SampleViewHolder)sampleView.getTag()).adClose.setOnClickListener(this);
            sampleView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            setContentView(sampleView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String readFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null)  input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
    }

    /**
     * Holder class that keep UI Component from the Dynamic View
     **/
    static public class SampleViewHolder {
        public SampleViewHolder() {}
        @DynamicViewId(id = "adClose")
        public View adClose;
    }

}
