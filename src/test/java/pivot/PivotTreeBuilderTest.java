package pivot;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotTreeBuilderTest {

    private final List<String> firstRow = Arrays.asList("Node I","Node J","Node C","Node D");
    private final List<String> secondRow = Arrays.asList("Node I","Node J","Node C","Node E");
    private final List<String> thirdRow = Arrays.asList("Node A","Node B","Node G","Node H");
    private final List<String> fourthRow = Arrays.asList("Node A","Node B","Node X","Node Y");
    private final List<String> fifthRow = Arrays.asList("Node A","Node B","Node X","Node H");

    private final Function<List<Integer>, Integer> product = (numbers) -> {
        int prod = 1;
        for(int number : numbers) {
            prod *= number;
        }
        return prod;
    };

    private final Function<List<Float>, Float> average = (numbers) -> {
        float sum = 0.0f;
        for(float number : numbers) {
            sum += number;
        }
        return sum / numbers.size();
    };

    private final Function<List<BigInteger>, BigInteger> minimum = (numbers) ->
        numbers.stream().reduce(new BigInteger(String.valueOf(Integer.MAX_VALUE)), BigInteger::min);

    @Test
    void testBuildIntegerTree() {
        PivotTreeBuilder<String, Integer> pivotTreeBuilder = new PivotTreeBuilder<>();
        List<PivotRow<String, Integer>> rows = getRowsInteger();

        PivotTree<String, Integer> tree = pivotTreeBuilder.build(rows, product);

        String expectedIntegerTree = "{\"root\":{\"label\":\"null\", \"value\":120, \"children\":[{\"label\":\"Node A\", \"value\":60, \"children\":[{\"label\":\"Node B\", \"value\":60, \"children\":[{\"label\":\"Node X\", \"value\":20, \"children\":[{\"label\":\"Node H\", \"value\":5, \"children\":[]}, {\"label\":\"Node Y\", \"value\":4, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":3, \"children\":[{\"label\":\"Node H\", \"value\":3, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":2, \"children\":[{\"label\":\"Node J\", \"value\":2, \"children\":[{\"label\":\"Node C\", \"value\":2, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":2, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedIntegerTree);
    }

    @Test
    void testBuildIntegerFloat() {
        PivotTreeBuilder<String, Float> pivotTreeBuilder = new PivotTreeBuilder<>();
        List<PivotRow<String, Float>> rows = getRowsFloat();

        PivotTree<String, Float> tree = pivotTreeBuilder.build(rows, average);

        String expectedFloatTree = "{\"root\":{\"label\":\"null\", \"value\":262.5, \"children\":[{\"label\":\"Node A\", \"value\":375.0, \"children\":[{\"label\":\"Node B\", \"value\":375.0, \"children\":[{\"label\":\"Node X\", \"value\":450.0, \"children\":[{\"label\":\"Node H\", \"value\":500.0, \"children\":[]}, {\"label\":\"Node Y\", \"value\":400.0, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":300.0, \"children\":[{\"label\":\"Node H\", \"value\":300.0, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":150.0, \"children\":[{\"label\":\"Node J\", \"value\":150.0, \"children\":[{\"label\":\"Node C\", \"value\":150.0, \"children\":[{\"label\":\"Node D\", \"value\":100.0, \"children\":[]}, {\"label\":\"Node E\", \"value\":200.0, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedFloatTree);
    }

    @Test
    void testBuildBigIntegerTree() {
        PivotTreeBuilder<String, BigInteger> pivotTreeBuilder = new PivotTreeBuilder<>();
        List<PivotRow<String, BigInteger>> rows = getRowsBigInt();

        PivotTree<String, BigInteger> tree = pivotTreeBuilder.build(rows, minimum);

        String expectedBigIntegerTree = "{\"root\":{\"label\":\"null\", \"value\":101, \"children\":[{\"label\":\"Node A\", \"value\":303, \"children\":[{\"label\":\"Node B\", \"value\":303, \"children\":[{\"label\":\"Node X\", \"value\":404, \"children\":[{\"label\":\"Node H\", \"value\":505, \"children\":[]}, {\"label\":\"Node Y\", \"value\":404, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":303, \"children\":[{\"label\":\"Node H\", \"value\":303, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":101, \"children\":[{\"label\":\"Node J\", \"value\":101, \"children\":[{\"label\":\"Node C\", \"value\":101, \"children\":[{\"label\":\"Node D\", \"value\":101, \"children\":[]}, {\"label\":\"Node E\", \"value\":202, \"children\":[]}]}]}]}]}}";

        assertThat(tree.toString()).isEqualTo(expectedBigIntegerTree);
    }

    private List<PivotRow<String, Integer>> getRowsInteger() {
        return Arrays.asList(
                new PivotRow<>(firstRow, 1),
                new PivotRow<>(secondRow, 2),
                new PivotRow<>(thirdRow, 3),
                new PivotRow<>(fourthRow, 4),
                new PivotRow<>(fifthRow, 5)
        );
    }

    private List<PivotRow<String, Float>> getRowsFloat() {
        return Arrays.asList(
                new PivotRow<>(firstRow, 100f),
                new PivotRow<>(secondRow, 200f),
                new PivotRow<>(thirdRow, 300f),
                new PivotRow<>(fourthRow, 400f),
                new PivotRow<>(fifthRow, 500f)
        );
    }

    private List<PivotRow<String, BigInteger>> getRowsBigInt() {
        return Arrays.asList(
                new PivotRow<>(firstRow, new BigInteger("101")),
                new PivotRow<>(secondRow, new BigInteger("202")),
                new PivotRow<>(thirdRow, new BigInteger("303")),
                new PivotRow<>(fourthRow, new BigInteger("404")),
                new PivotRow<>(fifthRow, new BigInteger("505"))
        );
    }
}