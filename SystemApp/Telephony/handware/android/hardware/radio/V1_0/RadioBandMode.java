package android.hardware.radio.V1_0;


public final class RadioBandMode {
    public static final int BAND_MODE_UNSPECIFIED = 0;
    public static final int BAND_MODE_EURO = 1;
    public static final int BAND_MODE_USA = 2;
    public static final int BAND_MODE_JPN = 3;
    public static final int BAND_MODE_AUS = 4;
    public static final int BAND_MODE_AUS_2 = 5;
    public static final int BAND_MODE_CELL_800 = 6;
    public static final int BAND_MODE_PCS = 7;
    public static final int BAND_MODE_JTACS = 8;
    public static final int BAND_MODE_KOREA_PCS = 9;
    public static final int BAND_MODE_5_450M = 10;
    public static final int BAND_MODE_IMT2000 = 11;
    public static final int BAND_MODE_7_700M_2 = 12;
    public static final int BAND_MODE_8_1800M = 13;
    public static final int BAND_MODE_9_900M = 14;
    public static final int BAND_MODE_10_800M_2 = 15;
    public static final int BAND_MODE_EURO_PAMR_400M = 16;
    public static final int BAND_MODE_AWS = 17;
    public static final int BAND_MODE_USA_2500M = 18;
    public static final String toString(int o) {
        if (o == BAND_MODE_UNSPECIFIED) {
            return "BAND_MODE_UNSPECIFIED";
        }
        if (o == BAND_MODE_EURO) {
            return "BAND_MODE_EURO";
        }
        if (o == BAND_MODE_USA) {
            return "BAND_MODE_USA";
        }
        if (o == BAND_MODE_JPN) {
            return "BAND_MODE_JPN";
        }
        if (o == BAND_MODE_AUS) {
            return "BAND_MODE_AUS";
        }
        if (o == BAND_MODE_AUS_2) {
            return "BAND_MODE_AUS_2";
        }
        if (o == BAND_MODE_CELL_800) {
            return "BAND_MODE_CELL_800";
        }
        if (o == BAND_MODE_PCS) {
            return "BAND_MODE_PCS";
        }
        if (o == BAND_MODE_JTACS) {
            return "BAND_MODE_JTACS";
        }
        if (o == BAND_MODE_KOREA_PCS) {
            return "BAND_MODE_KOREA_PCS";
        }
        if (o == BAND_MODE_5_450M) {
            return "BAND_MODE_5_450M";
        }
        if (o == BAND_MODE_IMT2000) {
            return "BAND_MODE_IMT2000";
        }
        if (o == BAND_MODE_7_700M_2) {
            return "BAND_MODE_7_700M_2";
        }
        if (o == BAND_MODE_8_1800M) {
            return "BAND_MODE_8_1800M";
        }
        if (o == BAND_MODE_9_900M) {
            return "BAND_MODE_9_900M";
        }
        if (o == BAND_MODE_10_800M_2) {
            return "BAND_MODE_10_800M_2";
        }
        if (o == BAND_MODE_EURO_PAMR_400M) {
            return "BAND_MODE_EURO_PAMR_400M";
        }
        if (o == BAND_MODE_AWS) {
            return "BAND_MODE_AWS";
        }
        if (o == BAND_MODE_USA_2500M) {
            return "BAND_MODE_USA_2500M";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        int flipped = 0;
        list.add("BAND_MODE_UNSPECIFIED"); // BAND_MODE_UNSPECIFIED == 0
        if ((o & BAND_MODE_EURO) == BAND_MODE_EURO) {
            list.add("BAND_MODE_EURO");
            flipped |= BAND_MODE_EURO;
        }
        if ((o & BAND_MODE_USA) == BAND_MODE_USA) {
            list.add("BAND_MODE_USA");
            flipped |= BAND_MODE_USA;
        }
        if ((o & BAND_MODE_JPN) == BAND_MODE_JPN) {
            list.add("BAND_MODE_JPN");
            flipped |= BAND_MODE_JPN;
        }
        if ((o & BAND_MODE_AUS) == BAND_MODE_AUS) {
            list.add("BAND_MODE_AUS");
            flipped |= BAND_MODE_AUS;
        }
        if ((o & BAND_MODE_AUS_2) == BAND_MODE_AUS_2) {
            list.add("BAND_MODE_AUS_2");
            flipped |= BAND_MODE_AUS_2;
        }
        if ((o & BAND_MODE_CELL_800) == BAND_MODE_CELL_800) {
            list.add("BAND_MODE_CELL_800");
            flipped |= BAND_MODE_CELL_800;
        }
        if ((o & BAND_MODE_PCS) == BAND_MODE_PCS) {
            list.add("BAND_MODE_PCS");
            flipped |= BAND_MODE_PCS;
        }
        if ((o & BAND_MODE_JTACS) == BAND_MODE_JTACS) {
            list.add("BAND_MODE_JTACS");
            flipped |= BAND_MODE_JTACS;
        }
        if ((o & BAND_MODE_KOREA_PCS) == BAND_MODE_KOREA_PCS) {
            list.add("BAND_MODE_KOREA_PCS");
            flipped |= BAND_MODE_KOREA_PCS;
        }
        if ((o & BAND_MODE_5_450M) == BAND_MODE_5_450M) {
            list.add("BAND_MODE_5_450M");
            flipped |= BAND_MODE_5_450M;
        }
        if ((o & BAND_MODE_IMT2000) == BAND_MODE_IMT2000) {
            list.add("BAND_MODE_IMT2000");
            flipped |= BAND_MODE_IMT2000;
        }
        if ((o & BAND_MODE_7_700M_2) == BAND_MODE_7_700M_2) {
            list.add("BAND_MODE_7_700M_2");
            flipped |= BAND_MODE_7_700M_2;
        }
        if ((o & BAND_MODE_8_1800M) == BAND_MODE_8_1800M) {
            list.add("BAND_MODE_8_1800M");
            flipped |= BAND_MODE_8_1800M;
        }
        if ((o & BAND_MODE_9_900M) == BAND_MODE_9_900M) {
            list.add("BAND_MODE_9_900M");
            flipped |= BAND_MODE_9_900M;
        }
        if ((o & BAND_MODE_10_800M_2) == BAND_MODE_10_800M_2) {
            list.add("BAND_MODE_10_800M_2");
            flipped |= BAND_MODE_10_800M_2;
        }
        if ((o & BAND_MODE_EURO_PAMR_400M) == BAND_MODE_EURO_PAMR_400M) {
            list.add("BAND_MODE_EURO_PAMR_400M");
            flipped |= BAND_MODE_EURO_PAMR_400M;
        }
        if ((o & BAND_MODE_AWS) == BAND_MODE_AWS) {
            list.add("BAND_MODE_AWS");
            flipped |= BAND_MODE_AWS;
        }
        if ((o & BAND_MODE_USA_2500M) == BAND_MODE_USA_2500M) {
            list.add("BAND_MODE_USA_2500M");
            flipped |= BAND_MODE_USA_2500M;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString(o & (~flipped)));
        }
        return String.join(" | ", list);
    }

};

