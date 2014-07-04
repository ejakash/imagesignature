package imagewatermarking;

import javax.imageio.*;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.*;

/**
 *
 * @author Akash EJ
 */
public class watermarking_keyless {
    
    static int i;
    public static void main(String path) throws IOException 	

	{		
       // Save the image file as a BufferedImage object	
                        
    File f=new File(path);                    
    BufferedImage cat = ImageIO.read(f);
    f.delete();
    path=path.replace(".jpg_binary.png","");
    path=path.replace(".JPG_binary.png","");
// Loop through all the pixels in the image (w = width, h = height)		
                int x,y,m;
                int[][] sharemap=new int[cat.getWidth()][cat.getHeight()];
                int w,h;
 
                for(w=0;w<cat.getWidth();w++)
                    for(h=0;h<cat.getHeight();h++)
                        sharemap[w][h]=-1;
                
		for(w= 0; w < cat.getWidth()-2 ; w+=3)		

		{			
                    
			for(h = 0 ; h < cat.getHeight()-1 ; h+=2)			

			{	
                            int p1,p2,p3,p4,p5,p6;
                            p1=bin(w,h,cat);
                            p2=bin(w,h+1,cat);
                            p3=bin(w+1,h,cat);
                            p4=bin(w+1,h+1,cat);
                            p5=bin(w+2,h,cat);
                            p6=bin(w+2,h+1,cat);
                            int a1=p1^p2^p3;
                            int a2=p4^p5^p6;
                      //  System.out.println(p1+" "+p2+" "+p3+" "+p4+" "+p5+" "+p6+" "+a1+" "+a2);
                            int s1=a1*8+a2*4+p1*2+p2;
                            int s2=p3*8+p4*4+p5*2+p6;
                     //      System.out.println(s1+" "+s2);
                            int[] sharearray=shamir(s1,s2);
                            sharemap[w][h]=sharearray[0]+238;
                            sharemap[w][h+1]=sharearray[1]+238;
                            
                         

                            for(m=2;m<6;m++){
//                           while(y==h||y==h+1||mappedheight[y]!=1)
                       //    System.out.println("Got out of loop "+x+" "+y);
                           
                      //     System.out.println(sharearray[m]);
                           if(m==2)
                               
                           {
                               x=w+1;
                               y=(h+(cat.getHeight()/2))%cat.getHeight();
                           sharemap[x][y]=sharearray[m]+238;
                       //    System.out.println((w+1)+" "+h+" "+x+" "+y+" "+m+" "+sharearray[m]);
                           }
                           if(m==3)
                           {
                               x=w+1;
                               y=(h+1+(cat.getHeight()/2))%cat.getHeight();
                               sharemap[x][y]=sharearray[m]+238;
                     //      System.out.println((w+1)+" "+(h+1)+" "+x+" "+y+" "+m+" "+sharearray[m]);
                           
                           }
                           if(m==4)
                           {
                               x=w+2;
                               y=(h+(cat.getHeight()/2))%cat.getHeight();
                               sharemap[x][y]=sharearray[m]+238;
                    //       System.out.println((w+2)+" "+h+" "+x+" "+y+" "+m+" "+sharearray[m]);             
                           }
                           if(m==5)
                           {
                               x=w+2;
                               y=(h+1+(cat.getHeight()/2))%cat.getHeight();
                               sharemap[x][y]=sharearray[m]+238;
                    //       System.out.println((w+2)+" "+(h+1)+" "+x+" "+y+" "+m+" "+sharearray[m]);
                           }
                           
                           
                           
                         // System.out.println(x+" "+y);
                           
                        }
                            
                            
                           
                           
                           // sharemap[w+1][h]=sharearray[2]+238;
                           // sharemap[w+1][h+1]=sharearray[3]+238;
                           // sharemap[w+2][h]=sharearray[4]+238;
                           // sharemap[w+2][h+1]=sharearray[5]+238;
  			}		

		}

   
              // for(x=0;x<cat.getWidth();x++)
                  //  for(y=0;y<cat.getHeight();y++)
                 //      System.out.println(key[x][y][0]+","+key[x][y][1]);
                 Image transpImg1 = setSemiTransparency(cat,sharemap);
                 BufferedImage resultImage1 = ImageToBufferedImage(transpImg1, cat.getWidth(), cat.getHeight());
                // System.out.println(Path);
                 File outFile1 = new File(path+"_watermarked.png");
                // System.out.println(Path+".watermarked.png");
                 ImageIO.write(resultImage1, "PNG", outFile1);
                System.out.println("Finished Watermarking");
                

        }                

  
static private Image setSemiTransparency(BufferedImage srcImg,final int[][] sharemap) // method accepts a transparent color.
                                                                     // It'll transform all pixels of the transparent color to transparent.
    {   
 

        ImageFilter filter;
        filter = new RGBImageFilter() // overriding part of the RGBImageFilterClass to produce a specialized filter.
{
@Override
public int  filterRGB(int x, int y, int rgb) // overriden method
{
  // System.out.println((int)sharemap[x][y]);
    if((rgb & 0xFF000000) != 0)
        return (rgb & 0x00FFFFFF) + ((int)sharemap[x][y] << 24); // alpha bits set to 0 yields transparency.  
    else
        return rgb;
}
};

        ImageProducer ip = new FilteredImageSource(srcImg.getSource(),filter);
        Image result = Toolkit.getDefaultToolkit().createImage(ip);
        return result;
    }               

                


    public static int bin(int w,int h,BufferedImage cat){		
    Color color = new Color(cat.getRGB(w, h));	
    int averageColor = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
    //System.out.println(averageColor);
    if(averageColor==255)
        averageColor=1;
    else averageColor=0;
    return averageColor;}
    
      static private BufferedImage ImageToBufferedImage(Image image, int width, int height)
  {
    BufferedImage dest = new BufferedImage(
        width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = dest.createGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();
    return dest;
  }

      
    static private int[] shamir(int s1,int s2)
    {
    int q1,x;
   int shares[]=new int[6];
    
        for(x=1;x<7;x++)
        {
            q1=(s1*x+s2)%17;
            shares[x-1]=q1;
     //       System.out.println("Share:"+x+" "+shares[x-1]);
        } 
       return shares; 
    }
}

