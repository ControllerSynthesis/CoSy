package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Map;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_03_EPDA_APPROXIMATE_ACCESSIBLE {

    private static Logger log = LogManager.getLogger(T03_03_EPDA_APPROXIMATE_ACCESSIBLE.class.getName());

    private T03_03_EPDA_APPROXIMATE_ACCESSIBLE() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    F_EPDA_AA(
            epda<state, event, stack> epda,
            int k) {
        Config.instance.measuring_thread.push("F_EPDA_AA");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EPDA_AA: " + "epda=" + epda.log_info());

        Map<state, General_Set<General_List<stack>>> result = epda.get_approximate_accessible_stack(k);
        if (result == null)
            result = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(epda, k);
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "F_EPDA_AA: result.keySet()=" + result.keySet().size());
        List<epda_step_label<state, event, stack>> filtered_E = new ArrayList<>();
        for (epda_step_label<state, event, stack> edge : epda.get_epda_delta()) {
            if (filtered_E.size() % 10000 == 0 && log.isInfoEnabled())
                log.info(Config.callStack() + "F_EPDA_AA: " + "filtered_E=" + filtered_E.size());

            for (General_List<stack> list : General_Map.get_default(result, edge.get_edge_src(), new General_Set<>(new HashSet<>())).get_value())
                if ((General_List.prefix(list.get_value(), edge.get_edge_pop()))
                        || General_List.prefix(edge.get_edge_pop(), list.get_value())) {
                    filtered_E.add(edge);
                    break;
                }
        }
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "F_EPDA_AA: start F_EPDA_R: result.keySet()=" + result.keySet().size());
        epda<state, event, stack> result_epda = T03_01_EPDA_RESTRICT.F_EPDA_R(epda, result.keySet(), new HashSet<>(filtered_E));
        result_epda.set_approximate_accessible_stack(k, result);
        Config.instance.measuring_thread.pop("F_EPDA_AA");
        return result_epda;
    }

}
