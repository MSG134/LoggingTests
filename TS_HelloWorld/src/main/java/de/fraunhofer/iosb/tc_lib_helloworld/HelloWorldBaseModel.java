package de.fraunhofer.iosb.tc_lib_helloworld;

import de.fraunhofer.iosb.tc_lib.IVCT_BaseModel;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.IVCT_TcParam;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateAmbassador.SupplementalReceiveInfo;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfloat32LE;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.FederateHandleNotKnown;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InvalidFederateHandle;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class HelloWorldBaseModel implements IVCT_BaseModel {
    private AttributeHandle                                _attributeIdName;
    private AttributeHandle                                _attributeIdPopulation;
    private boolean                                        receivedInteraction = false;
    private EncoderFactory                                 _encoderFactory;
    private FederateHandle                                 federateHandle;
    private InteractionClassHandle                         messageId;
    private IVCT_RTIambassador                             ivct_rti;
    private Logger                                         logger;
    private ParameterHandle                                parameterIdText;
    private String                                         message;
    private final Map<ObjectInstanceHandle, CountryValues> _knownObjects       = new HashMap<ObjectInstanceHandle, CountryValues>();

    private static class CountryValues {
        private final String countryName;
        private float        prevPopulation = 0;
        private float        currPopulation = 0;


        CountryValues(final String name) {
            this.countryName = name;
        }


        @Override
        public String toString() {
            return this.countryName;
        }


        public float getPopulation() {
            return this.currPopulation;
        }


        public void setPopulation(final float population) {
            this.prevPopulation = this.currPopulation;
            this.currPopulation = population;
        }


        public boolean testPopulation(final float delta, final Logger logger) {
            final float min = this.prevPopulation * delta * (float) 0.99;
            final float mid = this.prevPopulation * delta;
            final float max = this.prevPopulation * delta * (float) 1.01;

            logger.info("---------------------------------------------------------------------");
            logger.info("testPopulation: test value received " + this.currPopulation + " in range " + mid + " +/-1%");
            logger.info("---------------------------------------------------------------------");
            if (this.currPopulation > min && this.currPopulation < max) {
                return false;
            }

            return true;
        }

    }


    /**
     * @param federateName federate name
     * @param tcParam test case parameters
     * @return federate handle
     */
    @Override
    public FederateHandle initiateRti(final String federateName, final FederateAmbassador federateReference, final IVCT_TcParam tcParam) {
        this.federateHandle = this.ivct_rti.initiateRti(tcParam, federateReference, federateName);
        return this.federateHandle;
    }


    public FederateHandle getFederateHandle() {
        return this.federateHandle;
    }


    /**
     * @return
     */
    public String getFederateName(final FederateHandle federateHandle) {
        try {
            return this.ivct_rti.getFederateName(federateHandle);
        }
        catch (InvalidFederateHandle | FederateHandleNotKnown | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * @return
     */
    public boolean haveMessage() {
        return this.receivedInteraction;
    }


    /**
     * @return
     */
    public String getMessage() {
        this.receivedInteraction = false;
        return this.message;
    }


    /**
     * @return
     */
    public ParameterHandle getParameterIdText() {
        return this.parameterIdText;
    }


    /**
     * @return
     */
    public InteractionClassHandle getMessageId() {
        return this.messageId;
    }


    /**
     * @param federateReference
     * @param callbackModel
     * @param localSettingsDesignator
     */
    @Override
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
     * @param logger reference to a logger
     * @param ivct_rti reference to the RTI ambassador
     * @param encoderFactory
     */
    public HelloWorldBaseModel(final Logger logger, final IVCT_RTIambassador ivct_rti) {
        this.logger = logger;
        this.ivct_rti = ivct_rti;
        this._encoderFactory = ivct_rti.getEncoderFactory();
    }


    /**
     * @return true means error, false means correct
     */
    public boolean init() {

        // Subscribe and publish interactions
        try {
            this.messageId = this.ivct_rti.getInteractionClassHandle("Communication");
            this.parameterIdText = this.ivct_rti.getParameterHandle(this.messageId, "Message");
        }
        catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidInteractionClassHandle ex1) {
            this.logger.error("Cannot get interaction class handle or parameter handle");
            return true;
        }

        try {
            this.ivct_rti.subscribeInteractionClass(this.messageId);
            this.ivct_rti.publishInteractionClass(this.messageId);
        }
        catch (FederateServiceInvocationsAreBeingReportedViaMOM | InteractionClassNotDefined | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError ex1) {
            // TODO Auto-generated catch block
            ex1.printStackTrace();
        }

        // Subscribe and publish objects
        ObjectClassHandle participantId;
        try {
            participantId = this.ivct_rti.getObjectClassHandle("Country");
            this._attributeIdName = this.ivct_rti.getAttributeHandle(participantId, "Name");
            this._attributeIdPopulation = this.ivct_rti.getAttributeHandle(participantId, "Population");
            this._attributeIdName = this.ivct_rti.getAttributeHandle(participantId, "Name");
        }
        catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle ex) {
            this.logger.error("Cannot get object class handle or attribute handle");
            return true;
        }

        AttributeHandleSet attributeSet;
        try {
            attributeSet = this.ivct_rti.getAttributeHandleSetFactory().create();
            attributeSet.add(this._attributeIdName);
            attributeSet.add(this._attributeIdPopulation);
        }
        catch (FederateNotExecutionMember | NotConnected ex) {
            this.logger.error("Cannot build attribute set");
            return true;
        }

        try {
            // Only need to subscribe to the object class
            this.ivct_rti.subscribeObjectClassAttributes(participantId, attributeSet);
        }
        catch (AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
            this.logger.error("Cannot publish/subscribe attributes");
            return true;
        }

        return false;
    }


    /**
     * @param countryName the name of the tested country
     * @param delta the rate at which the population should be increasing
     * @return true means error, false means correct
     */
    public boolean testCountryPopulation(final String countryName, final float delta) {
        for (final Map.Entry<ObjectInstanceHandle, CountryValues> entry: this._knownObjects.entrySet()) {
            if (entry.getValue().toString().equals(countryName)) {
                if (entry.getValue().testPopulation(delta, this.logger)) {
                    this.logger.error("testCountryPopulation test failed");
                    return true;
                }
                return false;
            }
        }

        return true;
    }


    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        if (interactionClass.equals(this.messageId)) {
            if (!theParameters.containsKey(this.parameterIdText)) {
                System.out.println("Bad message received: No text.");
                return;
            }
            try {
                final HLAunicodeString messageDecoder = this._encoderFactory.createHLAunicodeString();
                messageDecoder.decode(theParameters.get(this.parameterIdText));
                this.message = messageDecoder.getValue();
            }
            catch (final DecoderException e) {
                System.out.println("Failed to decode incoming interaction");
            }
        }

        this.receivedInteraction = true;
    }


    /**
     * {@inheritDoc}
     */
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName) throws FederateInternalError {
        this.logger.info("discoverObjectInstance");

        if (!this._knownObjects.containsKey(theObject)) {
            final CountryValues member = new CountryValues(objectName);
            this.logger.info("[" + objectName + " has joined]");
            this._knownObjects.put(theObject, member);
        }
    }


    /**
     * {@inheritDoc}
     */
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final FederateAmbassador.SupplementalRemoveInfo removeInfo) {
        final CountryValues member = this._knownObjects.remove(theObject);
        if (member != null) {
            this.logger.info("[" + member + " has left]");
        }
    }


    /**
     * @param theObject the object instance handle
     * @param theAttributes the map of attribute handle / value
     */
    public void doReflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes) {
        if (theAttributes.containsKey(this._attributeIdName) && theAttributes.containsKey(this._attributeIdPopulation)) {
            try {
                CountryValues cv;
                final HLAunicodeString usernameDecoder = this._encoderFactory.createHLAunicodeString();
                usernameDecoder.decode(theAttributes.get(this._attributeIdName));
                final String memberName = usernameDecoder.getValue();
                final HLAfloat32LE populationDecoder = this._encoderFactory.createHLAfloat32LE();
                populationDecoder.decode(theAttributes.get(this._attributeIdPopulation));
                final float population = populationDecoder.getValue();
                this.logger.info("Population: " + population);
                if (this._knownObjects.containsKey(theObject)) {
                    cv = this._knownObjects.get(theObject);
                    if (cv.toString().equals(memberName) == false) {
                        this.logger.error("Country name not equal to country attribute name " + cv.toString() + " " + memberName);
                    }
                    cv.setPopulation(population);
                }
            }
            catch (final DecoderException e) {
                this.logger.error("Failed to decode incoming attribute");
            }
        }
    }
}
