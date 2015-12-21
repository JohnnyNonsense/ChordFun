/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Greg
 */
public class ChordFunMain extends Application {

    private GuiEngine guiEngine;
    
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void init() throws Exception {
        System.out.println("Application.init()-enter()");
        super.init();
        GuitarChordBase chords = GuitarChordBase.getInstance();
        System.out.println("G chords: " + chords.get("G"));
        System.out.println("Application.init()-exit()");
    }
    
    @Override
    public void start(Stage primaryStage) {
        guiEngine = GuiEngine.getInstance();
        
        Scene scene = new Scene(guiEngine.getRootPane(), 300, 250);
        
        primaryStage.setTitle("Chords Wow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
