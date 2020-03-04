package com.old2dimension.OCEANIA.blImpl;
import com.old2dimension.OCEANIA.OceaniaApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class GraphCalculateTest {
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Test
    public void graphInitializeTest(){
      graphCalculate.initializeGraph("src/test/resource/test_dependency_data.txt");
      Assert.assertEquals(graphCalculate.allEdges.size(),7);
      Assert.assertEquals(graphCalculate.adMatrix.getVerticesNum(),9);
      Assert.assertEquals(graphCalculate.allEdges.get(0).getWeight("closeness").getWeightValue(),0.3333333333333333,0.0000000000000001);


    }

}