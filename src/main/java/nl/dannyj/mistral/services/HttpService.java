/*
 * Copyright 2024-2025 Danny Jelsma
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.dannyj.mistral.services;

import lombok.NonNull;
import nl.dannyj.mistral.exceptions.MistralAPIException;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * The HttpService class is responsible for making HTTP requests to the Mistral AI API.
 * It uses the OkHttpClient from the MistralClient to execute these requests.
 */
public class HttpService {

    private static final String API_URL = "https://api.mistral.ai/v1";

    private final OkHttpClient httpClient;

    /**
     * Constructor that initializes the HttpService with a provided OkHttpClient.
     *
     * @param httpClient The OkHttpClient to be used for making requests to the Mistral AI API
     */
    public HttpService(@NonNull OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Makes a GET request to the specified URL path.
     *
     * @param urlPath The URL path to make the GET request to
     * @return The response body as a string
     */
    public String get(@NonNull String urlPath) {
        Request request = new Request.Builder()
                .url(API_URL + urlPath)
                .get()
                .build();

        return executeRequest(request);
    }

    /**
     * Makes a POST request to the specified URL path with the provided body.
     *
     * @param urlPath The URL path to make the POST request to
     * @param body    The JSON body of the POST request
     * @return The response body as a string
     */
    public String post(@NonNull String urlPath, @NonNull String body) {
        Request request = new Request.Builder()
                .url(API_URL + urlPath)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        return executeRequest(request);
    }

    /**
     * Makes a streaming POST request to the specified URL path with the provided body.
     *
     * @param urlPath  The URL path to make the POST request to
     * @param body     The JSON body of the POST request
     * @param callBack The callback to handle chunks received during streaming
     */
    public void streamPost(@NonNull String urlPath, @NonNull String body, Callback callBack) {
        Request request = new Request.Builder()
                .url(API_URL + urlPath)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).enqueue(callBack);
    }

    /**
     * Executes the provided request using the OkHttpClient from the MistralClient.
     *
     * @param request The request to be executed
     * @return The response body as a string
     * @throws MistralAPIException If the response is not successful, the response body is null or an IOException occurs in the objectmapper
     */
    private String executeRequest(Request request) {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new MistralAPIException("Received unexpected response code " + response.code() + ": " + (response.body() != null ? response.body().string() : response));
            }

            try (ResponseBody responseBody = response.body()) {
                if (responseBody == null) {
                    throw new MistralAPIException("Received null response from the API: " + response);
                }

                return responseBody.string();
            }
        } catch (IOException e) {
            throw new MistralAPIException(e);
        }
    }
}
