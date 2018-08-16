package unit_tests;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.basic_java_datatypes.General_SigmaUC;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.language_check.Compare_EPDA_EPDA;
import datatypes.formalisms.parser;
import import_export.XML_reader;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T01_COLLATE_CFG;
import operations.ADDITIONAL_OPERATIONS.T03_COLLATE_EPDA;
import operations.ADDITIONAL_OPERATIONS.T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT;
import operations.p01__ENFORCE_BLOCKFREE.Algorithm_Definition__Enforce_Blockfree;
import operations.p01__ENFORCE_BLOCKFREE.T02_03_CFG_TYPE_CONVERSION;
import operations.p05__CFG_CONSTRUCT_CONTROLLER.Algorithm_Definition__CFG_Construct_Controller;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p05__CFG_CONSTRUCT_CONTROLLER__Algorithm_Definition__CFG_Construct_Controller {

    private void runtest(String file, Function<String, Boolean> result_requirement, Function<String, Boolean> removed_requirement) {
        Config.instance.measuring_thread.abort();
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse(file);

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set1 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set1"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set2 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set2"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set3 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set3"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set4 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set4"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set5 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set5"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set6 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set6"));
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    dpda_set7 = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("dpda_set7"));
            if (!dpda_set1.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set2.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set3.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set4.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set5.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set6.is_valid(false))
                throw new RuntimeException();
            if (!dpda_set7.is_valid(false))
                throw new RuntimeException();


            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("spec"));
            spec.dot_export(Algorithm_Definition__CFG_Construct_Controller.PATH + "002_spec.dot");
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant = T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.WRAP_IN_SYMBOL_ABSTRACT_epda_all((epda<string_symbol, string_symbol, string_symbol>) reader.get_parsed_element("plant"));
            plant.dot_export(Algorithm_Definition__CFG_Construct_Controller.PATH + "001_plant.dot");
            General_SigmaUC<string_symbol>
                    sigmaUC = (General_SigmaUC<string_symbol>) reader.get_parsed_element("sigmaUC");

            List<symbol__abstract<string_symbol>> sigmaUC_wrapped = new ArrayList<>();
            for (string_symbol elem : sigmaUC.get_value().get_value())
                sigmaUC_wrapped.add(new symbol__cons_symbol_base<>(elem));

            option__abstract<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<string_symbol>, General_Integer>>, symbol__abstract<string_symbol>>>
                    cfgoption__abstract = Algorithm_Definition__CFG_Construct_Controller.F_UNVERIFIED_CFG_DPDA_DFA_CC(spec, plant, sigmaUC_wrapped);
            if (cfgoption__abstract instanceof option__None) {
                Algorithm_Definition__CFG_Construct_Controller.export(Algorithm_Definition__CFG_Construct_Controller.PATH, "_final_result.dot",
                        "digraph G { "
                                + " labelloc=\"t\";"
                                + "label=\"" + Algorithm_Definition__CFG_Construct_Controller.get_prefix() + "_final_result (empty)" + "\";"
                                + "}");
            } else if (cfgoption__abstract instanceof option__Some) {

                cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<string_symbol>, General_Integer>>, symbol__abstract<string_symbol>>
                        cfg = ((option__Some<cfg<symbol__abstract<tuple3<General_Integer, symbol__abstract<string_symbol>, General_Integer>>, symbol__abstract<string_symbol>>>)
                        cfgoption__abstract).get_value();

                cfg = T01_COLLATE_CFG.COLLATE_CFG(cfg, 5);
                Algorithm_Definition__CFG_Construct_Controller.print_grammarX(cfg, null, null, "_final_result.dot");

                {
                    parser<General_Set<cfg_item<symbol__abstract<tuple3<General_Integer, symbol__abstract<string_symbol>, General_Integer>>, symbol__abstract<string_symbol>>>, symbol__abstract<string_symbol>, option__abstract<option__abstract<cfg_step_label<symbol__abstract<tuple3<General_Integer, symbol__abstract<string_symbol>, General_Integer>>, symbol__abstract<string_symbol>>>>>
                            P0 = Algorithm_Definition__Enforce_Blockfree.lr1_cfg_to_parser__OPT(cfg);
                    if (!P0.is_valid())
                        throw new RuntimeException();
                    if (!P0.is_deterministic())
                        throw new RuntimeException();
                }

                cfg<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        G = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(cfg);
                epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                        dpda_controller = Algorithm_Definition__Enforce_Blockfree.lr1_cfg_to_dpda__OPT(G);
                if (!dpda_controller.is_valid(true))
                    throw new RuntimeException();

                dpda_controller.dot_export(Algorithm_Definition__CFG_Construct_Controller.PATH + "999_dpda_controller.dot");

                dpda_controller = T03_COLLATE_EPDA.COLLATE_EPDA(dpda_controller);
                dpda_controller.dot_export(Algorithm_Definition__CFG_Construct_Controller.PATH + "999_dpda_controller__COLLATED.dot");

                {
                    System.out.println("computing inclusion_result1");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set1, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (!inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result2");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set2, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (!inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result4");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set4, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (!inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result5");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set5, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (!inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result3");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set3, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (!inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result6");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set6, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (!inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

                {
                    System.out.println("computing inclusion_result7");
                    General_Tuple2<List<List<symbol__abstract<string_symbol>>>, List<List<symbol__abstract<string_symbol>>>>
                            inclusion_result = Compare_EPDA_EPDA.compare_inclusion(dpda_set7, dpda_controller,
                            true,
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
                            200000);
                    System.out.println("inclusion result: " + inclusion_result.getElemA().size() + ", " + inclusion_result.getElemB().size());
                    if (inclusion_result.getElemA().isEmpty())
                        throw new RuntimeException();
                    if (!inclusion_result.getElemB().isEmpty())
                        throw new RuntimeException();
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void Test() {
        Algorithm_Definition__CFG_Construct_Controller.PATH = "out/test/cosy/output_test/Test_enforce_controllability_on_cfg__example/";
        Algorithm_Definition__CFG_Construct_Controller.INDEX = 3;
        runtest("src/test/resources/unit_tests/p05__CFG_CONSTRUCT_CONTROLLER__Algorithm_Definition__CFG_Construct_Controller.xml",
                w -> w.contains("b"),
                w -> !w.contains("b"));
    }


}