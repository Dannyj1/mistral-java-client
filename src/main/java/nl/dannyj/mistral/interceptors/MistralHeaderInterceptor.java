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

package nl.dannyj.mistral.interceptors;

import lombok.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MistralHeaderInterceptor implements Interceptor {

    private final String apiKey;

    public MistralHeaderInterceptor(@NonNull String apiKey) {
        if (apiKey.isBlank()) {
            throw new IllegalArgumentException("No API key provided");
        }

        this.apiKey = apiKey;
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newRequestBuilder = request.newBuilder();

        if (request.header("Content-Type") == null) {
            newRequestBuilder.addHeader("Content-Type", "application/json");
        }

        if (request.header("Accept") == null) {
            newRequestBuilder.addHeader("Accept", "application/json");
        }

        if (request.header("Authorization") == null) {
            newRequestBuilder.addHeader("Authorization", "Bearer " + this.apiKey);
        }

        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }
}
