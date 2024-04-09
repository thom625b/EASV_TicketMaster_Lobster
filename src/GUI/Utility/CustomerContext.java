package GUI.Utility;

import BE.Costumers;

public class CustomerContext {

    private static CustomerContext instance;
    private Costumers currentCustomer;

    private CustomerContext() {}

    public static CustomerContext getInstance() {
        if (instance == null) {
            instance = new CustomerContext();
        }
        return instance;
    }



    public void setCurrentCustomer(Costumers customer) {
        this.currentCustomer = customer;
    }

    public Costumers getCurrentCustomer() {
        return this.currentCustomer;
    }

    public int getCurrentCustomerId() {
        if (currentCustomer != null) {
            return currentCustomer.getCostumerID();
        } else {
            // Handle case where there is no user logged in
            return -1; // or throw an exception.
        }
    }


    public void clear() {
        currentCustomer = null;
    }
}