package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SaveMenuController {

    private MainController main;

    @FXML
    public GridPane saveMenuGP;
    @FXML
    ComboBox saveMenuTopicCB = new ComboBox();
    @FXML
    CheckBox saveMenuFavouriteCB = new CheckBox();
    @FXML
    TextField saveMenuTitleTF = new TextField();
    @FXML
    ImageView saveMenuImageIV = new ImageView();
    @FXML
    TextArea saveMenuDescriptionTA = new TextArea();

    public void setImageAsCurrentEditingGraphIamge() {
        Image image = new Image("file:" + Context.getInstance().getFileLocationForSavedImages() + Context.getInstance().getCurrentEditingGraph().getFileName());
        saveMenuImageIV.setImage(image);
    }

    public void saveGraph() {
        //check for title uniqueness
        boolean uniqueTitle = true;
        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            if (saveMenuTitleTF.getText().equals(Context.getInstance().getGraphsLL().get(i).getTitle())) {
                System.out.println("title already exists");
                uniqueTitle = false;
                System.out.println(Context.getInstance().getGraphsLL().get(i).getTitle());
                break;
            }
        }
        //TODO: edit these console warnings to GUI
        if (uniqueTitle == false) {
            System.out.println("Title already exists");
        }
        else if (saveMenuTopicCB.getValue() == null) {
            System.out.println("Topic not selected");
        }
        else {
            Context.getInstance().getCurrentEditingGraph().setTitle(saveMenuTitleTF.getText());
            Date date = new Date();
            long dateOfCreation = date.getTime();
            Context.getInstance().getCurrentEditingGraph().setTime(dateOfCreation);
            Context.getInstance().getCurrentEditingGraph().setTopic(saveMenuTopicCB.getValue().toString());
            Context.getInstance().getCurrentEditingGraph().setDescription(saveMenuDescriptionTA.getText());
            Context.getInstance().getCurrentEditingGraph().setFavourite(saveMenuFavouriteCB.isSelected());

            Context.getInstance().getGraphsLL().add(Context.getInstance().getCurrentEditingGraph());
            saveGraphToTextFile();
            setScreenToLibrary();
            main.getLibraryController().resetAndDisplayGraphsFromGraphsLLToLibrary();
        }
        resetGraphMaker();
    }

    public void saveGraphToTextFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Context.getInstance().getFilePathForSavedGraphs()));
            String line;
            List<String> fileLines = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }

            PrintWriter pw = new PrintWriter(new FileWriter(Context.getInstance().getFilePathForSavedGraphs()));
            for (int i = 0; i < fileLines.size(); i++) {
                pw.println(fileLines.get(i));
            }
            String newGraphDetail = Context.getInstance().getCurrentEditingGraph().getTitle() + "," + Context.getInstance().getCurrentEditingGraph().getTopic() + "," +
                    Context.getInstance().getCurrentEditingGraph().getTime() + "," + Context.getInstance().getCurrentEditingGraph().getDescription() + "," +
                    Context.getInstance().getCurrentEditingGraph().isFavourite() + "," + Context.getInstance().getCurrentEditingGraph().getFileName();

            pw.println(newGraphDetail);
            String newCurveLine;
            for (int i = 0; i < Context.getInstance().getCurvesLL().size(); i++) {
                newCurveLine = "newComp," + Context.getInstance().getCurvesLL().get(i).getCurveType() + "," + Context.getInstance().getCurvesLL().get(i).getName() + "," + Context.getInstance().getCurvesLL().get(i).getCentreX()
                        + "," + Context.getInstance().getCurvesLL().get(i).getElasticityGap() + "," + Context.getInstance().getCurvesLL().get(i).getColour() + "," + Context.getInstance().getCurvesLL().get(i).getThickness()
                        + "," + Context.getInstance().getCurvesLL().get(i).getDotted();
                pw.println(newCurveLine);
            }

            String newShadedRegionLine;
            for (int i = 0; i < Context.getInstance().getShadedRegionFieldsLL().size(); i++) {
                newShadedRegionLine = "newComp,ShadedRegion," + Context.getInstance().getShadedRegionFieldsLL().get(i);
                pw.println(newShadedRegionLine);
            }
            pw.close();
            br.close();
        } catch (IOException ioe2) {
            System.out.println("IOError");
        }
    }

    public void setScreenToLibrary() {
        main.setScreenToLibrary();
    }

    public void resetGraphMaker() {
        main.resetForNewGraph();
    }

    public void setScreenToGraphMaker() {
        main.setScreenToGraphMaker();
    }

    public void init(MainController mainController) {
        main = mainController;
    }

    public TextField getSaveMenuTitleTF() {
        return saveMenuTitleTF;
    }

    public void setSaveMenuTitleTF(TextField saveMenuTitleTF) {
        this.saveMenuTitleTF = saveMenuTitleTF;
    }

    public CheckBox getSaveMenuFavouriteCB() {
        return saveMenuFavouriteCB;
    }

    public void setSaveMenuFavouriteCB(CheckBox saveMenuFavouriteCB) {
        this.saveMenuFavouriteCB = saveMenuFavouriteCB;
    }

    public ImageView getSaveMenuImageIV() {
        return saveMenuImageIV;
    }

    public void setSaveMenuImageIV(ImageView saveMenuImageIV) {
        this.saveMenuImageIV = saveMenuImageIV;
    }

    public TextArea getSaveMenuDescriptionTA() {
        return saveMenuDescriptionTA;
    }

    public void setSaveMenuDescriptionTA(TextArea saveMenuDescriptionTA) {
        this.saveMenuDescriptionTA = saveMenuDescriptionTA;
    }

    public ComboBox getSaveMenuTopicCB() {
        return saveMenuTopicCB;
    }

    public void setSaveMenuTopicCB(ComboBox saveMenuTopicCB) {
        this.saveMenuTopicCB = saveMenuTopicCB;
    }
}
