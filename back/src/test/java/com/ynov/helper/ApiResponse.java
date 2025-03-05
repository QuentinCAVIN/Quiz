package com.ynov.helper;

import io.restassured.http.Headers;

import java.util.Optional;

public record ApiResponse<T>(int status, Optional<T> body, Headers headers) {
    public String getLocationId(){
        return headers().getValue("Location");
    }
}
