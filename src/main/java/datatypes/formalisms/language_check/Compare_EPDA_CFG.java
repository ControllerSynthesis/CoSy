package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.*;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Compare_EPDA_CFG {

    public static <state extends General,
            stack extends General,
            nonterminal extends General,
            event extends General>
    General_Tuple2<List<List<event>>, List<List<event>>> compare_inclusion(
            epda<state, event, stack> epda,
            cfg<nonterminal, event> cfg,
            boolean check_only_marked,
            int random__max_depth,
            int random__max_num,
            int random__timeout,
            int breadth_first_search__max_depth,
            int breadth_first_search__max_num,
            int breadth_first_search__timeout,
            int combined__max_num,
            int combined__timeout,
            boolean random_before_breadth_first_search,
            long single_word_timeout,
            long all_words_timeout) {
        return new Comparison_inclusion<
                epda<state, event, stack>,
                epdaH_semantics<state, event, stack>,
                epdaH_configuration<state, event, stack>,
                epda_step_label<state, event, stack>,
                cfg<nonterminal, event>,
                cfgLM_semantics<nonterminal, event>,
                cfg_configuration<nonterminal, event>,
                cfg_step_label<nonterminal, event>,
                event>()
                .compare_inclusion(
                        epda,
                        new epdaH_semantics<>(),
                        cfg,
                        new cfgLM_semantics<>(),
                        check_only_marked,
                        random__max_depth,
                        random__max_num,
                        random__timeout,
                        breadth_first_search__max_depth,
                        breadth_first_search__max_num,
                        breadth_first_search__timeout,
                        combined__max_num,
                        combined__timeout,
                        random_before_breadth_first_search,
                        single_word_timeout,
                        all_words_timeout);
    }

    public static <state extends General,
            stack extends General,
            nonterminal extends General,
            stack2 extends General,
            event extends General>
    General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> compare_equal(
            epda<state, event, stack> epda,
            cfg<nonterminal, event> cfg,
            boolean check_only_marked,
            int random__max_depth,
            int random__max_num,
            int random__timeout,
            int breadth_first_search__max_depth,
            int breadth_first_search__max_num,
            int breadth_first_search__timeout,
            int combined__max_num,
            int combined__timeout,
            boolean random_before_breadth_first_search,
            long single_word_timeout,
            long all_words_timeout) {
        System.out.println("checking");
        return new Comparison_equal<
                epda<state, event, stack>,
                epdaH_semantics<state, event, stack>,
                epdaH_configuration<state, event, stack>,
                epda_step_label<state, event, stack>,
                cfg<nonterminal, event>,
                cfgLM_semantics<nonterminal, event>,
                cfg_configuration<nonterminal, event>,
                cfg_step_label<nonterminal, event>,
                event>()
                .compare_equal(
                        epda,
                        new epdaH_semantics<>(),
                        cfg,
                        new cfgLM_semantics<>(),
                        check_only_marked,
                        random__max_depth,
                        random__max_num,
                        random__timeout,
                        breadth_first_search__max_depth,
                        breadth_first_search__max_num,
                        breadth_first_search__timeout,
                        combined__max_num,
                        combined__timeout,
                        random_before_breadth_first_search,
                        single_word_timeout,
                        all_words_timeout);
    }
}
