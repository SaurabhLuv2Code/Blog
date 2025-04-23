package com.blog.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ApiResponse<T> extends ApiResponseSkeleton {
    ObjectMapper mapper = new ObjectMapper();
    HttpStatus httpStatus;

    public ApiResponse(HttpStatus httpStatus, Boolean success, T data, String message) throws JsonProcessingException {
        // Register JavaTimeModule to handle LocalDateTime
        mapper.registerModule(new JavaTimeModule());
        // Optional: Configure to write dates as ISO-8601 strings instead of timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        setHttpCode(httpStatus);
        setSuccess(success);
        setMessage(message);

        if (data instanceof String || data instanceof JSONArray || data instanceof JSONObject) {
            try {
                setData(mapper.readTree(String.valueOf(data)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            setData(mapper.valueToTree(data));
        }
        this.httpStatus = httpStatus;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public ResponseEntity<String> getResponse(T ApiResponse) {
        try {
            String jsonResponse = mapper.writeValueAsString(ApiResponse);
            return new ResponseEntity<>(jsonResponse, httpStatus);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
