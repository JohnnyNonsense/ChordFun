/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

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
    private GuiStatics.ChordListPane leftPane;

    public BorderPane getRootPane() {
        return rootPane;
    }

    private GuiEngine() {
        init();
    }

    private void init() {

        rootPane = new BorderPane();
        centerPane = GuiStatics.ChordCenterPane.getInstance();
        displayPane = GuiStatics.ChordDisplayPane.getInstance();
        palettePane = GuiStatics.ChordUpperPane.getInstance();
        try {
            leftPane = GuiStatics.ChordListPane.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(ChordFunMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        rootPane.setCenter(centerPane);
        rootPane.setLeft(leftPane);
        leftPane.addListener(
                (ObservableValue<? extends GuiStatics.ChordListItem> ov, 
                        GuiStatics.ChordListItem old_val,
                        GuiStatics.ChordListItem new_val) -> {
                    System.out.println(new_val);
                    palettePane.getChildren().clear();
                    try {
                        Collection<GuitarChord> chords = 
                                GuitarChordBase.getInstance().get(new_val.getGuitarChord().getLabel());
                        if (chords != null) {
                            for (GuitarChord chord : chords) {
                                GuiStatics.ChordCanvas canvas = GuiStatics.ChordCanvas
                                .create(chord, KeyOrientation.NONE);
                                //GuiStatics.ChordRegion region = GuiStatics.ChordRegion.create(canvas);
                                palettePane.getChildren().add(canvas);
                            }
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(GuiEngine.class.getName()).log(Level.SEVERE, null, ex);
                        palettePane.getChildren().clear();
                    }

                });

    }

    public static GuiEngine getInstance() {
        return GuiEngineHolder.INSTANCE;
    }

    private static class GuiEngineHolder {

        private static final GuiEngine INSTANCE = new GuiEngine();
    }
}
