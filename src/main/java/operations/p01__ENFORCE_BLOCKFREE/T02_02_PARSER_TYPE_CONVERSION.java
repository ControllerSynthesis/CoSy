package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.formalisms.parser;
import datatypes.formalisms.parser_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supporting_operations.SOME_injective;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T02_02_PARSER_TYPE_CONVERSION {

    private static Logger log = LogManager.getLogger(T02_02_PARSER_TYPE_CONVERSION.class.getName());

    private T02_02_PARSER_TYPE_CONVERSION() {
        throw new IllegalStateException("Utility Class");
    }

    private static <stackA extends General, stackB extends General, event extends General, marker extends General>
    parser_step_label<symbol__abstract<stackB>, event>
    F_PARSER_TC__rule(
            Map<stackA, symbol__abstract<stackB>> f_stack,
            parser_step_label<stackA, event> e) {
        return new parser_step_label(
                e.get_rule_lpop().stream().map(x -> f_stack.get(x)).collect(Collectors.toList()),
                e.get_rule_rpop(),
                e.get_rule_lpush().stream().map(x -> f_stack.get(x)).collect(Collectors.toList()),
                e.get_rule_rpush());
    }


    public static <stackA extends General, stackB extends General, event extends General, marker extends General>
    parser<symbol__abstract<stackB>, event, option__abstract<General_Integer>>
    F_PARSER_TC__parser(
            parser<stackA, event, marker> parser,
            Map<stackA, symbol__abstract<stackB>> f_stack) {
        List<parser_step_label<symbol__abstract<stackB>, event>> labels =
                parser.get_parser_step_labels().stream().map(e -> F_PARSER_TC__rule(f_stack, e)).collect(Collectors.toList());
        Map<parser_step_label<symbol__abstract<stackB>, event>, option__abstract<General_Integer>> parser_marker =
                Collections.synchronizedMap(new HashMap<>());
        labels.stream().forEach(e -> parser_marker.put(e, new option__None()));
        return new parser<>(
                parser.get_parser_nonterms().stream().map(e -> f_stack.get(e)).collect(Collectors.toList()),
                parser.get_parser_events(),
                f_stack.get(parser.get_parser_initial()),
                parser.get_parser_marking().stream().map(e -> f_stack.get(e)).collect(Collectors.toList()),
                labels,
                parser_marker,
                parser.get_parser_bottom());
    }


    public static <stackA extends General, stackB extends General, event extends General, marker extends General>
    parser<symbol__abstract<stackB>, event, option__abstract<General_Integer>>
    F_PARSER_TC(
            parser<stackA, event, marker> parser) {
        Config.instance.measuring_thread.push("F_PARSER_TC");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_PARSER_TC: " + "parser=" + parser.log_info());

        parser<symbol__abstract<stackB>, event, option__abstract<General_Integer>> result = F_PARSER_TC__parser(parser,
                SOME_injective.SOME_injective(parser.get_parser_nonterms()));
        Config.instance.measuring_thread.pop("F_PARSER_TC");
        return result;
    }

}
