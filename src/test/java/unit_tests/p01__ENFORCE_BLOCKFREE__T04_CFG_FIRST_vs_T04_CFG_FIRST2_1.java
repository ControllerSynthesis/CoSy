package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.cfg;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T06_CFG_WRAP_IN_SYMBOL_ABSTRACT;
import operations.p01__ENFORCE_BLOCKFREE.T04_CFG_FIRST;
import operations.p01__ENFORCE_BLOCKFREE.T04_CFG_FIRST2;

import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T04_CFG_FIRST_vs_T04_CFG_FIRST2_1 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T04_CFG_FIRST_vs_T04_CFG_FIRST2_1.xml");

            cfg<symbol__abstract<string_symbol>, string_symbol>
                    input = T06_CFG_WRAP_IN_SYMBOL_ABSTRACT.CFG_WRAP_IN_SYMBOL_ABSTRACT((cfg<string_symbol, string_symbol>) reader.get_parsed_element("input"));
            assert (input.is_valid());

            input = T01_COLLATE_CFG.COLLATE_CFG(input, 0);

            for (symbol__abstract<string_symbol> elem : input.get_cfg_nonterminals()) {
                Set<List<string_symbol>> output1 = T04_CFG_FIRST.F_CFG_FIRST(input, 1, General_List.list_by_enumeration(new two_elements__teA<>(elem)));
                Set<List<string_symbol>> output2 = T04_CFG_FIRST2.F_CFG_FIRST(input, 1, General_List.list_by_enumeration(new two_elements__teA<>(elem)));
                if (!output1.equals(output2)) {
                    System.out.println(elem);
                    assert (false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}