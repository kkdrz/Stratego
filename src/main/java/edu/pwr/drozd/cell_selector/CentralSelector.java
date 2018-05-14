package edu.pwr.drozd.cell_selector;

import java.util.Comparator;
import java.util.List;

public class CentralSelector implements CellSelector {

    @Override
    public void prioritize(List<int[]> possibleMoves, int boardSize) {
        possibleMoves.sort(Comparator.comparingInt(cell -> (Math.abs(cell[0] - boardSize / 2) + Math.abs(cell[1] - boardSize / 2))));
    }
}
