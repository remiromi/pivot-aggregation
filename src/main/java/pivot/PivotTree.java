package pivot;

import exception.LabelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PivotTree<LabelType, ValueType extends Number>{

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final PivotNode<LabelType, ValueType> root;
    private final Function<List<ValueType>, ValueType> aggregationFunction;

    public PivotTree(Function<List<ValueType>, ValueType> aggregationFunction) {
        this.root = new PivotNode<>();
        this.aggregationFunction = aggregationFunction;
    }

    public void addRow(List<LabelType> labels, ValueType value){
        PivotNode<LabelType, ValueType> previousNode = this.root;

        for(int index = 0; index < labels.size()-1; index++){
            LabelType currentLabel = labels.get(index);
            PivotNode<LabelType, ValueType> newChild = new PivotNode<>(currentLabel);
            previousNode.addChild(newChild);
            previousNode = previousNode.getChildFromLabel(currentLabel);
        }
        // TODO multiple values in single row?
        addLeafNode(labels, value, previousNode);
    }

    private void addLeafNode(List<LabelType> labels, ValueType value, PivotNode<LabelType, ValueType> parentNode) {
        LabelType leafLabel = labels.get(labels.size()-1);
        PivotNode<LabelType, ValueType> leafChild = parentNode.getChildFromLabel(leafLabel);
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

    private ValueType fillTreeValues(PivotNode<LabelType, ValueType> node){

        List<ValueType> subTreeValues = new ArrayList<>();

        for(PivotNode<LabelType, ValueType> child : node.getChildren()){
            if(child.isLeaf()) {
                subTreeValues.add(child.getValue());
            } else {
                subTreeValues.add(fillTreeValues(child));
            }
        }

        logger.log(Level.FINEST,"Visiting " + node.getLabel());
        ValueType currentNodeValue = aggregationFunction.apply(subTreeValues);
        node.setValue(currentNodeValue);
        return currentNodeValue;
    }

    public ValueType findValue(List<LabelType> labels) throws LabelNotFoundException {
        if(labels == null || labels.isEmpty()) {
            throw new LabelNotFoundException("Labels is null or empty.");
        }

        PivotNode<LabelType, ValueType> currentNode = root;

        for(LabelType label : labels){
            PivotNode<LabelType, ValueType> child = currentNode.getChildFromLabel(label);
            if(child == null){
                throw new LabelNotFoundException("Label with name:'" + label + "' not found.");
            }
            currentNode = child;
        }
        return currentNode.getValue();
    }

    public ValueType getTotal(){
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
