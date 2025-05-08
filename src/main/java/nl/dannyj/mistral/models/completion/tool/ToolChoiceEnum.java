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

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines the possible string values for the 'tool_choice' parameter in a ChatCompletionRequest.
 * It controls how the model responds to tool calls.
 */
public enum ToolChoiceEnum implements ToolChoiceOption {

    /**
     * The model can choose to call a tool or not. This is the default behavior.
     */
    AUTO("auto"),

    /**
     * The model will not call any tools.
     */
    NONE("none"),

    /**
     * The model is forced to call at least one tool.
     */
    ANY("any"),

    /**
     * The model is forced to call a specific tool (not directly represented by this enum,
     * but this value indicates a tool must be called, potentially specified by an object).
     * In the context of the enum, it implies a tool must be called.
     * The OpenAPI spec also lists "required" as a string option.
     */
    REQUIRED("required");

    private final String value;

    ToolChoiceEnum(String value) {
        this.value = value;
    }

    /**
     * Gets the string representation of the tool choice option.
     *
     * @return The tool choice option as a string.
     */
    @JsonValue
    public String getValue() {
        return value;
    }
}
