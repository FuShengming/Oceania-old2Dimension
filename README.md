# Group_old2Dimension Project Oceania iteration 1


本项目为南京大学2020年春软件工程与计算三课程项目

## 小组成员

171250640 付圣铭
171250532 苗朗宁
171250559 王煜霄
171250638 葛闰

## 项目说明

### 开发框架

本项目因考虑后续（迭代二迭代三）的开发，使用了springboot框架。


### 使用与相关功能代码说明

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

		输出为按路径长度从小到大输出路径信息

在使用本项目时可以在OceaniaRunner类中的run()方法中调用这三个方法，run()方法即为迭代一的入口。注意，这三个方法的使用要放在run()方法中的初始化相关代码之后。


### 检查点与功能实现说明

1. 功能点1：抽取依赖并生成有向图
	
	此功能的实现类为com.old2dimension.OCEANIA.blImpl包下的GraphCalculateImpl类中的方法，void initializeGraph(String filename)。实现为通过传入的文件名来读取文件数据，而后将数据根据顶点进行分割并初始化每个点和边的信息，再通过边信息初始化邻接矩阵，最后通过邻接矩阵计算出初始的连通域集合。

2. 功能点2：生成的代码依赖图中点、边、连通域的数量
	
	此功能的实现即为功能点1中对点、边、连通域的初始化，初始化完成后可以直接获得点、边、连通域的信息。

3. 功能点3：顶点需保留函数全名，计算每条边的紧密度并保存

	此功能的实现也为GraphCalculateImpl类中的void initializeGraph(String filename)方法所包含。我们先通过生成的邻接矩阵来统计每个点对应的出度和入度，并作为点的属性保存。而后，每条边持有起点和终点两个点信息。最后可以通过每个边所持有的点信息得到起点与终点的出度入度，从而计算出紧密度并进行保存。紧密度本身我们使用一个Weight类来保存，为了适应今后（迭代二、迭代三）可能出现的多种权重等情况，每条边实际持有的是weight的List。

4. 功能点4：紧密度阈值过滤

	此功能的实现为com.old2dimension.OCEANIA.blImpl包下的GraphCalculateImpl类中的方法，ResponseVO getConnectedDomains(ArrayList<WeightForm> weightForms)。此方法中会调用关键的过滤方法，DomainSet filterByWeights(ArrayList<WeightForm> thresholds)进行紧密度的过滤与过滤后连通域的计算。

5. 功能点5：路径查找
	
	此功能的实现为com.old2dimension.OCEANIA.blImpl包下的PathBLImpl类中的方法，ResponseVO findPath(FuncInfoForm startVertex, FuncInfoForm endVertex)。本方法利用邻接矩阵，使用深度优先方法进行路径查找。

注：接口使用ResponseVO是为第二阶段的前后端交互做准备


### 关于测试的说明

本项目因为考虑后续开发而使用了springboot框架，为了满足迭代一直接从控制台输入输出的要求，选择ApplicationRunner自动触发运行的方式。这使得在运行测试时OceaniaRunner中的run()方法也会被执行。此时若run()方法中的输入输出函数有需要控制台输入的（如输入紧密度阈值、输入函数名查找路径）会导致输入等待。故在进行测试时，为防止输入输出错误，请将OceaniaRunner.run()中的所有代码注释掉之后再运行测试。因为测试代码本身和功能代码是分离的，故也不会影响到项目的后续发布和开发。在后续的迭代二中使用前后端开发之后就会避免这个问题。


### 其他说明

本项目已经部署在腾讯云上（jenkins地址http://212.64.93.130:8080/  ）。此外，本小组在http://212.64.93.130:9999/ 上部署了静态分析工具，检查者可以访问此地址查看静态分析结果。