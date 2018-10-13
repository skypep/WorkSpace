package android.hardware.radio.V1_0;


public final class CardState {
    public static final int ABSENT = 0;
    public static final int PRESENT = 1;
    public static final int ERROR = 2;
    public static final int RESTRICTED = 3;
    public static final String toString(int o) {
        if (o == ABSENT) {
            return "ABSENT";
        }
        if (o == PRESENT) {
            return "PRESENT";
        }
        if (o == ERROR) {
            return "ERROR";
        }
        if (o == RESTRICTED) {
            return "RESTRICTED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        int flipped = 0;
        list.add("ABSENT"); // ABSENT == 0
        if ((o & PRESENT) == PRESENT) {
            list.add("PRESENT");
            flipped |= PRESENT;
        }
        if ((o & ERROR) == ERROR) {
            list.add("ERROR");
            flipped |= ERROR;
        }
        if ((o & RESTRICTED) == RESTRICTED) {
            list.add("RESTRICTED");
            flipped |= RESTRICTED;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString(o & (~flipped)));
        }
        return String.join(" | ", list);
    }

};
