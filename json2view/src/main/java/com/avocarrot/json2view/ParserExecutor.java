package com.avocarrot.json2view;

import android.content.Context;
import android.support.annotation.UiThread;
import android.view.View;

import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fco on 13-08-17.
 */

public class ParserExecutor {

    class AsynchronousParser implements Runnable{
        public Context context;
        public JSONObject jsonObject;
        public Class viewHolderClass;
        public DynamicView.OnJsonParsedAsView onJsonParsedAsView;
        View sampleView;

        @Override
        public void run() {
            sampleView = DynamicView.createView(context, jsonObject, viewHolderClass);
            if(onJsonParsedAsView != null) onJsonParsedAsView.onBackgroundChanges(sampleView);
            finished();
        }

        @UiThread
        private void finished(){
            if(onJsonParsedAsView != null){
                if(sampleView != null){
                    onJsonParsedAsView.onSuccess(sampleView);
                }else{
                    onJsonParsedAsView.onFailure();
                }
            }
        }
    }

    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private static ThreadPoolExecutor executor;

    public static ThreadPoolExecutor getExecutor(){
        if(executor == null){
            int processors = 1;
            executor =  new ThreadPoolExecutor(processors, processors, 1, TimeUnit.SECONDS, workQueue);
        }
        return executor;
    }
}
