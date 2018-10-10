package android.hardware.radio.V1_1;

public interface IRadioResponse extends android.hardware.radio.V1_0.IRadioResponse {
    public static final String kInterfaceName = "android.hardware.radio@1.1::IRadioResponse";

    /* package private */ static IRadioResponse asInterface(android.os.IHwBinder binder) {
        if (binder == null) {
            return null;
        }

        android.os.IHwInterface iface =
                binder.queryLocalInterface(kInterfaceName);

        if ((iface != null) && (iface instanceof IRadioResponse)) {
            return (IRadioResponse)iface;
        }

        IRadioResponse proxy = new IRadioResponse.Proxy(binder);

        try {
            for (String descriptor : proxy.interfaceChain()) {
                if (descriptor.equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (android.os.RemoteException e) {
        }

        return null;
    }

    public static IRadioResponse castFrom(android.os.IHwInterface iface) {
        return (iface == null) ? null : IRadioResponse.asInterface(iface.asBinder());
    }

    @Override
    public android.os.IHwBinder asBinder();

    public static IRadioResponse getService(String serviceName) throws android.os.RemoteException {
        return IRadioResponse.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.1::IRadioResponse",serviceName));
    }

    public static IRadioResponse getService() throws android.os.RemoteException {
        return IRadioResponse.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.1::IRadioResponse","default"));
    }

    void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
        throws android.os.RemoteException;
    void setSimCardPowerResponse_1_1(android.hardware.radio.V1_0.RadioResponseInfo info)
        throws android.os.RemoteException;
    void startNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
        throws android.os.RemoteException;
    void stopNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
        throws android.os.RemoteException;
    void startKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo info, KeepaliveStatus status)
        throws android.os.RemoteException;
    void stopKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
        throws android.os.RemoteException;
    java.util.ArrayList<String> interfaceChain()
        throws android.os.RemoteException;
    String interfaceDescriptor()
        throws android.os.RemoteException;
    java.util.ArrayList<byte[/* 32 */]> getHashChain()
        throws android.os.RemoteException;
    void setHALInstrumentation()
        throws android.os.RemoteException;
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient recipient, long cookie)
        throws android.os.RemoteException;
    void ping()
        throws android.os.RemoteException;
    android.hidl.base.V1_0.DebugInfo getDebugInfo()
        throws android.os.RemoteException;
    void notifySyspropsChanged()
        throws android.os.RemoteException;
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient recipient)
        throws android.os.RemoteException;

    public static final class Proxy implements IRadioResponse {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder remote) {
            mRemote = java.util.Objects.requireNonNull(remote);
        }

        @Override
        public android.os.IHwBinder asBinder() {
            return mRemote;
        }

        @Override
        public String toString() {
            try {
                return this.interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException ex) {
                /* ignored; handled below. */
            }
            return "[class or subclass of " + IRadioResponse.kInterfaceName + "]@Proxy";
        }

        // Methods from ::android::hardware::radio::V1_0::IRadioResponse follow.
        @Override
        public void getIccCardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.CardStatus cardStatus)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(1 /* getIccCardStatusResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(2 /* supplyIccPinForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPukForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(3 /* supplyIccPukForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(4 /* supplyIccPin2ForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPuk2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(5 /* supplyIccPuk2ForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void changeIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(6 /* changeIccPinForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void changeIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(7 /* changeIccPin2ForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyNetworkDepersonalizationResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int remainingRetries)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(8 /* supplyNetworkDepersonalizationResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCurrentCallsResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.Call> calls)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.Call.writeVectorToParcel(_hidl_request, calls);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(9 /* getCurrentCallsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void dialResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(10 /* dialResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getIMSIForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String imsi)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imsi);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(11 /* getIMSIForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangupConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(12 /* hangupConnectionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangupWaitingOrBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(13 /* hangupWaitingOrBackgroundResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangupForegroundResumeBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(14 /* hangupForegroundResumeBackgroundResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(15 /* switchWaitingOrHoldingAndActiveResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void conferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(16 /* conferenceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void rejectCallResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(17 /* rejectCallResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getLastCallFailCauseResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.LastCallFailCauseInfo failCauseinfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            failCauseinfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(18 /* getLastCallFailCauseResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getSignalStrengthResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SignalStrength sigStrength)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sigStrength.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(19 /* getSignalStrengthResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getVoiceRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.VoiceRegStateResult voiceRegResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            voiceRegResponse.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(20 /* getVoiceRegistrationStateResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDataRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.DataRegStateResult dataRegResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(21 /* getDataRegistrationStateResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getOperatorResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String longName, String shortName, String numeric)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(longName);
            _hidl_request.writeString(shortName);
            _hidl_request.writeString(numeric);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(22 /* getOperatorResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setRadioPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(23 /* setRadioPowerResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(24 /* sendDtmfResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SendSmsResult sms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(25 /* sendSmsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendSMSExpectMoreResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SendSmsResult sms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(26 /* sendSMSExpectMoreResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setupDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SetupDataCallResult dcResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dcResponse.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(27 /* setupDataCallResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccIOForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.IccIoResult iccIo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(28 /* iccIOForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(29 /* sendUssdResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cancelPendingUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(30 /* cancelPendingUssdResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getClirResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int n, int m)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            _hidl_request.writeInt32(m);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(31 /* getClirResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setClirResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(32 /* setClirResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCallForwardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.CallForwardInfo> callForwardInfos)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.CallForwardInfo.writeVectorToParcel(_hidl_request, callForwardInfos);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(33 /* getCallForwardStatusResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCallForwardResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(34 /* setCallForwardResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean enable, int serviceClass)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(35 /* getCallWaitingResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(36 /* setCallWaitingResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(37 /* acknowledgeLastIncomingGsmSmsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acceptCallResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(38 /* acceptCallResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deactivateDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(39 /* deactivateDataCallResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int response)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(response);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(40 /* getFacilityLockForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int retry)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(retry);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(41 /* setFacilityLockForAppResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setBarringPasswordResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(42 /* setBarringPasswordResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getNetworkSelectionModeResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean manual)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(manual);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(43 /* getNetworkSelectionModeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(44 /* setNetworkSelectionModeAutomaticResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setNetworkSelectionModeManualResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(45 /* setNetworkSelectionModeManualResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAvailableNetworksResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> networkInfos)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.OperatorInfo.writeVectorToParcel(_hidl_request, networkInfos);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(46 /* getAvailableNetworksResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(47 /* startDtmfResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(48 /* stopDtmfResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getBasebandVersionResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String version)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(version);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(49 /* getBasebandVersionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void separateConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(50 /* separateConnectionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(51 /* setMuteResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(52 /* getMuteResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getClipResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int status)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(status);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(53 /* getClipResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDataCallListResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> dcResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.SetupDataCallResult.writeVectorToParcel(_hidl_request, dcResponse);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(54 /* getDataCallListResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSuppServiceNotificationsResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(55 /* setSuppServiceNotificationsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void writeSmsToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int index)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(56 /* writeSmsToSimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deleteSmsOnSimResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(57 /* deleteSmsOnSimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setBandModeResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(58 /* setBandModeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAvailableBandModesResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<Integer> bandModes)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(bandModes);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(59 /* getAvailableBandModesResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendEnvelopeResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String commandResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(commandResponse);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(60 /* sendEnvelopeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendTerminalResponseToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(61 /* sendTerminalResponseToSimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(62 /* handleStkCallSetupRequestFromSimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void explicitCallTransferResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(63 /* explicitCallTransferResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(64 /* setPreferredNetworkTypeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int nwType)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(nwType);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(65 /* getPreferredNetworkTypeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getNeighboringCidsResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.NeighboringCell> cells)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.NeighboringCell.writeVectorToParcel(_hidl_request, cells);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(66 /* getNeighboringCidsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setLocationUpdatesResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(67 /* setLocationUpdatesResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(68 /* setCdmaSubscriptionSourceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(69 /* setCdmaRoamingPreferenceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(70 /* getCdmaRoamingPreferenceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(71 /* setTTYModeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int mode)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(72 /* getTTYModeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(73 /* setPreferredVoicePrivacyResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(74 /* getPreferredVoicePrivacyResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendCDMAFeatureCodeResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(75 /* sendCDMAFeatureCodeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendBurstDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(76 /* sendBurstDtmfResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SendSmsResult sms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(77 /* sendCdmaSmsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(78 /* acknowledgeLastIncomingCdmaSmsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> configs)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(79 /* getGsmBroadcastConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(80 /* setGsmBroadcastConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setGsmBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(81 /* setGsmBroadcastActivationResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> configs)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(82 /* getCdmaBroadcastConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(83 /* setCdmaBroadcastConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(84 /* setCdmaBroadcastActivationResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCDMASubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String mdn, String hSid, String hNid, String min, String prl)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(mdn);
            _hidl_request.writeString(hSid);
            _hidl_request.writeString(hNid);
            _hidl_request.writeString(min);
            _hidl_request.writeString(prl);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(85 /* getCDMASubscriptionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void writeSmsToRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int index)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(86 /* writeSmsToRuimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deleteSmsOnRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(87 /* deleteSmsOnRuimResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDeviceIdentityResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String imei, String imeisv, String esn, String meid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imei);
            _hidl_request.writeString(imeisv);
            _hidl_request.writeString(esn);
            _hidl_request.writeString(meid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(88 /* getDeviceIdentityResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void exitEmergencyCallbackModeResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(89 /* exitEmergencyCallbackModeResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String smsc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(smsc);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(90 /* getSmscAddressResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(91 /* setSmscAddressResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void reportSmsMemoryStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(92 /* reportSmsMemoryStatusResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void reportStkServiceIsRunningResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(93 /* reportStkServiceIsRunningResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int source)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(source);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(94 /* getCdmaSubscriptionSourceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestIsimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String response)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(response);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(95 /* requestIsimAuthenticationResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(96 /* acknowledgeIncomingGsmSmsWithPduResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendEnvelopeWithStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.IccIoResult iccIo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(97 /* sendEnvelopeWithStatusResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getVoiceRadioTechnologyResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int rat)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(rat);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(98 /* getVoiceRadioTechnologyResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCellInfoListResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.CellInfo> cellInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.CellInfo.writeVectorToParcel(_hidl_request, cellInfo);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(99 /* getCellInfoListResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCellInfoListRateResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(100 /* setCellInfoListRateResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setInitialAttachApnResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(101 /* setInitialAttachApnResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getImsRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean isRegistered, int ratFamily)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(isRegistered);
            _hidl_request.writeInt32(ratFamily);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(102 /* getImsRegistrationStateResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendImsSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.SendSmsResult sms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(103 /* sendImsSmsResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccTransmitApduBasicChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.IccIoResult result)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(104 /* iccTransmitApduBasicChannelResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccOpenLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int channelId, java.util.ArrayList<Byte> selectResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(channelId);
            _hidl_request.writeInt8Vector(selectResponse);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(105 /* iccOpenLogicalChannelResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccCloseLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(106 /* iccCloseLogicalChannelResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccTransmitApduLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.IccIoResult result)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(107 /* iccTransmitApduLogicalChannelResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvReadItemResponse(android.hardware.radio.V1_0.RadioResponseInfo info, String result)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(result);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(108 /* nvReadItemResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvWriteItemResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(109 /* nvWriteItemResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvWriteCdmaPrlResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(110 /* nvWriteCdmaPrlResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvResetConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(111 /* nvResetConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setUiccSubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(112 /* setUiccSubscriptionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setDataAllowedResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(113 /* setDataAllowedResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getHardwareConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo info, java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> config)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            android.hardware.radio.V1_0.HardwareConfig.writeVectorToParcel(_hidl_request, config);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(114 /* getHardwareConfigResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestIccSimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.IccIoResult result)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(115 /* requestIccSimAuthenticationResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setDataProfileResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(116 /* setDataProfileResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestShutdownResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(117 /* requestShutdownResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.RadioCapability rc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(118 /* getRadioCapabilityResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.RadioCapability rc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(119 /* setRadioCapabilityResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.LceStatusInfo statusInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(120 /* startLceServiceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.LceStatusInfo statusInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(121 /* stopLceServiceResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void pullLceDataResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.LceDataInfo lceInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            lceInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(122 /* pullLceDataResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getModemActivityInfoResponse(android.hardware.radio.V1_0.RadioResponseInfo info, android.hardware.radio.V1_0.ActivityStatsInfo activityInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            activityInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(123 /* getModemActivityInfoResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo info, int numAllowed)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(numAllowed);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(124 /* setAllowedCarriersResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo info, boolean allAllowed, android.hardware.radio.V1_0.CarrierRestrictions carriers)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(allAllowed);
            carriers.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(125 /* getAllowedCarriersResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendDeviceStateResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(126 /* sendDeviceStateResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setIndicationFilterResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(127 /* setIndicationFilterResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSimCardPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(128 /* setSimCardPowerResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeRequest(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(129 /* acknowledgeRequest */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        // Methods from ::android::hardware::radio::V1_1::IRadioResponse follow.
        @Override
        public void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(130 /* setCarrierInfoForImsiEncryptionResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSimCardPowerResponse_1_1(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(131 /* setSimCardPowerResponse_1_1 */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(132 /* startNetworkScanResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(133 /* stopNetworkScanResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo info, KeepaliveStatus status)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            status.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(134 /* startKeepaliveResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo info)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(135 /* stopKeepaliveResponse */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        // Methods from ::android::hidl::base::V1_0::IBase follow.
        @Override
        public java.util.ArrayList<String> interfaceChain()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(256067662 /* interfaceChain */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();

                java.util.ArrayList<String> _hidl_out_descriptors = _hidl_reply.readStringVector();
                return _hidl_out_descriptors;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public String interfaceDescriptor()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(256136003 /* interfaceDescriptor */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();

                String _hidl_out_descriptor = _hidl_reply.readString();
                return _hidl_out_descriptor;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public java.util.ArrayList<byte[/* 32 */]> getHashChain()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(256398152 /* getHashChain */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();

                java.util.ArrayList<byte[/* 32 */]> _hidl_out_hashchain =  new java.util.ArrayList<byte[/* 32 */]>();
                {
                    android.os.HwBlob _hidl_blob = _hidl_reply.readBuffer(16 /* size */);
                    {
                        int _hidl_vec_size = _hidl_blob.getInt32(0 /* offset */ + 8 /* offsetof(hidl_vec<T>, mSize) */);
                        android.os.HwBlob childBlob = _hidl_reply.readEmbeddedBuffer(
                                _hidl_vec_size * 32,_hidl_blob.handle(),
                                0 /* offset */ + 0 /* offsetof(hidl_vec<T>, mBuffer) */,true /* nullable */);

                        _hidl_out_hashchain.clear();
                        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                            final byte[/* 32 */] _hidl_vec_element = new byte[32];
                            {
                                long _hidl_array_offset_1 = _hidl_index_0 * 32;
                                for (int _hidl_index_1_0 = 0; _hidl_index_1_0 < 32; ++_hidl_index_1_0) {
                                    _hidl_vec_element[_hidl_index_1_0] = childBlob.getInt8(_hidl_array_offset_1);
                                    _hidl_array_offset_1 += 1;
                                }
                            }
                            _hidl_out_hashchain.add(_hidl_vec_element);
                        }
                    }
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setHALInstrumentation()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(256462420 /* setHALInstrumentation */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient recipient, long cookie)
                throws android.os.RemoteException {
            return mRemote.linkToDeath(recipient, cookie);
        }
        @Override
        public void ping()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(256921159 /* ping */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public android.hidl.base.V1_0.DebugInfo getDebugInfo()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(257049926 /* getDebugInfo */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();

                android.hidl.base.V1_0.DebugInfo _hidl_out_info = new android.hidl.base.V1_0.DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void notifySyspropsChanged()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(android.hidl.base.V1_0.IBase.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(257120595 /* notifySyspropsChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient recipient)
                throws android.os.RemoteException {
            return mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements IRadioResponse {
        @Override
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override
        public final java.util.ArrayList<String> interfaceChain() {
            return new java.util.ArrayList<String>(java.util.Arrays.asList(
                    IRadioResponse.kInterfaceName,
                    android.hardware.radio.V1_0.IRadioResponse.kInterfaceName,
                    android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;

        }

        @Override
        public final java.util.ArrayList<byte[/* 32 */]> getHashChain() {
            return new java.util.ArrayList<byte[/* 32 */]>(java.util.Arrays.asList(
                    new byte[/* 32 */]{5,-86,61,-26,19,10,-105,-120,-3,-74,-12,-45,-52,87,-61,-22,-112,-16,103,-25,122,94,9,-42,-89,114,-20,127,107,-54,51,-46} /* 05aa3de6130a9788fdb6f4d3cc57c3ea90f067e77a5e09d6a772ec7f6bca33d2 */,
                    new byte[/* 32 */]{45,-125,58,-18,-48,-51,29,89,67,122,-54,33,11,-27,-112,-87,83,-49,50,-68,-74,104,60,-42,61,8,-105,98,-90,67,-5,73} /* 2d833aeed0cd1d59437aca210be590a953cf32bcb6683cd63d089762a643fb49 */,
                    new byte[/* 32 */]{-67,-38,-74,24,77,122,52,109,-90,-96,125,-64,-126,-116,-15,-102,105,111,76,-86,54,17,-59,31,46,20,86,90,20,-76,15,-39} /* bddab6184d7a346da6a07dc0828cf19a696f4caa3611c51f2e14565a14b40fd9 */));

        }

        @Override
        public final void setHALInstrumentation() {

        }

        @Override
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override
        public final void ping() {
            return;

        }

        @Override
        public final android.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.hidl.base.V1_0.DebugInfo info = new android.hidl.base.V1_0.DebugInfo();
            info.pid = -1;
            info.ptr = 0;
            info.arch = android.hidl.base.V1_0.DebugInfo.Architecture.UNKNOWN;return info;
        }

        @Override
        public final void notifySyspropsChanged() {
            android.os.SystemProperties.reportSyspropChanged();
        }

        @Override
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient recipient) {
            return true;

        }

        @Override
        public android.os.IHwInterface queryLocalInterface(String descriptor) {
            if (kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws android.os.RemoteException {
            registerService(serviceName);
        }

        @Override
        public String toString() {
            return this.interfaceDescriptor() + "@Stub";
        }

        @Override
        public void onTransact(int _hidl_code, android.os.HwParcel _hidl_request, final android.os.HwParcel _hidl_reply, int _hidl_flags)
                throws android.os.RemoteException {
            switch (_hidl_code) {
                case 1 /* getIccCardStatusResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.CardStatus cardStatus = new android.hardware.radio.V1_0.CardStatus();
                    cardStatus.readFromParcel(_hidl_request);
                    getIccCardStatusResponse(info, cardStatus);
                    break;
                }

                case 2 /* supplyIccPinForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    supplyIccPinForAppResponse(info, remainingRetries);
                    break;
                }

                case 3 /* supplyIccPukForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    supplyIccPukForAppResponse(info, remainingRetries);
                    break;
                }

                case 4 /* supplyIccPin2ForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    supplyIccPin2ForAppResponse(info, remainingRetries);
                    break;
                }

                case 5 /* supplyIccPuk2ForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    supplyIccPuk2ForAppResponse(info, remainingRetries);
                    break;
                }

                case 6 /* changeIccPinForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    changeIccPinForAppResponse(info, remainingRetries);
                    break;
                }

                case 7 /* changeIccPin2ForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    changeIccPin2ForAppResponse(info, remainingRetries);
                    break;
                }

                case 8 /* supplyNetworkDepersonalizationResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int remainingRetries = _hidl_request.readInt32();
                    supplyNetworkDepersonalizationResponse(info, remainingRetries);
                    break;
                }

                case 9 /* getCurrentCallsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.Call> calls = android.hardware.radio.V1_0.Call.readVectorFromParcel(_hidl_request);
                    getCurrentCallsResponse(info, calls);
                    break;
                }

                case 10 /* dialResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    dialResponse(info);
                    break;
                }

                case 11 /* getIMSIForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String imsi = _hidl_request.readString();
                    getIMSIForAppResponse(info, imsi);
                    break;
                }

                case 12 /* hangupConnectionResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    hangupConnectionResponse(info);
                    break;
                }

                case 13 /* hangupWaitingOrBackgroundResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    hangupWaitingOrBackgroundResponse(info);
                    break;
                }

                case 14 /* hangupForegroundResumeBackgroundResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    hangupForegroundResumeBackgroundResponse(info);
                    break;
                }

                case 15 /* switchWaitingOrHoldingAndActiveResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    switchWaitingOrHoldingAndActiveResponse(info);
                    break;
                }

                case 16 /* conferenceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    conferenceResponse(info);
                    break;
                }

                case 17 /* rejectCallResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    rejectCallResponse(info);
                    break;
                }

                case 18 /* getLastCallFailCauseResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.LastCallFailCauseInfo failCauseinfo = new android.hardware.radio.V1_0.LastCallFailCauseInfo();
                    failCauseinfo.readFromParcel(_hidl_request);
                    getLastCallFailCauseResponse(info, failCauseinfo);
                    break;
                }

                case 19 /* getSignalStrengthResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SignalStrength sigStrength = new android.hardware.radio.V1_0.SignalStrength();
                    sigStrength.readFromParcel(_hidl_request);
                    getSignalStrengthResponse(info, sigStrength);
                    break;
                }

                case 20 /* getVoiceRegistrationStateResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.VoiceRegStateResult voiceRegResponse = new android.hardware.radio.V1_0.VoiceRegStateResult();
                    voiceRegResponse.readFromParcel(_hidl_request);
                    getVoiceRegistrationStateResponse(info, voiceRegResponse);
                    break;
                }

                case 21 /* getDataRegistrationStateResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.DataRegStateResult dataRegResponse = new android.hardware.radio.V1_0.DataRegStateResult();
                    dataRegResponse.readFromParcel(_hidl_request);
                    getDataRegistrationStateResponse(info, dataRegResponse);
                    break;
                }

                case 22 /* getOperatorResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String longName = _hidl_request.readString();
                    String shortName = _hidl_request.readString();
                    String numeric = _hidl_request.readString();
                    getOperatorResponse(info, longName, shortName, numeric);
                    break;
                }

                case 23 /* setRadioPowerResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setRadioPowerResponse(info);
                    break;
                }

                case 24 /* sendDtmfResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendDtmfResponse(info);
                    break;
                }

                case 25 /* sendSmsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SendSmsResult sms = new android.hardware.radio.V1_0.SendSmsResult();
                    sms.readFromParcel(_hidl_request);
                    sendSmsResponse(info, sms);
                    break;
                }

                case 26 /* sendSMSExpectMoreResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SendSmsResult sms = new android.hardware.radio.V1_0.SendSmsResult();
                    sms.readFromParcel(_hidl_request);
                    sendSMSExpectMoreResponse(info, sms);
                    break;
                }

                case 27 /* setupDataCallResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SetupDataCallResult dcResponse = new android.hardware.radio.V1_0.SetupDataCallResult();
                    dcResponse.readFromParcel(_hidl_request);
                    setupDataCallResponse(info, dcResponse);
                    break;
                }

                case 28 /* iccIOForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.IccIoResult iccIo = new android.hardware.radio.V1_0.IccIoResult();
                    iccIo.readFromParcel(_hidl_request);
                    iccIOForAppResponse(info, iccIo);
                    break;
                }

                case 29 /* sendUssdResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendUssdResponse(info);
                    break;
                }

                case 30 /* cancelPendingUssdResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    cancelPendingUssdResponse(info);
                    break;
                }

                case 31 /* getClirResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int n = _hidl_request.readInt32();
                    int m = _hidl_request.readInt32();
                    getClirResponse(info, n, m);
                    break;
                }

                case 32 /* setClirResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setClirResponse(info);
                    break;
                }

                case 33 /* getCallForwardStatusResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.CallForwardInfo> callForwardInfos = android.hardware.radio.V1_0.CallForwardInfo.readVectorFromParcel(_hidl_request);
                    getCallForwardStatusResponse(info, callForwardInfos);
                    break;
                }

                case 34 /* setCallForwardResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCallForwardResponse(info);
                    break;
                }

                case 35 /* getCallWaitingResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean enable = _hidl_request.readBool();
                    int serviceClass = _hidl_request.readInt32();
                    getCallWaitingResponse(info, enable, serviceClass);
                    break;
                }

                case 36 /* setCallWaitingResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCallWaitingResponse(info);
                    break;
                }

                case 37 /* acknowledgeLastIncomingGsmSmsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    acknowledgeLastIncomingGsmSmsResponse(info);
                    break;
                }

                case 38 /* acceptCallResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    acceptCallResponse(info);
                    break;
                }

                case 39 /* deactivateDataCallResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    deactivateDataCallResponse(info);
                    break;
                }

                case 40 /* getFacilityLockForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int response = _hidl_request.readInt32();
                    getFacilityLockForAppResponse(info, response);
                    break;
                }

                case 41 /* setFacilityLockForAppResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int retry = _hidl_request.readInt32();
                    setFacilityLockForAppResponse(info, retry);
                    break;
                }

                case 42 /* setBarringPasswordResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setBarringPasswordResponse(info);
                    break;
                }

                case 43 /* getNetworkSelectionModeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean manual = _hidl_request.readBool();
                    getNetworkSelectionModeResponse(info, manual);
                    break;
                }

                case 44 /* setNetworkSelectionModeAutomaticResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setNetworkSelectionModeAutomaticResponse(info);
                    break;
                }

                case 45 /* setNetworkSelectionModeManualResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setNetworkSelectionModeManualResponse(info);
                    break;
                }

                case 46 /* getAvailableNetworksResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> networkInfos = android.hardware.radio.V1_0.OperatorInfo.readVectorFromParcel(_hidl_request);
                    getAvailableNetworksResponse(info, networkInfos);
                    break;
                }

                case 47 /* startDtmfResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    startDtmfResponse(info);
                    break;
                }

                case 48 /* stopDtmfResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    stopDtmfResponse(info);
                    break;
                }

                case 49 /* getBasebandVersionResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String version = _hidl_request.readString();
                    getBasebandVersionResponse(info, version);
                    break;
                }

                case 50 /* separateConnectionResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    separateConnectionResponse(info);
                    break;
                }

                case 51 /* setMuteResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setMuteResponse(info);
                    break;
                }

                case 52 /* getMuteResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean enable = _hidl_request.readBool();
                    getMuteResponse(info, enable);
                    break;
                }

                case 53 /* getClipResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int status = _hidl_request.readInt32();
                    getClipResponse(info, status);
                    break;
                }

                case 54 /* getDataCallListResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> dcResponse = android.hardware.radio.V1_0.SetupDataCallResult.readVectorFromParcel(_hidl_request);
                    getDataCallListResponse(info, dcResponse);
                    break;
                }

                case 55 /* setSuppServiceNotificationsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setSuppServiceNotificationsResponse(info);
                    break;
                }

                case 56 /* writeSmsToSimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int index = _hidl_request.readInt32();
                    writeSmsToSimResponse(info, index);
                    break;
                }

                case 57 /* deleteSmsOnSimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    deleteSmsOnSimResponse(info);
                    break;
                }

                case 58 /* setBandModeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setBandModeResponse(info);
                    break;
                }

                case 59 /* getAvailableBandModesResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<Integer> bandModes = _hidl_request.readInt32Vector();
                    getAvailableBandModesResponse(info, bandModes);
                    break;
                }

                case 60 /* sendEnvelopeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String commandResponse = _hidl_request.readString();
                    sendEnvelopeResponse(info, commandResponse);
                    break;
                }

                case 61 /* sendTerminalResponseToSimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendTerminalResponseToSimResponse(info);
                    break;
                }

                case 62 /* handleStkCallSetupRequestFromSimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    handleStkCallSetupRequestFromSimResponse(info);
                    break;
                }

                case 63 /* explicitCallTransferResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    explicitCallTransferResponse(info);
                    break;
                }

                case 64 /* setPreferredNetworkTypeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setPreferredNetworkTypeResponse(info);
                    break;
                }

                case 65 /* getPreferredNetworkTypeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int nwType = _hidl_request.readInt32();
                    getPreferredNetworkTypeResponse(info, nwType);
                    break;
                }

                case 66 /* getNeighboringCidsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.NeighboringCell> cells = android.hardware.radio.V1_0.NeighboringCell.readVectorFromParcel(_hidl_request);
                    getNeighboringCidsResponse(info, cells);
                    break;
                }

                case 67 /* setLocationUpdatesResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setLocationUpdatesResponse(info);
                    break;
                }

                case 68 /* setCdmaSubscriptionSourceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCdmaSubscriptionSourceResponse(info);
                    break;
                }

                case 69 /* setCdmaRoamingPreferenceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCdmaRoamingPreferenceResponse(info);
                    break;
                }

                case 70 /* getCdmaRoamingPreferenceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int type = _hidl_request.readInt32();
                    getCdmaRoamingPreferenceResponse(info, type);
                    break;
                }

                case 71 /* setTTYModeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setTTYModeResponse(info);
                    break;
                }

                case 72 /* getTTYModeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int mode = _hidl_request.readInt32();
                    getTTYModeResponse(info, mode);
                    break;
                }

                case 73 /* setPreferredVoicePrivacyResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setPreferredVoicePrivacyResponse(info);
                    break;
                }

                case 74 /* getPreferredVoicePrivacyResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean enable = _hidl_request.readBool();
                    getPreferredVoicePrivacyResponse(info, enable);
                    break;
                }

                case 75 /* sendCDMAFeatureCodeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendCDMAFeatureCodeResponse(info);
                    break;
                }

                case 76 /* sendBurstDtmfResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendBurstDtmfResponse(info);
                    break;
                }

                case 77 /* sendCdmaSmsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SendSmsResult sms = new android.hardware.radio.V1_0.SendSmsResult();
                    sms.readFromParcel(_hidl_request);
                    sendCdmaSmsResponse(info, sms);
                    break;
                }

                case 78 /* acknowledgeLastIncomingCdmaSmsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    acknowledgeLastIncomingCdmaSmsResponse(info);
                    break;
                }

                case 79 /* getGsmBroadcastConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> configs = android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request);
                    getGsmBroadcastConfigResponse(info, configs);
                    break;
                }

                case 80 /* setGsmBroadcastConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setGsmBroadcastConfigResponse(info);
                    break;
                }

                case 81 /* setGsmBroadcastActivationResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setGsmBroadcastActivationResponse(info);
                    break;
                }

                case 82 /* getCdmaBroadcastConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> configs = android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request);
                    getCdmaBroadcastConfigResponse(info, configs);
                    break;
                }

                case 83 /* setCdmaBroadcastConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCdmaBroadcastConfigResponse(info);
                    break;
                }

                case 84 /* setCdmaBroadcastActivationResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCdmaBroadcastActivationResponse(info);
                    break;
                }

                case 85 /* getCDMASubscriptionResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String mdn = _hidl_request.readString();
                    String hSid = _hidl_request.readString();
                    String hNid = _hidl_request.readString();
                    String min = _hidl_request.readString();
                    String prl = _hidl_request.readString();
                    getCDMASubscriptionResponse(info, mdn, hSid, hNid, min, prl);
                    break;
                }

                case 86 /* writeSmsToRuimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int index = _hidl_request.readInt32();
                    writeSmsToRuimResponse(info, index);
                    break;
                }

                case 87 /* deleteSmsOnRuimResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    deleteSmsOnRuimResponse(info);
                    break;
                }

                case 88 /* getDeviceIdentityResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String imei = _hidl_request.readString();
                    String imeisv = _hidl_request.readString();
                    String esn = _hidl_request.readString();
                    String meid = _hidl_request.readString();
                    getDeviceIdentityResponse(info, imei, imeisv, esn, meid);
                    break;
                }

                case 89 /* exitEmergencyCallbackModeResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    exitEmergencyCallbackModeResponse(info);
                    break;
                }

                case 90 /* getSmscAddressResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String smsc = _hidl_request.readString();
                    getSmscAddressResponse(info, smsc);
                    break;
                }

                case 91 /* setSmscAddressResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setSmscAddressResponse(info);
                    break;
                }

                case 92 /* reportSmsMemoryStatusResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    reportSmsMemoryStatusResponse(info);
                    break;
                }

                case 93 /* reportStkServiceIsRunningResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    reportStkServiceIsRunningResponse(info);
                    break;
                }

                case 94 /* getCdmaSubscriptionSourceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int source = _hidl_request.readInt32();
                    getCdmaSubscriptionSourceResponse(info, source);
                    break;
                }

                case 95 /* requestIsimAuthenticationResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String response = _hidl_request.readString();
                    requestIsimAuthenticationResponse(info, response);
                    break;
                }

                case 96 /* acknowledgeIncomingGsmSmsWithPduResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    acknowledgeIncomingGsmSmsWithPduResponse(info);
                    break;
                }

                case 97 /* sendEnvelopeWithStatusResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.IccIoResult iccIo = new android.hardware.radio.V1_0.IccIoResult();
                    iccIo.readFromParcel(_hidl_request);
                    sendEnvelopeWithStatusResponse(info, iccIo);
                    break;
                }

                case 98 /* getVoiceRadioTechnologyResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int rat = _hidl_request.readInt32();
                    getVoiceRadioTechnologyResponse(info, rat);
                    break;
                }

                case 99 /* getCellInfoListResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.CellInfo> cellInfo = android.hardware.radio.V1_0.CellInfo.readVectorFromParcel(_hidl_request);
                    getCellInfoListResponse(info, cellInfo);
                    break;
                }

                case 100 /* setCellInfoListRateResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCellInfoListRateResponse(info);
                    break;
                }

                case 101 /* setInitialAttachApnResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setInitialAttachApnResponse(info);
                    break;
                }

                case 102 /* getImsRegistrationStateResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean isRegistered = _hidl_request.readBool();
                    int ratFamily = _hidl_request.readInt32();
                    getImsRegistrationStateResponse(info, isRegistered, ratFamily);
                    break;
                }

                case 103 /* sendImsSmsResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.SendSmsResult sms = new android.hardware.radio.V1_0.SendSmsResult();
                    sms.readFromParcel(_hidl_request);
                    sendImsSmsResponse(info, sms);
                    break;
                }

                case 104 /* iccTransmitApduBasicChannelResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.IccIoResult result = new android.hardware.radio.V1_0.IccIoResult();
                    result.readFromParcel(_hidl_request);
                    iccTransmitApduBasicChannelResponse(info, result);
                    break;
                }

                case 105 /* iccOpenLogicalChannelResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int channelId = _hidl_request.readInt32();
                    java.util.ArrayList<Byte> selectResponse = _hidl_request.readInt8Vector();
                    iccOpenLogicalChannelResponse(info, channelId, selectResponse);
                    break;
                }

                case 106 /* iccCloseLogicalChannelResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    iccCloseLogicalChannelResponse(info);
                    break;
                }

                case 107 /* iccTransmitApduLogicalChannelResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.IccIoResult result = new android.hardware.radio.V1_0.IccIoResult();
                    result.readFromParcel(_hidl_request);
                    iccTransmitApduLogicalChannelResponse(info, result);
                    break;
                }

                case 108 /* nvReadItemResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    String result = _hidl_request.readString();
                    nvReadItemResponse(info, result);
                    break;
                }

                case 109 /* nvWriteItemResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    nvWriteItemResponse(info);
                    break;
                }

                case 110 /* nvWriteCdmaPrlResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    nvWriteCdmaPrlResponse(info);
                    break;
                }

                case 111 /* nvResetConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    nvResetConfigResponse(info);
                    break;
                }

                case 112 /* setUiccSubscriptionResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setUiccSubscriptionResponse(info);
                    break;
                }

                case 113 /* setDataAllowedResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setDataAllowedResponse(info);
                    break;
                }

                case 114 /* getHardwareConfigResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> config = android.hardware.radio.V1_0.HardwareConfig.readVectorFromParcel(_hidl_request);
                    getHardwareConfigResponse(info, config);
                    break;
                }

                case 115 /* requestIccSimAuthenticationResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.IccIoResult result = new android.hardware.radio.V1_0.IccIoResult();
                    result.readFromParcel(_hidl_request);
                    requestIccSimAuthenticationResponse(info, result);
                    break;
                }

                case 116 /* setDataProfileResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setDataProfileResponse(info);
                    break;
                }

                case 117 /* requestShutdownResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    requestShutdownResponse(info);
                    break;
                }

                case 118 /* getRadioCapabilityResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.RadioCapability rc = new android.hardware.radio.V1_0.RadioCapability();
                    rc.readFromParcel(_hidl_request);
                    getRadioCapabilityResponse(info, rc);
                    break;
                }

                case 119 /* setRadioCapabilityResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.RadioCapability rc = new android.hardware.radio.V1_0.RadioCapability();
                    rc.readFromParcel(_hidl_request);
                    setRadioCapabilityResponse(info, rc);
                    break;
                }

                case 120 /* startLceServiceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.LceStatusInfo statusInfo = new android.hardware.radio.V1_0.LceStatusInfo();
                    statusInfo.readFromParcel(_hidl_request);
                    startLceServiceResponse(info, statusInfo);
                    break;
                }

                case 121 /* stopLceServiceResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.LceStatusInfo statusInfo = new android.hardware.radio.V1_0.LceStatusInfo();
                    statusInfo.readFromParcel(_hidl_request);
                    stopLceServiceResponse(info, statusInfo);
                    break;
                }

                case 122 /* pullLceDataResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.LceDataInfo lceInfo = new android.hardware.radio.V1_0.LceDataInfo();
                    lceInfo.readFromParcel(_hidl_request);
                    pullLceDataResponse(info, lceInfo);
                    break;
                }

                case 123 /* getModemActivityInfoResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    android.hardware.radio.V1_0.ActivityStatsInfo activityInfo = new android.hardware.radio.V1_0.ActivityStatsInfo();
                    activityInfo.readFromParcel(_hidl_request);
                    getModemActivityInfoResponse(info, activityInfo);
                    break;
                }

                case 124 /* setAllowedCarriersResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    int numAllowed = _hidl_request.readInt32();
                    setAllowedCarriersResponse(info, numAllowed);
                    break;
                }

                case 125 /* getAllowedCarriersResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    boolean allAllowed = _hidl_request.readBool();
                    android.hardware.radio.V1_0.CarrierRestrictions carriers = new android.hardware.radio.V1_0.CarrierRestrictions();
                    carriers.readFromParcel(_hidl_request);
                    getAllowedCarriersResponse(info, allAllowed, carriers);
                    break;
                }

                case 126 /* sendDeviceStateResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    sendDeviceStateResponse(info);
                    break;
                }

                case 127 /* setIndicationFilterResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setIndicationFilterResponse(info);
                    break;
                }

                case 128 /* setSimCardPowerResponse */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setSimCardPowerResponse(info);
                    break;
                }

                case 129 /* acknowledgeRequest */:
                {
                    _hidl_request.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    acknowledgeRequest(serial);
                    break;
                }

                case 130 /* setCarrierInfoForImsiEncryptionResponse */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setCarrierInfoForImsiEncryptionResponse(info);
                    break;
                }

                case 131 /* setSimCardPowerResponse_1_1 */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    setSimCardPowerResponse_1_1(info);
                    break;
                }

                case 132 /* startNetworkScanResponse */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    startNetworkScanResponse(info);
                    break;
                }

                case 133 /* stopNetworkScanResponse */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    stopNetworkScanResponse(info);
                    break;
                }

                case 134 /* startKeepaliveResponse */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    KeepaliveStatus status = new KeepaliveStatus();
                    status.readFromParcel(_hidl_request);
                    startKeepaliveResponse(info, status);
                    break;
                }

                case 135 /* stopKeepaliveResponse */:
                {
                    _hidl_request.enforceInterface(IRadioResponse.kInterfaceName);

                    android.hardware.radio.V1_0.RadioResponseInfo info = new android.hardware.radio.V1_0.RadioResponseInfo();
                    info.readFromParcel(_hidl_request);
                    stopKeepaliveResponse(info);
                    break;
                }

                case 256067662 /* interfaceChain */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    java.util.ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    _hidl_reply.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    break;
                }

                case 256131655 /* debug */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    _hidl_reply.send();
                    break;
                }

                case 256136003 /* interfaceDescriptor */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    String _hidl_out_descriptor = interfaceDescriptor();
                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    _hidl_reply.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    break;
                }

                case 256398152 /* getHashChain */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    java.util.ArrayList<byte[/* 32 */]> _hidl_out_hashchain = getHashChain();
                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    {
                        android.os.HwBlob _hidl_blob = new android.os.HwBlob(16 /* size */);
                        {
                            int _hidl_vec_size = _hidl_out_hashchain.size();
                            _hidl_blob.putInt32(0 /* offset */ + 8 /* offsetof(hidl_vec<T>, mSize) */, _hidl_vec_size);
                            _hidl_blob.putBool(0 /* offset */ + 12 /* offsetof(hidl_vec<T>, mOwnsBuffer) */, false);
                            android.os.HwBlob childBlob = new android.os.HwBlob((int)(_hidl_vec_size * 32));
                            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; ++_hidl_index_0) {
                                {
                                    long _hidl_array_offset_1 = _hidl_index_0 * 32;
                                    for (int _hidl_index_1_0 = 0; _hidl_index_1_0 < 32; ++_hidl_index_1_0) {
                                        childBlob.putInt8(_hidl_array_offset_1, _hidl_out_hashchain.get(_hidl_index_0)[_hidl_index_1_0]);
                                        _hidl_array_offset_1 += 1;
                                    }
                                }
                            }
                            _hidl_blob.putBlob(0 /* offset */ + 0 /* offsetof(hidl_vec<T>, mBuffer) */, childBlob);
                        }
                        _hidl_reply.writeBuffer(_hidl_blob);
                    }
                    _hidl_reply.send();
                    break;
                }

                case 256462420 /* setHALInstrumentation */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    setHALInstrumentation();
                    break;
                }

                case 256660548 /* linkToDeath */:
                {
                break;
                }

                case 256921159 /* ping */:
                {
                break;
                }

                case 257049926 /* getDebugInfo */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    android.hidl.base.V1_0.DebugInfo _hidl_out_info = getDebugInfo();
                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    _hidl_out_info.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    break;
                }

                case 257120595 /* notifySyspropsChanged */:
                {
                    _hidl_request.enforceInterface(android.hidl.base.V1_0.IBase.kInterfaceName);

                    notifySyspropsChanged();
                    break;
                }

                case 257250372 /* unlinkToDeath */:
                {
                break;
                }

            }
        }
    }
}
