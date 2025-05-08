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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the function call requested by the model, including the function name and its arguments as a JSON string.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionCall {

    /**
     * The name of the function to call. Cannot be blank.
     *
     * @param name The function name.
     * @return The function name.
     */
    @NotBlank
    private String name;

    /**
     * The arguments to call the function with, represented as a JSON string.
     *
     * @param arguments The function arguments as a JSON string.
     * @return The function arguments JSON string.
     */
    @NotNull
    private String arguments;
}
