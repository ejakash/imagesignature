imagesignature
==============

Converts an image to a unique signature without and visible changes in the image and add authentication self repair capability to the image.

The entire project was done using netbeans IDE.
And the project runs within the IDE. 

Code is fully functional but most of the common errors are not pointed out. The gui.java is the main function.
To run the project:

1. import the files to some IDE. Preferably Netbeans.
2. Create a project named ImageWatermarking and place all java files in src folder of the project.
3. Compile the project
4. Run gui.java.
5. Select the input image to  be watermarked. Preferable a document type image.
6. click on keyless watermarking button.
7. Now select the input image as the watermarked image created. Instead if you directly try the authetication button output will be 'tampered' because the watermarked image is the unique image and that is the image to  be checked.
8. After the keyless watermarking button is pressed, open the watermarked image using some software like photoshop, use pencil tool to tamper the image and save the changes.
9. Now click the select image button and select the tampered image.
10.click the authenticate button and authenticate the image. Image will be found tampered.
11.Click the self repair image and the original image is recreated from the tampered image.
12.If the buttons are pressed in different orders, different exceptions would arise. This is because, global values are used and each class changes these values affecting the initial conditions.

