package com.avocarrot.json2view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by avocarrot on 17/12/2014.
 */
public class Utils {

    public static JSONObject readJson(String filename, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(filename);
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
        try {
            return new JSONObject(returnString.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readAssetAsImage(String filename, Context context) {
        try {
            return BitmapFactory.decodeStream(context.getResources().getAssets().open(filename));
        } catch (Exception e) {
            return null;
        }
    }
    public static Bitmap readDrawable(int drawableId, Context context) {
        try {
            return BitmapFactory.decodeResource(context.getResources(),drawableId);
        } catch (Exception e) {
            return null;
        }
    }

    public static String readBase64(String filename, Context context) {
        try {
            InputStream ins = context.getAssets().open(filename);
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            int size = 0;
            // Read the entire resource into a local byte buffer.
            byte[] buffer = new byte[1024];
            while((size=ins.read(buffer,0,1024))>=0){
                outputStream.write(buffer,0,size);
            }
            ins.close();
            String toReturn = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
            return toReturn;
        } catch (Exception e) {
            return null;
        }
    }

}
