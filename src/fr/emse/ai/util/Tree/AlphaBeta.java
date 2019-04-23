package fr.emse.ai.util.Tree;

import fr.emse.ai.util.SimpleTwoPlyGameTree;

public class AlphaBeta {

    public Double getMinValue(SimpleTwoPlyGameTree<Integer> tree, Double alpha, Double beta){
        if(tree.getChildren().isEmpty()){
            double i=tree.getValue();
            return i;
        }
        double value = Double.POSITIVE_INFINITY;
        for(SimpleTwoPlyGameTree<Integer> child : tree.getChildren()){
            value = Math.min(value,getMaxValue(child,alpha,beta));
            if(value <= alpha){
                return value;
            }
            beta = Math.min(beta,value);
        }
        return value;

    }

    public Double getMaxValue(SimpleTwoPlyGameTree<Integer> tree, Double alpha , Double beta){
        if(tree.getChildren().isEmpty()){
            double i=tree.getValue();
            return i;
        }
        double value = Double.NEGATIVE_INFINITY;
        for(SimpleTwoPlyGameTree<Integer> child : tree.getChildren()){
            value = Math.max(value,getMinValue(child,alpha,beta));
            if(value >= beta){
                return value;
            }
            alpha = Math.max(alpha, value);
        }

        return value;
    }
}
