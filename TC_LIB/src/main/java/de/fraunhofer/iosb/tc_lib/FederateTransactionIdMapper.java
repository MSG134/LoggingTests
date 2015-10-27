package de.fraunhofer.iosb.tc_lib;

import java.util.HashMap;
import java.util.Map;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class FederateTransactionIdMapper {
    private final Map<String, Map<Integer, Boolean>> federateTransactionIdBoolMap = new HashMap<String, Map<Integer, Boolean>>();


    /**
     * @param federateTransactionId key to the data
     * @param requiredValue the new value to be added
     */
    public void add(final FederateTransactionId federateTransactionId, final boolean requiredValue) {
        Map<Integer, Boolean> TransactionIdBoolMap;
        TransactionIdBoolMap = this.federateTransactionIdBoolMap.get(federateTransactionId.getFederateName());
        if (TransactionIdBoolMap != null) {
            Boolean bool = TransactionIdBoolMap.get(new Integer(federateTransactionId.getTranactionId()));
            if (bool != null) {
                bool = Boolean.valueOf(requiredValue);
            }
            else {
                TransactionIdBoolMap.put(new Integer(federateTransactionId.getTranactionId()), new Boolean(requiredValue));
            }
        }
        else {
            TransactionIdBoolMap = new HashMap<Integer, Boolean>();
            TransactionIdBoolMap.put(new Integer(federateTransactionId.getTranactionId()), new Boolean(requiredValue));

            this.federateTransactionIdBoolMap.put(federateTransactionId.getFederateName(), TransactionIdBoolMap);
        }
    }


    /**
     * @param federateTransactionId key to the data
     * @param requiredValue the new value to be checked
     * @return whether the call had any problems
     */
    public boolean checkAndDelete(final FederateTransactionId federateTransactionId, final boolean requiredValue) {
        boolean ret = false;
        Map<Integer, Boolean> TransactionIdBoolMap;
        TransactionIdBoolMap = this.federateTransactionIdBoolMap.get(federateTransactionId.getFederateName());

        if (TransactionIdBoolMap != null) {
            final Boolean bool = TransactionIdBoolMap.get(new Integer(federateTransactionId.getTranactionId()));
            if (bool != null) {
                boolean tmpBool = false;
                if (bool.booleanValue()) {
                    tmpBool = true;
                }
                if (tmpBool != requiredValue) {
                    ret = true;
                }
                this.federateTransactionIdBoolMap.remove(TransactionIdBoolMap);
            }
        }
        else {
            ret = true;
        }

        return ret;
    }
}
