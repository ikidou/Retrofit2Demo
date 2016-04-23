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

import com.github.ikidou.entity.Blog;
import com.github.ikidou.entity.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * [Retrofit Converter 反序化]源码
 */
public class Example06 {
    public interface BlogService {
        @GET("blog/{id}")
            //这里的{id} 表示是一个变量
        Call<Result<Blog>> getFirstBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        BlogService service = retrofit.create(BlogService.class);
        Call<Result<Blog>> call = service.getFirstBlog(2);
        call.enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                // 已经转换为想要的类型了
                Result<Blog> result = response.body();
                System.out.println(result);
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
