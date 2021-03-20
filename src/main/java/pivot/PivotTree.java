package pivot;

import exception.LabelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PivotTree<LabelType>{

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final PivotNode<LabelType> root;
    private final Function<List<Integer>, Integer> aggregationFunction;

    public PivotTree(Function<List<Integer>, Integer> aggregationFunction) {
        this.root = new PivotNode<>();
        this.aggregationFunction = aggregationFunction;
    }

    public void addRow(List<LabelType> labels, int value){
        PivotNode<LabelType> previousNode = this.root;

        for(int index = 0; index < labels.size()-1; index++){
            LabelType currentLabel = labels.get(index);
            PivotNode<LabelType> newChild = new PivotNode<>(currentLabel);
            previousNode.addChild(newChild);
            previousNode = previousNode.getChildFromLabel(currentLabel);
        }
        // TODO multiple values in single row?
        addLeafNode(labels, value, previousNode);
    }

    private void addLeafNode(List<LabelType> labels, int value, PivotNode<LabelType> parentNode) {
        LabelType leafLabel = labels.get(labels.size()-1);
        PivotNode<LabelType> leafChild = parentNode.getChildFromLabel(leafLabel);
        if(leafChild != null) {
            leafChild.setValue(aggregationFunction.apply(List.of(leafChild.getValue(), value)));
        }
        else {
            parentNode.addChild(new PivotNode<>(leafLabel, value));
        }
    }

    public void fillTreeValues(){
        fillTreeValues(root);
    }

    private int fillTreeValues(PivotNode<LabelType> node){

        List<Integer> subTreeValues = new ArrayList<>();

        for(PivotNode<LabelType> child : node.getChildren()){
            if(child.isLeaf()) {
                subTreeValues.add(child.getValue());
            } else {
                subTreeValues.add(fillTreeValues(child));
            }
        }

        logger.log(Level.FINEST,"Visiting " + node.getLabel());
        int currentNodeValue = aggregationFunction.apply(subTreeValues);
        node.setValue(currentNodeValue);
        return currentNodeValue;
    }

    public int findValue(List<LabelType> labels) throws LabelNotFoundException {
        if(labels == null || labels.isEmpty()) {
            throw new LabelNotFoundException("Labels is null or empty.");
        }

        PivotNode<LabelType> currentNode = root;

        for(LabelType label : labels){
            PivotNode<LabelType> child = currentNode.getChildFromLabel(label);
            if(child == null){
                throw new LabelNotFoundException("Label with name:'" + label + "' not found.");
            }
            currentNode = child;
        }
        return currentNode.getValue();
    }

    public int getTotal(){
        // TODO use this in case query row is empty -> Add also in README.md
        return root.getValue();
    }

    @Override
    public String toString() {
        return "{\"root\":" + root + "}";
    }

        // consume Row -> Add node to tree starting from root?
        // https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html

}
