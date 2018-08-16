package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.*;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_05_LR_PARSER {
    private static Logger log = LogManager.getLogger(T05_05_LR_PARSER.class.getName());

    private T05_05_LR_PARSER() {
        throw new IllegalStateException("Utility Class");
    }

    private static <nonterminal extends General, event extends General>
    Set<parser_step_label<General_Set<cfg_item<nonterminal, event>>, event>>
    F_LR_PARSER__rules(
            cfg<nonterminal, event> cfg,
            cfg<nonterminal, event> cfg_annotated,
            epda<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> machine) {
        Set<parser_step_label<General_Set<cfg_item<nonterminal, event>>, event>> result = new HashSet<>();
        for (General_Set<cfg_item<nonterminal, event>> q : machine.get_epda_states())
            for (cfg_item<nonterminal, event> I : q.get_value())
                if (I.get_cfg_item_rhs1().isEmpty() && cfg.get_cfg_nonterminals().contains(I.get_cfg_item_lhs())) {
                    General_Set<cfg_item<nonterminal, event>> qA = T05_01_EPDA_GOTO.F_DFA_GOTO(machine, q, new two_elements__teA<>(I.get_cfg_item_lhs()));
                    List<General_Set<cfg_item<nonterminal, event>>> qseq = T05_01_EPDA_GOTO.F_DFA_GOTO_SEQ(machine, q, I.get_cfg_item_rhs2());
                    List<event> y = I.get_cfg_item_look_ahead();
                    result.add(new parser_step_label<>(General_List.cons(q, qseq), y, General_List.list_by_enumeration(q, qA), y));
                }
        for (General_Set<cfg_item<nonterminal, event>> q : machine.get_epda_states())
            for (cfg_item<nonterminal, event> I : q.get_value())
                if (!I.get_cfg_item_rhs2().isEmpty() && I.get_cfg_item_rhs2().get(0) instanceof two_elements__teB && cfg.get_cfg_nonterminals().contains(I.get_cfg_item_lhs())) {
                    event a = ((two_elements__teB<nonterminal, event>) I.get_cfg_item_rhs2().get(0)).get_value();
                    General_Set<cfg_item<nonterminal, event>> qA = T05_01_EPDA_GOTO.F_DFA_GOTO(machine, q, I.get_cfg_item_rhs2().get(0));
                    result.add(new parser_step_label<>(General_List.list_by_enumeration(q), General_List.list_by_enumeration(a), General_List.list_by_enumeration(q, qA), General_List.list_by_enumeration()));
                }
        return result;
    }

    public static <nonterminal extends General, event extends General>
    parser<General_Set<cfg_item<nonterminal, event>>, event, option__abstract<option__abstract<cfg_step_label<nonterminal, event>>>>
    F_LR_PARSER(
            cfg<nonterminal, event> cfg,
            cfg<nonterminal, event> cfg_annotated,
            epda<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer> machine,
            event eF) {
        Config.instance.measuring_thread.push("F_LR_PARSER");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_LR_PARSER: " + "cfg=" + cfg.log_info() + ", cfg_annotated=" + cfg_annotated.log_info() + ", machine=" + machine.log_info());

        //
        //General_Set<cfg_item<nonterminal, event>> state_empty =
        //      T05_01_EPDA_GOTO.F_DFA_GOTO(machine, machine.get_epda_initial(),
        //             new two_elements__teA<>(cfg.get_cfg_initial()));
        General_Set<cfg_item<nonterminal, event>> state_eF =
                General_List.last(T05_01_EPDA_GOTO.F_DFA_GOTO_SEQ(machine, machine.get_epda_initial(),
                        General_List.list_by_enumeration(new two_elements__teB<>(eF))));
        General_Set<cfg_item<nonterminal, event>> state_eFS =
                General_List.last(T05_01_EPDA_GOTO.F_DFA_GOTO_SEQ(machine, machine.get_epda_initial(),
                        General_List.list_by_enumeration(new two_elements__teB<>(eF), new two_elements__teA<>(cfg.get_cfg_initial()))));
        General_Set<cfg_item<nonterminal, event>> state_eFSeF =
                General_List.last(T05_01_EPDA_GOTO.F_DFA_GOTO_SEQ(machine, machine.get_epda_initial(),
                        General_List.list_by_enumeration(new two_elements__teB<>(eF), new two_elements__teA<>(cfg.get_cfg_initial()), new two_elements__teB<>(eF))));
        Set<General_Set<cfg_item<nonterminal, event>>> states =
                new HashSet<>(machine.get_epda_states());
        states.remove(machine.get_epda_initial());
        //states.remove(state_empty);
        states.remove(state_eFSeF);
        parser<General_Set<cfg_item<nonterminal, event>>, event, option__abstract<option__abstract<cfg_step_label<nonterminal, event>>>> result = new parser<>(
                new ArrayList<>(states),
                cfg_annotated.get_cfg_events(),
                state_eF,
                Collections.singletonList(state_eFS),
                new ArrayList<>(F_LR_PARSER__rules(cfg, cfg_annotated, machine)),
                new HashMap<>(),
                eF);
        Config.instance.measuring_thread.pop("F_LR_PARSER");
        return result;
    }

}
