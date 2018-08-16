package multithreading.general.abstract_components;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Abstract_Problem {

    private final boolean apply_problem_callbacks;
    private final boolean apply_solution_callbacks;

    public Abstract_Problem(boolean apply_problem_callbacks, boolean apply_solution_callbacks) {
        this.apply_problem_callbacks = apply_problem_callbacks;
        this.apply_solution_callbacks = apply_solution_callbacks;
    }

    public boolean is_apply_problem_callbacks() {
        return apply_problem_callbacks;
    }

    public boolean is_apply_solution_callbacks() {
        return apply_solution_callbacks;
    }
}
