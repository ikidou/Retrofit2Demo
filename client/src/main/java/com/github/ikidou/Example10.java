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
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * [Retrofit 自定义Converter]源码
 */
public class Example10 {
    public interface BlogService {
        @GET("blog/{id}")
            //这里的{id} 表示是一个变量
        CustomCall<String> getFirstBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);
    }

    public static class CustomCall<R> {

        public final Call<R> call;

        public CustomCall(Call<R> call) {
            this.call = call;
        }

        // 提供一个同步获取数据的方法
        public R get() throws IOException {
            return call.execute().body();
        }
    }

    public static class CustomCallAdapter implements CallAdapter<CustomCall<?>> {

        private final Type responseType;

        // 下面的 responseType 方法需要数据的类型
        CustomCallAdapter(Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public <R> CustomCall<R> adapt(Call<R> call) {
            // 由 CustomCall 决定如何使用
            return new CustomCall<>(call);
        }
    }

    public static class CustomCallAdapterFactory extends CallAdapter.Factory {
        public static final CustomCallAdapterFactory INSTANCE = new CustomCallAdapterFactory();

        @Override
        public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            // 获取原始类型
            Class<?> rawType = getRawType(returnType);
            // 返回值必须是CustomCall并且带有泛型
            if (rawType == CustomCall.class && returnType instanceof ParameterizedType) {
                Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
                return new CustomCallAdapter(callReturnType);
            }
            return null;
        }
    }


    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(Example09.StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CustomCallAdapterFactory.INSTANCE)
                .build();

        BlogService service = retrofit.create(BlogService.class);
        CustomCall<String> call = service.getFirstBlog(2);
        try {
            String result = call.get();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
