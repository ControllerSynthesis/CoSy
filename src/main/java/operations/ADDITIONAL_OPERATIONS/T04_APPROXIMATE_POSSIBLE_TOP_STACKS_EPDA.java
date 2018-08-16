package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA {

    private static Logger log = LogManager.getLogger(T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.class.getName());

    private T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA() {
        throw new IllegalStateException("Utility Class");
    }


    public static <state extends General, event extends General, stack extends General>
    Set<stack>
    APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(
            epda<state, event, stack> epda,
            int depth,
            int k,
            state q) {
        //Config.instance.measuring_thread.push("APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA");
        //if (log.isInfoEnabled())
        //log.info(Config.callStack() + "entering APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA: " + "epda=" + epda.log_info());


        Map<state, General_Set<General_List<stack>>> lists;
        synchronized (epda) {
            lists = epda.get_approximate_accessible_stack(k);
            if (lists == null) {
                lists = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(epda, k);
                epda.set_approximate_accessible_stack(k, lists);
            }
        }

        if (q == null)
            return null;

        Set<stack> result = new HashSet<>();
        if (lists.containsKey(q))
            for (General_List<stack> list : lists.get(q).get_value()) {
                if (list.get_value().size() > depth)
                    result.add(list.get_value().get(depth));
                else {
                    result.clear();
                    result.addAll(epda.get_epda_gamma());
                    break;
                }
            }
        //Config.instance.measuring_thread.pop("APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA");
        return result;
    }


    public static <state extends General, event extends General, stack extends General>
    Map<state, Set<stack>>
    APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree(
            epda<state, event, stack> epda) {
        Map<state, Set<stack>> result = new HashMap<state, Set<stack>>();
        int num = 0;
        for (state q : epda.get_epda_states()) {
            Set<stack> elements = new HashSet<>();
            if (epda.get_epda_marking().contains(q)) {
                elements.addAll(Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree() >= 0 ?
                        T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree(), q)
                        : epda.get_epda_gamma());
            } else {
                for (epda_step_label<state, event, stack> step_label : epda.get_map_source(q))
                    elements.add(step_label.get_edge_pop().get(0));
            }
            result.put(q, elements);
            num += elements.size();
        }
        if (log.isInfoEnabled())
            log.info("APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree: num=" + num + ", needs_auxiliary_for=" + result.size() + ", gamma=" + epda.get_epda_gamma().size() + ", MAX=" + (epda.get_epda_gamma().size() * epda.get_epda_states().size()));
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    Map<state, Set<stack>>
    APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(
            epda<state, event, stack> epda) {
        Map<state, Set<stack>> result = new HashMap<state, Set<stack>>();
        int num = 0;
        for (state q : epda.get_epda_states()) {
            Set<stack> elements = new HashSet<>();
            elements.addAll(Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree() >= 0 ?
                    T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree(), q)
                    : epda.get_epda_gamma());
            result.put(q, elements);
            num += elements.size();
        }
        if (log.isInfoEnabled())
            log.info("APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx: num=" + num + ", needs_auxiliary_for=" + result.size() + ", gamma=" + epda.get_epda_gamma().size() + ", MAX=" + (epda.get_epda_gamma().size() * epda.get_epda_states().size()));
        return result;
    }


    public static <state extends General, event extends General, stack extends General>
    Map<state, Set<stack>>
    APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_all(
            epda<state, event, stack> epda) {
        Map<state, Set<stack>> result = new HashMap<state, Set<stack>>();
        int num = 0;
        for (state q : epda.get_epda_states()) {
            Set<stack> elements = new HashSet<>();
            elements.addAll(epda.get_epda_gamma());
            result.put(q, elements);
            num += elements.size();
        }
        if (log.isInfoEnabled())
            log.info("APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_all: num=" + num + ", needs_auxiliary_for=" + result.size() + ", gamma=" + epda.get_epda_gamma().size() + ", MAX=" + (epda.get_epda_gamma().size() * epda.get_epda_states().size()));
        return result;
    }
}
