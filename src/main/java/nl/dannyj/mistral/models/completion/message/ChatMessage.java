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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;
import nl.dannyj.mistral.serialization.ContentChunkListDeserializer;

import java.util.List;

/**
 * Represents a single message in a chat conversation, serving as the base for different message types (system, user, assistant, tool).
 * This class uses Jackson polymorphism based on the 'role' property found in the JSON.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "role"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SystemMessage.class, name = "system"),
        @JsonSubTypes.Type(value = UserMessage.class, name = "user"),
        @JsonSubTypes.Type(value = AssistantMessage.class, name = "assistant"),
        @JsonSubTypes.Type(value = ToolMessage.class, name = "tool")
})
public abstract class ChatMessage {

    /**
     * The content of the message. Can be null or a list of content chunks.
     *
     * @return The list of content chunks, or null.
     */
    @Nullable
    @JsonDeserialize(using = ContentChunkListDeserializer.class)
    protected List<ContentChunk> content;

    /**
     * Gets the role of the message sender (e.g., system, user, assistant).
     * Subclasses must implement this to provide their specific role.
     *
     * @return The role of the message.
     */
    public abstract MessageRole getRole();

    /**
     * Gets the text content of the message.
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
            if (chunk instanceof TextChunk textChunk) {
                textContent.append(textChunk.getText());
            }
        }

        return textContent.toString();
    }

}
