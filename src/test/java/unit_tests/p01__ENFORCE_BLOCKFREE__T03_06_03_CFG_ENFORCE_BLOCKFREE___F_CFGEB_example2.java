package unit_tests;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_03_CFG_ENFORCE_BLOCKFREE;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE___F_CFGEB_example2 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE___F_CFGEB_example2.xml");

            cfg<string_symbol, string_symbol>
                    input = (cfg<string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid());
            assert (T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(input) instanceof option__None);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}