package network;

public class ProtocolCode {
    static final byte ACK = 1;
    static final byte ACCEPT = 1 << 1;
    static final byte REFUSAL = 1 << 2;

    static final byte HISTORY = 1 << 3;
    static final byte MENU = 1 << 4;
    static final byte OPTION = 1 << 5;
    static final byte ORDER = 1 << 5 | 1 << 4;
    static final byte REVIEW = 1 << 6;
    static final byte STORE = 1 << 6 | 1 << 5;
    static final byte CLASSIFICATION = 1 << 6 | 1 << 4;
    static final byte USER = 1 << 6 | 1 << 5 | 1 << 4;
}