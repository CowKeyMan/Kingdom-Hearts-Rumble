import java.awt.*;
import java.util.ArrayList;
public class Bob extends Character{
    //The duration for how long the bullet will travel before returning to the character
    long travelTime, travelTimer;
    
    //Booean to see wether directions are to be changed
    boolean toChange = false;
    
    int bulletX, bulletY;
    
    public Bob(//Movement images
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
         Image pabilityImage1, Image pabilityImage2,
         //The duration for how long the bullet will travel before returning to the character
         long ptravelTime
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
               name = "Bob";
               travelTime = ptravelTime;
    }
    
    void ability1(ArrayList<Character> other){
        //Check Timer
        if(Timer.time - abilityTimer1 > abilityCooldown1 && hp > 0 && !attacking() && !performingAbility1() && !performingAbility2()){
            CircleSprite s = new CircleSprite(midpointX() - 50, midpointY() - 50, 50);
            
            //Damage if it collides with an enemy
            for(int i = 0; i<other.size(); i++){
                if(!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false){
                    if(s.hasCollidedWith(other.get(i).boundsMiddle)){
                        damageChar(i, damage*2, other);
                    }
                }
            }
            //Set the image
            setImage(ability1Left);
            //Restart timer
            startAbilityTimer1();
        }
    }
    
    void ability2(int x, int y){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && hp > 0 && !attacking() && !performingAbility1() && !performingAbility2() && bullet.size() == 0){
            bulletX = x; bulletY = y;
            
            //Restart timer
            startAbilityTimer2();
            
            //Set the image
            if(x < c.getMidpointX()){
                c.setImage(ability2Left);
            }else{
                c.setImage(ability2Right);
            }
            toChange = true;
        }
    }
    void checkForAbility2(){
        if(performingAbility2() && Timer.time - abilityTimer2 > ability2AnimationTimer/2 && canPerformAbility2 == true){
            shootBullet(bulletX, bulletY);
            //Restart the travel timer
            travelTimer = Timer.time;
            canPerformAbility2 = false;
        }
    }
    
    //Move the bullet
    void moveStar(ArrayList<Character> other, int widthLimit, int heightLimit){
        for(int i = 0; i < bullet.size(); i++){
            bullet.get(i).moveRight(bullet.get(i).bulletSpeedX);
            bullet.get(i).moveDown(bullet.get(i).bulletSpeedY);
            
            if((bullet.get(i).midpointX > widthLimit+10 || bullet.get(i).midpointX < -5 || bullet.get(i).midpointY > heightLimit + 5 || bullet.get(i).midpointY < 20 || Timer.time - travelTimer > travelTime) && toChange == true){
                toChange = false;
                setBulletDirectionsToward(midpointX(), midpointY(), i);
                respectBulletBounds(widthLimit, heightLimit);
                travelTimer = Timer.time - travelTime;
            }
            
            for(int x = 0; x<other.size(); x++){
                if(bullet.get(i).hasCollidedWith(other.get(x).boundsMiddle) && (!isEnemy && other.get(x).isEnemy || isEnemy && other.get(x).isEnemy == false) && other.get(x).hp > 0){
                    //Damage the enemy
                    damageChar(x, ((int)(damage*0.1)), other);
                }
            }
            
            if(Timer.time - travelTimer > travelTime){
                if(bullet.get(i).hasCollidedWith(boundsMiddle)){
                    abilityTimer2 = Timer.time - abilityCooldown2;
                }
                if(bullet.get(i).midpointX > widthLimit || bullet.get(i).midpointX < 0 || bullet.get(i).midpointY > heightLimit || bullet.get(i).midpointY < 25 || bullet.get(i).hasCollidedWith(boundsMiddle)){
                    bullet.remove(i);
                }
            }
        }
    }
    
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if(hp>0){
            if(!performingAbility1() && !performingAbility2()){
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
                    if(Timer.time - touchingTimer > toHitTime){
                        r = (int)(1+Math.random()*100);
                        if(r < 50){
                            ability1(other);
                        }else{
                            attack(other);
                        }
                        touchingTimer = Timer.time;
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime*1.5f);
                    if(r == 1 || r == 2){
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0){
                            ability2(other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY());
                        }else{
                            ability2(other.get(getClosestChar(other)).midpointX(), other.get(getClosestChar(other)).midpointY());
                        }
                    }
                }
                moveSpecial(other, widthLimit, heightLimit);
                super.move(widthLimit, heightLimit);
            }else{
                checkForAbility2();
            }
            stopAnim(widthLimit, heightLimit);
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downStill);
            }
        }
        moveStar(other, widthLimit, heightLimit);
    }
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i < bullet.size(); i++){
            bullet.get(i).paint(g);
        }
    }
}