package com.javy.backend.shared.service;

public final class ServiceUtils {

    private ServiceUtils () {};

    //guard for the query above filterGame in GameRepository
    public static String blankToNull (String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return str;
    }

    //slugify user searches so it can be used for database search
    public static String slugify(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return str.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]+", "-");
    }


}
