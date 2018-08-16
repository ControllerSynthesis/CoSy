package unit_tests;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_CFG_CFG;
import datatypes.formalisms.language_check.Compare_EPDA_CFG;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_08_SDPDA_TO_LR1_OPT;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_06_08_SDPDA_TO_LR1_OPT___F_SDPDAtoCFG_example_SDPDA_nonempty_stack_marking1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_08_SDPDA_TO_LR1_OPT___F_SDPDAtoCFG_example_SDPDA_nonempty_stack_marking1.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("input");
            assert (input.is_valid(true));

            Config.instance.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY__create_only_states_used_in_edges = true;

            cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                    output = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                    T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(input, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(input), 1)).the();

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_CFG.compare_equal(
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
            assert (output.is_valid());
            System.out.println(output);

            assert (output.get_cfg_nonterminals().size() == 10);
            assert (output.get_cfg_step_labels().size() == 12);

            cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                    output2 = T01_COLLATE_CFG.COLLATE_CFG(output, 0);
            assert Comparison_equal.compare_equal_with_empty_check(Compare_CFG_CFG.compare_equal(
                    output,
                    output2,
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
            assert (output2.is_valid());
            System.out.println(output2);

            System.out.println(output2.get_cfg_nonterminals().size() + " :output2: " + output2.get_cfg_step_labels().size());
            assert (output2.get_cfg_nonterminals().size() == 7);
            assert (output2.get_cfg_step_labels().size() == 9);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}