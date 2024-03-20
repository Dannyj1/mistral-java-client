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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A choice of a streamed message chunk in a completion.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeltaChoice {

    /**
     * The index of the choice. Starts at 0.
     */
    private int index;

    /**
     * The message that was generated.
     */
    @JsonProperty("delta")
    private Message message;

    /**
     * Reason for the completion to finish.
     */
    @JsonProperty("finish_reason")
    private String finishReason;

    @JsonProperty("logprobs")
    private String logProbs;

}
