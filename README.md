#Group_old2Dimension Project Oceania iteration 1


本项目为南京大学2020年春软件工程与计算三课程项目

##小组成员
171250640 付圣铭
171250532 苗朗宁
171250559 王煜霄
171250638 葛闰

##项目说明

###开发框架

本项目因考虑后续（迭代二迭代三）的开发，使用了springboot框架。

###使用与相关功能代码说明

本项目为maven项目，使用前请先用maven导入相关依赖再运行springboot。

项目的com.old2dimension.OCEANIA目录下放置了springboot的启动类OceaniaApplication和本项目功能的入口类OceaniaRunner。为了实现迭代一要求的命令行输入输出功能，OceaniaRunner类实现了ApplicationRunner接口并重写了run方法，使得此类里的run方法会在springboot启动后直接被触发执行。也就是说在使用本项目迭代一时，不必像一般的springboot web项目一样在浏览器中访问某个地址来触发业务代码，只需要运行springboot即可以在控制台使用本项目。

迭代一的功能接口实现主要在com.old2dimension.OCEANIA.blImpl中的两个类中。
GraphCalculateImpl实现了从文件读数据并完成图的初始化、重名函数查找并返回List、紧密度过滤并返回List的功能。PathBLImpl主要实现了路径查找并返回路径信息的功能。注意，以上说的功能是功能类的代码，并不包含输入输出。

实现迭代一的三个输入输出功能的方法在com.old2dimension.OCEANIA.OceaniaRunner中，方法以及对应功能如下：

	1. 打印顶点、边、连通域数目：public void printGraphInfo(GraphCalculateImpl graphCalculate)

		graphCalculate参数为OceaniaRunner类中又springboot自动注入的graphCalculate成员变量

	2. 输入紧密度阈值进行连通域过滤并输出：public void closenessFilter(GraphCalculateImpl graphCalculate)

		graphCalculate参数为OceaniaRunner类中又springboot自动注入的graphCalculate成员变量

	3. 输入顶点数据查找路径数目与最短路径信息：public void findPath()

在使用本项目时可以在OceaniaRunner类中的run()方法中调用这三个方法，run()方法即为迭代一的入口。注意，这三个方法的使用要放在run()方法中的初始化相关代码之后。

###关于测试的说明

本项目因为考虑后续开发而使用了springboot框架，并采取了ApplicationRunner自动触发运行的方式。这使得在运行测试时OceaniaRunner中的run()方法也会被执行。此时若run()方法中的输入输出函数有需要控制台输入的（如输入紧密度阈值、输入函数名查找路径）会导致输入等待。故在进行测试时	请将OceaniaRunner.run()中有关控制台输入方法调用注释掉（在本项目中即为closenessFilter(GraphCalculateImpl graphCalculate)方法和findPath()方法），之后再运行测试。


