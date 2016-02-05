/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Greg
 */
public class GuitarChordBase {

    private static GuitarChordBase INSTANCE = null;

    public static GuitarChordBase getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new GuitarChordBase();
        }
        return INSTANCE;
    }

    private Multimap<ChordDbKey, GuitarChord> multimap = null;

    public Collection<GuitarChord> get(ChordDbKey dbKey) {
        return multimap.get(dbKey);
    }

    public GuitarChord find(ChordDbKey dbKey, int variation) {
        Collection<GuitarChord> chords = get(dbKey);
        if (chords == null) {
            return null;
        }
        for (GuitarChord gc : chords) {
            if (gc.getVariation() == variation) {
                return gc;
            }
        }
        return null;
    }

    public List<ChordDbKey> getKeys() {
        ArrayList<ChordDbKey> keys = new ArrayList<>(multimap.keySet());
        return keys;
    }
    
    public Collection<GuitarChord> getChords() {
        Collection<GuitarChord> chords = new ArrayList<>();
        for (ChordDbKey dbKey : multimap.keySet()) {
            Collection c = multimap.get(dbKey);
            if (c != null) {
                chords.addAll(c);
            }
        }
        return chords;
    }
    
    private GuitarChordBase() throws FileNotFoundException {
        System.out.println("loading chords from file ChordData.yaml");
        multimap = ArrayListMultimap.create();
        Path p = Paths.get("ChordData.yaml");
        InputStream input = new FileInputStream(p.toFile());
        Yaml yaml = new Yaml();
        Object data = yaml.load(input);
        System.out.println("data loaded: " + data);
        System.out.println("data class: " + data.getClass());
        if (data instanceof ArrayList) {
            ArrayList list = (ArrayList) data;
            for (Object item : list) {
                ArrayList row = (ArrayList) item;
                GuitarChord gc = GuitarChord.create(row);
                if (gc == null) {
                    System.out.println("ERROR: could not create GuitarChord from yaml row: " + row);
                } else {
                    multimap.put(gc.getDbKey(), gc);
                    System.out.println("chord: " + gc);
                }
            }
        }
    }

}
