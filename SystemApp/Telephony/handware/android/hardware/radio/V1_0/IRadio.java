package android.hardware.radio.V1_0;

public interface IRadio extends android.hidl.base.V1_0.IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadio";

    /* package private */ static IRadio asInterface(android.os.IHwBinder binder) {
        if (binder == null) {
            return null;
        }

        android.os.IHwInterface iface =
                binder.queryLocalInterface(kInterfaceName);

        if ((iface != null) && (iface instanceof IRadio)) {
            return (IRadio)iface;
        }

        IRadio proxy = new IRadio.Proxy(binder);

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

    public static IRadio castFrom(android.os.IHwInterface iface) {
        return (iface == null) ? null : IRadio.asInterface(iface.asBinder());
    }

    @Override
    public android.os.IHwBinder asBinder();

    public static IRadio getService(String serviceName) throws android.os.RemoteException {
        return IRadio.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.0::IRadio",serviceName));
    }

    public static IRadio getService() throws android.os.RemoteException {
        return IRadio.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.0::IRadio","default"));
    }

    void setResponseFunctions(IRadioResponse radioResponse, IRadioIndication radioIndication)
        throws android.os.RemoteException;
    void getIccCardStatus(int serial)
        throws android.os.RemoteException;
    void supplyIccPinForApp(int serial, String pin, String aid)
        throws android.os.RemoteException;
    void supplyIccPukForApp(int serial, String puk, String pin, String aid)
        throws android.os.RemoteException;
    void supplyIccPin2ForApp(int serial, String pin2, String aid)
        throws android.os.RemoteException;
    void supplyIccPuk2ForApp(int serial, String puk2, String pin2, String aid)
        throws android.os.RemoteException;
    void changeIccPinForApp(int serial, String oldPin, String newPin, String aid)
        throws android.os.RemoteException;
    void changeIccPin2ForApp(int serial, String oldPin2, String newPin2, String aid)
        throws android.os.RemoteException;
    void supplyNetworkDepersonalization(int serial, String netPin)
        throws android.os.RemoteException;
    void getCurrentCalls(int serial)
        throws android.os.RemoteException;
    void dial(int serial, Dial dialInfo)
        throws android.os.RemoteException;
    void getImsiForApp(int serial, String aid)
        throws android.os.RemoteException;
    void hangup(int serial, int gsmIndex)
        throws android.os.RemoteException;
    void hangupWaitingOrBackground(int serial)
        throws android.os.RemoteException;
    void hangupForegroundResumeBackground(int serial)
        throws android.os.RemoteException;
    void switchWaitingOrHoldingAndActive(int serial)
        throws android.os.RemoteException;
    void conference(int serial)
        throws android.os.RemoteException;
    void rejectCall(int serial)
        throws android.os.RemoteException;
    void getLastCallFailCause(int serial)
        throws android.os.RemoteException;
    void getSignalStrength(int serial)
        throws android.os.RemoteException;
    void getVoiceRegistrationState(int serial)
        throws android.os.RemoteException;
    void getDataRegistrationState(int serial)
        throws android.os.RemoteException;
    void getOperator(int serial)
        throws android.os.RemoteException;
    void setRadioPower(int serial, boolean on)
        throws android.os.RemoteException;
    void sendDtmf(int serial, String s)
        throws android.os.RemoteException;
    void sendSms(int serial, GsmSmsMessage message)
        throws android.os.RemoteException;
    void sendSMSExpectMore(int serial, GsmSmsMessage message)
        throws android.os.RemoteException;
    void setupDataCall(int serial, int radioTechnology, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean roamingAllowed, boolean isRoaming)
        throws android.os.RemoteException;
    void iccIOForApp(int serial, IccIo iccIo)
        throws android.os.RemoteException;
    void sendUssd(int serial, String ussd)
        throws android.os.RemoteException;
    void cancelPendingUssd(int serial)
        throws android.os.RemoteException;
    void getClir(int serial)
        throws android.os.RemoteException;
    void setClir(int serial, int status)
        throws android.os.RemoteException;
    void getCallForwardStatus(int serial, CallForwardInfo callInfo)
        throws android.os.RemoteException;
    void setCallForward(int serial, CallForwardInfo callInfo)
        throws android.os.RemoteException;
    void getCallWaiting(int serial, int serviceClass)
        throws android.os.RemoteException;
    void setCallWaiting(int serial, boolean enable, int serviceClass)
        throws android.os.RemoteException;
    void acknowledgeLastIncomingGsmSms(int serial, boolean success, int cause)
        throws android.os.RemoteException;
    void acceptCall(int serial)
        throws android.os.RemoteException;
    void deactivateDataCall(int serial, int cid, boolean reasonRadioShutDown)
        throws android.os.RemoteException;
    void getFacilityLockForApp(int serial, String facility, String password, int serviceClass, String appId)
        throws android.os.RemoteException;
    void setFacilityLockForApp(int serial, String facility, boolean lockState, String password, int serviceClass, String appId)
        throws android.os.RemoteException;
    void setBarringPassword(int serial, String facility, String oldPassword, String newPassword)
        throws android.os.RemoteException;
    void getNetworkSelectionMode(int serial)
        throws android.os.RemoteException;
    void setNetworkSelectionModeAutomatic(int serial)
        throws android.os.RemoteException;
    void setNetworkSelectionModeManual(int serial, String operatorNumeric)
        throws android.os.RemoteException;
    void getAvailableNetworks(int serial)
        throws android.os.RemoteException;
    void startDtmf(int serial, String s)
        throws android.os.RemoteException;
    void stopDtmf(int serial)
        throws android.os.RemoteException;
    void getBasebandVersion(int serial)
        throws android.os.RemoteException;
    void separateConnection(int serial, int gsmIndex)
        throws android.os.RemoteException;
    void setMute(int serial, boolean enable)
        throws android.os.RemoteException;
    void getMute(int serial)
        throws android.os.RemoteException;
    void getClip(int serial)
        throws android.os.RemoteException;
    void getDataCallList(int serial)
        throws android.os.RemoteException;
    void setSuppServiceNotifications(int serial, boolean enable)
        throws android.os.RemoteException;
    void writeSmsToSim(int serial, SmsWriteArgs smsWriteArgs)
        throws android.os.RemoteException;
    void deleteSmsOnSim(int serial, int index)
        throws android.os.RemoteException;
    void setBandMode(int serial, int mode)
        throws android.os.RemoteException;
    void getAvailableBandModes(int serial)
        throws android.os.RemoteException;
    void sendEnvelope(int serial, String command)
        throws android.os.RemoteException;
    void sendTerminalResponseToSim(int serial, String commandResponse)
        throws android.os.RemoteException;
    void handleStkCallSetupRequestFromSim(int serial, boolean accept)
        throws android.os.RemoteException;
    void explicitCallTransfer(int serial)
        throws android.os.RemoteException;
    void setPreferredNetworkType(int serial, int nwType)
        throws android.os.RemoteException;
    void getPreferredNetworkType(int serial)
        throws android.os.RemoteException;
    void getNeighboringCids(int serial)
        throws android.os.RemoteException;
    void setLocationUpdates(int serial, boolean enable)
        throws android.os.RemoteException;
    void setCdmaSubscriptionSource(int serial, int cdmaSub)
        throws android.os.RemoteException;
    void setCdmaRoamingPreference(int serial, int type)
        throws android.os.RemoteException;
    void getCdmaRoamingPreference(int serial)
        throws android.os.RemoteException;
    void setTTYMode(int serial, int mode)
        throws android.os.RemoteException;
    void getTTYMode(int serial)
        throws android.os.RemoteException;
    void setPreferredVoicePrivacy(int serial, boolean enable)
        throws android.os.RemoteException;
    void getPreferredVoicePrivacy(int serial)
        throws android.os.RemoteException;
    void sendCDMAFeatureCode(int serial, String featureCode)
        throws android.os.RemoteException;
    void sendBurstDtmf(int serial, String dtmf, int on, int off)
        throws android.os.RemoteException;
    void sendCdmaSms(int serial, CdmaSmsMessage sms)
        throws android.os.RemoteException;
    void acknowledgeLastIncomingCdmaSms(int serial, CdmaSmsAck smsAck)
        throws android.os.RemoteException;
    void getGsmBroadcastConfig(int serial)
        throws android.os.RemoteException;
    void setGsmBroadcastConfig(int serial, java.util.ArrayList<GsmBroadcastSmsConfigInfo> configInfo)
        throws android.os.RemoteException;
    void setGsmBroadcastActivation(int serial, boolean activate)
        throws android.os.RemoteException;
    void getCdmaBroadcastConfig(int serial)
        throws android.os.RemoteException;
    void setCdmaBroadcastConfig(int serial, java.util.ArrayList<CdmaBroadcastSmsConfigInfo> configInfo)
        throws android.os.RemoteException;
    void setCdmaBroadcastActivation(int serial, boolean activate)
        throws android.os.RemoteException;
    void getCDMASubscription(int serial)
        throws android.os.RemoteException;
    void writeSmsToRuim(int serial, CdmaSmsWriteArgs cdmaSms)
        throws android.os.RemoteException;
    void deleteSmsOnRuim(int serial, int index)
        throws android.os.RemoteException;
    void getDeviceIdentity(int serial)
        throws android.os.RemoteException;
    void exitEmergencyCallbackMode(int serial)
        throws android.os.RemoteException;
    void getSmscAddress(int serial)
        throws android.os.RemoteException;
    void setSmscAddress(int serial, String smsc)
        throws android.os.RemoteException;
    void reportSmsMemoryStatus(int serial, boolean available)
        throws android.os.RemoteException;
    void reportStkServiceIsRunning(int serial)
        throws android.os.RemoteException;
    void getCdmaSubscriptionSource(int serial)
        throws android.os.RemoteException;
    void requestIsimAuthentication(int serial, String challenge)
        throws android.os.RemoteException;
    void acknowledgeIncomingGsmSmsWithPdu(int serial, boolean success, String ackPdu)
        throws android.os.RemoteException;
    void sendEnvelopeWithStatus(int serial, String contents)
        throws android.os.RemoteException;
    void getVoiceRadioTechnology(int serial)
        throws android.os.RemoteException;
    void getCellInfoList(int serial)
        throws android.os.RemoteException;
    void setCellInfoListRate(int serial, int rate)
        throws android.os.RemoteException;
    void setInitialAttachApn(int serial, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean isRoaming)
        throws android.os.RemoteException;
    void getImsRegistrationState(int serial)
        throws android.os.RemoteException;
    void sendImsSms(int serial, ImsSmsMessage message)
        throws android.os.RemoteException;
    void iccTransmitApduBasicChannel(int serial, SimApdu message)
        throws android.os.RemoteException;
    void iccOpenLogicalChannel(int serial, String aid, int p2)
        throws android.os.RemoteException;
    void iccCloseLogicalChannel(int serial, int channelId)
        throws android.os.RemoteException;
    void iccTransmitApduLogicalChannel(int serial, SimApdu message)
        throws android.os.RemoteException;
    void nvReadItem(int serial, int itemId)
        throws android.os.RemoteException;
    void nvWriteItem(int serial, NvWriteItem item)
        throws android.os.RemoteException;
    void nvWriteCdmaPrl(int serial, java.util.ArrayList<Byte> prl)
        throws android.os.RemoteException;
    void nvResetConfig(int serial, int resetType)
        throws android.os.RemoteException;
    void setUiccSubscription(int serial, SelectUiccSub uiccSub)
        throws android.os.RemoteException;
    void setDataAllowed(int serial, boolean allow)
        throws android.os.RemoteException;
    void getHardwareConfig(int serial)
        throws android.os.RemoteException;
    void requestIccSimAuthentication(int serial, int authContext, String authData, String aid)
        throws android.os.RemoteException;
    void setDataProfile(int serial, java.util.ArrayList<DataProfileInfo> profiles, boolean isRoaming)
        throws android.os.RemoteException;
    void requestShutdown(int serial)
        throws android.os.RemoteException;
    void getRadioCapability(int serial)
        throws android.os.RemoteException;
    void setRadioCapability(int serial, RadioCapability rc)
        throws android.os.RemoteException;
    void startLceService(int serial, int reportInterval, boolean pullMode)
        throws android.os.RemoteException;
    void stopLceService(int serial)
        throws android.os.RemoteException;
    void pullLceData(int serial)
        throws android.os.RemoteException;
    void getModemActivityInfo(int serial)
        throws android.os.RemoteException;
    void setAllowedCarriers(int serial, boolean allAllowed, CarrierRestrictions carriers)
        throws android.os.RemoteException;
    void getAllowedCarriers(int serial)
        throws android.os.RemoteException;
    void sendDeviceState(int serial, int deviceStateType, boolean state)
        throws android.os.RemoteException;
    void setIndicationFilter(int serial, int indicationFilter)
        throws android.os.RemoteException;
    void setSimCardPower(int serial, boolean powerUp)
        throws android.os.RemoteException;
    void responseAcknowledgement()
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

    public static final class Proxy implements IRadio {
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
            return "[class or subclass of " + IRadio.kInterfaceName + "]@Proxy";
        }

        // Methods from ::android::hardware::radio::V1_0::IRadio follow.
        @Override
        public void setResponseFunctions(IRadioResponse radioResponse, IRadioIndication radioIndication)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            _hidl_request.writeStrongBinder(radioIndication == null ? null : radioIndication.asBinder());

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(1 /* setResponseFunctions */, _hidl_request, _hidl_reply, 0 /* flags */);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getIccCardStatus(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(2 /* getIccCardStatus */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPinForApp(int serial, String pin, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(3 /* supplyIccPinForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPukForApp(int serial, String puk, String pin, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(4 /* supplyIccPukForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPin2ForApp(int serial, String pin2, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(5 /* supplyIccPin2ForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyIccPuk2ForApp(int serial, String puk2, String pin2, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk2);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(6 /* supplyIccPuk2ForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void changeIccPinForApp(int serial, String oldPin, String newPin, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin);
            _hidl_request.writeString(newPin);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(7 /* changeIccPinForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void changeIccPin2ForApp(int serial, String oldPin2, String newPin2, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin2);
            _hidl_request.writeString(newPin2);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(8 /* changeIccPin2ForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void supplyNetworkDepersonalization(int serial, String netPin)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(netPin);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(9 /* supplyNetworkDepersonalization */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCurrentCalls(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(10 /* getCurrentCalls */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void dial(int serial, Dial dialInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(11 /* dial */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getImsiForApp(int serial, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(12 /* getImsiForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangup(int serial, int gsmIndex)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(13 /* hangup */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangupWaitingOrBackground(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(14 /* hangupWaitingOrBackground */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hangupForegroundResumeBackground(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(15 /* hangupForegroundResumeBackground */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void switchWaitingOrHoldingAndActive(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(16 /* switchWaitingOrHoldingAndActive */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void conference(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(17 /* conference */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void rejectCall(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(18 /* rejectCall */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getLastCallFailCause(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(19 /* getLastCallFailCause */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getSignalStrength(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(20 /* getSignalStrength */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getVoiceRegistrationState(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(21 /* getVoiceRegistrationState */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDataRegistrationState(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(22 /* getDataRegistrationState */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getOperator(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(23 /* getOperator */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setRadioPower(int serial, boolean on)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(on);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(24 /* setRadioPower */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendDtmf(int serial, String s)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(25 /* sendDtmf */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendSms(int serial, GsmSmsMessage message)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(26 /* sendSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendSMSExpectMore(int serial, GsmSmsMessage message)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(27 /* sendSMSExpectMore */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setupDataCall(int serial, int radioTechnology, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean roamingAllowed, boolean isRoaming)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(radioTechnology);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeBool(isRoaming);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(28 /* setupDataCall */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccIOForApp(int serial, IccIo iccIo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            iccIo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(29 /* iccIOForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendUssd(int serial, String ussd)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ussd);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(30 /* sendUssd */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cancelPendingUssd(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(31 /* cancelPendingUssd */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getClir(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(32 /* getClir */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setClir(int serial, int status)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(status);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(33 /* setClir */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCallForwardStatus(int serial, CallForwardInfo callInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(34 /* getCallForwardStatus */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCallForward(int serial, CallForwardInfo callInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(35 /* setCallForward */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCallWaiting(int serial, int serviceClass)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(serviceClass);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(36 /* getCallWaiting */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCallWaiting(int serial, boolean enable, int serviceClass)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(37 /* setCallWaiting */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeLastIncomingGsmSms(int serial, boolean success, int cause)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeInt32(cause);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(38 /* acknowledgeLastIncomingGsmSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acceptCall(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(39 /* acceptCall */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deactivateDataCall(int serial, int cid, boolean reasonRadioShutDown)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cid);
            _hidl_request.writeBool(reasonRadioShutDown);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(40 /* deactivateDataCall */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getFacilityLockForApp(int serial, String facility, String password, int serviceClass, String appId)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(41 /* getFacilityLockForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setFacilityLockForApp(int serial, String facility, boolean lockState, String password, int serviceClass, String appId)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeBool(lockState);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(42 /* setFacilityLockForApp */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setBarringPassword(int serial, String facility, String oldPassword, String newPassword)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(oldPassword);
            _hidl_request.writeString(newPassword);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(43 /* setBarringPassword */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getNetworkSelectionMode(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(44 /* getNetworkSelectionMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setNetworkSelectionModeAutomatic(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(45 /* setNetworkSelectionModeAutomatic */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setNetworkSelectionModeManual(int serial, String operatorNumeric)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(46 /* setNetworkSelectionModeManual */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAvailableNetworks(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(47 /* getAvailableNetworks */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startDtmf(int serial, String s)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(48 /* startDtmf */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopDtmf(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(49 /* stopDtmf */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getBasebandVersion(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(50 /* getBasebandVersion */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void separateConnection(int serial, int gsmIndex)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(51 /* separateConnection */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setMute(int serial, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(52 /* setMute */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getMute(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(53 /* getMute */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getClip(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(54 /* getClip */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDataCallList(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(55 /* getDataCallList */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSuppServiceNotifications(int serial, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(56 /* setSuppServiceNotifications */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void writeSmsToSim(int serial, SmsWriteArgs smsWriteArgs)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsWriteArgs.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(57 /* writeSmsToSim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deleteSmsOnSim(int serial, int index)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(58 /* deleteSmsOnSim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setBandMode(int serial, int mode)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(59 /* setBandMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAvailableBandModes(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(60 /* getAvailableBandModes */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendEnvelope(int serial, String command)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(command);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(61 /* sendEnvelope */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendTerminalResponseToSim(int serial, String commandResponse)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(commandResponse);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(62 /* sendTerminalResponseToSim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void handleStkCallSetupRequestFromSim(int serial, boolean accept)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(accept);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(63 /* handleStkCallSetupRequestFromSim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void explicitCallTransfer(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(64 /* explicitCallTransfer */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setPreferredNetworkType(int serial, int nwType)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(nwType);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(65 /* setPreferredNetworkType */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getPreferredNetworkType(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(66 /* getPreferredNetworkType */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getNeighboringCids(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(67 /* getNeighboringCids */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setLocationUpdates(int serial, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(68 /* setLocationUpdates */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaSubscriptionSource(int serial, int cdmaSub)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cdmaSub);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(69 /* setCdmaSubscriptionSource */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaRoamingPreference(int serial, int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(70 /* setCdmaRoamingPreference */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaRoamingPreference(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(71 /* getCdmaRoamingPreference */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setTTYMode(int serial, int mode)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(72 /* setTTYMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getTTYMode(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(73 /* getTTYMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setPreferredVoicePrivacy(int serial, boolean enable)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(74 /* setPreferredVoicePrivacy */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getPreferredVoicePrivacy(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(75 /* getPreferredVoicePrivacy */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendCDMAFeatureCode(int serial, String featureCode)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(featureCode);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(76 /* sendCDMAFeatureCode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendBurstDtmf(int serial, String dtmf, int on, int off)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(dtmf);
            _hidl_request.writeInt32(on);
            _hidl_request.writeInt32(off);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(77 /* sendBurstDtmf */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendCdmaSms(int serial, CdmaSmsMessage sms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(78 /* sendCdmaSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeLastIncomingCdmaSms(int serial, CdmaSmsAck smsAck)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsAck.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(79 /* acknowledgeLastIncomingCdmaSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getGsmBroadcastConfig(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(80 /* getGsmBroadcastConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setGsmBroadcastConfig(int serial, java.util.ArrayList<GsmBroadcastSmsConfigInfo> configInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(81 /* setGsmBroadcastConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setGsmBroadcastActivation(int serial, boolean activate)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(82 /* setGsmBroadcastActivation */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaBroadcastConfig(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(83 /* getCdmaBroadcastConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaBroadcastConfig(int serial, java.util.ArrayList<CdmaBroadcastSmsConfigInfo> configInfo)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(84 /* setCdmaBroadcastConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCdmaBroadcastActivation(int serial, boolean activate)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(85 /* setCdmaBroadcastActivation */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCDMASubscription(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(86 /* getCDMASubscription */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void writeSmsToRuim(int serial, CdmaSmsWriteArgs cdmaSms)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            cdmaSms.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(87 /* writeSmsToRuim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void deleteSmsOnRuim(int serial, int index)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(88 /* deleteSmsOnRuim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getDeviceIdentity(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(89 /* getDeviceIdentity */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void exitEmergencyCallbackMode(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(90 /* exitEmergencyCallbackMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getSmscAddress(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(91 /* getSmscAddress */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSmscAddress(int serial, String smsc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(smsc);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(92 /* setSmscAddress */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void reportSmsMemoryStatus(int serial, boolean available)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(available);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(93 /* reportSmsMemoryStatus */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void reportStkServiceIsRunning(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(94 /* reportStkServiceIsRunning */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCdmaSubscriptionSource(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(95 /* getCdmaSubscriptionSource */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestIsimAuthentication(int serial, String challenge)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(challenge);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(96 /* requestIsimAuthentication */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void acknowledgeIncomingGsmSmsWithPdu(int serial, boolean success, String ackPdu)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeString(ackPdu);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(97 /* acknowledgeIncomingGsmSmsWithPdu */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendEnvelopeWithStatus(int serial, String contents)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(contents);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(98 /* sendEnvelopeWithStatus */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getVoiceRadioTechnology(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(99 /* getVoiceRadioTechnology */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getCellInfoList(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(100 /* getCellInfoList */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setCellInfoListRate(int serial, int rate)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rate);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(101 /* setCellInfoListRate */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setInitialAttachApn(int serial, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean isRoaming)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(isRoaming);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(102 /* setInitialAttachApn */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getImsRegistrationState(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(103 /* getImsRegistrationState */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendImsSms(int serial, ImsSmsMessage message)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(104 /* sendImsSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccTransmitApduBasicChannel(int serial, SimApdu message)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(105 /* iccTransmitApduBasicChannel */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccOpenLogicalChannel(int serial, String aid, int p2)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            _hidl_request.writeInt32(p2);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(106 /* iccOpenLogicalChannel */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccCloseLogicalChannel(int serial, int channelId)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(channelId);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(107 /* iccCloseLogicalChannel */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void iccTransmitApduLogicalChannel(int serial, SimApdu message)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(108 /* iccTransmitApduLogicalChannel */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvReadItem(int serial, int itemId)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(itemId);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(109 /* nvReadItem */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvWriteItem(int serial, NvWriteItem item)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            item.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(110 /* nvWriteItem */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvWriteCdmaPrl(int serial, java.util.ArrayList<Byte> prl)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(prl);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(111 /* nvWriteCdmaPrl */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nvResetConfig(int serial, int resetType)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(resetType);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(112 /* nvResetConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setUiccSubscription(int serial, SelectUiccSub uiccSub)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            uiccSub.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(113 /* setUiccSubscription */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setDataAllowed(int serial, boolean allow)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allow);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(114 /* setDataAllowed */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getHardwareConfig(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(115 /* getHardwareConfig */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestIccSimAuthentication(int serial, int authContext, String authData, String aid)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(authContext);
            _hidl_request.writeString(authData);
            _hidl_request.writeString(aid);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(116 /* requestIccSimAuthentication */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setDataProfile(int serial, java.util.ArrayList<DataProfileInfo> profiles, boolean isRoaming)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            DataProfileInfo.writeVectorToParcel(_hidl_request, profiles);
            _hidl_request.writeBool(isRoaming);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(117 /* setDataProfile */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void requestShutdown(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(118 /* requestShutdown */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getRadioCapability(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(119 /* getRadioCapability */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setRadioCapability(int serial, RadioCapability rc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            rc.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(120 /* setRadioCapability */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void startLceService(int serial, int reportInterval, boolean pullMode)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(reportInterval);
            _hidl_request.writeBool(pullMode);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(121 /* startLceService */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stopLceService(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(122 /* stopLceService */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void pullLceData(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(123 /* pullLceData */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getModemActivityInfo(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(124 /* getModemActivityInfo */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setAllowedCarriers(int serial, boolean allAllowed, CarrierRestrictions carriers)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allAllowed);
            carriers.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(125 /* setAllowedCarriers */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void getAllowedCarriers(int serial)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(126 /* getAllowedCarriers */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void sendDeviceState(int serial, int deviceStateType, boolean state)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(deviceStateType);
            _hidl_request.writeBool(state);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(127 /* sendDeviceState */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setIndicationFilter(int serial, int indicationFilter)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(indicationFilter);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(128 /* setIndicationFilter */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setSimCardPower(int serial, boolean powerUp)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(powerUp);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(129 /* setSimCardPower */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void responseAcknowledgement()
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(130 /* responseAcknowledgement */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
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

    public static abstract class Stub extends android.os.HwBinder implements IRadio {
        @Override
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override
        public final java.util.ArrayList<String> interfaceChain() {
            return new java.util.ArrayList<String>(java.util.Arrays.asList(
                    IRadio.kInterfaceName,
                    android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;

        }

        @Override
        public final java.util.ArrayList<byte[/* 32 */]> getHashChain() {
            return new java.util.ArrayList<byte[/* 32 */]>(java.util.Arrays.asList(
                    new byte[/* 32 */]{-101,90,-92,-103,-20,59,66,38,-15,95,72,-11,-19,8,-119,110,47,-64,103,111,-105,-116,-98,25,-100,29,-94,29,-86,-16,2,-90} /* 9b5aa499ec3b4226f15f48f5ed08896e2fc0676f978c9e199c1da21daaf002a6 */,
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
                case 1 /* setResponseFunctions */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    IRadioResponse radioResponse = IRadioResponse.asInterface(_hidl_request.readStrongBinder());
                    IRadioIndication radioIndication = IRadioIndication.asInterface(_hidl_request.readStrongBinder());
                    setResponseFunctions(radioResponse, radioIndication);
                    _hidl_reply.writeStatus(android.os.HwParcel.STATUS_SUCCESS);
                    _hidl_reply.send();
                    break;
                }

                case 2 /* getIccCardStatus */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getIccCardStatus(serial);
                    break;
                }

                case 3 /* supplyIccPinForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String pin = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    supplyIccPinForApp(serial, pin, aid);
                    break;
                }

                case 4 /* supplyIccPukForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String puk = _hidl_request.readString();
                    String pin = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    supplyIccPukForApp(serial, puk, pin, aid);
                    break;
                }

                case 5 /* supplyIccPin2ForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String pin2 = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    supplyIccPin2ForApp(serial, pin2, aid);
                    break;
                }

                case 6 /* supplyIccPuk2ForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String puk2 = _hidl_request.readString();
                    String pin2 = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    supplyIccPuk2ForApp(serial, puk2, pin2, aid);
                    break;
                }

                case 7 /* changeIccPinForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String oldPin = _hidl_request.readString();
                    String newPin = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    changeIccPinForApp(serial, oldPin, newPin, aid);
                    break;
                }

                case 8 /* changeIccPin2ForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String oldPin2 = _hidl_request.readString();
                    String newPin2 = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    changeIccPin2ForApp(serial, oldPin2, newPin2, aid);
                    break;
                }

                case 9 /* supplyNetworkDepersonalization */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String netPin = _hidl_request.readString();
                    supplyNetworkDepersonalization(serial, netPin);
                    break;
                }

                case 10 /* getCurrentCalls */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCurrentCalls(serial);
                    break;
                }

                case 11 /* dial */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    Dial dialInfo = new Dial();
                    dialInfo.readFromParcel(_hidl_request);
                    dial(serial, dialInfo);
                    break;
                }

                case 12 /* getImsiForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String aid = _hidl_request.readString();
                    getImsiForApp(serial, aid);
                    break;
                }

                case 13 /* hangup */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int gsmIndex = _hidl_request.readInt32();
                    hangup(serial, gsmIndex);
                    break;
                }

                case 14 /* hangupWaitingOrBackground */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    hangupWaitingOrBackground(serial);
                    break;
                }

                case 15 /* hangupForegroundResumeBackground */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    hangupForegroundResumeBackground(serial);
                    break;
                }

                case 16 /* switchWaitingOrHoldingAndActive */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    switchWaitingOrHoldingAndActive(serial);
                    break;
                }

                case 17 /* conference */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    conference(serial);
                    break;
                }

                case 18 /* rejectCall */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    rejectCall(serial);
                    break;
                }

                case 19 /* getLastCallFailCause */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getLastCallFailCause(serial);
                    break;
                }

                case 20 /* getSignalStrength */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getSignalStrength(serial);
                    break;
                }

                case 21 /* getVoiceRegistrationState */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getVoiceRegistrationState(serial);
                    break;
                }

                case 22 /* getDataRegistrationState */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getDataRegistrationState(serial);
                    break;
                }

                case 23 /* getOperator */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getOperator(serial);
                    break;
                }

                case 24 /* setRadioPower */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean on = _hidl_request.readBool();
                    setRadioPower(serial, on);
                    break;
                }

                case 25 /* sendDtmf */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String s = _hidl_request.readString();
                    sendDtmf(serial, s);
                    break;
                }

                case 26 /* sendSms */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    GsmSmsMessage message = new GsmSmsMessage();
                    message.readFromParcel(_hidl_request);
                    sendSms(serial, message);
                    break;
                }

                case 27 /* sendSMSExpectMore */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    GsmSmsMessage message = new GsmSmsMessage();
                    message.readFromParcel(_hidl_request);
                    sendSMSExpectMore(serial, message);
                    break;
                }

                case 28 /* setupDataCall */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int radioTechnology = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo = new DataProfileInfo();
                    dataProfileInfo.readFromParcel(_hidl_request);
                    boolean modemCognitive = _hidl_request.readBool();
                    boolean roamingAllowed = _hidl_request.readBool();
                    boolean isRoaming = _hidl_request.readBool();
                    setupDataCall(serial, radioTechnology, dataProfileInfo, modemCognitive, roamingAllowed, isRoaming);
                    break;
                }

                case 29 /* iccIOForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    IccIo iccIo = new IccIo();
                    iccIo.readFromParcel(_hidl_request);
                    iccIOForApp(serial, iccIo);
                    break;
                }

                case 30 /* sendUssd */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String ussd = _hidl_request.readString();
                    sendUssd(serial, ussd);
                    break;
                }

                case 31 /* cancelPendingUssd */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    cancelPendingUssd(serial);
                    break;
                }

                case 32 /* getClir */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getClir(serial);
                    break;
                }

                case 33 /* setClir */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int status = _hidl_request.readInt32();
                    setClir(serial, status);
                    break;
                }

                case 34 /* getCallForwardStatus */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    CallForwardInfo callInfo = new CallForwardInfo();
                    callInfo.readFromParcel(_hidl_request);
                    getCallForwardStatus(serial, callInfo);
                    break;
                }

                case 35 /* setCallForward */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    CallForwardInfo callInfo = new CallForwardInfo();
                    callInfo.readFromParcel(_hidl_request);
                    setCallForward(serial, callInfo);
                    break;
                }

                case 36 /* getCallWaiting */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int serviceClass = _hidl_request.readInt32();
                    getCallWaiting(serial, serviceClass);
                    break;
                }

                case 37 /* setCallWaiting */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean enable = _hidl_request.readBool();
                    int serviceClass = _hidl_request.readInt32();
                    setCallWaiting(serial, enable, serviceClass);
                    break;
                }

                case 38 /* acknowledgeLastIncomingGsmSms */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean success = _hidl_request.readBool();
                    int cause = _hidl_request.readInt32();
                    acknowledgeLastIncomingGsmSms(serial, success, cause);
                    break;
                }

                case 39 /* acceptCall */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    acceptCall(serial);
                    break;
                }

                case 40 /* deactivateDataCall */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int cid = _hidl_request.readInt32();
                    boolean reasonRadioShutDown = _hidl_request.readBool();
                    deactivateDataCall(serial, cid, reasonRadioShutDown);
                    break;
                }

                case 41 /* getFacilityLockForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String facility = _hidl_request.readString();
                    String password = _hidl_request.readString();
                    int serviceClass = _hidl_request.readInt32();
                    String appId = _hidl_request.readString();
                    getFacilityLockForApp(serial, facility, password, serviceClass, appId);
                    break;
                }

                case 42 /* setFacilityLockForApp */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String facility = _hidl_request.readString();
                    boolean lockState = _hidl_request.readBool();
                    String password = _hidl_request.readString();
                    int serviceClass = _hidl_request.readInt32();
                    String appId = _hidl_request.readString();
                    setFacilityLockForApp(serial, facility, lockState, password, serviceClass, appId);
                    break;
                }

                case 43 /* setBarringPassword */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String facility = _hidl_request.readString();
                    String oldPassword = _hidl_request.readString();
                    String newPassword = _hidl_request.readString();
                    setBarringPassword(serial, facility, oldPassword, newPassword);
                    break;
                }

                case 44 /* getNetworkSelectionMode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getNetworkSelectionMode(serial);
                    break;
                }

                case 45 /* setNetworkSelectionModeAutomatic */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    setNetworkSelectionModeAutomatic(serial);
                    break;
                }

                case 46 /* setNetworkSelectionModeManual */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String operatorNumeric = _hidl_request.readString();
                    setNetworkSelectionModeManual(serial, operatorNumeric);
                    break;
                }

                case 47 /* getAvailableNetworks */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getAvailableNetworks(serial);
                    break;
                }

                case 48 /* startDtmf */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String s = _hidl_request.readString();
                    startDtmf(serial, s);
                    break;
                }

                case 49 /* stopDtmf */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    stopDtmf(serial);
                    break;
                }

                case 50 /* getBasebandVersion */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getBasebandVersion(serial);
                    break;
                }

                case 51 /* separateConnection */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int gsmIndex = _hidl_request.readInt32();
                    separateConnection(serial, gsmIndex);
                    break;
                }

                case 52 /* setMute */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean enable = _hidl_request.readBool();
                    setMute(serial, enable);
                    break;
                }

                case 53 /* getMute */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getMute(serial);
                    break;
                }

                case 54 /* getClip */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getClip(serial);
                    break;
                }

                case 55 /* getDataCallList */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getDataCallList(serial);
                    break;
                }

                case 56 /* setSuppServiceNotifications */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean enable = _hidl_request.readBool();
                    setSuppServiceNotifications(serial, enable);
                    break;
                }

                case 57 /* writeSmsToSim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
                    smsWriteArgs.readFromParcel(_hidl_request);
                    writeSmsToSim(serial, smsWriteArgs);
                    break;
                }

                case 58 /* deleteSmsOnSim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int index = _hidl_request.readInt32();
                    deleteSmsOnSim(serial, index);
                    break;
                }

                case 59 /* setBandMode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int mode = _hidl_request.readInt32();
                    setBandMode(serial, mode);
                    break;
                }

                case 60 /* getAvailableBandModes */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getAvailableBandModes(serial);
                    break;
                }

                case 61 /* sendEnvelope */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String command = _hidl_request.readString();
                    sendEnvelope(serial, command);
                    break;
                }

                case 62 /* sendTerminalResponseToSim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String commandResponse = _hidl_request.readString();
                    sendTerminalResponseToSim(serial, commandResponse);
                    break;
                }

                case 63 /* handleStkCallSetupRequestFromSim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean accept = _hidl_request.readBool();
                    handleStkCallSetupRequestFromSim(serial, accept);
                    break;
                }

                case 64 /* explicitCallTransfer */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    explicitCallTransfer(serial);
                    break;
                }

                case 65 /* setPreferredNetworkType */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int nwType = _hidl_request.readInt32();
                    setPreferredNetworkType(serial, nwType);
                    break;
                }

                case 66 /* getPreferredNetworkType */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getPreferredNetworkType(serial);
                    break;
                }

                case 67 /* getNeighboringCids */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getNeighboringCids(serial);
                    break;
                }

                case 68 /* setLocationUpdates */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean enable = _hidl_request.readBool();
                    setLocationUpdates(serial, enable);
                    break;
                }

                case 69 /* setCdmaSubscriptionSource */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int cdmaSub = _hidl_request.readInt32();
                    setCdmaSubscriptionSource(serial, cdmaSub);
                    break;
                }

                case 70 /* setCdmaRoamingPreference */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int type = _hidl_request.readInt32();
                    setCdmaRoamingPreference(serial, type);
                    break;
                }

                case 71 /* getCdmaRoamingPreference */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCdmaRoamingPreference(serial);
                    break;
                }

                case 72 /* setTTYMode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int mode = _hidl_request.readInt32();
                    setTTYMode(serial, mode);
                    break;
                }

                case 73 /* getTTYMode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getTTYMode(serial);
                    break;
                }

                case 74 /* setPreferredVoicePrivacy */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean enable = _hidl_request.readBool();
                    setPreferredVoicePrivacy(serial, enable);
                    break;
                }

                case 75 /* getPreferredVoicePrivacy */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getPreferredVoicePrivacy(serial);
                    break;
                }

                case 76 /* sendCDMAFeatureCode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String featureCode = _hidl_request.readString();
                    sendCDMAFeatureCode(serial, featureCode);
                    break;
                }

                case 77 /* sendBurstDtmf */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String dtmf = _hidl_request.readString();
                    int on = _hidl_request.readInt32();
                    int off = _hidl_request.readInt32();
                    sendBurstDtmf(serial, dtmf, on, off);
                    break;
                }

                case 78 /* sendCdmaSms */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    CdmaSmsMessage sms = new CdmaSmsMessage();
                    sms.readFromParcel(_hidl_request);
                    sendCdmaSms(serial, sms);
                    break;
                }

                case 79 /* acknowledgeLastIncomingCdmaSms */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    CdmaSmsAck smsAck = new CdmaSmsAck();
                    smsAck.readFromParcel(_hidl_request);
                    acknowledgeLastIncomingCdmaSms(serial, smsAck);
                    break;
                }

                case 80 /* getGsmBroadcastConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getGsmBroadcastConfig(serial);
                    break;
                }

                case 81 /* setGsmBroadcastConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    java.util.ArrayList<GsmBroadcastSmsConfigInfo> configInfo = GsmBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request);
                    setGsmBroadcastConfig(serial, configInfo);
                    break;
                }

                case 82 /* setGsmBroadcastActivation */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean activate = _hidl_request.readBool();
                    setGsmBroadcastActivation(serial, activate);
                    break;
                }

                case 83 /* getCdmaBroadcastConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCdmaBroadcastConfig(serial);
                    break;
                }

                case 84 /* setCdmaBroadcastConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    java.util.ArrayList<CdmaBroadcastSmsConfigInfo> configInfo = CdmaBroadcastSmsConfigInfo.readVectorFromParcel(_hidl_request);
                    setCdmaBroadcastConfig(serial, configInfo);
                    break;
                }

                case 85 /* setCdmaBroadcastActivation */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean activate = _hidl_request.readBool();
                    setCdmaBroadcastActivation(serial, activate);
                    break;
                }

                case 86 /* getCDMASubscription */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCDMASubscription(serial);
                    break;
                }

                case 87 /* writeSmsToRuim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    CdmaSmsWriteArgs cdmaSms = new CdmaSmsWriteArgs();
                    cdmaSms.readFromParcel(_hidl_request);
                    writeSmsToRuim(serial, cdmaSms);
                    break;
                }

                case 88 /* deleteSmsOnRuim */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int index = _hidl_request.readInt32();
                    deleteSmsOnRuim(serial, index);
                    break;
                }

                case 89 /* getDeviceIdentity */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getDeviceIdentity(serial);
                    break;
                }

                case 90 /* exitEmergencyCallbackMode */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    exitEmergencyCallbackMode(serial);
                    break;
                }

                case 91 /* getSmscAddress */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getSmscAddress(serial);
                    break;
                }

                case 92 /* setSmscAddress */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String smsc = _hidl_request.readString();
                    setSmscAddress(serial, smsc);
                    break;
                }

                case 93 /* reportSmsMemoryStatus */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean available = _hidl_request.readBool();
                    reportSmsMemoryStatus(serial, available);
                    break;
                }

                case 94 /* reportStkServiceIsRunning */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    reportStkServiceIsRunning(serial);
                    break;
                }

                case 95 /* getCdmaSubscriptionSource */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCdmaSubscriptionSource(serial);
                    break;
                }

                case 96 /* requestIsimAuthentication */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String challenge = _hidl_request.readString();
                    requestIsimAuthentication(serial, challenge);
                    break;
                }

                case 97 /* acknowledgeIncomingGsmSmsWithPdu */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean success = _hidl_request.readBool();
                    String ackPdu = _hidl_request.readString();
                    acknowledgeIncomingGsmSmsWithPdu(serial, success, ackPdu);
                    break;
                }

                case 98 /* sendEnvelopeWithStatus */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String contents = _hidl_request.readString();
                    sendEnvelopeWithStatus(serial, contents);
                    break;
                }

                case 99 /* getVoiceRadioTechnology */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getVoiceRadioTechnology(serial);
                    break;
                }

                case 100 /* getCellInfoList */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getCellInfoList(serial);
                    break;
                }

                case 101 /* setCellInfoListRate */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int rate = _hidl_request.readInt32();
                    setCellInfoListRate(serial, rate);
                    break;
                }

                case 102 /* setInitialAttachApn */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    DataProfileInfo dataProfileInfo = new DataProfileInfo();
                    dataProfileInfo.readFromParcel(_hidl_request);
                    boolean modemCognitive = _hidl_request.readBool();
                    boolean isRoaming = _hidl_request.readBool();
                    setInitialAttachApn(serial, dataProfileInfo, modemCognitive, isRoaming);
                    break;
                }

                case 103 /* getImsRegistrationState */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getImsRegistrationState(serial);
                    break;
                }

                case 104 /* sendImsSms */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    ImsSmsMessage message = new ImsSmsMessage();
                    message.readFromParcel(_hidl_request);
                    sendImsSms(serial, message);
                    break;
                }

                case 105 /* iccTransmitApduBasicChannel */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    SimApdu message = new SimApdu();
                    message.readFromParcel(_hidl_request);
                    iccTransmitApduBasicChannel(serial, message);
                    break;
                }

                case 106 /* iccOpenLogicalChannel */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    String aid = _hidl_request.readString();
                    int p2 = _hidl_request.readInt32();
                    iccOpenLogicalChannel(serial, aid, p2);
                    break;
                }

                case 107 /* iccCloseLogicalChannel */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int channelId = _hidl_request.readInt32();
                    iccCloseLogicalChannel(serial, channelId);
                    break;
                }

                case 108 /* iccTransmitApduLogicalChannel */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    SimApdu message = new SimApdu();
                    message.readFromParcel(_hidl_request);
                    iccTransmitApduLogicalChannel(serial, message);
                    break;
                }

                case 109 /* nvReadItem */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int itemId = _hidl_request.readInt32();
                    nvReadItem(serial, itemId);
                    break;
                }

                case 110 /* nvWriteItem */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    NvWriteItem item = new NvWriteItem();
                    item.readFromParcel(_hidl_request);
                    nvWriteItem(serial, item);
                    break;
                }

                case 111 /* nvWriteCdmaPrl */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    java.util.ArrayList<Byte> prl = _hidl_request.readInt8Vector();
                    nvWriteCdmaPrl(serial, prl);
                    break;
                }

                case 112 /* nvResetConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int resetType = _hidl_request.readInt32();
                    nvResetConfig(serial, resetType);
                    break;
                }

                case 113 /* setUiccSubscription */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    SelectUiccSub uiccSub = new SelectUiccSub();
                    uiccSub.readFromParcel(_hidl_request);
                    setUiccSubscription(serial, uiccSub);
                    break;
                }

                case 114 /* setDataAllowed */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean allow = _hidl_request.readBool();
                    setDataAllowed(serial, allow);
                    break;
                }

                case 115 /* getHardwareConfig */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getHardwareConfig(serial);
                    break;
                }

                case 116 /* requestIccSimAuthentication */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int authContext = _hidl_request.readInt32();
                    String authData = _hidl_request.readString();
                    String aid = _hidl_request.readString();
                    requestIccSimAuthentication(serial, authContext, authData, aid);
                    break;
                }

                case 117 /* setDataProfile */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    java.util.ArrayList<DataProfileInfo> profiles = DataProfileInfo.readVectorFromParcel(_hidl_request);
                    boolean isRoaming = _hidl_request.readBool();
                    setDataProfile(serial, profiles, isRoaming);
                    break;
                }

                case 118 /* requestShutdown */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    requestShutdown(serial);
                    break;
                }

                case 119 /* getRadioCapability */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getRadioCapability(serial);
                    break;
                }

                case 120 /* setRadioCapability */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(_hidl_request);
                    setRadioCapability(serial, rc);
                    break;
                }

                case 121 /* startLceService */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int reportInterval = _hidl_request.readInt32();
                    boolean pullMode = _hidl_request.readBool();
                    startLceService(serial, reportInterval, pullMode);
                    break;
                }

                case 122 /* stopLceService */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    stopLceService(serial);
                    break;
                }

                case 123 /* pullLceData */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    pullLceData(serial);
                    break;
                }

                case 124 /* getModemActivityInfo */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getModemActivityInfo(serial);
                    break;
                }

                case 125 /* setAllowedCarriers */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean allAllowed = _hidl_request.readBool();
                    CarrierRestrictions carriers = new CarrierRestrictions();
                    carriers.readFromParcel(_hidl_request);
                    setAllowedCarriers(serial, allAllowed, carriers);
                    break;
                }

                case 126 /* getAllowedCarriers */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    getAllowedCarriers(serial);
                    break;
                }

                case 127 /* sendDeviceState */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int deviceStateType = _hidl_request.readInt32();
                    boolean state = _hidl_request.readBool();
                    sendDeviceState(serial, deviceStateType, state);
                    break;
                }

                case 128 /* setIndicationFilter */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    int indicationFilter = _hidl_request.readInt32();
                    setIndicationFilter(serial, indicationFilter);
                    break;
                }

                case 129 /* setSimCardPower */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    int serial = _hidl_request.readInt32();
                    boolean powerUp = _hidl_request.readBool();
                    setSimCardPower(serial, powerUp);
                    break;
                }

                case 130 /* responseAcknowledgement */:
                {
                    _hidl_request.enforceInterface(IRadio.kInterfaceName);

                    responseAcknowledgement();
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
