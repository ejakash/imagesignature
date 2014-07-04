package imagewatermarking;

import static imagewatermarking.watermarking.bin;
//import static imagewatermarking.watermarking.key;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;
public class authentication {
    static int flag=1;
   
public static int tampered[][];
public static void main(String path) throws IOException, ClassNotFoundException 	

	{
            
       // Save the image file as a BufferedImage object	
    BufferedImage cat = ImageIO.read(new File(path));
   // System.out.println(Path);
		// Loop through all the pixels in the image (w = width, h = height)		
                int widthoffset;
                int heightoffset,i;
                tampered=new int[cat.getWidth()][cat.getHeight()];
         
                
		if(cat.getWidth()%3!=0)
                {
                     widthoffset=cat.getWidth()%3;
                 //    System.out.println(widthoffset+"pixel right border not considered from watermarking");
                }
                if(cat.getHeight()%2!=0)
                {
                     heightoffset=cat.getHeight()%2;
                //     System.out.println(heightoffset+"pixel bottom border not considered from watermarking");
                }
		for(int w = 0; w < cat.getWidth()-2 ; w+=3)		
		{			
			for(int h = 0 ; h < cat.getHeight()-1 ; h+=2)			
			{	
                            int p1,p2,p3,p4,p5,p6;
                            p1=bin(w,h,cat);
                            p2=bin(w,h+1,cat);
                            p3=bin(w+1,h,cat);
                            p4=bin(w+1,h+1,cat);
                            p5=bin(w+2,h,cat);
                            p6=bin(w+2,h+1,cat);
                            int b1=p1^p2^p3;
                            int b2=p4^p5^p6;
                            int color[]=new int [6];
                   //      System.out.println(p1+" "+p2+" "+p3+" "+p4+" "+p5+" "+p6+" "+b1+" "+b2);
                            color[0]=cat.getRGB(w,h);
                            color[1]=cat.getRGB(w,h+1);
                            int[] alpha=new int[6];
                            alpha[0] = (color[0] >> 24) & 0xFF; 
                            alpha[1] = (color[1] >> 24) & 0xFF;
                            alpha[0]-=238;
                            alpha[1]-=238;
                   //    System.out.println("Shares calculated are: "+alpha[0]+" "+alpha[1]);
                            int [] secret=revshamir(alpha);
                            int a1=(secret[0]&8)>>3;
                            int a2=(secret[0]&4)>>2;
                  //    System.out.println(secret[0]+" "+secret[1]);
                            if((!(a1==b1&&a2==b2))||alpha[0]>16||alpha[1]>16||alpha[0]<0||alpha[1]<0)
                            {
                         //       System.out.println(a1+" "+b1+" "+a2+" "+b2);
                              flag=0;
                        //      System.out.println("Tampered Block "+(((w/3))*(cat.getHeight()/2)+h/2+1));
                              tampered[w][h]=-1;
                            }
  			}		
		}
                if(flag==1)
                System.out.println("Image Authenticated");
                else{
                System.out.println("Image found Tampered");
                flag=1;}
                
            //   for(i=0;i<6;i++)
              //      for(int j=0;j<4;j++) 
                //    {
                  //      System.out.println(key[i][j][0]);
                    //    System.out.println(key[i][j][1]);
                    //}
        }

                
    static private int[] revshamir(int[] alpha)
    {   
           int secret[]=new int [2];
           secret[0]=alpha[1]-alpha[0];
           secret[1]=(2*alpha[0]-alpha[1])%17; 
           if(secret[1]<0)
               secret[1]=17-((-secret[1])%17);
           if(secret[0]<0)
               secret[0]=17-((-secret[0])%17);           
      //     System.out.println(secret[0]+" "+secret[1]);
           return secret; 
    }
    
}
