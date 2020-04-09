package com.old2dimension.OCEANIA.blImpl.GraphCalculateImplTest;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GraphInitializeTest {

    @Test
    public void graphInitializeTest1(){
        GraphCalculateImpl graphCalculate =new GraphCalculateImpl();
      graphCalculate.initializeGraph("src/test/resources/test_dependency_data.txt");
      Assert.assertEquals(graphCalculate.allEdges.size(),7);
      Assert.assertEquals(graphCalculate.adMatrix.getVerticesNum(),9);
      Assert.assertEquals(graphCalculate.allEdges.get(0).getWeight("closeness").getWeightValue(),0.3333333333333333,0.0000000000000001);
    }
@Test
    public void graphInitializeTest2(){
    GraphCalculateImpl graphCalculate =new GraphCalculateImpl();
        graphCalculate.initializeGraph("src/test/resources/runner_test_dependency_data.txt");
        Assert.assertEquals(graphCalculate.allEdges.size(),14);
        Assert.assertEquals(graphCalculate.adMatrix.getVerticesNum(),16);
        Assert.assertEquals(graphCalculate.allEdges.get(0).getWeight("closeness").getWeightValue(),0.6666666666666666,0.0000000000000001);
    }

    @Test
    public void graphInitializeTest3(){
        GraphCalculateImpl graphCalculate =new GraphCalculateImpl();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        graphCalculate.initializeGraph("");
        Assert.assertEquals("文件读取失败",outContent.toString().replaceAll("[\\t\\n\\r]", ""));

    }

}
