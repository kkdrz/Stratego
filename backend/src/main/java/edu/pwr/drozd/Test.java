import edu.pwr.drozd.algorithm.MinMaxAlgorithm;

public class Test {

    public static void main(String[] args) {
        int[][] gameBoard = {
                {1,2,2,0},
                {1,0,0,0},
                {0,0,0,0},
                {0,0,0,0}
        };
        MinMaxAlgorithm AI = new MinMaxAlgorithm();

        int [] result = AI.nextMove(gameBoard, true, 2, 1, 3);

        System.out.println("Row: " + result[1]);
        System.out.println("Column: " + result[2]);
        System.out.println("BestScore: " + result[0]);
        System.out.println("\n");
    }
}
