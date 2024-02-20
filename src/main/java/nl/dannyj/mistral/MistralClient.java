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

package nl.dannyj.mistral;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nl.dannyj.mistral.interceptors.MistralHeaderInterceptor;
import nl.dannyj.mistral.models.request.ChatCompletionRequest;
import nl.dannyj.mistral.models.response.ChatCompletionResponse;
import nl.dannyj.mistral.models.response.ListModelsResponse;
import nl.dannyj.mistral.services.HttpService;
import nl.dannyj.mistral.services.MistralService;
import okhttp3.OkHttpClient;

import java.util.concurrent.CompletableFuture;

/**
 * The MistralClient is the main class that interacts with all components of this library.
 * It initializes all the necessary components and provides methods to interact with the Mistral AI API.
 */
@Setter
@Getter
public class MistralClient {

    private static final String API_KEY_ENV_VAR = "MISTRAL_API_KEY";

    private String apiKey;

    private OkHttpClient httpClient;

    private ObjectMapper objectMapper;

    private MistralService mistralService;

    /**
     * Constructor that initializes the MistralClient with a provided API key.
     * @param apiKey The API key to be used for the Mistral AI API
     */
    public MistralClient(@NonNull String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = buildHttpClient();
        this.objectMapper = buildObjectMapper();
        this.mistralService = buildMistralService();
    }

    /**
     * Default constructor that initializes the MistralClient with the API key from the environment variable "MISTRAL_API_KEY".
     */
    public MistralClient() {
        this.apiKey = System.getenv(API_KEY_ENV_VAR);
        this.httpClient = buildHttpClient();
        this.objectMapper = buildObjectMapper();
        this.mistralService = buildMistralService();
    }

    /**
     * Constructor that initializes the MistralClient with a provided API key, HTTP client, and object mapper.
     * @param apiKey The API key to be used for the Mistral AI API
     * @param httpClient The OkHttpClient to be used for making requests to the Mistral AI API
     * @param objectMapper The Jackson ObjectMapper to be used for serializing and deserializing JSON
     */
    public MistralClient(@NonNull String apiKey, @NonNull OkHttpClient httpClient, @NonNull ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.mistralService = buildMistralService();
    }

    /**
     * Constructor that initializes the MistralClient with a provided API key and HTTP client.
     * @param apiKey The API key to be used for the Mistral AI API
     * @param httpClient The OkHttpClient to be used for making requests to the Mistral AI API
     */
    public MistralClient(@NonNull String apiKey, @NonNull OkHttpClient httpClient) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
        this.objectMapper = buildObjectMapper();
        this.mistralService = buildMistralService();
    }

    /**
     * Constructor that initializes the MistralClient with a provided API key and object mapper.
     * @param apiKey The API key to be used for the Mistral AI API
     * @param objectMapper The Jackson ObjectMapper to be used for serializing and deserializing JSON
     */
    public MistralClient(@NonNull String apiKey, @NonNull ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.httpClient = buildHttpClient();
        this.objectMapper = objectMapper;
        this.mistralService = buildMistralService();
    }

    /**
     * Use the Mistral AI API to create a chat completion (an assistant reply to the conversation).
     * This is a blocking method.
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return The response from the Mistral AI API containing the generated message. See {@link ChatCompletionResponse}.
     */
    public ChatCompletionResponse createChatCompletion(@NonNull ChatCompletionRequest request) {
        return mistralService.createChatCompletion(request);
    }

    /**
     * Use the Mistral AI API to create a chat completion (an assistant reply to the conversation).
     * This is a non-blocking/asynchronous method.
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return A CompletableFuture that will complete with generated message from the Mistral AI API. See {@link ChatCompletionResponse}.
     */
    public CompletableFuture<ChatCompletionResponse> createChatCompletionAsync(@NonNull ChatCompletionRequest request) {
        return mistralService.createChatCompletionAsync(request);
    }

    /**
     * Lists all models available according to the Mistral AI API.
     * @return The response from the Mistral AI API containing the list of models. See {@link ListModelsResponse}.
     */
    public ListModelsResponse listModels() {
        return mistralService.listModels();
    }

    /**
     * Builds the MistralService.
     * @return A new instance of MistralService
     */
    private MistralService buildMistralService() {
        return new MistralService(this, new HttpService(this));
    }

    /**
     * Builds the HTTP client.
     * @return A new instance of OkHttpClient
     */
    private OkHttpClient buildHttpClient() {
        MistralHeaderInterceptor mistralInterceptor = new MistralHeaderInterceptor(this);

        return new OkHttpClient.Builder()
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(mistralInterceptor)
                .build();
    }

    /**
     * Builds the object mapper.
     * @return A new instance of ObjectMapper
     */
    private ObjectMapper buildObjectMapper() {
        return new ObjectMapper();
    }
}
