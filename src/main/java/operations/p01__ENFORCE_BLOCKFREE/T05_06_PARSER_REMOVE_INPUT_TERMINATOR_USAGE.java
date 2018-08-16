package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.parser;
import datatypes.formalisms.parser_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE {
    private static Logger log = LogManager.getLogger(T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE.class.getName());

    private T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stack extends General, event extends General, marker extends General>
    parser<stack, event, marker>
    F_PARSER_RITU(
            parser<stack, event, marker> parser) {
        Config.instance.measuring_thread.push("F_PARSER_RITU");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_PARSER_RITU: " + "parser=" + parser.log_info());

        List<parser_step_label<stack, event>> step_labels = new ArrayList<>();
        List<event> bottomlist = General_List.list_by_enumeration(parser.get_parser_bottom());
        Set<stack> marking = new HashSet<stack>();
        for (parser_step_label<stack, event> step_label : parser.get_parser_step_labels())
            if (!step_label.get_rule_rpop().equals(bottomlist))
                step_labels.add(step_label);
            else
                marking.add(General_List.last(step_label.get_rule_lpop()));
        parser<stack, event, marker> result = new parser<>(
                parser.get_parser_nonterms(),
                parser.get_parser_events(),
                parser.get_parser_initial(),
                new ArrayList<stack>(marking),
                step_labels,
                parser.get_parser_marker(),
                parser.get_parser_bottom()
        );
        Config.instance.measuring_thread.pop("F_PARSER_RITU");
        return result;
    }

}
