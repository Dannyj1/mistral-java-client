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

package nl.dannyj.mistral.models.completion.tool;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.dannyj.mistral.serialization.ToolChoiceOptionDeserializer;

/**
 * Represents the possible options for the 'tool_choice' parameter in a ChatCompletionRequest.
 * This can be either a predefined string enum (ToolChoiceEnum) or an object specifying a function (SpecificToolChoice).
 */
@JsonDeserialize(using = ToolChoiceOptionDeserializer.class)
public sealed interface ToolChoiceOption permits ToolChoiceEnum, SpecificToolChoice {
}
