package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.LocalCacheTc;
import de.fraunhofer.iosb.tc_lib.LocalCacheTcFactory;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00002 {
    // Test case parameters
    private static Logger                    logger              = LoggerFactory.getLogger(TC00002.class);
    private static final LocalCacheTcFactory localCacheTcFactory = new LocalCacheTcFactory();
    private static String                    federateName        = "B";


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        execute(tcParam);
    }


    /**
     * @param tcParam test case parameters
     */
    public static void execute(final TcParam tcParam) {
        final IVCT_RTI ivct_rti = IVCT_RTI_Factory.getIVCT_RTI(logger);
        final LocalCacheTc localCacheTc = (LocalCacheTc) localCacheTcFactory.getLocalCache(ivct_rti, logger, tcParam);
        final IVCT_FederateAmbassador ivct_FederateAmbassador = new IVCT_FederateAmbassador(localCacheTc, logger);
        // Get logging-IVCT-RTI using tc_param federation name, host

        // Test case phase
        logger.info("TEST CASE PREAMBLE");

        // Initiate rti
        localCacheTc.initiateRti(federateName, ivct_FederateAmbassador, tcParam);

        // Publish interaction / object classes

        // Subscribe interaction / object classes

        // Test case phase
        logger.info("TEST CASE BODY");

        // PERFORM TEST

        // Test case phase
        logger.info("TEST CASE POST-AMBLE");

        // Terminate rti
        ivct_rti.terminateRti(tcParam);
    }
}
