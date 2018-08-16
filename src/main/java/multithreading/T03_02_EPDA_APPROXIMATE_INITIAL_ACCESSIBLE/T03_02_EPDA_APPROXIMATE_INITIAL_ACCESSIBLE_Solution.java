package multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;

import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solution<state extends General, stack extends General> extends Abstract_Solution {

    public Set<tuple2<state, General_List<stack>>> found;

    public T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solution(
            Abstract_Problem problem,
            Set<tuple2<state, General_List<stack>>> found) {
        super(problem);
        this.found = found;
    }
}
