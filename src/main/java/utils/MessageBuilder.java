package utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageBuilder {

    public static final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes attr) {
            String skip = attr.getName();
            return "busiType".equals(skip);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).create();


    public static final Gson errgson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes attr) {
            String skip = attr.getName();
            return "data".equals(skip);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).create();


    public static String toJson(Object target) {
        return gson.toJson(target);
    }

    public static <T> T create(String message, Class<T> clazz) {
        return gson.fromJson(message, clazz);
    }

}
