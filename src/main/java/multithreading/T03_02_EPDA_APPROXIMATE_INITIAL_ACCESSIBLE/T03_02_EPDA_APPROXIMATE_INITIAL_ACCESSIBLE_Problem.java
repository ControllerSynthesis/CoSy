package multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import multithreading.general.abstract_components.Abstract_Problem;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem<state extends General, event extends General, stack extends General> extends Abstract_Problem {

    public List<tuple2<state, General_List<stack>>> todo;
    public epda<state, event, stack> epda;
    public int k;

    public T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem(
            List<tuple2<state, General_List<stack>>> todo,
            epda<state, event, stack> epda,
            int k) {
        super(false, true);
        this.todo = todo;
        this.epda = epda;
        this.k = k;
    }

}
