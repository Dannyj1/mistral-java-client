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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the name of a function, used when specifying a particular function for tool_choice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionName {

    /**
     * The name of the function. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.
     *
     * @param name The name of the function.
     * @return The name of the function.
     */
    @NotNull
    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{1,64}$", message = "Function name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.")
    private String name;
}
