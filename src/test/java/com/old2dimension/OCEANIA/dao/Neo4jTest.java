package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Vertex;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jTest {
    @Autowired
    private VertexRepository vertexRepository;

    @Test
    public void saveNewVertex() {
        Vertex vertex = new Vertex();
        vertex.setFuncName("saveNewVertex");
        vertex.setBelongClass("Neo4jTest");
        vertex.setBelongPackage("com.old2dimension.OCEANIA.dao");
        vertexRepository.save(vertex);
    }
}
