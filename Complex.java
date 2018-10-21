/*
 This is the complex numbers class
 */
package javafxapplication1;

/**
 *
 * @author Vlad
 */
public class Complex {
    
    //The number of steps before the algorithm escapes
    static int maxSteps=100;
    
    private double real;
    private double img;
    
    //Current number of steps
    public int numSteps;
    
    //Constructor with no arguments
    public Complex(){
    real=0;
    img=0;
    numSteps=0;
    }
    
    //Constructor with arguments
    public Complex(double a, double b){
    real=a;
    img=b;
    numSteps=0;
    }
    
    //Sets a new maximum number of steps
    public static void setSteps(int a){
    maxSteps=a;
    }
    
    //Multiply two complex numbers
    public Complex multiply(Complex b){
    Complex result=new Complex();
    result.real=this.real*b.real-this.img*b.img;
    result.img=this.real*b.img+this.img*b.real;
    return result;
    }
    
    //Add two comlex numbers
    public Complex add(Complex b){
    Complex result=new Complex();
    result.real=this.real+b.real;
    result.img=this.img+b.img;
    return result;
    }
    
    //Returns the modulus of a complex number
    public double mod(){
        double m=real*real+img*img;
        return m;
    }
    
    //Tests for how many steps the number stays within the bailout radius
    public Complex testSteps(Complex a){
    Complex z = new Complex();   
    int i=0;
        for(;i<=maxSteps&&z.mod()<4;i++){
        z=(z.multiply(z)).add(a);
        }
    z.numSteps=i;
    return z;
    }
}
