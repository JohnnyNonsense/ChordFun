/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import chordfun.GuiStatics.ChordFormListItem;
import chordfun.GuiStatics.KeyButton;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Greg
 */
public class GuiEngine {

    private BorderPane rootPane;
    private GuiStatics.ChordCenterPane centerPane;
    private GuiStatics.ChordDisplayPane displayPane;
    private GuiStatics.ChordUpperPane palettePane;
    private GuiStatics.ChordFormListPane leftPane;
    private GuiStatics.KeyButtonBar keyButtonBar;

    public BorderPane getRootPane() {
        return rootPane;
    }

    private GuiEngine() {
        init();
    }

    /**
     * reads the key and form selection, gets the appropriate chords from the
     * database, creates Chord canvases for them, and inserts them into the
     * Chord palette pane, erasing the previous chords.
     */
    private void updateChordsToUI() {
        ChordForm form = null;
        palettePane.getChildren().clear();
        ChordFormListItem cfli = leftPane.selectedItem();
        if (cfli != null) {
            form = cfli.getChordForm();
            KeyButton kb = keyButtonBar.getSelected();
            if (kb != null) {
                Key key = kb.getKey();
                try {
                    ChordDbKey dbKey = new ChordDbKey(key.getNote(), form);
                    Collection<GuitarChordPrototype> chords
                            = GuitarChordDatabase.getInstance().get(dbKey);
                    KeyOrientation orientation = key.getOrientation();
                    if (chords != null) {
                        for (GuitarChordPrototype proto : chords) {
                            GuitarChord chord = GuitarChord.create(proto, key);
                            GuiStatics.ChordCanvas canvas = GuiStatics.ChordCanvas
                                    .create(chord);
                            if (canvas != null) {
                                palettePane.getChildren().add(canvas);
                            }
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(GuiEngine.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void init() {

        rootPane = new BorderPane();
        centerPane = GuiStatics.ChordCenterPane.getInstance();
        displayPane = GuiStatics.ChordDisplayPane.getInstance();
        palettePane = GuiStatics.ChordUpperPane.getInstance();
        keyButtonBar = GuiStatics.KeyButtonBar.getInstance();
        try {
            leftPane = GuiStatics.ChordFormListPane.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(ChordFunMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        rootPane.setCenter(centerPane);
        rootPane.setLeft(leftPane);
        keyButtonBar.addListener((ObservableValue<? extends GuiStatics.KeyButton> ov,
                GuiStatics.KeyButton old_val,
                GuiStatics.KeyButton new_val) -> {
            if (new_val != null) {
                System.out.println("selected key: " + new_val.getKey());
            }
            ChordFormListItem item = leftPane.selectedItem();
            if (item != null) {
                System.out.println("selected form: " + item.getChordForm().getLabel());
            }
            updateChordsToUI();
        });
        leftPane.addListener((ObservableValue<? extends GuiStatics.ChordFormListItem> ov,
                GuiStatics.ChordFormListItem old_val,
                GuiStatics.ChordFormListItem new_val) -> {
            palettePane.getChildren().clear();
            System.out.println("selected chord form: " + new_val);
            KeyButton kb = keyButtonBar.getSelected();
            if (kb != null) {
                System.out.println("selected note: " + kb.getText());
            }
            updateChordsToUI();
        });

    }

    public static GuiEngine getInstance() {
        return GuiEngineHolder.INSTANCE;
    }

    private static class GuiEngineHolder {

        private static final GuiEngine INSTANCE = new GuiEngine();
    }
}
