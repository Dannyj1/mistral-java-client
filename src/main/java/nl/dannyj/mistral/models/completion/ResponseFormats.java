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

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The response formats supported by mistral.
 */
public enum ResponseFormats {

    TEXT("text"),
    JSON_OBJECT("json_object"),
    JSON_SCHEMA("json_schema");

    private final String format;

    ResponseFormats(String format) {
        this.format = format;
    }

    /**
     * Returns a lowercase string representation of the format. To be used when interacting with the API.
     *
     * @return Lowercase string representation of the format.
     */
    @JsonValue
    public String getFormat() {
        return format;
    }
}
