package edu.pwr.drozd;

public class GameState {

    private int currentPlayer;
    private int opponentPlayer;
    private String algorithm;
    private int depth;
    private int[][] board;
    private String cellSelector;
    private String stateEvaluator;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(int opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getCellSelector() {
        return cellSelector;
    }

    public void setCellSelector(String cellSelector) {
        this.cellSelector = cellSelector;
    }

    public String getStateEvaluator() {
        return stateEvaluator;
    }

    public void setStateEvaluator(String stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }
}
