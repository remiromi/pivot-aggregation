package pivot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PivotTreeBuilder<LabelType, ValueType extends Number> {

    private Function<List<ValueType>, ValueType> aggregationFunction;
    private PivotTree<LabelType, ValueType> tree;
    private Map<List<LabelType>, List<ValueType>> leafValues;

    public PivotTree<LabelType, ValueType> build(List<PivotRow<LabelType, ValueType>> rows,
                                                 Function<List<ValueType>, ValueType> aggregationFunction) {
        this.tree = new PivotTree<>();
        this.aggregationFunction = aggregationFunction;
        this.leafValues = new HashMap<>();
        rows.forEach(this::addBranch);
        fillTreeValues();
        return tree;
    }

    public PivotTree<LabelType, ValueType> build(List<PivotRow<LabelType, ValueType>> rows,
                                                 Function<List<ValueType>, ValueType> aggregationFunction,
                                                 List<Integer> aggregationOrder) {
        List<PivotRow<LabelType, ValueType>> orderedRows = orderRows(rows, aggregationOrder);
        return build(orderedRows, aggregationFunction);
    }

    private List<PivotRow<LabelType,ValueType>> orderRows(List<PivotRow<LabelType,ValueType>> rows,
                                                          List<Integer> aggregationOrder) {
        List<PivotRow<LabelType, ValueType>> orderedRows = new ArrayList<>();

        for (PivotRow<LabelType,ValueType> row : rows)
            orderedRows.add(new PivotRow<>(getOrderedRow(aggregationOrder, row.getLabels()), row.getValue()));

        return orderedRows;
    }

    private List<LabelType> getOrderedRow(List<Integer> aggregationOrder, List<LabelType> unorderedLabels) {
        List<LabelType> orderedLabels = new ArrayList<>();
        for(int index : aggregationOrder){
            orderedLabels.add(unorderedLabels.get(index));
        }
        return orderedLabels;
    }

    private void addBranch(PivotRow<LabelType, ValueType> theRow) {

        PivotNode<LabelType, ValueType> previousNode = tree.getRoot();
        List<LabelType> labels = theRow.getLabels();
        ValueType value = theRow.getValue();

        for(int index = 0; index < labels.size()-1; index++){
            LabelType currentLabel = labels.get(index);
            PivotNode<LabelType, ValueType> newChild = new PivotNode<>(currentLabel);
            previousNode.addChild(newChild);
            previousNode = previousNode.getChildFromLabel(currentLabel);
        }
        addLeafNode(labels, value, previousNode);
    }

    private void addLeafNode(List<LabelType> labels, ValueType value, PivotNode<LabelType, ValueType> parentNode) {
        LabelType leafLabel = labels.get(labels.size()-1);
        PivotNode<LabelType, ValueType> leafChild = parentNode.getChildFromLabel(leafLabel);
        updateLeafValues(labels, value);
        if(leafChild != null) {
            leafChild.setValue(aggregationFunction.apply(leafValues.get(labels)));
        }
        else {
            parentNode.addChild(new PivotNode<>(leafLabel, value));
        }
    }

    private void updateLeafValues(List<LabelType> labels, ValueType value) {
        List<ValueType> values = leafValues.getOrDefault(labels, new ArrayList<>());
        values.add(value);
        leafValues.put(labels, values);
    }

    private void fillTreeValues(){
        fillTreeValues(tree.getRoot());
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
        ValueType currentNodeValue = aggregationFunction.apply(subTreeValues);
        node.setValue(currentNodeValue);
        return currentNodeValue;
    }

}
