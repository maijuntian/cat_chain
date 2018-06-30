package com.mai.cat_chain.net.converter;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mai.cat_chain.net.util.AESUtil;
import com.mai.cat_chain.utils.Key;
import com.mai.cat_chain.utils.MLog;
import com.mai.cat_chain.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;

import com.mai.cat_chain.net.respone.Response;

/**
 * 当出现data为null的时候会出现空指针，在这个地方处理
 */
public class ResponseTypeAdapter implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.isJsonObject()) {
            final JsonObject obj = json.getAsJsonObject();
            int status = obj.get("status").getAsInt();
            if (status != 200) {
                return new Response(new JSONObject(), status);
            } else {
                MLog.log("typeOfT-->" + typeOfT);

                if ((typeOfT.toString().contains("Rank") || typeOfT.toString().contains("RecordList")) || (!typeOfT.toString().contains("List") && typeOfT.toString().contains("java.lang.String"))) {
                    MLog.log("obj1-->" + obj);
                    return new Gson().fromJson(obj, typeOfT);
                } else {
                    try {
                        String data = obj.get("data").getAsString();
                        data = AESUtil.decrypt(data, SharedPreferencesUtils.Companion.getString(Key.INSTANCE.getUSER_KEY()));
                        obj.remove("data");
                        JsonElement jsonElement = new JsonParser().parse(data);
                        obj.add("data", jsonElement);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MLog.log("obj2-->" + obj);
                    return new Gson().fromJson(obj, typeOfT);
                }
            }
        }
        return null;
    }

}
