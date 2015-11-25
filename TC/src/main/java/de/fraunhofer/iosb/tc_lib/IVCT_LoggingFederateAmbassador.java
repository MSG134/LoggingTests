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
public final class IVCT_LoggingFederateAmbassador implements FederateAmbassador {
    private IVCT_NullFederateAmbassador localCache;
    private Logger                      logger;


    ////////////////////////////////////
    //Federation Management Services //
    ////////////////////////////////////

    /**
     * @param localCache
     * @param logger
     */
    public IVCT_LoggingFederateAmbassador(final FederateAmbassador localCache, final Logger logger) {
        this.localCache = (IVCT_NullFederateAmbassador) localCache;
        this.logger = logger;
    }


    // 4.4
    /**
     * {@inheritDoc}
     */
    @Override
    public void connectionLost(final String faultDescription) throws FederateInternalError {
        this.logger.info("connectionLost " + faultDescription);
        this.localCache.connectionLost(faultDescription);
    }


    // 4.8
    /**
     * {@inheritDoc}
     */
    @Override
    public void reportFederationExecutions(final FederationExecutionInformationSet theFederationExecutionInformationSet) throws FederateInternalError {
        this.logger.info("reportFederationExecutions " + theFederationExecutionInformationSet.toString());
        this.localCache.reportFederationExecutions(theFederationExecutionInformationSet);
    }


    //4.12
    /**
     * {@inheritDoc}
     */
    @Override
    public void synchronizationPointRegistrationSucceeded(final String synchronizationPointLabel) throws FederateInternalError {
        this.logger.info("synchronizationPointRegistrationSucceeded " + synchronizationPointLabel);
        this.localCache.synchronizationPointRegistrationSucceeded(synchronizationPointLabel);
    }


    //4.12
    /**
     * {@inheritDoc}
     */
    @Override
    public void synchronizationPointRegistrationFailed(final String synchronizationPointLabel, final SynchronizationPointFailureReason reason) throws FederateInternalError {
        this.logger.info("synchronizationPointRegistrationFailed " + synchronizationPointLabel + reason.toString());
        this.localCache.synchronizationPointRegistrationFailed(synchronizationPointLabel, reason);
    }


    //4.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void announceSynchronizationPoint(final String synchronizationPointLabel, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("announceSynchronizationPoint " + synchronizationPointLabel + " " + Arrays.toString(userSuppliedTag));
        this.localCache.announceSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
    }


    //4.15
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationSynchronized(final String synchronizationPointLabel, final FederateHandleSet failedToSyncSet) throws FederateInternalError {
        this.logger.info("federationSynchronized " + synchronizationPointLabel + " " + failedToSyncSet.toString());
        this.localCache.federationSynchronized(synchronizationPointLabel, failedToSyncSet);
    }


    //4.17
    /**
     * {@inheritDoc}
     */
    @Override
    public void initiateFederateSave(final String label) throws FederateInternalError {
        this.logger.info("initiateFederateSave " + label);
        this.localCache.initiateFederateSave(label);
    }


    //4.17
    /**
     * {@inheritDoc}
     */
    @Override
    public void initiateFederateSave(final String label, final LogicalTime time) throws FederateInternalError {
        this.logger.info("initiateFederateSave " + label + " " + time.toString());
        this.localCache.initiateFederateSave(label, time);
    }


    // 4.20
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationSaved() throws FederateInternalError {
        this.logger.info("federationSaved");
        this.localCache.federationSaved();
    }


    // 4.20
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationNotSaved(final SaveFailureReason reason) throws FederateInternalError {
        this.logger.info("federationNotSaved " + reason.toString());
        this.localCache.federationNotSaved(reason);
    }


    // 4.23
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationSaveStatusResponse(final FederateHandleSaveStatusPair[] response) throws FederateInternalError {
        this.logger.info("federationSaveStatusResponse " + response.toString());
        this.localCache.federationSaveStatusResponse(response);
    }


    // 4.25
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestFederationRestoreSucceeded(final String label) throws FederateInternalError {
        this.logger.info("requestFederationRestoreSucceeded " + label);
        this.localCache.requestFederationRestoreSucceeded(label);
    }


    // 4.25
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestFederationRestoreFailed(final String label) throws FederateInternalError {
        this.logger.info("requestFederationRestoreFailed " + label);
        this.localCache.requestFederationRestoreFailed(label);
    }


    // 4.26
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationRestoreBegun() throws FederateInternalError {
        this.logger.info("federationRestoreBegun");
        this.localCache.federationRestoreBegun();
    }


    // 4.27
    /**
     * {@inheritDoc}
     */
    @Override
    public void initiateFederateRestore(final String label, final String federateName, final FederateHandle federateHandle) throws FederateInternalError {
        this.logger.info("initiateFederateRestore " + label + " " + federateName + " " + federateHandle.toString());
        this.localCache.initiateFederateRestore(label, federateName, federateHandle);
    }


    // 4.29
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationRestored() throws FederateInternalError {
        this.logger.info("federationRestored");
        this.localCache.federationRestored();
    }


    // 4.29
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationNotRestored(final RestoreFailureReason reason) throws FederateInternalError {
        this.logger.info("federationNotRestored " + reason.toString());
        this.localCache.federationNotRestored(reason);
    }


    // 4.32
    /**
     * {@inheritDoc}
     */
    @Override
    public void federationRestoreStatusResponse(final FederateRestoreStatus[] response) throws FederateInternalError {
        this.logger.info("federationRestoreStatusResponse " + response.toString());
        this.localCache.federationRestoreStatusResponse(response);
    }

    /////////////////////////////////////
    //Declaration Management Services //
    /////////////////////////////////////


    // 5.10
    /**
     * {@inheritDoc}
     */
    @Override
    public void startRegistrationForObjectClass(final ObjectClassHandle theClass) throws FederateInternalError {
        this.logger.info("startRegistrationForObjectClass " + theClass.toString());
        this.localCache.startRegistrationForObjectClass(theClass);
    }


    // 5.11
    /**
     * {@inheritDoc}
     */
    @Override
    public void stopRegistrationForObjectClass(final ObjectClassHandle theClass) throws FederateInternalError {
        this.logger.info("stopRegistrationForObjectClass " + theClass.toString());
        this.localCache.stopRegistrationForObjectClass(theClass);
    }


    // 5.12
    /**
     * {@inheritDoc}
     */
    @Override
    public void turnInteractionsOn(final InteractionClassHandle theHandle) throws FederateInternalError {
        this.logger.info("turnInteractionsOn " + theHandle.toString());
        this.localCache.turnInteractionsOn(theHandle);
    }


    // 5.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void turnInteractionsOff(final InteractionClassHandle theHandle) throws FederateInternalError {
        this.logger.info("turnInteractionsOff " + theHandle);
        this.localCache.turnInteractionsOff(theHandle);
    }

    ////////////////////////////////
    //Object Management Services //
    ////////////////////////////////


    // 6.3
    /**
     * {@inheritDoc}
     */
    @Override
    public void objectInstanceNameReservationSucceeded(final String objectName) throws FederateInternalError {
        this.logger.info("objectInstanceNameReservationSucceeded " + objectName);
        this.localCache.objectInstanceNameReservationSucceeded(objectName);
    }


    // 6.3
    /**
     * {@inheritDoc}
     */
    @Override
    public void objectInstanceNameReservationFailed(final String objectName) throws FederateInternalError {
        this.logger.info("objectInstanceNameReservationFailed " + objectName);
        this.localCache.objectInstanceNameReservationFailed(objectName);
    }


    // 6.6
    /**
     * {@inheritDoc}
     */
    @Override
    public void multipleObjectInstanceNameReservationSucceeded(final Set<String> objectNames) throws FederateInternalError {
        this.logger.info("multipleObjectInstanceNameReservationSucceeded " + objectNames.toString());
        this.localCache.multipleObjectInstanceNameReservationSucceeded(objectNames);
    }


    // 6.6
    /**
     * {@inheritDoc}
     */
    @Override
    public void multipleObjectInstanceNameReservationFailed(final Set<String> objectNames) throws FederateInternalError {
        this.logger.info("multipleObjectInstanceNameReservationFailed " + objectNames.toString());
        this.localCache.multipleObjectInstanceNameReservationFailed(objectNames);
    }


    // 6.9
    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName) throws FederateInternalError {
        this.logger.info("discoverObjectInstance " + theObject.toString() + " " + theObjectClass.toString() + " " + objectName);
        this.localCache.discoverObjectInstance(theObject, theObjectClass, objectName);
    }


    // 6.9
    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName, final FederateHandle producingFederate) throws FederateInternalError {
        this.logger.info("discoverObjectInstance " + theObject.toString() + " " + theObjectClass.toString() + " " + objectName + " " + producingFederate.toString());
        this.localCache.discoverObjectInstance(theObject, theObjectClass, objectName, producingFederate);
    }


    // 6.11
    /**
     * {@inheritDoc}
     */
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, reflectInfo);
    }


    // 6.11
    /**
     * {@inheritDoc}
     */
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, reflectInfo);
    }


    // 6.11
    /**
     * {@inheritDoc}
     */
    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.logger.info("reflectAttributeValues " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + reflectInfo.toString());
        this.localCache.reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, retractionHandle, reflectInfo);
    }


    // 6.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + receiveInfo.toString());
        this.localCache.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
    }


    // 6.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + receiveInfo.toString());
        this.localCache.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, receiveInfo);
    }


    // 6.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.logger.info("receiveInteraction " + interactionClass.toString() + " " + theParameters.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTransport.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + receiveInfo.toString());
        this.localCache.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, retractionHandle, receiveInfo);
    }


    // 6.15
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + removeInfo.toString());
        this.localCache.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, removeInfo);
    }


    // 6.15
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + removeInfo.toString());
        this.localCache.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, theTime, receivedOrdering, removeInfo);
    }


    // 6.15
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.logger.info("removeObjectInstance " + theObject.toString() + " " + Arrays.toString(userSuppliedTag) + " " + sentOrdering.toString() + " " + theTime.toString() + " " + receivedOrdering.toString() + " " + retractionHandle.toString() + " " + removeInfo.toString());
        this.localCache.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, theTime, receivedOrdering, retractionHandle, removeInfo);
    }


    // 6.17
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributesInScope(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributesInScope " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.attributesInScope(theObject, theAttributes);
    }


    // 6.18
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributesOutOfScope(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributesOutOfScope " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.attributesOutOfScope(theObject, theAttributes);
    }


    // 6.20
    /**
     * {@inheritDoc}
     */
    @Override
    public void provideAttributeValueUpdate(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("provideAttributeValueUpdate " + theObject.toString() + " " + theAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
        this.localCache.provideAttributeValueUpdate(theObject, theAttributes, userSuppliedTag);
    }


    // 6.21
    /**
     * {@inheritDoc}
     */
    @Override
    public void turnUpdatesOnForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("turnUpdatesOnForObjectInstance " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.turnUpdatesOnForObjectInstance(theObject, theAttributes);
    }


    // 6.21
    /**
     * {@inheritDoc}
     */
    @Override
    public void turnUpdatesOnForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final String updateRateDesignator) throws FederateInternalError {
        this.logger.info("turnUpdatesOnForObjectInstance " + theObject.toString() + " " + theAttributes.toString() + " " + updateRateDesignator);
        this.localCache.turnUpdatesOnForObjectInstance(theObject, theAttributes, updateRateDesignator);
    }


    // 6.22
    /**
     * {@inheritDoc}
     */
    @Override
    public void turnUpdatesOffForObjectInstance(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("turnUpdatesOffForObjectInstance " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.turnUpdatesOffForObjectInstance(theObject, theAttributes);
    }


    // 6.24
    /**
     * {@inheritDoc}
     */
    @Override
    public void confirmAttributeTransportationTypeChange(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("confirmAttributeTransportationTypeChange " + theObject.toString() + " " + theAttributes.toString() + " " + theTransportation.toString());
        this.localCache.confirmAttributeTransportationTypeChange(theObject, theAttributes, theTransportation);
    }


    // 6.26
    /**
     * {@inheritDoc}
     */
    @Override
    public void reportAttributeTransportationType(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("reportAttributeTransportationType " + theObject.toString() + " " + theAttribute.toString() + " " + theTransportation.toString());
        this.localCache.reportAttributeTransportationType(theObject, theAttribute, theTransportation);
    }


    // 6.28
    /**
     * {@inheritDoc}
     */
    @Override
    public void confirmInteractionTransportationTypeChange(final InteractionClassHandle theInteraction, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("confirmInteractionTransportationTypeChange " + theInteraction.toString() + " " + theTransportation.toString());
        this.localCache.confirmInteractionTransportationTypeChange(theInteraction, theTransportation);
    }


    // 6.30
    /**
     * {@inheritDoc}
     */
    @Override
    public void reportInteractionTransportationType(final FederateHandle theFederate, final InteractionClassHandle theInteraction, final TransportationTypeHandle theTransportation) throws FederateInternalError {
        this.logger.info("reportInteractionTransportationType " + theFederate.toString() + " " + theInteraction.toString() + " " + theTransportation.toString());
        this.localCache.reportInteractionTransportationType(theFederate, theInteraction, theTransportation);
    }

    ///////////////////////////////////
    //Ownership Management Services //
    ///////////////////////////////////


    // 7.4
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestAttributeOwnershipAssumption(final ObjectInstanceHandle theObject, final AttributeHandleSet offeredAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("requestAttributeOwnershipAssumption " + theObject.toString() + " " + offeredAttributes + " " + Arrays.toString(userSuppliedTag));
        this.localCache.requestAttributeOwnershipAssumption(theObject, offeredAttributes, userSuppliedTag);
    }


    // 7.5
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestDivestitureConfirmation(final ObjectInstanceHandle theObject, final AttributeHandleSet offeredAttributes) throws FederateInternalError {
        this.logger.info("requestDivestitureConfirmation " + theObject.toString() + " " + offeredAttributes.toString());
        this.localCache.requestDivestitureConfirmation(theObject, offeredAttributes);
    }


    // 7.7
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeOwnershipAcquisitionNotification(final ObjectInstanceHandle theObject, final AttributeHandleSet securedAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("attributeOwnershipAcquisitionNotification " + theObject.toString() + " " + securedAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
        this.localCache.attributeOwnershipAcquisitionNotification(theObject, securedAttributes, userSuppliedTag);
    }


    // 7.10
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeOwnershipUnavailable(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("attributeOwnershipUnavailable " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.attributeOwnershipUnavailable(theObject, theAttributes);
    }


    // 7.11
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestAttributeOwnershipRelease(final ObjectInstanceHandle theObject, final AttributeHandleSet candidateAttributes, final byte[] userSuppliedTag) throws FederateInternalError {
        this.logger.info("requestAttributeOwnershipRelease " + theObject.toString() + " " + candidateAttributes.toString() + " " + Arrays.toString(userSuppliedTag));
        this.localCache.requestAttributeOwnershipRelease(theObject, candidateAttributes, userSuppliedTag);
    }


    // 7.16
    /**
     * {@inheritDoc}
     */
    @Override
    public void confirmAttributeOwnershipAcquisitionCancellation(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes) throws FederateInternalError {
        this.logger.info("confirmAttributeOwnershipAcquisitionCancellation " + theObject.toString() + " " + theAttributes.toString());
        this.localCache.confirmAttributeOwnershipAcquisitionCancellation(theObject, theAttributes);
    }


    // 7.18
    /**
     * {@inheritDoc}
     */
    @Override
    public void informAttributeOwnership(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute, final FederateHandle theOwner) throws FederateInternalError {
        this.logger.info("informAttributeOwnership " + theObject.toString() + " " + theAttribute.toString() + " " + theOwner.toString());
        this.localCache.informAttributeOwnership(theObject, theAttribute, theOwner);
    }


    // 7.18
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeIsNotOwned(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute) throws FederateInternalError {
        this.logger.info("attributeIsNotOwned " + theObject.toString() + " " + theAttribute.toString());
        this.localCache.attributeIsNotOwned(theObject, theAttribute);
    }


    // 7.18
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeIsOwnedByRTI(final ObjectInstanceHandle theObject, final AttributeHandle theAttribute) throws FederateInternalError {
        this.logger.info("attributeIsOwnedByRTI " + theObject.toString() + " " + theAttribute.toString());
        this.localCache.attributeIsOwnedByRTI(theObject, theAttribute);
    }

    //////////////////////////////
    //Time Management Services //
    //////////////////////////////


    // 8.3
    /**
     * {@inheritDoc}
     */
    @Override
    public void timeRegulationEnabled(final LogicalTime time) throws FederateInternalError {
        this.logger.info("timeRegulationEnabled " + time.toString());
        this.localCache.timeRegulationEnabled(time);
    }


    // 8.6
    /**
     * {@inheritDoc}
     */
    @Override
    public void timeConstrainedEnabled(final LogicalTime time) throws FederateInternalError {
        this.logger.info("timeConstrainedEnabled " + time.toString());
        this.localCache.timeConstrainedEnabled(time);
    }


    // 8.13
    /**
     * {@inheritDoc}
     */
    @Override
    public void timeAdvanceGrant(final LogicalTime theTime) throws FederateInternalError {
        this.logger.info("timeAdvanceGrant " + theTime.toString());
        this.localCache.timeAdvanceGrant(theTime);
    }


    // 8.22
    /**
     * {@inheritDoc}
     */
    @Override
    public void requestRetraction(final MessageRetractionHandle theHandle) throws FederateInternalError {
        this.logger.info("requestRetraction " + theHandle.toString());
        this.localCache.requestRetraction(theHandle);
    }
}
