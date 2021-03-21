package pivot;

import exception.LabelNotFoundException;

import java.util.List;

public class PivotTree<LabelType, ValueType extends Number> implements QueryableTree<LabelType, ValueType> {

    private final PivotNode<LabelType, ValueType> root;

    public PivotTree() {
        this.root = new PivotNode<>();
    }

    @Override
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

    @Override
    public ValueType getTotal(){
        return root.getValue();
    }

    @Override
    public String toString() {
        return "{\"root\":" + root + "}";
    }

    public PivotNode<LabelType,ValueType> getRoot() {
        return root;
    }
}
