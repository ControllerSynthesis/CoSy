package unit_tests;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_CFG_CFG;
import datatypes.formalisms.language_check.Compare_EPDA_CFG;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2.T09_SDPDA_TO_CFG_OPT2;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_07_SDPDA_TO_LR1_STD;

import java.util.HashMap;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ADDITIONAL_OPERATIONS__T09_SDPDA_TO_CFG_OPT2__3 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/ADDITIONAL_OPERATIONS__T09_SDPDA_TO_CFG_OPT2__3.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("input");
            assert (input.is_valid(true));

            cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                    output = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                    T09_SDPDA_TO_CFG_OPT2.F_SDPDA_TO_CFG_OPT2(input, new HashMap<>())).the();

            cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                    output_STD = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                    T03_06_07_SDPDA_TO_LR1_STD.F_SDPDA_TO_LR1_STD(input)).the();

            for (cfg_step_label<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>> elem : output_STD.get_cfg_step_labels()) {
                if (!output.get_cfg_step_labels().contains(elem))
                    System.out.print("MISSING: ");
                else
                    System.out.print("       : ");
                System.out.println(elem);
            }


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

            assert (output.get_cfg_nonterminals().size() == 11);
            assert (output.get_cfg_step_labels().size() == 14);

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
            assert (output2.get_cfg_nonterminals().size() == 9);
            assert (output2.get_cfg_step_labels().size() == 12);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}