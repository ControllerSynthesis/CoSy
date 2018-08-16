package multithreading.T05_04_LR_MACHINE;

import datatypes.basic_datatypes_of_algorithm.cfg_item;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;

import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_04_LR_MACHINE_Solution<nonterminal extends General, event extends General> extends Abstract_Solution {

    public Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> found;
    public Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained;

    public T05_04_LR_MACHINE_Solution(
            Abstract_Problem problem,
            Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> found,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained) {
        super(problem);
        this.found = found;
        this.obtained = obtained;
    }
}
