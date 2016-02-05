/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Greg
 */
public class ChordId implements Comparable<ChordId> {

    private final ChordDbKey key;
    private final int variation;

    
    public ChordId(Note note, ChordForm form, int variation) {
        this.key = new ChordDbKey(note, form);
        this.variation = variation;
    }
    
    public ChordId(ChordDbKey key, int variation) {
        this.key = key;
        this.variation = variation;
    }

    public ChordDbKey getKey() {
        return key;
    }

    public Note getNote() {
        return key.getNote();
    }
    
    public ChordForm getForm() {
        return key.getForm();
    }

    public int getVariation() {
        return variation;
    }
    
    public String dragStringOf() {
        String s = "chord|" + key.getNote().ordinal() + "/" + key.getForm().ordinal() + "/" + this.variation;
        return s;
    }

    public static ChordId createFromString(String s) {
        if (s.startsWith("chord|")) {
            s = s.substring(6);
        }
        Pattern p = Pattern.compile("(\\d+)/(\\d+)/(\\d+)$");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            int noteOrd = Integer.parseInt(m.group(1));
            int formOrd = Integer.parseInt(m.group(2));
            int variation = Integer.parseInt(m.group(3));
            return new ChordId(Note.values()[noteOrd], 
                    ChordForm.values()[formOrd], variation);
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.key);
        hash = 89 * hash + this.variation;
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
        final ChordId other = (ChordId) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (this.variation != other.variation) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ChordId o) {
        int cval = this.key.compareTo(o.key);
        if (cval != 0) {
            return cval;
        }
        return this.variation - o.variation;
    }

}
