/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Greg
 */
public class ChordInfo {
    private String label;
    private Set<Integer> notes; // 0 is the Unison note. 

    private ChordInfo(String label, Set<Integer> notes) {
        this.label = label;
        this.notes = notes;
    }
    public ChordInfo create(String label, String notes) {
        if (label == null || notes == null) {
            return null;
        }
        Set<Integer> set = new TreeSet<Integer>();
        return new ChordInfo(label, set);
    }
    
    @Override
    public String toString() {
        return "ChordInfo{" + "notes=" + notes + '}';
    }
    
}
