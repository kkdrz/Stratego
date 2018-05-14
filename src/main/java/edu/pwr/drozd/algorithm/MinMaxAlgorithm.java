package edu.pwr.drozd.algorithm;

import java.util.LinkedList;

public class MinMaxAlgorithm implements StrategoAI {

    public int[] nextMove(int[][] gameBoard, boolean maximize, int currentPlayerNumber, int opponentNumber, int depth) {
        LinkedList<int[]> possibleMoves = getPossibleMoves(gameBoard);

        int bestScore = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore = 0;
        int bestRow = -1;
        int bestColumn = -1;

        if (depth == 0 || possibleMoves.isEmpty()) {
            bestScore = evaluateBoardState(gameBoard, currentPlayerNumber);
        } else {
            for (int[] move : possibleMoves) {
                gameBoard[move[0]][move[1]] = maximize ? currentPlayerNumber : opponentNumber;
                if (maximize) {
                    currentScore = nextMove(gameBoard, false, currentPlayerNumber, opponentNumber, depth - 1)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                } else {
                    currentScore = nextMove(gameBoard, true, currentPlayerNumber, opponentNumber, depth - 1)[0];
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

    //SymbolsInCompletedLinesBoardStateEvaluationHeuristic
    private int evaluateBoardState(int[][] gameBoard, int currentPlayerNumber) {
        int value = 0;
        int boardSize = gameBoard.length;
        for (int i = 0; i < boardSize; i++) {
            boolean isHorizontalLineCompleted = true;
            boolean isVerticalLineCompleted = true;
            boolean isFirstDiagonalLineCompleted = true;
            boolean isSecondDiagonalLineCompleted = true;
            int horizontalValue = 0;
            int verticalValue = 0;
            int firstDiagonalValue = 0;
            int secondDiagonalValue = 0;
            int partialHorizontalValue = 0;
            int partialVerticalValue = 0;
            int partialFirstDiagonalValue = 0;
            int partialSecondDiagonalValue = 0;

            for (int j = 0; j < boardSize; j++) {
                if (isHorizontalLineCompleted) {
                    if (gameBoard[i][j] == currentPlayerNumber) {
                        partialHorizontalValue++;
                    } else if (gameBoard[i][j] == 0) {
                        isHorizontalLineCompleted = false;
                        horizontalValue = 0;
                        partialHorizontalValue = 0;
                    } else if (partialHorizontalValue > 1) {
                        horizontalValue += partialHorizontalValue;
                        partialHorizontalValue = 0;
                    }

                }

                if (isVerticalLineCompleted) {
                    if (gameBoard[j][i] == currentPlayerNumber) {
                        partialVerticalValue++;
                    } else if (gameBoard[j][i] == 0) {
                        isVerticalLineCompleted = false;
                        verticalValue = 0;
                        partialVerticalValue = 0;
                    } else {
                        if (partialVerticalValue > 1) {
                            verticalValue += partialVerticalValue;
                            partialVerticalValue = 0;
                        }
                    }
                }
                if (isFirstDiagonalLineCompleted && i + j < boardSize) {
                    if (gameBoard[i + j][j] == currentPlayerNumber) {
                        partialFirstDiagonalValue++;
                    } else if (gameBoard[i + j][j] == 0) {
                        isFirstDiagonalLineCompleted = false;
                        firstDiagonalValue = 0;
                        partialFirstDiagonalValue = 0;
                    } else {
                        if (partialFirstDiagonalValue > 1) {
                            firstDiagonalValue += partialFirstDiagonalValue;
                            partialFirstDiagonalValue = 0;
                        }
                    }
                }
                if (isSecondDiagonalLineCompleted && i - j >= 0) {
                    if (gameBoard[i - j][j] == currentPlayerNumber) {
                        partialSecondDiagonalValue++;
                    } else if (gameBoard[i - j][j] == 0) {
                        isSecondDiagonalLineCompleted = false;
                        secondDiagonalValue = 0;
                        partialSecondDiagonalValue = 0;
                    } else {
                        if (partialSecondDiagonalValue > 1) {
                            secondDiagonalValue += partialSecondDiagonalValue;
                            partialSecondDiagonalValue = 0;
                        }
                    }
                }
            }
            value += horizontalValue + verticalValue + firstDiagonalValue + secondDiagonalValue
                    + (partialHorizontalValue > 1 ? partialHorizontalValue : 0)
                    + (partialVerticalValue > 1 ? partialVerticalValue : 0)
                    + (partialFirstDiagonalValue > 1 ? partialFirstDiagonalValue : 0)
                    + (partialSecondDiagonalValue > 1 ? partialSecondDiagonalValue : 0);
        }
        return value;
    }

    private LinkedList<int[]> getPossibleMoves(int[][] gameBoard) {
        LinkedList<int[]> possibleMoves = new LinkedList<int[]>();

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
