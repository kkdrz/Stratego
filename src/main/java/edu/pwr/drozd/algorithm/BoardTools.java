package edu.pwr.drozd.algorithm;

import java.util.LinkedList;

public class BoardTools {

    public static LinkedList<int[]> getPossibleMoves(int[][] gameBoard) {
        LinkedList<int[]> possibleMoves = new LinkedList<>();

        int boardSize = gameBoard.length;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == 0) {
                    possibleMoves.add(new int[]{i, j});
                }
            }
        }
        return possibleMoves;
    }
}
