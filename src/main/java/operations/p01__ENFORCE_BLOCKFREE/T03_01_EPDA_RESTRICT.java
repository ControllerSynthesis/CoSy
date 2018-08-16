package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_01_EPDA_RESTRICT {

    private static Logger log = LogManager.getLogger(T03_01_EPDA_RESTRICT.class.getName());

    private T03_01_EPDA_RESTRICT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    F_EPDA_R(
            epda<state, event, stack> epda,
            Set<state> Q,
            Set<epda_step_label<state, event, stack>> E) {
        Config.instance.measuring_thread.push("F_EPDA_R");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EPDA_R: " + "epda=" + epda.log_info() + ", Q=" + Q.size() + ", E=" + E.size());


        Set<state> new_states = new HashSet<>(epda.get_epda_states().stream().filter(q -> Q.contains(q)).collect(Collectors.toList()));
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "F_EPDA_R: " + "new_states=" + new_states.size());

        epda<state, event, stack> result = new epda(
                new ArrayList(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                epda.get_epda_delta().stream()
                        .filter(e ->
                                E.contains(e)
                                        && new_states.contains(e.get_edge_src())
                                        && new_states.contains(e.get_edge_trg()))
                        .collect(Collectors.toList()),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking().stream().filter(q -> Q.contains(q)).collect(Collectors.toList()));
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "exiting F_EPDA_R: " + "result=" + result.log_info());
        Config.instance.measuring_thread.pop("F_EPDA_R");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    F_EPDA_RE(
            epda<state, event, stack> epda,
            Set<epda_step_label<state, event, stack>> E) {
        Set<state> Q = Collections.synchronizedSet(new HashSet<>());
        Q.add(epda.get_epda_initial());
        E.stream().
                filter(edge -> epda.get_epda_delta().contains(edge)).
                forEach(edge -> {
                    Q.add(edge.get_edge_src());
                    Q.add(edge.get_edge_trg());
                });
        return F_EPDA_R(epda, Q, E);
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<epda<state, event, stack>>
    F_EPDA_RS(
            epda<state, event, stack> epda,
            Set<state> Q) {
        if (!Q.contains(epda.get_epda_initial()))
            return new option__None();
        else
            return new option__Some(F_EPDA_R(epda, Q, new HashSet<>(epda.get_epda_delta())));
    }


}