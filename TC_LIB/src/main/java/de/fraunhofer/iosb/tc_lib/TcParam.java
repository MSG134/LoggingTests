package de.fraunhofer.iosb.tc_lib;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Store test case parameters
 *
 * @author Johannes Mulder (Fraunhofer IOSB)
 */
public class TcParam {
    // Get test case parameters
    //      use some constants for this example till we get params from a file
    private final String federation_name    = "NETN2";
    private final String rtiHost            = "localhost";
    private final String settingsDesignator = "crcAddress=" + this.rtiHost;
    private final int    fileNum            = 10;
    private File[]       fddFiles           = new File[this.fileNum];
    private URL[]        urls               = new URL[this.fileNum];


    public TcParam() {
        // Initiate data
        this.fddFiles[0] = new File("RPR-Switches_v2.0_draft19.10.xml");
        this.fddFiles[1] = new File("RPR-Base_v2.0_draft19.10.xml");
        this.fddFiles[2] = new File("RPR-Physical_v2.0_draft19.10.xml");
        this.fddFiles[3] = new File("RPR-Aggregate_v2.0_draft19.10.xml");
        this.fddFiles[4] = new File("NETN-Base_v1.0.2.xml");
        this.fddFiles[5] = new File("NETN-Physical_v1.1.2.xml");
        this.fddFiles[6] = new File("NETN-Aggregate_v1.0.4.xml");
        this.fddFiles[7] = new File("TMR_v1.1.3.xml");
        this.fddFiles[8] = new File("CBRN_v1.1.7.xml");
        this.fddFiles[9] = new File("MRM_v1.1.1.xml");
        for (int i = 0; i < this.fileNum; i++) {
            try {
                this.urls[i] = this.fddFiles[i].toURI().toURL();
            }
            catch (final MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * @return the federation name
     */
    public String getFederationName() {
        return this.federation_name;
    }


    /**
     * @return the RTI host value
     */
    public String getRtiHost() {
        return this.rtiHost;
    }


    /**
     * @return the settings designator
     */
    public String getSettingsDesignator() {
        return this.settingsDesignator;
    }


    /**
     * @return the urls
     */
    public URL[] getUrls() {
        return this.urls;
    }
}
