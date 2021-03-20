package pivot;

import org.junit.jupiter.api.Test;
import pivot.PivotRow;
import pivot.PivotTree;
import pivot.PivotTreeBuilder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotTreeBuilderTest {


    @Test
    void buildTest() {
        // TODO Build second tree and compare
        PivotTreeBuilder pivotTreeBuilder = new PivotTreeBuilder();
        List<PivotRow<String>> rows = getRows();

        PivotTree<String> tree = pivotTreeBuilder.build(rows);

        assertThat(tree.toString()).isEqualTo(getExpectedTreeAsString());
    }

    private String getExpectedTreeAsString() {
        return "{\"root\":{\"label\":\"Root\", \"value\":15, \"children\":[{\"label\":\"Node A\", \"value\":12, \"children\":[{\"label\":\"Node B\", \"value\":12, \"children\":[{\"label\":\"Node X\", \"value\":9, \"children\":[{\"label\":\"Node H\", \"value\":5, \"children\":[]}, {\"label\":\"Node Y\", \"value\":4, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":3, \"children\":[{\"label\":\"Node H\", \"value\":3, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":3, \"children\":[{\"label\":\"Node J\", \"value\":3, \"children\":[{\"label\":\"Node C\", \"value\":3, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":2, \"children\":[]}]}]}]}]}}";
    }

    private List<PivotRow<String>> getRows() {
        List<String> firstRow = Arrays.asList("Node I","Node J","Node C","Node D");
        List<String> secondRow = Arrays.asList("Node I","Node J","Node C","Node E");
        List<String> thirdRow =Arrays.asList("Node A","Node B","Node G","Node H");
        List<String> fourthRow = Arrays.asList("Node A","Node B","Node X","Node Y");
        List<String> fifthRow = Arrays.asList("Node A","Node B","Node X","Node H");

        return Arrays.asList(
                new PivotRow<>(firstRow, 1),
                new PivotRow<>(secondRow, 2),
                new PivotRow<>(thirdRow, 3),
                new PivotRow<>(fourthRow, 4),
                new PivotRow<>(fifthRow, 5)
        );
    }

}