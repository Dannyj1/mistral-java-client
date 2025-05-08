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
 * Defines the type of a tool.
 * Currently, only "function" is supported.
 */
public enum ToolType {

    /**
     * Represents a function tool.
     */
    FUNCTION("function");

    private final String type;

    ToolType(String type) {
        this.type = type;
    }

    /**
     * Gets the string representation of the tool type.
     * This value is used for JSON serialization.
     *
     * @return The tool type as a string.
     */
    @JsonValue
    public String getType() {
        return type;
    }
}
