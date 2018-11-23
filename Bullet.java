import java.awt.*;
public class Bullet extends CircleSprite{
    
    int bulletSpeed, bulletSpeedX, bulletSpeedY;
    
    public Bullet(Image i, int xCoordinate, int yCoordinate, int r){
        img = i;
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
    }
    
    public Bullet(Image i, int r){
        img = i;
        radius = r;
    }
    
    public Bullet(int xCoordinate, int yCoordinate, int r){
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
    }
    
    public Bullet(int xCoordinate, int yCoordinate, int r, int pbulletSpeed){
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
        bulletSpeed = pbulletSpeed;
    }
    
    public Bullet(Image i, int xCoordinate, int yCoordinate, int r, int pbulletSpeed, int x, int y){
        img = i;
        radius = r;
        midpointX = xCoordinate+r;
        midpointY = yCoordinate+r;
        bulletSpeed = pbulletSpeed;
        
        //The length between the points is calculated
        double hyp = Math.sqrt(Math.pow(x-midpointX,2)+Math.pow(y-midpointY,2));
        //The hyp is divided by the bullet speed and the answer is stored in dSpeed
        double dSpeed = hyp/bulletSpeed;
        //This is then used to divide the length between vertical and horizontal distances by it
        //to determine how many points the bullet must travel in each frame
        double xx = ((x - midpointX)/dSpeed);
        double yy = ((y - midpointY)/dSpeed);
        //The speed is then rounded to the nearest integer. This is unfortunate as the angles may not be perfect
        //and may miss if the target is very thin.
        if(xx < 0){
            xx = -xx;
        }
        if(yy < 0){
            yy = -yy;
        }
        
        int x2;
        int y2;
        if((int)xx+0.5>xx){
            x2 = (int)xx;
        }else{
            x2 = (int)xx++;
        }
        if((int)yy+0.5>yy){
            y2 = (int)yy;
        }else{
            y2 = (int)yy++;
        }
        //The direction of the bullet is Down and Right, therefore we ened to set the variables to negative
        //if they need to go Up or Left.
        if(y <= midpointY){
            bulletSpeedY = -y2;
        }else{
            bulletSpeedY = y2;
        }
        if(x <= midpointX){
            bulletSpeedX = -x2;
        }else{
            bulletSpeedX = x2;
        }
    }
    
}