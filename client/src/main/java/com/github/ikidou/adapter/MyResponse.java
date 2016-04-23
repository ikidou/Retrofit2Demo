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

package com.github.ikidou.adapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyResponse<T> {
    private final Call<T> call;

    public MyResponse(Call<T> call) {
        this.call = call;
    }

    public T getResult() throws IOException {
        Response<T> response = call.execute();
        return response.body();
    }
    
    public void getResult(final MyCallback<T> callback) {
        if (callback == null) throw new NullPointerException("callback is null");
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void cancel() {
        call.cancel();
    }

    public interface MyCallback<T> {
        void onResponse(T response);

        void onFailure(Throwable throwable);
    }
}
