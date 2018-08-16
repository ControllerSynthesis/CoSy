package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class l2_l3_nonterminals__cons_l2<state extends General, stack extends General> extends l2_l3_nonterminals__abstract<state, stack> {

    private state value1;
    private stack value2;
    private int cached_hashcode = 0;


    public l2_l3_nonterminals__cons_l2(state value1, stack value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public stack get_value2() {
        return value2;
    }

    public state get_value1() {
        return value1;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        value1.append(stringBuffer);
        stringBuffer.append(", ");
        value2.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        l2_l3_nonterminals__cons_l2<?, ?> that = (l2_l3_nonterminals__cons_l2<?, ?>) o;

        if (value1 != null ? !value1.equals(that.value1) : that.value1 != null) return false;
        return value2 != null ? value2.equals(that.value2) : that.value2 == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


}
