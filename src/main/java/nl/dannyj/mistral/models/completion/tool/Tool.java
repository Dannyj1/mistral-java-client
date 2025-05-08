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
 * Represents a tool that the model can call.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tool {

    /**
     * The type of the tool. Currently, only "function" is supported.
     *
     * @param type The type of the tool.
     * @return The type of the tool.
     */
    @NotNull
    @Builder.Default
    private ToolType type = ToolType.FUNCTION;

    /**
     * The function definition. Required if type is "function".
     *
     * @param function The function definition.
     * @return The function definition.
     */
    @NotNull
    private Function function;
}
