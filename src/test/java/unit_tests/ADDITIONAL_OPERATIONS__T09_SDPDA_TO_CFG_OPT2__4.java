package unit_tests;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_CFG;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.ADDITIONAL_OPERATIONS.T10_SDPDA_TO_LR_OPT2;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_07_SDPDA_TO_LR1_STD;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_08_SDPDA_TO_LR1_OPT;

import java.util.HashMap;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ADDITIONAL_OPERATIONS__T09_SDPDA_TO_CFG_OPT2__4 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/ADDITIONAL_OPERATIONS__T09_SDPDA_TO_CFG_OPT2__4.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    input = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>)
                    reader.get_parsed_element("input");
            assert (input.is_valid(true));

            double duration1 = 0;
            double duration2 = 0;
            double duration3 = 0;
            for (int i = 0; i < 10; i++) {
                double time1 = System.currentTimeMillis();
                cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                        output_F_SDPDA_TO_LR_OPT2 = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                        T10_SDPDA_TO_LR_OPT2.F_SDPDA_TO_LR_OPT2(input, new HashMap<>())).the();
                double time2 = System.currentTimeMillis();
                cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                        output_F_SDPDA_TO_LR1_STD = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                        T03_06_07_SDPDA_TO_LR1_STD.F_SDPDA_TO_LR1_STD(input)).the();
                double time3 = System.currentTimeMillis();
                cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                        output_F_SDPDA_TO_LR1_OPT = ((option__Some<cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>>)
                        T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(
                                input,
                                T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(input),
                                1)).the();
                double time4 = System.currentTimeMillis();

                assert (output_F_SDPDA_TO_LR_OPT2.is_valid());
                assert (output_F_SDPDA_TO_LR1_STD.is_valid());
                assert (output_F_SDPDA_TO_LR1_OPT.is_valid());

                assert (output_F_SDPDA_TO_LR_OPT2.equals(output_F_SDPDA_TO_LR1_STD));
                assert (output_F_SDPDA_TO_LR_OPT2.equals(output_F_SDPDA_TO_LR1_OPT));

                System.out.println(output_F_SDPDA_TO_LR_OPT2.get_cfg_nonterminals().size() + " :output_F_SDPDA_TO_LR_OPT2: " + output_F_SDPDA_TO_LR_OPT2.get_cfg_step_labels().size());
                assert (output_F_SDPDA_TO_LR_OPT2.get_cfg_nonterminals().size() == 17);
                assert (output_F_SDPDA_TO_LR_OPT2.get_cfg_step_labels().size() == 18);

                cfg<l2_l3_nonterminals__abstract<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>, symbol__abstract<string_symbol>>
                        output_T01_COLLATE_CFG = T01_COLLATE_CFG.COLLATE_CFG(output_F_SDPDA_TO_LR_OPT2, 0);
                assert (output_T01_COLLATE_CFG.is_valid());
                System.out.println(output_T01_COLLATE_CFG.get_cfg_nonterminals().size() + " :output_T01_COLLATE_CFG: " + output_T01_COLLATE_CFG.get_cfg_step_labels().size());
                assert (output_T01_COLLATE_CFG.get_cfg_nonterminals().size() == 13);
                assert (output_T01_COLLATE_CFG.get_cfg_step_labels().size() == 14);

                assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_CFG.compare_equal(
                        input,
                        output_T01_COLLATE_CFG,
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

                duration1 += (time2 - time1);
                duration2 += (time3 - time2);
                duration3 += (time4 - time3);
                System.out.println(i + " / " +
                        duration1 + "(" + (duration2 / duration1) + ":" + (duration3 / duration1) + ")" + " / " +
                        duration2 + "(" + (duration1 / duration2) + ":" + (duration3 / duration2) + ")" + " / " +
                        duration3 + "(" + (duration1 / duration3) + ":" + (duration2 / duration3) + ")");

            }


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}