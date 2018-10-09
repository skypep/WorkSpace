// IMyTrickerCallback.aidl
package org.fra.camera.tricker.intf;

// Declare any non-default types here with import statements

interface IMyTrickerCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    int onTrickerCommandExecutedDone(String command, in String[] args);
    int onTrickerEvent(int code, in String[] cooked);
}
