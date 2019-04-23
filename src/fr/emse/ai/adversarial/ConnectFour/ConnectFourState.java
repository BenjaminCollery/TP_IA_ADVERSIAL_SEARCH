package fr.emse.ai.adversarial.ConnectFour;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourState {

    public ArrayList<ArrayList<Integer>> state;
    public int player;

    public ConnectFourState(ArrayList<ArrayList<Integer>> state , int player){
        this.state = state;
        this.player = player;
    }

    public int getPlayer(){return player;}

    /**
     * fonction qui permet de déterminer les actions possibles à partir de l'état
     *
     * @return une liste contenant toutes les actions possibles sous forme de doublet (ligne,colonne)
     */
    public List<Integer> getAvailableActions(){
        ArrayList<Integer> actions = new ArrayList<>();
        for(int j = 0 ; j<7 ; j++){
            if(state.get(0).get(j) == 0) {
                actions.add(j);
            }
        }
        return actions;
    }

    /**
     * fonction permettant, à partir de létat et d'une action donnée, de déterminer l'état d'arrivée après l'action
     * @param action
     * @return un TictactoeState, nouvel état après l'action, en tenant compte du changement de joueur
     */
    public ConnectFourState getResult(Integer action){
        boolean findZero = false;
        int i = 5;
        ArrayList<ArrayList<Integer>> newState = new ArrayList<>(this.state);

        while(i>=0 && !findZero){
            if(state.get(i).get(action) == 0){
                ArrayList<Integer> newLine = new ArrayList<>(this.state.get(i));
                if(this.player == 0){
                    newLine.set(action,1);
                }
                else{
                    newLine.set(action,-1);
                }
                findZero = true;
                for(int k=0; k<6; k++){
                    if(k==i){
                        newState.set(k,newLine);
                    }
                }
            }

            i -=1;
        }
        ConnectFourState newconnectfourstate = new ConnectFourState(newState, 1- player);
        return newconnectfourstate;
    }

    /**
     * fonction qui permet de réaliser l'affichage du plateau de jeu
     */
    public void display() {
        String ligne;
        String horizontal = "-------------------------------";
        for (int i = 0; i < 6; i++) {
            ligne = "";
            for (int k = 0; k < 7; k++) {
                if (this.state.get(i).get(k) == -1) {
                    ligne += "| \033[5;31m\u25cf\033[0m ";
                }
                else if(this.state.get(i).get(k) == 1) {
                    ligne += "| \033[1;33m\u25cf\033[0m ";
                }
                else{
                    ligne += "|   ";
                }

            }
            ligne += "|";
            System.out.println(ligne);
            System.out.println(horizontal);
        }
    }
}
