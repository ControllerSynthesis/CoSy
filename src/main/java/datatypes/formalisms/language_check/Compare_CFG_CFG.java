package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfgLM_semantics;
import datatypes.formalisms.cfg_configuration;
import datatypes.formalisms.cfg_step_label;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Compare_CFG_CFG {

    public static <nonterminal1 extends General,
            nonterminal2 extends General,
            event extends General>
    General_Tuple2<List<List<event>>, List<List<event>>> compare_inclusion(
            cfg<nonterminal1, event> cfg1,
            cfg<nonterminal2, event> cfg2,
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
                cfg<nonterminal1, event>,
                cfgLM_semantics<nonterminal1, event>,
                cfg_configuration<nonterminal1, event>,
                cfg_step_label<nonterminal1, event>,
                cfg<nonterminal2, event>,
                cfgLM_semantics<nonterminal2, event>,
                cfg_configuration<nonterminal2, event>,
                cfg_step_label<nonterminal2, event>,
                event>()
                .compare_inclusion(
                        cfg1,
                        new cfgLM_semantics<>(),
                        cfg2,
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

    public static <nonterminal1 extends General,
            nonterminal2 extends General,
            event extends General>
    General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> compare_equal(
            cfg<nonterminal1, event> cfg1,
            cfg<nonterminal2, event> cfg2,
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
                cfg<nonterminal1, event>,
                cfgLM_semantics<nonterminal1, event>,
                cfg_configuration<nonterminal1, event>,
                cfg_step_label<nonterminal1, event>,
                cfg<nonterminal2, event>,
                cfgLM_semantics<nonterminal2, event>,
                cfg_configuration<nonterminal2, event>,
                cfg_step_label<nonterminal2, event>,
                event>()
                .compare_equal(
                        cfg1,
                        new cfgLM_semantics<>(),
                        cfg2,
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
