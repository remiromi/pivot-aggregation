package pivot;

import java.util.List;
import java.util.function.Function;

public class PivotTreeBuilder {

    public PivotTree<String> build(List<PivotRow<String>> rows, Function<List<Integer>, Integer> aggregationFunction) {
        PivotTree<String> tree = new PivotTree<>(aggregationFunction);
        rows.forEach( row -> tree.addRow(row.getLabels(), row.getValue()));
        tree.fillTreeValues();
        return tree;
    }
}
