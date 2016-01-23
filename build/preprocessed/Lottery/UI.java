/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lottery;

import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author dinesh
 */
public class UI extends MIDlet implements CommandListener {
    //from RMS

    Vector check;
    static final String DBNAME = "studentdata";
    RecordStore rs = null;
    public Playtone ptone;
    int j = 0;//global counter
    //for reading Xml file
    private XmlLoader readXml;
    //private RMS rms;
    private Thread th1;
    private Thread t;
    //to store user numbers
    Vector myNumber = new Vector();
    // display manager
    Display display;
    // a menu with items
    // main menu
    List menu;
    // list of choices
    List choose;
    // textbox
    TextBox input;
    //textbox
    TextBox output;
    // ticker
    Ticker ticker = new Ticker(
            "Choose 7 Numbers and Try your Luck");
    // alerts
    final Alert soundAlert =
            new Alert("sound Alert");
    final Alert checkAlert = new Alert("check Alert");
    final Alert savedAlert =
            new Alert("saved Alert");
    static final Command backCommand =
            new Command("Back", Command.BACK, 0);
    static final Command mainMenuCommand =
            new Command("Main", Command.SCREEN, 1);
    static final Command exitCommand =
            new Command("Exit", Command.STOP, 2);
    static final Command parseCmd = new Command("Display Data", Command.SCREEN, 2);
    static final Command viewCmd = new Command("Display DB", Command.SCREEN, 2);
    static final Command viewCommand = new Command("Save", Command.SCREEN, 2);
    String currentMenu;
    private String datstr;

    // constructor.
    public UI() {
    }

    /**
     * Start the MIDlet by creating a list of
     * items and associating the
     * exit command with it.
     */
    public void startApp() throws MIDletStateChangeException {
        try {
            RecordStore.deleteRecordStore(DBNAME);
        } catch (Exception e) {
        }
        display = Display.getDisplay(this);
        menu = new List(
                "Lottery Application", Choice.IMPLICIT);
        menu.append("View lottery numbers", null);
        menu.append("Numbers to select", null);
        menu.append("View DataBase", null);

        menu.append("Check Reasult", null);
        menu.addCommand(exitCommand);
        menu.setCommandListener(this);
        menu.setTicker(ticker);
        mainMenu();
        readXml = new XmlLoader();
        th1 = new Thread(readXml);
        th1.start();
        //String k =rms.getRecords();
        //String v = readXml.getRows();
    }

    public void pauseApp() {
        display = null;
        choose = null;
        menu = null;
        ticker = null;
        input = null;
        output = null;
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
    // main menu

    void mainMenu() {
        display.setCurrent(menu);
        currentMenu = "Main";
    }

    /**
     * Test the TextBox component.
     */
    public void testTextBox() {
        input = new TextBox("Check Reasults of Lottery:", "See the reasult", 1024, TextField.ANY);
        input.setTicker(new Ticker(
                "Lottery Reasults"));
        input.addCommand(backCommand);
        input.addCommand(parseCmd);
        input.setCommandListener(this);
        input.setString("");
        display.setCurrent(input);
        currentMenu = "input";
    }

    public void storeNumber() {
        try {
            rs = RecordStore.openRecordStore(DBNAME, true);

            String data = myNumber.toString();
            byte[] Data = data.getBytes();
            rs.addRecord(Data, 0, Data.length);
            System.out.println("Data Saved in db");
            rs.closeRecordStore();
        } catch (RecordStoreException e) {
            System.out.println(e);
        }
    }

    public void fetchNumbers() {
        try {
            rs = RecordStore.openRecordStore(DBNAME, true);
            datstr = "";
            int lastID = rs.getNextRecordID();
            for (int i = 1; i < lastID; ++i) {
                byte[] datbyte = rs.getRecord(i);
                datstr = datstr + new String(datbyte) + "\n";
            }
            System.out.println("Data from db");
            System.out.println("Data from db value" + datstr);
            rs.closeRecordStore();

        } catch (RecordStoreException e) {
            System.out.println(e);
        }

    }

    /**
     * Test the List component.
     */
    public void rmsTextBox() {
        output = new TextBox("Check Reasults of Lottery:", "Check db", 1024, TextField.ANY);
        output.setTicker(new Ticker(
                "Database data"));
        output.addCommand(backCommand);
        output.addCommand(viewCmd);
        output.setCommandListener(this);
        output.setString("");
        display.setCurrent(output);
        currentMenu = "output";
    }

    public void testList() {
        choose = new List("Choose only 7 numbers",
                Choice.MULTIPLE);
        choose.setTicker(new Ticker(
                "List of Numbers"));
        choose.addCommand(viewCommand);
        choose.addCommand(backCommand);
        choose.setCommandListener(this);
        // Append options, with no associated images
        //in a loop
        for (int k = 1; k < 40; k++) {
            String aString = Integer.toString(k);

            choose.append(aString, null);
        }
        display.setCurrent(choose);
        currentMenu = "list";
    }

    /**
     * Test the Alert component.
     */
    public void testAlert() {
        soundAlert.setType(AlertType.ERROR);
        //soundAlert.setTimeout(20);
        soundAlert.setString("** ERROR You Are supposed to select 7 numbers **");
        display.setCurrent(soundAlert);
    }

    public void reasultAlert() {
        checkAlert.setType(AlertType.INFO);
        //checkAlert.setTimeout(20);
        Check();
        if (j == 7) {
            ptone = new Playtone(this);
            t = new Thread(ptone);
            t.start();
            checkAlert.setString("** YOURS NO. OF MARCH IS **" + j + "YOU Win 3 Million euro");
            j = 0;
        } else if (j == 6) {
            ptone = new Playtone(this);
            t = new Thread(ptone);
            t.start();
            checkAlert.setString("** YOURS NO. OF MARCH IS **" + j + "YOU Win 1 587.80 euro");
            j = 0;
        } else if (j == 5) {
            checkAlert.setString("** YOURS NO. OF MARCH IS **" + j + "YOU Win 47.90 e euro");
            j = 0;
        } else if (j == 4) {
            checkAlert.setString("** YOURS NO. OF MARCH IS **" + j + "YOU Win 9,70 e euro");
            j = 0;
        } else {
            checkAlert.setString("** YOURS NO. OF MARCH IS **" + j);
            j = 0;
        }

        display.setCurrent(checkAlert);
    }

    public void Check() {
        System.out.println("my Vector" + readXml.getValue());
        System.out.println("selected Vector" + check);
        Vector reasult = readXml.getValue();




        for (int i = 0; i < 7; i++) {
            //System.out.println("Reasult at 2:"+reasult.elementAt(i));
            //System.out.println("Selection at 2:"+check.elementAt(i));
            String x = reasult.elementAt(i).toString();//checking the elements in vector
            String y = check.elementAt(i).toString();
            if (x == null ? y == null : x.equals(y)) {
                j++;
            }

            System.out.println("Your match:" + j);
        }

    }
    /**
     * Handle events.
     */
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if (label.equals("Save")) {
            boolean selected[] = new boolean[choose.size()];

            // Fill array indicating whether each element is checked 
            choose.getSelectedFlags(selected);

            for (int i = 0; i < choose.size(); i++) {
                //System.out.println(choose.getString(i) + (selected[i] ? ": selected" : ": not selected"));
                //to get the value of selected items in list and save it into array
                if (selected[i]) {
                    myNumber.addElement(choose.getString(i));
                    System.out.println(choose.getString(i));
                    //System.out.println(myNumber);
                }

                //System.out.print(myNumber[5]);
            }
            System.out.println("Size of Vector: " + myNumber.size());
            System.out.println("Capacity of Vector: " + myNumber.capacity());
            Enumeration vEnum = myNumber.elements();
            System.out.println("\nElements in vector:");

            while (vEnum.hasMoreElements()) {
                System.out.print(vEnum.nextElement() + " ");
            }
            System.out.println();
            if (myNumber.size() > 7 || myNumber.size() < 7) {
                myNumber = new Vector();
                testAlert();

            } else {
                check = myNumber;
                storeNumber();
                //rms.getClass();
                myNumber = new Vector();
                saveAlert();
            }
        } else if (label.equals("Display Data")) {
            String v = readXml.getRows();
            if (v != null) {
                input.setString(v);
                System.out.println("Show parsed XML Data from the net.");
            }
        } else if (label.equals("Display DB")) {
            //String k = rms.getRecords();
            fetchNumbers();
            System.out.println("Data from db value to see" + datstr);
            String k = datstr;

            if (k != null) {
                output.setString(k);
                //System.out.println(rms.getRecords());

                System.out.println("Show Data from DB.");
            }
        } else if (label.equals("Exit")) {
            destroyApp(true);
        } else if (label.equals("Back")) {
            if (currentMenu.equals("list")
                    || currentMenu.equals("input")
                    || currentMenu.equals("output")
                    || currentMenu.equals("date")
                    || currentMenu.equals("form")) {
                // go back to menu
                mainMenu();
            }

        } else {
            List down = (List) display.getCurrent();
            switch (down.getSelectedIndex()) {
                case 0:
                    testTextBox();
                    break;
                case 1:
                    testList();
                    break;
                case 2:
                    rmsTextBox();
                    break;
                case 3:
                    reasultAlert();
                    break;
            }

        }
    }

    Vector getVector() {
        System.out.println("\nElements in vector:");
        Enumeration vEnum = myNumber.elements();
        while (vEnum.hasMoreElements()) {
            System.out.print(vEnum.nextElement() + " ");
        }
        return myNumber;
    }

    private void saveAlert() {
        savedAlert.setType(AlertType.INFO);
        //soundAlert.setTimeout(20);
        savedAlert.setString("** Saved Your Number Sucessfully**");
        display.setCurrent(savedAlert);
    }
}
