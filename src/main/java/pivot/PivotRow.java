package pivot;

import java.util.List;

public class PivotRow<T> {
    public List<T> labels;
    public final int value;

    public PivotRow(List<T> labels, int value) {
        this.labels = labels;
        this.value = value;
    }

    public List<T> getLabels(){
        return labels;
    }

    public int getValue(){
        return value;
    }

}
