package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.formalisms.language_check.Semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfgLM_semantics<nonterminal extends General, event extends General>
        implements Semantic<cfg<nonterminal, event>, cfg_configuration<nonterminal, event>, cfg_step_label<nonterminal, event>, event> {

    @Override
    public List<tuple2<cfg_step_label<nonterminal, event>, cfg_configuration<nonterminal, event>>>
    step_relation(
            cfg<nonterminal, event> cfg,
            cfg_configuration<nonterminal, event> configuration) {
        List<tuple2<cfg_step_label<nonterminal, event>, cfg_configuration<nonterminal, event>>> result = new ArrayList<>();
        tuple2<General_Integer, two_elements__abstract<nonterminal, event>> left_most_nonterminal = configuration.left_most_nonterminal();
        if (left_most_nonterminal == null)
            return result;
        for (cfg_step_label<nonterminal, event> step_label : cfg.get_cfg_step_labels()) {
            if (step_label.get_prod_lhs().equals(((two_elements__teA) left_most_nonterminal.get_value2()).get_value())) {
                List<two_elements__abstract<nonterminal, event>> rhs = new ArrayList<>();
                rhs.addAll(configuration.get_cfg_conf().subList(0, left_most_nonterminal.get_value1().get_value()));
                rhs.addAll(step_label.get_prod_rhs());
                rhs.addAll(configuration.get_cfg_conf().subList(left_most_nonterminal.get_value1().get_value() + 1, configuration.get_cfg_conf().size()));

                result.add(new tuple2<>(
                        step_label,
                        new cfg_configuration<>(rhs)
                ));
            }
        }
        return result;
    }

    @Override
    public cfg_configuration<nonterminal, event> get_initial_configuration(cfg<nonterminal, event> cfg) {
        List<cfg_configuration<nonterminal, event>> result = new ArrayList<>();
        List<two_elements__abstract<nonterminal, event>> cfg_conf = new ArrayList<>();
        cfg_conf.add(new two_elements__teA<>(cfg.get_cfg_initial()));
        return new cfg_configuration<>(cfg_conf);
    }

    @Override
    public boolean is_marking_configuration(cfg<nonterminal, event> cfg, cfg_configuration<nonterminal, event> configuration) {
        return configuration.nonterminal_free();
    }

    public void print_accessible_configurations(cfg<nonterminal, event> cfg, int depth, boolean print_only_marking_configurations) {
        List<cfg_configuration<nonterminal, event>> todo = new ArrayList<>();
        todo.add(get_initial_configuration(cfg));
        for (int i = 0; i < depth; i++) {
            List<cfg_configuration<nonterminal, event>> next = new ArrayList<>();
            for (cfg_configuration<nonterminal, event> configuration : todo) {
                for (tuple2<cfg_step_label<nonterminal, event>, cfg_configuration<nonterminal, event>> pair : step_relation(cfg, configuration)) {
                    System.out.println(pair.get_value2());
                    next.add(pair.get_value2());
                }
            }
            todo = next;
        }
    }

}
