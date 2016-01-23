/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lottery;

import java.util.Vector;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


/**
 *
 * @author dinesh
 */
public class RMS implements Runnable {
    private String datstr;
    UI ui;
    static final String DBNAME = "studentdata";
    RecordStore rs = null;

    public RMS() {
       
        
    }

    void saveRecords() {

        
    }

    

    public void run() {
       
        try {
            RecordStore.deleteRecordStore(DBNAME);
        } catch (Exception e) {
        }
        try {
            rs = RecordStore.openRecordStore(DBNAME, true);
            Vector myChoise = ui.getVector();
            String data = myChoise.toString();
            byte[] Data = data.getBytes();
            rs.addRecord(Data,0, Data.length );
              rs.closeRecordStore();
        } catch (RecordStoreException e) {
            System.out.println(e);
        }
        try {
            rs = RecordStore.openRecordStore(DBNAME, true);
            datstr = "";
            int lastID = rs.getNextRecordID();
            for (int i = 1; i < lastID; ++i) {
                byte[] datbyte = rs.getRecord(i);
                datstr = datstr + new String(datbyte) + "\n";
            }
            System.out.println("Data from db");
            rs.closeRecordStore();
            
        } catch (RecordStoreException e) {
            System.out.println(e);
        }


    }
    String getRecords() {
        
        return datstr;

    }
}
