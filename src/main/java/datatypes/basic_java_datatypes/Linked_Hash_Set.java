package datatypes.basic_java_datatypes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Linked_Hash_Set<T> {

    Set<T> elements = new HashSet<>();
    LinkedList<T> list = new LinkedList<>();

    public void add(T element) {
        if (elements.contains(element))
            return;
        elements.add(element);
        list.addFirst(element);
    }

    public T remove() {
        if (elements.isEmpty())
            return null;
        T result = list.removeFirst();
        elements.remove(result);
        return result;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public Set<T> getElements() {
        return elements;
    }
}

