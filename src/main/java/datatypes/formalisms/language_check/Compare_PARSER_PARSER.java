package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.parser;
import datatypes.formalisms.parserHF_configuration;
import datatypes.formalisms.parserHF_semantics;
import datatypes.formalisms.parser_step_label;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Compare_PARSER_PARSER {

    public static <stack1 extends General,
            stack2 extends General,
            marker1 extends General,
            marker2 extends General,
            event extends General>
    General_Tuple2<List<List<event>>, List<List<event>>> compare_inclusion(
            parser<stack1, event, marker1> parser1,
            parser<stack2, event, marker2> parser2,
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
                parser<stack2, event, marker2>,
                parserHF_semantics<stack2, event, marker2>,
                parserHF_configuration<stack2, event>,
                parser_step_label<stack2, event>,
                event>()
                .compare_inclusion(
                        parser1,
                        new parserHF_semantics<>(),
                        parser2,
                        new parserHF_semantics<>(),
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
            marker1 extends General,
            marker2 extends General,
            event extends General>
    General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> compare_equal(
            parser<stack1, event, marker1> parser1,
            parser<stack2, event, marker2> parser2,
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
                parser<stack2, event, marker2>,
                parserHF_semantics<stack2, event, marker2>,
                parserHF_configuration<stack2, event>,
                parser_step_label<stack2, event>,
                event>()
                .compare_equal(
                        parser1,
                        new parserHF_semantics<>(),
                        parser2,
                        new parserHF_semantics<>(),
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
