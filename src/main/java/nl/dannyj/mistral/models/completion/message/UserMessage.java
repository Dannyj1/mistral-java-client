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

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;

import java.util.Collections;
import java.util.List;

/**
 * Represents a message with the 'user' role in a chat conversation.
 * User messages contain the input provided by the end-user.
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserMessage extends ChatMessage {

    /**
     * Constructs a new UserMessage with simple text content.
     *
     * @param textContent The text content for the user message. Cannot be null or empty.
     */
    public UserMessage(@NotNull String textContent) {
        if (textContent.isEmpty()) {
            throw new IllegalArgumentException("User message text content cannot be empty.");
        }
        this.content = Collections.singletonList(new TextChunk(textContent));
    }

    /**
     * Constructs a new UserMessage with a list of content chunks (for multi-modal input).
     *
     * @param contentChunks The list of content chunks. Cannot be null or empty.
     */
    public UserMessage(@NotNull @NotEmpty List<ContentChunk> contentChunks) {
        this.content = contentChunks;
    }


    /**
     * Gets the role of this message.
     *
     * @return Always returns {@link MessageRole#USER}.
     */
    @Override
    public MessageRole getRole() {
        return MessageRole.USER;
    }
}
