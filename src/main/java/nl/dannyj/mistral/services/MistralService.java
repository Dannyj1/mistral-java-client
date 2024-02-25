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

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.*;
import lombok.NonNull;
import nl.dannyj.mistral.MistralClient;
import nl.dannyj.mistral.exceptions.UnexpectedResponseException;
import nl.dannyj.mistral.models.Request;
import nl.dannyj.mistral.models.Response;
import nl.dannyj.mistral.models.completion.ChatCompletionRequest;
import nl.dannyj.mistral.models.completion.ChatCompletionResponse;
import nl.dannyj.mistral.models.completion.Message;
import nl.dannyj.mistral.models.completion.MessageRole;
import nl.dannyj.mistral.models.embedding.EmbeddingRequest;
import nl.dannyj.mistral.models.embedding.EmbeddingResponse;
import nl.dannyj.mistral.models.model.ListModelsResponse;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * The MistralService class provides methods to interact with the Mistral AI API.
 * It uses the HttpService for making HTTP requests to the Mistral API and returns the responses as POJOs.
 */
public class MistralService {

    private final HttpService httpService;
    private final MistralClient client;
    private final Validator validator;

    /**
     * Constructor that initializes the MistralService with a provided MistralClient and HttpService.
     *
     * @param client      The MistralClient to be used for interacting with the Mistral AI API
     * @param httpService The HttpService to be used for making HTTP requests
     */
    public MistralService(@NonNull MistralClient client, @NonNull HttpService httpService) {
        this.client = client;
        this.httpService = httpService;

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
    }

    /**
     * Use the Mistral AI API to create a chat completion (an assistant reply to the conversation).
     * This is a blocking method.
     *
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return The response from the Mistral AI API containing the generated message. See {@link ChatCompletionResponse}.
     * @throws ConstraintViolationException if the request does not pass validation
     * @throws UnexpectedResponseException  if an unexpected response is received from the Mistral AI API
     * @throws IllegalArgumentException     if the first message role is not 'user' or 'system'
     */
    public ChatCompletionResponse createChatCompletion(@NonNull ChatCompletionRequest request) {
        Message firstMessage = request.getMessages().get(0);
        MessageRole role = firstMessage.getRole();

        if (firstMessage.getRole() == null || (!role.equals(MessageRole.USER) && !role.equals(MessageRole.SYSTEM))) {
            throw new IllegalArgumentException("The first message role should be either 'user' or 'system'");
        }

        return validateRequestAndPost("/chat/completions", request, ChatCompletionResponse.class);
    }

    /**
     * Use the Mistral AI API to create a chat completion (an assistant reply to the conversation).
     * This is a non-blocking/asynchronous method.
     *
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return A CompletableFuture that will complete with generated message from the Mistral AI API. See {@link ChatCompletionResponse}.
     */
    public CompletableFuture<ChatCompletionResponse> createChatCompletionAsync(@NonNull ChatCompletionRequest request) {
        return CompletableFuture.supplyAsync(() -> createChatCompletion(request));
    }

    /**
     * Lists all models available according to the Mistral AI API.
     * This is a blocking method.
     *
     * @return The response from the Mistral AI API containing the list of models. See {@link ListModelsResponse}.
     * @throws UnexpectedResponseException if an unexpected response is received from the Mistral AI API
     */
    public ListModelsResponse listModels() {
        String response = httpService.get("/models");

        try {
            return client.getObjectMapper().readValue(response, ListModelsResponse.class);
        } catch (JsonProcessingException e) {
            throw new UnexpectedResponseException("Received unexpected response from the Mistral.ai API (mistral-java-client might need to be updated): " + response, e);
        }
    }

    /**
     * Lists all models available according to the Mistral AI API.
     * This is a non-blocking/asynchronous method.
     *
     * @return A CompletableFuture that will complete with the list of models from the Mistral AI API. See {@link ListModelsResponse}.
     */
    public CompletableFuture<ListModelsResponse> listModelsAsync() {
        return CompletableFuture.supplyAsync(this::listModels);
    }

    /**
     * This method is used to create an embedding using the Mistral AI API.
     * The embeddings for the input strings. See the <a href="https://docs.mistral.ai/guides/embeddings/">mistral documentation</a> for more details on embeddings.
     * This is a blocking method.
     *
     * @param request The request to create an embedding. See {@link EmbeddingRequest}.
     * @return The response from the Mistral AI API containing the generated embedding. See {@link EmbeddingResponse}.
     * @throws ConstraintViolationException if the request does not pass validation
     * @throws UnexpectedResponseException  if an unexpected response is received from the Mistral AI API
     */
    public EmbeddingResponse createEmbedding(@NonNull EmbeddingRequest request) {
        return validateRequestAndPost("/embeddings", request, EmbeddingResponse.class);
    }

    /**
     * This method is used to create an embedding using the Mistral AI API.
     * The embeddings for the input strings. See the <a href="https://docs.mistral.ai/guides/embeddings/">mistral documentation</a> for more details on embeddings.
     * This is a non-blocking/asynchronous method.
     *
     * @param request The request to create an embedding. See {@link EmbeddingRequest}.
     * @return A CompletableFuture that will complete with the generated embedding from the Mistral AI API. See {@link EmbeddingResponse}.
     */
    public CompletableFuture<EmbeddingResponse> createEmbeddingAsync(@NonNull EmbeddingRequest request) {
        return CompletableFuture.supplyAsync(() -> createEmbedding(request));
    }

    /**
     * This method is used to validate the request and post it to the specified endpoint.
     * It first validates the request using the validator. If there are any constraint violations, it throws a ConstraintViolationException.
     * If the request is valid, it converts the request to JSON and sends a POST request to the specified endpoint.
     * The response from the endpoint is then converted back to the specified response type and returned.
     *
     * @param <T>          The type of the request. It must extend Request.
     * @param <U>          The type of the response. It must extend Response.
     * @param endpoint     The endpoint to which the request should be posted.
     * @param request      The request to be posted.
     * @param responseType The class of the response type.
     * @return The response from the endpoint, converted to the specified response type.
     * @throws ConstraintViolationException if the request does not pass validation
     * @throws UnexpectedResponseException  if an unexpected response is received from the Mistral AI API
     */
    private <T extends Request, U extends Response> U validateRequestAndPost(String endpoint, T request, Class<U> responseType) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        String response = null;

        try {
            String requestJson = client.getObjectMapper().writeValueAsString(request);
            response = httpService.post(endpoint, requestJson);

            return client.getObjectMapper().readValue(response, responseType);
        } catch (JsonProcessingException e) {
            throw new UnexpectedResponseException("Received unexpected response from the Mistral.ai API (mistral-java-client might need to be updated): " + response, e);
        }
    }
}

