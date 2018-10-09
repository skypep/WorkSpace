// IMyTrickerController.aidl
package org.fra.camera.tricker.intf;

// Declare any non-default types here with import statements

import org.fra.camera.tricker.intf.IMyTrickerCallback;
//import android.os.FileDescriptor;
//import java.io.PrintWriter;

interface IMyTrickerController {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);

    // session_type = 0 , take photo post process
    // session_type = 1 , photo mode preview stream
    // session_type = 2 , video record stream
    // session_type = 3 , video preview stream
    int setBeautyLevel(int session_type, int level);
    int getBeautyLevel(int session_type);
    int setFilterIndex(int session_type, int index);
    int getFilterIndex(int session_type);

    void setCallback(IMyTrickerCallback cb);

    //void dumpInternal(in FileDescriptor fd, in PrintWriter pw, in String[] args);

    void sanityCheck();
}
