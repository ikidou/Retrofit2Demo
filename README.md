# Retrofit2Demo

关于Retrofit2的使用教程见我的博客 [《你真的会用Retrofit2吗?Retrofit2完全教程》](http://www.jianshu.com/p/308f3c54abdd)

Retrofit2 源码位置 [`client/src/main/java/com/github/ikidou/`](https://github.com/ikidou/Retrofit2Demo/tree/master/client/src/main/java/com/github/ikidou)

配套 Server 运行方式：找到[`server/src/main/java/com/github/ikidou/RESTServer.java`](https://github.com/ikidou/Retrofit2Demo/blob/master/server/src/main/java/com/github/ikidou/RESTServer.java) 直接右键运行即可

## Android Studio 用户需要注意：

该项目中没有的配置文件，也不是Android项目，所以打开后**可能会报错**，请按下面的步骤解决：

1. **File -> Project Structure (Ctrl+Alt+Shift+S)**,并保证配置符合下图的要求后保存。  
![项目设置1](jdk_setting.png)

2. 打开右侧的Gradle面板，刷新项目依赖。  
![项目设置2](gradle_setting.png)

## 解决 找不到或无法加载主类

- Android Studio: 在运行之前先Build
- Idea : 请按下图的配置修改（适用于所有标准Java Gradle项目）

![IDEA 运行配置](idea_config.png)

License
-------
    Copyright 2016 ikidou

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
