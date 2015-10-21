package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.LocalCache;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateAmbassador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00003 extends AbstractTestCase {
    // Test case parameters
    private static Logger                   LOGGER                = LoggerFactory.getLogger(TC00003.class);
    private static final LocalCache         localCache            = new LocalCache(LOGGER);
    private static final FederateAmbassador theFederateAmbassador = new IVCT_FederateAmbassador(localCache, LOGGER);


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        new TC00003().execute(tcParam, localCache, LOGGER, theFederateAmbassador);
    }


    @Override
    protected void preambleAction(final IVCT_RTI ivct_rti, final TcParam tcParam) {
        // Initiate rti
        ivct_rti.initiateRti(tcParam, theFederateAmbassador);

    }


    @Override
    protected void performTest(final IVCT_RTI ivct_rti, final TcParam tcParam) {
        // TODO Auto-generated method stub

    }


    @Override
    protected void postambleAction(final IVCT_RTI ivct_rti, final TcParam tcParam) {
        // Terminate rti
        ivct_rti.terminateRti(tcParam);

    }

}
