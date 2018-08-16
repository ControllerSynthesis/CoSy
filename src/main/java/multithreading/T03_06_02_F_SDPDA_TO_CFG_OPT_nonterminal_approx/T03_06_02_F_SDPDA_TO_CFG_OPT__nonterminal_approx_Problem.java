package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import multithreading.general.abstract_components.Abstract_Problem;

import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem<state extends General, event extends General, stack extends General> extends Abstract_Problem {

    public state state;
    public epda<state, event, stack> epda;
    public int k;
    public Map<state, Set<stack>> has_auxiliary_for;

    public T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem(
            state state,
            epda<state, event, stack> epda,
            int k,
            Map<state, Set<stack>> has_auxiliary_for) {
        super(false, true);
        this.state = state;
        this.epda = epda;
        this.k = k;
        this.has_auxiliary_for = has_auxiliary_for;

    }

}
