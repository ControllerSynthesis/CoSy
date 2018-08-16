package multithreading.general;

import datatypes.basic_java_datatypes.General_Tuple2;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Runnable_Solver extends Thread {

    private static Logger log = LogManager.getLogger(Runnable_Solver.class.getName());
    private ConcurrentHashMap<AtomicBoolean, ReducibleSemaphore> aborted_groups;
    private ConcurrentHashMap<Integer, BlockingDeque<General_Tuple2<AtomicBoolean, Abstract_Problem>>> queues;
    private ConcurrentHashMap<Class<? extends Abstract_Problem>, Class<? extends Abstract_Solver>> problem_to_solver;
    private ConcurrentHashMap<AtomicBoolean, List<Callback>> group_to_callback;
    private AtomicInteger kill_threads;
    private List<Integer> sorted_priorities;
    private Local_Solver parent_solver;

    public Runnable_Solver(
            ConcurrentHashMap<Integer, BlockingDeque<General_Tuple2<AtomicBoolean, Abstract_Problem>>> queues,
            List<Integer> sorted_priorities,
            ConcurrentHashMap<AtomicBoolean, ReducibleSemaphore> aborted_groups,
            ConcurrentHashMap<Class<? extends Abstract_Problem>, Class<? extends Abstract_Solver>> problem_to_solver,
            ConcurrentHashMap<AtomicBoolean, List<Callback>> group_to_callback,
            AtomicInteger kill_threads,
            Local_Solver parent_solver) {
        this.sorted_priorities = sorted_priorities;
        this.queues = queues;
        this.aborted_groups = aborted_groups;
        this.problem_to_solver = problem_to_solver;
        this.group_to_callback = group_to_callback;
        this.kill_threads = kill_threads;
        this.parent_solver = parent_solver;
    }

    @Override
    public void run() {
        while (true) {
            try {
                General_Tuple2<AtomicBoolean, Abstract_Problem> pair = null;
                synchronized (sorted_priorities) {
                    while (pair == null) {
                        if (kill_threads.get() > 0) {
                            synchronized (kill_threads) {
                                if (kill_threads.get() > 0) {
                                    kill_threads.decrementAndGet();
                                    parent_solver.remove_solver(this);
                                    return;
                                }
                            }
                        }

                        for (Integer priority : sorted_priorities) {
                            pair = queues.get(priority).poll();
                            if (pair != null) {
                                break;
                            }
                        }
                        if (pair == null)
                            sorted_priorities.wait();
                    }
                }

                if (pair.getElemA().get()) {
                    aborted_groups.get(pair.getElemA()).release();
                    continue;
                }
                if (pair.getElemB().is_apply_problem_callbacks())
                    for (Callback _callback : group_to_callback.get(pair.getElemA()))
                        _callback.problem_callback(pair.getElemB());
                Abstract_Solver solver = null;
                try {
                    solver = problem_to_solver.get(pair.getElemB().getClass()).newInstance();
                } catch (NullPointerException e) {
                    System.out.println("no solver for problem");
                }
                solver.solve(pair.getElemB(), pair.getElemA());
                if (pair.getElemB().is_apply_solution_callbacks())
                    for (Callback _callback : group_to_callback.get(pair.getElemA()))
                        _callback.solution_callback(solver.get_solution());
                aborted_groups.get(pair.getElemA()).release();
            } catch (InterruptedException e) {
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


}
