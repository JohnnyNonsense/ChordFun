/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

/**
 *
 * @author Greg
 */
public class Fingering {
    private final Finger finger;
    private final int fret;
    
    public Fingering(Finger finger, int fret) {
        this.finger = finger;
        this.fret = fret;
    }

    public Finger getFinger() {
        return finger;
    }

    public int getFret() {
        return fret;
    }

    @Override
    public String toString() {
        return "Fingering{" + "finger=" + finger + ", fret=" + fret + '}';
    }
    
    
}
