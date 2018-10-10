package android.hardware.radio.V1_0;


public final class PersoSubstate {
    public static final int UNKNOWN = 0;
    public static final int IN_PROGRESS = 1;
    public static final int READY = 2;
    public static final int SIM_NETWORK = 3;
    public static final int SIM_NETWORK_SUBSET = 4;
    public static final int SIM_CORPORATE = 5;
    public static final int SIM_SERVICE_PROVIDER = 6;
    public static final int SIM_SIM = 7;
    public static final int SIM_NETWORK_PUK = 8;
    public static final int SIM_NETWORK_SUBSET_PUK = 9;
    public static final int SIM_CORPORATE_PUK = 10;
    public static final int SIM_SERVICE_PROVIDER_PUK = 11;
    public static final int SIM_SIM_PUK = 12;
    public static final int RUIM_NETWORK1 = 13;
    public static final int RUIM_NETWORK2 = 14;
    public static final int RUIM_HRPD = 15;
    public static final int RUIM_CORPORATE = 16;
    public static final int RUIM_SERVICE_PROVIDER = 17;
    public static final int RUIM_RUIM = 18;
    public static final int RUIM_NETWORK1_PUK = 19;
    public static final int RUIM_NETWORK2_PUK = 20;
    public static final int RUIM_HRPD_PUK = 21;
    public static final int RUIM_CORPORATE_PUK = 22;
    public static final int RUIM_SERVICE_PROVIDER_PUK = 23;
    public static final int RUIM_RUIM_PUK = 24;
    public static final String toString(int o) {
        if (o == UNKNOWN) {
            return "UNKNOWN";
        }
        if (o == IN_PROGRESS) {
            return "IN_PROGRESS";
        }
        if (o == READY) {
            return "READY";
        }
        if (o == SIM_NETWORK) {
            return "SIM_NETWORK";
        }
        if (o == SIM_NETWORK_SUBSET) {
            return "SIM_NETWORK_SUBSET";
        }
        if (o == SIM_CORPORATE) {
            return "SIM_CORPORATE";
        }
        if (o == SIM_SERVICE_PROVIDER) {
            return "SIM_SERVICE_PROVIDER";
        }
        if (o == SIM_SIM) {
            return "SIM_SIM";
        }
        if (o == SIM_NETWORK_PUK) {
            return "SIM_NETWORK_PUK";
        }
        if (o == SIM_NETWORK_SUBSET_PUK) {
            return "SIM_NETWORK_SUBSET_PUK";
        }
        if (o == SIM_CORPORATE_PUK) {
            return "SIM_CORPORATE_PUK";
        }
        if (o == SIM_SERVICE_PROVIDER_PUK) {
            return "SIM_SERVICE_PROVIDER_PUK";
        }
        if (o == SIM_SIM_PUK) {
            return "SIM_SIM_PUK";
        }
        if (o == RUIM_NETWORK1) {
            return "RUIM_NETWORK1";
        }
        if (o == RUIM_NETWORK2) {
            return "RUIM_NETWORK2";
        }
        if (o == RUIM_HRPD) {
            return "RUIM_HRPD";
        }
        if (o == RUIM_CORPORATE) {
            return "RUIM_CORPORATE";
        }
        if (o == RUIM_SERVICE_PROVIDER) {
            return "RUIM_SERVICE_PROVIDER";
        }
        if (o == RUIM_RUIM) {
            return "RUIM_RUIM";
        }
        if (o == RUIM_NETWORK1_PUK) {
            return "RUIM_NETWORK1_PUK";
        }
        if (o == RUIM_NETWORK2_PUK) {
            return "RUIM_NETWORK2_PUK";
        }
        if (o == RUIM_HRPD_PUK) {
            return "RUIM_HRPD_PUK";
        }
        if (o == RUIM_CORPORATE_PUK) {
            return "RUIM_CORPORATE_PUK";
        }
        if (o == RUIM_SERVICE_PROVIDER_PUK) {
            return "RUIM_SERVICE_PROVIDER_PUK";
        }
        if (o == RUIM_RUIM_PUK) {
            return "RUIM_RUIM_PUK";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        int flipped = 0;
        list.add("UNKNOWN"); // UNKNOWN == 0
        if ((o & IN_PROGRESS) == IN_PROGRESS) {
            list.add("IN_PROGRESS");
            flipped |= IN_PROGRESS;
        }
        if ((o & READY) == READY) {
            list.add("READY");
            flipped |= READY;
        }
        if ((o & SIM_NETWORK) == SIM_NETWORK) {
            list.add("SIM_NETWORK");
            flipped |= SIM_NETWORK;
        }
        if ((o & SIM_NETWORK_SUBSET) == SIM_NETWORK_SUBSET) {
            list.add("SIM_NETWORK_SUBSET");
            flipped |= SIM_NETWORK_SUBSET;
        }
        if ((o & SIM_CORPORATE) == SIM_CORPORATE) {
            list.add("SIM_CORPORATE");
            flipped |= SIM_CORPORATE;
        }
        if ((o & SIM_SERVICE_PROVIDER) == SIM_SERVICE_PROVIDER) {
            list.add("SIM_SERVICE_PROVIDER");
            flipped |= SIM_SERVICE_PROVIDER;
        }
        if ((o & SIM_SIM) == SIM_SIM) {
            list.add("SIM_SIM");
            flipped |= SIM_SIM;
        }
        if ((o & SIM_NETWORK_PUK) == SIM_NETWORK_PUK) {
            list.add("SIM_NETWORK_PUK");
            flipped |= SIM_NETWORK_PUK;
        }
        if ((o & SIM_NETWORK_SUBSET_PUK) == SIM_NETWORK_SUBSET_PUK) {
            list.add("SIM_NETWORK_SUBSET_PUK");
            flipped |= SIM_NETWORK_SUBSET_PUK;
        }
        if ((o & SIM_CORPORATE_PUK) == SIM_CORPORATE_PUK) {
            list.add("SIM_CORPORATE_PUK");
            flipped |= SIM_CORPORATE_PUK;
        }
        if ((o & SIM_SERVICE_PROVIDER_PUK) == SIM_SERVICE_PROVIDER_PUK) {
            list.add("SIM_SERVICE_PROVIDER_PUK");
            flipped |= SIM_SERVICE_PROVIDER_PUK;
        }
        if ((o & SIM_SIM_PUK) == SIM_SIM_PUK) {
            list.add("SIM_SIM_PUK");
            flipped |= SIM_SIM_PUK;
        }
        if ((o & RUIM_NETWORK1) == RUIM_NETWORK1) {
            list.add("RUIM_NETWORK1");
            flipped |= RUIM_NETWORK1;
        }
        if ((o & RUIM_NETWORK2) == RUIM_NETWORK2) {
            list.add("RUIM_NETWORK2");
            flipped |= RUIM_NETWORK2;
        }
        if ((o & RUIM_HRPD) == RUIM_HRPD) {
            list.add("RUIM_HRPD");
            flipped |= RUIM_HRPD;
        }
        if ((o & RUIM_CORPORATE) == RUIM_CORPORATE) {
            list.add("RUIM_CORPORATE");
            flipped |= RUIM_CORPORATE;
        }
        if ((o & RUIM_SERVICE_PROVIDER) == RUIM_SERVICE_PROVIDER) {
            list.add("RUIM_SERVICE_PROVIDER");
            flipped |= RUIM_SERVICE_PROVIDER;
        }
        if ((o & RUIM_RUIM) == RUIM_RUIM) {
            list.add("RUIM_RUIM");
            flipped |= RUIM_RUIM;
        }
        if ((o & RUIM_NETWORK1_PUK) == RUIM_NETWORK1_PUK) {
            list.add("RUIM_NETWORK1_PUK");
            flipped |= RUIM_NETWORK1_PUK;
        }
        if ((o & RUIM_NETWORK2_PUK) == RUIM_NETWORK2_PUK) {
            list.add("RUIM_NETWORK2_PUK");
            flipped |= RUIM_NETWORK2_PUK;
        }
        if ((o & RUIM_HRPD_PUK) == RUIM_HRPD_PUK) {
            list.add("RUIM_HRPD_PUK");
            flipped |= RUIM_HRPD_PUK;
        }
        if ((o & RUIM_CORPORATE_PUK) == RUIM_CORPORATE_PUK) {
            list.add("RUIM_CORPORATE_PUK");
            flipped |= RUIM_CORPORATE_PUK;
        }
        if ((o & RUIM_SERVICE_PROVIDER_PUK) == RUIM_SERVICE_PROVIDER_PUK) {
            list.add("RUIM_SERVICE_PROVIDER_PUK");
            flipped |= RUIM_SERVICE_PROVIDER_PUK;
        }
        if ((o & RUIM_RUIM_PUK) == RUIM_RUIM_PUK) {
            list.add("RUIM_RUIM_PUK");
            flipped |= RUIM_RUIM_PUK;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString(o & (~flipped)));
        }
        return String.join(" | ", list);
    }

};

