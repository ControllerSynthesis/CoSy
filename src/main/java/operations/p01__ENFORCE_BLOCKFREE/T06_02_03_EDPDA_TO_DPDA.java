package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T06_02_03_EDPDA_TO_DPDA {
    private static Logger log = LogManager.getLogger(T06_02_03_EDPDA_TO_DPDA.class.getName());

    private T06_02_03_EDPDA_TO_DPDA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<symbol__abstract<state>, event, symbol__abstract<stack>>
    F_EDPDA_TO_DPDA(
            epda<symbol__abstract<state>, event, symbol__abstract<stack>> epda1) {
        Config.instance.measuring_thread.push("F_EDPDA_TO_DPDA");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EDPDA_TO_DPDA: " + "epda1=" + epda1.log_info());

//        Config.instance.check_invariants(epda1);

        //T03_COLLATE_EPDA.COLLATE_EPDA(epda1);


        if (Config.instance.getF_EPDA_AA__before_RNPE() >= 0) {
            int size = epda1.get_epda_delta().size() + epda1.get_epda_states().size();
            epda1 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(epda1, Config.instance.getF_EPDA_AA__before_RNPE());
            if (log.isInfoEnabled()) {
                if (epda1.get_epda_delta().size() + epda1.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in T06_02_03_EDPDA_TO_DPDA: getF_EPDA_AA__before_RNPE should be zero");
            }
        }
        epda<symbol__abstract<state>, event, symbol__abstract<stack>> epda2 = T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES.F_EDPDA_RNPE(epda1);

        if (Config.T06_02_03_EDPDA_TO_DPDA__OUTPUT)
            Config.instance.output(epda1);
        if (Config.T06_02_03_EDPDA_TO_DPDA__OUTPUT)
            Config.instance.output(epda2);

        //epda2.print_epda_info("epda2", 0);

        //compare_unmarked_language.instance.test("F_EDPDA_RNPE(epda1)", epda1, epda2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);

        if (Config.instance.getF_EPDA_AA__before_RMPOE() >= 0) {
            int size = epda2.get_epda_delta().size() + epda2.get_epda_states().size();
            epda2 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(epda2, Config.instance.getF_EPDA_AA__before_RMPOE());
            if (log.isInfoEnabled()) {
                if (epda2.get_epda_delta().size() + epda2.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in T06_02_03_EDPDA_TO_DPDA: getF_EPDA_AA__before_RMPOE should be zero");
            }
        }

        //epda2.print_epda_info("epda2", 0);
        //compare_unmarked_language.instance.test("F_EPDA_AA(epda2", epda1, epda2, 1000, 10000, 0, 100, 1000, 10000, 0, 100);
        //Set<List<event>> epda_middle_AA_get_marked_lang_small = get_ulang(epda2, 1000);
        //Set<List<event>> epda_middle_AA_get_marked_lang_large = get_ulang(epda2, 10000);

        epda<symbol__abstract<state>, event, symbol__abstract<stack>> result;
        if (Config.instance.T06_02_03_EDPDA_TO_DPDA__USE_ALTERNATIVE) {

            Config.instance.check_invariants(epda2);
            epda<two_elements__abstract<symbol__abstract<state>, tuple2<symbol__abstract<state>, General_List<symbol__abstract<stack>>>>, event, symbol__abstract<stack>>
                    result_ALT = T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2.F_EDPDA_RMPOE__ALTERNATIVE(epda2);
            if (Config.T06_02_03_EDPDA_TO_DPDA__OUTPUT)
                Config.instance.output(result_ALT);
            //compare_unmarked_language.instance.test("F_EDPDA_RMPOE__ALTERNATIVE(epda2", epda2, result_ALT, 1000, 10000, 0, 100, 1000, 10000, 0, 100);
            result = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(result_ALT);
        } else
            result = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES.F_EDPDA_RMPOE(epda2));

        Config.instance.measuring_thread.pop("F_EDPDA_TO_DPDA");
        return result;
    }


}
