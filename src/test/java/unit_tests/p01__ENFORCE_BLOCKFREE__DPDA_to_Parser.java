package unit_tests;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.*;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT;
import operations.ADDITIONAL_OPERATIONS.T10_SDPDA_TO_LR_OPT2;
import operations.p01__ENFORCE_BLOCKFREE.*;
import operations.p05__CFG_CONSTRUCT_CONTROLLER.Algorithm_Definition__CFG_Construct_Controller;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__DPDA_to_Parser {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/dpda_with_no_wcet.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_with_no_wcet = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_with_no_wcet"));

            symbol__abstract<string_symbol> event_a = new symbol__cons_symbol_base<string_symbol>((string_symbol) reader.get_parsed_element("event_a"));
            symbol__abstract<string_symbol> event_b = new symbol__cons_symbol_base<string_symbol>((string_symbol) reader.get_parsed_element("event_b"));
            symbol__abstract<string_symbol> event_c = new symbol__cons_symbol_base<string_symbol>((string_symbol) reader.get_parsed_element("event_c"));
            symbol__abstract<string_symbol> event_d = new symbol__cons_symbol_base<string_symbol>((string_symbol) reader.get_parsed_element("event_d"));

            if (!dpda_with_no_wcet.is_valid(true))
                throw new RuntimeException();

            epda<state_or_edge_nat__abstract<
                    state_or_edge__abstract<
                            state_or_edge__abstract<
                                    state_or_edge__abstract<
                                            symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>,
                            symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>,
                    symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>,
                    symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_with_no_wcet__SDPDA = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(dpda_with_no_wcet);
            option__abstract<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>
                    dpda_with_no_wcet__LRCFG_OPT = T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(dpda_with_no_wcet__SDPDA, null);
            if (dpda_with_no_wcet__LRCFG_OPT instanceof option__None)
                throw new RuntimeException();
            cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                    dpda_with_no_wcet__LRCFG = ((option__Some<cfg<l2_l3_nonterminals__abstract<state_or_edge_nat__abstract<state_or_edge__abstract<state_or_edge__abstract<state_or_edge__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>) dpda_with_no_wcet__LRCFG_OPT).the();

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_with_no_wcet__LRCFG_TC = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(dpda_with_no_wcet__LRCFG);


            if (!dpda_with_no_wcet__LRCFG_TC.is_valid())
                throw new RuntimeException();

            Algorithm_Definition__CFG_Construct_Controller.PATH = "out/test/cosy/output_test/dpda_with_no_wcet/";
            Algorithm_Definition__CFG_Construct_Controller.print_grammarX(dpda_with_no_wcet__LRCFG_TC, null, null, "_lr_cfg.dot");


            {
                symbol__abstract<string_symbol>
                        Do = T01_FRESH.F_FRESH(dpda_with_no_wcet__LRCFG_TC.get_cfg_events());
                symbol__abstract<string_symbol>
                        Sp = T01_FRESH.F_FRESH(dpda_with_no_wcet__LRCFG_TC.get_cfg_nonterminals());
                cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        Gp = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(dpda_with_no_wcet__LRCFG_TC, Sp, Do);

                epda<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, two_elements__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, General_Integer>
                        M = T05_04_LR_MACHINE.F_LR_MACHINE(Gp, 1);


                Map<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, Integer> naming = new HashMap<>();

                Algorithm_Definition__CFG_Construct_Controller.print_machineX(M, null, naming, false, null, "_LRMACHINE.dot");


                parser<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>>>
                        P0 = T05_05_LR_PARSER.F_LR_PARSER(dpda_with_no_wcet__LRCFG_TC, Gp, M, Do);

                if (!P0.is_valid())
                    throw new RuntimeException();
                if (!P0.is_deterministic())
                    throw new RuntimeException();

                Map<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>> renaming = new HashMap<>();
                for (Map.Entry<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, Integer> entry : naming.entrySet()) {
                    renaming.put(entry.getKey(), new symbol__cons_symbol_nat<>(entry.getValue()));
                }
                parser<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, option__abstract<General_Integer>> P1 = T02_02_PARSER_TYPE_CONVERSION.F_PARSER_TC__parser(P0, renaming);
                int longest_lpop = 0;
                for (parser_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> elem : P1.get_parser_step_labels())
                    longest_lpop = Math.max(longest_lpop, elem.get_rule_lpop().size());
                int longest_lpush = 0;
                for (parser_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> elem : P1.get_parser_step_labels())
                    longest_lpush = Math.max(longest_lpush, elem.get_rule_lpush().size());
                for (parser_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>> elem : P1.get_parser_step_labels()) {
                    for (int i = 0; i < longest_lpop - elem.get_rule_lpop().size(); i++)
                        System.out.print(",");
                    System.out.print(elem.get_rule_lpop() + "*");
                    System.out.print(elem.get_rule_rpop() + "*");
                    for (int i = 0; i < longest_lpush - elem.get_rule_lpush().size(); i++)
                        System.out.print(",");
                    System.out.print(elem.get_rule_lpush() + "*");
                    System.out.print(elem.get_rule_rpush());
                    System.out.println();
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
