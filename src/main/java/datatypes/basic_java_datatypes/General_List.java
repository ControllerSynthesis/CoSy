package datatypes.basic_java_datatypes;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_List<alpha extends General> implements General {


    boolean modifiable = false;
    private List<alpha> value;
    private int cached_hashcode = 0;

    public General_List(
            List<alpha> value) {
        if (value == null)
            throw new RuntimeException();
        this.value = Collections.unmodifiableList(value);
    }

    public General_List(
            boolean modifiable, List<alpha> value) {
        this.modifiable = modifiable;
        if (modifiable)
            this.value = value;
        else
            this.value = Collections.unmodifiableList(value);
    }

    public General_List() {
        this.value = Collections.unmodifiableList(new ArrayList<alpha>());
    }

    public static <alpha>
    BinaryOperator<List<alpha>>
    get_concat() {
        return (list, list2) -> {
            list.addAll(list2);
            return list;
        };
    }

    public static <alpha extends General>
    boolean
    prefix(
            General_List<alpha> w1,
            General_List<alpha> w2) {
        return prefix(w1.get_value(), w2.get_value());
    }

    public static <alpha>
    boolean
    prefix(
            List<alpha> w1,
            List<alpha> w2) {
        return w1.size() <= w2.size() && w1.equals(w2.subList(0, w1.size()));
    }

    public static <alpha extends General>
    boolean
    suffix(
            General_List<alpha> w1,
            General_List<alpha> w2) {
        return suffix(w1.get_value(), w2.get_value());
    }

    public static <alpha>
    boolean
    suffix(
            List<alpha> w1,
            List<alpha> w2) {
        return w2.size() <= w1.size() && w2.equals(w1.subList(w2.size(), w1.size()));
    }

    public static <alpha>
    List<alpha>
    list_by_enumeration(
            alpha... elements) {
        return Arrays.stream(elements).collect(Collectors.toList());
    }

    public static <alpha extends General>
    alpha
    last(
            List<alpha> list) {
        return list.get(list.size() - 1);
    }

    public static <alpha>
    List<alpha>
    drop(
            List<alpha> list,
            int i) {
        return list.subList(Math.min(i, list.size()), list.size());
    }

    public static <alpha extends General>
    General_List<alpha>
    drop(
            General_List<alpha> list,
            int i) {
        return new General_List<>(drop(list.get_value(), i));
    }

    public static <alpha>
    List<alpha>
    take(
            List<alpha> list,
            int i) {
        return list.subList(0, Math.min(i, list.size()));
    }

    public static <alpha extends General>
    General_List<alpha> take(
            General_List<alpha> list,
            int i) {
        return new General_List<>(take(list.get_value(), i));
    }

    public static <alpha extends General>
    General_List<alpha>
    cons(
            alpha elem,
            General_List<alpha> list) {
        return new General_List<>(cons(elem, list.get_value()));
    }

    public static <alpha extends General>
    List<alpha>
    cons(
            alpha elem,
            List<alpha> list) {
        List<alpha> result = new ArrayList<alpha>();
        result.add(elem);
        result.addAll(list);
        return result;
    }

    public static <alpha> List<alpha> append(List<alpha> list, alpha elem) {
        List<alpha> result = new ArrayList<alpha>();
        result.addAll(list);
        result.add(elem);
        return result;
    }

    public static <alpha>
    List<alpha>
    append(
            List<alpha> list1,
            List<alpha> list2) {
        List<alpha> result = new ArrayList<alpha>();
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }

    public static <alpha>
    List<alpha>
    drop_from_end(
            List<alpha> list1,
            List<alpha> list2) {
        return list1.subList(0, list1.size() - list2.size());
    }

    public static <alpha>
    boolean
    ends_with(
            List<alpha> list1,
            List<alpha> list2) {
        if (list1.size() < list2.size())
            return false;
        return list1.subList(list1.size() - list2.size(), list1.size()).equals(list2);
    }

    public static <alpha>
    List<alpha>
    butlast_if_match(
            List<alpha> list,
            alpha elem) {
        if (list.size() == 0 || !list.get(list.size() - 1).equals(elem))
            return list;
        return list.subList(0, list.size() - 1);
    }

    public static <alpha extends General>
    option__abstract<alpha>
    list_to_option(List<alpha> list) {
        if (list.isEmpty())
            return new option__None<>();
        else
            return new option__Some<>(list.get(0));
    }

    public static <alpha extends General>
    List<alpha>
    butlast(List<alpha> list) {
        return list.subList(0, list.size() - 1);
    }

    public static <alpha extends General>
    List<alpha>
    rev(List<alpha> list) {
        List<alpha> copy = new ArrayList<alpha>(list);
        Collections.reverse(copy);
        return copy;
    }

    public static <stack extends General>
    boolean
    in_prefix_closure(
            List<stack> list,
            Set<List<stack>> lists) {
        for (List<stack> list2 : lists)
            if (prefix(list, list2))
                return true;
        return false;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public List<alpha>
    get_value() {
        return value;
    }

    @Override
    public void
    append(
            StringBuffer stringBuffer) {
        Append_helper.append(value, stringBuffer);
    }

    @Override
    public String
    toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public boolean
    equals(
            Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        General_List<?> generalList = (General_List<?>) o;

        return value != null ? value.equals(generalList.value) : generalList.value == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0 || modifiable)
            cached_hashcode = value.hashCode();
        return cached_hashcode;
    }
}
