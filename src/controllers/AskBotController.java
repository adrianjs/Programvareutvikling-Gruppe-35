package controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
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
        System.out.println("SETUP WEBVIEW");
        WebEngine engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        String url = this.getClass().getResource("../resources/botto.html").toExternalForm();
        engine.load(url);
        System.out.println(engine.getOnError());
        engine.setOnAlert(event -> System.out.println("EVENT DATA: " + event.getData()));


    }
}
