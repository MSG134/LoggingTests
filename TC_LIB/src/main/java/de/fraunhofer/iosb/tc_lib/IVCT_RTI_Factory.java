package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import org.slf4j.Logger;


/**
 * @author Johannes Mulder (Fraunhofer IOSB)
 */
public class IVCT_RTI_Factory {

    /**
     * @param localCache test case data cache
     * @param logger reference to the logger
     * @return
     */
    public static IVCT_RTI getIVCT_RTI(final LocalCache localCache, final Logger logger) {

        // Connect to RTI
        try {
            final RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
            EncoderFactory encoderFactory;
            RTIambassador rtiAmbassador = null;

            rtiAmbassador = rtiFactory.getRtiAmbassador();
            encoderFactory = rtiFactory.getEncoderFactory();
            localCache.addRti(rtiAmbassador, encoderFactory);
            return new IVCT_RTI(rtiAmbassador, encoderFactory, logger);
        }
        catch (final Exception e) {
            logger.error("Unable to create RTI ambassador.");
            return null;
        }
    }
}
