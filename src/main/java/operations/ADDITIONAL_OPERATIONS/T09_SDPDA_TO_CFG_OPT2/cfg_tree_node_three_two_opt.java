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
public class cfg_tree_node_three_two_opt<state extends General, event extends General, stack extends General> extends cfg_tree_node<state, event, stack> {

    private static Logger log = LogManager.getLogger(cfg_tree_node_three_two_opt.class.getName());

    final state state1;
    final stack stack1;
    final Set<state> result = new HashSet<>();
    final Map<cfg_tree_node_three_opt<state, event, stack>, Set<cfg_tree_node_two<state, event, stack>>> sibling = new HashMap<>();
    final stack stack2;

    public cfg_tree_node_three_two_opt(cfg_tree<state, event, stack> tree, state state1, stack stack1, stack stack2) {
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
        return "L3L2(" + state1 + ", " + stack1 + ", ?, " + stack2 + " :: ? = " + result + ")";
    }

    @Override
    protected Set<state> get_target_states() {
        return new HashSet<>(result);
    }

    @Override
    public void generate() {
        if (log.isTraceEnabled())
            log.trace("generate: " + this);
        throw new RuntimeException();
    }


    @Override
    public Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> get_productions(Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>> result, Set<cfg_tree_node<state, event, stack>> visited) {
        if (meaningful) {
            for (Map.Entry<cfg_tree_node_three_opt<state, event, stack>, Set<cfg_tree_node_two<state, event, stack>>> pair : sibling.entrySet()) {
                cfg_tree_node_three_opt<state, event, stack> child1 = pair.getKey();
                for (cfg_tree_node_two<state, event, stack> child2 : pair.getValue())
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

    @Override
    public void handle(cfg_tree_task__ascent<state, event, stack> task) {
        if (log.isTraceEnabled())
            log.trace("handle: " + this);
        if (task instanceof cfg_tree_task__ascent_from_three_opt) {
            cfg_tree_task__ascent_from_three_opt<state, event, stack> task_casted = (cfg_tree_task__ascent_from_three_opt<state, event, stack>) task;
            if (!children.get(true).contains(task.child))
                throw new RuntimeException();
            // generate second child node
            if (log.isTraceEnabled())
                log.trace("handle generate second: " + this);
            cfg_tree_node_three_opt<state, event, stack> casted_child = (cfg_tree_node_three_opt<state, event, stack>) task.child;
            cfg_tree_node_two<state, event, stack> already_existing = (cfg_tree_node_two<state, event, stack>) tree.get_problem(tree.two_problems, task_casted.target, stack2);
            if (already_existing == null) {
                cfg_tree_node_two<state, event, stack> second_problem = new cfg_tree_node_two<>(tree, task_casted.target, stack2);
                tree.add_problem(second_problem);
                addChildAndParentEdge(second_problem, false, null);
                tree.todo.add(new cfg_tree_task__decent<>(second_problem));
                this.sibling.putIfAbsent(casted_child, new HashSet<>());
                this.sibling.get(casted_child).add(second_problem);
            } else {
                addChildAndParentEdge(already_existing, false, null);
                this.sibling.putIfAbsent(casted_child, new HashSet<>());
                this.sibling.get(casted_child).add(already_existing);
                ascent_from_meaningful(already_existing, new cfg_tree_task__ascent_from_two<>(this, already_existing));
            }
        } else if (task instanceof cfg_tree_task__ascent_from_two) {
            cfg_tree_task__ascent_from_two<state, event, stack> task_casted = (cfg_tree_task__ascent_from_two<state, event, stack>) task;
            cfg_tree_node_two<state, event, stack> casted_child = (cfg_tree_node_two<state, event, stack>) task.child;
            if (!children.get(false).contains(task.child))
                throw new RuntimeException();
            // handle finished
            if (log.isTraceEnabled())
                log.trace("handle finished: " + this);
            meaningful = true;
            this.result.add(casted_child.state);
            if (!task.child.meaningful)
                throw new RuntimeException();
            meaningful = true;
            for (cfg_tree_node<state, event, stack> elem : parent.get(true))
                for (state state : this.result)
                    elem.ascent_from_meaningful(this, new cfg_tree_task__ascent_from_three_two_opt<>(elem, this, state));
        } else throw new RuntimeException();
    }

}
