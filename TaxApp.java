package taxapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created on 14 Nov 2018
 * @author Mark Glover
 */
public class TaxApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try 
        {
            AnchorPane page = (AnchorPane) FXMLLoader.load(TaxApp.class.getResource("TaxApp.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TaxApp");
            primaryStage.show(); 
        } 
        catch (Exception ex) {
            Logger.getLogger(TaxApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
