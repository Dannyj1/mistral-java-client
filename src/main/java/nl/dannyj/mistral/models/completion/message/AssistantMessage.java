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

package nl.dannyj.mistral.models.completion.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;
import nl.dannyj.mistral.models.completion.tool.ToolCall;

import java.util.Collections;
import java.util.List;

/**
 * Represents a message with the 'assistant' role in a chat conversation.
 * Assistant messages contain the model's responses, which might include text content and/or tool calls.
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssistantMessage extends ChatMessage {

    /**
     * A list of tool calls requested by the model. Can be null if the message only contains text content.
     *
     * @param toolCalls The list of tool calls, or null.
     * @return The list of tool calls, or null.
     */
    @Nullable
    @JsonProperty("tool_calls")
    @Getter
    @Setter
    private List<ToolCall> toolCalls;

    /**
     * Indicates if this assistant message acts as a prefix to guide the model's subsequent response.
     * Defaults to false.
     *
     * @param prefix True if this is a prefix message, false otherwise.
     * @return The prefix flag state.
     */
    @Getter
    @Setter
    private boolean prefix = false;

    /**
     * Constructs an AssistantMessage with text content.
     *
     * @param textContent The text content.
     */
    public AssistantMessage(@NonNull String textContent) {
        this.content = Collections.singletonList(new TextChunk(textContent));
        this.toolCalls = null;
    }

    /**
     * Constructs an AssistantMessage with tool calls.
     *
     * @param toolCalls The list of tool calls. Cannot be null or empty.
     */
    public AssistantMessage(@NonNull @NotEmpty List<ToolCall> toolCalls) {
        this.content = null;
        this.toolCalls = toolCalls;
    }

    /**
     * Constructs an AssistantMessage with both text content and tool calls.
     * Use setters if only one is needed or to set the prefix flag.
     *
     * @param content   The list of content chunks. Can be null.
     * @param toolCalls The list of tool calls. Can be null.
     */
    public AssistantMessage(@Nullable List<ContentChunk> content, @Nullable List<ToolCall> toolCalls) {
        this.content = content;
        this.toolCalls = toolCalls;
    }

    /**
     * Gets the role of this message.
     *
     * @return Always returns {@link MessageRole#ASSISTANT}.
     */
    @Override
    public MessageRole getRole() {
        return MessageRole.ASSISTANT;
    }

}
