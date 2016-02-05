/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.Objects;

/**
 *
 * @author Greg
 */
public class ChordDbKey implements Comparable<ChordDbKey> {
    private final Note note;
    private final ChordForm form;

    @Override
    public int compareTo(ChordDbKey o) {
        if (this.note.ordinal() < o.note.ordinal()) {
            return -1;
        }
        if (this.note.ordinal() > o.note.ordinal()) {
            return 1;
        }
        return this.form.ordinal() - o.form.ordinal();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.note);
        hash = 67 * hash + Objects.hashCode(this.form);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChordDbKey other = (ChordDbKey) obj;
        if (this.note != other.note) {
            return false;
        }
        if (this.form != other.form) {
            return false;
        }
        return true;
    }

    public ChordDbKey(Note note, ChordForm form) {
        this.note = note;
        this.form = form;
    }

    public Note getNote() {
        return note;
    }

    public ChordForm getForm() {
        return form;
    }
    
    public String getAsciiLabel(KeyOrientation orientation) {
        String sNote = note.getAsciiLabel(orientation);
        String sForm = form.getLabel();
        return sNote + sForm;
    }
    
    public String getUnicodeLabel(KeyOrientation orientation) {
        String sNote = note.getUnicodeLabel(orientation);
        String sForm = form.getLabel();
        return sNote + sForm;
    }
    
    public String getDualLabel() {
        String sNote = note.getDualLabel();
        String sForm = form.getLabel();
        return sNote + sForm;
    }
}
