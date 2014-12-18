package com.avocarrot.json2view;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicViewJsonBuilder {

    private String widget = "";
    private List<JSONObject> properties;
    private List<JSONObject> views;

    public DynamicViewJsonBuilder() {
        properties = new ArrayList<>();
        views = new ArrayList<>();
    }

    public DynamicViewJsonBuilder setWidget(String widget) {
        this.widget = widget;
        return this;
    }
    public DynamicViewJsonBuilder addProperty(JSONObject property) {
        this.properties.add(property);
        return this;
    }
    public DynamicViewJsonBuilder addView(JSONObject view) {
        this.views.add(view);
        return this;
    }

    public JSONObject build() {
        Map<String, Object> map = new HashMap<>();
        map.put("widget", widget);
        map.put("properties", new JSONArray(properties));
        map.put("views", new JSONArray(views));
        return new JSONObject(map);
    }

}
