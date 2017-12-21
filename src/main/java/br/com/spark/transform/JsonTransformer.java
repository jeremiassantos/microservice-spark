package br.com.spark.transform;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonTransformer::toJson;
    }

    public static <T> T toObject(String body, Class<T> tClass) {
        return new Gson().fromJson(body, tClass);
    }
}