package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleSaveStatusPair;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateRestoreStatus;
import hla.rti1516e.FederationExecutionInformationSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RestoreFailureReason;
import hla.rti1516e.SaveFailureReason;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.FederateInternalError;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;


/**
 * A wrapper to add additional functionality to rti calls e.g. automatic logging
 * e.g. combine rti calls to make test cases more compact
 *
 * @author Johannes Mulder (Fraunhofer IOSB)
 */
public class IVCT_FederateAmbassador implements FederateAmbassador {
    private IVCT_NullFederateAmbassador localCache;
    private Logger                      logger;


    ////////////////////////////////////
    //Federation Management Services //
    ////////////////////////////////////
    public IVCT_FederateAmbassador(final FederateAmbassador localCache, final Logger logger) {
        this.localCache = (IVCT_NullFederateAmbassador) localCache;
        this.logger = logger;
    }


    // 4.4
    @Override
    public void connectionLost(final String faultDescription) throws FederateInternalError {
        this.logger.info("connectionLost " + faultDescription);
    }


    // 4.8
    @Override
    public void reportFederationExecutions(final FederationExecutionInformationSet theFederationExecutionInformationSet) throws FederateInternalError {
        this.logger.info("reportFederationExecutions " + theFederationExecutionInformationSet.toString());
    }


    //4.12
    @Override
    public void synchronizationPointRegistrationSucceeded(final String synchronizationPointLabel) throws FederateInternalError {
        this.logger.info("synchronizationPointRegistrationSucceeded " + synchronizationPointLabel);
    }


    //4.12
    @Override
    public void synchronizationPointRegistrationFailed(final String synchronizationPointLabel, final SynchronizationPointFailureReason reason) throws FederateInternalError {
        this.logger.info("synchronizationPointRegistrationFailed " + synchronizationPointLabel + reason.toString());
    }


    //4.13
    @Override
    public void announceSynchronizationPoint(final String synchronizationPointLabel, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("announceSynchronizationPoint " + synchronizationPointLabel + " " + Arrays.toString(userSuppliedTag));
    }


    //4.15
    @Override
    public void federationSynchronized(final String synchronizationPointLabel, final FederateHandleSet failedToSyncSet) throws FederateInternalError {
        this.logger.info("federationSynchronized " + synchronizationPointLabel + " " + failedToSyncSet.toString());
    }


    //4.17
    @Override
    public void initiateFederateSave(final String label) throws FederateInternalError {
        this.logger.info("initiateFederateSave " + label);
    }


    //4.17
    @Override
    public void initiateFederateSave(final String label, final LogicalTime time) throws FederateInternalError {
        this.logger.info("initiateFederateSave " + label + " " + time.toString());
    }


    // 4.20
    @Override
    public void federationSaved() throws FederateInternalError {
        this.logger.info("federationSaved");
    }


    // 4.20
    @Override
    public void federationNotSaved(final SaveFailureReason reason) throws FederateInternalError {
        this.logger.info("federationNotSaved " + reason.toString());
    }


    // 4.23
    @Override
    public void federationSaveStatusResponse(final FederateHandleSaveStatusPair[] response) throws FederateInternalError {
        this.logger.info("federationSaveStatusResponse " + response.toString());
    }


    // 4.25
    @Override
    public void requestFederationRestoreSucceeded(final String label) throws FederateInternalError {
        this.logger.info("requestFederationRestoreSucceeded " + label);
    }


    // 4.25
    @Override
    public void requestFederationRestoreFailed(final String label) throws FederateInternalError {
        this.logger.info("requestFederationRestoreFailed " + label);
    }


    // 4.26
    @Override
    public void federationRestoreBegun() throws FederateInternalError {
        this.logger.info("federationRestoreBegun");
    }


    // 4.27
    @Override
    public void initiateFederateRestore(final String label, final String federateName, final FederateHandle federateHandle) throws FederateInternalError {
        this.logger.info("initiateFederateRestore " + label + " " + federateName + " " + federateHandle.toString());
    }


    // 4.29
    @Override
    public void federationRestored() throws FederateInternalError {
        this.logger.info("federationRestored");
    }


    // 4.29
    @Override
    public void federationNotRestored(final RestoreFailureReason reason) throws FederateInternalError {
        this.logger.info("federationNotRestored " + reason.toString());
    }


    // 4.32
    @Override
    public void federationRestoreStatusResponse(final FederateRestoreStatus[] response) throws FederateInternalError {
        this.logger.info("federationRestoreStatusResponse " + response.toString());
    }

    /////////////////////////////////////
    //Declaration Management Services //
    /////////////////////////////////////


    // 5.10
    @Override
    public void startRegistrationForObjectClass(final ObjectClassHandle theClass) throws FederateInternalError {
        this.logger.info("startRegistrationForObjectClass " + theClass.toString());
    }


    // 5.11
    @Override
    public void stopRegistrationForObjectClass(final ObjectClassHandle theClass) throws FederateInternalError {
        this.logger.info("stopRegistrationForObjectClass " + theClass.toString());
    }


    // 5.12
    @Override
    public void turnInteractionsOn(final InteractionClassHandle theHandle) throws FederateInternalError {
        this.logger.info("turnInteractionsOn " + theHandle.toString());
    }


    // 5.13
    @Override
    public void turnInteractionsOff(final InteractionClassHandle theHandle) throws FederateInternalError {
        this.logger.info("turnInteractionsOff " + theHandle);
    }

    ////////////////////////////////
    //Object Management Services //
    ////////////////////////////////


    // 6.3
    @Override
    public void objectInstanceNameReservationSucceeded(final String objectName) throws FederateInternalError {
        this.logger.info("objectInstanceNameReservationSucceeded " + objectName);
    }


    // 6.3
    @Override
    public void objectInstanceNameReservationFailed(final String objectName) throws FederateInternalError {
        this.logger.info("objectInstanceNameReservationFailed " + objectName);
    }


    // 6.6
    @Override
    public void multipleObjectInstanceNameReservationSucceeded(final Set<String> objectNames) throws FederateInternalError {
        this.logger.info("multipleObjectInstanceNameReservationSucceeded " + objectNames.toString());
    }


    // 6.6
    @Override
    public void multipleObjectInstanceNameReservationFailed(final Set<String> objectNames) throws FederateInternalError {
        this.logger.info("multipleObjectInstanceNameReservationFailed " + objectNames.toString());
    }


    // 6.9
    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName) throws FederateInternalError {
        this.logger.info("discoverObjectInstance " + theObject.toString() + " " + theObjectClass.toString() + " " + objectName);
        this.localCache.discoverObjectInstance(theObject, theObjectClass, objectName);
    }


    // 6.9
    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName, final FederateHandle producingFederate) throws FederateInternalError {
        this.logger.info("discoverObjectInstance " + theObject.toString() + " " + theObjectClass.toString() + " " + objectName + " " + producingFederate.toString());
        this.localCache.discoverObjectInstance(theObject, theObjectClass, objectName, producingFederate);
    }


    // 6.11
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, reflectInfo);
    }


    // 6.11
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, reflectInfo);
    }


    // 6.11
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, retractionHandle, reflectInfo);
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + receiveInfo.toString());
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + receiveInfo.toString());
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + receiveInfo.toString());
    }


    // 6.15
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + removeInfo.toString());
    }


    // 6.15
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + removeInfo.toString());
    }


    // 6.15
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + removeInfo.toString());
    }


    // 6.17
    @Override
    public void attributesInScope(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributesInScope " + theObject.toString() + " " + theAttributes.toString());
    }


    // 6.18
    @Override
    public void attributesOutOfScope(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributesOutOfScope " + theObject.toString() + " " + theAttributes.toString());
    }


    // 6.20
    @Override
    public void provideAttributeValueUpdate(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("provideAttributeValueUpdate " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
    }


    // 6.21
    @Override
    public void turnUpdatesOnForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("turnUpdatesOnForObjectInstance " + theObject.toString() + " " + theAttributes.toString());
    }


    // 6.21
    @Override
    public void turnUpdatesOnForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final String updateRateDesignator) throws FederateInternalError {
        this.logger.info("turnUpdatesOnForObjectInstance " + theObject.toString() + " " + theAttributes.toString() + " " + updateRateDesignator);
    }


    // 6.22
    @Override
    public void turnUpdatesOffForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("turnUpdatesOffForObjectInstance " + theObject.toString() + " " + theAttributes.toString());
    }


    // 6.24
    @Override
    public void confirmAttributeTransportationTypeChange(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("confirmAttributeTransportationTypeChange " + theObject.toString() + " " + theAttributes.toString() + " " + theTransportation.toString());
    }


    // 6.26
    @Override
    public void reportAttributeTransportationType(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("reportAttributeTransportationType " + theObject.toString() + " " + theAttribute.toString() + " " + theTransportation.toString());
    }


    // 6.28
    @Override
    public void confirmInteractionTransportationTypeChange(final InteractionClassHandle theInteraction, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("confirmInteractionTransportationTypeChange " + theInteraction.toString() + " " + theTransportation.toString());
    }


    // 6.30
    @Override
    public void reportInteractionTransportationType(final FederateHandle theFederate, final InteractionClassHandle theInteraction, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("reportInteractionTransportationType " + theFederate.toString() + " " + theInteraction.toString() + " " + theTransportation.toString());
    }

    ///////////////////////////////////
    //Ownership Management Services //
    ///////////////////////////////////


    // 7.4
    @Override
    public void requestAttributeOwnershipAssumption(final ObjectInstanceHandle theObject, final AttributeHandleSet offeredAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("requestAttributeOwnershipAssumption " + theObject.toString() + " " + offeredAttributes + " " + Arrays.toString(userSuppliedTag));
    }


    // 7.5
    @Override
    public void requestDivestitureConfirmation(final ObjectInstanceHandle theObject, final AttributeHandleSet offeredAttributes) throws FederateInternalError {
        this.logger.info("requestDivestitureConfirmation " + theObject.toString() + " " + offeredAttributes.toString());
    }


    // 7.7
    @Override
    public void attributeOwnershipAcquisitionNotification(final ObjectInstanceHandle theObject, final AttributeHandleSet securedAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("attributeOwnershipAcquisitionNotification " + theObject.toString() + " " + securedAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
    }


    // 7.10
    @Override
    public void attributeOwnershipUnavailable(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributeOwnershipUnavailable " + theObject.toString() + " " + theAttributes.toString());
    }


    // 7.11
    @Override
    public void requestAttributeOwnershipRelease(final ObjectInstanceHandle theObject, final AttributeHandleSet candidateAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("requestAttributeOwnershipRelease " + theObject.toString() + " " + candidateAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
    }


    // 7.16
    @Override
    public void confirmAttributeOwnershipAcquisitionCancellation(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("confirmAttributeOwnershipAcquisitionCancellation " + theObject.toString() + " " + theAttributes.toString());
    }


    // 7.18
    @Override
    public void informAttributeOwnership(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute, final FederateHandle theOwner) throws FederateInternalError {
        this.logger.info("informAttributeOwnership " + theObject.toString() + " " + theAttribute.toString() + " " + theOwner.toString());
    }


    // 7.18
    @Override
    public void attributeIsNotOwned(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute) throws FederateInternalError {
        this.logger.info("attributeIsNotOwned " + theObject.toString() + " " + theAttribute.toString());
    }


    // 7.18
    @Override
    public void attributeIsOwnedByRTI(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute) throws FederateInternalError {
        this.logger.info("attributeIsOwnedByRTI " + theObject.toString() + " " + theAttribute.toString());
    }

    //////////////////////////////
    //Time Management Services //
    //////////////////////////////


    // 8.3
    @Override
    public void timeRegulationEnabled(final LogicalTime time) throws FederateInternalError {
        this.logger.info("timeRegulationEnabled " + time.toString());
    }


    // 8.6
    @Override
    public void timeConstrainedEnabled(final LogicalTime time) throws FederateInternalError {
        this.logger.info("timeConstrainedEnabled " + time.toString());
    }


    // 8.13
    @Override
    public void timeAdvanceGrant(final LogicalTime theTime) throws FederateInternalError {
        this.logger.info("timeAdvanceGrant " + theTime.toString());
    }


    // 8.22
    @Override
    public void requestRetraction(final MessageRetractionHandle theHandle) throws FederateInternalError {
        this.logger.info("requestRetraction " + theHandle.toString());
    }
}
