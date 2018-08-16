package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_02_SDPDA_TO_CFG_OPT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solver.class.getName());

    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem<state, event, stack> casted_problem = (T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Problem<state, event, stack>) problem;
        epda<state, event, stack> epda = casted_problem.epda;
        state state = casted_problem.state;
        int k = casted_problem.k;
        Map<state, Set<stack>> has_auxiliary_for = casted_problem.has_auxiliary_for;


        List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> input = new ArrayList<>();
        for (stack s : has_auxiliary_for.get(state)) {
            tuple3<state, General_List<stack>, option__abstract<General_Integer>>
                    elem = new tuple3<>(state, new General_List<>(General_List.list_by_enumeration(s)), new option__Some<>(new General_Integer(1)));
            input.add(elem);
        }

        List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2 = new ArrayList<>();
        List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3 = new ArrayList<>();

        long last = System.currentTimeMillis();

        int num = 0;
        for (tuple3<state, General_List<stack>, option__abstract<General_Integer>> candidate : input) {
            long now = System.currentTimeMillis();
            num++;
            if (now - last > 10000 && log.isInfoEnabled()) {
                log.info("num=" + num + ", max=" + input.size());
                last = now;
            }

            stack A = candidate.get_value2().get_value().get(0);
            boolean l2added = false;

            accessibility_graph<state, stack> S = new accessibility_graph<>();
            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>
                    todo = new HashSet<>();
            todo.add(new option__Some<>(candidate));
            while (!todo.isEmpty()) {
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem : new HashSet<>(todo)) {
                    if (elem instanceof option__None)
                        throw new RuntimeException();
                    else {
                        tuple3<state, General_List<stack>, option__abstract<General_Integer>> elemC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem).the();
                        if (elemC.get_value3().equals(new option__Some<>(new General_Integer(0))))
                            todo.remove(elem);
                        else {
                            if (!l2added && epda.get_epda_marking().contains(elemC.get_value1())) {
                                result_l2.add(new l2_l3_nonterminals__cons_l2<>(candidate.get_value1(), A));
                                l2added = true;
                            }
                            if (elemC.get_value3().equals(new option__None<>())
                                    || elemC.get_value3().equals(new option__Some<>(new General_Integer(1))))
                                if ((elemC.get_value2().get_value().isEmpty()
                                        && has_auxiliary_for.get(elemC.get_value1()).contains(A))
                                        || (!elemC.get_value2().get_value().isEmpty()
                                        && elemC.get_value2().get_value().get(0).equals(A))) {
                                    for (epda_step_label<state, event, stack> step_label : epda.get_map_source_pop_edges(elemC.get_value1(), A)) {
                                        result_l3.add(new l2_l3_nonterminals__cons_l3<>(candidate.get_value1(), A, step_label.get_edge_trg()));
                                        if (elemC.get_value3().equals(new option__Some<>(new General_Integer(1)))
                                                && !elemC.get_value2().get_value().isEmpty()
                                                && elemC.get_value2().get_value().get(0).equals(A))
                                            todo.remove(elem);
                                    }
                                }
                        }
                    }
                }
                todo = T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT__step_closure__fp_one(epda, S, todo, k, epda.get_edges_by_type_and_stack_and_source());
            }
        }

        solution = new T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solution(problem, result_l2, result_l3);
    }
}
