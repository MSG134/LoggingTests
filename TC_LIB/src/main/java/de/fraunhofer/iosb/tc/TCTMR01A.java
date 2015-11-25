package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.FederateTransactionId;
import de.fraunhofer.iosb.tc_lib.IVCT_LoggingFederateAmbassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI_Factory;
import de.fraunhofer.iosb.tc_lib.AbstractTestCase;
import de.fraunhofer.iosb.tc_lib.BaseModelTmr;
import de.fraunhofer.iosb.tc_lib.TcFailed;
import de.fraunhofer.iosb.tc_lib.TcInconclusive;
import de.fraunhofer.iosb.tc_lib.TcParam;
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


/**
 * @author mul (Fraunhofer IOSB)
 */
public class TCTMR01A extends AbstractTestCase {
    // Test case parameters
    private static Logger                   LOGGER                   = LoggerFactory.getLogger(TCTMR01A.class);
    private static int                      transactionID32          = 1;
    private final FederateTransactionId     federateTransactionId    = new FederateTransactionId();
    private final Set<ObjectInstanceHandle> sutObjectInstanceHandles = new HashSet<ObjectInstanceHandle>();
    private final Set<String>               attributeNames           = new HashSet<String>();
    private String                          federateName             = "B";

    // Build test case parameters to use
    final static TcParam                    tcParam                  = new TcParam();

    // Get logging-IVCT-RTI using tc_param federation name, host
    private static IVCT_RTIambassador                 ivct_rti                 = IVCT_RTI_Factory.getIVCT_RTI(LOGGER);
    final static BaseModelTmr              localCacheTmr            = new BaseModelTmr(LOGGER, ivct_rti);


    public static void main(final String[] args) {
        // Build test case parameters to use
        final TcParam tcParam = new TcParam();
        new TCTMR01A().execute(tcParam, localCacheTmr, LOGGER);
    }


    @Override
    protected void preambleAction() throws TcInconclusive {
        final IVCT_LoggingFederateAmbassador ivct_FederateAmbassador = new IVCT_LoggingFederateAmbassador(TCTMR01A.localCacheTmr, LOGGER);
        // Initiate rti
        TCTMR01A.localCacheTmr.initiateRti(this.federateName, ivct_FederateAmbassador, tcParam);

        // Prepare specific data for TMR
        final int capabilityType = 3;
        final int transferType = 2;
        final int myTransactionID = transactionID32++;
        TCTMR01A.localCacheTmr.isOfferingClear();
        final FederateHandle myFederateHandle = TCTMR01A.localCacheTmr.getMyFederateHandle();

        // ADD HANDLES TO sutObjectInstanceHandles from tcParam
        // ADD ATTRIBUTE NAMES TO attributeNames from tcParam

        try {
            this.federateTransactionId.setValues(myFederateHandle, myTransactionID, TCTMR01A.localCacheTmr.getFederateName(myFederateHandle));
        }
        catch (InvalidFederateHandle | FederateHandleNotKnown | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        // Build and send the TMR request
        TCTMR01A.localCacheTmr.sendTMR(this.federateTransactionId, tcParam.getSutFederate(), tcParam.getSuteFederate(), transferType, this.sutObjectInstanceHandles, this.attributeNames, capabilityType, true);
    }


    @Override
    protected void performTest() throws TcInconclusive, TcFailed {
        // Allow time to work.
        try {
            Thread.sleep(tcParam.getSleepTimeTmr());
        }
        catch (final InterruptedException ex) {
            throw new TcInconclusive(ex.getMessage());
        }

        // Check the values seen
        if (TCTMR01A.localCacheTmr.checkAndDelete(this.federateTransactionId, true)) {
            throw new TcFailed("checkAndDelete");
        }

        // Check is offering
        if (TCTMR01A.localCacheTmr.checkIsOffering()) {
            throw new TcFailed("checkIsOffering");
        }

        // Check ownership of each attribute
        if (TCTMR01A.localCacheTmr.checkOwnership(this.sutObjectInstanceHandles, this.attributeNames, tcParam.getSuteFederate())) {
            throw new TcInconclusive("checkOwnership");
        }
    }


    @Override
    protected void postambleAction() throws TcInconclusive {
        // Terminate rti
        TCTMR01A.localCacheTmr.terminateRti(tcParam);

    }

}
