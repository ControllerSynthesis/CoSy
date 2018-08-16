package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Map;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * not implemented with F_CFG_FIRST__enforce_blockfree
 * <p>
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T04_CFG_FIRST<nonterminal extends General, event extends General> {
    private static Logger log = LogManager.getLogger(T04_CFG_FIRST.class.getName());
    private static T04_CFG_FIRST instance = null;
    private cfg<nonterminal, event> cfg = null;
    private int k = -1;
    private Set<List<two_elements__abstract<nonterminal, event>>> table_domain = null;
    private Map<List<two_elements__abstract<nonterminal, event>>, Set<option__abstract<event>>> table = null;

    public static T04_CFG_FIRST get_instance() {
        if (instance == null)
            instance = new T04_CFG_FIRST();
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
            get_instance().cfg = cfg;
            get_instance().k = k;
            get_instance().table_domain = null;
            get_instance().table = null;
            get_instance().F_CFG_FIRST__table__fp();
            log.info("reseted instance");
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
            if (!F_CFG_FIRST__fp(w).isEmpty()) {
                result.add(new ArrayList<>());
                return result;
            } else
                return result;
        } else if (k == 1) {
            for (option__abstract<event> elem : F_CFG_FIRST__fp(w))
                if (elem instanceof option__Some)
                    result.add(General_List.list_by_enumeration(((option__Some<event>) elem).the()));
            return result;
        } else
            return result;
    }

    private Set<option__abstract<event>>
    F_CFG_FIRST__fp_one(
            List<two_elements__abstract<nonterminal, event>> w) {
//        for (Map.Entry<List<two_elements__abstract<nonterminal, event>>, Set<option__abstract<event>>> elem : F_CFG_FIRST__table__fp().entrySet()) {
//            System.out.println(elem.getKey() + " -> " + elem.getValue());
//        }
        return F_CFG_FIRST__table__fp().get(w);
    }

    private Set<option__abstract<event>>
    F_CFG_FIRST__fp(
            List<two_elements__abstract<nonterminal, event>> w) {
        if (w.isEmpty()) {
            Set<option__abstract<event>> result = new HashSet<>();
            result.add(new option__None<event>());
            return result;
        } else {
            if (w.get(0) instanceof two_elements__teA) {
                Set<option__abstract<event>> result = F_CFG_FIRST__fp_one(General_List.list_by_enumeration(w.get(0)));
                if (result == null)
                    System.out.println("XX");
                if (result.contains(new option__None<event>())) {
                    result.remove(new option__None<event>());
                    result.addAll(F_CFG_FIRST__fp(General_List.drop(w, 1)));
                    return result;
                } else {
                    result.remove(new option__None<event>());
                    return result;
                }
            } else {
                Set<option__abstract<event>> result = new HashSet<>();
                result.add(new option__Some<>(((two_elements__teB<nonterminal, event>) w.get(0)).get_value()));
                return result;
            }
        }
    }

    private Set<List<two_elements__abstract<nonterminal, event>>>
    F_CFG_FIRST__table_domain() {
        if (table_domain == null)
            inner__F_CFG_FIRST__table_domain();
        return table_domain;
    }

    private void
    inner__F_CFG_FIRST__table_domain() {
        Config.instance.measuring_thread.push("inner__F_CFG_FIRST__table_domain");
        table_domain = new HashSet<>();
        for (cfg_step_label<nonterminal, event> e : cfg.get_cfg_step_labels())
            table_domain.add(General_List.list_by_enumeration(new two_elements__teA<>(e.get_prod_lhs())));
        for (cfg_step_label<nonterminal, event> e : cfg.get_cfg_step_labels())
            for (int i = 0; i <= e.get_prod_rhs().size(); i++)
                table_domain.add(General_List.drop(e.get_prod_rhs(), i));
        Config.instance.measuring_thread.pop("inner__F_CFG_FIRST__table_domain");
    }

    private Map<List<two_elements__abstract<nonterminal, event>>, Set<option__abstract<event>>>
    F_CFG_FIRST__table__fp() {
        if (table == null)
            inner__F_CFG_FIRST__table__fp();
        return table;
    }

    private void
    inner__F_CFG_FIRST__table__fp() {
        Config.instance.measuring_thread.push("inner__F_CFG_FIRST__table__fp");
        table = new HashMap<>();
        while (F_CFG_FIRST__table__fp_one())
            ;
        Config.instance.measuring_thread.pop("inner__F_CFG_FIRST__table__fp");
    }

    private boolean
    F_CFG_FIRST__table__fp_one() {
        boolean changed = false;
        for (List<two_elements__abstract<nonterminal, event>> w : F_CFG_FIRST__table_domain())
            table.putIfAbsent(w, new HashSet<>());
        if (log.isInfoEnabled())
            log.info("F_CFG_FIRST__table__fp_one: table=" + table.size());
//        System.out.println("==============");
//        System.out.println("== pre");
//        for (List<two_elements__abstract<nonterminal, event>> x : table.keySet())
//            System.out.println(enlarge(x.toString(), 20) + " -> " + table.get(x));
//        System.out.println("==============");
        //Map<List<two_elements__abstract<nonterminal, event>>, Set<option__abstract<event>>> new_table = new HashMap<>();
        for (List<two_elements__abstract<nonterminal, event>> w : F_CFG_FIRST__table_domain()) {
            table.put(w, new HashSet<>(table.get(w)));
            if (w.isEmpty()) {
                changed = table.get(w).add(new option__None<>()) || changed;
            } else if (w.get(0) instanceof two_elements__teA) {
                nonterminal A = ((two_elements__teA<nonterminal, event>) w.get(0)).get_value();
                Set<option__abstract<event>> additional = new HashSet<>();
                Set<option__abstract<event>> first = General_Map.get_default(table, General_List.list_by_enumeration(w.get(0)), new HashSet<>());

                additional.addAll(first);
                if (w.size() > 1)
                    additional.remove(new option__None<event>());
                if (first.contains(new option__None<event>()))
                    additional.addAll(table.get(General_List.drop(w, 1)));
                Set<option__abstract<event>> additional2 = new HashSet<>();
                for (cfg_step_label<nonterminal, event> e : cfg.get_cfg_step_labels())
                    if (e.get_prod_lhs().equals(A))
                        additional2.addAll(General_Map.get_default(table, e.get_prod_rhs(), new HashSet<>()));
                if (w.size() > 1)
                    additional2.remove(new option__None<event>());
                additional.addAll(additional2);
                changed = table.get(w).addAll(additional) || changed;
            } else {
                changed = table.get(w).add(new option__Some<>(((two_elements__teB<nonterminal, event>) w.get(0)).get_value())) || changed;
            }
        }
        //table = new_table;

//        System.out.println("==============");
//        System.out.println("== post");
//        for (List<two_elements__abstract<nonterminal, event>> x : table.keySet())
//          System.out.println(enlarge(x.toString(), 20) + " -> " + table.get(x));
//        System.out.println("==============");

        //StringBuffer stringBuffer = new StringBuffer();
        //Append_helper.append_map_general(table, stringBuffer);
        //System.out.println(stringBuffer.toString());
        return changed;
    }

    private String enlarge(String s, int i) {
        while (s.length() < i)
            s += " ";
        return s;
    }
}