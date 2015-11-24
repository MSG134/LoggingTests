package de.fraunhofer.iosb.tc_lib;

import de.fraunhofer.iosb.tc.LocalCache;
import de.fraunhofer.iosb.tc.LocalCacheFactory;
import org.slf4j.Logger;


public class LocalCacheTcFactory implements LocalCacheFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    @Override
    public LocalCache getLocalCache(final IVCT_RTI ivct_rti, final Logger logger, final TcParam tcParam) {

        try {
            return new LocalCacheTc(logger, ivct_rti);
        }
        catch (final Exception e) {
            return null;
        }
    }
}
