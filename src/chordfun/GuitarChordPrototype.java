/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Greg
 */
public class GuitarChordPrototype {

    final ArrayList yaml;
    final ChordPrototypeId id;
    final Key key;
    final static Pattern PATT = Pattern.compile("(\\d*)/(\\d*)");

    // A list of string Fingerings
    // There will be exactly 6 strings in the list, from low E to high E
    final List<Fingering> fingeringList;

    public Note getNote() {
        return key.getNote();
    }

    public Key getKey() {
        return key;
    }

    public KeyOrientation getOrientation() {
        return key.getOrientation();
    }

    public boolean isMovable() {
        for (Fingering fng : fingeringList) {
            if (fng.getFinger() == Finger.OPEN) {
                return false;
            }
        }
        return true;
    }

    public ChordForm getForm() {
        return id.getForm();
    }

    public int getRootPosition() {
        return getNote().ordinal();
    }

    /*
    public String getAsciiNote(KeyOrientation orientation) {
        if (orientation == KeyOrientation.NONE) {
            return getNote().getAsciiLabel(this.orientation);
        }
        return getNote().getAsciiLabel(orientation);
    }

    public String getUnicodeNote(KeyOrientation orientation) {
        if (orientation == KeyOrientation.NONE) {
            return getNote().getUnicodeLabel(this.orientation);
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
     */
    public ArrayList getYaml() {
        return yaml;
    }

    public int getVariation() {
        return id.getVariation();
    }

    public List<Fingering> getFingeringList() {
        return fingeringList;
    }

    public int chordPosition() {
        int minFret = 1000;
        for (int i = 0; i < 6; i++) {
            Fingering fng = fingeringList.get(i);
            Finger fn = fng.getFinger();
            if (fn != Finger.MUTED) {
                minFret = Math.min(minFret, fng.getFret());
            }
        }
        if (minFret == 1000) {
            minFret = -1;
        }
        return minFret;
    }

    public ChordDbKey getDbKey() {
        return id.getDbKey();
    }

    /*
    public GuitarChordPrototype reorient(KeyOrientation orientation) {
        GuitarChordPrototype gc = reorient(this, orientation);
        return gc;
    }
     */
    private GuitarChordPrototype(ArrayList yaml,
            Key key,
            ChordForm form,
            int variation,
            List<Fingering> fingeringList) {
        this.yaml = yaml;
        this.key = key;
        this.fingeringList = fingeringList;
        this.id = new ChordPrototypeId(key.getNote(), form, variation);
    }

    /*
    public static GuitarChordPrototype reorient(GuitarChordPrototype gc, KeyOrientation orientation) {
        String label = gc.label;
        int variation = gc.getVariation();
        Note note = gc.getNote();
        ChordForm form = gc.getForm();
        Map<Integer, Integer> fret = new HashMap<>(gc.fretMap);
        Map<Integer, Integer> finger = new HashMap<>(gc.fingerMap);
        GuitarChordPrototype newGc = new GuitarChordPrototype(label, variation, note,
                orientation, form, fret, finger);
        return newGc;
    }
     */
 /*
    public static GuitarChordPrototype copy(GuitarChordPrototype gc) {
        String label = gc.label;
        int variation = gc.getVariation();
        Note note = gc.getNote();
        KeyOrientation orientation = gc.getOrientation();
        ChordForm form = gc.getForm();
        Map<Integer, Integer> fret = new HashMap<>(gc.fretMap);
        Map<Integer, Integer> finger = new HashMap<>(gc.fingerMap);
        GuitarChordPrototype newGc = new GuitarChordPrototype(label, variation, note,
                orientation, form, fret, finger);
        return newGc;
    }
     */
    public static GuitarChordPrototype create(ArrayList yaml) {
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
            System.out.println("Error in yaml chord file: "
                    + yaml.toString() + ", index 0");
            return null;
        }
        String label = (String) yaml.get(0);

        System.out.println("label: " + label);
        
        Key key = ChordStatics.labelKey(label);
        System.out.println("key: " + key);
        
        ChordForm form = ChordStatics.labelChordForm(label);
        System.out.println("form: " + form);
        
        Object var = yaml.get(1);
        int variation;
        if (var instanceof Integer) {
            variation = (Integer)var;
            System.out.println("variation: " + variation);
        }
        else {
            System.out.println("Error in yaml chord file: "
                    + yaml.toString() + ", index 1");
            return null;
        }
        List<Fingering> fngLst = new ArrayList<>();
        int error_index = -1;
        for (int i = 0; i < 6; i++) {
            System.out.println("string: " + i);
            Object o = yaml.get(i + 2);
            System.out.println("object @ index " + (i+2) + ": " + o);
            if (o instanceof String) {
                if (((String) o).equalsIgnoreCase("x")) {
                    System.out.println("setting fingering for x");
                    fngLst.add(new Fingering(Finger.MUTED, 0));
                } else {
                    System.out.println("setting fingering for a note");
                    Matcher m = PATT.matcher((String) o);
                    if (m.matches()) {
                        try {
                            Integer fret = Integer.parseUnsignedInt(m.group(1));
                            Integer fng = Integer.parseUnsignedInt(m.group(2));
                            if (fret == 0 && fng == 0) {
                                fngLst.add(new Fingering(Finger.OPEN, 0));
                            } else if (fret > 0 && fng > 0 && fng < 6) {
                                fngLst.add(new Fingering(
                                        Finger.values()[fng - 1
                                        + Finger.F1.ordinal()], fret));
                            } else {
                                error_index = i;
                                break;
                            }
                        } catch (NumberFormatException nfe) {
                            error_index = i;
                            break;
                        }
                    } else {
                        error_index = i;
                        break;
                    }
                }
            } else if (o instanceof Integer) {
                System.out.println("setting fingering for a lone integer");
                if ((int) o == 0) {
                    fngLst.add(i, new Fingering(Finger.OPEN, 0));
                } else {
                    error_index = i;
                    break;
                }
            } else {
                error_index = i;
                break;
            }
        }
        if (error_index > -1) {
            System.out.println("Error in yaml chord file: "
                    + yaml.toString() + ", index " + (error_index + 2));
            return null;
        }
        return create(yaml, key, form, variation, fngLst);
    }

    public static GuitarChordPrototype create(ArrayList yaml,
            Key key,
            ChordForm form,
            int variation,
            List<Fingering> fngLst) {
        if (fngLst == null || key == null || form == null) {
            return null;
        }
        if (fngLst.size() != 6) {
            return null;
        }
        GuitarChordPrototype gc = new GuitarChordPrototype(yaml,
                key, form, variation, fngLst);
        return gc;
    }

    @Override
    public String toString() {
        return "GuitarChordPrototype{" + "yaml=" + yaml + ", id=" + id 
                + ", key=" + key 
                + ", fingeringList=" + fingeringList + '}';
    }

    public String dragStringOf() {
        String s = id.dragStringOf() + "/" + key.ordinal();
        return s;
    }

}
