package ks.econograph.controller.screens;

import com.sun.imageio.plugins.gif.GIFImageReader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by KSarm on 07/01/2017.
 */
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

    public void saveGraph() {
        try {
            //check for title uniqueness
            boolean uniqueTitle = true;
            for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
                if (saveMenuTitleTF.getText().equals(Context.getInstance().getGraphsLL().get(i).getTitle())) {
                    System.out.println("title already exists");
                    uniqueTitle = false;
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
                Context.getInstance().getCurrentEditingGraph().setDate(dateOfCreation);
                //TODO: set type
                Context.getInstance().getCurrentEditingGraph().setFavourite(saveMenuFavouriteCB.isSelected());

                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
                //prevents losing all data since printwriter overwrites file
                LinkedList<String> fileLines = new LinkedList<String>();
                String line;
                while ((line = br.readLine()) != null) {
                    fileLines.add(line);
                }

                PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
                for (int i = 0; i < fileLines.size(); i++) {
                    pw.println(fileLines.get(i));
                }
                String newGraphDetail = Context.getInstance().getCurrentEditingGraph().getTitle() + "," + Context.getInstance().getCurrentEditingGraph().getTopic() + "," + Context.getInstance().getCurrentEditingGraph().getDate() + "," + Context.getInstance().getCurrentEditingGraph().isFavourite();
                pw.println(newGraphDetail);
                String newCurveLine = "";
                for (int i = 0; i < Context.getInstance().getCurvesLL().size(); i++) {
                    newCurveLine = "newComp," + Context.getInstance().getCurvesLL().get(i).getType() + "," + Context.getInstance().getCurvesLL().get(i).getName() + "," + Context.getInstance().getCurvesLL().get(i).getCentreX()
                            + "," + Context.getInstance().getCurvesLL().get(i).getElasticityGap() + "," + Context.getInstance().getCurvesLL().get(i).getColour() + "," + Context.getInstance().getCurvesLL().get(i).getThickness()
                            + "," + Context.getInstance().getCurvesLL().get(i).isDotted();
                    pw.println(newCurveLine);
                }
                pw.close();
                br.close();
                setScreenToLibrary();
            }

            resetGraphMaker();
        }
        catch (IOException ioe2) {
            System.out.println("IOError");
        }
    }

    public void setScreenToLibrary() {
        main.setScreenToLibrary();
    }

    public void resetGraphMaker() {
        main.resetGraphMaker();
    }

    public void setScreenToGraphMaker() {
        main.setScreenToGraphMaker();
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
