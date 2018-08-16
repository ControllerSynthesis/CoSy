package import_export;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.EntityResolver2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class XML_reader {

    private static Logger log = LogManager.getLogger(XML_reader.class.getName());
    DocumentBuilderFactory factory;

    private Map<String, General> parsed_elements = new HashMap<>();
    private Set<String> parsed_files = new HashSet<>();

    public XML_reader() throws ParserConfigurationException {
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setXIncludeAware(true);
        factory.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", false);
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
    }

    public Object get_parsed_element(String key) {
        return parsed_elements.get(key);
    }

    public void parse(String file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new EntityResolver2() {

            Set<String> done = new HashSet<>();

            @Override
            public InputSource getExternalSubset(String s, String s1) throws SAXException, IOException {
                throw new RuntimeException("EntityResolver2" + " " + "getExternalSubset" + " " + s + " / " + s1);
            }

            @Override
            public InputSource resolveEntity(String string, String string1, String string2, String string3) throws SAXException, IOException {
                java.io.File newfile = new java.io.File(string2.substring(5, string2.lastIndexOf("/") + 1) + string3);
                String newfilename = newfile.getCanonicalPath();

                if (log.isTraceEnabled()) log.trace("newfilename = " + newfilename);
                if (done.contains(newfilename)) {
                    InputSource is = new InputSource(new StringReader("<root />"));
                    is.setSystemId(newfilename);
                    return is;
                }
                if (log.isTraceEnabled()) log.trace("adding = " + newfilename);
                done.add(newfilename);
                final InputSource is = new InputSource();
                is.setSystemId(newfilename);
                is.setByteStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(newfilename));
                return is;
            }

            @Override
            public InputSource resolveEntity(String s, String s1) throws SAXException, IOException {
                throw new RuntimeException("EntityResolver2" + " " + "resolveEntity" + " " + s + " / " + s1);
            }
        });

        builder.setErrorHandler(new SimpleErrorHandler());
        Document document = builder.parse(new InputSource(file));
        document.normalizeDocument();

        NodeList nl = document.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals("root"))
                parse__root(nl.item(i));
        }
    }

    private List<General> ids_to_objects(String ids) {
        List<General> result = new ArrayList<>();
        for (String s : ids == null ? new String[0] : ids.split(" "))
            result.add(parsed_elements.get(s));
        return result;
    }

    private void parse__root(Node item) {
        NodeList nl = item.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals("root"))
                parse__root(nl.item(i));
            else if (nl.item(i).getNodeName().equals("sigma_uncontrollable"))
                parse__sigma_uncontrollable(nl.item(i));
            else if (nl.item(i).getNodeName().equals("test_sequences"))
                parse__test_sequences(nl.item(i));
            else if (nl.item(i).getNodeName().equals("string_symbol"))
                parse__string_symbol(nl.item(i));
            else if (nl.item(i).getNodeName().equals("l2_l3_nonterminals__cons_l2"))
                parse__l2_l3_nonterminals__cons_l2(nl.item(i));
            else if (nl.item(i).getNodeName().equals("l2_l3_nonterminals__cons_l3"))
                parse__l2_l3_nonterminals__cons_l3(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_and_stack_or_state__cons_state"))
                parse__state_and_stack_or_state__cons_state(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_and_stack_or_state__cons_state_and_stack"))
                parse__state_and_stack_or_state__cons_state_and_stack(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_edge__cons_state_or_edge_new"))
                parse__state_or_edge__cons_state_or_edge_new(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_edge__cons_state_or_edge_old"))
                parse__state_or_edge__cons_state_or_edge_old(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_edge_nat__cons_state_or_edge_nat_new"))
                parse__state_or_edge_nat__cons_state_or_edge_nat_new(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_edge_nat__cons_state_or_edge_nat_old"))
                parse__state_or_edge_nat__cons_state_or_edge_nat_old(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_state__cons_state_or_state_new"))
                parse__state_or_state__cons_state_or_state_new(nl.item(i));
            else if (nl.item(i).getNodeName().equals("state_or_state__cons_state_or_state_old"))
                parse__state_or_state__cons_state_or_state_old(nl.item(i));
            else if (nl.item(i).getNodeName().equals("symbol__cons_symbol_base"))
                parse__symbol__cons_symbol_base(nl.item(i));
            else if (nl.item(i).getNodeName().equals("symbol__cons_symbol_nat"))
                parse__symbol__cons_symbol_nat(nl.item(i));
            else if (nl.item(i).getNodeName().equals("tuple2"))
                parse__tuple2(nl.item(i));
            else if (nl.item(i).getNodeName().equals("tuple3"))
                parse__tuple3(nl.item(i));
            else if (nl.item(i).getNodeName().equals("two_elements__teA"))
                parse__two_elements__teA(nl.item(i));
            else if (nl.item(i).getNodeName().equals("two_elements__teB"))
                parse__two_elements__teB(nl.item(i));
            else if (nl.item(i).getNodeName().equals("epda_step_label"))
                parse__epda_step_label(nl.item(i));
            else if (nl.item(i).getNodeName().equals("epda"))
                parser__epda(nl.item(i));
            else if (nl.item(i).getNodeName().equals("cfg_step_label"))
                parse__cfg_step_label(nl.item(i));
            else if (nl.item(i).getNodeName().equals("cfg"))
                parse__cfg(nl.item(i));
            else if (nl.item(i).getNodeName().equals("parser_step_label"))
                parse__parser_step_label(nl.item(i));
            else if (nl.item(i).getNodeName().equals("parser"))
                parse__parser(nl.item(i));
        }
    }

    private void parse__test_sequences(Node item) {
        String id = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
        }

        List<General_Test_Sequence> result = new ArrayList<>();
        NodeList nl = item.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals("test_sequence"))
                result.add(parse__test_sequence(nl.item(i)));
        }

        parsed_elements.put(id, new General_List<>(result));

    }

    private General_Test_Sequence parse__test_sequence(Node item) {
        String id = null;
        String expected = null;
        List<General> sequence = new ArrayList<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("expected"))
                expected = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("sequence"))
                sequence.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
        }
        return new General_Test_Sequence(id, expected, new General_List(sequence));
    }

    private void parse__sigma_uncontrollable(Node item) {
        String id = null;
        List<General> elements = new ArrayList<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("elements"))
                elements.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
        }
        parsed_elements.put(id, new General_SigmaUC(new General_List(elements)));
    }

    @SuppressWarnings("unchecked")
    private void parse__parser(Node item) {
        String id = null;
        List<General> parser_nonterms = new ArrayList<>();
        List<General> parser_events = new ArrayList<>();
        General parser_initial = null;
        List<General> parser_marking = new ArrayList<>();
        List<General> parser_step_labels = new ArrayList<>();
        General parser_eoi = null;
        Map<General, General> parser_marker = new HashMap<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("parser_nonterms"))
                parser_nonterms.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("parser_events"))
                parser_events.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("parser_initial"))
                parser_initial = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("parser_marking"))
                parser_marking.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("parser_step_labels"))
                parser_step_labels.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("parser_eoi"))
                parser_eoi = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("parser_marker")) {
                List<General> elements = ids_to_objects(nnm.item(i).getNodeValue());
                for (int j = 0; j < elements.size(); j += 2)
                    parser_marker.put(elements.get(j), elements.get(j + 1));
            }
        }
        parsed_elements.put(id, new parser(parser_nonterms, parser_events, parser_initial, parser_marking, parser_step_labels, parser_marker, parser_eoi));
    }

    @SuppressWarnings("unchecked")
    private void parse__parser_step_label(Node item) {
        String id = null;
        List<Object> rule_lpop = new ArrayList<>();
        List<Object> rule_rpop = new ArrayList<>();
        List<Object> rule_lpush = new ArrayList<>();
        List<Object> rule_rpush = new ArrayList<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("rule_lpop"))
                rule_lpop.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("rule_rpop"))
                rule_rpop.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("rule_lpush"))
                rule_lpush.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("rule_rpush"))
                rule_rpush.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
        }
        parsed_elements.put(id, new parser_step_label(rule_lpop, rule_rpop, rule_lpush, rule_rpush));
    }

    @SuppressWarnings("unchecked")
    private void parse__cfg(Node item) {
        String id = null;
        List<Object> nonterminals = new ArrayList<>();
        List<Object> events = new ArrayList<>();
        Object initial = null;
        List<Object> step_labels = new ArrayList<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("cfg_nonterminals"))
                nonterminals.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("cfg_events"))
                events.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("cfg_step_labels"))
                step_labels.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("cfg_initial"))
                initial = parsed_elements.get(nnm.item(i).getNodeValue());
        }
        parsed_elements.put(id, new cfg(nonterminals, events, (General) initial, step_labels));
    }

    @SuppressWarnings("unchecked")
    private void parse__cfg_step_label(Node item) {
        String id = null;
        Object prod_lhs = null;
        List<Object> prod_rhs = new ArrayList<>();
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("prod_lhs"))
                prod_lhs = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("prod_rhs"))
                prod_rhs.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
        }
        parsed_elements.put(id, new cfg_step_label((General) prod_lhs, prod_rhs));
    }

    @SuppressWarnings("unchecked")
    private void parser__epda(Node item) {
        String id = null;
        List<General> epda_states = new ArrayList<>();
        List<General> epda_marking = new ArrayList<>();
        List<General> epda_gamma = new ArrayList<>();
        List<General> epda_events = new ArrayList<>();
        List<General> epda_delta = new ArrayList<>();
        General epda_initial = null;
        General epda_box = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("epda_states"))
                epda_states.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("epda_marking"))
                epda_marking.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("epda_gamma"))
                epda_gamma.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("epda_events"))
                epda_events.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("epda_delta"))
                epda_delta.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("epda_initial"))
                epda_initial = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("epda_eos"))
                epda_box = parsed_elements.get(nnm.item(i).getNodeValue());
        }
        parsed_elements.put(id, new epda(epda_states, epda_events, epda_gamma, epda_delta, epda_initial, epda_box, epda_marking));
    }

    @SuppressWarnings("unchecked")
    private void parse__epda_step_label(Node item) {
        String id = null;
        General edge_src = null;
        General edge_event = null;
        List<General> edge_pop = new ArrayList<>();
        List<General> edge_push = new ArrayList<>();
        General edge_trg = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("edge_src"))
                edge_src = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("edge_event"))
                edge_event = parsed_elements.get(nnm.item(i).getNodeValue());
            else if (nnm.item(i).getNodeName().equals("edge_pop"))
                edge_pop.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("edge_push"))
                edge_push.addAll(ids_to_objects(nnm.item(i).getNodeValue()));
            else if (nnm.item(i).getNodeName().equals("edge_trg"))
                edge_trg = parsed_elements.get(nnm.item(i).getNodeValue());
        }
        option__abstract edge_event_option = edge_event == null ? new option__None() : new option__Some(edge_event);
        parsed_elements.put(id, new epda_step_label(edge_src, edge_event_option, edge_pop, edge_push, edge_trg));
    }

    @SuppressWarnings("unchecked")
    private void parse__two_elements__teB(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new two_elements__teB(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__two_elements__teA(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new two_elements__teA(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__tuple3(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        String value3 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value3"))
                value3 = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new tuple3(parsed_elements.get(value1), parsed_elements.get(value2), parsed_elements.get(value3)));
    }

    @SuppressWarnings("unchecked")
    private void parse__tuple2(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
        }
        assert value2 != null;
        parsed_elements.put(id, new tuple2(parsed_elements.get(value1), new General_Integer(Integer.parseInt(value2))));
    }

    private void parse__symbol__cons_symbol_nat(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        assert value != null;
        parsed_elements.put(id, new symbol__cons_symbol_nat(Integer.parseInt(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__symbol__cons_symbol_base(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new symbol__cons_symbol_base(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_state__cons_state_or_state_old(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_or_state__cons_state_or_state_old(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_state__cons_state_or_state_new(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_or_state__cons_state_or_state_new(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_edge_nat__cons_state_or_edge_nat_old(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_or_edge_nat__cons_state_or_edge_nat_old(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_edge_nat__cons_state_or_edge_nat_new(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
        }
        assert value2 != null;
        parsed_elements.put(id, new state_or_edge_nat__cons_state_or_edge_nat_new((epda_step_label) parsed_elements.get(value1), Integer.parseInt(value2)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_edge__cons_state_or_edge_old(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_or_edge__cons_state_or_edge_old(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_or_edge__cons_state_or_edge_new(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_or_edge__cons_state_or_edge_new((epda_step_label) parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_and_stack_or_state__cons_state_and_stack(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_and_stack_or_state__cons_state_and_stack(parsed_elements.get(value1), parsed_elements.get(value2)));
    }

    @SuppressWarnings("unchecked")
    private void parse__state_and_stack_or_state__cons_state(Node item) {
        String id = null;
        String value = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value"))
                value = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new state_and_stack_or_state__cons_state(parsed_elements.get(value)));
    }

    @SuppressWarnings("unchecked")
    private void parse__l2_l3_nonterminals__cons_l3(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        String value3 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value3"))
                value3 = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new l2_l3_nonterminals__cons_l3(parsed_elements.get(value1), parsed_elements.get(value2), parsed_elements.get(value3)));
    }

    @SuppressWarnings("unchecked")
    private void parse__l2_l3_nonterminals__cons_l2(Node item) {
        String id = null;
        String value1 = null;
        String value2 = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value1"))
                value1 = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("value2"))
                value2 = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new l2_l3_nonterminals__cons_l2(parsed_elements.get(value1), parsed_elements.get(value2)));
    }

    private void parse__string_symbol(Node item) {
        String id = null;
        String string = null;
        NamedNodeMap nnm = item.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            if (nnm.item(i).getNodeName().equals("id"))
                id = nnm.item(i).getNodeValue();
            else if (nnm.item(i).getNodeName().equals("string"))
                string = nnm.item(i).getNodeValue();
        }
        parsed_elements.put(id, new string_symbol(string));
    }

    private static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            throw e;
        }

        public void error(SAXParseException e) throws SAXException {
            throw e;
        }

        public void fatalError(SAXParseException e) throws SAXException {
            throw e;
        }
    }


}
