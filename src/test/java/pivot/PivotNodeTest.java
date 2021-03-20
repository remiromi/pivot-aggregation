package pivot;

import org.junit.jupiter.api.Test;
import pivot.PivotNode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotNodeTest {

    private final PivotNode<String> aChildNode = new PivotNode<>("Node C");

    @Test
    void testIsLeaf() {
        PivotNode<String> aLeafNode = new PivotNode<>("Node L");

        assertThat(aLeafNode.isLeaf()).isTrue();
    }

    @Test
    void testIsNotLeaf() {
        PivotNode<String> aNode = initializeFatherNode();

        assertThat(aNode.isLeaf()).isFalse();
    }

    @Test
    void testGetChildFromLabelFound() {

        PivotNode<String> aNode = initializeFatherNode();
        String childLabel = "Node C";
        PivotNode<String> expectedChildNode = new PivotNode<>("Node C");

        PivotNode<String> childFromLabel = aNode.getChildFromLabel(childLabel);

        assertThat(childFromLabel).isEqualTo(expectedChildNode);
    }

    @Test
    void testGetChildFromLabelNotFound() {

        PivotNode<String> aNode = initializeFatherNode();
        String anotherLabel = "Node L";

        PivotNode<String> childFromLabel = aNode.getChildFromLabel(anotherLabel);

        assertThat(childFromLabel).isNull();
    }

    private PivotNode<String> initializeFatherNode() {
        PivotNode<String> aNode = new PivotNode<>("Node A");
        aNode.addChild(aChildNode);
        return aNode;
    }

}