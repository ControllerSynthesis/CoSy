package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_01_EPDA_RESTRICT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_APPROXIMATE_BLOCKFREE {

    private static Logger log = LogManager.getLogger(T05_APPROXIMATE_BLOCKFREE.class.getName());

    private T05_APPROXIMATE_BLOCKFREE() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    APPROXIMATE_BLOCKFREE(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("APPROXIMATE_BLOCKFREE");
        //if (log.isInfoEnabled())
        //log.info(Config.callStack() + "entering APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA: " + "epda=" + epda.log_info());

        Set<state> marking = new HashSet<>(epda.get_epda_marking());
        epda.get_map_source(null);
        List<state> no_leave =
                epda
                        .get_epda_states()
                        .parallelStream()
                        .filter(q -> {
                            if (marking.contains(q))
                                return false;
                            return epda.get_map_source(q).isEmpty();
                        }).collect(Collectors.toList());

        epda<state, event, stack> result;
        if (!no_leave.isEmpty()) {
            Set<state> keep_states = new HashSet<state>(epda.get_epda_states());
            keep_states.removeAll(no_leave);
            keep_states.add(epda.get_epda_initial());
            result = ((option__Some<epda<state, event, stack>>) T03_01_EPDA_RESTRICT.F_EPDA_RS(epda, keep_states)).the();
        } else
            result = epda;
        Config.instance.measuring_thread.pop("APPROXIMATE_BLOCKFREE");
        return result;
    }


}
