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

package nl.dannyj.mistral.models.completion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.Response;
import nl.dannyj.mistral.models.usage.Usage;

import java.util.List;

/**
 * The ChatCompletionResponse class represents a response from the Mistral API when creating a chat completion.
 * Most of these fields are undocumented.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatCompletionResponse implements Response {

    /**
     * Unique identifier for this response.
     *
     * @return the id of the response.
     */
    private String id;

    /**
     * Undocumented, seems to be the type of the response.
     */
    private String object;

    /**
     * The time the chat completion was created in seconds since the epoch.
     *
     * @return the time the chat completion was created.
     */
    private long created;

    /**
     * The model used to generate the completion.
     *
     * @return the model used to generate the completion.
     */
    private String model;

    /**
     * The generated completions.
     *
     * @return the generated completions.
     */
    private List<Choice> choices;

    /**
     * The tokens used to generate the completion.
     *
     * @return the amount of tokens used to generate the completion.
     */
    private Usage usage;

}
