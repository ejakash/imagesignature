package imagewatermarking;
//import static imagewatermarking.authentication.importToFile;
import static imagewatermarking.authentication.tampered;
import java.awt.Color;
//import static imagewatermarking.authentication.key;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class repairing {
    
    static int w,h;
        static int key[][][];
        static int flag=1;
        public static void importToFile(String Path) throws IOException, ClassNotFoundException{
    FileInputStream saveFile = new FileInputStream(Path);
        try (ObjectInputStream save = new ObjectInputStream(saveFile)) {
            key = (int [][][]) save.readObject();
        }
        }
    public static int validate(int x,int y,BufferedImage cat)
    {
    int color;
   // if(tampered[key[x][y][0]-(key[x][y][0]%3)][key[x][y][1]-(key[x][y][1]%2)]==-1)
   // color=-1;  
   // else
   //     color= ((cat.getRGB(key[x][y][0],key[x][y][1]) >> 24) & 0xFF)-238;
    color=key[x][y][2];
   //// System.out.println(x+" "+y+" "+color+" "+key[x][y][0]+" "+key[x][y][1]);
    return color;
    }
public static void main(String Path, String PathK) throws IOException, ClassNotFoundException 	

{		
       // Save the image file as a BufferedImage object	
    BufferedImage cat = ImageIO.read(new File(Path));
    importToFile(PathK);
          ////          System.out.println("\n The key array importerd is:\n");
          ////      for(h=0;h<cat.getHeight();h++){
          ////           for(w=0;w<cat.getWidth();w++)
               ////          System.out.print("  "+key[w][h][0]+","+key[w][h][1]+" ("+(((cat.getRGB(key[w][h][0],key[w][h][1]) >> 24) & 0xFF)-238)+") "+"("+(cat.getRGB(key[w][h][0],key[w][h][1]) & 0xFF)+")  ");
               ////      System.out.print("\n");}
                
		// Loop through all the pixels in the image (w = width, h = height)		
                int p1,p2,p3,p4,p5,p6;
                int color[]=new int [6];
		for(int width = 0; width < cat.getWidth()-2 ; width+=3)		
		{			
			for(int height = 0 ; height < cat.getHeight()-1 ; height+=2)			
			{
                            if(tampered[width][height]==-1)
                            
                            {
                              
                            //  System.out.println(p1+" "+p2+" "+p3+" "+p4+" "+p5+" "+p6+" "+a1+" "+a2);
                                color[0]=validate(width,height,cat);
                                color[1]=validate(width,height+1,cat);
                                color[2]=validate(width+1,height,cat);
                                color[3]=validate(width+1,height+1,cat);
                                color[4]=validate(width+2,height,cat);
                                color[5]=validate(width+2,height+1,cat);
            ////                System.out.println("Shares calculated are: "+color[0]+" "+color[1]+" "+color[2]+" "+color[3]+" "+color[4]+" "+color[5]);
                                int [] secret=revshamiradv(color);
          ////                      System.out.println("secret 0 is "+secret[0]+" Secret 1 is "+secret[1]);
                                p1=(secret[0]&2)>>1;
                                p2= secret[0]&1;
                                p3=(secret[1]&8)>>3;
                                p4=(secret[1]&4)>>2;
                                p5=(secret[1]&2)>>1;
                                p6=secret[1]&1;
           ////                     System.out.println(p1+" "+p2+" "+p3+" "+p4+" "+p5+" "+p6+" "+width+" "+height); 
                                p1=revbin(p1);
                                p2=revbin(p2);
                                p3=revbin(p3);
                                p4=revbin(p4);
                                p5=revbin(p5);
                                p6=revbin(p6);
          ////                      System.out.println(p1+" "+p2+" "+p3+" "+p4+" "+p5+" "+p6+" "+width+" "+height);                                if(secret[0]==3&&secret[1]==15)
                        //            flag=0;
                                
                                cat.setRGB( width, height,p1);
                                cat.setRGB( width, height+1,p2);
                                cat.setRGB( width+1, height,p3);
                                cat.setRGB( width+1, height+1,p4);
                                cat.setRGB( width+2, height,p5);
                                cat.setRGB( width+2, height+1,p6);
                                
                            }	
                        }	
		}
                if(flag==1)
                System.out.println("Image Repaired ");
                else
                 System.out.println("Some Blocks unrepaired");
                flag=1;
                Path=Path.replace("_watermarked.png","");
                ImageIO.write(cat, "png", new File(Path+"_repaired.png"));
        }

    public static int revbin(int color){		
    if(color==1)
        color=255;
    
    Color c=new Color(color,color,color);
    return c.getRGB();}       
    
    static private int[] revshamiradv(int[] alpha)
    {
      int f=0,d,c1;
      int alpha1[]=new int[2];
      int secret[]=new int[2];
      int x[]=new int[2];
      int m[]=new int[2];
      for(int i=0;i<6;i++)
      {
          if(alpha[i]==-1)
          {
      ////        System.out.println(i+" th share found corrupt...searching for the next share");
              continue;
          }
              else
          {
       ////       System.out.println(i+" th share found ...searching for the next share");
              alpha1[f]=alpha[i];
              x[f]=i+1;
              f++;
           }
          
          if(f==2)
              break;
     }
      if(f!=2)
      { ////  System.out.println("Unable to get 2 distinct reliable shares... block cannot be repaired");
          secret[0]=3;
          secret[1]=15;
          flag=0;
          return secret;
      }   
    ////  System.out.println("the value of f is "+f+" THe shares obtained are "+alpha1[0]+" "+alpha1[1]+" The x values are "+x[0]+" "+x[1]);
      m[0]=alpha1[0]*x[1]/(x[0]-x[1]);
      m[1]=alpha1[1]*x[0]/(x[1]-x[0]);
      
      d=(-1*(m[0]+m[1]))%17;
      if (d < 0)
{
    d += 17;
}
      c1=(alpha1[1]-alpha1[0])%17;
      if (c1< 0)
{
    c1 += 17;
}
      secret[1]=d;
      secret[0]=c1;
      return secret;
    }
    

}
