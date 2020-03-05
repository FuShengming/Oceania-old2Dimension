package com.old2dimension.OCEANIA.blImpl.GraphCalculateImplTest;

import com.old2dimension.OCEANIA.OceaniaApplication;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class GetFuncInfosTest {
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Test
    public void getFuncInfosTest(){
        graphCalculate.initializeGraph("src/test/resource/test_dependency_data.txt");
        Object o = graphCalculate.getAmbiguousFuncInfos("Age").getContent();
        ArrayList<FuncInfoForm> list = (ArrayList<FuncInfoForm>) o;
        Assert.assertEquals(list.size(), 0);
    }
}