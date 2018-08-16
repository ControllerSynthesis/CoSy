package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2.T09_SDPDA_TO_CFG_OPT2;
import operations.ADDITIONAL_OPERATIONS.T10_SDPDA_TO_LR_OPT2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T07_03_DPDA_DETERMINE_REQUIRED_EDGES {
    private static Logger log = LogManager.getLogger(T07_03_DPDA_DETERMINE_REQUIRED_EDGES.class.getName());

    private T07_03_DPDA_DETERMINE_REQUIRED_EDGES() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    Set<epda_step_label<state, event, symbol__abstract<stack>>>
    F_DPDA_DRE(
            epda<state, event, symbol__abstract<stack>> epda,
            boolean is_blockfree,
            int k,
            boolean use_recursive_lr1_cfg_construction) {
        Config.instance.measuring_thread.push("F_DPDA_DRE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DRE: " + "epda=" + epda.log_info());

        //System.out.println("epda");
        //epdaH_semantics.print_epda(epda, 20, true, null, null);
        //System.out.println("that was epda " + epda.is_valid());

        Map<epda_step_label<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                epda_step_label<state, event, symbol__abstract<stack>>> reversal_F_DPDA_SEE = new HashMap<>();
        epda<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G1 = T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE(epda, reversal_F_DPDA_SEE);

        //compare_unmarked_language.instance.test("F_DPDA_SEE(epda, reversal_F_DPDA_SEE)",epda, G1, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

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

        //compare_unmarked_language.instance.test("F_DPDA_RNE(G1, PB, reversal_F_DPDA_RNE)",G1, G2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

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
        //compare_unmarked_language.instance.test("F_DPDA_SPPE(G2, reversal_F_DPDA_SPPE)",G2, G3, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

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
        // compare_unmarked_language.instance.test("F_DPDA_RMPUE(G3, reversal_F_DPDA_RMPUE)",G3, G4, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (Config.instance.getF_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE() >= 0) {
            int size = G4.get_epda_delta().size() + G4.get_epda_states().size();
            G4 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(G4, Config.instance.getF_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE());
            if (log.isInfoEnabled()) {
                if (G4.get_epda_delta().size() + G4.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in T07_03_DPDA_DETERMINE_REQUIRED_EDGES: getF_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE should be zero");
            }
        }


        // compare_unmarked_language.instance.test("F_EPDA_AA(G4,",G3, G4, 1000, 10000, 0, 100, 1000, 10000, 0, 100);
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

        Map<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, Set<symbol__abstract<stack>>> needs_auxiliary_for = new HashMap<>();
        if (!use_recursive_lr1_cfg_construction) {
            if (is_blockfree && Config.instance.T07_03_DPDA_DETERMINE_REQUIRED_EDGES__use_blockfreeness_for_top_stack_approximation) {
                needs_auxiliary_for = T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree(G4);
            } else if (Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__T07_03_DPDA_DETERMINE_REQUIRED_EDGES() >= 0) {
                needs_auxiliary_for = T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(G4);
            } else {
                needs_auxiliary_for = T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_all(G4);
            }
        }

        option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>> G5opt =
                use_recursive_lr1_cfg_construction ? T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(G4, reversal_F_SDPDA_TO_CFG_STD)
                        : (
                        Config.USE_T09_SDPDA_TO_CFG_OPT2 ?
                                T09_SDPDA_TO_CFG_OPT2.F_SDPDA_TO_CFG_OPT2(G4, reversal_F_SDPDA_TO_CFG_STD) :
                                T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT(G4, k, needs_auxiliary_for, reversal_F_SDPDA_TO_CFG_STD));
//        System.out.println("G5opt " + G5opt.get_cfg_step_labels().size() + " " + G5opt.get_cfg_nonterminals().size() + " " + G5opt.is_valid());
//
//        System.out.println("G5p " + G5p.get_cfg_step_labels().size() + " " + G5p.get_cfg_nonterminals().size());
//        cfgLM_semantics.print_cfg(G5p, 30, true, null, null);


        Set<epda_step_label<state, event, symbol__abstract<stack>>> RE0 = new HashSet<>();
        if (G5opt instanceof option__Some) {
            if (log.isInfoEnabled())
                log.info("G5opt is Some");
            cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                    state_or_edge__abstract<
                            state_or_edge__abstract<
                                    state_or_edge__abstract<
                                            state, event, symbol__abstract<stack>>,
                                    event, symbol__abstract<stack>>,
                            event, symbol__abstract<stack>>,
                    event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>
                    G6 = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                    state_or_edge__abstract<
                            state_or_edge__abstract<
                                    state_or_edge__abstract<
                                            state, event, symbol__abstract<stack>>,
                                    event, symbol__abstract<stack>>,
                            event, symbol__abstract<stack>>,
                    event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>>) G5opt).the();

            option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                    state_or_edge__abstract<
                            state_or_edge__abstract<
                                    state_or_edge__abstract<
                                            state, event, symbol__abstract<stack>>,
                                    event, symbol__abstract<stack>>,
                            event, symbol__abstract<stack>>,
                    event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>>
                    G7 = T03_06_05_CFG_TRIM.F_CFG_TRIM(G6);

            if (G7 instanceof option__Some) {
                if (log.isInfoEnabled())
                    log.info("G7 is Some: starting reverse");
                Config.instance.measuring_thread.push("F_DPDA_DRE_reverse");
                List<cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state_or_edge__abstract<
                                                state, event, symbol__abstract<stack>>,
                                        event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>> required_productions = null;
                required_productions = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state_or_edge__abstract<
                                                state, event, symbol__abstract<stack>>,
                                        event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>, symbol__abstract<stack>>, event>>) G7).the().get_cfg_step_labels();

                Set<epda_step_label<state_or_edge_nat__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state_or_edge__abstract<
                                                state, event, symbol__abstract<stack>>,
                                        event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>> RE4 = new HashSet<>();
                for (cfg_step_label<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>, symbol__abstract<stack>>, event> x : required_productions)
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


                for (epda_step_label<state_or_edge__abstract<state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>> x : RE1)
                    if (reversal_F_DPDA_SEE.containsKey(x))
                        RE0.add(reversal_F_DPDA_SEE.get(x));
                Config.instance.measuring_thread.pop("F_DPDA_DRE_reverse");
            } else {
                if (log.isInfoEnabled())
                    log.info("G7 is None");
            }
        } else {
            if (log.isInfoEnabled())
                log.info("G5opt is None");
        }

        //System.out.println("ac_productions " + ac_productions.size());


        //System.out.println("RE0 " + RE0.size());
        Config.instance.measuring_thread.pop("F_DPDA_DRE");
        return RE0;
    }

}
