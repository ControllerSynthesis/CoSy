package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Tuple3<T1, T2, T3> {

    private T1 elemA;

    private T2 elemB;
    private T3 elemC;

    public General_Tuple3(
            T1 elemA,
            T2 elemB,
            T3 elemC) {
        this.elemA = elemA;
        this.elemB = elemB;
        this.elemC = elemC;
    }

    public T1 getElemA() {
        return elemA;
    }

    public void setElemA(T1 elemA) {
        this.elemA = elemA;
    }

    public T2 getElemB() {
        return elemB;
    }

    public void setElemB(T2 elemB) {
        this.elemB = elemB;
    }

    public T3 getElemC() {
        return elemC;
    }

    public void setElemC(T3 elemC) {
        this.elemC = elemC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        General_Tuple3<?, ?, ?> tupleThree = (General_Tuple3<?, ?, ?>) o;

        if (elemA != null ? !elemA.equals(tupleThree.elemA) : tupleThree.elemA != null) return false;
        if (elemB != null ? !elemB.equals(tupleThree.elemB) : tupleThree.elemB != null) return false;
        return elemC != null ? elemC.equals(tupleThree.elemC) : tupleThree.elemC == null;

    }

    @Override
    public int hashCode() {
        int result = elemA != null ? elemA.hashCode() : 0;
        result = 31 * result + (elemB != null ? elemB.hashCode() : 0);
        result = 31 * result + (elemC != null ? elemC.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "(" + elemA + ", " + elemB + ", " + elemC + ")";
    }
}

