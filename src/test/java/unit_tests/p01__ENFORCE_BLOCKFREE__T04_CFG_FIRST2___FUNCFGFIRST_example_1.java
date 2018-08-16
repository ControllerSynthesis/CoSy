package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T04_CFG_FIRST2;

import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T04_CFG_FIRST2___FUNCFGFIRST_example_1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T04_CFG_FIRST___FUNCFGFIRST_example_1.xml");

            cfg<string_symbol, string_symbol>
                    input = (cfg<string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid());

            two_elements__teA<string_symbol, string_symbol> nonterminal_S = new two_elements__teA<>((string_symbol) reader.get_parsed_element("nonterminal_S"));
            two_elements__teA<string_symbol, string_symbol> nonterminal_A = new two_elements__teA<>((string_symbol) reader.get_parsed_element("nonterminal_A"));
            two_elements__teA<string_symbol, string_symbol> nonterminal_B = new two_elements__teA<>((string_symbol) reader.get_parsed_element("nonterminal_B"));
            two_elements__teB<string_symbol, string_symbol> event_a = new two_elements__teB<>((string_symbol) reader.get_parsed_element("event_a"));
            two_elements__teB<string_symbol, string_symbol> event_b = new two_elements__teB<>((string_symbol) reader.get_parsed_element("event_b"));
            two_elements__teB<string_symbol, string_symbol> event_c = new two_elements__teB<>((string_symbol) reader.get_parsed_element("event_c"));
            Set<List<string_symbol>> output;

            output = T04_CFG_FIRST2.F_CFG_FIRST(input, 1, General_List.list_by_enumeration(nonterminal_S));
            System.out.println(output);
            assert (output.size() == 2);

            output = T04_CFG_FIRST2.F_CFG_FIRST(input, 1, General_List.list_by_enumeration(nonterminal_A));
            System.out.println(output);
            assert (output.size() == 1);

            output = T04_CFG_FIRST2.F_CFG_FIRST(input, 1, General_List.list_by_enumeration(nonterminal_S, nonterminal_A, event_b));
            System.out.println(output);
            assert (output.size() == 3);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}