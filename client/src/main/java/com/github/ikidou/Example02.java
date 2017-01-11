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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * [Retrofit注解详解 之 HTTP注解]源码
 */
public class Example02 {
    public interface BlogService {
        /**
         * method 表示请的方法，不区分大小写
         * path表示路径
         * hasBody表示是否有请求体
         */
        @HTTP(method = "get", path = "blog/{id}", hasBody = false)
        Call<ResponseBody> getBlog(@Path("id") int id);
    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();

        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.getBlog(2);
        ResponseBodyPrinter.printResponseBody(call);
    }
}
