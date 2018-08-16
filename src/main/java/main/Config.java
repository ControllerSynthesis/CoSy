package main;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.formalisms.epda;
import import_export.XML_writer;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS.DETERMINE_SHORTEST_MARKED_WORDS_Problem;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS.DETERMINE_SHORTEST_MARKED_WORDS_Solver;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER.DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER.DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solver;
import multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem;
import multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solver;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solver;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solver;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx.T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx.T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solver;
import multithreading.T05_04_LR_MACHINE.T05_04_LR_MACHINE_Problem;
import multithreading.T05_04_LR_MACHINE.T05_04_LR_MACHINE_Solver;
import multithreading.general.Local_Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Config {

    public static final boolean CHECK_VALID_PARSER_ON_LOG_INFO = false;
    public static final boolean CHECK_VALID_CFG_ON_LOG_INFO = false;
    public static final boolean CHECK_VALID_EDPDA_ON_LOG_INFO = false;
    public static final boolean USE_T09_SDPDA_TO_CFG_OPT2 = true;
    public static final boolean T03_DPDA_OBSERVE_TOP_STACK_OPT = true;
    public static final boolean T07_UNDO_OTS__APPLY_AFTER__T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT = true;
    public static final boolean T03_COLLATE_EPDA__APPLY_AFTER__T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT = true;
    public static final int DETERMINE_SHORTEST_MARKED_WORDS_INNER_PRIORITY = 8;
    public static boolean COLLATE_CFG__BISIM = true;
    public static Config instance = new Config();
    public static boolean USE__Algorithm_Definition__DPDA_DFA_PRODUCT_OPT = true;
    public static boolean T03_06_02_SDPDA_TO_CFG_OPT___OPTIMIZE_L2_UNDERFLOW = true;
    public static boolean T03_06_02_SDPDA_TO_CFG_OPT___LESS_L2_INITIAL = true;
    public static boolean USE_T04_CFG_FIRST2 = true;
    public static boolean Algorithm_Definition__Enforce_Blockfree__OUTPUT = false;
    public static boolean Algorithm_Definition__Enforce_Controllable__OUTPUT = false;
    public static boolean T01_COLLATE_CFG__OUTPUT = false;
    public static boolean T06_02_03_EDPDA_TO_DPDA__OUTPUT = false;
    public static boolean T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES__info_on_reachability = false;
    public static int T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_PRIORITY = 7;
    public static int T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_PRIORITY = 7;
    public static int T03_06_02_SDPDA_TO_CFG_OPT_PRIORITY = 7;
    public static int T05_04_LR_MACHINE_PRIORITY = 7;
    // potentially worse! requires stronger F_EPDA_AA values?
    public static boolean COLLATE_CFG = true;
    private static Logger log = LogManager.getLogger(Config.class.getName());
    public boolean F_DPDA_DRE__use_recursive_lr1_cfg_construction = true;
    // the higher this value the larger the approximation and the smaller the obtained set of l2-l3-nonterminals
    public int F_DPDA_DRE__k = 1;
    public boolean F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE = true;
    // usually does not work well because the ALTERNATIVE is better than the standard
    public boolean F_SDPDA_TO_CFG_OPT__nonterminals___BACK_TO_BACK = false;
    public boolean REMOVE_UNUSED_STATE_STACK_EPDA__before__F_SDPDA_TO_LR1_OPT__in__F_DPDA_EB_OPT = true;
    public boolean REMOVE_UNUSED_STATE_STACK_EPDA__before__F_DPDA_EA_OPT__in__F_DPDA_EB_OPT = true;
    public boolean REMOVE_UNUSED_STATE_STACK_EPDA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT = true;
    public AtomicBoolean abort_memory_usage_thread = new AtomicBoolean(false);
    public Measuring_Thread measuring_thread;
    public boolean T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES__create_only_states_used_in_edges = true;
    public boolean T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES__create_only_states_used_in_edges = true;
    public boolean T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES__create_only_states_used_in_edges = true;
    public boolean T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES__create_only_states_used_in_edges = true;
    public boolean T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY__create_only_states_used_in_edges = true;
    public boolean T06_02_03_EDPDA_TO_DPDA__USE_ALTERNATIVE = true;
    public boolean T05_04_LR_MACHINE__PARALLEL = true;
    public int output_number = 0;
    public boolean T01_COLLATE_CFG__NO_INCREASE_STEP_LABELS = true;
    public boolean F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE__ONE_CLOSURE_FOR_ALL = false;
    public boolean T03_DPDA_OBSERVE_TOP_STACK__generate_states_from_edges = true;
    public boolean T03_DPDA_OBSERVE_TOP_STACK__use_blockfreeness_for_top_stack_approximation = true;
    public boolean T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE__ignore_when_unstable_marking = true;
    public boolean T07_03_DPDA_DETERMINE_REQUIRED_EDGES__use_blockfreeness_for_top_stack_approximation = true;
    public boolean F_DPDA_EC__assume_nonblocking_after_F_DPDA_OTS_and_F_DPDA_EUML = true;
    public boolean DISABLE_ENFORCE_ACCESSIBLE_IN_ENFORCE_BLOCKFREE = true;
    public boolean DISABLE_ENFORCE_ACCESSIBLE_IN_ENFORCE_CONTROLLABLE = true;
    public int DETERMINE_SHORTEST_MARKED_WORDS_PRIORITY = 7;
    public Local_Solver local_solver;
    General_List<General_Test_Sequence<symbol__abstract<string_symbol>>> invariants = null;
    private int F_EPDA_AA__GLOBAL_DEFAULT = 2;
    private int F_EPDA_AA__before_RNPE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__before_RMPOE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_ACCESSIBLE__F_DPDA_EB_OPT = F_EPDA_AA__GLOBAL_DEFAULT;
    private int F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__T07_03_DPDA_DETERMINE_REQUIRED_EDGES = F_EPDA_AA__GLOBAL_DEFAULT;
    private int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree = F_EPDA_AA__GLOBAL_DEFAULT;

    public Config() {
        measuring_thread = new Measuring_Thread();
        measuring_thread.start();

        local_solver = new Local_Solver(true, 0, 0);
        local_solver.register_solver(T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem.class, T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solver.class);
        local_solver.register_solver(T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem.class, T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solver.class);
        local_solver.register_solver(T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem.class, T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solver.class);
        local_solver.register_solver(T05_04_LR_MACHINE_Problem.class, T05_04_LR_MACHINE_Solver.class);
        local_solver.register_solver(T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem.class, T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solver.class);
        local_solver.register_solver(DETERMINE_SHORTEST_MARKED_WORDS_Problem.class, DETERMINE_SHORTEST_MARKED_WORDS_Solver.class);
        local_solver.register_solver(DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem.class, DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solver.class);
    }

    public static String callStack() {
        Exception e = new Exception();
        return "STACK(" + e.getStackTrace().length + ")";
    }

    public static <alpha> List<List<alpha>> split(int min_chunk_size, int max_chunk_size, int additional_separation, List<alpha> input) {
        List<List<alpha>> result = new ArrayList<>();
        int runners = Config.instance.local_solver.get_starting_thread_count();
        int separation = additional_separation * runners;
        int size = input.size();
        int chunk_size = size / separation;
        if (chunk_size < min_chunk_size) {
            chunk_size = min_chunk_size;
            separation = (size / chunk_size) + 1;
        }
        if (chunk_size > max_chunk_size) {
            chunk_size = max_chunk_size;
            separation = (size / chunk_size) + 1;
        }
        for (int i = 0; i < separation; i++) {
            int min = i * chunk_size;
            int max = (i + 1) * chunk_size;
            if (i + 1 == separation)
                max = size;
            if (min == max)
                continue;
            result.add(input.subList(min, max));
        }
        return result;
    }

    public int getF_EPDA_AA__before_RNPE() {
        return F_EPDA_AA__before_RNPE;
    }

    public int getF_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS() {
        return F_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS;
    }

    public void output(Object o) {
        measuring_thread.push("output");
        XML_writer xml_writer = new XML_writer("out/test/cosy/output_test", "output_main_" + output_number + ".xml", "../../main/resources/cosy.xsd");
        xml_writer.add_to_be_exported(o);
        xml_writer.export();

        if (o instanceof epda) {
            try {
                ((epda) o).dot_export("out/test/cosy/output_test/output_main_" + output_number + ".dot");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        output_number++;
        measuring_thread.pop("output");
    }

    public void check_invariants(epda M) {
        if (invariants == null)
            return;
        for (General_Test_Sequence<symbol__abstract<string_symbol>> testSequence : invariants.get_value()) {
            log.info("test: " + testSequence.toString());
            Boolean result_value = epda.check_word_inclusion(M, testSequence.get_sequence().get_value(), testSequence.get_expected());
            if (result_value == null)
                throw new RuntimeException("TEST UNCLEAR");
            if (result_value)
                log.info("TEST SUCCEEDED");
            else {
                throw new RuntimeException("TEST FAILED");
            }
        }
    }

    public void set_invariants(General_List<General_Test_Sequence<symbol__abstract<string_symbol>>> invariants) {
        this.invariants = invariants;
    }

    public int getF_EPDA_AA__before_RMPOE() {
        return F_EPDA_AA__before_RMPOE;
    }

    public int getF_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE() {
        return F_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE;
    }

    public int getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT() {
        return F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT;
    }

    public int getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS() {
        return F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE;
    }

    public void setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE(int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE) {
        this.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE = APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE;
    }

    public void setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE(int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE) {
        this.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE = APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE;
    }

    public void setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(int APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE) {
        this.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE = APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS;
    }

    public int getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML() {
        return F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML;
    }

    public int getAPPROXIMATE_ACCESSIBLE__F_DPDA_EB_OPT() {
        return APPROXIMATE_ACCESSIBLE__F_DPDA_EB_OPT;
    }

    public int getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS() {
        return F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__T07_03_DPDA_DETERMINE_REQUIRED_EDGES() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__T07_03_DPDA_DETERMINE_REQUIRED_EDGES;
    }

    public int getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree() {
        return APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree;
    }

    public int getF_EPDA_AA__GLOBAL_DEFAULT() {
        return F_EPDA_AA__GLOBAL_DEFAULT;
    }

    public void setF_EPDA_AA__GLOBAL_DEFAULT(int value) {
        F_EPDA_AA__GLOBAL_DEFAULT = value;
        F_EPDA_AA__before_RNPE = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__before_RMPOE = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__before_F_SDPDA_TO_LR1_OPT_in_F_DPDA_DRE = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS = F_EPDA_AA__GLOBAL_DEFAULT;
        F_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__T07_03_DPDA_DETERMINE_REQUIRED_EDGES = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree = F_EPDA_AA__GLOBAL_DEFAULT;
        APPROXIMATE_ACCESSIBLE__F_DPDA_EB_OPT = F_EPDA_AA__GLOBAL_DEFAULT;
    }
}

