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

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the object variant for the 'tool_choice' parameter in a ChatCompletionRequest.
 * This forces the model to call a specific function.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpecificToolChoice implements ToolChoiceOption {

    /**
     * The type of the tool to be called. Currently, only "function" is supported.
     *
     * @param type The type of the tool.
     * @return The type of the tool.
     */
    @NotNull
    @Builder.Default
    private ToolType type = ToolType.FUNCTION;

    /**
     * The details of the function to be called.
     *
     * @param function The function name.
     * @return The function name.
     */
    @NotNull
    private FunctionName function;
}
