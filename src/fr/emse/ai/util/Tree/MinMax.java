package fr.emse.ai.util.Tree;

import fr.emse.ai.util.SimpleTwoPlyGameTree;

public class MinMax {

    public Double getMinValue(SimpleTwoPlyGameTree<Integer> tree){
        if(tree.getChildren().isEmpty()){
            double i=tree.getValue();
            return i;
        }
        double value = Double.POSITIVE_INFINITY;
        for(SimpleTwoPlyGameTree<Integer> child : tree.getChildren()){
            value = Math.min(value,getMaxValue(child));
        }
        return value;

    }

    public Double getMaxValue(SimpleTwoPlyGameTree<Integer> tree){
        if(tree.getChildren().isEmpty()){
            double i=tree.getValue();
            return i;
        }
        double value = Double.NEGATIVE_INFINITY;
        for(SimpleTwoPlyGameTree<Integer> child : tree.getChildren()){
            value = Math.max(value,getMinValue(child));
        }
        return value;
    }
}
