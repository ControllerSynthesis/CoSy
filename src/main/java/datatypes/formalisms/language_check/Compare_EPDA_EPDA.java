package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.epda;
import datatypes.formalisms.epdaH_configuration;
import datatypes.formalisms.epdaH_semantics;
import datatypes.formalisms.epda_step_label;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Compare_EPDA_EPDA {

    public static <state1 extends General,
            stack1 extends General,
            state2 extends General,
            stack2 extends General,
            event extends General>
    General_Tuple2<List<List<event>>, List<List<event>>> compare_inclusion(
            epda<state1, event, stack1> epda1,
            epda<state2, event, stack2> epda2,
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
                epda<state1, event, stack1>,
                epdaH_semantics<state1, event, stack1>,
                epdaH_configuration<state1, event, stack1>,
                epda_step_label<state1, event, stack1>,
                epda<state2, event, stack2>,
                epdaH_semantics<state2, event, stack2>,
                epdaH_configuration<state2, event, stack2>,
                epda_step_label<state2, event, stack2>,
                event>()
                .compare_inclusion(
                        epda1,
                        new epdaH_semantics<>(),
                        epda2,
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

    public static <state1 extends General,
            stack1 extends General,
            state2 extends General,
            stack2 extends General,
            event extends General>
    General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> compare_equal(
            epda<state1, event, stack1> epda1,
            epda<state2, event, stack2> epda2,
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
                epda<state1, event, stack1>,
                epdaH_semantics<state1, event, stack1>,
                epdaH_configuration<state1, event, stack1>,
                epda_step_label<state1, event, stack1>,
                epda<state2, event, stack2>,
                epdaH_semantics<state2, event, stack2>,
                epdaH_configuration<state2, event, stack2>,
                epda_step_label<state2, event, stack2>,
                event>()
                .compare_equal(
                        epda1,
                        new epdaH_semantics<>(),
                        epda2,
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
