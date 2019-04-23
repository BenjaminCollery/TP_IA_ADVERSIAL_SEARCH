package fr.emse.ai.adversarial.ConnectFour;

import fr.emse.ai.adversarial.AlphaBetaSearch;
import fr.emse.ai.adversarial.IterativeDeepeningAlphaBetaSearch;
import fr.emse.ai.adversarial.MinimaxSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectFourGamePlay {


    /**
     * fonction qui permet de lancer un jeu de puissance 4
     * @param args
     */
    public static void main(String[] args) {
        ConnectFourGame game = new ConnectFourGame();
        MinimaxSearch<ConnectFourState, Integer, Integer> minimaxSearch = MinimaxSearch.createFor(game);
        AlphaBetaSearch<ConnectFourState, Integer, Integer> alphabetaSearch = AlphaBetaSearch.createFor(game);
        IterativeDeepeningAlphaBetaSearch<ConnectFourState,Integer, Integer> iterativeDeepeningAlphaBetaSearch = IterativeDeepeningAlphaBetaSearch.createFor(game, -1, 1, 10);
        ConnectFourState state = game.getInitialState();
        while (!game.isTerminal(state)) {
            System.out.println("======================");
            state.display();
            int action = -1;
            if (state.player == 0) {
                //human
                List<Integer> actions = game.getActions(state);
                Scanner in = new Scanner(System.in);
                while (!actions.contains(action)) {
                    System.out.println("Human player, what is your column?");
                    action= in.nextInt();
                }
            } else {
                //machine
                System.out.println("Machine player, what is your action?");
                // action = minimaxSearch.makeDecision(state);
                System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
                // alphabetaSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta : " + alphabetaSearch.getMetrics());
                action = iterativeDeepeningAlphaBetaSearch.makeDecision(state);
                System.out.println("Metrics for IDAlphaBetaSearch : " + iterativeDeepeningAlphaBetaSearch.getMetrics());
            }
            System.out.println("Chosen action is " + action);
            state = game.getResult(state, action);
            state.display();
        }
        System.out.print("GAME OVER: ");
        if (state.player == 0)
            System.out.println("Machine wins!");
        else
            System.out.println("Human wins!");
    }
}
