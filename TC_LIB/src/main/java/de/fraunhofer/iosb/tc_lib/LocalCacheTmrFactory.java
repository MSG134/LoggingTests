package de.fraunhofer.iosb.tc_lib;

import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class LocalCacheTmrFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    public static LocalCacheTmr getLocalCacheTmr(final Logger logger) {

        try {
            return new LocalCacheTmr(logger);
        }
        catch (final Exception e) {
            return null;
        }
    }
}
