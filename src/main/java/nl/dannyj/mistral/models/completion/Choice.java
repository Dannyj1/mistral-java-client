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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.completion.message.AssistantMessage;

/**
 * Represents a choice in a chat completion response. A choice contains the generated assistant message and the reason the generation finished.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Choice {

    /**
     * The index of the choice. Starts at 0.
     *
     * @return the index of the choice
     */
    private int index;

    /**
     * The message that was generated.
     *
     * @return the assistant message that was generated
     */
    private AssistantMessage message;

    /**
     * Reason for the completion to finish.
     *
     * @return the reason for the completion to finish
     */
    @JsonProperty("finish_reason")
    private FinishReason finishReason;

}
