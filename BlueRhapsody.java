import java.awt.*;
import java.util.ArrayList;
public class BlueRhapsody extends Character{
    //To determine wether the blue rhapsody is in ranged or melee mode
    boolean ranged = false;
    //Rectangle for the full boy
    Rectangle fullBody;
    //Image to be displayed whenicy blast hits
    Image icyBlast;
    //The timer for how long the animation should be displayed for
    long icyBlastTimer;
    long icyBlastDuration;
    //The coordinates for the icy blast
    int ibx, iby;
    //Coordinates of the bullet
    int bulletX, bulletY;
    //Fired or not
    boolean ibFired = false;
    //The sprite for the icyBlast snowball
    CircleSprite snowball;
    Image snowballImage;
    //Speeds for the icyblasts and radius
    int ibSpeed, ibSpeedX, ibSpeedY, ibRadius, snowballRadius;
    //Timer for when the character is to shoot
    long hittingFrequency;
    //The timer and duration for being ranged
    long rangedTimer, rangedDuration;
    
    boolean canAttackRanged = false;
    boolean isAttackingRanged = false;
    
    //Constructor
    public BlueRhapsody(//Movement images
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
                 //Image to be displayed when
                 Image picyBlast,
                 //The timer for how long the animation should be displayed for
                 long picyBlastDuration,
                 //Icy blast snowball image
                 Image snowballImage,
                 //Speeds for the icyblasts and radius
                 int pibSpeed, int pibRadius, int psnowballRadius,
                 //Timer for when the character is to shoot
                 long phittingFrequency,
                 //The duration for being ranged
                 long prangedDuration
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
                       name = "Blue Rhapsody";
                       //Rectangle for the full boy
                       fullBody = new Rectangle(sideWidth*2 + midWidth, sideHeight*2 + midHeight);
                       //Image to be displayed when
                       icyBlast = picyBlast;
                       //The timer for how long the animation should be displayed for
                       icyBlastDuration = picyBlastDuration;
                       //The sprite for the icyBlast snowball
                       snowball = new CircleSprite(snowballImage, psnowballRadius);
                       //Speeds for the icyblasts and radius
                       ibSpeed = pibSpeed; ibRadius = pibRadius;
                       //Timer for when the character is to shoot
                       hittingFrequency = phittingFrequency;
                       //The duration for being ranged
                       rangedDuration = prangedDuration;
    }
    
    //Overloaded for full body seting
    void resetBounds(){
        super.resetBounds();
        fullBody.x = boundsUpLeft.x;
        fullBody.y = boundsUpLeft.y;
    }
    
    //Overloaded not to move towards character while ranged and attacking
    //Method to check if the charactersis moving in a special way and move if he
    void moveSpecial(ArrayList<Character> other, int widthLimit, int heightLimit){
        checkForAttack(other);            
        if(!attacking() && speedUpAfterAttack == true){
            setSpeed(getBaseSpeed());
            speedUpAfterAttack = false;
        }

        if(getClosestChar(other) != -1){
            
            //Move towars the closest character
            if(movingTowardsChar == true){
                moveTowardsChar(other.get(getClosestChar(other)));
            }
            //Stop if shooting a ranged attack untiol animation ends
            if(performingAbility1() || (!attacking() && isAttackingRanged == true)){
                stop(widthLimit, heightLimit);
                isAttackingRanged = false;
            }
            //Move away from the closest character
            if(movingAwayFromChar == true){
                moveAwayFromChar(other.get(getClosestChar(other)));
            }
            //Move around the closest character
            if(movingAroundChar == true){
                moveAroundChar(other.get(getClosestChar(other)));
            }
        }
    }
    
    //stop moving
    void stop(int widthLimit, int heightLimit){
        if((!attacking() && !performingAbility2()) || (boundsMiddle.x+boundsMiddle.getWidth()+speed > widthLimit || boundsMiddle.y+boundsMiddle.getHeight()+speed > heightLimit || boundsMiddle.x-speedX < 3 || boundsMiddle.y-speedY < 25)){
            if(!performingAbility1()){
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
            }
            //Speeds become 0
            speedX = speedY = 0;
        }
    }
    
    void checkForAttack(ArrayList<Character> other){
        if(attacking() && Timer.time - hittingTimer > hittingAnimation/2 && ranged == false && canAttack == true){
            canAttack = false;
            
            //Damage character if hit
            fullBody.setLocation((int)boundsUpLeft.getX(), (int)boundsUpLeft.getY());
            for(int i = 0;i<other.size();i++){
                if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                    if(other.get(i).boundsMiddle.intersects(fullBody)){
                        damageChar(i, damage, other);
                    }
                }
            }
        }else if(Timer.time - hittingTimer > hittingAnimation - 150 && ranged == true && canAttackRanged == true){
            shootBullet(bulletX, bulletY);
            canAttackRanged = false;
        }
    }
    
    //For this enemy, he hits all nearby enemies with his melee attack
    void attack(ArrayList<Character> other){
        //Only if in melee mode and the animation of the previous attack has finished
        if(Timer.time - hittingTimer > hittingAnimation && hp > 0){
            canAttack = true;
            
            //Set slower speed
            setSpeed((int)(speed*0.7f));
            speedUpAfterAttack = true;
            
            movingAwayFromChar = false;
            movingAroundChar = false;
            //So the character stops moving after he attacks
            stopAfterAttacking = true;
            //Restart hitting Timer
            hittingTimer = Timer.time;
            //Change the image to either left or right
            if(isProtagonist){
                if(direction >=0 && direction >= 3){
                    c.setImage(rightAttack);
                }else{
                    c.setImage(leftAttack);
                }
            }else{
                if(midpointX() > other.get(getClosestChar(other)).midpointX()){
                    c.setImage(rightAttack);
                }else{
                    c.setImage(leftAttack);
                }
            }
        }
    }
    void attack(int x, int y){
        //Attack when ranged
        if(ranged == true && !performingAbility1() && !attacking() && !performingAbility2() && hp > 0 && canAttackRanged == false){
            bulletX = x; bulletY = y;
            hittingTimer = Timer.time;
            if(midpointX() > x){
                c.setImage(ability1Left);
            }else{
                c.setImage(ability1Right);
            }
            canAttackRanged = true;
            isAttackingRanged = true;
            stopAfterAttacking = true;
        }
    }
    //Be ranged for an amount of time
    void ability1(){
        if(Timer.time - abilityTimer1 > abilityCooldown1){
            //Restart timer
            rangedTimer = Timer.time;
            ranged = true;
            startAbilityTimer1();
        }
    }
    //Shoot the icy blast
    void ability2(int x, int y){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && !attacking() && !performingAbility1() && hp > 0){
            if(ibFired == false){
                snowball.setPoints(midpointX() - snowball.getRadius(), midpointY() - snowball.getRadius());
                
                //The length between the points is calculated
                double hyp = Math.sqrt(Math.pow(x-snowball.midpointX,2)+Math.pow(y-snowball.midpointY,2));
                //The hyp is divided by the bullet speed and the answer is stored in dSpeed
                double dSpeed = hyp/ibSpeed;
                //This is then used to divide the length between vertical and horizontal distances by it
                //to determine how many points the bullet must travel in each frame
                double xx = Math.abs(x-snowball.midpointX)/dSpeed;
                double yy = Math.abs(y-snowball.midpointY)/dSpeed;
                //The speed is then rounded to the nearest integer. This is unfortunate as the angles may not be perfect
                //and may miss if the target is very thin.
                
                if((int)xx+0.5>xx){
                    ibSpeedX = (int)xx;
                }else{
                    ibSpeedX = (int)xx++;
                }
                if((int)yy+0.5>yy){
                    ibSpeedY = (int)yy;
                }else{
                    ibSpeedY = (int)yy++;
                }
                //The direction of the bullet is Down and Right, therefore we ened to set the variables to negative
                //if they need to go Up or Left.
                if(y<=snowball.midpointY){
                    ibSpeedY = -ibSpeedY;
                }
                if(x <= snowball.midpointX){
                    ibSpeedX = -ibSpeedX;
                    c.setImage(ability2Left);
                }else{
                    c.setImage(ability2Right);
                }
                startAbilityTimer2();
            }
        }
    }
    void checkForAbility2(ArrayList<Character> other){
        if(performingAbility2() && Timer.time - abilityTimer2 > ability2AnimationTimer/2 && canPerformAbility2 == true){
            ibFired = true;
            canPerformAbility2 = false;
        }
    }
    //Move the snowball
    void moveSnowball(ArrayList<Character> other, int widthLimit, int heightLimit){
        //Only move if fired
        if(ibFired){
            //Move
            snowball.moveRight(ibSpeedX);
            snowball.moveDown(ibSpeedY);
            //For the number of enemies, check for first collision
            for(int i = 0; i < other.size(); i++){
                //If the snowball hits
                if(snowball.hasCollidedWith(other.get(i).boundsMiddle) && (!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp > 0){
                    //No longer fired
                    ibFired = false;
                    ibx = snowball.midpointX - ibRadius;
                    iby = snowball.midpointY - ibRadius;
                    //Create a new circle sprite to detect collision
                    CircleSprite blast = new CircleSprite(ibx, iby, ibRadius);
                    icyBlastTimer = Timer.time;
                    for(int i2 = 0; i2 < other.size(); i2++){
                        if((!isEnemy && other.get(i2).isEnemy || isEnemy && other.get(i2).isEnemy == false) && blast.hasCollidedWith(other.get(i2).boundsMiddle)){
                            damageChar(i2, ((int)(damage)*3), other);
                        }
                    }
                }
            }
            if(snowball.midpointX > widthLimit || snowball.midpointX < 0 || snowball.midpointY > heightLimit || snowball.midpointY < 25){
                ibFired = false;
            }
        }
    }
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if((hp>0 && !isEnemy) || isEnemy){
            if(!performingAbility2()){
                if(!isProtagonist){
                    super.moveRandomly(other, widthLimit, heightLimit);
                    if(!ranged){
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
                            touchingTimer = Timer.time;
                        }
                    }else{
                        if(Timer.time - abilityTimer1 > hittingFrequency){
                            if(isEnemy){
                                int r = (int)(Math.random()*100);
                                if(r > 50 && other.get(getProtagonist(other)).hp > 0 ){
                                    attack(other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY());
                                }else{
                                    attack(other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY());
                                }
                            }else{
                                attack(other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY());
                            }
                        }
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime*1.5f);
                    if(r == 1){
                        ability1();
                    }else if(r == 2){
                        if(isEnemy){
                            ability2(other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY());
                        }else{
                            ability2(other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY());
                        }
                    }
                }
                if(ranged && Timer.time - rangedDuration > rangedTimer){
                    ranged = false;
                    isAttackingRanged = false;
                }
                moveSpecial(other, widthLimit, heightLimit);
                if(!isAttackingRanged || ranged == false){
                    super.move(widthLimit, heightLimit);
                }
            }else{
                checkForAbility2(other);
            }
            stopAnim(widthLimit,heightLimit);
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downRightMove);
            }
        }
        moveBullet2(widthLimit, heightLimit, other);
        moveSnowball(other, widthLimit, heightLimit);
    }
    //Paint method
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i<bullet.size(); i++){
            bullet.get(i).paint(g);
        }
        if(ibFired == true){
            snowball.paint(g);
        }
        if(Timer.time - icyBlastTimer < icyBlastDuration){
            g.drawImage(icyBlast, ibx, iby, null);
        }
        //g.fillRect(fullBody.x, fullBody.y, (int)fullBody.getWidth(), (int)fullBody.getHeight());
    }
}