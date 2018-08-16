package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.basic_java_datatypes.General_Tuple3;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import import_export.XML_writer;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T02_03_CFG_TYPE_CONVERSION;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_05_CFG_TRIM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T01_COLLATE_CFG {

    private static final boolean DEBUG = false;
    private static Logger log = LogManager.getLogger(T01_COLLATE_CFG.class.getName());

    private T01_COLLATE_CFG() {
        throw new IllegalStateException("Utility Class");
    }


    public static <nonterminal extends General, event extends General>
    cfg<nonterminal, event>
    COLLATE_CFG(
            cfg<nonterminal, event> cfg, int bisim_depth) {

        Config.instance.measuring_thread.push("COLLATE_CFG");


        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering COLLATE_CFG: " + "cfg=" + cfg.log_info());

        cfg<nonterminal, event> result = cfg;

        while (true) {
            int size1 = result.get_cfg_nonterminals().size();
            int size2 = result.get_cfg_step_labels().size();

            result = remove_unsused_nonterminals(result);
            if (result.get_cfg_nonterminals().size() != size1)
                continue;
            if (result.get_cfg_step_labels().size() != size2)
                continue;
            size1 = result.get_cfg_nonterminals().size();
            size2 = result.get_cfg_step_labels().size();

            result = snap_back(result);
            if (result.get_cfg_nonterminals().size() != size1)
                continue;
            if (result.get_cfg_step_labels().size() != size2)
                continue;
            size1 = result.get_cfg_nonterminals().size();
            size2 = result.get_cfg_step_labels().size();

            result = no_duplicated_productions(result);
            if (result.get_cfg_nonterminals().size() != size1)
                continue;
            if (result.get_cfg_step_labels().size() != size2)
                continue;
            size1 = result.get_cfg_nonterminals().size();
            size2 = result.get_cfg_step_labels().size();

            result = merge_nonterminals_with_equal_right_hand_sides(result);
//            System.out.println("comparing for merge_nonterminals_with_equal_right_hand_sides");
//            if (!compare(result, cfg, 5, 30))
//                throw new RuntimeException();

            if (result.get_cfg_nonterminals().size() != size1)
                continue;
            if (result.get_cfg_step_labels().size() != size2)
                continue;
            size1 = result.get_cfg_nonterminals().size();
            size2 = result.get_cfg_step_labels().size();

            result = ((option__Some<cfg<nonterminal, event>>) T03_06_05_CFG_TRIM.F_CFG_TRIM(result)).the();
//            System.out.println("comparing for F_CFG_TRIM");
//            if (!compare(result, cfg, 5, 30))
//                throw new RuntimeException();

            if (result.get_cfg_nonterminals().size() != size1)
                continue;
            if (result.get_cfg_step_labels().size() != size2)
                continue;
            size1 = result.get_cfg_nonterminals().size();
            size2 = result.get_cfg_step_labels().size();

            if (Config.COLLATE_CFG__BISIM) {
                result = merge_nonterminals_with_bisimulation(result, bisim_depth);
                if (result.get_cfg_nonterminals().size() != size1)
                    continue;
                if (result.get_cfg_step_labels().size() != size2)
                    continue;
            }

//            System.out.println("comparing for F_CFG_TRIM");
//            if (!compare(result, cfg, 5, 30))
//                throw new RuntimeException();

//            result = remove_one_replacement(result);
//            if (result.get_cfg_nonterminals().size() != size1)
//                continue;
//            if (result.get_cfg_step_labels().size() != size2)
//                continue;
//            size1 = result.get_cfg_nonterminals().size();
//            size2 = result.get_cfg_step_labels().size();

            break;
        }


        //check_dup(result);

        Config.instance.measuring_thread.pop("COLLATE_CFG");

        return result;

    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> merge_nonterminals_with_bisimulation(cfg<nonterminal, event> input, int bisim_depth) {
        cfg<nonterminal, event> result = input;
        List<nonterminal> list = result.get_cfg_nonterminals();
        Set<General_Tuple2<nonterminal, nonterminal>> not_bisimilar = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            Map<General_Tuple2<nonterminal, nonterminal>, Set<Set<General_Tuple2<nonterminal, nonterminal>>>> cached_unifications = new HashMap<>();
            nonterminal nonterminalA = list.get(i);
            if (!result.get_cfg_nonterminals().contains(nonterminalA))
                continue;
            Set<nonterminal> unreachable = new HashSet<>(result.get_cfg_nonterminals());
//            unreachable.removeAll(
//                    T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(
//                            new cfg<>(result.get_cfg_nonterminals(), result.get_cfg_events(), list.get(i), result.get_cfg_step_labels())).get_cfg_nonterminals());
            int j = -1;
            for (nonterminal nonterminalB : unreachable) {
                j++;
                String id = "i=" + i + "," +
                        "j=" + j + "," +
                        "nonterminalA=" + nonterminalA + "," +
                        "nonterminalB=" + nonterminalB + "," +
                        "MAX1=" + list.size() + "," +
                        "MAX2=" + unreachable.size() + "," +
                        "remaining nonterminals=" + result.get_cfg_nonterminals().size();
                System.out.println("starting " + id);
                if (!result.get_cfg_nonterminals().contains(nonterminalA)
                        || !result.get_cfg_nonterminals().contains(nonterminalB)) {
                    // already merged with sth

                    System.out.println("already merged");
                    continue;
                }
                Set<General_Tuple2<nonterminal, nonterminal>> conjecture_relation = new HashSet<>();
                conjecture_relation.add(new General_Tuple2<>(nonterminalA, nonterminalB));
                Set<General_Tuple2<nonterminal, nonterminal>> bisimulation = extend_to_bisimulation(result, conjecture_relation, conjecture_relation, id, 0, not_bisimilar, bisim_depth, cached_unifications);
                if (bisimulation != null) {
                    System.out.println("bisimulation found!");

                    Map<nonterminal, nonterminal> replacement = new HashMap<>();
                    for (nonterminal elem : result.get_cfg_nonterminals())
                        replacement.put(elem, elem);
                    for (General_Tuple2<nonterminal, nonterminal> pair : bisimulation) {
                        replacement.put(pair.getElemB(), pair.getElemA());
                    }
                    List<cfg_step_label<nonterminal, event>> labels = new ArrayList<>();
                    for (cfg_step_label<nonterminal, event> production : result.get_cfg_step_labels()) {
                        List<two_elements__abstract<nonterminal, event>> rhs = new ArrayList<>();
                        for (two_elements__abstract<nonterminal, event> symbol : production.get_prod_rhs()) {
                            if (symbol instanceof two_elements__teA)
                                rhs.add(new two_elements__teA<>(replacement.get(((two_elements__teA<nonterminal, event>) symbol).get_value())));
                            else
                                rhs.add(symbol);
                        }
                        labels.add(new cfg_step_label<>(replacement.get(production.get_prod_lhs()), rhs));
                    }
                    result = new cfg<>(new ArrayList<>(new HashSet<>(replacement.values())), result.get_cfg_events(), replacement.get(result.get_cfg_initial()), labels);

                } else {
                    System.out.println("bisimulation not found!");
                }
                System.out.println("ending " + id);
            }
        }
        return result;
    }

    private static <nonterminal extends General, event extends General> Set<General_Tuple2<nonterminal, nonterminal>>
    extend_to_bisimulation(
            cfg<nonterminal, event> cfg,
            Set<General_Tuple2<nonterminal, nonterminal>> estimate,
            Set<General_Tuple2<nonterminal, nonterminal>> unclear,
            String id,
            int depth,
            Set<General_Tuple2<nonterminal, nonterminal>> not_bisimilar,
            int bisim_depth,
            Map<General_Tuple2<nonterminal, nonterminal>, Set<Set<General_Tuple2<nonterminal, nonterminal>>>> cached_unifications) {
        String refined_id = id + ", depth=" + depth + ",not_bisimilar=" + not_bisimilar.size();
        //System.out.println(refined_id + " extend_to_bisimulation: " + estimate + "; " + unclear);
        if (depth >= bisim_depth) {
            return null;
        }
        // for each pair in unclear we have to consider their right hand sides and make proposals for unification;
        // the considered pair is removed from unclear; one unification is added to established and unclear; and we restart
        if (unclear.isEmpty())
            return estimate;

        for (General_Tuple2<nonterminal, nonterminal> elem : unclear) {
            if (not_bisimilar.contains(elem) || not_bisimilar.contains(new General_Tuple2<>(elem.getElemB(), elem.getElemA())))
                return null;
            Set<Set<General_Tuple2<nonterminal, nonterminal>>>
                    unifications = null;
            if (cached_unifications.containsKey(elem))
                unifications = cached_unifications.get(elem);
            else {
                unifications = get_unifications(cfg, elem, refined_id);
                cached_unifications.put(elem, unifications);
            }

            if (unifications == null) {
                not_bisimilar.add(elem);
                return null;
            }
            for (Set<General_Tuple2<nonterminal, nonterminal>> unification : unifications) {
                Set<General_Tuple2<nonterminal, nonterminal>> estimate_this = new HashSet<>(estimate);
                Set<General_Tuple2<nonterminal, nonterminal>> unclear_this = new HashSet<>(unclear);
                unclear_this.remove(elem);
                unification.removeAll(estimate);
                estimate_this.addAll(unification);
                unclear_this.addAll(unification);
                Set<General_Tuple2<nonterminal, nonterminal>> result = extend_to_bisimulation(cfg, estimate_this, unclear_this, id, depth + 1, not_bisimilar, bisim_depth, cached_unifications);
                if (result != null)
                    return result;
            }
        }
        return null;

    }

    private static <nonterminal extends General, event extends General> Set<Set<General_Tuple2<nonterminal, nonterminal>>>
    get_unifications(cfg<nonterminal, event> cfg, General_Tuple2<nonterminal, nonterminal> elem, String id) {
        System.out.println(id + ": get_unifications: " + elem);
        Set<Set<General_Tuple2<nonterminal, nonterminal>>> result = new HashSet<>();
        result.add(new HashSet<>());

        General_Tuple3<Map<event, Set<nonterminal>>, Set<General_Tuple2<nonterminal, nonterminal>>, Boolean>
                signature1 = get_signature(cfg, elem.getElemA());
        General_Tuple3<Map<event, Set<nonterminal>>, Set<General_Tuple2<nonterminal, nonterminal>>, Boolean>
                signature2 = get_signature(cfg, elem.getElemB());

        if (!signature1.getElemA().keySet().equals(signature2.getElemA().keySet())) {
            System.out.println(id + ": no unification 1");
            return null;
        }
        if (signature1.getElemB().isEmpty() != signature2.getElemB().isEmpty()) {
            System.out.println(id + ": no unification 2");
            return null;
        }
        if (signature1.getElemC().booleanValue() != signature2.getElemC().booleanValue()) {
            System.out.println(id + ": no unification 3");
            return null;
        }

        //System.out.println(id + ": unification events");
        for (event event : signature1.getElemA().keySet()) {
            //System.out.println(id + ": unification event " + event + " (" + signature1.getElemA().keySet() + ")");
            Set<nonterminal> set1 = signature1.getElemA().get(event);
            Set<nonterminal> set2 = signature2.getElemA().get(event);
            Set<General_Tuple2<nonterminal, nonterminal>> cartesian_product = cartesian_product(set1, set2);
            Set<Set<General_Tuple2<nonterminal, nonterminal>>> powerset = powerset(cartesian_product);
            powerset.remove(new HashSet<>());

            Set<Set<General_Tuple2<nonterminal, nonterminal>>> extended_result = new HashSet<>();
            for (Set<General_Tuple2<nonterminal, nonterminal>> unification : result) {
                for (Set<General_Tuple2<nonterminal, nonterminal>> extension : powerset) {
                    Set<General_Tuple2<nonterminal, nonterminal>> extended_unification = new HashSet<>(unification);
                    for (General_Tuple2<nonterminal, nonterminal> pair : extension) {
                        if (!pair.getElemA().equals(pair.getElemB()))
                            extended_unification.add(pair);
                    }

                    extended_result.add(extended_unification);
                }
            }
            result = extended_result;
        }
        //System.out.println(id + ": unification events done");
        if (!signature1.getElemB().isEmpty()) {
            Set<General_Tuple2<General_Tuple2<nonterminal, nonterminal>, General_Tuple2<nonterminal, nonterminal>>> cartesian_product = cartesian_product(signature1.getElemB(), signature2.getElemB());
            Set<Set<General_Tuple2<General_Tuple2<nonterminal, nonterminal>, General_Tuple2<nonterminal, nonterminal>>>> powerset = powerset(cartesian_product);
            powerset.remove(new HashSet<>());

            Set<Set<General_Tuple2<nonterminal, nonterminal>>> extended_result = new HashSet<>();
            for (Set<General_Tuple2<nonterminal, nonterminal>> unification : result) {
                for (Set<General_Tuple2<General_Tuple2<nonterminal, nonterminal>, General_Tuple2<nonterminal, nonterminal>>> extension : powerset) {
                    Set<General_Tuple2<nonterminal, nonterminal>> extended_unification = new HashSet<>(unification);
                    for (General_Tuple2<General_Tuple2<nonterminal, nonterminal>, General_Tuple2<nonterminal, nonterminal>> double_assignment : extension) {
                        General_Tuple2<nonterminal, nonterminal> pair;
                        pair = new General_Tuple2<>(double_assignment.getElemA().getElemA(), double_assignment.getElemB().getElemA());
                        if (!pair.getElemA().equals(pair.getElemB()))
                            extended_unification.add(pair);
                        pair = new General_Tuple2<>(double_assignment.getElemA().getElemB(), double_assignment.getElemB().getElemB());
                        if (!pair.getElemA().equals(pair.getElemB()))
                            extended_unification.add(pair);
                    }
                    extended_result.add(extended_unification);
                }
            }
            result = extended_result;
        }
        //System.out.println(id + ": unification done " + result.size());
        return result;
    }

    private static <A> Set<Set<A>> powerset(
            Set<A> input) {
        Set<Set<A>> result = new HashSet<>();
        result.add(new HashSet<>());
        for (A elem : input) {
            List<Set<A>> new_elements = new ArrayList<>();
            for (Set<A> list : result) {
                Set<A> enlarged_list = new HashSet<>(list);
                enlarged_list.add(elem);
                new_elements.add(enlarged_list);
            }
            result.addAll(new_elements);
        }
        return result;
    }

    static <A, B> Set<General_Tuple2<A, B>> cartesian_product(Set<A> set1, Set<B> set2) {
        Set<General_Tuple2<A, B>> result = new HashSet<>();
        for (A elemA : set1)
            for (B elemB : set2)
                result.add(new General_Tuple2<>(elemA, elemB));
        return result;
    }

    private static <event extends General, nonterminal extends General> General_Tuple3<Map<event, Set<nonterminal>>, Set<General_Tuple2<nonterminal, nonterminal>>, Boolean>
    get_signature(cfg<nonterminal, event> cfg, nonterminal nonterminal) {
        Map<event, Set<nonterminal>> event_based = new HashMap<>();
        Set<General_Tuple2<nonterminal, nonterminal>> double_based = new HashSet<>();
        Boolean finish = false;
        for (cfg_step_label<nonterminal, event> prod : cfg.get_cfg_step_labels_by_lhs().get(nonterminal)) {
            if (prod.get_prod_rhs().size() == 0) {
                finish = true;
            } else if (prod.get_prod_rhs().size() == 1) {
                throw new RuntimeException("should be removed here already");
            } else if (prod.get_prod_rhs().size() == 2) {
                if (prod.get_prod_rhs().get(0) instanceof two_elements__teA) {
                    two_elements__teA<nonterminal, event> casted1 = (two_elements__teA<nonterminal, event>) prod.get_prod_rhs().get(0);
                    two_elements__teA<nonterminal, event> casted2 = (two_elements__teA<nonterminal, event>) prod.get_prod_rhs().get(1);
                    double_based.add(new General_Tuple2<>(casted1.get_value(), casted2.get_value()));
                } else if (prod.get_prod_rhs().get(0) instanceof two_elements__teB) {
                    two_elements__teB<nonterminal, event> casted1 = (two_elements__teB<nonterminal, event>) prod.get_prod_rhs().get(0);
                    two_elements__teA<nonterminal, event> casted2 = (two_elements__teA<nonterminal, event>) prod.get_prod_rhs().get(1);
                    event_based.putIfAbsent(casted1.get_value(), new HashSet<>());
                    event_based.get(casted1.get_value()).add(casted2.get_value());
                } else throw new RuntimeException();
            } else throw new RuntimeException();
        }
        return new General_Tuple3<>(event_based, double_based, finish);
    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> merge_nonterminals_with_equal_right_hand_sides
            (cfg<nonterminal, event> result) {
        Map<nonterminal, Set<List<two_elements__abstract<nonterminal, event>>>> right_hand_sides = new HashMap<>();
        for (cfg_step_label<nonterminal, event> elem : result.get_cfg_step_labels()) {
            right_hand_sides.putIfAbsent(elem.get_prod_lhs(), new HashSet<>());
            List<two_elements__abstract<nonterminal, event>> changed = new ArrayList<>();
            for (two_elements__abstract<nonterminal, event> symbol : elem.get_prod_rhs()) {
                if (symbol instanceof two_elements__teA) {
                    nonterminal casted = ((two_elements__teA<nonterminal, event>) symbol).get_value();
                    if (casted.equals(elem.get_prod_lhs()))
                        changed.add(null);
                    else
                        changed.add(symbol);
                } else
                    changed.add(symbol);
            }
            right_hand_sides.get(elem.get_prod_lhs()).add(changed);
        }
        Map<Set<List<two_elements__abstract<nonterminal, event>>>, Set<nonterminal>> equal_right_hand_sides = new HashMap<>();
        for (Map.Entry<nonterminal, Set<List<two_elements__abstract<nonterminal, event>>>> entry : right_hand_sides.entrySet()) {
            equal_right_hand_sides.putIfAbsent(entry.getValue(), new HashSet<>());
            equal_right_hand_sides.get(entry.getValue()).add(entry.getKey());
        }
        Map<nonterminal, nonterminal> replacement = new HashMap<>();
        for (nonterminal elem : result.get_cfg_nonterminals())
            replacement.put(elem, elem);
        for (Set<nonterminal> entry : equal_right_hand_sides.values()) {
            nonterminal representant = entry.iterator().next();
            for (nonterminal elem : entry)
                replacement.put(elem, representant);
        }
//        System.out.println(new HashSet<>(replacement.values()).size());

        List<cfg_step_label<nonterminal, event>> labels = new ArrayList<>();
        for (cfg_step_label<nonterminal, event> production : result.get_cfg_step_labels()) {
            List<two_elements__abstract<nonterminal, event>> rhs = new ArrayList<>();
            for (two_elements__abstract<nonterminal, event> symbol : production.get_prod_rhs()) {
                if (symbol instanceof two_elements__teA)
                    rhs.add(new two_elements__teA<>(replacement.get(((two_elements__teA<nonterminal, event>) symbol).get_value())));
                else
                    rhs.add(symbol);
            }
            labels.add(new cfg_step_label<>(replacement.get(production.get_prod_lhs()), rhs));
        }
        return new cfg<>(new ArrayList<>(new HashSet<>(replacement.values())), result.get_cfg_events(), replacement.get(result.get_cfg_initial()), labels);
    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> remove_unsused_nonterminals(cfg<nonterminal, event> result) {
        Set<nonterminal> used = new HashSet<>();
        for (cfg_step_label<nonterminal, event> elem : result.get_cfg_step_labels()) {
            used.add(elem.get_prod_lhs());
            for (two_elements__abstract<nonterminal, event> element : elem.get_prod_rhs()) {
                if (element instanceof two_elements__teA)
                    used.add(((two_elements__teA<nonterminal, event>) element).get_value());
            }
        }
        return new cfg<>(new ArrayList<>(used), result.get_cfg_events(), result.get_cfg_initial(), result.get_cfg_step_labels());
    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> snap_back(cfg<nonterminal, event> cfg) {
        Map<nonterminal, List<cfg_step_label<nonterminal, event>>> by_lhs = new HashMap<>(cfg.get_cfg_step_labels_by_lhs());
        for (nonterminal elem : cfg.get_cfg_nonterminals()) {
            for (cfg_step_label<nonterminal, event> production : new ArrayList<>(by_lhs.getOrDefault(elem, new ArrayList<>()))) {
                if (production.get_prod_rhs().size() == 1) {
                    if (production.get_prod_rhs().get(0) instanceof two_elements__teA) {
                        by_lhs.get(elem).remove(production);
                        two_elements__teA<nonterminal, event> casted = (two_elements__teA<nonterminal, event>) production.get_prod_rhs().get(0);
                        if (elem != casted.get_value())
                            for (cfg_step_label<nonterminal, event> production2 : by_lhs.getOrDefault(casted.get_value(), new ArrayList<>())) {
                                by_lhs.get(elem).add(new cfg_step_label<>(elem, production2.get_prod_rhs()));
                            }
                    }
                }
            }
        }
        List<cfg_step_label<nonterminal, event>> labels = new ArrayList<>();
        for (Map.Entry<nonterminal, List<cfg_step_label<nonterminal, event>>> elem : by_lhs.entrySet())
            labels.addAll(elem.getValue());
        return new cfg<>(cfg.get_cfg_nonterminals(), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(labels));
    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> no_duplicated_productions(cfg<nonterminal, event> cfg) {
        Set<cfg_step_label<nonterminal, event>> labels = new HashSet<>(cfg.get_cfg_step_labels());
        for (nonterminal elem : cfg.get_cfg_nonterminals()) {
            List<cfg_step_label<nonterminal, event>> list = new ArrayList<>(new HashSet<>(cfg.get_cfg_step_labels_by_lhs().get(elem)));
            if (list == null)
                continue;
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).get_prod_rhs().equals(list.get(j).get_prod_rhs())) {
                        System.out.println("removing: " + list.get(j));
                        System.out.println("retaining: " + list.get(i));
                        labels.remove(list.get(j));
                    }
                }
            }
        }
        return new cfg<>(cfg.get_cfg_nonterminals(), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(labels));
    }

    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> remove_loops(cfg<nonterminal, event> cfg) {
        Config.instance.measuring_thread.push("COLLATE_CFG_remove_loops");
        if (DEBUG) {
            XML_writer writer = new XML_writer("out/test/cosy/output_test", "example_from_JDES.xml", "../../main/resources/cosy.xsd");
            writer.add_to_be_exported(T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(cfg));
            writer.export();
        }


        Map<nonterminal, Set<cfg_step_label<nonterminal, event>>> occurence = new HashMap<>();
        {
            for (cfg_step_label<nonterminal, event> step_label : cfg.get_cfg_step_labels()) {
                for (nonterminal elem : two_elements__teA.filter(step_label.get_prod_rhs())) {
                    occurence.putIfAbsent(elem, new HashSet<>());
                    occurence.get(elem).add(step_label);
                }
            }
        }
        Map<nonterminal, List<cfg_step_label<nonterminal, event>>> get_by_lhs = cfg.get_cfg_step_labels_by_lhs();

        List<nonterminal> loop_free = new ArrayList<>();
        {
            Set<nonterminal> loop_free_set = new HashSet<>(cfg.get_cfg_nonterminals());
            Map<nonterminal, Set<nonterminal>> dependency = new HashMap<>();
            for (cfg_step_label<nonterminal, event> step_label : cfg.get_cfg_step_labels()) {
                dependency.putIfAbsent(step_label.get_prod_lhs(), new HashSet<>());
                dependency.get(step_label.get_prod_lhs()).addAll(two_elements__teA.filter(step_label.get_prod_rhs()));
            }
            for (Map.Entry<nonterminal, Set<nonterminal>> entry : dependency.entrySet())
                if (entry.getValue().contains(entry.getKey()))
                    loop_free_set.remove(entry.getKey());
            loop_free_set.remove(cfg.get_cfg_initial());
            loop_free.addAll(loop_free_set);
            loop_free.sort(Comparator.comparingInt(t -> get_by_lhs.getOrDefault(t, new ArrayList<>()).size()));
        }

        Set<cfg_step_label<nonterminal, event>> remaining_step_labels = new HashSet<>(cfg.get_cfg_step_labels());
        Set<nonterminal> remaining_nonterminals = new HashSet<>(cfg.get_cfg_nonterminals());


        while (!loop_free.isEmpty()) {
            if (DEBUG) {
                if (!new cfg<>(new ArrayList<>(remaining_nonterminals), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(remaining_step_labels)).is_valid())
                    throw new RuntimeException();

                for (cfg_step_label<nonterminal, event> x : remaining_step_labels)
                    System.out.println("remaining_step_labels: " + x);

                for (Map.Entry<nonterminal, Set<cfg_step_label<nonterminal, event>>> entry : occurence.entrySet())
                    for (cfg_step_label<nonterminal, event> step : entry.getValue())
                        System.out.println("occurence: " + entry.getKey() + " -> " + step);

                for (Map.Entry<nonterminal, List<cfg_step_label<nonterminal, event>>> entry : get_by_lhs.entrySet())
                    for (cfg_step_label<nonterminal, event> step : entry.getValue())
                        System.out.println("get_by_lhs: " + entry.getKey() + " -> " + step);


                for (Map.Entry<nonterminal, Set<cfg_step_label<nonterminal, event>>> entry : occurence.entrySet())
                    for (cfg_step_label<nonterminal, event> step : entry.getValue()) {
                        if (!step.get_prod_rhs().contains(new two_elements__teA<>(entry.getKey()))) {
                            System.out.println(entry.getKey());
                            System.out.println(step);
                            throw new RuntimeException();
                        }
                        if (!remaining_nonterminals.containsAll(two_elements__teA.filter(step.get_prod_rhs()))) {
                            System.out.println(entry.getKey());
                            System.out.println(step);
                            throw new RuntimeException();
                        }
                    }
                for (Map.Entry<nonterminal, List<cfg_step_label<nonterminal, event>>> entry : get_by_lhs.entrySet())
                    for (cfg_step_label<nonterminal, event> step : entry.getValue()) {
                        if (!step.get_prod_lhs().equals(entry.getKey()))
                            throw new RuntimeException();
                        if (!remaining_nonterminals.containsAll(two_elements__teA.filter(step.get_prod_rhs()))) {
                            System.out.println(entry.getKey());
                            System.out.println(step);
                            throw new RuntimeException();
                        }
                    }
            }

            nonterminal to_be_removed = loop_free.remove(0);
            if (DEBUG)
                System.out.println("removing: " + to_be_removed);
            List<cfg_step_label<nonterminal, event>> outgoing_step_labels = get_by_lhs.getOrDefault(to_be_removed, new ArrayList<>());
            if (Config.instance.T01_COLLATE_CFG__NO_INCREASE_STEP_LABELS && outgoing_step_labels.size() > 1)
                continue;
            remaining_nonterminals.remove(to_be_removed);

            get_by_lhs.remove(to_be_removed);
            if (DEBUG)
                if (outgoing_step_labels == null || outgoing_step_labels.size() == 0)
                    throw new RuntimeException("should not occur if nonblocking cfg");
            remaining_step_labels.removeAll(outgoing_step_labels);
            if (DEBUG)
                System.out.println("removing: " + outgoing_step_labels);

            List<cfg_step_label<nonterminal, event>> todo = new ArrayList<>(occurence.getOrDefault(to_be_removed, new HashSet<>()));
            occurence.remove(to_be_removed);
            remaining_step_labels.removeAll(todo);
            if (DEBUG)
                System.out.println("removing: " + todo);


            while (!todo.isEmpty()) {
                cfg_step_label<nonterminal, event> to_be_adapted = todo.remove(0);
                remaining_step_labels.remove(to_be_adapted);
                get_by_lhs.getOrDefault(to_be_adapted.get_prod_lhs(), new ArrayList<>()).remove(to_be_adapted);

                int index = to_be_adapted.get_prod_rhs().indexOf(new two_elements__teA<nonterminal, event>(to_be_removed));
                if (DEBUG)
                    if (index == -1)
                        throw new RuntimeException("should not occur!");

                List<cfg_step_label<nonterminal, event>> replacements = new ArrayList<>();
                for (cfg_step_label<nonterminal, event> removed_step_label : outgoing_step_labels) {
                    List<two_elements__abstract<nonterminal, event>> rhs = new ArrayList<>(to_be_adapted.get_prod_rhs());
                    rhs.remove(index);
                    rhs.addAll(index, removed_step_label.get_prod_rhs());
                    cfg_step_label<nonterminal, event> replacement = new cfg_step_label<>(to_be_adapted.get_prod_lhs(), rhs);
                    replacements.add(replacement);
                    if (rhs.contains(new two_elements__teA<nonterminal, event>(replacement.get_prod_lhs())))
                        loop_free.remove(replacement.get_prod_lhs());
                    if (rhs.contains(new two_elements__teA<nonterminal, event>(to_be_removed))) {
                        remaining_step_labels.add(replacement);
                        todo.add(replacement);
                    }
                    Set<nonterminal> other_nonterminals = new HashSet<>(two_elements__teA.filter(rhs));
                    other_nonterminals.remove(to_be_removed);
                    for (nonterminal reference : other_nonterminals) {
                        occurence.putIfAbsent(reference, new HashSet<>());
                        occurence.get(reference).remove(to_be_adapted);
                        occurence.get(reference).remove(removed_step_label);
                        occurence.get(reference).add(replacement);
                    }
                }
                for (cfg_step_label<nonterminal, event> replacement : replacements)
                    get_by_lhs.getOrDefault(replacement.get_prod_lhs(), new ArrayList<>()).add(replacement);
                remaining_step_labels.addAll(replacements);
                if (DEBUG)
                    System.out.println("adding: " + replacements);

            }
        }


        cfg<nonterminal, event> result = new cfg<>(new ArrayList<>(remaining_nonterminals), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(remaining_step_labels));


        if (DEBUG)
            if (!result.is_valid())
                throw new RuntimeException();

        if (Config.T01_COLLATE_CFG__OUTPUT)
            Config.instance.output(result);
        Config.instance.measuring_thread.pop("COLLATE_CFG_remove_loops");
        return result;
    }


    private static <nonterminal extends General, event extends General> cfg<nonterminal, event> remove_one_replacement(cfg<nonterminal, event> cfg) {

        // find all nonterminals with only one replacement!
        Set<nonterminal> one_replacement = new HashSet<>();

        for (nonterminal elem : cfg.get_cfg_nonterminals()) {
            if (cfg.get_cfg_step_labels_by_lhs().get(elem).size() == 1)
                one_replacement.add(elem);
        }
        if (one_replacement.isEmpty())
            return cfg;

        Set<cfg_step_label<nonterminal, event>> labels = new HashSet<>();
        for (cfg_step_label<nonterminal, event> elem : cfg.get_cfg_step_labels()) {
            List<two_elements__abstract<nonterminal, event>> list = new ArrayList<>();
            for (two_elements__abstract<nonterminal, event> rhsElem : elem.get_prod_rhs()) {
                if (rhsElem instanceof two_elements__teA) {
                    two_elements__teA<nonterminal, event> teA = (two_elements__teA<nonterminal, event>) rhsElem;
                    if (one_replacement.contains(teA.get_value()) && !teA.get_value().equals(elem.get_prod_lhs())) {
                        list.addAll(cfg.get_cfg_step_labels_by_lhs().get(teA.get_value()).iterator().next().get_prod_rhs());
                    } else
                        list.add(teA);
                } else
                    list.add(rhsElem);
            }
            labels.add(new cfg_step_label<>(elem.get_prod_lhs(), list));
        }
        return new cfg<>(cfg.get_cfg_nonterminals(), cfg.get_cfg_events(), cfg.get_cfg_initial(), new ArrayList<>(labels));
    }


}

