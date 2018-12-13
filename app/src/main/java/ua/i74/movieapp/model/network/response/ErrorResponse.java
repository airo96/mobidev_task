package ua.i74.movieapp.model.network.response;

import com.squareup.moshi.Json;

public class ErrorResponse {
    @Json(name = "status_message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
