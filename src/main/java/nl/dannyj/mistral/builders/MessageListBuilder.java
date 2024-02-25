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

package nl.dannyj.mistral.builders;

import nl.dannyj.mistral.models.completion.Message;
import nl.dannyj.mistral.models.completion.MessageRole;

import java.util.ArrayList;
import java.util.List;

/**
 * The MessageListBuilder class is a builder class for creating a list of Message objects.
 * It provides methods to add messages of different roles (system, assistant, user) to the list.
 * The build method returns the list of Message objects that have been added.
 */
public class MessageListBuilder {

    private final List<Message> messages;

    /**
     * Default constructor that initializes an empty list of Message objects.
     */
    public MessageListBuilder() {
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor that initializes the list of Message objects with a provided list.
     *
     * @param messages The initial list of Message objects
     */
    public MessageListBuilder(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Adds a message with the system role to the list with the provided content.
     *
     * @param content The content of the system message
     * @return The builder instance
     */
    public MessageListBuilder system(String content) {
        this.messages.add(new Message(MessageRole.SYSTEM, content));
        return this;
    }

    /**
     * Adds a message with the assistant role to the list with the provided content.
     *
     * @param content The content of the assistant message
     * @return The builder instance
     */
    public MessageListBuilder assistant(String content) {
        this.messages.add(new Message(MessageRole.ASSISTANT, content));
        return this;
    }

    /**
     * Adds a message with the user role to the list with the provided content.
     *
     * @param content The content of the user message
     * @return The builder instance
     */
    public MessageListBuilder user(String content) {
        this.messages.add(new Message(MessageRole.USER, content));
        return this;
    }

    /**
     * Adds a custom Message object to the list.
     *
     * @param message The Message object to be added
     * @return The builder instance
     */
    public MessageListBuilder message(Message message) {
        this.messages.add(message);
        return this;
    }

    /**
     * Returns the list of Message objects that have been added.
     *
     * @return The list of Message objects
     */
    public List<Message> build() {
        return this.messages;
    }
}
