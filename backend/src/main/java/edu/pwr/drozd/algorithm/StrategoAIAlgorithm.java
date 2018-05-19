package edu.pwr.drozd.algorithm;

import edu.pwr.drozd.cell_selector.CellSelector;
import edu.pwr.drozd.evaluator.BoardStateEvaluator;

public interface StrategoAIAlgorithm {

   int[] nextMove(int[][] gameBoard,
                          int currentPlayer,
                          int opponentPlayer,
                          int depth,
                          CellSelector cellSelector,
                          BoardStateEvaluator stateEvaluator);

}
