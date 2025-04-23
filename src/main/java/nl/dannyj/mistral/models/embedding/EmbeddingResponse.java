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

package nl.dannyj.mistral.models.embedding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.Response;
import nl.dannyj.mistral.models.usage.Usage;

import java.util.List;

/**
 * The EmbeddingResponse class represents a response from the Mistral API when creating embeddings.
 * Most of these fields are undocumented.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmbeddingResponse implements Response {

    /**
     * Unique identifier for this response.
     *
     * @return the id of the response
     */
    private String id;

    /**
     * Undocumented, seems to be the type of the response.
     *
     * @return the object type
     */
    private String object;

    /**
     * The embeddings that were created for the list of input strings.
     *
     * @return a list of float embeddings
     */
    private List<FloatEmbedding> data;

    /**
     * The model used to create the embeddings.
     *
     * @return the ID of the model used to generate the embeddings
     */
    private String model;

    /**
     * The token usage of the request.
     *
     * @return the usage of the request
     */
    private Usage usage;

}
