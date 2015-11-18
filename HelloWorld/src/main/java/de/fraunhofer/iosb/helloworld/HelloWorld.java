package de.fraunhofer.iosb.helloworld;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfloat32LE;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.IllegalName;
import hla.rti1516e.exceptions.RTIexception;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


class HelloWorld extends NullFederateAmbassador {
    private RTIambassador                                _rtiAmbassador;
    private final String[]                               _args;
    private InteractionClassHandle                       _messageId;
    private ParameterHandle                              _parameterIdText;
    private ParameterHandle                              _parameterIdSender;
    private ObjectInstanceHandle                         _countryId;
    private AttributeHandle                              _attributeIdName;
    private AttributeHandle                              _attributeIdPopulation;
    private String                                       myCountry;
    private float                                        myPopulation          = (float) 100.0;

    private volatile boolean                             _reservationComplete;
    private volatile boolean                             _reservationSucceeded;
    private final Object                                 _reservationSemaphore = new Object();

    private static final int                             CRC_PORT              = 8989;
    private static final String                          FEDERATION_NAME       = "HelloWorld";
    private EncoderFactory                               _encoderFactory;

    private final Map<ObjectInstanceHandle, Participant> _knownObjects         = new HashMap<ObjectInstanceHandle, Participant>();
    private final Map<String, HLAfloat32LE>              countryPopulations    = new HashMap<String, HLAfloat32LE>();

    private static class Participant {
        private final String _name;


        Participant(final String name) {
            this._name = name;
        }


        @Override
        public String toString() {
            return this._name;
        }
    }


    public static void main(final String[] args) {
        new HelloWorld(args).run();
    }


    private HelloWorld(final String[] args) {
        this._args = args;
    }


    private void run() {
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String rtiHost = "";

            if (this._args.length > 0) {
                rtiHost = this._args[0];
            }
            else {
                System.out.println("Enter the CRC address, such as");
                System.out.println("'localhost', 'localhost:8989', '192.168.1.62'");
                System.out.println("or when using Pitch Booster on the form");
                System.out.println("<CRC name>@<booster address>:<booster port>");
                System.out.println("such as 'MyCRCname@192.168.1.70:8688'");
                System.out.println();
                System.out.print("[localhost]: ");
                rtiHost = in.readLine();
                if (rtiHost.length() == 0) {
                    rtiHost = "localhost";
                }
            }

            try {
                final RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
                this._rtiAmbassador = rtiFactory.getRtiAmbassador();
                this._encoderFactory = rtiFactory.getEncoderFactory();
            }
            catch (final Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Unable to create RTI ambassador.");
                return;
            }

            final String settingsDesignator = "crcAddress=" + rtiHost;

            this._rtiAmbassador.connect(this, CallbackModel.HLA_IMMEDIATE, settingsDesignator);

            try {
                // Clean up old federation
                this._rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
            }
            catch (final FederatesCurrentlyJoined ignored) {
                // No problem just ignore
            }
            catch (final FederationExecutionDoesNotExist ignored) {
                // No problem just ignore
            }
            final File fddFile = new File("H:\\Projects\\Hla\\MSG-134\\LoggingTests\\LoggingTests\\HelloWorld\\HelloWorld.xml");
            try {
                this._rtiAmbassador.createFederationExecution(FEDERATION_NAME, new URL[] {
                        fddFile.toURL()
                }, "HLAfloat64Time");
            }
            catch (final FederationExecutionAlreadyExists ignored) {}

            System.out.print("Enter your country: ");
            this.myCountry = in.readLine();

            this._rtiAmbassador.joinFederationExecution(this.myCountry, FEDERATION_NAME, new URL[] {
                    fddFile.toURL()
            });

            // Subscribe and publish interactions
            this._messageId = this._rtiAmbassador.getInteractionClassHandle("Communication");
            this._parameterIdText = this._rtiAmbassador.getParameterHandle(this._messageId, "Message");

            System.out.println("AA");
            this._rtiAmbassador.subscribeInteractionClass(this._messageId);
            this._rtiAmbassador.publishInteractionClass(this._messageId);

            System.out.println("AA");
            // Subscribe and publish objects
            final ObjectClassHandle participantId = this._rtiAmbassador.getObjectClassHandle("Country");
            this._attributeIdName = this._rtiAmbassador.getAttributeHandle(participantId, "Name");
            this._attributeIdPopulation = this._rtiAmbassador.getAttributeHandle(participantId, "Population");

            final AttributeHandleSet attributeSet = this._rtiAmbassador.getAttributeHandleSetFactory().create();
            attributeSet.add(this._attributeIdName);
            attributeSet.add(this._attributeIdPopulation);

            this._rtiAmbassador.subscribeObjectClassAttributes(participantId, attributeSet);
            this._rtiAmbassador.publishObjectClassAttributes(participantId, attributeSet);

            // Reserve object instance name and register object instance
            do {
                try {
                    this._reservationComplete = false;
                    this._rtiAmbassador.reserveObjectInstanceName(this.myCountry);
                    synchronized (this._reservationSemaphore) {
                        // Wait for response from RTI
                        while (!this._reservationComplete) {
                            try {
                                this._reservationSemaphore.wait();
                            }
                            catch (final InterruptedException ignored) {}
                        }
                    }
                    if (!this._reservationSucceeded) {
                        System.out.println("Name already taken, try again.");
                    }
                }
                catch (final IllegalName e) {
                    System.out.println("Illegal name. Try again.");
                }
                catch (final RTIexception e) {
                    System.out.println("RTI exception when reserving name: " + e.getMessage());
                    return;
                }
            } while (!this._reservationSucceeded);

            this._countryId = this._rtiAmbassador.registerObjectInstance(participantId, this.myCountry);

            final HLAunicodeString nameEncoder = this._encoderFactory.createHLAunicodeString(this.myCountry);

            for (int i = 0; i < 10; i++) {
                final AttributeHandleValueMap attributes = this._rtiAmbassador.getAttributeHandleValueMapFactory().create(2);
                final HLAfloat32LE messageEncoder = this._encoderFactory.createHLAfloat32LE();
                messageEncoder.setValue(this.myPopulation);
                this.myPopulation *= (float) 1.03;
                attributes.put(this._attributeIdPopulation, messageEncoder.toByteArray());
                attributes.put(this._attributeIdName, nameEncoder.toByteArray());

                this._rtiAmbassador.updateAttributeValues(this._countryId, attributes, null);
                Thread.sleep(1000);
                this.printCountryPopulations();
            }

            this._rtiAmbassador.resignFederationExecution(ResignAction.DELETE_OBJECTS_THEN_DIVEST);
            try {
                this._rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
            }
            catch (final FederatesCurrentlyJoined ignored) {}
            this._rtiAmbassador.disconnect();
            this._rtiAmbassador = null;
        }
        catch (final Exception e) {
            e.printStackTrace();
            try {
                System.out.println("Press <ENTER> to shutdown");
                final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                in.readLine();
            }
            catch (final IOException ioe) {}
        }
    }


    private void printCountryPopulations() {
        System.out.println("Country " + this.myCountry + " has a population of " + this.myPopulation);

        for (final Map.Entry<String, HLAfloat32LE> entry: this.countryPopulations.entrySet()) {
            System.out.println("Country " + entry.getKey() + " has a population of " + entry.getValue());
        }
    }


    @Override
    public void discoverObjectInstance(final ObjectInstanceHandle theObject, final ObjectClassHandle theObjectClass, final String objectName) throws FederateInternalError {
        if (!this._knownObjects.containsKey(theObject)) {
            final Participant member = new Participant(objectName);
            System.out.println("[" + objectName + " has joined]");
            System.out.print("> ");
            this._knownObjects.put(theObject, member);
        }
    }


    @Override
    public void receiveInteraction(final InteractionClassHandle interactionClass, final ParameterHandleValueMap theParameters, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
        if (interactionClass.equals(this._messageId)) {
            if (!theParameters.containsKey(this._parameterIdText)) {
                System.out.println("Bad message received: No text.");
                return;
            }
            if (!theParameters.containsKey(this._parameterIdSender)) {
                System.out.println("Bad message received: No sender.");
                return;
            }
            try {
                final HLAunicodeString messageDecoder = this._encoderFactory.createHLAunicodeString();
                final HLAunicodeString senderDecoder = this._encoderFactory.createHLAunicodeString();
                messageDecoder.decode(theParameters.get(this._parameterIdText));
                senderDecoder.decode(theParameters.get(this._parameterIdSender));
                final String message = messageDecoder.getValue();
                final String sender = senderDecoder.getValue();

                System.out.println(sender + ": " + message);
                System.out.print("> ");
            }
            catch (final DecoderException e) {
                System.out.println("Failed to decode incoming interaction");
            }
        }
    }


    @Override
    public final void objectInstanceNameReservationSucceeded(final String objectName) {
        synchronized (this._reservationSemaphore) {
            this._reservationComplete = true;
            this._reservationSucceeded = true;
            this._reservationSemaphore.notifyAll();
        }
    }


    @Override
    public final void objectInstanceNameReservationFailed(final String objectName) {
        synchronized (this._reservationSemaphore) {
            this._reservationComplete = true;
            this._reservationSucceeded = false;
            this._reservationSemaphore.notifyAll();
        }
    }


    @Override
    public void removeObjectInstance(final ObjectInstanceHandle theObject, final byte[] userSuppliedTag, final OrderType sentOrdering, final SupplementalRemoveInfo removeInfo) {
        final Participant member = this._knownObjects.remove(theObject);
        if (member != null) {
            final HLAfloat32LE f = this.countryPopulations.remove(member.toString());
            System.out.println("[" + member + " has left]");
        }
    }


    @Override
    public void reflectAttributeValues(final ObjectInstanceHandle theObject, final AttributeHandleValueMap theAttributes, final byte[] userSuppliedTag, final OrderType sentOrdering, final TransportationTypeHandle theTransport, final SupplementalReflectInfo reflectInfo) {
        if (theAttributes.containsKey(this._attributeIdName) && theAttributes.containsKey(this._attributeIdPopulation)) {
            try {
                final HLAunicodeString usernameDecoder = this._encoderFactory.createHLAunicodeString();
                usernameDecoder.decode(theAttributes.get(this._attributeIdName));
                final String memberName = usernameDecoder.getValue();
                final Participant member = new Participant(memberName);
                final HLAfloat32LE populationDecoder = this._encoderFactory.createHLAfloat32LE();
                populationDecoder.decode(theAttributes.get(this._attributeIdPopulation));
                final float population = populationDecoder.getValue();

                this.countryPopulations.put(memberName, populationDecoder);
            }
            catch (final DecoderException e) {
                System.out.println("Failed to decode incoming attribute");
            }
        }
    }


    @Override
    public final void provideAttributeValueUpdate(final ObjectInstanceHandle theObject, final AttributeHandleSet theAttributes, final byte[] userSuppliedTag) {
        if (theObject.equals(this._countryId) && theAttributes.contains(this._attributeIdName)) {
            try {
                final AttributeHandleValueMap attributeValues = this._rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
                final HLAunicodeString nameEncoder = this._encoderFactory.createHLAunicodeString(this.myCountry);
                attributeValues.put(this._attributeIdName, nameEncoder.toByteArray());
                this._rtiAmbassador.updateAttributeValues(this._countryId, attributeValues, null);
            }
            catch (final RTIexception ignored) {}
        }
    }
}
