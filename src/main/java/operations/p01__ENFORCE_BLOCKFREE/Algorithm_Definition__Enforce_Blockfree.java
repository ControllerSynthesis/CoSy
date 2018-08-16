package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.*;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T02_REMOVE_UNUSED_STATE_STACK_EPDA;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.ADDITIONAL_OPERATIONS.T10_SDPDA_TO_LR_OPT2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Algorithm_Definition__Enforce_Blockfree {

    private static Logger log = LogManager.getLogger(Algorithm_Definition__Enforce_Blockfree.class.getName());

    private Algorithm_Definition__Enforce_Blockfree() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>>
    F_DPDA_EB_STD(epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>> G0) {
        Config.instance.measuring_thread.push("F_DPDA_EB_STD");
        epda<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>
                G1 = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(G0);
        epda<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<event>, symbol__abstract<stack>>
                G2 = T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.F_SDPDA_EUME(G1);
        option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>
                G3 = T03_06_07_SDPDA_TO_LR1_STD.F_SDPDA_TO_LR1_STD(G2);
        option__abstract<epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>> result = null;
        if (G3 instanceof option__None)
            result = new option__None<>();
        else {
            cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>
                    G3_Some =
                    ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>)
                            G3).the();
            cfg<symbol__abstract<state>, symbol__abstract<event>>
                    G = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(G3_Some);
            epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
                    RESULT = lr1_cfg_to_dpda__STD(G);
            result = new option__Some<>(RESULT);
        }
        Config.instance.measuring_thread.pop("F_DPDA_EB_STD");
        return result;
    }

    public static <state extends General, event extends General, stack extends General> epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>> lr1_cfg_to_dpda__STD(cfg<symbol__abstract<state>, symbol__abstract<event>> G) {
        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P0 = lr1_cfg_to_parser__STD(G);
        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P1 = T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE.F_PARSER_RITU(P0);
        parser<stack_and_event_or_stack__abstract<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P2 = T05_07_PARSER_REMOVE_TOP_RULES.F_PARSER_RTR(P1);
        parser<symbol__abstract<state>, symbol__abstract<event>, option__abstract<General_Integer>>
                P2p = T02_02_PARSER_TYPE_CONVERSION.F_PARSER_TC(P2);
        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E1 = T06_01_PARSER_TO_EDPDA.F_PARSER_TO_EDPDA(P2p, T01_FRESH.F_FRESH(P2p.get_parser_nonterms()));
        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E2 = T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(E1);
        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E3 = T07_02_DPDA_ENFORCE_ACCESSIBLE_STD.F_DPDA_EA_STD(E2);
        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
                RESULT = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(E3);
        return RESULT;
    }

    public static <state extends General, event extends General>
    parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
    lr1_cfg_to_parser__STD(cfg<symbol__abstract<state>, symbol__abstract<event>> G) {
        symbol__abstract<event>
                Do = T01_FRESH.F_FRESH(G.get_cfg_events());
        symbol__abstract<state>
                Sp = T01_FRESH.F_FRESH(G.get_cfg_nonterminals());
        cfg<symbol__abstract<state>, symbol__abstract<event>>
                Gp = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(G, Sp, Do);
        epda<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<state>, symbol__abstract<event>>, General_Integer>
                M = T05_04_LR_MACHINE.F_LR_MACHINE(Gp, 1);
        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P0 = T05_05_LR_PARSER.F_LR_PARSER(G, Gp, M, Do);
        return P0;
    }

    public static <state extends General, event extends General, stack extends General> epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>> lr1_cfg_to_dpda__OPT(cfg<symbol__abstract<state>, symbol__abstract<event>> G) {

        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P0 = lr1_cfg_to_parser__OPT(G);

        // compare_unmarked_language.instance.test("F_LR_PARSER(G, Gp, M, Do)", G, P0, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P1 = T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE.F_PARSER_RITU(P0);

        //compare_unmarked_language.instance.test("F_PARSER_RITU(P0)", P0, P1, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        parser<stack_and_event_or_stack__abstract<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P2 = T05_07_PARSER_REMOVE_TOP_RULES.F_PARSER_RTR(P1);

        //compare_unmarked_language.instance.test("F_PARSER_RTR(P1)", P1, P2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        parser<symbol__abstract<state>, symbol__abstract<event>, option__abstract<General_Integer>>
                P2p = T02_02_PARSER_TYPE_CONVERSION.F_PARSER_TC(P2);

        //compare_unmarked_language.instance.test("F_PARSER_TC(P2)", P2, P2p, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E1 = T06_01_PARSER_TO_EDPDA.F_PARSER_TO_EDPDA(P2p, T01_FRESH.F_FRESH(P2p.get_parser_nonterms()));

        //compare_unmarked_language.instance.test("F_PARSER_TO_EDPDA(P2p", P2p, E1, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E2 = T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(E1);

        // compare_unmarked_language.instance.test("F_EDPDA_TO_DPDA(E1)", E1, E2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (Config.instance.REMOVE_UNUSED_STATE_STACK_EPDA__before__F_DPDA_EA_OPT__in__F_DPDA_EB_OPT)
            E2 = T02_REMOVE_UNUSED_STATE_STACK_EPDA.REMOVE_UNUSED_STATE_STACK_EPDA(E2);

        // compare_unmarked_language.instance.test("REMOVE_UNUSED_STATE_STACK_EPDA(E2)", E1, E2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        E2 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(E2, Config.instance.getAPPROXIMATE_ACCESSIBLE__F_DPDA_EB_OPT());

        // compare_unmarked_language.instance.test("F_EPDA_AA(E2, 10)", E1, E2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<state>>
                E3 = Config.instance.DISABLE_ENFORCE_ACCESSIBLE_IN_ENFORCE_BLOCKFREE ? E2 : T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(E2, true);

        if (log.isInfoEnabled())
            log.info("T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT at blockfree: pre=" + E2.log_info() + ", post=" + E3.log_info());

        //compare_unmarked_language.instance.test("T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(E2)", E2, E3, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
                RESULT = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(E3);
        return RESULT;
    }

    public static <state extends General, event extends General>
    parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
    lr1_cfg_to_parser__OPT(cfg<symbol__abstract<state>, symbol__abstract<event>> G) {
        if (Config.COLLATE_CFG)
            G = T01_COLLATE_CFG.COLLATE_CFG(G, 0);

        symbol__abstract<event>
                Do = T01_FRESH.F_FRESH(G.get_cfg_events());
        symbol__abstract<state>
                Sp = T01_FRESH.F_FRESH(G.get_cfg_nonterminals());
        cfg<symbol__abstract<state>, symbol__abstract<event>>
                Gp = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(G, Sp, Do);

        check_equal_first_outer(Gp);


        epda<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, two_elements__abstract<symbol__abstract<state>, symbol__abstract<event>>, General_Integer>
                M = T05_04_LR_MACHINE.F_LR_MACHINE(Gp, 1);

        parser<General_Set<cfg_item<symbol__abstract<state>, symbol__abstract<event>>>, symbol__abstract<event>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<state>, symbol__abstract<event>>>>>
                P0 = T05_05_LR_PARSER.F_LR_PARSER(G, Gp, M, Do);
        return P0;
    }

    private static <nonterminal extends General, event extends General> void check_equal_first_outer(cfg<symbol__abstract<nonterminal>, symbol__abstract<event>> cfg) {
        if (false) {
            boolean found_error = check_equal_first(cfg);
            if (found_error) {
                while (true) {
                    found_error = false;
                    log.info("current cfg size: " + cfg.get_cfg_step_labels().size());
                    for (int i = 2; i <= 1024; i *= 2) {
                        found_error = false;
                        log.info("starting with i = " + i);
                        int max_remove = cfg.get_cfg_step_labels().size() / i;
                        if (i < 1024 && max_remove > 0) {
                            log.info("max_remove = " + max_remove);
                            List<cfg_step_label<symbol__abstract<nonterminal>, symbol__abstract<event>>> use_productions = new ArrayList<>(cfg.get_cfg_step_labels());
                            Random rand = new Random();
                            for (int j = 0; j < max_remove; j++) {
                                use_productions.remove(rand.nextInt(use_productions.size()));
                            }
                            cfg<symbol__abstract<nonterminal>, symbol__abstract<event>> cfg_test = new cfg<>(cfg.get_cfg_nonterminals(), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(use_productions));
                            option__abstract<cfg<symbol__abstract<nonterminal>, symbol__abstract<event>>> cfg_test_trim = T03_06_05_CFG_TRIM.F_CFG_TRIM(cfg_test);
                            if (cfg_test_trim instanceof option__Some) {
                                log.info("cfg is not empty after removal and trimming");
                                cfg<symbol__abstract<nonterminal>, symbol__abstract<event>> cfg_test_trim_the = ((option__Some<datatypes.formalisms.cfg<symbol__abstract<nonterminal>, symbol__abstract<event>>>) cfg_test_trim).the();
                                if (check_equal_first(cfg_test_trim_the)) {
                                    log.info("reduction preserving error found: removing (with deps): " + max_remove);
                                    cfg = cfg_test_trim_the;
                                    found_error = true;
                                    log.info("using trim cfg");
                                    break;
                                } else {
                                    log.info("error removed by removal and trimming!");
                                }
                            } else {
                                log.info("cfg is empty after removal and trimming");
                            }
                        } else {
                            for (cfg_step_label<symbol__abstract<nonterminal>, symbol__abstract<event>> elem : new ArrayList<>(cfg.get_cfg_step_labels())) {
                                Set<cfg_step_label<symbol__abstract<nonterminal>, symbol__abstract<event>>> productions = new HashSet<>(cfg.get_cfg_step_labels());
                                productions.remove(elem);
                                cfg<symbol__abstract<nonterminal>, symbol__abstract<event>> cfg_test = new cfg<>(cfg.get_cfg_nonterminals(), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(productions));
                                option__abstract<cfg<symbol__abstract<nonterminal>, symbol__abstract<event>>> cfg_test_trim = T03_06_05_CFG_TRIM.F_CFG_TRIM(cfg_test);
                                if (cfg_test_trim instanceof option__Some) {
                                    log.info("cfg is not empty after removal and trimming");
                                    cfg<symbol__abstract<nonterminal>, symbol__abstract<event>> cfg_test_trim_the = ((option__Some<datatypes.formalisms.cfg<symbol__abstract<nonterminal>, symbol__abstract<event>>>) cfg_test_trim).the();
                                    if (check_equal_first(cfg_test_trim_the)) {
                                        log.info("reduction preserving error found: removing (with deps): " + elem);
                                        cfg = cfg_test_trim_the;
                                        found_error = true;
                                        log.info("using trim cfg");
                                        break;
                                    } else {
                                        log.info("error removed by removal and trimming!");
                                    }
                                } else {
                                    log.info("cfg is empty after removal and trimming");
                                }
                            }
                            if (!found_error) {
                                break;
                            }
                        }
                        log.info("increase i");
                    }
                    if (found_error)
                        continue;
                    cfg = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(cfg);
                    if (check_equal_first(T01_COLLATE_CFG.COLLATE_CFG(cfg, 0))) {
                        cfg = T01_COLLATE_CFG.COLLATE_CFG(cfg, 0);
                    }
                    log.info("no further reduction preserving error found");
                    break;
                }
            }
            log.info("no error found");
        }
    }

    private static <nonterminal extends General, event extends General> boolean check_equal_first(cfg<nonterminal, event> cfg) {
        log.info("creating first (1/2)");
        T04_CFG_FIRST2.F_CFG_FIRST(cfg, 1, new ArrayList<>());
        log.info("creating first (2/2)");
        T04_CFG_FIRST.F_CFG_FIRST(cfg, 1, new ArrayList<>());
        log.info("comparing first");
        for (nonterminal elem : cfg.get_cfg_nonterminals()) {
            log.info("checking: " + elem);
            Set<List<event>> events_lists1 = T04_CFG_FIRST2.F_CFG_FIRST(cfg, 1, General_List.list_by_enumeration(new two_elements__teA<>(elem)));
            Set<List<event>> events_lists2 = T04_CFG_FIRST.F_CFG_FIRST(cfg, 1, General_List.list_by_enumeration(new two_elements__teA<>(elem)));
            log.info("checking1: " + events_lists1);
            log.info("checking2: " + events_lists2);
            if (!events_lists1.equals(events_lists2)) {
                log.info("found error");
                return true;
            }
        }
        return false;
    }


    public static <state extends General, event extends General, stack extends General>
    option__abstract<epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>>
    F_DPDA_EB_OPT(
            epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>> G0) {
        Config.instance.measuring_thread.push("F_DPDA_EB_OPT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_EB_OPT: " + "G0=" + G0.log_info());

        epda<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>
                G1 = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(G0);

        //compare_unmarked_language.instance.test("F_DPDA_TO_SDPDA(G0)",G0, G1, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        epda<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<event>, symbol__abstract<stack>>
                G2 = T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.F_SDPDA_EUME(G1);

        //compare_unmarked_language.instance.test("F_SDPDA_EUME(G1)",G1, G2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (Config.instance.REMOVE_UNUSED_STATE_STACK_EPDA__before__F_SDPDA_TO_LR1_OPT__in__F_DPDA_EB_OPT)
            G2 = T02_REMOVE_UNUSED_STATE_STACK_EPDA.REMOVE_UNUSED_STATE_STACK_EPDA(G2);

        //compare_unmarked_language.instance.test("REMOVE_UNUSED_STATE_STACK_EPDA(G2)",G1, G2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (false) {
            // for back to back testing of conversion to LR1
            epda G2prime = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(G2);
            while (true) {
                log.info("CHECK: " + G2prime.get_epda_delta().size());
                List<epda_step_label> all_productions = new ArrayList<>(G2prime.get_epda_delta());
                System.out.println(all_productions.size());
                boolean found = false;
                for (epda_step_label prod : all_productions) {
                    List<epda_step_label> use_now_prods = new ArrayList<>(all_productions);
                    use_now_prods.remove(prod);
                    epda G2primeCurrent = new epda(G2prime.get_epda_states(),
                            G2prime.get_epda_events(),
                            G2prime.get_epda_gamma(),
                            use_now_prods,
                            G2prime.get_epda_initial(),
                            G2prime.get_epda_box(),
                            G2prime.get_epda_marking());
                    option__abstract<cfg> G3X = T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(G2primeCurrent, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(G2primeCurrent), 1);
                    if (G3X instanceof option__None)
                        continue;
                    option__abstract<cfg> G3optX = null;
                    try {
                        G3optX = T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(G2primeCurrent, null);
                    } catch (Exception e) {
                        if (G2primeCurrent.get_epda_delta().size() == 10)
                            log.info("CHECK: ERROR: " + e.getStackTrace());
                        G2prime = G2primeCurrent;
                        found = true;
                        break;
                    }
                    if (G3optX instanceof option__None) {
                        log.info("CHECK: ERROR: G3optX NONE");
                        G2prime = G2primeCurrent;
                        found = true;
                        break;
                    }
                    cfg G3 = ((option__Some<cfg>) G3X).the();
                    cfg G3opt = ((option__Some<cfg>) G3optX).the();
                    if (G3.get_cfg_step_labels().size() != G3opt.get_cfg_step_labels().size()) {
                        log.info("CHECK: ERROR: G3opt " + G3opt.get_cfg_step_labels().size() + " / G3 " + G3.get_cfg_step_labels().size());
                        G2prime = G2primeCurrent;
                        found = true;
                        break;
                    }
                }
                if (!found)
                    break;
            }
            log.info("CHECK: " + G2prime.get_epda_delta().size() + " => " + G2prime);
        }

        option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>
                G3 =
                Config.USE_T09_SDPDA_TO_CFG_OPT2 ?
                        T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(G2, null) :
                        T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(G2, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(G2), 1);

        if (false) {
            // compare F_SDPDA_TO_LR_OPT2 with F_SDPDA_TO_LR1_OPT
            option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>
                    G3alt =
                    !Config.USE_T09_SDPDA_TO_CFG_OPT2 ?
                            T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(G2, null) :
                            T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(G2, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(G2), 1);

            if (log.isInfoEnabled()) {
                cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>
                        G3the = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>)
                        G3).the();
                cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>
                        G3altthe = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>)
                        G3alt).the();
                log.info(Config.callStack() + "COMPARE: G3: " + "cfg=" + G3the.log_info());
                log.info(Config.callStack() + "COMPARE: G3alt: " + "cfg=" + G3altthe.log_info());
            }
            System.out.println("FOO");
            if (true)
                System.exit(1);
        }


        option__abstract<epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>> result = null;
        if (G3 instanceof option__None)
            result = new option__None<>();
        else {
            cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>
                    G3_Some =
                    ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>)
                            G3).the();

//            if (log.isInfoEnabled())
//                log.info(Config.callStack() + "F_SDPDA_TO_LR1_OPT done: " + "G3_Some=" + G3_Some.log_info());


////            T09_SDPDA_TO_CFG_OPT2.F_SDPDA_TO_CFG_OPT2(T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(G2), null, null);
//            option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>
//                    G3_opt2 = T09_SDPDA_TO_CFG_OPT2.F_SDPDA_TO_CFG_OPT2(G2, null, G3_Some);
//            cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>
//                    G3_opt2_Some =
//                    ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_state__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>, symbol__abstract<event>, symbol__abstract<stack>>>, symbol__abstract<stack>>, symbol__abstract<event>>>)
//                            G3_opt2).the();
//            log.info("SIZE: G3_Some: " + G3_Some.get_cfg_step_labels().size());
//            log.info("SIZE: G3_opt2_Some: " + G3_opt2_Some.get_cfg_step_labels().size());
//            if (G3_opt2_Some.get_cfg_step_labels().size() != G3_Some.get_cfg_step_labels().size())
//                throw new RuntimeException();


            // compare_unmarked_language.instance.test("F_SDPDA_TO_LR1_OPT(G2, 1)",G2, G3_Some, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

            cfg<symbol__abstract<state>, symbol__abstract<event>>
                    G = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(G3_Some);

            //compare_unmarked_language.instance.test("F_CFG_TC(G3_Some)",G3_Some, G, 1000, 10000, 0, 100, 1000, 10000, 0, 100);


            epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
                    RESULT = lr1_cfg_to_dpda__OPT(G);

            //compare_unmarked_language.instance.test("T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(E3)", E3, RESULT, 1000, 10000, 0, 100, 1000, 10000, 0, 100);
            if (Config.Algorithm_Definition__Enforce_Blockfree__OUTPUT)
                Config.instance.output(RESULT);
            Config.instance.check_invariants(RESULT);
            result = new option__Some<>(RESULT);
        }
        Config.instance.measuring_thread.pop("F_DPDA_EB_OPT");
        return result;
    }

}
