/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Greg
 */
public class GuitarChord {

    final String label;
    final ChordId id;
    //final Note note;
    final KeyOrientation labelOrientation;
    //final ChordForm form;
    //final int variation;
    final Map<Integer, Integer> fretMap;
    final Map<Integer, Integer> fingerMap;
    //final ChordDbKey dbKey;
    
    public Note getNote() {
        return id.getNote();
    }

    public KeyOrientation getOrientation() {
        return labelOrientation;
    }

    public ChordForm getForm() {
        return id.getForm();
    }

    public int getRootPosition() {
        return getNote().ordinal();
    }
    
    public String getAsciiNote(KeyOrientation orientation) {
        if (orientation == KeyOrientation.NONE) {
            return getNote().getAsciiLabel(this.labelOrientation);
        }
        return getNote().getAsciiLabel(orientation);
    }
    public String getUnicodeNote(KeyOrientation orientation) {
        if (orientation == KeyOrientation.NONE) {
            return getNote().getUnicodeLabel(this.labelOrientation);
        }
        return getNote().getUnicodeLabel(orientation);
    }
    
    public String getAsciiLabel(KeyOrientation orientation) {
        String s = getAsciiNote(orientation) + getForm().getLabel();
        return s;
    }
    
    public String getUnicodeLabel(KeyOrientation orientation) {
        String sUni = getUnicodeNote(orientation) + getForm().getLabel();
        return sUni;
    }
    
    public String getLabel() {
        return label;
    }

    public int getVariation() {
        return id.getVariation();
    }

    public Map<Integer, Integer> getFretMap() {
        return fretMap;
    }

    public Map<Integer, Integer> getFingerMap() {
        return fingerMap;
    }

    public int chordPosition() {
        int minFret = -1;
        for (int i = 0; i < 6; i++) {
            int fret = fretMap.getOrDefault(i, -1);
            if (fret > -1) {
                if (minFret == -1) {
                    minFret = fret;
                }
                else if (fret < minFret) {
                    minFret = fret;
                }
            }
        }
        return minFret;
    }
    
    /*
    public String getKey() {
        return label + "/" + ((Integer)variation).toString();
    }
    */
    
    public ChordDbKey getDbKey() {
        return id.getKey();
    }
    
    private GuitarChord(String label, int variation, 
            Note note, KeyOrientation labelOrientation,
            ChordForm form,
            Map<Integer, Integer> fret,
            Map<Integer, Integer> finger) {
        this.label = label;
        this.labelOrientation = labelOrientation;
        this.fretMap = fret;
        this.fingerMap = finger;
        this.id = new ChordId(note, form, variation);
    }

    
    
    public static GuitarChord copy(GuitarChord gc) {
        String label = gc.label;
        int variation = gc.getVariation();
        Note note = gc.getNote();
        KeyOrientation orientation = gc.getOrientation();
        ChordForm form = gc.getForm();
        Map<Integer, Integer> fret = new HashMap<>(gc.fretMap);
        Map<Integer, Integer> finger = new HashMap<>(gc.fingerMap);
        GuitarChord newGc = new GuitarChord(label, variation, note, 
                orientation, form, fret, finger);
        return newGc;
    }
    
    public static GuitarChord create(ArrayList yaml) {
        System.out.println("creating guitar chord from yaml");
        if (yaml == null) {
            System.out.println("error: yaml is null");
            return null;
        }
        if (yaml.size() != 8) {
            System.out.println("error: size of yaml is " + yaml.size());
            return null;
        }
        if (!(yaml.get(0) instanceof String)) {
            System.out.println("Error: first item in yaml row must be a string");
            return null;
        }
        if (!(yaml.get(1) instanceof Integer)) {
            System.out.println("Error: second item in yaml row must be an integer");
            return null;
        }
        String name = (String)yaml.get(0);
        int variation = (Integer)yaml.get(1);
        String[] fingers = new String[6];
        for (int i = 0; i < 6; i++) {
            Object o = yaml.get(i+2);
            if (o instanceof String) {
                fingers[i] = (String)o;
            }
            else if (o instanceof Integer) {
                fingers[i] = ((Integer)o).toString();
            }
            else {
                System.out.println("Error: wrong item type in yaml row, item index: " + i);
                return null;
            }
        }

        return create(name, variation, fingers);
    }
    
    
    public static GuitarChord create(String label, int variation, 
            String fingers) {

        if (label == null || fingers == null) {
            return null;
        }
        String[] split = fingers.trim().split("\\s+");
        return create(label, variation, split);
    }

    public static GuitarChord create(String label, int variation, 
            String[] fingers) {
        if (label == null || fingers == null) {
            return null;
        }
        if (fingers.length != 6) {
            return null;
        }
        int rootPosition = ChordStatics.labelNote(label);
        if (rootPosition == -1) {
            return null;
        }
        Note note = Note.values()[rootPosition];
        KeyOrientation orientation = ChordStatics.labelOrientation(label);
        ChordForm form = ChordStatics.labelChordForm(label);
        Map<Integer, Integer> fret = new HashMap<>();
        Map<Integer, Integer> finger = new HashMap<>();
        Pattern p = Pattern.compile("(\\d*)/(\\d*)");
        for (int i = 0; i < 6; i++) {
            String s = fingers[i];
            if (s.length() < 1) {
                return null;
            } else if (s.startsWith("x")) {
            } else if (s.equals("0")) {
                fret.put(i, 0);
            } else {
                Matcher m = p.matcher(s);
                if (m.matches()) {
                    try {
                        Integer int1 = Integer.parseUnsignedInt(m.group(1));
                        Integer int2 = Integer.parseUnsignedInt(m.group(2));
                        fret.put(i, int1);
                        finger.put(i, int2);
                    } catch (NumberFormatException nfe) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        GuitarChord gc = new GuitarChord(label, variation, 
                note, orientation, form, fret, finger);
        return gc;
        
    }

    @Override
    public String toString() {
        return "GuitarChord{" + "label=" + label + ", note=" + getNote() + ", labelOrientation=" + labelOrientation + ", form=" + getForm() + ", variation=" + getVariation() + ", fretMap=" + fretMap + ", fingerMap=" + fingerMap + '}';
    }

 
    public String dragStringOf() {
        return id.dragStringOf();
    }
    


}
