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

package nl.dannyj.mistral.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dannyj.mistral.models.Model;

import java.util.List;

/**
 * The ListModelsResponse contains a list of all available models according to the Mistral AI API.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListModelsResponse {

    private String object;

    /**
     * The list of available models.
     */
    @JsonProperty("data")
    private List<Model> models;

}
