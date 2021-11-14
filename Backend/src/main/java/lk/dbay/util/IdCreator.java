package lk.dbay.util;

public class IdCreator {

//    private static int limit = 12;

    public String encodeId(Long value, int limit) {

        int count = 0;
        Long tempValue = value;
        while (value != 0) {
            value /= 10;
            count++;
        }

        String pId = "";
        for (int i = 0; i < limit - count; i++) {
            pId += "0";
        }

        pId += tempValue;

        return pId;
    }

//    public String encodeIdWithSequence(Long value, int limit) {
//
//        value = (value * 2) - 1;
//        int count = 0;
//        Long tempValue = value;
//        while (value != 0) {
//            value /= 10;
//            count++;
//        }
//
//        String pId = "";
//        for (int i = 0; i < limit - count; i++) {
//            pId += "0";
//        }
//
//        pId += tempValue;
//
//        return pId;
//    }

    public Long decodeId(String value) {
        return Long.valueOf(value);
    }

//    public Long decodeIdWithSequence(String value) {
//        long val = Long.valueOf(value);
////        if (val % 2 != 0) {
////            return 0L;
////        }
//        return (val + 1) / 2;
//    }
}
