import java.awt.*;
import java.util.ArrayList;
public class Goofy extends Character{
    //shield turning points;
    int tpx,tpy;
    boolean shieldThrown = false, movingAround = true;
    //Duration for shield rotating
    long shieldDuration;
    boolean speedUp = false;
    
    //Constructor
    public Goofy(//Movement images
                 Image pupMove, Image pdownMove, Image prightMove, Image pleftMove,
                 Image pupLeftMove, Image pupRightMove, Image pdownLeftMove, Image pdownRightMove,
                 //Attacking images
                 Image pupAttack, Image pdownAttack, Image pleftAttack, Image prightAttack,
                 Image pupLeftAttack, Image pupRightAttack, Image pdownLeftAttack, Image pdownRightAttack,
                 //Still images
                 Image pupStill, Image pdownStill, Image prightStill, Image pleftStill,
                 Image pupLeftStill, Image pupRightStill, Image pdownLeftStill, Image pdownRightStill,
                 //Ability Images
                 Image pability1Left, Image pability1Right, Image pability2Left, Image pability2Right,
                 //Knocked out image
                 Image pkoImage,
                 //A full 3D image used to show the character
                 Image pbaseImage,
                 //Length and height of rectangle bounds
                 int sideWidth, int midWidth,
                 int sideHeight, int midHeight,
                 //Enemy or ally
                 boolean pisEnemy,
                 //Protagonist or not
                 boolean pisProtagonist,
                 //bullet radius
                 int pbulletRadius, int pmyBulletSpeed,
                 //bullet image
                 Image pbulletImage,
                 //Timers to display animations
                 long phittingAnimation, long pability1AnimationTimer, long pability2AnimationTimer,
                 //Ability cooldown timers
                 long pabilityCooldown1, long pabilityCooldown2,
                 //Character stats
                 int pmaxHP, int pbaseSpeed, int plevel, int pbaseDamage,
                 //Sprite postitions
                 int pcx, int pcy,
                 //The time to hit the character
                 int pmaxToHitTime,
                 //Images for the UI and their coordinates
                 Image phealthyImage,  Image phurtImage,
                 int phealthyImagex, int phealthyImagey,
                 int phurtImagex, int phurtImagey,
                 //Images for the ability icons
                 Image pabilityImage1, Image pabilityImage2
                 ){
                       super(//Movement images
                             pupMove, pdownMove, prightMove, pleftMove,
                             pupLeftMove, pupRightMove, pdownLeftMove, pdownRightMove,
                             //Attacking images
                             pupAttack, pdownAttack, pleftAttack, prightAttack,
                             pupLeftAttack, pupRightAttack, pdownLeftAttack, pdownRightAttack,
                             //Still images
                             pupStill, pdownStill, prightStill, pleftStill,
                             pupLeftStill, pupRightStill, pdownLeftStill, pdownRightStill,
                             //Ability Images
                             pability1Left, pability1Right, pability2Left, pability2Right,
                             //Knocked out image
                             pkoImage,
                             //A full 3D image used to show the character
                             pbaseImage,
                             //Length and height of rectangle bounds
                             sideWidth, midWidth,
                             sideHeight, midHeight,
                             //Enemy or ally
                             pisEnemy,
                             //Protagonist or not
                             pisProtagonist,
                             //bullet radius
                             pbulletRadius, pmyBulletSpeed,
                             //bullet image
                             pbulletImage,
                             //Timers to display animations
                             phittingAnimation, pability1AnimationTimer, pability2AnimationTimer,
                             //Ability cooldown timers
                             pabilityCooldown1, pabilityCooldown2,
                             //Character stats
                             pmaxHP, pbaseSpeed, plevel, pbaseDamage,
                             //Sprite postitions
                             pcx, pcy,
                             //The time to hit the character
                             pmaxToHitTime,
                             //Images for the UI
                             phealthyImage, phurtImage,
                             phealthyImagex, phealthyImagey,
                             phurtImagex, phurtImagey,
                             //Images for the ability icons
                             pabilityImage1, pabilityImage2);
                       name = "Goofy";
    }
    
    //Overwritten to be able to move while performing the spin ability
    //stop moving
    void stop(int widthLimit, int heightLimit){
        if((!attacking() && !performingAbility2()) || (boundsMiddle.x+boundsMiddle.getWidth()+speed < widthLimit || boundsMiddle.y+boundsMiddle.getHeight()+speed < heightLimit || boundsMiddle.x-speedX > 3 || boundsMiddle.y-speedY > 25)){
            if( (speedX > 0 && speedY > 0) || (Timer.time - downTimer < 50 && Timer.time - rightTimer < 50) ){
                setImage(downRightStill);
            }else if( (speedX > 0 && speedY < 0) || (Timer.time - upTimer < 50 && Timer.time - rightTimer < 50) ){
                setImage(upRightStill);
            }else if( (speedX < 0 && speedY < 0) || (Timer.time - upTimer < 50 && Timer.time - leftTimer < 50) ){
                setImage(upLeftStill);
            }else if( (speedX < 0 && speedY > 0) || (Timer.time - downTimer < 50 && Timer.time - leftTimer < 50) ){
                setImage(downLeftStill);
            }else if(speedX == 0 && speedY > 0){
                setImage(downStill);
            }else if(speedX == 0 && speedY < 0){
                setImage(upStill);
            }else if(speedX > 0 && speedY == 0){
                setImage(rightStill);
            }else if(speedX < 0 && speedY == 0){
                setImage(leftStill);
            }else{
                setImage(downStill);
            }
            //Speeds become 0
            speedX = speedY = 0;
        }
    }
    
    //Spin and every enemy being hit will be damaged continuoulsy
    void ability1(){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && !attacking()){
            //Restart timer
            startAbilityTimer1();
            c.setImage(ability1Left);
            setSpeed((int)(speed * 0.7f));
            speedUp = true;
        }
    }
    //Throw the shield in a circular manner
    void ability2(int x, int y, int widthLimit, int heightLimit){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && !performingAbility1() && !performingAbility2() && bullet.size() == 0 && !attacking() && hp > 0){
            tpx = (x + c.getMidpointX())/2;
            tpy = (y + c.getMidpointY())/2;
            //Restart timer
            startAbilityTimer2();
            
            stop(widthLimit, heightLimit);
            
            //Determining the duration that the shield must rotate before it makes a full circle
            //finding radius of travel
            double myRadius = (Math.sqrt(Math.pow((x - c.getMidpointX())/2,2) + Math.pow((y-c.getMidpointY())/2,2)));
            //finding circumference of radius
            double myCircumference = Math.PI*myRadius*2;
            //Finding duration by dividing the distance by the velocity and multiplying it the interval before it moves again
            shieldDuration = (long)((myCircumference/(double)myBulletSpeed)*20);
            
            //Set image
            if(x>c.getMidpointX()){
                c.setImage(ability2Right);
            }else{
                c.setImage(ability2Left);
            }
        }
    }
    void checkForAbility2(){
        if(performingAbility2() && Timer.time - abilityTimer2 > ability2AnimationTimer/2 && canPerformAbility2 == true){
            shieldThrown = true;
            shootBullet(tpx,tpy);
            canPerformAbility2 = false;
        }
    }
    //Move the shield
    void moveShield(ArrayList<Character> other){
        for(int i = 0; i < bullet.size(); i++){
            if(Timer.time - abilityTimer2 < shieldDuration){
                //Get midpoints of sprites
                int x1 = tpx;
                int y1 = tpy;
                int x2 = (int)(bullet.get(i).midpointX);
                int y2 = (int)(bullet.get(i).midpointY);
                
                //Get distance between points
                int w = Math.abs(x1-x2);
                int h = Math.abs(y1-y2);
                
                //Get the speed squared an divide it by the sum of the
                //horizontal squared and vertical squared
                double t = Math.sqrt(Math.pow(bullet.get(i).bulletSpeed,2) / (Math.pow(w,2) + Math.pow(h,2)) );
                
                //The result is the multiplicaton of previous results
                double horizontal = t*h;
                double diagonal = t*w;
                
                //Round moveH
                int moveH;
                if((int)horizontal+0.5>horizontal){
                    moveH = ((int)horizontal);
                }else{
                    moveH = ((int)horizontal+1);
                }
                //Round moveD
                int moveD;
                if((int)diagonal+0.5>diagonal){
                    moveD = ((int)diagonal);
                }else{
                    moveD = ((int)diagonal+1);
                }
                
                //Depending on the direction the other is relative to the character
                //set the directions of speedX and speedY
                if(x1<=x2 && y1<=y2){
                    bullet.get(i).bulletSpeedX = -moveH;
                    bullet.get(i).bulletSpeedY = moveD;
                }else if(x1<=x2 && y1>=y2){
                    bullet.get(i).bulletSpeedX = moveH;
                    bullet.get(i).bulletSpeedY = moveD;
                }else if(x1>=x2 && y1<=y2){
                    bullet.get(i).bulletSpeedX = -moveH;
                    bullet.get(i).bulletSpeedY = -moveD;
                }else if(x1>=x2 && y1>=y2){
                    bullet.get(i).bulletSpeedX = moveH;
                    bullet.get(i).bulletSpeedY = -moveD;
                }
            }else{
                setBulletDirectionsToward(c.getMidpointX(),c.getMidpointY(),i);
            }
            bullet.get(i).moveRight(bullet.get(i).bulletSpeedX);
            bullet.get(i).moveDown(bullet.get(i).bulletSpeedY);
            for(int x = 0; x<other.size(); x++){
                if(bullet.get(i).hasCollidedWith(other.get(x).boundsMiddle) && (!isEnemy && other.get(x).isEnemy || isEnemy && other.get(x).isEnemy == false) && other.get(x).hp > 0){
                    //Damage the enemy
                    damageChar(x, ((int)(damage*0.3)), other);
                }
            }
            if(bullet.get(i).hasCollidedWith(c) && Timer.time - abilityTimer2 > shieldDuration){
                bullet.remove(i);
            }
        }
    }
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if((hp>0 && !isEnemy) || isEnemy){
            if(!performingAbility2()){
                if(!isProtagonist){
                    super.moveRandomly(other, widthLimit, heightLimit);
                    //Time the timer to hit attack, reset when no longer hitting
                    boolean touching = false;
                    for(int i = 0; i<other.size() && touching == false; i++){
                        if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                            Rectangle bm = other.get(i).boundsMiddle;
                            if((bm.intersects(boundsMiddle) || bm.intersects(boundsUpLeft) || bm.intersects(boundsUp) || bm.intersects(boundsUpRight) || bm.intersects(boundsRight) || bm.intersects(boundsDownRight) || bm.intersects(boundsDown) || bm.intersects(boundsDownLeft) || bm.intersects(boundsLeft))){
                                touching = true;
                            }
                        }
                    }
                    if(!touching){
                        touchingTimer = Timer.time;
                    }
                    //If timer is left to exceed a certain amount, attack
                    if(Timer.time - touchingTimer > toHitTime*1.5f){
                        attack(other);
                        touchingTimer = Timer.time;
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime);
                    if(r == 1){
                        ability1();
                    }else if(r == 2){
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0){
                            ability2(other.get(getProtagonist(other)).c.getMidpointX(), other.get(getProtagonist(other)).c.getMidpointY(), widthLimit, heightLimit);
                        }else{
                            ability2(other.get(getFurthestChar(other)).c.getMidpointX(), other.get(getFurthestChar(other)).c.getMidpointY(), widthLimit, heightLimit);
                        }
                    }
                }
                
                if(performingAbility1() && !isProtagonist){
                    moveTowardsChar(other.get(getClosestChar(other)));
                }else{
                    moveSpecial(other, widthLimit, heightLimit);
                }
                if(!performingAbility1() && speedUp == true){
                    speedUp = false;
                    setSpeed(getBaseSpeed());
                }
                //move items
                super.move(widthLimit, heightLimit);
            }else{
                checkForAbility2();
            }
            //Stop ability animations
            stopAnim(widthLimit, heightLimit);
            if(Timer.time - abilityTimer1 < ability1AnimationTimer){
                //Keep the same image throughout the ability
                c.setImage(ability1Left);
                for(int i = 0; i<other.size(); i++){
                    if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                        Rectangle bm = other.get(i).boundsMiddle;
                        if(bm.intersects(boundsMiddle) || bm.intersects(boundsUpLeft) || bm.intersects(boundsUpRight) || bm.intersects(boundsRight) || bm.intersects(boundsDownRight) || bm.intersects(boundsDownLeft) || bm.intersects(boundsLeft)){
                            damageChar(i, ((int)(damage*0.07)), other);
                        }
                    }
                }
            }
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downStill);
            }
        }
        moveShield(other);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i<bullet.size(); i++){
            bullet.get(i).paint(g);
        }
    }
}