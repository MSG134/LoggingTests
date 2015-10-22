package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.ObjectInstanceHandle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class LocalCacheTmr {
    private Logger               logger;

    /**
     *
     */
    private Map<String, Boolean> isOfferingMap = new HashMap<String, Boolean>();


    /**
     * @param LOGGER reference to the logger
     */
    public LocalCacheTmr(final Logger LOGGER) {
        this.logger = LOGGER;
    }


    /**
     *
     */
    public void isOfferingClear() {
        this.isOfferingMap.clear();
    }


    /**
     * @param federateTransactionId
     * @param sutFederate
     * @param suteFederate
     * @param transferType
     * @param sutObjectInstanceHandles
     * @param attributeNames
     * @param capabilityType
     * @param isInitiator
     */
    public void sendTMR(final FederateTransactionId federateTransactionId, final String sutFederate, final String suteFederate, final int transferType, final Set<ObjectInstanceHandle> sutObjectInstanceHandles, final Set<String> attributeNames, final int capabilityType, final Boolean isInitiator) {

    }


    /**
     * @param federateTransactionId
     * @param theRequiredValue
     * @return false (no problem) or true (got problem)
     */
    public boolean checkAndDelete(final FederateTransactionId federateTransactionId, final Boolean theRequiredValue) {
        return false;
    }


    /**
     * @return false (no problem) or true (got problem)
     */
    public boolean checkIsOffering() {
        return false;
    }


    /**
     * @return
     */
    public boolean checkOwnership(final Set<ObjectInstanceHandle> sutObjectInstanceHandles, final Set<String> attributeNames, final String suteFederate) {
        return false;
    }
}
