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
 * Represents the reason why a chat completion generation finished.
 */
public enum FinishReason {

    /**
     * The model hit a natural stop point or a provided stop sequence.
     */
    STOP("stop"),

    /**
     * The maximum number of tokens specified in the request was reached.
     */
    LENGTH("length"),

    /**
     * The context length of the model was exceeded.
     */
    MODEL_LENGTH("model_length"),

    /**
     * The generation stopped due to an error.
     */
    ERROR("error"),

    /**
     * The model decided to call one or more tools.
     */
    TOOL_CALLS("tool_calls");

    private final String reason;

    FinishReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the string representation of the finish reason.
     *
     * @return The finish reason as a string.
     */
    @JsonValue
    public String getReason() {
        return reason;
    }
}
