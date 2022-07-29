package paperstable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnShow;

    @FXML
    private TableView<UserBean> tbl;
    
    @FXML
    private HBox btnExit;

    @FXML
    void doShow(ActionEvent event) {
    	
    	TableColumn<UserBean, String>p=new TableColumn<UserBean, String>("Newspaper");//any thing
    	p.setCellValueFactory(new PropertyValueFactory<UserBean,String>("paper"));
    	p.setMinWidth(100);
    	
    	TableColumn<UserBean, Float> cost=new TableColumn<UserBean, Float>("Price");
    	cost.setCellValueFactory(new PropertyValueFactory<UserBean,Float>("price"));//same as field name in bean
    	cost.setMinWidth(100);
    	
    	tbl.getColumns().addAll(p,cost);
    	
    	ObservableList<UserBean> table=showAll();
    	System.out.println(table);
    	tbl.setItems(table);//fill array in table

    }
    
    ObservableList<UserBean> showAll() {
    	ObservableList<UserBean> aryList=FXCollections.observableArrayList();
    	try{
   		 PreparedStatement	pstmt=con.prepareStatement("select * from papers");
   			ResultSet table= pstmt.executeQuery();
   			//table is basically array of Objects
   			
   			while(table.next())//moves the cursor in next row if no row found
   			{
   				
   				String paper=table.getString("paper");
   				float price=table.getFloat("price");
   				
   				System.out.println(paper+"  "+price+"  ");
   				UserBean obj= new UserBean(paper, price);
   				aryList.add(obj);
   			}
   		}
   		catch(Exception exp)
   		{
   			exp.printStackTrace();
   		}
   		return aryList;
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
    

    Connection con;
    @FXML
    void initialize() {
    	 	con=DataBaseConnection.doConnect();
        assert btnShow != null : "fx:id=\"btnShow\" was not injected: check your FXML file 'TableView.fxml'.";
        assert tbl != null : "fx:id=\"tbl\" was not injected: check your FXML file 'TableView.fxml'.";

    }
}

/*1. create connection
2. create bean class
3. fetch and create array of objects of ObservableList and return it
4. add Columns in table - see line no. 37
5. call the function
6. and fill data in table
*/
