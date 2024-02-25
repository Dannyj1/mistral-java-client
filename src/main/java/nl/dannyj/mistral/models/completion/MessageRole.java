/*
 * Copyright 2024 Danny Jelsma
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
 * The role of the message.
 */
public enum MessageRole {

    SYSTEM("system"),
    ASSISTANT("assistant"),
    USER("user");

    private final String role;

    MessageRole(String role) {
        this.role = role;
    }

    /**
     * @return Lowercase string representation of the role. To be used when interacting with the API.
     */
    @JsonValue
    public String getRole() {
        return role;
    }
}
