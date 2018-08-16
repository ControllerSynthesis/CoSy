package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l2;
import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l3;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.basic_java_datatypes.Linked_Hash_Set;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_tree<state extends General, event extends General, stack extends General> {

    private static Logger log = LogManager.getLogger(cfg_tree.class.getName());
    final epda<state, event, stack> epda;
    final Set<state> marking_states;
    final Map<state, Map<stack, cfg_tree_node<state, event, stack>>> two_problems = new HashMap<>();
    final Map<state, Map<stack, cfg_tree_node<state, event, stack>>> three_problems = new HashMap<>();
    // General_Tuple2: (node,null): construct children of node
    // General_Tuple2: (node1,node2): node2 finished, recheck node1 for that
    final Linked_Hash_Set<cfg_tree_task<state, event, stack>> todo = new Linked_Hash_Set<>();
    private final Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse;
    private final Map<state, Map<stack, l2_l3_nonterminals__cons_l2<state, stack>>> S2map = new HashMap<>();
    private final Map<state, Map<stack, Map<state, l2_l3_nonterminals__cons_l3<state, stack>>>> S3map = new HashMap<>();
    private final Map<cfg_tree_node<state, event, stack>, Map<cfg_tree_node<state, event, stack>, epda_step_label<state, event, stack>>> used_edge = new HashMap<>();

    public cfg_tree(epda<state, event, stack> epda,
                    Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        this.epda = epda;
        this.reverse = reverse;
        this.marking_states = new HashSet<>(epda.get_epda_marking());
    }

    void
    reverse_put(
            cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> production,
            epda_step_label<state, event, stack> epda_edge) {
        if (reverse != null)
            reverse.put(production, epda_edge);
    }

    void
    set_edge(
            cfg_tree_node<state, event, stack> node_parent,
            cfg_tree_node<state, event, stack> node_child,
            epda_step_label<state, event, stack> edge) {
        used_edge.putIfAbsent(node_parent, new HashMap<>());
        used_edge.get(node_parent).putIfAbsent(node_child, edge);
    }

    epda_step_label<state, event, stack>
    get_edge(
            cfg_tree_node<state, event, stack> node_parent,
            cfg_tree_node<state, event, stack> node_child) {
        if (!used_edge.containsKey(node_parent))
            return null;
        return used_edge.get(node_parent).get(node_child);
    }

    void
    add_problem(
            cfg_tree_node<state, event, stack> start) {
        if (start instanceof cfg_tree_node_two) {
            cfg_tree_node_two<state, event, stack> casted = (cfg_tree_node_two<state, event, stack>) start;
            two_problems.putIfAbsent(casted.state, new HashMap<>());
            two_problems.get(casted.state).put(casted.stack, start);
        } else if (start instanceof cfg_tree_node_three_opt) {
            cfg_tree_node_three_opt<state, event, stack> casted = (cfg_tree_node_three_opt<state, event, stack>) start;
            three_problems.putIfAbsent(casted.state1, new HashMap<>());
            three_problems.get(casted.state1).put(casted.stack, start);
        }
    }

    cfg_tree_node<state, event, stack>
    get_problem(
            Map<state, Map<stack, cfg_tree_node<state, event, stack>>> two_problems,
            state state,
            stack stack) {
        if (two_problems.containsKey(state)) {
            return two_problems.get(state).get(stack);
        } else
            return null;
    }

    l2_l3_nonterminals__cons_l2<state, stack>
    get_l2_nonterminal(
            state state,
            stack stack) {
        S2map.putIfAbsent(state, new HashMap<>());
        S2map.get(state).putIfAbsent(stack, new l2_l3_nonterminals__cons_l2<>(state, stack));
        return S2map.get(state).get(stack);
    }

    l2_l3_nonterminals__cons_l3<state, stack>
    get_l3_nonterminal(
            state state1,
            stack stack,
            state state2) {
        S3map.putIfAbsent(state1, new HashMap<>());
        S3map.get(state1).putIfAbsent(stack, new HashMap<>());
        S3map.get(state1).get(stack).putIfAbsent(state2, new l2_l3_nonterminals__cons_l3<>(state1, stack, state2));
        return S3map.get(state1).get(stack).get(state2);
    }


    private boolean
    all_childs_meaningful(
            cfg_tree_node<state, event, stack> problem_casted,
            Map<cfg_tree_node<state, event, stack>, Set<cfg_tree_node<state, event, stack>>> child) {
        for (cfg_tree_node<state, event, stack> elem : child.getOrDefault(problem_casted, new HashSet<>()))
            if (!elem.meaningful)
                return false;
        return true;
    }


    public General_Tuple2<Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>, l2_l3_nonterminals__cons_l2<state, stack>> generate() {
        cfg_tree_node_two<state, event, stack> start = new cfg_tree_node_two<>(this, epda.get_epda_initial(), epda.get_epda_box());
        add_problem(start);
        todo.add(new cfg_tree_task__decent<>(start));


        int number = 0;
        while (!todo.isEmpty()) {
            if (log.isInfoEnabled() && number % 10000 == 0) {
                log.info("TODO: number = " + number + ", TODO = " + todo.size());
            }
            if (log.isTraceEnabled()) {
                log.trace("==========================================================================================");
                log.trace("number: " + number);
                log.trace("PRE STATE");
                log_state();
                log.trace("..........................................................................................");
            }

            number++;
            cfg_tree_task<state, event, stack> todo_elem = todo.remove();
            if (log.isTraceEnabled())
                log.trace("todo_elem = " + todo_elem);

            if (todo_elem instanceof cfg_tree_task__decent) {
                cfg_tree_task__decent<state, event, stack> todo_elem_casted = (cfg_tree_task__decent<state, event, stack>) todo_elem;
                todo_elem_casted.node.generate();
            } else if (todo_elem instanceof cfg_tree_task__ascent) {
                cfg_tree_task__ascent<state, event, stack> todo_elem_casted = (cfg_tree_task__ascent<state, event, stack>) todo_elem;
                todo_elem_casted.parent.handle(todo_elem_casted);
            } else throw new RuntimeException();

//            if (all_childs_meaningful(problem_current, child)) {
//                if (log.isTraceEnabled()) log.trace("all_childs_meaningful of " + problem_current);
//                problem_current.meaningful = true;
//                for (cfg_tree_node<state, event, stack> parent_elem : parent.getOrDefault(problem_current, new HashSet<>()))
//                    if (!parent_elem.meaningful) {
//                        if (all_childs_meaningful(parent_elem, child)) {
//                            if (log.isTraceEnabled()) log.trace("all_childs_meaningful of " + parent_elem);
//                            todo.add(parent_elem);
//                        } else {
//                            if (log.isTraceEnabled()) log.trace("NOT all_childs_meaningful of " + parent_elem);
//                        }
//                    } else if (log.isTraceEnabled()) log.trace("already meaningful " + parent);
//            } else {
//                if (log.isTraceEnabled()) log.trace("NOT all_childs_meaningful of " + problem_current);
//            }
            if (log.isTraceEnabled()) {
                log.trace("..........................................................................................");
                log.trace("POST STATE");
                log_state();
            }

        }

        if (log.isTraceEnabled()) {
            StringBuffer dot_directed_graph = new StringBuffer();
            start.print(dot_directed_graph, new HashSet<>());
            try {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/cfgtree.dot"), "utf-8"))) {
                    writer.write("digraph G {\n");
                    writer.write(dot_directed_graph.toString());
                    writer.write("}\n");
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return new General_Tuple2<>(start.get_productions(new HashSet<>(), new HashSet<>()), get_l2_nonterminal(start.state, start.stack));

    }


    private void log_state() {
        for (cfg_tree_task<state, event, stack> elem : todo.getElements()) {
            log.trace("TODO: " + elem);
        }
//        for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : productions)
//            log.trace("production: " + elem);
//                for (state state : two_problems.keySet())
//                    for (stack stack : two_problems.get(state).keySet())
//                        log.trace("problem: " + state + ":" + stack + ":" + two_problems.get(state).get(stack));
//                for (state state : three_problems.keySet())
//                    for (stack stack : three_problems.get(state).keySet())
//                        log.trace("problem: " + state + ":" + stack + ":" + three_problems.get(state).get(stack));
    }


}
