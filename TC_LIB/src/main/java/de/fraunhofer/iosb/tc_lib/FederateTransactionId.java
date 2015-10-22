package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.FederateHandle;


public class FederateTransactionId {
    FederateHandle federateHandle;
    int            tranactionId = 0;
    String         federateName;


    /**
     * @param federateHandle
     * @param tranactionId
     * @param federateName
     */
    public void setValues(final FederateHandle federateHandle, final int tranactionId, final String federateName) {
        this.federateHandle = federateHandle;
        this.tranactionId = tranactionId;
        this.federateName = federateName;
    }

}
