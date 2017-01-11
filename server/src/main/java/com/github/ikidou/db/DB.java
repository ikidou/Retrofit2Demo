
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

package com.github.ikidou.db;

import com.github.ikidou.entity.Blog;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static ConnectionSource getConnectionSource() throws SQLException {
        return new JdbcConnectionSource("jdbc:sqlite:Data/data.db", new SqliteDatabaseType());
    }

    public static void init() {
        File dbFile = new File("Data/data.db");
        ConnectionSource source = null;
        try {
            source = DB.getConnectionSource();
            if (!dbFile.exists()) {
                File parentFile = dbFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                TableUtils.createTable(source, Blog.class);
                List<Blog> blogList = generateDefaultBlogList();
                Dao<Blog, Long> blogDao = DaoManager.createDao(source, Blog.class);
                DatabaseConnection databaseConnection = blogDao.startThreadConnection();
                boolean autoCommit = databaseConnection.isAutoCommit();
                databaseConnection.setAutoCommit(false);
                for (Blog blog : blogList) {
                    blogDao.create(blog);
                }
                blogDao.endThreadConnection(databaseConnection);
                blogDao.commit(databaseConnection);
                databaseConnection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Blog> generateDefaultBlogList() {
        List<Blog> blogList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Blog blog = new Blog();
            blog.author = "怪盗kidou";
            blog.title = "Retrofit2 测试" + (i + 1);
            blog.content = "这里是 Retrofit2 Demo 测试服务器" + (i + 1);
            blogList.add(blog);
        }
        return blogList;
    }
}
