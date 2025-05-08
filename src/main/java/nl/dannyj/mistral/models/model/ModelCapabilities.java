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

package nl.dannyj.mistral.models.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the capabilities of a Mistral AI model.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModelCapabilities {

    /**
     * Indicates if the model supports chat completions.
     *
     * @return True if chat completion is supported, false otherwise.
     */
    @JsonProperty("completion_chat")
    private Boolean completionChat;

    /**
     * Indicates if the model supports fill-in-the-middle completions.
     *
     * @return True if FIM completion is supported, false otherwise.
     */
    @JsonProperty("completion_fim")
    private Boolean completionFim;

    /**
     * Indicates if the model supports function calling.
     *
     * @return True if function calling is supported, false otherwise.
     */
    @JsonProperty("function_calling")
    private Boolean functionCalling;

    /**
     * Indicates if the model supports fine-tuning.
     *
     * @return True if fine-tuning is supported, false otherwise.
     */
    @JsonProperty("fine_tuning")
    private Boolean fineTuning;

    /**
     * Indicates if the model has vision capabilities.
     *
     * @return True if vision capabilities are supported, false otherwise.
     */
    @JsonProperty("vision")
    private Boolean vision;

    /**
     * Indicates if the model has classification capabilities (relevant for fine-tuned models).
     *
     * @return True if classification capabilities are supported, false otherwise.
     */
    @JsonProperty("classification")
    private Boolean classification;

    public boolean supportsChatCompletion() {
        return completionChat;
    }

    public boolean supportsFimCompletion() {
        return completionFim;
    }

    public boolean supportsFunctionCalling() {
        return functionCalling;
    }

    public boolean supportsFineTuning() {
        return fineTuning;
    }

    public boolean supportsVision() {
        return vision;
    }

    public boolean supportsClassification() {
        return classification;
    }
}
