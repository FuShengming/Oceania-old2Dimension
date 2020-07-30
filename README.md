# Group_old2Dimension Project Oceania iteration 3

本项目为南京大学2020年春软件工程与计算三课程项目

## 小组成员

171250640 付圣铭
171250532 苗朗宁
171250559 王煜霄
171250638 葛闰

## 项目说明

### 开发框架

后端使用了Spring boot，Spring Data框架，Spring MVC模型，前端使用了jQuery，Bootstrap框架，使用jwt进行身份安全认证，cytoscape.js用于图形展示。后端推送消息使用WebSocket技术。


### 使用与相关功能说明

#### 使用说明

网络使用： 

直接访问 http://old2dimension.cn/  即可使用本Oceania系统。

本地运行项目：

本项目为maven项目，使用前请先用maven导入相关依赖再运行springboot。

本地运行前请导入项目src/main/resources/db 目录下.sql文件，初始化mysql数据库与表结构。

导入表结构之后请在命令行中跳转到项目lib目录，执行以下命令来引入静态分析工具依赖

	mvn install:install-file -Dfile=javacg-0.1-SNAPSHOT-static.jar -DgroupId=gr.gousoisg -DartifactId=javacg.stat -Dversion=0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true

项目启动后访问本地8086端口/或/index可以进入网站主页，根据页面提示可以进入登录和注册页面。

#### 功能说明

相对于上次迭代新增：

1. 创建/退出小组功能

   用户可以使用本系统创建合作小组并邀请其他用户加入，创建小组的用户默认为组长。小组的名称和简介可以编辑。被邀请的用户会收到邀请通知，可以在通知中选择接受邀请或忽略邀请。

   小组组员可以退出小组，小组组长在小组仍有其他成员的情况下需转让小组组长职位后退出小组。

2. 小组公告功能

   小组中可以由组长发布小组公告，出现新公告后小组成员会收到新消息提醒。小组成员阅读公告后公告会被设为对其已读。

3. 小组任务发布与分配功能

   小组组长可以发布和分配任务给小组成员。任务信息包括任务的描述、开始与截止时间、任务状态等。小组成员可以完成任务。

4. 小组公有项目功能

   小组可以拥有自己的公有代码项目，小组项目对所有组员可见并可以由多名小组成员进行编辑。

   代码项目可以选择从自己的个人代码复制，也可以上传新的项目。

5. 小组项目标记数据统计功能

   小组组长可以查看小组项目的统计信息，包含对每个项目，每个小组成员所贡献的图元素注释数。

6. 私聊功能

   用户可以使用系统与其他用户进行文字私聊。


### 其他说明

1. 本项目已经部署在腾讯云上（jenkins地址http://212.64.93.130:8080/ ）。
2. 用户可以直接访问 http://old2dimension.cn/ 来直接访问和使用我们的Oceania系统。
3. 系统默认的管理员账号，用户名为oceania_admin, 密码为OCEANIA, 可以登录此管理员账号，查看系统统计信息。
4. 如果需要下载项目本地运行请先导入src/main/resources/db下的.sql文件初始化数据库与数据库表。导入表结构之后请在命令行中跳转到项目lib目录，执行以下命令来引入静态分析工具依赖
	mvn install:install-file -Dfile=javacg-0.1-SNAPSHOT-static.jar -DgroupId=gr.gousoisg -DartifactId=javacg.stat -Dversion=0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true