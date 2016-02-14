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
public class ChordPrototypeId {

    private final ChordDbKey dbKey;
    private final int variation;
    
    public ChordPrototypeId(Note note, //KeyOrientation orientation, 
            ChordForm form, int variation) {
        this.dbKey = new ChordDbKey(note, form);
        this.variation = variation;
    }
    
    public ChordPrototypeId(ChordDbKey dbKey, int variation) {
        this.dbKey = dbKey;
        this.variation = variation;
   }

    public ChordDbKey getDbKey() {
        return dbKey;
    }

    public Note getNote() {
        return dbKey.getNote();
    }
    
    public ChordForm getForm() {
        return dbKey.getForm();
    }

    public int getVariation() {
        return variation;
    }
    
    public String dragStringOf() {
        String s = getNote().ordinal() + "/" + getForm().ordinal() + "/" + getVariation();
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.dbKey);
        hash = 97 * hash + this.variation;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChordPrototypeId other = (ChordPrototypeId) obj;
        if (this.variation != other.variation) {
            return false;
        }
        if (!Objects.equals(this.dbKey, other.dbKey)) {
            return false;
        }
        return true;
    }


/*
    @Override
    public int compareTo(ChordPrototypeId o) {
        int cval = getNote().compareTo(o.getNote());
        if (cval != 0) {
            return cval;
        }
        if (this.orientation == KeyOrientation.FLATTED 
                && o.orientation == KeyOrientation.SHARPED) {
            return 1;
        }
        if (this.orientation == KeyOrientation.SHARPED 
                && o.orientation == KeyOrientation.FLATTED) {
            return -1;
        }
        cval = this.dbKey.compareTo(o.dbKey); // compares the chord form
        if (cval != 0) {
            return cval;
        }
        return this.variation - o.variation;
    }
*/

}
