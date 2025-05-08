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

package nl.dannyj.mistral.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.dannyj.mistral.models.completion.tool.SpecificToolChoice;
import nl.dannyj.mistral.models.completion.tool.ToolChoiceEnum;
import nl.dannyj.mistral.models.completion.tool.ToolChoiceOption;

import java.io.IOException;

/**
 * Custom deserializer for the ToolChoiceOption sealed interface.
 * It handles deserialization from either a JSON string (for ToolChoiceEnum)
 * or a JSON object (for SpecificToolChoice).
 */
public class ToolChoiceOptionDeserializer extends JsonDeserializer<ToolChoiceOption> {

    @Override
    public ToolChoiceOption deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonToken token = jp.currentToken();

        if (token == JsonToken.VALUE_STRING) {
            String enumValue = jp.getText().toUpperCase();

            return ToolChoiceEnum.valueOf(enumValue);
        } else if (token == JsonToken.START_OBJECT) {
            return mapper.readValue(jp, SpecificToolChoice.class);
        }

        return (ToolChoiceOption) ctxt.handleUnexpectedToken(ToolChoiceOption.class, jp);
    }
}
