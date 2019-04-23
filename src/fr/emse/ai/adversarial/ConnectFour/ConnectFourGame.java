package fr.emse.ai.adversarial.ConnectFour;

import fr.emse.ai.adversarial.Game;
import fr.emse.ai.adversarial.tictactoe.TictactoeState;

import java.util.ArrayList;
import java.util.List;


public class ConnectFourGame implements Game<ConnectFourState,Integer,Integer> {

    public final static Integer[] players = {0,1};
    public static ConnectFourState initialstate;

    public ConnectFourGame(){
        ArrayList<ArrayList<Integer>> state = new ArrayList<>();
        ArrayList<Integer> line = new ArrayList<>();
        for(int i =0 ; i<7 ; i++){
            line.add(0);
        }
        for(int j=0 ; j<6 ; j++){
            state.add(line);
        }
        this.initialstate = new ConnectFourState(state,1);
    }

    @Override
    public ConnectFourState getInitialState(){return initialstate;}

    @Override
    public Integer[] getPlayers(){return players;}

    @Override
    public Integer getPlayer(ConnectFourState state){return state.getPlayer();}

    @Override
    public List<Integer> getActions(ConnectFourState state){return state.getAvailableActions();}

    @Override
    public ConnectFourState getResult(ConnectFourState state , Integer action){
        return state.getResult(action);
    }

    /**
     * fonction qui permet de savoir si un état est terminal, c'est à dire qu'un des deux joueur à gagné ou que le match est nul
     * @param state
     * @return true si l'état est final, false sinon
     */
    @Override
    public boolean isTerminal(ConnectFourState state){
        boolean winByLine = false;
        boolean winByColumn = false;
        boolean winByDiag = false;
        boolean winByAntiDiag = false;

        for(int i = 5 ; i>-1 ; i--) {
            for (int j = 0; j < 4; j++) {
                winByLine = winByLine || ((state.state.get(i).get(j) == state.state.get(i).get(j + 1)) && (state.state.get(i).get(j) == state.state.get(i).get(j + 2)) && (state.state.get(i).get(j) == state.state.get(i).get(j + 3)) && state.state.get(i).get(j) != 0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 7; j++) {
                winByColumn = winByColumn|| ((state.state.get(i).get(j) == state.state.get(i-1).get(j)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j))  && state.state.get(i).get(j)!=0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 4; j++) {
                winByDiag = winByDiag|| ((state.state.get(i).get(j) == state.state.get(i-1).get(j+1)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j+2)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j+3))  && state.state.get(i).get(j)!=0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 3; j < 7; j++) {
                winByAntiDiag = winByAntiDiag || ((state.state.get(i).get(j) == state.state.get(i-1).get(j-1)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j-2)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j-3))  && state.state.get(i).get(j)!=0);
            }
        }

        if(winByLine || winByColumn || winByDiag || winByAntiDiag){
            return true;
        }
        for(int i=0; i<6 ; i++){
            for(int j=0 ; j<7 ; j++ ){
                if(state.state.get(i).get(j) == 0){
                    return false;
                }

            }
        }
        return true;

    }

    /**
     * fonction qui permet de connaître l'utilité d'un état, elle n'est utilisée que pas des états terminaux
     * @param state
     * @param player
     * @return l'utilité d'un état
     */
    @Override
    public double getUtility(ConnectFourState state, Integer player){
        int utility =0;


        for(int i = 5 ; i>-1 ; i--) {
            for (int j = 0; j < 4; j++) {
                // lignes machine
                if (state.state.get(i).get(j) != 1 && state.state.get(i).get(j + 1) != 1 && state.state.get(i).get(j + 2) != 1 && state.state.get(i).get(j + 3) != 1) {
                    int somme = state.state.get(i).get(j) + state.state.get(i).get(j + 1) + state.state.get(i).get(j + 2) + state.state.get(i).get(j + 3);
                    if (somme == -1) {
                        utility += 1;
                    } else if (somme == -2) {
                        utility += 10;
                    } else if (somme == -3) {
                        utility += 100;
                    } else if (somme == 4) {
                        utility += 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 7; j++) {
                // colonnes
                if (state.state.get(i).get(j) != 1 && state.state.get(i-1).get(j)!= 1 && state.state.get(i-2).get(j) != 1 && state.state.get(i-3).get(j) != 1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j) + state.state.get(i-2).get(j) + state.state.get(i-3).get(j);
                    if(somme == -1){
                        utility += 1;
                    }
                    else if(somme == -2){
                        utility += 10;
                    }
                    else if(somme == -3){
                        utility += 100;
                    }
                    else if(somme == 4){
                        utility += 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 4; j++) {
                // diagonales
                if (state.state.get(i).get(j) != 1 && state.state.get(i-1).get(j+1)!= 1 && state.state.get(i-2).get(j+2) != 1 && state.state.get(i-3).get(j+3) != 1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j+1) + state.state.get(i-2).get(j+2) + state.state.get(i-3).get(j+3);
                    if(somme == -1){
                        utility += 1;
                    }
                    else if(somme == -2){
                        utility += 10;
                    }
                    else if(somme == -3){
                        utility += 100;
                    }
                    else if(somme == 4){
                        utility += 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 3; j < 7; j++) {
                // antidiagonales
                if (state.state.get(i).get(j) != 1 && state.state.get(i-1).get(j-1)!= 1 && state.state.get(i-2).get(j-2) != 1 && state.state.get(i-3).get(j-3) != 1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j-1) + state.state.get(i-2).get(j-2) + state.state.get(i-3).get(j-3);
                    if(somme == -1){
                        utility += 1;
                    }
                    else if(somme == -2){
                        utility += 10;
                    }
                    else if(somme == -3){
                        utility += 100;
                    }
                    else if(somme == 4){
                        utility += 1000;
                    }
                }
            }
        }




        for(int i = 5 ; i>-1 ; i--) {
            for (int j = 0; j < 4; j++) {
                // lignes humain
                if (state.state.get(i).get(j) != -1 && state.state.get(i).get(j+1)!= -1 && state.state.get(i).get(j+2) != -1 && state.state.get(i).get(j+3) != -1){
                    int somme = state.state.get(i).get(j) + state.state.get(i).get(j+1) + state.state.get(i).get(j+2) + state.state.get(i).get(j+3);
                    if(somme == 1){
                        utility -= 1;
                    }
                    else if(somme == 2){
                        utility -= 10;
                    }
                    else if(somme == 3){
                        utility -= 100;
                    }
                    else if(somme == 4){
                        utility -= 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 7; j++) {
                // colonnes
                if (state.state.get(i).get(j) != -1 && state.state.get(i-1).get(j)!= -1 && state.state.get(i-2).get(j) != -1 && state.state.get(i-3).get(j) != -1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j) + state.state.get(i-2).get(j) + state.state.get(i-3).get(j);
                    if(somme == 1){
                        utility -= 1;
                    }
                    else if(somme == 2){
                        utility -= 10;
                    }
                    else if(somme == 3){
                        utility -= 100;
                    }
                    else if(somme == 4){
                        utility -= 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 4; j++) {
                // diagonales
                if (state.state.get(i).get(j) != -1 && state.state.get(i-1).get(j+1)!= -1 && state.state.get(i-2).get(j+2) != -1 && state.state.get(i-3).get(j+3) != -1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j+1) + state.state.get(i-2).get(j+2) + state.state.get(i-3).get(j+3);
                    if(somme == 1){
                        utility -= 1;
                    }
                    else if(somme == 2){
                        utility -= 10;
                    }
                    else if(somme == 3){
                        utility -= 100;
                    }
                    else if(somme == 4){
                        utility -= 1000;
                    }
                }
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 3; j < 7; j++) {
                // antidiagonales
                if (state.state.get(i).get(j) != -1 && state.state.get(i-1).get(j-1)!= -1 && state.state.get(i-2).get(j-2) != -1 && state.state.get(i-3).get(j-3) != -1){
                    int somme = state.state.get(i).get(j) + state.state.get(i-1).get(j-1) + state.state.get(i-2).get(j-2) + state.state.get(i-3).get(j-3);
                    if(somme == 1){
                        utility -= 1;
                    }
                    else if(somme == 2){
                        utility -= 10;
                    }
                    else if(somme == 3){
                        utility -= 100;
                    }
                    else if(somme == 4){
                        utility -= 1000;
                    }
                }
            }
        }

        boolean winByLine = false;
        boolean winByColumn = false;
        boolean winByDiag = false;
        boolean winByAntiDiag = false;

        for(int i = 5 ; i>-1 ; i--) {
            for (int j = 0; j < 4; j++) {
                winByLine = winByLine || ((state.state.get(i).get(j) == state.state.get(i).get(j + 1)) && (state.state.get(i).get(j) == state.state.get(i).get(j + 2)) && (state.state.get(i).get(j) == state.state.get(i).get(j + 3)) && state.state.get(i).get(j) != 0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 7; j++) {
                winByColumn = winByColumn|| ((state.state.get(i).get(j) == state.state.get(i-1).get(j)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j))  && state.state.get(i).get(j)!=0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 0; j < 4; j++) {
                winByDiag = winByDiag|| ((state.state.get(i).get(j) == state.state.get(i-1).get(j+1)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j+2)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j+3))  && state.state.get(i).get(j)!=0);
            }
        }
        for(int i = 5 ; i>2 ; i--) {
            for (int j = 3; j < 7; j++) {
                winByAntiDiag = winByAntiDiag || ((state.state.get(i).get(j) == state.state.get(i-1).get(j-1)) && (state.state.get(i).get(j) == state.state.get(i-2).get(j-2)) && (state.state.get(i).get(j) == state.state.get(i-3).get(j-3))  && state.state.get(i).get(j)!=0);
            }
        }

        if(winByLine || winByColumn || winByDiag || winByAntiDiag){
            return utility;
        }
        for(int i=0; i<6 ; i++){
            for(int j=0 ; j<7 ; j++ ){
                if(state.state.get(i).get(j) == 0){
                    return 0;
                }

            }
        }
        return 0;
    }

}
