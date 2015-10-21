package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.LocalCache;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateAmbassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00002 {
    // Test case parameters
    private static Logger                   LOGGER                = LoggerFactory.getLogger(TC00002.class);
    private static final LocalCache         localCache            = new LocalCache(LOGGER);
    private static final FederateAmbassador theFederateAmbassador = new IVCT_FederateAmbassador(localCache, LOGGER);


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        execute(tcParam);
    }


    /**
     * @param tcParam test case parameters
     */
    public static void execute(final TcParam tcParam) {
        // Get logging-IVCT-RTI using tc_param federation name, host
        final IVCT_RTI ivct_rti = IVCT_RTI_Factory.getIVCT_RTI(localCache, LOGGER);

        // Set test case status
        ivct_rti.tc_preamble();

        // Initiate rti
        ivct_rti.initiateRti(tcParam, theFederateAmbassador);

        // Publish interaction / object classes

        // Subscribe interaction / object classes

        // Set test case status
        ivct_rti.tc_test_body();

        // PERFORM TEST

        // Set test case status
        ivct_rti.tc_postamble();

        // Terminate rti
        ivct_rti.terminateRti(tcParam);
    }
}
