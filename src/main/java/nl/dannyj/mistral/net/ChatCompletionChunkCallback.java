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

package nl.dannyj.mistral.net;

import nl.dannyj.mistral.models.completion.MessageChunk;

/**
 * Interface for handling streaming chat completion requests.
 */
public interface ChatCompletionChunkCallback {
    /**
     * Called when a new message chunk of the response is received.
     *
     * @param chunk The received chunk
     */
    void onChunkReceived(MessageChunk chunk);

    /**
     * Called when the entire response is received.
     */
    void onComplete();

    /**
     * Called when an error occurs during the streaming request.
     *
     * @param e The exception representing the error
     */
    void onError(Exception e);
}
