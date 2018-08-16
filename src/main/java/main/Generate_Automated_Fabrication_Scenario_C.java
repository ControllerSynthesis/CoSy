package main;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import operations.ADDITIONAL_OPERATIONS.T08_MINIMIZE_DFA;
import operations.p01__ENFORCE_BLOCKFREE.T02_01_EPDA_TYPE_CONVERSION;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_03_EDPDA_TO_DPDA;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import operations.p04__CONSTRUCT_CONTROLLER.Algorithm_Definition__Construct_Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Generate_Automated_Fabrication_Scenario_C {

    private static Logger log = LogManager.getLogger(Main.class.getName());
    private final boolean debug_computations;
    private final boolean run_controller_synthesis;


    epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
            plant = null;
    epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
            spec = null;
    List<symbol__abstract<string_symbol>>
            sigmaUC = null;

    General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
            tests = null;

    public Generate_Automated_Fabrication_Scenario_C(boolean debug_computations, boolean run_controller_synthesis, List<String> example_args) {
        this.debug_computations = debug_computations;
        this.run_controller_synthesis = run_controller_synthesis;
        List<Integer> params = new ArrayList<>();
        for (String s : example_args)
            params.add(Integer.parseInt(s));


        // TODO: maybe only accept new tasks if no orders are open?
        if (params.size() == 0) {
            System.out.println("arguments: none; using default arguments");
            params = new ArrayList<>();
            if (1 == 1) {
                params.add(0);
                params.add(1);
                params.add(1);
                params.add(2);
                params.add(1);
            } else {
                params.add(1);
                params.add(1);
                params.add(1);
                params.add(5);
                params.add(2);
            }
        }
        if (params.size() != 5)
            throw new RuntimeException();

        System.out.println("arguments: num_shared_pieces_BC   : " + params.get(0));
        System.out.println("arguments: num_nonshared_pieces_B : " + params.get(1));
        System.out.println("arguments: num_nonshared_pieces_C : " + params.get(2));
        System.out.println("arguments: stack_lengthm          : " + params.get(3));
        System.out.println("arguments: concurrent_orders      : " + params.get(4));
        for (int elem = 5; elem < params.size(); elem++)
            System.out.println("arguments: (ignored)              : " + params.get(elem));

        log.info("generating: " + params.get(0) + ", " + params.get(1) + ", " + params.get(2) + ", " + params.get(3) + ", " + params.get(4));

        generate(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
        log.info("generating done");

    }

    private static epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
    generate_machine_spec(
            List<symbol__abstract<string_symbol>> stack,
            List<symbol__abstract<string_symbol>> controllable_events,
            List<symbol__abstract<string_symbol>> uncontrollable_events,
            int concurrent_orders,
            int stack_lengthm,
            List<symbol__abstract<string_symbol>> container_events,
            int elements) {
        List<symbol__abstract<string_symbol>> states = new ArrayList<>();
        List<epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> step_labels = new ArrayList<>();
        symbol__abstract<string_symbol> state1 = new symbol__cons_symbol_base<>(new string_symbol("machineSpec_state1"));
        symbol__abstract<string_symbol> state2 = new symbol__cons_symbol_base<>(new string_symbol("machineSpec_state2"));
        symbol__abstract<string_symbol> state3 = new symbol__cons_symbol_base<>(new string_symbol("machineSpec_state3"));
        states.add(state1);
        states.add(state2);
        states.add(state3);
        {
            // q1,s,☐,○○○○○☐,q2
            List<symbol__abstract<string_symbol>> push = new ArrayList<>();
            for (int i = 1; i <= stack_lengthm; i++)
                push.add(get_symbol(stack, "○"));
            push.add(get_symbol(stack, "☐"));
            step_labels.add(new epda_step_label<>(
                    state1,
                    new option__Some<>(get_symbol(controllable_events, "s")),
                    Collections.singletonList(get_symbol(stack, "☐")),
                    push,
                    state2));
        }
        List<List<symbol__abstract<string_symbol>>> container_words = generate_container_words(container_events, stack_lengthm);
        for (int i = 1; i <= 2; i++) {
            // q3,c_i,-,-,q2
            step_labels.add(new epda_step_label<>(
                    state3,
                    new option__Some<>(get_symbol(controllable_events, "c" + i)),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    state2));
            for (List<symbol__abstract<string_symbol>> w : container_words) {
                List<symbol__abstract<string_symbol>> wti = new ArrayList<>(w);
                wti.add(get_symbol(stack, "t" + i));
                // q2,t_i,w,wt_i,q2
                // q2,s_i,wt_i,w,q3
                // q3,t_i,w,wt_i,q3
                step_labels.add(new epda_step_label<>(
                        state2,
                        new option__Some<>(get_symbol(uncontrollable_events, "t" + i)),
                        w,
                        wti,
                        state2));
                step_labels.add(new epda_step_label<>(
                        state2,
                        new option__Some<>(get_symbol(controllable_events, "s" + i)),
                        wti,
                        w,
                        state3));
                step_labels.add(new epda_step_label<>(
                        state3,
                        new option__Some<>(get_symbol(uncontrollable_events, "t" + i)),
                        w,
                        wti,
                        state3));
            }
        }
        {
            // q2,o_j,w[circ],w[o_j],q2
            // q2,o_j,w[o_j],w[d_j],q2
            // q3,o_j,w[o_j],w[d_j],q3
            // q3,e,w[e'],w[circ],q3 (where (e,e') ...)
            for (int i = 1; i <= elements; i++) {
                Set<General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>>>
                        circ_oi = new HashSet<>();
                Set<General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>>>
                        oi_di = new HashSet<>();
                Set<General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>>>
                        ei_circ = new HashSet<>();

                for (List<symbol__abstract<string_symbol>> w : container_words) {
                    if (w.contains(get_symbol(container_events, "○"))) {
                        List<symbol__abstract<string_symbol>> w_subst = new ArrayList<>(w);
                        w_subst.set(w.indexOf(get_symbol(container_events, "○")), get_symbol(container_events, "o" + i));
                        sort(container_events, w_subst);
                        circ_oi.add(new General_Tuple2<>(w, w_subst));
                        // circ_oi.add(drop_common_tail(w, w_subst));
                    }
                    if (w.contains(get_symbol(container_events, "o" + i))) {
                        List<symbol__abstract<string_symbol>> w_subst = new ArrayList<>(w);
                        w_subst.set(w.indexOf(get_symbol(container_events, "o" + i)), get_symbol(container_events, "e" + i));
                        sort(container_events, w_subst);
                        oi_di.add(new General_Tuple2<>(w, w_subst));
                        // oi_di.add(drop_common_tail(w, w_subst));
                    }
                    if (w.contains(get_symbol(container_events, "e" + i))) {
                        List<symbol__abstract<string_symbol>> w_subst = new ArrayList<>(w);
                        w_subst.set(w.indexOf(get_symbol(container_events, "e" + i)), get_symbol(container_events, "○"));
                        sort(container_events, w_subst);
                        ei_circ.add(new General_Tuple2<>(w, w_subst));
                        // ei_circ.add(drop_common_tail(w, w_subst));
                    }
                }
                for (General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>> pop_push : circ_oi) {
                    step_labels.add(new epda_step_label<>(
                            state2,
                            new option__Some<>(get_symbol(controllable_events, "o" + i)),
                            pop_push.getElemA(),
                            pop_push.getElemB(),
                            state2));
                }
                for (General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>> pop_push : oi_di) {
                    step_labels.add(new epda_step_label<>(
                            state2,
                            new option__Some<>(get_symbol(uncontrollable_events, "d" + i)),
                            pop_push.getElemA(),
                            pop_push.getElemB(),
                            state2));
                    step_labels.add(new epda_step_label<>(
                            state3,
                            new option__Some<>(get_symbol(uncontrollable_events, "d" + i)),
                            pop_push.getElemA(),
                            pop_push.getElemB(),
                            state3));
                }
                for (General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>> pop_push : ei_circ) {
                    if (get_symbol(controllable_events, "e" + i + "B") != null)
                        step_labels.add(new epda_step_label<>(
                                state3,
                                new option__Some<>(get_symbol(controllable_events, "e" + i + "B")),
                                pop_push.getElemA(),
                                pop_push.getElemB(),
                                state3));
                    if (get_symbol(controllable_events, "e" + i + "C") != null)
                        step_labels.add(new epda_step_label<>(
                                state3,
                                new option__Some<>(get_symbol(controllable_events, "e" + i + "C")),
                                pop_push.getElemA(),
                                pop_push.getElemB(),
                                state3));
                }
            }
        }

        List<symbol__abstract<string_symbol>> events = new ArrayList<>();
        events.addAll(controllable_events);
        events.addAll(uncontrollable_events);
        return new epda<>(
                states,
                events,
                stack,
                step_labels,
                state1,
                get_symbol(stack, "☐"),
                Collections.singletonList(state2));
    }

    private static General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>>
    drop_common_tail(
            List<symbol__abstract<string_symbol>> w1,
            List<symbol__abstract<string_symbol>> w2) {
        General_Tuple2<List<symbol__abstract<string_symbol>>, List<symbol__abstract<string_symbol>>>
                result = new General_Tuple2<>(new ArrayList<>(w1), new ArrayList<>(w2));
        while (result.getElemA().size() > 0 && result.getElemB().size() > 0 && result.getElemA().get(result.getElemA().size() - 1) == result.getElemB().get(result.getElemB().size() - 1)) {
            result.getElemA().remove(result.getElemA().size() - 1);
            result.getElemB().remove(result.getElemB().size() - 1);
        }
        return result;
    }

    private static void
    sort(
            List<symbol__abstract<string_symbol>> container_events,
            List<symbol__abstract<string_symbol>> list) {
        Collections.sort(list, Comparator.comparingInt(container_events::indexOf));
    }

    private static List<List<symbol__abstract<string_symbol>>> generate_container_words(List<symbol__abstract<string_symbol>> container_events, int length) {
        List<List<symbol__abstract<string_symbol>>> result = new ArrayList<>();
        List<List<symbol__abstract<string_symbol>>> todo = new ArrayList<>();
        todo.add(new ArrayList<>());
        while (!todo.isEmpty()) {
            List<symbol__abstract<string_symbol>> elem = todo.iterator().next();
            todo.remove(elem);
            if (elem.size() == length) {
                result.add(elem);
            } else if (elem.size() == 0) {
                for (symbol__abstract<string_symbol> s : container_events) {
                    todo.add(Collections.singletonList(s));
                }
            } else {
                symbol__abstract<string_symbol> last = elem.get(elem.size() - 1);
                boolean found = false;
                for (symbol__abstract<string_symbol> s : container_events) {
                    if (s == last) {
                        found = true;
                    }
                    if (!found)
                        continue;
                    List<symbol__abstract<string_symbol>> newElem = new ArrayList<>(elem);
                    newElem.add(s);
                    todo.add(newElem);
                }
            }
        }
        return result;
    }

    private static epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
    generate_machine_B(
            List<symbol__abstract<string_symbol>> stack,
            List<symbol__abstract<string_symbol>> event_specific_B,
            List<symbol__abstract<string_symbol>> controllable_events,
            List<symbol__abstract<string_symbol>> uncontrollable_events,
            int num_shared_pieces_BC,
            int num_nonshared_pieces_B,
            int num_nonshared_pieces_C) {
        List<symbol__abstract<string_symbol>> states = new ArrayList<>();
        List<epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> step_labels = new ArrayList<>();

        for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_B + 3; i++) {
            states.add(new symbol__cons_symbol_base<>(new string_symbol("machineB_state" + i)));
        }
        for (int j = 1; j <= num_shared_pieces_BC + num_nonshared_pieces_B + 3; j++) {
            symbol__abstract<string_symbol> state_cur = get_symbol(states, "machineB_state" + j);
            symbol__abstract<string_symbol> state_next = get_symbol(states, "machineB_state" + (j + 1));
            symbol__abstract<string_symbol> state_start_produce = get_symbol(states, "machineB_state" + 2);
            Set<symbol__abstract<string_symbol>> exiting = new HashSet<>();
            if (j == 1)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "s")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            else if (j == 2)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "s1")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            else if (j == num_shared_pieces_BC + num_nonshared_pieces_B + 3)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "c1")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_start_produce));
            else {
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "e" + (j - 2) + "B")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            }
            if (j > 1) {
                for (symbol__abstract<string_symbol> s : controllable_events) {
                    if (event_specific_B.contains(s))
                        continue;
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_cur));
                }
                for (symbol__abstract<string_symbol> s : uncontrollable_events) {
                    if (event_specific_B.contains(s))
                        continue;
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_cur));
                }
            }
        }
//        System.out.println(states);
//        for (epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> elem : step_labels)
//            System.out.println(elem);
//        System.out.println(step_labels.size());

        List<symbol__abstract<string_symbol>> events = new ArrayList<>();
        events.addAll(controllable_events);
        events.addAll(uncontrollable_events);
        return new epda<>(
                states,
                events,
                stack,
                step_labels,
                get_symbol(states, "machineB_state1"),
                get_symbol(stack, "☐"),
                Collections.singletonList(get_symbol(states, "machineB_state2")));
    }

    private static epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
    generate_machine_C(
            List<symbol__abstract<string_symbol>> stack,
            List<symbol__abstract<string_symbol>> event_specific_C,
            List<symbol__abstract<string_symbol>> controllable_events,
            List<symbol__abstract<string_symbol>> uncontrollable_events,
            int num_shared_pieces_BC,
            int num_nonshared_pieces_B,
            int num_nonshared_pieces_C) {
        List<symbol__abstract<string_symbol>> states = new ArrayList<>();
        List<epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> step_labels = new ArrayList<>();

        for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_C + 3; i++) {
            states.add(new symbol__cons_symbol_base<>(new string_symbol("machineC_state" + i)));
        }
        for (int j = 1; j <= num_shared_pieces_BC + num_nonshared_pieces_C + 3; j++) {
            symbol__abstract<string_symbol> state_cur = get_symbol(states, "machineC_state" + j);
            symbol__abstract<string_symbol> state_next = get_symbol(states, "machineC_state" + (j + 1));
            symbol__abstract<string_symbol> state_start_produce = get_symbol(states, "machineC_state" + 2);
            if (j == 1)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "s")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            else if (j == 2)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "s2")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            else if (j == num_shared_pieces_BC + num_nonshared_pieces_C + 3)
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "c2")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_start_produce));
            else {
                if (j - 2 <= num_shared_pieces_BC) {
                    symbol__abstract<string_symbol> s = get_symbol(controllable_events, "e" + (j - 2) + "C");
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_next));
                } else {
                    symbol__abstract<string_symbol> s = get_symbol(controllable_events, "e" + (j - 2 + num_nonshared_pieces_B) + "C");
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_next));
                }
            }
            if (j > 1) {
                for (symbol__abstract<string_symbol> s : controllable_events) {
                    if (event_specific_C.contains(s))
                        continue;
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_cur));
                }
                for (symbol__abstract<string_symbol> s : uncontrollable_events) {
                    if (event_specific_C.contains(s))
                        continue;
                    step_labels.add(new epda_step_label<>(
                            state_cur,
                            new option__Some<>(s),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            Collections.singletonList(get_symbol(stack, "☐")),
                            state_cur));
                }
            }
        }
//        System.out.println(states);
//        for (epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> elem : step_labels)
//            System.out.println(elem);
//        System.out.println(step_labels.size());

        List<symbol__abstract<string_symbol>> events = new ArrayList<>();
        events.addAll(controllable_events);
        events.addAll(uncontrollable_events);
        return new epda<>(
                states,
                events,
                stack,
                step_labels,
                get_symbol(states, "machineC_state1"),
                get_symbol(stack, "☐"),
                Collections.singletonList(get_symbol(states, "machineC_state2")));
    }

    private static symbol__abstract<string_symbol>
    get_symbol(List<symbol__abstract<string_symbol>> list, String name) {
        for (symbol__abstract<string_symbol> s : list)
            if (((symbol__cons_symbol_base<string_symbol>) s).get_value().get_value().equals(name))
                return s;
        return null;
    }

    private static epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
    generate_machines_A(
            List<symbol__abstract<string_symbol>> stack,
            List<symbol__abstract<string_symbol>> controllable_events,
            List<symbol__abstract<string_symbol>> uncontrollable_events,
            int concurrent_orders,
            int num_A_machines) {
        //System.out.println("generate_machines_A " + num_A_machines);
        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> result = null;
        for (int i = 1; i <= num_A_machines; i++) {
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    machine_Ai = generate_machines_Ai(stack, controllable_events, uncontrollable_events, concurrent_orders, i);
            if (!machine_Ai.is_valid(true))
                throw new RuntimeException();
            if (result == null)
                result = machine_Ai;
            else {
                result = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(result, machine_Ai));
            }
            if (!result.is_valid(true))
                throw new RuntimeException();
        }
        if (!result.is_valid(true))
            throw new RuntimeException();
        return result;
    }

    private static epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
    generate_machines_Ai(
            List<symbol__abstract<string_symbol>> stack,
            List<symbol__abstract<string_symbol>> controllable_events,
            List<symbol__abstract<string_symbol>> uncontrollable_events,
            int concurrent_orders,
            int num) {
        //System.out.println("generate_machines_Ai" + num);
//        System.out.println("machine A" + num);
        List<symbol__abstract<string_symbol>> states = new ArrayList<>();
        List<epda_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> step_labels = new ArrayList<>();
        for (int j = 1; j <= concurrent_orders + 1; j++)
            states.add(new symbol__cons_symbol_base<>(new string_symbol("machineA" + num + "_state" + j)));

        for (int j = 1; j <= concurrent_orders + 1; j++) {
            symbol__abstract<string_symbol> state_prev = get_symbol(states, "machineA" + num + "_state" + (j - 1));
            symbol__abstract<string_symbol> state_cur = get_symbol(states, "machineA" + num + "_state" + j);
            symbol__abstract<string_symbol> state_next = get_symbol(states, "machineA" + num + "_state" + (j + 1));
            Set<symbol__abstract<string_symbol>> exiting = new HashSet<>();
            exiting.add(get_symbol(uncontrollable_events, "d" + num));
            exiting.add(get_symbol(controllable_events, "o" + num));
            if (state_prev != null) {
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(uncontrollable_events, "d" + num)),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_prev));
            }
            if (state_next != null) {
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(get_symbol(controllable_events, "o" + num)),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_next));
            }
            for (symbol__abstract<string_symbol> s : controllable_events) {
                if (exiting.contains(s))
                    continue;
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(s),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_cur));
            }
            for (symbol__abstract<string_symbol> s : uncontrollable_events) {
                if (exiting.contains(s))
                    continue;
                step_labels.add(new epda_step_label<>(
                        state_cur,
                        new option__Some<>(s),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        Collections.singletonList(get_symbol(stack, "☐")),
                        state_cur));
            }
        }
//        for (epda_step_label<string_symbol, string_symbol, string_symbol> elem : step_labels)
//            System.out.println(elem);
//        System.out.println(step_labels.size());
        List<symbol__abstract<string_symbol>> events = new ArrayList<>();
        events.addAll(controllable_events);
        events.addAll(uncontrollable_events);
        return new epda<>(
                states,
                events,
                stack,
                step_labels,
                get_symbol(states, "machineA" + num + "_state1"),
                get_symbol(stack, "☐"),
                states);
    }

    public void run() {
        log.info("now starting computation of controller");
        if (debug_computations)
            Config.instance.output(spec);
        option__abstract<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>
                result = null;
        if (run_controller_synthesis)
            result = Algorithm_Definition__Construct_Controller.F_DPDA_DFA_CC(spec, plant, sigmaUC);
        log.info("now ending computation of controller");

        if (debug_computations) {
            if (result instanceof option__Some) {
                epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        resultX = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) result).the();
                for (General_Test_Sequence<symbol__abstract<string_symbol>> testSequence : tests.get_value()) {
                    Boolean result_value = epda.check_word_inclusion(resultX, testSequence.get_sequence().get_value(), testSequence.get_expected());
                    if (result_value == null)
                        throw new RuntimeException();
                    if (result_value)
                        System.out.println("TEST SUCCEEDED");
                    else {
                        System.out.println("TEST FAILED");
                        assert false;
                    }
                }

            } else {
                System.out.println("no controller obtained");
            }
        }
        Config.instance.local_solver.kill_runnables();
        Config.instance.measuring_thread.abort();
    }

    private void
    generate(
            int num_shared_pieces_BC,
            int num_nonshared_pieces_B,
            int num_nonshared_pieces_C,
            int stack_lengthm,
            int concurrent_orders) {
        List<symbol__abstract<string_symbol>> controllable_events = new ArrayList<>();
        List<symbol__abstract<string_symbol>> non_synchronization_events_B = new ArrayList<>();
        List<symbol__abstract<string_symbol>> non_synchronization_events_C = new ArrayList<>();
        List<symbol__abstract<string_symbol>> stack = new ArrayList<>();
        List<symbol__abstract<string_symbol>> container_events = new ArrayList<>();
        List<symbol__abstract<string_symbol>> uncontrollable_events = new ArrayList<>();
        stack.add(new symbol__cons_symbol_base<>(new string_symbol("☐")));
        symbol__abstract<string_symbol> circ = new symbol__cons_symbol_base<>(new string_symbol("○"));
        container_events.add(circ);
        stack.add(circ);
        for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_B + num_nonshared_pieces_C; i++) {
            symbol__abstract<string_symbol> ei = new symbol__cons_symbol_base<>(new string_symbol("e" + i));
            container_events.add(ei);
            stack.add(ei);
            symbol__abstract<string_symbol> oi = new symbol__cons_symbol_base<>(new string_symbol("o" + i));
            controllable_events.add(oi);
            stack.add(oi);
            container_events.add(oi);
            symbol__abstract<string_symbol> di = new symbol__cons_symbol_base<>(new string_symbol("d" + i));
            uncontrollable_events.add(di);
            if (i <= num_shared_pieces_BC) {
                symbol__abstract<string_symbol> eiB = new symbol__cons_symbol_base<>(new string_symbol("e" + i + "B"));
                controllable_events.add(eiB);
                non_synchronization_events_B.add(eiB);
                symbol__abstract<string_symbol> eiC = new symbol__cons_symbol_base<>(new string_symbol("e" + i + "C"));
                controllable_events.add(eiC);
                non_synchronization_events_C.add(eiC);
            } else if (i <= num_shared_pieces_BC + num_nonshared_pieces_B) {
                symbol__abstract<string_symbol> eiB = new symbol__cons_symbol_base<>(new string_symbol("e" + i + "B"));
                controllable_events.add(eiB);
                non_synchronization_events_B.add(eiB);
            } else {
                symbol__abstract<string_symbol> eiC = new symbol__cons_symbol_base<>(new string_symbol("e" + i + "C"));
                controllable_events.add(eiC);
                non_synchronization_events_C.add(eiC);
            }
        }
        symbol__abstract<string_symbol> s = new symbol__cons_symbol_base<>(new string_symbol("s"));
        controllable_events.add(s);
        non_synchronization_events_B.add(s);
        non_synchronization_events_C.add(s);
        {
            symbol__abstract<string_symbol> si = new symbol__cons_symbol_base<>(new string_symbol("s1"));
            controllable_events.add(si);
            non_synchronization_events_B.add(si);
            symbol__abstract<string_symbol> ti = new symbol__cons_symbol_base<>(new string_symbol("t1"));
            stack.add(ti);
            uncontrollable_events.add(ti);
            //non_synchronization_events_B.add(ti);
            symbol__abstract<string_symbol> ci = new symbol__cons_symbol_base<>(new string_symbol("c1"));
            controllable_events.add(ci);
            non_synchronization_events_B.add(ci);
        }
        {
            symbol__abstract<string_symbol> si = new symbol__cons_symbol_base<>(new string_symbol("s2"));
            controllable_events.add(si);
            non_synchronization_events_C.add(si);
            symbol__abstract<string_symbol> ti = new symbol__cons_symbol_base<>(new string_symbol("t2"));
            stack.add(ti);
            uncontrollable_events.add(ti);
            //non_synchronization_events_C.add(ti);
            symbol__abstract<string_symbol> ci = new symbol__cons_symbol_base<>(new string_symbol("c2"));
            controllable_events.add(ci);
            non_synchronization_events_C.add(ci);
        }

        if (debug_computations) {
            // TODO: more tests?
            List<General_Test_Sequence<symbol__abstract<string_symbol>>> test_list = new ArrayList<>();
            // now invariants
            {
                List<symbol__abstract<string_symbol>> testcase = new ArrayList<>();
                testcase.add(get_symbol(controllable_events, "s"));
                testcase.add(get_symbol(uncontrollable_events, "t1"));
                for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_B; i++) {
                    testcase.add(get_symbol(controllable_events, "o" + i));
                }
                for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_B; i++) {
                    testcase.add(get_symbol(uncontrollable_events, "d" + i));
                }
                testcase.add(get_symbol(controllable_events, "s1"));
                for (int i = 1; i <= num_shared_pieces_BC + num_nonshared_pieces_B; i++) {
                    testcase.add(get_symbol(controllable_events, "e" + i + "B"));
                }
                testcase.add(get_symbol(controllable_events, "c1"));
                test_list.add(new General_Test_Sequence<>("INVsequenceA", "MARK", new General_List(testcase)));
            }
            Config.instance.set_invariants(new General_List<>(new ArrayList<>(test_list)));
            // now final conditions
            {
                List<symbol__abstract<string_symbol>> testcase = new ArrayList<>();
                testcase.add(get_symbol(controllable_events, "s"));
                testcase.add(get_symbol(uncontrollable_events, "t2"));
                for (int i = 0; i < stack_lengthm; i++) {
                    testcase.add(get_symbol(controllable_events, "o" + (num_shared_pieces_BC + num_nonshared_pieces_B)));
                    testcase.add(get_symbol(uncontrollable_events, "d" + (num_shared_pieces_BC + num_nonshared_pieces_B)));
                }
                testcase.add(get_symbol(controllable_events, "s2"));
                test_list.add(new General_Test_Sequence<>("sequence_with_blocking", "REJECT", new General_List(testcase)));
            }

            tests = new General_List<>(test_list);


        }

        //System.out.println(stack);
        //System.out.println(controllable_events);
        //System.out.println(uncontrollable_events);
        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                machinesA = generate_machines_A(stack, controllable_events, uncontrollable_events, concurrent_orders, num_nonshared_pieces_B + num_nonshared_pieces_C + num_shared_pieces_BC);
//        machinesA.print_epda_info("machinesA", 0);
        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                machineB = generate_machine_B(stack, non_synchronization_events_B, controllable_events, uncontrollable_events, num_shared_pieces_BC, num_nonshared_pieces_B, num_nonshared_pieces_C);
        if (debug_computations && !machineB.is_valid(true))
            throw new RuntimeException();
        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                machineC = generate_machine_C(stack, non_synchronization_events_C, controllable_events, uncontrollable_events, num_shared_pieces_BC, num_nonshared_pieces_B, num_nonshared_pieces_C);
        if (debug_computations && !machineC.is_valid(true))
            throw new RuntimeException();

        Config.instance.check_invariants(machinesA);
        Config.instance.check_invariants(machineB);
        Config.instance.check_invariants(machineC);

        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                plant = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(machineC,
                T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(machineB,
                        machinesA))));


        if (debug_computations)
            plant.print_epda_info("plant", 0);
        plant = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) T08_MINIMIZE_DFA.MINIMIZE_DFA(plant)).the();
        if (debug_computations)
            plant.print_epda_info("plant", 0);
//        generate_machine_C(stack, controllable_events, uncontrollable_events, num_shared_pieces_BC, num_nonshared_pieces_B, num_nonshared_pieces_C);

        epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                spec = generate_machine_spec(stack, controllable_events, uncontrollable_events, concurrent_orders, stack_lengthm, container_events, num_shared_pieces_BC + num_nonshared_pieces_B + num_nonshared_pieces_C);
        spec = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(spec));
        if (debug_computations && !spec.is_valid(true))
            throw new RuntimeException();
        if (debug_computations)
            spec.print_epda_info("spec", 0);

        this.plant = plant;
        this.spec = spec;
        this.sigmaUC = uncontrollable_events;


    }

}

