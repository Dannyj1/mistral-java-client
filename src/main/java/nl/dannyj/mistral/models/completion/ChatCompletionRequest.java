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

package nl.dannyj.mistral.models.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.MistralClient;
import nl.dannyj.mistral.models.Request;
import nl.dannyj.mistral.models.completion.message.ChatMessage;
import nl.dannyj.mistral.models.completion.tool.SpecificToolChoice;
import nl.dannyj.mistral.models.completion.tool.Tool;
import nl.dannyj.mistral.models.completion.tool.ToolChoiceEnum;
import nl.dannyj.mistral.models.completion.tool.ToolChoiceOption;
import nl.dannyj.mistral.net.ChatCompletionChunkCallback;

import java.util.List;

/**
 * The ChatCompletionRequest class represents a request to create a chat completion (an assistant reply to the conversation).
 * Most of the field descriptions are taken from the Mistral API documentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatCompletionRequest implements Request {

    /**
     * ID of the model to use. You can use the List Available Models API ({@link MistralClient#listModels()}) to see all of your available models.
     *
     * @param model The model's ID. Can't be null or empty.
     * @return The model's ID.
     */
    @NotNull
    @NotBlank
    private String model;

    /**
     * What sampling temperature to use, Mistral recommends this to be between 0.0 and 0.7.
     * Higher values like 0.7 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     * Mistral generally recommends altering this or top_p but not both. The default value varies depending on the model you are targeting.
     *
     * @param temperature The sampling temperature to use. Has to be between 0.0 and 1.5. Null will default to the model's default value.
     * @return The sampling temperature to use.
     */
    @Nullable
    @Builder.Default
    @DecimalMin("0.0")
    @DecimalMax("1.5")
    private Double temperature = null;

    /**
     * Nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * We generally recommend altering this or temperature but not both.
     * Defaults to 1.0 (i.e., no nucleus sampling).
     *
     * @param topP the top p value to use. Has to be between 0.0 and 1.0.
     * @return the top p value to use.
     */
    @JsonProperty("top_p")
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @NotNull
    @Builder.Default
    private Double topP = 1.0;

    /**
     * The maximum number of tokens to generate in the completion.
     * The token count of your prompt plus max_tokens cannot exceed the model's context length.
     *
     * @param maxTokens The maximum number of tokens to generate in the completion. Has to be positive or zero. Null for the model's default value.
     * @return The maximum number of tokens to generate in the completion.
     */
    @JsonProperty("max_tokens")
    @PositiveOrZero
    @Builder.Default
    @Nullable
    private Integer maxTokens = null;

    /**
     * Whether to stream back partial progress. When set to true, the {@link nl.dannyj.mistral.MistralClient#createChatCompletionStream(ChatCompletionRequest, ChatCompletionChunkCallback)} method has to be used.
     *
     * @param stream Whether to stream back partial progress. Setting to null will default to false.
     * @return Whether to stream back partial progress.
     */
    @Builder.Default
    @NotNull
    private Boolean stream = false;

    /**
     * Stop generation if this token is detected. Or if one of these tokens is detected when providing an array
     *
     * @param stop The stop sequence(s) to use. Use an empty List (default value) for no stop sequence.
     * @return The stop sequence(s) to use.
     */
    @NotNull
    @Builder.Default
    private List<String> stop = List.of();

    /**
     * The seed to use for random sampling. If set, different calls will generate deterministic results.
     *
     * @param randomSeed The seed to use for random sampling. Set to null for a random seed.
     * @return The seed to use for random sampling.
     */
    @JsonProperty("random_seed")
    @Nullable
    @Builder.Default
    private Long randomSeed = null;

    /**
     * The prompt(s) to generate completions for, encoded as a list of dict with role and content.
     * Must contain at least one message and the first prompt role should be user or system.
     *
     * @param messages The list of messages representing the conversation history. Must contain at least one message.
     * @return The list of messages.
     */
    @NotNull
    @Size(min = 1)
    private List<ChatMessage> messages;

    /**
     * An object specifying the format that the model must output. Setting to JSON_OBJECT enables JSON mode, which guarantees the message the model generates is in JSON. When using JSON mode you MUST also instruct the model to produce JSON yourself with a system or a user message.
     *
     * @param responseFormat The response format of the completion request. Currently only available when using mistral small and mistral large models. For other models, this MUST be set to null.
     * @return The response format of the completion request.
     */
    @JsonProperty("response_format")
    @NotNull
    @Builder.Default
    private ResponseFormat responseFormat = new ResponseFormat(ResponseFormats.TEXT);

    /**
     * Whether to inject a safety prompt before all conversations.
     * Toggling the safe prompt will prepend your messages with the following system prompt:
     * Always assist with care, respect, and truth. Respond with utmost utility yet securely. Avoid harmful, unethical, prejudiced, or negative content. Ensure replies promote fairness and positivity.
     *
     * @param safePrompt Whether to inject a safety prompt before all conversations.
     * @return Whether to inject a safety prompt before all conversations.
     */
    @JsonProperty("safe_prompt")
    @Builder.Default
    private boolean safePrompt = false;

    /**
     * A list of tools the model may call. Currently, only functions are supported as a tool.
     * Use this to provide a list of functions the model may generate JSON inputs for.
     * Set to null or an empty list if no tools should be available.
     *
     * @param tools The list of tools.
     * @return The list of tools.
     */
    @Nullable
    @JsonProperty("tools")
    @Builder.Default
    private List<Tool> tools = null;

    /**
     * Controls which function call(s) are made, if any.
     * 'none' means the model will not call a function and instead generates a message.
     * 'auto' means the model can pick between generating a message or calling a function.
     * 'any' forces the model to call a function.
     * Specifying a particular function via {@link SpecificToolChoice} forces the model to call that function.
     * Defaults to 'auto'.
     *
     * @param toolChoice The tool choice option (can be {@link ToolChoiceEnum} or {@link SpecificToolChoice}).
     * @return The tool choice option.
     */
    @JsonProperty("tool_choice")
    @Builder.Default
    private ToolChoiceOption toolChoice = ToolChoiceEnum.AUTO;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far,
     * increasing the model's likelihood to talk about new topics.
     * Defaults to 0.0.
     *
     * @param presencePenalty The presence penalty.
     * @return The presence penalty.
     */
    @Nullable
    @JsonProperty("presence_penalty")
    @DecimalMin("-2.0")
    @DecimalMax("2.0")
    @Builder.Default
    private Double presencePenalty = 0.0;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far,
     * decreasing the model's likelihood to repeat the same line verbatim.
     * Defaults to 0.0.
     *
     * @param frequencyPenalty The frequency penalty.
     * @return The frequency penalty.
     */
    @Nullable
    @JsonProperty("frequency_penalty")
    @DecimalMin("-2.0")
    @DecimalMax("2.0")
    @Builder.Default
    private Double frequencyPenalty = 0.0;

    /**
     * How many chat completion choices to generate for each input message.
     * Note that you will be charged based on the number of generated tokens across all of the choices.
     * Defaults to 1.
     *
     * @param n The number of choices to generate.
     * @return The number of choices.
     */
    @Nullable
    @JsonProperty("n")
    @Builder.Default
    private Integer n = null;

    /**
     * Provides predicted output to optimize response time.
     * See the <a href="https://docs.mistral.ai/capabilities/predicted-outputs/">Predicted Outputs guide</a> for more details.
     * API might have its own default object if null.
     *
     * @param prediction The prediction object.
     * @return The prediction object.
     */
    @Nullable
    @JsonProperty("prediction")
    @Builder.Default
    private Prediction prediction = null;

    /**
     * Whether to allow parallel function calls.
     * Defaults to true.
     *
     * @param parallelToolCalls Whether parallel tool calls are allowed.
     * @return True if parallel tool calls are allowed, false otherwise.
     */
    @JsonProperty("parallel_tool_calls")
    @Builder.Default
    private Boolean parallelToolCalls = true;

}
