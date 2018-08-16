package multithreading.general.abstract_components;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public abstract class Abstract_Solver {

    protected Abstract_Solution solution;

    public Abstract_Solver() {
    }

    public abstract void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException;

    public Abstract_Solution get_solution() {
        return solution;
    }
}
