/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.util.prefs.Preferences;

/**
 *
 * @author Greg
 */
public class Prefs {
    private Preferences prefs;
    public static final String STAGE_X = "StageX";
    public static final String STAGE_Y = "StageY";
    public static final String STAGE_WIDTH = "StageWidth";
    public static final String STAGE_HEIGHT = "StageHeight";
    
    private Prefs() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        System.out.println("class name: " + this.getClass().getName());
        //prefs = Preferences.userRoot().node("ChordFun");
    }
    
    public void putDouble(String id, double value) {
        switch(id) {
            case STAGE_X:
            case STAGE_Y:
            case STAGE_WIDTH:
            case STAGE_HEIGHT:
                prefs.putDouble(id, value);
                break;
        }
    }
    
    public double getDouble(String id) {
        switch(id) {
            case STAGE_X:
                return prefs.getDouble(id, 100);
            case STAGE_Y:
                return prefs.getDouble(id, 100);
            case STAGE_WIDTH:
                return prefs.getDouble(id, 400);
            case STAGE_HEIGHT:
                return prefs.getDouble(id, 400);
        }
        return 100.0;
    }
    
    public static Prefs getInstance() {
        return PrefsHolder.INSTANCE;
    }
    
    private static class PrefsHolder {

        private static final Prefs INSTANCE = new Prefs();
    }
}
