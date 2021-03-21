package pivot;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PivotNode<LabelType, ValueType extends Number> {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LabelType label;
    private ValueType value;
    private final Set<PivotNode<LabelType, ValueType>> children = new HashSet<>();

    public PivotNode(){}

    public PivotNode(LabelType label){
        this.label = label;
    }

    public PivotNode(LabelType label, ValueType value) {
        this.label = label;
        this.value = value;
    }

    public void addChild(PivotNode<LabelType, ValueType> newChild){
        logger.log(Level.FINEST,"Calling addChild for node: " + newChild.getLabel());
        this.getChildren().add(newChild);
    }

    public PivotNode<LabelType, ValueType> getChildFromLabel(LabelType theLabel){
        for(PivotNode<LabelType, ValueType> child : children){
            if(child.getLabel().equals(theLabel)){
                return child;
            }
        }
        return null;
    }

    public LabelType getLabel() {
        return label;
    }

    public ValueType getValue() {
        return value;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public Set<PivotNode<LabelType, ValueType>> getChildren() {
        return children;
    }

    public void setValue(ValueType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "\"label\":\"" + label + '\"' +
                ", \"value\":" + value +
                ", \"children\":" + children +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PivotNode<?, ?> pivotNode = (PivotNode<?, ?>) o;
        return label.equals(pivotNode.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
