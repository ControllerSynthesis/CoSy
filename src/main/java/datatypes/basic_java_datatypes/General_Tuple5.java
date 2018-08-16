package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Tuple5<T1, T2, T3, T4, T5> {

    private T1 elemA;
    private T2 elemB;
    private T3 elemC;
    private T4 elemD;
    private T5 elemE;

    public General_Tuple5(
            T1 elemA,
            T2 elemB,
            T3 elemC,
            T4 elemD,
            T5 elemE) {
        this.elemA = elemA;
        this.elemB = elemB;
        this.elemC = elemC;
        this.elemD = elemD;
        this.elemE = elemE;
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

    public T5 getElemE() {
        return elemE;
    }

    public void setElemE(T5 elemE) {
        this.elemE = elemE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        General_Tuple5<?, ?, ?, ?, ?> tupleFive = (General_Tuple5<?, ?, ?, ?, ?>) o;

        if (elemA != null ? !elemA.equals(tupleFive.elemA) : tupleFive.elemA != null) return false;
        if (elemB != null ? !elemB.equals(tupleFive.elemB) : tupleFive.elemB != null) return false;
        if (elemC != null ? !elemC.equals(tupleFive.elemC) : tupleFive.elemC != null) return false;
        if (elemD != null ? !elemD.equals(tupleFive.elemD) : tupleFive.elemD != null) return false;
        return elemE != null ? elemE.equals(tupleFive.elemE) : tupleFive.elemE == null;

    }

    @Override
    public int hashCode() {
        int result = elemA != null ? elemA.hashCode() : 0;
        result = 31 * result + (elemB != null ? elemB.hashCode() : 0);
        result = 31 * result + (elemC != null ? elemC.hashCode() : 0);
        result = 31 * result + (elemD != null ? elemD.hashCode() : 0);
        result = 31 * result + (elemE != null ? elemE.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "(" + elemA.toString() + ", " + elemB.toString() + ", " + elemC.toString() + ", " + elemD.toString() + ", " + elemE.toString() + ")";
    }
}

