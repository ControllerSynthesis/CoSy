package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Tuple4<T1, T2, T3, T4> {

    private T1 elemA;
    private T2 elemB;
    private T3 elemC;
    private T4 elemD;

    public General_Tuple4(
            T1 elemA,
            T2 elemB,
            T3 elemC,
            T4 elemD) {
        this.elemA = elemA;
        this.elemB = elemB;
        this.elemC = elemC;
        this.elemD = elemD;
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

    public T4 getElemD() {
        return elemD;
    }

    public void setElemD(T4 elemD) {
        this.elemD = elemD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        General_Tuple4<?, ?, ?, ?> tupleFour = (General_Tuple4<?, ?, ?, ?>) o;

        if (elemA != null ? !elemA.equals(tupleFour.elemA) : tupleFour.elemA != null) return false;
        if (elemB != null ? !elemB.equals(tupleFour.elemB) : tupleFour.elemB != null) return false;
        if (elemC != null ? !elemC.equals(tupleFour.elemC) : tupleFour.elemC != null) return false;
        return elemD != null ? elemD.equals(tupleFour.elemD) : tupleFour.elemD == null;

    }

    @Override
    public int hashCode() {
        int result = elemA != null ? elemA.hashCode() : 0;
        result = 31 * result + (elemB != null ? elemB.hashCode() : 0);
        result = 31 * result + (elemC != null ? elemC.hashCode() : 0);
        result = 31 * result + (elemD != null ? elemD.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "(" + elemA.toString() + ", " + elemB.toString() + ", " + elemC.toString() + ", " + elemD.toString() + ")";
    }
}

