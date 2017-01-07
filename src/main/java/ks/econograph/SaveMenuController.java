package ks.econograph;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by KSarm on 07/01/2017.
 */
public class SaveMenuController {

    @FXML ComboBox saveMenuTopicCB = new ComboBox();
    @FXML CheckBox saveMenuFavouriteCB = new CheckBox();
    @FXML TextField saveMenuTitleTF = new TextField();

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
        Context.getInstance().getLibrary().setVisible(true);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(false);
        Context.getInstance().getSaveMenu().setVisible(false);
    }

    public void resetGraphMaker() {
        Context.getInstance().setDemandCount(0);
        Context.getInstance().setSupplyCount(0);
        Context.getInstance().setNewClassicalCount(0);
        Context.getInstance().setKeynesianCount(0);

        if (Context.getInstance().getStaticGraphMakerWorkspaceP() != null && Context.getInstance().getCurvesLL().size() > 0) {
            Context.getInstance().getStaticGraphMakerWorkspaceP().getChildren().remove(1, Context.getInstance().getStaticGraphMakerWorkspaceP().getChildren().size());
            Context.getInstance().setCurvesLL(new LinkedList<>());
        }

        if (Context.getInstance().getStaticGraphMakerRadioButtonsFP() != null && Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().size() > 0) {
            Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().remove(1, Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().size());
        }
    }

    public void setScreenToGraphMaker() {
        Context.getInstance().getLibrary().setVisible(false);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(true);
        Context.getInstance().getSaveMenu().setVisible(false);
    }
}
