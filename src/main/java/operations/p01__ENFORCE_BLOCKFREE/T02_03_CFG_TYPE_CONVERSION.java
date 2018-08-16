package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supporting_operations.SOME_injective;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T02_03_CFG_TYPE_CONVERSION {

    private static Logger log = LogManager.getLogger(T02_03_CFG_TYPE_CONVERSION.class.getName());

    private T02_03_CFG_TYPE_CONVERSION() {
        throw new IllegalStateException("Utility Class");
    }

    private static <nonterminalA extends General, nonterminalB extends General, event extends General>
    List<two_elements__abstract<nonterminalB, event>>
    F_CFG_TC__word(
            List<two_elements__abstract<nonterminalA, event>> w,
            Map<nonterminalA, nonterminalB> mapping) {
        return w.stream().map(x ->
                {
                    if (x instanceof two_elements__teA)
                        return new two_elements__teA<nonterminalB, event>(mapping.get(((two_elements__teA<nonterminalB, event>) x).get_value()));
                    else
                        return new two_elements__teB<nonterminalB, event>(((two_elements__teB<nonterminalB, event>) x).get_value());
                }
        ).collect(Collectors.toList());
    }

    private static <nonterminalA extends General, nonterminalB extends General, event extends General>
    cfg_step_label<nonterminalB, event>
    F_CFG_TC__production(
            cfg_step_label<nonterminalA, event> e,
            Map<nonterminalA, nonterminalB> mapping) {
        return new cfg_step_label(
                mapping.get(e.get_prod_lhs()),
                F_CFG_TC__word(e.get_prod_rhs(), mapping));
    }


    public static <nonterminalA extends General, nonterminalB extends General, event extends General>
    cfg<nonterminalB, event>
    F_CFG_TC_inner(
            cfg<nonterminalA, event> cfg,
            Map<nonterminalA, nonterminalB> mapping) {
        Config.instance.measuring_thread.push("F_CFG_TC_inner");

        cfg<nonterminalB, event> result = new cfg<>(
                cfg.get_cfg_nonterminals().stream().map(e -> mapping.get(e)).collect(Collectors.toList()),
                cfg.get_cfg_events(),
                mapping.get(cfg.get_cfg_initial()),
                cfg.get_cfg_step_labels().stream().map(e -> F_CFG_TC__production(e, mapping)).collect(Collectors.toList()));

        Config.instance.measuring_thread.pop("F_CFG_TC_inner");

        return result;

    }

    public static <nonterminalA extends General, nonterminalB extends General, event extends General>
    cfg<symbol__abstract<nonterminalB>, event>
    F_CFG_TC(
            cfg<nonterminalA, event> cfg) {
        Config.instance.measuring_thread.push("F_CFG_TC");
        if (log.isInfoEnabled()) {
            log.info(Config.callStack() + "entering F_CFG_TC: " + "cfg=" + cfg.log_info());
        }

        Map<nonterminalA, symbol__abstract<nonterminalB>> mapping = SOME_injective.SOME_injective(cfg.get_cfg_nonterminals());
        cfg<symbol__abstract<nonterminalB>, event> result = F_CFG_TC_inner(cfg, mapping);

        Config.instance.measuring_thread.pop("F_CFG_TC");

        return result;

    }
}
