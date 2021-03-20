package pivot;

import exception.LabelNotFoundException;
import org.junit.jupiter.api.Test;
import pivot.PivotTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PivotTreeTest {

    private final List<String> startingRow = Arrays.asList("Node I","Node J","Node C","Node D");
    private final List<String> commonBranchRow = Arrays.asList("Node I","Node J","Node C","Node E");
    private final List<String> newBranchRow =Arrays.asList("Node A","Node B","Node G","Node H");

    @Test
    void testAddRowEmptyTree() {
        PivotTree<String> tree = new PivotTree<>();

        tree.addRow(startingRow, 1);

        String expectedTreeString = "{\"root\":{\"label\":\"Root\", \"value\":0, \"children\":[{\"label\":\"Node I\", \"value\":0, \"children\":[{\"label\":\"Node J\", \"value\":0, \"children\":[{\"label\":\"Node C\", \"value\":0, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddTwoDifferentRows() {
        PivotTree<String> tree = new PivotTree<>();

        tree.addRow(startingRow, 1);
        tree.addRow(newBranchRow, 2);

        String expectedTreeString = "{\"root\":{\"label\":\"Root\", \"value\":0, \"children\":[{\"label\":\"Node A\", \"value\":0, \"children\":[{\"label\":\"Node B\", \"value\":0, \"children\":[{\"label\":\"Node G\", \"value\":0, \"children\":[{\"label\":\"Node H\", \"value\":2, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":0, \"children\":[{\"label\":\"Node J\", \"value\":0, \"children\":[{\"label\":\"Node C\", \"value\":0, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddCommonBranch() {
        PivotTree<String> tree = new PivotTree<>();

        tree.addRow(startingRow, 1);
        tree.addRow(commonBranchRow, 399);

        String expectedTreeString = "{\"root\":{\"label\":\"Root\", \"value\":0, \"children\":[{\"label\":\"Node I\", \"value\":0, \"children\":[{\"label\":\"Node J\", \"value\":0, \"children\":[{\"label\":\"Node C\", \"value\":0, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":399, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddRowAlreadyExisting() {
        PivotTree<String> tree = new PivotTree<>();

        tree.addRow(startingRow, 1);
        tree.addRow(startingRow, 999);

        String expectedTreeString = "{\"root\":{\"label\":\"Root\", \"value\":0, \"children\":[{\"label\":\"Node I\", \"value\":0, \"children\":[{\"label\":\"Node J\", \"value\":0, \"children\":[{\"label\":\"Node C\", \"value\":0, \"children\":[{\"label\":\"Node D\", \"value\":1000, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testFillTreeValues() {
        PivotTree<String> tree = new PivotTree<>();
        tree.addRow(startingRow, 1);
        tree.addRow(commonBranchRow, 28);
        tree.addRow(newBranchRow, 21);

        tree.fillTreeValues();

        String expectedTreeString = "{\"root\":{\"label\":\"Root\", \"value\":50, \"children\":[{\"label\":\"Node A\", \"value\":21, \"children\":[{\"label\":\"Node B\", \"value\":21, \"children\":[{\"label\":\"Node G\", \"value\":21, \"children\":[{\"label\":\"Node H\", \"value\":21, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":29, \"children\":[{\"label\":\"Node J\", \"value\":29, \"children\":[{\"label\":\"Node C\", \"value\":29, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":28, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testFindValueEmptyLabels() {
        PivotTree<String> tree = generateFilledTree();
        List<String> labels = new ArrayList<>();

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(labels) );
        assertThat(exception.getMessage()).isEqualTo("Labels is null or empty.");
    }

    @Test
    void testFindValueNullLabels() {
        PivotTree<String> tree = generateFilledTree();

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(null) );
        assertThat(exception.getMessage()).isEqualTo("Labels is null or empty.");
    }

    @Test
    void testFindValueLabelNotFound(){
        PivotTree<String> tree = generateFilledTree();
        List<String> labels = List.of("Fake Label");

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(labels) );
        assertThat(exception.getMessage()).isEqualTo("Label with name:'Fake Label' not found.");
    }

    @Test
    void testFindValueSingleLabelFound() throws LabelNotFoundException{
        PivotTree<String> tree = generateFilledTree();
        List<String> labels = List.of("Node A");

        assertThat(tree.findValue(labels)).isEqualTo(21);
    }

    @Test
    void testFindValueMultipleLabelsFound() throws LabelNotFoundException{
        PivotTree<String> tree = generateFilledTree();
        List<String> labels = List.of("Node I", "Node J", "Node C");

        assertThat(tree.findValue(labels)).isEqualTo(29);
    }

    @Test
    void testGetTotal() {
        PivotTree<String> tree = generateFilledTree();

        assertThat(tree.getTotal()).isEqualTo(50);
    }


    private PivotTree<String> generateFilledTree() {
        PivotTree<String> tree = new PivotTree<>();
        tree.addRow(startingRow, 1);
        tree.addRow(commonBranchRow, 28);
        tree.addRow(newBranchRow, 21);
        tree.fillTreeValues();
        return tree;
    }

}