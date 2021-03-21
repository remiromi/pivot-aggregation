package pivot;

import exception.LabelNotFoundException;

import java.util.List;

/**
 * This Interface exposes QueryableTree objects with aggregated data.
 *
 * The data is aggregated at any level of the tree, so it's possible to query it with any number of labels to find out
 * the aggregated values.
 *
 * @param <LabelType> The labels domain that will be the node labels
 * @param <ValueType> The value type (e.g. Integer, Float)
 */
public interface QueryableTree<LabelType, ValueType extends Number> {

    /**
     * Given a list of labels, finds the matching node.
     *
     * If the queried label does not exist, it throws a LabelNotFoundException, defined in the exception package.
     * Note: the labels are assumed to be always in order according to the specified aggregation order.
     *
     * @param labels The list of labels to query the tree
     * @return The requested aggregation value.
     * @throws LabelNotFoundException when there is no label match.
     */
    ValueType findValue(List<LabelType> labels) throws LabelNotFoundException;

    /**
     * Returns the total aggregation value.
     *
     * The same logic of the findValue method, but with an empty Labels value.
     *
     * @return The root aggregated value.
     */
    ValueType getTotal();
}
