package com.old2dimension.OCEANIA;

import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.blImpl.PathBLImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class OceaniaRunnerTest {
    @Autowired
    OceaniaRunner oceaniaRunner;
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Autowired
    PathBLImpl pathBL;
    @Test
    public void OceaniaRunnerTest1(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resources/runner_test_dependency_data.txt");
        oceaniaRunner.printGraphInfo(graphCalculate);
        Assert.assertEquals("图中顶点数为：16\r\n图中边数目为：14\r\n图中连通域个数为：4\r\n".replaceAll("[\\t\\n\\r]", ""),outContent.toString().replaceAll("[\\t\\n\\r]", ""));
    }
    @Test
    public void OceaniaRunnerTest2(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="0.55\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resources/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------" +
                "连通域顶点数：3" +
                "Node1: Class1:Func5()" +
                "Node2: Class1:Func7()" +
                "Node3: Class1:Func6(java.lang.long)"+
                "Edge1: Class1:Func5() -- 0.6666 --> Class1:Func7()"+
                "Edge2: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)"+
                "-------连通域2-------" +
                "连通域顶点数：2" +
                "Node1: Class1:Func1()" +
                "Node2: Class1:Func2()" +
                "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()"+
                        "-------连通域3-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func8()" +
                        "Node2: Class1:Func9()" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()"+
                        "-------连通域4-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func11()" +
                        "Node2: Class1:Func14()" +
                        "Edge1: Class1:Func11() -- 1.0000 --> Class1:Func14()".replaceAll("[\\n\\r]", "")
                ,outContent.toString().replaceAll("[\\n\\r]", ""));
    }
    @Test
    public void OceaniaRunnerTest3(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="0\r\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resources/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------" +
                        "连通域顶点数：5" +
                        "Node1: Class1:Func1()" +
                        "Node2: Class1:Func2()" +
                        "Node3: Class1:Func4()" +
                        "Node4: Class1:Func3()" +
                        "Node5: Class1:Func4(java.lang.long)" +
                        "" +
                        "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()" +
                        "Edge2: Class1:Func2() -- 0.4000 --> Class1:Func4()" +
                        "Edge3: Class1:Func1() -- 0.5000 --> Class1:Func4()" +
                        "Edge4: Class1:Func2() -- 0.5000 --> Class1:Func3()" +
                        "Edge5: Class1:Func2() -- 0.5000 --> Class1:Func4(java.lang.long)" +
                        "-------连通域2-------" +
                        "连通域顶点数：5" +
                        "Node1: Class1:Func10()" +
                        "Node2: Class1:Func12()" +
                        "Node3: Class1:Func11()" +
                        "Node4: Class1:Func13()" +
                        "Node5: Class1:Func14()" +
                        "" +
                        "Edge1: Class1:Func10() -- 0.5000 --> Class1:Func12()" +
                        "Edge2: Class1:Func10() -- 0.5000 --> Class1:Func11()" +
                        "Edge3: Class1:Func10() -- 0.5000 --> Class1:Func13()" +
                        "Edge4: Class1:Func11() -- 1.0000 --> Class1:Func14()" +
                        "-------连通域3-------" +
                        "连通域顶点数：4" +
                        "Node1: Class1:Func5()" +
                        "Node2: Class1:Func6()" +
                        "Node3: Class1:Func7()" +
                        "Node4: Class1:Func6(java.lang.long)" +
                        "" +
                        "Edge1: Class1:Func5() -- 0.5000 --> Class1:Func6()" +
                        "Edge2: Class1:Func5() -- 0.6666 --> Class1:Func7()" +
                        "Edge3: Class1:Func7() -- 0.5000 --> Class1:Func6()" +
                        "Edge4: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)" +
                        "-------连通域4-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func8()" +
                        "Node2: Class1:Func9()" +
                        "" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()".replaceAll("[\\n\\r]", "")
                ,outContent.toString().replaceAll("[\\n\\r]", ""));
    }

    @Test
    public void OceaniaRunnerTest4(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="-1\r\n2\r\n0.55\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resources/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)："+"紧密度阈值必须大于等于0！"+
                        "请输入用于过滤的紧密度阈值(非负小数或整数)："+"紧密度阈值必须小于等于1！"+
                        "请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------" +
                        "连通域顶点数：3" +
                        "Node1: Class1:Func5()" +
                        "Node2: Class1:Func7()" +
                        "Node3: Class1:Func6(java.lang.long)"+
                        "Edge1: Class1:Func5() -- 0.6666 --> Class1:Func7()"+
                        "Edge2: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)"+
                        "-------连通域2-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func1()" +
                        "Node2: Class1:Func2()" +
                        "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()"+
                        "-------连通域3-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func8()" +
                        "Node2: Class1:Func9()" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()"+
                        "-------连通域4-------" +
                        "连通域顶点数：2" +
                        "Node1: Class1:Func11()" +
                        "Node2: Class1:Func14()" +
                        "Edge1: Class1:Func11() -- 1.0000 --> Class1:Func14()".replaceAll("[\\n\\r]", "")

                ,outContent.toString().replaceAll("[\\n\\r]", ""));
    }

    @Test
    public void OceaniaRunnerTest5(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String inString1="getAvailableHospitals\r\n" +
                "1\r\n" +
                "getConnection\r\n" +
                "2\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString1.getBytes());
        System.setIn(inContent);
        oceaniaRunner.initializeGraph(graphCalculate,"call_dependencies_update.txt");
        pathBL = new PathBLImpl(graphCalculate);
        oceaniaRunner.findPath(pathBL);
        Assert.assertEquals("请输入起点类/函数/参数名：" +
                        "1. edu.ncsu.csc.itrust.action.ManageHospitalAssignmentsAction:getAvailableHospitals(java.lang.String)" +
                        "" +
                        "请输入你所选择的节点序号（不超过序号范围的正整数）：" +
                        "请输入终点类/函数/参数名：" +
                        "1. edu.ncsu.csc.itrust.dao.DAOFactory:getConnection()" +
                        "2. edu.ncsu.csc.itrust.dao.IConnectionDriver:getConnection()" +
                        "" +
                        "请输入你所选择的节点序号（不超过序号范围的正整数）：" +
                        "SOURCE NODE: ManageHospitalAssignmentsAction:getAvailableHospitals(java.lang.String)" +
                        "TARGET NODE: IConnectionDriver:getConnection()" +
                        "" +
                        "共有2条路径:" +
                        "" +
                        "PATH 1:ManageHospitalAssignmentsAction:getAvailableHospitals(java.lang.String) -- 0.4 -->" +
                        "HospitalsDAO:getAllHospitals()-- 0.008403361344537815 -->" +
                        "DAOFactory:getConnection()-- 1.0 -->" +
                        "IConnectionDriver:getConnection()" +
                        "" +
                        "PATH 2:ManageHospitalAssignmentsAction:getAvailableHospitals(java.lang.String) -- 0.3333333333333333 -->" +
                        "PersonnelDAO:getHospitals(long)-- 0.008403361344537815 -->" +
                        "DAOFactory:getConnection()-- 1.0 -->" +
                        "IConnectionDriver:getConnection()" +
                        ""
                ,outContent.toString().replaceAll("[\\t\\n\\r]", ""));
    }
}
