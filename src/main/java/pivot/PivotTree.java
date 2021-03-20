package pivot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PivotTree<LabelType>{

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final PivotNode<LabelType> root;

    public PivotTree() {
        logger.log(Level.INFO, "Building Tree");
        root = new PivotNode<>((LabelType) "Root");
    }

    public void addRow(List<LabelType> labels, int value){
        PivotNode<LabelType> previousNode = this.root;

        for(LabelType label : labels){
            PivotNode<LabelType> newChild = new PivotNode<>(label);
            previousNode.addChild(newChild);
            previousNode = previousNode.getChildFromLabel(label);
        }
        //TODO change SUM to general AGGREGATION FUNC
        previousNode.setValue(previousNode.getValue() + value);
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
        int currentNodeValue = aggregation(subTreeValues);
        node.setValue(currentNodeValue);
        return currentNodeValue;
    }

    public int findValue(List<LabelType> labels) throws Exception {
        if(labels == null || labels.isEmpty()) {
            // TODO Create EXCEPTION
            throw new Exception("Labels is null or empty");
        }

        PivotNode<LabelType> currentNode = root;

        for(LabelType label : labels){
            PivotNode<LabelType> child = currentNode.getChildFromLabel(label);
            if(child == null){
                /* TODO if child is null trigger exc -> New Exception Class or descriptive message? */
                throw new Exception("Node has no Child with label " + label + ". Insert another value.");
            }
            currentNode = child;
        }
        return currentNode.getValue();
    }

    private int aggregation(List<Integer> values) {
        return values.stream().reduce(0, Integer::sum);
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
