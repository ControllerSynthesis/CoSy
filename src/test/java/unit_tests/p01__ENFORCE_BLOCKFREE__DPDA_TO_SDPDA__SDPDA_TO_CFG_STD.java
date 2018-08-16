package unit_tests;

import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import datatypes.formalisms.epdaH_semantics;
import datatypes.formalisms.parser;
import import_export.XML_reader;
import import_export.XML_writer;
import operations.p01__ENFORCE_BLOCKFREE.T03_04_05_DPDA_TO_SDPDA;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_01_SDPDA_TO_CFG_STD;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__DPDA_TO_SDPDA__SDPDA_TO_CFG_STD {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/a2n_b2n.xml");

            epda dpda__a2n_b2n = (epda) reader.get_parsed_element("dpda__a2n_b2n");
            cfg cfg__a2n_b2n = (cfg) reader.get_parsed_element("cfg__a2n_b2n");
            parser parser__a2n_b2n = (parser) reader.get_parsed_element("parser__a2n_b2n");


            XML_writer writer = new XML_writer("out/test/cosy/output_test", "unit_tests/a2n_b2n.xml", "../../main/resources/cosy.xsd");

            new epdaH_semantics<>().print_accessible_configurations(dpda__a2n_b2n, 10, true);
            System.out.println();
            epda result1 = T03_04_05_DPDA_TO_SDPDA.F_DPDA_TO_SDPDA(dpda__a2n_b2n);
            new epdaH_semantics<>().print_accessible_configurations(result1, 50, false);
            new epdaH_semantics<>().print_accessible_configurations(result1, 50, true);
            System.out.println();

            cfg result2 = T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(result1, null);
            System.out.println();
            System.out.println("result2");
            List<string_symbol> word = new ArrayList<>();
            word.add((string_symbol) reader.get_parsed_element("event_a"));
            word.add((string_symbol) reader.get_parsed_element("event_a"));
            word.add((string_symbol) reader.get_parsed_element("event_a"));
            word.add((string_symbol) reader.get_parsed_element("event_b"));
            word.add((string_symbol) reader.get_parsed_element("event_b"));
            word.add((string_symbol) reader.get_parsed_element("event_b"));

            assert epda.check_word_inclusion(dpda__a2n_b2n, word, General_Test_Sequence.MARK);
            assert epda.check_word_inclusion(result1, word, General_Test_Sequence.MARK);
            assert cfg.check_word_inclusion(result2, word, General_Test_Sequence.MARK);
            System.out.println();

            //writer.add_to_be_exported();
            //writer.add_to_be_exported(cfg__a2n_b2n);
            //writer.add_to_be_exported(parser__a2n_b2n);
            writer.export();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}