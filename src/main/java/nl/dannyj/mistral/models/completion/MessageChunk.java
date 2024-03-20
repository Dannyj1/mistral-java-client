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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.usage.Usage;

import java.util.List;

/**
 * A chunk of a message in a conversation.
 * Returned when using streaming chat completions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageChunk {

    /**
     * The ID of the message chunk.
     */
    private String id;

    /**
     * Always chat.completion.chunk
     */
    private String object;

    /**
     * The time the message chunk was created.
     */
    private long created;

    /**
     * The model used to generate the completions.
     */
    private String model;

    /**
     * The generated completions.
     */
    private List<DeltaChoice> choices;

    /**
     * The tokens used to generate the completion.
     */
    private Usage usage;
}
