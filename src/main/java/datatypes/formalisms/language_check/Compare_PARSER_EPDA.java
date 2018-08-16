package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.*;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Compare_PARSER_EPDA {

    public static <stack1 extends General,
            stack2 extends General,
            state2 extends General,
            marker1 extends General,
            event extends General>
    General_Tuple2<List<List<event>>, List<List<event>>> compare_inclusion(
            parser<stack1, event, marker1> parser,
            epda<state2, event, stack2> epda,
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
                parser<stack1, event, marker1>,
                parserHF_semantics<stack1, event, marker1>,
                parserHF_configuration<stack1, event>,
                parser_step_label<stack1, event>,
                epda<state2, event, stack2>,
                epdaH_semantics<state2, event, stack2>,
                epdaH_configuration<state2, event, stack2>,
                epda_step_label<state2, event, stack2>,
                event>()
                .compare_inclusion(
                        parser,
                        new parserHF_semantics<>(),
                        epda,
                        new epdaH_semantics<>(),
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

    public static <stack1 extends General,
            stack2 extends General,
            state2 extends General,
            marker1 extends General,
            event extends General>
    General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> compare_equal(
            parser<stack1, event, marker1> parser,
            epda<state2, event, stack2> epda,
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
                parser<stack1, event, marker1>,
                parserHF_semantics<stack1, event, marker1>,
                parserHF_configuration<stack1, event>,
                parser_step_label<stack1, event>,
                epda<state2, event, stack2>,
                epdaH_semantics<state2, event, stack2>,
                epdaH_configuration<state2, event, stack2>,
                epda_step_label<state2, event, stack2>,
                event>()
                .compare_equal(
                        parser,
                        new parserHF_semantics<>(),
                        epda,
                        new epdaH_semantics<>(),
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
