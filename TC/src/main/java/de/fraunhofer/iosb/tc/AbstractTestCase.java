package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;


/**
 * Abstract base class for test cases. In the concrete test cases, the three
 * methods preambleAction, performTest and postambleAction have to be
 * implemented as they will be called by the execute method of this abstract
 * class. Empty implementations of these classes are also valid.
 *
 * @author sen (Fraunhofer IOSB)
 */
public abstract class AbstractTestCase {

    protected abstract void performTest(LocalCache localCache, TcParam tcParam) throws TcInconclusive, TcFailed;


    protected abstract void preambleAction(LocalCache localCache, TcParam tcParam) throws TcInconclusive;


    protected abstract void postambleAction(LocalCache localCache, TcParam tcParam) throws TcInconclusive;


    /**
     * @param tcParam test case parameters
     * @param localCacheFactory reference to the local cache factory
     * @param logger The {@link Logger} to use
     */
    public void execute(final TcParam tcParam, final LocalCacheFactory localCacheFactory, final Logger logger) {

        // Get logging-IVCT-RTI using tc_param federation name, host
        final IVCT_RTI ivct_rti = IVCT_RTI_Factory.getIVCT_RTI(logger);
        final LocalCache localcache = localCacheFactory.getLocalCache(ivct_rti, logger, tcParam);

        // preamble block
        try {
            // Test case phase
            logger.info("TEST CASE PREAMBLE");

            // Publish interaction / object classes
            // Subscribe interaction / object classes

            this.preambleAction(localcache, tcParam);
        }
        catch (final Exception ex) {
            // TODO
        }

        //test body block
        try {
            // Test case phase
            logger.info("TEST CASE BODY");

            // PERFORM TEST
            this.performTest(localcache, tcParam);

        }
        catch (final Exception ex) {
            //TODO
        }

        // postamble block
        try {
            // Test case phase
            logger.info("TEST CASE POSTAMBLE");
            this.postambleAction(localcache, tcParam);
            logger.info("TC PASSED");
        }
        catch (final Exception ex) {
            //TODO
        }
    }
}
