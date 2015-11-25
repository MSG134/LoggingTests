package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.AbstractTestCase;
import de.fraunhofer.iosb.tc_lib.BaseModelTc;
import de.fraunhofer.iosb.tc_lib.IVCT_LoggingFederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00003 extends AbstractTestCase {
    // Test case parameters
    private static Logger     LOGGER       = LoggerFactory.getLogger(TC00003.class);
    private String            federateName = "B";

    // Build test case parameters to use
    final static TcParam      tcParam      = new TcParam();

    // Get logging-IVCT-RTI using tc_param federation name, host
    private static IVCT_RTIambassador   ivct_rti     = IVCT_RTI_Factory.getIVCT_RTI(LOGGER);
    final static BaseModelTc localCacheTc = new BaseModelTc(LOGGER, ivct_rti);


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        new TC00003().execute(tcParam, localCacheTc, LOGGER);
    }


    @Override
    protected void preambleAction() {
        final IVCT_LoggingFederateAmbassador ivct_FederateAmbassador = new IVCT_LoggingFederateAmbassador(TC00003.localCacheTc, LOGGER);
        // Initiate rti
        TC00003.localCacheTc.initiateRti(this.federateName, ivct_FederateAmbassador, tcParam);

    }


    @Override
    protected void performTest() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void postambleAction() {
        // Terminate rti
        TC00003.localCacheTc.terminateRti(tcParam);

    }

}
