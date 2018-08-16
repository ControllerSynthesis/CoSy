package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda_step_label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public abstract class cfg_tree_node<state extends General, event extends General, stack extends General> {

    private static Logger log = LogManager.getLogger(cfg_tree_node.class.getName());
    public Map<Boolean, Set<cfg_tree_node<state, event, stack>>> children = new HashMap<>();
    public Map<cfg_tree_node<state, event, stack>, Set<cfg_tree_task__ascent<state, event, stack>>> children_generated_from = new HashMap<>();
    public Map<Boolean, Set<cfg_tree_node<state, event, stack>>> parent = new HashMap<>();
    cfg_tree<state, event, stack> tree;
    boolean meaningful = false;

    Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> productions = new HashSet<>();

    void
    add_and_check(
            cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> production) {
        if (log.isTraceEnabled())
            log.trace("ADDED: " + production);
        if (productions.contains(production))
            throw new RuntimeException("duplicate insertion: " + production);
        productions.add(production);
    }

    void ascent_from_meaningful(cfg_tree_node<state, event, stack> child, cfg_tree_task__ascent<state, event, stack> task) {
        if (child.meaningful) {
            children_generated_from.putIfAbsent(child, new HashSet<>());
            if (children_generated_from.get(child).add(task))
                tree.todo.add(task);
        }
    }

    protected abstract Set<state> get_target_states();

    public void
    addChildAndParentEdge(cfg_tree_node<state, event, stack> child_problem, boolean first, epda_step_label<state, event, stack> edge) {
        children.get(first).add(child_problem);
        child_problem.parent.get(first).add(this);
        if (edge != null)
            tree.set_edge(this, child_problem, edge);
    }

    public abstract void generate();

    public void print(StringBuffer dot_directed_graph, Set<cfg_tree_node<state, event, stack>> done) {
        if (done.contains(this))
            return;
        done.add(this);
        for (cfg_tree_node<state, event, stack> elem : children.get(true)) {
            dot_directed_graph.append("\"" + this.toString() + "\" -> \"" + elem.toString() + "\" [label=\"1: " + tree.get_edge(this, elem) + "\"]");
            elem.print(dot_directed_graph, done);
        }
        for (cfg_tree_node<state, event, stack> elem : children.getOrDefault(false, new HashSet<>())) {
            dot_directed_graph.append("\"" + this.toString() + "\" -> \"" + elem.toString() + "\" [label=\"2: " + tree.get_edge(this, elem) + "\"]");
            elem.print(dot_directed_graph, done);
        }
    }

    public Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> get_productions(Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result, Set<cfg_tree_node<state, event, stack>> visited) {
        if (meaningful) {
            result.addAll(productions);
            for (cfg_tree_node<state, event, stack> elem : children.get(true))
                if (visited.add(elem))
                    elem.get_productions(result, visited);
        }
        return result;
    }

    public abstract void handle(cfg_tree_task__ascent<state, event, stack> task);
}
