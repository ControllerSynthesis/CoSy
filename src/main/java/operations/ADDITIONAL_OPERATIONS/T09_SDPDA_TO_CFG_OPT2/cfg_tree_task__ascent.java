package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public abstract class cfg_tree_task__ascent<state extends General, event extends General, stack extends General> extends cfg_tree_task<state, event, stack> {

    cfg_tree_node<state, event, stack> parent;
    cfg_tree_node<state, event, stack> child;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        cfg_tree_task__ascent<?, ?, ?> that = (cfg_tree_task__ascent<?, ?, ?>) o;

        if (!parent.equals(that.parent)) return false;
        return child.equals(that.child);
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + child.hashCode();
        return result;
    }
}
