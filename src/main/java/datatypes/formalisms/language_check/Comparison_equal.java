package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;

import java.util.List;

/**
 * generator1_semantics should be a branching semantic
 * <p>
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Comparison_equal<
        generator1 extends General,
        generator1_semantics extends Semantic<generator1, configuration1, step_label1, event>,
        configuration1 extends General
                & Get_History<event>,
        step_label1 extends General,
        generator2 extends General,
        generator2_semantics extends Semantic<generator2, configuration2, step_label2, event>,
        configuration2 extends General
                & Get_History<event>,
        step_label2 extends General,
        event extends General> {

    public static <event extends General>
    boolean compare_equal_with_empty_check(General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>> result) {
        return result.getElemA().getElemB().isEmpty() && result.getElemB().getElemB().isEmpty();
    }


    public General_Tuple2<General_Tuple2<List<List<event>>, List<List<event>>>, General_Tuple2<List<List<event>>, List<List<event>>>>
    compare_equal(
            generator1 generator1,
            generator1_semantics generator1_semantics,
            generator2 generator2,
            generator2_semantics generator2_semantics,
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
        return new General_Tuple2<>(new Comparison_inclusion<generator1, generator1_semantics, configuration1, step_label1, generator2, generator2_semantics, configuration2, step_label2, event>().compare_inclusion(
                generator1,
                generator1_semantics,
                generator2,
                generator2_semantics,
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
                all_words_timeout),
                new Comparison_inclusion<generator2, generator2_semantics, configuration2, step_label2, generator1, generator1_semantics, configuration1, step_label1, event>().compare_inclusion(
                        generator2,
                        generator2_semantics,
                        generator1,
                        generator1_semantics,
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
                        all_words_timeout));


    }

}
