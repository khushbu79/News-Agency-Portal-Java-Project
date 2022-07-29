package password;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import papersmaster.DataBaseConnection;

public class pwdViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField pass;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private AnchorPane btn;

    @FXML
    private TextField txtShowpass;

    @FXML
    private CheckBox chkshow;
    
    @FXML
    private FontAwesomeIcon eye;

    @FXML
    private FontAwesomeIcon eyeslash;

   
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			
    			alert.setTitle("Message");
    			
    			alert.setHeaderText("Task Done");
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    
    void showMsgError(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Error");
    			
    			alert.setHeaderText("");
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    
    

    @FXML
    void doLogin(ActionEvent event) {
    	
		
		  if(pass.getText().equals("2001")) {
			  //showMsg("LOGIN SUCCESSFUL");

	    	try{
	    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/DashBoardView.fxml")); 
				Scene scene = new Scene(root);
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.show();
	    		
	    		
				//to hide the opened window
				 
				  Scene scene1=(Scene)btnLogin.getScene();
				  scene1.getWindow().hide();
				 
	    	}

			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		  }
		 else
		   showMsgError("LOGIN UNSUCCESSFUL");
		 
    	
    	
    	}

    @FXML
    void doExit(MouseEvent event) {
    	
    	if(event.getClickCount()==2) {
    		Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
    	}

    }
    @FXML
    void doshow(ActionEvent event) {
    	if(chkshow.isSelected())
    		 {txtShowpass.setDisable(false);
    		 eyeslash.setVisible(true);
    	    	eyeslash.setDisable(false);
    	    	eye.setVisible(false);
    	    	eye.setDisable(true);
    		 String pwd = pass.getText();
    		 txtShowpass.setText(pwd);
    		
    		 }
    	    
    	else
    		{txtShowpass.setDisable(true);
    		eye.setVisible(true);
        	eye.setDisable(false);
        	eyeslash.setVisible(false);
        	eyeslash.setDisable(true);
    		 String str = txtShowpass.getText();
    		 pass.setText(str);
    		txtShowpass.clear();
    		}

    }

    @FXML
    void doeye(MouseEvent event) {
    	if(event.getClickCount()==1)
    	{
    	eyeslash.setVisible(true);
    	eyeslash.setDisable(false);
    	eye.setVisible(false);
    	eye.setDisable(true);
    	chkshow.setSelected(true);
    	 txtShowpass.setDisable(false);
   		 String pwd = pass.getText();
   		 txtShowpass.setText(pwd);
    	}

    }

    @FXML
    void doeyeslash(MouseEvent event) {
    	
    	if(event.getClickCount()==1)
    	{
    	eye.setVisible(true);
    	eye.setDisable(false);
    	eyeslash.setVisible(false);
    	eyeslash.setDisable(true);
    	chkshow.setSelected(false);
    	txtShowpass.setDisable(true);
		 String str = txtShowpass.getText();
		 pass.setText(str);
		txtShowpass.clear();
    	}

    }
    

    Connection con;
    @FXML
    void initialize() {
       con=DataBaseConnection.doConnect();
       if(con==null)
    	   {System.out.println("not connected");
    	 // showMsgError("not connected");
    	   }
       else
       {System.out.println(" connected");
    	   //showMsg("Connected");
       }
       
       txtShowpass.setDisable(true);
       eyeslash.setVisible(false);
       eyeslash.setDisable(true);
       
    }
}
