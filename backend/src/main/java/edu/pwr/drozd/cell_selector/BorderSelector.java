package edu.pwr.drozd.cell_selector;

import java.util.List;

public class BorderSelector implements CellSelector {
    
    @Override
    public void prioritize(List<int[]> possibleMoves, int boardSize) {
        possibleMoves.sort((node1, node2) -> (Math.abs(node2[0] - boardSize / 2) + Math.abs(node2[1] - boardSize / 2)) - (Math.abs(node1[0] - boardSize / 2) + Math.abs(node1[1] - boardSize / 2)));
    }
}
