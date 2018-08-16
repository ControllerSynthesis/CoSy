package multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epdaH_configuration;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;

import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solution<state extends General, event extends General, stack extends General> extends Abstract_Solution {

    public Set<List<event>> found;
    public List<epdaH_configuration<state, event, stack>> successor;

    public DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solution(
            Abstract_Problem problem,
            List<epdaH_configuration<state, event, stack>> successor) {
        super(problem);
        this.successor = successor;
    }


}
