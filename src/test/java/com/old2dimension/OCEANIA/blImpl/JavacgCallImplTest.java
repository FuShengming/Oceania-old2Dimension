package com.old2dimension.OCEANIA.blImpl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JavacgCallImplTest {

    @Test
    void getCallGraph() {
        JavacgCallImpl javacgCall = new JavacgCallImpl();
        ArrayList<String> stringArrayList = javacgCall.getCallGraph("lib/iTrust_update.jar", "edu.ncsu.csc.itrust");
    }
}