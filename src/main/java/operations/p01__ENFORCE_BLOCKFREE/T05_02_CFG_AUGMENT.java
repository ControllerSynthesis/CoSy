package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_02_CFG_AUGMENT {
    private static Logger log = LogManager.getLogger(T05_02_CFG_AUGMENT.class.getName());

    private T05_02_CFG_AUGMENT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <nonterminal extends General, event extends General>
    cfg<nonterminal, event>
    F_CFG_AUGMENT(
            cfg<nonterminal, event> cfg,
            nonterminal FS,
            event FE) {
        Config.instance.measuring_thread.push("F_CFG_AUGMENT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_CFG_AUGMENT: " + "cfg=" + cfg.log_info());
        List<nonterminal> nonterminals = new ArrayList<>(cfg.get_cfg_nonterminals());
        List<event> events = new ArrayList<>(cfg.get_cfg_events());
        List<cfg_step_label<nonterminal, event>> productions = new ArrayList<>(cfg.get_cfg_step_labels());
        nonterminals.add(FS);
        events.add(FE);
        productions.add(new cfg_step_label<>(FS, General_List.list_by_enumeration(
                new two_elements__teB<>(FE),
                new two_elements__teA<>(cfg.get_cfg_initial()),
                new two_elements__teB<>(FE)
        )));
        cfg<nonterminal, event> result = new cfg<>(nonterminals, events, FS, productions);
        Config.instance.measuring_thread.pop("F_CFG_AUGMENT");
        return result;
    }


}
