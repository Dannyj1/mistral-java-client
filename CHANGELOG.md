# 2.1.0
- Added Mistral OCR. See the README.md for more details.

# 2.0.0

- **BREAKING**: Major refactor of message handling for chat completions:
    - The `nl.dannyj.mistral.models.completion.Message` class has been removed.
    - Introduced `nl.dannyj.mistral.models.completion.message.ChatMessage` as an abstract base class for messages.
    - New concrete message classes: `SystemMessage`, `UserMessage`, `AssistantMessage`, `ToolMessage`.
    - Message content is now represented by `List<nl.dannyj.mistral.models.completion.content.ContentChunk>` (e.g., `TextChunk`, `ImageURLChunk`) to support multi-modal inputs.
    - `ChatCompletionRequest` now expects `List<ChatMessage>`.
    - Streaming: `DeltaChoice` now contains a `nl.dannyj.mistral.models.completion.message.DeltaMessage` object which holds the actual delta (`role`, `content`, `toolCalls`).
- **BREAKING**: Changes to `nl.dannyj.mistral.models.completion.Choice`:
    - The `finishReason` field type changed from `String` to `nl.dannyj.mistral.models.completion.FinishReason` (enum).
    - The `logProbs` field has been removed.
- **BREAKING**: `encodingFormat` field removed from `EmbeddingRequest` as it is no longer supported by the API.
- **BREAKING**: The `nl.dannyj.mistral.models.model.ModelPermission` class has been removed.
- **BREAKING**: Package changes for core classes:
    - `MessageChunk` moved to `nl.dannyj.mistral.models.completion.message.MessageChunk`.
    - `MessageRole` moved to `nl.dannyj.mistral.models.completion.message.MessageRole`.
- Added support for function calling (tools).
- Added `nl.dannyj.mistral.models.model.ModelCapabilities` to provide model capability information (e.g., `supportsVision()`, `supportsFunctionCalling()`).
- Increased default read timeout to 120 seconds.
- Made it easier to configure timeouts ([issue #7](https://github.com/Dannyj1/mistral-java-client/issues/7))
- Update jackson-databind from 2.16.1 to 2.18.3
- Update jakarta.validation-api from 3.1.0-M1 to 3.1.1
- Update okio from 3.8.0 to 3.11.0
