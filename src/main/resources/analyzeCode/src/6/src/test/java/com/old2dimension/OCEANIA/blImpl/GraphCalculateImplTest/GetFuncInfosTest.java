package com.old2dimension.OCEANIA.blImpl.GraphCalculateImplTest;

import com.old2dimension.OCEANIA.OceaniaApplication;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.vo.VertexVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = OceaniaApplication.class)
public class GetFuncInfosTest {

    @Test
    public void getFuncInfosTest(){
        GraphCalculateImpl graphCalculate = new GraphCalculateImpl();
        graphCalculate.initializeGraph("src/test/resources/test_dependency_data.txt");
        Assert.assertEquals(3, ((ArrayList<VertexVO>) graphCalculate.getAmbiguousFuncInfos("Age").getContent()).size());
        Assert.assertEquals("AgeFactorTest:testRegularAge()", ((ArrayList<VertexVO>) graphCalculate.getAmbiguousFuncInfos("Age").getContent()).get(0).getClassNameAndFunc());
        Assert.assertEquals(9, ((ArrayList<VertexVO>) graphCalculate.getAmbiguousFuncInfos("").getContent()).size());
        Assert.assertEquals(0, ((ArrayList<VertexVO>) graphCalculate.getAmbiguousFuncInfos("rua").getContent()).size());
    }
}