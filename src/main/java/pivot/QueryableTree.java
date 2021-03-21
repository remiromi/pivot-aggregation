package pivot;

import exception.LabelNotFoundException;

import java.util.List;

public interface QueryableTree<LabelType, ValueType extends Number> {
    /**
     *
     * @param labels
     * @return
     * @throws LabelNotFoundException
     */
    ValueType findValue(List<LabelType> labels) throws LabelNotFoundException;

    /**
     *
     * @return
     */
    ValueType getTotal();
}
