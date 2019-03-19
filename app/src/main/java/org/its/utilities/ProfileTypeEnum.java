package org.its.utilities;

public  enum ProfileTypeEnum {
    WIFI(0), BEACON(1), NFC(3), GPS(4);

    private int value;

    private ProfileTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }




}
