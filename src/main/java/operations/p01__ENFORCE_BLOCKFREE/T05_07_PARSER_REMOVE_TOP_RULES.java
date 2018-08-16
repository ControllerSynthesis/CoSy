package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.parser;
import datatypes.formalisms.parser_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_07_PARSER_REMOVE_TOP_RULES {
    private static Logger log = LogManager.getLogger(T05_07_PARSER_REMOVE_TOP_RULES.class.getName());

    private T05_07_PARSER_REMOVE_TOP_RULES() {
        throw new IllegalStateException("Utility Class");
    }

    private static <stack extends General, event extends General>
    stack_and_event_or_stack__abstract<stack, event>
    F_PARSER_RTR__rules_annotate_element(
            stack stack,
            option__abstract<event> event) {
        if (event instanceof option__None)
            return new stack_and_event_or_stack__cons_stack<>(stack);
        return new stack_and_event_or_stack__cons_stack_and_event<stack, event>(stack, ((option__Some<event>) event).the());
    }

    private static <stack extends General, event extends General>
    List<stack_and_event_or_stack__abstract<stack, event>>
    F_PARSER_RTR__rules_annotate_word(
            List<stack> w,
            option__abstract<event> event) {
        if (w.isEmpty())
            return new ArrayList<>();
        List<stack_and_event_or_stack__abstract<stack, event>> result = new ArrayList<>();
        for (int i = 0; i < w.size() - 1; i++)
            result.add(F_PARSER_RTR__rules_annotate_element(w.get(i), new option__None<>()));
        result.add(F_PARSER_RTR__rules_annotate_element(w.get(w.size() - 1), event));
        return result;
    }

    /**
     * F_PARSER_RTR__rules_reduce_old_observe, F_PARSER_RTR__rules_reduce_new_observe,
     * F_PARSER_RTR__rules_shift_old_observed, F_PARSER_RTR__rules_shift_new_observed
     * implemented implicitly
     */
    public static <stack extends General, event extends General, marker extends General>
    parser<stack_and_event_or_stack__abstract<stack, event>, event, marker>
    F_PARSER_RTR(
            parser<stack, event, marker> parser) {
        Config.instance.measuring_thread.push("F_PARSER_RTR");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_PARSER_RTR: " + "parser=" + parser.log_info());

        List<event> emptylist = new ArrayList<>();
        Map<event, option__Some<event>> events = new HashMap<>();

        List<parser_step_label<stack_and_event_or_stack__abstract<stack, event>, event>> step_labels = new ArrayList<>();
        for (parser_step_label<stack, event> e : parser.get_parser_step_labels()) {
            event first_event = e.get_rule_rpop().get(0);
            option__Some<event> first_event_Some = events.get(first_event);
            if (first_event_Some == null) {
                events.put(first_event, new option__Some<>(first_event));
                first_event_Some = events.get(first_event);
            }
            if (e.get_rule_rpop().equals(e.get_rule_rpush())) {
                //F_PARSER_RTR__rules_reduce_old_observe
                step_labels.add(new parser_step_label<>(
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpop(), new option__None<>()),
                        e.get_rule_rpop(),
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpush(), first_event_Some),
                        emptylist
                ));
                //F_PARSER_RTR__rules_reduce_new_observe
                step_labels.add(new parser_step_label<>(
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpop(), first_event_Some),
                        emptylist,
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpush(), first_event_Some),
                        emptylist
                ));
            } else {
                //F_PARSER_RTR__rules_shift_old_observed
                step_labels.add(new parser_step_label<>(
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpop(), first_event_Some),
                        emptylist,
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpush(), new option__None<>()),
                        emptylist
                ));
                //F_PARSER_RTR__rules_shift_new_observed
                step_labels.add(new parser_step_label<>(
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpop(), new option__None<>()),
                        e.get_rule_rpop(),
                        F_PARSER_RTR__rules_annotate_word(e.get_rule_lpush(), new option__None<>()),
                        emptylist
                ));
            }
        }
        stack_and_event_or_stack__abstract<stack, event> initial = F_PARSER_RTR__rules_annotate_element(parser.get_parser_initial(), new option__None<>());
        Set<stack_and_event_or_stack__abstract<stack, event>> states = new HashSet<>();
        states.add(initial);
        for (parser_step_label<stack_and_event_or_stack__abstract<stack, event>, event> e : step_labels) {
            states.addAll(e.get_rule_lpop());
            states.addAll(e.get_rule_lpush());
        }
        Set<stack> pre_marking = new HashSet<>(parser.get_parser_marking());
        List<stack_and_event_or_stack__abstract<stack, event>> marking = new ArrayList<>();
        for (stack_and_event_or_stack__abstract<stack, event> q : states) {
            if (q instanceof stack_and_event_or_stack__cons_stack && pre_marking.contains(((stack_and_event_or_stack__cons_stack<stack, event>) q).get_value()))
                marking.add(q);
        }

        parser<stack_and_event_or_stack__abstract<stack, event>, event, marker> result = new parser<>(
                new ArrayList<>(states),
                parser.get_parser_events(),
                initial,
                marking,
                step_labels,
                new HashMap<>(),
                parser.get_parser_bottom()
        );
        Config.instance.measuring_thread.pop("F_PARSER_RTR");
        return result;
    }

}
