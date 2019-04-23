package fr.emse.ai.adversarial.tictactoe;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class TictactoeState {

    // représentation de l'état sous forme de matrice
    public ArrayList<ArrayList<Integer>> state = new ArrayList<>();
    public int player;

    public TictactoeState(ArrayList<ArrayList<Integer>> state, int player){
        this.state = state;
        this.player = player;
    }

    public int getPlayer(){return player;}


    /**
     * fonction qui permet de déterminer les actions possibles à partir de l'état
     *
     * @return une liste contenant toutes les actions possibles sous forme de doublet (ligne,colonne)
     */
    public List<ArrayList<Integer>> getAvailableActions(){
        // on stocke les actions possible dans une liste
        ArrayList<ArrayList<Integer>> actions = new ArrayList<>();

        // on teste toutes les cases, si elles sont libres, alors on les ajoute à la liste d'actions possibles
        for(int i=0 ; i <3 ; i++){
            for(int j=0 ; j<3 ; j++){
                if(state.get(i).get(j) == 0){
                    ArrayList<Integer> action = new ArrayList<Integer>();
                    action.add(i);
                    action.add(j);
                    actions.add(action);
                }
            }
        }

        return actions;
    }

    /**
     * fonction permettant, à partir de létat et d'une action donnée, de déterminer l'état d'arrivée après l'action
     * @param action
     * @return un TictactoeState, nouvel état après l'action, en tenant compte du changement de joueur
     */
    public TictactoeState getResult(ArrayList<Integer> action){
        int i = action.get(0);
        int j = action.get(1);
        // pour éviter les problèmes, on créé une nouvelle ligne, copie (mais pas liée) de l'ancienne, sur laquelle l'action se passe
        ArrayList<Integer> newLigne = new ArrayList<>(this.state.get(i));

        // suivant le player, on positionne un 1 ou -1 sur la ligne
        if(this.player == 0){
            newLigne.set(j,1);
        }
        else{
            newLigne.set(j,-1);
        }

        // pour éviter les problèmes, on créé une nouvelle matrice newState, qui est une copie (mais pas liée) à la matrice state
        ArrayList<ArrayList<Integer>> newState = new ArrayList<>(this.state);
        for(int k=0 ; k<3 ; k++){
            // on copie la nouvelle ligne à la place de l'ancienne
            if(k==i){
                newState.set(k,newLigne);
            }

        }
        // pour éviter les problèmes, on créé un TictactoeState, copie (mais pas liée) de l'ancien
        TictactoeState newtictactoestate = new TictactoeState(newState , 1 - this.player);
        return newtictactoestate;

    }

    /**
     * fonction qui permet de réaliser l'affichage du plateau de jeu
     */
    public void display() {
        String ligne;
        String horizontal = "--------------";
        for (int i = 0; i < 3; i++) {
            ligne = "";
            for (int k = 0; k < 3; k++) {
                if (this.state.get(i).get(k) == -1) {
                    ligne += "| X ";
                }
                else if(this.state.get(i).get(k) == 0) {
                    ligne += "|   ";
                }
                else{
                    ligne += "| O ";
                }

            }
            ligne += " |";
            System.out.println(ligne);
            System.out.println(horizontal);
        }
    }
}
