package edu.pwr.drozd.algorithm;

import edu.pwr.drozd.cell_selector.CellSelector;
import edu.pwr.drozd.evaluator.BoardStateEvaluator;

import java.util.LinkedList;

public class MinMaxAlgorithm implements StrategoAIAlgorithm {

    public int[] nextMove(
            int[][] gameBoard,
            boolean maximize,
            int currentPlayer,
            int opponentPlayer,
            int depth,
            CellSelector cellSelector,
            BoardStateEvaluator stateEvaluator) {

        LinkedList<int[]> possibleMoves = BoardTools.getPossibleMoves(gameBoard);

        int bestScore = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore = 0;
        int bestRow = -1;
        int bestColumn = -1;

        if (cellSelector != null) {
            cellSelector.prioritize(possibleMoves, gameBoard.length);
        }

        if (depth == 0 || possibleMoves.isEmpty()) {
            bestScore = stateEvaluator.evaluate(gameBoard, currentPlayer);
        } else {
            for (int[] move : possibleMoves) {
                gameBoard[move[0]][move[1]] = maximize ? currentPlayer : opponentPlayer;
                if (maximize) {
                    currentScore = nextMove(gameBoard, false, currentPlayer, opponentPlayer, depth - 1, cellSelector, stateEvaluator)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                } else {
                    currentScore = nextMove(gameBoard, true, currentPlayer, opponentPlayer, depth - 1, cellSelector, stateEvaluator)[0];
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


    @Override
    public int[] nextMove(
            int[][] gameBoard,
            int currentPlayer,
            int opponentPlayer,
            int depth,
            CellSelector cellSelector,
            BoardStateEvaluator stateEvaluator) {

        return nextMove(gameBoard, true, currentPlayer, opponentPlayer, depth, cellSelector, stateEvaluator);
    }
}
