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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;

import java.util.Collections;
import java.util.List;

/**
 * Represents a message with the 'tool' role in a chat conversation.
 * Tool messages are used to provide the results of a tool call back to the model.
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolMessage extends ChatMessage {

    /**
     * The ID of the tool call that this message is a response to. Can be null.
     *
     * @param toolCallId The tool call ID.
     * @return The tool call ID.
     */
    @Nullable
    @JsonProperty("tool_call_id")
    @Getter
    @Setter
    private String toolCallId;

    /**
     * The name associated with the tool call. Can be null.
     *
     * @param name The name.
     * @return The name.
     */
    @Nullable
    @Getter
    @Setter
    private String name;

    /**
     * Constructs a ToolMessage with simple text content and the corresponding tool call ID.
     *
     * @param textContent The text content (result) of the tool call. Cannot be null.
     * @param toolCallId  The ID of the tool call this message responds to. Can be null.
     */
    public ToolMessage(@NotNull String textContent, @Nullable String toolCallId) {
        this.content = Collections.singletonList(new TextChunk(textContent));
        this.toolCallId = toolCallId;
    }

    /**
     * Constructs a ToolMessage with potentially complex content and the corresponding tool call ID.
     *
     * @param contentChunks The list of content chunks representing the tool result. Cannot be null or empty.
     * @param toolCallId    The ID of the tool call this message responds to. Can be null.
     */
    public ToolMessage(@NotNull @jakarta.validation.constraints.NotEmpty List<ContentChunk> contentChunks, @Nullable String toolCallId) {
        this.content = contentChunks;
        this.toolCallId = toolCallId;
    }


    /**
     * Gets the role of this message.
     *
     * @return Always returns {@link MessageRole#TOOL}.
     */
    @Override
    public MessageRole getRole() {
        return MessageRole.TOOL;
    }
}
