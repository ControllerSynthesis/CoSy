package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda;
import main.Config;
import multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem;
import multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Store_Result_Callback;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE {

    private static Logger log = LogManager.getLogger(T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.class.getName());

    private T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    tuple2<state, General_List<stack>>
    F_EPDA_AIA__fp_start(
            epda<state, event, stack> epda,
            int k) {
        return new tuple2<>(epda.get_epda_initial(),
                new General_List<>(General_List.take(General_List.list_by_enumeration(epda.get_epda_box()), k)));
    }

    /**
     * F_EPDA_AIA__fp_one and F_EPDA_AIA__fp are implicitly implemented
     */
    public static <state extends General, event extends General, stack extends General>
    Map<state, General_Set<General_List<stack>>>
    F_EPDA_AIA(
            epda<state, event, stack> epda,
            int k) {
        Config.instance.measuring_thread.push("F_EPDA_AIA");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EPDA_AIA: " + "epda=" + epda.log_info() + ", k=" + k);


        tuple2<state, General_List<stack>> initial = F_EPDA_AIA__fp_start(epda, k);
        Map<state, General_Set<General_List<stack>>> result = new HashMap<>();
        result.putIfAbsent(initial.get_value1(), new General_Set<>(new HashSet<>(), true));
        result.get(initial.get_value1()).get_value().add(initial.get_value2());
        List<tuple2<state, General_List<stack>>> todo = new ArrayList<>();
        todo.add(initial);

        // to construct the data!
        epda.get_map_source(null);

        final AtomicBoolean abort = new AtomicBoolean(false);
        final List<Callback> callbackList = new ArrayList<>();
        callbackList.add(new T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Store_Result_Callback(result, abort));
        Config.instance.local_solver.register_callbacks(abort, callbackList);
        Config.instance.local_solver.add_problem(
                Config.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_PRIORITY,
                abort,
                new T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem<>(todo, epda, k
                ));
        try {
            Config.instance.local_solver.wait_for_completion(abort);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }


        if (log.isInfoEnabled())
            log.info(Config.callStack() + "F_EPDA_AIA: result=" + result.size());
        Config.instance.measuring_thread.pop("F_EPDA_AIA");

        return result;
    }


}