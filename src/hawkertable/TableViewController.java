package hawkertable;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import paperstable.DataBaseConnection;
import paperstable.UserBean;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<UserBean1> tbl;

    @FXML
    private HBox btnExit;

    @FXML
    private ComboBox<String> comboArea;
    
    void fillArea(){
    	
    	comboArea.getItems().clear();
        ArrayList<String> areas = new ArrayList<String>(Arrays.asList("Ajit Road","Mall Road","Model town","Shant Nagar"));
	    comboArea.getItems().addAll(areas);
 }
    

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
    void doFliter(ActionEvent event) {
    	
    	TableColumn<UserBean1, String>name=new TableColumn<UserBean1, String>("Hawker Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<UserBean1,String>("hname"));
    	name.setMinWidth(100);
    	
    	TableColumn<UserBean1, String> add=new TableColumn<UserBean1, String>("Address");
    	add.setCellValueFactory(new PropertyValueFactory<UserBean1,String>("address"));//same as field name in bean
    	add.setMinWidth(100);
    	
    	TableColumn<UserBean1, String> mob=new TableColumn<UserBean1, String>("Mobile Number");
    	mob.setCellValueFactory(new PropertyValueFactory<UserBean1,String>("mobile"));//same as field name in bean
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean1, Date> date=new TableColumn<UserBean1, Date>("Date of Joining");
    	date.setCellValueFactory(new PropertyValueFactory<UserBean1,Date>("doj"));//same as field name in bean
    	date.setMinWidth(100);
    	
    	tbl.getColumns().addAll(name,add,mob,date);

    }

    @FXML
    void doShow(ActionEvent event) {
    	

    	ObservableList<UserBean1> table=showAll();
    	System.out.println(table);
    	tbl.setItems(table);//fill array in table

    }
    
    ObservableList<UserBean1> showAll() {
    	ObservableList<UserBean1> aryList=FXCollections.observableArrayList();
    	try{
   		 PreparedStatement	pstmt=con.prepareStatement("select * from hawkers where sareas LIKE ?");
   		 pstmt.setString(1,"%"+comboArea.getSelectionModel().getSelectedItem()+"%");
   			ResultSet table= pstmt.executeQuery();
   			//table is basically array of Objects
   			
   			while(table.next())//moves the cursor in next row if no row found
   			{
   				
   				String hname=table.getString("hname");
   				String address=table.getString("address");			
   				String mobile=table.getString("mobile");
   				Date doj=table.getDate("doj");
   				
   				System.out.println(hname+"  "+address+"  "+mobile+"  "+doj+"  ");
   				UserBean1 obj1= new UserBean1(hname, address, mobile, doj);
   				aryList.add(obj1);
   			}
   		}
   		catch(Exception exp)
   		{
   			exp.printStackTrace();
   		}
   		return aryList;
    }
    
    @FXML
    void doClear(ActionEvent event) {
    	
    	comboArea.getSelectionModel().clearSelection();
    	tbl.getItems().clear();
    	tbl.getColumns().clear();

    }
    

    Connection con;
    @FXML
    void initialize() {
    	con=DataBaseConnection.doConnect();
    	fillArea();
        assert tbl != null : "fx:id=\"tbl\" was not injected: check your FXML file 'TableView.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'TableView.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'TableView.fxml'.";

    }
}
