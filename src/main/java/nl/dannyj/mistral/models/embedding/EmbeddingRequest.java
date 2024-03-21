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

package nl.dannyj.mistral.models.embedding;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dannyj.mistral.models.Request;

import java.util.List;

/**
 * The EmbeddingRequest class represents a request to create embedding for a list of strings.
 * Most of the field descriptions are taken from the Mistral API documentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmbeddingRequest implements Request {

    /**
     * The ID of the model to use for this request.
     *
     * @param model The model ID.
     * @return The model ID.
     */
    @NotNull
    @NotBlank
    private String model;

    /**
     * The list of strings to embed.
     *
     * @param input The list of strings to embed. Each entry will be embedded separately.
     * @return The list of strings to embed.
     */
    @NotNull
    @Size(min = 1)
    private List<String> input;

    /**
     * The format of the output data.
     *
     * @param encodingFormat The format of the output data. Can only be "float" is valid for now.
     * @return The format of the output data.
     */
    @JsonProperty("encoding_format")
    @Builder.Default
    @NotNull
    @NotBlank
    private String encodingFormat = "float";

}
