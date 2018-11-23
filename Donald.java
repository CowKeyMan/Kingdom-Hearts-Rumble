//Imported to use array list
import java.util.ArrayList;
//Imported to use images
import java.awt.*;
public class Donald extends Character{
    Image thunderImage, healImage;
    //Thunder Width, thunder full image height, thunder lenght to hit tnemy and damage him
    int thunderWidth, thunderHeight, thunderLength;
    //The duration for the thunder image animation
    long thunderDuration;
    int tx = 0, ty = 0;
    //The durations for the heal image animation
    long healTimer, healDuration;
    int hChar;
    //The amount of times damage is multiplied to heal
    int healToDamage;
    
    //constructor
    public Donald(//Movement images
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
                 int pcx,int  pcy,
                 //The time to hit the character
                 int pmaxToHitTime,
                 //Images for the UI and their coordinates
                 Image phealthyImage,  Image phurtImage,
                 int phealthyImagex, int phealthyImagey,
                 int phurtImagex, int phurtImagey,
                 //Images for the ability icons
                 Image pabilityImage1, Image pabilityImage2,
                 //Specific Images for this class
                 Image pthunderImage, Image phealImage,
                 //Thunder ability Radius
                 int pthunderWidth, int pthunderHeight, int pthunderLength,
                 //The duration for the thunder image animation
                 long pthunderDuration,
                 //The durations for the heal image animation
                 long phealDuration,
                 //The amount of times damage is multiplied to heal
                 int phealToDamage
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
                     name = "Donald";
                     //Specific Images for this class
                     thunderImage = pthunderImage; healImage = phealImage;
                     //Thunder ability Radius
                     thunderWidth = pthunderWidth; thunderHeight = pthunderHeight; thunderLength = pthunderLength;
                     //The duration for the thunder image animation
                     thunderDuration = pthunderDuration;
                     //The durations for the heal image animation
                     healDuration = phealDuration;
                     //The amount of times damage is multiplied to heal
                     healToDamage = phealToDamage;
                    }
    //HEAL!
    void ability1(ArrayList<Character> other, int x, int y, int widthLimit, int heightLimit){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && !performingAbility1() && hp > 0 && !attacking()){
            //The first character
            Rectangle rec = new Rectangle(x, y, 1 , 1);
            boolean cont = true;
            for(int i = 0; i < other.size() && cont == true; i++){
                if(other.get(i).c.hasCollidedWith(rec)){
                    if(!(!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp < other.get(i).maxHP){
                        //Set for animation
                        healTimer = Timer.time;
                        hChar = i;
                        //negative damage
                        damageChar(i, -(int)(damage*healToDamage + other.get(i).maxHP *0.2), other);
                        startAbilityTimer1();
                        stop(widthLimit, heightLimit);
                        //Set the character image
                        if(other.get(i).c.getMidpointX() < c.getMidpointX()){
                            c.setImage(ability1Left);
                        }else{
                            c.setImage(ability1Right);
                        }
                        cont = false;
                    }
                }
            }
        }
    }
    //Thunder(damage enemies around a circular area)
    void ability2(ArrayList<Character> other, int x, int y, int widthLimit, int heightLimit){
        //Check Timer
        if(Timer.time - abilityTimer2 > abilityCooldown2 && hp > 0 && !attacking()){
            stop(heightLimit, widthLimit);
            //Set thunder positions
            tx = x;
            ty = y;
            //Make a new Sprite to detect collision
            
            Sprite thunder = new Sprite(thunderImage, tx - thunderWidth/2, ty - thunderLength/2, thunderWidth, thunderLength);
            
            //Damage if it collides with an enemy
            for(int i = 0; i<other.size(); i++){
                if(!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false){
                    if(thunder.hasCollidedWith(other.get(i).boundsMiddle)){
                        damageChar(i, damage*2, other);
                    }
                }
            }
            //Set the image
            if(x < c.getMidpointX()){
                c.setImage(ability2Left);
            }else{
                c.setImage(ability2Right);
            }
            //Restart timer
            startAbilityTimer2();
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
                        attack(other);
                        touchingTimer = Timer.time;
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime*1.5f);
                    if(r == 1){
                        if(other.get(0).getAllyWithLeastHP(other) != - 1){
                            ability1(other, other.get(getAllyWithLeastHP(other)).midpointX(),  other.get(getAllyWithLeastHP(other)).midpointY(), widthLimit, heightLimit);
                        }
                    }else if(r == 2){
                        if(getAllyWithLeastHP(other) != -1){
                            ability2(other, other.get(getEnemyWithLeastHP(other)).midpointX(),  other.get(getEnemyWithLeastHP(other)).midpointY(), widthLimit, heightLimit);
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
    }
    public void paint(Graphics g, ArrayList<Character> other){
        super.paint(g);
        if(Timer.time - abilityTimer2 < thunderDuration){
            g.drawImage(thunderImage, tx - thunderWidth/2, ty - thunderHeight + thunderLength/2, null);
        }
        if(Timer.time - healTimer < healDuration){
            g.drawImage(healImage, (other.get(hChar).c.getX() + other.get(hChar).c.getWidth()/2) - healImage.getWidth(null)/2, (other.get(hChar).c.getY() + other.get(hChar).c.getHeight()) - healImage.getHeight(null), null);
        }
    }
}