package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.FederateTransactionId;
import de.fraunhofer.iosb.tc_lib.IVCT_FederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.LocalCache;
import de.fraunhofer.iosb.tc_lib.LocalCacheTmr;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.exceptions.FederateHandleNotKnown;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidFederateHandle;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TCTMR01A extends AbstractTestCase {
    // Test case parameters
    private static Logger                   LOGGER                   = LoggerFactory.getLogger(TCTMR01A.class);
    private static final LocalCache         localCache               = new LocalCache(LOGGER);
    private static final LocalCacheTmr      localCacheTmr            = new LocalCacheTmr(LOGGER);
    private static final FederateAmbassador theFederateAmbassador    = new IVCT_FederateAmbassador(localCache, LOGGER);
    private static int                      transactionID32          = 1;
    private final FederateTransactionId     federateTransactionId    = new FederateTransactionId();
    private final Set<ObjectInstanceHandle> sutObjectInstanceHandles = new HashSet<ObjectInstanceHandle>();
    private final Set<String>               attributeNames           = new HashSet<String>();


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        new TCTMR01A().execute(tcParam, localCache, LOGGER, theFederateAmbassador);
    }


    @Override
    protected void preambleAction(final IVCT_RTI ivct_rti, final TcParam tcParam) throws TcInconclusive {
        // Initiate rti
        ivct_rti.initiateRti(tcParam, theFederateAmbassador);

        // Prepare specific data for TMR
        final int capabilityType = 3;
        final int transferType = 2;
        final int myTransactionID = transactionID32++;
        localCacheTmr.isOfferingClear();
        final FederateHandle myFederateHandle = ivct_rti.getMyFederateHandle();

        // ADD HANDLES TO sutObjectInstanceHandles from tcParam
        // ADD ATTRIBUTE NAMES TO attributeNames from tcParam

        try {
            this.federateTransactionId.setValues(myFederateHandle, myTransactionID, ivct_rti.getFederateName(myFederateHandle));
        }
        catch (InvalidFederateHandle | FederateHandleNotKnown | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        // Build and send the TMR request
        localCacheTmr.sendTMR(this.federateTransactionId, tcParam.getSutFederate(), tcParam.getSuteFederate(), transferType, this.sutObjectInstanceHandles, this.attributeNames, capabilityType, true);
    }


    @Override
    protected void performTest(final IVCT_RTI ivct_rti, final TcParam tcParam) throws TcInconclusive, TcFailed {
        // Allow time to work.
        try {
            Thread.sleep(tcParam.getSleepTimeTmr());
        }
        catch (final InterruptedException ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        // Check the values seen
        if (localCacheTmr.checkAndDelete(this.federateTransactionId, true)) {
            throw new TcFailed("checkAndDelete");
        }

        // Check is offering
        if (localCacheTmr.checkIsOffering()) {
            throw new TcFailed("checkIsOffering");
        }

        // Check ownership of each attribute
        if (localCacheTmr.checkOwnership(this.sutObjectInstanceHandles, this.attributeNames, tcParam.getSuteFederate())) {
            throw new TcInconclusive("checkOwnership");
        }
    }


    @Override
    protected void postambleAction(final IVCT_RTI ivct_rti, final TcParam tcParam) throws TcInconclusive {
        // Terminate rti
        ivct_rti.terminateRti(tcParam);

    }

}
