package Lottery;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dineshs
 */
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.*;

class XmlLoader implements Runnable {
    //to store user numbers

    Vector myReasult = new Vector();
    StreamConnection c = null;
    private boolean isRunning;
    InputStream is = null;
    StringBuffer b = new StringBuffer();
    //UI sm;
    String search = "OIKEAT NUMEROT:";//check the required number
    int correction = 30;
    String txt;
    String reasult;

    public XmlLoader() {
          
    }

    public void run() {


        String url = "http://www.yle.fi/tekstitv/txt/P471_01.html";
        try {
            c = (StreamConnection) Connector.open(url);
            is = c.openInputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
                //match = true;
            }
            txt = b.toString();
            int index = txt.indexOf(search);          //finding the index of required text
            int len = search.length();              //finding the length of string
            int begin = index + len + correction;
            //System.out.println(txt);
            System.out.println("indexOf(t) = " + txt.indexOf(search));
            reasult = txt.substring(begin, begin + 19);
            System.out.println(reasult);
            int i = reasult.indexOf(",");
            System.out.println(i);


            String myString = reasult.substring(0, 1);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(2, 4);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(5, 7);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(8, 10);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(11, 13);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(14, 16);
            myReasult.addElement(myString);
            System.out.println(myString);
            myString = reasult.substring(17, 19);
            myReasult.addElement(myString);
            System.out.println(myString);
            System.out.println("Size of Vector: " + myReasult.size());
            System.out.println("Capacity of Vector: " + myReasult.capacity());
            Enumeration vEnum = myReasult.elements();
            System.out.println("\nElements in vector:");
            while (vEnum.hasMoreElements()) {
                System.out.print(vEnum.nextElement() + " ");
            }
            //sm.mtxt = txt;
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (IOException e) {
                System.out.println("Exception: " + e);
            }
        }
    }

    String getRows() {
        if (isRunning == false) {


            //return txt;
            return reasult;

        } else {
            return null;
        }
    }
    Vector getValue(){
       return myReasult;
    }
}
