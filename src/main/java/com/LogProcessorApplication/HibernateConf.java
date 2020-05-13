//package com.example.LogProcessorApplication;
//
//import java.util.Properties;
//
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.Environment;
//import org.hibernate.service.ServiceRegistry;
//
//import com.example.LogProcessorApplication.model.PaymentRecord;
//import com.example.LogProcessorApplication.model.UserRecord;
//
//
//public class HibernateConf {
//
//
//	private static SessionFactory sessionFactory;
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration();
//                Properties settings = new Properties();
//                settings.put(Environment.DRIVER, "org.apache.derby.jdbc.ClientDriver");
//                settings.put(Environment.URL, "jdbc:derby://localhost:1527/myDB;create=true");
//                settings.put(Environment.USER, "sa");
//                settings.put(Environment.PASS, "password");
//                settings.put(Environment.DIALECT, "org.hibernate.dialect.DerbyTenSevenDialect");
//                settings.put(Environment.SHOW_SQL, "true");
//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
//                configuration.setProperties(settings);
//                configuration.addAnnotatedClass(PaymentRecord.class);
//                configuration.addAnnotatedClass(UserRecord.class);
//                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties()).build();
//                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return sessionFactory;
//    }
//}



package com.LogProcessorApplication;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.LogProcessorApplication.model.PaymentRecord;
import com.LogProcessorApplication.model.UserRecord;


public class HibernateConf {
	 private static SessionFactory sessionFactory;
 
    public static void shutdown() {
    	sessionFactory.close();
    }
    
    
    public static Session getHibernateSession() {

        		if(sessionFactory==null) {
        		 Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        		 configuration.addAnnotatedClass(PaymentRecord.class);
        		 configuration.addAnnotatedClass(UserRecord.class);
        		 
        		 sessionFactory  = configuration.buildSessionFactory();
        		}
        final Session session = sessionFactory.openSession();
        return session;
        }
}
