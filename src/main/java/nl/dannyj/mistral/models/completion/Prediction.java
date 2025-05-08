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

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the predicted output to optimize response time for ChatCompletionRequest.
 * See the <a href="https://docs.mistral.ai/capabilities/predicted-outputs/">Predicted Outputs guide</a> for more details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {

    /**
     * The type of the prediction. Currently, only "content" is supported.
     *
     * @param type The type of the prediction.
     * @return The type of the prediction.
     */
    @NotNull
    @Builder.Default
    private String type = "content";

    /**
     * The predicted content.
     *
     * @param content The predicted content string.
     * @return The predicted content string.
     */
    @NotNull
    @Builder.Default
    private String content = "";
}
