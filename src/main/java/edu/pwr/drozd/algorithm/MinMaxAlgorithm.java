package edu.pwr.drozd.algorithm;

import edu.pwr.drozd.cell_selector.CellSelector;
import edu.pwr.drozd.evaluator.BoardStateEvaluator;

import java.util.LinkedList;

public class MinMaxAlgorithm implements StrategoAI {

    public int[] nextMove(
            int[][] gameBoard,
            boolean maximize,
            int currentPlayerNumber,
            int opponentNumber,
            int depth,
            CellSelector cellSelector,
            BoardStateEvaluator stateEvaluator) {

        LinkedList<int[]> possibleMoves = getPossibleMoves(gameBoard);

        int bestScore = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore = 0;
        int bestRow = -1;
        int bestColumn = -1;

        if (cellSelector != null) {
            cellSelector.prioritize(possibleMoves, gameBoard.length);
        }

        if (depth == 0 || possibleMoves.isEmpty()) {
            bestScore = stateEvaluator.evaluate(gameBoard, currentPlayerNumber);
        } else {
            for (int[] move : possibleMoves) {
                gameBoard[move[0]][move[1]] = maximize ? currentPlayerNumber : opponentNumber;
                if (maximize) {
                    currentScore = nextMove(gameBoard, false, currentPlayerNumber, opponentNumber, depth - 1, cellSelector, stateEvaluator)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                } else {
                    currentScore = nextMove(gameBoard, true, currentPlayerNumber, opponentNumber, depth - 1, cellSelector, stateEvaluator)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                }
                gameBoard[move[0]][move[1]] = 0;
            }
        }
        return new int[]{bestScore, bestRow, bestColumn};
    }

    private LinkedList<int[]> getPossibleMoves(int[][] gameBoard) {
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
