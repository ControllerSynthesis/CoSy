package import_export;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General_SigmaUC;
import datatypes.formalisms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class XML_writer {

    private static Logger log = LogManager.getLogger(XML_writer.class.getName());

    private String path;
    private String filename;
    private String path_to_xsd;
    private List<Object> to_be_exported = new ArrayList<>();
    private Map<Object, String> generated_ids = new HashMap<>();
    private Map<String, Integer> typed_id_min = new HashMap<>();

    public XML_writer(String path, String filename, String path_to_xsd) {
        this.path = path;
        if (!this.path.endsWith("/"))
            this.path += "/";
        File file = new File(path + "/xy.txt");
        if (file.getParentFile() != null)
            file.getParentFile().mkdirs();
        this.filename = filename;
        this.path_to_xsd = path_to_xsd;
    }

    public static String depth(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result += "    ";
        }
        return result;
    }

    private String get_id(String type, Object o) {
        String id = generated_ids.get(o);
        if (id != null)
            return id;
        typed_id_min.putIfAbsent(type, 0);
        Integer min = typed_id_min.get(type);
        min++;
        typed_id_min.put(type, min);
        id = type + "-" + min;
        generated_ids.put(o, id);
        return id;
    }

    public void add_to_be_exported(Object o) {
        to_be_exported.add(o);
    }

    public void set_filename(String filename) {
        this.filename = filename;
    }

    public void export() {
        try {
            File file = new File(path + filename);
            if (file.getParentFile() != null)
                file.getParentFile().mkdirs();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

                // step1: create StringBuffer with content
                bufferedWriter.write("<?xml version=\"1.0\"?>" + System.lineSeparator());
                bufferedWriter.write("<root" + System.lineSeparator());
                bufferedWriter.write(depth(1) + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + System.lineSeparator());
                bufferedWriter.write(depth(1) + "xmlns:xi=\"http://www.w3.org/2001/XInclude\"" + System.lineSeparator());
                bufferedWriter.write(depth(1) + "xsi:noNamespaceSchemaLocation=\"" + path_to_xsd + "\">" + System.lineSeparator());

                for (Object o : to_be_exported)
                    export__object(bufferedWriter, o);
                bufferedWriter.write("</root>" + System.lineSeparator());
                if (log.isInfoEnabled()) log.info(XML_writer.class.toString() + " done");

                bufferedWriter.close();
            }
            if (log.isInfoEnabled()) log.info(XML_writer.class.toString() + " closed");
        } catch (IOException e) {
            log.error(XML_writer.class.toString() + " " + e.toString());
        } finally {
            to_be_exported.clear();
            generated_ids.clear();

        }
    }

    private void export__object(BufferedWriter bufferedWriter, Object o) throws IOException {
        if (o instanceof string_symbol)
            export__string_symbol(bufferedWriter, (string_symbol) o);
        else if (o instanceof General_SigmaUC)
            export__sigma_uncontrollable(bufferedWriter, (General_SigmaUC) o);
        else if (o instanceof l2_l3_nonterminals__cons_l2)
            export__l2_l3_nonterminals__cons_l2(bufferedWriter, (l2_l3_nonterminals__cons_l2) o);
        else if (o instanceof l2_l3_nonterminals__cons_l3)
            export__l2_l3_nonterminals__cons_l3(bufferedWriter, (l2_l3_nonterminals__cons_l3) o);
        else if (o instanceof state_and_stack_or_state__cons_state)
            export__state_and_stack_or_state__cons_state(bufferedWriter, (state_and_stack_or_state__cons_state) o);
        else if (o instanceof state_and_stack_or_state__cons_state_and_stack)
            export__state_and_stack_or_state__cons_state_and_stack(bufferedWriter, (state_and_stack_or_state__cons_state_and_stack) o);
        else if (o instanceof state_or_edge__cons_state_or_edge_new)
            export__state_or_edge__cons_state_or_edge_new(bufferedWriter, (state_or_edge__cons_state_or_edge_new) o);
        else if (o instanceof state_or_edge__cons_state_or_edge_old)
            export__state_or_edge__cons_state_or_edge_old(bufferedWriter, (state_or_edge__cons_state_or_edge_old) o);
        else if (o instanceof state_or_edge_nat__cons_state_or_edge_nat_new)
            export__state_or_edge_nat__cons_state_or_edge_nat_new(bufferedWriter, (state_or_edge_nat__cons_state_or_edge_nat_new) o);
        else if (o instanceof state_or_edge_nat__cons_state_or_edge_nat_old)
            export__state_or_edge_nat__cons_state_or_edge_nat_old(bufferedWriter, (state_or_edge_nat__cons_state_or_edge_nat_old) o);
        else if (o instanceof state_or_state__cons_state_or_state_new)
            export__state_or_state__cons_state_or_state_new(bufferedWriter, (state_or_state__cons_state_or_state_new) o);
        else if (o instanceof state_or_state__cons_state_or_state_old)
            export__state_or_state__cons_state_or_state_old(bufferedWriter, (state_or_state__cons_state_or_state_old) o);
        else if (o instanceof symbol__cons_symbol_base)
            export__symbol__cons_symbol_base(bufferedWriter, (symbol__cons_symbol_base) o);
        else if (o instanceof symbol__cons_symbol_nat)
            export__symbol__cons_symbol_nat(bufferedWriter, (symbol__cons_symbol_nat) o);
        else if (o instanceof tuple2)
            export__tuple2(bufferedWriter, (tuple2) o);
        else if (o instanceof tuple3)
            export__tuple3(bufferedWriter, (tuple3) o);
        else if (o instanceof two_elements__teA)
            export__two_elements__teA(bufferedWriter, (two_elements__teA) o);
        else if (o instanceof two_elements__teB)
            export__two_elements__teB(bufferedWriter, (two_elements__teB) o);
        else if (o instanceof epda_step_label)
            export__epda_step_label(bufferedWriter, (epda_step_label) o);
        else if (o instanceof epda)
            export__epda(bufferedWriter, (epda) o);
        else if (o instanceof cfg_step_label)
            export__cfg_step_label(bufferedWriter, (cfg_step_label) o);
        else if (o instanceof cfg)
            export__cfg(bufferedWriter, (cfg) o);
        else if (o instanceof parser_step_label)
            export__parser_step_label(bufferedWriter, (parser_step_label) o);
        else if (o instanceof parser)
            export__parser(bufferedWriter, (parser) o);
    }

    private void export__sigma_uncontrollable(BufferedWriter bufferedWriter, General_SigmaUC o) throws IOException {
        String id = get_id("sigma_uncontrollable", o);
        String elements = "";
        for (Object element : o.get_value().get_value()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            elements += " " + generated_ids.get(element);
        }
        elements = elements.trim();
        bufferedWriter.write(depth(1) + "<sigma_uncontrollable id=\"" + id + "\" elements=\"" + elements + "\"/>" + System.lineSeparator());
    }

    private void export__string_symbol(BufferedWriter bufferedWriter, string_symbol o) throws IOException {
        String id = get_id("string_symbol", o);
        bufferedWriter.write(depth(1) + "<string_symbol id=\"" + id + "\" string=\"" + o.get_value() + "\"/>" + System.lineSeparator());
    }

    private void export__l2_l3_nonterminals__cons_l2(BufferedWriter bufferedWriter, l2_l3_nonterminals__cons_l2 o) throws IOException {
        String id = get_id("l2_l3_nonterminals__cons_l2", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        bufferedWriter.write(depth(1) + "<l2_l3_nonterminals__cons_l2 id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\"/>" + System.lineSeparator());
    }

    private void export__l2_l3_nonterminals__cons_l3(BufferedWriter bufferedWriter, l2_l3_nonterminals__cons_l3 o) throws IOException {
        String id = get_id("l2_l3_nonterminals__cons_l3", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        if (!generated_ids.containsKey(o.get_value3()))
            export__object(bufferedWriter, o.get_value3());
        String value3 = generated_ids.get(o.get_value3());
        bufferedWriter.write(depth(1) + "<l2_l3_nonterminals__cons_l3 id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\" value3=\"" + value3 + "\"/>" + System.lineSeparator());
    }

    private void export__state_and_stack_or_state__cons_state(BufferedWriter bufferedWriter, state_and_stack_or_state__cons_state o) throws IOException {
        String id = get_id("state_and_stack_or_state__cons_state", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_and_stack_or_state__cons_state id=\"" + id + "\" value=\"" + value + "\">" + System.lineSeparator());
    }

    private void export__state_and_stack_or_state__cons_state_and_stack(BufferedWriter bufferedWriter, state_and_stack_or_state__cons_state_and_stack o) throws IOException {
        String id = get_id("state_and_stack_or_state__cons_state_and_stack", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        bufferedWriter.write(depth(1) + "<state_and_stack_or_state__cons_state_and_stack id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_edge__cons_state_or_edge_new(BufferedWriter bufferedWriter, state_or_edge__cons_state_or_edge_new o) throws IOException {
        String id = get_id("state_or_edge__cons_state_or_edge_new", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_or_edge__cons_state_or_edge_new id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_edge__cons_state_or_edge_old(BufferedWriter bufferedWriter, state_or_edge__cons_state_or_edge_old o) throws IOException {
        String id = get_id("state_or_edge__cons_state_or_edge_old", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_or_edge__cons_state_or_edge_old id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_edge_nat__cons_state_or_edge_nat_new(BufferedWriter bufferedWriter, state_or_edge_nat__cons_state_or_edge_nat_new o) throws IOException {
        String id = get_id("state_or_edge_nat__cons_state_or_edge_nat_new", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        bufferedWriter.write(depth(1) + "<state_or_edge_nat__cons_state_or_edge_nat_new id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_edge_nat__cons_state_or_edge_nat_old(BufferedWriter bufferedWriter, state_or_edge_nat__cons_state_or_edge_nat_old o) throws IOException {
        String id = get_id("state_or_edge_nat__cons_state_or_edge_nat_old", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_or_edge_nat__cons_state_or_edge_nat_old id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_state__cons_state_or_state_new(BufferedWriter bufferedWriter, state_or_state__cons_state_or_state_new o) throws IOException {
        String id = get_id("state_or_state__cons_state_or_state_new", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_or_state__cons_state_or_state_new id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__state_or_state__cons_state_or_state_old(BufferedWriter bufferedWriter, state_or_state__cons_state_or_state_old o) throws IOException {
        String id = get_id("state_or_state__cons_state_or_state_old", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<state_or_state__cons_state_or_state_old id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__symbol__cons_symbol_base(BufferedWriter bufferedWriter, symbol__cons_symbol_base o) throws IOException {
        String id = get_id("symbol__cons_symbol_base", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<symbol__cons_symbol_base id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__symbol__cons_symbol_nat(BufferedWriter bufferedWriter, symbol__cons_symbol_nat o) throws IOException {
        String id = get_id("symbol__cons_symbol_nat", o);
        bufferedWriter.write(depth(1) + "<symbol__cons_symbol_nat id=\"" + id + "\" value=\"" + o.get_value() + "\"/>" + System.lineSeparator());
    }

    private void export__tuple2(BufferedWriter bufferedWriter, tuple2 o) throws IOException {
        String id = get_id("tuple2", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        bufferedWriter.write(depth(1) + "<tuple2 id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\"/>" + System.lineSeparator());
    }

    private void export__tuple3(BufferedWriter bufferedWriter, tuple3 o) throws IOException {
        String id = get_id("tuple3", o);
        if (!generated_ids.containsKey(o.get_value1()))
            export__object(bufferedWriter, o.get_value1());
        String value1 = generated_ids.get(o.get_value1());
        if (!generated_ids.containsKey(o.get_value2()))
            export__object(bufferedWriter, o.get_value2());
        String value2 = generated_ids.get(o.get_value2());
        if (!generated_ids.containsKey(o.get_value3()))
            export__object(bufferedWriter, o.get_value3());
        String value3 = generated_ids.get(o.get_value3());
        bufferedWriter.write(depth(1) + "<tuple3 id=\"" + id + "\" value1=\"" + value1 + "\" value2=\"" + value2 + "\" value3=\"" + value3 + "\"/>" + System.lineSeparator());
    }

    private void export__two_elements__teA(BufferedWriter bufferedWriter, two_elements__teA o) throws IOException {
        String id = get_id("two_elements__teA", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<two_elements__teA id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__two_elements__teB(BufferedWriter bufferedWriter, two_elements__teB o) throws IOException {
        String id = get_id("two_elements__teB", o);
        if (!generated_ids.containsKey(o.get_value()))
            export__object(bufferedWriter, o.get_value());
        String value = generated_ids.get(o.get_value());
        bufferedWriter.write(depth(1) + "<two_elements__teB id=\"" + id + "\" value=\"" + value + "\"/>" + System.lineSeparator());
    }

    private void export__epda_step_label(BufferedWriter bufferedWriter, epda_step_label o) throws IOException {
        String id = get_id("epda_step_label", o);
        if (!generated_ids.containsKey(o.get_edge_src()))
            export__object(bufferedWriter, o.get_edge_src());
        if (o.get_edge_event() instanceof option__Some && !generated_ids.containsKey(((option__Some) o.get_edge_event()).get_value()))
            export__object(bufferedWriter, ((option__Some) o.get_edge_event()).get_value());
        String edge_pop_ids = "";
        for (Object element : o.get_edge_pop()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            edge_pop_ids += " " + generated_ids.get(element);
        }
        edge_pop_ids = edge_pop_ids.trim();
        String edge_push_ids = "";
        for (Object element : o.get_edge_push()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            edge_push_ids += " " + generated_ids.get(element);
        }
        edge_push_ids = edge_push_ids.trim();
        if (!generated_ids.containsKey(o.get_edge_trg()))
            export__object(bufferedWriter, o.get_edge_trg());

        bufferedWriter.write(depth(1) + "<epda_step_label id=\"" + id
                + "\" edge_src=\"" + generated_ids.get(o.get_edge_src()) + "\""
                + (o.get_edge_event() instanceof option__Some ?
                " edge_event=\"" + generated_ids.get(((option__Some) o.get_edge_event()).get_value()) + "\""
                : "")
                + (!edge_pop_ids.isEmpty() ? " edge_pop=\"" + edge_pop_ids + "\"" : "")
                + (!edge_push_ids.isEmpty() ? " edge_push=\"" + edge_push_ids + "\"" : "")
                + " edge_trg=\"" + generated_ids.get(o.get_edge_trg()) + "\""
                + "/>" + System.lineSeparator());
    }

    private void export__epda(BufferedWriter bufferedWriter, epda o) throws IOException {
        String id = get_id("epda", o);

        String epda_states_ids = "";
        for (Object element : o.get_epda_states()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            epda_states_ids += " " + generated_ids.get(element);
        }
        epda_states_ids = epda_states_ids.trim();

        String epda_events_ids = "";
        for (Object element : o.get_epda_events()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            epda_events_ids += " " + generated_ids.get(element);
        }
        epda_events_ids = epda_events_ids.trim();

        String epda_gamma_ids = "";
        for (Object element : o.get_epda_gamma()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            epda_gamma_ids += " " + generated_ids.get(element);
        }
        epda_gamma_ids = epda_gamma_ids.trim();

        String epda_delta_ids = "";
        for (Object element : o.get_epda_delta()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            epda_delta_ids += " " + generated_ids.get(element);
        }
        epda_delta_ids = epda_delta_ids.trim();

        if (!generated_ids.containsKey(o.get_epda_initial()))
            export__object(bufferedWriter, o.get_epda_initial());

        if (!generated_ids.containsKey(o.get_epda_box()))
            export__object(bufferedWriter, o.get_epda_box());

        String epda_marking_ids = "";
        for (Object element : o.get_epda_marking()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            epda_marking_ids += " " + generated_ids.get(element);
        }
        epda_marking_ids = epda_marking_ids.trim();

        bufferedWriter.write(depth(1) + "<epda id=\"" + id + "\""
                + (!epda_states_ids.isEmpty() ? " epda_states=\"" + epda_states_ids + "\"" : "")
                + (!epda_events_ids.isEmpty() ? " epda_events=\"" + epda_events_ids + "\"" : "")
                + (!epda_gamma_ids.isEmpty() ? " epda_gamma=\"" + epda_gamma_ids + "\"" : "")
                + (!epda_delta_ids.isEmpty() ? " epda_delta=\"" + epda_delta_ids + "\"" : "")
                + " epda_initial=\"" + generated_ids.get(o.get_epda_initial()) + "\""
                + " epda_box=\"" + generated_ids.get(o.get_epda_box()) + "\""
                + (!epda_marking_ids.isEmpty() ? " epda_marking=\"" + epda_marking_ids + "\"" : "")
                + "/>" + System.lineSeparator());
    }

    private void export__cfg_step_label(BufferedWriter bufferedWriter, cfg_step_label o) throws IOException {
        String id = get_id("cfg_step_label", o);
        if (!generated_ids.containsKey(o.get_prod_lhs()))
            export__object(bufferedWriter, o.get_prod_lhs());
        String prod_rhs_ids = "";
        for (Object element : o.get_prod_rhs()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            prod_rhs_ids += " " + generated_ids.get(element);
        }
        prod_rhs_ids = prod_rhs_ids.trim();

        bufferedWriter.write(depth(1) + "<cfg_step_label id=\"" + id + "\""
                + " prod_lhs=\"" + generated_ids.get(o.get_prod_lhs()) + "\""
                + (!prod_rhs_ids.isEmpty() ? " prod_rhs=\"" + prod_rhs_ids + "\"" : "")
                + "/>" + System.lineSeparator());
    }

    private void export__cfg(BufferedWriter bufferedWriter, cfg o) throws IOException {
        String id = get_id("cfg", o);

        String cfg_nonterminals_ids = "";
        for (Object element : o.get_cfg_nonterminals()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            cfg_nonterminals_ids += " " + generated_ids.get(element);
        }
        cfg_nonterminals_ids = cfg_nonterminals_ids.trim();

        String cfg_events_ids = "";
        for (Object element : o.get_cfg_events()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            cfg_events_ids += " " + generated_ids.get(element);
        }
        cfg_events_ids = cfg_events_ids.trim();

        if (!generated_ids.containsKey(o.get_cfg_initial()))
            export__object(bufferedWriter, o.get_cfg_initial());

        String cfg_step_labels_ids = "";
        for (Object element : o.get_cfg_step_labels()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            cfg_step_labels_ids += " " + generated_ids.get(element);
        }
        cfg_step_labels_ids = cfg_step_labels_ids.trim();

        bufferedWriter.write(depth(1) + "<cfg id=\"" + id + "\""
                + (!cfg_nonterminals_ids.isEmpty() ? " cfg_nonterminals=\"" + cfg_nonterminals_ids + "\"" : "")
                + (!cfg_events_ids.isEmpty() ? " cfg_events=\"" + cfg_events_ids + "\"" : "")
                + " cfg_initial=\"" + generated_ids.get(o.get_cfg_initial()) + "\""
                + (!cfg_step_labels_ids.isEmpty() ? " cfg_step_labels=\"" + cfg_step_labels_ids + "\"" : "")
                + "/>" + System.lineSeparator());
    }

    private void export__parser_step_label(BufferedWriter bufferedWriter, parser_step_label o) throws IOException {
        String id = get_id("parser_step_label", o);
        String rule_lpop_ids = "";
        for (Object element : o.get_rule_lpop()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            rule_lpop_ids += " " + generated_ids.get(element);
        }
        rule_lpop_ids = rule_lpop_ids.trim();

        String rule_rpop_ids = "";
        for (Object element : o.get_rule_rpop()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            rule_rpop_ids += " " + generated_ids.get(element);
        }
        rule_rpop_ids = rule_rpop_ids.trim();

        String rule_lpush_ids = "";
        for (Object element : o.get_rule_lpush()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            rule_lpush_ids += " " + generated_ids.get(element);
        }
        rule_lpush_ids = rule_lpush_ids.trim();

        String rule_rpush_ids = "";
        for (Object element : o.get_rule_rpush()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            rule_rpush_ids += " " + generated_ids.get(element);
        }
        rule_rpush_ids = rule_rpush_ids.trim();

        bufferedWriter.write(depth(1) + "<parser_step_label id=\"" + id + "\""
                + (!rule_lpop_ids.isEmpty() ? " rule_lpop=\"" + rule_lpop_ids + "\"" : "")
                + (!rule_rpop_ids.isEmpty() ? " rule_rpop=\"" + rule_rpop_ids + "\"" : "")
                + (!rule_lpush_ids.isEmpty() ? " rule_lpush=\"" + rule_lpush_ids + "\"" : "")
                + (!rule_rpush_ids.isEmpty() ? " rule_rpush=\"" + rule_rpush_ids + "\"" : "")
                + "/>" + System.lineSeparator());
    }

    private void export__parser(BufferedWriter bufferedWriter, parser o) throws IOException {
        String id = get_id("parser", o);

        String parser_nonterms_ids = "";
        for (Object element : o.get_parser_nonterms()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            parser_nonterms_ids += " " + generated_ids.get(element);
        }
        parser_nonterms_ids = parser_nonterms_ids.trim();

        String parser_events_ids = "";
        for (Object element : o.get_parser_events()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            parser_events_ids += " " + generated_ids.get(element);
        }
        parser_events_ids = parser_events_ids.trim();

        if (!generated_ids.containsKey(o.get_parser_initial()))
            export__object(bufferedWriter, o.get_parser_initial());

        String parser_marking_ids = "";
        for (Object element : o.get_parser_marking()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            parser_marking_ids += " " + generated_ids.get(element);
        }
        parser_marking_ids = parser_marking_ids.trim();

        String parser_step_labels_ids = "";
        for (Object element : o.get_parser_step_labels()) {
            if (!generated_ids.containsKey(element))
                export__object(bufferedWriter, element);
            parser_step_labels_ids += " " + generated_ids.get(element);
        }
        parser_step_labels_ids = parser_step_labels_ids.trim();

        if (!generated_ids.containsKey(o.get_parser_bottom()))
            export__object(bufferedWriter, o.get_parser_bottom());

        String parser_marker_ids = "";
        for (Object key : o.get_parser_marker().keySet()) {
            Object value = o.get_parser_marker().get(key);
            if (!generated_ids.containsKey(key))
                export__object(bufferedWriter, key);
            parser_marker_ids += " " + generated_ids.get(key);
            if (!generated_ids.containsKey(value))
                export__object(bufferedWriter, value);
            parser_marker_ids += " " + generated_ids.get(value);
        }
        parser_marker_ids = parser_marker_ids.trim();

        bufferedWriter.write(depth(1) + "<parser id=\"" + id + "\""
                + (!parser_nonterms_ids.isEmpty() ? " parser_nonterms=\"" + parser_nonterms_ids + "\"" : "")
                + (!parser_events_ids.isEmpty() ? " parser_events=\"" + parser_events_ids + "\"" : "")
                + " parser_initial=\"" + generated_ids.get(o.get_parser_initial()) + "\""
                + (!parser_marking_ids.isEmpty() ? " parser_marking=\"" + parser_marking_ids + "\"" : "")
                + (!parser_step_labels_ids.isEmpty() ? " parser_step_labels=\"" + parser_step_labels_ids + "\"" : "")
                + " parser_bottom=\"" + generated_ids.get(o.get_parser_bottom()) + "\""
                + (!parser_marker_ids.isEmpty() ? " parser_marker=\"" + parser_marker_ids + "\"" : "")
                + "/>" + System.lineSeparator());
    }

}
