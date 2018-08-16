package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_tree_task__decent<state extends General, event extends General, stack extends General> extends cfg_tree_task<state, event, stack> {

    cfg_tree_node<state, event, stack> node;

    public cfg_tree_task__decent(cfg_tree_node<state, event, stack> node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "TASK: " + node;
    }

}
