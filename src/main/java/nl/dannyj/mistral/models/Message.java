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

package nl.dannyj.mistral.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * A message in a conversation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

}
