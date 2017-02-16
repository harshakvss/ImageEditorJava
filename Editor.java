import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.color.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.imageio.stream.*;

////start the ImgArea class
//The ImgArea class acts as a drawing area of the Image Editor program

class ImgArea extends Canvas{ 

  Image Img;
  BufferedImage BufferedImg;
  BufferedImage bufimg; 
  BufferedImage bufimg1; 
  float e;
  float radian;
  Dimension ds;
  int mX;
  int mY;
  int x;
  int y;
  static boolean imgLoad;
  boolean actionSlided;
  boolean actionResized;
  boolean actionCompressed;
  boolean actionTransparent;
  boolean actionRotated;
  boolean actionDraw;
  boolean drawn;
  MediaTracker mt;
  static Color c;
  Color colorTextDraw;
  Robot rb;
  boolean dirHor;
  String imgFileName;
  String fontName;
  int fontSize;
  String textToDraw;
  public ImgArea(){

   
  
	  addMouseListener(new mousexy());
	 
	   addKeyListener(new KeyList()); 

	   try{
	    rb=new Robot(); 
	   }catch(AWTException e){}

	   ds=getToolkit().getScreenSize();    
	   mX=(int)ds.getWidth()/2; 
	   mY=(int)ds.getHeight()/2;
	   
	  }
	  
	  public void paint(Graphics g){
	   Graphics2D g2d=(Graphics2D)g;   
	   if(imgLoad){

		    
		    if(actionSlided || actionResized || actionTransparent || actionRotated || drawn ){
		     x=mX-bufimg.getWidth()/2;
		     y=mY-bufimg.getHeight()/2;
		     g2d.translate(x,y);  
		     g2d.drawImage(bufimg,0,0,null); 
		     
		     }
	 
		    else{
	     
	   
	     x=mX-BufferedImg.getWidth()/2;
	     y=mY-BufferedImg.getHeight()/2;
	     g2d.translate(x,y); 
	     g2d.drawImage(BufferedImg,0,0,null); 
	     
		    }}
	   g2d.dispose(); 
	   
	  }

	  class mousexy extends MouseAdapter{
	   
	   public void mousePressed(MouseEvent e){
	 
	   }
	   
	   
	  }
	  

	

	 
	  class KeyList extends KeyAdapter{
	   public void keyPressed(KeyEvent e){
	    if(e.getKeyCode()==27){ 
	     actionDraw=false;
	     textToDraw="";
	     fontName="";
	     fontSize=0;
	     }
	    }
	   }

  
  public void prepareImg(String filename){
   
   try{
  
   mt=new MediaTracker(this);    
   Img=Toolkit.getDefaultToolkit().getImage(filename); 
   mt.addImage(Img,0);
    mt.waitForID(0); 
    
   int width=Img.getWidth(null);
   int height=Img.getHeight(null);
   
   BufferedImg=createBufferedImageFromImage(Img,width,height,false);
   
   bufimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
   imgLoad=true;
   }catch(Exception e){System.exit(-1);}
  }
	
	 public BufferedImage createBufferedImageFromImage(Image image, int width, int height, boolean tran)
   { BufferedImage dest ;
  if(tran) 
       dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  else
   dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       Graphics2D g2 = dest.createGraphics();
       g2.drawImage(image, 0, 0, null);
       g2.dispose();
       return dest;
   }
 

 public void filterImage(){
	 float[] elements={0.0f, 1.0f, 0.0f, -1.0f,e,1.0f,0.0f,0.0f,0.0f};
	 Kernel kernel=new Kernel(3,3,elements);//create kernel object to encapsulate the elements array
	 ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); //create ConvolveOp to encapsulate the kernel
	 bufimg= new BufferedImage(BufferedImg.getWidth(),BufferedImg.getHeight(),BufferedImage.TYPE_INT_RGB);
	 cop.filter(BufferedImg,bufimg); 
	 
	  
 }

 public void setValue(float value){ 
  e=value;
 }

 public void setActionSlided(boolean value ){ 
  actionSlided=value;
 }

 public void initialize(){
	   imgLoad=false; 
	   actionSlided=false;
	   actionResized=false;
	   actionCompressed=false;
	   actionTransparent=false;
	   actionRotated=false;
	   actionDraw=false;
	   drawn=false;
	   dirHor=false;
	   c=null;
	   radian=0.0f;
	   e=0.0f;
	   }
 //cancelling the editing done so far
 public void reset(){
 if(imgLoad){
 prepareImg(imgFileName);
 repaint();
 }
 
}
	
	
 
 
 public void ImgResize(int w,int h){
   BufferedImage bi=(BufferedImage)createImage(w,h);
   Graphics2D g2d=(Graphics2D)bi.createGraphics();
   

   if(actionSlided || actionTransparent || actionRotated ||drawn)
    g2d.drawImage(bufimg,0,0,w,h,null);
 
   else
    g2d.drawImage(BufferedImg,0,0,w,h,null);
   bufimg=bi;
   g2d.dispose();
  
 }

 public void setActionResized(boolean value ){ 
  actionResized=value;
 } 
	 public void ImgRotation(BufferedImage image,int w,int h){
	   
	   BufferedImage bi=(BufferedImage)createImage(w,h);
	   Graphics2D  g2d=(Graphics2D)bi.createGraphics(); 
	   radian=(float)Math.PI/2;     
	   g2d.translate(w/2,h/2); 
	   g2d.rotate(radian); 
	   g2d.translate(-h/2,-w/2); 
	   g2d.drawImage(image,0,0,null); 
	   bufimg=bi; 
	   g2d.dispose();  
	   
	   
	  }
	  
	  public void rotateImage(){
	    BufferedImage bi;
	    
	    if(actionSlided || actionResized || actionTransparent || actionRotated || drawn){
	     bi=bufimg;     
	    }
	     
	    else{
	     bi=BufferedImg;
	    }

	   ImgRotation(bi,bi.getHeight(),bi.getWidth());
	        
	    actionRotated=true; 
	    repaint(); 
	     
	   }
 
}

public class Editor{
	  
	 public static void main(String args[]){
	       Main m=new Main();
	   
	 }
	}

  
