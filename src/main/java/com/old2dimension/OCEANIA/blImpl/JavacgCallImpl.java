package com.old2dimension.OCEANIA.blImpl;

import gr.gousiosg.javacg.stat.JCallGraph;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class JavacgCallImpl {

    public JavacgCallImpl() {
    }

    public ArrayList<String> getCallGraph(String jarPath, String pkg) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintStream originPrintStream = System.out;
        System.setOut(printStream);
        JCallGraph.main(new String[]{jarPath});
        System.out.flush();
        System.setOut(originPrintStream);

        ArrayList<String> callList = new ArrayList<>();
        Collections.addAll(callList, byteArrayOutputStream.toString().split("\n"));
        return filter(callList, pkg);
    }

    public ArrayList<String> filter(ArrayList<String> stringArrayList, String pkg) {
        pkg = pkg.replace(".", "\\.");
        String regex1 = "^M:" + pkg + ".*";
        String regex2 = "^\\([MIOSD]\\)" + pkg + ".*";
        stringArrayList.removeIf(
                dependency -> !Pattern.matches(regex1, dependency.split(" ")[0])
        );
        stringArrayList.removeIf(
                dependency ->!Pattern.matches(regex2, dependency.split(" ")[1])
        );
        return stringArrayList;
    }
}
