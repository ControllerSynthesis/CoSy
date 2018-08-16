package main;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.string_symbol;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_SigmaUC;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.formalisms.epda;
import import_export.XML_reader;
import import_export.XML_writer;
import operations.p01__ENFORCE_BLOCKFREE.T02_01_EPDA_TYPE_CONVERSION;
import operations.p01__ENFORCE_BLOCKFREE.T06_02_03_EDPDA_TO_DPDA;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import operations.p04__CONSTRUCT_CONTROLLER.Algorithm_Definition__Construct_Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Main {


    private static Logger log = LogManager.getLogger(Main.class.getName());

    public static void example_of_nontermination(boolean debug_computations, boolean run_controller_synthesis, List<String> args) {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/example_of_nontermination.xml");

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plant");
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("spec");
            General_SigmaUC<symbol__abstract<string_symbol>>
                    sigmaUC = (General_SigmaUC<symbol__abstract<string_symbol>>) reader.get_parsed_element("sigmaUC");

            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
                    tests = null;
            if (debug_computations)
                tests = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_for_resulting_controller");


            if (debug_computations) {
                System.out.println("sigmaUC = " + sigmaUC.toString());
                plant.print_epda_info("plant", 4);
                spec.print_epda_info("spec", 4);
            }

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec_DPDA = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(spec));
            if (debug_computations)
                spec_DPDA.print_epda_info("spec_DPDA", 4);

            log.info("now starting computation of controller");
            if (debug_computations)
                Config.instance.output(spec_DPDA);
            option__abstract<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>
                    result = null;
            if (run_controller_synthesis)
                result = Algorithm_Definition__Construct_Controller.F_DPDA_DFA_CC(spec_DPDA, plant, sigmaUC.get_value().get_value());
            log.info("now ending computation of controller");

            if (debug_computations) {
                if (result instanceof option__Some) {
                    epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                            resultX = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) result).the();

                    XML_writer writer = new XML_writer("out/test/cosy/output_test", "example_of_nontermination.xml", "../../main/resources/cosy.xsd");
                    writer.add_to_be_exported(plant);
                    writer.add_to_be_exported(plant);
                    writer.add_to_be_exported(spec);
                    writer.add_to_be_exported(spec_DPDA);
                    writer.add_to_be_exported(sigmaUC);
                    writer.add_to_be_exported(resultX);
                    writer.export();
                    resultX.print_epda_info("result", 20);

                    for (General_Test_Sequence<symbol__abstract<string_symbol>> testSequence : tests.get_value()) {
                        log.info("test: " + testSequence.toString());
                        Boolean result_value = epda.check_word_inclusion(resultX, testSequence.get_sequence().get_value(), testSequence.get_expected());
                        if (result_value == null)
                            throw new RuntimeException();
                        if (result_value)
                            log.info("TEST SUCCEEDED");
                        else {
                            log.info("TEST FAILED");
                            assert false;
                        }
                    }
                }
            } else {
                System.out.println("no controller obtained");
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config.instance.local_solver.kill_runnables();
        Config.instance.measuring_thread.abort();
    }

    public static void example_automated_fabrication_scenario_A(boolean debug_computations, boolean run_controller_synthesis, List<String> args) {
        XML_reader reader;
        try {
            reader = new XML_reader();
            //reader.parse("src/test/resources/unit_tests/example_from_JDES__no_loop.xml");
            //reader.parse("src/test/resources/unit_tests/example_from_JDES__fixed_from_start.xml");
            reader.parse("src/test/resources/unit_tests/example_automated_fabrication_scenario_A.xml");
            //reader.parse("src/test/resources/unit_tests/example_of_nontermination.xml");


            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant1 = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plant1");
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant2 = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plant2");
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("spec");
            General_SigmaUC<symbol__abstract<string_symbol>>
                    sigmaUC = (General_SigmaUC<symbol__abstract<string_symbol>>) reader.get_parsed_element("sigmaUC");

            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
                    tests = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_for_resulting_controller");


            if (debug_computations) {
                System.out.println("sigmaUC = " + sigmaUC.toString());
                plant1.print_epda_info("plant1", 2);
                plant2.print_epda_info("plant2", 2);
                spec.print_epda_info("spec", 4);
            }

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(plant1, plant2));
            if (debug_computations) {
                plant.print_epda_info("plant", 4);
            }


            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec_DPDA = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(spec));
            if (debug_computations) {
                spec_DPDA.print_epda_info("spec_DPDA", 4);
            }

            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
                    invariants = null;
            if (debug_computations) {
                invariants = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_as_invariants");
                Config.instance.set_invariants(invariants);
            }


            //compare_unmarked_language.instance.test("TEST", spec, spec_DPDA, 1000, 1000000, 0, 100, 1000, 1000000, 0, 100);

            log.info("now starting computation of controller");
            if (debug_computations)
                Config.instance.output(spec_DPDA);
            option__abstract<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>
                    result = null;

            if (run_controller_synthesis)
                result = Algorithm_Definition__Construct_Controller.F_DPDA_DFA_CC(spec_DPDA, plant, sigmaUC.get_value().get_value());
            log.info("now ending computation of controller");

            if (debug_computations) {
                if (result instanceof option__Some) {
                    epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                            resultX = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) result).the();

                    XML_writer writer = new XML_writer("out/test/cosy/output_test", "example_automated_fabrication_scenario_A.xml", "../../main/resources/cosy.xsd");
                    writer.add_to_be_exported(plant1);
                    writer.add_to_be_exported(plant2);
                    writer.add_to_be_exported(plant);
                    writer.add_to_be_exported(spec);
                    writer.add_to_be_exported(spec_DPDA);
                    writer.add_to_be_exported(sigmaUC);
                    writer.add_to_be_exported(resultX);
                    writer.export();
                    resultX.print_epda_info("result", 20);

                    for (General_Test_Sequence<symbol__abstract<string_symbol>> testSequence : tests.get_value()) {
                        log.info("test: " + testSequence.toString());
                        Boolean result_value = epda.check_word_inclusion(resultX, testSequence.get_sequence().get_value(), testSequence.get_expected());
                        if (result_value == null)
                            throw new RuntimeException();
                        if (result_value)
                            log.info("TEST SUCCEEDED");
                        else {
                            log.info("TEST FAILED");
                            assert false;
                        }
                    }

                } else {
                    log.info("no controller obtained");
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config.instance.local_solver.kill_runnables();
        Config.instance.measuring_thread.abort();
    }

    public static void example_automated_fabrication_scenario_B(boolean debug_computations, boolean run_controller_synthesis, List<String> args) {
        XML_reader reader;
        try {
            reader = new XML_reader();
            //reader.parse("src/test/resources/unit_tests/example_from_JDES__no_loop.xml");
            //reader.parse("src/test/resources/unit_tests/example_from_JDES__fixed_from_start.xml");
            reader.parse("src/test/resources/unit_tests/example_automated_fabrication_scenario_B.xml");
            //reader.parse("src/test/resources/unit_tests/example_of_nontermination.xml");


            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    plant = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plant");
            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("spec");
            General_SigmaUC<symbol__abstract<string_symbol>>
                    sigmaUC = (General_SigmaUC<symbol__abstract<string_symbol>>) reader.get_parsed_element("sigmaUC");

            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
                    tests = null;
            if (debug_computations)
                tests = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_for_resulting_controller");

            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
                    invariants = null;
            if (debug_computations) {
                invariants = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_as_invariants");
                Config.instance.set_invariants(invariants);
            }

            if (debug_computations) {
                System.out.println("sigmaUC = " + sigmaUC.toString());
                plant.print_epda_info("plant", 2);
                spec.print_epda_info("spec", 4);
            }

            Config.instance.check_invariants(plant);

            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                    spec_DPDA = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(T06_02_03_EDPDA_TO_DPDA.F_EDPDA_TO_DPDA(spec));
            if (debug_computations)
                spec_DPDA.print_epda_info("spec_DPDA", 4);

            if (debug_computations) {
                if (!plant.is_valid(true))
                    throw new RuntimeException();
                if (!spec_DPDA.is_valid(true))
                    throw new RuntimeException();
            }

            //compare_unmarked_language.instance.test("TEST", spec, spec_DPDA, 1000, 1000000, 0, 100, 1000, 1000000, 0, 100);


            log.info("now starting computation of controller");
            if (debug_computations)
                Config.instance.output(spec_DPDA);
            option__abstract<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>
                    result = null;
            if (run_controller_synthesis)
                result = Algorithm_Definition__Construct_Controller.F_DPDA_DFA_CC(spec_DPDA, plant, sigmaUC.get_value().get_value());
            log.info("now ending computation of controller");

            if (debug_computations) {
                if (result instanceof option__Some) {
                    epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
                            resultX = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>) result).the();

                    XML_writer writer = new XML_writer("out/test/cosy/output_test", "example_automated_fabrication_scenario_B.xml", "../../main/resources/cosy.xsd");
                    writer.add_to_be_exported(plant);
                    writer.add_to_be_exported(spec);
                    writer.add_to_be_exported(spec_DPDA);
                    writer.add_to_be_exported(sigmaUC);
                    writer.add_to_be_exported(resultX);
                    writer.export();
                    resultX.print_epda_info("result", 5);

                    for (General_Test_Sequence<symbol__abstract<string_symbol>> testSequence : tests.get_value()) {
                        System.out.println("test: " + testSequence.toString());
                        Boolean result_value = epda.check_word_inclusion(resultX, testSequence.get_sequence().get_value(), testSequence.get_expected());
                        if (result_value == null)
                            throw new RuntimeException();
                        if (result_value)
                            System.out.println("TEST SUCCEEDED");
                        else {
                            System.out.println("TEST FAILED");
                            assert false;
                        }
                    }

                } else {
                    System.out.println("no controller obtained");
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config.instance.local_solver.kill_runnables();
        Config.instance.measuring_thread.abort();
    }

//    public static void example_automated_fabrication_scenario_C() {
//        XML_reader reader;
//        try {
//            reader = new XML_reader();
//            reader.parse("src/test/resources/unit_tests/example_automated_fabrication_scenario_C.xml");
//
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plantA1 = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plantA1");
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plantA2 = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plantA2");
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plantA3 = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plantA3");
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plantB = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plantB");
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plantC = (epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>) reader.get_parsed_element("plantC");
//
//            General_SigmaUC<symbol__abstract<string_symbol>>
//                    sigmaUC = (General_SigmaUC<symbol__abstract<string_symbol>>) reader.get_parsed_element("sigmaUC");
//
//            General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>
//                    invariants = (General_List<General_Test_Sequence<symbol__abstract<string_symbol>>>) reader.get_parsed_element("test_sequences_as_invariants");
//
//            Config.set_invariants(invariants);
//
//            if(!plantA1.is_valid())
//                throw new RuntimeException();
//            if(!plantA2.is_valid())
//                throw new RuntimeException();
//            if(!plantA3.is_valid())
//                throw new RuntimeException();
//            if(!plantB.is_valid())
//                throw new RuntimeException();
//            if(!plantC.is_valid())
//                throw new RuntimeException();
//            System.out.println("sigmaUC = " + sigmaUC.toString());
//            plantA1.print_epda_info("plantA1", 2);
//            plantA2.print_epda_info("plantA2", 2);
//            plantA3.print_epda_info("plantA3", 2);
//            plantB.print_epda_info("plantB", 2);
//            plantC.print_epda_info("plantC", 2);
//
//            Config.check_invariants(plantA1);
//            Config.check_invariants(plantA2);
//            Config.check_invariants(plantA3);
//            Config.check_invariants(plantB);
//            Config.check_invariants(plantC);
//
//            epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>
//                    plant = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(plantA1,
//                    Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(plantA2,
//                            Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(plantA3,
//                                    Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(plantB, plantC)))));
//            plant.print_epda_info("plant", 4);
//            Config.check_invariants(plant);
//            plant = ((option__Some<epda<symbol__abstract<string_symbol>, symbol__abstract<string_symbol>, symbol__abstract<string_symbol>>>)T08_MINIMIZE_DFA.MINIMIZE_DFA(plant)).the();
//            if(!plant.is_valid())
//                throw new RuntimeException();
//            plant.print_epda_info("plant", 4);
//            Config.check_invariants(plant);
//
//            if(!plant.is_valid())
//                throw new RuntimeException();
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Config.local_solver.kill_runnables();
//        Config.instance.measuring_thread.abort();
//    }


    public static void main(String[] args) {
        Config.COLLATE_CFG__BISIM = false;
        if (args.length == 0) {
            args = new String[]{"debug_computations", "true", "run_controller_synthesis", "true", "approximationvalue", "0", "example", "example_automated_fabrication_scenario_C", "0", "1", "1", "2", "1"};
//            args = new String[]{"debug_computations", "true", "run_controller_synthesis", "true", "approximationvalue", "0", "example", "example_automated_fabrication_scenario_B"};
//            args = new String[]{"debug_computations", "true", "run_controller_synthesis", "true", "approximationvalue", "1", "example", "example_automated_fabrication_scenario_A"};
            System.out.println("arguments: none; using default arguments");
        }
        if (args.length > 0) {
            boolean debug_computations = false;
            boolean run_controller_synthesis = true;
            String example = null;
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            boolean done = false;
            while (!arguments.isEmpty() && !done) {
                String value = arguments.remove(0);
                switch (value) {
                    case "debug_computations":
                        debug_computations = Boolean.parseBoolean(arguments.remove(0));
                        break;
                    case "run_controller_synthesis":
                        run_controller_synthesis = Boolean.parseBoolean(arguments.remove(0));
                        break;
                    case "approximationvalue":
                        Config.instance.setF_EPDA_AA__GLOBAL_DEFAULT(Integer.parseInt(arguments.remove(0)));
                        break;
                    case "example":
                        example = arguments.remove(0);
                        break;
                    case "--":
                        done = true;
                        break;
                }
            }
            if (debug_computations && !run_controller_synthesis)
                throw new RuntimeException();
            if (example == null)
                throw new RuntimeException();

            System.out.println("arguments: debug_computations       = " + debug_computations);
            System.out.println("arguments: run_controller_synthesis = " + run_controller_synthesis);
            System.out.println("arguments: approximationvalue       = " + Config.instance.getF_EPDA_AA__GLOBAL_DEFAULT());
            System.out.println("arguments: example                  = " + example);
            System.out.println("arguments: (forwarded)              = " + arguments);


            switch (example) {
                case "example_of_nontermination":
                    example_of_nontermination(debug_computations, run_controller_synthesis, arguments);
                    break;
                case "example_automated_fabrication_scenario_A":
                    example_automated_fabrication_scenario_A(debug_computations, run_controller_synthesis, arguments);
                    break;
                case "example_automated_fabrication_scenario_B":
                    example_automated_fabrication_scenario_B(debug_computations, run_controller_synthesis, arguments);
                    break;
                case "example_automated_fabrication_scenario_C":
                    new Generate_Automated_Fabrication_Scenario_C(debug_computations, run_controller_synthesis, arguments).run();
                    break;
                default:
                    System.out.println("invalid example=" + example);

            }
        } else {
            System.out.println("Missing arguments!");
        }

//        example_of_nontermination();

    }


}
