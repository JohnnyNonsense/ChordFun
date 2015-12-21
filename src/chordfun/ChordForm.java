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
public enum ChordForm {
    UNKNOWN ("unknown"), 
    MAJOR ("M"), 
    MINOR ("m"), 
    SIXTH ("6"), 
    MIN6 ("m6"),
    MAJ6 ("maj6"), 
    SEVENTH ("7"), 
    MIN7 ("m7"), 
    MAJ7 ("maj7"), 
    SEVENTH_AUG ("7aug"),
    SEVENTH_SUS4 ("7sus4"), 
    SUS4 ("sus4"), 
    NINTH ("9"), 
    MIN9 ("m9"),
    ADD9 ("add9"), 
    MINOR_ADD9 ("madd9"),
    ELEVENTH ("11"), 
    DIM ("dim"),
    AUG ("aug"), 
    ;
    
    private final String label;
    
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "ChordForm{" + "label=" + label + '}';
    }
    
    ChordForm(String label) {
        this.label = label;
    }
}
