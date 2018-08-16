package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_tree_task__ascent_from_two<state extends General, event extends General, stack extends General> extends cfg_tree_task__ascent<state, event, stack> {


    public cfg_tree_task__ascent_from_two(cfg_tree_node<state, event, stack> parent, cfg_tree_node<state, event, stack> child) {
        this.parent = parent;
        this.child = child;
        if (!(child instanceof cfg_tree_node_two))
            throw new RuntimeException();
    }

    @Override
    public String toString() {
        return "TASK: " + parent + " -> " + child;
    }


}
