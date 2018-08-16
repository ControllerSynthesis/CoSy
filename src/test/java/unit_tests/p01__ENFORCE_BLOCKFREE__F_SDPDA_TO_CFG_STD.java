package unit_tests;

import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.formalisms.*;
import import_export.XML_reader;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import operations.p01__ENFORCE_BLOCKFREE.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class p01__ENFORCE_BLOCKFREE__F_SDPDA_TO_CFG_STD {

    @org.junit.Test
    public void Test() {
        XML_reader reader;
        try {
            reader = new XML_reader();
            reader.parse("src/test/resources/unit_tests/p01__ENFORCE_BLOCKFREE__T03_06_08_SDPDA_TO_LR1_OPT___F_SDPDAtoCFG_example_SDPDA_empty_stack_marking.xml");

            epda F_SDPDAtoCFG_example_SDPDA_empty_stack_marking = (epda) reader.get_parsed_element("input");
            new epdaH_semantics<>().print_accessible_configurations(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 5, false);
            System.out.println();

            cfg result = T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, null);
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(result, 15, true);

            cfg cfg_EB = ((option__Some<cfg>) T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(result)).the();
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(cfg_EB, 15, true);
            System.out.println("done cfg_EB");

            cfg cfg_EA = T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(result);
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(cfg_EA, 15, true);
            System.out.println("done cfg_EA");

            cfg cfg_EAEB = T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(((option__Some<cfg>) T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(result)).the());
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(cfg_EAEB, 15, true);
            System.out.println("done cfg_EAEB");

            cfg cfg_TRIM = ((option__Some<cfg>) T03_06_05_CFG_TRIM.F_CFG_TRIM(result)).the();
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(cfg_TRIM, 15, true);
            System.out.println("done cfg_TRIM");


            cfg resultOPT = ((option__Some<cfg>) T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 2, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking), null)).the();
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(resultOPT, 15, true);

            for (Object elem : resultOPT.get_cfg_nonterminals())
                if (!cfg_TRIM.get_cfg_nonterminals().contains(elem))
                    System.out.println("N: resultOPT - cfg_TRIM : " + elem);

            for (Object elem : cfg_TRIM.get_cfg_nonterminals())
                if (!resultOPT.get_cfg_nonterminals().contains(elem))
                    System.out.println("N: cfg_TRIM - resultOPT: " + elem);


            for (Object elem : resultOPT.get_cfg_step_labels())
                if (!cfg_TRIM.get_cfg_step_labels().contains(elem))
                    System.out.println("E: resultOPT - cfg_TRIM : " + elem);

            for (Object elem : cfg_TRIM.get_cfg_step_labels())
                if (!resultOPT.get_cfg_step_labels().contains(elem))
                    System.out.println("E: cfg_TRIM - resultOPT: " + elem);


            cfg cfgOPT_TRIM = ((option__Some<cfg>) T03_06_05_CFG_TRIM.F_CFG_TRIM(resultOPT)).the();
            System.out.println();
            new cfgLM_semantics<>().print_accessible_configurations(cfgOPT_TRIM, 15, false);
            System.out.println("done cfgOPT_TRIM");


            System.out.println("result.get_cfg_step_labels().size() = " + result.get_cfg_step_labels().size());
            System.out.println("result.get_cfg_nonterminals().size() = " + result.get_cfg_nonterminals().size());
            System.out.println("cfg_EB.get_cfg_step_labels().size() = " + cfg_EB.get_cfg_step_labels().size());
            System.out.println("cfg_EB.get_cfg_nonterminals().size() = " + cfg_EB.get_cfg_nonterminals().size());
            System.out.println("cfg_EA.get_cfg_step_labels().size() = " + cfg_EA.get_cfg_step_labels().size());
            System.out.println("cfg_EA.get_cfg_nonterminals().size() = " + cfg_EA.get_cfg_nonterminals().size());
            System.out.println("cfg_EAEB.get_cfg_step_labels().size() = " + cfg_EAEB.get_cfg_step_labels().size());
            System.out.println("cfg_EAEB.get_cfg_nonterminals().size() = " + cfg_EAEB.get_cfg_nonterminals().size());
            System.out.println("cfg_TRIM.get_cfg_step_labels().size() = " + cfg_TRIM.get_cfg_step_labels().size());
            System.out.println("cfg_TRIM.get_cfg_nonterminals().size() = " + cfg_TRIM.get_cfg_nonterminals().size());

            System.out.println("resultOPT.get_cfg_step_labels().size() = " + resultOPT.get_cfg_step_labels().size());
            System.out.println("resultOPT.get_cfg_nonterminals().size() = " + resultOPT.get_cfg_nonterminals().size());
            System.out.println("cfgOPT_TRIM.get_cfg_step_labels().size() = " + cfgOPT_TRIM.get_cfg_step_labels().size());
            System.out.println("cfgOPT_TRIM.get_cfg_nonterminals().size() = " + cfgOPT_TRIM.get_cfg_nonterminals().size());


            if (false) {
                System.out.println("opt1 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 1, 10000));
                System.out.println("opt2 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 2, 10000));
                System.out.println("opt3 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 3, 10000));
                System.out.println("opt4 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 4, 10000));
                System.out.println("opt5 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 5, 10000));
                System.out.println("opt6 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 6, 10000));
                System.out.println("opt7 = " + test_time_opt(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 7, 10000));
                System.out.println("std  = " + test_time_std(F_SDPDAtoCFG_example_SDPDA_empty_stack_marking, 10000));
            }

            {
                cfg normal = T02_03_CFG_TYPE_CONVERSION.F_CFG_TC(cfgOPT_TRIM);
                System.out.println("normal");
                new cfgLM_semantics<>().print_accessible_configurations(normal, 20, true);
                System.out.println("that was normal " + normal.is_valid());

                symbol__abstract Do = T01_FRESH.F_FRESH(normal.get_cfg_events());
                symbol__abstract Sp = T01_FRESH.F_FRESH(normal.get_cfg_nonterminals());
                cfg annotated = T05_02_CFG_AUGMENT.F_CFG_AUGMENT(normal, Sp, Do);
                epda machine = T05_04_LR_MACHINE.F_LR_MACHINE(annotated, 1);
                parser parser = T05_05_LR_PARSER.F_LR_PARSER(normal, annotated, machine, Do);
                System.out.println("parser");
                new parserHF_semantics<>().print_accessible_configurations(parser, 20, true);
                System.out.println("that was parser " + parser.is_valid());

                parser parserRITU = T05_06_PARSER_REMOVE_INPUT_TERMINATOR_USAGE.F_PARSER_RITU(parser);
                System.out.println("parserRITU");
                new parserHF_semantics<>().print_accessible_configurations(parserRITU, 20, true);
                System.out.println("that was parserRITU " + parserRITU.is_valid());


                parser parserTC = T02_02_PARSER_TYPE_CONVERSION.F_PARSER_TC(parserRITU);
                System.out.println("parserTC");
                new parserHF_semantics<>().print_accessible_configurations(parserTC, 20, true);
                System.out.println("that was parserTC " + parserTC.is_valid());


                parser parserRTR = T05_07_PARSER_REMOVE_TOP_RULES.F_PARSER_RTR(parserTC);
                System.out.println("parserRTR");
                new parserHF_semantics<>().print_accessible_configurations(parserRTR, 20, true);
                System.out.println("that was parserRTR " + parserRTR.is_valid());

                symbol__abstract box = T01_FRESH.F_FRESH(parserRTR.get_parser_nonterms());
                epda epdaRES = T06_01_PARSER_TO_EDPDA.F_PARSER_TO_EDPDA(parserRTR, box);
                print_epda_info("epdaRES", epdaRES, 20);

                epda epdaRNPE = T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES.F_EDPDA_RNPE(epdaRES);
                print_epda_info("epdaRNPE", epdaRNPE, 20);

                epda epdaRMPOE = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2.F_EDPDA_RMPOE__ALTERNATIVE(epdaRNPE);
                print_epda_info("epdaRMPOE", epdaRMPOE, 20);


                epda epdaSTD_ACC = T07_02_DPDA_ENFORCE_ACCESSIBLE_STD.F_DPDA_EA_STD(epdaRMPOE);
                print_epda_info("epdaSTD_ACC", epdaSTD_ACC, 20);

                epda epdaOPT_ACC = T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(epdaRMPOE, false);
                print_epda_info("epdaOPT_ACC", epdaOPT_ACC, 20);


                //System.out.println("done epdaSTD_ACC");
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print_epda_info(String name, epda epda, int num) {
        System.out.println(name + " : start");
        new epdaH_semantics<>().print_accessible_configurations(epda, num, true);
        System.out.println(name + " : end : get_epda_delta().size() = " + epda.get_epda_delta().size() + ", get_epda_states().size() = " + epda.get_epda_states().size() + ", is_valid() = " + epda.is_valid(true));
    }

    private long test_time_opt(epda input, int opt, int max) {
        long start = System.nanoTime();
        for (int i = 0; i <= max; i++) {
            cfg result = ((option__Some<cfg>) T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT(input, opt, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__use_approx(input), null)).the();
            cfg trim = ((option__Some<cfg>) T03_06_05_CFG_TRIM.F_CFG_TRIM(result)).the();
        }
        long end = System.nanoTime();
        return (end - start) / max;
    }

    private long test_time_std(epda input, int max) {
        long start = System.nanoTime();
        for (int i = 0; i <= max; i++) {
            cfg result = T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(input, null);
            cfg trim = ((option__Some<cfg>) T03_06_05_CFG_TRIM.F_CFG_TRIM(result)).the();
        }
        long end = System.nanoTime();
        return (end - start) / max;
    }


}