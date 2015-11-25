package de.fraunhofer.iosb.tc_lib;

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

    protected abstract void performTest() throws TcInconclusive, TcFailed;


    protected abstract void preambleAction() throws TcInconclusive;


    protected abstract void postambleAction() throws TcInconclusive;


    /**
     * @param tcParam test case parameters
     * @param localCacheFactory reference to the local cache factory
     * @param logger The {@link Logger} to use
     */
    public void execute(final TcParam tcParam, final IVCT_BaseModel localCache, final Logger logger) {

        // preamble block
        try {
            // Test case phase
            logger.info("TEST CASE PREAMBLE");

            // Publish interaction / object classes
            // Subscribe interaction / object classes

            this.preambleAction();
        }
        catch (final Exception ex) {
            // TODO
        }

        //test body block
        try {
            // Test case phase
            logger.info("TEST CASE BODY");

            // PERFORM TEST
            this.performTest();

        }
        catch (final Exception ex) {
            //TODO
        }

        // postamble block
        try {
            // Test case phase
            logger.info("TEST CASE POSTAMBLE");
            this.postambleAction();
            logger.info("TC PASSED");
        }
        catch (final Exception ex) {
            //TODO
        }
    }
}
