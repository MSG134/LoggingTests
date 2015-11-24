package de.fraunhofer.iosb.ts_helloworld;

import de.fraunhofer.iosb.tc.LocalCache;
import de.fraunhofer.iosb.tc.LocalCacheFactory;
import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class LocalCacheHelloWorldFactory implements LocalCacheFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    @Override
    public LocalCache getLocalCache(final IVCT_RTI ivct_rti, final Logger logger, final TcParam tcParam) {

        try {
            final LocalCacheHelloWorld localCache = new LocalCacheHelloWorld(logger, ivct_rti);
            return localCache;
        }
        catch (final Exception e) {
            return null;
        }
    }
}
