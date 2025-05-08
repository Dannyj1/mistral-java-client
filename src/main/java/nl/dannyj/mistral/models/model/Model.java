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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a model available in the Mistral AI API.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Model {

    /**
     * The ID of the model. Should be used to refer to the model in other API calls.
     *
     * @return The ID of the model.
     */
    private String id;

    /**
     * The object type, which is always "model".
     *
     * @return The object type.
     */
    private String object;

    /**
     * Creation time of the model in seconds since the Unix epoch.
     *
     * @return The creation time of the model in seconds since the Unix epoch.
     */
    private long created;

    /**
     * Owner of the model. Defaults to "mistralai".
     *
     * @return The owner of the model.
     */
    @JsonProperty("owned_by")
    private String ownedBy;

    /**
     * The capabilities of the model.
     *
     * @return The model capabilities.
     */
    private ModelCapabilities capabilities;

    /**
     * The name of the model. Can be null.
     *
     * @return The name of the model.
     */
    private String name;

    /**
     * The description of the model. Can be null.
     *
     * @return The description of the model.
     */
    private String description;

    /**
     * The maximum context length supported by the model. Defaults to 32768.
     *
     * @return The maximum context length.
     */
    @JsonProperty("max_context_length")
    private Integer maxContextLength;

    /**
     * A list of aliases for the model. Defaults to an empty list.
     *
     * @return The list of aliases.
     */
    private List<String> aliases;

    /**
     * The deprecation date and time for the model. Can be null.
     *
     * @return The deprecation date and time.
     */
    private OffsetDateTime deprecation;

    /**
     * The default sampling temperature for the model. Can be null.
     *
     * @return The default model temperature.
     */
    @JsonProperty("default_model_temperature")
    private Double defaultModelTemperature;

    /**
     * The type of the model, e.g., "base" or "fine-tuned".
     *
     * @return The type of the model.
     */
    private String type;

    /**
     * The ID of the fine-tuning job that created this model. Only present for fine-tuned models.
     *
     * @return The job ID.
     */
    private String job;

    /**
     * The root model ID from which this fine-tuned model was derived. Only present for fine-tuned models.
     *
     * @return The root model ID.
     */
    private String root;

    /**
     * Indicates if the fine-tuned model is archived. Defaults to false. Only present for fine-tuned models.
     *
     * @return True if the model is archived, false otherwise.
     */
    private Boolean archived;
}
