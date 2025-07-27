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

package nl.dannyj.mistral.models.completion.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the JSON schema definition used within ResponseFormat when type is json_schema.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JsonSchema {

    /**
     * The name of the schema.
     *
     * @param name The name of the schema.
     * @return The name of the schema.
     */
    @NotNull
    private String name;

    /**
     * An optional description of the schema.
     *
     * @param description The description of the schema.
     * @return The description of the schema.
     */
    private String description;

    /**
     * The JSON schema definition, represented as a String.
     *
     * @param schema The string representing the JSON schema.
     * @return The string representing the JSON schema.
     */
    @NotNull
    @JsonRawValue
    @JsonProperty("schema")
    private String schema;

    /**
     * Whether the schema should be strictly adhered to. Defaults to false.
     *
     * @param strict Whether the schema is strict.
     * @return Whether the schema is strict.
     */
    @Builder.Default
    private boolean strict = false;



}
