package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda_step_label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_tree_node_three_opt<state extends General, event extends General, stack extends General> extends cfg_tree_node<state, event, stack> {
    private static Logger log = LogManager.getLogger(cfg_tree_node_three_opt.class.getName());

    final state state1;
    final Set<state> state2 = new HashSet<>();
    final stack stack;
    private boolean children_generated = false;

    public cfg_tree_node_three_opt(cfg_tree<state, event, stack> tree, state state1, stack stack) {
        this.tree = tree;
        children.put(true, new HashSet<>());
        parent.put(true, new HashSet<>());
        parent.put(false, new HashSet<>());
        this.state1 = state1;
        this.stack = stack;
    }

    @Override
    protected Set<state> get_target_states() {
        return new HashSet<>(state2);
    }

    @Override
    public String toString() {
        return "L3(" + state1 + ", " + stack + ", ? :: ? = " + state2 + ")";
    }

    @Override
    public void generate() {
        if (log.isTraceEnabled())
            log.trace("generate: " + this);
        if (children_generated)
            throw new RuntimeException();
        children_generated = true;
        for (epda_step_label<state, event, stack> edge : tree.epda.get_map_source_stack_edges(state1, stack)) {
            if (log.isTraceEnabled()) log.trace(edge);
            if (edge.get_edge_event() instanceof option__Some) {
                cfg_tree_node_three_opt<state, event, stack> already_existing = (cfg_tree_node_three_opt<state, event, stack>) tree.get_problem(tree.three_problems, edge.get_edge_trg(), edge.get_edge_push().get(0));
                if (already_existing == null) {
                    cfg_tree_node_three_opt<state, event, stack> changed_state = new cfg_tree_node_three_opt<>(tree, edge.get_edge_trg(), edge.get_edge_push().get(0));
                    tree.add_problem(changed_state);
                    addChildAndParentEdge(changed_state, true, edge);
                    tree.todo.add(new cfg_tree_task__decent<>(changed_state));
                } else {
                    addChildAndParentEdge(already_existing, true, edge);
                    for (state state : already_existing.state2)
                        ascent_from_meaningful(already_existing, new cfg_tree_task__ascent_from_three_opt<>(this, already_existing, state, true));
                }
            } else if (edge.get_edge_push().size() == 2) {
                // pushing edge
                cfg_tree_node_three_three_opt<state, event, stack> derived
                        = new cfg_tree_node_three_three_opt<>(tree, edge.get_edge_trg(), edge.get_edge_push().get(0), edge.get_edge_push().get(1));
                addChildAndParentEdge(derived, true, edge);
                //add problem for first part
                cfg_tree_node_three_opt<state, event, stack> already_existing = (cfg_tree_node_three_opt<state, event, stack>) tree.get_problem(tree.three_problems, edge.get_edge_trg(), edge.get_edge_push().get(0));
                if (already_existing == null) {
                    cfg_tree_node_three_opt<state, event, stack> first_problem = new cfg_tree_node_three_opt<>(tree, edge.get_edge_trg(), edge.get_edge_push().get(0));
                    tree.add_problem(first_problem);
                    tree.set_edge(this, first_problem, edge);
                    derived.addChildAndParentEdge(first_problem, true, null);
                    tree.todo.add(new cfg_tree_task__decent<>(first_problem));
                } else {
                    derived.addChildAndParentEdge(already_existing, true, null);
                    for (state state : already_existing.state2)
                        derived.ascent_from_meaningful(already_existing, new cfg_tree_task__ascent_from_three_opt<>(derived, already_existing, state, true));
                }
            } else {
                // popping edge
                cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> production = new cfg_step_label<>(tree.get_l3_nonterminal(state1, stack, edge.get_edge_trg()), new ArrayList<>());
                add_and_check(production);
                tree.reverse_put(production, edge);
                meaningful = true;
                this.state2.add(edge.get_edge_trg());
                for (boolean first : new boolean[]{true, false})
                    for (cfg_tree_node<state, event, stack> elem : parent.get(first))
                        elem.ascent_from_meaningful(this, new cfg_tree_task__ascent_from_three_opt<>(elem, this, edge.get_edge_trg(), first));
            }
        }
    }


    @Override
    public void handle(cfg_tree_task__ascent<state, event, stack> task) {
        if (log.isTraceEnabled())
            log.trace("handle: " + this);
        if (!task.child.meaningful)
            throw new RuntimeException();
        if (task instanceof cfg_tree_task__ascent_from_three_opt) {
            cfg_tree_task__ascent_from_three_opt<state, event, stack> task_casted = (cfg_tree_task__ascent_from_three_opt<state, event, stack>) task;
            cfg_tree_node_three_opt<state, event, stack> elem_casted = (cfg_tree_node_three_opt<state, event, stack>) task.child;
            state2.add(task_casted.target);
            List<two_elements__abstract<l2_l3_nonterminals__abstract<state, stack>, event>> rhs = new ArrayList<>();
            epda_step_label<state, event, stack> elem = tree.get_edge(this, task.child);
            if (elem.get_edge_event() instanceof option__Some)
                rhs.add(new two_elements__teB<>(((option__Some<event>) elem.get_edge_event()).the()));
            rhs.add(new two_elements__teA<>(tree.get_l3_nonterminal(elem_casted.state1, elem_casted.stack, task_casted.target)));
            cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> production = new cfg_step_label<>(tree.get_l3_nonterminal(state1, stack, task_casted.target), rhs);
            add_and_check(production);
            tree.reverse_put(production, elem);
        } else if (task instanceof cfg_tree_task__ascent_from_three_three_opt) {
            cfg_tree_task__ascent_from_three_three_opt<state, event, stack> task_casted = (cfg_tree_task__ascent_from_three_three_opt<state, event, stack>) task;
            cfg_tree_node_three_three_opt<state, event, stack> elem_casted = (cfg_tree_node_three_three_opt<state, event, stack>) task.child;
            state2.add(task_casted.target);
            List<two_elements__abstract<l2_l3_nonterminals__abstract<state, stack>, event>> rhs = new ArrayList<>();
            rhs.add(new two_elements__teA<>(tree.get_l3_nonterminal(elem_casted.state1, elem_casted.stack1, task_casted.middle)));
            rhs.add(new two_elements__teA<>(tree.get_l3_nonterminal(task_casted.middle, elem_casted.stack2, task_casted.target)));
            cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> production = new cfg_step_label<>(tree.get_l3_nonterminal(state1, stack, task_casted.target), rhs);
            add_and_check(production);
            tree.reverse_put(production, tree.get_edge(this, task.child));
        } else {
            throw new RuntimeException();
        }
        meaningful = true;
        for (boolean next_first : new boolean[]{true, false})
            for (cfg_tree_node<state, event, stack> elem : parent.get(next_first))
                for (state state : this.state2)
                    elem.ascent_from_meaningful(this, new cfg_tree_task__ascent_from_three_opt<>(elem, this, state, next_first));
    }
}

