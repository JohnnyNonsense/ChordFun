/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Greg
 */
public class GuitarChord {

    private final GuitarChordPrototype prototype;
    private final Key key;
    private final int fretOffset; // fretOffset from prototype, 0 for no offset

    public GuitarChordPrototype getPrototype() {
        return prototype;
    }

    public Key getKey() {
        return key;
    }

    public ChordForm getForm() {
        return prototype.getForm();
    }

    public Note getNote() {
        return prototype.getNote();
    }

    public int getFretOffset() {
        return fretOffset;
    }

    public boolean isMovable() {
        return prototype.isMovable();
    }

    private GuitarChord(GuitarChordPrototype prototype) {
        this.prototype = prototype;
        fretOffset = 0;
        key = prototype.getKey();
    }

    private GuitarChord(GuitarChordPrototype prototype, Key key) {
        this.prototype = prototype;
        this.key = key;
        Note pNote = prototype.getNote();
        Note kNote = key.getNote();
        int tmpOffset = kNote.ordinal() - pNote.ordinal();
        while (tmpOffset < 0) {
            tmpOffset += 12;
        }
        fretOffset = tmpOffset;
    }

    public static GuitarChord create(String dragString) throws IOException {
        /* consists of a series of integers, separated by "/".
        They are: 
        * guitarChord key ordinal
        * guitarChord fretOffset
        * prototype id note ordinal
        * prototype id form ordinal
        * prototype id variation
        * prototype key ordinal
         */
        if (dragString.startsWith("chord|")) {
            Pattern p = Pattern.compile("(\\d+)/(\\d+)/(\\d+)/(\\d+)/(\\d+)/(\\d+)$");
            Matcher m = p.matcher(dragString.substring(6));
            if (m.matches()) {
                int gcKeyOrd = Integer.parseInt(m.group(1));
                int gcFretOff = Integer.parseInt(m.group(2));
                int protoNoteOrd = Integer.parseInt(m.group(3));
                int protoFormOrd = Integer.parseInt(m.group(4));
                int protoVar = Integer.parseInt(m.group(5));
                int protoKeyOrd = Integer.parseInt(m.group(6));
                Note dbKeyNote = Note.values()[protoNoteOrd];
                ChordForm dbKeyForm = ChordForm.values()[protoFormOrd];
                ChordDbKey dbKey = new ChordDbKey(dbKeyNote, dbKeyForm);
                GuitarChordDatabase gcd = GuitarChordDatabase.getInstance();
                GuitarChordPrototype gcp = gcd.find(dbKey, protoVar);
                if (gcp != null) {
                    Key key = Key.values()[protoKeyOrd];
                    GuitarChord gc = GuitarChord.create(gcp, key);
                    return gc;
                }
            }
        }
        return null;
    }

    public static GuitarChord create(GuitarChordPrototype prototype) {
        if (prototype != null) {
            GuitarChord gc = new GuitarChord(prototype);
            return gc;
        }
        return null;
    }

    public static GuitarChord create(GuitarChordPrototype prototype, Key key) {
        if (prototype != null && prototype.isMovable()) {
            if (key != null && prototype.isMovable()) {
                GuitarChord gc = new GuitarChord(prototype, key);
                return gc;
            } else if (key == null) {
                GuitarChord gc = new GuitarChord(prototype);
                return gc;
            }
        }
        return null;
    }

    public String getAsciiKey() {
        return key.getLabelAscii();
    }

    public String getUnicodeKey() {
        return key.getLabelUnicode();
    }

    public String getAsciiLabel() {
        String s = getAsciiKey() + getForm().getLabel();
        return s;
    }

    public String getUnicodeLabel() {
        String sUni = getUnicodeKey() + getForm().getLabel();
        return sUni;
    }

    public String dragStringOf() {
        String s = "chord|" + key.ordinal() + "/" + fretOffset + "/"
                + prototype.dragStringOf();
        return s;
    }
}
