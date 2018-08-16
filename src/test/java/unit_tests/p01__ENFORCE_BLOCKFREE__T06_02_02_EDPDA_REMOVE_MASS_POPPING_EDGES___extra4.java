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
import operations.p01__ENFORCE_BLOCKFREE.T03_03_EPDA_APPROXIMATE_ACCESSIBLE;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___extra4 {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES___extra4.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = ((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input"));
            assert (input.is_valid(true));

            if (Config.instance.getF_EPDA_AA__before_RNPE() >= 0)
                input = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(input, Config.instance.getF_EPDA_AA__before_RNPE());
            epda<string_symbol, string_symbol, string_symbol> input_RNPE = T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES.F_EDPDA_RNPE(input);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input,
                    input_RNPE,
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
                    output = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2.F_EDPDA_RMPOE__ALTERNATIVE(input_RNPE);
            //assert (output.is_valid(true));

            Config.instance.output(input_RNPE);
            Config.instance.output(output);

            assert Comparison_equal.compare_equal_with_empty_check(Compare_EPDA_EPDA.compare_equal(
                    input_RNPE,
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

            assert (output.get_epda_states().size() == 2);
            assert (output.get_epda_delta().size() == 8);


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}