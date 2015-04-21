/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ysmal.ssc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManagerScanner {
public   List <String> mylist;
public ManagerScanner() {
//    public static void main(String[] args) throws InterruptedException {
//        try {
//            AbstractScannerThread th1 = new HHScanner("Java", 7200000L);
//            th1.start();
//            
//            do {
//            System.out.println("Manager thread wakes up");
//            Thread.sleep(172800000L); //24x2 hours
//
//            } while (true);
//        } catch (IOException ex) {
//            //Logger.getLogger(ManagerScanner.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Run Application");
        this.mylist = new ArrayList();
        mylist.add("First string");
        mylist.add("Second string"); 
        mylist.add("Third string");
}
//    }
//    private SessionFactory getSessionFactory() {
//        return new Configuration().configure().buildSessionFactory();
//    }
}
