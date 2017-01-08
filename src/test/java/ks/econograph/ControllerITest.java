package ks.econograph;

import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Created by KSarm on 29/12/2016.
 */
public class ControllerITest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        EconoGraphApplication econoGraphApplication = new EconoGraphApplication();
        try {
            econoGraphApplication.start(stage);
        } catch (Exception e) {
            fail("should not have thrown any exceptions");
        }
    }
    //TODO: requires controller to be split for each screen to fix this
    @Ignore
    @Test
    public void shouldCreateAndDisplayDemandLineWhenDemandButtonIsClicked() {
        //when

        clickOn("Make a Graph");
        clickOn("Begin");
        clickOn("Demand");
        clickOn("Demand");

        //then
        //demandRadio1 seems to be null
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("demand count : " + Context.getInstance().getDemandCount());
        System.out.println("curve count : " + Context.getInstance().getCurveCount());
        System.out.println(Context.getInstance().getStaticGraphMakerRadioButtonsFP());

        verifyThat("#demandRadio1", isNotNull());
        verifyThat("#demandRadio1", isVisible());
        verifyThat("#line1", isNotNull());
        verifyThat("#line1", isVisible());

        verifyThat("#demandRadio2", isNotNull());
        verifyThat("#demandRadio2", isVisible());
        verifyThat("#line2", isNotNull());
        verifyThat("#line2", isVisible());
    }

    @Test
    public void shouldUploadSavedFilesAndDrawLinesSuccessfully() {
        // given
        clickOn("Make a Graph");
        clickOn("Begin");
        clickOn("Demand");
        clickOn("Demand");
        clickOn("Save");
        clickOn("#saveMenuTitleTF").write("Test Graph");
        clickOn("Select a Topic");
        clickOn("Trade");
        clickOn("Done");

        //when
        clickOn("#editGraph");

        //then
        verifyThat("#demandRadio1", isNotNull());
        verifyThat("#demandRadio1", isVisible());
        verifyThat("#line1", isNotNull());
        verifyThat("#line1", isVisible());
        verifyThat("#demandRadio2", isNotNull());
        verifyThat("#demandRadio2", isVisible());
        verifyThat("#line2", isNotNull());
        verifyThat("#line2", isVisible());
    }

    @Test
    public void shouldDisplayOptionsScreenWhenMakeAGraphButtonIsClicked() {
        // when
        clickOn("Make a Graph");

        // then
        verifyThat("#optionsGP", isVisible());
        verifyThat("#optionsCreateYourOwnGraphL", isVisible());
        verifyThat("#optionsTitleL", isVisible());
        verifyThat("#optionsTitleTF", isVisible());
        verifyThat("#optionsSelectACanvasL", isVisible());
        verifyThat("#optionsTopicsSP", isVisible());
        verifyThat("#optionsMacroB", isVisible());
        verifyThat("#optionsMicroB", isVisible());
        verifyThat("#optionsLabourForceB", isVisible());
        verifyThat("#optionsSubsidyB", isVisible());
        verifyThat("#optionsBeginB", isVisible());
        verifyThat("#optionsCancelB", isVisible());
    }

    @Test
    public void shouldDisplayGraphMakerScreenWhenBeginButtonIsClicked(){
        //when
        clickOn("Make a Graph");
        clickOn("Begin");
        //then
        verifyThat("#graphMakerGP", isVisible());
        verifyThat("#graphMakerInsertL", isVisible());
        verifyThat("#graphMakerInsertSP", isVisible());
        verifyThat("#graphMakerInsertFP", isVisible());
        verifyThat("#graphMakerDemandB", isVisible());
        verifyThat("#graphMakerSupplyB", isVisible());
        verifyThat("#graphMakerSaveB", isVisible());
        verifyThat("#graphMakerWorkspaceL", isVisible());
        verifyThat("#graphMakerAxisIV", isVisible());
        verifyThat("#graphMakerEditL", isVisible());
        verifyThat("#graphMakerRadioButtonsSP", isVisible());
        verifyThat("#graphMakerRadioButtonsFP", isVisible());
        verifyThat("#graphMakerSelectACurveL", isVisible());
        verifyThat("#graphMakerEditSP", isVisible());
        verifyThat("#graphMakerElasticitySlider", isVisible());
        verifyThat("#graphMakerShiftSlider", isVisible());
        verifyThat("#graphMakerColourPicker", isVisible());
        verifyThat("#graphMakerThicknessSlider", isVisible());
    }

    @Test
    public void shouldDisplayLibraryWhenCancelButtonIsClicked(){
        //when
        clickOn("Make a Graph");
        clickOn("Cancel");
        //then
        verifyThat("#libraryGP", isVisible());
        verifyThat("#libraryTitleIV", isVisible());
        verifyThat("#librarySideMenuVB", isVisible());
        verifyThat("#libraryMakeAGraphB", isVisible());
        verifyThat("#librarySaveB", isVisible());
        verifyThat("#libraryDeleteB", isVisible());
        verifyThat("#libraryGP", isVisible());
        verifyThat("#librarySortCB", isVisible());
        verifyThat("#libraryFilterCB", isVisible());
        verifyThat("#libraryFavouritesCB", isVisible());
        verifyThat("#librarySearchTF", isVisible());
        verifyThat("#libraryGraphsSP", isVisible());
        verifyThat("#libraryGraphGP", isVisible());
        verifyThat("#libraryGraphL", isVisible());
        verifyThat("#libraryDescriptionL", isVisible());
        verifyThat("#libraryOptionsL", isVisible());
        verifyThat("#libraryFavouritesL", isVisible());
    }

    @Test
    public void shouldDisplaySaveMenuWhenSaveButtonIsClicked() {
        //when
        clickOn("Make a Graph");
        clickOn("Begin");
        clickOn("Save");
        //then
        verifyThat("#saveMenuGP", isVisible());
        verifyThat("#saveMenuImageIV", isVisible());
        verifyThat("#saveMenuTitleL", isVisible());
        verifyThat("#saveMenuTitleTF", isVisible());
        verifyThat("#saveMenuTopicL", isVisible());
        verifyThat("#saveMenuTopicCB", isVisible());
        verifyThat("#saveMenuFavouriteCB", isVisible());
        verifyThat("#saveMenuDoneB", isVisible());
        verifyThat("#saveMenuBackB", isVisible());
    }

}