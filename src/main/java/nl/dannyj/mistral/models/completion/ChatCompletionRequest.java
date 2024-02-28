package nl.dannyj.mistral.models.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.Request;

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
     * ID of the model to use. You can use the List Available Models API to see all of your available models.
     */
    @NotNull
    @NotBlank
    private String model;

    /**
     * The prompt(s) to generate completions for, encoded as a list of dict with role and content.
     * Must contain at least one message and the first prompt role should be user or system.
     */
    @NotNull
    @Size(min = 1)
    private List<Message> messages;

    /**
     * What sampling temperature to use, between 0.0 and 1.0. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     * We generally recommend altering this or top_p but not both.
     * Defaults to 0.7.
     */
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Builder.Default
    private Double temperature = 0.7;

    /**
     * Nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * We generally recommend altering this or temperature but not both.
     * Defaults to 1.0 (i.e., no nucleus sampling).
     */
    @JsonProperty("top_p")
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Builder.Default
    private Double topP = 1.0;

    /**
     * The maximum number of tokens to generate in the completion.
     * The token count of your prompt plus max_tokens cannot exceed the model's context length.
     * Defaults to 32000, which is the maximum value for all currently available models.
     */
    @JsonProperty("max_tokens")
    @Builder.Default
    @PositiveOrZero
    private Integer maxTokens = 32000;

    /**
     * Currently not implemented, setting this to true will cause issues.
     */
    @Builder.Default
    @AssertFalse
    private Boolean stream = false;

    /**
     * Whether to inject a safety prompt before all conversations.
     * Toggling the safe prompt will prepend your messages with the following system prompt:
     * Always assist with care, respect, and truth. Respond with utmost utility yet securely. Avoid harmful, unethical, prejudiced, or negative content. Ensure replies promote fairness and positivity.
     */
    @JsonProperty("safe_prompt")
    @Builder.Default
    private boolean safePrompt = false;

    /**
     * The seed to use for random sampling. If set, different calls will generate deterministic results.
     */
    @JsonProperty("random_seed")
    @Builder.Default
    private Long randomSeed = null;

    /**
     * The response format of the completion request. Defaults to "text".
     * Currently only usable when using mistral small and mistral large models. For other models, this MUST be set to null. Otherwise, you may get a 422 Unprocessable Content error.
     */
    @JsonProperty("response_format")
    private ResponseFormat responseFormat = null;

    public static class ChatCompletionRequestBuilder {
        public ChatCompletionRequestBuilder responseFormat(ResponseFormats responseFormat) {
            this.responseFormat = new ResponseFormat(responseFormat);
            return this;
        }
    }
}
