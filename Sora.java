import java.awt.*;
import java.util.ArrayList;
public class Sora extends Character{
    //More attacks
    Image leftAttack2, leftAttack3,rightAttack2, rightAttack3;
    //For combos, if an attack is failed, restart combo
    int attackNo = 0;
    Sprite wind;
    //duration for the wind
    long windDuration;
    boolean hit2 = false;
    
    int bulletDirectionX, bulletDirectionY;
    //Contructor
    public Sora(//Movement images
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
                 Image pleftAttack2, Image pleftAttack3,Image prightAttack2, Image prightAttack3,
                 //Specific sprites
                 Image pimageWind,
                 Sprite pwind,
                 //Timer for wind
                 long pwindDuration){
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
                             //Images for the UI and their coordinates
                             phealthyImage, phurtImage,
                             phealthyImagex, phealthyImagey,
                             phurtImagex, phurtImagey,
                             //Images for the ability icons
                             pabilityImage1, pabilityImage2);
                       name = "Sora";
                       //Specific images
                       leftAttack2 = pleftAttack2; leftAttack3 = pleftAttack3; rightAttack2 = prightAttack2; rightAttack3 = prightAttack3;
                       //Wind Sprite
                       wind = new Sprite(pimageWind);
                       //Wind duartion
                       windDuration = pwindDuration;
    }
    
    void checkForAttack(ArrayList<Character> other){
        if(attacking() && Timer.time - hittingTimer > hittingAnimation/2 && canAttack == true){
            canAttack = false;
            for(int i = 0;i < other.size();i++){
                boolean hit = false;
                //No friendly fire!
                if((!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                    //According to the direction in which the character is facing, set animation and attacks to face that direction
                    switch(direction){
                        case 0:
                            //if the character hits,set hit to true 
                            if(boundsUpLeft.intersects(other.get(i).boundsMiddle) || boundsUp.intersects(other.get(i).boundsMiddle) || boundsUpRight.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 1:
                            if(boundsUp.intersects(other.get(i).boundsMiddle) || boundsUpRight.intersects(other.get(i).boundsMiddle) || boundsRight.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 2:
                            if(boundsUpRight.intersects(other.get(i).boundsMiddle) || boundsRight.intersects(other.get(i).boundsMiddle) || boundsDownRight.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 3:
                            if(boundsRight.intersects(other.get(i).boundsMiddle) || boundsDownRight.intersects(other.get(i).boundsMiddle) || boundsDown.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 4:
                            if(boundsDownRight.intersects(other.get(i).boundsMiddle) || boundsDown.intersects(other.get(i).boundsMiddle) || boundsDownLeft.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 5:
                            if(boundsDown.intersects(other.get(i).boundsMiddle) || boundsDownLeft.intersects(other.get(i).boundsMiddle) || boundsLeft.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 6:
                            if(boundsDownLeft.intersects(other.get(i).boundsMiddle) || boundsLeft.intersects(other.get(i).boundsMiddle) || boundsUpLeft.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                        case 7:
                            if(boundsLeft.intersects(other.get(i).boundsMiddle) || boundsUpLeft.intersects(other.get(i).boundsMiddle) || boundsUp.intersects(other.get(i).boundsMiddle)){
                                hit = hit2 = true;
                            }
                        break;
                    }
                    if(boundsMiddle.intersects(other.get(i).boundsMiddle)){
                        hit = hit2 = true;
                    }
                    //If the user hit an enemy
                    if(hit){
                        //Inflict damage
                        damageChar(i, (int)(damage * (attackNo+1) * 0.7f), other);
                    }
                }
            }
            if(!hit2){
                 //Start combo timer from 0 if he doesn't hit
                 attackNo = 0;
            }else{
                //Increment attack number
                attackNo = (attackNo==2)? 0:attackNo+1;
            }
        }
    }
    
    //The attacking algorythm
    //Since Sora has 3 different and attacks, this method is made specifically for it
    void attack(ArrayList<Character> other){
        //Set the hit to false
        hit2 = false;
        //Only attack after the previous attack has finished
        if(Timer.time - hittingTimer > hittingAnimation && hp > 0){
            canAttack = true;
            
            //Set slower speed
            setSpeed((int)(speed*0.7f));
            speedUpAfterAttack = true;
            
            movingAwayFromChar = false;
            movingAroundChar = false;
            stopAfterAttacking = true;
            //If the character takes too long o hit, start count from 0
            if(Timer.time - hittingTimer > hittingAnimation + 500){
                attackNo = 0;
            }
            //Restart hitting Timer
            hittingTimer = Timer.time;
            switch(direction){
                case 0:
                    //Acording to the attack number, set the animation
                    if(attackNo == 2){
                        c.setImage(leftAttack3);
                    }else if(attackNo == 1){
                        c.setImage(leftAttack2);
                    }else{
                        c.setImage(upAttack);
                    }
                break;
                case 1:
                    if(attackNo == 2){
                        c.setImage(rightAttack3);
                    }else if(attackNo == 1){
                        c.setImage(rightAttack2);
                    }else{
                        c.setImage(upRightAttack);
                    }
                break;
                case 2:
                    if(attackNo == 2){
                        c.setImage(rightAttack3);
                    }else if(attackNo == 1){
                        c.setImage(rightAttack2);
                    }else{
                        c.setImage(rightAttack);
                    }
                break;
                case 3:
                    if(attackNo == 2){
                        c.setImage(rightAttack3);
                    }else if(attackNo == 1){
                        c.setImage(rightAttack2);
                    }else{
                        c.setImage(downRightAttack);
                    }
                break;
                case 4:
                    if(attackNo == 2){
                        c.setImage(rightAttack3);
                    }else if(attackNo == 1){
                        c.setImage(rightAttack2);
                    }else{
                        c.setImage(downAttack);
                    }
                break;
                case 5:
                    if(attackNo == 2){
                        c.setImage(leftAttack3);
                    }else if(attackNo == 1){
                        c.setImage(leftAttack2);
                    }else{
                        c.setImage(downLeftAttack);
                    }
                break;
                case 6:
                    if(attackNo == 2){
                        c.setImage(leftAttack3);
                    }else if(attackNo == 1){
                        c.setImage(leftAttack2);
                    }else{
                        c.setImage(leftAttack);
                    }
                break;
                case 7:
                    if(attackNo == 2){
                        c.setImage(leftAttack3);
                    }else if(attackNo == 1){
                        c.setImage(leftAttack2);
                    }else{
                        c.setImage(upLeftAttack);
                    }
                break;
            }
        }
    }
    //ABILITY 1!
    //Throw keyblade which bounces off of anemies
    void ability1(int x, int y, int widthLimit, int heightLimit){
        //If the cooldown is back and keyblade is not still rebounding
        if(Timer.time - abilityTimer1 > abilityCooldown1 && !attacking()  && !performingAbility1() && !performingAbility2() && hp > 0){
            //Start the cooldown
            startAbilityTimer1();
            //Set the character image
            stop(widthLimit, heightLimit);
            if(x < c.getMidpointX()){
                c.setImage(ability1Left);
            }else{
                c.setImage(ability1Right);
            }
            bulletDirectionX = x; bulletDirectionY = y;
        }
    }
    void checkForAbility1(){
        if(performingAbility1() && Timer.time - abilityTimer1 > ability1AnimationTimer/2 && canPerformAbility1 == true){
            //Shoot the keyblade/bullet
            shootBullet(bulletDirectionX, bulletDirectionY);
            canPerformAbility1 = false;
        }
    }
    //Move Keyblade after throwing it
    void moveBullet(int widthLimit, int heightLimit, ArrayList<Character> other){
        for(int x = 0; x < bullet.size(); x++){
            //Move the keyblade
            bullet.get(x).moveRight(bullet.get(x).bulletSpeedX);
            bullet.get(x).moveDown(bullet.get(x).bulletSpeedY);
            for(int i = 0; bullet.size() != 0 && i < other.size();i++){
                if(bullet.get(x).hasCollidedWith(other.get(i).boundsMiddle) && (!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0){
                    //Rebound against the enemy
                    setBulletDirectionsAwayFrom((int)(other.get(i).midpointX()), (int)(other.get(i).midpointY()), x);
                    //Damage the enemy
                    damageChar(i, ((int)(damage*2)), other);
                }
            }
            //Stop moving if it hits a wall
            if(bullet.get(x).midpointX > widthLimit+10 || bullet.get(x).midpointX < 0 || bullet.get(x).midpointY > heightLimit || bullet.get(x).midpointY < 25){
                bullet.remove(x);
            }
        }
    }
    //ABILITY 2!
    //Activate the wind ability
    void ability2(){
        //If the cooldown is back
        if(Timer.time - abilityTimer2 > abilityCooldown2  && !attacking() && !performingAbility1() && !performingAbility2() && hp > 0){
            startAbilityTimer2();
            c.setImage(ability2Left);
        }
    }
    //If the wind is activated, knock other enemies
    void wind(ArrayList<Character> other, int widthLimit, int heightLimit){
        wind.setX(midpointX() - wind.getWidth()/2);
        wind.setY(boundsMiddle.y + boundsMiddle.height - wind.getHeight());
        if(Timer.time - abilityTimer2 < windDuration && Timer.time - abilityTimer2 > ability2AnimationTimer){
            for(int i = 0;i<other.size();i++){
                if(wind.hasCollidedWith(other.get(i).boundsMiddle) && (!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false)){
                    //Get midpoints of wind and character
                    int midX = other.get(i).c.getMidpointX();
                    int midY = other.get(i).c.getMidpointY();
                    int windX = wind.getMidpointX();
                    int windY = wind.getMidpointY();
                    //Move the character depending on the positions of the 2 sprites
                    if(windX < midX){
                        other.get(i).c.setX(c.getMidpointX() - 150 - other.get(i).boundsMiddle.width);
                    }else if(windX > midX){
                        other.get(i).c.setX(c.getMidpointX() + 150);
                    }
                    if(windY < midY){
                        other.get(i).c.setY(c.getMidpointY() - 150 - other.get(i).boundsMiddle.height);
                    }else if(windY > midY){
                        other.get(i).c.setY(c.getMidpointY() + 150);
                    }
                    other.get(i).resetBounds();
                    //Respect bounds
                    other.get(i).respectBounds(widthLimit, heightLimit);
                    //Damage the character
                    damageChar(i, (int)(damage*1.5f), other);
                }
            }
        }
    }
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if((hp>0)){
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
                    if(Timer.time - hittingTimer > hittingAnimation && attackNo!=0){
                        attack(other);
                        touchingTimer = Timer.time;
                    }
                    //Abilities
                    r = (int)(1+Math.random()*toHitTime*1.5f);
                    if(r == 1){
                        ability1(other.get(getFurthestChar(other)).c.getMidpointX(), other.get(getFurthestChar(other)).c.getMidpointY(), widthLimit, heightLimit);
                    }else if(r == 2){
                        ability2();
                    }
                }
                moveSpecial(other, widthLimit, heightLimit);
                super.move(widthLimit, heightLimit);
            }else{
                checkForAbility1();
                checkForAbility2();
            }
            if(Timer.time - hittingTimer > hittingAnimation + 500){
                attackNo = 0;
            }
            //Stop ability animations
            stopAnim(widthLimit, heightLimit);
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downStill);
            }
        }
        //These keep going even if he dies and is an ally
        wind(other, widthLimit, heightLimit);
        moveBullet(widthLimit, heightLimit, other);
    }
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i<bullet.size(); i++){
            bullet.get(i).paint(g);
        }
        if(Timer.time - abilityTimer2 < windDuration && Timer.time - abilityTimer2 > ability2AnimationTimer){
            wind.paint(g);
        }
    }
}