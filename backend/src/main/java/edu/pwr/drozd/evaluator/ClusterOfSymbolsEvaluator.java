package edu.pwr.drozd.evaluator;

public class ClusterOfSymbolsEvaluator implements BoardStateEvaluator {

    @Override
    public int evaluate(int[][] gameBoard, int currentPlayerNumber) {
        int value = 0;
        int boardSize = gameBoard.length;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; i < boardSize; i++) {
                if (gameBoard[i][j] == currentPlayerNumber) {
                    if (i > 0 && gameBoard[i - 1][j] == currentPlayerNumber)
                        value++;
                    if (i < boardSize - 1 && gameBoard[i + 1][j] == currentPlayerNumber)
                        value++;
                    if (j < boardSize - 1 && gameBoard[i][j + 1] == currentPlayerNumber)
                        value++;
                    if (i < boardSize - 1 && j < boardSize - 1 && gameBoard[i + 1][j + 1] == currentPlayerNumber)
                        value++;
                    if (i > 0 && j < boardSize - 1 && gameBoard[i - 1][j + 1] == currentPlayerNumber)
                        value++;
                }
            }
        }
        return value;
    }
}
