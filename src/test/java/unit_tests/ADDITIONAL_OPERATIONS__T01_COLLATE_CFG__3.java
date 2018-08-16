package unit_tests;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_05_CFG_TRIM;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class ADDITIONAL_OPERATIONS__T01_COLLATE_CFG__3 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/ADDITIONAL_OPERATIONS__T01_COLLATE_CFG__3.xml");

            cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    cfg = (cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("cfg");

            option__abstract<cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>> cfg_trim = T03_06_05_CFG_TRIM.F_CFG_TRIM(cfg);

            if (cfg_trim instanceof option__Some) {
                System.out.println(cfg.get_cfg_step_labels().size());
                System.out.println(((option__Some<cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) cfg_trim).the().get_cfg_step_labels().size());
                cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        result = T01_COLLATE_CFG.COLLATE_CFG(((option__Some<cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) cfg_trim).the(), 0);
                System.out.println(result);
            } else {
                System.out.println("EMPTY");
            }

            // TODO make assertions
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}