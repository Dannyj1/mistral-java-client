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

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tool call requested by the model as part of an Assistant message.
 * Based on the 'ToolCall' definition in the Mistral AI API specification.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolCall {

    /**
     * The unique identifier for this specific tool call.
     * This ID is required when sending a response back using a ToolMessage.
     *
     * @param id The tool call ID.
     * @return The tool call ID, or null if not provided by the API.
     */
    @Nullable
    private String id;

    /**
     * The type of the tool being called. Currently only 'function' is supported.
     * Defaults to FUNCTION if not specified.
     *
     * @param type The tool type.
     * @return The tool type.
     */
    @NotNull
    private ToolType type = ToolType.FUNCTION;

    /**
     * The details of the function to be called. Required.
     *
     * @param function The function call details.
     * @return The function call details.
     */
    @NotNull
    private FunctionCall function;

    /**
     * The index of the tool call in the list, if multiple calls are made.
     * Defaults to 0 if not specified by the API.
     *
     * @param index The index of the tool call.
     * @return The index.
     */
    @Min(0)
    private int index = 0;
}
