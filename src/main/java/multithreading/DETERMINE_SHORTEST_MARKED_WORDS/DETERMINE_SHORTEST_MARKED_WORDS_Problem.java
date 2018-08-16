package multithreading.DETERMINE_SHORTEST_MARKED_WORDS;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import multithreading.general.abstract_components.Abstract_Problem;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_Problem<state extends General, event extends General, stack extends General> extends Abstract_Problem {

    public final epda<state, event, stack> epda;
    public final state q;
    public final String test;
    public final int number;
    public final int max_depth;

    public DETERMINE_SHORTEST_MARKED_WORDS_Problem(
            epda<state, event, stack> epda,
            state q,
            String test,
            int number,
            int max_depth) {
        super(false, true);
        this.epda = epda;
        this.test = test;
        this.q = q;
        this.number = number;
        this.max_depth = max_depth;
    }

}
