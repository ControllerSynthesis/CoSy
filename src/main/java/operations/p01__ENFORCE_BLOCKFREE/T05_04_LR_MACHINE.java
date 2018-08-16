package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import multithreading.T05_04_LR_MACHINE.T05_04_LR_MACHINE_Problem;
import multithreading.T05_04_LR_MACHINE.T05_04_LR_MACHINE_Store_Result_Callback;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_04_LR_MACHINE {
    private static final General_Integer ZERO = new General_Integer(0);
    private static final List<General_Integer> ZERO_LIST = General_List.list_by_enumeration(ZERO);
    private static Logger log = LogManager.getLogger(T05_04_LR_MACHINE.class.getName());
    private T05_04_LR_MACHINE() {
        throw new IllegalStateException("Utility Class");
    }

    public static <nonterminal extends General, event extends General>
    Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>>
    F_LR_MACHINE__one(
            cfg<nonterminal, event> cfg,
            int k,
            Set<General_Set<cfg_item<nonterminal, event>>> S) {
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__one : " + cfg.log_info() + ", S=" + S.size());
        Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> result = new HashSet<>();
        for (General_Set<cfg_item<nonterminal, event>> q : S) {
            for (nonterminal X : cfg.get_cfg_nonterminals()) {
                two_elements__abstract<nonterminal, event> elem = new two_elements__teA<>(X);
                General_Set<cfg_item<nonterminal, event>> set = T05_03_VALID_ITEM_SETS.F_VALID_ITEM_SET_GOTO(cfg, k, elem, q, null, null);
                if (set.get_value().isEmpty())
                    continue;
                result.add(new epda_step_label<>(q, new option__Some<>(elem),
                        ZERO_LIST, ZERO_LIST, set));
            }
            for (event X : cfg.get_cfg_events()) {
                two_elements__abstract<nonterminal, event> elem = new two_elements__teB<>(X);
                General_Set<cfg_item<nonterminal, event>> set = T05_03_VALID_ITEM_SETS.F_VALID_ITEM_SET_GOTO(cfg, k, elem, q, null, null);
                if (set.get_value().isEmpty())
                    continue;
                result.add(new epda_step_label<>(q, new option__Some<>(elem),
                        ZERO_LIST, ZERO_LIST, set));
            }
        }
        return result;
    }

    private static <nonterminal extends General, event extends General>
    tuple2<General_Set<General_Set<cfg_item<nonterminal, event>>>, General_Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>>>
    F_LR_MACHINE__fp_one(
            cfg<nonterminal, event> cfg,
            int k,
            Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> E,
            Set<General_Set<cfg_item<nonterminal, event>>> V,
            Set<General_Set<cfg_item<nonterminal, event>>> S,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained) {
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one : E=" + E.size() + ", S=" + S.size());
        if (S.isEmpty())
            return new tuple2<>(new General_Set<>(V), new General_Set<>(E));
        Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> new_edges;
        if (Config.instance.T05_04_LR_MACHINE__PARALLEL) {
            new_edges = F_LR_MACHINE_one_parallel(cfg, k, S, obtained);
        } else
            new_edges = F_LR_MACHINE__one(cfg, k, S);
        if (false) {
            log.info("obtained.size() = " + obtained.size());
            Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> new_edges2 = F_LR_MACHINE__one(cfg, k, S);
            if (!new_edges.equals(new_edges2)) {
                log.info("ERROR: F_LR_MACHINE__one not equal: " + new_edges.size() + " / " + new_edges2.size());
                for (epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> elem : new_edges)
                    if (!new_edges2.contains(elem)) {
                        log.info("ERROR: new_edges2 misses: " + elem);
                    }
                for (epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> elem : new_edges2)
                    if (!new_edges.contains(elem)) {
                        log.info("ERROR: new_edges misses: " + elem);
                    }
//                for (int i = 0; i < 1000; i++) {
//                    new_edges = F_LR_MACHINE_one_parallel(cfg, k, S, new HashMap<>(obtained));
//                    log.info("ERROR: attempt " + i + ": " + new_edges.size());
//                }
                System.exit(1);
            }
        }
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:1: " + System.currentTimeMillis() + " new_edges=" + new_edges.size() + ", E=" + E.size());
        E.addAll(new_edges);
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:2: " + System.currentTimeMillis() + " V=" + V.size() + ", S=" + S.size());
        V.addAll(S);
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:3A: " + System.currentTimeMillis() + " new_edges=" + new_edges.size());
        for (epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> edge : new_edges)
            edge.get_edge_trg().cache_hashcode();
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:3B: " + System.currentTimeMillis() + " new_edges=" + new_edges.size());
        Set<General_Set<cfg_item<nonterminal, event>>> new_S = new HashSet<>(new_edges.size());
        for (epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> edge : new_edges)
            new_S.add(edge.get_edge_trg());

        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:4: " + System.currentTimeMillis() + " new_S=" + new_S.size() + ", V=" + V.size());
        new_S.removeAll(V);
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:5: " + System.currentTimeMillis() + " new_S=" + new_S.size() + ", S=" + S.size());
        new_S.removeAll(S);
        if (log.isInfoEnabled())
            log.info("F_LR_MACHINE__fp_one:6: " + System.currentTimeMillis() + " new_S=" + new_S.size());
        return F_LR_MACHINE__fp_one(cfg, k, E, V, new_S, obtained);
    }

    private static <nonterminal extends General, event extends General> Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>>
    F_LR_MACHINE_one_parallel(
            cfg<nonterminal, event> cfg,
            int k,
            Set<General_Set<cfg_item<nonterminal, event>>> S,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained) {
        Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> new_edges;
        new_edges = new HashSet<>();
        final AtomicBoolean abort = new AtomicBoolean(false);
        final List<Callback> callbackList = new ArrayList<>();
        callbackList.add(new T05_04_LR_MACHINE_Store_Result_Callback<>(new_edges, obtained));
        Config.instance.local_solver.register_callbacks(abort, callbackList);
        List<General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>>> tasks = new ArrayList<>();
        for (General_Set<cfg_item<nonterminal, event>> state : S) {
            if (false) {
                if (state.hashCode() == -1868749381)
                    log.info(state.get_value().size() + " / " + state);
                if (state.hashCode() == -98300096)
                    log.info(state.get_value().size() + " / " + state);
            }
            // we only add problems for those where a nonempty result is obtained!
            for (cfg_item<nonterminal, event> item : state.get_value())
                if (!item.get_cfg_item_rhs2().isEmpty()) {
                    General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>> task = new General_Tuple2<>(state, item.get_cfg_item_rhs2().get(0));
                    tasks.add(task);
                    if (false)
                        log.info(task.getElemA().hashCode() + " / " + task.getElemB().hashCode());
                }
        }
        int block_size = tasks.size() / Config.instance.local_solver.get_number_of_solvers();
        if (tasks.size() % Config.instance.local_solver.get_number_of_solvers() != 0)
            block_size++;

        if (block_size > 1000)
            block_size = 1000;

        log.info("block_size = " + block_size + ", tasks = " + tasks.size());

        List<General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>>> this_tasks = new ArrayList<>();
        for (General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>> elem : tasks) {
            this_tasks.add(elem);
            if (this_tasks.size() == block_size) {
                Config.instance.local_solver.add_problem(
                        Config.T05_04_LR_MACHINE_PRIORITY,
                        abort,
                        new T05_04_LR_MACHINE_Problem<>(cfg, k, this_tasks, obtained));
                log.info("task_size = " + this_tasks.size());
                this_tasks = new ArrayList<>();
            }
        }
        if (!this_tasks.isEmpty()) {
            Config.instance.local_solver.add_problem(
                    Config.T05_04_LR_MACHINE_PRIORITY,
                    abort,
                    new T05_04_LR_MACHINE_Problem<>(cfg, k, this_tasks, obtained));
            log.info("task_size = " + this_tasks.size());
        }
        try {
            if (!tasks.isEmpty())
                Config.instance.local_solver.wait_for_completion(abort);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new_edges;
    }

    public static <nonterminal extends General, event extends General>
    epda<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>
    F_LR_MACHINE(
            cfg<nonterminal, event> cfg,
            int k) {
        Config.instance.measuring_thread.push("F_LR_MACHINE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_LR_MACHINE: " + "cfg=" + cfg.log_info());
        if (Config.USE_T04_CFG_FIRST2) {
            T04_CFG_FIRST2.F_CFG_FIRST(cfg, k, new ArrayList<>());
        } else
            T04_CFG_FIRST.F_CFG_FIRST(cfg, k, new ArrayList<>());

        Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained = new HashMap<>();
        General_Set<cfg_item<nonterminal, event>> initial_state = T05_03_VALID_ITEM_SETS.F_VALID_ITEM_SET_INITIAL(cfg, k, null, obtained);
        cfg.get_cfg_step_labels_by_lhs(); // important to avoid parallel modification/generation by threads
        if (false) {
            log.info("cfg.get_cfg_step_labels_by_lhs().hashCode() = " + cfg.get_cfg_step_labels_by_lhs().hashCode());
            int sum = 0;
            for (nonterminal lhs : cfg.get_cfg_nonterminals()) {
                if (lhs.toString().equals("⟪nat:909⟫"))
                    for (cfg_step_label<nonterminal, event> elem : cfg.get_cfg_step_labels_by_lhs().get(lhs))
                        log.info("PROD FOUND: " + elem);
                sum += cfg.get_cfg_step_labels_by_lhs().get(lhs).size();
            }
            log.info("INSERTED PRODS: " + sum + " of " + cfg.get_cfg_step_labels().size());
            for (nonterminal lhs : cfg.get_cfg_nonterminals()) {
                event some_event = cfg.get_cfg_events().get(0);
                List<two_elements__abstract<nonterminal, event>> input = new ArrayList<>();
                input.add(new two_elements__teA<>(lhs));
                input.add(new two_elements__teB<>(some_event));
                if (!T04_CFG_FIRST2.F_CFG_FIRST(cfg, k, Collections.singletonList(new two_elements__teA<>(lhs))).equals(T04_CFG_FIRST.F_CFG_FIRST(cfg, k, Collections.singletonList(new two_elements__teA<>(lhs))))
                        || lhs.toString().equals("⟪nat:1471⟫")) {
                    for (List<event> events : T04_CFG_FIRST2.F_CFG_FIRST(cfg, k, input))
                        log.info("lhs = " + lhs + " / LOOKAHEAD FOUND_OPT: " + events);
                    for (List<event> events : T04_CFG_FIRST.F_CFG_FIRST(cfg, k, input))
                        log.info("lhs = " + lhs + " / LOOKAHEAD FOUND_REG: " + events);
                }
            }
        }

        tuple2<General_Set<General_Set<cfg_item<nonterminal, event>>>, General_Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>>> pair =
                F_LR_MACHINE__fp_one(cfg, k, new HashSet<>(), new HashSet<>(), Collections.singleton(initial_state), obtained);
        List<two_elements__abstract<nonterminal, event>> events = new ArrayList<>();
        for (nonterminal X : cfg.get_cfg_nonterminals())
            events.add(new two_elements__teA<>(X));
        for (event X : cfg.get_cfg_events())
            events.add(new two_elements__teB<>(X));
        if (Config.USE_T04_CFG_FIRST2) {
            T04_CFG_FIRST2.remove_instance();
        } else
            T04_CFG_FIRST.remove_instance();

        epda<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> result = new epda<>(
                new ArrayList<>(pair.get_value1().get_value()),
                events,
                Collections.singletonList(ZERO),
                new ArrayList<>(pair.get_value2().get_value()),
                initial_state,
                ZERO,
                new ArrayList<>());
        Config.instance.measuring_thread.pop("F_LR_MACHINE");
        return result;
    }


}
