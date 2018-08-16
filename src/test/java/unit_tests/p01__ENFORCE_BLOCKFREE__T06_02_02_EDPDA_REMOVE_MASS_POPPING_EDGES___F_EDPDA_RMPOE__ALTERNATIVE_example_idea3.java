package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___F_EDPDA_RMPOE__ALTERNATIVE_example_idea3 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___FUNEDPDARMPOE_example_idea3.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = ((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input"));
            assert (input.is_valid(true));
            Config.instance.setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(10);
            epda<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, string_symbol, string_symbol>
                    output = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2.F_EDPDA_RMPOE__ALTERNATIVE(input);

            for (epda_step_label<string_symbol, string_symbol, string_symbol> x : input.get_epda_delta())
                System.out.println(x);
            System.out.println("............");
            for (epda_step_label<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, string_symbol, string_symbol> x : output.get_epda_delta())
                System.out.println(x);

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
            assert (output.get_epda_states().size() == 6);
            assert (output.get_epda_delta().size() == 7);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}