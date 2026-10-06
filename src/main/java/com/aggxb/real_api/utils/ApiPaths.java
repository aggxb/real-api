package com.aggxb.real_api.utils;

public class ApiPaths {

    public static final String BASE_PATH = "/api/v1";
    public static final String AUTH_PATH = BASE_PATH + "/auth";
    public static final String USERS_PATH = BASE_PATH + "/users";

    private ApiPaths() {
        throw new IllegalStateException("Classe utilitária");
    }
}
