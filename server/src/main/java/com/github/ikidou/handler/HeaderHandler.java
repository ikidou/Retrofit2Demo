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

package com.github.ikidou.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import spark.Request;
import spark.Response;
import spark.Route;

public class HeaderHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, String> customHeaderMap = new HashMap<>();
        String showAll = request.queryParams("showAll");

        String[] normalHeaders = new String[]{"Accept-Encoding", "Connection", "Content-Length", "Content-Type", "Host", "User-Agent"};
        Set<String> headers = request.headers();

        if (!"true".equalsIgnoreCase(showAll)) {
            for (String s : normalHeaders) {
                headers.remove(s);
            }
        }

        if (!headers.isEmpty()) {

            for (String header : headers) {
                customHeaderMap.put(header, request.headers(header));
            }

        }
        return customHeaderMap;
    }
}
