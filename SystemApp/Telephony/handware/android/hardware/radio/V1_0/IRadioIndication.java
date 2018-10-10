package android.hardware.radio.V1_0;

public interface IRadioIndication extends android.hidl.base.V1_0.IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadioIndication";

    /* package private */ static IRadioIndication asInterface(android.os.IHwBinder binder) {
        if (binder == null) {
            return null;
        }

        android.os.IHwInterface iface =
                binder.queryLocalInterface(kInterfaceName);

        if ((iface != null) && (iface instanceof IRadioIndication)) {
            return (IRadioIndication)iface;
        }

        IRadioIndication proxy = new IRadioIndication.Proxy(binder);

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

    public static IRadioIndication castFrom(android.os.IHwInterface iface) {
        return (iface == null) ? null : IRadioIndication.asInterface(iface.asBinder());
    }

    @Override
    public android.os.IHwBinder asBinder();

    public static IRadioIndication getService(String serviceName) throws android.os.RemoteException {
        return IRadioIndication.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.0::IRadioIndication",serviceName));
    }

    public static IRadioIndication getService() throws android.os.RemoteException {
        return IRadioIndication.asInterface(android.os.HwBinder.getService("android.hardware.radio@1.0::IRadioIndication","default"));
    }

    void radioStateChanged(int type, int radioState)
        throws android.os.RemoteException;
    void callStateChanged(int type)
        throws android.os.RemoteException;
    void networkStateChanged(int type)
        throws android.os.RemoteException;
    void newSms(int type, java.util.ArrayList<Byte> pdu)
        throws android.os.RemoteException;
    void newSmsStatusReport(int type, java.util.ArrayList<Byte> pdu)
        throws android.os.RemoteException;
    void newSmsOnSim(int type, int recordNumber)
        throws android.os.RemoteException;
    void onUssd(int type, int modeType, String msg)
        throws android.os.RemoteException;
    void nitzTimeReceived(int type, String nitzTime, long receivedTime)
        throws android.os.RemoteException;
    void currentSignalStrength(int type, SignalStrength signalStrength)
        throws android.os.RemoteException;
    void dataCallListChanged(int type, java.util.ArrayList<SetupDataCallResult> dcList)
        throws android.os.RemoteException;
    void suppSvcNotify(int type, SuppSvcNotification suppSvc)
        throws android.os.RemoteException;
    void stkSessionEnd(int type)
        throws android.os.RemoteException;
    void stkProactiveCommand(int type, String cmd)
        throws android.os.RemoteException;
    void stkEventNotify(int type, String cmd)
        throws android.os.RemoteException;
    void stkCallSetup(int type, long timeout)
        throws android.os.RemoteException;
    void simSmsStorageFull(int type)
        throws android.os.RemoteException;
    void simRefresh(int type, SimRefreshResult refreshResult)
        throws android.os.RemoteException;
    void callRing(int type, boolean isGsm, CdmaSignalInfoRecord record)
        throws android.os.RemoteException;
    void simStatusChanged(int type)
        throws android.os.RemoteException;
    void cdmaNewSms(int type, CdmaSmsMessage msg)
        throws android.os.RemoteException;
    void newBroadcastSms(int type, java.util.ArrayList<Byte> data)
        throws android.os.RemoteException;
    void cdmaRuimSmsStorageFull(int type)
        throws android.os.RemoteException;
    void restrictedStateChanged(int type, int state)
        throws android.os.RemoteException;
    void enterEmergencyCallbackMode(int type)
        throws android.os.RemoteException;
    void cdmaCallWaiting(int type, CdmaCallWaiting callWaitingRecord)
        throws android.os.RemoteException;
    void cdmaOtaProvisionStatus(int type, int status)
        throws android.os.RemoteException;
    void cdmaInfoRec(int type, CdmaInformationRecords records)
        throws android.os.RemoteException;
    void indicateRingbackTone(int type, boolean start)
        throws android.os.RemoteException;
    void resendIncallMute(int type)
        throws android.os.RemoteException;
    void cdmaSubscriptionSourceChanged(int type, int cdmaSource)
        throws android.os.RemoteException;
    void cdmaPrlChanged(int type, int version)
        throws android.os.RemoteException;
    void exitEmergencyCallbackMode(int type)
        throws android.os.RemoteException;
    void rilConnected(int type)
        throws android.os.RemoteException;
    void voiceRadioTechChanged(int type, int rat)
        throws android.os.RemoteException;
    void cellInfoList(int type, java.util.ArrayList<CellInfo> records)
        throws android.os.RemoteException;
    void imsNetworkStateChanged(int type)
        throws android.os.RemoteException;
    void subscriptionStatusChanged(int type, boolean activate)
        throws android.os.RemoteException;
    void srvccStateNotify(int type, int state)
        throws android.os.RemoteException;
    void hardwareConfigChanged(int type, java.util.ArrayList<HardwareConfig> configs)
        throws android.os.RemoteException;
    void radioCapabilityIndication(int type, RadioCapability rc)
        throws android.os.RemoteException;
    void onSupplementaryServiceIndication(int type, StkCcUnsolSsResult ss)
        throws android.os.RemoteException;
    void stkCallControlAlphaNotify(int type, String alpha)
        throws android.os.RemoteException;
    void lceData(int type, LceDataInfo lce)
        throws android.os.RemoteException;
    void pcoData(int type, PcoDataInfo pco)
        throws android.os.RemoteException;
    void modemReset(int type, String reason)
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

    public static final class Proxy implements IRadioIndication {
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
            return "[class or subclass of " + IRadioIndication.kInterfaceName + "]@Proxy";
        }

        // Methods from ::android::hardware::radio::V1_0::IRadioIndication follow.
        @Override
        public void radioStateChanged(int type, int radioState)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(radioState);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(1 /* radioStateChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void callStateChanged(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(2 /* callStateChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void networkStateChanged(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(3 /* networkStateChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void newSms(int type, java.util.ArrayList<Byte> pdu)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(pdu);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(4 /* newSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void newSmsStatusReport(int type, java.util.ArrayList<Byte> pdu)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(pdu);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(5 /* newSmsStatusReport */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void newSmsOnSim(int type, int recordNumber)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(recordNumber);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(6 /* newSmsOnSim */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void onUssd(int type, int modeType, String msg)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(modeType);
            _hidl_request.writeString(msg);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(7 /* onUssd */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void nitzTimeReceived(int type, String nitzTime, long receivedTime)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(nitzTime);
            _hidl_request.writeInt64(receivedTime);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(8 /* nitzTimeReceived */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void currentSignalStrength(int type, SignalStrength signalStrength)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            signalStrength.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(9 /* currentSignalStrength */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void dataCallListChanged(int type, java.util.ArrayList<SetupDataCallResult> dcList)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            SetupDataCallResult.writeVectorToParcel(_hidl_request, dcList);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(10 /* dataCallListChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void suppSvcNotify(int type, SuppSvcNotification suppSvc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            suppSvc.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(11 /* suppSvcNotify */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stkSessionEnd(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(12 /* stkSessionEnd */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stkProactiveCommand(int type, String cmd)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(13 /* stkProactiveCommand */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stkEventNotify(int type, String cmd)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(14 /* stkEventNotify */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stkCallSetup(int type, long timeout)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt64(timeout);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(15 /* stkCallSetup */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void simSmsStorageFull(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(16 /* simSmsStorageFull */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void simRefresh(int type, SimRefreshResult refreshResult)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            refreshResult.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(17 /* simRefresh */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void callRing(int type, boolean isGsm, CdmaSignalInfoRecord record)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(isGsm);
            record.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(18 /* callRing */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void simStatusChanged(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(19 /* simStatusChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaNewSms(int type, CdmaSmsMessage msg)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            msg.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(20 /* cdmaNewSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void newBroadcastSms(int type, java.util.ArrayList<Byte> data)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(data);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(21 /* newBroadcastSms */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaRuimSmsStorageFull(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(22 /* cdmaRuimSmsStorageFull */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void restrictedStateChanged(int type, int state)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(23 /* restrictedStateChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void enterEmergencyCallbackMode(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(24 /* enterEmergencyCallbackMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaCallWaiting(int type, CdmaCallWaiting callWaitingRecord)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            callWaitingRecord.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(25 /* cdmaCallWaiting */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaOtaProvisionStatus(int type, int status)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(status);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(26 /* cdmaOtaProvisionStatus */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaInfoRec(int type, CdmaInformationRecords records)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            records.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(27 /* cdmaInfoRec */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void indicateRingbackTone(int type, boolean start)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(start);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(28 /* indicateRingbackTone */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void resendIncallMute(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(29 /* resendIncallMute */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaSubscriptionSourceChanged(int type, int cdmaSource)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(cdmaSource);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(30 /* cdmaSubscriptionSourceChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cdmaPrlChanged(int type, int version)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(version);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(31 /* cdmaPrlChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void exitEmergencyCallbackMode(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(32 /* exitEmergencyCallbackMode */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void rilConnected(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(33 /* rilConnected */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void voiceRadioTechChanged(int type, int rat)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(rat);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(34 /* voiceRadioTechChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void cellInfoList(int type, java.util.ArrayList<CellInfo> records)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            CellInfo.writeVectorToParcel(_hidl_request, records);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(35 /* cellInfoList */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void imsNetworkStateChanged(int type)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(36 /* imsNetworkStateChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void subscriptionStatusChanged(int type, boolean activate)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeBool(activate);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(37 /* subscriptionStatusChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void srvccStateNotify(int type, int state)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(38 /* srvccStateNotify */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void hardwareConfigChanged(int type, java.util.ArrayList<HardwareConfig> configs)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HardwareConfig.writeVectorToParcel(_hidl_request, configs);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(39 /* hardwareConfigChanged */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void radioCapabilityIndication(int type, RadioCapability rc)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            rc.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(40 /* radioCapabilityIndication */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void onSupplementaryServiceIndication(int type, StkCcUnsolSsResult ss)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            ss.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(41 /* onSupplementaryServiceIndication */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void stkCallControlAlphaNotify(int type, String alpha)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(alpha);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(42 /* stkCallControlAlphaNotify */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void lceData(int type, LceDataInfo lce)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            lce.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(43 /* lceData */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void pcoData(int type, PcoDataInfo pco)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            pco.writeToParcel(_hidl_request);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(44 /* pcoData */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void modemReset(int type, String reason)
                throws android.os.RemoteException {
            android.os.HwParcel _hidl_request = new android.os.HwParcel();
            _hidl_request.writeInterfaceToken(IRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(reason);

            android.os.HwParcel _hidl_reply = new android.os.HwParcel();
            try {
                mRemote.transact(45 /* modemReset */, _hidl_request, _hidl_reply, android.os.IHwBinder.FLAG_ONEWAY);
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

    public static abstract class Stub extends android.os.HwBinder implements IRadioIndication {
        @Override
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override
        public final java.util.ArrayList<String> interfaceChain() {
            return new java.util.ArrayList<String>(java.util.Arrays.asList(
                    IRadioIndication.kInterfaceName,
                    android.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioIndication.kInterfaceName;

        }

        @Override
        public final java.util.ArrayList<byte[/* 32 */]> getHashChain() {
            return new java.util.ArrayList<byte[/* 32 */]>(java.util.Arrays.asList(
                    new byte[/* 32 */]{92,-114,-5,-71,-60,81,-91,-105,55,-19,44,108,32,35,10,-82,71,69,-125,-100,-96,29,-128,-120,-42,-36,-55,2,14,82,-46,-59} /* 5c8efbb9c451a59737ed2c6c20230aae4745839ca01d8088d6dcc9020e52d2c5 */,
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
                case 1 /* radioStateChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int radioState = _hidl_request.readInt32();
                    radioStateChanged(type, radioState);
                    break;
                }

                case 2 /* callStateChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    callStateChanged(type);
                    break;
                }

                case 3 /* networkStateChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    networkStateChanged(type);
                    break;
                }

                case 4 /* newSms */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<Byte> pdu = _hidl_request.readInt8Vector();
                    newSms(type, pdu);
                    break;
                }

                case 5 /* newSmsStatusReport */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<Byte> pdu = _hidl_request.readInt8Vector();
                    newSmsStatusReport(type, pdu);
                    break;
                }

                case 6 /* newSmsOnSim */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int recordNumber = _hidl_request.readInt32();
                    newSmsOnSim(type, recordNumber);
                    break;
                }

                case 7 /* onUssd */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int modeType = _hidl_request.readInt32();
                    String msg = _hidl_request.readString();
                    onUssd(type, modeType, msg);
                    break;
                }

                case 8 /* nitzTimeReceived */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    String nitzTime = _hidl_request.readString();
                    long receivedTime = _hidl_request.readInt64();
                    nitzTimeReceived(type, nitzTime, receivedTime);
                    break;
                }

                case 9 /* currentSignalStrength */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    SignalStrength signalStrength = new SignalStrength();
                    signalStrength.readFromParcel(_hidl_request);
                    currentSignalStrength(type, signalStrength);
                    break;
                }

                case 10 /* dataCallListChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<SetupDataCallResult> dcList = SetupDataCallResult.readVectorFromParcel(_hidl_request);
                    dataCallListChanged(type, dcList);
                    break;
                }

                case 11 /* suppSvcNotify */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    SuppSvcNotification suppSvc = new SuppSvcNotification();
                    suppSvc.readFromParcel(_hidl_request);
                    suppSvcNotify(type, suppSvc);
                    break;
                }

                case 12 /* stkSessionEnd */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    stkSessionEnd(type);
                    break;
                }

                case 13 /* stkProactiveCommand */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    String cmd = _hidl_request.readString();
                    stkProactiveCommand(type, cmd);
                    break;
                }

                case 14 /* stkEventNotify */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    String cmd = _hidl_request.readString();
                    stkEventNotify(type, cmd);
                    break;
                }

                case 15 /* stkCallSetup */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    long timeout = _hidl_request.readInt64();
                    stkCallSetup(type, timeout);
                    break;
                }

                case 16 /* simSmsStorageFull */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    simSmsStorageFull(type);
                    break;
                }

                case 17 /* simRefresh */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    SimRefreshResult refreshResult = new SimRefreshResult();
                    refreshResult.readFromParcel(_hidl_request);
                    simRefresh(type, refreshResult);
                    break;
                }

                case 18 /* callRing */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    boolean isGsm = _hidl_request.readBool();
                    CdmaSignalInfoRecord record = new CdmaSignalInfoRecord();
                    record.readFromParcel(_hidl_request);
                    callRing(type, isGsm, record);
                    break;
                }

                case 19 /* simStatusChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    simStatusChanged(type);
                    break;
                }

                case 20 /* cdmaNewSms */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    CdmaSmsMessage msg = new CdmaSmsMessage();
                    msg.readFromParcel(_hidl_request);
                    cdmaNewSms(type, msg);
                    break;
                }

                case 21 /* newBroadcastSms */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<Byte> data = _hidl_request.readInt8Vector();
                    newBroadcastSms(type, data);
                    break;
                }

                case 22 /* cdmaRuimSmsStorageFull */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    cdmaRuimSmsStorageFull(type);
                    break;
                }

                case 23 /* restrictedStateChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int state = _hidl_request.readInt32();
                    restrictedStateChanged(type, state);
                    break;
                }

                case 24 /* enterEmergencyCallbackMode */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    enterEmergencyCallbackMode(type);
                    break;
                }

                case 25 /* cdmaCallWaiting */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    CdmaCallWaiting callWaitingRecord = new CdmaCallWaiting();
                    callWaitingRecord.readFromParcel(_hidl_request);
                    cdmaCallWaiting(type, callWaitingRecord);
                    break;
                }

                case 26 /* cdmaOtaProvisionStatus */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int status = _hidl_request.readInt32();
                    cdmaOtaProvisionStatus(type, status);
                    break;
                }

                case 27 /* cdmaInfoRec */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    CdmaInformationRecords records = new CdmaInformationRecords();
                    records.readFromParcel(_hidl_request);
                    cdmaInfoRec(type, records);
                    break;
                }

                case 28 /* indicateRingbackTone */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    boolean start = _hidl_request.readBool();
                    indicateRingbackTone(type, start);
                    break;
                }

                case 29 /* resendIncallMute */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    resendIncallMute(type);
                    break;
                }

                case 30 /* cdmaSubscriptionSourceChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int cdmaSource = _hidl_request.readInt32();
                    cdmaSubscriptionSourceChanged(type, cdmaSource);
                    break;
                }

                case 31 /* cdmaPrlChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int version = _hidl_request.readInt32();
                    cdmaPrlChanged(type, version);
                    break;
                }

                case 32 /* exitEmergencyCallbackMode */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    exitEmergencyCallbackMode(type);
                    break;
                }

                case 33 /* rilConnected */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    rilConnected(type);
                    break;
                }

                case 34 /* voiceRadioTechChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int rat = _hidl_request.readInt32();
                    voiceRadioTechChanged(type, rat);
                    break;
                }

                case 35 /* cellInfoList */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<CellInfo> records = CellInfo.readVectorFromParcel(_hidl_request);
                    cellInfoList(type, records);
                    break;
                }

                case 36 /* imsNetworkStateChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    imsNetworkStateChanged(type);
                    break;
                }

                case 37 /* subscriptionStatusChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    boolean activate = _hidl_request.readBool();
                    subscriptionStatusChanged(type, activate);
                    break;
                }

                case 38 /* srvccStateNotify */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    int state = _hidl_request.readInt32();
                    srvccStateNotify(type, state);
                    break;
                }

                case 39 /* hardwareConfigChanged */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    java.util.ArrayList<HardwareConfig> configs = HardwareConfig.readVectorFromParcel(_hidl_request);
                    hardwareConfigChanged(type, configs);
                    break;
                }

                case 40 /* radioCapabilityIndication */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    RadioCapability rc = new RadioCapability();
                    rc.readFromParcel(_hidl_request);
                    radioCapabilityIndication(type, rc);
                    break;
                }

                case 41 /* onSupplementaryServiceIndication */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    StkCcUnsolSsResult ss = new StkCcUnsolSsResult();
                    ss.readFromParcel(_hidl_request);
                    onSupplementaryServiceIndication(type, ss);
                    break;
                }

                case 42 /* stkCallControlAlphaNotify */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    String alpha = _hidl_request.readString();
                    stkCallControlAlphaNotify(type, alpha);
                    break;
                }

                case 43 /* lceData */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    LceDataInfo lce = new LceDataInfo();
                    lce.readFromParcel(_hidl_request);
                    lceData(type, lce);
                    break;
                }

                case 44 /* pcoData */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    PcoDataInfo pco = new PcoDataInfo();
                    pco.readFromParcel(_hidl_request);
                    pcoData(type, pco);
                    break;
                }

                case 45 /* modemReset */:
                {
                    _hidl_request.enforceInterface(IRadioIndication.kInterfaceName);

                    int type = _hidl_request.readInt32();
                    String reason = _hidl_request.readString();
                    modemReset(type, reason);
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
