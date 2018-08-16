package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_tree_task__ascent_from_three_opt<state extends General, event extends General, stack extends General> extends cfg_tree_task__ascent<state, event, stack> {

    state target;
    boolean first;

    public cfg_tree_task__ascent_from_three_opt(cfg_tree_node<state, event, stack> parent, cfg_tree_node<state, event, stack> child, state target, boolean first) {
        this.parent = parent;
        this.child = child;
        this.target = target;
        this.first = first;
        if (!(child instanceof cfg_tree_node_three_opt))
            throw new RuntimeException();
        if (parent instanceof cfg_tree_node_three_two_opt && !first)
            throw new RuntimeException();
    }

    @Override
    public String toString() {
        return "TASK: " + parent + " -> " + child + " @ " + target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        cfg_tree_task__ascent_from_three_opt<?, ?, ?> that = (cfg_tree_task__ascent_from_three_opt<?, ?, ?>) o;

        if (first != that.first) return false;
        return target.equals(that.target);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + (first ? 1 : 0);
        return result;
    }
}
