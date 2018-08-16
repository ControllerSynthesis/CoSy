package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.language_check.Comparison_equal;
import import_export.XML_reader;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___extra3 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___extra3.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = ((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input"));
            assert (input.is_valid(true));


            Config.instance.setAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(-1);

            //input = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(input, 10);
            //input = T02_REMOVE_UNUSED_STATE_STACK_EPDA.REMOVE_UNUSED_STATE_STACK_EPDA(input);
            //input = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(input);


            //XML_writer writer = new XML_writer("test_cases/output_test", "epda_middle.xml", "../../main/resources/cosy.xsd");
            //writer.add_to_be_exported(input);
            //writer.export();


            //epda<tuple3<symbol__abstract<string_symbol>, General_List<symbol__abstract<string_symbol>>, option__abstract<symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    output = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE(input);

            epda<two_elements__abstract<string_symbol, tuple2<string_symbol, General_List<string_symbol>>>, string_symbol, string_symbol>
                    output = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE_TRIVIAL(input);
            //assert (output.is_valid(true));

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input,
                    output,
                    false,
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

//            Set<List<symbol__abstract<string_symbol>>> output_get_marked_lang_small = output.get_marked_lang(20);
//            Set<List<symbol__abstract<string_symbol>>> input_get_marked_lang_large = input.get_marked_lang(20);
//            assert (input_get_marked_lang_large.containsAll(output_get_marked_lang_small));
//
//            Set<List<symbol__abstract<string_symbol>>> input_get_marked_lang_small = input.get_marked_lang(20);
//            Set<List<symbol__abstract<string_symbol>>> output_get_marked_lang_large = output.get_marked_lang(20);
//            assert (output_get_marked_lang_large.containsAll(input_get_marked_lang_small));
//
//            System.out.println("input_get_marked_lang_small=" + input_get_marked_lang_small.size());
//            System.out.println("output_get_marked_lang_small=" + output_get_marked_lang_small.size());
//
//            assert (output.get_epda_states().size() == 10);
//            assert (output.get_epda_delta().size() == 10);

            assert (output.get_epda_states().size() == 5);
            assert (output.get_epda_delta().size() == 5);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}