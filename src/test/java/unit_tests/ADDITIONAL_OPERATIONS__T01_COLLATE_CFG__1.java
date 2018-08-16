package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ADDITIONAL_OPERATIONS__T01_COLLATE_CFG__1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/ADDITIONAL_OPERATIONS__T01_COLLATE_CFG__1.xml");

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    cfg = (cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("cfg");

            try {
                cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        result = T01_COLLATE_CFG.COLLATE_CFG(cfg, 0);
            } catch (ClassCastException e) {
                // the grammar produces no words!
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}