package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_01_EPDA_RESTRICT;

import java.util.HashSet;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_01_EPDA_RESTRICT___F_EPDAR_example_local {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_01_EPDA_RESTRICT___F_EPDAR_example_local.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = (epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input");
            input.print_epda_info("input", 20);
            assert (input.is_valid(false));


            Set<string_symbol> states = new HashSet<>(input.get_epda_states());
            string_symbol state_q3 = (string_symbol) reader.get_parsed_element("state_3");
            states.remove(state_q3);
            states.remove(reader.get_parsed_element("state_7"));
            states.remove(reader.get_parsed_element("state_9"));
            Set<epda_step_label<string_symbol, string_symbol, string_symbol>> edges = new HashSet<>(input.get_epda_delta());
            edges.remove(reader.get_parsed_element("edge_4"));
            edges.remove(reader.get_parsed_element("edge_8"));

            epda<string_symbol, string_symbol, string_symbol>
                    output = T03_01_EPDA_RESTRICT.F_EPDA_R(input, states, edges);
            output.print_epda_info("output", 20);
            assert (output.is_valid(false));
            assert (output.get_epda_marking().size() == 1);
            assert (output.get_epda_states().size() == 7);
            assert (output.get_epda_delta().size() == 7);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input,
                    output,
                    true,
                    0,
                    0,
                    0,
                    20,
                    100,
                    10000,
                    200,
                    20000,
                    true,
                    1000,
                    200000));


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}