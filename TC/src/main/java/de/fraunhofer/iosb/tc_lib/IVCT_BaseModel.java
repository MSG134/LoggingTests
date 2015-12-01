package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;


/**
 * @author mul (Fraunhofer IOSB)
 */
public interface IVCT_BaseModel {
    /**
     * @param tcParam the test case parameters
     */
    public FederateHandle initiateRti(final String federateName, final FederateAmbassador federateReference, final IVCT_TcParam tcParam);


    /**
     * @param tcParam
     */
    public void terminateRti(final IVCT_TcParam tcParam);
}
