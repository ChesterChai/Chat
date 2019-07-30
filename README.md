# Java基于多线程和NIO实现聊天室

- 环境搭建
   - 运行mybatisDB.sql文件中的内容，创建数据表
   - 更改服务器中的全局配置文件jdbcConfig.properties以符合你自己的情况

- 涉及到的技术点
   - 线程池ThreadPoolExecutor
   - 阻塞队列BlockingQueue，生产者消费者模式
   - Selector
   - Channel
   - ByteBuffer
   - ProtoStuff 高性能序列化
   - HttpClient连接池
   - Spring依赖注入
   - mybatis
   - lombok简化POJO开发
   - 原子变量
   - 内置锁
   - CompletionService
   - log4j+slf4j日志
   
- 实现的功能
   - 登录注销
   - 注册
   - 单聊
   - 群聊
   - 客户端提交任务,下载图片并显示
   - 上线下线公告
   - 在线用户记录
   - 批量下载豆瓣电影的图片，并打为压缩包传输给客户端

- 客户端使用方式：
   - 注册：打开客户端根据提示注册用户，等待注册成功消息，点击确定按钮后自动跳转到登陆界面
   - 登录：使用注册过的用户信息直接在登陆界面输入，然后点击登陆按钮即可
   - 注销：关闭客户端即可
   - 单聊：@username:message
        - 例：@user2:hello
   - 群聊：message
        -  例：hello,everyone
   - 提交任务：task.file:图片的URL  / task.crawl_image:豆瓣电影的id[?imageSize=n] 可以加请求参数
        - 例1：task.file:https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p2107289058.webp
          下载完毕后会弹出一个框，输入想将其保存到的路径，比如E:/img.webp
        - 例2：task.crawl_image:1292371?imageSize=2 
          下载完毕后在弹出的框中输入E:/images.zip
          
- 假设用户输入都是符合格式的
   
- 尽可能提高程序的健壮性，对各种异常情况进行处理
