package multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epdaH_configuration;
import multithreading.general.abstract_components.Abstract_Problem;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state extends General, event extends General, stack extends General> extends Abstract_Problem {

    public final epda<state, event, stack> epda;
    public final epdaH_configuration<state, event, stack> configuration;
    public final String test;
    public final int number;
    public final int max_depth;
    public final int depth;

    public DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem(
            datatypes.formalisms.epda<state, event, stack> epda,
            epdaH_configuration<state, event, stack> configuration,
            String test,
            int number,
            int max_depth,
            int depth) {
        super(true, true);
        this.epda = epda;
        this.configuration = configuration;
        this.test = test;
        this.number = number;
        this.max_depth = max_depth;
        this.depth = depth;
    }

}
