package papersmaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class MasterPaperViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboNpp;

    @FXML
    private TextField txtCost;
    
    @FXML
    private HBox btnExit;
    
    PreparedStatement pstmt;
    
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			
    			alert.setTitle("done");
    			
    			alert.setHeaderText("Header:done");
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    
    void fillppr ()
    {
    	comboNpp.getItems().clear();
    	ArrayList<String> ppr = new ArrayList<String>();
    	try {
			pstmt=con.prepareStatement("select distinct paper from papers");
			ResultSet table = pstmt.executeQuery();
			while(table.next())
			{
				String nppr = table.getString("paper");
				ppr.add(String.valueOf(nppr));
			}
			comboNpp.getItems().addAll(ppr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void doFetch(ActionEvent event) {

    	try {
			pstmt = con.prepareStatement("select * from papers where paper=?");
			pstmt.setString(1, comboNpp.getEditor().getText());
			ResultSet table = pstmt.executeQuery();
			boolean jasoos= false;
			while(table.next()) {
				float cost = table.getFloat("price");
				txtCost.setText(String.valueOf(cost));
				jasoos=true;
			}
			
			if(jasoos==false)
				showMsg("INVALID NEWSPAPER");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doNew(ActionEvent event) {
    	comboNpp.getSelectionModel().clearSelection();
    	txtCost.clear();

    	
    }

    @FXML
    void doRemove(ActionEvent event) {
    	try {
    	pstmt=con.prepareStatement("delete from papers where paper=?");
    	pstmt.setString(1, comboNpp.getEditor().getText());
    	int count=pstmt.executeUpdate();
    	if(count==0)
    		showMsg("INVALID NAME OF NEWSPAPER");
    	else
    		showMsg(count + "data(s) deleted");
    	fillppr();
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	

    }

    @FXML
    void doSave(ActionEvent event) {
    	try {
    		pstmt=con.prepareStatement("insert into papers values(?,?)");
			pstmt.setString(1,comboNpp.getEditor().getText());
			pstmt.setFloat(2, Float.parseFloat(txtCost.getText()));
			int count=pstmt.executeUpdate();
			showMsg(count + "Data(s) Saved");
			fillppr ();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doUpdate(ActionEvent event) {
    	try {
    	pstmt=con.prepareStatement("update  papers set price= ? where paper = ?");
    	pstmt.setString(2, comboNpp.getEditor().getText());
    	pstmt.setFloat(1, Float.parseFloat(txtCost.getText()));
    	int count=pstmt.executeUpdate();
		showMsg(count + "Data(s) Updated");
		fillppr ();
		txtCost.clear();
    	
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}

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
       if(con==null)
    	   System.out.println("not connected");
       else
    	   System.out.println("Connected");
       fillppr();
    }
}
