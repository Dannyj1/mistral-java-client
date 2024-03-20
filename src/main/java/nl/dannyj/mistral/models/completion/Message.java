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

package nl.dannyj.mistral.models.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A message in a conversation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * The role of the message.
     * Currently, there are 3 roles: user, assistant, and system.
     */
    @NotNull
    private MessageRole role;

    /**
     * The content of the message.
     */
    @NotNull
    private String content;

    /**
     * Unimplemented. Don't use.
     */
    @JsonProperty("tool_calls")
    private List<String> toolCalls;

    public Message(MessageRole role, String content) {
        this.role = role;
        this.content = content;
    }
}
