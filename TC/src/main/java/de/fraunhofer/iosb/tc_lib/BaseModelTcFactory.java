package de.fraunhofer.iosb.tc_lib;

import de.fraunhofer.iosb.tc_lib.IVCT_RTIambassador;
import de.fraunhofer.iosb.tc_lib.TcParam;
import org.slf4j.Logger;


public class BaseModelTcFactory implements IVCT_BaseModelFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    @Override
    public IVCT_BaseModel getLocalCache(final IVCT_RTIambassador ivct_rti, final Logger logger, final TcParam tcParam) {

        try {
            return new BaseModelTc(logger, ivct_rti);
        }
        catch (final Exception e) {
            return null;
        }
    }
}
