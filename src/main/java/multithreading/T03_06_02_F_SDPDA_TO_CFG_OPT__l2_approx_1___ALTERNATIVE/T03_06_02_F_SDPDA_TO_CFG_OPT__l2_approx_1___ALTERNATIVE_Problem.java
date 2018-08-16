package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE;

import datatypes.basic_datatypes_of_algorithm.accessibility_graph;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.tuple3;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import multithreading.general.abstract_components.Abstract_Problem;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem<state extends General, event extends General, stack extends General> extends Abstract_Problem {

    public List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo;
    public accessibility_graph<state, stack> closure;
    public epda<state, event, stack> epda;

    public T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem(
            List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo,
            accessibility_graph<state, stack> closure,
            epda<state, event, stack> epda) {
        super(false, true);
        this.todo = todo;
        this.closure = closure;
        this.epda = epda;
    }

}
