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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class TC0001 extends AbstractTestCase {
    FederateHandle                            federateHandle;
    private static Logger                     logger                       = LoggerFactory.getLogger(TC0001.class);
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
        stringBuilder.append("Test if a HelloWorld federate calculates a fixed population increase\n");
        stringBuilder.append("correctly\n");
        stringBuilder.append("Observe the federate for a fixed number of cycles and compare the\n");
        stringBuilder.append("last received value with the previously received value plus the fixed\n");
        stringBuilder.append("percentage and a small tolerence for each cycle\n");
        stringBuilder.append("---------------------------------------------------------------------\n");
        final String testPurpose = stringBuilder.toString();

        logger.info(testPurpose);

        new TC0001().execute(helloWorldTcParam, helloWorldBaseModel, logger);
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

        for (int i = 0; i < 10; i++) {
            if (helloWorldBaseModel.testCountryPopulation(helloWorldTcParam.getSutFederate(), helloWorldTcParam.getPopulationGrowthValue())) {
                throw new TcFailed("Population incorrectly calculated");
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
