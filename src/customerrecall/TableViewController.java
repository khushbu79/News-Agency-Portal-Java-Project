package customerrecall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import hawkertable.UserBean1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<UserBean> tbl;

    @FXML
    private HBox btnExit;

    @FXML
    private RadioButton radPaid;

    @FXML
    private RadioButton rsdNoPaid;

    @FXML
    private ComboBox<String> comboArea;
    
    List<UserBean> details = new ArrayList<UserBean>();
    
    
 void fillArea(){
    	
    	comboArea.getItems().clear();
        ArrayList<String> areas = new ArrayList<String>(Arrays.asList("Ajit Road","Mall Road","Model town","Shant Nagar"));
	    comboArea.getItems().addAll(areas);
 }
    

    @FXML
    void doClear(ActionEvent event) {
    	
    	tbl.getColumns().clear();
    	tbl.getItems().clear();
    	comboArea.getSelectionModel().clearSelection();
    	radPaid.setSelected(false);
    	rsdNoPaid.setSelected(false);
        
    }

    @FXML
    void doExcel(ActionEvent event) {
    	//details.clear();
    	
    	String[] columns = {"Customer name","Mobile Number","Address","Area","Hawker","Selected paper","Date of Start","Total Payable"} ;
    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet = workbook.createSheet("Customer Details");
    	Font headerfont = workbook.createFont();
    	headerfont.setBold(true);
    	headerfont.setFontHeightInPoints((short) 17 );
    	headerfont.setColor(IndexedColors.RED.getIndex());
    	System.out.println("!!!!!!");
    	
    	CellStyle headerCellStyle= workbook.createCellStyle();
    	headerCellStyle.setFont(headerfont);
    	
    	Row headerRow = sheet.createRow(0);
    	
    	for (int i = 0; i < columns.length; i++) {
    		Cell cell = headerRow.createCell(i);
    		cell.setCellValue(columns[i]);
    		cell.setCellStyle(headerCellStyle);
    		System.out.println("***********");
			
		}
    	
    	int rowNum =1;
    	
    	for(UserBean str  : details) {
    		Row row = sheet.createRow(rowNum++);
    		row.createCell(0).setCellValue(str.cname);
    		row.createCell(1).setCellValue(str.mobile);
    		row.createCell(2).setCellValue(str.address);
    		row.createCell(3).setCellValue(str.area);
    		row.createCell(4).setCellValue(str.hawker);
    		row.createCell(5).setCellValue(str.selpaper);
    		row.createCell(6).setCellValue(str.dos.toString());
    		System.out.println(str.dos);
    		row.createCell(7).setCellValue(str.bill);
    		System.out.println("###########");
    	}
    	
    	for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
			System.out.println("&&&&&&&&&");
		}
    	
    	FileOutputStream fileout;
    	FileChooser excelfile = new FileChooser();
    	excelfile.setTitle("Save");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
    	File initial = new File("C:\\Users\\khush\\eclipse-workspace\\newspaperagency");
    	excelfile.setInitialDirectory(initial);
        excelfile.getExtensionFilters().add(extFilter);
    	
    	Stage stage = new Stage();
    	File file = excelfile.showSaveDialog(stage);

        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//		try {
//			fileout = new FileOutputStream("customers.xlsx");
//			workbook.write(fileout);
//			fileout.close();
//			workbook.close();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	

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
    void doFind(ActionEvent event) {
    	TableColumn<UserBean, String>name=new TableColumn<UserBean, String>("Customer Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<UserBean,String>("cname"));
    	name.setMinWidth(100);
    	
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile Number");
    	mob.setCellValueFactory(new PropertyValueFactory<UserBean,String>("mobile"));//same as field name in bean
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean, String> add=new TableColumn<UserBean, String>("Address");
    	add.setCellValueFactory(new PropertyValueFactory<UserBean,String>("address"));//same as field name in bean
    	add.setMinWidth(100);
    	
    	TableColumn<UserBean, String> h=new TableColumn<UserBean, String>("Hawker");
    	h.setCellValueFactory(new PropertyValueFactory<UserBean,String>("hawker"));//same as field name in bean
    	h.setMinWidth(100);
    	
    	TableColumn<UserBean, String> p=new TableColumn<UserBean, String>("Selected paper");
    	p.setCellValueFactory(new PropertyValueFactory<UserBean,String>("selpaper"));//same as field name in bean
    	p.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> date=new TableColumn<UserBean, Date>("Date of Start");
    	date.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("dos"));//same as field name in bean
    	date.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> cost=new TableColumn<UserBean, Date>("Total Payable");
    	cost.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("bill"));//same as field name in bean
    	cost.setMinWidth(100);
    	tbl.getColumns().clear();
    	tbl.getItems().clear();
    	
    	
    	tbl.getColumns().addAll(name,mob,add,h,p,date,cost);
    	
    	ObservableList<UserBean> table=showselect();
    	System.out.println(table);
    	tbl.setItems(table);//fill array in table

    }
    
  

    @FXML
    void doNotPaid(ActionEvent event) {
    	TableColumn<UserBean, String>name=new TableColumn<UserBean, String>("Customer Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<UserBean,String>("cname"));
    	name.setMinWidth(100);
    	
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile Number");
    	mob.setCellValueFactory(new PropertyValueFactory<UserBean,String>("mobile"));//same as field name in bean
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean, String> add=new TableColumn<UserBean, String>("Address");
    	add.setCellValueFactory(new PropertyValueFactory<UserBean,String>("address"));//same as field name in bean
    	add.setMinWidth(100);
    	
    	TableColumn<UserBean, String> h=new TableColumn<UserBean, String>("Hawker");
    	h.setCellValueFactory(new PropertyValueFactory<UserBean,String>("hawker"));//same as field name in bean
    	h.setMinWidth(100);
    	

    	TableColumn<UserBean, String> a=new TableColumn<UserBean, String>("Area");
    	a.setCellValueFactory(new PropertyValueFactory<UserBean,String>("area"));//same as field name in bean
    	a.setMinWidth(100);
    	
    	TableColumn<UserBean, String> p=new TableColumn<UserBean, String>("Selected paper");
    	p.setCellValueFactory(new PropertyValueFactory<UserBean,String>("selpaper"));//same as field name in bean
    	p.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> date=new TableColumn<UserBean, Date>("Date of Start");
    	date.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("dos"));//same as field name in bean
    	date.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> cost=new TableColumn<UserBean, Date>("Total Payable");
    	cost.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("bill"));//same as field name in bean
    	cost.setMinWidth(100);
    	tbl.getColumns().clear();
    	tbl.getItems().clear();
    	
    	tbl.getColumns().addAll(name,mob,add,h,a,p,date,cost);
    	pay="not";
    	
    	

    }
    

    @FXML
    void doPaid(ActionEvent event) {
    	TableColumn<UserBean, String>name=new TableColumn<UserBean, String>("Customer Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<UserBean,String>("cname"));
    	name.setMinWidth(100);
    	
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile Number");
    	mob.setCellValueFactory(new PropertyValueFactory<UserBean,String>("mobile"));//same as field name in bean
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean, String> add=new TableColumn<UserBean, String>("Address");
    	add.setCellValueFactory(new PropertyValueFactory<UserBean,String>("address"));//same as field name in bean
    	add.setMinWidth(100);
    	
    	TableColumn<UserBean, String> h=new TableColumn<UserBean, String>("Hawker");
    	h.setCellValueFactory(new PropertyValueFactory<UserBean,String>("hawker"));//same as field name in bean
    	h.setMinWidth(100);
    	

    	TableColumn<UserBean, String> a=new TableColumn<UserBean, String>("Area");
    	a.setCellValueFactory(new PropertyValueFactory<UserBean,String>("area"));//same as field name in bean
    	a.setMinWidth(100);
    	
    	TableColumn<UserBean, String> p=new TableColumn<UserBean, String>("Selected paper");
    	p.setCellValueFactory(new PropertyValueFactory<UserBean,String>("selpaper"));//same as field name in bean
    	p.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> date=new TableColumn<UserBean, Date>("Date of Start");
    	date.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("dos"));//same as field name in bean
    	date.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> cost=new TableColumn<UserBean, Date>("Total Payable");
    	cost.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("bill"));//same as field name in bean
    	cost.setMinWidth(100);
    	tbl.getColumns().clear();
    	tbl.getItems().clear();
    	tbl.getColumns().addAll(name,mob,add,h,a,p,date,cost);
    	pay="paid";
    	
    	
    	

    }
    
    String pay="";
    
ObservableList<UserBean> notpaid() {
    	
    	ObservableList<UserBean> aryList=FXCollections.observableArrayList();
    	try {
    		
         PreparedStatement	pstmt1=con.prepareStatement("select * from customers where status=1  ");
  		
  		
			ResultSet table1= pstmt1.executeQuery();
			//table is basically array of Objects
			
			while(table1.next())//moves the cursor in next row if no row found
			{
				
				String cname1=table1.getString("cname");
				String mobile1=table1.getString("mobile");
			    String area1=table1.getString("area"); 
				String address1=table1.getString("address");
				String hawker1=table1.getString("hawker");
				String selpaper1=table1.getString("selpaper");
				Date dos1=table1.getDate("dos");
				float bill1=table1.getFloat("bill");
				
				System.out.println(cname1+"  "+mobile1+"  "+address1+"  "+hawker1+"  "+selpaper1+"  "+dos1+"  "+bill1+"  ");
		        UserBean obj2 = new UserBean(cname1, mobile1, address1, area1, hawker1, dos1, selpaper1, bill1);
				aryList.add(obj2);
				
				details.add(obj2);
			}
    	}
			catch(Exception e) {e.printStackTrace();}
		
    	return aryList;
    	
    }

    @FXML
    void doShow(ActionEvent event) {
    	
    	TableColumn<UserBean, String>name=new TableColumn<UserBean, String>("Customer Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<UserBean,String>("cname"));
    	name.setMinWidth(100);
    	
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile Number");
    	mob.setCellValueFactory(new PropertyValueFactory<UserBean,String>("mobile"));//same as field name in bean
    	mob.setMinWidth(100);
    	
    	TableColumn<UserBean, String> add=new TableColumn<UserBean, String>("Address");
    	add.setCellValueFactory(new PropertyValueFactory<UserBean,String>("address"));//same as field name in bean
    	add.setMinWidth(100);
    	
    	TableColumn<UserBean, String> h=new TableColumn<UserBean, String>("Hawker");
    	h.setCellValueFactory(new PropertyValueFactory<UserBean,String>("hawker"));//same as field name in bean
    	h.setMinWidth(100);
    	

    	TableColumn<UserBean, String> a=new TableColumn<UserBean, String>("Area");
    	a.setCellValueFactory(new PropertyValueFactory<UserBean,String>("area"));//same as field name in bean
    	a.setMinWidth(100);
    	
    	TableColumn<UserBean, String> p=new TableColumn<UserBean, String>("Selected paper");
    	p.setCellValueFactory(new PropertyValueFactory<UserBean,String>("selpaper"));//same as field name in bean
    	p.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> date=new TableColumn<UserBean, Date>("Date of Start");
    	date.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("dos"));//same as field name in bean
    	date.setMinWidth(100);
    	
    	TableColumn<UserBean, Date> cost=new TableColumn<UserBean, Date>("Total Payable");
    	cost.setCellValueFactory(new PropertyValueFactory<UserBean,Date>("bill"));//same as field name in bean
    	cost.setMinWidth(100);
    	tbl.getColumns().clear();
    	tbl.getItems().clear();
    	
    	tbl.getColumns().addAll(name,mob,add,h,a,p,date,cost);
    	
    	ObservableList<UserBean> table=showall();
    	System.out.println(table);
    	tbl.setItems(table);//fill array in table

    }
    
    ObservableList<UserBean> showselect() {
    	ObservableList<UserBean> aryList=FXCollections.observableArrayList();
    	try{
   		 PreparedStatement	pstmt=con.prepareStatement("select * from customers where area LIKE ?");
   		 String area = comboArea.getSelectionModel().getSelectedItem();
   		 pstmt.setString(1,"%"+area+"%");
   			ResultSet table= pstmt.executeQuery();
   			//table is basically array of Objects
   			
   			while(table.next())//moves the cursor in next row if no row found
   			{
   				
   				String cname=table.getString("cname");
   				String mobile=table.getString("mobile");			
   				String address=table.getString("address");
   				String hawker=table.getString("hawker");
   				String selpaper=table.getString("selpaper");
   				Date dos=table.getDate("dos");
   				float bill=table.getFloat("bill");
   				
   				System.out.println(cname+"  "+mobile+"  "+address+"  "+hawker+"  "+selpaper+"  "+dos+"  "+bill+"  ");
   		        UserBean obj1 = new UserBean(cname, mobile, address, area, hawker, dos, selpaper, bill);
   				aryList.add(obj1);
   				
				details.add(obj1);
   			}
   				
   				
   		}
   		catch(Exception exp)
   		{
   			exp.printStackTrace();
   		}
   		return aryList;
    }
ObservableList<UserBean> paid() {
    	
    	ObservableList<UserBean> aryList=FXCollections.observableArrayList();
    	try {
    		
         PreparedStatement	pstmt1=con.prepareStatement("select * from customers where status=0  ");
  		
  		
			ResultSet table1= pstmt1.executeQuery();
			//table is basically array of Objects
			
			while(table1.next())//moves the cursor in next row if no row found
			{
				
				String cname1=table1.getString("cname");
				String mobile1=table1.getString("mobile");
			    String area1=table1.getString("area"); 
				String address1=table1.getString("address");
				String hawker1=table1.getString("hawker");
				String selpaper1=table1.getString("selpaper");
				Date dos1=table1.getDate("dos");
				float bill1=table1.getFloat("bill");
				
				System.out.println(cname1+"  "+mobile1+"  "+address1+"  "+hawker1+"  "+selpaper1+"  "+dos1+"  "+bill1+"  ");
		        UserBean obj2 = new UserBean(cname1, mobile1, address1, area1, hawker1, dos1, selpaper1, bill1);
				aryList.add(obj2);
				
				details.add(obj2);
			}
    	}
			catch(Exception e) {e.printStackTrace();}
		
    	return aryList;
    	
    }
    
    ObservableList<UserBean> showall(){
    	ObservableList<UserBean> aryList=FXCollections.observableArrayList();
    	try {
    		
         PreparedStatement	pstmt1=con.prepareStatement("select * from customers ");
  		
  		
			ResultSet table1= pstmt1.executeQuery();
			//table is basically array of Objects
			
			while(table1.next())//moves the cursor in next row if no row found
			{
				
				String cname1=table1.getString("cname");
				String mobile1=table1.getString("mobile");
			    String area1=table1.getString("area"); 
				String address1=table1.getString("address");
				String hawker1=table1.getString("hawker");
				String selpaper1=table1.getString("selpaper");
				Date dos1=table1.getDate("dos");
				float bill1=table1.getFloat("bill");
				
				System.out.println(cname1+"  "+mobile1+"  "+address1+"  "+hawker1+"  "+selpaper1+"  "+dos1+"  "+bill1+"  ");
		        UserBean obj2 = new UserBean(cname1, mobile1, address1, area1, hawker1, dos1, selpaper1, bill1);
				aryList.add(obj2);
				
				details.add(obj2);
			}
    	}
			catch(Exception e) {e.printStackTrace();}
		
    	return aryList;
	}
    
    @FXML
    void doshowP(ActionEvent event) {
    	
    	if(pay.equals("paid")) {
    		rsdNoPaid.setSelected(false);
    		ObservableList<UserBean> table=paid();
        	System.out.println(table);
        	tbl.setItems(table);//fill array in table
    	}
    	if(pay.equals("not")) {
    		radPaid.setSelected(false);
    		ObservableList<UserBean> table=notpaid();
        	System.out.println(table);
        	tbl.setItems(table);//fill array in table
    	}

    }

    
    

     Connection con;
    @FXML
    void initialize() {
    	
    	con=DataBaseConnection.doConnect();
    	fillArea();
        assert tbl != null : "fx:id=\"tbl\" was not injected: check your FXML file 'TableView.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'TableView.fxml'.";
        assert radPaid != null : "fx:id=\"radPaid\" was not injected: check your FXML file 'TableView.fxml'.";
        assert rsdNoPaid != null : "fx:id=\"rsdNoPaid\" was not injected: check your FXML file 'TableView.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'TableView.fxml'.";

    }
}
