package datatypes.formalisms.language_check;

import datatypes.basic_java_datatypes.General;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */

public interface Get_History<event extends General> {

    List<event> get_history();

}
