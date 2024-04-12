package GUI.Controllers.Frame.Coordinator;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatorTicketsControllerTest {

    CoordinatorTicketsController coor;

    @BeforeAll
    static void setupAll(){
        new JFXPanel();
    }

    @BeforeEach
    void setup(){
        coor = new CoordinatorTicketsController();
        coor.setLabel();
    }

    @Test
    void isLabelSet(){
        assertEquals("", coor.getLblTicket().getText());
        coor.btnAddTicket(new ActionEvent());

        assertEquals("2", coor.getLblTicket().getText());

        coor.btnSubtractTicket(new ActionEvent());
        assertEquals("1", coor.getLblTicket().getText());
    }

    @Test
    void ticketCountNotLessThan1() {
        //Triple A pattern

        // Arrange - setup our test objects etc.

        // Act - do the actual calc or method run.
        assertEquals(1,coor.getTicketCount());
        coor.btnSubtractTicket(new ActionEvent());

        // Assert - check if actual val is equal to expected val
        assertEquals(1,coor.getTicketCount());

    }

    @Test
    void btnSubtractTicket() {
        //Triple A pattern

        // Arrange - setup our test objects etc.

        // Act - do the actual calc or method run.
        coor.setTicketCount(10);
        coor.btnSubtractTicket(new ActionEvent());

        // Assert - check if actual val is equal to expected val
        assertEquals(9,coor.getTicketCount());

    }

    @Test
    void btnAddTicket() {
        //Triple A pattern

        // Arrange - setup our test objects etc.

        // Act - do the actual calc or method run.
        coor.setTicketCount(10);
        coor.btnAddTicket(new ActionEvent());

        // Assert - check if actual val is equal to expected val
        assertEquals(11,coor.getTicketCount());
    }
}