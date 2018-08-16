package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import operations.p01__ENFORCE_BLOCKFREE.T03_01_EPDA_RESTRICT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T08_MINIMIZE_DFA {

    private static Logger log = LogManager.getLogger(T08_MINIMIZE_DFA.class.getName());

    private T08_MINIMIZE_DFA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<epda<state, event, stack>>
    MINIMIZE_DFA(
            epda<state, event, stack> epda) {
        option__abstract<epda<state, event, stack>> epda_optional = TRIM(epda);
        if (epda_optional instanceof option__None)
            return epda_optional;
        epda = ((option__Some<epda<state, event, stack>>) epda_optional).the();

        Set<Set<state>> partitions = new HashSet<>();
        partitions.add(new HashSet<>(epda.get_epda_marking()));
        {
            Set<state> nonmarking = new HashSet<>(epda.get_epda_states());
            nonmarking.removeAll(epda.get_epda_marking());
            partitions.add(nonmarking);
        }
        Set<Set<state>> working = new HashSet<>();
        working.add(new HashSet<>(epda.get_epda_marking()));


        epda.get_map_target_event(null, null);
        while (!working.isEmpty()) {
            Set<state> setA = working.iterator().next();
            working.remove(setA);

            for (event c : epda.get_epda_events()) {
                Set<state> X = new HashSet<>();
                for (state q : setA)
                    for (epda_step_label<state, event, stack> elem : epda.get_map_target_event(q, new option__Some<>(c)))
                        X.add(elem.get_edge_src());
                Set<Set<state>> newpartitions = new HashSet<>();
                for (Set<state> Y : partitions) {
                    Set<state> XcapY = new HashSet<>(X);
                    XcapY.retainAll(Y);
                    if (XcapY.isEmpty()) {
                        newpartitions.add(Y);
                        continue;
                    }
                    Set<state> YminusX = new HashSet<>(Y);
                    YminusX.removeAll(X);
                    if (YminusX.isEmpty()) {
                        newpartitions.add(Y);
                        continue;
                    }
                    newpartitions.add(XcapY);
                    newpartitions.add(YminusX);
                    if (working.containsAll(Y)) {
                        working.remove(Y);
                        working.add(XcapY);
                        working.add(YminusX);
                    } else {
                        if (XcapY.size() <= YminusX.size())
                            working.add(XcapY);
                        else
                            working.add(YminusX);
                    }
                }
                partitions = newpartitions;
            }
        }

        Map<state, state> renaming = new HashMap<>();
        for (Set<state> set : partitions) {
            state rep = set.iterator().next();
            for (state s : set)
                renaming.put(s, rep);
        }


        List<state> new_states = new ArrayList<>(new HashSet<>(renaming.values()));
        List<state> new_marking = new ArrayList<>();
        for (state elem : epda.get_epda_marking())
            new_marking.add(renaming.get(elem));

        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>();
        for (epda_step_label<state, event, stack> elem : epda.get_epda_delta())
            new_step_labels.add(new epda_step_label<>(renaming.get(elem.get_edge_src()), elem.get_edge_event(), elem.get_edge_pop(), elem.get_edge_push(), renaming.get(elem.get_edge_trg())));

        epda<state, event, stack> result = new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                renaming.get(epda.get_epda_initial()),
                epda.get_epda_box(),
                new_marking);

        return new option__Some<>(result);
    }


    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    ENFORCE_ACCESSIBLE(epda<state, event, stack> epda) {
        Set<state> accessible = new HashSet<>();
        Set<state> todo = new HashSet<>();
        todo.add(epda.get_epda_initial());
        epda.get_map_source(null);
        while (!todo.isEmpty()) {
            state current = todo.iterator().next();
            todo.remove(current);
            if (!accessible.contains(current)) {
                accessible.add(current);
                for (epda_step_label<state, event, stack> elem : epda.get_map_source(current)) {
                    if (!accessible.contains(elem.get_edge_trg()))
                        todo.add(elem.get_edge_trg());
                }
            }
        }
        return ((option__Some<epda<state, event, stack>>) T03_01_EPDA_RESTRICT.F_EPDA_RS(epda, accessible)).the();
    }

    private static <state extends General, event extends General, stack extends General>
    option__abstract<epda<state, event, stack>>
    ENFORCE_COACCESSIBLE(epda<state, event, stack> epda) {
        Set<state> coaccessible = new HashSet<>();
        Set<state> todo = new HashSet<>();
        todo.addAll(epda.get_epda_marking());
        epda.get_map_target(null);
        while (!todo.isEmpty()) {
            state current = todo.iterator().next();
            todo.remove(current);
            if (!coaccessible.contains(current)) {
                coaccessible.add(current);
                for (epda_step_label<state, event, stack> elem : epda.get_map_target(current)) {
                    if (!coaccessible.contains(elem.get_edge_src()))
                        todo.add(elem.get_edge_src());
                }
            }
        }
        return T03_01_EPDA_RESTRICT.F_EPDA_RS(epda, coaccessible);
    }

    private static <state extends General, event extends General, stack extends General>
    option__abstract<epda<state, event, stack>>
    TRIM(epda<state, event, stack> epda) {
        return ENFORCE_COACCESSIBLE(ENFORCE_ACCESSIBLE(epda));
    }


}
