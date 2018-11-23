//Imported to use array lists
import java.util.ArrayList;
//Imported to use images
import java.awt.*;
public class ShadowHeartless extends Character{
    //If the character is hittable or not
    boolean vulnerable = true, stopAfterSlide = false;
    //Images to be used when invulnerable
    Image invUpLeftMove, invUpRightMove, invDownLeftMove, invDownRightMove;
    
    public ShadowHeartless(//Movement images
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
                 //Specific Images for this class
                 Image pinvUpLeftMove, Image pinvUpRightMove, Image pinvDownLeftMove, Image pinvDownRightMove
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
                     name = "Shadow";
                     //Specific Images for this class
                     invUpLeftMove = pinvUpLeftMove; invUpRightMove = pinvUpRightMove; invDownLeftMove = pinvDownLeftMove; invDownRightMove = pinvDownRightMove;
    }
    
    //Overwritten to not be able to move elswhere while performing ability 1(slide)
    //Move horizontal and vertical
    //In case there are monsters
    void moveUp(ArrayList<Character> other){
        if(!attacking() && !performingAbility1()){
            speedX = 0;
            speedY = -speed;
        }
    }
    //In case there are no monsters
    void moveUp(){
        if(!attacking() && !performingAbility1()){
            speedX = 0;
            speedY = -speed;
        }
    }
    //In case there are monsters
    void moveDown(ArrayList<Character> other){
        if(!attacking() && !performingAbility1()){
            speedX = 0;
            speedY = speed;
        }
    }
    //In case there are no monsters
    void moveDown(){
        if(!attacking() && !performingAbility1()){
            speedX = 0;
            speedY = speed;
        }
    }
    //In case there are monsters
    void moveLeft(ArrayList<Character> other){
        if(!attacking() && !performingAbility1()){
            speedX = -speed;
            speedY = 0;
        }
    }
    //In case there are no monsters
    void moveLeft(){
        if(!attacking() && !performingAbility1()){
            speedX = -speed;
            speedY = 0;
        }
    }
    //In case there are monsters
    void moveRight(ArrayList<Character> other){
        if(!attacking() && !performingAbility1()){
            speedX = speed;
            speedY = 0;
        }
    }
    //In case there are no monsters
    void moveRight(){
        if(!attacking() && !performingAbility1()){
            speedX = speed;
            speedY = 0;
        }
    }
    //MoveDiagonally
    void moveUpLeft(){
        if(!attacking() && !performingAbility1()){
            if(boundsMiddle.x-speedX > 0 && boundsMiddle.y-speedY > 25){
                speedY = -mmove;
                speedX = -mmove;
            }else if(boundsMiddle.x-speedX > 0){
                moveLeft();
            }else if(boundsMiddle.y-speedY > 25){
                moveUp();
            }
        }
    }
    void moveUpRight(int widthLimit){
        if(!attacking() && !performingAbility1()){
            if(boundsMiddle.x+boundsMiddle.getWidth()+speed < widthLimit && boundsMiddle.y-speedY > 25){
                speedY = -mmove;
                speedX = mmove;
            }else if(boundsMiddle.x+boundsMiddle.getWidth()+speed < widthLimit){
                moveRight();
            }else if(boundsMiddle.y-speedY > 25){
                moveUp();
            }
        }
    }
    void moveDownLeft(int heightLimit){
        if(!attacking() && !performingAbility1()){
            if(boundsMiddle.x-speed > 3 && boundsMiddle.y+boundsMiddle.getHeight()+speed < heightLimit){
                speedY = mmove;
                speedX = -mmove;
            }else if(boundsMiddle.x-speed > 3){
                moveLeft();
            }else if(boundsMiddle.y+boundsMiddle.getHeight()+speed < heightLimit){
                moveDown();
            }
        }
    }
    void moveDownRight(int widthLimit, int heightLimit){
        if(!attacking() && !performingAbility1()){
            if(boundsMiddle.x+boundsMiddle.getWidth()+speed < widthLimit && boundsMiddle.y+boundsMiddle.getHeight()+speed < heightLimit){
                speedY = mmove;
                speedX = mmove;
            }else if(boundsMiddle.x+boundsMiddle.getWidth()+speed < widthLimit){
                moveRight();
            }else if(boundsMiddle.y+boundsMiddle.getHeight()+speed < heightLimit){
                moveDown();
            }
        }
    }
    
    void checkForAttack(ArrayList<Character> other){
        if(attacking() && Timer.time - hittingTimer > hittingAnimation/2 && canAttack == true){
            canAttack = false;
            for(int i = 0;i < other.size();i++){
                boolean hit = false;
                //According to the direction in which the character is facing, set animation and attacks to face that direction
                switch(direction){
                    case 0:
                        //if the character hits,set hit to true 
                        if(boundsUpLeft.intersects(other.get(i).boundsMiddle) || boundsUp.intersects(other.get(i).boundsMiddle) || boundsUpRight.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 1:
                        if(boundsUp.intersects(other.get(i).boundsMiddle) || boundsUpRight.intersects(other.get(i).boundsMiddle) || boundsRight.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 2:
                        if(boundsUpRight.intersects(other.get(i).boundsMiddle) || boundsRight.intersects(other.get(i).boundsMiddle) || boundsDownRight.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 3:
                        if(boundsRight.intersects(other.get(i).boundsMiddle) || boundsDownRight.intersects(other.get(i).boundsMiddle) || boundsDown.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 4:
                        if(boundsDownRight.intersects(other.get(i).boundsMiddle) || boundsDown.intersects(other.get(i).boundsMiddle) || boundsDownLeft.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 5:
                        if(boundsDown.intersects(other.get(i).boundsMiddle) || boundsDownLeft.intersects(other.get(i).boundsMiddle) || boundsLeft.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 6:
                        if(boundsDownLeft.intersects(other.get(i).boundsMiddle) || boundsLeft.intersects(other.get(i).boundsMiddle) || boundsUpLeft.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                    case 7:
                        if(boundsLeft.intersects(other.get(i).boundsMiddle) || boundsUpLeft.intersects(other.get(i).boundsMiddle) || boundsUp.intersects(other.get(i).boundsMiddle)){
                            hit = true;
                        }
                    break;
                }
                if(boundsMiddle.intersects(other.get(i).boundsMiddle)){
                    hit = true;
                }
                //If the user hit an enemy
                if(hit){
                    //No friendly fire!
                    if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                        damageChar(i, damage, other);
                    }
                }
            }
        }
    }
    
    //The attacking algorythm
    //Overwritten to be able to attack while performing ability 2
    void attack(ArrayList<Character> other){
        if(hp > 0){
            //Set the hit to false
            //Only attack after the previous attack has finished
            if(!attacking() && !performingAbility1()){
                movingAwayFromChar = false;
                movingAroundChar = false;
                //Make this true so he stops after he attacks
                stopAfterAttacking = true;
                //Attacking if is protagonist(player controlled)
                //Restart hitting Timer
                hittingTimer = Timer.time;
                //Set slower speed
                setSpeed((int)(speed*0.7f));
                speedUpAfterAttack = true;
                //The damage may go out
                canAttack = true;
                
                if(isProtagonist){
                    for(int i = 0;i < other.size();i++){
                        boolean hit = false;
                        //According to the direction in which the character is facing, set animation and attacks to face that direction
                        switch(direction){
                            case 0:
                                //set the animation corresponding to the directon faced 
                                c.setImage(upAttack);
                            break;
                            case 1:
                                c.setImage(upRightAttack);
                            break;
                            case 2:
                                c.setImage(rightAttack);
                            break;
                            case 3:
                                c.setImage(downRightAttack);
                            break;
                            case 4:
                                c.setImage(downAttack);
                            break;
                            case 5:
                                c.setImage(downLeftAttack);
                            break;
                            case 6:
                                c.setImage(leftAttack);
                            break;
                            case 7:
                                c.setImage(upLeftAttack);
                            break;
                        }
                    }
                }else{
                    int otherX = other.get(getClosestChar(other)).midpointX();
                    int otherY = other.get(getClosestChar(other)).midpointY();
                    int myX = midpointX();
                    int myY = midpointY();
                    if(otherX > myX && otherY > myY){
                        c.setImage(downRightAttack);
                        direction = 3;
                    }else if(otherX < myX && otherY > myY){
                        c.setImage(downLeftAttack);
                        direction = 5;
                    }else if(otherX < myX && otherY < myY){
                        c.setImage(upLeftAttack);
                        direction = 7;
                    }else if(otherX >= myX && otherY <= myY){
                        c.setImage(upRightAttack);
                        direction = 1;
                    }
                }
            }
        }
    }
    
    //Overloaded for the invulenrable images
    void stop(int widthLimit, int heightLimit){
        if((!attacking() && !performingAbility1()) || (boundsMiddle.x+boundsMiddle.getWidth()+speed > widthLimit || boundsMiddle.y+boundsMiddle.getHeight()+speed > heightLimit || boundsMiddle.x-speedX < 3 || boundsMiddle.y-speedY < 25)){
            if(vulnerable){
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
            }else{
                if( (speedX > 0 && speedY > 0) || (Timer.time - downTimer < 50 && Timer.time - rightTimer < 50) ){
                    setImage(invDownRightMove);
                }else if( (speedX > 0 && speedY < 0) || (Timer.time - upTimer < 50 && Timer.time - rightTimer < 50) ){
                    setImage(invUpRightMove);
                }else if( (speedX < 0 && speedY < 0) || (Timer.time - upTimer < 50 && Timer.time - leftTimer < 50) ){
                    setImage(invUpLeftMove);
                }else if( (speedX < 0 && speedY > 0) || (Timer.time - downTimer < 50 && Timer.time - leftTimer < 50) ){
                    setImage(invDownLeftMove);
                }else if(speedX == 0 && speedY > 0){
                    setImage(invDownRightMove);
                }else if(speedX == 0 && speedY < 0){
                    setImage(invUpLeftMove);
                }else if(speedX > 0 && speedY == 0){
                    setImage(invUpRightMove);
                }else if(speedX < 0 && speedY == 0){
                    setImage(invDownLeftMove);
                }else{
                    setImage(invDownLeftMove);
                }
            }
            //Speeds become 0
            speedX = speedY = 0;
        }
    }
    
    //Move the character IF it does not go out of the screen and change it's image
    void doMove(int widthLimit, int heightLimit){
        if(speedX > 0 && speedY > 0){
            direction = 3;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit && boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                if(vulnerable){
                    setImage(downRightMove);
                }else{
                    setImage(invDownRightMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX > 0 && speedY < 0){
            direction = 1;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit && boundsMiddle.y-speedY > 25){
                if(vulnerable){
                    setImage(upRightMove);
                }else{
                    setImage(invUpRightMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY < 0){
            direction = 7;
            if(boundsMiddle.x-speedX > 3 && boundsMiddle.y-speedY > 25){
                if(vulnerable){
                    setImage(upLeftMove);
                }else{
                    setImage(invUpLeftMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY > 0){
            direction = 5;
            if(boundsMiddle.x-speedX > 3 && boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                if(vulnerable){
                    setImage(downLeftMove);
                }else{
                    setImage(invDownLeftMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX == 0 && speedY > 0){
            direction = 4;
            if(boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                if(vulnerable){
                    setImage(downRightMove);
                }else{
                    setImage(invDownRightMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX == 0 && speedY < 0){
            direction = 0;
            if(boundsMiddle.y-speedY > 25){
                if(vulnerable){
                    setImage(upLeftMove);
                }else{
                    setImage(invUpLeftMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX > 0 && speedY == 0){
            direction = 2;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit){
                if(vulnerable){
                    setImage(upRightMove);
                }else{
                    setImage(invUpRightMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY == 0){
            direction = 6;
            if(boundsMiddle.x-speedX > 3){
                if(vulnerable){
                    setImage(downLeftMove);
                }else{
                    setImage(invDownLeftMove);
                }
            }else{
                stop(widthLimit, heightLimit);
            }
        }
        //Speeds are optimized to be +ve, -ve and 0 for this reason
        c.moveRight(speedX);
        c.moveDown(speedY);
    }
    
    //Method to damgage the character overloaded for this class
    //Since this character may be invulnerable
    void takeDamage(int dmg, int difficulty, boolean canPlaySound){
        if(vulnerable || attacking()){
            super.takeDamage(dmg, difficulty, canPlaySound);
        }
    }
    
    //A restriction is set for the changing of the image in certain cases
    void setImage(Image y){
        if(!attacking() && !performingAbility1()){
            c.setImage(y);
        }
    }
    
    //Slide and damage all characters in his way
    void ability1(ArrayList<Character> other, int x, int y, int widthLimit, int heightLimit){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && hp > 0 && !attacking()){
            setSpeed(getSpeed()*2);
            //Make character move at a certain direction
            if(c.getMidpointX()>x){
                setImage(ability1Left);
            }else{
                setImage(ability1Right);
            }
            startAbilityTimer1();
            stopAfterSlide = true;
            moveTowardsPoint(x, y);
        }
    }
    //Become invulnerable for a duration
    void ability2(){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && hp > 0){
            startAbilityTimer2();
            vulnerable = false;
            c.setImage(invDownRightMove);
        }
    }
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other , int widthLimit, int heightLimit){
        if(hp>0){
            checkForAttack(other);
            if(!performingAbility1() && ! attacking()){
                moveSpecial(other, widthLimit, heightLimit);
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
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0){
                            ability1(other, other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY(), widthLimit, heightLimit);
                        }else{
                            ability1(other, other.get(getClosestChar(other)).midpointX(), other.get(getClosestChar(other)).midpointY(), widthLimit, heightLimit);
                        }
                    }else if(r == 2){
                        ability2();
                    }
                }
                if(Timer.time - abilityTimer2 > ability2AnimationTimer && !vulnerable){
                    vulnerable = true;
                    setImage(downRightMove);
                }
                if(!performingAbility1() && stopAfterSlide == true){
                    stopAfterSlide = false;
                    stop(widthLimit, heightLimit);
                    if(vulnerable){
                        setImage(downRightMove);
                    }else{
                        setImage(invDownRightMove);
                    }
                    setSpeed(getBaseSpeed());
                }
            }
            for(int i = 0; i<other.size(); i++){
                if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                    Rectangle bm = other.get(i).boundsMiddle;
                    if(performingAbility1() && bm.intersects(boundsMiddle)){
                        damageChar(i, (int)(damage*0.25), other);
                    }
                }
            }
            doMove(widthLimit, heightLimit);
            stopAnim(widthLimit,heightLimit);
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downRightMove);
            }
        }
    }
        
    public void paint(Graphics g){
        super.paint(g);
    }
    
}