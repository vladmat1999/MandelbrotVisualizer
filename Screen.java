/*
The Screen class is use to keep track of the numbers in relation to the
events and map each pixel to a number. I know the class has only static
variables and methots and will try to fix this in the future (the easiest
fix is just to delete all the static words and create a new Screen in the
main application.
 */
package javafxapplication1;

/**
 *
 * @author Vlad
 */
public class Screen {
    public static int width = 600;
    public static int height = 600;
    public static double aspectRatio = width/(double)height;
    
    public static double centerX = 0;  //The positions of the 
    public static double centerY = 0;  //center of the screen
    
    public static double lenghtX = 4;  //Lenght of the axis
    public static double lenghtY = lenghtX/aspectRatio;
    
    public static double zoomFactor = 1;  //Current zoom
    
    //Returns the real part of the complex number according to 
    //current screen settings
    public static double getX(int j){
    double x;
        x = centerX+(j-width/2)*1.0/(width+1)*lenghtX*zoomFactor;
    return x;
    }
    
    //Returns the imaginary part of the complex number according to 
    //current screen settings
    public static double getY(int i){
    double y;
        y = centerY+(i-height/2)*1.0/(height+1)*lenghtY*zoomFactor;
    return y;
    }
     
    //Sets the zoom (useless)
    public static void setZoom(double a){
    zoomFactor*=a;
    }
    
    //Sets the width and height of the screen
    public static void setWidthHeight(int newWidth, int newHeight){
    width = newWidth;
    height = newHeight;
    aspectRatio = width/(double)height;
    lenghtY = lenghtX/aspectRatio;
    }
    
}
