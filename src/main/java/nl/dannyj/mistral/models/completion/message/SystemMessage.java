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

import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.completion.content.TextChunk;

import java.util.ArrayList;

/**
 * Represents a message with the 'system' role in a chat conversation.
 * System messages are used to provide high-level instructions or context to the model.
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SystemMessage extends ChatMessage {

    public SystemMessage(@Nullable String content) {
        this.content = new ArrayList<>();

        this.content.add(new TextChunk(content));
    }

    /**
     * Gets the role of this message.
     *
     * @return Always returns {@link MessageRole#SYSTEM}.
     */
    @Override
    public MessageRole getRole() {
        return MessageRole.SYSTEM;
    }
}
