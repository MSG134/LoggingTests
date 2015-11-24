package de.fraunhofer.iosb.tc;

import de.fraunhofer.iosb.tc_lib.IVCT_RTI;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;


public interface LocalCacheFactory {

    /**
     * @param logger reference to the logger
     * @param ivct_rti reference to the ivct rti
     * @return a local cache or null in case of a problem
     */
    LocalCache getLocalCache(final IVCT_RTI ivct_rti, final Logger logger, final TcParam tcParam);
}
