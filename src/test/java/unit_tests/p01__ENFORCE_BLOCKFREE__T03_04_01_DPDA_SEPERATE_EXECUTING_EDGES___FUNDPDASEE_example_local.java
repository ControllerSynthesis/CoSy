package unit_tests;

import datatypes.basic_datatypes_of_algorithm.state_or_edge__abstract;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES___FUNDPDASEE_example_local {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES___FUNDPDASEE_example_local.xml");

            Config.instance.T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES__create_only_states_used_in_edges = true;
            epda<string_symbol, string_symbol, string_symbol>
                    input = (epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid(true));


            epda<state_or_edge__abstract<string_symbol, string_symbol, string_symbol>, string_symbol, string_symbol>
                    output = T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE(input, null);

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

            assert (output.is_valid(true));
            assert (output.get_epda_marking().size() == 1);
            assert (output.get_epda_states().size() == 6);
            assert (output.get_epda_delta().size() == 5);
            System.out.println(output);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}