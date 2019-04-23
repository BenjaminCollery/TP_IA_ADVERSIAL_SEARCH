package fr.emse.ai.adversarial.tictactoe;

import fr.emse.ai.adversarial.Game;

import java.util.ArrayList;
import java.util.List;


public class TictactoeGame implements Game<TictactoeState, ArrayList<Integer>, Integer> {

    public final static Integer[] players = {0,1};
    public static TictactoeState initialState;

    public TictactoeGame(){
        ArrayList<ArrayList<Integer>> i = new ArrayList<>();
        ArrayList<Integer> ligne = new ArrayList<>();
        ligne.add(0);
        ligne.add(0);
        ligne.add(0);
        i.add(ligne);
        i.add(ligne);
        i.add(ligne);
        this.initialState = new TictactoeState(i,1);
    }

    @Override
    public TictactoeState getInitialState(){return initialState;}

    @Override
    public Integer[] getPlayers(){return players;}

    @Override
    public Integer getPlayer(TictactoeState state){return state.getPlayer();}

    @Override
    public List<ArrayList<Integer>> getActions(TictactoeState state){return state.getAvailableActions();}

    @Override
    public TictactoeState getResult(TictactoeState state , ArrayList<Integer> action){
        return state.getResult(action);
    }

    /**
     * fonction qui permet de savoir si un état est terminal, c'est à dire qu'un des deux joueur à gagné ou que le match est nul
     * @param state
     * @return true si l'état est final, false sinon
     */
    @Override
    public boolean isTerminal(TictactoeState state){
        // on suppose qu'il n'y a pas de case vide
        boolean isNoZero = true;

        // on vérifie si une ligne ou une colonne est gagnante
        for(int i=0 ; i<3 ;i ++){
            int sumLine = state.state.get(i).get(0) + state.state.get(i).get(1) + state.state.get(i).get(2);
            int sumColumn = state.state.get(0).get(i) + state.state.get(1).get(i) + state.state.get(2).get(i);

            // on regarde si on a trouvé une case vide
            for(int j=0 ; j<3 ; j++){
                isNoZero = isNoZero && (state.state.get(i).get(j)!=0);
            }
            //si on a trouvé une ligne ou colonne gagnante, on retourne true
            if(Math.abs(sumLine) == 3 || Math.abs(sumColumn) == 3){
                return true;
            }
        }
        int sumDiag = state.state.get(0).get(0) + state.state.get(1).get(1) + state.state.get(2).get(2);
        int sumAntiDiag = state.state.get(2).get(0) + state.state.get(1).get(1) + state.state.get(0).get(2);
        // si la diagonale ou l'antidiagonale sont gagnantes ou que tous les coups ont été joués, on retourne true
        if(Math.abs(sumDiag) == 3 || Math.abs(sumAntiDiag) == 3 || isNoZero) {
            return true;
        }

        // sinon c'est que l'état n'est pas final
        return false;
    }

    /**
     * fonction permettant de savoir si on a un match null (sert simplement à afficher "match nul" le cas échéchant, dans le main)
     * @param state
     * @return true si le match est nul, false sinon
     */
    public boolean isNull(TictactoeState state){
        boolean isNoZero = true;

        for(int i=0 ; i<3 ;i ++){
            int sumLine = state.state.get(i).get(0) + state.state.get(i).get(1) + state.state.get(i).get(2);
            int sumColumn = state.state.get(0).get(i) + state.state.get(1).get(i) + state.state.get(2).get(i);
            for(int j=0 ; j<3 ; j++){
                isNoZero= isNoZero && (state.state.get(i).get(j)!=0);
            }
            if(Math.abs(sumLine) == 3 || Math.abs(sumColumn) == 3){
                return false;
            }
        }
        int sumDiag = state.state.get(0).get(0) + state.state.get(1).get(1) + state.state.get(2).get(2);
        int sumAntiDiag = state.state.get(2).get(0) + state.state.get(1).get(1) + state.state.get(0).get(2);
        if(Math.abs(sumDiag) == 3 || Math.abs(sumAntiDiag) == 3 || isNoZero == false) {
            return false;
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
    public double getUtility(TictactoeState state, Integer player){
        int utility = 0;

        // on compte d'abord les ouvertures pour le joueur humain (coups pouvant encore mener à la victoire
        // les lignes avec 1 jetons valent 1
        // les lignes avec 2 jetons valent 3
        // les lignes avec 3 jetons valent 10 (victoire)

        // on compte sur lignes et colonnes
        for (int i =0 ; i<3 ; i++){
            if(state.state.get(i).get(0) != -1 && state.state.get(i).get(1) != -1 && state.state.get(i).get(2) != -1){
                if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == 1){
                    utility += 1;
                }
                else if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == 2){
                    utility += 3;
                }
                else if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == 3){
                    utility += 10;
                }

            }
            if(state.state.get(0).get(i) != -1 && state.state.get(1).get(i) != -1 && state.state.get(2).get(i) != -1){
                if(state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == 1){
                    utility += 1;
                }
                else if(state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == 2){
                    utility += 3;
                }
                else if (state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == 3){
                    utility +=10;
                }

            }
        }
        // on compte les diagonales
        if(state.state.get(0).get(0) != -1 && state.state.get(1).get(1) != -1 && state.state.get(2).get(2) != -1){
            if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == 1){
                utility += 1;
            }
            else if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == 2){
                utility += 3;
            }
            else if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == 3){
                utility +=10;
            }

        }
        if(state.state.get(2).get(0) != -1 && state.state.get(1).get(1) != -1 && state.state.get(0).get(2) != -1){
            if(state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == 1){
                utility += 1;
            }
            else if(state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == 2){
                utility += 3;
            }
            else if(state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == 3){
                utility +=10;
            }

        }


        // puis on comte ceux de la machine
        // les lignes avec 1 jetons valent -1
        // les lignes avec 2 jetons valent -3
        // les lignes avec 3 jetons valent -10 (victoire)

        // on compte sur lignes et colonnes
        for (int i =0 ; i<3 ; i++){
            if(state.state.get(i).get(0) != 1 && state.state.get(i).get(1) != 1 && state.state.get(i).get(2) != 1){
                if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == -1){
                    utility -= 1;
                }
                else if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == -2){
                    utility -= 3;
                }
                else if(state.state.get(i).get(0)+state.state.get(i).get(1)+state.state.get(i).get(2) == -3){
                    utility -=10;
                }

            }
            if(state.state.get(0).get(i) != 1 && state.state.get(1).get(i) != 1 && state.state.get(2).get(i) != 1){
                if(state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == -1){
                    utility -= 1;
                }
                else if(state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == -2){
                    utility -= 3;
                }
                else if(state.state.get(0).get(i)+state.state.get(1).get(i)+state.state.get(2).get(i) == -3){
                    utility -=10;
                }

            }
        }

        // on teste les diagonales
        if(state.state.get(0).get(0) != 1 && state.state.get(1).get(1) != 1 && state.state.get(2).get(2) != 1){
            if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == -1){
                utility -= 1;
            }
            else if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == -2){
                utility -= 3;
            }
            else if(state.state.get(0).get(0)+state.state.get(1).get(1)+state.state.get(2).get(2) == -3){
                utility -=10;
            }

        }
        if(state.state.get(2).get(0) !=1 && state.state.get(1).get(1) != 1 && state.state.get(0).get(2) != 1){
            if(state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == -1){
                utility -= 1;
            }
            else if(state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == -2){
                utility -= 3;
            }
            else if (state.state.get(2).get(0)+state.state.get(1).get(1)+state.state.get(0).get(2) == -3){
                utility -=10;
            }

        }
        // si on a un gagnant, on retourne l'utilité
        for (int i=0 ; i<3 ; i++) {
            int sumLine = state.state.get(i).get(0) + state.state.get(i).get(1) + state.state.get(i).get(2);
            int sumColumn = state.state.get(0).get(i) + state.state.get(1).get(i) + state.state.get(2).get(i);
            boolean isWinner = Math.abs(sumLine) == 3 || Math.abs(sumColumn) == 3;
            if (isWinner) {
                return -utility;
            }
        }

        int sumDiag = state.state.get(0).get(0) + state.state.get(1).get(1) + state.state.get(2).get(2);
        int sumAntiDiag = state.state.get(2).get(0) + state.state.get(1).get(1) + state.state.get(0).get(2);
        boolean isWinner = Math.abs(sumDiag) == 3 || Math.abs(sumAntiDiag) == 3;
        if (isWinner) {
            return -utility;
        }

        // sinon on retourne 0
        return 0;
    }

}
