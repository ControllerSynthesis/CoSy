package multithreading.general.abstract_components;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public interface Callback {

    void solution_callback(Abstract_Solution solution);

    void problem_callback(Abstract_Problem problem);

    void finalize_callback();
}
