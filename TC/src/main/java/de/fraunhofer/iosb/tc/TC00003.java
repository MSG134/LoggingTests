package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.LocalCacheTc;
import de.fraunhofer.iosb.tc_lib.LocalCacheTcFactory;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TC00003 extends AbstractTestCase {
    // Test case parameters
    private static Logger                    LOGGER              = LoggerFactory.getLogger(TC00003.class);
    private static final LocalCacheTcFactory localCacheTcFactory = new LocalCacheTcFactory();
    private String                           federateName        = "B";


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        new TC00003().execute(tcParam, localCacheTcFactory, LOGGER);
    }


    @Override
    protected void preambleAction(final LocalCache localCache, final TcParam tcParam) {
        final LocalCacheTc localCacheTc = (LocalCacheTc) localCache;
        final IVCT_FederateAmbassador ivct_FederateAmbassador = new IVCT_FederateAmbassador(localCacheTc, LOGGER);
        // Initiate rti
        localCache.initiateRti(this.federateName, ivct_FederateAmbassador, tcParam);

    }


    @Override
    protected void performTest(final LocalCache localCache, final TcParam tcParam) {
        // TODO Auto-generated method stub

    }


    @Override
    protected void postambleAction(final LocalCache localCache, final TcParam tcParam) {
        // Terminate rti
        localCache.terminateRti(tcParam);

    }

}
