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

package nl.dannyj.mistral.models.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * The Model class represents a model in the Mistral AI API.
 * Most of these fields are undocumented.
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

    private String object;

    /**
     * Creation time of the model in seconds since the Unix epoch.
     *
     * @return The creation time of the model in seconds since the Unix epoch.
     */
    private long created;

    /**
     * Owner of the model.
     *
     * @return The owner of the model.
     */
    @JsonProperty("owned_by")
    private String ownedBy;

    private String root;

    private String parent;

    private List<ModelPermission> permission;
}
