package pivot;

import org.junit.jupiter.api.Test;
import pivot.PivotRow;
import pivot.PivotTree;
import pivot.PivotTreeBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotTreeBuilderTest {

    private final Function<List<Integer>, Integer> product = (numbers) -> {
        int prod = 1;
        for(int number : numbers) {
            prod *= number;
        }
        return prod;
    };

    @Test
    void buildTest() {
        PivotTreeBuilder pivotTreeBuilder = new PivotTreeBuilder();
        List<PivotRow<String>> rows = getRows();

        PivotTree<String> tree = pivotTreeBuilder.build(rows, product);

        assertThat(tree.toString()).isEqualTo(getExpectedTreeAsString());
    }

    private String getExpectedTreeAsString() {
        return "{\"root\":{\"label\":\"null\", \"value\":120, \"children\":[{\"label\":\"Node A\", \"value\":60, \"children\":[{\"label\":\"Node B\", \"value\":60, \"children\":[{\"label\":\"Node X\", \"value\":20, \"children\":[{\"label\":\"Node H\", \"value\":5, \"children\":[]}, {\"label\":\"Node Y\", \"value\":4, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":3, \"children\":[{\"label\":\"Node H\", \"value\":3, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":2, \"children\":[{\"label\":\"Node J\", \"value\":2, \"children\":[{\"label\":\"Node C\", \"value\":2, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":2, \"children\":[]}]}]}]}]}}";
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