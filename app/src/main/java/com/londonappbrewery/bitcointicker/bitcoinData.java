package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class bitcoinData {

    private String mValue;

    public String getValue() {
        return mValue;
    }

    public static bitcoinData fromJson(JSONObject jsonObject) {
        try {
            bitcoinData price = new bitcoinData();
            price.mValue = jsonObject.getString("ask");

            return price;

        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}