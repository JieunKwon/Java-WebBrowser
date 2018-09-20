////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Assignment2
//      Task : Web browser
////////////////////////////////////////////////////////////////////////////////////////////     
//      - current src file : MainBrowserController.java
//      - current src task : Controller  
//      - created by : Jieun Kwon
//      - created date : March 23, 2018
//      - modified date : March 28, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.util.ArrayList; 

/**
 *
 * @author JIEUN KWON
 */
public class WebBrowserModel {
  
    // member vars
    private ArrayList<String> urls;

    // ctor with no args
    public WebBrowserModel() {
        urls = new ArrayList<String>();
    }

    // ctor with arg
    public WebBrowserModel(ArrayList<String> urls) {
        this.urls = urls;
    }
    
    // Method addUrls : add new Url into ArrayList
    public void addUrls(String url){
    
        urls.add(url);
    }
    
    // Method prev : return previous url with param (List's index)
    public String prev(int c){
    
       if(c >= 1){  
            return urls.get(c-1);
       }else{
           return null;
       }
    
    }
    
    // Method next : return next url with param (list's index)
    public String next(int c){
       if(c < urls.size()){  
            return urls.get(c+1);
       }else{
           return null;
       }
    }
    
    // Method getSize : return List's size
    public int getSize(){
    
        return urls.size();
    }
    
    // Method setList : restore ArrayList using subList()
    public void setSubList(int c){
 
        // remove elements after current position index
        for(int i = ++c ; i < urls.size(); i++){
            urls.remove(i);
        }
     
    }
}
