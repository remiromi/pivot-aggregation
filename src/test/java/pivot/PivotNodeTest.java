package pivot;

import org.junit.jupiter.api.Test;
import pivot.PivotNode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotNodeTest {

    private final PivotNode<String, Integer> aChildNode = new PivotNode<>("Node C");

    @Test
    void testIsLeaf() {
        PivotNode<String, Integer> aLeafNode = new PivotNode<>("Node L");

        assertThat(aLeafNode.isLeaf()).isTrue();
    }

    @Test
    void testIsNotLeaf() {
        PivotNode<String, Integer> aNode = initializeFatherNode();

        assertThat(aNode.isLeaf()).isFalse();
    }

    @Test
    void testGetChildFromLabelFound() {

        PivotNode<String, Integer> aNode = initializeFatherNode();
        String childLabel = "Node C";
        PivotNode<String, Integer> expectedChildNode = new PivotNode<>("Node C");

        PivotNode<String, Integer> childFromLabel = aNode.getChildFromLabel(childLabel);

        assertThat(childFromLabel).isEqualTo(expectedChildNode);
    }

    @Test
    void testGetChildFromLabelNotFound() {

        PivotNode<String, Integer> aNode = initializeFatherNode();
        String anotherLabel = "Node L";

        PivotNode<String, Integer> childFromLabel = aNode.getChildFromLabel(anotherLabel);

        assertThat(childFromLabel).isNull();
    }

    private PivotNode<String, Integer> initializeFatherNode() {
        PivotNode<String, Integer> aNode = new PivotNode<>("Node A");
        aNode.addChild(aChildNode);
        return aNode;
    }

}