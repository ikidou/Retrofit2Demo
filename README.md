# Retrofit2Demo

Retrofit2 源码位置 `client/src/main/java`


测试接口服务器在 **server** 项目下，直接运行 [`RESTServer.main()`](server/src/main/java/com/github/ikidou/RESTServer.java) 即可启动测试服务器，所面代码示例均使用该接口(接口地址 http://localhost:4567/ ).
当然你也可以自己借助 [json-server](https://github.com/typicode/json-server) 或 最新开源的[Parse](https://github.com/ParsePlatform/parse-server) 搭建一个REST API，不过都需要安装Node.js，有兴趣的可以去试试。

接口列表：

|地址|请求方法|参数|说明|
|---|---|---|---|
|/blog|GET|page={page},sort=asc或desc|分页获取Blog列表,每页10条|
|/blog/{id}|GET|id|获取指定ID的Blog|
|/blog|POST|{"author":"","title":"","content":""}|创建一个新Blog|
|/blog/{id}|PUT|{"author":"","title":"","content":""} 中至少一个|修改Blog|
|/blog/{id}|DELETE|id|删除一个Blog|
|/form|POST|任意,最终以Json Object形式返回|用于测试Form表单，支持文件上传|
|/headers|GET|showAll=true或false,默认false|返回自定义请求头，all=true是显示全部|
 注：以上的接口的`{id}`和`{page}`均代表一个纯数字，`/blog/{id}` 等价于 `/blog?id=XXX`



