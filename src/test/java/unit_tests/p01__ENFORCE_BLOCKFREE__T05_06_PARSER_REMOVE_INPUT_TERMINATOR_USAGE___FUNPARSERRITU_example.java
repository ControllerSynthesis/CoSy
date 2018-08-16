package unit_tests;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_CFG_PARSER;
import datatypes.formalisms.language_check.Compare_PARSER_PARSER;
import datatypes.formalisms.language_check.Comparison_equal;
import datatypes.formalisms.parser;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE___FUNPARSERRITU_example {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T05_04_LR_MACHINE___FUNLRMACHINE_example2.xml");

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("input");
            assert (input.is_valid());


            symbol__abstract<string_symbol>
                    Do = T01_FRESH.F_FRESH(input.get_cfg_events());
            symbol__abstract<string_symbol>
                    Sp = T01_FRESH.F_FRESH(input.get_cfg_nonterminals());

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input_augmented = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(input, Sp, Do);
            assert (input_augmented.is_valid());

            epda<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, two_elements__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, General_Integer>
                    machine = T05_04_LR_MACHINE.F_LR_MACHINE(input_augmented, 1);

            assert (machine.get_epda_marking().isEmpty());
            assert (machine.get_epda_delta().size() == 12);
            assert (machine.get_epda_states().size() == 12);
            assert (machine.get_epda_gamma().size() == 1);
            assert (machine.get_epda_events().size() == 6);

            int sum = 0;
            for (General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> x : machine.get_epda_states())
                sum += x.get_value().size();
            assert (sum == 35);

            parser<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>>>
                    output_parser = T05_05_LR_PARSER.F_LR_PARSER(input, input_augmented, machine, Do);

            assert (output_parser.get_parser_step_labels().size() == 21);


            assert Comparison_equal.compare_equal_with_empty_check(Compare_CFG_PARSER.compare_equal(
                    input,
                    output_parser,
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

            parser<General_Set<cfg_item<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>>>
                    output_parser_RITU = T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE.F_PARSER_RITU(output_parser);

            assert (output_parser_RITU.get_parser_step_labels().size() == 11);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_PARSER_PARSER.compare_equal(
                    output_parser,
                    output_parser_RITU,
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


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}