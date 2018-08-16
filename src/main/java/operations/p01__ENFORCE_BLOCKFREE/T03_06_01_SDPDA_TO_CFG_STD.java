package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_01_SDPDA_TO_CFG_STD {

    private static Logger log = LogManager.getLogger(T03_06_01_SDPDA_TO_CFG_STD.class.getName());

    private T03_06_01_SDPDA_TO_CFG_STD() {
        throw new IllegalStateException("Utility Class");
    }

    static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l3_read(
            epda_step_label<state, event, stack> e,
            List<state> Q) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (state qt : Q)
            result.add(new cfg_step_label<>(
                    new l2_l3_nonterminals__cons_l3<>(e.get_edge_src(), e.get_edge_pop().get(0), qt),
                    General_List.list_by_enumeration(
                            new two_elements__teB<>(((option__Some<event>) e.get_edge_event()).the()),
                            new two_elements__teA<>(new l2_l3_nonterminals__cons_l3<>(e.get_edge_trg(), e.get_edge_pop().get(0), qt))
                    )));
        return result;
    }

    static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l3_pop(
            epda_step_label<state, event, stack> e) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        result.add(new cfg_step_label<>(
                new l2_l3_nonterminals__cons_l3<>(e.get_edge_src(), e.get_edge_pop().get(0), e.get_edge_trg()),
                new ArrayList<>()));
        return result;
    }

    static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l3_push(
            epda_step_label<state, event, stack> e,
            List<state> Q) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (state qt : Q)
            for (state qs : Q)
                result.add(new cfg_step_label<>(
                        new l2_l3_nonterminals__cons_l3<>(e.get_edge_src(), e.get_edge_pop().get(0), qt),
                        General_List.list_by_enumeration(
                                new two_elements__teA<>(new l2_l3_nonterminals__cons_l3<>(e.get_edge_trg(), e.get_edge_push().get(0), qs)),
                                new two_elements__teA<>(new l2_l3_nonterminals__cons_l3<>(qs, e.get_edge_pop().get(0), qt))
                        )));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l3(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reversal) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!(e.get_edge_event() instanceof option__None)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> current = F_SDPDA_TO_CFG_STD__edges_l3_read(e, epda.get_epda_states());
                result.addAll(current);
                if (reversal != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> current_step_label : current)
                        reversal.put(current_step_label, e);
            }
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (e.get_edge_push().isEmpty()) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> current = F_SDPDA_TO_CFG_STD__edges_l3_pop(e);
                result.addAll(current);
                if (reversal != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> current_step_label : current)
                        reversal.put(current_step_label, e);
            }
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!e.get_edge_push().isEmpty() && !(e.get_edge_event() instanceof option__Some)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> current = F_SDPDA_TO_CFG_STD__edges_l3_push(e, epda.get_epda_states());
                result.addAll(current);
                if (reversal != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> current_step_label : current)
                        reversal.put(current_step_label, e);

            }
        return result;
    }

    static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l2_read(
            epda_step_label<state, event, stack> e) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        result.add(new cfg_step_label<>(
                new l2_l3_nonterminals__cons_l2<>(e.get_edge_src(), e.get_edge_pop().get(0)),
                General_List.list_by_enumeration(
                        new two_elements__teB<>(((option__Some<event>) e.get_edge_event()).the()),
                        new two_elements__teA<>(new l2_l3_nonterminals__cons_l2<>(e.get_edge_trg(), e.get_edge_pop().get(0)))
                )));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l2_final(
            List<state> F,
            List<stack> S) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (state i : F)
            for (stack A : S)
                result.add(new cfg_step_label<>(
                        new l2_l3_nonterminals__cons_l2<>(i, A),
                        new ArrayList<>()
                ));
        return result;
    }

    static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l2_push(
            epda_step_label<state, event, stack> e,
            List<state> Q) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        result.add(new cfg_step_label<>(
                new l2_l3_nonterminals__cons_l2<>(e.get_edge_src(), e.get_edge_pop().get(0)),
                General_List.list_by_enumeration(
                        new two_elements__teA<>(new l2_l3_nonterminals__cons_l2<>(e.get_edge_trg(), e.get_edge_push().get(0)))
                )));
        for (state qs : Q)
            result.add(new cfg_step_label<>(
                    new l2_l3_nonterminals__cons_l2<>(e.get_edge_src(), e.get_edge_pop().get(0)),
                    General_List.list_by_enumeration(
                            new two_elements__teA<>(new l2_l3_nonterminals__cons_l3<>(e.get_edge_trg(), e.get_edge_push().get(0), qs)),
                            new two_elements__teA<>(new l2_l3_nonterminals__cons_l2<>(qs, e.get_edge_pop().get(0)))
                    )));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_STD__edges_l2(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reversal) {
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result =
                new ArrayList<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!(e.get_edge_event() instanceof option__None)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> current = F_SDPDA_TO_CFG_STD__edges_l2_read(e);
                result.addAll(current);
                if (reversal != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> current_step_label : current)
                        reversal.put(current_step_label, e);
            }
        result.addAll(F_SDPDA_TO_CFG_STD__edges_l2_final(epda.get_epda_marking(), epda.get_epda_gamma()));
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (!e.get_edge_push().isEmpty() && !(e.get_edge_event() instanceof option__Some)) {
                List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> current = F_SDPDA_TO_CFG_STD__edges_l2_push(e, epda.get_epda_states());
                result.addAll(current);
                if (reversal != null)
                    for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> current_step_label : current)
                        reversal.put(current_step_label, e);
            }
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    cfg<l2_l3_nonterminals__abstract<state, stack>, event>
    inner__F_SDPDA_TO_CFG_STD(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reversal) {
        List<l2_l3_nonterminals__abstract<state, stack>> states =
                new ArrayList<>();
        for (state q : epda.get_epda_states()) {
            for (stack A : epda.get_epda_gamma())
                states.add(new l2_l3_nonterminals__cons_l2<>(q, A));
        }
        for (state q1 : epda.get_epda_states()) {
            for (state q2 : epda.get_epda_states())
                for (stack A : epda.get_epda_gamma())
                    states.add(new l2_l3_nonterminals__cons_l3<>(q1, A, q2));
        }
        List<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> productions =
                new ArrayList<>();
        productions.addAll(F_SDPDA_TO_CFG_STD__edges_l2(epda, reversal));
        productions.addAll(F_SDPDA_TO_CFG_STD__edges_l3(epda, reversal));
        return new cfg<>(
                states,
                epda.get_epda_events(),
                new l2_l3_nonterminals__cons_l2<>(epda.get_epda_initial(), epda.get_epda_box()),
                productions);
    }


    public static <state extends General, event extends General, stack extends General>
    cfg<l2_l3_nonterminals__abstract<state, stack>, event>
    F_SDPDA_TO_CFG_STD(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reversal) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_STD");
        cfg<l2_l3_nonterminals__abstract<state, stack>, event> result =
                inner__F_SDPDA_TO_CFG_STD(epda, reversal);
        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_STD");
        return result;
    }


}