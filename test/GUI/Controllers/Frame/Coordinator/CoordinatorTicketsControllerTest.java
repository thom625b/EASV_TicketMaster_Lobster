package GUI.Controllers.Frame.Coordinator;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatorTicketsControllerTest {

    CoordinatorTicketsController coor;

    // Setting up JavaFX environment before all tests run
    @BeforeAll
    static void setupAll(){
        new JFXPanel();
    }

    // Setting up objects before each test method
    @BeforeEach
    void setup(){
        coor = new CoordinatorTicketsController();
        coor.setLabel();
    }

    // Test to check if label is set correctly after adding and subtracting tickets
    @DisplayName("Checks if label is set")

    @Test
    void isLabelSet(){
        // Assert that label text is initially empty
        assertEquals("", coor.getLblTicket().getText());

        // Add tickets
        coor.btnAddTicket(new ActionEvent());
        assertEquals("2", coor.getLblTicket().getText()); // Assert that label text is updated to 2

        // Subtract tickets
        coor.btnSubtractTicket(new ActionEvent());
        assertEquals("1", coor.getLblTicket().getText()); // Assert that label text is updated to 1
    }

    // Test to ensure ticket count is not less than 1
    @DisplayName("Making sure tickets can not be less than 1")
    @Test
    void ticketCountNotLessThan1() {
        // Triple A pattern

        // Act - Attempt to subtract a ticket
        assertEquals(1, coor.getTicketCount()); // Assert that ticket count is initially 1
        coor.btnSubtractTicket(new ActionEvent());

        // Assert - Verify that ticket count remains 1
        assertEquals(1, coor.getTicketCount());
    }

    // Test for btnSubtractTicket method
    @DisplayName("Button to subtract tickets")
    @Test
    void btnSubtractTicket() {
        // Triple A pattern

        // Arrange - Set initial ticket count
        coor.setTicketCount(10);

        // Act - Subtract a ticket
        coor.btnSubtractTicket(new ActionEvent());

        // Assert - Verify that ticket count is decreased by 1
        assertEquals(9, coor.getTicketCount());
    }

    // Test for btnAddTicket method
    @DisplayName("Button to add tickets")
    @Test
    void btnAddTicket() {
        // Triple A pattern

        // Arrange - Set initial ticket count
        coor.setTicketCount(10);

        // Act - Add a ticket
        coor.btnAddTicket(new ActionEvent());

        // Assert - Verify that ticket count is increased by 1
        assertEquals(11, coor.getTicketCount());
    }
}
