package imagewatermarking;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class greyscale

{	
    public static String main(String pathi) throws IOException 	

	{		

	int avgmin=255;
        int avgmax=0;
                // Save the image file as a BufferedImage object		

		BufferedImage cat = ImageIO.read(new File(pathi));				

		

		// Loop through all the pixels in the image (w = width, h = height)		

		

		for(int w = 0; w < cat.getWidth() ; w++)		

		{			

			for(int h = 0 ; h < cat.getHeight() ; h++)			

			{				

				// BufferedImage.getRGB() saves the colour of the pixel as a single integer.				

				// use Color(int) to grab the RGB values individually.				

				

				Color color = new Color(cat.getRGB(w, h));								

				                      //  System.out.println(w+" "+h+" "+color.getRed()+" "+color.getGreen()+" "+color.getBlue());

				// use the RGB values to get their average.				

				int averageColor = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);				
                                
                                if(avgmin>averageColor)
                                    avgmin=averageColor;
                                if(avgmax<averageColor)
                                    avgmax=averageColor;
				

				// create a new Color object using the average colour as the red, green and blue				

				// colour values				

				Color avg = new Color(averageColor, averageColor, averageColor);								

				

				// set the pixel at that position to the new Color object using Color.getRGB().				

				cat.setRGB(w, h, avg.getRGB());			

			}		

		}				

		

		// save the newly created image in a new file.		

                int avgcolor=(avgmin+avgmax)/2;	
    
    for(int w = 0; w < cat.getWidth() ; w++)		

		{			

			for(int h = 0 ; h < cat.getHeight() ; h++)			
                        {
                        Color color = new Color(cat.getRGB(w, h));								
			

				// use the RGB values to get their average.				

				int averageColor = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);				
                                if(averageColor<avgcolor)
                                    averageColor=0;
                                else averageColor=255;
                                Color avg = new Color(averageColor, averageColor, averageColor);
				cat.setRGB(w, h, avg.getRGB());	
}}
    String pathb=pathi+"_binary.png";
  ImageIO.write(cat, "png", new File(pathb));  
  return pathb;
}}
