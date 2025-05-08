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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents a part of the message content. The content of a message can be composed of multiple chunks of different types.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextChunk.class, name = "text"),
        @JsonSubTypes.Type(value = ImageURLChunk.class, name = "image_url"),
        @JsonSubTypes.Type(value = DocumentURLChunk.class, name = "document_url"),
        @JsonSubTypes.Type(value = ReferenceChunk.class, name = "reference")
})
public interface ContentChunk {

    /**
     * Gets the type identifier for this content chunk.
     *
     * @return The type string (e.g., "text", "image_url").
     */
    String getType();
}
