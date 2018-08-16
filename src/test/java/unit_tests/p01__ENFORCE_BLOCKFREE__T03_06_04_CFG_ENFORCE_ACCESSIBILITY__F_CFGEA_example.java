package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_04_CFG_ENFORCE_ACCESSIBILITY;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_06_04_CFG_ENFORCE_ACCESSIBILITY__F_CFGEA_example {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_04_CFG_ENFORCE_ACCESSIBILITY__F_CFGEA_example.xml");

            cfg<string_symbol, string_symbol>
                    input = (cfg<string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid());

            cfg<string_symbol, string_symbol>
                    output = T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(input);
            assert (output.is_valid());
            System.out.println(output);
            assert (output.get_cfg_nonterminals().size() == 3);
            assert (output.get_cfg_step_labels().size() == 8);
            assert (output.get_cfg_events().size() == 2);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}