package org.example.loanservicedtb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class CustomOkHttp3Client {

    private final OkHttpClient client = getPostClient();
    private final OkHttpClient postClient = getPostClient();
    private final ObjectMapper objectMapper;

    private OkHttpClient getPostClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(200, TimeUnit.SECONDS) // Set connect timeout to 15 seconds
                .readTimeout(200, TimeUnit.SECONDS)    // Set read timeout to 20 seconds
                .build();
    }

    public Response sendPostRequest(String url, Object jsonBody, Headers headers) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = objectMapper.writeValueAsString(jsonBody);
        RequestBody requestBody = RequestBody.create(jsonString, JSON);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
//            try () {
//                return response.body().string();
//            }
    }
}
