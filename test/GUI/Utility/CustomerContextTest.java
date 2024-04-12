package GUI.Utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BE.Costumers;

class CustomerContextTest {

    private CustomerContext customerContext;

    @BeforeEach
    void setUp() {
        // Reset CustomerContext before each test
        customerContext = null;
        CustomerContext.getInstance().clear();
    }

    @Test
    void getInstance() {
        // Test if getInstance returns the same instance
        CustomerContext instance1 = CustomerContext.getInstance();
        CustomerContext instance2 = CustomerContext.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void setCurrentCustomer() {
        // Create a new customer
        Costumers customer = new Costumers("test@easv365.com", "Søren", "Spangsbjerg");

        // Set the current customer
        CustomerContext.getInstance().setCurrentCustomer(customer);

        // Test if the current customer is set correctly
        assertEquals(customer, CustomerContext.getInstance().getCurrentCustomer());
    }

    @Test
    void getCurrentCustomer() {
        // Test if getCurrentCustomer returns null when no customer is set
        assertNull(CustomerContext.getInstance().getCurrentCustomer());

        // Create a new customer
        Costumers customer = new Costumers("test@easv365.com", "Søren", "Spangsbjerg");

        // Set the current customer
        CustomerContext.getInstance().setCurrentCustomer(customer);

        // Test if getCurrentCustomer returns the correct customer
        assertEquals(customer, CustomerContext.getInstance().getCurrentCustomer());
    }

    @Test
    void getCurrentCustomerId() {
        // Test if getCurrentCustomerId returns -1 when no customer is set
        assertEquals(-1, CustomerContext.getInstance().getCurrentCustomerId());

        // Create a new customer
        Costumers customer = new Costumers("test@example.com", "John", "Doe");

        // Set the current customer
        CustomerContext.getInstance().setCurrentCustomer(customer);

        // Test if getCurrentCustomerId returns the correct customer ID
        assertEquals(customer.getCostumerID(), CustomerContext.getInstance().getCurrentCustomerId());
    }

    @Test
    void clear() {
        // Create a new customer
        Costumers customer = new Costumers("test@example.com", "John", "Doe");

        // Set the current customer
        CustomerContext.getInstance().setCurrentCustomer(customer);

        // Clear the current customer
        CustomerContext.getInstance().clear();

        // Test if getCurrentCustomer returns null after clearing
        assertNull(CustomerContext.getInstance().getCurrentCustomer());

        // Test if getCurrentCustomerId returns -1 after clearing
        assertEquals(-1, CustomerContext.getInstance().getCurrentCustomerId());
    }
}
