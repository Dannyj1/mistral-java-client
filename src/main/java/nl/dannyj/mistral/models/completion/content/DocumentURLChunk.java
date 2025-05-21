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
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;

/**
 * Represents a document URL part of the message content.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentURLChunk implements ContentChunk {

    /**
     * The URI of the document.
     *
     * @param documentUrl The document URI.
     * @return The document URI.
     */
    @NotNull
    @JsonProperty("document_url")
    @Getter
    private URI documentUrl;

    /**
     * The optional filename of the document.
     *
     * @param documentName The filename.
     * @return The filename, or null if not specified.
     */
    @Nullable
    @JsonProperty("document_name")
    @Getter
    private String documentName;

    /**
     * Gets the type identifier for this content chunk.
     *
     * @return The type string "document_url".
     */
    @Override
    @JsonIgnore
    public String getType() {
        return "document_url";
    }
}
