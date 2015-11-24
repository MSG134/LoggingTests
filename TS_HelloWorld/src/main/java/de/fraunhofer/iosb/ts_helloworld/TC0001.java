package de.fraunhofer.iosb.ts_helloworld;

import de.fraunhofer.iosb.tc.AbstractTestCase;
import de.fraunhofer.iosb.tc.LocalCache;
import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC0001 extends AbstractTestCase {
    FederateHandle                             federateHandle;
    private static Logger                      logger                      = LoggerFactory.getLogger(TC0001.class);
    private static LocalCacheHelloWorldFactory localCacheHelloWorldFactory = new LocalCacheHelloWorldFactory();
    private String                             federateName                = "B";


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        logger.info("---------------------------------------------------------------------");
        logger.info("TEST PURPOSE");
        logger.info("Test if a HelloWorld federate calculates a fixed population increase");
        logger.info("correctly");
        logger.info("Observe the federate for a fixed number of cycles and compare the");
        logger.info("last received value with the previously received value plus the fixed");
        logger.info("percentage and a small tolerence for each cycle");
        logger.info("---------------------------------------------------------------------");
        logger.info(" ");
        new TC0001().execute(tcParam, localCacheHelloWorldFactory, logger);
    }


    @Override
    protected void preambleAction(final LocalCache localCache, final TcParam tcParam) throws TcInconclusive {
        final LocalCacheHelloWorld localCacheHelloWorld = (LocalCacheHelloWorld) localCache;
        final IVCT_FederateAmbassador ivct_FederateAmbassador = new IVCT_FederateAmbassador(localCacheHelloWorld, logger);
        this.federateHandle = localCacheHelloWorld.initiateRti(this.federateName, ivct_FederateAmbassador, tcParam);
        if (localCacheHelloWorld.init()) {
            throw new TcInconclusive("Cannot init()");
        }
    }


    @Override
    protected void performTest(final LocalCache localCache, final TcParam tcParam) throws TcInconclusive, TcFailed {
        final LocalCacheHelloWorld localCacheTmr = (LocalCacheHelloWorld) localCache;
        this.federateHandle = localCacheTmr.getFederateHandle();
        // Allow time to work.
        try {
            Thread.sleep(3000);
        }
        catch (final InterruptedException ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        for (int i = 0; i < 10; i++) {
            if (localCacheTmr.testCountryPopulation("A", 1.03f)) {
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
    protected void postambleAction(final LocalCache localCache, final TcParam tcParam) throws TcInconclusive {
        // Terminate rti
        localCache.terminateRti(tcParam);
    }
}
