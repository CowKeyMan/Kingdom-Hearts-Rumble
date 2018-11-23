//Imported to use Array Lists
import java.util.ArrayList;
//Imported to use images
import java.awt.*;
public class Riku extends Character{
    //Animation image
    Image teleportImage;
    int bulletAmount;
    //the coordinates the teleport animation appears on
    int tpx, tpy;
    
    //Constructor
    public Riku(//Movement images
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
                 //Specific Images
                 Image pteleportImage,
                 //number of bullets for ability
                 int pbulletAmount
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
                     name = "Riku";
                     //Specific Images
                     teleportImage = pteleportImage;
                     //number of bullets for ability
                     bulletAmount = pbulletAmount;
    }
    
    //Teleport anywhere on the map
    void ability1(int x, int y, int widthLimit, int heightLimit){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && !performingAbility2() && hp > 0 && !attacking()){
            //Set the positions of the tp animation at where the character is currently
            tpx = boundsMiddle.x;
            tpy = boundsMiddle.y;
            //Teleport character
            c.setPoints(x-(int)boundsMiddle.getWidth()/2, y-(int)boundsMiddle.getHeight()/2);
            abilityTimer1 = Timer.time;
            resetBounds();
            respectBounds(widthLimit, heightLimit);
        }
    }
    //shoot a multitude of bullets at one time
    void ability2(int x, int y, int widthLimit, int heightLimit){
        //Check timer
        if(Timer.time - abilityTimer2 > abilityCooldown2 && hp>0 && !attacking()){
            //For the amount of bullets
            //Shoot bullet and each time leave a gap of 30 between each bullet
            if((midpointX() <= x && midpointY() <= y) || (midpointX() >= x && midpointY() >= y)){
                for(int i = 0; i<bulletAmount;i++){
                    if(i%2 == 0){
                        shootBullet(x + i*30, y + i*-30);
                    }else{
                        shootBullet(x + i*-30, y + i*30);
                    }
                }
            }else{
                for(int i = 0; i<bulletAmount;i++){
                    if(i%2 == 0){
                        shootBullet(x + i*-30, y + i*-30);
                    }else{
                        shootBullet(x + i*30, y + i*30);
                    }
                }
            }
            
            //Set the character image
            stop(widthLimit, heightLimit);
            if(x < c.getMidpointX()){
                c.setImage(ability2Left);
            }else{
                c.setImage(ability2Right);
            }
            startAbilityTimer2();
        }
    }
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if(hp>0){
            if(!performingAbility2()){
                if(!isProtagonist){
                    super.moveRandomly(other, widthLimit, heightLimit);
                    //Time the timer to hit attack, reset when no longer hitting
                    boolean touching = false;
                    for(int i = 0; i<other.size() && touching == false; i++){
                        if(!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false){
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
                        attack(other);
                        touchingTimer = Timer.time;
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime*1.5f);
                    if(r == 1){
                        int r = (int)(Math.random()*100);
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0 && r > 50){
                            ability1(other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY(), widthLimit, heightLimit);
                        }else{
                            ability1(other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY(), widthLimit, heightLimit);
                        }
                        toHitTime = 100;
                    }else if(r == 2){
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0){
                            ability2(other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY(), widthLimit, heightLimit);
                        }else{
                            ability2(other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY(), widthLimit, heightLimit);
                        }
                    }
                }
                moveSpecial(other, widthLimit, heightLimit);
                super.move(widthLimit, heightLimit);
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
        moveBullet2(widthLimit, heightLimit, other);
    }
    public void paint(Graphics g){
        super.paint(g);
        if(Timer.time - abilityTimer1 < 1300){
            g.drawImage(teleportImage, tpx, tpy, null);
        }
        for(int i = 0; i<bullet.size(); i++){
            bullet.get(i).paint(g);
        }
    }
}