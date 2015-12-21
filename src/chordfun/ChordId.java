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
public class ChordId implements Comparable<ChordId> {
    private final ChordDbKey key;
    private final int variation;

    public ChordId(ChordDbKey key, int variation) {
        this.key = key;
        this.variation = variation;
    }

    public ChordDbKey getKey() {
        return key;
    }

    public int getVariation() {
        return variation;
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
