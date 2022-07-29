package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashBoardViewController {
	
	@FXML
    private VBox pm;
	
	 @FXML
	 private HBox btnLogout;

     @FXML
	 private VBox btnHawker;

    @FXML
    private VBox btnCust;

     @FXML
     private VBox btngen;

	 @FXML
	 private VBox btnColl;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private HBox npRec;

    @FXML
    private HBox hawkRec;
    @FXML
    private HBox btnDev;
    
    @FXML
    private HBox btnRecCust;
    
    @FXML
    private VBox btnProg;
    
    @FXML
    void doprog(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("prog/ProgView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnProg.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }
    
    @FXML
    void doRecCust(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerrecall/TableView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnRecCust.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }
    
    @FXML
    void dodev(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dev/DevView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnDev.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }


    @FXML
    void doBillGen(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billing/BillingView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btngen.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }

    @FXML
    void doCollector(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billcollector/BillCollView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnColl.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }

    @FXML
    void doCust(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerreg/CustomerView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnCust.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}
    	
    	

    }

    @FXML
    void doHawker(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawkers/HawkersView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnHawker.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }

    @FXML
    void doLogout(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("password/pwdView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)btnLogout.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    	
    	

    }

    @FXML
    void doPapers(MouseEvent event) {
    	if(event.getClickCount()==1) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("papersmaster/MasterPaperView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
    		
			//to hide the opened window
			
			   Scene scene1=(Scene)pm.getScene();
			   scene1.getWindow().hide();
			 
    	}

		
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	}

    }
    
    @FXML
    void dohawkList(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawkertable/TableView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)hawkRec.getScene();
    			   scene1.getWindow().hide();
    			 
        	}

    		
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	}

    }

    @FXML
    void donpList(MouseEvent event) {
    	
    	if(event.getClickCount()==1) {
        	try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("paperstable/TableView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
        		
        		
    			//to hide the opened window
    			
    			   Scene scene1=(Scene)npRec.getScene();
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

    }
}
