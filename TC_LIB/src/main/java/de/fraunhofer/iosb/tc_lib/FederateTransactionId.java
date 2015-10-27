package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.FederateHandle;


public class FederateTransactionId {
    private FederateHandle federateHandle;
    private int            tranactionId = 0;
    private String         federateName;


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


    /**
     * @return federate handle
     */
    public FederateHandle getFederateHandle() {
        return this.federateHandle;
    }


    /**
     * @return transaction id
     */
    public int getTranactionId() {
        return this.tranactionId;
    }


    /**
     * @return federate name
     */
    public String getFederateName() {
        return this.federateName;
    }
}
