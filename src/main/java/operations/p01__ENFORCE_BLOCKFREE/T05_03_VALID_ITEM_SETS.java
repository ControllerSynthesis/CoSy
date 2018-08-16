package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.cfg_item;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Map;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_03_VALID_ITEM_SETS {
    private static Logger log = LogManager.getLogger(T05_03_VALID_ITEM_SETS.class.getName());

    private T05_03_VALID_ITEM_SETS() {
        throw new IllegalStateException("Utility Class");
    }

    private static <nonterminal extends General, event extends General>
    General_Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_INITIAL__fp_start(
            cfg<nonterminal, event> cfg) {
        Set<cfg_item<nonterminal, event>> result = new HashSet<>();
        for (cfg_step_label<nonterminal, event> production : cfg.get_cfg_step_labels_by_lhs().get(cfg.get_cfg_initial()))
            result.add(new cfg_item<>(production.get_prod_lhs(), new ArrayList<>(), production.get_prod_rhs(), new ArrayList<>()));
        return new General_Set<>(result);
    }

    private static <nonterminal extends General, event extends General>
    General_Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_GOTO__basis(
            two_elements__abstract<nonterminal, event> X,
            General_Set<cfg_item<nonterminal, event>> items) {
        Set<cfg_item<nonterminal, event>> result = new HashSet<>();
        for (cfg_item<nonterminal, event> item : items.get_value())
            F_VALID_ITEM_SET_GOTO__passes(X, item, result);
        return new General_Set<>(result);
    }

    /*
     * slightly different type
     * */
    private static <nonterminal extends General, event extends General>
    void
    F_VALID_ITEM_SET_GOTO__passes(
            two_elements__abstract<nonterminal, event> X,
            cfg_item<nonterminal, event> item,
            Set<cfg_item<nonterminal, event>> result) {
        if (item.get_cfg_item_rhs2().size() > 0
                && item.get_cfg_item_rhs2().get(0).equals(X)) {
            result.add(new cfg_item<>(
                    item.get_cfg_item_lhs(),
                    General_List.append(item.get_cfg_item_rhs1(), X),
                    General_List.drop(item.get_cfg_item_rhs2(), 1),
                    item.get_cfg_item_look_ahead()
            ));
        }
    }

    private static <nonterminal extends General, event extends General>
    General_Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_GOTO__descent__fp(
            cfg<nonterminal, event> cfg,
            int k,
            General_Set<cfg_item<nonterminal, event>> S,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained) {
        Set<cfg_item<nonterminal, event>> result = new HashSet<>(S.get_value());
        Set<cfg_item<nonterminal, event>> todo = new HashSet<>(S.get_value());
        while (!todo.isEmpty()) {
            todo = F_VALID_ITEM_SET_GOTO__descent__fp_one_1s(cfg, k, todo, old_obtained, new_obtained);
            todo.removeAll(result);
            result.addAll(todo);
        }
        return new General_Set<>(result);
    }

    private static <nonterminal extends General, event extends General>
    Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_GOTO__descent__fp_one_1s(
            cfg<nonterminal, event> cfg,
            int k,
            Set<cfg_item<nonterminal, event>> S,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained) {
        Set<cfg_item<nonterminal, event>> additional = new HashSet<>();
        for (cfg_item<nonterminal, event> x : S)
            F_VALID_ITEM_SET_GOTO__descent__fp_one_1(cfg, k, x, additional, old_obtained, new_obtained);
        return additional;
    }

    private static <nonterminal extends General, event extends General>
    void
    F_VALID_ITEM_SET_GOTO__descent__fp_one_1(
            cfg<nonterminal, event> cfg,
            int k,
            cfg_item<nonterminal, event> I,
            Set<cfg_item<nonterminal, event>> additional,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained) {
        if (old_obtained != null && old_obtained.containsKey(I)) {
//            log.info("item = " + I + "old_obtained.containsKey(I) = " + old_obtained.containsKey(I) + ", old_obtained.get(I) = " + old_obtained.get(I));
            additional.addAll(old_obtained.get(I));
            return;
        }
        if (new_obtained != null && new_obtained.containsKey(I)) {
//            log.info("item = " + I + "new_obtained.containsKey(I) = " + new_obtained.containsKey(I) + ", new_obtained.get(I) = " + new_obtained.get(I));
            additional.addAll(new_obtained.get(I));
            return;
        }
        if (I.get_cfg_item_rhs2().size() == 0 || (I.get_cfg_item_rhs2().get(0) instanceof two_elements__teB))
            return;
        nonterminal B = ((two_elements__teA<nonterminal, event>) I.get_cfg_item_rhs2().get(0)).get_value();
        Set<List<event>> events_lists;
        if (Config.USE_T04_CFG_FIRST2) {
            events_lists = T04_CFG_FIRST2.F_CFG_FIRST(cfg, k, General_List.append(General_List.drop(I.get_cfg_item_rhs2(), 1), two_elements__teB.lift(I.get_cfg_item_look_ahead())));
        } else {
            events_lists = T04_CFG_FIRST.F_CFG_FIRST(cfg, k, General_List.append(General_List.drop(I.get_cfg_item_rhs2(), 1), two_elements__teB.lift(I.get_cfg_item_look_ahead())));
        }

        if (new_obtained != null)
            new_obtained.putIfAbsent(I, new HashSet<>());
        Set<cfg_item<nonterminal, event>> set = null;
        if (new_obtained != null)
            set = new_obtained.get(I);
        for (cfg_step_label<nonterminal, event> prod : General_Map.get_default(cfg.get_cfg_step_labels_by_lhs(), B, new ArrayList<>())) {
            for (List<event> z : events_lists) {
                cfg_item<nonterminal, event> new_item = new cfg_item<>(B, new ArrayList<>(), prod.get_prod_rhs(), z);
                new_item.cache_hashcode();
                additional.add(new_item);
                if (set != null)
                    set.add(new_item);
            }
        }
    }

    public static <nonterminal extends General, event extends General>
    General_Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_GOTO(
            cfg<nonterminal, event> cfg,
            int k,
            two_elements__abstract<nonterminal, event> X,
            General_Set<cfg_item<nonterminal, event>> S,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained) {
        Config.instance.measuring_thread.push("F_VALID_ITEM_SET_GOTO");
        General_Set<cfg_item<nonterminal, event>> result = F_VALID_ITEM_SET_GOTO__descent__fp(cfg, k, F_VALID_ITEM_SET_GOTO__basis(X, S), old_obtained, new_obtained);
        Config.instance.measuring_thread.pop("F_VALID_ITEM_SET_GOTO");
        return result;
    }

    public static <nonterminal extends General, event extends General>
    General_Set<cfg_item<nonterminal, event>>
    F_VALID_ITEM_SET_INITIAL(
            cfg<nonterminal, event> cfg,
            int k,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained) {
        Config.instance.measuring_thread.push("F_VALID_ITEM_SET_INITIAL");
        General_Set<cfg_item<nonterminal, event>> result = F_VALID_ITEM_SET_GOTO__descent__fp(cfg, k, F_VALID_ITEM_SET_INITIAL__fp_start(cfg), old_obtained, new_obtained);
        Config.instance.measuring_thread.pop("F_VALID_ITEM_SET_INITIAL");
        return result;
    }

}