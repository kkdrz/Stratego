package edu.pwr.drozd.cell_selector;

import java.util.List;

public interface CellSelector {

    void prioritize(List<int[]> possibleMoves, int boardSize);

}
