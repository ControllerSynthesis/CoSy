package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T05_01_EPDA_GOTO;

import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T05_01_EPDA_GOTO___EPDA_GOTO_example {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T05_01_EPDA_GOTO___EPDA_GOTO_example.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = (epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input");
            assert (input.is_valid(false));

            string_symbol event_a = (string_symbol) reader.get_parsed_element("event_a");
            string_symbol event_b = (string_symbol) reader.get_parsed_element("event_b");
            string_symbol event_c = (string_symbol) reader.get_parsed_element("event_c");
            string_symbol event_d = (string_symbol) reader.get_parsed_element("event_d");
            string_symbol state_0 = (string_symbol) reader.get_parsed_element("state_0");
            string_symbol state_1 = (string_symbol) reader.get_parsed_element("state_1");
            string_symbol state_2 = (string_symbol) reader.get_parsed_element("state_2");
            string_symbol state_3 = (string_symbol) reader.get_parsed_element("state_3");
            string_symbol state_4 = (string_symbol) reader.get_parsed_element("state_4");
            string_symbol state_5 = (string_symbol) reader.get_parsed_element("state_5");
            Set<string_symbol> output;

            output = T05_01_EPDA_GOTO.F_EPDA_GOTO(input, state_0, event_c);
            System.out.println(output);
            assert (output.size() == 0);

            output = T05_01_EPDA_GOTO.F_EPDA_GOTO(input, state_2, event_b);
            System.out.println(output);
            assert (output.size() == 3);

            output = T05_01_EPDA_GOTO.F_EPDA_GOTO(input, state_0, event_b);
            System.out.println(output);
            assert (output.size() == 1);

            Set<List<string_symbol>> output_seq;

            output_seq = T05_01_EPDA_GOTO.F_EPDA_GOTO_SEQ(input, state_0, General_List.list_by_enumeration());
            System.out.println(output_seq);
            assert (output_seq.size() == 1);

            output_seq = T05_01_EPDA_GOTO.F_EPDA_GOTO_SEQ(input, state_0, General_List.list_by_enumeration(event_b, event_b));
            System.out.println(output_seq);
            assert (output_seq.size() == 0);

            output_seq = T05_01_EPDA_GOTO.F_EPDA_GOTO_SEQ(input, state_0, General_List.list_by_enumeration(event_b, event_c, event_c, event_d));
            System.out.println(output_seq);
            assert (output_seq.size() == 1);

            output_seq = T05_01_EPDA_GOTO.F_EPDA_GOTO_SEQ(input, state_2, General_List.list_by_enumeration(event_b, event_b, event_a));
            System.out.println(output_seq);
            assert (output_seq.size() == 1);

            output_seq = T05_01_EPDA_GOTO.F_EPDA_GOTO_SEQ(input, state_2, General_List.list_by_enumeration(event_b, event_b, event_b, event_a));
            System.out.println(output_seq);
            assert (output_seq.size() == 2);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}