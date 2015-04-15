/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dwi
 */
public class ApplicationContextProvider {
    
    private ApplicationContext applicationContext;
    private static ApplicationContextProvider provider;
    
    private ApplicationContextProvider() throws ExceptionInInitializerError{
        try {
            this.applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        } catch (Throwable ex) {
            System.err.println("Initial ApplicationContext creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public synchronized static ApplicationContextProvider getInstance() throws ExceptionInInitializerError {
        ApplicationContextProvider tempProvider = null;
        if (provider == null) {
            provider = new ApplicationContextProvider();
            tempProvider = provider;
        } else if (provider.getApplicationContext() == null) {
            provider = new ApplicationContextProvider();
            tempProvider = provider;
        } else {
            tempProvider = provider;
        }

        return tempProvider;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContextProvider getProvider() {
        return provider;
    }

    public static void setProvider(ApplicationContextProvider provider) {
        ApplicationContextProvider.provider = provider;
    }
    
}
