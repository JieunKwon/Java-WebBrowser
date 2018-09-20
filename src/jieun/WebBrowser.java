////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Assignment2
//      Task : Web browser
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : MainBrowser.java
//     - current src task : Model 
//     - created by : Jieun Kwon
//     - created date : March 17, 2018
//     - modified date : March 27, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author JIEUN KWON
 */
public class WebBrowser extends Application {
    
    // Start Primary stage
    @Override
    public void start(Stage stage) throws Exception {
        
        // load FXML file
        Parent root = FXMLLoader.load(getClass().getResource("MainBrowser.fxml"));
        
        // create a scene and set the scene graph to it
        Scene scene = new Scene(root);
        
        // style sheet
        scene.getStylesheets().add(getClass().getResource("app.css").toString());
     
        // set the title of browser 
        stage.setTitle("JigleJigle"); 
        
        // set scene
        stage.setScene(scene);
        
        // show the window
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
