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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an image URL part of the message content.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ImageURLChunk implements ContentChunk {

    /**
     * The image URL details.
     *
     * @param imageUrl The ImageURL object containing the URI and optional detail.
     * @return The ImageURL object.
     */
    @NotNull
    @JsonProperty("image_url")
    @Getter
    private ImageURL imageUrl;

    /**
     * Gets the type identifier for this content chunk.
     *
     * @return The type string "image_url".
     */
    @Override
    @JsonIgnore
    public String getType() {
        return "image_url";
    }
}
