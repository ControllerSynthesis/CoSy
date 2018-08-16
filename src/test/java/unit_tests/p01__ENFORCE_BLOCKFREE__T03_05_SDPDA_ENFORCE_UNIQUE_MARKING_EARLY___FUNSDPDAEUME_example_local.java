package unit_tests;

import datatypes.basic_datatypes_of_algorithm.state_or_state__abstract;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY___FUNSDPDAEUME_example_local {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY___FUNSDPDAEUME_example_local.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("input");
            assert (input.is_valid(true));

            Config.instance.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY__create_only_states_used_in_edges = true;

            epda<state_or_state__abstract<symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    output = T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.F_SDPDA_EUME(input);

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
            System.out.println(output);

            if (Config.instance.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY__create_only_states_used_in_edges) {
                assert (output.get_epda_marking().size() == 4);
                assert (output.get_epda_states().size() == 7);
                assert (output.get_epda_delta().size() == 14);
            } else {
                assert (output.get_epda_marking().size() == 5);
                assert (output.get_epda_states().size() == 12);
                assert (output.get_epda_delta().size() == 24);
            }


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}