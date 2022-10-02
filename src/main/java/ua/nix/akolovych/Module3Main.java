package ua.nix.akolovych;

import ua.nix.akolovych.view.UserInterface;

import java.sql.SQLException;


public class Module3Main {

    public static void main(String[] args) throws SQLException {
        UserInterface userInterface = new UserInterface();
        userInterface.run();
    }
}
