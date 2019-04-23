package fr.emse.ai.util.Tree;

import fr.emse.ai.util.SimpleTwoPlyGameTree;

import java.util.ArrayList;

public class TreeMain {

    public static void main(String[] args){

        SimpleTwoPlyGameTree tree1 = new SimpleTwoPlyGameTree(5,true);

        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist1 =
                new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(3,true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(12,true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(8,true));
        SimpleTwoPlyGameTree<Integer> subtree1 =
                new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist1);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist2 =
                new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(2,true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(4,true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(6,true));
        SimpleTwoPlyGameTree<Integer> subtree2 =
                new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist2);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist3 =
                new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(14,true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(5,true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(2,true));
        SimpleTwoPlyGameTree<Integer> subtree3 =
                new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE,false,sublist3);
        tree1.addChild(subtree1);
        tree1.addChild(subtree2);
        tree1.addChild(subtree3);

        MinMax minmax = new MinMax();
        AlphaBeta alphabeta = new AlphaBeta();
        Double alpha = Double.NEGATIVE_INFINITY;
        Double beta = Double.POSITIVE_INFINITY;

        Double max = minmax.getMaxValue(tree1);
        Double minimaxalphabeta = alphabeta.getMaxValue(tree1,alpha,beta);

        System.out.println("La valeur minimax de l'arbre est :");
        System.out.println(max);

        System.out.println("La valeur minimax avec alphabeta de l'arbre est :");
        System.out.println(minimaxalphabeta);

    }
}
