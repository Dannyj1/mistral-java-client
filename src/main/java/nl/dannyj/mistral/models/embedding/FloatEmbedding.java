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

package nl.dannyj.mistral.models.embedding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FloatEmbedding {

    /**
     * Undocumented, seems to be the type of the response.
     */
    private String object;

    /**
     * The embeddings for the input strings. See the <a href="https://docs.mistral.ai/guides/embeddings/">mistral documentation</a> for more details on embeddings.
     */
    private List<Float> embedding;

    /**
     * The index of the input string in the input list.
     */
    private int index;

}
