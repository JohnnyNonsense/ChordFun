/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        GuitarChordDatabase chords = GuitarChordDatabase.getInstance();
        System.out.println("Application.init()-exit()");
    }
    
    @Override
    public void start(Stage primaryStage) {
        guiEngine = GuiEngine.getInstance();
        
        Scene scene = new Scene(guiEngine.getRootPane(), 300, 250);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, 
                    Number oldValue, Number newValue) {
                System.out.println("Scene Width: " + newValue);
            }
            
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, 
                    Number oldValue, Number newValue) {
                System.out.println("Scene Height: " + newValue);
            }
        });
        scene.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Scene xProperty: " + newValue);
            }
            
        });
        scene.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Scene yProperty: " + newValue);
            }
            
        });
        primaryStage.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Stage widthProperty: " + newValue);
                Prefs.getInstance().putDouble(Prefs.STAGE_WIDTH, (double)newValue);
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Stage heightProperty: " + newValue);
                Prefs.getInstance().putDouble(Prefs.STAGE_HEIGHT, (double)newValue);
            }
        });
        primaryStage.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Stage yProperty: " + newValue);
                Prefs.getInstance().putDouble(Prefs.STAGE_Y, (double)newValue);
            }
        });
        primaryStage.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Stage xProperty: " + newValue);
                Prefs.getInstance().putDouble(Prefs.STAGE_X, (double)newValue);
            }
        });
        primaryStage.setX(Prefs.getInstance().getDouble(Prefs.STAGE_X));
        System.out.println("initial X: " + Prefs.getInstance().getDouble(Prefs.STAGE_X));
        primaryStage.setY(Prefs.getInstance().getDouble(Prefs.STAGE_Y));
        primaryStage.setWidth(Prefs.getInstance().getDouble(Prefs.STAGE_WIDTH));
        primaryStage.setHeight(Prefs.getInstance().getDouble(Prefs.STAGE_HEIGHT));

        primaryStage.setTitle("Chords!");
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
