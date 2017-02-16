import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame implements ActionListener{
	 ImgArea ima;
	 JFileChooser Choose; 
	 JMenuBar mainmenu;
	 JMenu menu;
	 JMenu editmenu;
	 JMenuItem open;
	 JMenuItem saveas;
	 JMenuItem save;
	 JMenuItem exit; 
	 JMenuItem bright; 
	 JMenuItem compress; 
	 JMenuItem resize;
	 JMenuItem rotate;
	 JMenuItem transparent;
	 JMenuItem addingtext;
	 JMenuItem cancel;
	 String filename;
	 Main(){
	  ima=new ImgArea();
	  Container cont=getContentPane();
	  cont.add(ima,BorderLayout.CENTER );  
	  mainmenu=new JMenuBar();
	  //for file Menu
	  menu=new JMenu("File");
	  menu.setMnemonic(KeyEvent.VK_F);
	  
	  //for option open  in file menu
	  open=new JMenuItem("Open...");
	  open.setMnemonic(KeyEvent.VK_O);
	  open.addActionListener(this);

	  saveas=new JMenuItem("Save as...");
	  saveas.setMnemonic(KeyEvent.VK_S);
	  saveas.addActionListener(this);

	  save=new JMenuItem("Save");
	  save.setMnemonic(KeyEvent.VK_V);
	  save.addActionListener(this);  

	  exit=new JMenuItem("Exit");
	  exit.setMnemonic(KeyEvent.VK_X);
	  exit.addActionListener(this);
	  menu.add(open);
	  menu.add(saveas);
	  menu.add(save);
	  menu.add(exit);  

	  editmenu=new JMenu("Edit");
	  editmenu.setMnemonic(KeyEvent.VK_E);
	  bright=new JMenuItem("Image brightness");
	  bright.setMnemonic(KeyEvent.VK_B);
	  bright.addActionListener(this);

	  addingtext=new JMenuItem("Add text on image");
	  addingtext.setMnemonic(KeyEvent.VK_A);
	  addingtext.addActionListener(this);  

	  resize=new JMenuItem("Image resize");
	  resize.setMnemonic(KeyEvent.VK_R);
	  resize.addActionListener(this);
	 
	  compress=new JMenuItem("Image compression");
	  compress.setMnemonic(KeyEvent.VK_P);
	  compress.addActionListener(this);

	  rotate=new JMenuItem("Image rotation");
	  rotate.setMnemonic(KeyEvent.VK_T);
	  rotate.addActionListener(this);

	  transparent=new JMenuItem("Image transparency");
	  transparent.setMnemonic(KeyEvent.VK_T);
	  transparent.addActionListener(this);
	 
	  cancel=new JMenuItem("Cancel editing");
	  cancel.setMnemonic(KeyEvent.VK_L);
	  cancel.addActionListener(this);

	  editmenu.add(addingtext);
	  editmenu.add(bright);
	  editmenu.add(compress);
	  editmenu.add(resize);
	  editmenu.add(rotate);
	  editmenu.add(transparent);
	  editmenu.add(cancel);

	  mainmenu.add(menu);
	  mainmenu.add(editmenu);
	  setJMenuBar(mainmenu);
	 
	  setTitle("Image Editor");
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
	     setVisible(true); 

	  Choose = new JFileChooser();
	      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "gif","bmp","png");
	      Choose.setFileFilter(filter);
	      Choose.setMultiSelectionEnabled(false);
	  enableSaving(false);
	  ima.requestFocus();
	  }
	public class ImgBrighten extends JFrame implements ChangeListener{

		 JSlider slider;
		
		
		ImgBrighten(){
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					dispose();
				}
			});
			
			Container cont=getContentPane();
			slider=new JSlider(-10,10,0);
			slider.setEnabled(false);
			slider.addChangeListener(this);
			cont.add(slider, BorderLayout.CENTER);
			slider.setEnabled(true);
			setTitle("Image Brightness");
			setPreferredSize(new Dimension(300,100));
			setVisible(true);
			pack();
			
			enabledSlider(false);
					}
		public void enabledSlider(boolean enabled){
			slider.setEnabled(enabled);
		}
		
		public void stateChanged(ChangeEvent e) {
			ima.setValue(slider.getValue()/10.0f);
		    ima.setActionSlided(true);   
		    ima.filterImage();
		    ima.repaint();
		    enableSaving(true);
			
		}
		
		
		
		 
	 }
	//ImageResize class allows to resize the image by resizing width and height of image
	 public class ImgResized extends JFrame implements ActionListener {
	  JPanel panel;
	  JTextField textWidth;
	  JTextField textHeight;
	  JButton Okbt;
	  ImgResized(){
	  setTitle("Image resize");
	  //setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setPreferredSize(new Dimension(400,100));
	  
	  Okbt=new JButton("OK");
	  Okbt.setBackground(Color.BLACK);
	  Okbt.setForeground(Color.BLUE);  
	  Okbt.addActionListener(this);

	  textWidth=new JTextField(4);
	  textWidth.addKeyListener(new KeyList());
	  textHeight=new JTextField(4);
	  textHeight.addKeyListener(new KeyList());
	  panel=new JPanel();
	  panel.setLayout(new FlowLayout());
	  panel.add(new JLabel("Width:"));
	  panel.add(textWidth);
	  panel.add(new JLabel("Height:"));
	  
	  panel.add(textHeight);
	  panel.add(Okbt);
	  panel.setBackground(Color.GRAY);
	  add(panel, BorderLayout.CENTER);
	  setVisible(true);
	  pack();
	  enableComponents(false);
	  }
	  
	  public void enableComponents(boolean enabled){
	   textWidth.setEnabled(enabled);
	   textHeight.setEnabled(enabled);
	   Okbt.setEnabled(enabled);
	  }
	  
	  public void actionPerformed(ActionEvent e){
	   if(e.getSource()==Okbt){
	    ima.setActionResized(true);     
	    ima.ImgResize(Integer.parseInt(textWidth.getText()),Integer.parseInt(textHeight.getText()));
	    enableSaving(true);
	    ima.repaint();
	    }
	  }
	  
	  public class KeyList extends KeyAdapter{
	     public void keyTyped(KeyEvent ke){
	 
	    char c = ke.getKeyChar(); 
	    int intkey=(int)c;
	    if(!(intkey>=48 && intkey<=57 || intkey==8 || intkey==127))
	     {
	     ke.consume();
	  
	      }  
	     
	   }
	  
	  } 
	 }
	public void actionPerformed(ActionEvent e){

	  JMenuItem source = (JMenuItem)(e.getSource());
	  if(source.getText().compareTo("Open...")==0)
	    {
	    setImage();
	    ima.repaint();
	     validate();
	      
	     }
	  else if(source.getText().compareTo("Image brightness")==0)
	    {
	     
	    ImgBrighten ib=new ImgBrighten(); 
	    if(ImgArea.imgLoad)
	     ib.enabledSlider(true); 
	     }
	     
	  else if(source.getText().compareTo("Image resize")==0)
	    {
	     
	    ImgResized ir=new ImgResized();
	    if(ImgArea.imgLoad)
	     ir.enableComponents(true);  
	     }
		   else if(source.getText().compareTo("Image rotation")==0)
	    {
	     
	    if(ImgArea.imgLoad){
	     ima.rotateImage();
	     enableSaving(true);
	     } 
	    }
	  }
	public void setImage(){
	  
	  int retValue = Choose.showOpenDialog(this);
	      if(retValue == JFileChooser.APPROVE_OPTION) {   
	   filename=Choose.getSelectedFile().toString();
	   ima.prepareImg(filename);
	   }
	           
	  }



	 
	 public void enableSaving(boolean f){
	  saveas.setEnabled(f);
	  save.setEnabled(f); 
	  
	  }
	
	      
}
