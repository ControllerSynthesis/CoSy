package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda;
import import_export.XML_reader;
import operations.p01__ENFORCE_BLOCKFREE.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE___F_EPDAAIA_example_local {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE___F_EPDAAIA_example_local.xml");

            epda<string_symbol, string_symbol, string_symbol>
                    input = (epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("input");
            input.print_epda_info("input", 20);

            Map<string_symbol, General_Set<General_List<string_symbol>>> expected0
                    = new HashMap<>();
            Map<string_symbol, General_Set<General_List<string_symbol>>> expected1
                    = new HashMap<>();
            Map<string_symbol, General_Set<General_List<string_symbol>>> expected2
                    = new HashMap<>();
            Map<string_symbol, General_Set<General_List<string_symbol>>> expected3
                    = new HashMap<>();

            add(reader, expected0, "state_0");
            add(reader, expected0, "state_1");
            add(reader, expected0, "state_2");
            add(reader, expected0, "state_3");
            add(reader, expected0, "state_4");
            add(reader, expected0, "state_5");
            add(reader, expected0, "state_6");
            add(reader, expected0, "state_7");
            add(reader, expected0, "state_8");

            add(reader, expected1, "state_0", "box");
            add(reader, expected1, "state_1", "bullet");
            add(reader, expected1, "state_2", "circ");
            add(reader, expected1, "state_4", "circ");
            add(reader, expected1, "state_5");
            add(reader, expected1, "state_6");
            add(reader, expected1, "state_7", "box");
            add(reader, expected1, "state_8", "bullet");

            add(reader, expected2, "state_0", "box");
            add(reader, expected2, "state_1", "bullet", "box");
            add(reader, expected2, "state_2", "circ", "bullet");
            add(reader, expected2, "state_2", "circ", "circ");
            add(reader, expected2, "state_4", "circ", "bullet");
            add(reader, expected2, "state_4", "circ", "circ");
            add(reader, expected2, "state_5", "bullet");
            add(reader, expected2, "state_5", "circ");
            add(reader, expected2, "state_6");
            add(reader, expected2, "state_7", "box");
            add(reader, expected2, "state_8", "bullet");

            add(reader, expected3, "state_0", "box");
            add(reader, expected3, "state_1", "bullet", "box");
            add(reader, expected3, "state_2", "circ", "bullet", "box");
            add(reader, expected3, "state_2", "circ", "circ", "bullet");
            add(reader, expected3, "state_2", "circ", "circ", "circ");
            add(reader, expected3, "state_4", "circ", "bullet", "box");
            add(reader, expected3, "state_4", "circ", "circ", "bullet");
            add(reader, expected3, "state_4", "circ", "circ", "circ");
            add(reader, expected3, "state_5", "bullet", "box");
            add(reader, expected3, "state_5", "circ", "bullet");
            add(reader, expected3, "state_5", "circ", "circ");
            add(reader, expected3, "state_6", "bullet");
            add(reader, expected3, "state_6", "circ");
            add(reader, expected3, "state_8", "bullet");


            Map<string_symbol, General_Set<General_List<string_symbol>>>
                    result0 = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(input, 0);
            Map<string_symbol, General_Set<General_List<string_symbol>>>
                    result1 = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(input, 1);
            Map<string_symbol, General_Set<General_List<string_symbol>>>
                    result2 = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(input, 2);
            Map<string_symbol, General_Set<General_List<string_symbol>>>
                    result3 = T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE.F_EPDA_AIA(input, 3);

            for (string_symbol key : result0.keySet())
                System.out.println("0: " + key + " -> " + result0.get(key));

            for (string_symbol key : result1.keySet())
                System.out.println("1: " + key + " -> " + result1.get(key));

            for (string_symbol key : result2.keySet())
                System.out.println("2: " + key + " -> " + result2.get(key));

            for (string_symbol key : result3.keySet())
                System.out.println("3: " + key + " -> " + result3.get(key));

            assert (result0.equals(expected0));
            assert (result1.equals(expected1));
            assert (result2.equals(expected2));
            assert (result3.equals(expected3));


        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    private void add(XML_reader reader, Map<string_symbol, General_Set<General_List<string_symbol>>> map, String stateX, String... elems) {
        string_symbol state = (string_symbol) reader.get_parsed_element(stateX);
        General_Set<General_List<string_symbol>> set = map.get(state);
        if (set == null) {
            set = new General_Set<>(new HashSet<>(), true);
            map.put(state, set);
        }
        List<string_symbol> list = new ArrayList<>();
        for (String s : elems)
            list.add((string_symbol) reader.get_parsed_element(s));
        set.get_value().add(new General_List<>(list));
    }


}