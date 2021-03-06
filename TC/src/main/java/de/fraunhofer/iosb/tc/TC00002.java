package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.TcBaseModel;
import de.fraunhofer.iosb.tc_lib.TcFederateAmbassador;
import de.fraunhofer.iosb.tc_lib.TcParamTmr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00002 {
    // Test case parameters
    private static Logger logger       = LoggerFactory.getLogger(TC00002.class);
    private static String federateName = "B";


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParamTmr tcParam = new TcParamTmr();
        execute(tcParam);
    }


    /**
     * @param tcParam test case parameters
     */
    public static void execute(final TcParamTmr tcParam) {
        final IVCT_RTIambassador ivct_rti = IVCT_RTI_Factory.getIVCT_RTI(logger);
        final TcBaseModel baseModelTc = new TcBaseModel(logger, ivct_rti);
        final TcFederateAmbassador tcFederateAmbassador = new TcFederateAmbassador(baseModelTc, logger);
        // Get logging-IVCT-RTI using tc_param federation name, host

        // Test case phase
        logger.info("TEST CASE PREAMBLE");

        // Initiate rti
        baseModelTc.initiateRti(federateName, tcFederateAmbassador, tcParam);

        // Publish interaction / object classes

        // Subscribe interaction / object classes

        // Test case phase
        logger.info("TEST CASE BODY");

        // PERFORM TEST

        // Test case phase
        logger.info("TEST CASE POSTAMBLE");

        // Terminate rti
        ivct_rti.terminateRti(tcParam);
    }
}
