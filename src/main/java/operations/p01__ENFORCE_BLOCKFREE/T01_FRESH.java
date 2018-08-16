package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.symbol__cons_symbol_nat;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T01_FRESH {

    private static Logger log = LogManager.getLogger(T01_FRESH.class.getName());

    private T01_FRESH() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * inefficient
     */
    private static <alpha> symbol__abstract<alpha>
    F_FRESH__recursive(Collection<symbol__abstract<alpha>> Q, int n) {
        symbol__abstract<alpha> value = new symbol__cons_symbol_nat(n);
        if (!Q.contains(value))
            return value;
        else
            return F_FRESH__recursive(Q, n + 1);
    }

    public static <alpha> symbol__abstract<alpha>
    F_FRESH(List<symbol__abstract<alpha>> Q) {
        Config.instance.measuring_thread.push("F_FRESH");
        // symbol__abstract<alpha> result = F_FRESH__recursive(Q, 0);
        symbol__abstract<alpha> result = null;
        Set<symbol__abstract<alpha>> set = new HashSet<>(Q);
        for (int n = 0; n < Integer.MAX_VALUE; n++) {
            symbol__abstract<alpha> test_elem = new symbol__cons_symbol_nat(n);
            if (!set.contains(test_elem)) {
                result = test_elem;
                break;
            }

        }
        if (result == null)
            throw new RuntimeException();
        Config.instance.measuring_thread.pop("F_FRESH");
        return result;
    }

}
