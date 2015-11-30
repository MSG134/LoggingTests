package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.encoding.HLAinteger32BE;
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.FederateHandleNotKnown;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidFederateHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class TmrBaseModel extends IVCT_NullFederateAmbassador implements IVCT_BaseModel {
    private final FederateTransactionIdMapper federateTransactionIdMapper = new FederateTransactionIdMapper();
    private boolean                           gotLastOwnerFederate        = false;
    private IVCT_RTIambassador                ivct_rti;
    private Logger                            logger;
    private String                            lastOwnerFederate;

    /**
     *
     */
    private Map<String, Boolean>              isOfferingMap               = new HashMap<String, Boolean>();


    /**
     * @param logger reference to the logger
     */
    public TmrBaseModel(final Logger logger, final IVCT_RTIambassador ivct_rti) {
        super(logger);
        this.logger = logger;
    }


    /**
     * @param ivct_rti
     */
    public void addRti(final IVCT_RTIambassador ivct_rti) {
        this.ivct_rti = ivct_rti;
    }


    /**
     * @param federateName
     * @param federateReference
     * @param tcParam
     * @return
     */
    @Override
    public FederateHandle initiateRti(final String federateName, final FederateAmbassador federateReference, final IVCT_TcParam tcParam) {
        return this.ivct_rti.initiateRti(tcParam, federateReference, federateName);
    }


    /**
     * @param federateReference
     * @param callbackModel
     * @param localSettingsDesignator
     */
    public void connect(final FederateAmbassador federateReference, final CallbackModel callbackModel, final String localSettingsDesignator) {
        try {
            this.ivct_rti.connect(federateReference, callbackModel, localSettingsDesignator);
        }
        catch (ConnectionFailed | InvalidLocalSettingsDesignator | UnsupportedCallbackModel | AlreadyConnected | CallNotAllowedFromWithinCallback | RTIinternalError ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }


    @Override
    public void terminateRti(final IVCT_TcParam tcParam) {
        this.ivct_rti.terminateRti(tcParam);
    }


    /**
     *
     */
    public void isOfferingClear() {
        this.isOfferingMap.clear();
    }


    /**
     * @return value of the federate handle
     */
    public FederateHandle getMyFederateHandle() {
        return this.ivct_rti.getMyFederateHandle();
    }


    public String getFederateName(final FederateHandle theHandle) throws InvalidFederateHandle, FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError {
        return this.ivct_rti.getFederateName(theHandle);
    }


    /**
     * @param federateTransactionId
     * @param requestFederate
     * @param responseFederate
     * @param transferType
     * @param objectInstanceHandles
     * @param attributeNames
     * @param capabilityType
     * @param isInitiator
     */
    public void sendTMR(final FederateTransactionId federateTransactionId, final String requestFederate, final String responseFederate, final int transferType, final Set<ObjectInstanceHandle> objectInstanceHandles, final Set<String> attributeNames, final int capabilityType, final boolean isInitiator) {
        //        final ParameterHandleValueMap handleValueMap = this.ivct_rti.getParameterHandleValueMapFactory().create(100);
        final HLAinteger32BE transactionID32BE = this.ivct_rti.getEncoderFactory().createHLAinteger32BE();
        final HLAinteger32BE size32 = this.ivct_rti.getEncoderFactory().createHLAinteger32BE();
        final int i;
        final int mod;
        final int modLength = 0;
        final int numberOfAttributes = 0;
        final int pos = 0;
        final int prevStlen = 0;
        int size;
        final int sizeSum = 0;
        String str;
        final byte[] buff = new byte[10000];
        //        final VariableLengthData tmpVariableLengthDataAttributes;
        //        final VariableLengthData tmpVariableLengthDataObjects;
        //        final VariableLengthData variableLengthData;

        this.logger.info("sendTMR");
        this.logger.info("RequestFederate " + requestFederate);
        this.logger.info("ResponseFederate " + responseFederate);

        // transferType
        // 0 == Other
        // 1 == Acquire
        // 2 == Divest
        // 3 == AcquireWithoutNegotiating
        switch (transferType) {
            case 1: {
                this.logger.info("TransferType Acquire");
                break;
            }
            case 2: {
                this.logger.info("TransferType Divest");
                break;
            }
            case 3: {
                this.logger.info("TransferType AcquireWithoutNegotiating");
                break;
            }
            default: {
                this.logger.info("TransferType unknown");
                break;
            }
        }
        for (final ObjectInstanceHandle objectInstanceHandle: objectInstanceHandles) {
            this.logger.info("Object " + objectInstanceHandle.toString());
        }
        for (final String attributeName: attributeNames) {
            this.logger.info("Attribute " + attributeName);
        }

        transactionID32BE.setValue(federateTransactionId.getTranactionId());
        //        mapCommonTmrAttributes(handleValueMap, variableLengthData, federateTransactionId.getFederateHandle(), transactionID32BE, requestFederate, responseFederate, transferType);

        // TransferType
        final HLAinteger32BE transferType32BE = this.ivct_rti.getEncoderFactory().createHLAinteger32BE(transferType);
        if (isInitiator) {
            //            final InteractionClassHandle iTMR_InitiateTransferModellingResponsibility = this.ivct_rti.getInteractionClassHandle("TMR.TMR_InitiateTransferModellingResponsibility");
            //            final ParameterHandle pIniTransferType = this.ivct_rti.getParameterHandle(iTMR_InitiateTransferModellingResponsibility, "TransferType");
            //            handleValueMap.put(pIniTransferType, transferType32BE.toByteArray());
        }
        else {
            //            final InteractionClassHandle iTMR_RequestTransferModellingResponsibility = this.ivct_rti.getInteractionClassHandle("TMR.TMR_RequestTransferModellingResponsibility");
            //            final ParameterHandle pReqTransferType = this.ivct_rti.getParameterHandle(iTMR_RequestTransferModellingResponsibility, "TransferType");
            //            handleValueMap.put(pReqTransferType, transferType32BE.toByteArray());
        }

        // Instances
        size32.setValue(objectInstanceHandles.size());
        //        size32.decode(buff);
        for (final ObjectInstanceHandle objectInstanceHandle: objectInstanceHandles) {
            final ObjectData objectData;

            //          objectData = localDB.getObjectData(objectInstanceHandle);
            //          if (objectData != null)
            //          {
            //            str = "UUID";
            //            size = 16;
            //            sizeSum += size;
            //            if (objectData.getUuidSet() == false)
            //            {
            //              logModule.logString(eTRACE, eINFO, ws);
            //            }
            //            localDB.printOctetString(str, objectData.uuidValue16, size);
            //            memcpy(&buff[4 + i * size], objectData->uuidValue16, size);
            //          }
        }
        size = 4 + sizeSum;
        //        mapHLAinteger32BEfromsize_t(size32, size);
        str = "Instances";
        //        printOctetString(str, buff, size);
        //        VariableLengthData tmpVariableLengthDataAllObjects(buff, size32);
        if (isInitiator) {
            //            handleValueMap[pIniInstances] = tmpVariableLengthDataAllObjects;
        }
        else {
            //            handleValueMap[pReqInstances] = tmpVariableLengthDataAllObjects;
        }

        // Attributes
        //        memset(buff, 0, sizeof(buff));
        //        numberOfAttributes = attributeNames.size();
        //        mapHLAinteger32BEfromsize_t(size32, numberOfAttributes);
        //        encodeHLAinteger32BE(buff, size32);
        //        for (i = 0, itAttributeHandleSet = theAttributes.begin(); itAttributeHandleSet != theAttributes.end(); i++, itAttributeHandleSet++)
        {
            //          size = itAttributeHandleSet.size();
            //          mapHLAinteger32BEfromsize_t(size32, size);
            //          pos = 4 + i * 4 + modLength + prevStlen;
            // Encode string length.
            mod = pos % 4;
            //          modLength += mod;
            //          if (mod)
            //          {
            //            pos += 4 - mod;
            //          }
            //          encodeHLAinteger32BE(&buff[pos], size32);
            //          prevStlen += 2 * size;
            //          pos += 4;
            // Encode string value.
            //          encodeWstring(&buff[pos], *itAttributeHandleSet);
        }
        // number of strings plus string length plus string values.
        size = 4 + numberOfAttributes * 4 + modLength + prevStlen;
        //        mapHLAinteger32BEfromsize_t(size32, size);
        //        VariableLengthData tmpVariableLengthDataAllAttributes(buff, size32);
        if (isInitiator) {
            //            handleValueMap[pIniAttributes] = tmpVariableLengthDataAllAttributes;
        }
        else {
            //            handleValueMap[pReqAttributes] = tmpVariableLengthDataAllAttributes;
        }

        if (isInitiator) {
            // Initiating
            // Length of the string
            //          mapHLAinteger32BEfromsize_t(size32, theFederateTransactionId.federateName.size());
            //          encodeHLAinteger32BE(buff, size32);
            // The string itself
            //          encodeWstring(&buff[4], theFederateTransactionId.federateName);
            //          size = 4 + 2 * (theFederateTransactionId.federateName.size());
            // Callsign for the role possessor ????
            // TODO
            //          mapHLAinteger32BEfromsize_t(size32, size);
            //            VariableLengthData variableLengthDataTwo(buff, size32);
            //            handleValueMap[pIniInitiating] = variableLengthDataTwo;
        }

        // CapabilityType
        //          HLAinteger32BE capabilityType(theCapabilityType);
        if (isInitiator) {
            //            handleValueMap[pIniCapabilityType] = capabilityType.encode();
        }
        else {
            //            handleValueMap[pReqCapabilityType] = capabilityType.encode();
        }

        // InstanceAttributeValues
        // HLAbyte instanceAttributeValues
        // TODO
        // if (isInitiating)
        // {
        //   handleValueMap[pIniInstanceAttributeValues] = instanceAttributeValues.encode();
        // }
        // else
        // {
        //   handleValueMap[pReqInstanceAttributeValues] = instanceAttributeValues.encode();
        // }

        // sendInteraction
        final byte[] data = new byte[20];
        //        memcpy(data, variableLengthData.data(), 20);
        if (isInitiator) {
            //            this.ivct_rti.sendInteraction(iTMR_InitiateTransferModellingResponsibility, handleValueMap, variableLengthData);
        }
        else {
            //            this.ivct_rti.sendInteraction(iTMR_RequestTransferModellingResponsibility, handleValueMap, variableLengthData);
        }
    }


    /**
     * @param federateTransactionId
     * @param requiredValue
     * @return false (no problem) or true (got problem)
     */
    public boolean checkAndDelete(final FederateTransactionId federateTransactionId, final boolean requiredValue) {
        return this.federateTransactionIdMapper.checkAndDelete(federateTransactionId, requiredValue);
    }


    /**
     * @return false (no problem) or true (got problem)
     */
    public boolean checkIsOffering() {
        for (final Map.Entry<String, Boolean> entry: this.isOfferingMap.entrySet()) {
            if (entry.getValue().booleanValue() == false) {
                return true;
            }
        }

        return false;
    }


    /**
     * @param objectInstanceHandles set of object instance handles to use
     * @param attributeNames set of attribute names to use
     * @param expectedOwner name of federate expected to be owner
     * @return true if not owned or problem
     */
    public boolean checkOwnership(final Set<ObjectInstanceHandle> objectInstanceHandles, final Set<String> attributeNames, final String expectedOwner) {
        for (final ObjectInstanceHandle entry: objectInstanceHandles) {
            ObjectClassHandle objectClassHandle;

            try {
                objectClassHandle = this.ivct_rti.getKnownObjectClassHandle(entry);
            }
            catch (ObjectInstanceNotKnown | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
                return true;
            }
            for (final String str: attributeNames) {
                AttributeHandle attributeHandle;

                try {
                    attributeHandle = this.ivct_rti.getAttributeHandle(objectClassHandle, str);
                }
                catch (NameNotFound | InvalidObjectClassHandle | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                    return true;
                }
                this.gotLastOwnerFederate = false;
                try {
                    this.ivct_rti.queryAttributeOwnership(entry, attributeHandle);
                }
                catch (AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                // Allow RTI to work.
                for (int i = 0; i < 3; i++) {
                    if (this.gotLastOwnerFederate) {
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    }
                    catch (final InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                        return true;
                    }
                }
                if (this.lastOwnerFederate == null) {
                    return true;
                }
                if (this.lastOwnerFederate != expectedOwner) {
                    return true;
                }
            }
        }

        // Ownership check successful
        return false;
    }


    ObjectData getObjectData(final ObjectInstanceHandle objectInstanceHandle) {
        ObjectData ret;

        ret = null;
        //        ret = objectDataMapWrapper.getObject(theObjectInstanceHandle);

        return ret;
    }
}
