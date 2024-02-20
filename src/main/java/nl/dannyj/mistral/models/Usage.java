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

package nl.dannyj.mistral.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usage {

    /**
     * The number of tokens used for the prompt ("input tokens").
     */
    @JsonProperty("prompt_tokens")
    private int promptTokens;

    /**
     * The number of tokens used for the completion ("output tokens").
     */
    @JsonProperty("completion_tokens")
    private int completionTokens;

    /**
     * The total number of tokens used (prompt tokens + completion tokens).
     */
    @JsonProperty("total_tokens")
    private int totalTokens;

}
