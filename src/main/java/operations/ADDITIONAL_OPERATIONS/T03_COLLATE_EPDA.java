package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T01_FRESH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_COLLATE_EPDA {

    private static Logger log = LogManager.getLogger(T03_COLLATE_EPDA.class.getName());

    private T03_COLLATE_EPDA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_NEVER_PUSHED(
            epda<state, event, stack> epda) {
        Set<stack> new_stacks = new HashSet<>();
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>();
        Set<state> new_states = new HashSet<>();
        new_states.add(epda.get_epda_initial());


        new_stacks.add(epda.get_epda_box());
        Set<epda_step_label<state, event, stack>> todo1 = new HashSet<>(epda.get_epda_delta());
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<epda_step_label<state, event, stack>> todo2 = new HashSet<>(todo1);
            for (epda_step_label<state, event, stack> step_label : todo2) {
                if (new_stacks.containsAll(step_label.get_edge_pop())) {
                    changed = true;
                    new_stacks.addAll(step_label.get_edge_push());
                    todo1.remove(step_label);
                    new_step_labels.add(step_label);
                    new_states.add(step_label.get_edge_src());
                    new_states.add(step_label.get_edge_trg());
                }
            }
        }

        Set<state> new_marking_states = new HashSet<>(epda.get_epda_marking());
        new_marking_states.retainAll(new_states);

        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                new ArrayList<>(new_stacks),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                new ArrayList<>(new_marking_states));
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("COLLATE_EPDA");

        {
            int sum = epda.get_epda_delta().size() + epda.get_epda_states().size() + epda.get_epda_gamma().size();
            boolean changed = true;
            while (changed) {
                if (log.isInfoEnabled())
                    log.info("step1: " + epda.log_info());
                epda epda1 = COLLATE_EPDA__REMOVE_NEVER_PUSHED(epda);

                //compare_unmarked_language.instance.test("TESTepda", epda, epda1, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step2: " + epda1.log_info());
                epda epda2 = COLLATE_EPDA__REMOVE_EDGES_TO_NONMARKING_STATES_WITHOUT_EXIT(epda1);

                //compare_unmarked_language.instance.test("TESTepda1", epda1, epda2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step3: " + epda2.log_info());
                epda epda3 = COLLATE_EPDA__REMOVE_STATES_WITHOUT_ENTRY(epda2);

                // compare_unmarked_language.instance.test("TESTepda2", epda2, epda3, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step4: " + epda3.log_info());
                epda epda4 = COLLATE_EPDA__REMOVE_ADJACENT_PUSH_AND_POP(epda3);

                //  compare_unmarked_language.instance.test("TESTepda3", epda3, epda4, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step5: " + epda4.log_info());
                epda epda5 = COLLATE_EPDA__REMOVE_ADJACENT_NEUTRAL_EDGES_FORWARD(epda4);

                // compare_unmarked_language.instance.test("TESTepda4", epda4, epda5, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step6: " + epda5.log_info());
                epda epda6 = COLLATE_EPDA__REMOVE_ADJACENT_NEUTRAL_EDGES_BACKWARD(epda5);

                // compare_unmarked_language.instance.test("TESTepda5", epda5, epda6, 1000, 10000, 0, 100, 1000, 10000, 0, 100);


                if (log.isInfoEnabled())
                    log.info("step7: " + epda6.log_info());
                epda epda7 = COLLATE_EPDA__BASIC_ACCESSIBLE(epda6);

                //  compare_unmarked_language.instance.test("TESTepda6", epda6, epda7, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

                if (log.isInfoEnabled())
                    log.info("step8: " + epda7.log_info());
                epda epda8 = COLLATE_EPDA__BASIC_BLOCKFREE(epda7);

                if (log.isInfoEnabled())
                    log.info("step9: " + epda.log_info());
                epda epda9 = COLLATE_EPDA__REMOVE_NEUTRAL_EDGES_TO_MARKING_STATE_WITH_NO_EXIT(epda8);


//                  TAKES TOO MUCH TIME
//                if (log.isInfoEnabled())
//                    log.info("step10: " + epda.log_info());
//                epda epda10 = COLLATE_EPDA__CLEAR_STACK_WHEN_PUSHING_A_SYMBOL_THAT_IS_NEVER_REMOVED(epda9);
//                if (!epda9.equals(epda10))
//                    epda10 = T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(epda10, false);

//                if (log.isInfoEnabled())
//                    log.info("step11: " + epda.log_info());
//                epda epda11 = COLLATE_EPDA__SPLIT_STATES_WITH_NOT_ONE_IN_AND_ONE_OUT(epda10);
//                if (!epda10.equals(epda11))
//                    epda11 = T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(epda11, false);


                // here the unmarked language is changed!

                epda = epda9;


                int new_sum = epda.get_epda_delta().size() + epda.get_epda_states().size() + epda.get_epda_gamma().size();
                changed = sum != new_sum;
                sum = new_sum;
            }
        }


        //compare_unmarked_language.instance.test("TESTepda-9", epda, epda9, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (log.isInfoEnabled())
            log.info("final: " + epda.log_info());

        Config.instance.measuring_thread.pop("COLLATE_EPDA");
        if (true)
            return epda;

        // find inner states (only states not marking)
        List<state> one_input_one_output_states = new ArrayList<>();
        for (state q : epda.get_epda_states()) {
            if (epda.get_epda_marking().contains(q)) {
                System.out.println(">> " + q + " is marking");
                continue;
            }
            System.out.println(epda.get_map_target(q));
            System.out.println(epda.get_map_source(q));
            if (epda.get_map_source(q).size() != 1) {
                System.out.println(">> " + q + " source " + epda.get_map_source(q).size());
                continue;
            }
            if (epda.get_map_target(q).size() != 1) {
                System.out.println(epda.get_map_target(q));
                System.out.println(">> " + q + " target " + epda.get_map_target(q).size());
                continue;
            }
            epda_step_label<state, event, stack> edge_out = epda.get_map_source(q).get(0);
            epda_step_label<state, event, stack> edge_in = epda.get_map_target(q).get(0);
            if (edge_in.get_edge_src() == edge_in.get_edge_trg()) {
                System.out.println(">> " + q + " edge_in loop");
                continue;
            }
            if (edge_out.get_edge_src() == edge_out.get_edge_trg()) {
                System.out.println(">> " + q + " edge_out loop");
                continue;
            }
            one_input_one_output_states.add(q);
        }

        if (one_input_one_output_states.isEmpty())
            return epda;

        // find starting states and make first edge to a list
        List<List<epda_step_label<state, event, stack>>> sequences = new ArrayList<>();
        for (state q : one_input_one_output_states) {
            epda_step_label<state, event, stack> edge = epda.get_map_target(q).get(0);
            if (!one_input_one_output_states.contains(edge.get_edge_src()))
                sequences.add(General_List.list_by_enumeration(edge));
        }

        // extend obtained sequences
        for (List<epda_step_label<state, event, stack>> sequence : sequences) {
            state last = General_List.last(sequence).get_edge_trg();
            while (true) {
                if (!epda.get_epda_marking().contains(last)
                        && epda.get_map_source(last).size() == 1) {
                    epda_step_label<state, event, stack> edge = epda.get_map_source(last).get(0);
                    last = edge.get_edge_trg();
                    sequence.add(edge);
                } else
                    break;
            }
        }

        // compute impact
        Map<List<epda_step_label<state, event, stack>>, List<event>> stack_execute = new HashMap<>();
        Map<List<epda_step_label<state, event, stack>>, List<stack>> stack_pop = new HashMap<>();
        Map<List<epda_step_label<state, event, stack>>, List<stack>> stack_push = new HashMap<>();
        for (List<epda_step_label<state, event, stack>> sequence : sequences) {
            List<stack> pop_list = new ArrayList<>();
            List<stack> push_list = new ArrayList<>();
            List<event> events_list = new ArrayList<>();

            for (epda_step_label<state, event, stack> step_label : sequence) {
                if (step_label.get_edge_event() instanceof option__Some)
                    events_list.add(((option__Some<event>) step_label.get_edge_event()).the());

                if (step_label.get_edge_pop().size() > push_list.size())
                    pop_list.addAll(General_List.drop(step_label.get_edge_pop(), push_list.size()));
                push_list = General_List.drop(push_list, step_label.get_edge_pop().size());
                push_list = General_List.append(step_label.get_edge_push(), push_list);

            }
            stack_pop.put(sequence, pop_list);
            stack_push.put(sequence, push_list);
            stack_execute.put(sequence, events_list);

            // TODO: sequence should be compacted iff pop_list is not extended after events_list is nonempty
        }

        Set<epda_step_label<state, event, stack>> step_labels = new HashSet<>(epda.get_epda_delta());
        Set<state> states = new HashSet<>(epda.get_epda_states());
        for (List<epda_step_label<state, event, stack>> sequence : sequences) {
            // remove step_labels
            step_labels.removeAll(sequence);
            // remove states
            for (epda_step_label<state, event, stack> step_label : General_List.drop(sequence, 1))
                states.remove(step_label.get_edge_src());
            state current = sequence.get(0).get_edge_src();

            // create fake steps
            int index = 0;
            int cnt = stack_pop.get(sequence).size()
                    + stack_push.get(sequence).size()
                    + stack_execute.get(sequence).size();
            List<tuple2<General_Integer, General>> steps = new ArrayList<>();
            for (stack s : stack_pop.get(sequence))
                steps.add(new tuple2<>(new General_Integer(0), s));
            for (stack s : stack_push.get(sequence))
                steps.add(new tuple2<>(new General_Integer(1), s));
            for (event e : stack_execute.get(sequence))
                steps.add(new tuple2<>(new General_Integer(2), e));

            // insert pop edges
            for (stack s : stack_pop.get(sequence)) {
                steps.add(new tuple2<>(new General_Integer(0), s));
            }
            // insert push edges
            // insert execute edges
        }

        return null;

//        return new epda<>(
//                new ArrayList<>(used_states),
//                epda.get_epda_events(),
//                new ArrayList<>(used_stack),
//                epda.get_epda_delta(),
//                epda.get_epda_initial(),
//                epda.get_epda_box(),
//                new ArrayList<>(used_marking_states));

    }

    private static <state extends General, event extends General, stack extends General>
    epda<symbol__abstract<state>, event, stack>
    COLLATE_EPDA__SPLIT_STATES_WITH_NOT_ONE_IN_AND_ONE_OUT(epda<symbol__abstract<state>, event, stack> epda) {
        // does not terminate for loops

        boolean changed = true;
        while (changed) {
            changed = false;
            Set<epda_step_label<symbol__abstract<state>, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
            Set<symbol__abstract<state>> new_states = new HashSet<>(epda.get_epda_states());
            for (symbol__abstract<state> state : epda.get_epda_states()) {
                if (changed)
                    break;
                Set<epda_step_label<symbol__abstract<state>, event, stack>> real_in = new HashSet<>();
                Set<epda_step_label<symbol__abstract<state>, event, stack>> real_out = new HashSet<>();
                Set<epda_step_label<symbol__abstract<state>, event, stack>> loops = new HashSet<>();
                for (epda_step_label<symbol__abstract<state>, event, stack> elem : epda.get_map_source(state))
                    if (elem.get_edge_trg() != elem.get_edge_src())
                        real_out.add(elem);
                for (epda_step_label<symbol__abstract<state>, event, stack> elem : epda.get_map_target(state))
                    if (elem.get_edge_trg() != elem.get_edge_src())
                        real_in.add(elem);
                    else loops.add(elem);
                if (real_in.size() == 1 && real_out.size() == 1)
                    continue;
                changed = true;
                new_step_labels.removeAll(real_in);
                new_step_labels.removeAll(real_out);
                new_step_labels.removeAll(loops);
                for (General_Tuple2<epda_step_label<symbol__abstract<state>, event, stack>, epda_step_label<symbol__abstract<state>, event, stack>> elem : T01_COLLATE_CFG.cartesian_product(real_in, real_out)) {
                    symbol__abstract<state> fresh_state = T01_FRESH.F_FRESH(new ArrayList<>(new_states));
                    new_states.add(fresh_state);
                    new_step_labels.add(new epda_step_label<>(
                            elem.getElemA().get_edge_src(),
                            elem.getElemA().get_edge_event(),
                            elem.getElemA().get_edge_pop(),
                            elem.getElemA().get_edge_push(),
                            fresh_state));
                    for (epda_step_label<symbol__abstract<state>, event, stack> loop : loops)
                        new_step_labels.add(new epda_step_label<>(
                                fresh_state,
                                loop.get_edge_event(),
                                loop.get_edge_pop(),
                                loop.get_edge_push(),
                                fresh_state));
                    new_step_labels.add(new epda_step_label<>(
                            fresh_state,
                            elem.getElemB().get_edge_event(),
                            elem.getElemB().get_edge_pop(),
                            elem.getElemB().get_edge_push(),
                            elem.getElemB().get_edge_trg()));
                }
            }

            epda = new epda<>(
                    new ArrayList<>(new_states),
                    epda.get_epda_events(),
                    epda.get_epda_gamma(),
                    new ArrayList<>(new_step_labels),
                    epda.get_epda_initial(),
                    epda.get_epda_box(),
                    epda.get_epda_marking());
        }
        return epda;
    }

    private static <state extends General, event extends General, stack extends General>
    epda<symbol__abstract<state>, event, stack>
    COLLATE_EPDA__CLEAR_STACK_WHEN_PUSHING_A_SYMBOL_THAT_IS_NEVER_REMOVED(epda<symbol__abstract<state>, event, stack> epda) {
        Set<epda_step_label<symbol__abstract<state>, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
        Set<symbol__abstract<state>> new_states = new HashSet<>(epda.get_epda_states());

        // besides epda.get_epda_box() the following are interesting
        Set<stack> never_popped_stack_symbols = new HashSet<>(epda.get_epda_gamma());
        for (epda_step_label<symbol__abstract<state>, event, stack> elem : epda.get_epda_delta())
            if (elem.get_edge_push().isEmpty())
                never_popped_stack_symbols.removeAll(elem.get_edge_pop());
        never_popped_stack_symbols.remove(epda.get_epda_box());

        // whenever one of them is pushed we can clear the stack below.
        for (epda_step_label<symbol__abstract<state>, event, stack> elem : epda.get_epda_delta()) {
            if (elem.get_edge_push().size() == 2
                    && never_popped_stack_symbols.contains(elem.get_edge_push().get(0))
                    && !elem.get_edge_push().get(1).equals(epda.get_epda_box())) {
                symbol__abstract<state> fresh_state = T01_FRESH.F_FRESH(new ArrayList<>(new_states));
                new_step_labels.remove(elem);
                new_step_labels.add(new epda_step_label<>(elem.get_edge_src(), elem.get_edge_event(), elem.get_edge_pop(), elem.get_edge_pop(), fresh_state));
                new_step_labels.add(new epda_step_label<>(fresh_state, new option__None<>(), Collections.singletonList(epda.get_epda_box()), General_List.list_by_enumeration(elem.get_edge_push().get(0), epda.get_epda_box()), elem.get_edge_trg()));
                for (stack stack : epda.get_epda_gamma())
                    new_step_labels.add(new epda_step_label<>(fresh_state, new option__None<>(), Collections.singletonList(stack), General_List.list_by_enumeration(), fresh_state));
                new_states.add(fresh_state);
            }
        }

        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }

    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_ADJACENT_PUSH_AND_POP(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
        Set<state> new_states = new HashSet<>(epda.get_epda_states());

        for (state q : epda.get_epda_states()) {
            if (epda.get_epda_marking().contains(q))
                continue;
            if (epda.get_map_source(q).size() != 1)
                continue;
            epda_step_label<state, event, stack> step_label = epda.get_map_source(q).iterator().next();
            stack popping = null;
            if (step_label.get_edge_event() instanceof option__None
                    && step_label.get_edge_push().isEmpty()
                    && !step_label.get_edge_trg().equals(step_label.get_edge_src())
                    && step_label.get_edge_pop().size() == 1)
                popping = step_label.get_edge_pop().get(0);
            if (popping == null)
                continue;

            boolean contra_found = false;
            for (epda_step_label<state, event, stack> step_label2 : epda.get_map_target(q)) {
                if (step_label2.get_edge_event() instanceof option__None
                        && step_label2.get_edge_push().size() == 2
                        && !step_label2.get_edge_trg().equals(step_label2.get_edge_src())
                        && step_label2.get_edge_pop().size() == 1) {
                    if (!popping.equals(step_label2.get_edge_push().get(0))) {
                        contra_found = true;
                        break;
                    }
                } else {
                    contra_found = true;
                    break;
                }
            }
            if (contra_found)
                continue;

            List<epda_step_label<state, event, stack>> entering = epda.get_map_target(q);
            new_step_labels.removeAll(entering);
            new_step_labels.remove(step_label);
            new_states.remove(step_label.get_edge_src());
            for (epda_step_label<state, event, stack> step_label2 : entering) {
                epda_step_label<state, event, stack> new_edge = new epda_step_label<>(
                        step_label2.get_edge_src(),
                        step_label2.get_edge_event(),
                        step_label2.get_edge_pop(),
                        General_List.drop(step_label2.get_edge_push(), 1),
                        step_label.get_edge_trg()
                );
                new_step_labels.add(new_edge);
            }


        }

        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }


    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_ADJACENT_NEUTRAL_EDGES_FORWARD(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());

        for (epda_step_label<state, event, stack> step_label : epda.get_epda_delta()) {
            if (step_label.get_edge_event() instanceof option__None
                    && step_label.get_edge_pop().equals(step_label.get_edge_push())
                    && step_label.get_edge_pop().size() == 1
                    && !epda.get_epda_marking().contains(step_label.get_edge_trg())) {
                new_step_labels.remove(step_label);
                if (step_label.get_edge_trg().equals(step_label.get_edge_src()))
                    continue;
                for (epda_step_label<state, event, stack> step_label2 : epda.get_map_source(step_label.get_edge_trg())) {
                    if (step_label2.get_edge_pop().equals(step_label.get_edge_push())) {
                        epda_step_label<state, event, stack> new_edge = new epda_step_label<>(
                                step_label.get_edge_src(),
                                step_label2.get_edge_event(),
                                step_label2.get_edge_pop(),
                                step_label2.get_edge_push(),
                                step_label2.get_edge_trg()
                        );
                        new_step_labels.add(new_edge);
                    }
                }
            }
        }

        return new epda<>(
                epda.get_epda_states(),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }

    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_ADJACENT_NEUTRAL_EDGES_BACKWARD(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());

        for (epda_step_label<state, event, stack> step_label : epda.get_epda_delta()) {
            if (step_label.get_edge_event() instanceof option__None
                    && step_label.get_edge_pop().equals(step_label.get_edge_push())
                    && step_label.get_edge_pop().size() == 1
                    && !epda.get_epda_marking().contains(step_label.get_edge_src())) {
                if (step_label.get_edge_trg().equals(step_label.get_edge_src())) {
                    new_step_labels.remove(step_label);
                    continue;
                }
                for (epda_step_label<state, event, stack> step_label2 : epda.get_map_target(step_label.get_edge_src())) {
                    if (!step_label2.get_edge_push().isEmpty()
                            && step_label2.get_edge_push().get(0).equals(step_label.get_edge_push())) {
                        new_step_labels.remove(step_label2);
                        epda_step_label<state, event, stack> new_edge = new epda_step_label<>(
                                step_label2.get_edge_src(),
                                step_label2.get_edge_event(),
                                step_label2.get_edge_pop(),
                                step_label2.get_edge_push(),
                                step_label.get_edge_trg()
                        );
                        new_step_labels.add(new_edge);
                    }
                }
            }
        }

        return new epda<>(
                epda.get_epda_states(),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }

    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_NEUTRAL_EDGES_TO_MARKING_STATE_WITH_NO_EXIT(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
        Set<state> new_marking_states = new HashSet<>(epda.get_epda_marking());

        // remove empty self loops for marking states without exits to other states and without executing edges
        Set<state> terminal_states = new HashSet<>();
        for (state state : epda.get_epda_marking()) {
            boolean terminal = true;
            for (epda_step_label<state, event, stack> elem : epda.get_map_source(state)) {
                if (elem.get_edge_trg() != state) {
                    terminal = false;
                    break;
                }
                if (elem.get_edge_event() instanceof option__Some) {
                    terminal = false;
                    break;
                }
            }
            if (terminal) {
                new_step_labels.removeAll(epda.get_map_source(state));
                terminal_states.add(state);
            }
        }

        // make not executing edges leading to a terminal state to be neutral
        for (state state : terminal_states) {
            for (epda_step_label<state, event, stack> elem : epda.get_map_target(state)) {
                if (elem.get_edge_event() instanceof option__None) {
                    if (!elem.get_edge_push().equals(elem.get_edge_pop())) {
                        new_step_labels.remove(elem);
                        new_marking_states.add(elem.get_edge_src());
                    }
                }
            }
        }


        for (epda_step_label<state, event, stack> step_label : epda.get_epda_delta()) {
            if (step_label.get_edge_event() instanceof option__None
                    && step_label.get_edge_pop().equals(step_label.get_edge_push())
                    && step_label.get_edge_pop().size() == 1
                    && !epda.get_epda_marking().contains(step_label.get_edge_src())) {
                if (step_label.get_edge_trg().equals(step_label.get_edge_src())) {
                    new_step_labels.remove(step_label);
                    continue;
                }
                if (epda.get_map_source(step_label.get_edge_trg()).isEmpty()
                        && epda.get_epda_marking().contains(step_label.get_edge_trg())) {
                    if (epda.get_epda_gamma().size() > 1) {
                        for (stack s : epda.get_epda_gamma()) {
                            if (!s.equals(epda.get_epda_box())) {
                                new_step_labels.remove(step_label);
                                epda_step_label<state, event, stack> new_edge = new epda_step_label<>(
                                        step_label.get_edge_src(),
                                        step_label.get_edge_event(),
                                        step_label.get_edge_pop(),
                                        General_List.list_by_enumeration(s, step_label.get_edge_pop().get(0)),
                                        step_label.get_edge_trg()
                                );
                                new_step_labels.add(new_edge);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return new epda<>(
                epda.get_epda_states(),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                new ArrayList<>(new_marking_states));
    }


    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_EDGES_TO_NONMARKING_STATES_WITHOUT_EXIT(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
        Set<state> new_states = new HashSet<>(epda.get_epda_states());

        for (state q : epda.get_epda_states())
            if (!epda.get_epda_marking().contains(q)
                    && epda.get_map_source(q).isEmpty()) {
                new_states.remove(q);
                new_step_labels.removeAll(epda.get_map_target(q));
            }
        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }

    private static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__REMOVE_STATES_WITHOUT_ENTRY(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>(epda.get_epda_delta());
        Set<state> new_states = new HashSet<>(epda.get_epda_states());

        for (state q : epda.get_epda_states())
            if (!epda.get_epda_initial().equals(q) && epda.get_map_target(q).isEmpty()) {
                new_states.remove(q);
                new_step_labels.removeAll(epda.get_map_source(q));
            }
        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__BASIC_ACCESSIBLE(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>();
        Set<state> new_states = new HashSet<>();

        Set<state> todo = new HashSet<>();
        todo.add(epda.get_epda_initial());
        new_states.add(epda.get_epda_initial());

        while (!todo.isEmpty()) {
            state q = todo.iterator().next();
            todo.remove(q);
            for (epda_step_label<state, event, stack> step_label : epda.get_map_source(q)) {
                new_step_labels.add(step_label);
                if (!new_states.contains(step_label.get_edge_trg())) {
                    new_states.add(step_label.get_edge_trg());
                    todo.add(step_label.get_edge_trg());
                }
            }
        }

        Set<state> new_marking_states = new HashSet<>(epda.get_epda_marking());
        new_marking_states.retainAll(new_states);

        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                new ArrayList<state>(new_marking_states));
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    COLLATE_EPDA__BASIC_BLOCKFREE(
            epda<state, event, stack> epda) {
        Set<epda_step_label<state, event, stack>> new_step_labels = new HashSet<>();
        Set<state> new_states = new HashSet<>(epda.get_epda_marking());
        Set<state> todo = new HashSet<>(epda.get_epda_marking());

        while (!todo.isEmpty()) {
            state q = todo.iterator().next();
            todo.remove(q);
            for (epda_step_label<state, event, stack> step_label : epda.get_map_target(q)) {
                new_step_labels.add(step_label);
                if (!new_states.contains(step_label.get_edge_src())) {
                    new_states.add(step_label.get_edge_src());
                    todo.add(step_label.get_edge_src());
                }
            }
        }

        if (!new_states.contains(epda.get_epda_initial())) {
            new_states.clear();
            new_states.add(epda.get_epda_initial());
            new_states.addAll(epda.get_epda_marking());
            new_step_labels.clear();
        }

        return new epda<>(
                new ArrayList<>(new_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(new_step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());
    }


}
