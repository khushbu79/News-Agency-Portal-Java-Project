package customerreg;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import hawkers.DataBaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;


public class CustomerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtAddr;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboHawker;

    @FXML
    private ListView<String> listPaper;

    @FXML
    private ListView<String> listSelP;

    @FXML
    private ListView<String> listPrice;
    
    @FXML
    private DatePicker dateStart;
    
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
    
    void fillAreaHawk(){
    	comboArea.getItems().clear();
    	
    	
    	ArrayList<String> areas = new ArrayList<String>(Arrays.asList("Ajit Road","Mall Road","Model town","Shant Nagar"));
		comboArea.getItems().addAll(areas);

    }
    
    
    @FXML
    void getHawkers(ActionEvent event) {
    	//comboHawker.getItems().clear();
    	ArrayList<String> hnames = new ArrayList<String>(); 
		 
		try {
			comboHawker.getItems().clear();
			pstmt=con.prepareStatement("select hname from hawkers where sareas LIKE ?");
			pstmt.setString(1, "%"+comboArea.getSelectionModel().getSelectedItem()+"%");
			ResultSet table = pstmt.executeQuery();
			while(table.next()) {
				String name = table.getString("hname");
				hnames.add(name);
				//comboHawker.getItems().add(table.getString("hname"));
				
			}
			comboHawker.getItems().addAll(hnames);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
    void addpaperscost()
    {
    	
    	ArrayList<String> paper = new ArrayList<String> ();
    	
    	try {
    		
    		// filled papers from database
			pstmt=con.prepareStatement("select paper from papers");
			ResultSet table = pstmt.executeQuery();
			while(table.next()) {
				String p = table.getString("paper");
				paper.add(p);
				//listPaper.getItems().add(table.getString("paper"));
			}
			 listPaper.getItems().addAll(paper);
	
		   	 
		} catch (SQLException e) {
			e.printStackTrace();
		}
       
    }

    @FXML
    void getPapers(MouseEvent event) {
    	try {
    		
    		String selp=listPaper.getSelectionModel().getSelectedItem();
      	    
    	if(event.getClickCount()==2) {
    	
    	//listSelP.getItems().clear();
  	    //listPrice.getItems().clear();
  	   // String selp=listPaper.getSelectionModel().getSelectedItem();
  	    //int index = listPaper.getSelectionModel().getSelectedIndex();
  	    
  	    	
			 pstmt=con.prepareStatement("select price from papers where paper = ?");
			 pstmt.setString(1, selp);
			ResultSet table1 = pstmt.executeQuery();
				 table1.next();
				 String p = table1.getString("price");
			     listSelP.getItems().addAll(selp);
			     listPrice.getItems().add(p);
	         }
    	}
		
  	    catch(Exception e)
  	    {
  	    	e.printStackTrace();
  	    }
    	
    }

    
 
    @FXML
    void doClear(ActionEvent event) {
    	comboHawker.getEditor().clear();
    	txtAddr.clear();
    	txtName.clear();
    	txtContact.clear();
    	dateStart.getEditor().clear();
    	listSelP.getItems().clear();
    	listPrice.getItems().clear();
    	comboArea.getSelectionModel().clearSelection();
    	comboHawker.getSelectionModel().clearSelection();
    	listPaper.getSelectionModel().clearSelection();

    	
    }

    @FXML
    void doDel(ActionEvent event) {
    	
    	try {
    		pstmt=con.prepareStatement("delete from customers where mobile=?");
    		pstmt.setString(1, txtContact.getText());
        	int count=pstmt.executeUpdate();
        	showMsg(count + "data(s) deleted");
        	doClear(event);
        		
        	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	

    }

    @FXML
    void doSave(ActionEvent event) {
    	
    	try {
        	pstmt=con.prepareStatement("insert into customers values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, txtName.getText());
        	pstmt.setString(2, txtContact.getText());
        	pstmt.setString(3, txtAddr.getText());
        	pstmt.setString(4,comboArea.getSelectionModel().getSelectedItem());
        	pstmt.setString(5, comboHawker.getSelectionModel().getSelectedItem());
        	String p = String.join(",", listSelP.getItems());
        	pstmt.setString(6,p);
        	String t = String.join(",", listPrice.getItems());
        	pstmt.setString(7, t );
        	pstmt.setFloat(8, 0);
        	pstmt.setDate(9,Date.valueOf(dateStart.getValue()));
        	pstmt.setDate(10, null);
        	pstmt.setInt(11, 0);
        	pstmt.setInt(12, 0);
        	pstmt.setFloat(13, 0);
        	pstmt.setString(14, "0");
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
    		
    	}
    	
    	try {
			pstmt = con.prepareStatement("select * from customers where mobile=?");
			pstmt.setString(1, txtContact.getText());
			ResultSet table = pstmt.executeQuery();
			boolean jasoos= false;
			while(table.next()) {
				String name = table.getString("cname");
				txtName.setText(String.valueOf(name));
				String ad = table.getString("address");
				txtAddr.setText(String.valueOf(ad));
				String ar = table.getString("area");
				comboArea.setValue(ar);
				String h = table.getString("hawker");
				comboHawker.setValue(h);
				String date = table.getString("dos");
				dateStart.setValue(LocalDate.parse(date));
				String[] ary = table.getString("selpaper").split(",");
				for (String string : ary) {
					listSelP.getItems().add(string);
				}
				String[] ary1 = table.getString("totalprice").split(",");
				for (String string : ary1) {
					listPrice.getItems().add(string);
				}
		
			    jasoos=true;
			}
			
			
			if(jasoos==false)
				showMsgError("INVALID mobile no");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    }

    @FXML
    void doUpdate(ActionEvent event) {
    	
    	try {
			pstmt=con.prepareStatement("update customers set cname=?,address=?,area=?,hawker=?,selpaper=?,totalprice=?,dos=?,bill=?,status=? where mobile=? ");
			pstmt.setString(10, txtContact.getText());
	    	pstmt.setString(1, txtName.getText());
	    	pstmt.setString(2, txtAddr.getText());
	    	pstmt.setString(3,comboArea.getSelectionModel().getSelectedItem());
	    	pstmt.setString(4,comboHawker.getSelectionModel().getSelectedItem());
	    	String p = String.join(",", listSelP.getItems());
	    	pstmt.setString(5,p);
	    	String t = String.join(",", listPrice.getItems());
        	pstmt.setString(6, t );
	    	pstmt.setDate(7,Date.valueOf(dateStart.getValue()));
	    	pstmt.setInt(8, 0);
        	pstmt.setInt(9, 0);
	    	int count = pstmt.executeUpdate();
	    	showMsg(count + "Data(s) Updated");
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
    
    Connection con;
    @FXML
    void initialize() {
    	con=DataBaseConnection.doConnect();
        if(con==null)
     	   {System.out.println("not connected");
          // showMsgError("Not Connected!");
           }
        else
     	   {System.out.println("Connected");
        // showMsg("Connected");
        }
        fillAreaHawk();
        
        listPaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listSelP.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addpaperscost();

    }
}
