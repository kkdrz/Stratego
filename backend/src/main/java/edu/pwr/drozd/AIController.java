package edu.pwr.drozd;

import edu.pwr.drozd.algorithm.AlphaBetaAlgorithm;
import edu.pwr.drozd.algorithm.MinMaxAlgorithm;
import edu.pwr.drozd.algorithm.StrategoAIAlgorithm;
import edu.pwr.drozd.cell_selector.BorderSelector;
import edu.pwr.drozd.cell_selector.CellSelector;
import edu.pwr.drozd.cell_selector.CentralSelector;
import edu.pwr.drozd.evaluator.BoardStateEvaluator;
import edu.pwr.drozd.evaluator.ClusterOfSymbolsEvaluator;
import edu.pwr.drozd.evaluator.SymbolsInCompletedLinesEvaluator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AIController {

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public GameState hello() {
        GameState gs = new GameState();
        gs.setAlgorithm("minmax");
        gs.setBoard(new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}});
        gs.setCellSelector("central");
        gs.setCurrentPlayer(1);
        gs.setOpponentPlayer(2);
        gs.setDepth(3);
        gs.setStateEvaluator("symbols_in_completed_lines");

        return gs;
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/nextMove", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<int[]> nextMove(@RequestBody GameState gameState) {
        StrategoAIAlgorithm ai = getAlgorithm(gameState);
        CellSelector cellSelector = getCellSelector(gameState);
        BoardStateEvaluator stateEvaluator = getBoardStateEvaluator(gameState);

        if (ai != null && cellSelector != null & stateEvaluator != null) {
            return new ResponseEntity<>(ai.nextMove(gameState.getBoard(), gameState.getCurrentPlayer(), gameState.getOpponentPlayer(), gameState.getDepth(), cellSelector, stateEvaluator), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private BoardStateEvaluator getBoardStateEvaluator(@RequestBody GameState gameState) {
        BoardStateEvaluator stateEvaluator = null;
        if ("Symbols in completed lines".equals(gameState.getStateEvaluator())) {
            stateEvaluator = new SymbolsInCompletedLinesEvaluator();
        } else if ("Cluster of symbols".equals(gameState.getStateEvaluator())) {
            stateEvaluator = new ClusterOfSymbolsEvaluator();
        }
        return stateEvaluator;
    }

    private CellSelector getCellSelector(@RequestBody GameState gameState) {
        CellSelector cellSelector = null;
        if ("Central".equals(gameState.getCellSelector())) {
            cellSelector = new CentralSelector();
        } else if ("Border".equals(gameState.getCellSelector())) {
            cellSelector = new BorderSelector();
        }
        return cellSelector;
    }

    private StrategoAIAlgorithm getAlgorithm(@RequestBody GameState gameState) {
        StrategoAIAlgorithm ai = null;
        if ("MinMax".equals(gameState.getAlgorithm())) {
            ai = new MinMaxAlgorithm();
        } else if ("AlphaBeta".equals(gameState.getAlgorithm())) {
            ai = new AlphaBetaAlgorithm();
        }
        return ai;
    }
}
