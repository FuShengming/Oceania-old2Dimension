package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Vertex;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VertexRepository extends Neo4jRepository<Vertex, Long> {

}
