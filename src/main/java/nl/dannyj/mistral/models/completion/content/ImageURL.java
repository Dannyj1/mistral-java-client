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

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

/**
 * Represents the URL and optional detail level for an image within a message content chunk.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageURL {

    /**
     * The URI of the image. Can be a standard web URI (http/https) or a data URI (e.g., data:image/png;base64,...).
     *
     * @param url The image URI.
     * @return The image URI.
     */
    @NotNull
    private URI url;

    /**
     * Specifies the detail level of the image. Valid values have not been documented, recommended to leave at null.
     *
     * @param detail The detail level string.
     * @return The detail level string, or null if not specified.
     */
    @Nullable
    private String detail;
}
