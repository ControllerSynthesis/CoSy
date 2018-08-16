package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.language_check.Semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved
 */
public class parserHF_semantics<stack extends General, event extends General, marker extends General>
        implements Semantic<parser<stack, event, marker>, parserHF_configuration<stack, event>, parser_step_label<stack, event>, event> {

    @Override
    public List<tuple2<parser_step_label<stack, event>, parserHF_configuration<stack, event>>>
    step_relation(
            parser<stack, event, marker> parser,
            parserHF_configuration<stack, event> parserHF_configuration) {
        List<tuple2<parser_step_label<stack, event>, parserHF_configuration<stack, event>>> result = new ArrayList<>();
        for (parser_step_label<stack, event> step_label : parser.get_parser_step_labels()) {
            if (!General_List.ends_with(parserHF_configuration.get_parserHF_conf_stack(), step_label.get_rule_lpop()))
                continue;
            if (!General_List.prefix(step_label.get_rule_rpop(), parserHF_configuration.get_parserHF_conf_fixed()) && !General_List.prefix(parserHF_configuration.get_parserHF_conf_fixed(), step_label.get_rule_rpop()))
                continue;
            List<stack> new_stack = General_List.append(General_List.drop_from_end(parserHF_configuration.get_parserHF_conf_stack(), step_label.get_rule_lpop()), step_label.get_rule_lpush());
            List<event> new_history =
                    General_List.append(parserHF_configuration.get_parserHF_conf_history(), General_List.drop(General_List.butlast_if_match(step_label.get_rule_rpop(), parser.get_parser_bottom()), parserHF_configuration.get_parserHF_conf_fixed().size()));
            List<event> new_fixed =
                    General_List.append(step_label.get_rule_rpush(), General_List.drop(parserHF_configuration.get_parserHF_conf_fixed(), step_label.get_rule_rpop().size()));
            result.add(new tuple2<>(step_label, new parserHF_configuration<>(new_fixed, new_history, new_stack)));
        }
        return result;
    }

    @Override
    public parserHF_configuration<stack, event> get_initial_configuration(parser<stack, event, marker> parser) {
        return new parserHF_configuration<>(new ArrayList<>(), new ArrayList<event>(), General_List.list_by_enumeration(parser.get_parser_initial()));
    }

    @Override
    public boolean is_marking_configuration(parser<stack, event, marker> parser, parserHF_configuration<stack, event> configuration) {
        return parser.get_parser_marking().contains(configuration.get_parserHF_conf_stack().get(configuration.get_parserHF_conf_stack().size() - 1));
    }

    public void print_accessible_configurations(parser<stack, event, marker> parser, int depth, boolean print_only_marking_configurations) {
        List<parserHF_configuration<stack, event>> todo = new ArrayList<>();
        todo.add(get_initial_configuration(parser));
        for (int i = 0; i < depth; i++) {
            List<parserHF_configuration<stack, event>> next = new ArrayList<>();
            for (parserHF_configuration<stack, event> configuration : todo) {
                for (tuple2<parser_step_label<stack, event>, parserHF_configuration<stack, event>> pair : step_relation(parser, configuration)) {
                    System.out.println(pair.get_value2());
                    next.add(pair.get_value2());
                }
            }
            todo = next;
        }
    }


}
