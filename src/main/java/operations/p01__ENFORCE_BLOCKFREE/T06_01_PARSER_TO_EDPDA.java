package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import datatypes.formalisms.parser;
import datatypes.formalisms.parser_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T06_01_PARSER_TO_EDPDA {
    private static Logger log = LogManager.getLogger(T06_01_PARSER_TO_EDPDA.class.getName());

    private T06_01_PARSER_TO_EDPDA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stack extends General, event extends General, marker extends General>
    epda<stack, event, stack>
    F_PARSER_TO_EDPDA(
            parser<stack, event, marker> parser,
            stack box) {
        Config.instance.measuring_thread.push("F_PARSER_TO_EDPDA");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_PARSER_TO_EDPDA: " + "parser=" + parser.log_info());
        List<event> events = new ArrayList<>(parser.get_parser_events());
        events.remove(parser.get_parser_bottom());
        List<stack> gamma = new ArrayList<>(parser.get_parser_nonterms());
        gamma.add(box);
        List<epda_step_label<stack, event, stack>> step_labels = new ArrayList<>();
        for (parser_step_label<stack, event> e : parser.get_parser_step_labels())
            step_labels.add(F_PARSER_TO_EDPDA__rules(e));


        epda<stack, event, stack> result = new epda<>(
                parser.get_parser_nonterms(),
                events,
                gamma,
                step_labels,
                parser.get_parser_initial(),
                box,
                parser.get_parser_marking());
        Config.instance.measuring_thread.pop("F_PARSER_TO_EDPDA");
        return result;
    }

    private static <stack extends General, event extends General>
    epda_step_label<stack, event, stack>
    F_PARSER_TO_EDPDA__rules(
            parser_step_label<stack, event> e) {
        return new epda_step_label<stack, event, stack>(
                General_List.last(e.get_rule_lpop()),
                General_List.list_to_option(e.get_rule_rpop()),
                General_List.rev(General_List.butlast(e.get_rule_lpop())),
                General_List.rev(General_List.butlast(e.get_rule_lpush())),
                General_List.last(e.get_rule_lpush()));
    }

}
