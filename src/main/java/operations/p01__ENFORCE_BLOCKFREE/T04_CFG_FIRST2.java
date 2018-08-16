package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
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
public class T04_CFG_FIRST2<nonterminal extends General, event extends General> {
    private static Logger log = LogManager.getLogger(T04_CFG_FIRST2.class.getName());
    private static T04_CFG_FIRST2 instance = null;
    int k = -1;
    private cfg<nonterminal, event> cfg = null;
    private Map<nonterminal, Set<option__abstract<event>>> table = null;

    private static T04_CFG_FIRST2 get_instance() {
        if (instance == null)
            instance = new T04_CFG_FIRST2();
        return instance;
    }

    public static <nonterminal extends General, event extends General>
    Set<List<event>>
    F_CFG_FIRST(
            cfg<nonterminal, event> cfg,
            int k,
            List<two_elements__abstract<nonterminal, event>> w) {
        Config.instance.measuring_thread.push("F_CFG_FIRST");
        if (get_instance().cfg != cfg || get_instance().k != k) {
            synchronized (cfg) {
                if (get_instance().cfg != cfg || get_instance().k != k) {
                    get_instance().cfg = cfg;
                    get_instance().k = k;
                    get_instance().table = null;
                    get_instance().F_CFG_FIRST__table();
                    log.info("reseted instance and computed table");
                }
            }
        }
        Set<List<event>> result = get_instance().inner_F_CFG_FIRST(w);
        Config.instance.measuring_thread.pop("F_CFG_FIRST");
        return result;
    }

    public static void remove_instance() {
        instance = null;
    }

    private Set<List<event>>
    inner_F_CFG_FIRST(
            List<two_elements__abstract<nonterminal, event>> w) {
        Set<List<event>> result = new HashSet<>();
        if (k == 0) {
            if (!F_CFG_FIRST__word(F_CFG_FIRST__table(), w).isEmpty()) {
                result.add(new ArrayList<>());
                return result;
            } else
                return result;
        } else if (k == 1) {
            for (option__abstract<event>
                    elem : F_CFG_FIRST__word(F_CFG_FIRST__table(), w))
                if (elem instanceof option__Some)
                    result.add(General_List.list_by_enumeration(((option__Some<event>) elem).the()));
            return result;
        } else
            return result;
    }

    private Set<option__abstract<event>>
    F_CFG_FIRST__word(
            Map<nonterminal, Set<option__abstract<event>>> map,
            List<two_elements__abstract<nonterminal, event>> w) {
        Set<option__abstract<event>>
                result = new HashSet<>();
        result.add(new option__None<>());
        for (two_elements__abstract<nonterminal, event> elem : w) {
            result.remove(new option__None<>());
            if (elem instanceof two_elements__teA) {
                result.addAll(map.get(((two_elements__teA<nonterminal, event>) elem).get_value()));
                if (!result.contains(new option__None<>()))
                    break;
            } else {
                result.add(new option__Some<>(((two_elements__teB<nonterminal, event>) elem).get_value()));
                break;
            }
        }
        return result;
//        if (w.isEmpty()) {
//            Set<option__abstract<event>>
//                    result = new HashSet<>();
//            result.add(new option__None<>());
//            return result;
//        } else {
//            if (w.get(0) instanceof two_elements__teA) {
//                Set<option__abstract<event>>
//                        result = map.get(((two_elements__teA<nonterminal, event>) w.get(0)).get_value());
//                if (result.contains(new option__None<>())) {
//                    result.remove(new option__None<>());
//                    result.addAll(F_CFG_FIRST__word(map, General_List.drop(w, 1)));
//                    return result;
//                } else {
//                    result.remove(new option__None<>());
//                    return result;
//                }
//            } else {
//                Set<option__abstract<event>>
//                        result = new HashSet<>();
//                result.add(new option__Some<>(((two_elements__teB<nonterminal, event>) w.get(0)).get_value()));
//                return result;
//            }
//        }
    }

    private Map<nonterminal, Set<option__abstract<event>>>
    F_CFG_FIRST__table() {
        if (table == null)
            inner__F_CFG_FIRST__table();
        return table;
    }

    private void
    inner__F_CFG_FIRST__table() {
        Config.instance.measuring_thread.push("inner__F_CFG_FIRST__table");
        log.info("computing table: inner__F_CFG_FIRST__table " + cfg.hashCode());

        Set<nonterminal> directly_eliminable = new HashSet<>();
        Map<nonterminal, Map<nonterminal, Set<List<two_elements__abstract<nonterminal, event>>>>> blocks = new HashMap<>();
        Map<nonterminal, Set<two_elements__abstract<nonterminal, event>>> pre_table = new HashMap<>();

        for (cfg_step_label<nonterminal, event> step_label : cfg.get_cfg_step_labels()) {
            List<two_elements__abstract<nonterminal, event>> w = step_label.get_prod_rhs();
            {
                int index = two_elements__teB.first_index_of_teB(w);
                if (index >= 0)
                    w = General_List.take(w, index + 1);
            }
            if (w.isEmpty())
                directly_eliminable.add(step_label.get_prod_lhs());
            else {
                if (w.get(0) instanceof two_elements__teA) {
                    nonterminal A = ((two_elements__teA<nonterminal, event>) w.get(0)).get_value();
                    blocks.putIfAbsent(A, new HashMap<>());
                    blocks.get(A).putIfAbsent(step_label.get_prod_lhs(), new HashSet<>());
                    blocks.get(A).get(step_label.get_prod_lhs()).add(General_List.drop(w, 1));
                }
                pre_table.putIfAbsent(step_label.get_prod_lhs(), new HashSet<>());
                pre_table.get(step_label.get_prod_lhs()).add(w.get(0));
            }
        }

        if (false) {
            for (nonterminal x : pre_table.keySet()) {
                for (two_elements__abstract<nonterminal, event> y : pre_table.get(x))
                    System.out.println("pretable: " + x + " -> " + y);
            }
            for (nonterminal x : blocks.keySet()) {
                for (nonterminal y : blocks.get(x).keySet())
                    System.out.println("blocks: " + x + " -> " + y + " -> " + blocks.get(x).get(y));
            }

            System.out.println("foo");
        }


        {
            Set<nonterminal> todo = new HashSet<>(directly_eliminable);
            while (!todo.isEmpty()) {
                nonterminal A = todo.iterator().next();
                todo.remove(A);

                if (!blocks.containsKey(A))
                    continue;
                Set<Map.Entry<nonterminal, Set<List<two_elements__abstract<nonterminal, event>>>>> entries = new HashSet<>(blocks.get(A).entrySet());
                blocks.remove(A);

                for (Map.Entry<nonterminal, Set<List<two_elements__abstract<nonterminal, event>>>> entry : entries) {
                    nonterminal lhs = entry.getKey();
                    for (List<two_elements__abstract<nonterminal, event>> w : entry.getValue()) {

                        boolean entirely_elininable = true;
                        for (int i = 0; i < w.size(); i++) {
                            two_elements__abstract<nonterminal, event> elem = w.get(i);
                            if (elem instanceof two_elements__teB) {
                                entirely_elininable = false;
                                pre_table.putIfAbsent(lhs, new HashSet<>());
                                pre_table.get(lhs).add(elem);
                                break;
                            } else if (elem instanceof two_elements__teA) {
                                nonterminal casted = ((two_elements__teA<nonterminal, event>) elem).get_value();
                                if (directly_eliminable.contains(casted)) {
                                    pre_table.putIfAbsent(lhs, new HashSet<>());
                                    pre_table.get(lhs).addAll(pre_table.getOrDefault(casted, new HashSet<>()));
                                    continue;
                                } else {
                                    entirely_elininable = false;
                                    blocks.putIfAbsent(casted, new HashMap<>());
                                    blocks.get(casted).putIfAbsent(lhs, new HashSet<>());
                                    blocks.get(casted).get(lhs).add(General_List.drop(w, i + 1));
                                    pre_table.putIfAbsent(lhs, new HashSet<>());
                                    pre_table.get(lhs).add(elem);
                                    break;
                                }
                            } else throw new RuntimeException();
                        }
                        if (entirely_elininable) {
                            directly_eliminable.add(lhs);
                            todo.add(lhs);
                        }
                    }
                }

                if (false) {
                    System.out.println();
                    for (nonterminal x : pre_table.keySet()) {
                        for (two_elements__abstract<nonterminal, event> y : pre_table.get(x))
                            System.out.println("pretable: " + x + " -> " + y);
                    }
                    for (nonterminal x : blocks.keySet()) {
                        for (nonterminal y : blocks.get(x).keySet())
                            System.out.println("blocks: " + x + " -> " + y + " -> " + blocks.get(x).get(y));
                    }
                    System.out.println();
                }
            }
        }

        table = new HashMap<>();
        for (nonterminal A : cfg.get_cfg_nonterminals())
            table.put(A, new HashSet<>());
        for (nonterminal A : directly_eliminable)
            table.get(A).add(new option__None<>());
        for (nonterminal A : cfg.get_cfg_nonterminals()) {
            Set<nonterminal> todo = new HashSet<>();
            Set<nonterminal> done = new HashSet<>();
            todo.add(A);
            done.add(A);
            while (!todo.isEmpty()) {
                nonterminal X = todo.iterator().next();
                todo.remove(X);
//                if (directly_eliminable.contains(X)) {
//                    table.get(X).add(new option__None<>());
//                }
                if (!pre_table.containsKey(X))
                    continue;
                for (two_elements__abstract<nonterminal, event> elem : pre_table.get(X)) {
                    if (elem instanceof two_elements__teA) {
                        nonterminal elemC = ((two_elements__teA<nonterminal, event>) elem).get_value();
                        if (!done.contains(elemC)) {
                            done.add(elemC);
                            todo.add(elemC);
                        }
                    } else {
                        event elemC = ((two_elements__teB<nonterminal, event>) elem).get_value();
//                        System.out.println(table.get(A));
                        table.get(A).add(new option__Some<>(elemC));
                    }
                }
            }
        }

        Config.instance.measuring_thread.pop("inner__F_CFG_FIRST__table");
    }
}