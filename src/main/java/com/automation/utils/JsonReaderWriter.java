package com.automation.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonReaderWriter {
    private JSONObject jsonObject;
    private String filePath;

    public JsonReaderWriter(String filePath) {
        this.filePath = filePath;
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            jsonObject = (JSONObject) parser.parse(reader);
            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JSON file");
        }
    }

    public String getValue(String key) {
        try {
            return jsonObject.containsKey(key) ? jsonObject.get(key).toString() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setValue(String key, String value) {
        try {
            jsonObject.put(key, value);
            saveJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNestedValue(String parentKey, String childKey) {
        try {
            JSONObject parentObject = (JSONObject) jsonObject.get(parentKey);
            return parentObject != null && parentObject.containsKey(childKey) ? parentObject.get(childKey).toString() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setNestedValue(String parentKey, String childKey, String value) {
        try {
            JSONObject parentObject = (JSONObject) jsonObject.get(parentKey);
            if (parentObject == null) {
                parentObject = new JSONObject();
            }
            parentObject.put(childKey, value);
            jsonObject.put(parentKey, parentObject);
            saveJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addArrayElement(String arrayKey, String value) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get(arrayKey);
            if (jsonArray == null) {
                jsonArray = new JSONArray();
            }
            jsonArray.add(value);
            jsonObject.put(arrayKey, jsonArray);
            saveJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getArrayElement(String arrayKey, int index) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get(arrayKey);
            if (jsonArray != null && index >= 0 && index < jsonArray.size()) {
                return jsonArray.get(index).toString();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void printJson() {
        try {
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveJson() {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write to JSON file");
        }
    }
}