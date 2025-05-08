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
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ContentChunkListDeserializer extends StdDeserializer<List<ContentChunk>> implements ContextualDeserializer {

    private JsonDeserializer<?> defaultDeserializer;

    public ContentChunkListDeserializer() {
        this(null);
    }

    public ContentChunkListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<ContentChunk> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentToken() == JsonToken.VALUE_STRING) {
            String textContent = p.getText();
            return Collections.singletonList(new TextChunk(textContent));
        }

        if (defaultDeserializer != null) {
            return (List<ContentChunk>) defaultDeserializer.deserialize(p, ctxt);
        }

        return ctxt.readValue(p, ctxt.constructType(List.class));
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType listType = ctxt.getContextualType();
        JsonDeserializer<?> deser = ctxt.findContextualValueDeserializer(listType, property);

        ContentChunkListDeserializer contextualDeserializer = new ContentChunkListDeserializer();
        contextualDeserializer.defaultDeserializer = deser;
        return contextualDeserializer;
    }
}
