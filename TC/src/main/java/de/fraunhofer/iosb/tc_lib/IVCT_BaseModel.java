package de.fraunhofer.iosb.tc_lib;

import de.fraunhofer.iosb.tc_lib.TcParam;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;


/**
 * @author mul (Fraunhofer IOSB)
 */
public interface IVCT_BaseModel {
    /**
     * @param tcParam the test case parameters
     */
    public FederateHandle initiateRti(final String federateName, final FederateAmbassador federateReference, final TcParam tcParam);


    /**
     * @param federateReference
     * @param callbackModel
     * @param localSettingsDesignator
     */
    public void connect(final FederateAmbassador federateReference, final CallbackModel callbackModel, final String localSettingsDesignator);


    /**
     * @param tcParam
     */
    public void terminateRti(final TcParam tcParam);
}
