package com.old2dimension.OCEANIA.po;


public class AdjacencyMatrix {

    private int verticesNum;

    private boolean[][] matrix;


    public AdjacencyMatrix(int verticesNum) {
        this.verticesNum = verticesNum;
        matrix = new boolean[verticesNum][verticesNum];
        for (int i = 0; i < verticesNum; i++) {
            for (int j = 0; j < verticesNum; j++) {
                matrix[i][j] = false;
            }
        }
    }

    public int getVerticesNum() {
        return verticesNum;
    }

    public void setMatrix(int index1, int index2, boolean b) {
        matrix[index1][index2] = b;
    }

    public boolean getMatrix(int index1, int index2) {
        return matrix[index1][index2];
    }
}
