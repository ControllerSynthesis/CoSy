package supporting_operations;

import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.symbol__cons_symbol_nat;
import datatypes.basic_java_datatypes.General;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class SOME_injective {
    private SOME_injective() {
        throw new IllegalStateException("Utility Class");
    }

    public static <alpha extends General, beta extends General> Map<alpha, symbol__abstract<beta>> SOME_injective(List<alpha> list) {
        Map<alpha, symbol__abstract<beta>> result = new HashMap<>();
        int i = 0;
        for (alpha elem : list)
            result.put(elem, new symbol__cons_symbol_nat(i++));
        return result;
    }

}
