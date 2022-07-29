package bill;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;

public class BillViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtHawker;

    @FXML
    private TextField txtArea;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateBill;

    @FXML
    private DatePicker dateNoService;

    @FXML
    private TextField txtNoService;

    @FXML
    private TextField txtStatus;
    
    @FXML
    private TextField txtBill;

    PreparedStatement pstmt;
    
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
    
    String txt="";
    int count=0;
    
    @FXML
    void fillDates(ActionEvent event) {
    	
    	System.out.println(dateNoService.getValue());
		
		  Date ar = Date.valueOf(dateNoService.getValue()); 
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		  String strDate =dateFormat.format(ar); 
		  System.out.println(strDate);
		  if (count!=0)
				  {  txt="";
				  count=0;
				  }
		  txt=txt+","+strDate;
		  if(txt.startsWith(",")) 
			  txt=txt.substring(1, txt.length());
		  txtNoService.setText(txt);
		 
    }
    
    @FXML
    void doBill(MouseEvent event) {
    	
    	
    	try {
			pstmt= con.prepareStatement("update customers set totaldays = datediff(dos,dobill) - noservice where mobile = ?");
			pstmt.executeUpdate();
			pstmt=con.prepareStatement("select * from customers where mobile=?");
			pstmt.setString(1, txtContact.getText());
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	

    }
    @FXML
    void doNew(ActionEvent event) {
        txtArea.clear();
    	txtBill.clear();
    	txtContact.clear();
    	txtHawker.clear();
    	txtNoService.clear();
    	dateStart.getEditor().clear();
    	dateBill.getEditor().clear();
    	dateNoService.getEditor().clear();
    	txtStatus.setText("DUE");
    	
    }

    @FXML
    void doSave(ActionEvent event) {
    	
    	try {
        	pstmt=con.prepareStatement("insert into customers(dobill,noserive,bill,status) values( ?,?,?,?)");
        	pstmt.setDate(1,Date.valueOf(dateBill.getValue()));
        	pstmt.setString(2, txtNoService.getText());
        	pstmt.setFloat(3, Float.parseFloat(txtBill.getText()));
			txtArea.setText("Paid");
        	pstmt.setString(4, txtStatus.getText());
        	int count = pstmt.executeUpdate();
        	showMsg(count + "Data Recorded");
        	
        
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}

    }

    @FXML
    void doSearch(ActionEvent event) {
    	
    	TextInputDialog search = new TextInputDialog();
    	search.setTitle("Search Box");
    	search.setHeaderText("Enter Contact Number to Search");
    	search.setContentText("Mobile no. :");
    	Optional<String> mob = search.showAndWait();
    	if(mob.isPresent()) {
    		txtContact.setText(mob.get());
    		
    		
    	try {
    			pstmt = con.prepareStatement("select * from customers where mobile=?");
    			pstmt.setString(1, txtContact.getText());
    			ResultSet table = pstmt.executeQuery();
    			boolean jasoos= false;
    			while(table.next()) {
    				String ar = table.getString("area");
    				txtArea.setText(ar);
    				String h = table.getString("hawker");
    				txtHawker.setText(h);
    				String date = table.getString("dos");
    				dateStart.setValue(LocalDate.parse(date));
    			    jasoos=true;
    			}
    			
    			
    			if(jasoos==false)
    				showMsgError("INVALID mobile no");
    			
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    	}

    }

    @FXML
    void doUpdate(ActionEvent event) {
    	

    	try {
			pstmt=con.prepareStatement("update customers set dobill=?,noservice=?,bill=?,status=? where mobile=? ");
			pstmt.setString(5, txtContact.getText());
	    	pstmt.setDate(1,Date.valueOf(dateBill.getValue()));
	    	pstmt.setString(2, txtNoService.getText());
	    	pstmt.setFloat(3,Float.parseFloat(txtBill.getText()));
	    	pstmt.setString(2, txtStatus.getText());
	    	int count = pstmt.executeUpdate();
	    	showMsg(count + "Data Updated");
	    	
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		}


    }
    
    Connection con;

    @FXML
    void initialize() {
    	con = DataBaseConnection.doConnect();
    	if(con==null)
  	   {System.out.println("not connected");
        showMsgError("Not Connected!");}
     else
  	   {System.out.println("Connected");
     showMsg("Connected");}
    		
        

    }
}
