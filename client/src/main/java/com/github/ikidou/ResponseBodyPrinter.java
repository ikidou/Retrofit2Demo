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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ResponseBodyPrinter {
    /**
     * 为了方保证执行的顺序，所以调用的是同步方法
     */
    public static void printResponseBody(Call<ResponseBody> call) {
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            } else {
                System.err.println("HttpCode:" + response.code());
                System.err.println("Message:" + response.message());
                System.err.println(response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
