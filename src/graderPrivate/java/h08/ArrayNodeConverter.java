package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

class ArrayNodeConverter {
    private final ArrayNode node;

    ArrayNodeConverter(ArrayNode node) {
        this.node = node;
    }

    double[][] convert() {
        var list = new ArrayList<double[]>();

        for (var secondaryArrayNode : node) {
            if (secondaryArrayNode.isNull()) {
                list.add(null);
            } else {
                var secondaryArrayList = new ArrayList<Double>();
                for (var valueNode : secondaryArrayNode) {
                    secondaryArrayList.add(valueNode.doubleValue());
                }
                list.add(secondaryArrayList.stream().mapToDouble(Double::doubleValue).toArray());
            }
        }

        return list.toArray(new double[list.size()][]);
    }
}
