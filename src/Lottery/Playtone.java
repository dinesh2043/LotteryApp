/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lottery;

import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VolumeControl;

/**
 *
 * @author dinesh
 */
class Playtone implements Runnable {
    private Player player;
    private ToneControl tc;
    private UI midlet;
    private byte[] seq=null;
    private byte tempo;
    
    public Playtone(UI amidlet) {
    midlet = amidlet;
    }

    

    void stopPlayer() {
        player.close();
    }

    public void run() {
        tempo = 30;
        seq = new byte[] {
        ToneControl.VERSION, 1, //version 1
        ToneControl.TEMPO, tempo, // set tempo
        67, 16, // The
        69, 16, // hills
        67, 8, // are
        65, 8, // a -
        64, 48, // live
        62, 8, // with
        60, 8, // the
        59, 16, // sound
        57, 16, // of
        59, 32, // mu -
        59, 32 // sic
  };
        try{
        player =
        Manager.createPlayer(Manager.TONE_DEVICE_LOCATOR);
        player.realize();
        tc = (ToneControl)(player.getControl("ToneControl"));
        tc.setSequence(seq);
        player.start();
        }
        catch (MediaException pe) {
        }
        catch (IOException ioe) {
        }
        Player p;
        VolumeControl vc;
        try {
        p = Manager.createPlayer("http://www.youtube.com/watch?v=WEHXP261Q7Y");
        p.realize();
        vc = (VolumeControl) p.getControl("VolumeControl");
        if(vc != null) {
        //vc.setVolume(50);
        }
        p.prefetch();
        p.start();
        }
        catch(IOException ioe) { }
        catch(MediaException e) { }
        
}
}
