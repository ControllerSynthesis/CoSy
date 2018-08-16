package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES___FUNEDPDARNPE_example_local {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES___FUNEDPDARNPE_example_local.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("input");
            assert (input.is_valid(true));

            Config.instance.setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE(1);

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    output = T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES.F_EDPDA_RNPE(input);
            assert (output.is_valid(true));

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input,
                    output,
                    false,
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

            assert (output.get_epda_states().size() == 5);
            assert (output.get_epda_delta().size() == 4);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}