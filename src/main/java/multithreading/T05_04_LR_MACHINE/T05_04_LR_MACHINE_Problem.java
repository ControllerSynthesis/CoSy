package multithreading.T05_04_LR_MACHINE;

import datatypes.basic_datatypes_of_algorithm.cfg_item;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.cfg;
import multithreading.general.abstract_components.Abstract_Problem;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_04_LR_MACHINE_Problem<nonterminal extends General, event extends General> extends Abstract_Problem {

    public final cfg<nonterminal, event> cfg;
    public final int k;
    public final List<General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>>> this_tasks;
    public final Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained;

    public T05_04_LR_MACHINE_Problem(
            cfg<nonterminal, event> cfg,
            int k,
            List<General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>>> this_tasks,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained) {
        super(false, true);
        this.cfg = cfg;
        this.k = k;
        this.this_tasks = this_tasks;
        this.obtained = obtained;
    }

}
