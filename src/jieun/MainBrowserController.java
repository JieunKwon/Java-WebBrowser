////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Assignment2
//      Task : Web browser
////////////////////////////////////////////////////////////////////////////////////////////     
//      - current src file : MainBrowserController.java
//      - current src task : Controller  
//      - created by : Jieun Kwon
//      - created date : March 17, 2018
//      - modified date : March 28, 2018 
//      - updated task  : add progressBar
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button; 
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField; 
import javafx.scene.layout.AnchorPane; 
import javafx.scene.web.WebView;

/**
 *
 * @author JIEUN KWON
 */
public class MainBrowserController implements Initializable {
    
    // member vars
    @FXML
    private AnchorPane mainPane;
    @FXML
    private WebView webviewMain;
    @FXML
    private TextField textUrl;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    @FXML
    private ProgressBar pgBar;
    
    // model componant for Url ArrayList
    private WebBrowserModel model = new WebBrowserModel();      
    
    // index for checking current page 
    private int crtIndex;        
    
    // if addUrlChk is true, add url into List (when go to new address)
    private boolean addUrlChk;                          
    
    // url address  
    private String tfAdress;   
    
    //////////////////////////////////////////////////
    // initialize
    //////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         // set textfiled to start http://  
         textUrl.setText("http://");
         
         // set disable for prev and next button 
         buttonPrev.setDisable(true);
         buttonNext.setDisable(true);
         
         // insert "home" into Url list for returning 
         crtIndex = 0;
         model.addUrls("home");
         
        // Worker : for working progressBar and loading web page
        Worker<Void> worker = webviewMain.getEngine().getLoadWorker();
        
        // Listening to the status of worker 
        worker.stateProperty().addListener(          
               new ChangeListener<Worker.State>() {
               @Override
               public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {                               

                   // page failed 
                   if(newState == Worker.State.FAILED){
                       
                       // update addUrlChk for adding list
                       addUrlChk = false;
            
                       // print html in webview
                       webviewMain.getEngine().loadContent("<html><h1>Sorry</h1><h2>This address is not valid</h2></html>","text/html");

                   // page succeeded 
                   }else if(newState == Worker.State.SUCCEEDED){
                        
                       // if new address, add List  
                       if(addUrlChk){
                           
                            // reset List from index 0 to current index
                             if(crtIndex < (model.getSize() - 1)) model.setSubList(crtIndex);

                             // update current position 
                             crtIndex++;
                              
                             // add url into List
                             model.addUrls(tfAdress);

                             // set button to be able
                             buttonPrev.setDisable(false);
                             buttonNext.setDisable(true);
                       }
                       
                   }
               }
        });   
        
        // Progress Bar
        pgBar.progressProperty().bind(worker.progressProperty());
               
    }    
 
    
    //////////////////////////////////////////////////
    // Go button action 
    //////////////////////////////////////////////////
    // URL string validation :  
    // 1. not empty
    // 2. URL must start with http:// or https:// 
    // 3. if not, add http:// to the URL string and textfield is also updated 
    @FXML
    private void handleButtonGoAction(ActionEvent event) throws Exception {
        
        // make Opacity = 1 for webview when current page is home
        if (crtIndex == 0) webviewMain.setOpacity(1);
        
        // get url address from testField   
        tfAdress = textUrl.getText();
         
        // validate empty 
        if(tfAdress.isEmpty()){       
            textUrl.setText("http://");  
        
        // validate string http:// or https:// in url
        }else if( (tfAdress.startsWith("http://") && (tfAdress.length() > 7)) || 
                (tfAdress.startsWith("https://") && (tfAdress.length() > 8))){ 
            
            // update addUrlChk for adding list
            addUrlChk = true;
            
            // go to webpage   
            webviewMain.getEngine().load(tfAdress);
                 
        // when url without "http://"     
        } else if( !tfAdress.startsWith("http://") &&  
                !tfAdress.startsWith("https://") ){ 
            
            // update addUrlChk for adding list
            addUrlChk = true;
            
            // add "http://" in tfAdress
            tfAdress = "http://" + tfAdress;
            
            // update textField 
            textUrl.setText(tfAdress); 
           
            // go to webpage
            webviewMain.getEngine().load(tfAdress);
 
        }
    }

    //////////////////////////////////////////////////
    // Back(<) button action 
    //////////////////////////////////////////////////
    @FXML
    private void handleButtonBackAction(ActionEvent event) {
        
        // get previous url address 
        tfAdress = model.prev(crtIndex);
       
        // update addUrlChk to not add list
        addUrlChk = false;
            
        // check home
        if ( tfAdress == "home") {

            // set home(the first page) mode : textfield value, load blank page, make prev button disable, webView's opacity
            textUrl.setText("http://");
            webviewMain.getEngine().load("");
            buttonPrev.setDisable(true);
            webviewMain.setOpacity(0.5);

        // go to prev url
        }else{
            textUrl.setText(tfAdress);                // set textField value
            webviewMain.getEngine().load(tfAdress);   // load prev url
        }

        // update current position 
        crtIndex--; 
        
        // make next button visible
        if ( crtIndex < (model.getSize() -1) ) buttonNext.setDisable(false);
 
    }

    ///////////////////////////////////////////////////
    // Next(>) button action 
    //////////////////////////////////////////////////
    @FXML
    private void handleButtonNextAction(ActionEvent event) {
        
            // get next url address
            tfAdress = model.next(crtIndex);
            
            // update addUrlChk for adding list
            addUrlChk = false;
     
            // make Opacity = 1 for webview when current page is home
            if (crtIndex == 0) webviewMain.setOpacity(1);

            // make prev button visible
            if ( crtIndex > 0 ) buttonPrev.setDisable(false);

            // update textField
            textUrl.setText(tfAdress);

            // load next url 
            webviewMain.getEngine().load(tfAdress);

            // update current position 
            crtIndex++;
            
            // make next button disable when position is the last
            if ( crtIndex == (model.getSize() - 1) ) buttonNext.setDisable(true);

    }   
   
}
