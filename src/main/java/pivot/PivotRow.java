package pivot;

import java.util.List;

public class PivotRow<LabelType, ValueType extends Number> {
    public List<LabelType> labels;
    public final ValueType value;

    public PivotRow(List<LabelType> labels, ValueType value) {
        this.labels = labels;
        this.value = value;
    }

    public List<LabelType> getLabels(){
        return labels;
    }

    public ValueType getValue(){
        return value;
    }

}
