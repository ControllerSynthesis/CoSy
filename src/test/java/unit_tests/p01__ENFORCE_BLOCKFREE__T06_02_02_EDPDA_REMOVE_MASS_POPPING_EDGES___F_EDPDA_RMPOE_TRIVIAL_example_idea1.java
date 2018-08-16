package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___F_EDPDA_RMPOE_TRIVIAL_example_idea1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___FUNEDPDARMPOE_example_idea1.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = ((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input"));
            assert (input.is_valid(true));
            Config.instance.setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(-1);
            epda<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, string_symbol, string_symbol>
                    output = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE_TRIVIAL(input);
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