package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Tuple2<T1, T2> {

    private T1 elemA;

    private T2 elemB;

    public General_Tuple2(
            T1 elemA,
            T2 elemB) {
        this.elemA = elemA;
        this.elemB = elemB;
    }

    public T1
    getElemA() {
        return elemA;
    }

    public void
    setElemA(
            T1 elemA) {
        this.elemA = elemA;
    }

    public T2
    getElemB() {
        return elemB;
    }

    public void
    setElemB(
            T2 elemB) {
        this.elemB = elemB;
    }

    @Override
    public boolean
    equals(
            Object o) {
        if (this == o) return true;
        if (!(o instanceof General_Tuple2)) return false;

        General_Tuple2 pair = (General_Tuple2) o;

        if (elemA != null ? !elemA.equals(pair.elemA) : pair.elemA != null) return false;
        return elemB != null ? elemB.equals(pair.elemB) : pair.elemB == null;
    }

    @Override
    public int
    hashCode() {
        int result = elemA != null ? elemA.hashCode() : 0;
        result = 31 * result + (elemB != null ? elemB.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "(" + elemA + ", " + elemB + ")";
    }
}

