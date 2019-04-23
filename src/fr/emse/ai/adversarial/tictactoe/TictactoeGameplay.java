package fr.emse.ai.adversarial.tictactoe;

import com.sun.javafx.image.IntPixelGetter;
import fr.emse.ai.adversarial.AlphaBetaSearch;
import fr.emse.ai.adversarial.Game;
import fr.emse.ai.adversarial.IterativeDeepeningAlphaBetaSearch;
import fr.emse.ai.adversarial.MinimaxSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TictactoeGameplay {

    /**
     * fonction qui permet de lancer un jeu de Tictactoe
     * @param args
     */
    public static void main(String[] args) {
        TictactoeGame game = new TictactoeGame();
        MinimaxSearch<TictactoeState, ArrayList<Integer>, Integer> minimaxSearch = MinimaxSearch.createFor(game);
        AlphaBetaSearch<TictactoeState, ArrayList<Integer>, Integer> alphabetaSearch = AlphaBetaSearch.createFor(game);
        IterativeDeepeningAlphaBetaSearch<TictactoeState, ArrayList<Integer>, Integer> iterativeDeepeningAlphaBetaSearch = IterativeDeepeningAlphaBetaSearch.createFor(game, -10, 10, 10);
        TictactoeState state = game.getInitialState();

        // on continue tant que la grille n'est pas dans un état terminal (victoire ou match nul)
        while (!game.isTerminal(state)) {
            System.out.println("======================");
            state.display();
            // on initialise une action, qui est volontairement impossible
            ArrayList<Integer> action = new ArrayList<>(2);
            action.add(-1);
            action.add(-1);

            // tour du joueur humain
            if (state.player == 0) {
                //human
                List<ArrayList<Integer>> actions = game.getActions(state);
                Scanner in = new Scanner(System.in);
                while (!isIn(actions,action)) {
                    System.out.println("Human player, what is your line?");
                    action.set(0,in.nextInt());
                    System.out.println("Human player, what is your column?");
                    action.set(1,in.nextInt());
                }
            } else {
                //tour de la machine
                System.out.println("Machine player, what is your action?");
                //action = minimaxSearch.makeDecision(state);
                System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
                // action = alphabetaSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta : " + alphabetaSearch.getMetrics());
                action = iterativeDeepeningAlphaBetaSearch.makeDecision(state);
                System.out.println("Metrics for IDAlphaBetaSearch : " + iterativeDeepeningAlphaBetaSearch.getMetrics());
            }
            System.out.println("Chosen action is " + action);
            state = game.getResult(state, action);
            state.display();
        }

        // on est sur un état terminal, on affiche donc le résultat
        System.out.print("GAME OVER: ");
        boolean nullmatch = game.isNull(state);
        if (nullmatch){
            System.out.println("Null match");
        }
        if (state.player == 0 && nullmatch == false)
            System.out.println("Machine wins!");
        else if(state.player == 1 && nullmatch == false)
            System.out.println("Human wins!");
    }

    /**
     * fonction qui permet de savoir si un doublet est coutenu dans une liste de doublet
     * @param actions
     * @param action
     * @return true si actions contient action, false sinon
     */
    public static boolean isIn(List<ArrayList<Integer>> actions,ArrayList<Integer> action) {
        for (int i =0;i < actions.size();i++) {
            if (actions.get(i).get(0) == action.get(0) && actions.get(i).get(1) == action.get(1)) {
                return true;
            }
        }
        return false;
    }
}
