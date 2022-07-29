package hawkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HawkersViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private TextField txtPath;

    @FXML
    private ImageView img;

    @FXML
    private TextField txtAdd;

    @FXML
    private TextField txtNo;

    @FXML
    private DatePicker dateJoin;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private TextField txtServe;
    
   @FXML
    private AnchorPane anchorpane;
    
   @FXML
   private HBox btnExit;
   
    PreparedStatement pstmt;
    
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.INFORMATION);
    			
    			alert.setTitle("done");
    			
    			alert.setHeaderText("Header:done");
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    
    void fillnameArea(){
    	comboName.getItems().clear();
    	comboArea.getItems().clear();
    	ArrayList<String> names = new ArrayList<String>();
    	
    	try {
			pstmt=con.prepareStatement("select distinct hname from hawkers");
			ResultSet table1 = pstmt.executeQuery();
			while(table1.next()) {
				String n = table1.getString("hname");
				names.add(String.valueOf(n));
			}
			comboName.getItems().addAll(names);
			
			ArrayList<String> areas = new ArrayList<String>(Arrays.asList("Ajit Road","Mall Road","Model town","Shant Nagar"));
			comboArea.getItems().addAll(areas);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	
    }
    
    String txt="";
    int count=0;
    @FXML
    void fillServe(ActionEvent event) {

    	String ar =comboArea.getSelectionModel().getSelectedItem();
    	if(count!=0) {
    		txt="";
    		count=0;
    	}
    	txt=txt+","+ar;
    	if(txt.startsWith(","))
    	   txt=txt.substring(1, txt.length());
    	txtServe.setText(txt);
    }


    @FXML
    void doBrowsePic(ActionEvent event) {
    	
    	final FileChooser fc = new FileChooser();
    	Stage stage = (Stage)anchorpane.getScene().getWindow();
    	File file = fc.showOpenDialog(stage);
    	if(file!=null) {
    		txtPath.setText(file.getAbsolutePath());
    		InputStream stream;
			try {
				stream = new FileInputStream(file.getAbsolutePath());
				Image image = new Image(stream);
	    	     img.setImage(image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	else
    		showMsg("select a path to continue");
    	

    	
    }

    @FXML
    void doDelete(ActionEvent event) {
    	try {
    		pstmt=con.prepareStatement("delete from hawkers where hname=?");
    		pstmt.setString(1, comboName.getEditor().getText());
        	int count=pstmt.executeUpdate();
        	if(count==0)
        		showMsg("NO HAWKER OF SUCH NAME EXISTS");
        	else
        		showMsg(count + "data(s) deleted");
        	fillnameArea();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	

    }

    @FXML
    void doNew(ActionEvent event) {
    	comboName.getEditor().clear();
    	txtPath.clear();
    	txtAdd.clear();
    	txtNo.clear();
    	dateJoin.getEditor().clear();
    	comboArea.getEditor().clear();
    	txtServe.clear();
    	img.setImage(null);
    	count=count+1;
    	
    	

    }

    @FXML
    void doSave(ActionEvent event) {

    	try {
    	pstmt=con.prepareStatement("insert into hawkers values( ?,?,?,?,?,?)");
    	pstmt.setString(1, comboName.getEditor().getText());
    	pstmt.setString(2, txtPath.getText());
    	pstmt.setString(3, txtAdd.getText());
    	pstmt.setString(4,txtNo.getText());
    	pstmt.setDate(5,Date.valueOf(dateJoin.getValue()));
    	pstmt.setString(6, txtServe.getText());
    	int count = pstmt.executeUpdate();
    	showMsg(count + "Data(s) Recorded");
    	fillnameArea();
    	count=count+1;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }

    @FXML
    void doSearch(ActionEvent event) {
    	try {
			pstmt = con.prepareStatement("select * from hawkers where hname=?");
			pstmt.setString(1, comboName.getSelectionModel().getSelectedItem());
			ResultSet table = pstmt.executeQuery();
			boolean jasoos= false;
			while(table.next()) {
				String path = table.getString("picpath");
				txtPath.setText(String.valueOf(path));
	    		InputStream stream;
				stream = new FileInputStream(path);
				Image image = new Image(stream);
		 	    img.setImage(image);
				String ad = table.getString("address");
				txtAdd.setText(String.valueOf(ad));
				String mob = table.getString("mobile");
				txtNo.setText(String.valueOf(mob));
				String date = table.getString("doj");
				dateJoin.setValue(LocalDate.parse(date));
				String area = table.getString("sareas");
				txtServe.setText(String.valueOf(area));
			    jasoos=true;
			}
			count=count+1;
			
			if(jasoos==false)
				showMsg("INVALID Hawker");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doUpdate(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("update hawkers set picpath=?,address=?,mobile=?,doj=?,sareas=? where hname=? ");
			pstmt.setString(6, comboName.getSelectionModel().getSelectedItem());
	    	pstmt.setString(1, txtPath.getText());
	    	pstmt.setString(2, txtAdd.getText());
	    	pstmt.setString(3,txtNo.getText());
	    	pstmt.setDate(4,Date.valueOf(dateJoin.getValue()));
	    	pstmt.setString(5, txtServe.getText());
	    	int count = pstmt.executeUpdate();
	    	showMsg(count + "Data(s) Updated");
	    	fillnameArea();
	    	count=count+1;
			
			
			
		} catch (SQLException e) {
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
       fillnameArea();

    }
}
