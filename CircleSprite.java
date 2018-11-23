//imported for using images
import java.awt.*;
public class CircleSprite{
    Image img;
    int radius;
    int midpointX, midpointY;
    boolean visible = true;
    //setting up the sprite
    public CircleSprite(){}
    
    public CircleSprite(Image i, int xCoordinate, int yCoordinate, int r){
        img = i;
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
    }
    
    public CircleSprite(Image i, int r){
        img = i;
        radius = r;
    }
    
    public CircleSprite(int xCoordinate, int yCoordinate, int r){
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
    }
    
    //collision detection
    public boolean hasCollidedWith(Rectangle other){
        double a = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y-midpointY),2));
        double b = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        double c = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y-midpointY),2));
        double d = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        
        //If it hits any of the edges of the rectangle
        if(a<=radius || b<=radius || c<=radius || d<=radius){
            return true;
        }
        //Entering from the left
        else if(other.x<=midpointX+radius && other.x+other.width>=midpointX+radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("RightEntering");
            return true;
        }
        //Entering from the Right
        else if(other.x<=midpointX-radius && other.x+other.width>=midpointX-radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("RightEntering");
            return true;
        }
        //Entering from above
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y<=midpointY+radius && other.y+other.getHeight()>=midpointY+radius){
            //System.out.println("AboveEntering");
            return true;
        }
        //Entering from below
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y<=midpointY-radius && other.y+other.getHeight()>=midpointY-radius){
            //System.out.println("BelowEntering");
            return true;
        }
        //If the rectangle is sretched out horizontally
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y>=midpointY-radius && other.y+other.getHeight()<=midpointY+radius){
            //System.out.println("HorizontalEntering");
            return true;
        }
        //If the rectangle is stretched out vertically
        else if(other.x>=midpointX-radius && other.x+other.width<=midpointX+radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("VerticalEntering");
            return true;
        }
        else{
            return false;
        }
    }
    public boolean hasCollidedWith(Sprite other2){
        Rectangle other = other2.area;
        
        double a = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y-midpointY),2));
        double b = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        double c = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y-midpointY),2));
        double d = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        
        //If it hits any of the edges of the rectangle
        if(a<=radius || b<=radius || c<=radius || d<=radius){
            return true;
        }
        //Entering from the left
        else if(other.x<=midpointX+radius && other.x+other.width>=midpointX+radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("RightEntering");
            return true;
        }
        //Entering from the Right
        else if(other.x<=midpointX-radius && other.x+other.width>=midpointX-radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("RightEntering");
            return true;
        }
        //Entering from above
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y<=midpointY+radius && other.y+other.getHeight()>=midpointY+radius){
            //System.out.println("AboveEntering");
            return true;
        }
        //Entering from below
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y<=midpointY-radius && other.y+other.getHeight()>=midpointY-radius){
            //System.out.println("BelowEntering");
            return true;
        }
        //If the rectangle is sretched out horizontally
        else if(other.x<=midpointX && other.x+other.getWidth()>=midpointX && other.y>=midpointY-radius && other.y+other.getHeight()<=midpointY+radius){
            //System.out.println("HorizontalEntering");
            return true;
        }
        //If the rectangle is stretched out vertically
        else if(other.x>=midpointX-radius && other.x+other.width<=midpointX+radius && other.y+other.height>=midpointY && other.y<=midpointY){
            //System.out.println("VerticalEntering");
            return true;
        }
        else{
            return false;
        }
    }
    public boolean hasCollidedWith(int x, int y){
        Rectangle other = new Rectangle(x, y, 1, 1);
        double a = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y-midpointY),2));
        double b = Math.sqrt(Math.pow((other.x-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        double c = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y-midpointY),2));
        double d = Math.sqrt(Math.pow((other.x+other.width-midpointX),2)+Math.pow((other.y+other.height-midpointY),2));
        
        if(a<=radius || b<=radius || c<=radius || d<=radius){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean hasCollidedWith(CircleSprite other){
        int radii = this.radius+other.radius;
        if(midpointX == other.midpointX){
            if((radii - Math.abs(midpointY-other.midpointY))>0){
                return true;
            }else{
                return false;
            }
        }else if(midpointY == other.midpointY) {
            if((radii - Math.abs(midpointX-other.midpointX))>0){
                return true;
            }else{
                return false;
            }
        }else{
            if(radii -(Math.sqrt(Math.pow(midpointX-other.midpointX,2)+Math.pow(midpointY-other.midpointY,2)))  > 0){
                return true;
            }else{
                return false;
            }
        }
    }
    //decrease x to move to the left
    public void moveLeft(){
        midpointX -= 1;
    }
    //reduce x by number of pixels to move to left
    public void moveLeft(int px){
        midpointX -= px;
    }
    //increase x to move to right
    public void moveRight(){
        midpointX += 1;
    }
    //increase x by number of pixels to move to right
    public void moveRight(int px){
        midpointX += px;
    }
    //reduce y to move up
    public void moveUp(){
        midpointY -= 1;
    }
    //reduce y by a number of pixels to move up
    public void moveUp(int px){
        midpointY -= px;
    }
    //increase y to move down
    public void moveDown(){
        midpointY += 1;
    }
    //increase y by a number of pixels to move down
    public void moveDown(int px){
        midpointY += px;
    }
    
    //changes coordinates of image/char
    public void moveTo(int px, int py){
        midpointX = radius+px;
        midpointY = py+radius;
    }
    //This paint method is designed to adapt to situations where the collision place is not the whole sprite but rather a 
    //part of it
    public void paint(Graphics g, int distanceXFromMidpoint, int distanceYFromMidpoint){
        if(visible == true){
            g.drawImage(img, (int)(midpointX-distanceXFromMidpoint), (int)(midpointY-distanceYFromMidpoint), null);
            //g.fillOval(getX(), getY(), radius*2, radius*2);
        }
    }
    //the paint method to draw the image on screen
    public void paint(Graphics g){
        if(visible == true){
            g.drawImage(img, (int)(midpointX-radius), (int)(midpointY-radius), null);
            //g.fillOval(getX(), getY(), radius*2, radius*2);
        }
    }
    //@Override
    public void update(Graphics g){
       paint(g);
    }
    //Get the x co-ordinate 
    public int getX(){
        return midpointX-radius;
    }
    //Get the y co-ordinate 
    public int getY(){
        return midpointY-radius;
    }
    //Make the x co-ordinate go to set point
    public void setX(int px){
       midpointX = px+radius;
    }
    //Make the y co-ordinate go to set point
    public void setY(int py){
       midpointY = py+radius;
    }
    //set the end points
    public void setPoints(int px, int py){
       midpointX = px+radius;
       midpointY = py+radius;
    }
    //set the mid points
    public void setMidpoints(int px, int py){
       midpointX = px;
       midpointY = py;
    }
    
    //getting the radius
    public int getRadius(){
        return radius;
    }
    //getting the diameter
    public int getDiameter(){
        return radius*2;
    }
    //getting the image
    public Image getImage(){
        return img;
    }
    //setting a new image without touching it's dimension
    public void setImage(Image newImage){
        img = newImage;
    }
    //changing an image and dimensions
    public void setImage(Image newImage, int r){
        img = newImage;   
        //changes size
        radius = r;
    }
    //set the image in/visible
    public void setVisible(boolean v){
        visible = v;
    }
}