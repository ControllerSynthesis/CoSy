package datatypes.formalisms.language_check;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Tuple2;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * generator1_semantics should be a branching semantic
 * <p>
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Comparison_inclusion<
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


    public General_Tuple2<List<List<event>>, List<List<event>>>
    compare_inclusion(
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
        General_Tuple2<List<List<event>>, List<List<event>>>
                result = new General_Tuple2<>(new ArrayList<>(), new ArrayList<>());

        Set<List<event>> found_words = compare_find_words(
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
                random_before_breadth_first_search);

        System.out.println("words computed: " + found_words.size());

        long all_words_deadline = Math.max(System.currentTimeMillis() + all_words_timeout, all_words_timeout);
        for (List<event> found_word : found_words) {
            if (compare_check_word(generator2, generator2_semantics, check_only_marked, found_word, single_word_timeout, all_words_deadline))
                result.getElemA().add(found_word);
            else
                result.getElemB().add(found_word);
        }

        return result;
    }

    public Set<List<event>>
    compare_find_words(
            generator1 generator1,
            generator1_semantics generator1_semantics,
            boolean check_only_marked,
            int random__max_depth,
            int random__max_num,
            long random__timeout,
            int breadth_first_search__max_depth,
            int breadth_first_search__max_num,
            long breadth_first_search__timeout,
            int combined__max_num,
            int combined__timeout,
            boolean random_before_breadth_first_search) {
        Set<List<event>>
                result = new HashSet<>();

        long combined_deadline = Math.max(System.currentTimeMillis() + combined__timeout, combined__timeout);

        if (random_before_breadth_first_search) {
            Set<List<event>> result1 = compare_find_words_random(
                    generator1,
                    generator1_semantics,
                    check_only_marked,
                    random__max_depth,
                    Math.min(random__max_num, combined__max_num),
                    random__timeout,
                    combined_deadline);
            Set<List<event>> result2 = compare_find_words_breadth_first_search(
                    generator1,
                    generator1_semantics,
                    check_only_marked,
                    breadth_first_search__max_depth,
                    Math.min(breadth_first_search__max_num, combined__max_num - result1.size()),
                    breadth_first_search__timeout,
                    combined_deadline);
            result.addAll(result1);
            System.out.println("words computed1: " + result1.size());
            result.addAll(result2);
            System.out.println("words computed2: " + result2.size());
        } else {
            Set<List<event>> result1 = compare_find_words_breadth_first_search(
                    generator1,
                    generator1_semantics,
                    check_only_marked,
                    breadth_first_search__max_depth,
                    Math.min(breadth_first_search__max_num, combined__max_num),
                    breadth_first_search__timeout,
                    combined_deadline);
            Set<List<event>> result2 = compare_find_words_random(
                    generator1,
                    generator1_semantics,
                    check_only_marked,
                    random__max_depth,
                    Math.min(random__max_num, combined__max_num - result1.size()),
                    random__timeout,
                    combined_deadline);
            result.addAll(result1);
            System.out.println("words computed1: " + result1.size());
            result.addAll(result2);
            System.out.println("words computed2: " + result2.size());
        }


        return result;
    }

    private Set<List<event>>
    compare_find_words_breadth_first_search(
            generator1 generator1,
            generator1_semantics generator1_semantics,
            boolean check_only_marked,
            int breadth_first_search__max_depth,
            int max_words,
            long breadth_first_search__timeout,
            long combined_deadline) {
        Set<List<event>> result = new HashSet<>();
        if (breadth_first_search__max_depth >= 0) {
            long deadline = Math.min(combined_deadline, Math.max(System.currentTimeMillis() + breadth_first_search__timeout, breadth_first_search__timeout));

            BlockingDeque<General_Tuple2<configuration1, Integer>> states = new LinkedBlockingDeque<>();
            states.add(new General_Tuple2<>(generator1_semantics.get_initial_configuration(generator1), 0));
            while (result.size() < max_words
                    && System.currentTimeMillis() < deadline
                    && !states.isEmpty()) {
                General_Tuple2<configuration1, Integer>
                        state = states.removeLast();
                configuration1 configuration = state.getElemA();
                int current_depth = state.getElemB();

                if (current_depth > breadth_first_search__max_depth)
                    continue;
                if (!check_only_marked || generator1_semantics.is_marking_configuration(generator1, configuration))
                    result.add(configuration.get_history());
                List<tuple2<step_label1, configuration1>>
                        successors = generator1_semantics.step_relation(generator1, configuration);
                for (tuple2<step_label1, configuration1> elem : successors)
                    states.addFirst(new General_Tuple2<>(elem.get_value2(), current_depth + 1));
            }
        }
        return result;
    }

    private Set<List<event>> compare_find_words_random(
            generator1 generator1,
            generator1_semantics generator1_semantics,
            boolean check_only_marked,
            int random__max_depth,
            int max_words,
            long random__timeout,
            long combined_deadline) {
        Set<List<event>> result = new HashSet<>();
        if (random__max_depth >= 0) {
            long deadline = Math.min(combined_deadline, Math.max(System.currentTimeMillis() + random__timeout, random__timeout));

            int current_depth = 0;
            configuration1 configuration = generator1_semantics.get_initial_configuration(generator1);
            Random random = new Random();
            while (result.size() < max_words
                    && System.currentTimeMillis() < deadline) {
                if (current_depth > random__max_depth) {
                    configuration = generator1_semantics.get_initial_configuration(generator1);
                    current_depth = 0;
                }
                if (!check_only_marked || generator1_semantics.is_marking_configuration(generator1, configuration))
                    result.add(configuration.get_history());
                List<tuple2<step_label1, configuration1>>
                        successors = generator1_semantics.step_relation(generator1, configuration);
                if (successors.isEmpty()) {
                    configuration = generator1_semantics.get_initial_configuration(generator1);
                    current_depth = 0;
                } else {
                    configuration = successors.get(random.nextInt(successors.size())).get_value2();
                    current_depth++;
                }
                //System.out.println(configuration);
            }
        }
        return result;
    }

    public boolean
    compare_check_word(
            generator2 generator2,
            generator2_semantics generator2_semantics,
            boolean check_only_marked,
            List<event> word,
            long single_word_timeout,
            long all_words_deadline) {
//        System.out.println("checking word: " + word);
        long deadline = Math.min(all_words_deadline, Math.max(System.currentTimeMillis() + single_word_timeout, single_word_timeout));

        BlockingDeque<configuration2> configurations = new LinkedBlockingDeque<>();
        configurations.add(generator2_semantics.get_initial_configuration(generator2));
        while (System.currentTimeMillis() < deadline
                && !configurations.isEmpty()) {
            configuration2 configuration = configurations.removeLast();
//            System.out.println("checking: "+word+"; "+configuration.get_generatorH_history());
            if (!check_only_marked || generator2_semantics.is_marking_configuration(generator2, configuration) && word.equals(configuration.get_history()))
                return true;
            List<tuple2<step_label2, configuration2>>
                    successors = generator2_semantics.step_relation(generator2, configuration);
            for (tuple2<step_label2, configuration2> elem : successors)
                if (General_List.prefix(elem.get_value2().get_history(), word))
                    configurations.addFirst(elem.get_value2());
        }
        return false;
    }


}
