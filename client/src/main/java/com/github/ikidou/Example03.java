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

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * [Retrofit注解详解 之 FormUrlEncoded/Field/FieldMap/Multipart/Part/PartMap注解]源码
 */
public class Example03 {
    public interface BlogService {

        /**
         * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

        /**
         * Map的key作为表单的键
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);


        /**
         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

        /**
         * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
         * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
         * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);
    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();

        BlogService service = retrofit.create(BlogService.class);


        // 演示 @FormUrlEncoded 和 @Field
        Call<ResponseBody> call1 = service.testFormUrlEncoded1("怪盗kidou", 24);
        ResponseBodyPrinter.printResponseBody(call1);


        //===================================================

        // 演示 @FormUrlEncoded 和 @FieldMap
        // 实现的效果与上面想同
        Map<String, Object> map = new HashMap<>();
        map.put("username", "怪盗kidou");
        map.put("age", 24);
        Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
        ResponseBodyPrinter.printResponseBody(call2);


        //===================================================


        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "怪盗kidou");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        // 演示 @Multipart 和 @Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);
        ResponseBodyPrinter.printResponseBody(call3);

        //===================================================
        // 演示 @Multipart 和 @PartMap
        // 实现和上面同样的效果
        Map<String, RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name", name);
        fileUpload2Args.put("age", age);
        //这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
        //fileUpload2Args.put("file", file);
        Call<ResponseBody> call4 = service.testFileUpload2(fileUpload2Args, filePart); //单独处理文件
        ResponseBodyPrinter.printResponseBody(call4);

        //===================================================
        // 还有一种比较hack的方式可以实现文件上传，
        // 上面说过被当成文件上传的必要条件就是 Content-Disposition 请求头中必须要有 filename="xxx" 才会被当成文件
        // 所有我们在写文件名的时候可以拼把 filename="XXX" 也拼接上去，
        // 即文件名变成  表单键名"; filename="文件名  （两端的引号会自动加，所以这里不加）也可以实现，但是不推荐方式

        Map<String, RequestBody> fileUpload3Args = new HashMap<>();
        fileUpload3Args.put("name",name);
        fileUpload3Args.put("age",age);
        fileUpload3Args.put("file\"; filename=\"test.txt",file);
        Call<ResponseBody> testFileUpload3 = service.testFileUpload3(fileUpload3Args);
        ResponseBodyPrinter.printResponseBody(testFileUpload3);
    }
}
