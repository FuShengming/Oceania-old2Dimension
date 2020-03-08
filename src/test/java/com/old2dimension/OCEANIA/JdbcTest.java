package com.old2dimension.OCEANIA;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OceaniaApplication.class)
public class JdbcTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Test
//    public void connection() {
//        Integer two = jdbcTemplate.queryForObject("SELECT 1 + 1 AS solution", Integer.class);
//        System.out.println("1 + 1 = " + two.toString());
//        Assert.assertTrue(two.equals(2));
//    }
}
