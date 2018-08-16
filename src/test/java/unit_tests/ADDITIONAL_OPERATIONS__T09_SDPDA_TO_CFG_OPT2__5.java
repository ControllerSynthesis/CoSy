package unit_tests;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_CFG;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.ADDITIONAL_OPERATIONS.T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT;
import operations.p01__ENFORCE_BLOCKFREE.T02_01_EPDA_TYPE_CONVERSION;
import operations.p01__ENFORCE_BLOCKFREE.T03_04_05_DPDA_TO_SDPDA;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_07_SDPDA_TO_LR1_STD;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_08_SDPDA_TO_LR1_OPT;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ADDITIONAL_OPERATIONS__T09_SDPDA_TO_CFG_OPT2__5 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/example_of_nontermination.xml");

            epda<symbol__abstract<string_symbol>, string_symbol, symbol__abstract<string_symbol>>
                    epda1 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.EPDA_WRAP_IN_SYMBOL_ABSTRACT((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("spec"));

            assert (epda1.is_valid(true));

            epda epda2 = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(epda1);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    epda1,
                    epda2,
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


            epda epda3 = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(epda2);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    epda2,
                    epda3,
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
            option__abstract<cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol>>
                    cfg1_STD = T03_06_07_SDPDA_TO_LR1_STD.F_SDPDA_TO_LR1_STD(epda3);
            option__abstract<cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol>>
                    cfg1_OPT = T03_06_08_SDPDA_TO_LR1_OPT.F_SDPDA_TO_LR1_OPT(epda3, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(epda3), 1);


            cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol> cfg2_STD = ((option__Some<cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol>>) cfg1_STD).the();
            cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol> cfg2_OPT = ((option__Some<cfg<l2_l3_nonterminals__abstract<string_symbol, string_symbol>, string_symbol>>) cfg1_OPT).the();

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_CFG.compare_equal(
                    epda3,
                    cfg2_STD,
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

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_CFG.compare_equal(
                    epda3,
                    cfg2_OPT,
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


            System.out.println(cfg1_STD);
            System.out.println(cfg2_STD);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}