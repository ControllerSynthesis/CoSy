package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Map;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T07_01_DPDA_DETERMINE_ACCEESSIBLE_EDGES {
    private static Logger log = LogManager.getLogger(T07_01_DPDA_DETERMINE_ACCEESSIBLE_EDGES.class.getName());

    private T07_01_DPDA_DETERMINE_ACCEESSIBLE_EDGES() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, symbol__abstract<stack>>>
    verified__F_DPDA_DAE(
            epda<state, event, symbol__abstract<stack>> epda) {

        Config.instance.measuring_thread.push("verified__F_DPDA_DAE");

        epda<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G1 = T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE(epda, null);
        symbol__abstract<stack> PB = T01_FRESH.F_FRESH(G1.get_epda_gamma());
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G2 = T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES.F_DPDA_RNE(G1, PB, null);
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G3 = T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.F_DPDA_SPPE(G2, null);
        epda<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G4 = T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.F_DPDA_RMPUE(G3, null);
        cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> G5 = T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(G4, null);
        List<cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>> ac_productions = F_CFG_APLM(G5);
        List<epda_step_label<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE4 = F_DPDA_DRE__revert_F_SDPDA_TO_CFG_STD(G4, ac_productions);
        List<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE3 = F_DPDA_DRE__revert_F_DPDA_RMPE(G3, RE4);
        List<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE2 = F_DPDA_DRE__revert_F_DPDA_SPPE(G2, RE3);
        List<epda_step_label<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE1 = F_DPDA_DRE__revert_F_DPDA_RNE(G1, PB, RE2);
        List<epda_step_label<state, event, symbol__abstract<stack>>> RE0 = F_DPDA_DRE__revert_F_DPDA_SEE(epda, RE1);
        Config.instance.measuring_thread.pop("verified__F_DPDA_DAE");
        return RE0;
    }

    public static <state extends General, event extends General, stack extends General>
    Set<epda_step_label<state, event, symbol__abstract<stack>>>
    F_DPDA_DAE(
            epda<state, event, symbol__abstract<stack>> epda) {
        Config.instance.measuring_thread.push("F_DPDA_DAE");
        //System.out.println("epda");
        //epdaH_semantics.print_epda(epda, 20, true, null, null);
        //System.out.println("that was epda " + epda.is_valid());

        Map<epda_step_label<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                epda_step_label<state, event, symbol__abstract<stack>>> reversal_F_DPDA_SEE = new HashMap<>();
        epda<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G1 = T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE(epda, reversal_F_DPDA_SEE);
        //System.out.println("G1");
        //epdaH_semantics.print_epda(G1, 20, true, null, null);
        //System.out.println("that was G1 " + G1.is_valid());

        symbol__abstract<stack> PB = T01_FRESH.F_FRESH(G1.get_epda_gamma());
        //System.out.println("PB");

        Map<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, epda_step_label<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> reversal_F_DPDA_RNE = new HashMap<>();
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G2 = T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES.F_DPDA_RNE(G1, PB, reversal_F_DPDA_RNE);
        //System.out.println("G2");
        //epdaH_semantics.print_epda(G2, 30, true, null, null);
        //System.out.println("that was G2 " + G2.is_valid());

        Map<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> reversal_F_DPDA_SPPE = new HashMap<>();
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G3 = T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.F_DPDA_SPPE(G2, reversal_F_DPDA_SPPE);
        //System.out.println("G3");
        //epdaH_semantics.print_epda(G3, 30, true, null, null);
        //System.out.println("that was G3 " + G3.is_valid());

        Map<epda_step_label<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> reversal_F_DPDA_RMPUE = new HashMap<>();
        epda<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G4 = T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.F_DPDA_RMPUE(G3, reversal_F_DPDA_RMPUE);
        //System.out.println("G4 " + G4.get_epda_delta().size() + " " + G4.get_epda_states().size());
        //epdaH_semantics.print_epda(G4, 30, true, null, null);
        //System.out.println("that was G4 " + G4.is_valid());

        epda<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G4p = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(G4, 2);
        //System.out.println("G4p " + G4p.get_epda_delta().size() + " " + G4p.get_epda_states().size());
        //epdaH_semantics.print_epda(G4p, 30, true, null, null);
        //System.out.println("that was G4p " + G4.is_valid());


        Map<cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>, epda_step_label<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> reversal_F_SDPDA_TO_CFG_STD = new HashMap<>();
        cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> G5 = T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(G4p, reversal_F_SDPDA_TO_CFG_STD);
        //System.out.println("G5 " + G5.get_cfg_step_labels().size() + " " + G5.get_cfg_nonterminals().size() + " " + G5.is_valid());
        //Object G4TC = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(G4p);
        //epdaH_semantics.print_epda_seq((epda) G4TC, 30, true, null, null);
        //System.out.println("that was G4TC " + ((epda) G4TC).is_valid());
        //Object G5opt2 = T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.F_SDPDA_TO_CFG_OPT((epda) G4TC, 2);
        //System.out.println(G5opt2);

//        cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
//                state_or_edge__abstract<
//                        state_or_edge__abstract<
//                                state_or_edge__abstract<
//                                        state, event, symbol__abstract<stack>>,
//                                event, symbol__abstract<stack>>,
//                        event, symbol__abstract<stack>>,
//                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> G5opt =
//                ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
//                        state_or_edge__abstract<
//                                state_or_edge__abstract<
//                                        state_or_edge__abstract<
//                                                state, event, symbol__abstract<stack>>,
//                                        event, symbol__abstract<stack>>,
//                                event, symbol__abstract<stack>>,
//                        event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>>)
//                        T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.F_SDPDA_TO_CFG_OPT(G4p, 2)).the();
//        System.out.println("G5opt " + G5opt.get_cfg_step_labels().size() + " " + G5opt.get_cfg_nonterminals().size() + " " + G5opt.is_valid());
//
//        cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
//                state_or_edge__abstract<
//                        state_or_edge__abstract<
//                                state_or_edge__abstract<
//                                        state, event, symbol__abstract<stack>>,
//                                event, symbol__abstract<stack>>,
//                        event, symbol__abstract<stack>>,
//                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> G5p = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
//                state_or_edge__abstract<
//                        state_or_edge__abstract<
//                                state_or_edge__abstract<
//                                        state, event, symbol__abstract<stack>>,
//                                event, symbol__abstract<stack>>,
//                        event, symbol__abstract<stack>>,
//                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>>) T03_06_05_CFG_TRIM.F_CFG_TRIM(G5)).the();
//        System.out.println("G5p " + G5p.get_cfg_step_labels().size() + " " + G5p.get_cfg_nonterminals().size());
//        cfgLM_semantics.print_cfg(G5p, 30, true, null, null);


        List<cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>> ac_productions = F_CFG_APLM(G5);
        //System.out.println("ac_productions " + ac_productions.size());

        Set<epda_step_label<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE4 = new HashSet<>();
        for (cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> x : ac_productions)
            if (reversal_F_SDPDA_TO_CFG_STD.containsKey(x))
                RE4.add(reversal_F_SDPDA_TO_CFG_STD.get(x));
        //System.out.println("RE4 " + RE4.size());

        Set<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE3 = new HashSet<>();
        for (epda_step_label<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>> x : RE4)
            if (reversal_F_DPDA_RMPUE.containsKey(x))
                RE3.add(reversal_F_DPDA_RMPUE.get(x));
        //System.out.println("RE3 " + RE3.size());

        Set<epda_step_label<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE2 = new HashSet<>();
        for (epda_step_label<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>> x : RE3)
            if (reversal_F_DPDA_SPPE.containsKey(x))
                RE2.add(reversal_F_DPDA_SPPE.get(x));
        //System.out.println("RE2 " + RE2.size());

        Set<epda_step_label<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>> RE1 = new HashSet<>();
        for (epda_step_label<state_or_edge__abstract<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>> x : RE2)
            if (reversal_F_DPDA_RNE.containsKey(x))
                RE1.add(reversal_F_DPDA_RNE.get(x));
        //System.out.println("RE1 " + RE1.size());


        Set<epda_step_label<state, event, symbol__abstract<stack>>> RE0 = new HashSet<>();
        for (epda_step_label<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>> x : RE1)
            if (reversal_F_DPDA_SEE.containsKey(x))
                RE0.add(reversal_F_DPDA_SEE.get(x));
        //System.out.println("RE0 " + RE0.size());
        Config.instance.measuring_thread.pop("F_DPDA_DAE");
        return RE0;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_SDPDA_TO_CFG_STD(
            epda<state, event, stack> epda,
            List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> cfg_step_labels) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();
        //int INDEX = 0;
        for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> p : cfg_step_labels) {
            //System.out.println(" " + result.size() + " " + INDEX++);
            result.addAll(F_DPDA_DRE__revert_F_SDPDA_TO_CFG_STD_1(epda, p));
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_SDPDA_TO_CFG_STD_1(
            epda<state, event, stack> epda,
            cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> p) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();

        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if ((e.get_edge_event() instanceof option__Some) && (T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD__edges_l3_read(e, epda.get_epda_states()).contains(p))
                    || (e.get_edge_push().isEmpty() && T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD__edges_l3_pop(e).contains(p))
                    || (!e.get_edge_push().isEmpty() && (e.get_edge_event() instanceof option__None) && T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD__edges_l3_push(e, epda.get_epda_states()).contains(p))
                    || ((e.get_edge_event() instanceof option__Some) && T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD__edges_l2_read(e).contains(p))
                    || (!e.get_edge_push().isEmpty() && (e.get_edge_event() instanceof option__None)) && T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD__edges_l2_push(e, epda.get_epda_states()).contains(p))
                result.add(e);

        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_DPDA_RMPE(
            epda<state, event, stack> epda,
            List<epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>> epda_step_labels) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();
        for (epda_step_label<state, event, stack> epda_step_label : epda.get_epda_delta())
            for (epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack> p : epda_step_labels) {
                if (T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.F_DPDA_RMPUE__edge_then(epda_step_label).contains(p)
                        || T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.F_DPDA_RMPUE__edge_else(epda_step_label).equals(p)) {
                    result.add(epda_step_label);
                    break;
                }
            }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_DPDA_SPPE(
            epda<state, event, stack> epda,
            List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>> epda_step_labels) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();
        for (epda_step_label<state, event, stack> epda_step_label : epda.get_epda_delta())
            for (epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack> p : epda_step_labels) {
                if (T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.F_DPDA_SPPE__edge_then(epda_step_label, epda.get_epda_gamma()).contains(p)
                        || T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.F_DPDA_SPPE__edge_else(epda_step_label).equals(p)) {
                    result.add(epda_step_label);
                    break;
                }
            }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_DPDA_RNE(
            epda<state, event, stack> epda,
            stack PB,
            List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>> epda_step_labels) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();
        for (epda_step_label<state, event, stack> epda_step_label : epda.get_epda_delta())
            for (epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack> p : epda_step_labels) {
                if (T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES.F_DPDA_RNE__edge_then(epda_step_label, PB).contains(p)
                        || T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES.F_DPDA_RNE__edge_else(epda_step_label).equals(p)) {
                    result.add(epda_step_label);
                    break;
                }
            }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state, event, stack>>
    F_DPDA_DRE__revert_F_DPDA_SEE(
            epda<state, event, stack> epda,
            List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>> epda_step_labels) {
        List<epda_step_label<state, event, stack>> result = new ArrayList<>();
        for (epda_step_label<state, event, stack> epda_step_label : epda.get_epda_delta())
            for (epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack> p : epda_step_labels) {
                if (T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE__edge_then(epda_step_label).contains(p)
                        || T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE__edge_else(epda_step_label).contains(p)) {
                    result.add(epda_step_label);
                    break;
                }
            }
        return result;
    }


    private static <nonterminal extends General, event extends General>
    List<cfg_step_label<nonterminal, event>>
    F_CFG_APLM(
            cfg<nonterminal, event> cfg) {
        Set<nonterminal> nonterminals = new HashSet<>();
        nonterminals.add(cfg.get_cfg_initial());
        option__abstract<cfg<nonterminal, event>> cfgBF = T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(cfg);
        if (cfgBF instanceof option__Some) {
            Set<nonterminal> blockfree_nonterminals = new HashSet<>(((option__Some<cfg<nonterminal, event>>) cfgBF).get_value().get_cfg_nonterminals());
            Set<nonterminal> todo = new HashSet<>(nonterminals);
            while (!todo.isEmpty()) {
                Set<nonterminal> additional = new HashSet<>();
                for (nonterminal lhs : todo)
                    for (cfg_step_label<nonterminal, event> step_label : General_Map.get_default(cfg.get_cfg_step_labels_by_lhs(), lhs, new ArrayList<>()))
                        for (two_elements__abstract<nonterminal, event> elem : step_label.get_prod_rhs())
                            if (elem instanceof two_elements__teA) {
                                nonterminal current = ((two_elements__teA<nonterminal, event>) elem).get_value();
                                if (!nonterminals.contains(current))
                                    additional.add(current);
                                if (!blockfree_nonterminals.contains(current))
                                    break;
                            }
                nonterminals.addAll(additional);
                todo = additional;
            }
        }

        List<cfg_step_label<nonterminal, event>> result = new ArrayList<>();
        for (cfg_step_label<nonterminal, event> cfg_step_label : cfg.get_cfg_step_labels())
            if (nonterminals.contains(cfg_step_label.get_prod_lhs()))
                result.add(cfg_step_label);
        return result;
    }

}