# Group_old2Dimension Project Oceania iteration 2

本项目为南京大学2020年春软件工程与计算三课程项目

## 小组成员

171250640 付圣铭
171250532 苗朗宁
171250559 王煜霄
171250638 葛闰

## 项目说明

### 开发框架

后端使用了Spring boot，Spring Data框架，Spring MVC模型，前端使用了jQuery，Bootstrap框架，使用jwt进行身份安全认证，cytoscape.js用于图形展示。


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

如果是管理员（管理员账号见下方其他说明）：

![](https://i.bmp.ovh/imgs/2020/04/d62f91c677f9e481.png)

可以查看需求中的各项统计。

如果是普通用户：

![](https://i.bmp.ovh/imgs/2020/04/717b2709d5ec2b6b.png)

登录或注册完成后会自动跳转到/workspace，该页面中可以选择一个项目查看依赖图（1）；新建一个项目，上传jar包与代码进行分析（2）；更改项目名称（3）；删除该项目（4）。

![](https://i.bmp.ovh/imgs/2020/04/d4d1936853e491f9.png)

在/workspace选择一个项目后会跳转至该项目的依赖图/graph页面。

该页面中可以选择左侧结构树中的函数（1）自动定位图上的节点；

右键或左键长按节点/边/连通域会出现菜单，可以将该元素及其邻居元素选中并居中（2）；

可以为该元素添加标签（3），在右侧显示；

可以高亮该元素（紫色）（4）；

在选中左侧结构树中的函数或图中节点时会显示该节点代码（10）；

可以重新刷新图的布局（5）；

可以将全图居中并适应屏幕（6）；

可以查找节点与路径（7）；

可以更改阈值，过滤连通域（8）；

可以查看该阈值下元素个数（9）；

可以保存当前布局（11），在下次打开项目时不再重新生成布局；

可以将依赖图保存生成JPG文件（12）。


### 其他说明

1. 本项目已经部署在腾讯云上（jenkins地址http://212.64.93.130:8080/ ）。
2. 用户可以直接访问 http://old2dimension.cn/ 来直接访问和使用我们的Oceania系统。
3. 系统默认的管理员账号，用户名为oceania_admin, 密码为OCEANIA, 可以登录此管理员账号，查看系统统计信息。
4. 如果需要下载项目本地运行请先导入src/main/resources/db下的.sql文件初始化数据库与数据库表。导入表结构之后请在命令行中跳转到项目lib目录，执行以下命令来引入静态分析工具依赖
	mvn install:install-file -Dfile=javacg-0.1-SNAPSHOT-static.jar -DgroupId=gr.gousoisg -DartifactId=javacg.stat -Dversion=0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true