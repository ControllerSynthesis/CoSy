package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public abstract class two_elements__abstract<alpha extends General, beta extends General> implements General {

    public static <alpha extends General, beta extends General> tuple2<General_Integer, two_elements__abstract<alpha, beta>>
    get_left_most_teA(List<two_elements__abstract<alpha, beta>> list) {
        int i = -1;
        for (two_elements__abstract<alpha, beta> elem : list) {
            i++;
            if (elem instanceof two_elements__teA)
                return new tuple2<>(new General_Integer(i), elem);
        }
        return null;
    }

    public static <alpha extends General, beta extends General> int first_index_of_teB(List<two_elements__abstract<alpha, beta>> w) {
        int index = -1;
        for (two_elements__abstract<alpha, beta> elem : w) {
            index++;
            if (elem instanceof two_elements__teB)
                return index;
        }
        return -1;
    }

    public static <beta extends General, alpha extends General> List<two_elements__abstract<alpha, beta>>
    get_maximal_beB_prefix(
            List<two_elements__abstract<alpha, beta>> list) {
        List<two_elements__abstract<alpha, beta>> result = new ArrayList<>();
        for (two_elements__abstract<alpha, beta> elem : list) {
            if (elem instanceof two_elements__teB)
                result.add(elem);
            else break;
        }
        return result;
    }

    public static <beta extends General, alpha extends General> List<beta>
    filterB(
            List<two_elements__abstract<alpha, beta>> list) {
        List<beta> result = new ArrayList<>();
        for (two_elements__abstract<alpha, beta> elem : list) {
            if (elem instanceof two_elements__teB)
                result.add(((two_elements__teB<alpha, beta>) elem).get_value());
        }
        return result;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    private option__abstract<alpha>
    FB_first_teA(
            List<two_elements__abstract<alpha, beta>> w) {
        for (two_elements__abstract<alpha, beta> elem : w)
            if (elem instanceof two_elements__teA)
                return new option__Some<>(((two_elements__teA<alpha, beta>) elem).get_value());
        return new option__None<>();
    }
}
