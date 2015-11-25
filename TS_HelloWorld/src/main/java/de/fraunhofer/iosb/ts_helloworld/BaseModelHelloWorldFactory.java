package de.fraunhofer.iosb.ts_helloworld;

import de.fraunhofer.iosb.tc_lib.IVCT_BaseModel;
import de.fraunhofer.iosb.tc_lib.IVCT_BaseModelFactory;
import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class BaseModelHelloWorldFactory implements IVCT_BaseModelFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    @Override
    public IVCT_BaseModel getLocalCache(final IVCT_RTIambassador ivct_rti, final Logger logger, final TcParam tcParam) {

        try {
            final BaseModelHelloWorld localCache = new BaseModelHelloWorld(logger, ivct_rti);
            return localCache;
        }
        catch (final Exception e) {
            return null;
        }
    }
}
