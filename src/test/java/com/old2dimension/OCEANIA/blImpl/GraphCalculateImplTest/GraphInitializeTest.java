package com.old2dimension.OCEANIA.blImpl.GraphCalculateImplTest;
import com.old2dimension.OCEANIA.OceaniaApplication;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class GraphInitializeTest {
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Test
    public void graphInitializeTest1(){
      graphCalculate.initializeGraph("src/test/resource/test_dependency_data.txt");
      Assert.assertEquals(graphCalculate.allEdges.size(),7);
      Assert.assertEquals(graphCalculate.adMatrix.getVerticesNum(),9);
      Assert.assertEquals(graphCalculate.allEdges.get(0).getWeight("closeness").getWeightValue(),0.3333333333333333,0.0000000000000001);
    }
@Test
    public void graphInitializeTest2(){
        graphCalculate.initializeGraph("src/test/resource/runner_test_dependency_data.txt");
        Assert.assertEquals(graphCalculate.allEdges.size(),14);
        Assert.assertEquals(graphCalculate.adMatrix.getVerticesNum(),16);
        Assert.assertEquals(graphCalculate.allEdges.get(0).getWeight("closeness").getWeightValue(),0.6666666666666666,0.0000000000000001);
    }

    @Test
    public void graphInitializeTest3(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        graphCalculate.initializeGraph("");
        Assert.assertEquals("文件读取失败\r\n",outContent.toString());

    }

}
