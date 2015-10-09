package de.fraunhofer.iosb.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test class.
 *
 * @author Manfred Schenk (Fraunhofer IOSB)
 */
public class Test {

    private static Logger LOGGER = LoggerFactory.getLogger(Test.class);


    /**
     * The Main method.
     *
     * @param args the commandline arguments
     * @throws InterruptedException Loop was interrupted
     */
    public static void main(final String[] args) throws InterruptedException {
        while (true) {
            LOGGER.debug("Test");

        }
    }
}
