# Migration Guide: Updating Message Handling

This guide outlines the necessary changes to migrate your code from the previous message handling structure (using the
`nl.dannyj.mistral.models.completion.Message` class) to the new structure introduced to support multi-modal content and
tool calls, based on the abstract `nl.dannyj.mistral.models.completion.ChatMessage` class.

## Core Changes

1. **`Message` Class Removed:** The old `nl.dannyj.mistral.models.completion.Message` class has been removed.
2. **`ChatMessage` Hierarchy:** A new abstract base class `nl.dannyj.mistral.models.completion.ChatMessage` has been
   introduced. Specific message types now extend this class:
    * `nl.dannyj.mistral.models.completion.message.SystemMessage`
    * `nl.dannyj.mistral.models.completion.message.UserMessage`
    * `nl.dannyj.mistral.models.completion.message.AssistantMessage`
    * `nl.dannyj.mistral.models.completion.message.ToolMessage`
3. **Multi-Modal Content:** Messages (primarily `UserMessage`, `AssistantMessage`, `ToolMessage`) now use a
   `List<ContentChunk>` for their `content` field to support text, images, etc. The
   `nl.dannyj.mistral.models.completion.content.ContentChunk` interface has implementations like `TextChunk`,
   `ImageURLChunk`, etc.
    * Convenience constructors/methods are provided for creating messages with simple text content.
4. **Tool Calls:** `AssistantMessage` now includes a `List<ToolCall> toolCalls` field, and `ToolMessage` includes
   `toolCallId` and `name` fields to support function calling/tools.
5. **Streaming Deltas:** The `DeltaChoice` class (used in streaming responses via `MessageChunk`) now contains a
   `nl.dannyj.mistral.models.completion.message.DeltaMessage` object (accessible via `getDelta()`). This `DeltaMessage`
   object, in turn, holds the partial delta fields: `role`, `content` (as `List<ContentChunk>`), and `toolCalls`.

## Migration Steps

### 1. Update `ChatCompletionRequest` Message List

The `messages` field in `ChatCompletionRequest` now expects `List<ChatMessage>` instead of `List<Message>`.

**Before:**

```java
import nl.dannyj.mistral.models.completion.Message;

// ...
List<Message> messages = ...;
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(messages)
                // ...
                .build();
```

**After:**

```java
import nl.dannyj.mistral.models.completion.ChatMessage;

// ...
List<ChatMessage> messages = ...; // See MessageListBuilder changes below
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(messages)
                // ...
                .build();
```

### 2. Update `MessageListBuilder` Usage

The `MessageListBuilder` now works with `ChatMessage` and its subclasses.

**Before:**

```java
import nl.dannyj.mistral.models.completion.Message;
import nl.dannyj.mistral.builders.MessageListBuilder;

// ...
List<Message> messages = new MessageListBuilder()
        .system("System prompt")
        .user("User query")
        .assistant("Assistant response")
        .build();
```

**After:**

```java
import nl.dannyj.mistral.models.completion.ChatMessage;
import nl.dannyj.mistral.builders.MessageListBuilder;

// ...
List<ChatMessage> messages = new MessageListBuilder()
        .system("System prompt") // Creates SystemMessage
        .user("User query")     // Creates UserMessage
        .assistant("Assistant response") // Creates AssistantMessage (text only)
        // .assistant(listOfToolCalls) // Use this overload for tool calls
        // .tool("Tool result", "tool_call_id_123") // Creates ToolMessage
        .build();

// Alternatively, add pre-constructed messages:
// messages = new MessageListBuilder()
//     .message(new SystemMessage("System prompt"))
//     .message(new UserMessage("User query"))
//     .message(AssistantMessage.fromText("Assistant response")) // Factory method
//     .build();
```

### 3. Update Handling of Non-Streaming Responses (`ChatCompletionResponse`)

The `message` field within the `Choice` object (obtained from `ChatCompletionResponse.getChoices()`) is now specifically
an `AssistantMessage`.

**Before:**

```java
ChatCompletionResponse response = client.createChatCompletion(request);
Message responseMsg = response.getChoices().get(0).getMessage();
String content = responseMsg.getContent();
System.out.

println(content);
```

**After:**

```java
import nl.dannyj.mistral.models.completion.message.AssistantMessage;
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;

// ...
ChatCompletionResponse response = client.createChatCompletion(request);
        AssistantMessage responseMsg = response.getChoices().get(0).getMessage();

        // Extract text content (handle potential multi-modal content)
        String textContent = "";
if(responseMsg.

        getContent() !=null){
        for(
        ContentChunk chunk :responseMsg.

        getContent()){
        if(chunk instanceof
        TextChunk textChunk){
        textContent +=textChunk.

        getText(); // Append text from TextChunks
        }
                // Handle other chunk types (ImageURLChunk, etc.) if necessary
                }
                }
                System.out.

        println(textContent);

// Check for tool calls if applicable
if(responseMsg.

        getToolCalls() !=null){
        // Process tool calls
        }
```

### 4. Update Handling of Streaming Responses (`ChatCompletionChunkCallback`)

The `onChunkReceived` method receives `MessageChunk`. The structure within `MessageChunk.getChoices().get(0)` (which is
a `DeltaChoice`) has changed. Access `role`, `content`, and `toolCalls` directly from the `DeltaChoice` object.

**Before:**

```java
client.createChatCompletionStream(request, new ChatCompletionChunkCallback() {
    @Override
    public void onChunkReceived (MessageChunk chunk){
        // Old structure assumed delta was nested in a Message object
        String contentDelta = chunk.getChoices().get(0).getMessage().getContent();
        if (contentDelta != null) {
            System.out.print(contentDelta);
        }
    }
    // ... onComplete, onError
});
```

**After:**

```java
import nl.dannyj.mistral.models.completion.DeltaChoice;
import nl.dannyj.mistral.models.completion.message.DeltaMessage; // <-- Add this import
import nl.dannyj.mistral.models.completion.message.MessageChunk; // <-- Updated import path
import nl.dannyj.mistral.models.completion.content.ContentChunk;
import nl.dannyj.mistral.models.completion.content.TextChunk;
// ...
client.createChatCompletionStream(request, new ChatCompletionChunkCallback() {
    @Override
    public void onChunkReceived (MessageChunk chunk){
        if (chunk.getChoices() == null || chunk.getChoices().isEmpty()) return;

        DeltaChoice deltaChoice = chunk.getChoices().get(0);
        DeltaMessage delta = deltaChoice.getDelta(); // <-- Get the DeltaMessage

        // Check for text content delta using the convenience method
        String textDelta = delta.getTextContent();

        if (textDelta != null) {
            System.out.print(textDelta);
        }

    }
    // ... onComplete, onError
});

        ### 5.
Update Handling
of `Choice`
Object in `ChatCompletionResponse`

The `nl.dannyj.mistral.models.completion.Choice`class,
which is
part of
the `ChatCompletionResponse.

getChoices()`,
has the
following breaking
changes:

        ***`finishReason`
Type Change:**
        *The `finishReason`
field is
now of
type `nl.dannyj.mistral.models.completion.FinishReason` (an enum)
instead of
a raw `String`.
        ***Before:** `
String finishReason = choice.getFinishReason();`
        ***After:** `
FinishReason finishReasonEnum = choice.getFinishReason();`
        *
To get
the underlying

string value(e.g ., "stop","length"),you can call `finishReasonEnum.

getReason()`.
        ***
Action Required:**
Update your
code to
work with
the `FinishReason`enum.
This typically
involves changing
variable types
and how
you compare

the reason(e.g ., using `==` with enum constants or comparing the string from `getReason()`).

        **Example:Handling `finishReason` (
within the
context of
iterating choices
from `ChatCompletionResponse`)**

        **

Before(inside loop processing `choice`):**
        ```java
String reason = choice.getFinishReason();
    if("stop".

equals(reason)){
        // ...
        }
        ```

        **

After(inside loop processing `choice`):**
        ```java
    import nl.dannyj.mistral.models.completion.FinishReason; // Ensure import
// ...
FinishReason reason = choice.getFinishReason();
    if(reason ==FinishReason.STOP){
        // ...
        }
        // Or, to compare with the string value:
        // if ("stop".equals(reason.getReason())) { ... }
        ```

        ***`logProbs`
Field Removal:**
        *The `logProbs`

field(previously a `String`) has been removed from the `Choice`

class as it is no longer returned by the API.
        ***
Action Required:**
If your
code was
accessing `choice.

getLogProbs()`,this
will now
result in
a compilation
error.You must
remove such
accesses .
```
