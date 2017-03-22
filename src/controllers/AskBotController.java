package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 15.03.2017.
 */
public class AskBotController implements Initializable{


    @FXML WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Controller class Botto");
        setWebView();

    }

    /**
     * Sets the web view for the bot
     */
    public void setWebView(){
        WebEngine engine = webView.getEngine();
        engine.load("http://folk.ntnu.no/adrianjs/(b)Otto/index.html");
    }
}
