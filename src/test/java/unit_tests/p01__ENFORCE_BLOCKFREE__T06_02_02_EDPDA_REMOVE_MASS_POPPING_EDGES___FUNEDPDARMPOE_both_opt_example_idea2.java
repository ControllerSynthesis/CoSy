package unit_tests;

import datatypes.basic_datatypes_of_algorithm.*;
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
public class p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___FUNEDPDARMPOE_both_opt_example_idea2 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___FUNEDPDARMPOE_example_idea2.xml");
            epda<string_symbol, string_symbol, string_symbol>
                    input = (epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid(true));
            Config.instance.setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(10);
            epda<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, string_symbol, string_symbol>
                    output1 = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE_TRIVIAL(input);
            epda<tuple3<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, General_List<string_symbol>, option__abstract<string_symbol>>, string_symbol, string_symbol>
                    output2 = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE(output1);
            assert (output2.is_valid(true));
            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input,
                    output2,
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
            assert (output1.get_epda_states().size() == 5);
            assert (output1.get_epda_delta().size() == 6);
            assert (output2.get_epda_states().size() == 15);
            assert (output2.get_epda_delta().size() == 17);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}