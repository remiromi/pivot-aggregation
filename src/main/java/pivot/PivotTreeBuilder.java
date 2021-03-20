package pivot;

import java.util.List;
import java.util.function.Function;

public class PivotTreeBuilder<LabelType, ValueType extends Number> {

    public PivotTree<LabelType, ValueType> build(List<PivotRow<LabelType, ValueType>> rows, Function<List<ValueType>, ValueType> aggregationFunction) {
        PivotTree<LabelType, ValueType> tree = new PivotTree<>(aggregationFunction);
        rows.forEach( row -> tree.addRow(row.getLabels(), row.getValue()));
        tree.fillTreeValues();
        return tree;
    }
}
