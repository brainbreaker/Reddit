package com.carousell.brainbreaker.reddit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brainbreaker on 20/05/18.
 */

public class Response {
    @SerializedName("message")
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
