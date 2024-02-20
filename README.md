# About

**Mistral-java-client** is a Java client for the [Mistral AI](https://mistral.ai/) API. It allows you to easily interact
with the Mistral AI API from your Java application.
Currently supports all chat completion models. At the time of writing these are:

- mistral-tiny
- mistral-small
- mistral-medium

The embedding endpoint will be supported at a later date.

**NOTE:** This library is currently in **alpha**. It is currently NOT possible to using streaming in message completions
or to use embedding models. These features will be added in the future. The currently supported APIs should be stable
however.

# Supported APIs

Mistral-java-client is built against version 0.0.1 of the [Mistral AI API](https://docs.mistral.ai/api/).

- [Create Chat Completions](https://docs.mistral.ai/api/#operation/createChatCompletion)
- [List Available Models](https://docs.mistral.ai/api/#operation/listModels)
- "Create Embeddings" to be implemented later

# Installation

## Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Dannyj1:mistral-java-client:0.1.1'
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
    <version>0.1</version>
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
for (Model model : models) {
    System.out.println(model.getId());
}
```

Example output:

```
mistral-medium
mistral-small
mistral-tiny
mistral-embed
```

## Chat Completions

```java
// You can also put the API key in an environment variable called MISTRAL_API_KEY and remove the apiKey parameter given to the MistralClient constructor
String apiKey = "API_KEY_HERE";

// Initialize the client. This should ideally only be done once. The instance should be re-used for multiple requests
MistralClient client = new MistralClient(apiKey);
String model = "mistral-tiny";
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

# Roadmap

- [ ] Add support for streaming in message completions
- [ ] Add support for embedding models
- [ ] Figure out how Mistral handles rate limiting and create a queue system to handle it
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
