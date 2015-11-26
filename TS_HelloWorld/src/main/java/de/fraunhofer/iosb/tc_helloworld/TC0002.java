package de.fraunhofer.iosb.tc_helloworld;

import de.fraunhofer.iosb.tc_lib.AbstractTestCase;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib_helloworld.HelloWorldBaseModel;
import de.fraunhofer.iosb.tc_lib_helloworld.HelloWorldFederateAmbassador;
import de.fraunhofer.iosb.tc_lib_helloworld.HelloWorldTcParam;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InteractionClassNotPublished;
import hla.rti1516e.exceptions.InteractionParameterNotDefined;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class TC0002 extends AbstractTestCase {
    FederateHandle                            federateHandle;
    private static Logger                     logger                       = LoggerFactory.getLogger(TC0002.class);
    private String                            federateName                 = "B";

    // Build test case parameters to use
    final static HelloWorldTcParam            helloWorldTcParam            = new HelloWorldTcParam();

    // Get logging-IVCT-RTI using tc_param federation name, host
    private static IVCT_RTIambassador         ivct_rti                     = IVCT_RTI_Factory.getIVCT_RTI(logger);
    final static HelloWorldBaseModel          helloWorldBaseModel          = new HelloWorldBaseModel(logger, ivct_rti);

    final static HelloWorldFederateAmbassador helloWorldFederateAmbassador = new HelloWorldFederateAmbassador(helloWorldBaseModel, logger);


    /**
     * @param args
     */
    public static void main(final String[] args) {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("---------------------------------------------------------------------\n");
        stringBuilder.append("TEST PURPOSE\n");
        stringBuilder.append("Test if a HelloWorld federate answers with: \"HelloWorld <country name>\"\n");
        stringBuilder.append("upon receiving a \"HelloWorld\" interaction\n");
        stringBuilder.append("Repeat sending the \"HelloWorld\" interaction for several cycles and evaluate\n");
        stringBuilder.append("the interactions received\n");
        stringBuilder.append("---------------------------------------------------------------------\n");
        final String testPurpose = stringBuilder.toString();

        logger.info(testPurpose);

        new TC0002().execute(helloWorldTcParam, helloWorldBaseModel, logger);
    }


    @Override
    protected void preambleAction() throws TcInconclusive {
        this.federateHandle = helloWorldBaseModel.initiateRti(this.federateName, helloWorldFederateAmbassador, helloWorldTcParam);
        if (helloWorldBaseModel.init()) {
            throw new TcInconclusive("Cannot helloWorldBaseModel.init()");
        }
    }


    @Override
    protected void performTest() throws TcInconclusive, TcFailed {
        this.federateHandle = helloWorldBaseModel.getFederateHandle();
        // Allow time to work.
        try {
            Thread.sleep(helloWorldTcParam.getSleepTimeWait());
        }
        catch (final InterruptedException ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        final String testMessage = "Hello World from " + helloWorldTcParam.getSutFederate();
        for (int i = 0; i < 10; i++) {
            ParameterHandleValueMap parameters;
            try {
                parameters = ivct_rti.getParameterHandleValueMapFactory().create(1);
            }
            catch (FederateNotExecutionMember | NotConnected ex1) {
                throw new TcInconclusive(ex1.getMessage());
            }
            final HLAunicodeString messageEncoderString = ivct_rti.getEncoderFactory().createHLAunicodeString();
            final String message = "Hello World";
            messageEncoderString.setValue(message);
            parameters.put(helloWorldBaseModel.getParameterIdText(), messageEncoderString.toByteArray());

            try {
                ivct_rti.sendInteraction(helloWorldBaseModel.getMessageId(), parameters, null);
            }
            catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError ex1) {
                throw new TcInconclusive(ex1.getMessage());
            }
            if (helloWorldBaseModel.getMessageStatus()) {
                throw new TcInconclusive("Did not receive any \"HelloWorld\" message");
            }
            final String messageReceived = helloWorldBaseModel.getMessage();
            logger.info(messageReceived);
            if (messageReceived.equals(testMessage) == false) {
                throw new TcFailed("Incorrect message received: got \"" + messageReceived + "\" expected \"" + testMessage + "\"");
            }
            try {
                Thread.sleep(helloWorldTcParam.getSleepTimeCycle());
            }
            catch (final InterruptedException ex) {
                throw new TcInconclusive(ex.getMessage());
            }
        }
    }


    @Override
    protected void postambleAction() throws TcInconclusive {
        // Terminate rti
        helloWorldBaseModel.terminateRti(helloWorldTcParam);
    }
}
