package com.old2dimension.OCEANIA;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class OceaniaRunnerTest {
    @Autowired
    OceaniaRunner oceaniaRunner;
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Test
    public void OceaniaRunnerTest1(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resource/runner_test_dependency_data.txt");
        oceaniaRunner.printGraphInfo(graphCalculate);
        Assert.assertEquals("图中顶点数为：16\r\n图中边数目为：14\r\n图中连通域个数为：4\r\n",outContent.toString());
    }
    @Test
    public void OceaniaRunnerTest2(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="0.55\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resource/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------\r\n" +
                "连通域顶点数：3\r\n" +
                "Node1: Class1:Func5()\r\n" +
                "Node2: Class1:Func7()\r\n" +
                "Node3: Class1:Func6(java.lang.long)\r\n\r\n"+
                "Edge1: Class1:Func5() -- 0.6666 --> Class1:Func7()\r\n"+
                "Edge2: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)\r\n"+
                "-------连通域2-------\r\n" +
                "连通域顶点数：2\r\n" +
                "Node1: Class1:Func1()\r\n" +
                "Node2: Class1:Func2()\r\n\r\n" +
                "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()\r\n"+
                        "-------连通域3-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func8()\r\n" +
                        "Node2: Class1:Func9()\r\n\r\n" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()\r\n"+
                        "-------连通域4-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func11()\r\n" +
                        "Node2: Class1:Func14()\r\n\r\n" +
                        "Edge1: Class1:Func11() -- 1.0000 --> Class1:Func14()\r\n"
                ,outContent.toString());
    }
    @Test
    public void OceaniaRunnerTest3(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="0\r\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resource/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------\r\n" +
                        "连通域顶点数：5\r\n" +
                        "Node1: Class1:Func1()\r\n" +
                        "Node2: Class1:Func2()\r\n" +
                        "Node3: Class1:Func4()\r\n" +
                        "Node4: Class1:Func3()\r\n" +
                        "Node5: Class1:Func4(java.lang.long)\r\n" +
                        "\r\n" +
                        "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()\r\n" +
                        "Edge2: Class1:Func2() -- 0.4000 --> Class1:Func4()\r\n" +
                        "Edge3: Class1:Func1() -- 0.5000 --> Class1:Func4()\r\n" +
                        "Edge4: Class1:Func2() -- 0.5000 --> Class1:Func3()\r\n" +
                        "Edge5: Class1:Func2() -- 0.5000 --> Class1:Func4(java.lang.long)\r\n" +
                        "-------连通域2-------\r\n" +
                        "连通域顶点数：5\r\n" +
                        "Node1: Class1:Func10()\r\n" +
                        "Node2: Class1:Func12()\r\n" +
                        "Node3: Class1:Func11()\r\n" +
                        "Node4: Class1:Func13()\r\n" +
                        "Node5: Class1:Func14()\r\n" +
                        "\r\n" +
                        "Edge1: Class1:Func10() -- 0.5000 --> Class1:Func12()\r\n" +
                        "Edge2: Class1:Func10() -- 0.5000 --> Class1:Func11()\r\n" +
                        "Edge3: Class1:Func10() -- 0.5000 --> Class1:Func13()\r\n" +
                        "Edge4: Class1:Func11() -- 1.0000 --> Class1:Func14()\r\n" +
                        "-------连通域3-------\r\n" +
                        "连通域顶点数：4\r\n" +
                        "Node1: Class1:Func5()\r\n" +
                        "Node2: Class1:Func6()\r\n" +
                        "Node3: Class1:Func7()\r\n" +
                        "Node4: Class1:Func6(java.lang.long)\r\n" +
                        "\r\n" +
                        "Edge1: Class1:Func5() -- 0.5000 --> Class1:Func6()\r\n" +
                        "Edge2: Class1:Func5() -- 0.6666 --> Class1:Func7()\r\n" +
                        "Edge3: Class1:Func7() -- 0.5000 --> Class1:Func6()\r\n" +
                        "Edge4: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)\r\n" +
                        "-------连通域4-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func8()\r\n" +
                        "Node2: Class1:Func9()\r\n" +
                        "\r\n" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()\r\n"
                ,outContent.toString());
    }

    @Test
    public void OceaniaRunnerTest4(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String inString="-1\r\n2\r\n0.55\n";
        final ByteArrayInputStream inContent =new ByteArrayInputStream(inString.getBytes());
        System.setIn(inContent);

        oceaniaRunner.initializeGraph(graphCalculate,"src/test/resource/runner_test_dependency_data.txt");
        oceaniaRunner.closenessFilter(graphCalculate);
        Assert.assertEquals("请输入用于过滤的紧密度阈值(非负小数或整数)："+"紧密度阈值必须大于等于0！\r\n"+
                        "请输入用于过滤的紧密度阈值(非负小数或整数)："+"紧密度阈值必须小于等于1！\r\n"+
                        "请输入用于过滤的紧密度阈值(非负小数或整数)：-------连通域1-------\r\n" +
                        "连通域顶点数：3\r\n" +
                        "Node1: Class1:Func5()\r\n" +
                        "Node2: Class1:Func7()\r\n" +
                        "Node3: Class1:Func6(java.lang.long)\r\n\r\n"+
                        "Edge1: Class1:Func5() -- 0.6666 --> Class1:Func7()\r\n"+
                        "Edge2: Class1:Func7() -- 0.6666 --> Class1:Func6(java.lang.long)\r\n"+
                        "-------连通域2-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func1()\r\n" +
                        "Node2: Class1:Func2()\r\n\r\n" +
                        "Edge1: Class1:Func1() -- 0.6666 --> Class1:Func2()\r\n"+
                        "-------连通域3-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func8()\r\n" +
                        "Node2: Class1:Func9()\r\n\r\n" +
                        "Edge1: Class1:Func8() -- 1.0000 --> Class1:Func9()\r\n"+
                        "-------连通域4-------\r\n" +
                        "连通域顶点数：2\r\n" +
                        "Node1: Class1:Func11()\r\n" +
                        "Node2: Class1:Func14()\r\n\r\n" +
                        "Edge1: Class1:Func11() -- 1.0000 --> Class1:Func14()\r\n"

                ,outContent.toString());
    }
}
