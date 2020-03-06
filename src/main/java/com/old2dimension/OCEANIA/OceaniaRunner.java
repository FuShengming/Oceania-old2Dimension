package com.old2dimension.OCEANIA;

import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OceaniaRunner implements ApplicationRunner {
    @Autowired
    GraphCalculateImpl graphCalculate;
    @Override
    public void run(ApplicationArguments args) throws Exception{
        graphCalculate.initializeGraph("call_dependencies_update.txt");
    }
}
