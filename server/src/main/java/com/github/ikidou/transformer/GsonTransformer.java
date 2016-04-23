/*
 * Copyright 2016 ikidou
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

package com.github.ikidou.transformer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;

public class GsonTransformer implements ResponseTransformer {
    private static GsonTransformer instance = new GsonTransformer();
    private final Gson gson;

    private GsonTransformer() {
        this(null);
    }

    private GsonTransformer(Gson gson) {
        if (gson == null) {
            gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
        }
        this.gson = gson;
    }


    public static GsonTransformer getDefault() {
        return instance;
    }

    public static GsonTransformer newInstance(Gson gson) {
        return new GsonTransformer(gson);
    }

    public static GsonTransformer newInstance() {
        return new GsonTransformer();
    }

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }
}
