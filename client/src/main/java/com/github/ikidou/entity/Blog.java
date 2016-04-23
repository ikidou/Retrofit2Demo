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

package com.github.ikidou.entity;

import com.google.gson.annotations.SerializedName;

public class Blog {

    @SerializedName("id")
    public long id;

    @SerializedName("date")
    public String date;

    @SerializedName("author")
    public String author;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
