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

package nl.dannyj.mistral.builders;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import nl.dannyj.mistral.models.completion.message.AssistantMessage;
import nl.dannyj.mistral.models.completion.message.ChatMessage;
import nl.dannyj.mistral.models.completion.message.SystemMessage;
import nl.dannyj.mistral.models.completion.message.ToolMessage;
import nl.dannyj.mistral.models.completion.message.UserMessage;
import nl.dannyj.mistral.models.completion.tool.ToolCall;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for creating a list of {@link ChatMessage} objects for a chat completion request.
 * Provides convenience methods for adding messages with different roles and content types.
 */
public class MessageListBuilder {

    private final List<ChatMessage> messages;

    /**
     * Default constructor that initializes an empty list of ChatMessage objects.
     */
    public MessageListBuilder() {
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor that initializes the list of ChatMessage objects with a provided list.
     *
     * @param messages The initial list of ChatMessage objects.
     */
    public MessageListBuilder(List<ChatMessage> messages) {
        this.messages = new ArrayList<>(messages);
    }

    /**
     * Adds a system message to the list with the provided content.
     *
     * @param content The text content of the system message. Cannot be null.
     * @return This builder instance.
     */
    public MessageListBuilder system(@NonNull String content) {
        this.messages.add(new SystemMessage(content));
        return this;
    }

    /**
     * Adds an assistant message with text content to the list.
     *
     * @param content The text content of the assistant message. Cannot be null.
     * @return This builder instance.
     */
    public MessageListBuilder assistant(@NonNull String content) {
        this.messages.add(new AssistantMessage(content));
        return this;
    }

    /**
     * Adds an assistant message with tool calls to the list.
     *
     * @param toolCalls The list of tool calls. Cannot be null or empty.
     * @return This builder instance.
     */
    public MessageListBuilder assistant(@NonNull @NotEmpty List<ToolCall> toolCalls) {
        this.messages.add(new AssistantMessage(toolCalls));
        return this;
    }

    /**
     * Adds a user message with text content to the list.
     *
     * @param content The text content of the user message. Cannot be null.
     * @return This builder instance.
     */
    public MessageListBuilder user(@NonNull String content) {
        this.messages.add(new UserMessage(content));
        return this;
    }

    /**
     * Adds a tool message with text content to the list.
     *
     * @param content    The text content of the tool call. Cannot be null.
     * @param toolCallId The ID of the tool call this message responds to. Can be null.
     * @return This builder instance.
     */
    public MessageListBuilder tool(@NonNull String content, @Nullable String toolCallId) {
        this.messages.add(new ToolMessage(content, toolCallId));
        return this;
    }


    /**
     * Adds a pre-constructed ChatMessage object to the list.
     * Useful for adding messages with complex content or specific configurations (e.g., AssistantMessage with both content and tool calls).
     *
     * @param message The ChatMessage object to be added. Cannot be null.
     * @return This builder instance.
     */
    public MessageListBuilder message(@NonNull ChatMessage message) {
        this.messages.add(message);
        return this;
    }

    /**
     * Returns the list of ChatMessage objects that have been added.
     *
     * @return The list of ChatMessage objects.
     */
    public List<ChatMessage> build() {
        return this.messages;
    }
}
