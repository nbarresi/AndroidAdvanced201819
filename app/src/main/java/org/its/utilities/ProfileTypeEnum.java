package org.its.utilities;

public enum ProfileTypeEnum {
    WIFI(0), BEACON(1), NFC(2), GPS(3);

    private int value;

    ProfileTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProfileTypeEnum getEnumFromInt(int value) {
        ProfileTypeEnum objectToReturn;
        switch (value) {
            case 0:
                objectToReturn = ProfileTypeEnum.WIFI;
                break;
            case 1:
                objectToReturn = ProfileTypeEnum.BEACON;
                break;
            case 2:
                objectToReturn = ProfileTypeEnum.NFC;
                break;
            case 3:
                objectToReturn = ProfileTypeEnum.GPS;
                break;
            default:
                objectToReturn = ProfileTypeEnum.WIFI;
                break;


        }
        return objectToReturn;
    }


}
