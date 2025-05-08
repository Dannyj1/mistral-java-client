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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.completion.message.DeltaMessage;

/**
 * Represents a delta update within a choice during a streamed chat completion.
 * Contains partial information about the message delta.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeltaChoice {

    /**
     * The index of the choice. Starts at 0.
     *
     * @return the index of the choice
     */
    private int index;

    /**
     * The delta containing partial message updates.
     *
     * @param delta The delta message object.
     * @return The delta message object.
     */
    private DeltaMessage delta;

    /**
     * Reason for the completion to finish.
     * Can be null if the stream is not finished.
     *
     * @return the reason for the completion to finish, or null.
     */
    @Nullable
    @JsonProperty("finish_reason")
    private FinishReason finishReason;

    /**
     * Gets the text content of the delta.
     * This is a convenience method that extracts the text from all TextChunks, ignoring other types of content.
     *
     * @return The concatenated text content of the message from the delta, or null if the delta is null.
     */
    @Nullable
    @JsonIgnore
    public String getTextContent() {
        return delta != null ? delta.getTextContent() : null;
    }

}
