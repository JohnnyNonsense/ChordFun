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
public enum Note {
    AN("A"), 
    AS_BF("A#", "Bb", "A\u266F", "B\u266D", "A#/Bb"), 
    BN("B"), CN("C"), 
    CS_DF("C#", "Db", "C\u266F", "D\u266D", "C#/Db"), 
    DN("D"), 
    DS_EF("D#", "Eb", "D\u266F", "E\u266D", "D#/Eb"), 
    EN("E"), 
    FN("F"), 
    FS_GF("F#", "Gb", "F\u266F", "G\u266D", "F#/Gb"), 
    GN("G"), 
    GS_AF("G#", "Ab", "G\u266F", "A\u266D", "G#/Ab");
    
    private final boolean orientable;
    private final String sLabel;
    private final String sAscSharp;
    private final String sAscFlat;
    private final String sUniSharp;
    private final String sUniFlat;
    private final String sToggleLabel;
    
    Note(String sLabel) {
        this.orientable = false;
        this.sLabel = sLabel;
        this.sAscSharp = sLabel;
        this.sAscFlat = sLabel;
        this.sUniSharp = sLabel;
        this.sUniFlat = sLabel;
        this.sToggleLabel = sLabel;
    }

    Note(String sAscSharp, String sAscFlat, String sUniSharp, String sUniFlat,
            String sToggleLabel) {
        this.orientable = true;
        this.sLabel = "undefined";
        this.sAscSharp = sAscSharp;
        this.sAscFlat = sAscFlat;
        this.sUniSharp = sUniSharp;
        this.sUniFlat = sUniFlat;
        this.sToggleLabel = sToggleLabel;
    }
    
    public String getToggleLabel() {
        return sToggleLabel;
    }
    
    public String getAsciiLabel(KeyOrientation orientation) {
        if (orientable) {
            return orientation == KeyOrientation.FLATTED ? sAscFlat : sAscSharp;
        }
        else {
            return sLabel;
        }
    }
    public String getAsciiLabel() {
        return sAscSharp;
    }
    public String getUnicodeLabel(KeyOrientation orientation) {
        if (orientable) {
            return orientation == KeyOrientation.FLATTED ? sUniFlat : sUniSharp;
        }
        else {
            return sLabel;
        }
    }
    public String getUnicodeLabel() {
        return sUniSharp;
    }
    static public String unicodeSharp = "\u266F";
    static public String unicodeFlat = "\u266D";
}
