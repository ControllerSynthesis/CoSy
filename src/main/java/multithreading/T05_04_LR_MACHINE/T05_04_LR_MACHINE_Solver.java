package multithreading.T05_04_LR_MACHINE;

import datatypes.basic_datatypes_of_algorithm.cfg_item;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import operations.p01__ENFORCE_BLOCKFREE.T05_03_VALID_ITEM_SETS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_04_LR_MACHINE_Solver<nonterminal extends General, event extends General> extends Abstract_Solver {

    private static final General_Integer ZERO = new General_Integer(0);
    private static final List<General_Integer> ZERO_LIST = General_List.list_by_enumeration(ZERO);
    private static Logger log = LogManager.getLogger(T05_04_LR_MACHINE_Solver.class.getName());

    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        T05_04_LR_MACHINE_Problem casted_problem = (T05_04_LR_MACHINE_Problem) problem;
        final List<General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>>> this_tasks = casted_problem.this_tasks;
        final cfg<nonterminal, event> cfg = casted_problem.cfg;
        final int k = casted_problem.k;


        long start1 = System.currentTimeMillis();
        final Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> old_obtained;
        synchronized (casted_problem.obtained) {
            old_obtained = new HashMap<>(casted_problem.obtained);
        }

        Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>>
                found = new HashSet<>();


        Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> new_obtained = new HashMap<>();
        for (General_Tuple2<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>> elem : this_tasks) {
            General_Set<cfg_item<nonterminal, event>> set = T05_03_VALID_ITEM_SETS.F_VALID_ITEM_SET_GOTO(cfg, k, elem.getElemB(), elem.getElemA(), old_obtained, new_obtained);
            if (!set.get_value().isEmpty()) {
                found.add(new epda_step_label<>(elem.getElemA(), new option__Some<>(elem.getElemB()),
                        ZERO_LIST, ZERO_LIST, set));
            }
        }

        long end1 = System.currentTimeMillis();
        long duration1 = (end1 - start1);

        if (log.isInfoEnabled())
            log.info("this_tasks = " + this_tasks.size()
                    + ", duration1 = " + duration1 + ", ms per task = " + ((duration1 * 1.0) / this_tasks.size())
                    + ", old_obtained = " + old_obtained.size()
                    + ", new_obtained = " + new_obtained.size());


        solution = new T05_04_LR_MACHINE_Solution(problem, found, new_obtained);
    }
}
