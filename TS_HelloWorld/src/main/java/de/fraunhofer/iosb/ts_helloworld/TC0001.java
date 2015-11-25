package de.fraunhofer.iosb.ts_helloworld;

import de.fraunhofer.iosb.tc_lib.AbstractTestCase;
import de.fraunhofer.iosb.tc_lib.IVCT_LoggingFederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC0001 extends AbstractTestCase {
    FederateHandle                    federateHandle;
    private static Logger             logger               = LoggerFactory.getLogger(TC0001.class);
    private String                    federateName         = "B";

    // Build test case parameters to use
    final static TcParam              tcParam              = new TcParam();

    // Get logging-IVCT-RTI using tc_param federation name, host
    private static IVCT_RTIambassador ivct_rti             = IVCT_RTI_Factory.getIVCT_RTI(logger);
    final static BaseModelHelloWorld  localCacheHelloWorld = new BaseModelHelloWorld(logger, ivct_rti);


    public static void main(final String[] args) {

        final String testPurpose = "\n---------------------------------------------------------------------\n" + "TEST PURPOSE\n" + "Test if a HelloWorld federate calculates a fixed population increase\n" + "correctly\n" + "Observe the federate for a fixed number of cycles and compare the\n" + "last received value with the previously received value plus the fixed\n" + "percentage and a small tolerence for each cycle\n" + "---------------------------------------------------------------------";
        logger.info(testPurpose);
        new TC0001().execute(tcParam, localCacheHelloWorld, logger);
    }


    @Override
    protected void preambleAction() throws TcInconclusive {
        final IVCT_LoggingFederateAmbassador ivct_FederateAmbassador = new IVCT_LoggingFederateAmbassador(TC0001.localCacheHelloWorld, logger);
        this.federateHandle = TC0001.localCacheHelloWorld.initiateRti(this.federateName, ivct_FederateAmbassador, tcParam);
        if (TC0001.localCacheHelloWorld.init()) {
            throw new TcInconclusive("Cannot init()");
        }
    }


    @Override
    protected void performTest() throws TcInconclusive, TcFailed {
        this.federateHandle = TC0001.localCacheHelloWorld.getFederateHandle();
        // Allow time to work.
        try {
            Thread.sleep(3000);
        }
        catch (final InterruptedException ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        for (int i = 0; i < 10; i++) {
            if (TC0001.localCacheHelloWorld.testCountryPopulation("A", 1.03f)) {
                throw new TcFailed("Population incorrectly calculated");
            }
            try {
                Thread.sleep(1000);
            }
            catch (final InterruptedException ex) {
                throw new TcInconclusive(ex.getMessage());
            }
        }
    }


    @Override
    protected void postambleAction() throws TcInconclusive {
        // Terminate rti
        TC0001.localCacheHelloWorld.terminateRti(tcParam);
    }
}
