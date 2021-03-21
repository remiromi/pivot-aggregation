package pivot;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PivotTreeBuilderTest {

    private final List<String> firstRow = Arrays.asList("Node I","Node J","Node C","Node D");
    private final List<String> secondRow = Arrays.asList("Node I","Node J","Node C","Node E");
    private final List<String> thirdRow = Arrays.asList("Node A","Node B","Node G","Node H");
    private final List<String> fourthRow = Arrays.asList("Node A","Node B","Node X","Node Y");
    private final List<String> fifthRow = Arrays.asList("Node A","Node B","Node X","Node H");

    private final Function<List<Integer>, Integer> sum = (numbers) -> numbers.stream().reduce(0, Integer::sum);
    private final Function<List<Integer>, Integer> max = (numbers) -> numbers.stream().reduce(MIN_VALUE, Integer::max);

    private final Function<List<BigInteger>, BigInteger> minimum = (numbers) ->
            numbers.stream().reduce(new BigInteger(String.valueOf(MAX_VALUE)), BigInteger::min);

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

    @Test
    void testAddRowEmptyTree() {

        PivotTreeBuilder<String, Integer> treeBuilder = new PivotTreeBuilder<>();
        PivotRow<String, Integer> startingRow = new PivotRow<>(firstRow, 1);

        PivotTree<String, Integer> tree = treeBuilder.build(List.of(startingRow), sum);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":1, \"children\":[{\"label\":\"Node I\", \"value\":1, \"children\":[{\"label\":\"Node J\", \"value\":1, \"children\":[{\"label\":\"Node C\", \"value\":1, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddTwoDifferentRows() {
        PivotTreeBuilder<String, Integer> treeBuilder = new PivotTreeBuilder<>();

        PivotRow<String, Integer> startingRow = new PivotRow<>(firstRow, 1);
        PivotRow<String, Integer> newBranchRow = new PivotRow<>(thirdRow, 2);

        PivotTree<String, Integer> tree = treeBuilder.build(List.of(startingRow, newBranchRow), sum);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":3, \"children\":[{\"label\":\"Node A\", \"value\":2, \"children\":[{\"label\":\"Node B\", \"value\":2, \"children\":[{\"label\":\"Node G\", \"value\":2, \"children\":[{\"label\":\"Node H\", \"value\":2, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":1, \"children\":[{\"label\":\"Node J\", \"value\":1, \"children\":[{\"label\":\"Node C\", \"value\":1, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

   @Test
    void testAddCommonBranch() {

        PivotTreeBuilder<String, Integer> treeBuilder = new PivotTreeBuilder<>();

        PivotRow<String, Integer> startingRow = new PivotRow<>(firstRow, 1);
        PivotRow<String, Integer> commonBranchRow = new PivotRow<>(secondRow, 399);

        PivotTree<String, Integer> tree = treeBuilder.build(List.of(startingRow, commonBranchRow), sum);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":400, \"children\":[{\"label\":\"Node I\", \"value\":400, \"children\":[{\"label\":\"Node J\", \"value\":400, \"children\":[{\"label\":\"Node C\", \"value\":400, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":399, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddRowAlreadyExisting() {
        PivotTreeBuilder<String, Integer> treeBuilder = new PivotTreeBuilder<>();

        PivotRow<String, Integer> startingRow = new PivotRow<>(firstRow, 1);
        PivotRow<String, Integer> differentValueRow = new PivotRow<>(firstRow, 999);

        PivotTree<String, Integer> tree = treeBuilder.build(List.of(startingRow, differentValueRow), sum);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":1000, \"children\":[{\"label\":\"Node I\", \"value\":1000, \"children\":[{\"label\":\"Node J\", \"value\":1000, \"children\":[{\"label\":\"Node C\", \"value\":1000, \"children\":[{\"label\":\"Node D\", \"value\":1000, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddMultipleIdenticalRowsSum() {
        PivotTreeBuilder<String, Integer> treeBuilder = new PivotTreeBuilder<>();

        PivotRow<String, Integer> startingRow = new PivotRow<>(firstRow, 1);
        PivotRow<String, Integer> differentValueRow = new PivotRow<>(firstRow, 499);
        PivotRow<String, Integer> anotherDifferentValueRow = new PivotRow<>(firstRow, 500);

        PivotTree<String, Integer> tree = treeBuilder.build(List.of(startingRow, differentValueRow, anotherDifferentValueRow), sum);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":1000, \"children\":[{\"label\":\"Node I\", \"value\":1000, \"children\":[{\"label\":\"Node J\", \"value\":1000, \"children\":[{\"label\":\"Node C\", \"value\":1000, \"children\":[{\"label\":\"Node D\", \"value\":1000, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testAddMultipleIdenticalRowsAverage() {
        PivotTreeBuilder<String, Float> treeBuilder = new PivotTreeBuilder<>();

        PivotRow<String, Float> startingRow = new PivotRow<>(firstRow, 3f);
        PivotRow<String, Float> secondSameRow = new PivotRow<>(firstRow, 4f);
        PivotRow<String, Float> thirdSameRow = new PivotRow<>(firstRow, 5f);

        PivotTree<String, Float> tree = treeBuilder.build(List.of(startingRow, secondSameRow, thirdSameRow), average);

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":4.0, \"children\":[{\"label\":\"Node I\", \"value\":4.0, \"children\":[{\"label\":\"Node J\", \"value\":4.0, \"children\":[{\"label\":\"Node C\", \"value\":4.0, \"children\":[{\"label\":\"Node D\", \"value\":4.0, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testBuildIntegerTree() {
        PivotTreeBuilder<String, Integer> pivotTreeBuilder = new PivotTreeBuilder<>();
        List<PivotRow<String, Integer>> rows = getRowsInteger();

        PivotTree<String, Integer> tree = pivotTreeBuilder.build(rows, product);

        String expectedIntegerTree = "{\"root\":{\"label\":\"null\", \"value\":120, \"children\":[{\"label\":\"Node A\", \"value\":60, \"children\":[{\"label\":\"Node B\", \"value\":60, \"children\":[{\"label\":\"Node X\", \"value\":20, \"children\":[{\"label\":\"Node H\", \"value\":5, \"children\":[]}, {\"label\":\"Node Y\", \"value\":4, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":3, \"children\":[{\"label\":\"Node H\", \"value\":3, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":2, \"children\":[{\"label\":\"Node J\", \"value\":2, \"children\":[{\"label\":\"Node C\", \"value\":2, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":2, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedIntegerTree);
    }

    @Test
    void testBuildOrderedIntegerTree() {
        PivotTreeBuilder<String, Integer> pivotTreeBuilder = new PivotTreeBuilder<>();
        List<PivotRow<String, Integer>> rows = getUnorderedRowsInteger();

        PivotTree<String, Integer> tree = pivotTreeBuilder.build(rows, product, List.of(3, 2, 1, 0));

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

    @Test
    void testBuildTreeMax() {
        PivotTreeBuilder<String, Integer> pivotTreeBuilder = new PivotTreeBuilder<>();

        PivotTree<String, Integer> tree = pivotTreeBuilder.build(getRowsInteger(), max);

        String expectedTreeString ="{\"root\":{\"label\":\"null\", \"value\":5, \"children\":[{\"label\":\"Node A\", \"value\":5, \"children\":[{\"label\":\"Node B\", \"value\":5, \"children\":[{\"label\":\"Node X\", \"value\":5, \"children\":[{\"label\":\"Node H\", \"value\":5, \"children\":[]}, {\"label\":\"Node Y\", \"value\":4, \"children\":[]}]}, {\"label\":\"Node G\", \"value\":3, \"children\":[{\"label\":\"Node H\", \"value\":3, \"children\":[]}]}]}]}, {\"label\":\"Node I\", \"value\":2, \"children\":[{\"label\":\"Node J\", \"value\":2, \"children\":[{\"label\":\"Node C\", \"value\":2, \"children\":[{\"label\":\"Node D\", \"value\":1, \"children\":[]}, {\"label\":\"Node E\", \"value\":2, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    @Test
    void testBuildCharTreeProduct() {
        PivotTreeBuilder<Character, Integer> pivotTreeBuilder = new PivotTreeBuilder<>();

        PivotTree<Character, Integer> tree = pivotTreeBuilder.build(getUnorderedCharRowsInteger(), product,
                List.of(0, 2, 1, 3));

        String expectedTreeString = "{\"root\":{\"label\":\"null\", \"value\":120, \"children\":[{\"label\":\"A\", \"value\":60, \"children\":[{\"label\":\"B\", \"value\":60, \"children\":[{\"label\":\"G\", \"value\":3, \"children\":[{\"label\":\"H\", \"value\":3, \"children\":[]}]}, {\"label\":\"X\", \"value\":20, \"children\":[{\"label\":\"H\", \"value\":5, \"children\":[]}, {\"label\":\"Y\", \"value\":4, \"children\":[]}]}]}]}, {\"label\":\"I\", \"value\":2, \"children\":[{\"label\":\"J\", \"value\":2, \"children\":[{\"label\":\"C\", \"value\":2, \"children\":[{\"label\":\"D\", \"value\":1, \"children\":[]}, {\"label\":\"E\", \"value\":2, \"children\":[]}]}]}]}]}}";
        assertThat(tree.toString()).isEqualTo(expectedTreeString);
    }

    private List<PivotRow<Character, Integer>> getUnorderedCharRowsInteger() {
        return Arrays.asList( // order 0, 2, 1, 3
                new PivotRow<>(Arrays.asList('I', 'C', 'J', 'D'), 1),
                new PivotRow<>(Arrays.asList('I', 'C', 'J', 'E'), 2),
                new PivotRow<>(Arrays.asList('A', 'G', 'B', 'H'), 3),
                new PivotRow<>(Arrays.asList('A', 'X', 'B', 'Y'), 4),
                new PivotRow<>(Arrays.asList('A', 'X', 'B', 'H'), 5)
        );
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

    private List<PivotRow<String, Integer>> getUnorderedRowsInteger() {
        return Arrays.asList(
                new PivotRow<>(Arrays.asList("Node D","Node C","Node J","Node I"), 1),
                new PivotRow<>(Arrays.asList("Node E","Node C","Node J","Node I"), 2),
                new PivotRow<>(Arrays.asList("Node H","Node G","Node B","Node A"), 3),
                new PivotRow<>(Arrays.asList("Node Y","Node X","Node B","Node A"), 4),
                new PivotRow<>(Arrays.asList("Node H","Node X","Node B","Node A"), 5)
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