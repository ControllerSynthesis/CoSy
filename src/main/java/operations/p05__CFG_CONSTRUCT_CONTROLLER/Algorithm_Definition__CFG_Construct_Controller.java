package operations.p05__CFG_CONSTRUCT_CONTROLLER;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T06_CFG_WRAP_IN_SYMBOL_ABSTRACT;
import operations.ADDITIONAL_OPERATIONS.T10_SDPDA_TO_LR_OPT2;
import operations.p01__ENFORCE_BLOCKFREE.*;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT_OPT;
import operations.p04__CONSTRUCT_CONTROLLER.Algorithm_Definition__Construct_Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Algorithm_Definition__CFG_Construct_Controller {
    public static int INDEX = 1;
    public static String PATH = "out/test/cosy/CFG_APPROACH/";
    private static Logger log = LogManager.getLogger(Algorithm_Definition__CFG_Construct_Controller.class.getName());

    private Algorithm_Definition__CFG_Construct_Controller() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General>
    cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<event>>
    split_cfg_weak(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<event>> cfg,
            Set<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>> nonterminals_to_be_splitted) {
        List<cfg_step_label<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<event>>> delta = new ArrayList<>();
        List<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>> nonterminals = new ArrayList<>();
        nonterminals.add(cfg.get_cfg_initial());

        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, Integer> index = new HashMap<>();
        for (symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>> elem : cfg.get_cfg_nonterminals())
            index.put(elem, 0);
        index.put(cfg.get_cfg_initial(), 1);

        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>>
                nonterminals_this_path = new HashMap<>();
        nonterminals_this_path.put(cfg.get_cfg_initial(), cfg.get_cfg_initial());

        for (symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>> nonterminal : cfg.get_cfg_nonterminals())
            if (!nonterminals_to_be_splitted.contains(nonterminal) && !nonterminals_this_path.containsKey(nonterminal)) {
                nonterminals_this_path.put(nonterminal, nonterminal);
                nonterminals.add(nonterminal);
                delta.addAll(cfg.get_cfg_step_labels_by_lhs().get(nonterminal));
            }

        fill_recursively_weak(cfg, index, cfg.get_cfg_initial(), delta, nonterminals, nonterminals_this_path);

        return new cfg<>(nonterminals, cfg.get_cfg_events(), cfg.get_cfg_initial(), delta);
    }

    private static <state extends General, event extends General>
    void
    fill_recursively_weak(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event> cfg,
            Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, Integer> index,
            symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>> current,
            List<cfg_step_label<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event>> delta,
            List<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>> nonterminals,
            Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>> nonterminals_this_path) {
        for (cfg_step_label<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event>
                step : cfg.get_cfg_step_labels_by_lhs().getOrDefault(current, new ArrayList<>())) {
            List<two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event>>
                    rhs = new ArrayList<>();
            for (two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event>
                    elem : step.get_prod_rhs()) {
                if (elem instanceof two_elements__teB)
                    rhs.add(elem);
                else if (elem instanceof two_elements__teA) {
                    symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>
                            nonterminalX = ((two_elements__teA<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, event>) elem).get_value();
                    tuple3<General_Integer, symbol__abstract<state>, General_Integer>
                            nonterminal = ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>) nonterminalX).get_value();
                    if (nonterminals_this_path.containsKey(nonterminalX)) {
                        rhs.add(new two_elements__teA<>(nonterminals_this_path.get(nonterminalX)));
                    } else {
                        int this_index = index.get(nonterminalX);
                        index.put(nonterminalX, this_index + 1);
                        symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>
                                replacement = new symbol__cons_symbol_base<>(new tuple3<>(nonterminal.get_value1(), nonterminal.get_value2(), new General_Integer(this_index)));
                        rhs.add(new two_elements__teA<>(replacement));
                        nonterminals.add(replacement);
                        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<state>, General_Integer>>>
                                nonterminals_this_path_updated = new HashMap<>(nonterminals_this_path);
                        nonterminals_this_path_updated.put(nonterminalX, replacement);
                        fill_recursively_weak(cfg, index, nonterminalX, delta, nonterminals, nonterminals_this_path_updated);
                    }
                } else throw new RuntimeException();
            }
            System.out.println(step + "\n  " + new cfg_step_label<>(nonterminals_this_path.get(current), rhs));
            delta.add(new cfg_step_label<>(nonterminals_this_path.get(current), rhs));
        }
    }

    private static <stateB extends General, event extends General, stackB extends General>
    tuple2<option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, General_Boolean>
    F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_one(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>> C,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC, General_Map<symbol__abstract<stateB>,
            General_Set<symbol__abstract<event>>> plant_states_exited_by_event2) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_one");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_one: " + "C=" + C.log_info() + ", P=" + P.log_info());
        tuple2<option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, General_Boolean>
                result = F_UNVERIFIED_CFG_DFA_EC(C, plant_states_exited_by_event2);
        if (result.get_value1() instanceof option__None)
            result = new tuple2<>(new option__None<>(), new General_Boolean(false));
        else {
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                    Cp = ((option__Some<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>)
                    result.get_value1()).the();
            if (result.get_value2().get_value()) {
                result = new tuple2<>(new option__Some<>(Cp), new General_Boolean(false));
            } else {
                option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
                        resultX = T03_06_05_CFG_TRIM.F_CFG_TRIM(Cp);
                result = new tuple2<>(resultX, new General_Boolean(resultX instanceof option__Some));
            }
        }
        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_one");
        return result;
    }

    public static String get_prefix() {
        return String.format("%03d", INDEX);
    }

    public static void export(String path, String filename, String data) {
        try {
            File file = new File(path + get_prefix() + filename);
            if (file.getParentFile() != null)
                file.getParentFile().mkdirs();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                bufferedWriter.write(data);
                bufferedWriter.close();
            }
        } catch (IOException e) {
        }
        INDEX++;
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    Map<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
    F_UNVERIFIED_PLANT_STATE_WITH_INDEX(
            cfg<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>, symbol__abstract<event>> C) {
        Map<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
                nonterminal_to_plant_state_with_index = new HashMap<>();

        Map<symbol__abstract<stateB>, Integer> occurence = new HashMap<>();
        for (symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>
                elem : C.get_cfg_nonterminals()) {
            symbol__abstract<stateB> plant_state = select(elem).getElemD();
            occurence.putIfAbsent(plant_state, 0);
            int current = occurence.get(plant_state);
            occurence.put(plant_state, current + 1);
            nonterminal_to_plant_state_with_index.put(elem, new symbol__cons_symbol_base<>(new tuple3<>(new General_Integer(current), plant_state, new General_Integer(0))));
        }
        return nonterminal_to_plant_state_with_index;
    }

    private static <stateB extends General, event extends General, stackB extends General>
    tuple2<option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, General_Boolean>
    F_UNVERIFIED_CFG_DFA_EC(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>> splitted,
            General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>> plant_states_exited_by_event2) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DFA_EC");
        final boolean DEBUG = true;

        if (DEBUG) {
            export_splitted(splitted, "__EC__", plant_states_exited_by_event2);

            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                    collated = T01_COLLATE_CFG.COLLATE_CFG(splitted, 5);
            export_splitted(collated, "__EC_Collated__", plant_states_exited_by_event2);

        }
        // =============================================================================================================

        symbol__abstract<event>
                Do = T01_FRESH.F_FRESH(splitted.get_cfg_events());
        symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>
                Sp = T01_FRESH.F_FRESH(splitted.get_cfg_nonterminals());
        cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                augmented_grammar = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(splitted, Sp, Do);
        epda<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>, General_Integer>
                lr1_machine = T05_04_LR_MACHINE.F_LR_MACHINE(augmented_grammar, 1);

        lr1_machine.get_map_source(lr1_machine.get_epda_initial());// build up cache
        Set<General_Tuple3<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, Boolean, Boolean>>
                checked = lr1_machine.get_epda_states().parallelStream().map(state -> check_state(state, plant_states_exited_by_event2, lr1_machine)).flatMap(set -> set.stream()).collect(Collectors.toSet());
        Set<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
                proven_alive_nonterminals =
                checked.parallelStream().filter(triple -> !triple.getElemC()).map(triple -> triple.getElemA()).collect(Collectors.toSet());
        Set<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
                dead_nonterminals =
                checked.parallelStream().filter(triple -> triple.getElemC()
                        //&& !proven_alive_nonterminals.contains(triple.getElemA())
                ).map(triple -> triple.getElemA()).collect(Collectors.toSet());

        if (DEBUG)

        {
            System.out.println("DEBUG START: proven_alive_nonterminals");
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : proven_alive_nonterminals) {
                System.out.println(elem);
            }
            System.out.println("DEBUG END: proven_alive_nonterminals");
            System.out.println("DEBUG START: dead_nonterminals");
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : dead_nonterminals) {
                System.out.println(elem);
            }
            System.out.println("DEBUG END: dead_nonterminals");
            System.out.println("DEBUG START: dead and alive nonterminals");
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : dead_nonterminals) {
                if (proven_alive_nonterminals.contains(elem))
                    System.out.println(elem);
            }
            System.out.println("DEBUG END: dead and alive nonterminals");
            System.out.println("DEBUG START: further checked");
            for (General_Tuple3<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, Boolean, Boolean> elem : checked) {
                if (!proven_alive_nonterminals.contains(elem.getElemA()) && !dead_nonterminals.contains(elem.getElemA()))
                    System.out.println(elem);
            }
            System.out.println("DEBUG END: further checked");
        }


        if (DEBUG)

        {
            Map<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, String>
                    fill = new HashMap<>();
            Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, string_symbol>
                    naming = get_renaming(augmented_grammar);

            for (General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
                    state : lr1_machine.get_epda_states()) {
                boolean requires_some_uncontrollable_event = false;
                boolean misses_some_uncontrollable_event = false;

                for (cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                        item : state.get_value()) {
                    if (!item.get_cfg_item_rhs1().isEmpty())
                        continue;
                    symbol__abstract<stateB>
                            plant_state = null;
                    if (item.get_cfg_item_lhs() instanceof symbol__cons_symbol_base) {
                        plant_state = ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>)
                                item.get_cfg_item_lhs()).get_value().get_value2();
                    } else {
                        continue;
                    }
                    if (plant_states_exited_by_event2.get_value().get(plant_state) == null)
                        continue;
                    requires_some_uncontrollable_event = true;


                    Set<symbol__abstract<event>> first_symbols = new HashSet<>();
                    for (epda_step_label<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>, General_Integer>
                            step_label : lr1_machine.get_map_source(state)) {
                        if (step_label.get_edge_event() instanceof option__Some) {
                            two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                                    elem = ((option__Some<two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>) step_label.get_edge_event()).the();
                            if (elem instanceof two_elements__teB)
                                first_symbols.add(((two_elements__teB<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>) elem).get_value());
                        }
                    }
                    if (!first_symbols.containsAll(plant_states_exited_by_event2.get_value().get(plant_state).get_value())) {
                        misses_some_uncontrollable_event = true;
                        break;
                    }
                }
                if (requires_some_uncontrollable_event) {
                    if (!misses_some_uncontrollable_event)
                        fill.put(state, "#9FF781");
                    else
                        fill.put(state, "#FA8258");
                } else
                    fill.put(state, "#81BEF7");

            }
            print_machineX(lr1_machine, naming, null, false, fill, "__EC__epda_machine.dot");
        }

        // =============================================================================================================

        tuple2<option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, General_Boolean>
                result = null;
        if (dead_nonterminals.isEmpty())

        {
            result = new tuple2<>(new option__Some<>(splitted), new General_Boolean(true));
        } else

        {
            result = new tuple2<>(remove_nonterminals(splitted, dead_nonterminals), new General_Boolean(false));
        }
        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DFA_EC");
        return result;
    }

    private static <nonterminal extends General, event extends General>
    option__abstract<cfg<nonterminal, event>>
    remove_nonterminals(
            cfg<nonterminal, event> cfg,
            Set<nonterminal> to_be_removed) {
        if (to_be_removed.contains(cfg.get_cfg_initial()))
            return new option__None<>();
        Set<nonterminal>
                remaining_nonterminals = cfg.get_cfg_nonterminals().parallelStream().filter(nonterminal -> !to_be_removed.contains(nonterminal)).collect(Collectors.toSet());
        cfg<nonterminal, event> resulting_cfg = new cfg<>(
                new ArrayList<>(remaining_nonterminals),
                cfg.get_cfg_events(),
                cfg.get_cfg_initial(),
                cfg.get_cfg_step_labels().parallelStream().filter(step_label ->
                        remaining_nonterminals.contains(step_label.get_prod_lhs())
                                && remaining_nonterminals.containsAll(two_elements__teA.filter(step_label.get_prod_rhs()))
                ).collect(Collectors.toList())
        );

        return new option__Some<>(resulting_cfg);
    }

    private static <stateB extends General, event extends General>
    Set<General_Tuple3<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, Boolean, Boolean>>
    check_state(
            General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>> state,
            General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>> plant_states_exited_by_event2,
            epda<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>, General_Integer> lr1_machine) {
        Set<General_Tuple3<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, Boolean, Boolean>>
                result = new HashSet<>();
        boolean requires_some_uncontrollable_event = false;
        boolean misses_some_uncontrollable_event = false;

        for (cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                item : state.get_value()) {
            if (!item.get_cfg_item_rhs1().isEmpty())
                continue;
            symbol__abstract<stateB>
                    plant_state = null;
            if (item.get_cfg_item_lhs() instanceof symbol__cons_symbol_base) {
                plant_state = ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>) item.get_cfg_item_lhs()).get_value().get_value2();
            } else {
                continue;
            }
            if (plant_states_exited_by_event2.get_value().get(plant_state) == null)
                continue;
            requires_some_uncontrollable_event = true;


            Set<symbol__abstract<event>> first_symbols = new HashSet<>();
            for (epda_step_label<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>, General_Integer>
                    step_label : lr1_machine.get_map_source(state)) {
                if (step_label.get_edge_event() instanceof option__Some) {
                    two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                            elem = ((option__Some<two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>) step_label.get_edge_event()).the();
                    if (elem instanceof two_elements__teB)
                        first_symbols.add(((two_elements__teB<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>) elem).get_value());
                }
            }
            if (!first_symbols.containsAll(plant_states_exited_by_event2.get_value().get(plant_state).get_value())) {
                misses_some_uncontrollable_event = true;
            }
            result.add(new General_Tuple3<>(item.get_cfg_item_lhs(), requires_some_uncontrollable_event, misses_some_uncontrollable_event));
        }
        return result;
    }

    private static <stateB extends General, event extends General>
    Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, string_symbol>
    get_renaming(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>> augmented_grammar) {
        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, string_symbol>
                result = new HashMap<>();
        for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : augmented_grammar.get_cfg_nonterminals()) {
            if (elem instanceof symbol__cons_symbol_base) {
                tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>
                        casted = ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>) elem).get_value();
                result.put(elem, new string_symbol("(" + casted.get_value1().get_value() + "," + casted.get_value2() + "," + casted.get_value3().get_value() + ")"));
            } else {
                int casted = ((symbol__cons_symbol_nat<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>) elem).get_value();
                result.put(elem, new string_symbol("" + casted));
            }
        }
        return result;
    }

    public static <nonterminal extends General, event extends General>
    void
    print_grammarX(
            cfg<nonterminal, symbol__abstract<event>> C,
            Map<nonterminal, string_symbol> mapping,
            Map<nonterminal, String> fill,
            String title) {

        StringBuffer result = new StringBuffer();
        result.append("digraph G {" + System.lineSeparator());
        result.append("labelloc=\"t\";" + System.lineSeparator());
        result.append("label=\"" + get_prefix() + title + "\";" + System.lineSeparator());


        for (nonterminal elem : C.get_cfg_nonterminals()) {
            String s = (mapping == null ? elem.toString() : mapping.get(elem).toString());
            String color = (fill == null || fill.get(elem) == null ? "" : ",fillcolor=\"" + fill.get(elem) + "\",style=filled");
            result.append("\"" + s + "\" [shape=" + (elem.equals(C.get_cfg_initial()) ? "octagon" : "rectangle") + ",label=\"" + s + "\"" + color + "]" + System.lineSeparator());
        }

        int xIndex = 0;
        for (cfg_step_label<nonterminal, symbol__abstract<event>> elem : C.get_cfg_step_labels()) {
            String w = (mapping == null ? elem.get_prod_lhs().toString() : mapping.get(elem.get_prod_lhs()).toString());
            String label = "";
            List<String> nonterminals = new ArrayList<>();
            for (two_elements__abstract<nonterminal, symbol__abstract<event>> x : (elem.get_prod_rhs())) {
                if (x instanceof two_elements__teB) {
                    Object x_inner = ((two_elements__teB) x).get_value();
                    if (x_inner instanceof symbol__cons_symbol_base) {
                        label = ((symbol__cons_symbol_base) x_inner).get_value().toString();
                    } else
                        label = x.toString();
                } else {
                    String s = (mapping == null ? ((two_elements__teA) x).get_value().toString() : mapping.get(((two_elements__teA) x).get_value()).toString());
                    nonterminals.add(s);
                }
            }
            if (nonterminals.size() == 1) {
                result.append("\"" + w + "\"" + " -> ");
                result.append("\"" + nonterminals.get(0) + "\" ");
                if (label.length() > 0)
                    result.append("[label=\"" + label + "\"]");
                result.append(System.lineSeparator());
            }

            if (nonterminals.size() == 2) {
                result.append("\"" + w + "\"" + " -> ");
                xIndex++;
                result.append("\"x" + xIndex + "\"" + System.lineSeparator());
                result.append("\"x" + xIndex + "\" -> ");
                result.append("\"" + nonterminals.get(0) + "\" [label=\"1\"]" + System.lineSeparator());
                result.append("\"x" + xIndex + "\" -> ");
                result.append("\"" + nonterminals.get(1) + "\" [label=\"2\"]" + System.lineSeparator());
            }

            if (nonterminals.size() == 0) {
                result.append("\"" + w + "\"" + " -> ");
                result.append("\"" + w + "\" ");
                result.append("[label=\"" + "§" + "\"]");
                result.append(System.lineSeparator());

                //result.append("\"" + w + "\" [color=\"green\"]" + System.lineSeparator());
            }
        }
        result.append("}" + System.lineSeparator());
        export(PATH, title, result.toString());
    }


    public static <nonterminal extends General, event extends General>
    void
    print_machineX(
            epda<General_Set<cfg_item<nonterminal, symbol__abstract<event>>>, two_elements__abstract<nonterminal, symbol__abstract<event>>, General_Integer> lr1_machine,
            Map<nonterminal, string_symbol> mapping,
            Map<General_Set<cfg_item<nonterminal, symbol__abstract<event>>>, Integer> naming,
            boolean hidden_state_content,
            Map<General_Set<cfg_item<nonterminal, symbol__abstract<event>>>, String> fill,
            String title) {

        StringBuffer result = new StringBuffer();
        result.append("digraph G {" + System.lineSeparator());
        result.append("labelloc=\"t\";" + System.lineSeparator());
        result.append("label=\"" + get_prefix() + title + "\";" + System.lineSeparator());


        result.append("rankdir = LR;" + System.lineSeparator());


        if (naming == null)
            naming = new HashMap<>();
        int index = 0;
        for (General_Set<cfg_item<nonterminal, symbol__abstract<event>>> state : lr1_machine.get_epda_states()) {
            index++;

            naming.put(state, index);
            String state_string = "{" + index + " | {";
            boolean first = true;
            for (cfg_item<nonterminal, symbol__abstract<event>> item : state.get_value()) {
                String item_string = "";
                if (!first)
                    item_string += " | ";
                first = false;
                item_string += (mapping == null ? item.get_cfg_item_lhs().toString() : mapping.get(item.get_cfg_item_lhs()));
                item_string += "→";
                for (two_elements__abstract<nonterminal, symbol__abstract<event>> elem : item.get_cfg_item_rhs1()) {
                    item_string += "(" + print_element(elem, mapping) + ")";
                }
                item_string += ".";
                for (two_elements__abstract<nonterminal, symbol__abstract<event>> elem : item.get_cfg_item_rhs2()) {
                    item_string += "(" + print_element(elem, mapping) + ")";
                }
                item_string += ",";

                if (item.get_cfg_item_look_ahead().size() > 0)
                    item_string += print_element(new two_elements__teB<>(item.get_cfg_item_look_ahead().get(0)), mapping);

                state_string += item_string;
            }
            state_string += "}}";
            String color = (fill == null || fill.get(state) == null ? "" : ",fillcolor=\"" + fill.get(state) + "\",style=filled");
            if (hidden_state_content)
                result.append("\"" + index + "\" [shape=record,label=\"" + index + "\"" + color + "]" + System.lineSeparator());
            else
                result.append("\"" + index + "\" [shape=record,label=\"" + state_string + "\"" + color + "]" + System.lineSeparator());

        }
        for (epda_step_label<General_Set<cfg_item<nonterminal, symbol__abstract<event>>>, two_elements__abstract<nonterminal, symbol__abstract<event>>, General_Integer> elem : lr1_machine.get_epda_delta()) {
            String label = "";
            two_elements__abstract<nonterminal, symbol__abstract<event>>
                    x = ((option__Some<two_elements__abstract<nonterminal, symbol__abstract<event>>>) elem.get_edge_event()).the();

            label = print_element(x, mapping);

            boolean is_q1 = label.contains("q1") && false;
            boolean is_u = label.contains("u") && false;
            result.append("\"" + naming.get(elem.get_edge_src()) + "\""
                    + " -> " +
                    "\"" + naming.get(elem.get_edge_trg()) + "\""
                    + " [label=\"" + label + "\"" + (is_q1 || is_u ? ",color=red" : "") + "]" + System.lineSeparator()
            );
            //.toString().replaceAll("Some:","").replaceAll("teB:","").replaceAll("teA:","").replaceAll("old:","").replaceAll("nat:","")
        }
        result.append("}" + System.lineSeparator());
        export(PATH, title, result.toString());
    }

    private static <nonterminal extends General, event extends General>
    String
    print_element(two_elements__abstract<nonterminal, symbol__abstract<event>> elem, Map<nonterminal, string_symbol> mapping) {
        if (elem instanceof two_elements__teB) {
            Object x_inner = ((two_elements__teB) elem).get_value();
            if (x_inner instanceof symbol__cons_symbol_base) {
                return ((symbol__cons_symbol_base) x_inner).get_value().toString();
            } else
                return elem.toString();
        } else {
            String s = (mapping == null ? ((two_elements__teA) elem).get_value().toString() : mapping.get(((two_elements__teA) elem).get_value()).get_value());
            return s;
        }
    }


    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select(
            symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>> input) {
        l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>
                o1 = ((symbol__cons_symbol_base<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>) input).get_value();
        General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
                result = select_level2(o1);
        return result;
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level2(
            l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>> o1) {
        if (o1 instanceof l2_l3_nonterminals__cons_l2) {
            l2_l3_nonterminals__cons_l2<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>
                    c1 = (l2_l3_nonterminals__cons_l2<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
                    result = select_level3A(c1.get_value1());
            return new General_Tuple4<>("2" + result.getElemA(), ((symbol__cons_symbol_base) c1.get_value2()).get_value().toString(), result.getElemC(), result.getElemD());
        } else if (o1 instanceof l2_l3_nonterminals__cons_l3) {
            l2_l3_nonterminals__cons_l3<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>
                    c1 = (l2_l3_nonterminals__cons_l3<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
                    result = select_level3A(c1.get_value1());
            return new General_Tuple4<>("3" + result.getElemA(), ((symbol__cons_symbol_base) c1.get_value2()).get_value().toString(), result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level3A(
            state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>> o1) {
        if (o1 instanceof state_or_state__cons_state_or_state_old) {
            state_or_state__cons_state_or_state_old<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>
                    c1 = (state_or_state__cons_state_or_state_old<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level3(c1.get_value());
            return new General_Tuple4<>("o" + result.getElemA(), null, result.getElemC(), result.getElemD());
        } else if (o1 instanceof state_or_state__cons_state_or_state_new) {
            state_or_state__cons_state_or_state_new<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>
                    c1 = (state_or_state__cons_state_or_state_new<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level3(c1.get_value());
            return new General_Tuple4<>("n" + result.getElemA(), null, result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level3(
            state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> o1) {
        if (o1 instanceof state_or_edge_nat__cons_state_or_edge_nat_old) {
            state_or_edge_nat__cons_state_or_edge_nat_old<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge_nat__cons_state_or_edge_nat_old<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level4(c1.get_value());
            return new General_Tuple4<>("q" + result.getElemA(), null, result.getElemC(), result.getElemD());
        } else if (o1 instanceof state_or_edge_nat__cons_state_or_edge_nat_new) {
            state_or_edge_nat__cons_state_or_edge_nat_new<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge_nat__cons_state_or_edge_nat_new<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level4(c1.get_value1().get_edge_src()); // TODO: correct?
            return new General_Tuple4<>("e" + result.getElemA(), null, result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level4(
            state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> o1) {
        if (o1 instanceof state_or_edge__cons_state_or_edge_old) {
            state_or_edge__cons_state_or_edge_old<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_old<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level5(c1.get_value());
            return new General_Tuple4<>("s" + result.getElemA(), null, result.getElemC(), result.getElemD());
        } else if (o1 instanceof state_or_edge__cons_state_or_edge_new) {
            state_or_edge__cons_state_or_edge_new<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_new<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level5(c1.get_value().get_edge_src()); // TODO: correct?
            return new General_Tuple4<>("e" + result.getElemA(), null, result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level5(
            state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> o1) {
        if (o1 instanceof state_or_edge__cons_state_or_edge_old) {
            state_or_edge__cons_state_or_edge_old<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_old<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level6(c1.get_value());
            return new General_Tuple4<>("s" + result.getElemA(), null, result.getElemC(), result.getElemD());
        } else if (o1 instanceof state_or_edge__cons_state_or_edge_new) {
            state_or_edge__cons_state_or_edge_new<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_new<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level6(c1.get_value().get_edge_src()); // TODO: correct?
            return new General_Tuple4<>("e" + result.getElemA(), null, result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level6(
            state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>> o1) {
        if (o1 instanceof state_or_edge__cons_state_or_edge_old) {
            state_or_edge__cons_state_or_edge_old<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_old<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level7(c1.get_value());
            return new General_Tuple4<>("s" + result.getElemA(), null, result.getElemC(), result.getElemD());
        } else if (o1 instanceof state_or_edge__cons_state_or_edge_new) {
            state_or_edge__cons_state_or_edge_new<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>> c1 = (state_or_edge__cons_state_or_edge_new<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>) o1;
            General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>> result = select_level7(c1.get_value().get_edge_trg()); // TODO: correct?
            return new General_Tuple4<>("e" + result.getElemA(), null, result.getElemC(), result.getElemD());
        }
        throw new RuntimeException();
    }

    private static <stateA extends General, stateB extends General>
    General_Tuple4<String, String, symbol__abstract<stateA>, symbol__abstract<stateB>>
    select_level7(
            tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>> o1) {
        return new General_Tuple4<>("", null, o1.get_value1(), o1.get_value2());
    }

    private static <stateB extends General, event extends General, stackB extends General>
    option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
    F_UNVERIFIED_CFG_DPDA_DFA_CC__fp(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>> C,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC, General_Map<symbol__abstract<stateB>,
            General_Set<symbol__abstract<event>>> plant_states_exited_by_event2) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_UNVERIFIED_CFG_DPDA_DFA_CC__fp: " + "C=" + C.log_info() + ", P=" + P.log_info());
        option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
                result = Algorithm_Definition__Construct_Controller.FB_iterate(X -> F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_one(X, P, SigmaUC, plant_states_exited_by_event2), C);
        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp");
        return result;
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
    F_UNVERIFIED_CFG_DPDA_DFA_CC(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> S,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DPDA_DFA_CC");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_UNVERIFIED_CFG_DPDA_DFA_CC: " + "S=" + S.log_info() + ", P=" + P.log_info());
        option__abstract<tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
                General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>>
                optional_start_result = F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start(S, P, SigmaUC);
        option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
                result = null;
        if (optional_start_result instanceof option__Some) {
            tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
                    General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>
                    start_result = ((option__Some<tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
                    General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>>)
                    optional_start_result).the();
            result = F_UNVERIFIED_CFG_DPDA_DFA_CC__fp(start_result.get_value1(), P, SigmaUC, start_result.get_value2());
        } else
            result = new option__None<>();
        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DPDA_DFA_CC");
        return result;
    }

    private static <stateA extends General, event extends General, stackA extends General, stackB extends General, stateB extends General>
    option__abstract<tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
            General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>>
    F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> S,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start");
        epda<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>
                product = Config.USE__Algorithm_Definition__DPDA_DFA_PRODUCT_OPT ?
                Algorithm_Definition__DPDA_DFA_PRODUCT_OPT.F_DPDA_DFA_PRODUCT_OPT(S, P) :
                Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(S, P);
        epda<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>,
                        symbol__abstract<event>, symbol__abstract<stackA>>,
                symbol__abstract<event>, symbol__abstract<stackA>>,
                symbol__abstract<event>, symbol__abstract<stackA>>
                sdpda = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(product);

        epda<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                G2 = T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.F_SDPDA_EUME(sdpda);

        option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>, symbol__abstract<event>>>
                optional_lr1_cfg = T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(G2, null);

        option__abstract<tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
                General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>>
                result;
        if (optional_lr1_cfg instanceof option__Some) {
            cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>, symbol__abstract<event>>
                    lr1_cfg = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>, symbol__abstract<event>>>)
                    optional_lr1_cfg).the();
            result = new option__Some<>(F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start2(T06_CFG_WRAP_IN_SYMBOL_ABSTRACT.CFG_WRAP_IN_SYMBOL_ABSTRACT(lr1_cfg), P, SigmaUC));
        } else {
            result = new option__None<>();
        }
        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start");
        return result;
    }

    private static <stateA extends General, event extends General, stackA extends General, stackB extends General, stateB extends General>
    tuple2<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>,
            General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>>
    F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start2(
            cfg<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>, symbol__abstract<event>> C,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start2");
        boolean DEBUG = true;

        if (DEBUG) {
            print_grammarX(C, null, null, "__start2__cfg.dot");
        }


        // =============================================================================================================

        Map<symbol__abstract<event>, Set<symbol__abstract<stateB>>> plant_states_exited_by_event = new HashMap<>();
        for (epda_step_label<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> elem : P.get_epda_delta()) {
            symbol__abstract<event> executed = ((option__Some<symbol__abstract<event>>) elem.get_edge_event()).get_value();
            if (SigmaUC.contains(executed)) {
                plant_states_exited_by_event.putIfAbsent(executed, new HashSet<>());
                plant_states_exited_by_event.get(executed).add(elem.get_edge_src());
            }
        }

        if (DEBUG) {
            System.out.println("DEBUG START: plant_states_exited_by_event");
            for (Map.Entry<symbol__abstract<event>, Set<symbol__abstract<stateB>>> elem : plant_states_exited_by_event.entrySet()) {
                String str = elem.getKey() + " -> ";
                boolean first = true;
                for (symbol__abstract<stateB> value : elem.getValue()) {
                    if (!first)
                        str += ", ";
                    first = false;
                    str += value.toString();
                }
                System.out.println(str);
            }
            System.out.println("DEBUG END: plant_states_exited_by_event");
        }

        General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>>
                plant_states_exited_by_event2 = new General_Map<>(new HashMap<>(), true);
        for (Map.Entry<symbol__abstract<event>, Set<symbol__abstract<stateB>>> elem : plant_states_exited_by_event.entrySet()) {
            for (symbol__abstract<stateB> state : elem.getValue()) {
                plant_states_exited_by_event2.get_value().putIfAbsent(state, new General_Set<symbol__abstract<event>>(new HashSet<>(), true));
                plant_states_exited_by_event2.get_value().get(state).get_value().add(elem.getKey());
            }
        }

        if (DEBUG) {
            System.out.println("DEBUG START: plant_states_exited_by_event2");
            for (Map.Entry<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>> elem : plant_states_exited_by_event2.get_value().entrySet()) {
                String str = elem.getKey() + " -> ";
                boolean first = true;
                for (symbol__abstract<event> value : elem.getValue().get_value()) {
                    if (!first)
                        str += ", ";
                    first = false;
                    str += value.toString();
                }
                System.out.println(str);
            }
            System.out.println("DEBUG END: plant_states_exited_by_event2");
        }

        // =============================================================================================================

        Map<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>,
                symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
                nonterminal_to_plant_state_with_index = F_UNVERIFIED_PLANT_STATE_WITH_INDEX(C);

        if (DEBUG) {
            System.out.println("DEBUG START: nonterminal_to_plant_state_with_index");
            for (Map.Entry<symbol__abstract<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>>, symbol__abstract<stackA>>>, symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>> elem : nonterminal_to_plant_state_with_index.entrySet()) {
                System.out.println(elem.getKey() + System.lineSeparator() + " -> " + elem.getValue());
            }
            System.out.println("DEBUG END: nonterminal_to_plant_state_with_index");
        }

        // =============================================================================================================

        cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                renamed = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC_inner(C, nonterminal_to_plant_state_with_index);

        // NOTE: applying this AFTER the other split is not least restrictive
        // as demonstrated by the example in the thesis
        cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                controller_with_CFG_PULL;
        if (true)
            controller_with_CFG_PULL = FUN_CFG_PULL(renamed);
        else
            controller_with_CFG_PULL = renamed;


        Set<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>> possibly_dead = new HashSet<>();

        for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : controller_with_CFG_PULL.get_cfg_nonterminals()) {
            symbol__abstract<stateB>
                    plant_state = ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>) elem).get_value().get_value2();
            if (plant_states_exited_by_event2.get_value().get(plant_state) == null)
                continue;
            List<two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>> w = new ArrayList<>();
            w.add(new two_elements__teA<>(elem));
            Set<List<symbol__abstract<event>>> first_strings = T04_CFG_FIRST.F_CFG_FIRST(controller_with_CFG_PULL, 1, w);
            Set<symbol__abstract<event>> first_symbols = new HashSet<>();
            for (List<symbol__abstract<event>> first_string : first_strings) {
                if (first_string.size() > 0)
                    first_symbols.add(first_string.get(0));
            }
            if (!first_symbols.containsAll(plant_states_exited_by_event2.get_value().get(plant_state).get_value()))
                possibly_dead.add(elem);
        }
        if (DEBUG) {
            System.out.println("DEBUG START: possibly_dead");
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : possibly_dead) {
                System.out.println(elem);
            }
            System.out.println("DEBUG END: possibly_dead");
        }

        Set<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>>
                ancestors_of_possibly_dead = coreachable(controller_with_CFG_PULL, possibly_dead);
        if (DEBUG) {
            System.out.println("DEBUG START: ancestors_of_possibly_dead");
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : ancestors_of_possibly_dead) {
                System.out.println(elem);
            }
            System.out.println("DEBUG END: ancestors_of_possibly_dead");
        }


        if (DEBUG) {
            Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, String> fill = new HashMap();
            for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : controller_with_CFG_PULL.get_cfg_nonterminals()) {
                if (possibly_dead.contains(elem))
                    fill.put(elem, "#FA8258");
                else if (ancestors_of_possibly_dead.contains(elem))
                    fill.put(elem, "#F4FA58");
                else
                    fill.put(elem, "#81BEF7");
            }
            print_grammarX(renamed, null, fill, "__start2__cfg_renamed.dot");
            print_grammarX(controller_with_CFG_PULL, null, fill, "__start2__cfg_extended.dot");


        }


        if (DEBUG) {
            symbol__abstract<event>
                    Do = T01_FRESH.F_FRESH(renamed.get_cfg_events());
            symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>
                    Sp = T01_FRESH.F_FRESH(renamed.get_cfg_nonterminals());
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                    augmented_grammar = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(controller_with_CFG_PULL, Sp, Do);
            epda<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>, General_Integer>
                    lr1_machine = T05_04_LR_MACHINE.F_LR_MACHINE(augmented_grammar, 0);
            print_machineX(lr1_machine, null, null, false, null, "__start2__epda_machine.dot");
        }

        // =============================================================================================================

        cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
                splitted = split_cfg_weak(controller_with_CFG_PULL, ancestors_of_possibly_dead);

//        cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>
//                splitted = renamed;
//        if (DEBUG) {
//            if (!splitted.is_valid())
//                throw new RuntimeException();
//
//            export_splitted(splitted, "__start2__", plant_states_exited_by_event2);
//        }

        Config.instance.measuring_thread.pop("F_UNVERIFIED_CFG_DPDA_DFA_CC__fp_start2");
        return new tuple2<>(splitted, plant_states_exited_by_event2);
    }

    private
    static <nonterminal extends General, event extends General>
    cfg<nonterminal, event>
    FUN_CFG_PULL(
            cfg<nonterminal, event> C) {

        List<cfg_step_label<nonterminal, event>>
                labels = new ArrayList<>();
        Map<nonterminal, Set<nonterminal>> weakly_reachable = new HashMap<>();
        for (cfg_step_label<nonterminal, event> elem : C.get_cfg_step_labels())
            if (elem.get_prod_rhs().size() == 1) {
                if (elem.get_prod_rhs().get(0) instanceof two_elements__teA) {
                    two_elements__teA<nonterminal, event> casted = (two_elements__teA<nonterminal, event>) elem.get_prod_rhs().get(0);
                    weakly_reachable.putIfAbsent(elem.get_prod_lhs(), new HashSet<>());
                    weakly_reachable.get(elem.get_prod_lhs()).add(casted.get_value());
                } else throw new RuntimeException();
            } else {
                // also consider the two nonterminal case!
                // add firstN and add secondN if "[] \in first(firstN)"
            }
        transitive_closure(weakly_reachable);
        for (Map.Entry<nonterminal, Set<nonterminal>> elem : weakly_reachable.entrySet()) {
            for (nonterminal nonterminal : elem.getValue())
                for (cfg_step_label<nonterminal, event> production : C.get_cfg_step_labels_by_lhs().get(nonterminal)) {
                    labels.add(new cfg_step_label<>(elem.getKey(), new ArrayList<>(production.get_prod_rhs())));
                }
        }

        labels.addAll(C.get_cfg_step_labels());
        return new cfg<>(C.get_cfg_nonterminals(), C.get_cfg_events(), C.get_cfg_initial(), labels);
    }

    public static void main(String[] args) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        map.put(0, new HashSet<>());
        map.put(1, new HashSet<>());
        map.put(2, new HashSet<>());
        map.put(3, new HashSet<>());
        map.put(4, new HashSet<>());
        map.put(5, new HashSet<>());
        map.get(0).add(0);
        map.get(0).add(1);
        map.get(0).add(3);
        map.get(1).add(2);
        map.get(2).add(0);
        map.get(3).add(4);
        map.get(4).add(5);
        transitive_closure(map);
        for (Map.Entry<Integer, Set<Integer>> elem : map.entrySet())
            System.out.println(elem);
    }

    private static <element> void transitive_closure(Map<element, Set<element>> map) {
        Set<element> todo = new HashSet<>(map.keySet());
//        for (Map.Entry<element, Set<element>> elem : map.entrySet())
//            System.out.println(elem);

        while (!todo.isEmpty()) {
//            System.out.println(todo);
            Set<element> newtodo = new HashSet<>();
            for (element nodeA : map.keySet()) {
//                System.out.println(nodeA);
                boolean updated = false;
                for (element nodeB : todo) {
                    if (map.get(nodeA).contains(nodeB)) {
//                        System.out.println("add: " + nodeB + " ; " + map.get(nodeB));
                        updated = updated || map.get(nodeA).addAll(map.get(nodeB));
                    }
                }
                if (updated)
                    newtodo.add(nodeA);

//                for (Map.Entry<element, Set<element>> elem : map.entrySet())
//                    System.out.println(elem);
            }
            todo = newtodo;
        }

    }

    private static <nonterminal extends General, event extends General>
    Set<nonterminal>
    coreachable(
            cfg<nonterminal, event> cfg,
            Set<nonterminal> start) {
        Set<nonterminal> result_nonterminals = new HashSet<>();
        List<nonterminal> todo = new ArrayList<>(start);
        result_nonterminals.addAll(start);
        while (!todo.isEmpty()) {
            nonterminal N = todo.remove(0);
            for (cfg_step_label<nonterminal, event> e : cfg.get_cfg_step_labels()) {
                if (result_nonterminals.contains(e.get_prod_lhs()))
                    continue;
                if (new HashSet<>(two_elements__teA.filter(e.get_prod_rhs())).contains(N)) {
                    result_nonterminals.add(e.get_prod_lhs());
                    todo.add(e.get_prod_lhs());
                }
            }
        }
        return result_nonterminals;
    }

    private static <stateB extends General, event extends General>
    void
    export_splitted(
            cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>> splitted,
            String prefix,
            General_Map<symbol__abstract<stateB>, General_Set<symbol__abstract<event>>> plant_states_exited_by_event2) {
        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, String> fill = new HashMap();
        for (symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>> elem : splitted.get_cfg_nonterminals()) {
            symbol__abstract<stateB>
                    plant_state =
                    ((symbol__cons_symbol_base<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>)
                            elem).get_value().get_value2();
            if (plant_states_exited_by_event2.get_value().get(plant_state) == null)
                continue;
            List<two_elements__abstract<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, symbol__abstract<event>>>
                    w = new ArrayList<>();
            w.add(new two_elements__teA<>(elem));
            Set<List<symbol__abstract<event>>> first_strings = T04_CFG_FIRST.F_CFG_FIRST(splitted, 1, w);
            Set<symbol__abstract<event>> first_symbols = new HashSet<>();
            for (List<symbol__abstract<event>> first_string : first_strings) {
                if (first_string.size() > 0)
                    first_symbols.add(first_string.get(0));
            }
            if (first_symbols.containsAll(plant_states_exited_by_event2.get_value().get(plant_state).get_value()))
                fill.put(elem, "#9FF781");
            else
                fill.put(elem, "#FA8258");
        }
        Map<symbol__abstract<tuple3<General_Integer, symbol__abstract<stateB>, General_Integer>>, string_symbol>
                naming = get_renaming(splitted);

        print_grammarX(splitted, naming, fill, prefix + "cfg_splitted.dot");
    }


}
