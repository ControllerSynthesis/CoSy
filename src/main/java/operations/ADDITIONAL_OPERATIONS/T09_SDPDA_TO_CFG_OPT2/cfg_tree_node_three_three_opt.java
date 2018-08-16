package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg_step_label;
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
public class cfg_tree_node_three_three_opt<state extends General, event extends General, stack extends General> extends cfg_tree_node<state, event, stack> {

    private static Logger log = LogManager.getLogger(cfg_tree_node_three_three_opt.class.getName());

    final state state1;
    final stack stack1;
    final Map<state, Set<state>> result = new HashMap<>();
    final Map<cfg_tree_node_three_opt<state, event, stack>, Set<cfg_tree_node_three_opt<state, event, stack>>> sibling = new HashMap<>();
    final stack stack2;


    public cfg_tree_node_three_three_opt(cfg_tree<state, event, stack> tree, state state1, stack stack1, stack stack2) {
        this.tree = tree;
        children.put(true, new HashSet<>());
        parent.put(true, new HashSet<>());
        children.put(false, new HashSet<>());
        parent.put(false, new HashSet<>());
        this.state1 = state1;
        this.stack1 = stack1;
        this.stack2 = stack2;
    }

    @Override
    public String toString() {
        return "L3L3(" + state1 + ", " + stack1 + ", ?, " + stack2 + ", ? :: " + result + ")";
    }

    @Override
    public void generate() {
        if (log.isTraceEnabled())
            log.trace("generate: " + this);
        throw new RuntimeException();
    }

    @Override
    protected Set<state> get_target_states() {
        Set<state> resultset = new HashSet<>();
        for (Map.Entry<state, Set<state>> elem : result.entrySet())
            resultset.addAll(elem.getValue());
        return resultset;
    }

    @Override
    public void handle(cfg_tree_task__ascent<state, event, stack> task) {
        if (log.isTraceEnabled())
            log.trace("handle: " + this);
        cfg_tree_task__ascent_from_three_opt<state, event, stack> task_casted = (cfg_tree_task__ascent_from_three_opt<state, event, stack>) task;
        if (task_casted.first && children.get(true).contains(task_casted.child)) {
            // generate second child node
            if (log.isTraceEnabled())
                log.trace("handle generate second: " + this);
            cfg_tree_node_three_opt<state, event, stack> casted_child = (cfg_tree_node_three_opt<state, event, stack>) task_casted.child;
            cfg_tree_node_three_opt<state, event, stack> already_existing = (cfg_tree_node_three_opt<state, event, stack>) tree.get_problem(tree.three_problems, task_casted.target, stack2);
            if (already_existing == null) {
                cfg_tree_node_three_opt<state, event, stack> second_problem = new cfg_tree_node_three_opt<>(tree, task_casted.target, stack2);
                tree.add_problem(second_problem);
                addChildAndParentEdge(second_problem, false, null);
                tree.todo.add(new cfg_tree_task__decent<>(second_problem));
                this.sibling.putIfAbsent(casted_child, new HashSet<>());
                this.sibling.get(casted_child).add(second_problem);
            } else {
                addChildAndParentEdge(already_existing, false, null);
                this.sibling.putIfAbsent(casted_child, new HashSet<>());
                this.sibling.get(casted_child).add(already_existing);
                for (state state : already_existing.state2)
                    ascent_from_meaningful(already_existing, new cfg_tree_task__ascent_from_three_opt<>(this, already_existing, state, false));
            }
        } else if (!task_casted.first && children.get(false).contains(task_casted.child)) {
            // handle finished
            if (log.isTraceEnabled())
                log.trace("handle finished: " + this);
            meaningful = true;
            if (task_casted.child instanceof cfg_tree_node_three_opt) {
                cfg_tree_node_three_opt<state, event, stack> casted_child = (cfg_tree_node_three_opt<state, event, stack>) task.child;
                if (!task_casted.child.meaningful)
                    throw new RuntimeException();
                this.result.putIfAbsent(casted_child.state1, new HashSet<>());
                this.result.get(casted_child.state1).add(task_casted.target);
                meaningful = true;
                for (cfg_tree_node<state, event, stack> elem : parent.get(true))
                    for (Map.Entry<state, Set<state>> entry : result.entrySet())
                        for (state target : entry.getValue())
                            elem.ascent_from_meaningful(this, new cfg_tree_task__ascent_from_three_three_opt<>(elem, this, entry.getKey(), target));
            } else throw new RuntimeException();
        } else throw new RuntimeException();
    }

    @Override
    public Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> get_productions(Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result, Set<cfg_tree_node<state, event, stack>> visited) {
        if (meaningful) {
            for (Map.Entry<cfg_tree_node_three_opt<state, event, stack>, Set<cfg_tree_node_three_opt<state, event, stack>>> pair : sibling.entrySet()) {
                cfg_tree_node_three_opt<state, event, stack> child1 = pair.getKey();
                for (cfg_tree_node_three_opt<state, event, stack> child2 : pair.getValue())
                    if (child1.meaningful && child2.meaningful) {
                        if (visited.add(child1))
                            child1.get_productions(result, visited);
                        if (visited.add(child2))
                            child2.get_productions(result, visited);
                    }
            }
        }
        return result;
    }

}
