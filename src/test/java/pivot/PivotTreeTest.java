package pivot;

import exception.LabelNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PivotTreeTest {
    private final Function<List<Integer>, Integer> sum = (numbers) -> numbers.stream().reduce(0, Integer::sum);

    @Test
    void testFindValueEmptyLabels() {
        PivotTree<String, Integer> tree = generateFilledTree();
        List<String> labels = new ArrayList<>();

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(labels) );
        assertThat(exception.getMessage()).isEqualTo("Labels is null or empty.");
    }

    @Test
    void testFindValueNullLabels() {
        PivotTree<String, Integer> tree = generateFilledTree();

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(null) );
        assertThat(exception.getMessage()).isEqualTo("Labels is null or empty.");
    }

    @Test
    void testFindValueLabelNotFound(){
        PivotTree<String, Integer> tree = generateFilledTree();
        List<String> labels = List.of("Fake Label");

        Exception exception = assertThrows(LabelNotFoundException.class, () -> tree.findValue(labels) );
        assertThat(exception.getMessage()).isEqualTo("Label with name:'Fake Label' not found.");
    }

    @Test
    void testFindValueSingleLabelFound() throws LabelNotFoundException{
        PivotTree<String, Integer> tree = generateFilledTree();
        List<String> labels = List.of("Node A");

        assertThat(tree.findValue(labels)).isEqualTo(21);
    }

    @Test
    void testFindValueMultipleLabelsFound() throws LabelNotFoundException{
        PivotTree<String, Integer> tree = generateFilledTree();
        List<String> labels = List.of("Node I", "Node J", "Node C");

        assertThat(tree.findValue(labels)).isEqualTo(29);
    }

    @Test
    void testGetTotal() {
        PivotTree<String, Integer> tree = generateFilledTree();

        assertThat(tree.getTotal()).isEqualTo(50);
    }


    private PivotTree<String, Integer> generateFilledTree() {
        PivotTree<String, Integer> tree = new PivotTree<>();

        tree.getRoot().setValue(50);

        PivotNode<String, Integer> nodeI = new PivotNode<>("Node I", 29);
        PivotNode<String, Integer> nodeJ = new PivotNode<>("Node J", 29);
        PivotNode<String, Integer> nodeC = new PivotNode<>("Node C", 29);
        PivotNode<String, Integer> nodeD = new PivotNode<>("Node D", 1);
        PivotNode<String, Integer> nodeE = new PivotNode<>("Node E", 28);
        PivotNode<String, Integer> nodeA = new PivotNode<>("Node A", 21);
        PivotNode<String, Integer> nodeB = new PivotNode<>("Node B", 21);
        PivotNode<String, Integer> nodeG = new PivotNode<>("Node G", 21);
        PivotNode<String, Integer> nodeH = new PivotNode<>("Node H", 21);

        nodeG.addChild(nodeH);
        nodeB.addChild(nodeG);
        nodeA.addChild(nodeB);

        nodeC.addChild(nodeE);
        nodeC.addChild(nodeD);
        nodeJ.addChild(nodeC);
        nodeI.addChild(nodeJ);

        tree.getRoot().addChild(nodeA);
        tree.getRoot().addChild(nodeI);

        return tree;
    }

}
