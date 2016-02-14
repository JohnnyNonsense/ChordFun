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
public enum Key {
    AF("Ab", "A\u266D", Note.GS_AF, KeyOrientation.FLATTED),
    AN("A", "A", Note.AN, KeyOrientation.NONE), 
    AS("A#", "A\u266F", Note.AS_BF, KeyOrientation.SHARPED),
    BF("Bb", "B\u266D", Note.AS_BF, KeyOrientation.FLATTED),
    BN("B", "B", Note.BN, KeyOrientation.NONE),
    CN("C", "C", Note.CN, KeyOrientation.NONE),
    CS("C#", "C\u266F", Note.CS_DF, KeyOrientation.SHARPED),
    DF("Db", "D\u266D", Note.CS_DF, KeyOrientation.FLATTED),
    DN("D", "D", Note.DN, KeyOrientation.NONE),
    DS("D#", "D\u266F", Note.DS_EF, KeyOrientation.SHARPED),
    EF("Eb", "E\u266D", Note.DS_EF, KeyOrientation.FLATTED),
    FN("F", "F", Note.FN, KeyOrientation.NONE),
    FS("F#", "F\u266F", Note.FS_GF, KeyOrientation.SHARPED),
    GF("Gb", "G\u266D", Note.FS_GF, KeyOrientation.FLATTED),
    GN("G", "G", Note.GN, KeyOrientation.NONE),
    GS("G#", "G\u266F", Note.GS_AF, KeyOrientation.SHARPED);


    Key(String labelAscii, String labelUnicode, Note note, KeyOrientation orientation) {
        this.note = note;
        this.labelAscii = labelAscii;
        this.labelUnicode = labelUnicode;
        this.orientation = orientation;
    }
    
    private final Note note;
    private final KeyOrientation orientation;
    private final String labelAscii;
    private final String labelUnicode;

    public Note getNote() {
        return note;
    }

    public KeyOrientation getOrientation() {
        return orientation;
    }

    public String getLabelAscii() {
        return labelAscii;
    }

    public String getLabelUnicode() {
        return labelUnicode;
    }
    
    
    
}
