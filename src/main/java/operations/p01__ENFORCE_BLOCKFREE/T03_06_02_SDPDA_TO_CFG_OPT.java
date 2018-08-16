package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Store_Result_Callback;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Store_Result_Callback;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx.T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem;
import multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx.T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Store_Result_Callback;
import multithreading.general.abstract_components.Callback;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * possibly construct only a single reachability graph with the various entry points!
 * <p>
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_SDPDA_TO_CFG_OPT {

    private static Logger log = LogManager.getLogger(T03_06_02_SDPDA_TO_CFG_OPT.class.getName());

    private T03_06_02_SDPDA_TO_CFG_OPT() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l3_read(
            epda_step_label<state, event, stack> e,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (Map.Entry<state, l2_l3_nonterminals__cons_l3<state, stack>> elem1 : General_Map.get_default(General_Map.get_default(S3map, e.get_edge_src(), new HashMap<>()), e.get_edge_pop().get(0), new HashMap<>()).entrySet()) {
            state qt = elem1.getKey();
            l2_l3_nonterminals__cons_l3<state, stack> elem2 = General_Map.get_default(General_Map.get_default(S3map, e.get_edge_trg(), new HashMap<>()), e.get_edge_pop().get(0), new HashMap<>()).get(qt);
            if (elem2 == null)
                continue;
            result.add(new cfg_step_label<>(
                    elem1.getValue(),
                    General_List.list_by_enumeration(
                            new two_elements__teB<>(((option__Some<event>) e.get_edge_event()).the()),
                            new two_elements__teA<>(elem2)
                    )));
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l3_pop(
            epda_step_label<state, event, stack> e,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        l2_l3_nonterminals__cons_l3<state, stack> elem = General_Map.get_default(General_Map.get_default(S3map, e.get_edge_src(), new HashMap<>()), e.get_edge_pop().get(0), new HashMap<>()).get(e.get_edge_trg());
        if (elem == null)
            return result;
        result.add(new cfg_step_label<>(elem, new ArrayList<>()));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l3_push(
            epda_step_label<state, event, stack> e,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();

        for (Map.Entry<state, l2_l3_nonterminals__cons_l3<state, stack>> elem1 : General_Map.get_default(General_Map.get_default(S3map, e.get_edge_src(), new HashMap<>()), e.get_edge_pop().get(0), new HashMap<>()).entrySet()) {
            state qt = elem1.getKey();
            for (Map.Entry<state, l2_l3_nonterminals__cons_l3<state, stack>> elem2 : General_Map.get_default(General_Map.get_default(S3map, e.get_edge_trg(), new HashMap<>()), e.get_edge_push().get(0), new HashMap<>()).entrySet()) {
                state qs = elem2.getKey();
                l2_l3_nonterminals__cons_l3<state, stack> elem3 = General_Map.get_default(General_Map.get_default(S3map, qs, new HashMap<>()), e.get_edge_pop().get(0), new HashMap<>()).get(qt);
                if (elem3 == null)
                    continue;
                result.add(new cfg_step_label<>(
                        elem1.getValue(),
                        General_List.list_by_enumeration(
                                new two_elements__teA<>(elem2.getValue()),
                                new two_elements__teA<>(elem3)
                        )));
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l3(
            epda<state, event, stack> epda,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__edges_l3");
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        int count = 0;
        int step = epda.get_epda_delta().size() / 100;
        if (step == 0)
            step = 1;
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            if (log.isInfoEnabled()) {
                count++;
                if (count % step == 0)
                    log.info("F_SDPDA_TO_CFG_OPT__edges_l3_read: " + (count / step));
            }
            if (!(e.get_edge_event() instanceof option__None)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> new_edges = F_SDPDA_TO_CFG_OPT__edges_l3_read(e, S3map);
                result.addAll(new_edges);
                if (reverse != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : new_edges)
                        reverse.put(elem, e);
            }
        }
        count = 0;
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            if (log.isInfoEnabled()) {
                count++;
                if (count % step == 0)
                    log.info("F_SDPDA_TO_CFG_OPT__edges_l3_pop: " + (count / step));
            }
            if (e.get_edge_push().isEmpty()) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> new_edges = F_SDPDA_TO_CFG_OPT__edges_l3_pop(e, S3map);
                result.addAll(new_edges);
                if (reverse != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : new_edges)
                        reverse.put(elem, e);
            }
        }
        count = 0;
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            if (log.isInfoEnabled()) {
                count++;
                if (count % step == 0)
                    log.info("F_SDPDA_TO_CFG_OPT__edges_l3_push: " + (count / step));
            }
            if (!e.get_edge_push().isEmpty() && !(e.get_edge_event() instanceof option__Some)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> new_edges = F_SDPDA_TO_CFG_OPT__edges_l3_push(e, S3map);
                result.addAll(new_edges);
                if (reverse != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : new_edges)
                        reverse.put(elem, e);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l2_read(
            epda_step_label<state, event, stack> e,
            Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        l2_l3_nonterminals__cons_l2<state, stack> elem1 = General_Map.get_default(General_Map.get_default(S2map, e.get_edge_src(), new HashMap<>()), e.get_edge_pop().get(0), null);
        if (elem1 == null)
            return result;
        l2_l3_nonterminals__cons_l2<state, stack> elem2 = General_Map.get_default(General_Map.get_default(S2map, e.get_edge_trg(), new HashMap<>()), e.get_edge_pop().get(0), null);
        if (elem2 == null)
            return result;
        result.add(new cfg_step_label<>(
                elem1,
                General_List.list_by_enumeration(
                        new two_elements__teB<>(((option__Some<event>) e.get_edge_event()).the()),
                        new two_elements__teA<>(elem2)
                )));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l2_final(
            List<state> F,
            Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (state i : F)
            for (l2_l3_nonterminals__cons_l2<state, stack> elem : General_Map.get_default(S2map, i, new HashMap<>()).values())
                result.add(new cfg_step_label<>(
                        elem,
                        new ArrayList<>()
                ));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l2_push(
            epda_step_label<state, event, stack> e,
            Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        l2_l3_nonterminals__cons_l2<state, stack> elem1 = General_Map.get_default(S2map, e.get_edge_src(), new HashMap<>()).get(e.get_edge_pop().get(0));
        if (elem1 == null)
            return result;
        if (S2map.containsKey(e.get_edge_trg())) {
            l2_l3_nonterminals__cons_l2<state, stack> elem2 = S2map.get(e.get_edge_trg()).get(e.get_edge_push().get(0));
            if (elem2 != null)
                result.add(new cfg_step_label<>(
                        elem1,
                        General_List.list_by_enumeration(
                                new two_elements__teA<>(elem2)
                        )));
        }
        for (Map.Entry<state, l2_l3_nonterminals__cons_l3<state, stack>> elem2 : General_Map.get_default(General_Map.get_default(S3map, e.get_edge_trg(), new HashMap<>()), e.get_edge_push().get(0), new HashMap<>()).entrySet()) {
            state qs = elem2.getKey();
            l2_l3_nonterminals__cons_l2<state, stack> elem3 = General_Map.get_default(General_Map.get_default(S2map, qs, new HashMap<>()), e.get_edge_pop().get(0), null);
            if (elem3 == null)
                continue;
            result.add(new cfg_step_label<>(
                    elem1,
                    General_List.list_by_enumeration(
                            new two_elements__teA<>(elem2.getValue()),
                            new two_elements__teA<>(elem3)
                    )));
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT__edges_l2(
            epda<state, event, stack> epda,
            Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map,
            Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__edges_l2");

        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!(e.get_edge_event() instanceof option__None)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> new_edges = F_SDPDA_TO_CFG_OPT__edges_l2_read(e, S2map);
                result.addAll(new_edges);
                if (reverse != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : new_edges)
                        reverse.put(elem, e);
            }
        result.addAll(F_SDPDA_TO_CFG_OPT__edges_l2_final(epda.get_epda_marking(), S2map));
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!e.get_edge_push().isEmpty() && !(e.get_edge_event() instanceof option__Some)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> new_edges = F_SDPDA_TO_CFG_OPT__edges_l2_push(e, S2map, S3map);
                result.addAll(new_edges);
                if (reverse != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : new_edges)
                        reverse.put(elem, e);
            }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    inner__F_SDPDA_TO_CFG_OPT(
            epda<state, event, stack> epda,
            int k,
            Map<state, Set<stack>> has_auxiliary_for,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering inner__F_SDPDA_TO_CFG_OPT: " + "epda=" + epda.log_info());

        tuple2<General_Set<l2_l3_nonterminals__cons_l2<state, stack>>, General_Set<l2_l3_nonterminals__cons_l3<state, stack>>> X = null;
        if (Config.instance.F_SDPDA_TO_CFG_OPT__nonterminals___BACK_TO_BACK) {
            tuple2<General_Set<l2_l3_nonterminals__cons_l2<state, stack>>, General_Set<l2_l3_nonterminals__cons_l3<state, stack>>>
                    Xalt = F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE(epda, k, has_auxiliary_for);
            tuple2<General_Set<l2_l3_nonterminals__cons_l2<state, stack>>, General_Set<l2_l3_nonterminals__cons_l3<state, stack>>>
                    Xstd = F_SDPDA_TO_CFG_OPT__nonterminals(epda, k);
            if (!Xalt.equals(Xstd)) {
                System.out.println(Xalt.get_value1().get_value().size());
                System.out.println(Xstd.get_value1().get_value().size());
                for (l2_l3_nonterminals__cons_l2<state, stack> x : Xstd.get_value1().get_value())
                    if (!Xalt.get_value1().get_value().contains(x))
                        System.out.println(x);
                System.out.println(Xalt.get_value2().get_value().size());
                System.out.println(Xstd.get_value2().get_value().size());
                for (l2_l3_nonterminals__cons_l3<state, stack> x : Xalt.get_value2().get_value())
                    if (!Xstd.get_value2().get_value().contains(x))
                        System.out.println("1: " + x);

                for (l2_l3_nonterminals__cons_l3<state, stack> x : Xstd.get_value2().get_value())
                    if (!Xalt.get_value2().get_value().contains(x))
                        System.out.println("2: " + x);

                throw new RuntimeException();
            }
            X = Xalt;
        } else {
            if (Config.instance.F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE)
                X = F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE(epda, k, has_auxiliary_for);
            else
                X = F_SDPDA_TO_CFG_OPT__nonterminals(epda, k);
        }
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "DONE F_SDPDA_TO_CFG_OPT__nonterminals: " + "X.1=" + X.get_value1().get_value().size() + ", X.2=" + X.get_value2().get_value().size());

        l2_l3_nonterminals__abstract<state, stack> new_initial = new l2_l3_nonterminals__cons_l2<>(epda.get_epda_initial(), epda.get_epda_box());
        if (!X.get_value1().get_value().contains(new_initial))
            return new option__None<>();


        Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map = new HashMap<>();
        for (l2_l3_nonterminals__cons_l2<state, stack> elem : X.get_value1().get_value()) {
            S2map.putIfAbsent(elem.get_value1(), new HashMap<>());
            S2map.get(elem.get_value1()).put(elem.get_value2(), elem);
        }

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "DONE F_SDPDA_TO_CFG_OPT__nonterminals: " + "S2map constructed");


        Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map = new HashMap<>();
        for (l2_l3_nonterminals__cons_l3<state, stack> elem : X.get_value2().get_value()) {
            S3map.putIfAbsent(elem.get_value1(), new HashMap<>());
            S3map.get(elem.get_value1()).putIfAbsent(elem.get_value2(), new HashMap<>());
            S3map.get(elem.get_value1()).get(elem.get_value2()).put(elem.get_value3(), elem);
        }

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "DONE F_SDPDA_TO_CFG_OPT__nonterminals: " + "S3map constructed");


        List<l2_l3_nonterminals__abstract<state, stack>> nonterminals = new ArrayList<>();
        nonterminals.addAll(X.get_value1().get_value());
        nonterminals.addAll(X.get_value2().get_value());
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> productions =
                new ArrayList<>();
        productions.addAll(F_SDPDA_TO_CFG_OPT__edges_l2(epda, S2map, S3map, reverse));
        productions.addAll(F_SDPDA_TO_CFG_OPT__edges_l3(epda, S3map, reverse));

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "F_SDPDA_TO_LR1_OPT done: " + "productions=" + productions.size());
        return new option__Some<>(new cfg<>(
                nonterminals,
                epda.get_epda_events(),
                new_initial,
                productions));
    }

    /**
     * result is the last argument
     */
    private static <state extends General, event extends General, stack extends General>
    void
    F_SDPDA_TO_CFG_OPT__step_closure__l2_init(
            epda<state, event, stack> epda,
            stack x,
            Map<state, General_Set<General_List<stack>>> R,
            accessibility_graph<state, stack> result) {
        result.get_value().get_value().putIfAbsent(new option__None<>(), new General_Set<>(new HashSet<>(), true));
        for (state q : R.keySet()) {
            for (General_List<stack> w : R.get(q).get_value()) {
                if (w.get_value().size() > 0 && w.get_value().get(0).equals(x)) {
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>
                            elem = new option__Some<>(new tuple3<>(q, w, new option__Some<>(new General_Integer(1))));
                    result.get_value().get_value().get(new option__None<>()).get_value().add(elem);
                } else if (w.get_value().isEmpty()) {
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>
                            elem = new option__Some<>(new tuple3<>(q, new General_List<>(General_List.list_by_enumeration(x)), new option__Some<>(new General_Integer(1))));
                    result.get_value().get_value().get(new option__None<>()).get_value().add(elem);
                }
            }
        }
    }


    private static <state extends General, event extends General, stack extends General>
    void
    F_SDPDA_TO_CFG_OPT__step_closure__l2_init__LESS_INITIAL(
            Set<stack> stacks,
            Map<state, General_Set<General_List<stack>>> R,
            accessibility_graph<state, stack> result) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> target_set = new HashSet<>();
        target_set = R.entrySet().parallelStream().map(
                entry -> {
                    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result_set = new HashSet<>();
                    Set<stack> top_stacks = new HashSet<>();
                    if (entry.getValue().get_value().isEmpty())
                        return result_set;
                    if (entry.getValue().get_value().contains(new General_List<>(new ArrayList<>()))) {
                        top_stacks.addAll(stacks);
                    } else
                        for (General_List<stack> list : entry.getValue().get_value())
                            top_stacks.add(list.get_value().get(0));
                    for (stack s : top_stacks) {
                        option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>
                                elem = new option__Some<>(new tuple3<>(entry.getKey(), new General_List<>(General_List.list_by_enumeration(s)), new option__Some<>(new General_Integer(1))));
                        result_set.add(elem);
                    }
                    return result_set;
                }
        ).reduce(Collections.synchronizedSet(new HashSet<>()), (x, y) -> {
            x.addAll(y);
            return x;
        });
        result.get_value().get_value().put(new option__None<>(), new General_Set<>(target_set, true));
    }

    private static <state extends General, event extends General, stack extends General>
    void
    F_SDPDA_TO_CFG_OPT__step_closure__l3_init(
            epda<state, event, stack> epda,
            stack x,
            Map<state, General_Set<General_List<stack>>> R,
            accessibility_graph<state, stack> result) {
        result.get_value().get_value().putIfAbsent(new option__None<>(), new General_Set<>(new HashSet<>(), true));
        Set<state> states = F_SDPDA_TO_CFG_OPT__push_target_state(epda, x);
        for (state q : R.keySet()) {
            for (General_List<stack> wX : R.get(q).get_value()) {
                if (wX.get_value().size() > 0
                        && wX.get_value().get(0).equals(x)
                        && states.contains(q)) {
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>
                            elem = new option__Some<>(new tuple3<>(q, wX, new option__Some<>(new General_Integer(1))));
                    result.get_value().get_value().get(new option__None<>()).get_value().add(elem);
                }
            }
        }
    }

    private static <state extends General, event extends General, stack extends General>
    tuple2<General_Set<l2_l3_nonterminals__cons_l2<state, stack>>, General_Set<l2_l3_nonterminals__cons_l3<state, stack>>>
    F_SDPDA_TO_CFG_OPT__nonterminals(
            epda<state, event, stack> epda,
            int k) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__nonterminals: " + "epda=" + epda.log_info());

        Map<state, General_Set<General_List<stack>>> R = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(epda, k);
        return new tuple2<>(
                F_SDPDA_TO_CFG_OPT__l2_approx(epda, k, R),
                F_SDPDA_TO_CFG_OPT__l3_approx(epda, k, R));
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l2<state, stack>>
    F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE__SINGLE_THREAD(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> closure) {
        Set<l2_l3_nonterminals__cons_l2<state, stack>> result = new HashSet<>();
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> added = closure.get_value().get_value().get(new option__None<>()).get_value();
        int index1 = 0;
        int index1_MAX = added.size();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem1 : added) {
            index1++;
            if (index1 % 1000 == 0)
                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE: 1: index1=" + index1 + " ,index1_MAX=" + index1_MAX + ", result=" + result.size());

            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem1C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem1).the();

            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> done = new HashSet<>();
            ArrayDeque<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo = new ArrayDeque<>();
            todo.add(elem1);
            done.add(elem1);
            while (!todo.isEmpty()) {
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> current = todo.poll();


                Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> set = new HashSet<>(General_Map.get_default(closure.get_value().get_value(), current, new General_Set<>(new HashSet<>())).get_value());
                set.add(current);
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem2 : set) {
                    if (!done.contains(elem2)) {
                        todo.add(elem2);
                        done.add(elem2);
                    } else if (current != elem2)
                        continue;

                    tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem2C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem2).the();
                    if (epda.get_epda_marking().contains(elem2C.get_value1())
                            && !(elem2C.get_value3().equals(new option__Some<>(new General_Integer(0)))))
                        result.add(new l2_l3_nonterminals__cons_l2<>(elem1C.get_value1(), elem1C.get_value2().get_value().get(0)));
                }
            }
        }
        return new ArrayList<>(result);
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l2<state, stack>>
    F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> closure) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE");
        Set<l2_l3_nonterminals__cons_l2<state, stack>> result = new HashSet<>();
        List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo = new ArrayList<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> x : closure.get_value().get_value().get(new option__None<>()).get_value()) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> xC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) x).the();
            todo.add(xC);
        }

        // needed in the computation
        //epda.get_map_source_pop_edges(null, null);
        //if (Config.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT >= 0)
        //    T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT, null);


        final AtomicBoolean abort = new AtomicBoolean(false);
        final List<Callback> callbackList = new ArrayList<>();
        callbackList.add(new T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Store_Result_Callback(result, abort));
        Config.instance.local_solver.register_callbacks(abort, callbackList);


        boolean added = false;
        for (List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> problem : Config.split(1, 200, 10, todo)) {
            Config.instance.local_solver.add_problem(
                    Config.T03_06_02_SDPDA_TO_CFG_OPT_PRIORITY,
                    abort,
                    new T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem<>(problem, closure, epda
                    ));
            added = true;
        }
        if (added) {
            try {
                Config.instance.local_solver.wait_for_completion(abort);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE");
        return new ArrayList<>(result);
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l3<state, stack>>
    F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> closure,
            int k) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE");
        Set<tuple2<state, General_List<stack>>> done = new HashSet<>();
        Set<l2_l3_nonterminals__cons_l3<state, stack>> result = new HashSet<>();
        List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo = new ArrayList<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> x : closure.get_value().get_value().get(new option__None<>()).get_value()) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> xC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) x).the();
            todo.add(xC);
            done.add(new tuple2<>(xC.get_value1(), xC.get_value2()));
        }

        // needed in the computation
        epda.get_map_source_pop_edges(null, null);
        if (Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT() >= 0)
            T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT(), null);


        final AtomicBoolean abort = new AtomicBoolean(false);
        final List<Callback> callbackList = new ArrayList<>();
        callbackList.add(new T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Store_Result_Callback(result, abort));
        Config.instance.local_solver.register_callbacks(abort, callbackList);


        boolean added = false;
        for (List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> problem : Config.split(1, 2000, 10, todo)) {
            Config.instance.local_solver.add_problem(
                    Config.T03_06_02_SDPDA_TO_CFG_OPT_PRIORITY,
                    abort,
                    new T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem<>(problem, done, closure, epda, k
                    ));
            added = true;
        }
        if (added) {
            try {
                Config.instance.local_solver.wait_for_completion(abort);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE");
        return new ArrayList<>(result);
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l3<state, stack>>
    F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE__SINGLE_THREAD(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> closure,
            int k) {


        Set<tuple2<state, General_List<stack>>> done = new HashSet<>();
        Set<l2_l3_nonterminals__cons_l3<state, stack>> result = new HashSet<>();
        ArrayDeque<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo = new ArrayDeque<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> x : closure.get_value().get_value().get(new option__None<>()).get_value()) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> xC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) x).the();
            todo.add(xC);
            done.add(new tuple2<>(xC.get_value1(), xC.get_value2()));
        }


        int index1 = 0;
        long last = System.currentTimeMillis();
        while (!todo.isEmpty()) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem_start = todo.poll();
            int remaining = todo.size();
            index1++;

            long now = System.currentTimeMillis();
            if (now - last > 1000) {
                last = System.currentTimeMillis();
                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE: 1: index1=" + index1 + " ,remaining=" + remaining + ", result=" + result.size());
            }


            state src = elem_start.get_value1();
            stack A = elem_start.get_value2().get_value().get(0);

            Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> not_extend_with = new HashSet<>();
            not_extend_with.add(elem_start);

            ArrayDeque<tuple2<General_Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo_queue = new ArrayDeque<>();
            todo_queue.add(new tuple2<>(new General_Set<>(new HashSet<>()), elem_start));
            while (!todo_queue.isEmpty()) {
                if (log.isInfoEnabled())
                    if (todo_queue.size() % 1000 == 0)
                        log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE: 1: index1=" + index1 + " ,remaining=" + remaining + ", result=" + result.size() + ", todo_queue=" + todo_queue.size());

                tuple2<General_Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, tuple3<state, General_List<stack>, option__abstract<General_Integer>>> current = todo_queue.poll();
                tuple3<state, General_List<stack>, option__abstract<General_Integer>> currentC = current.get_value2();
                Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> used_in_trace = new HashSet<>(current.get_value1().get_value());
                used_in_trace.add(currentC);

                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> descendant : General_Map.get_default(closure.get_value().get_value(), new option__Some<>(currentC), new General_Set<>(new HashSet<>())).get_value()) {
                    tuple3<state, General_List<stack>, option__abstract<General_Integer>> descendantC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) descendant).the();
                    if (!current.get_value1().get_value().contains(descendantC)) {
                        if (!not_extend_with.contains(descendantC)) {
                            todo_queue.add(new tuple2<>(new General_Set<>(used_in_trace), descendantC));
                            not_extend_with.add(descendantC);
                        }
                    }
                }

                // currentC has get_value3() of (None) or (Some 1)
                if (currentC.get_value3() instanceof option__Some
                        && ((option__Some<General_Integer>) currentC.get_value3()).get_value().get_value() > 1)
                    continue;
                // currentC may pop A
                if (!(currentC.get_value2().get_value().isEmpty() || currentC.get_value2().get_value().get(0).equals(A)))
                    continue;
                // currentC has state where pop of
                boolean has_added = false;
                for (epda_step_label<state, event, stack> step_label : epda.get_map_source_pop_edges(currentC.get_value1(), A)) {
                    has_added = true;
                    state trg = step_label.get_edge_trg();
                    l2_l3_nonterminals__cons_l3 elem = new l2_l3_nonterminals__cons_l3<>(src, A, trg);
                    result.add(elem);
                }
                if (has_added) {
                    Set<option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> now_added = new HashSet<>();
                    for (tuple3<state, General_List<stack>, option__abstract<General_Integer>> candidate : used_in_trace) {
                        if (candidate.get_value2().get_value().size() > 0) {
                            boolean shorter_stack_already_contained = false;
                            for (int i = 0; i <= candidate.get_value2().get_value().size(); i++)
                                if (done.contains(new tuple2<>(candidate.get_value1(),
                                        General_List.take(candidate.get_value2(), i)
                                ))) {
                                    shorter_stack_already_contained = true;
                                    break;
                                }
                            if (shorter_stack_already_contained)
                                continue;
                            tuple3<state, General_List<stack>, option__abstract<General_Integer>> new_todo = new tuple3<>(
                                    candidate.get_value1(),
                                    candidate.get_value2(),
                                    new option__Some<>(new General_Integer(1)));
                            todo.add(new_todo);
                            now_added.add(new option__Some<>(new_todo));
                            done.add(new tuple2<>(candidate.get_value1(), candidate.get_value2()));
                        } else {
                            for (stack X : Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT() >= 0 ?
                                    T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT(), candidate.get_value1())
                                    : epda.get_epda_gamma()) {
                                tuple3<state, General_List<stack>, option__abstract<General_Integer>> new_todo = new tuple3<>(
                                        candidate.get_value1(),
                                        new General_List<>(General_List.list_by_enumeration(X)),
                                        new option__Some<>(new General_Integer(1)));
                                if (!done.contains(new tuple2<>(candidate.get_value1(), new General_List<>(General_List.list_by_enumeration(X))))) {
                                    todo.add(new_todo);
                                    now_added.add(new option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>(new_todo));
                                    done.add(new tuple2<>(candidate.get_value1(), new General_List<>(General_List.list_by_enumeration(X))));
                                }
                            }
                        }
                    }
                    if (!now_added.isEmpty()) {
                        closure.get_value().get_value().get(new option__None<>()).get_value().addAll(now_added);
                        //System.out.println("updating closure " + now_added.size());
                        closure = F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, closure, null, k);
                        //System.out.println("updating closure done");
                    }


                }

            }
        }
        return new ArrayList<>(result);
    }

    /**
     * we compute the graph approximation first with all starting points.
     */
    private static <state extends General, event extends General, stack extends General>
    tuple2<General_Set<l2_l3_nonterminals__cons_l2<state, stack>>, General_Set<l2_l3_nonterminals__cons_l3<state, stack>>>
    F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE(
            epda<state, event, stack> epda,
            int k,
            Map<state, Set<stack>> has_auxiliary_for) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: " + "epda=" + epda.log_info());
        General_Set<l2_l3_nonterminals__cons_l2<state, stack>> l2result;
        General_Set<l2_l3_nonterminals__cons_l3<state, stack>> l3result;
        if (Config.instance.F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE__ONE_CLOSURE_FOR_ALL) {
            Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_EPDA_AIA");
            Map<state, General_Set<General_List<stack>>> R = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(epda, k);
            Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_EPDA_AIA");

            Set<stack> stacks = F_SDPDA_TO_CFG_OPT__pushed_symbols(epda);

            {
                accessibility_graph<state, stack> initial2 = new accessibility_graph<>();
                initial2.get_value().get_value().putIfAbsent(new option__None<>(), new General_Set<>(new HashSet<>(), true));
                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__l2_init");
                if (Config.T03_06_02_SDPDA_TO_CFG_OPT___LESS_L2_INITIAL) {
                    Set<stack> local_stacks = new HashSet<>(stacks);
                    local_stacks.add(epda.get_epda_box());
                    F_SDPDA_TO_CFG_OPT__step_closure__l2_init__LESS_INITIAL(local_stacks, R, initial2);
                } else {
                    for (stack A : stacks)
                        F_SDPDA_TO_CFG_OPT__step_closure__l2_init(epda, A, R, initial2);
                    F_SDPDA_TO_CFG_OPT__step_closure__l2_init(epda, epda.get_epda_box(), R, initial2);
                }
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__l2_init");
                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__step_closure__l2_init done " + initial2.get_value().get_value().get(new option__None<>()).get_value().size());

                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__fp");
                accessibility_graph<state, stack> closurel2 = F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, initial2, null, k);
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__fp");
                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__step_closure__fp done " + closurel2.get_value().get_value().size());

                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE");
                l2result = new General_Set<>(new HashSet<>(F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE(epda, closurel2)));
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE");

                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE done " + l2result.get_value().size());
            }

            {
                accessibility_graph<state, stack> initial3 = new accessibility_graph<>();
                initial3.get_value().get_value().putIfAbsent(new option__None<>(), new General_Set<>(new HashSet<>(), true));
                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__l3_init");
                for (stack A : stacks)
                    F_SDPDA_TO_CFG_OPT__step_closure__l3_init(epda, A, R, initial3);
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__l3_init");

                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__step_closure__l3_init done " + initial3.get_value().get_value().get(new option__None<>()).get_value().size());

                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__fp");
                accessibility_graph<state, stack> closurel3 = F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, initial3, null, k);
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__step_closure__fp");
                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__step_closure__fp done " + closurel3.get_value().get_value().size());

                Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE");
                l3result = new General_Set<>(new HashSet<>(F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE(epda, closurel3, k)));
                Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE:F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE");

                if (log.isInfoEnabled())
                    log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE: F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE done " + l3result.get_value().size());
            }
        } else {
            Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE");
            T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, k, null);

            if (log.isInfoEnabled())
                log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE");
            epda.get_edges_by_type_and_stack_and_source();
            epda.get_map_source_pop_edges(null, null);
            List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2 = new ArrayList<>();
            List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3 = new ArrayList<>();
            final List<Callback> callbackList = new ArrayList<>();
            callbackList.add(new T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Store_Result_Callback<>(result_l2, result_l3));
            AtomicBoolean abort = new AtomicBoolean(false);
            Config.instance.local_solver.register_callbacks(abort, callbackList);
            for (state q : epda.get_epda_states())
                Config.instance.local_solver.add_problem(
                        Config.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_PRIORITY,
                        abort,
                        new T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem<>(q, epda, k, has_auxiliary_for));
            try {
                Config.instance.local_solver.wait_for_completion(abort);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            l2result = new General_Set<>(new HashSet<>(result_l2));
            l3result = new General_Set<>(new HashSet<>(result_l3));
            if (log.isInfoEnabled())
                log.info(Config.callStack() + "F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE l2result=" + l2result.get_value().size() + ", l3result=" + l3result.get_value().size());
            Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__nonterminals___ALTERNATIVE");
        }
        return new tuple2<>(l2result, l3result);
    }

    /**
     * F_SDPDA_TO_CFG_OPT__transitive_reflexive_closure__fp_one is implicitly implemented
     */
    private static <state extends General, stack extends General>
    accessibility_graph<state, stack>
    F_SDPDA_TO_CFG_OPT__transitive_reflexive_closure__fp(
            accessibility_graph<state, stack> S) {
        accessibility_graph<state, stack> result_accessibility_graph = new accessibility_graph<>();
        accessibility_graph<state, stack> todo = new accessibility_graph<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem : S.get_value().get_value().keySet()) {
            todo.get_value().get_value().putIfAbsent(elem, new General_Set<>(new HashSet<>(), true));
            todo.get_value().get_value().get(elem).get_value().add(elem);
            result_accessibility_graph.get_value().get_value().putIfAbsent(elem, new General_Set<>(new HashSet<>(), true));
            result_accessibility_graph.get_value().get_value().get(elem).get_value().add(elem);
            for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> other_elem : S.get_value().get_value().get(elem).get_value()) {
                todo.get_value().get_value().putIfAbsent(other_elem, new General_Set<>(new HashSet<>(), true));
                todo.get_value().get_value().get(other_elem).get_value().add(other_elem);
                result_accessibility_graph.get_value().get_value().putIfAbsent(other_elem, new General_Set<>(new HashSet<>(), true));
                result_accessibility_graph.get_value().get_value().get(other_elem).get_value().add(other_elem);
            }
        }

        while (!todo.get_value().get_value().isEmpty()) {
            accessibility_graph<state, stack> new_todo = new accessibility_graph<>();
            for (Map.Entry<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>> entry : todo.get_value().get_value().entrySet()) {
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem1 = entry.getKey();
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem2 : entry.getValue().get_value()) {
                    for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem3 : General_Map.get_default(S.get_value().get_value(), elem2, new General_Set<>(new HashSet<>())).get_value()) {
                        General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> new_elems = result_accessibility_graph.get_value().get_value().get(elem3);
                        if (new_elems == null)
                            continue;
                        new_todo.get_value().get_value().putIfAbsent(elem1, new General_Set<>(new HashSet<>(), true));
                        new_todo.get_value().get_value().get(elem1).get_value().addAll(new_elems.get_value());
                    }
                }
            }
            for (Map.Entry<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>> entry : new_todo.get_value().get_value().entrySet()) {
                new_todo.get_value().get_value().get(entry.getKey()).get_value().removeAll(result_accessibility_graph.get_value().get_value().get(entry.getKey()).get_value());
                result_accessibility_graph.get_value().get_value().get(entry.getKey()).get_value().addAll(new_todo.get_value().get_value().get(entry.getKey()).get_value());
            }
            todo = new_todo;
        }
        return result_accessibility_graph;
    }

    public static <state extends General, event extends General, stack extends General>
    accessibility_graph<state, stack>
    F_SDPDA_TO_CFG_OPT__step_closure__fp(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo_input,
            int k) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT__step_closure__fp");
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo = null;
        if (todo_input == null)
            todo = S.get_value().get_value().get(new option__None<>()).get_value();
        else
            todo = todo_input;
        while (!todo.isEmpty()) {
            todo = F_SDPDA_TO_CFG_OPT__step_closure__fp_one(epda, S, todo, k, epda.get_edges_by_type_and_stack_and_source());
        }
        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT__step_closure__fp");
        return S;
    }

    public static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__fp_one(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            int k,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();

        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__read__exists__greater_than_0(epda, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__read__not_exists__none(epda, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__pop__exists__greater_than_0(epda, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__pop__exists__none(epda, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__pop__not_exists__none(epda, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__push__exists__none(epda, k, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__push__not_exists__none(epda, k, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__push__exists__less_than_k(epda, k, S, todo, edges_by_type_and_stack_and_source));
        result.addAll(F_SDPDA_TO_CFG_OPT__step_closure__push__exists__k(epda, k, S, todo, edges_by_type_and_stack_and_source));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__read__exists__greater_than_0(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3().equals(new option__Some<>(new General_Integer(0))))
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack X = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.EXECUTE, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            X, new HashSet<>())) {
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        elemYC.get_value2(),
                        elemYC.get_value3()
                ));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__read__not_exists__none(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__Some)
                continue;
            if (!elemYC.get_value2().get_value().isEmpty())
                continue;
            for (Set<epda_step_label<state, event, stack>> edge_set :
                    General_Map.get_default(
                            General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.EXECUTE, new HashMap<>()),
                            elemYC.get_value1(), new HashMap<>()).values()) {
                for (epda_step_label<state, event, stack> e : edge_set) {
                    stack X = e.get_edge_pop().get(0);
                    S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                            e.get_edge_trg(),
                            new General_List<>(General_List.list_by_enumeration(X)),
                            elemYC.get_value3()
                    ));
                    if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                        result.add(new_elem);
                }
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__pop__exists__greater_than_0(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__None)
                continue;
            int num = ((option__Some<General_Integer>) elemYC.get_value3()).get_value().get_value();
            if (num == 0)
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack X = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.POP, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            X, new HashSet<>())) {
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        General_List.drop(elemYC.get_value2(), 1),
                        new option__Some<>(new General_Integer(num - 1))));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__pop__exists__none(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__Some)
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack X = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.POP, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            X, new HashSet<>())) {
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        General_List.drop(elemYC.get_value2(), 1),
                        elemYC.get_value3()
                ));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__pop__not_exists__none(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__Some)
                continue;
            if (elemYC.get_value2().get_value().size() > 0)
                continue;
            for (Set<epda_step_label<state, event, stack>> edge_set :
                    General_Map.get_default(
                            General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.POP, new HashMap<>()),
                            elemYC.get_value1(), new HashMap<>()).values()) {
                for (epda_step_label<state, event, stack> e : edge_set) {
                    S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                            e.get_edge_trg(),
                            elemYC.get_value2(),
                            elemYC.get_value3()
                    ));
                    if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                        result.add(new_elem);
                }
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__push__exists__none(
            epda<state, event, stack> epda,
            int k,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__Some)
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack Y = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.PUSH, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            Y, new HashSet<>())) {
                stack X = e.get_edge_push().get(0);
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        General_List.take(General_List.cons(X, elemYC.get_value2()), k),
                        elemYC.get_value3()
                ));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__push__not_exists__none(
            epda<state, event, stack> epda,
            int k,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__Some)
                continue;
            if (!elemYC.get_value2().get_value().isEmpty())
                continue;
            for (Set<epda_step_label<state, event, stack>> edge_set :
                    General_Map.get_default(
                            General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.PUSH, new HashMap<>()),
                            elemYC.get_value1(), new HashMap<>()).values()) {
                for (epda_step_label<state, event, stack> e : edge_set) {
                    S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                    option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                            e.get_edge_trg(),
                            new General_List<>(General_List.take(e.get_edge_push(), k)),
                            elemYC.get_value3()
                    ));
                    if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                        result.add(new_elem);
                }
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__push__exists__less_than_k(
            epda<state, event, stack> epda,
            int k,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__None)
                continue;
            int num = ((option__Some<General_Integer>) elemYC.get_value3()).get_value().get_value();
            if (num == 0)
                continue;
            if (!(num + 1 <= k))
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack Y = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.PUSH, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            Y, new HashSet<>())) {
                stack X = e.get_edge_push().get(0);
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        General_List.take(General_List.cons(X, elemYC.get_value2()), k),
                        new option__Some<>(new General_Integer(num + 1))
                ));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
    F_SDPDA_TO_CFG_OPT__step_closure__push__exists__k(
            epda<state, event, stack> epda,
            int k,
            accessibility_graph<state, stack> S,
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo,
            Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source) {
        Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> result = new HashSet<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elemY : todo) {
            if (elemY instanceof option__None)
                continue;
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemYC =
                    ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elemY).the();
            if (elemYC.get_value3() instanceof option__None)
                continue;
            int num = ((option__Some<General_Integer>) elemYC.get_value3()).get_value().get_value();
            if (num == 0)
                continue;
            if (num != k)
                continue;
            if (elemYC.get_value2().get_value().size() == 0)
                continue;
            stack Y = elemYC.get_value2().get_value().get(0);
            for (epda_step_label<state, event, stack> e :
                    General_Map.get_default(
                            General_Map.get_default(
                                    General_Map.get_default(edges_by_type_and_stack_and_source, datatypes.formalisms.epda.PUSH, new HashMap<>()),
                                    elemYC.get_value1(), new HashMap<>()),
                            Y, new HashSet<>())) {
                stack X = e.get_edge_push().get(0);
                S.get_value().get_value().putIfAbsent(elemY, new General_Set<>(new HashSet<>(), true));
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem = new option__Some<>(new tuple3<>(
                        e.get_edge_trg(),
                        General_List.take(General_List.cons(X, elemYC.get_value2()), k),
                        new option__None<>()
                ));
                if (S.get_value().get_value().get(elemY).get_value().add(new_elem))
                    result.add(new_elem);
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l2<state, stack>>
    F_SDPDA_TO_CFG_OPT__l2_approx_1(
            epda<state, event, stack> epda,
            stack A,
            accessibility_graph<state, stack> CL,
            accessibility_graph<state, stack> initial) {
        List<l2_l3_nonterminals__cons_l2<state, stack>> result = new ArrayList<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem1 : initial.get_value().get_value().get(new option__None<>()).get_value()) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem1C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem1).the();
            for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem2 : CL.get_value().get_value().get(elem1).get_value()) {
                tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem2C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem2).the();
                if (epda.get_epda_marking().contains(elem2C.get_value1())
                        && !(elem2C.get_value3().equals(new option__Some<>(new General_Integer(0)))))
                    result.add(new l2_l3_nonterminals__cons_l2<>(elem1C.get_value1(), A));
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    General_Set<l2_l3_nonterminals__cons_l2<state, stack>>
    F_SDPDA_TO_CFG_OPT__l2_approx(
            epda<state, event, stack> epda,
            int k,
            Map<state, General_Set<General_List<stack>>> R) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l2_approx: " + "epda=" + epda.log_info());

        General_Set<l2_l3_nonterminals__cons_l2<state, stack>> result = new General_Set<>(new HashSet<>(), true);
        Set<stack> stacks = F_SDPDA_TO_CFG_OPT__pushed_symbols(epda);
        stacks.add(epda.get_epda_box());
        for (stack A : stacks) {
            if (log.isInfoEnabled())
                log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l2_approx: " + "A=" + A + ", stacks=" + stacks.size());
            accessibility_graph<state, stack> initial = new accessibility_graph<>();
            F_SDPDA_TO_CFG_OPT__step_closure__l2_init(epda, A, R, initial);
            result.get_value().addAll(F_SDPDA_TO_CFG_OPT__l2_approx_1(epda, A,
                    F_SDPDA_TO_CFG_OPT__transitive_reflexive_closure__fp(F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, initial, null, k)), initial));
        }
        return result;
    }

    private static <stack extends General, state extends General, event extends General>
    Set<state>
    F_SDPDA_TO_CFG_OPT__push_target_state(
            epda<state, event, stack> epda,
            stack x) {
        Set<state> result = new HashSet<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (e.get_edge_event() instanceof option__None
                    && e.get_edge_push().size() == 2
                    && e.get_edge_pop().size() == 1
                    && e.get_edge_push().get(1).equals(e.get_edge_pop().get(0)))
                result.add(e.get_edge_trg());
        return result;
    }

    private static <stack extends General, state extends General, event extends General>
    Set<stack>
    F_SDPDA_TO_CFG_OPT__pushed_symbols(
            epda<state, event, stack> epda) {
        Set<stack> result = new HashSet<stack>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (e.get_edge_event() instanceof option__None
                    && e.get_edge_push().size() == 2
                    && e.get_edge_pop().size() == 1
                    && e.get_edge_push().get(1).equals(e.get_edge_pop().get(0)))
                result.add(e.get_edge_push().get(0));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<l2_l3_nonterminals__cons_l3<state, stack>>
    F_SDPDA_TO_CFG_OPT__l3_approx_1(
            epda<state, event, stack> epda,
            accessibility_graph<state, stack> CL,
            accessibility_graph<state, stack> initial) {
        List<l2_l3_nonterminals__cons_l3<state, stack>> result = new ArrayList<>();
        for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem1 : General_Map.get_default(initial.get_value().get_value(), new option__None<>(), new General_Set<>(new HashSet<>())).get_value()) {
            for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem2 : CL.get_value().get_value().get(elem1).get_value()) {
                tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem2C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem2).the();
                List<stack> w = elem2C.get_value2().get_value();
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem3 : CL.get_value().get_value().get(elem2).get_value()) {
                    tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem3C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem3).the();
                    if (elem3C.get_value3() instanceof option__Some
                            && ((option__Some<General_Integer>) elem3C.get_value3()).get_value().get_value() > 0)
                        continue;
                    if (w.isEmpty()) {
                        for (stack A : Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT() >= 0 ?
                                T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT(), elem2C.get_value1())
                                : epda.get_epda_gamma()) {
                            l2_l3_nonterminals__cons_l3<state, stack> new_elem = new l2_l3_nonterminals__cons_l3<>(elem2C.get_value1(), A, elem3C.get_value1());
                            if (new_elem.toString().equals("⟪⟪nat:1⟫, ⟪nat:1⟫, ⟪nat:1⟫⟫"))
                                System.out.println("...");
                            result.add(new_elem);
                        }
                    } else {
                        l2_l3_nonterminals__cons_l3<state, stack> new_elem = new l2_l3_nonterminals__cons_l3<>(elem2C.get_value1(), w.get(0), elem3C.get_value1());
                        if (new_elem.toString().equals("⟪⟪nat:1⟫, ⟪nat:1⟫, ⟪nat:1⟫⟫"))
                            System.out.println("...");
                        result.add(new_elem);
                    }
                }
            }
        }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    General_Set<l2_l3_nonterminals__cons_l3<state, stack>>
    F_SDPDA_TO_CFG_OPT__l3_approx(
            epda<state, event, stack> epda,
            int k,
            Map<state, General_Set<General_List<stack>>> R) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l3_approx: " + "epda=" + epda.log_info());

        General_Set<l2_l3_nonterminals__cons_l3<state, stack>> result = new General_Set<>(new HashSet<>(), true);
        Set<stack> stacks = F_SDPDA_TO_CFG_OPT__pushed_symbols(epda);
        for (stack x : stacks) {
            if (log.isInfoEnabled())
                log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT__l3_approx: " + "x=" + x);

            accessibility_graph<state, stack> initial = new accessibility_graph<state, stack>();
            F_SDPDA_TO_CFG_OPT__step_closure__l3_init(epda, x, R, initial);
            result.get_value().addAll(F_SDPDA_TO_CFG_OPT__l3_approx_1(epda,
                    F_SDPDA_TO_CFG_OPT__transitive_reflexive_closure__fp(F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, initial, null, k)), initial));
        }
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT(
            epda<state, event, stack> epda,
            int k,
            Map<state, Set<stack>> has_auxiliary_for,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT: " + "epda=" + epda.log_info() + ", k=" + k);

        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                inner__F_SDPDA_TO_CFG_OPT(epda, k, has_auxiliary_for, reverse);

        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT");
        return result;
    }

    @FunctionalInterface
    interface Function4<One, Two, Three, Four> {
        Four apply(One one, Two two, Three three);
    }

    @FunctionalInterface
    interface Function3<One, Two, Three> {
        Three apply(One one, Two two);
    }


}
