package com.vitaliyhtc.socialnetworksapi;

public abstract class Constants {

    public static final int AUTH_BY_GOOGLE = 0x00F1;
    public static final int AUTH_BY_FACEBOOK = 0x00F2;
    public static final int AUTH_BY_NOT_SELECTED = 0x0000;


    private Constants() {
        throw new AssertionError();
    }
}
