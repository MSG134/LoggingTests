package de.fraunhofer.iosb.tc_lib;

import org.slf4j.Logger;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class BaseModelTmrFactory implements IVCT_BaseModelFactory {

    /**
     * @param logger reference to the logger
     * @return a local cache TMR or null in case of a problem
     */
    @Override
    public IVCT_BaseModel getLocalCache(final IVCT_RTIambassador ivct_rti, final Logger logger, final TcParam tcParam) {

        try {
            return new BaseModelTmr(logger, ivct_rti);
        }
        catch (final Exception e) {
            return null;
        }
    }
}
