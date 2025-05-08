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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;
import nl.dannyj.mistral.models.completion.tool.ToolCall;
import nl.dannyj.mistral.serialization.ContentChunkListDeserializer;

import java.util.List;

/**
 * Represents the delta content within a streamed chat completion choice.
 * Contains partial updates for role, content, or tool calls.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeltaMessage {

    /**
     * The role of the message sender (e.g., assistant). Only sent if it changes or at the start.
     *
     * @param role The message role, or null if not present in this delta.
     * @return The message role, or null.
     */
    @Nullable
    private MessageRole role;

    /**
     * A part of the message content. Can be null if this delta updates role or tool calls.
     * Can be a string or a list of ContentChunks.
     *
     * @param content The partial content list or string, or null.
     * @return The partial content list or string, or null.
     */
    @Nullable
    @JsonDeserialize(using = ContentChunkListDeserializer.class)
    private List<ContentChunk> content;

    /**
     * A partial list of tool calls requested by the model. Can be null.
     *
     * @param toolCalls The partial list of tool calls, or null.
     * @return The partial list of tool calls, or null.
     */
    @Nullable
    @JsonProperty("tool_calls")
    private List<ToolCall> toolCalls;

    /**
     * Gets the text content of the delta message.
     * This is a convenience method that extracts the text from all TextChunks, ignoring other types of content.
     *
     * @return The concatenated text content of the message, or null if no text content is present.
     */
    @Nullable
    @JsonIgnore
    public String getTextContent() {
        if (content == null) {
            return null;
        }

        StringBuilder textContent = new StringBuilder();

        for (ContentChunk chunk : content) {
            if (chunk instanceof TextChunk textChunk && textChunk.getText() != null) {
                textContent.append(textChunk.getText());
            }
        }

        return !textContent.isEmpty() ? textContent.toString() : null;
    }
}
