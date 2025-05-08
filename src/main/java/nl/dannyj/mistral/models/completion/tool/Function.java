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

import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the definition of a function that can be called by the model.
 * This is used when defining tools available to the model in a ChatCompletionRequest.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Function {

    /**
     * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.
     *
     * @param name The name of the function.
     * @return The name of the function.
     */
    @NotNull
    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{1,64}$", message = "Function name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.")
    private String name;

    /**
     * A description of what the function does, used by the model to choose when and how to call the function.
     *
     * @param description The description of the function.
     * @return The description of the function.
     */
    @Builder.Default
    private String description = "";

    /**
     * The parameters the functions accepts, described as a JSON Schema object.
     * See the <a href="https://json-schema.org/understanding-json-schema/">guide</a> for more information.
     * The user of this SDK should provide a valid JSON string representing the schema.
     *
     * @param parameters A JSON string representing the parameters schema.
     * @return A JSON string representing the parameters schema.
     */
    @NotNull
    @NotBlank
    @JsonRawValue
    private String parameters;

    /**
     * Whether to enable strict schema adherence for the function parameters.
     * When true, the model will make a best effort to adhere to the JSON schema.
     * See the <a href="https://docs.mistral.ai/capabilities/structured-output/structured_output_overview/">guide</a> for more details.
     * Defaults to false.
     *
     * @param strict Whether strict schema adherence is enabled.
     * @return True if strict schema adherence is enabled, false otherwise.
     */
    @Builder.Default
    private boolean strict = false;
}
