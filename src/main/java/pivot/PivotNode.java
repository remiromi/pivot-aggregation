package pivot;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PivotNode<LabelType> {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LabelType label;
    private int value;
    private final Set<PivotNode<LabelType>> children = new HashSet<>();

    public PivotNode(){}

    public PivotNode(LabelType label){
        this.label = label;
    }

    public PivotNode(LabelType label, int value) {
        this.label = label;
        this.value = value;
    }

    public void addChild(PivotNode<LabelType> newChild){
        logger.log(Level.FINEST,"Calling addChild for node: " + newChild.getLabel());
        // TODO Check sum value if already existing
        this.getChildren().add(newChild);
    }

    public PivotNode<LabelType> getChildFromLabel(LabelType theLabel){
        for(PivotNode<LabelType> child : children){
            if(child.getLabel().equals(theLabel)){
                return child;
            }
        }
        return null;
    }

    public LabelType getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public Set<PivotNode<LabelType>> getChildren() {
        return children;
    }

    public void setValue(int value) {
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
        PivotNode<LabelType> pivotNode = (PivotNode<LabelType>) o;
        return label.equals(pivotNode.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
