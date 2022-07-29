package billcollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import bill.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BillCollViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMoblile;

    @FXML
    private TextField txtTotal;

    @FXML
    private Button btnClose;
    
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

    PreparedStatement pstmt;
    @FXML
    void doFetch(ActionEvent event) {
    	try {
			pstmt= con.prepareStatement("select * from customers where mobile=?");
			pstmt.setString(1, txtMoblile.getText());
			ResultSet table = pstmt.executeQuery();
			while(table.next()) {
				Date dos = table.getDate("dos");
				Date dob = table.getDate("dobill");
				String status = table.getString("status");
				if(dos.compareTo(dob) > 0 && status.equals("0") )   
				{  
				showMsg("Bill Already Paid"); 
				}   
				else    
				{  
				 float bill = table.getFloat("bill");
				 txtTotal.setText(String.valueOf(bill));
				}   
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doPaid(ActionEvent event) {
    	
    	try {
			pstmt=con.prepareStatement("update customers set status=? where mobile=?");
			pstmt.setString(2, txtMoblile.getText());
			pstmt.setString(1, "0");
			pstmt.executeUpdate();
			showMsg("Payment Successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }

    @FXML
    void doClear(ActionEvent event) {
    	
    	txtMoblile.clear();
    	txtTotal.clear();

    }

    @FXML
    void doClose(ActionEvent event) {

    	Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

    Connection con;

    @FXML
    void initialize() {
    	con = DataBaseConnection.doConnect();
    	if(con==null)
  	   {System.out.println("not connected");
        //showMsgError("Not Connected!");
        }
     else
  	   {System.out.println("Connected");
     //showMsg("Connected");
     }
    	
 }
}
