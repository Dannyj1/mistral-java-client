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

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.dannyj.mistral.models.completion.tool.JsonSchema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * The response format of a completion request.
 */
public class ResponseFormat {

    /**
     * The type of the response format. Currently, can either be TEXT or JSON.
     *
     * @param type The type of the response format.
     * @return The type of the response format.
     */
    @NotNull
    private ResponseFormats type = ResponseFormats.TEXT;

    /**
     * The JSON schema definition to use when type is JSON_SCHEMA.
     *
     * @param jsonSchema The JSON schema definition.
     * @return The JSON schema definition.
     */
    @JsonProperty("json_schema")
    @Nullable
    private JsonSchema jsonSchema = null;

    /**
     * Constructor for creating a ResponseFormat object with a specified type.
     *
     * @param type The type of the response format.
     */
    public ResponseFormat(ResponseFormats type) {
        this.type = type;
    }
}
