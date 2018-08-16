package multithreading.general;

import datatypes.basic_java_datatypes.General_Tuple2;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Local_Solver {

    private static Logger log = LogManager.getLogger(Local_Solver.class.getName());
    private final int starting_threads;
    private AtomicInteger kill_threads = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, BlockingDeque<General_Tuple2<AtomicBoolean, Abstract_Problem>>> queues = new ConcurrentHashMap<>();
    private List<Integer> sorted_priorities = Collections.synchronizedList(new ArrayList<Integer>());
    private ConcurrentHashMap<AtomicBoolean, ReducibleSemaphore> aborted_groups = new ConcurrentHashMap<>();
    private AtomicBoolean abort = new AtomicBoolean(false);
    private ConcurrentHashMap<Class<? extends Abstract_Problem>, Class<? extends Abstract_Solver>> problem_to_solver = new ConcurrentHashMap<>();
    private ConcurrentHashMap<AtomicBoolean, List<Callback>> group_to_callback = new ConcurrentHashMap<>();
    private List<Runnable_Solver> runnable_solverList = new ArrayList<>();

    public Local_Solver(boolean automatic_threads, int delta, int thread_count) {
        if (thread_count == 0 && automatic_threads) {
            thread_count = Runtime.getRuntime().availableProcessors();
            thread_count += delta;
        }
        if (thread_count <= 0)
            throw new RuntimeException("illegal number of Runnable_Solver");
        this.starting_threads = thread_count;
        increase_threads(thread_count);
        internal_add_priority(0);
    }

    public int get_number_of_solvers() {
        return runnable_solverList.size();
    }

    private boolean internal_add_priority(int priority) {
        synchronized (sorted_priorities) {
            if (sorted_priorities.contains(priority))
                return true;
            int insert_before = sorted_priorities.size();
            for (int i = 0; i < sorted_priorities.size(); i++) {
                if (sorted_priorities.get(i) > priority) {
                    insert_before = i;
                    break;
                }
            }
            sorted_priorities.add(insert_before, priority);
            queues.put(priority, new LinkedBlockingDeque<>());
            return false;
        }
    }

    public void register_solver(Class<? extends Abstract_Problem> problem, Class<? extends Abstract_Solver> solver) {
        problem_to_solver.put(problem, solver);
    }

    public void kill_runnables() {
        kill_threads.set(Integer.MAX_VALUE);

        synchronized (sorted_priorities) {
            sorted_priorities.notifyAll();
        }

        try {
            synchronized (runnable_solverList) {
                while (!runnable_solverList.isEmpty())
                    runnable_solverList.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean add_priority(int priority) {
        if (priority <= 0)
            throw new RuntimeException("priorities must be > 0 (" + priority + ")");
        return internal_add_priority(priority);
    }

    public void add_problem(int priority, AtomicBoolean group, Abstract_Problem problem_abstract) {
        if (!queues.containsKey(priority))
            add_priority(priority);
        try {
            ReducibleSemaphore semaphore = aborted_groups.get(group);
            if (semaphore == null) {
                semaphore = new ReducibleSemaphore(1);
                aborted_groups.put(group, semaphore);
            }
            semaphore.reducePermits(1);
            queues.get(priority).put(new General_Tuple2<AtomicBoolean, Abstract_Problem>(group, problem_abstract));
            //queues.get(priority).putFirst(new General_Tuple2<AtomicBoolean, Abstract_Problem>(group, problem_abstract));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        if (false && log.isInfoEnabled()) {
            int sum = 0;
            for (BlockingQueue<General_Tuple2<AtomicBoolean, Abstract_Problem>> queue : queues.values())
                sum += queue.size();
            log.info("problems: " + sum + " in " + queues.keySet().size() + " queues");
        }
        synchronized (sorted_priorities) {
            sorted_priorities.notifyAll();
        }
    }

    public String thread_states() {
        String result = "";
        for (Runnable_Solver elem : runnable_solverList) {
            result += "\n---";
            for (StackTraceElement elemX : elem.getStackTrace())
                result += "\n" + elemX.getFileName() + ":" + elemX.getMethodName() + ":" + elemX.getLineNumber();
        }
        return result;
    }

    public void wait_for_completion(AtomicBoolean group) throws InterruptedException {
        Config.instance.local_solver.increase_threads(1);
        aborted_groups.get(group).acquire();
        for (Callback _callback : group_to_callback.get(group))
            _callback.finalize_callback();
        aborted_groups.get(group).release();
        aborted_groups.remove(group);
        group_to_callback.remove(group);
        Config.instance.local_solver.decrease_threads(1);
    }

    public void register_callbacks(AtomicBoolean abort, List<Callback> callbackList) {
        group_to_callback.put(abort, callbackList);
    }

    public void increase_threads(int delta) {
        synchronized (kill_threads) {
            if (false && log.isInfoEnabled())
                log.info("increase_threads : " + delta + " : " + kill_threads.get());
            if (kill_threads.get() <= delta) {
                delta -= kill_threads.get();
                kill_threads.set(0);
            } else {
                kill_threads.addAndGet(-delta);
                return;
            }
        }
        if (false && log.isInfoEnabled())
            log.info("increase_threads : updated_delta : " + delta + " : " + kill_threads.get());
        for (int i = 0; i < delta; i++) {
            Runnable_Solver runnable_solver = new Runnable_Solver(queues, sorted_priorities,
                    aborted_groups,
                    problem_to_solver,
                    group_to_callback,
                    kill_threads,
                    this);
            synchronized (runnable_solverList) {
                runnable_solverList.add(runnable_solver);
            }
            runnable_solver.start();
        }
    }

    public void decrease_threads(int delta) {
        synchronized (kill_threads) {
            kill_threads.addAndGet(delta);
        }
    }

    public int get_starting_thread_count() {
        return starting_threads;
    }


    public void remove_solver(Runnable_Solver solver) {
        synchronized (runnable_solverList) {
            runnable_solverList.remove(solver);
            runnable_solverList.notifyAll();
        }

    }

    public List<String> get_stat() {
        List<String> result = new ArrayList<>();
        synchronized (queues) {
            for (BlockingDeque<General_Tuple2<AtomicBoolean, Abstract_Problem>> queue : queues.values()) {
                result.add("" + queue.size());
            }
        }
        return result;
    }
}
