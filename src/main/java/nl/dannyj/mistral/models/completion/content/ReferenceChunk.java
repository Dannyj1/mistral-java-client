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

package nl.dannyj.mistral.models.completion.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a reference part of the message content, linking to specific source materials or documents.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceChunk implements ContentChunk {

    /**
     * A list of integer identifiers referencing related documents or sources.
     *
     * @param referenceIds The list of reference IDs.
     * @return The list of reference IDs.
     */
    @NotNull
    @NotEmpty
    @JsonProperty("reference_ids")
    @Getter
    private List<Integer> referenceIds;

    /**
     * Gets the type identifier for this content chunk.
     *
     * @return The type string "reference".
     */
    @Override
    @JsonIgnore
    public String getType() {
        return "reference";
    }
}
