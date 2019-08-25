import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author Vlad
 */
public class Main extends Application {
    public double mousePosX, mousePosY;  //Mouse position for the 
    public double mouseOldX, mouseOldY;  //MousePressed and MouseReleased events
    @Override
    public void start(Stage primaryStage) {
        
        //Initialize the scene
        Group root = new Group();
        Canvas canvas=new Canvas(Screen.width,Screen.height);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        Scene scene = new Scene(root, Screen.width, Screen.height);
        
        //Draw the set with current settings
        draw(gc);
        
        moveScene(root,gc);
        
        zoomScene(root, gc);
        
        //Whenever the scene resolution changes, decrease the resolution for speed.
        //This will also be implemented for mouse zoom in the future        
        ChangeListener<Number> sceneSizeListener = (observable, oldValue, newValue) ->{
        Screen.setWidthHeight((int)scene.getWidth(),(int)scene.getHeight());
        canvas.setWidth(scene.getWidth());
        canvas.setHeight(scene.getHeight());
        drawSmallRes(gc,4);
        };

        scene.widthProperty().addListener(sceneSizeListener);
        scene.heightProperty().addListener(sceneSizeListener);
        
        primaryStage.setTitle("Mandelbrot Visualizer");
        primaryStage.setScene(scene);
        root.getChildren().add(canvas);
        primaryStage.show();
    }
    
    //The main method
    public static void main(String[] args) {
        launch(args);
    }

    //Draws the set full resolution and smooth colouring
    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, Screen.width, Screen.height);
        gc.setLineWidth(1);
        for(int i=1;i<=Screen.height;i+=1){
            for(int j=1;j<=Screen.width;j+=1){
                 Complex a = new Complex(Screen.getX(j),Screen.getY(i));
                 Complex z=a.testSteps(a);
                 gc.setStroke(mapColours(z));
                 gc.strokeLine(j,i,j+1,i);
            }
        }
        }
    
    //Draws the set at the specified 1/x resolution and aliased colouring
    //to increase speed
    private void drawSmallRes(GraphicsContext gc, int resolution){
    gc.clearRect(0, 0, Screen.width, Screen.height);
        gc.setLineWidth(resolution);
        for(int i=1;i<=Screen.height;i+=resolution){
            for(int j=1;j<=Screen.width;j+=resolution){
                 Complex a = new Complex(Screen.getX(j),Screen.getY(i));
                 Complex z=a.testSteps(a);
                 gc.setStroke(mapColours(z.numSteps));
                 gc.strokeLine(j,i,j+resolution,i);
            }
        }
    }
    
    //Maps the numbers to colour hue without smooth colouring
    public Color mapColours(int n){
    if(n>Complex.maxSteps)
        return Color.BLACK;
    Color temp = Color.hsb(360/(Complex.maxSteps)*n, 1, 1);
    return temp;
   }
    
    //Maps the numbers to colour hue with smooth colouring
    public Color mapColours(Complex z){
    int n = z.numSteps;
    if(n>Complex.maxSteps)
        return Color.BLACK;
    double h=n-(Math.log(Math.log(z.mod())/Math.log(4))/Math.log(2));
    Color temp = Color.hsb(360/(Complex.maxSteps)*h, 1, 1);
    return temp;
    }
    
    //Moves the scene when the mouse is clicked and released
    public void moveScene(Group root, GraphicsContext gc){
    root.setOnMousePressed((MouseEvent event)->{
        mouseOldX=event.getX();
        mouseOldY=event.getY();
        });
        
        root.setOnMouseReleased((MouseEvent event)->{
        mousePosX = event.getX();
        mousePosY=event.getY();
        
        double deltaX = mouseOldX - mousePosX;
        double deltaY = mouseOldY - mousePosY;
        
        double deltaMoveX = deltaX*Screen.lenghtX/Screen.width*Screen.zoomFactor;
        double deltaMoveY = deltaY/Screen.height*Screen.lenghtY*Screen.zoomFactor;
        
        Screen.centerX += deltaMoveX;
        Screen.centerY += deltaMoveY;
        draw(gc);
        });
    }
    //Zooms the scene with the mouse scroll wheel
    public void zoomScene(Group root, GraphicsContext gc){
    root.setOnScroll((ScrollEvent event)->{
        double deltaY = event.getDeltaY();
        int mouseX = (int) event.getX();
        int mouseY = (int) event.getY();
        int centerX = Screen.width/2;
        int centerY = Screen.height/2;
        
        if(deltaY<0){
        Screen.setZoom(1.1);
        double deltaMouseX = -(mouseX-centerX)*(Screen.zoomFactor*0.1);
        double deltaMouseY = -(mouseY-centerY)*(Screen.zoomFactor*0.1);
        
        double deltaMoveX = deltaMouseX*Screen.lenghtX/Screen.width;
        double deltaMoveY = deltaMouseY/Screen.height*Screen.lenghtY;
        
        Screen.centerX += deltaMoveX;
        Screen.centerY += deltaMoveY;
        draw(gc);
        }
        if(deltaY>0){
        Screen.setZoom(1/1.1);
        double deltaMouseX = (mouseX-centerX)*(Screen.zoomFactor*0.1);
        double deltaMouseY = (mouseY-centerY)*(Screen.zoomFactor*0.1);
        
        double deltaMoveX = deltaMouseX*Screen.lenghtX/Screen.width;
        double deltaMoveY = deltaMouseY/Screen.height*Screen.lenghtY;
        
        Screen.centerX += deltaMoveX;
        Screen.centerY += deltaMoveY;
        draw(gc);
        }
        });
    }
}
