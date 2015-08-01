package haltckt;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.h2.tools.Backup;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;



import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nitido.utils.toaster.Toaster;

import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HallTkt extends JFrame implements ActionListener, MouseListener {

	/**
	 * @param args
	 */
	Dimension dim;
	ImageIcon view_img;
	ImageIcon ic = null;
	
	JLabel roll = new JLabel("Roll No.");
	JLabel reg = new JLabel("Reg No.");
	JLabel name = new JLabel("Name");
	JLabel fath_name = new JLabel("Father's Name");
	JLabel addr_1 = new JLabel("Address Line 1");
	JLabel addr_2 = new JLabel("Address Line 2");
	JLabel city = new JLabel("City");
	JLabel patasala = new JLabel("Patasala Name");
	JLabel vedam = new JLabel("Vedam");
	JLabel part = new JLabel("Part");
	JLabel saka = new JLabel("Sakha");
	//JLabel sub_saka = new JLabel("Sub Sakha");
	JLabel photo = new JLabel("Photo");
	JLabel for_img = new JLabel();
	
	JLabel exam_ti = new JLabel("Exam Title");
	JLabel ex_dat = new JLabel("Date of Exam");
	JLabel place = new JLabel("Place of Exam");
	JLabel centerTimeToReach = new JLabel("Exam Start Time");
	JLabel cert_dist = new JLabel("Certificate distribution day");
	JLabel percent = new JLabel("Percentage");
	
	JTextField roll_no = new JTextField();
	JTextField reg_no = new JTextField();
	JTextField cand_name = new JTextField();
	JTextField cand_fath_name = new JTextField();
	JTextField addr_line_1 = new JTextField();
	JTextField addr_line_2 = new JTextField();
	JTextField city_town = new JTextField();
	JTextField patas = new JTextField();
	JTextField poto = new JTextField();
	
	JTextField xam_title = new JTextField();
	JTextField xam_date = new JTextField();
	JTextField xam_place = new JTextField();
	JTextField xam_time = new JTextField();
	JTextField xam_inst = new JTextField();
	JTextField percentage = new JTextField();
	
	String[] vedas = {"Rig Vedam", "Suklayajur Vedam", "Krishnayajur Vedam", "Sama Vedam"};
	String[] for_rig = {"Moolam", "Kramam", "Ghanam", "Aithreya Bhramna"};
	String[] for_yajur = {"Kramam", "Ghanam"};
	
	String[] for_sama = {"Poorva Bhagam", "Uttera Bhagam"};
	String[] sak = {"", "Kanva", "Madhayandin"};
	String[] for_sak = {"", "Kramam", "Ghanam"};
	String[] for_sak_sama = {"", "Gowthama","Jaimania"};
	
	JComboBox ved = new JComboBox(vedas);
	JComboBox par = new JComboBox(for_rig);
	JComboBox sakha = new JComboBox(sak);
	//JComboBox sub_sak = new JComboBox(for_sak);
	JPanel main, header;
	JButton add, view, edit, gen_pdf, Import, export, result, clear_db;
	
	JButton save = new JButton("Save");
	JButton reset = new JButton("Reset");
	JButton back = new JButton("Back");
	JButton browse = new JButton("Browse");
	
	JButton retrive1 = new JButton("Retrive");
	JButton retrive2 = new JButton("Retrive");
	JButton update = new JButton("Update");
	JButton delete = new JButton("Delete");
	JButton change = new JButton("Change");
	JButton previous = new JButton("Back");
	JButton reset1 = new JButton("Reset");
	
	JButton back1 = new JButton("Back");
	JButton get_hall = new JButton("Get HallTicket");
	JButton get_addr = new JButton("Get Address");
	JButton get_atten = new JButton("Get Attendance");
	JButton get_res = new JButton("Get Result");
	JButton res_csv = new JButton("Result CSV");
	JButton back2 = new JButton("Back");
	JButton next = new JButton("Next");
	
	GridLayout grid;
	JLabel img, left, right, bottom;
	
	JMenuBar menu = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu file_view = new JMenu("View");
	JMenuItem goBack = new JMenuItem("Back");
	JMenuItem all = new JMenuItem("All");
	JMenuItem rig_menu = new JMenuItem("Rig Vedam");
	JMenuItem sukla_menu = new JMenuItem("Suklayajur Vedam");
	JMenuItem kri_menu = new JMenuItem("Krishnayajur Vedam");
	JMenuItem sama_menu = new JMenuItem("Sama Vedam");
	
	ImageIcon head = new ImageIcon(this.getClass().getResource("header.png"));
	ImageIcon sankara = new ImageIcon(this.getClass().getResource("sankara.png"));
	ImageIcon periyava = new ImageIcon(this.getClass().getResource("periyava.png"));
	ImageIcon create = new ImageIcon(this.getClass().getResource("create.png"));
	ImageIcon ex = new ImageIcon(this.getClass().getResource("export.png"));
	ImageIcon im = new ImageIcon(this.getClass().getResource("import.png"));
	ImageIcon ne = new ImageIcon(this.getClass().getResource("new.png"));
	ImageIcon ed = new ImageIcon(this.getClass().getResource("edit.png"));
	ImageIcon vi = new ImageIcon(this.getClass().getResource("find.png"));
	ImageIcon pd = new ImageIcon(this.getClass().getResource("pdf1.png"));
	ImageIcon cd = new ImageIcon(this.getClass().getResource("erase.png"));
	ImageIcon rd = new ImageIcon(this.getClass().getResource("folder-blue.png"));
	JLabel for_head = new JLabel(head);
	final static int maxGap = 20;
	JFrame newF, viewF, editF, genF, resF;
	JFileChooser pic = new JFileChooser();
	JFileChooser imp = new JFileChooser();
	JFileChooser pdf = new JFileChooser();
	FileNameExtensionFilter  csv_files = new FileNameExtensionFilter("csv files", "csv");
	FileNameExtensionFilter pdf_files = new FileNameExtensionFilter("Pdf files","pdf");
	DefaultTableModel model = new DefaultTableModel();
	JTable view_table = new JTable(model);
	Connection conn1 = null;
	ResultSet rs1 = null;
	JScrollPane tab_scroll, edit_Scroll;
	//JScrollPane sp = new JScrollPane(addr, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	HallTkt(){
		setTitle("vrnt");
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		setSize(dim.width, dim.height-taskht);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imp.addChoosableFileFilter(csv_files);
		pdf.addChoosableFileFilter(pdf_files);
		grid = new GridLayout(2,2);
		reg_no.setToolTipText("Enter Register Number");
		cand_name.setToolTipText("Enter Name");
		cand_fath_name.setToolTipText("Enter Father's Name");
		addr_line_1.setToolTipText("Enter Address Line 1");
		addr_line_2.setToolTipText("Enter Address Line 2");
		city_town.setToolTipText("Enter City/Town");
		patas.setToolTipText("Enter Patasala Name");
		ved.setToolTipText("Select Vedam");
		par.setToolTipText("Select Part");
		sakha.setToolTipText("Select Sakha");
		//sub_sak.setToolTipText("Select Sub-Sakha");
		
		
		//setVisible(true);
		//interior();
	}
	
	public void addComponentsToPane(final Container pane){
		
		img = new JLabel(head);
		left = new JLabel(sankara);
		right = new JLabel(periyava);
		bottom = new JLabel(create);
		main = new JPanel();
	//	header = new JPanel();
	//	JPanel left_pane = new JPanel();
	//	JPanel right_pane = new JPanel();
	//	JPanel bottom_pane = new JPanel();
		//header.setLayout(grid);
	//	main.setLayout(new GridLayout(2,2,50,50));
	//	JButton b = new JButton("Just fake button");
       // Dimension buttonSize = b.getPreferredSize();
        
		add = new JButton("New", ne);
		
		view = new JButton("View", vi);
		edit = new JButton("Edit", ed);
		gen_pdf = new JButton("Pdf", pd);
		Import = new JButton("Import", im);
		export = new JButton("Export", ex);
		result = new JButton("Result", rd);
		clear_db = new JButton("Clear Database", cd);
	/*	main.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+maxGap,
                (int)(buttonSize.getHeight() * 3.5)+maxGap * 2));
		add(main);
		add(header);
		header.add(img);
		left_pane.add(left);
		right_pane.add(right);
		bottom_pane.add(bottom);
		main.add(add);
		main.add(view);
		main.add(edit);
		main.add(gen_pdf);
		main.add(Import);
		main.add(export);
		main.setLayout(grid);
		
		pane.add(header, BorderLayout.NORTH);
		pane.add(main, BorderLayout.CENTER);
		pane.add(left_pane, BorderLayout.WEST);
		pane.add(right_pane, BorderLayout.EAST);
		pane.add(bottom_pane, BorderLayout.SOUTH); */
		add.addActionListener(this);
		view.addActionListener(this);
		edit.addActionListener(this);
		gen_pdf.addActionListener(this);
		Import.addActionListener(this);
		export.addActionListener(this);
		result.addActionListener(this);
		clear_db.addActionListener(this);
		add.setFocusPainted(false);
		view.setFocusPainted(false);
		edit.setFocusPainted(false);
		gen_pdf.setFocusPainted(false);
		Import.setFocusPainted(false);
		export.setFocusPainted(false);
		result.setFocusPainted(false);
		clear_db.setFocusPainted(false);
		add(main);
		GroupLayout main_lay = new GroupLayout(main);
		main.setLayout(main_lay);
		main_lay.setAutoCreateGaps(true);
		main_lay.setAutoCreateContainerGaps(true);
		
		main_lay.setHorizontalGroup(
				main_lay.createSequentialGroup()
				.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(left))
				.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(img)
						.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(main_lay.createSequentialGroup()
						.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(add)
								.addComponent(gen_pdf)
								)
								
								.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(clear_db)
										.addComponent(view)
										.addComponent(Import)
										.addComponent(result)
										)
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(edit)
												.addComponent(export))
										))
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(bottom)))
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(right)));
		main_lay.linkSize(SwingConstants.HORIZONTAL, add, view, edit, gen_pdf, Import, export, result, clear_db);
		main_lay.setVerticalGroup(
				main_lay.createSequentialGroup()
				.addComponent(img)
				.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(left)
						.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addGroup(main_lay.createSequentialGroup()
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(clear_db))
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(add)
								.addComponent(view)
								.addComponent(edit))
								
								.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(gen_pdf)
										.addComponent(Import)
										.addComponent(export)
										)
										.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(result)))
									//	.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.BASELINE)
										//		.addComponent(edit)
											//	.addComponent(export))
												)
												.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.CENTER)
												.addComponent(right)))
												.addGroup(main_lay.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent(bottom)));
	}
	
	
	public void newFrame() {
		newF = new JFrame();
		newF.setTitle("Add New Entry");
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		newF.setSize(dim.width, dim.height-taskht);
		
		//newF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newF.setResizable(false);
		newF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roll_no.setToolTipText("Roll Number Set To Auto-Generated Uneditable");
		poto.setToolTipText("Click On Browse To Select Image Path");
		back.setToolTipText("Go Back To Previous Window");
		browse.setToolTipText("Select An Image From Your Local Disk");
		save.setToolTipText("Save Data To Database");
		reset.setToolTipText("Clear All Fields");
		back.setFocusable(false);
		roll_no.setFocusable(false);
		//reg_no.requestFocus(true);
		roll_no.setEditable(false);
		ved.addActionListener(this);
		par.addActionListener(this);
		sakha.addActionListener(this);
		//sub_sak.addActionListener(this);
		back.addActionListener(this);
		browse.addActionListener(this);
		save.addActionListener(this);
		reset.addActionListener(this);
		back.setFocusPainted(false);
		browse.setFocusPainted(false);
		save.setFocusPainted(false);
		reset.setFocusPainted(false);
		sakha.setEnabled(false);
		//sub_sak.setEnabled(false);
		poto.setEditable(false);
		JPanel pan = new JPanel();
		newF.add(pan);
		save.setDefaultCapable(true);
		newF.getRootPane().setDefaultButton(save);
		GroupLayout layout = new GroupLayout(pan);
		pan.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				   
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
				    		  
				    		  .addComponent(roll)				    		  
				    		  .addComponent(reg)
				    		  .addComponent(name)
				    		  .addComponent(fath_name)
				    		  .addComponent(addr_1)
				    		  .addComponent(addr_2)
				    		  .addComponent(city)
				    		  .addComponent(patasala)
				    		  .addComponent(vedam)
				    		  .addComponent(part)
				    		  .addComponent(saka)
				    		//  .addComponent(sub_saka)
				    		  .addComponent(photo))
				    		  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				    				  .addComponent(for_head)
				    				  .addComponent(roll_no)
				    				  .addComponent(reg_no)
				    				  .addComponent(cand_name)
				    				  .addComponent(cand_fath_name)
				    				  .addComponent(addr_line_1)
				    				  .addComponent(addr_line_2)
				    				  .addComponent(city_town)
				    				  .addComponent(patas)
				    				  .addComponent(ved)
				    				  .addComponent(par)
				    				  .addComponent(sakha)
				    				 // .addComponent(sub_sak)
				    				  .addComponent(poto)
				    				  
				    				  .addComponent(save))
				    				  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    						  .addComponent(browse)
				    						  .addComponent(reset))
				    						  .addComponent(back)
				    				  
				);
		layout.linkSize(SwingConstants.HORIZONTAL, back, browse, save, reset);
		
				layout.setVerticalGroup(
				   layout.createSequentialGroup()
				   .addComponent(for_head)
				   .addComponent(back)
				     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    		 .addComponent(roll)
				    		 .addComponent(roll_no))
				    		 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    				 .addComponent(reg)
				    				 .addComponent(reg_no))
				    				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    						 .addComponent(name)
				    						 .addComponent(cand_name))
				    						 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    								 .addComponent(fath_name)
				    								 .addComponent(cand_fath_name))
				    								 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    										 .addComponent(addr_1)
				    										 .addComponent(addr_line_1))
				    										 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    												 .addComponent(addr_2)
				    												 .addComponent(addr_line_2))
				    												 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    														 .addComponent(city)
				    														 .addComponent(city_town))
				    														 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																 .addComponent(patasala)
				    																 .addComponent(patas))
				    																 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																		 .addComponent(vedam)
				    																		 .addComponent(ved))
				    																		 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																				 .addComponent(part)
				    																				 .addComponent(par))
				    																				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																						 .addComponent(saka)
				    																						 .addComponent(sakha))
				    																					//	 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																						//		 .addComponent(sub_saka)
				    																							//	 .addComponent(sub_sak))
				    																								 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																										 .addComponent(photo)
				    																										 .addComponent(poto)
				    																										 .addComponent(browse))
				    																										 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																										 
				    																												 .addComponent(save)
				    																												 .addComponent(reset))
				); 
				
		try{
			Class.forName("org.h2.Driver");
   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
			Statement stm=conn.createStatement();
			String st = "select max(roll_no) from candidate";
			ResultSet rs = stm.executeQuery(st);
			rs.next();
			int s = rs.getInt(1);			
			roll_no.setText(String.valueOf(s+1));
			conn.close();
		}
		catch (ClassNotFoundException e)
		{
			Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
		}
		catch(SQLException ee)
		{
			System.err.println(ee);
			if (ee.getErrorCode() == 42122){
				roll_no.setText("1");
			}
			Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
		}
		newF.setVisible(true);
		
	}
	
	public void viewFrame(){
		viewF = new JFrame();
		viewF.setTitle("View Entry");
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		viewF.setSize(dim.width, dim.height-taskht);
		
		//viewF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//viewF.setResizable(false);
		viewF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewF.setVisible(true);
		file.setMnemonic(KeyEvent.VK_F);
		file_view.setMnemonic(KeyEvent.VK_V);
		goBack.setMnemonic(KeyEvent.VK_B);
		all.setMnemonic(KeyEvent.VK_A);
		rig_menu.setMnemonic(KeyEvent.VK_R);
		sukla_menu.setMnemonic(KeyEvent.VK_S);
		kri_menu.setMnemonic(KeyEvent.VK_K);
		sama_menu.setMnemonic(KeyEvent.VK_S);
		file.add(goBack);
		goBack.addActionListener(this);
		file_view.add(all);
		all.addActionListener(this);
		file_view.add(rig_menu);
		rig_menu.addActionListener(this);
		file_view.add(sukla_menu);
		sukla_menu.addActionListener(this);
		file_view.add(kri_menu);
		kri_menu.addActionListener(this);
		file_view.add(sama_menu);
		sama_menu.addActionListener(this);
		menu.add(file);
		menu.add(file_view);
		//JPanel view_pan = new JPanel();
		//viewF.add(view_pan);
		viewF.setLayout(new BorderLayout());
		
		viewF.add(menu, BorderLayout.PAGE_START);
		
		//viewF.add(for_head, BorderLayout.NORTH);
		
		viewF.add(view_table, BorderLayout.NORTH);
		
		
		tab_scroll = new JScrollPane(view_table);
		viewF.add(tab_scroll);
		Font ff = new Font("Calibri", Font.PLAIN, 16);
		view_table.setFont(ff);
		view_table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 18));
		view_table.setRowHeight(30);
		view_table.addMouseListener(this);
		model.addColumn("Roll No");
		model.addColumn("Reg No");
		model.addColumn("Name");
		model.addColumn("Father's Name");
		model.addColumn("Address Line 1");
		model.addColumn("Address Line 2");
		model.addColumn("City/Town");
		model.addColumn("Patasala Name");
		model.addColumn("Vedam");
		model.addColumn("Part");
		model.addColumn("Sakha");
	//	model.addColumn("Sub-Sakha");
		model.addColumn("Photo");
	/*	view_table.getColumnModel().getColumn(0).setMaxWidth(100);
		view_table.getColumnModel().getColumn(1).setMaxWidth(100);
		view_table.getColumnModel().getColumn(2).setMaxWidth(100);
		view_table.getColumnModel().getColumn(3).setMaxWidth(100);
		view_table.getColumnModel().getColumn(4).setMaxWidth(100);
		view_table.getColumnModel().getColumn(5).setMaxWidth(100);
		view_table.getColumnModel().getColumn(6).setMaxWidth(100);
		view_table.getColumnModel().getColumn(7).setMaxWidth(100);
		view_table.getColumnModel().getColumn(8).setMaxWidth(100);
		view_table.getColumnModel().getColumn(9).setMaxWidth(100);
		view_table.getColumnModel().getColumn(10).setMaxWidth(100);
		view_table.getColumnModel().getColumn(11).setMaxWidth(100);
		view_table.getColumnModel().getColumn(12).setMaxWidth(100); */
		view_table.setEnabled(false);
		table_data();
		
	}
	
	
	public void table_data(){
		try{
			Class.forName("org.h2.Driver");
   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
			Statement stm=conn.createStatement();
			String st = "select * from (candidate) order by roll_no ";
			ResultSet rs = stm.executeQuery(st);
			//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
			//rs = pre.executeQuery();
			int i = 0;
			
			while(rs.next()){
				int r = rs.getInt(1);
				String re = rs.getString(2);
				String nam = rs.getString(3);
				String fath = rs.getString(4);
				String a1 = rs.getString(5);
				String a2 = rs.getString(6);
				String c = rs.getString(7);
				String pa = rs.getString(8);
				String ve = rs.getString(9);
				String p = rs.getString(10);
				String sa = rs.getString(11);
			//	String su = rs.getString(12);
				//Blob b = rs.getBlob(13);
				//InputStream in = b.getBinaryStream();
			/*	byte by[];
				by = new byte[(int)b.length()];
				by = b.getBytes(1, (int)b.length());
				//String temp = System.getProperty("java.io.tmpdir");
				File f = null;
				try {
					
					f = File.createTempFile("vrnt_pic", ".jpg");
					FileOutputStream out = new FileOutputStream(f);
					//in.read(by);
					out.write(by);
					out.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				try {
					ic = new ImageIcon(ImageIO.read(f));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
				view_table.getColumnModel().getColumn(11).setCellRenderer(new ImageRenderer());
				model.addRow(new Object[] {r, re, nam, fath, a1, a2, c, pa, ve, p, sa, new ImageRenderer()});
				
			//	f.deleteOnExit();
				
			}
			conn.close();
			
		}
		catch (ClassNotFoundException e)
		{
			Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
		}
		catch(SQLException ee)
		{
			System.err.println(ee);
			Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	class ImageRenderer extends DefaultTableCellRenderer
	{
		JLabel label;
		String path;
	/*	ImageRenderer(File filename){
			path = filename.getAbsolutePath();
			
			//this.setIcon(icon);
		} */
		
		void setPath(File val){
			 //path = val;
			 label.setIcon(new ImageIcon(val.getAbsolutePath().toString()));
		}
	    @Override
	    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column)
	    {
	       // label = new JLabel("View Image", SwingConstants.CENTER);
	        JButton but = new JButton("View Image");
	      /*  if (value!=null) {
	        label.setHorizontalAlignment(JLabel.CENTER);
	        //value is parameter which filled by byteOfImage
	        label.setIcon(new ImageIcon(path));
	        JScrollPane jsp = new JScrollPane(label);
	        getContentPane().add(jsp);
	        
	        } */
	        //label.setToolTipText("<html><img src = "+path+"> </html>");
	       // Cursor cur = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	       // label.setCursor(cur);
	        //but.setCursor(cur);
	        //but.setSize(50, 20);
	        return but;
	    }
	}

	public void editFrame(){
		editF = new JFrame();
		editF.setTitle("Update Entry");
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		editF.setSize(dim.width, dim.height-taskht);
		
		//editF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//editF.setResizable(false);
		editF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roll_no.setToolTipText("Enter Roll Number");
		previous.setToolTipText("Go Back To Previous Window");
		retrive1.setToolTipText("Retrive Data Using Roll Number");
		retrive2.setToolTipText("Retrive Data Using Register Number");
		change.setToolTipText("Select Another Image From Your Local Disk");
		update.setToolTipText("Update All Fields");
		delete.setToolTipText("Delete The Entry");
		reset1.setToolTipText("Clear All Fields");
		previous.setFocusable(false);
		JPanel pan2 = new JPanel();
		roll_no.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if (ke.getKeyCode() == KeyEvent.VK_ENTER){
					retrive1.doClick();
					roll_no.setFocusable(false);
				}
			}
		});
		
		reg_no.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if (ke.getKeyCode() == KeyEvent.VK_ENTER){
					retrive2.doClick();
					reg_no.setFocusable(false);
				}
			}
		});
		
		ved.addActionListener(this);
		par.addActionListener(this);
		sakha.addActionListener(this);
	//	sub_sak.addActionListener(this);
		//back.addActionListener(this);
		//browse.addActionListener(this);
		//save.addActionListener(this);
		//reset.addActionListener(this);
		cand_name.setEditable(false);
		cand_fath_name.setEditable(false);
		addr_line_1.setEditable(false);
		addr_line_2.setEditable(false);
		city_town.setEditable(false);
		patas.setEditable(false);
		ved.setEnabled(false);
		par.setEnabled(false);
		sakha.setEnabled(false);
	//	sub_sak.setEnabled(false);
		change.setEnabled(false);
		delete.setEnabled(false);
		//poto.setEditable(false);
		retrive1.addActionListener(this);
		retrive2.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		change.addActionListener(this);
		previous.addActionListener(this);
		reset1.addActionListener(this);
		retrive1.setFocusPainted(false);
		retrive2.setFocusPainted(false);
		update.setFocusPainted(false);
		delete.setFocusPainted(false);
		change.setFocusPainted(false);
		previous.setFocusPainted(false);
		reset1.setFocusPainted(false);
		editF.add(pan2);
		edit_Scroll = new JScrollPane(pan2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//update.setDefaultCapable(true);
		//editF.getRootPane().setDefaultButton(update);
		GroupLayout layout = new GroupLayout(pan2);
		pan2.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		editF.add(edit_Scroll);
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				   
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)				    		  
				    		  .addComponent(roll)				    		  
				    		  .addComponent(reg)
				    		  .addComponent(name)
				    		  .addComponent(fath_name)
				    		  .addComponent(addr_1)
				    		  .addComponent(addr_2)
				    		  .addComponent(city)
				    		  .addComponent(patasala)
				    		  .addComponent(vedam)
				    		  .addComponent(part)
				    		  .addComponent(saka)
				    		//  .addComponent(sub_saka)
				    		  .addComponent(photo))
				    		  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				    				  
				    				  .addComponent(for_head)
				    				  .addComponent(roll_no)
				    				  .addComponent(reg_no)
				    				  .addComponent(cand_name)
				    				  .addComponent(cand_fath_name)
				    				  .addComponent(addr_line_1)
				    				  .addComponent(addr_line_2)
				    				  .addComponent(city_town)
				    				  .addComponent(patas)
				    				  .addComponent(ved)
				    				  .addComponent(par)
				    				  .addComponent(sakha)
				    			//	  .addComponent(sub_sak)
				    				  
				    				  .addComponent(for_img)
				    				  
				    				  .addComponent(update))
				    				  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    						  .addComponent(retrive1)
				    						  .addComponent(retrive2)
				    						  .addComponent(change)
				    						  .addComponent(delete))
				    						  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    								  .addComponent(previous)
				    								  .addComponent(reset1))
				    								 // .addComponent(edit_Scroll)
				    						  
				    						  
				    				  
				);
		layout.linkSize(SwingConstants.HORIZONTAL, retrive1, retrive2, previous, change, update, delete);
		
				layout.setVerticalGroup(
				   layout.createSequentialGroup()
				   
				  // .addComponent(edit_Scroll)
				 //  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				   		   .addComponent(for_head)
						   .addComponent(previous) //  )
				   
				     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    		 .addComponent(roll)
				    		 .addComponent(roll_no)
				    		 .addComponent(retrive1))
				    		 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    				 .addComponent(reg)
				    				 .addComponent(reg_no)
				    				 .addComponent(retrive2))
				    				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    						 .addComponent(name)
				    						 .addComponent(cand_name))
				    						 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    								 .addComponent(fath_name)
				    								 .addComponent(cand_fath_name))
				    								 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    										 .addComponent(addr_1)
				    										 .addComponent(addr_line_1))
				    										 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    												 .addComponent(addr_2)
				    												 .addComponent(addr_line_2))
				    												 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    														 .addComponent(city)
				    														 .addComponent(city_town))
				    														 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																 .addComponent(patasala)
				    																 .addComponent(patas))
				    																 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																		 .addComponent(vedam)
				    																		 .addComponent(ved))
				    																		 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																				 .addComponent(part)
				    																				 .addComponent(par))
				    																				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																						 .addComponent(saka)
				    																						 .addComponent(sakha))
				    																					//	 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																						//		 .addComponent(sub_saka)
				    																							//	 .addComponent(sub_sak))
				    																								 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																										 .addComponent(photo)
				    																										 .addComponent(for_img)
				    																										 .addComponent(change))
				    																										 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    																										 
				    																												 .addComponent(update)
				    																												 .addComponent(delete)
				    																												 .addComponent(reset1))
				); 
				editF.setVisible(true);
	}
	
	public void genFrame(){
		genF = new JFrame();
		genF.setTitle("Generate PDF");
		
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		genF.setSize(dim.width, dim.height-taskht);
		//genF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//genF.setResizable(false);
		genF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		xam_title.setToolTipText("Enter The Title Of The Exam");
		xam_date.setToolTipText("Enter The Date Of The Exam");
		xam_place.setToolTipText("Enter The Place Of The Exam");
		xam_time.setToolTipText("Enter The Date and Time Of Exam to reach Students");
		xam_inst.setToolTipText("Enter Certificate Distribution Date And Time");
		back1.addActionListener(this);
		get_hall.addActionListener(this);
		get_addr.addActionListener(this);
		get_atten.addActionListener(this);
		get_res.addActionListener(this);
		res_csv.addActionListener(this);
		back1.setFocusPainted(false);
		get_hall.setFocusPainted(false);
		get_addr.setFocusPainted(false);
		get_atten.setFocusPainted(false);
		get_res.setFocusPainted(false);
		res_csv.setFocusPainted(false);
		get_hall.setDefaultCapable(true);		
		genF.getRootPane().setDefaultButton(get_hall);
		back1.setFocusable(false);
		JPanel pan3 = new JPanel();
		GroupLayout layout = new GroupLayout(pan3);
		pan3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		genF.add(pan3);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(exam_ti)
						.addComponent(ex_dat)
						.addComponent(place)
						.addComponent(centerTimeToReach)
						.addComponent(cert_dist))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(for_head)
								.addComponent(xam_title)
								.addComponent(xam_date)
								.addComponent(xam_place)
								.addComponent(xam_time)
								.addComponent(xam_inst)
								.addComponent(get_hall))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										
										
									//	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(back1)
												.addComponent(get_addr)
												.addComponent(get_atten)
												.addComponent(get_res)
												.addComponent(res_csv)
										//		)
												));
		
		layout.linkSize(SwingConstants.HORIZONTAL, back1, get_hall, get_addr, get_atten, get_res, res_csv);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(for_head))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(back1))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(exam_ti)
										.addComponent(xam_title))
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(ex_dat)
												.addComponent(xam_date))
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(place)
														.addComponent(xam_place))
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(centerTimeToReach)
																.addComponent(xam_time))
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																	.addComponent(cert_dist)
																	.addComponent(xam_inst))
																	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																			
																			.addComponent(get_addr)
																			)
																			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																					.addComponent(get_atten))
																					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																							.addComponent(get_hall)
																							.addComponent(get_res))
																					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																							.addComponent(res_csv)));
			
		
		genF.setVisible(true);
	}
	
	public void resFrame(){
		resF = new JFrame();
		resF.setTitle("Result");
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskht = dim.height - rec.height;
		resF.setSize(dim.width, dim.height-taskht);
		
		//resF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//genF.setResizable(false);
		
		back2.addActionListener(this);
		back2.setFocusPainted(false);
		next.setFocusPainted(false);
		resF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		back2.setFocusable(false);
		roll_no.setFocusable(false);
		reg_no.setFocusable(false);
		cand_name.setFocusable(false);
		
		next.setDefaultCapable(true);		
		resF.getRootPane().setDefaultButton(next);
		JPanel pan3 = new JPanel();
		GroupLayout layout = new GroupLayout(pan3);
		pan3.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		resF.add(pan3);
		roll.setToolTipText("Roll Number");
		reg_no.setToolTipText("Register Number");
		cand_name.setToolTipText("Name");
		percentage.setToolTipText("Enter The Percentage");
		roll_no.setEditable(false);
		reg_no.setEditable(false);
		cand_name.setEditable(false);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(roll)
						.addComponent(reg)
						.addComponent(name)
						.addComponent(percent))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(for_head)
								.addComponent(roll_no)
								.addComponent(reg_no)
								.addComponent(cand_name)
								.addComponent(percentage)
								.addComponent(next))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(back2)));
		layout.linkSize(SwingConstants.HORIZONTAL, back2, next);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(for_head))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(back2))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(roll)
										.addComponent(roll_no))
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(reg)
												.addComponent(reg_no))
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(name)
														.addComponent(cand_name))
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(percent)
																.addComponent(percentage))
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																		.addComponent(next)));
		
		try{
			Class.forName("org.h2.Driver");
			conn1 = DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
			Statement stm = conn1.createStatement();
			String st = "select roll_no, reg_no, name, percentage from candidate order by roll_no";
			rs1 = stm.executeQuery(st);
			if (rs1.next()){
				int r = rs1.getInt(1);
				String re = rs1.getString(2);
				String n = rs1.getString(3);
				float p = rs1.getFloat(4);
				roll_no.setText(String.valueOf(r));
				reg_no.setText(re);
				cand_name.setText(n);
				percentage.setText(String.valueOf(p));
				
			}
			Telegraph tele = new Telegraph("Success", "Successfully Exported...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
			TelegraphQueue que = new TelegraphQueue();
			//que.add(tele);
			//conn.close();
			//JOptionPane.showMessageDialog(null, "Successfully Exported...", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException e1)
		{
			Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
		}
		catch(SQLException ee)
		{
			System.err.println(ee);
			Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
		}
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try{
					Class.forName("org.h2.Driver");
					Connection conn2 = DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm = conn2.createStatement();
					String st = "update candidate set percentage = "+percentage.getText()+" where reg_no = '"+reg_no.getText()+"'";
					stm.execute(st);
					Telegraph tele = new Telegraph("Success", "Updated Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					if (rs1.isLast()){
						next.setEnabled(false);
					}
					if (rs1.next()){
						int r = rs1.getInt(1);
						String re = rs1.getString(2);
						String n = rs1.getString(3);
						float p = rs1.getFloat(4);
						roll_no.setText(String.valueOf(r));
						reg_no.setText(re);
						cand_name.setText(n);
						percentage.setText(String.valueOf(p));
						
					}
					
					//conn.close();
					//JOptionPane.showMessageDialog(null, "Successfully Exported...", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		resF.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HallTkt ht = new HallTkt();
		ht.addComponentsToPane(ht.getContentPane());
		ht.setVisible(true);
		try{
			Class.forName("org.h2.Driver");
   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
			Statement stm=conn.createStatement();
			String st = "create table if not exists candidate(roll_no int auto_increment, reg_no varchar(50) primary key, name varchar(100), father_name varchar(100), addr_1 varchar(100), addr_2 varchar(100), city varchar(100), patasala varchar(100), vedam varchar(20), part varchar(20), sakha varchar(20), percentage float, photo blob)";
			stm.executeUpdate(st);
			conn.close();
		}
		catch (ClassNotFoundException e)
		{
			Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
		}
		catch(SQLException ee)
		{
			System.err.println(ee);
			Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue que = new TelegraphQueue();
			que.add(tele);
			//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
		}
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object src = arg0.getSource();
		
		if (src == clear_db){
			
					int confirm = JOptionPane.showConfirmDialog(null, "Are You Sure Want To Clear Database...", "Clear Database", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.OK_OPTION){
						try{
							Class.forName("org.h2.Driver");
				   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
							Statement stm=conn.createStatement();
							String st = "truncate table candidate";							
							stm.executeUpdate(st);
							conn.close();
							Telegraph tele = new Telegraph("Success", "Database Deleted Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);				
							TelegraphQueue que = new TelegraphQueue();
							que.add(tele);
						}
						catch (ClassNotFoundException e)
						{
							Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
							TelegraphQueue que = new TelegraphQueue();
							que.add(tele);
							//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
						}
						catch(SQLException ee)
						{
							System.err.println(ee);
							Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
							TelegraphQueue que = new TelegraphQueue();
							que.add(tele);
							//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
						}
					
					}
				
			
		}
		
		if (src == add){
			dispose();
			newFrame();
		}
		
		if (src == back){
			newF.dispose();
			main(null);
		}
		if (src == ved){
			if (ved.getSelectedItem().equals("Rig Vedam")){
				
				par.removeAllItems();				
				par.addItem(for_rig[0]);
				par.addItem(for_rig[1]);
				par.addItem(for_rig[2]);
				par.addItem(for_rig[3]);
				sakha.removeAllItems();
				//sakha.setSelectedIndex(0);
				sakha.addItem(sak[0]);
			//	sub_sak.removeAllItems();
			//	sub_sak.setSelectedItem(for_sak[0]);
				sakha.setEnabled(false);
			//	sub_sak.setEnabled(false);
				
			}
			else if(ved.getSelectedItem().equals("Suklayajur Vedam")){
				par.removeAllItems();				
				par.addItem(for_yajur[0]);
				par.addItem(for_yajur[1]);
				sakha.removeAllItems();
				sakha.addItem(sak[1]);
				sakha.addItem(sak[2]);
			//	sub_sak.removeAllItems();
			//	sub_sak.addItem(for_sak[1]);
			//	sub_sak.addItem(for_sak[2]);
				sakha.setEnabled(true);
			//	sub_sak.setEnabled(true);
				
			}
			else if(ved.getSelectedItem().equals("Krishnayajur Vedam")){
				par.removeAllItems();				
				par.addItem(for_yajur[0]);
				par.addItem(for_yajur[1]);	
				sakha.removeAllItems();
				sakha.addItem(sak[0]);
			//	sub_sak.removeAllItems();
			//	sub_sak.setSelectedItem(for_sak[0]);
				sakha.setEnabled(false);
			//	sub_sak.setEnabled(false);
			}
			else if(ved.getSelectedItem().equals("Sama Vedam")){
				par.removeAllItems();
				par.addItem(for_sama[0]);
				par.addItem(for_sama[1]);
				sakha.removeAllItems();
				//sakha.addItem(for_sak_sama[0]);
				sakha.addItem(for_sak_sama[1]);
				sakha.addItem(for_sak_sama[2]);
			//	sub_sak.removeAllItems();
			//	sub_sak.addItem(for_sak[1]);
			//	sub_sak.addItem(for_sak[2]);
				sakha.setEnabled(true);
			//	sub_sak.setEnabled(true);
			}
	  	}
		
		if (src == browse){
			int ret = pic.showOpenDialog(this);
			File s = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = pic.getSelectedFile();
				String fi = s.toString();
				poto.setText(fi);
			}
			
		}
		
		if (src == reset){
			reg_no.setText("");
			cand_name.setText("");
			cand_fath_name.setText("");
			addr_line_1.setText("");
			addr_line_2.setText("");
			city_town.setText("");
			patas.setText("");
			ved.setSelectedItem(vedas[0]);
			par.setSelectedItem(for_rig[0]);
			poto.setText("");
			reset.setFocusable(false);
			
		}
		
		if (src == save){
			TelegraphQueue que = new TelegraphQueue();
			if (reg_no.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Reg No.", "Register Number can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				reg_no.requestFocus();
			} else if(cand_name.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Name", "Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				cand_name.requestFocus();
			} else if(cand_fath_name.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Father's Name", "Father's Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				cand_fath_name.requestFocus();
			} else if(addr_line_1.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Address Line 1", "Address Line 1 can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				addr_line_1.requestFocus();
			} else if(city_town.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter City", "City can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				city_town.requestFocus();
			} else if(patas.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Patasala Name", "Patasala Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				patas.requestFocus();
			} else if (poto.getText().length() == 0){
				Telegraph tele = new Telegraph("Select Image", "Please select an image", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				
			} else{
			
				BufferedImage src_img = null;
				File target = null;
				try {
					src_img = ImageIO.read(new File(poto.getText()));
					BufferedImage img = Scalr.resize(src_img, Mode.AUTOMATIC, 200, 250);
					target = File.createTempFile("vrnt_pic", ".jpg");
					ImageIO.write(img, "jpg", target);
					target.deleteOnExit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			try{
				
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "insert into candidate(roll_no, reg_no, name, father_name, addr_1, addr_2, city, patasala, vedam, part, sakha, photo) values("+roll_no.getText()+",'"+reg_no.getText()+"',"+"'"+cand_name.getText()+"',"+"'"+cand_fath_name.getText()+"',"+"'"+addr_line_1.getText()+"',"+"'"+addr_line_2.getText()+"',"+"'"+city_town.getText()+"',"+"'"+patas.getText()+"',"+"'"+ved.getSelectedItem()+"',"+"'"+par.getSelectedItem()+"',"+"'"+sakha.getSelectedItem()+"',"+"FILE_READ('"+target.getAbsolutePath()+"')"+")";
				stm.executeUpdate(st);
				//JOptionPane.showMessageDialog(null, "Saved successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
				Telegraph tele = new Telegraph("Success", "Saved successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				reg_no.setText("");
				cand_name.setText("");
				cand_fath_name.setText("");
				addr_line_1.setText("");
				addr_line_2.setText("");
				city_town.setText("");
				patas.setText("");
				ved.setSelectedItem(vedas[0]);
				par.setSelectedItem(for_rig[0]);
				//sakha.setSelectedIndex(0);
				//sub_sak.setSelectedIndex(0);
				poto.setText("");
				String str = "select max(roll_no) from candidate";
				ResultSet rs = stm.executeQuery(str);
				rs.next();
				int s = rs.getInt(1);			
				roll_no.setText(String.valueOf(s+1));
				conn.close();
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				if (ee.getErrorCode() == 42122){
					roll_no.setText("1");
				}
				
				if (ee.getErrorCode() == 23505){
					Telegraph tele = new Telegraph("Data already exist", "The Register Number You Entered Is Already Registered...", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue quee = new TelegraphQueue();
					quee.add(tele);
					//JOptionPane.showMessageDialog(null, "Duplicate Entry Found...", "Data already exists", JOptionPane.ERROR_MESSAGE);
				}
				else {
				
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				}
				System.err.println(ee);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		  }
		}
		
		if (src == view){
			dispose();
			viewFrame();
		}
		
		if (src == goBack){
			viewF.dispose();
			main(null);
		}
		
		if (src == all){
			model.setRowCount(0);
			table_data();
		}
		
		if (src == rig_menu){
			model.setRowCount(0);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) where vedam = 'Rig Vedam' order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				int i = 0;
				
				while(rs.next()){
					int r = rs.getInt(1);
					String re = rs.getString(2);
					String nam = rs.getString(3);
					String fath = rs.getString(4);
					String a1 = rs.getString(5);
					String a2 = rs.getString(6);
					String c = rs.getString(7);
					String pa = rs.getString(8);
					String ve = rs.getString(9);
					String p = rs.getString(10);
					String sa = rs.getString(11);
					//String su = rs.getString(12);
					//Blob b = rs.getBlob(13);
					//InputStream in = b.getBinaryStream();
				/*	byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					view_table.getColumnModel().getColumn(11).setCellRenderer(new ImageRenderer());
					model.addRow(new Object[] {r, re, nam, fath, a1, a2, c, pa, ve, p, sa, new ImageRenderer()});
					
				//	f.deleteOnExit();
					
				}
				conn.close();
				
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == sukla_menu){
			model.setRowCount(0);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) where vedam = 'Suklayajur Vedam' order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				int i = 0;
				
				while(rs.next()){
					int r = rs.getInt(1);
					String re = rs.getString(2);
					String nam = rs.getString(3);
					String fath = rs.getString(4);
					String a1 = rs.getString(5);
					String a2 = rs.getString(6);
					String c = rs.getString(7);
					String pa = rs.getString(8);
					String ve = rs.getString(9);
					String p = rs.getString(10);
					String sa = rs.getString(11);
					//String su = rs.getString(12);
					//Blob b = rs.getBlob(13);
					//InputStream in = b.getBinaryStream();
				/*	byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					view_table.getColumnModel().getColumn(11).setCellRenderer(new ImageRenderer());
					model.addRow(new Object[] {r, re, nam, fath, a1, a2, c, pa, ve, p, sa, new ImageRenderer()});
					
				//	f.deleteOnExit();
					
				}
				conn.close();
				
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == kri_menu){
			model.setRowCount(0);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) where vedam = 'Krishnayajur Vedam' order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				int i = 0;
				
				while(rs.next()){
					int r = rs.getInt(1);
					String re = rs.getString(2);
					String nam = rs.getString(3);
					String fath = rs.getString(4);
					String a1 = rs.getString(5);
					String a2 = rs.getString(6);
					String c = rs.getString(7);
					String pa = rs.getString(8);
					String ve = rs.getString(9);
					String p = rs.getString(10);
					String sa = rs.getString(11);
					//String su = rs.getString(12);
					//Blob b = rs.getBlob(13);
					//InputStream in = b.getBinaryStream();
				/*	byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					view_table.getColumnModel().getColumn(11).setCellRenderer(new ImageRenderer());
					model.addRow(new Object[] {r, re, nam, fath, a1, a2, c, pa, ve, p, sa, new ImageRenderer()});
					
				//	f.deleteOnExit();
					
				}
				conn.close();
				
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == sama_menu){
			model.setRowCount(0);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) where vedam = 'Sama Vedam' order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				int i = 0;
				
				while(rs.next()){
					int r = rs.getInt(1);
					String re = rs.getString(2);
					String nam = rs.getString(3);
					String fath = rs.getString(4);
					String a1 = rs.getString(5);
					String a2 = rs.getString(6);
					String c = rs.getString(7);
					String pa = rs.getString(8);
					String ve = rs.getString(9);
					String p = rs.getString(10);
					String sa = rs.getString(11);
					//String su = rs.getString(12);
					//Blob b = rs.getBlob(13);
					//InputStream in = b.getBinaryStream();
				/*	byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					view_table.getColumnModel().getColumn(11).setCellRenderer(new ImageRenderer());
					model.addRow(new Object[] {r, re, nam, fath, a1, a2, c, pa, ve, p, sa, new ImageRenderer()});
					
				//	f.deleteOnExit();
					
				}
				conn.close();
				
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == edit){
			dispose();
			editFrame();
		}
		
		if (src == retrive1){
			
			
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from candidate where roll_no = "+roll_no.getText();
				ResultSet rs = stm.executeQuery(st);
				if(rs.first()){
					roll_no.setFocusable(true);
					
					roll_no.setEditable(false);
					reg_no.setEditable(false);
					cand_name.setEditable(true);
					cand_fath_name.setEditable(true);
					addr_line_1.setEditable(true);
					addr_line_2.setEditable(true);
					city_town.setEditable(true);
					patas.setEditable(true);
					ved.setEnabled(true);
					par.setEnabled(true);
					change.setEnabled(true);
					delete.setEnabled(true);
					reg_no.setText(rs.getString(2));
					cand_name.setText(rs.getString(3));
					cand_fath_name.setText(rs.getString(4));
					addr_line_1.setText(rs.getString(5));
					addr_line_2.setText(rs.getString(6));
					city_town.setText(rs.getString(7));
					patas.setText(rs.getString(8));
					ved.setSelectedItem(rs.getString(9));
					par.setSelectedItem(rs.getString(10));
					sakha.setSelectedItem(rs.getString(11));
				//	sub_sak.setSelectedItem(rs.getString(12));
					Blob bl = rs.getBlob(13);
					byte by[];
					by = new byte[(int)bl.length()];
					by = bl.getBytes(1, (int)bl.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						f.deleteOnExit();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for_img.setIcon(ic);
					
				} else{
					Telegraph tele = new Telegraph("Entrie Not Found", "The Roll No. you entered is not found in the database...", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Entrie not found...", "Error", JOptionPane.ERROR_MESSAGE);
				}
				conn.close();
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == retrive2){
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from candidate where reg_no = '"+reg_no.getText()+"'";
				ResultSet rs = stm.executeQuery(st);
				if(rs.first()){
					
					reg_no.setFocusable(true);
					roll_no.setEditable(false);
					reg_no.setEditable(false);
					cand_name.setEditable(true);
					cand_fath_name.setEditable(true);
					addr_line_1.setEditable(true);
					addr_line_2.setEditable(true);
					city_town.setEditable(true);
					patas.setEditable(true);
					ved.setEnabled(true);
					par.setEnabled(true);
					change.setEnabled(true);
					delete.setEnabled(true);
					roll_no.setText(rs.getString(1));
					cand_name.setText(rs.getString(3));
					cand_fath_name.setText(rs.getString(4));
					addr_line_1.setText(rs.getString(5));
					addr_line_2.setText(rs.getString(6));
					city_town.setText(rs.getString(7));
					patas.setText(rs.getString(8));
					ved.setSelectedItem(rs.getString(9));
					par.setSelectedItem(rs.getString(10));
					sakha.setSelectedItem(rs.getString(11));
				//	sub_sak.setSelectedItem(rs.getString(12));
					Blob bl = rs.getBlob(13);
					byte by[];
					by = new byte[(int)bl.length()];
					by = bl.getBytes(1, (int)bl.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f = null;
					try {
						f = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f);
						//in.read(by);
						out.write(by);
						out.close();
						f.deleteOnExit();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					try {
						ic = new ImageIcon(ImageIO.read(f));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for_img.setIcon(ic);
				} else{
					Telegraph tele = new Telegraph("Entrie Not Found", "The Reg No. you entered is not yet registered.", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Entrie not found...", "Error", JOptionPane.ERROR_MESSAGE);
				}
				conn.close();
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == update){
			TelegraphQueue que = new TelegraphQueue();
			if(cand_name.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Name", "Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				cand_name.requestFocus();
			} else if(cand_fath_name.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Father's Name", "Father's Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				cand_fath_name.requestFocus();
			} else if(addr_line_1.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Address Line 1", "Address Line 1 can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				addr_line_1.requestFocus();
			} else if(city_town.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter City", "City can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				city_town.requestFocus();
			} else if(patas.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Patasala Name", "Patasala Name can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
				patas.requestFocus();
			} else {
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "update candidate set name = '"+cand_name.getText()+"', father_name = "+"'"+cand_fath_name.getText()+"', addr_1 = "+"'"+addr_line_1.getText()+"', addr_2 = "+"'"+addr_line_2.getText()+"', city = "+"'"+city_town.getText()+"', patasala = "+"'"+patas.getText()+"', vedam = "+"'"+ved.getSelectedItem()+"', part = "+"'"+par.getSelectedItem()+"', sakha = "+"'"+sakha.getSelectedItem()+"' where reg_no = '"+reg_no.getText()+"'";
				stm.executeUpdate(st);
				Telegraph tele = new Telegraph("Success", "Updated successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null, "Updated successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
				conn.close();
				roll_no.setText("");
				reg_no.setText("");
				cand_name.setText("");
				cand_fath_name.setText("");
				addr_line_1.setText("");
				addr_line_2.setText("");
				city_town.setText("");
				patas.setText("");
				roll_no.setEditable(true);
				reg_no.setEditable(true);
				cand_name.setEditable(false);
				cand_fath_name.setEditable(false);
				addr_line_1.setEditable(false);
				addr_line_2.setEditable(false);
				city_town.setEditable(false);
				patas.setEditable(false);
				ved.setEnabled(false);
				par.setEnabled(false);
				for_img.setIcon(null);
				change.setEnabled(false);
				delete.setEnabled(false);
				roll_no.setFocusable(true);
				reg_no.setFocusable(true);
			}
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		  }
		}
		
		if (src == delete){
			int t = JOptionPane.showConfirmDialog(null, "Are you sure want to delete "+cand_name.getText(), "Confirm delete", JOptionPane.YES_NO_OPTION);
			if (t == JOptionPane.YES_OPTION){
				try{
					Class.forName("org.h2.Driver");
		   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm=conn.createStatement();
					String st = "delete from candidate where reg_no = '"+reg_no.getText()+"'";
					stm.executeUpdate(st);
					Telegraph tele = new Telegraph("Success", "Deleted successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Deleted successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
					roll_no.setText("");
					reg_no.setText("");
					cand_name.setText("");
					cand_fath_name.setText("");
					addr_line_1.setText("");
					addr_line_2.setText("");
					city_town.setText("");
					patas.setText("");
					roll_no.setEditable(true);
					reg_no.setEditable(true);
					cand_name.setEditable(false);
					cand_fath_name.setEditable(false);
					addr_line_1.setEditable(false);
					addr_line_2.setEditable(false);
					city_town.setEditable(false);
					patas.setEditable(false);
					ved.setEnabled(false);
					par.setEnabled(false);
					for_img.setIcon(null);
					change.setEnabled(false);
					delete.setEnabled(false);
					roll_no.setFocusable(true);
					reg_no.setFocusable(true);
					conn.close();
				}
				catch (ClassNotFoundException e)
				{
					Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue quee = new TelegraphQueue();
					quee.add(tele);
					//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue quee = new TelegraphQueue();
					quee.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
		if (src == previous){
			editF.dispose();
			main(null);
		}
		
		if (src == change){
			int ret = pic.showOpenDialog(this);
			File s = null;
			String fi = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = pic.getSelectedFile();
				fi = s.toString();
				//poto.setText(fi);
				BufferedImage src_img = null;
				File target = null;
				try {
					src_img = ImageIO.read(s);
					BufferedImage img = Scalr.resize(src_img, Mode.AUTOMATIC, 200, 250);
					target = File.createTempFile("vrnt_pic", ".jpg");
					ImageIO.write(img, "jpg", target);
					target.deleteOnExit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try{
					Class.forName("org.h2.Driver");
		   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm=conn.createStatement();
					String st = "update candidate set photo = FILE_READ('"+target.getAbsolutePath()+"') where reg_no = '"+reg_no.getText()+"'";
					stm.executeUpdate(st);
					Telegraph tele = new Telegraph("Success", "Image changed successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Image changed successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
					String st1 = "select photo from candidate where reg_no = '"+reg_no.getText()+"'";
					ResultSet rs = stm.executeQuery(st1);
					if(rs.first()){
						Blob bl = rs.getBlob(1);
						byte by[];
						by = new byte[(int)bl.length()];
						by = bl.getBytes(1, (int)bl.length());
						//String temp = System.getProperty("java.io.tmpdir");
						File f = null;
						try {
							f = File.createTempFile("vrnt_pic", ".jpg");
							FileOutputStream out = new FileOutputStream(f);
							//in.read(by);
							out.write(by);
							out.close();
							f.deleteOnExit();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						try {
							ic = new ImageIcon(ImageIO.read(f));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for_img.setIcon(ic);
						
					} else {
						Telegraph telem = new Telegraph("Error", "Error loading image...", TelegraphType.NOTIFICATION_ERROR, WindowPosition.BOTTOMRIGHT, 4000);
						TelegraphQueue quee = new TelegraphQueue();
						quee.add(telem);
						//JOptionPane.showMessageDialog(null, "Error loading image...", "Error", JOptionPane.ERROR_MESSAGE);
					}
					conn.close();
				}
				catch (ClassNotFoundException e)
				{
					Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue quee = new TelegraphQueue();
					quee.add(tele);
					//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue quee = new TelegraphQueue();
					quee.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				}
			}
			
			
		}
		
		if (src == reset1){
			roll_no.setFocusable(true);
			reg_no.setFocusable(true);
			roll_no.setText("");
			reg_no.setText("");
			cand_name.setText("");
			cand_fath_name.setText("");
			addr_line_1.setText("");
			addr_line_2.setText("");
			city_town.setText("");
			patas.setText("");
			ved.setSelectedIndex(0);
			par.setSelectedIndex(0);
			roll_no.setEditable(true);
			reg_no.setEditable(true);
			cand_name.setEditable(false);
			cand_fath_name.setEditable(false);
			addr_line_1.setEditable(false);
			addr_line_2.setEditable(false);
			city_town.setEditable(false);
			patas.setEditable(false);
			ved.setEnabled(false);
			par.setEnabled(false);
			sakha.setEnabled(false);
		//	sub_sak.setEnabled(false);
			for_img.setIcon(null);
			change.setEnabled(false);
			delete.setEnabled(false);
			reset1.setFocusable(false);
			
		}
		
		if (src == Import){
			int ret = imp.showOpenDialog(this);
			File s = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = imp.getSelectedFile();
				String fi = s.toString();
			}
			Connection conn = null;
			try{
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm = conn.createStatement();
				String st = "insert into candidate (select * from csvread('"+s+"'))";
				stm.executeUpdate(st);
				Telegraph tele = new Telegraph("Success", "Imported successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null, "Imported Successfully...", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch(SQLException e6){
				System.err.println(e6);
				if (e6.getErrorCode() == 23505){
					Telegraph tele = new Telegraph("Data already exist", "Duplicate entry found...", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Duplicate Entry Found...", "Data already exists", JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
			}
		}
		
		if (src == export){
			int ret = imp.showSaveDialog(this);
			File s;
			if(ret == JFileChooser.APPROVE_OPTION){
				s = imp.getSelectedFile();
				String file_name = s.toString();
				if (!file_name.toLowerCase().endsWith(".csv")){
					s = new File(file_name+".csv");
				}
				Connection conn = null;
				try{
					Class.forName("org.h2.Driver");
					conn = DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm = conn.createStatement();
					String st = "call csvwrite('"+s+"', 'select * from candidate')";
					stm.executeUpdate(st);
					Telegraph tele = new Telegraph("Success", "Successfully Exported...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Successfully Exported...", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e)
				{
					Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
		if (src == gen_pdf){
			dispose();
			genFrame();
		}
		
		if (src == back1){
			genF.dispose();
			main(null);
		}
		
		
		
		if (src == get_hall){
			TelegraphQueue que = new TelegraphQueue();
			if (xam_title.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Exam Title", "Exam Title can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
			} else if (xam_date.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Date", "Date of Exam can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
			} else if (xam_place.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Place of Exam", "Place of Exam can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
			} else if (xam_time.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Time of Exam", "Time of Exam can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
			}else if (xam_inst.getText().length() == 0){
				Telegraph tele = new Telegraph("Enter Certificate Distribution Day", "Certificate Distribution Day can't be empty", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				que.add(tele);
			} else {
			int ret = pdf.showSaveDialog(this);
			File s = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = pdf.getSelectedFile();
				String stm = s.toString();
				if(!stm.endsWith(".pdf")){
					s = new File(stm+".pdf");
				}
			}
			PdfWriter writer = null;
			Document doc = new Document();
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(s));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			doc.setPageSize(PageSize.A4);
			doc.open();
			doc.setMarginMirroring(true);
			PdfPTable table = new PdfPTable(3);
			table.setComplete(true);
			//Phrase h = new Phrase("SRI GURUBHYO NAMAHA");
			com.itextpdf.text.Font f = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 18);
			com.itextpdf.text.Font n = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 10);
			com.itextpdf.text.Font nb = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			com.itextpdf.text.Font si = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 8);
			//Phrase pan = new Phrase("PAN No.: AAATV3147P");
			Phrase vrnt = new Phrase("VEDA RAKSHNA NIDHI TRUST", f);
			Phrase vrnt_add = new Phrase("No.64/31, Subramaniyam Sreet, West Mambalam, Chennai - 600 033.", si);
			Phrase mail = new Phrase("E-Mail: office@vrnt.org \t Phone: 044-24740549", n);
			Phrase ph = new Phrase("Phone: 044-24740549");
			Phrase web = new Phrase("Website: www.vrnt.org", n);
			//Phrase xam = new Phrase("VIJAYA DASAMI POORTHI PARIKSHA HALL TICKET – 2013");
			Phrase xam = new Phrase(xam_title.getText().toUpperCase());
			PdfPCell cell1, cell2, cell3, cell4, cell5;
			cell1 = new PdfPCell(vrnt);
			cell2 = new PdfPCell(vrnt_add);
			cell3 = new PdfPCell(mail);
			cell4 = new PdfPCell(web);
			cell5 = new PdfPCell(xam);
			cell1.setColspan(2);
			cell2.setColspan(2);
			cell3.setColspan(2);
			cell4.setColspan(2);
			cell5.setColspan(3);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			Phrase inst = new Phrase("IMPORTANT INSTRUCTIONS", n);
			Phrase ins1 = new Phrase("Please be present at the Centre before "+xam_time.getText().toUpperCase(), si);
			Phrase ins2 = new Phrase("Distribution of Certificates and Sambhavana for the successful candidates from the benign hands of Acharyas shall be on "+xam_inst.getText().toUpperCase(), si);
			Phrase ins3 = new Phrase("Travelling Allowance by shortest route for Examination and Sambhavana Distribution Function will be paid by the Trust on production of valid NON-A/C train or bus tickets which is a must.", si);
			Phrase ins4 = new Phrase("Please produce this hall ticket for entry into examination centre.", si);
			Phrase cand_sign = new Phrase("SIGNATURE OF CANDIDATE", n);
			Phrase auth_sign = new Phrase("SIGNATURE OF AUTHORITY", n);
			
			ZapfDingbatsList ins_list = new ZapfDingbatsList(226, 20);
			ins_list.add(new ListItem(ins1));
			ins_list.add(new ListItem(ins2));
			ins_list.add(new ListItem(ins3));
			ins_list.add(new ListItem(ins4));
			PdfPCell ins_c = new PdfPCell(inst);
			PdfPCell ins1_c = new PdfPCell(ins1);
			PdfPCell ins2_c = new PdfPCell(ins2);
			PdfPCell ins3_c = new PdfPCell(ins3);
			PdfPCell ins4_c = new PdfPCell(ins4);
			PdfPCell sign_c = new PdfPCell(cand_sign);
			PdfPCell auth_c = new PdfPCell(auth_sign);
			ins_c.setBorder(0);
			sign_c.setBorder(0);
			auth_c.setBorder(0);
			PdfContentByte cb = writer.getDirectContent();
			//cb.roundRectangle(20, doc.getPageSize().getHeight()/2+20, doc.getPageSize().getWidth()-40, doc.getPageSize().getHeight()/2-50, 20);
			//cb.roundRectangle(20, 40, doc.getPageSize().getWidth()-40, doc.getPageSize().getHeight()/2-50, 20);
			
			//cb.closePath();
			//cb.stroke();
			PdfPCell imgcell = null;
			com.itextpdf.text.Image im = null;
			//com.itextpdf.text.Image water = null;
			com.itextpdf.text.Image V = null;
			com.itextpdf.text.Image G = null;
			com.itextpdf.text.Image w = null;
			com.itextpdf.text.Image sign = null;
			try {
				im = Image.getInstance(this.getClass().getResource("Periyava-COLOR.jpg"));
				//water = com.itextpdf.text.Image.getInstance(this.getClass().getResource("untitled1.bmp"));
				V = Image.getInstance(this.getClass().getResource("NEW1.png"));
				G = Image.getInstance(this.getClass().getResource("NEW2.png"));
				w = Image.getInstance(this.getClass().getResource("w.jpg"));
				sign = Image.getInstance(this.getClass().getResource("ET-SIGN.png"));
				//im.scaleToFit(doc.getPageSize().getWidth()-60, im.getHeight());
				//im.setAbsolutePosition(30, doc.getPageSize().getHeight()-180);
				//doc.add(im);
			//	water.setAbsolutePosition(160, 250);
				//water.setTransparency(new int[]{ 0xF0, 0xFF });
			//	doc.add(water);
			//	water.setAbsolutePosition(160, 650);
			//	doc.add(water);
				imgcell = new PdfPCell(im);
				imgcell.setRowspan(4);
				im.scaleToFit(60, 60);
				imgcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				imgcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				table.setWidths(new int[]{50, 60, 40});
				//table.setWidthPercentage(100);
				//im.setAbsolutePosition(30, doc.getPageSize().getHeight()/2-150);
				//doc.add(im);
				//table.addCell(im);
				
				//doc.add(vrnt);
				
				
				
			// values
				ins_c.setColspan(3);
				//ins1_c.setColspan(3);
				//ins2_c.setColspan(3);
				//ins3_c.setColspan(3);
				//ins4_c.setColspan(3);
				//sign_c.setColspan(2);
				auth_c.setColspan(2);
				auth_c.setHorizontalAlignment(Element.ALIGN_RIGHT);
				
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			int i1 = 0;
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				
				
				while(rs.next()){
					int r = rs.getInt(1);
					String re = rs.getString(2);
					String nam = rs.getString(3);
					String fath = rs.getString(4);
					String a1 = rs.getString(5);
					String a2 = rs.getString(6);
					String c = rs.getString(7);
					String pa = rs.getString(8);
					String ve = rs.getString(9);
					String p = rs.getString(10);
					String sa = rs.getString(11);
				//	String su = rs.getString(12);
					Blob b = rs.getBlob(13);
					InputStream in = b.getBinaryStream();
					byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f1 = null;
					try {
						
						f1 = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f1);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					com.itextpdf.text.Image im_c = null;
					try {
						im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
					} catch (BadElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				/*	try {
						ic = new ImageIcon(ImageIO.read(f1));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  */
					String ip = null;
					if (sa == null || sa.equals("null")){
						sa = "";
					} if (a2 == null){
						a2 = "";
					}
					if (sa.length() != 0){
						ip = "- ";
					} else {
						ip = "";
					}
					
					
					imgcell.setBorder(0);
					cell1.setBorder(0);
					cell2.setBorder(0);
					cell3.setBorder(0);
					cell4.setBorder(0);
					cell5.setBorder(0);
					table.addCell(imgcell);
					table.addCell(cell1);
					
					table.addCell(cell2);
					table.addCell(cell3);
					table.addCell(cell4);
					table.addCell(cell5);
					
					Phrase Reg = new Phrase("Register No: "+re.toUpperCase(), n);
					
					
					Phrase Name = new Phrase("Name: "+nam.toUpperCase(), nb);
					Phrase Fath = new Phrase("Father's Name: "+fath.toUpperCase(), n);
					Phrase Addr = new Phrase("Address: "+a1.toUpperCase()+" "+a2.toUpperCase()+" "+c.toUpperCase(), n);
					Phrase Patasala = new Phrase("Patasala Name: "+pa.toUpperCase(), nb);
					Phrase Vedam = new Phrase("Vedam: "+ve, n);
					Phrase Portion = new Phrase("Portion: "+p+" "+ip+sa, n);
					Phrase Date = new Phrase("Date: "+xam_date.getText(), nb);
					Phrase Place = new Phrase("Place Of Exam: "+xam_place.getText().toUpperCase(), nb);
				/*	PdfPCell r_c = new PdfPCell(Reg);
					
					PdfPCell n_c = new PdfPCell(Name);
					PdfPCell f_c = new PdfPCell(Fath);
					PdfPCell a_c = new PdfPCell(Addr);
					
					PdfPCell p_c = new PdfPCell(Patasala);
					PdfPCell v_c = new PdfPCell(Vedam);
					PdfPCell po_c = new PdfPCell(Portion);
					PdfPCell d_c = new PdfPCell(Date);
					PdfPCell pl_c = new PdfPCell(Place); */
					Phrase emp = new Phrase("\0");
					PdfPCell r1_c = new PdfPCell(Reg);
					
					r1_c.setColspan(2);
					r1_c.setBorder(0);
					PdfPCell n1_c = new PdfPCell(Name);
					n1_c.setColspan(2);
					n1_c.setBorder(0);
					PdfPCell f1_c = new PdfPCell(Fath);
					f1_c.setColspan(2);
					f1_c.setBorder(0);
					PdfPCell a1_c = new PdfPCell(Addr);
					a1_c.setColspan(2);
					
					a1_c.setBorder(0);
					//c_c.setColspan(2);
					PdfPCell p1_c = new PdfPCell(Patasala);
					p1_c.setColspan(3);
					p1_c.setBorder(0);
					PdfPCell v1_c = new PdfPCell(Vedam);
					v1_c.setBorder(0);
					PdfPCell po1_c = new PdfPCell(Portion);
					//po1_c.setColspan(2);
					po1_c.setBorder(0);
					PdfPCell d1_c = new PdfPCell(Date);
					d1_c.setBorder(0);
					PdfPCell pl1_c = new PdfPCell(Place);
					pl1_c.setColspan(3);			
					pl1_c.setBorder(0);
					PdfPCell cell_poto = new PdfPCell(im_c);
					im_c.scaleToFit(130,78);
					cell_poto.setBorder(0);
					cell_poto.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell_poto.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell_poto.setRowspan(4);
					PdfPCell sign_poto = new PdfPCell(sign);
					sign.scaleToFit(230, 40);
					//sign.scaleToFit(sign_poto);
					sign_poto.setBorder(0);
					sign_poto.setColspan(3);
					sign_poto.setRowspan(2);
					sign_poto.setHorizontalAlignment(Element.ALIGN_RIGHT);
					sign_poto.setVerticalAlignment(Element.ALIGN_RIGHT);
					PdfPCell empty = new PdfPCell(emp);
					empty.setColspan(3);
					empty.setBorder(0);
					PdfPCell li_c = new PdfPCell();
					li_c.addElement(ins_list);
					li_c.setColspan(3);
					li_c.setBorder(0);
					//table.addCell(r_c);
					table.addCell(empty);
					table.addCell(r1_c);
					table.addCell(cell_poto);
					//table.addCell(n_c);
					
					table.addCell(n1_c);
					//table.addCell(f_c);
					table.addCell(f1_c);
					//table.addCell(a_c);
					table.addCell(a1_c);
					//table.addCell(c_c);
					//table.addCell(p_c);
					table.addCell(p1_c);
					//table.addCell(v_c);
					table.addCell(v1_c);
					//table.addCell(po_c);
					table.addCell(po1_c);
					//table.addCell(d_c);
					table.addCell(d1_c);
					//table.addCell(pl_c);
					table.addCell(pl1_c);
					table.addCell(empty);
					table.addCell(ins_c);
					table.addCell(li_c);
					//table.addCell(ins1_c);
					//table.addCell(ins2_c);
					//table.addCell(ins3_c);
					//table.addCell(ins4_c);
					//table.addCell(empty);
					//table.addCell(empty);
					table.addCell(sign_poto);
					table.addCell(sign_c);
					table.addCell(auth_c);
					
					
					
					
					
					
					
					try {
						
						if (i1 % 2 == 0){
							//System.out.println("new page");
							table.addCell(empty);
							table.addCell(empty);
							//table.addCell(empty);
							doc.newPage();
							
							writer.setPageEmpty(false);
							cb.roundRectangle(20, doc.getPageSize().getHeight()/2+10, doc.getPageSize().getWidth()-40, doc.getPageSize().getHeight()/2-40, 20);
							cb.roundRectangle(20, 35, doc.getPageSize().getWidth()-40, doc.getPageSize().getHeight()/2-40, 20);
							//cb.closePath();					
							cb.stroke();
							
							
							
						} 
						
					//	water.setAbsolutePosition(15, 200);
						//water.setTransparency(new int[]{ 0xF0, 0xFF });
					//	doc.add(water);
					//	water.setAbsolutePosition(15, 600);
					//	doc.add(water);
							//writer.setPageEmpty(false);
						//writer.flush();
						/*V.scaleAbsolute(50, doc.getPageSize().getHeight()/2-70);
						V.setAbsolutePosition(30, 55);
						G.scaleAbsolute(40, doc.getPageSize().getHeight()/2-70);
						G.setAbsolutePosition(doc.getPageSize().getWidth()-70, 55);
						doc.add(V);
						doc.add(G);
						V.setAbsolutePosition(30, doc.getPageSize().getHeight()/2+30);
						G.setAbsolutePosition(doc.getPageSize().getWidth()-70, doc.getPageSize().getHeight()/2+30);
						doc.add(V);
						doc.add(G); */
						w.setAbsolutePosition(150, 80);
						w.scaleAbsolute(250, 250);
						doc.add(w);
						w.setAbsolutePosition(150, doc.getPageSize().getHeight()/2+60);
						doc.add(w);
						doc.add(table);	
						
						
						table.flushContent();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//doc.newPage();
					
					i1 = i1 + 1;
					f1.deleteOnExit();
					
				}
				conn.close();
				doc.close();
				
				Telegraph tele = new Telegraph("Success", "Hall Ticket Generated Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				
				xam_title.setText("");
				xam_date.setText("");
				xam_place.setText("");
				xam_time.setText("");
				xam_inst.setText("");
			}
			
			
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quea = new TelegraphQueue();
				quea.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quea = new TelegraphQueue();
				quea.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
			
			
			//PdfPTable inner_tab = new PdfPTable(5);
			
			
		//	PdfPCell inner = new PdfPCell(inner_tab);
			//inner.setPadding(0);
			
			//table.addCell(inner_tab);
			
			//inner.setColspan(2);
			
			
		// constants	
			
			
			
			}	
			
		
			
			
		}
		
		if (src == get_addr){
			int ret = pdf.showSaveDialog(this);
			File s = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = pdf.getSelectedFile();
				String stm = s.toString();
				if(!stm.endsWith(".pdf")){
					s = new File(stm+".pdf");
				}
			}
			PdfWriter writer = null;
			Document doc = new Document();
			com.itextpdf.text.Font n = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 10);
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(s));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			doc.setPageSize(PageSize.A4);
			doc.open();
			doc.setMarginMirroring(true);
			PdfPTable table = new PdfPTable(4);
			table.setComplete(true);
			table.setWidthPercentage(100);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				
				while(rs.next()){
					String rr = rs.getString(2);
					String nn = rs.getString(3);
					String ff = rs.getString(4);
					String a11 = rs.getString(5);
					String a12 = rs.getString(6);
					String cc = rs.getString(7);
					String pp = rs.getString(8);
					Phrase p = null;
					
					if (a12 != null){
						//a12 = "\b";
					
						p = new Phrase("Reg No. "+rr.toUpperCase()+"\n"+nn.toUpperCase()+"\nS/O "+ff.toUpperCase()+"\n"+a11.toUpperCase()+"\n"+a12.toUpperCase()+"\n"+cc.toUpperCase()+"\n"+pp.toUpperCase(), n);
					}
					else {
						
						p = new Phrase("Reg No. "+rr.toUpperCase()+"\n"+nn.toUpperCase()+"\nS/O "+ff.toUpperCase()+"\n"+a11.toUpperCase()+"\n"+cc.toUpperCase()+"\n"+pp.toUpperCase(), n);
					}
					table.addCell(p);
				}
				table.addCell("");
				table.addCell("");
				table.addCell("");
				
				try {
					doc.add(table);
					doc.close();
					Telegraph tele = new Telegraph("Success", "Address Generated Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if (src == get_atten){
			int ret = pdf.showSaveDialog(this);
			File s = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = pdf.getSelectedFile();
				String stm = s.toString();
				if(!stm.endsWith(".pdf")){
					s = new File(stm+".pdf");
				}
			}
			PdfWriter writer = null;
			Document doc = new Document();
			com.itextpdf.text.Font n = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			com.itextpdf.text.Font i = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 8);
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(s));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			doc.setPageSize(PageSize.A4);
			doc.open();
			doc.setMarginMirroring(true);
			PdfPTable table = new PdfPTable(6);
			table.setComplete(true);
			try {
				table.setWidths(new int[]{30, 40, 80, 60, 50, 80});
				
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			table.addCell(new Phrase("Roll No", n));
			table.addCell(new Phrase("Reg No", n));
			table.addCell(new Phrase("Name", n));
			table.addCell(new Phrase("Patasala", n));
			table.addCell(new Phrase("Photo", n));
			table.addCell(new Phrase("Signature", n));
			table.setHeaderRows(1);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) order by roll_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				
				while(rs.next()){
					int r = rs.getInt(1);
					
					String rr = rs.getString(2);
					String nn = rs.getString(3);
					String ff = rs.getString(8);
					//String a11 = rs.getString(5);
					//String a12 = rs.getString(6);
					//String cc = rs.getString(7);
					Blob b = rs.getBlob(13);
					InputStream in = b.getBinaryStream();
					byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f1 = null;
					try {
						
						f1 = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f1);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					com.itextpdf.text.Image im_c = null;
					try {
						im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
					} catch (BadElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					f1.deleteOnExit();
					
					Phrase p1 = new Phrase(String.valueOf(r), i);
					//PdfPCell pr = new PdfPCell(p1);
					Phrase p2 = new Phrase(rr.toUpperCase(), i);
					Phrase p3 = new Phrase(nn.toUpperCase(), i);
					Phrase p4 = new Phrase(ff.toUpperCase(), i);
					PdfPCell img = new PdfPCell(im_c);
					im_c.scaleToFit(100,60);
					img.setFixedHeight(80);
					img.setHorizontalAlignment(Element.ALIGN_CENTER);
					img.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(p1);
					table.addCell(p2);
					table.addCell(p3);
					table.addCell(p4);
					table.addCell(img);
					table.addCell("");
					
				}
				
				
				try {
					doc.add(table);
					doc.close();
					
					Telegraph tele = new Telegraph("Success", "Attendance Sheet Generated Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
			catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (src == get_res){
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ret = j.showSaveDialog(this);
			File s = null, sk = null, sg = null,sa = null, s1 = null, s1g = null, s2 = null, s2g = null, s3 = null, s3u = null;
			if (ret == JFileChooser.APPROVE_OPTION){
				s = j.getSelectedFile();
				
				String stm = s.toString();
				if(!stm.endsWith("\\")){
					s = new File(stm+"\\RigVedamMoolam.pdf");
					sk = new File(stm+"\\RigVedamKramam.pdf");
					sg = new File(stm+"\\RigVedamGhanam.pdf");
					sa = new File(stm+"\\RigVedamAithreyaBhramna.pdf");
					s1 = new File(stm+"\\KrishnaYajurKramam.pdf");
					s1g = new File(stm+"\\KrishnaYajurGhanam.pdf");
					s2 = new File(stm+"\\SuklaYajurKramam.pdf");
					s2g = new File(stm+"\\SuklaYajurGhanam.pdf");
					s3 = new File(stm+"\\SamaVedamPoorvabagh.pdf");
					s3u = new File(stm+"\\SamaVedamUtterabagh.pdf");
				} else {
					s = new File(stm+"RigVedamMoolam.pdf");
					sk = new File(stm+"RigVedamKramam.pdf");
					sg = new File(stm+"RigVedamGhanam.pdf");
					sa = new File(stm+"RigVedamAithreyaBhramna.pdf");
					s1 = new File(stm+"KrishnaYajurKramam.pdf");
					s1g = new File(stm+"KrishnaYajurGhanam.pdf");
					s2 = new File(stm+"SuklaYajurKramam.pdf");
					s2g = new File(stm+"SuklaYajurGhanam.pdf");
					s3 = new File(stm+"SamaVedamPoorvabagh.pdf");
					s3u = new File(stm+"SamaVedamUtterabagh.pdf");
				}
			}
			PdfWriter writer = null;
			Document doc = new Document();
			Document dock = new Document();
			Document docg = new Document();
			Document doca = new Document();
			Document doc1 = new Document();
			Document doc1g = new Document();
			Document doc2 = new Document();
			Document doc2g = new Document();
			Document doc3 = new Document();
			Document doc3u = new Document();
			com.itextpdf.text.Font n = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			com.itextpdf.text.Font i = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 8);
			com.itextpdf.text.Font ns = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(s));
				writer = PdfWriter.getInstance(dock, new FileOutputStream(sk));
				writer = PdfWriter.getInstance(docg, new FileOutputStream(sg));
				writer = PdfWriter.getInstance(doca, new FileOutputStream(sa));
				writer = PdfWriter.getInstance(doc1, new FileOutputStream(s1));
				writer = PdfWriter.getInstance(doc1g, new FileOutputStream(s1g));
				writer = PdfWriter.getInstance(doc2, new FileOutputStream(s2));
				writer = PdfWriter.getInstance(doc2g, new FileOutputStream(s2g));
				writer = PdfWriter.getInstance(doc3, new FileOutputStream(s3));
				writer = PdfWriter.getInstance(doc3u, new FileOutputStream(s3u));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String cap = JOptionPane.showInputDialog(null, "Enter Term Exam Title");
			PdfPTable caption = new PdfPTable(1);
			PdfPCell caps = new PdfPCell(new Phrase(cap.toUpperCase(), ns));
			caps.setHorizontalAlignment(Element.ALIGN_CENTER);
			caps.setBorder(0);
			
			caption.addCell(caps);
			
			doc.setPageSize(PageSize.A4);
			doc.open();
			dock.setMarginMirroring(true);
			dock.setPageSize(PageSize.A4);
			dock.open();
			dock.setMarginMirroring(true);
			docg.setPageSize(PageSize.A4);
			docg.open();
			docg.setMarginMirroring(true);
			doca.setPageSize(PageSize.A4);
			doca.open();
			doca.setMarginMirroring(true);
			PdfPTable table = new PdfPTable(6);
			
			table.setComplete(true);
			table.setWidthPercentage(100);
			try {
				table.setWidths(new int[]{40, 80, 80, 50, 50, 30});
				
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			table.addCell(new Phrase("Reg No", n));
			table.addCell(new Phrase("Name", n));
			table.addCell(new Phrase("Patasala", n));
			table.addCell(new Phrase("Photo", n));
			table.addCell(new Phrase("Percentage", n));
			table.addCell(new Phrase("Class", n));
			table.setHeaderRows(1);
			PdfPTable tablen = new PdfPTable(6);
			tablen.setComplete(true);
			tablen.setWidthPercentage(100);
			try {
				tablen.setWidths(new int[]{40, 80, 80, 50, 50, 30});
				
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			tablen.addCell(new Phrase("Reg No", n));
			tablen.addCell(new Phrase("Name", n));
			tablen.addCell(new Phrase("Patasala", n));
			tablen.addCell(new Phrase("Photo", n));
			tablen.addCell(new Phrase("Percentage", n));
			tablen.addCell(new Phrase("Class", n));
			tablen.setHeaderRows(1);
			PdfPTable tablens = new PdfPTable(6);
			tablens.setComplete(true);
			tablens.setWidthPercentage(100);
			try {
				tablens.setWidths(new int[]{40, 80, 80, 50, 50, 30});
				
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			tablens.addCell(new Phrase("Reg No", n));
			tablens.addCell(new Phrase("Name", n));
			tablens.addCell(new Phrase("Patasala", n));
			tablens.addCell(new Phrase("Photo", n));
			tablens.addCell(new Phrase("Percentage", n));
			tablens.addCell(new Phrase("Class", n));
			tablens.setHeaderRows(1);
			PdfPTable tablena = new PdfPTable(6);
			tablena.setComplete(true);
			tablena.setWidthPercentage(100);
			try {
				tablena.setWidths(new int[]{40, 80, 80, 50, 50, 30});
				
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			tablena.addCell(new Phrase("Reg No", n));
			tablena.addCell(new Phrase("Name", n));
			tablena.addCell(new Phrase("Patasala", n));
			tablena.addCell(new Phrase("Photo", n));
			tablena.addCell(new Phrase("Percentage", n));
			tablena.addCell(new Phrase("Class", n));
			tablena.setHeaderRows(1);
			try{
				Class.forName("org.h2.Driver");
	   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
				Statement stm=conn.createStatement();
				String st = "select * from (candidate) where vedam = 'Rig Vedam' order by reg_no ";
				ResultSet rs = stm.executeQuery(st);
				//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
				//rs = pre.executeQuery();
				
				while(rs.next()){
					//int r = rs.getInt(1);
					
					String rr = rs.getString(2);
					String nn = rs.getString(3);					
					String ff = rs.getString(8);
					String part = rs.getString(10);				
					//String a11 = rs.getString(5);
					//String a12 = rs.getString(6);
					//String cc = rs.getString(7);
					float res = rs.getFloat(12);
					Blob b = rs.getBlob(13);
					InputStream in = b.getBinaryStream();
					byte by[];
					by = new byte[(int)b.length()];
					by = b.getBytes(1, (int)b.length());
					//String temp = System.getProperty("java.io.tmpdir");
					File f1 = null;
					try {
						
						f1 = File.createTempFile("vrnt_pic", ".jpg");
						FileOutputStream out = new FileOutputStream(f1);
						//in.read(by);
						out.write(by);
						out.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					com.itextpdf.text.Image im_c = null;
					try {
						im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
					} catch (BadElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					f1.deleteOnExit();
					String cl = null;
					if (res > 50){
					if (res >= 75 && res <= 100){
						cl = "I";
					} else if (res >= 60 && res < 75){
						cl = "II";
					} else if (res >= 50 && res < 60){
						cl = "III";
					} else {
						cl = "RA";
					}
						
					
					Phrase p1 = new Phrase(cl, i);
					//PdfPCell pr = new PdfPCell(p1);
					Phrase p2 = new Phrase(rr.toUpperCase(), i);
					Phrase p3 = new Phrase(nn.toUpperCase(), i);
					Phrase p4 = new Phrase(ff.toUpperCase(), i);
					Phrase p5 = new Phrase(String.valueOf(res), i);
					PdfPCell img = new PdfPCell(im_c);
					im_c.scaleToFit(100,60);
					img.setFixedHeight(80);
					img.setHorizontalAlignment(Element.ALIGN_CENTER);
					img.setVerticalAlignment(Element.ALIGN_MIDDLE);
					if(part.equals("Moolam")){
						
						
						table.addCell(p2);
						table.addCell(p3);
						table.addCell(p4);
						table.addCell(img);
						table.addCell(p5);
						table.addCell(p1);
					} else if(part.equals("Kramam")){
						
						
						tablen.addCell(p2);
						tablen.addCell(p3);
						tablen.addCell(p4);
						tablen.addCell(img);
						tablen.addCell(p5);
						tablen.addCell(p1);
					} else if(part.equals("Ghanam")){
						
						
						tablens.addCell(p2);
						tablens.addCell(p3);
						tablens.addCell(p4);
						tablens.addCell(img);
						tablens.addCell(p5);
						tablens.addCell(p1);
					}else if(part.equals("Aithreya Bhramna")){
						
						
						tablena.addCell(p2);
						tablena.addCell(p3);
						tablena.addCell(p4);
						tablena.addCell(img);
						tablena.addCell(p5);
						tablena.addCell(p1);
						
					}
				  } else {
					  doc.add(new Phrase("\0"));
					  //doc.close();
					  dock.add(new Phrase("\0"));
					  //dock.close();
					  docg.add(new Phrase("\0"));
					  //docg.close();
					  
				  }
					
				}
				
				PdfPCell tit;
				try {
					tit = new PdfPCell(new Phrase("Rig Vedam - Moolam", n));
					tit.setHorizontalAlignment(Element.ALIGN_CENTER);
					tit.setBorder(0);
					caption.addCell(tit);
					doc.add(caption);
					doc.add(new Phrase("\0"));
					doc.add(table);
					doc.close();
					caption.deleteLastRow();
					tit = new PdfPCell(new Phrase("Rig Vedam - Kramam", n));
					tit.setHorizontalAlignment(Element.ALIGN_CENTER);
					tit.setBorder(0);
					caption.addCell(tit);
					dock.add(caption);
					dock.add(new Phrase("\0"));
					dock.add(tablen);
					dock.close();
					caption.deleteLastRow();
					tit = new PdfPCell(new Phrase("Rig Vedam - Ghanam", n));
					tit.setHorizontalAlignment(Element.ALIGN_CENTER);
					tit.setBorder(0);
					caption.addCell(tit);
					docg.add(caption);
					docg.add(new Phrase("\0"));
					docg.add(tablens);
					docg.close();
					caption.deleteLastRow();
					tit = new PdfPCell(new Phrase("Rig Vedam - Aithreya Bhramna", n));
					tit.setHorizontalAlignment(Element.ALIGN_CENTER);
					tit.setBorder(0);
					caption.addCell(tit);
					doca.add(caption);
					doca.add(new Phrase("\0"));
					doca.add(tablena);
					doca.close();
					caption.deleteLastRow();
					
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (ClassNotFoundException e)
			{
				Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
			catch(SQLException ee)
			{
				System.err.println(ee);
				Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				doc1.setPageSize(PageSize.A4);
				doc1.open();
				doc1.setMarginMirroring(true);
				doc1g.setPageSize(PageSize.A4);
				doc1g.open();
				doc1g.setMarginMirroring(true);
				PdfPTable table1 = new PdfPTable(6);
				table1.setComplete(true);
				table1.setWidthPercentage(100);
				try {
					table1.setWidths(new int[]{40, 80, 80, 50, 50, 30});
					
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				table1.addCell(new Phrase("Reg No", n));
				table1.addCell(new Phrase("Name", n));
				table1.addCell(new Phrase("Patasala", n));
				table1.addCell(new Phrase("Photo", n));
				table1.addCell(new Phrase("Percentage", n));
				table1.addCell(new Phrase("Class", n));
				table1.setHeaderRows(1);
				PdfPTable table1g = new PdfPTable(6);
				table1g.setComplete(true);
				table1g.setWidthPercentage(100);
				try {
					table1g.setWidths(new int[]{40, 80, 80, 50, 50, 30});
					
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				table1g.addCell(new Phrase("Reg No", n));
				table1g.addCell(new Phrase("Name", n));
				table1g.addCell(new Phrase("Patasala", n));
				table1g.addCell(new Phrase("Photo", n));
				table1g.addCell(new Phrase("Percentage", n));
				table1g.addCell(new Phrase("Class", n));
				table1g.setHeaderRows(1);
				try{
					Class.forName("org.h2.Driver");
		   			Connection conn1=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm1=conn1.createStatement();
					String st1 = "select * from (candidate) where vedam = 'Krishnayajur Vedam' order by reg_no ";
					ResultSet rs1 = stm1.executeQuery(st1);
					//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
					//rs = pre.executeQuery();
					
					while(rs1.next()){
						//int r = rs1.getInt(1);
						
						String rr = rs1.getString(2);
						String nn = rs1.getString(3);
						String ff = rs1.getString(8);
						String part = rs1.getString(10);
						//String a11 = rs.getString(5);
						//String a12 = rs.getString(6);
						//String cc = rs.getString(7);
						float res = rs1.getFloat(12);
						Blob b = rs1.getBlob(13);
						InputStream in = b.getBinaryStream();
						byte by[];
						by = new byte[(int)b.length()];
						by = b.getBytes(1, (int)b.length());
						//String temp = System.getProperty("java.io.tmpdir");
						File f1 = null;
						try {
							
							f1 = File.createTempFile("vrnt_pic", ".jpg");
							FileOutputStream out = new FileOutputStream(f1);
							//in.read(by);
							out.write(by);
							out.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						com.itextpdf.text.Image im_c = null;
						try {
							im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
						} catch (BadElementException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						f1.deleteOnExit();
						String cl = null;
						if (res > 50){
						if (res >= 75 && res <= 100){
							cl = "I";
						} else if (res >= 60 && res < 75){
							cl = "II";
						} else if (res >= 50 && res < 60){
							cl = "III";
						} else {
							cl = "RA";
						}
							
						
						Phrase p1 = new Phrase(cl, i);
						//PdfPCell pr = new PdfPCell(p1);
						Phrase p2 = new Phrase(rr.toUpperCase(), i);
						Phrase p3 = new Phrase(nn.toUpperCase(), i);
						Phrase p4 = new Phrase(ff.toUpperCase(), i);
						Phrase p5 = new Phrase(String.valueOf(res), i);
						PdfPCell img = new PdfPCell(im_c);
						im_c.scaleToFit(100,60);
						img.setFixedHeight(80);
						img.setHorizontalAlignment(Element.ALIGN_CENTER);
						img.setVerticalAlignment(Element.ALIGN_MIDDLE);
						if (part.equals("Kramam")){
							
							table1.addCell(p2);
							table1.addCell(p3);
							table1.addCell(p4);
							table1.addCell(img);
							table1.addCell(p5);
							table1.addCell(p1);
						} else if (part.equals("Ghanam")){
							
							table1g.addCell(p2);
							table1g.addCell(p3);
							table1g.addCell(p4);
							table1g.addCell(img);
							table1g.addCell(p5);
							table1g.addCell(p1);
						}
					  } else {
						  doc1.add(new Phrase("\0"));
						  //doc1.close();
						  doc1g.add(new Phrase("\0"));
						  //doc1g.close();
						  
						  
					  }
						
					}
					PdfPCell tit;
					try {
						tit = new PdfPCell(new Phrase("Krishnayajur Vedam - Kramam\n", n));
						tit.setHorizontalAlignment(Element.ALIGN_CENTER);
						tit.setBorder(0);
						caption.addCell(tit);
						doc1.add(caption);
						doc1.add(new Phrase("\0"));
						doc1.add(table1);
						doc1.close();
						caption.deleteLastRow();
						tit = new PdfPCell(new Phrase("Krishnayajur Vedam - Ghanam\n", n));
						tit.setHorizontalAlignment(Element.ALIGN_CENTER);
						tit.setBorder(0);
						caption.addCell(tit);
						doc1g.add(caption);
						doc1g.add(new Phrase("\0"));
						doc1g.add(table1g);
						doc1g.close();
						caption.deleteLastRow();
						
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}catch (ClassNotFoundException e)
				{
					Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					doc2.setPageSize(PageSize.A4);
					doc2.open();
					doc2.setMarginMirroring(true);
					doc2g.setPageSize(PageSize.A4);
					doc2g.open();
					doc2g.setMarginMirroring(true);
					PdfPTable table2 = new PdfPTable(6);
					table2.setComplete(true);
					table2.setWidthPercentage(100);
					try {
						table2.setWidths(new int[]{40, 80, 80, 50, 50, 30});
						
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table2.addCell(new Phrase("Reg No", n));
					table2.addCell(new Phrase("Name", n));
					table2.addCell(new Phrase("Patasala", n));
					table2.addCell(new Phrase("Photo", n));
					table2.addCell(new Phrase("Percentage", n));
					table2.addCell(new Phrase("Class", n));
					table2.setHeaderRows(1);
					PdfPTable table2g = new PdfPTable(6);
					table2g.setComplete(true);
					table2g.setWidthPercentage(100);
					try {
						table2g.setWidths(new int[]{40, 80, 80, 50, 50, 30});
						
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table2g.addCell(new Phrase("Reg No", n));
					table2g.addCell(new Phrase("Name", n));
					table2g.addCell(new Phrase("Patasala", n));
					table2g.addCell(new Phrase("Photo", n));
					table2g.addCell(new Phrase("Percentage", n));
					table2g.addCell(new Phrase("Class", n));
					table2g.setHeaderRows(1);
					try{
						Class.forName("org.h2.Driver");
			   			Connection conn2=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
						Statement stm2=conn2.createStatement();
						String st2 = "select * from (candidate) where vedam = 'Suklayajur Vedam' order by reg_no ";
						ResultSet rs2 = stm2.executeQuery(st2);
						//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
						//rs = pre.executeQuery();
						
						while(rs2.next()){
							//int r = rs2.getInt(1);
							
							String rr = rs2.getString(2);
							String nn = rs2.getString(3);
							String ff = rs2.getString(8);
							String part = rs2.getString(10);
							//String a11 = rs.getString(5);
							//String a12 = rs.getString(6);
							//String cc = rs.getString(7);
							float res = rs2.getFloat(12);
							Blob b = rs2.getBlob(13);
							InputStream in = b.getBinaryStream();
							byte by[];
							by = new byte[(int)b.length()];
							by = b.getBytes(1, (int)b.length());
							//String temp = System.getProperty("java.io.tmpdir");
							File f1 = null;
							try {
								
								f1 = File.createTempFile("vrnt_pic", ".jpg");
								FileOutputStream out = new FileOutputStream(f1);
								//in.read(by);
								out.write(by);
								out.close();
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							com.itextpdf.text.Image im_c = null;
							try {
								im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
							} catch (BadElementException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							f1.deleteOnExit();
							String cl = null;
							if (res > 50){
							if (res >= 75 && res <= 100){
								cl = "I";
							} else if (res >= 60 && res < 75){
								cl = "II";
							} else if (res >= 50 && res < 60){
								cl = "III";
							} else {
								cl = "RA";
							}
								
							
							Phrase p1 = new Phrase(cl, i);
							//PdfPCell pr = new PdfPCell(p1);
							Phrase p2 = new Phrase(rr.toUpperCase(), i);
							Phrase p3 = new Phrase(nn.toUpperCase(), i);
							Phrase p4 = new Phrase(ff.toUpperCase(), i);
							Phrase p5 = new Phrase(String.valueOf(res), i);
							PdfPCell img = new PdfPCell(im_c);
							im_c.scaleToFit(100,60);
							img.setFixedHeight(80);
							img.setHorizontalAlignment(Element.ALIGN_CENTER);
							img.setVerticalAlignment(Element.ALIGN_MIDDLE);
							if (part.equals("Kramam")){
								
								table2.addCell(p2);
								table2.addCell(p3);
								table2.addCell(p4);
								table2.addCell(img);
								table2.addCell(p5);
								table2.addCell(p1);
							} else if (part.equals("Ghanam")){
								
								table2g.addCell(p2);
								table2g.addCell(p3);
								table2g.addCell(p4);
								table2g.addCell(img);
								table2g.addCell(p5);
								table2g.addCell(p1);
							}
						  } else {
							  doc2.add(new Phrase("\0"));
							  //doc2.close();
							  doc2g.add(new Phrase("\0"));
							  //doc2g.close();
							 
							  
						  }
							
						}
						PdfPCell tit;
						try {
							tit = new PdfPCell(new Phrase("Suklayajur Vedam - Kramam\n", n));
							tit.setHorizontalAlignment(Element.ALIGN_CENTER);
							tit.setBorder(0);
							caption.addCell(tit);
							doc2.add(caption);
							doc2.add(new Phrase("\0"));
							doc2.add(table2);
							doc2.close();
							caption.deleteLastRow();
							tit = new PdfPCell(new Phrase("Suklayajur Vedam - Ghanam\n", n));
							tit.setHorizontalAlignment(Element.ALIGN_CENTER);
							tit.setBorder(0);
							caption.addCell(tit);
							doc2g.add(caption);
							doc2g.add(new Phrase("\0"));
							doc2g.add(table2g);
							doc2g.close();
							caption.deleteLastRow();
							
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}catch (ClassNotFoundException e)
					{
						Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
						TelegraphQueue que = new TelegraphQueue();
						que.add(tele);
						//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
					}
					catch(SQLException ee)
					{
						System.err.println(ee);
						Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
						TelegraphQueue que = new TelegraphQueue();
						que.add(tele);
						//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					doc3.setPageSize(PageSize.A4);
					doc3.open();
					doc3.setMarginMirroring(true);
					doc3u.setPageSize(PageSize.A4);
					doc3u.open();
					doc3u.setMarginMirroring(true);
					PdfPTable table3 = new PdfPTable(6);
					table3.setComplete(true);
					table3.setWidthPercentage(100);
					try {
						table3.setWidths(new int[]{40, 80, 80, 50, 50, 30});
						
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table3.addCell(new Phrase("Reg No", n));
					table3.addCell(new Phrase("Name", n));
					table3.addCell(new Phrase("Patasala", n));
					table3.addCell(new Phrase("Photo", n));
					table3.addCell(new Phrase("Percentage", n));
					table3.addCell(new Phrase("Class", n));
					table3.setHeaderRows(1);
					PdfPTable table3u = new PdfPTable(6);
					table3u.setComplete(true);
					table3u.setWidthPercentage(100);
					try {
						table3u.setWidths(new int[]{40, 80, 80, 50, 50, 30});
						
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					table3u.addCell(new Phrase("Reg No", n));
					table3u.addCell(new Phrase("Name", n));
					table3u.addCell(new Phrase("Patasala", n));
					table3u.addCell(new Phrase("Photo", n));
					table3u.addCell(new Phrase("Percentage", n));
					table3u.addCell(new Phrase("Class", n));
					table3u.setHeaderRows(1);
					try{
						Class.forName("org.h2.Driver");
			   			Connection conn3=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
						Statement stm3=conn3.createStatement();
						String st3 = "select * from (candidate) where vedam = 'Sama Vedam' order by reg_no ";
						ResultSet rs3 = stm3.executeQuery(st3);
						//PreparedStatement pre = conn.prepareStatement("select photo from candidate");
						//rs = pre.executeQuery();
						
						while(rs3.next()){
							//int r = rs3.getInt(1);
							
							String rr = rs3.getString(2);
							String nn = rs3.getString(3);
							String ff = rs3.getString(8);
							String part = rs3.getString(10);
							//String a11 = rs.getString(5);
							//String a12 = rs.getString(6);
							//String cc = rs.getString(7);
							float res = rs3.getFloat(12);
							Blob b = rs3.getBlob(13);
							InputStream in = b.getBinaryStream();
							byte by[];
							by = new byte[(int)b.length()];
							by = b.getBytes(1, (int)b.length());
							//String temp = System.getProperty("java.io.tmpdir");
							File f1 = null;
							try {
								
								f1 = File.createTempFile("vrnt_pic", ".jpg");
								FileOutputStream out = new FileOutputStream(f1);
								//in.read(by);
								out.write(by);
								out.close();
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							com.itextpdf.text.Image im_c = null;
							try {
								im_c = com.itextpdf.text.Image.getInstance(f1.getAbsolutePath());
							} catch (BadElementException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							f1.deleteOnExit();
							String cl = null;
							if (res > 50){
							if (res >= 75 && res <= 100){
								cl = "I";
							} else if (res >= 60 && res < 75){
								cl = "II";
							} else if (res >= 50 && res < 60){
								cl = "III";
							} else {
								cl = "RA";
							}
								
							
							Phrase p1 = new Phrase(cl, i);
							//PdfPCell pr = new PdfPCell(p1);
							Phrase p2 = new Phrase(rr.toUpperCase(), i);
							Phrase p3 = new Phrase(nn.toUpperCase(), i);
							Phrase p4 = new Phrase(ff.toUpperCase(), i);
							Phrase p5 = new Phrase(String.valueOf(res), i);
							PdfPCell img = new PdfPCell(im_c);
							im_c.scaleToFit(100,60);
							img.setFixedHeight(80);
							img.setHorizontalAlignment(Element.ALIGN_CENTER);
							img.setVerticalAlignment(Element.ALIGN_MIDDLE);
							if (part.equals("Poorva Bhagam")){
								
								table3.addCell(p2);
								table3.addCell(p3);
								table3.addCell(p4);
								table3.addCell(img);
								table3.addCell(p5);
								table3.addCell(p1);
							} else if (part.equals("Uttera Bhagam")){
								
								table3u.addCell(p2);
								table3u.addCell(p3);
								table3u.addCell(p4);
								table3u.addCell(img);
								table3u.addCell(p5);
								table3u.addCell(p1);
							}
						  } else {
							  doc3.add(new Phrase("\0"));
							  //doc3.close();
							  doc3u.add(new Phrase("\0"));
							  //doc3u.close();
							 
							  
						  }
							
						}
						PdfPCell tit;
						try {
							tit = new PdfPCell(new Phrase("Sama Vedam - PoorvaBhagam\n", n));
							tit.setHorizontalAlignment(Element.ALIGN_CENTER);
							tit.setBorder(0);
							caption.addCell(tit);
							doc3.add(caption);
							doc3.add(new Phrase("\0"));
							doc3.add(table3);
							doc3.close();
							caption.deleteLastRow();
							tit = new PdfPCell(new Phrase("Sama Vedam - UtteraBhagam\n", n));
							tit.setHorizontalAlignment(Element.ALIGN_CENTER);
							tit.setBorder(0);
							caption.addCell(tit);
							doc3u.add(caption);
							doc3u.add(new Phrase("\0"));
							doc3u.add(table3u);
							doc3u.close();
							caption.deleteLastRow();
							
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}catch (ClassNotFoundException e)
					{
						Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
						TelegraphQueue que = new TelegraphQueue();
						que.add(tele);
						//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
					}
					catch(SQLException ee)
					{
						System.err.println(ee);
						Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
						TelegraphQueue que = new TelegraphQueue();
						que.add(tele);
						//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				Telegraph tele = new Telegraph("Success", "Result Statement Generated Successfully...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
				TelegraphQueue que = new TelegraphQueue();
				que.add(tele);
				
			}
			
		if(src == res_csv){
			int ret = imp.showSaveDialog(this);
			File s;
			if(ret == JFileChooser.APPROVE_OPTION){
				s = imp.getSelectedFile();
				String file_name = s.toString();
				if (!file_name.toLowerCase().endsWith(".csv")){
					s = new File(file_name+".csv");
				}
				Connection conn = null;
				try{
					Class.forName("org.h2.Driver");
					conn = DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
					Statement stm = conn.createStatement();
					String st = "call csvwrite('"+s+"', 'select reg_no, name, patasala, vedam, part, percentage from candidate')";
					stm.executeUpdate(st);
					Telegraph tele = new Telegraph("Success", "Successfully Exported...", TelegraphType.NOTIFICATION_DONE, WindowPosition.BOTTOMRIGHT, 4000);
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null, "Successfully Exported...", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e)
				{
					Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException ee)
				{
					System.err.println(ee);
					Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
					TelegraphQueue que = new TelegraphQueue();
					que.add(tele);
					//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
			
			
			
		
	
		
		if (src == result){
			dispose();
			resFrame();
		}
		
		if (src == back2){
			resF.dispose();
			main(null);
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		int row = view_table.rowAtPoint(e.getPoint());
		int RollNo =  (Integer) model.getValueAt(row, 0);		
		String value = (String) model.getValueAt(row, 1);
		String Name = (String) model.getValueAt(row, 2);
		//System.out.println(value);
		try{
			Class.forName("org.h2.Driver");
   			Connection conn=DriverManager.getConnection("jdbc:h2:~/vrnt_hallTkt","kanchimatam","kanchimatam");
			Statement stm=conn.createStatement();
			
			String st1 = "select photo from candidate where reg_no = '"+value+"'";
			ResultSet rs = stm.executeQuery(st1);
			if(rs.first()){
				Blob bl = rs.getBlob(1);
				byte by[];
				by = new byte[(int)bl.length()];
				by = bl.getBytes(1, (int)bl.length());
				//String temp = System.getProperty("java.io.tmpdir");
				File f = null;
				try {
					f = File.createTempFile("vrnt_pic", ".jpg");
					FileOutputStream out = new FileOutputStream(f);
					//in.read(by);
					out.write(by);
					out.close();
					f.deleteOnExit();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} 
				
				try {
					ic = new ImageIcon(ImageIO.read(f));
					Toaster tos = new Toaster();
					tos.setToasterHeight(220);
					tos.setToasterWidth(400);
					tos.showToaster(ic, "Roll No. "+RollNo+"\nReg No. "+value+"\nName "+Name);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				//for_img.setIcon(ic);
				
				//new ImageRenderer().label.setToolTipText("<html><img src = "+f.getAbsolutePath()+"> </html>");
			} else {
				Telegraph tele = new Telegraph("Error", "Error loading image...", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
				TelegraphQueue quee = new TelegraphQueue();
				quee.add(tele);
				//JOptionPane.showMessageDialog(null, "Error loading image...", "Error", JOptionPane.ERROR_MESSAGE);
			}
			conn.close();
		}
		catch (ClassNotFoundException e4)
		{
			Telegraph tele = new Telegraph("Warning!", "Please Check Your Database Driver", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue quee = new TelegraphQueue();
			quee.add(tele);
			//JOptionPane.showMessageDialog(null,"Please Check Your Database Driver", "Warning!",JOptionPane.WARNING_MESSAGE);
		}
		catch(SQLException ee)
		{
			System.err.println(ee);
			Telegraph tele = new Telegraph("Warning!", "Some problem might be occured in Database", TelegraphType.NOTIFICATION_WARNING, WindowPosition.BOTTOMRIGHT, 4000);				
			TelegraphQueue quee = new TelegraphQueue();
			quee.add(tele);
			//JOptionPane.showMessageDialog(null,"Some problem might be occured in Database","Warning!",JOptionPane.WARNING_MESSAGE);
		}
	  
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
	

}
