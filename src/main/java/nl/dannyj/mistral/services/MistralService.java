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
import nl.dannyj.mistral.models.Message;
import nl.dannyj.mistral.models.MessageRole;
import nl.dannyj.mistral.models.request.ChatCompletionRequest;
import nl.dannyj.mistral.models.response.ChatCompletionResponse;
import nl.dannyj.mistral.models.response.ListModelsResponse;

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
     * @param client The MistralClient to be used for interacting with the Mistral AI API
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
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return The response from the Mistral AI API containing the generated message. See {@link ChatCompletionResponse}.
     * @throws ConstraintViolationException if the request does not pass validation
     * @throws UnexpectedResponseException if an unexpected response is received from the Mistral AI API
     * @throws IllegalArgumentException if the first message role is not 'user' or 'system'
     */
    public ChatCompletionResponse createChatCompletion(@NonNull ChatCompletionRequest request) {
        Set<ConstraintViolation<ChatCompletionRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Message firstMessage = request.getMessages().get(0);
        MessageRole role = firstMessage.getRole();

        if (firstMessage.getRole() == null || (!role.equals(MessageRole.USER) && !role.equals(MessageRole.SYSTEM))) {
            throw new IllegalArgumentException("The first message role should be either 'user' or 'system'");
        }

        String response = null;

        try {
            String requestJson = client.getObjectMapper().writeValueAsString(request);
            response = httpService.post("/chat/completions", requestJson);

            return client.getObjectMapper().readValue(response, ChatCompletionResponse.class);
        } catch (JsonProcessingException e) {
            throw new UnexpectedResponseException("Received unexpected response from the Mistral.ai API (mistral-java-client might need to be updated): " + response, e);
        }
    }

    /**
     * Use the Mistral AI API to create a chat completion (an assistant reply to the conversation).
     * This is a non-blocking/asynchronous method.
     * @param request The request to create a chat completion. See {@link ChatCompletionRequest}.
     * @return A CompletableFuture that will complete with generated message from the Mistral AI API. See {@link ChatCompletionResponse}.
     */
    public CompletableFuture<ChatCompletionResponse> createChatCompletionAsync(@NonNull ChatCompletionRequest request) {
        return CompletableFuture.supplyAsync(() -> createChatCompletion(request));
    }

    /**
     * Lists all models available according to the Mistral AI API.
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
}

