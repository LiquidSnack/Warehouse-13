package com.mag.boikov.testapp.statistics.acquisition;

class TestData {
    static byte[] randomBytes(int count) {
        if (count < 0) return new byte[0];
        byte[] bytes = new byte[count];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (Math.random() * 256);
        }
        return bytes;
    }
}
