package org.guce.orchestra.process.vt1.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.guce.orchestra.core.ServiceUtility;
import org.guce.orchestra.dao.ProcessFacadeLocal;

public class ClassicUtil {

    static Properties properties = null;

    public static Properties getDossierProperties(String processId) {
        ProcessFacadeLocal processF = ServiceUtility.getProcessFacade();
        org.guce.orchestra.entity.Process process = processF.find(processId);
        String params = process.getParams();
        Properties prop = new Properties();
        try {
            prop.load(new ByteArrayInputStream(params.getBytes()));
        } catch (IOException ex) {
            Logger.getLogger(ClassicUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

    public static String getLocalProperties(String key)  {
        if (properties == null) {
            properties = new Properties();
            InputStream in = ClassicUtil.class.getClassLoader().getResourceAsStream("classic.properties") ;
            try {
                properties.load(in);
            } catch (IOException ex) {
                Logger.getLogger(ClassicUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return properties.getProperty(key);
    }
}
