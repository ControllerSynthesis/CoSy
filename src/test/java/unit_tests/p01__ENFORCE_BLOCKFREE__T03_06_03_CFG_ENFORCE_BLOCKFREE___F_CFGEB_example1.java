package unit_tests;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_03_CFG_ENFORCE_BLOCKFREE;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE___F_CFGEB_example1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE___F_CFGEB_example1.xml");

            cfg<string_symbol, string_symbol>
                    input = (cfg<string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid());

            cfg<string_symbol, string_symbol>
                    output = ((option__Some<cfg<string_symbol, string_symbol>>) T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(input)).the();
            assert (output.is_valid());
            assert (output.get_cfg_nonterminals().size() == 6);
            assert (output.get_cfg_step_labels().size() == 7);
            assert (output.get_cfg_events().size() == 3);
            System.out.println(output);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}