package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.language_check.Semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class epdaH_semantics<state extends General, event extends General, stack extends General>
        implements Semantic<epda<state, event, stack>, epdaH_configuration<state, event, stack>, epda_step_label<state, event, stack>, event> {

    @Override
    public List<tuple2<epda_step_label<state, event, stack>, epdaH_configuration<state, event, stack>>>
    step_relation(
            epda<state, event, stack> epda,
            epdaH_configuration<state, event, stack> epdaH_configuration) {
        List<tuple2<epda_step_label<state, event, stack>, epdaH_configuration<state, event, stack>>> result = new ArrayList<>();
        for (epda_step_label<state, event, stack> step_label : epda.get_map_source(epdaH_configuration.get_epdaH_state())) {
            if (General_List.prefix(step_label.get_edge_pop(), epdaH_configuration.get_epdaH_stack())) {
                List<stack> result_stack = new ArrayList<>(step_label.get_edge_push());
                result_stack.addAll(General_List.drop(epdaH_configuration.get_epdaH_stack(), step_label.get_edge_pop().size()));
                List<event> result_history = new ArrayList<>(epdaH_configuration.get_epdaH_history());
                if (step_label.get_edge_event() instanceof option__Some)
                    result_history.add(((option__Some<event>) step_label.get_edge_event()).the());
                result.add(new tuple2<>(
                        step_label,
                        new epdaH_configuration<>(step_label.get_edge_trg(), result_history, result_stack)));
            }
        }
        return result;
    }

    @Override
    public epdaH_configuration<state, event, stack> get_initial_configuration(epda<state, event, stack> epda) {
        List<stack> stack = new ArrayList<>();
        stack.add(epda.get_epda_box());
        return new epdaH_configuration<>(epda.get_epda_initial(), new ArrayList<event>(), stack);
    }

    @Override
    public boolean is_marking_configuration(epda<state, event, stack> epda, epdaH_configuration<state, event, stack> configuration) {
        return epda.get_epda_marking().contains(configuration.get_epdaH_state());
    }

    public void print_accessible_configurations(epda<state, event, stack> epda, int depth, boolean print_only_marking_configurations) {
        List<epdaH_configuration<state, event, stack>> todo = new ArrayList<>();
        todo.add(get_initial_configuration(epda));
        for (int i = 0; i < depth; i++) {
            List<epdaH_configuration<state, event, stack>> next = new ArrayList<>();
            for (epdaH_configuration<state, event, stack> configuration : todo) {
                for (tuple2<epda_step_label<state, event, stack>, epdaH_configuration<state, event, stack>> pair : step_relation(epda, configuration)) {
                    System.out.println(pair.get_value2());
                    next.add(pair.get_value2());
                }
            }
            todo = next;
        }
    }


}
