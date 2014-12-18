package com.avocarrot.json2view;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicPropertyJsonBuilder {

    private String name;
    private String type;
    private String value;

    public DynamicPropertyJsonBuilder() {}

    public DynamicPropertyJsonBuilder setName(DynamicProperty.NAME name) {
        this.name = name.toString();
        return this;
    }
    public DynamicPropertyJsonBuilder setType(DynamicProperty.TYPE type) {
        this.type = type.toString();
        return this;
    }
    public DynamicPropertyJsonBuilder setValue(Object value) {
        this.value = value.toString();
        return this;
    }

    public JSONObject build() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("value", value);
        return new JSONObject(map);
    }

}
