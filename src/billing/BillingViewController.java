package billing;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bill.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BillingViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStrDate;

    @FXML
    private DatePicker dateBill;

    @FXML
    private TextField txtSkip;

    @FXML
    private TextField txtAmout;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSave;
    
    @FXML
    private TextField txtDaysTotal;
    
    @FXML
    private HBox btnExit;
    
PreparedStatement pstmt;

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
    
    void fillCont()
    {
    	comboMob.getItems().clear();
    	
    	ArrayList<String> mob = new ArrayList<String>();
    	
    	try {
			pstmt=con.prepareStatement("select distinct mobile from customers where status=0");
			ResultSet table1 = pstmt.executeQuery();
			while(table1.next()) {
				String n = table1.getString("mobile");
				mob.add(String.valueOf(n));}
			comboMob.getItems().addAll(mob);
    }
    	
catch (Exception e) {
			
			e.printStackTrace();
		}
    }
    @FXML
    void doCalDays(ActionEvent event) {
    	
    	int skip = Integer.parseInt(txtSkip.getText());
    	String mob= comboMob.getSelectionModel().getSelectedItem();
    	try {
			pstmt=con.prepareStatement("update customers set dobill=?,noservice=? where mobile=?");
			pstmt.setDate(1,Date.valueOf(dateBill.getValue()));
			pstmt.setInt(2,skip);
			pstmt.setString(3, mob);
			pstmt.executeUpdate();
			PreparedStatement pstmt1=con.prepareStatement("update customers set totaldays = datediff(dobill,dos) - noservice,cost=? where mobile = ?");
			pstmt1.setString(2, mob);
			pstmt1.setFloat(1,Float.parseFloat(txtPrice.getText()));
			pstmt1.executeUpdate();
			PreparedStatement pstmt2 = con.prepareStatement("select * from customers where mobile=?");
			pstmt2.setString(1, comboMob.getSelectionModel().getSelectedItem());
			ResultSet table = pstmt2.executeQuery();
			table.next();
			int total = table.getInt("totaldays");
			txtDaysTotal.setText(String.valueOf(total));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }

    @FXML
    void doFetch(ActionEvent event) {
    	
    	String selno = comboMob.getSelectionModel().getSelectedItem();
    	try {
			pstmt= con.prepareStatement("select * from customers where mobile=?");
			pstmt.setString(1, selno);
			ResultSet table = pstmt.executeQuery();
			while(table.next()) {
			String[] ary1 = table.getString("totalprice").split(",");
			float sum=0.0f;
			for (String string : ary1) {
				float cost = Float.parseFloat(string);
				 sum=sum+cost;
			}
			txtPrice.setText(String.valueOf(sum));
			String date = table.getString("dos");
			txtStrDate.setText(date);}
		
	     } catch (SQLException e) {
			e.printStackTrace();
		}
     }

    @FXML
    void doGenerate(ActionEvent event) {
    	
    	
			txtAmout.setText(String.valueOf(((Float.parseFloat(txtPrice.getText()))*Integer.parseInt(txtDaysTotal.getText()))));
			
		
    	}
    
    @FXML
    void doClose(ActionEvent event) {

    	Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void doSave(ActionEvent event) {
    	try {
    	pstmt=con.prepareStatement("update customers set bill=?,status=?,dos=? where mobile=?");
		pstmt.setString(4,comboMob.getSelectionModel().getSelectedItem());
		pstmt.setFloat(1,((Float.parseFloat(txtPrice.getText()))*Integer.parseInt(txtDaysTotal.getText())));
		pstmt.setString(2,"1");
		pstmt.setDate(3,Date.valueOf(dateBill.getValue().plusDays(1)));
		pstmt.executeUpdate();
		
    	showMsg("SAVED");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}

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
    // showMsg("Connected");
     }
    	fillCont();
 }
}
