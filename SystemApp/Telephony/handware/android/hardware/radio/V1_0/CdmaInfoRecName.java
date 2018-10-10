package android.hardware.radio.V1_0;


public final class CdmaInfoRecName {
    public static final int DISPLAY = 0;
    public static final int CALLED_PARTY_NUMBER = 1;
    public static final int CALLING_PARTY_NUMBER = 2;
    public static final int CONNECTED_NUMBER = 3;
    public static final int SIGNAL = 4;
    public static final int REDIRECTING_NUMBER = 5;
    public static final int LINE_CONTROL = 6;
    public static final int EXTENDED_DISPLAY = 7;
    public static final int T53_CLIR = 8;
    public static final int T53_RELEASE = 9;
    public static final int T53_AUDIO_CONTROL = 10;
    public static final String toString(int o) {
        if (o == DISPLAY) {
            return "DISPLAY";
        }
        if (o == CALLED_PARTY_NUMBER) {
            return "CALLED_PARTY_NUMBER";
        }
        if (o == CALLING_PARTY_NUMBER) {
            return "CALLING_PARTY_NUMBER";
        }
        if (o == CONNECTED_NUMBER) {
            return "CONNECTED_NUMBER";
        }
        if (o == SIGNAL) {
            return "SIGNAL";
        }
        if (o == REDIRECTING_NUMBER) {
            return "REDIRECTING_NUMBER";
        }
        if (o == LINE_CONTROL) {
            return "LINE_CONTROL";
        }
        if (o == EXTENDED_DISPLAY) {
            return "EXTENDED_DISPLAY";
        }
        if (o == T53_CLIR) {
            return "T53_CLIR";
        }
        if (o == T53_RELEASE) {
            return "T53_RELEASE";
        }
        if (o == T53_AUDIO_CONTROL) {
            return "T53_AUDIO_CONTROL";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        int flipped = 0;
        list.add("DISPLAY"); // DISPLAY == 0
        if ((o & CALLED_PARTY_NUMBER) == CALLED_PARTY_NUMBER) {
            list.add("CALLED_PARTY_NUMBER");
            flipped |= CALLED_PARTY_NUMBER;
        }
        if ((o & CALLING_PARTY_NUMBER) == CALLING_PARTY_NUMBER) {
            list.add("CALLING_PARTY_NUMBER");
            flipped |= CALLING_PARTY_NUMBER;
        }
        if ((o & CONNECTED_NUMBER) == CONNECTED_NUMBER) {
            list.add("CONNECTED_NUMBER");
            flipped |= CONNECTED_NUMBER;
        }
        if ((o & SIGNAL) == SIGNAL) {
            list.add("SIGNAL");
            flipped |= SIGNAL;
        }
        if ((o & REDIRECTING_NUMBER) == REDIRECTING_NUMBER) {
            list.add("REDIRECTING_NUMBER");
            flipped |= REDIRECTING_NUMBER;
        }
        if ((o & LINE_CONTROL) == LINE_CONTROL) {
            list.add("LINE_CONTROL");
            flipped |= LINE_CONTROL;
        }
        if ((o & EXTENDED_DISPLAY) == EXTENDED_DISPLAY) {
            list.add("EXTENDED_DISPLAY");
            flipped |= EXTENDED_DISPLAY;
        }
        if ((o & T53_CLIR) == T53_CLIR) {
            list.add("T53_CLIR");
            flipped |= T53_CLIR;
        }
        if ((o & T53_RELEASE) == T53_RELEASE) {
            list.add("T53_RELEASE");
            flipped |= T53_RELEASE;
        }
        if ((o & T53_AUDIO_CONTROL) == T53_AUDIO_CONTROL) {
            list.add("T53_AUDIO_CONTROL");
            flipped |= T53_AUDIO_CONTROL;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString(o & (~flipped)));
        }
        return String.join(" | ", list);
    }

};

