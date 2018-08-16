package datatypes.formalisms;

import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.formalisms.language_check.Comparison_inclusion;
import main.Config;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class parser<stack extends General, event extends General, marker extends General>
        implements General {


    private List<stack> parser_nonterms;
    private List<event> parser_events;
    private stack parser_initial;
    private List<stack> parser_marking;
    private List<parser_step_label<stack, event>> parser_step_labels;
    private event parser_bottom;
    private Map<parser_step_label<stack, event>, marker> parser_marker;
    private int cached_hashcode = 0;

    public parser(
            List<stack> parser_nonterms,
            List<event> parser_events,
            stack parser_initial,
            List<stack> parser_marking,
            List<parser_step_label<stack, event>> parser_step_labels,
            Map<parser_step_label<stack, event>, marker> parser_marker,
            event parser_bottom) {
        this.parser_nonterms = Collections.unmodifiableList(parser_nonterms);
        this.parser_events = Collections.unmodifiableList(parser_events);
        this.parser_initial = parser_initial;
        this.parser_marking = Collections.unmodifiableList(parser_marking);
        this.parser_step_labels = Collections.unmodifiableList(parser_step_labels);
        this.parser_marker = Collections.unmodifiableMap(parser_marker);
        this.parser_bottom = parser_bottom;
    }

    public static <stack extends General,
            event extends General,
            marker extends General>
    boolean check_word_inclusion(parser<stack, event, marker> parser, List<event> word, int expected) {
        if (General_Test_Sequence.MARK == expected)
            return new Comparison_inclusion<
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    event>()
                    .compare_check_word(
                            parser,
                            new parserHF_semantics<>(),
                            true,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.UNMARK == expected)
            return new Comparison_inclusion<
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    event>()
                    .compare_check_word(
                            parser,
                            new parserHF_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.REJECT == expected)
            return !new Comparison_inclusion<
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    parser<stack, event, marker>,
                    parserHF_semantics<stack, event, marker>,
                    parserHF_configuration<stack, event>,
                    parser_step_label<stack, event>,
                    event>()
                    .compare_check_word(
                            parser,
                            new parserHF_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else throw new RuntimeException();
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public String log_info() {
        if (Config.CHECK_VALID_PARSER_ON_LOG_INFO && !is_valid())
            throw new RuntimeException();
        return "(" + get_parser_nonterms().size() + "," + get_parser_step_labels().size() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        parser<?, ?, ?> parser = (parser<?, ?, ?>) o;

        if (parser_nonterms != null ? !parser_nonterms.equals(parser.parser_nonterms) : parser.parser_nonterms != null)
            return false;
        if (parser_events != null ? !parser_events.equals(parser.parser_events) : parser.parser_events != null)
            return false;
        if (parser_initial != null ? !parser_initial.equals(parser.parser_initial) : parser.parser_initial != null)
            return false;
        if (parser_marking != null ? !parser_marking.equals(parser.parser_marking) : parser.parser_marking != null)
            return false;
        if (parser_step_labels != null ? !parser_step_labels.equals(parser.parser_step_labels) : parser.parser_step_labels != null)
            return false;
        if (parser_bottom != null ? !parser_bottom.equals(parser.parser_bottom) : parser.parser_bottom != null)
            return false;
        return parser_marker != null ? parser_marker.equals(parser.parser_marker) : parser.parser_marker == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = parser_nonterms != null ? parser_nonterms.hashCode() : 0;
            result = 31 * result + (parser_events != null ? parser_events.hashCode() : 0);
            result = 31 * result + (parser_initial != null ? parser_initial.hashCode() : 0);
            result = 31 * result + (parser_marking != null ? parser_marking.hashCode() : 0);
            result = 31 * result + (parser_step_labels != null ? parser_step_labels.hashCode() : 0);
            result = 31 * result + (parser_bottom != null ? parser_bottom.hashCode() : 0);
            result = 31 * result + (parser_marker != null ? parser_marker.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    public List<stack> get_parser_nonterms() {
        return parser_nonterms;
    }

    public List<event> get_parser_events() {
        return parser_events;
    }

    public stack get_parser_initial() {
        return parser_initial;
    }

    public List<stack> get_parser_marking() {
        return parser_marking;
    }

    public List<parser_step_label<stack, event>> get_parser_step_labels() {
        return parser_step_labels;
    }

    public event get_parser_bottom() {
        return parser_bottom;
    }

    public Map<parser_step_label<stack, event>, marker> get_parser_marker() {
        return parser_marker;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        new Append_helper().append(parser_nonterms, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(parser_events, stringBuffer);
        stringBuffer.append(", ");
        parser_initial.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(parser_marking, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(parser_step_labels, stringBuffer);
        stringBuffer.append(", ");
        parser_bottom.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(parser_marker, stringBuffer);
        stringBuffer.append("⟫");
    }

    public boolean is_valid() {
        Set<stack> stackSet = new HashSet<stack>(get_parser_nonterms());
        for (parser_step_label<stack, event> step_label : get_parser_step_labels()) {
            if (!stackSet.containsAll(step_label.get_rule_lpop()))
                return false;
            if (!stackSet.containsAll(step_label.get_rule_lpush()))
                return false;
            if (!get_parser_events().containsAll(step_label.get_rule_rpop()))
                return false;
            if (!get_parser_events().containsAll(step_label.get_rule_rpush()))
                return false;
        }
        if (!get_parser_events().contains(get_parser_bottom()))
            return false;
        if (!stackSet.contains(get_parser_initial()))
            return false;
        return stackSet.containsAll(get_parser_marking());
    }

    public boolean is_deterministic() {
        for (parser_step_label<stack, event> step1 : get_parser_step_labels())
            for (parser_step_label<stack, event> step2 : get_parser_step_labels())
                if ((step1 != step2)
                        && (General_List.suffix(step2.get_rule_lpop(), step1.get_rule_lpop())
                        || General_List.suffix(step1.get_rule_lpop(), step2.get_rule_lpop()))
                        && (General_List.suffix(step2.get_rule_rpop(), step1.get_rule_rpop())
                        || General_List.suffix(step1.get_rule_rpop(), step2.get_rule_rpop())))
                    return false;
        return true;
    }

}
