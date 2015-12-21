/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Greg
 */
public class ChordStatics {

    private static final Map<String, Integer> notePos;

    private static final List<String> intName;

    private static final List<Integer> guitarStringNote;

    private static final Map<String, ChordForm> chordForm;
    
    static {
        chordForm = new TreeMap();
        chordForm.put("", ChordForm.MAJOR);
        chordForm.put("M", ChordForm.MAJOR);
        chordForm.put("m", ChordForm.MINOR);
        chordForm.put("6", ChordForm.SIXTH);
        chordForm.put("m6", ChordForm.MIN6);
        chordForm.put("7", ChordForm.SEVENTH);
        chordForm.put("m7", ChordForm.MIN7);
        chordForm.put("maj7", ChordForm.MAJ7);
        chordForm.put("sus", ChordForm.SUS4);
        chordForm.put("7sus", ChordForm.SEVENTH_SUS4);
        chordForm.put("9", ChordForm.NINTH);
        chordForm.put("m9", ChordForm.MIN9);
        chordForm.put("add9", ChordForm.ADD9);
        chordForm.put("madd9", ChordForm.MINOR_ADD9);
        chordForm.put("dim", ChordForm.DIM);
        chordForm.put("aug", ChordForm.AUG);
        
        notePos = new TreeMap();
        notePos.put("A", 0);
        notePos.put("A#", 1);
        notePos.put("Bb", 1);
        notePos.put("B", 2);
        notePos.put("C", 3);
        notePos.put("C#", 4);
        notePos.put("Db", 4);
        notePos.put("D", 5);
        notePos.put("D#", 6);
        notePos.put("Eb", 6);
        notePos.put("E", 7);
        notePos.put("F", 8);
        notePos.put("F#", 9);
        notePos.put("Gb", 9);
        notePos.put("G", 10);
        notePos.put("G#", 11);
        notePos.put("Ab", 11);

        intName = new ArrayList();
        intName.add("P1");
        intName.add("m2");
        intName.add("M2");
        intName.add("m3");
        intName.add("M3");
        intName.add("P4");
        intName.add("d5");
        intName.add("P5");
        intName.add("m6");
        intName.add("M6");
        intName.add("m7");
        intName.add("M7");

        guitarStringNote = new ArrayList();
        guitarStringNote.add(7); // E
        guitarStringNote.add(0); // A
        guitarStringNote.add(5); // D
        guitarStringNote.add(10); // G
        guitarStringNote.add(2); // B
        guitarStringNote.add(7); // E
    }

    public static String intervalName(int interval) {
        return intName.get(interval);
    }

    public static int interval(int rootPos, int notePos) {
        int ival = (notePos - rootPos) % 12;
        while (ival < 0) {
            ival += 12;
        }
        return ival;
    }

    public static String intervalName(int rootPos, int notePos) {
        int i = interval(rootPos, notePos);
        return intervalName(i);
    }

    public static String intervalName(String rootNote, int notePos) {
        int rootPos = labelNote(rootNote);
        if (rootPos >= 0) {
            return intervalName(rootPos, notePos);
        }
        return null;
    }

    public static String intervalName(String rootNote, String posNote) {
        int rootPos = labelNote(rootNote);
        if (rootPos >= 0) {
            int notePos = labelNote(posNote);
            if (notePos >= 0) {
                return intervalName(rootPos, notePos);
            }
        }
        return null;
    }

    public static KeyOrientation labelOrientation(String note) {
        if (note.length() > 1) {
            if (note.charAt(1) == 'b') {
                return KeyOrientation.FLATTED;
            }
            if (note.charAt(1) == '#') {
                return KeyOrientation.SHARPED;
            }
        }
        return KeyOrientation.NONE;
    }

    public static int labelNote(String note) {
        Integer pos = notePos.get(note);
        if (pos != null) {
            return pos;
        }
        if (note.length() > 1) {
            pos = notePos.get(note.substring(0, 2));
            if (pos != null) {
                return pos;
            }
        }
        if (note.length() > 0) {
            pos = notePos.get(note.substring(0, 1));
            if (pos != null) {
                return pos;
            }
        }
        return -1;
    }

    public static String labelKeyString(String label) {
        if (label.length() < 1) {
            return null;
        }
        char ch1 = label.charAt(0);
        if (!(ch1 >= 'A' && ch1 <= 'G')) {
            return null;
        }
        if (label.length() == 1) {
            return label;
        }
        char ch2 = label.charAt(1);
        if (ch2 == 'b' || ch2 == '#') {
            return label.substring(0,2);
        }
        return label.substring(0,1);
    }
    
    public static ChordForm labelChordForm(String label) {
        if (label.length() < 1) {
            return ChordForm.UNKNOWN;
        }
        String sKey = labelKeyString(label);
        if (sKey == null) {
            return ChordForm.UNKNOWN;
        }
        String sForm = label.substring(sKey.length());
        ChordForm form = chordForm.getOrDefault(sForm, ChordForm.UNKNOWN);
        if (form.equals(ChordForm.UNKNOWN)) {
            System.out.println("ERROR: UNKNOWN CHORD FORM");
        }
        return form;
    }
    
    public static int guitarNote(int string, int fret) {
        Integer pos = guitarStringNote.get(string);
        if (pos == null) {
            return -1;
        }
        int i = (pos + fret) % 12;
        return i;
    }

    public static int guitarInterval(int rootPos, int string, int fret) {
        int guitarPos = guitarNote(string, fret);
        if (guitarPos >= 0) {
            return interval(rootPos, guitarPos);
        }
        return -1;
    }
}
