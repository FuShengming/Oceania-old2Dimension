package com.old2dimension.OCEANIA.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.old2dimension.OCEANIA.bl.GraphCalculate;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.po.DomainSet;
import com.old2dimension.OCEANIA.po.Vertex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class HelloQueryResolver implements GraphQLQueryResolver {
    public String hello(final String who) {
        return String.format("Hello, %s!", Optional.ofNullable(who).orElse("World"));
    }
}
