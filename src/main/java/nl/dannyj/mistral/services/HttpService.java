/*
 * Copyright 2024 Danny Jelsma
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
import nl.dannyj.mistral.MistralClient;
import nl.dannyj.mistral.exceptions.MistralAPIException;
import okhttp3.*;

import java.io.IOException;

/**
 * The HttpService class is responsible for making HTTP requests to the Mistral AI API.
 * It uses the OkHttpClient from the MistralClient to execute these requests.
 */
public class HttpService {

    private static final String API_URL = "https://api.mistral.ai/v1";

    private final MistralClient client;

    /**
     * Constructor that initializes the HttpService with a provided MistralClient.
     * @param client The MistralClient to be used for making requests to the Mistral AI API
     */
    public HttpService(@NonNull MistralClient client) {
        this.client = client;
    }

    /**
     * Makes a GET request to the specified URL path.
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
     * @param urlPath The URL path to make the POST request to
     * @param body The JSON body of the POST request
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
     * Executes the provided request using the OkHttpClient from the MistralClient.
     * @param request The request to be executed
     * @return The response body as a string
     * @throws MistralAPIException If the response is not successful, the response body is null or an IOException occurs in the objectmapper
     */
    private String executeRequest(Request request) {
        OkHttpClient httpClient = client.getHttpClient();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new MistralAPIException("Received unexpected response code " + response.code() + ": " + response);
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
