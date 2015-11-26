package de.fraunhofer.iosb.tc_lib_helloworld;

import de.fraunhofer.iosb.tc_lib.IVCT_NullFederateAmbassador;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.FederateInternalError;
import org.slf4j.Logger;


public class HelloWorldFederateAmbassador extends IVCT_NullFederateAmbassador {
    HelloWorldBaseModel helloWorldBaseModel;


    public HelloWorldFederateAmbassador(final HelloWorldBaseModel helloWorldBaseModel, final Logger logger) {
        super(logger);
        this.helloWorldBaseModel = helloWorldBaseModel;
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.helloWorldBaseModel.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.helloWorldBaseModel.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
    }


    // 6.13
    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        this.helloWorldBaseModel.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
    }


    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName) throws FederateInternalError {
        this.helloWorldBaseModel.discoverObjectInstance(theObject, theObjectClass, objectName);
    }


    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName, final FederateHandle producingFederate) throws FederateInternalError {
        this.helloWorldBaseModel.discoverObjectInstance(theObject, theObjectClass, objectName);
    }


    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final FederateAmbassador.SupplementalRemoveInfo removeInfo) throws FederateInternalError {
        this.helloWorldBaseModel.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, removeInfo);
    }


    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.helloWorldBaseModel.doReflectAttributeValues(theObject, theAttributes);
    }


    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.helloWorldBaseModel.doReflectAttributeValues(theObject, theAttributes);
    }


    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final LogicalTime theTime, final OrderType receivedOrdering, final MessageRetractionHandle retractionHandle, final SupplementalReflectInfo reflectInfo) throws FederateInternalError {
        this.helloWorldBaseModel.doReflectAttributeValues(theObject, theAttributes);
    }
}
