package controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import model.Filter;
import view.FilterPanel;
import model.CharactersCollection;
import javax.swing.JTextField;

/**
 * Test class for FilterController
 * 
 * This class contains unit tests for the FilterController class.
 * It tests the buildCompositeFilter and buildAgeFilter methods.
 */
class FilterControllerTest {
    private final CharactersCollection collection = new CharactersCollection();
    private final FilterPanel filterPanel = new FilterPanel();
    private final Runnable callback = () -> {};
    private final FilterController controller = new FilterController(collection, filterPanel, callback);

    @Test
    // test the buildCompositeFilter method
    void testBuildCompositeFilter() throws Exception {
        Method method = FilterController.class.getDeclaredMethod("buildCompositeFilter");
        method.setAccessible(true);
        Filter filter = (Filter) method.invoke(controller);
        assertNotNull(filter, "buildCompositeFilter should not return null");
    }

    @Test
    // test the buildAgeFilter method
    void testBuildAgeFilter() throws Exception {
        // use reflection to set the value of the age field
        Field minAgeField = FilterPanel.class.getDeclaredField("minAgeField");
        minAgeField.setAccessible(true);
        JTextField minAgeTextField = (JTextField) minAgeField.get(filterPanel);
        minAgeTextField.setText("18");

        Field maxAgeField = FilterPanel.class.getDeclaredField("maxAgeField");
        maxAgeField.setAccessible(true);
        JTextField maxAgeTextField = (JTextField) maxAgeField.get(filterPanel);
        maxAgeTextField.setText("30");

        Method method = FilterController.class.getDeclaredMethod("buildAgeFilter");
        method.setAccessible(true);
        Filter filter = (Filter) method.invoke(controller);
        assertNotNull(filter, "buildAgeFilter should not return null");
    }
} 