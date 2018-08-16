package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES<state extends General, event extends General, stack extends General> {
    private static Logger log = LogManager.getLogger(T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.class.getName());
    private Map<state, Set<List<stack>>> cached__F_EDPDA_RMPOE__pop_components = new HashMap<>();

    public static <state extends General, event extends General, stack extends General>
    epda<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>
    F_EDPDA_RMPOE_TRIVIAL(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_EDPDA_RMPOE_TRIVIAL");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EDPDA_RMPOE_TRIVIAL: " + "epda=" + epda.log_info());
        Set<two_elements__abstract<state, tuple2<state, General_List<stack>>>> states = new HashSet<>();
        Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> step_labels = new HashSet<>();

        for (state source : epda.get_epda_states()) {
            states.add(new two_elements__teA<>(source));
            List<List<stack>> poppers = new ArrayList<>();
            Map<List<stack>, Integer> pophere = new HashMap<>();
            for (epda_step_label<state, event, stack> e : epda.get_map_source(source))
                poppers.add(e.get_edge_pop());

            poppers = new ArrayList<>(new HashSet<>(poppers));
            poppers.sort((list1, list2) -> list1.size() - list2.size());
            for (int i = 0; i < poppers.size(); i++) {
                Integer curlen = poppers.get(i).size() - 1;
                for (int j = 0; j < i; j++)
                    if (General_List.prefix(poppers.get(j), poppers.get(i)))
                        curlen = Math.min(curlen, poppers.get(j).size() - 1);
                pophere.put(poppers.get(i), curlen);
            }

            for (epda_step_label<state, event, stack> e : epda.get_map_source(source)) {
                for (int i = 0; i <= pophere.get(e.get_edge_pop()); i++) {
                    two_elements__abstract<state, tuple2<state, General_List<stack>>> q1;
                    two_elements__abstract<state, tuple2<state, General_List<stack>>> q2;
                    List<stack> pop = new ArrayList<>();
                    List<stack> push = new ArrayList<>();
                    option__abstract<event> event;
                    if (i == 0) {
                        q1 = new two_elements__teA<>(e.get_edge_src());
                    } else {
                        q1 = new two_elements__teB<>(new tuple2<>(
                                e.get_edge_src(),
                                new General_List<>(General_List.take(e.get_edge_pop(), i))
                        ));
                    }
                    if (i == pophere.get(e.get_edge_pop())) {
                        q2 = new two_elements__teA<>(e.get_edge_trg());
                        event = e.get_edge_event();
                        pop.addAll(General_List.drop(e.get_edge_pop(), i));
                        push = e.get_edge_push();
                    } else {
                        q2 = new two_elements__teB<>(new tuple2<>(
                                e.get_edge_src(),
                                new General_List<>(General_List.take(e.get_edge_pop(), i + 1))
                        ));
                        event = new option__None<>();
                        pop.add(e.get_edge_pop().get(i));
                    }
                    states.add(q1);
                    states.add(q2);
                    step_labels.add(new epda_step_label<>(q1, event, pop, push, q2));
                }
            }
        }

        List<two_elements__abstract<state, tuple2<state, General_List<stack>>>> marking =
                new ArrayList<>();
        for (state q : epda.get_epda_marking())
            marking.add(new two_elements__teA<>(q));

        epda<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> result = new epda<>(
                new ArrayList<>(states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(step_labels),
                new two_elements__teA<>(epda.get_epda_initial()),
                epda.get_epda_box(),
                marking);

        Config.instance.measuring_thread.pop("F_EDPDA_RMPOE_TRIVIAL");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    epda<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack>
    F_EDPDA_RMPOE(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_EDPDA_RMPOE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EDPDA_RMPOE: " + "epda=" + epda.log_info());
        epda<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack> result = new T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES<state, event, stack>().inner__F_EDPDA_RMPOE(epda);
        Config.instance.measuring_thread.pop("F_EDPDA_RMPOE");
        return result;
    }

    private epda<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack>
    inner__F_EDPDA_RMPOE(
            epda<state, event, stack> epda) {
        Set<tuple3<state, General_List<stack>, option__abstract<stack>>> states =
                F_EDPDA_RMPOE__states(epda);
        List<epda_step_label<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack>> step_labels =
                F_EDPDA_RMPOE__edges(epda, new HashSet<>(states));
        tuple3<state, General_List<stack>, option__abstract<stack>> initial =
                new tuple3<>(epda.get_epda_initial(), new General_List<>(), new option__None<>());
        List<tuple3<state, General_List<stack>, option__abstract<stack>>> marking =
                new ArrayList<>();
        for (state q : epda.get_epda_marking())
            marking.add(new tuple3<>(q, new General_List<>(), new option__None<>()));
        return new epda<>(
                new ArrayList<>(states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                step_labels,
                initial,
                epda.get_epda_box(),
                marking);
    }

    private List<epda_step_label<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack>>
    F_EDPDA_RMPOE__edges(
            epda<state, event, stack> epda,
            Set<tuple3<state, General_List<stack>, option__abstract<stack>>> states) {
        List<epda_step_label<tuple3<state, General_List<stack>, option__abstract<stack>>, event, stack>> result =
                new ArrayList<>();
        //F_EDPDA_RMPOE__edges_append_list
        for (tuple3<state, General_List<stack>, option__abstract<stack>> state : states) {
            if (state.get_value3().equals(new option__None<stack>())
                    && state.get_value2().get_value().size() > 0) {
                tuple3<state, General_List<stack>, option__abstract<stack>> state_src = new tuple3<>(
                        state.get_value1(),
                        new General_List<>(General_List.butlast(state.get_value2().get_value())),
                        state.get_value3());
                if (states.contains(state_src))
                    result.add(new epda_step_label<>(
                            state_src,
                            new option__None<>(),
                            General_List.list_by_enumeration(General_List.last(state.get_value2().get_value())),
                            General_List.list_by_enumeration(),
                            state));
            } else if (state.get_value3() instanceof option__Some) {
                //F_EDPDA_RMPOE__edges_append_option
                tuple3<state, General_List<stack>, option__abstract<stack>> state_src = new tuple3<>(
                        state.get_value1(),
                        state.get_value2(),
                        new option__None<stack>());
                if (states.contains(state_src))
                    result.add(new epda_step_label<>(
                            state_src,
                            new option__None<>(),
                            General_List.list_by_enumeration(((option__Some<stack>) state.get_value3()).get_value()),
                            General_List.list_by_enumeration(((option__Some<stack>) state.get_value3()).get_value()),
                            state));

                //F_EDPDA_RMPOE__edges_final
                stack gamma = ((option__Some<stack>) state.get_value3()).get_value();
                for (epda_step_label<state, event, stack> edge : epda.get_map_source(state.get_value1())) {
                    if (General_List.prefix(edge.get_edge_pop(), General_List.append(state.get_value2().get_value(), gamma))) {
                        tuple3<state, General_List<stack>, option__abstract<stack>> state_trg =
                                new tuple3<>(edge.get_edge_trg(), new General_List<>(), new option__None<>());
                        if (states.contains(state_trg))
                            result.add(new epda_step_label<>(
                                    state,
                                    edge.get_edge_event(),
                                    General_List.list_by_enumeration(gamma),
                                    General_List.append(edge.get_edge_push(), General_List.drop(General_List.append(state.get_value2().get_value(), gamma), edge.get_edge_pop().size())),
                                    state_trg));
                    }
                }

            }
        }

        return result;
    }

    private Set<tuple3<state, General_List<stack>, option__abstract<stack>>>
    F_EDPDA_RMPOE__states(
            epda<state, event, stack> epda) {
        Set<tuple3<state, General_List<stack>, option__abstract<stack>>> result = new HashSet<>();
        option__abstract<stack> third = new option__Some<>(epda.get_epda_box());
        option__abstract<stack> NONE = new option__None<>();

        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            //F_EDPDA_RMPOE__states_before_pop
            for (int i = 0; i < e.get_edge_pop().size(); i++)
                result.add(new tuple3<>(e.get_edge_src(), new General_List<>(General_List.take(e.get_edge_pop(), i)), NONE));
            if (e.get_edge_pop().size() > 0 && General_List.last(e.get_edge_pop()).equals(epda.get_epda_box()))
                //F_EDPDA_RMPOE__states_bottom_on_top
                result.add(new tuple3<>(e.get_edge_src(), new General_List<>(General_List.butlast(e.get_edge_pop())), third));
            else
                //F_EDPDA_RMPOE__states_last_of_pop
                result.add(new tuple3<>(e.get_edge_src(), new General_List<>(e.get_edge_pop()), NONE));
            //F_EDPDA_RMPOE__states_context_of_top
            for (int i = 0; i <= e.get_edge_pop().size(); i++) {
                General_List<stack> x = new General_List<>(General_List.take(e.get_edge_pop(), i));
                if (x.get_value().size() > 0 && General_List.last(x.get_value()).equals(epda.get_epda_box()))
                    continue;
                for (stack gamma : Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE() >= 0 ?
                        T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, x.get_value().size(), Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(), e.get_edge_src())
                        : epda.get_epda_gamma())
                    if (!General_List.in_prefix_closure(General_List.append(x.get_value(), gamma),
                            F_EDPDA_RMPOE__pop_components(epda, e.get_edge_src())))
                        result.add(new tuple3<>(e.get_edge_src(), x, new option__Some<>(gamma)));
            }
        }
        //F_EDPDA_RMPOE__states_basic
        for (state q : epda.get_epda_states())
            result.add(new tuple3<>(q, new General_List<>(), NONE));
        return result;
    }

    private Set<List<stack>>
    F_EDPDA_RMPOE__pop_components(
            epda<state, event, stack> epda,
            state source) {
        if (cached__F_EDPDA_RMPOE__pop_components.containsKey(source))
            return cached__F_EDPDA_RMPOE__pop_components.get(source);
        Set<List<stack>> result = new HashSet<>();

        for (epda_step_label<state, event, stack> e : epda.get_map_source(source))
            result.add(e.get_edge_pop());

        cached__F_EDPDA_RMPOE__pop_components.put(source, result);
        return result;
    }


}
