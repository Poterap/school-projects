package zad1.administrator;

import java.io.IOException;

public class Administrator {
    public static void main(String[] args) throws IOException {
        AdministratorView administratorView = new AdministratorView();
        AdministratorModel administratorModel = new AdministratorModel();
        AdministratorController administratorController = new AdministratorController(administratorView, administratorModel);
    }
}
