package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Test_Sequence<event extends General> implements General {

    public static final int REJECT = 0;
    public static final int MARK = 1;
    public static final int UNMARK = 2;
    private General_List<event> sequence;
    private int expected;
    private String id;
    private int cached_hashcode = 0;

    public General_Test_Sequence(String id, String expected, General_List sequence) {
        this.id = id;
        if (expected.equals("REJECT")) this.expected = REJECT;
        else if (expected.equals("MARK")) this.expected = MARK;
        else if (expected.equals("UNMARK")) this.expected = UNMARK;
        else throw new RuntimeException();
        this.sequence = sequence;
    }

    public General_List<event> get_sequence() {
        return sequence;
    }

    public int get_expected() {
        return expected;
    }

    public String get_id() {
        return id;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        stringBuffer.append(id);
        stringBuffer.append(", ");
        if (expected == REJECT)
            stringBuffer.append("REJECT");
        if (expected == MARK)
            stringBuffer.append("MARK");
        if (expected == UNMARK)
            stringBuffer.append("UNMARK");
        stringBuffer.append(", ");
        sequence.append(stringBuffer);
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

        General_Test_Sequence<?> that = (General_Test_Sequence<?>) o;

        if (expected != that.expected) return false;
        if (sequence != null ? !sequence.equals(that.sequence) : that.sequence != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = sequence != null ? sequence.hashCode() : 0;
            result = 31 * result + expected;
            result = 31 * result + (id != null ? id.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

}
