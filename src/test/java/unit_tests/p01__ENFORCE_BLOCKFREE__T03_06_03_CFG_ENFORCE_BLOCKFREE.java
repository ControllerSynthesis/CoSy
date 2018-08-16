package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_03_CFG_ENFORCE_BLOCKFREE;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_03_CFG_ENFORCE_BLOCKFREE.xml");

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    cfg = (cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("cfg");

            T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(cfg);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}
