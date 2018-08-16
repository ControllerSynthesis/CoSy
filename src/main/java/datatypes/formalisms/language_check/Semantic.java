package datatypes.formalisms.language_check;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */

public interface Semantic<generator extends General, configuration extends General, step_label extends General, event extends General> {

    List<tuple2<step_label, configuration>> step_relation(generator generator, configuration configuration);

    configuration get_initial_configuration(generator generator);

    boolean is_marking_configuration(generator generator, configuration configuration);


}
