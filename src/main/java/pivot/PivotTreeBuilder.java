package pivot;

import java.util.List;

public class PivotTreeBuilder {

    public PivotTree<String> build(List<PivotRow<String>> rows) {
        PivotTree<String> tree = new PivotTree<>();
        rows.forEach( row -> tree.addRow(row.getLabels(), row.getValue()));
        tree.fillTreeValues();
        return tree;
    }
}
