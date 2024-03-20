# About

**Mistral-java-client** is a Java client for the [Mistral AI](https://mistral.ai/) API. It allows you to easily interact
with the Mistral AI API from your Java application.
Currently supports all chat completion models. At the time of writing these are:

- open-mistral-7b
- open-mixtral-8x7b
- mistral-small-latest
- mistral-medium-latest
- mistral-large-latest
- mistral-embed

New models or models not listed here may be already supported without any updates to the library.

**NOTE:** This library is currently in **alpha**. It is currently NOT possible to using streaming in message
completions or function calls. This will be added in the future. The currently supported APIs should be stable
however.

# Supported APIs

Mistral-java-client is built against version 0.0.1 of the [Mistral AI API](https://docs.mistral.ai/api/).

- [Create Chat Completions](https://docs.mistral.ai/api/#operation/createChatCompletion)
- [List Available Models](https://docs.mistral.ai/api/#operation/listModels)
- [Create Embeddings](https://docs.mistral.ai/guides/embeddings/)

# Requirements

- Java 17 or higher
- A Mistral AI API Key (see the [Mistral documentation](https://docs.mistral.ai/#api-access) for more details on API
  access)

# Installation

## Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Dannyj1:mistral-java-client:0.3.0'
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
    <version>0.3.0</version>
</dependency>
```

# Usage

The MistralClient class contains all the methods needed to interact with the Mistral AI API. The following examples show
how to use the client to list available models and create chat completions.

## List Available Models

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the apiKey parameter given to the MistralClient constructor
String apiKey = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(apiKey);

// Get a list of available models
List<Model> models = client.listModels().getModels();

// Loop through all available models and print their ID. The id can be used to specify the model when creating chat completions
for(
Model model :models){
        System.out.

println(model.getId());
        }
```

Example output:

```
open-mistral-7b
open-mixtral-8x7b
mistral-small-latest
mistral-medium-latest
mistral-large-latest
mistral-embed
```

## Chat Completions
The chat completion in this example code is blocking and will wait until the whole response is generated.
See [Streaming Chat Completions](#streaming-chat-completions) for a way to stream chunks as they are being generated.
```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the apiKey parameter given to the MistralClient constructor
String apiKey = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(apiKey);
String model = "open-mistral-7b";
// MessageListBuilder can be used to easily create a List<Message> object, with the method specifying the role.
// You can also manually create a list of Message objects
List<Message> messages = new MessageListBuilder()
        .system("You are a helpful assistant.")
        .user("Write a java program that prints 'Hello World!'.")
        .build();

// Create a ChatCompletionRequest. A ChatCompletionRequest has to be passed to the createChatCompletion method.
// It contains all the parameters needed to create a chat completion. Parameters not specified will be set to their default values.
ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model(model)
        .temperature(0.75)
        .messages(messages)
        .safePrompt(false)
        .build();

// Create a chat completion
ChatCompletionResponse response = client.createChatCompletion(request);

// A response contains a list of choices. We get the first choice here and print it
Message firstChoice = response.getChoices().get(0).getMessage();
System.out.println(firstChoice.getRole() + ":\n" + firstChoice.getContent() + "\n");
```

Example output:
```
ASSISTANT:
Here is a simple Java program that prints "Hello World!" to the console:

'''java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
'''
```

## Streaming Chat Completions

The following example show how to use a streaming chat completion. The API will return chunks of the message as it is
being generated instead of waiting for the whole message to be generated.

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the apiKey parameter given to the MistralClient constructor
String apiKey = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(apiKey);
String model = "open-mistral-7b";
List<Message> messages = new MessageListBuilder()
        .system("You are a helpful programming assistant.")
        .user("Write a java program that prints the first `n` numbers of the Fibonacci sequence.")
        .build();
ChatCompletionRequest request = ChatCompletionRequest.builder()
        .model(model)
        .messages(messages)
        .stream(true)
        .build();

client.

createChatCompletionStream(request, new ChatCompletionChunkCallback() {
  @Override
  public void onChunkReceived (MessageChunk chunk){
    // This method receives a chunk of the message as it is being generated.
    System.out.print(chunk.getChoices().get(0).getMessage().getContent());
  }

  @Override
  public void onComplete () {
    // This method is called when the generation is fully completed.
    System.out.println("\n\n== Generation Done! ==");
  }

  @Override
  public void onError (Exception e){
    // This method is called when an error occurs. Generation will be stopped and the other methods will not be called
    e.printStackTrace();
  }
});
```

Example output:

```
Here is a Java program that prints the first `n` numbers of the Fibonacci sequence:

'''java
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
'''

To run the program, save it as `Fibonacci.java` and compile it using the command `javac Fibonacci.java`. To run the program and print the first `n` numbers of the Fibonacci sequence, provide the number as a command line argument, e.g. `java Fibonacci 10` to print the first 10 numbers.

== Generation Done! ==
```

## Embeddings

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the apiKey parameter given to the MistralClient constructor
String apiKey = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(apiKey);
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
// See the Mistral documentation for more information: https://docs.mistral.ai/guides/embeddings/
List<FloatEmbedding> embeddings = embeddingsResponse.getData();
embeddings.forEach(embedding -> System.out.println(embedding.getEmbedding()));
```

Example output:

```
[-0.028015137, 0.02532959, 0.042785645, ... , -0.020980835, 0.011947632, -0.0035934448]
[-0.02015686, 0.04272461, 0.05529785, ... , -0.006855011, 0.009529114, -0.016448975]
```

# Roadmap
- [ ] Add support for Function Calls
- [ ] Handle rate limiting using some sort of queue system
- [ ] Unit tests

# License

Copyright 2024 Danny Jelsma

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
