package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.LocalCache;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateAmbassador;
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

    protected abstract void performTest(IVCT_RTI ivct_rti, TcParam tcParam) throws TcInconclusive, TcFailed;


    protected abstract void preambleAction(IVCT_RTI ivct_rti, TcParam tcParam) throws TcInconclusive;


    protected abstract void postambleAction(IVCT_RTI ivct_rti, TcParam tcParam) throws TcInconclusive;


    /**
     * @param tcParam test case parameters
     */
    public void execute(final TcParam tcParam, final LocalCache localcache, final Logger logger, final FederateAmbassador theFederateAmbassador) {
        // Get logging-IVCT-RTI using tc_param federation name, host
        final IVCT_RTI ivct_rti = IVCT_RTI_Factory.getIVCT_RTI(localcache, logger);

        // preamble block
        try {
            // Set test case status
            ivct_rti.tc_preamble();

            // Publish interaction / object classes
            // Subscribe interaction / object classes

            this.preambleAction(ivct_rti, tcParam);
        }
        catch (final Exception ex) {
            // TODO
        }

        //test body block
        try {
            // Set test case status
            ivct_rti.tc_test_body();

            // PERFORM TEST
            this.performTest(ivct_rti, tcParam);

        }
        catch (final Exception ex) {
            //TODO
        }

        // postamble block
        try {
            // Set test case status
            ivct_rti.tc_postamble();
            this.postambleAction(ivct_rti, tcParam);
        }
        catch (final Exception ex) {
            //TODO
        }

    }
}
