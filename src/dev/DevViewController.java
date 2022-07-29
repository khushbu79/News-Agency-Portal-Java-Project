package dev;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DevViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox btnExit;

    @FXML
    void doExit(MouseEvent event) {
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/DashBoardView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			 
    			  Scene scene1=(Scene)btnExit.getScene();
    			  scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}

    }

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'DevView.fxml'.";

    }
}
