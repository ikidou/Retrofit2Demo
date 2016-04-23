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

package com.github.ikidou;

public class Resp {
    public int code;
    public String msg;
    public Object data;
    public long count;
    public long page;

    public static Resp create(int code, String msg) {
        return create(code, msg, null);
    }

    public static Resp create(int code, String msg, Object data) {
        Resp resp = new Resp();
        resp.code = code;
        resp.msg = msg;
        resp.data = data;
        return resp;
    }
}