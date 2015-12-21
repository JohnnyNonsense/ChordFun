/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordfun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Greg
 */
public class GuiStatics {

    static {
        chordFont = Font.font("Dialog", FontWeight.BOLD, 14);
        intervalFont = Font.font("Arial", FontWeight.THIN, 10);
    }
    
    private static Font chordFont;
    private static Font intervalFont;
    
    public static class ChordRegion extends Region {

        private ChordRegion(ChordCanvas canvas) {
            super();
            double outerPadding = 2;
            double innerPadding = 4;
            double layoutOffset = outerPadding + innerPadding;
            Background bg = new Background(new BackgroundFill(Color.GREEN,
                    null, new Insets(outerPadding)));
            this.setBackground(bg);
            this.setPadding(new Insets(innerPadding));
            this.getChildren().add(canvas);
            canvas.relocate(innerPadding + outerPadding, innerPadding + outerPadding);
        }

        public static ChordRegion create(ChordCanvas canvas) {
            ChordRegion region = new ChordRegion(canvas);
            return region;
        }
    }

    public static class ChordCanvas extends Canvas {

        GuitarChord chord;

        private ChordCanvas(int width, int height, GuitarChord chord,
                KeyOrientation orientation) {
            super(width, height);
            this.chord = chord;
            GraphicsContext gc = this.getGraphicsContext2D();
            gc.setFont(chordFont);
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.BLACK);
            System.out.println("chord label: " + chord.getLabel());
            String sLabel = chord.getUnicodeLabel(orientation);
            Text t = new Text(sLabel);
            t.setFont(chordFont);
            Bounds b = t.getBoundsInLocal();
            System.out.println("text BoundsInLocal: " + t.getBoundsInLocal());
            System.out.println("baseLineOffset: " + t.getBaselineOffset());
            System.out.println("text Width: " + b.getWidth());
            System.out.println("text Height: " + b.getHeight());
            double xOffset = (width - b.getWidth()) * 0.5;
            gc.fillText(sLabel, xOffset, t.getBaselineOffset());
            double gridWidth = 0.65 * width;
            double gridSpacing = gridWidth / 5.0;
            double gridStrokeWidth = 2.0;
            double gridOffsetX = (width - gridWidth) * 0.5;
            double gridOffsetY = (height - b.getHeight() - gridWidth) * 0.5
                    + b.getHeight();
            // draw strings
            for (int i = 0; i < 6; i++) {
                double x1 = gridOffsetX + i * gridSpacing;
                double y1 = gridOffsetY;
                double x2 = gridOffsetX + i * gridSpacing;
                double y2 = gridOffsetY + gridWidth;
                gc.strokeLine(x1, y1, x2, y2);
            }
            // draw frets
            for (int i = 0; i < 6; i++) {
                double x1 = gridOffsetX;
                double y1 = gridOffsetY + i * gridSpacing;
                double x2 = gridOffsetX + gridWidth;
                double y2 = gridOffsetY + i * gridSpacing;
                gc.strokeLine(x1, y1, x2, y2);
            }
            Map<Integer, Integer> fingers = chord.getFingerMap();
            Map<Integer, Integer> frets = chord.getFretMap();
            int position = chord.chordPosition();
            int fretOffset = 0;
            if (position > 2) {
                fretOffset = 1 - position;
            }
            for (int string = 0; string < 6; string++) {
                int fret = frets.getOrDefault(string, -1);
                int finger = fingers.getOrDefault(string, -1);
                int notePosition = -1;
                if (fret == -1) {
                    Text tx = new Text("X");
                    tx.setFont(chordFont);
                    Bounds bx = tx.getBoundsInLocal();
                    double wx = bx.getWidth();
                    System.out.println("width of X: " + wx);
                    double oy = tx.getBaselineOffset();
                    System.out.println("baseline offset of X: " + oy);
                    System.out.println("height of X: " + bx.getHeight());
                    double x1 = gridOffsetX + string * gridSpacing - wx * 0.5;
                    double y1 = gridOffsetY - bx.getHeight() + oy;
                    gc.setFont(chordFont);
                    gc.setFill(Color.BLACK);
                    gc.fillText("X", x1, y1);
                } else if (fret == 0) {
                    Text tx = new Text("0");
                    tx.setFont(chordFont);
                    Bounds bx = tx.getBoundsInLocal();
                    double wx = bx.getWidth();
                    System.out.println("width of 0: " + wx);
                    double oy = tx.getBaselineOffset();
                    System.out.println("baseline offset of 0: " + oy);
                    System.out.println("height of 0: " + bx.getHeight());
                    double x1 = gridOffsetX + string * gridSpacing - wx * 0.5;
                    double y1 = gridOffsetY - bx.getHeight() + oy;
                    gc.setFont(chordFont);
                    gc.setFill(Color.BLACK);
                    gc.fillText("0", x1, y1);
                    notePosition = ChordStatics.guitarNote(string, fret);
                    String sInt = ChordStatics.intervalName(chord.getRootPosition(),
                            notePosition);
                    tx.setText(sInt);
                    tx.setFont(intervalFont);
                    bx = tx.getBoundsInLocal();
                    wx = bx.getWidth();
                    oy = tx.getBaselineOffset();
                    x1 = gridOffsetX + string * gridSpacing - wx * 0.5;
                    y1 = gridOffsetY + 6 * gridSpacing;
                    gc.setFont(intervalFont);
                    gc.setFill(Color.BLACK);
                    gc.fillText(sInt, x1, y1);
                } else {
                    double radius = (gridSpacing - 2.0) * 0.5;
                    double x1 = gridOffsetX + string * gridSpacing;
                    double y1 = gridOffsetY + (fret + fretOffset - 0.5) * gridSpacing;
                    gc.setFill(Color.BLACK);
                    gc.fillOval(x1 - radius, y1 - radius, radius * 2.0, radius * 2.0);
                    String sF = Integer.toString(finger);
                    Text tf = new Text(sF);
                    tf.setFont(chordFont);
                    Bounds bx = tf.getBoundsInLocal();
                    double wx = bx.getWidth();
                    double oy = tf.getBaselineOffset();
                    double x2 = gridOffsetX + string * gridSpacing - wx * 0.5;
                    double y2 = gridOffsetY + (fret + fretOffset) * gridSpacing
                            - bx.getHeight() + oy;
                    gc.setFont(chordFont);
                    gc.setFill(Color.WHITE);
                    gc.fillText(sF, x2, y2);
                    notePosition = ChordStatics.guitarNote(string, fret);
                    String sInt = ChordStatics.intervalName(chord.getRootPosition(),
                            notePosition);
                    tf.setText(sInt);
                    tf.setFont(intervalFont);
                    bx = tf.getBoundsInLocal();
                    wx = bx.getWidth();
                    oy = tf.getBaselineOffset();
                    x1 = gridOffsetX + string * gridSpacing - wx * 0.5;
                    y1 = gridOffsetY + 6 * gridSpacing;
                    gc.setFont(intervalFont);
                    gc.setFill(Color.BLACK);
                    gc.fillText(sInt, x1, y1);
                }
            }
            if (position > 2) {
                String sB = Integer.toString(position);
                Text tb = new Text(sB);
                tb.setFont(chordFont);
                Bounds bx = tb.getBoundsInLocal();
                double wx = bx.getWidth();
                double oy = tb.getBaselineOffset();
                double x1 = gridOffsetX + 5.6 * gridSpacing;
                double y1 = gridOffsetY + gridSpacing
                        - bx.getHeight() + oy;
                gc.setFont(chordFont);
                gc.setFill(Color.BLACK);
                gc.fillText(sB, x1, y1);
            }
            this.setOnDragDetected(new EventHandler() {

                @Override
                public void handle(Event event) {
                    System.out.println("onDragDetected");
                    Dragboard db = startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();
                    String sContent = "chord|" + chord.getKey();
                    System.out.println("content: " + sContent);
                    content.putString(sContent);
                    db.setContent(content);
                    event.consume();
                }
            });
        }

        public static ChordCanvas create(GuitarChord chord, 
                KeyOrientation orientation) {
            ChordCanvas canvas = new ChordCanvas(130, 160, chord, orientation);
            return canvas;
        }
    }

    public static class ChordUpperScrollPane extends ScrollPane {
        private ChordUpperScrollPane() {
            this.setContent(ChordUpperPane.getInstance());
            this.setOnDragOver((DragEvent event) -> {
                System.out.println("dragging over ChordUpperScrollPane");
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            });
            this.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println("Dropped on ChordUpperScrollPane: " + db.getString());
                    success = true;
                    GuitarChordBase gcs;
                    try {
                        gcs = GuitarChordBase.getInstance();
                        GuitarChord gc = gcs.find(db.getString());
                        if (gc != null) {
                            System.out.println("got chord: " + gc);
                            ChordCanvas canvas = ChordCanvas.create(gc, KeyOrientation.NONE);
                            this.getChildren().add(canvas);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GuiStatics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                event.setDropCompleted(success);
                event.consume();

            });
 
        }
        private static class ChordUpperScrollPaneHolder {

            private static final ChordUpperScrollPane INSTANCE 
                    = new ChordUpperScrollPane();
        }

        public static ChordUpperScrollPane getInstance() {
            return ChordUpperScrollPaneHolder.INSTANCE;
        }
        
    }
    
    public static class ChordUpperPane extends TilePane {

        private ChordUpperPane() {
            this.setVgap(10.0);
            this.setHgap(10.0);
            this.setOnDragOver((DragEvent event) -> {
                System.out.println("dragging over ChordUpperPane");
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            });
            this.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println("Dropped on ChordUpperPane: " + db.getString());
                    success = true;
                    GuitarChordBase gcs;
                    try {
                        gcs = GuitarChordBase.getInstance();
                        GuitarChord gc = gcs.find(db.getString());
                        if (gc != null) {
                            System.out.println("got chord: " + gc);
                            ChordCanvas canvas = ChordCanvas.create(gc, KeyOrientation.NONE);
                            this.getChildren().add(canvas);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GuiStatics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                event.setDropCompleted(success);
                event.consume();

            });
        }

        private static class ChordUpperPaneHolder {

            private static final ChordUpperPane INSTANCE = new ChordUpperPane();
        }

        public static ChordUpperPane getInstance() {
            return ChordUpperPaneHolder.INSTANCE;
        }
    }
    public static class ChordDisplayScrollPane extends ScrollPane {
        private ChordDisplayScrollPane() {
            this.setContent(ChordDisplayPane.getInstance());
            
            this.setOnDragOver((DragEvent event) -> {
                System.out.println("dragging over ChordDisplayScrollPane");
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            });
            this.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println("Dropped on ChordDisplayScrollPane: " + db.getString());
                    success = true;
                    GuitarChordBase gcs;
                    try {
                        gcs = GuitarChordBase.getInstance();
                        GuitarChord gc = gcs.find(db.getString());
                        if (gc != null) {
                            System.out.println("got chord: " + gc);
                            ChordCanvas canvas = ChordCanvas.create(gc, KeyOrientation.NONE);
                            ChordDisplayPane cdp = ChordDisplayPane.getInstance();
                            cdp.getChildren().add(canvas);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GuiStatics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                event.setDropCompleted(success);
                event.consume();

            });

        }
        private static class ChordDisplayScrollPaneHolder {

            private static final ChordDisplayScrollPane INSTANCE 
                    = new ChordDisplayScrollPane();
        }

        public static ChordDisplayScrollPane getInstance() {
            return ChordDisplayScrollPaneHolder.INSTANCE;
        }
        
    }
    
    public static class ChordDisplayPane extends TilePane {

        private ChordDisplayPane() {
            this.setVgap(10.0);
            this.setHgap(10.0);
            this.setOnDragOver((DragEvent event) -> {
                System.out.println("dragging over ChordDisplayPane");
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            });
            this.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println("Dropped on ChordDisplayPane: " + db.getString());
                    success = true;
                    GuitarChordBase gcs;
                    try {
                        gcs = GuitarChordBase.getInstance();
                        GuitarChord gc = gcs.find(db.getString());
                        if (gc != null) {
                            System.out.println("got chord: " + gc);
                            ChordCanvas canvas = ChordCanvas.create(gc, KeyOrientation.NONE);
                            this.getChildren().add(canvas);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GuiStatics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                event.setDropCompleted(success);
                event.consume();

            });
        }

        private static class ChordDisplayPaneHolder {

            private static final ChordDisplayPane INSTANCE 
                    = new ChordDisplayPane();
        }

        public static ChordDisplayPane getInstance() {
            return ChordDisplayPaneHolder.INSTANCE;
        }
    }


    public static class ChordCenterPane extends StackPane {

        private SplitPane splitPane;

        private ChordCenterPane() {
            ChordDisplayScrollPane displayPane = ChordDisplayScrollPane.getInstance();
            ChordUpperScrollPane upperPane = ChordUpperScrollPane.getInstance();
            splitPane = new SplitPane(upperPane, displayPane);
            splitPane.setOrientation(Orientation.VERTICAL);
            this.getChildren().add(splitPane);
        }

        private static class ChordCenterPaneHolder {

            private static final ChordCenterPane INSTANCE = new ChordCenterPane();
        }

        public static ChordCenterPane getInstance() {
            return ChordCenterPaneHolder.INSTANCE;
        }
    }

    public static class NoteButtonBar extends ToolBar {
        private final List<ToggleButton> buttons;
        private final ToggleGroup group;
        
        private NoteButtonBar() {
            buttons = new ArrayList<>();
            group = new ToggleGroup();
            for (Note note : Note.values()) {
                ToggleButton btn = new ToggleButton(note.getToggleLabel());
                buttons.add(btn);
                btn.setToggleGroup(group);
                btn.setPrefWidth(55.0);
            }
            this.getItems().addAll(buttons);
            this.setOrientation(Orientation.VERTICAL);
            this.setPrefWidth(70.0);
        }
        private static class NoteButtonBarHolder {
            private static final NoteButtonBar INSTANCE = new NoteButtonBar();
        }
        public static NoteButtonBar getInstance() {
            return NoteButtonBarHolder.INSTANCE;
        }
    }
    public static class NoteButtonScrollPane extends ScrollPane {
        private final NoteButtonBar buttonBar;
        
        private NoteButtonScrollPane() {
            buttonBar = NoteButtonBar.getInstance();
            setContent(buttonBar);
        }
        private static class NoteButtonScrollPaneHolder {
            private static final NoteButtonScrollPane INSTANCE = new NoteButtonScrollPane();
        }
        public static NoteButtonScrollPane getInstance() {
            return NoteButtonScrollPaneHolder.INSTANCE;
        }
    }
    
    public static class ChordListItem implements Comparable<ChordListItem> {
        private GuitarChord gc;
        ChordListItem(GuitarChord gc) {
            this.gc = gc;
        }

        public GuitarChord getGuitarChord() {
            return gc;
        }

        @Override
        public String toString() {
            return gc.getLabel();
        }

        @Override
        public int compareTo(ChordListItem o) {
            int cmpVal = gc.note.compareTo(o.gc.note); 
            if (cmpVal != 0) return cmpVal;
            if (gc.labelOrientation != o.gc.labelOrientation) {
                if (gc.labelOrientation == KeyOrientation.FLATTED
                        && o.gc.labelOrientation == KeyOrientation.SHARPED) {
                    return 1;
                }
                if (gc.labelOrientation == KeyOrientation.SHARPED
                        && o.gc.labelOrientation == KeyOrientation.FLATTED) {
                    return -1;
                }
            }
            cmpVal = gc.form.compareTo(o.gc.form);
            if (cmpVal != 0) return cmpVal;
            return gc.variation - o.gc.variation;
        }
        
    }
    
    public static class ChordListPane extends HBox {

        ListView<ChordListItem> listView;
        ObservableList<ChordListItem> itemList;
        Collection<GuitarChord> chords;
        Collection<ChordListItem> items;

        public void addListener(ChangeListener<ChordListItem> listener) {
            if (listView != null) {
                listView.getSelectionModel().selectedItemProperty().addListener(listener);
            }
        }

        private ChordListPane() throws IOException {
            chords = GuitarChordBase.getInstance().getChords();
            items = new ArrayList<>();
            for (GuitarChord gc : chords) {
                items.add(new ChordListItem(gc));
            }
            itemList = FXCollections.observableArrayList(items);
            FXCollections.sort(itemList);
            listView = new ListView<>(itemList);
            this.getChildren().addAll(NoteButtonScrollPane.getInstance(), listView);
            listView.setPrefWidth(200.0);
            VBox.setVgrow(listView, Priority.ALWAYS);
            
            HBox.setHgrow(this, Priority.ALWAYS);
        }

        private static class ChordListPaneHolder {

            private static ChordListPane INSTANCE = null;
        }

        public static ChordListPane getInstance() throws IOException {
            if (ChordListPane.ChordListPaneHolder.INSTANCE == null) {
                ChordListPane.ChordListPaneHolder.INSTANCE = new ChordListPane();
            }
            return ChordListPane.ChordListPaneHolder.INSTANCE;
        }

    }

}
