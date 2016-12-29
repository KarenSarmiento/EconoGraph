package ks.econograph;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.Main;

import static org.junit.Assert.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Created by KSarm on 29/12/2016.
 */
public class ControllerITest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        Main main = new Main();
        try {
            main.start(stage);
        } catch (Exception e) {
            fail("should not have thrown any exceptions");
        }
    }

    @Test
    public void shouldDisplayGraphMakerScreenWhenMakeAGraphButtonIsClicked() {
        // when
        clickOn("Make a Graph");

        // then
        verifyThat("#optionsGP", isVisible());
        verifyThat("#optionsTitleL", hasText("Create your own new Graph"));
    }
}
