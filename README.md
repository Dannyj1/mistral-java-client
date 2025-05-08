[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://dannyj1.github.io/mistral-java-client/)

# About

**Mistral-java-client** is a Java client for the [Mistral AI](https://mistral.ai/) API. It allows you to easily interact
with the Mistral AI API from your Java application.
Supports all chat completion and embedding models available in the API.

New models or models not listed here may be already supported without any updates to the library.

# Supported APIs

Mistral-java-client is built against version 0.0.2 of the [Mistral AI API](https://docs.mistral.ai/api/).

- [Chat Completion](https://docs.mistral.ai/api/#tag/chat/operation/chat_completion_v1_chat_completions_post)
- [List Models](https://docs.mistral.ai/api/#tag/models/operation/list_models_v1_models_get)
- [Embeddings](https://docs.mistral.ai/api/#tag/embeddings/operation/embeddings_v1_embeddings_post)

# Requirements

- Java 17 or higher
- A Mistral AI API Key (see the [Mistral Quickstart documentation](https://docs.mistral.ai/getting-started/quickstart/)
  for more details on API
  access)

# Installation

## Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Dannyj1:mistral-java-client:2.0.0'
}
```

## Maven

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
<groupId>com.github.Dannyj1</groupId>
<artifactId>mistral-java-client</artifactId>
<version>2.0.0</version>
</dependency>
```

# Documentation

You can find the javadocs for this library here: https://dannyj1.github.io/mistral-java-client/

The Mistral documentation also contains a lot of useful information about their models and what certain parameters
do: https://docs.mistral.ai/

# Usage

The MistralClient class contains all the methods needed to interact with the Mistral AI API. The following examples show
how to use the client to list available models and create chat completions.

## List Available Models

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the API_KEY parameter given to the MistralClient constructor
String API_KEY = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(API_KEY);

// Get a list of available models.
List<Model> models = client.listModels().getModels();

// Loop through all available models and print their ID. The ID can be used to specify the model when creating chat completions.
for (Model model : models) {
    // Only print models that support chat completion and vision.
    if (model.getCapabilities().supportsChatCompletion() && model.getCapabilities().supportsVision()) {
        System.out.println(model.getId());
    }
}
```

Example output:

```text
pixtral-large-2411
pixtral-large-latest
mistral-large-pixtral-2411
pixtral-12b-2409
pixtral-12b
pixtral-12b-latest
mistral-small-2503
mistral-small-latest
```

## Chat Completions

The chat completion in this example code is blocking and will wait until the whole response is generated.
See [Streaming Chat Completions](#streaming-chat-completions) for a way to stream chunks as they are being generated.

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the API_KEY parameter given to the MistralClient constructor
String API_KEY = "API_KEY_HERE";
String MODEL_NAME = "mistral-small-latest";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(API_KEY);

// MessageListBuilder can be used to easily create a List<ChatMessage> object, with the method specifying the role.
// You can also manually create a list of ChatMessage objects (e.g. SystemMessage, UserMessage, AssistantMessage)
// For UserMessage with multimodal content (e.g. images), or AssistantMessage with tool calls, you'd construct them directly.
List<ChatMessage> messages = new MessageListBuilder()
        .system("You are a helpful Java assistant.")
        .user("Write a java program that prints 'Hello World!' a random amount of times. Do not explain the code.")
        .build();

// Create a ChatCompletionRequest. A ChatCompletionRequest has to be passed to the createChatCompletion method.
// It contains all the parameters needed to create a chat completion. Parameters not specified will be set to their default values.
ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model(MODEL_NAME)
        .messages(messages)
        .build();

// Create a chat completion
ChatCompletionResponse response = client.createChatCompletion(request);

// A response contains a list of choices. The message in a choice is an AssistantMessage.
// We get the first choice here and print its content.
// Content is a List<ContentChunk>. For simple text, we iterate and append text from TextChunk instances.
AssistantMessage firstChoice = response.getChoices().get(0).getMessage();
String textContent = firstChoice.getTextContent();

if (textContent !=null) {
    System.out.println("Assistant:\n" + textContent);
} else { 
    System.out.println("No text content available in the assistant's response.");
}
```

Example output:

```java
Assistant:
import java.util.Random;

public class HelloWorldRandom {
    public static void main(String[] args) {
        Random rand = new Random();
        int times = rand.nextInt(10) + 1;
        for (int i = 0; i < times; i++) {
            System.out.println("Hello World!");
        }
    }
}
```

### Function Calling Example

The following example shows how to use the function calling feature of the Mistral API.

```java
String API_KEY = "API_KEY_HERE";
String MODEL_NAME = "mistral-large-latest"; // Must be a model that supports function calling

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(API_KEY);

// 1. Define the tool (function) schema as a compact JSON string
String weatherFunctionSchema = """
        {
            "type": "object",
            "properties": {
                "location": {
                    "type": "string",
                    "description": "The city and state, e.g. San Francisco, CA"
                },
                "unit": {
                    "type": "string",
                    "enum": ["celsius", "fahrenheit"],
                    "description": "The temperature unit to use."
                }
            },
            "required": ["location", "unit"]
        }
        """;

Tool weatherTool = Tool.builder()
        .function(Function.builder()
                .name("getCurrentWeather")
                .description("Get the current weather in a given location")
                .parameters(weatherFunctionSchema)
                .build())
        .build();

// 2. Build initial message list and request
List<ChatMessage> messages = new ArrayList<>(new MessageListBuilder()
        .user("What's the weather like in London?")
        .build());

ChatCompletionRequest initialRequest = ChatCompletionRequest.builder()
        .model(MODEL_NAME)
        .messages(messages)
        .tools(List.of(weatherTool))
        .toolChoice(ToolChoiceEnum.ANY) // Force the model to use a tool
        .build();

System.out.println("Initial Request -> Model");

// 3. First API Call
ChatCompletionResponse response = client.createChatCompletion(initialRequest);

AssistantMessage responseMessage = response.getChoices().get(0).getMessage();
messages.add(responseMessage); // Add assistant's response (with tool call) to messages

// 4. Check for tool calls and simulate execution
if (responseMessage.getToolCalls() != null && !responseMessage.getToolCalls().isEmpty()) {
    System.out.println("Model Response -> Tool Call Requested");

    ToolCall toolCall = responseMessage.getToolCalls().get(0); // Assuming one tool call for simplicity
    FunctionCall functionCall = toolCall.getFunction();

    System.out.println("  Function Name: " + functionCall.getName());
    System.out.println("  Arguments: " + functionCall.getArguments());

    // 5. Simulate tool execution based on function name and arguments
    String toolResultJson;
    if ("getCurrentWeather".equals(functionCall.getName())) {
        // In a real application, you would parse arguments and call your actual function
        // Here, we just simulate a result
        toolResultJson = "{\"temperature\": \"15\", \"unit\": \"celsius\", \"description\": \"Cloudy\"}";
        System.out.println("  Simulated Tool Result: " + toolResultJson);
    } else {
        System.out.println("  Unknown function called: " + functionCall.getName());
        toolResultJson = "{\"error\": \"Unknown function\"}";
    }

    // 6. Build follow-up request with tool result
    messages.add(new ToolMessage(toolResultJson, toolCall.getId()));

    ChatCompletionRequest followUpRequest = ChatCompletionRequest.builder()
            .model(MODEL_NAME)
            .messages(messages)
            .build();

    System.out.println("Tool Result + History -> Model");

    // 7. Second API Call
    ChatCompletionResponse finalResponse = client.createChatCompletion(followUpRequest);

    // 8. Display final result
    AssistantMessage finalAssistantMessage = finalResponse.getChoices().get(0).getMessage();
    String finalContent = finalAssistantMessage.getTextContent();
    System.out.println("Final Model Response:\n" + (finalContent != null ? finalContent : "No text content in final response."));

} else {
    // Model decided not to call a tool
    System.out.println("Model Response -> No Tool Call");

    String textContent = responseMessage.getTextContent();
    System.out.println("Assistant:\n" + (textContent != null ? textContent : "No text content available."));
}
System.out.println("--- End of Function Calling Example ---");
}
```

Example output:

```text
--- Function Calling Example ---
Initial Request -> Model
Model Response -> Tool Call Requested
  Function Name: getCurrentWeather
  Arguments: {"location": "London", "unit": "celsius"}
  Simulated Tool Result: {"temperature": "15", "unit": "celsius", "description": "Cloudy"}
Tool Result + History -> Model
Final Model Response:
The weather in London is currently 15°C and cloudy.
--- End of Function Calling Example ---
```

## Streaming Chat Completions

The following example shows how to use a streaming chat completion. The API will return chunks of the message as it is
being generated instead of waiting for the whole message to be generated.

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the API_KEY parameter given to the MistralClient constructor
String API_KEY = "API_KEY_HERE";
String MODEL_NAME = "mistral-small-latest";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(API_KEY);
// Use the MessageListBuilder to easily build a List<ChatMessage>
List<ChatMessage> messages = new MessageListBuilder()
        .system("You are a helpful Java assistant.")
        .user("Write a java program that prints the first `n` numbers of the Fibonacci sequence.")
        .build();
ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model(MODEL_NAME)
        .messages(messages)
        .stream(true)
        .build();

client.createChatCompletionStream(request, new ChatCompletionChunkCallback() {
    @Override
    public void onChunkReceived(MessageChunk chunk) {
        // This method receives a chunk of the message as it is being generated.
        // The MessageChunk contains choices, and each choice (DeltaChoice) has the delta.
        if (chunk.getChoices() == null || chunk.getChoices().isEmpty()) {
            return;
        }

        DeltaChoice deltaChoice = chunk.getChoices().get(0);
        String textContent = deltaChoice.getTextContent();

        System.out.print(textContent);
        // If using tool calls, you might also check deltaChoice.getToolCalls()
    }

    @Override
    public void onComplete() {
        // This method is called when the generation is fully completed.
        System.out.println("\n\n== Generation Done! ==");
    }

    @Override
    public void onError(Exception e) {
        // This method is called when an error occurs. Generation will be stopped and the other methods will not be called
        e.printStackTrace();
    }
});
```

Example output:

```java
Here is a Java program that prints the first `n` numbers of the Fibonacci sequence:

public class Fibonacci {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); // Get the number of Fibonacci numbers to print from command line argument

        int num1 = 0, num2 = 1;

        System.out.print(num1 + " " + num2); // Print the first two numbers of the sequence

        for (int i = 2; i < n; i++) {
            int nextNumber = num1 + num2;
            System.out.print(" " + nextNumber);
            num1 = num2;
            num2 = nextNumber;
        }
    }
}

To run the program, save it as `Fibonacci.java` and compile it using the command `javac Fibonacci.java`. To run the program and print the first `n` numbers of the Fibonacci sequence, provide the number as a command line argument, e.g. `java Fibonacci 10` to print the first 10 numbers.

== Generation Done! ==
```

## Embeddings

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the API_KEY parameter given to the MistralClient constructor
String API_KEY = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(API_KEY);
List<String> exampleTexts = List.of(
        "This is a test sentence.",
        "This is another test sentence."
);

EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
        .model("mistral-embed") // mistral-embed is currently the only model available for embedding
        .input(exampleTexts)
        .build();

EmbeddingResponse embeddingsResponse = client.createEmbedding(embeddingRequest);
// Embeddings are returned as a list of FloatEmbedding objects. FloatEmbedding objects contain a list of floats per input string.
// See the Mistral documentation for more information: https://docs.mistral.ai/capabilities/embeddings/
List<FloatEmbedding> embeddings = embeddingsResponse.getData();
embeddings.forEach(embedding -> System.out.println(embedding.getEmbedding()));
```

Example output:

```text
[-0.028015137, 0.02532959, 0.042785645, ... , -0.020980835, 0.011947632, -0.0035934448]
[-0.02015686, 0.04272461, 0.05529785, ... , -0.006855011, 0.009529114, -0.016448975]
```

# Roadmap

- [ ] Add support for all missing features (e.g. OCR)
- [ ] Handle rate limits
- [ ] Unit tests

# License

Copyright 2024-2025 Danny Jelsma

Mistral-java-client is licensed under the Apache License, Version 2.0 (the "License"). See the [LICENSE](LICENSE) file
for the full license text or obtain a copy of the license at https://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.

# Disclaimer

This project is an unofficial and independent library/client for the Mistral AI API. Mistral-java-client and its
maintainers are not affiliated with or endorsed by Mistral AI in any way.

Before using this library, please make sure to read and understand
the [Mistral AI Terms of Service](https://mistral.ai/terms-of-service/).
