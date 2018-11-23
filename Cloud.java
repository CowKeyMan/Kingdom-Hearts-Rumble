//Imported to use arraylists
import java.util.ArrayList;
//Imported for using images
import java.awt.*;
public class Cloud extends Character{
    int attackNo = 0;
    Image rightAttack2, leftAttack2, rightAttack3, leftAttack3;
    long abilityDuration1, abilityDuration2;
    boolean hit2;
    
    //constructor
    public Cloud(//Movement images
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
                 //Specific attacking images
                 Image prightAttack2, Image pleftAttack2, Image prightAttack3, Image pleftAttack3,
                 //Timers for ability Durations
                 long pabilityDuration1, long pabilityDuration2
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
                       name = "Cloud";
                       //Specific attacking images
                       rightAttack2 = prightAttack2; leftAttack2 = pleftAttack2; rightAttack3 = prightAttack3; leftAttack3 = pleftAttack3;
                       //Timers for ability Durations
                       abilityDuration1 = pabilityDuration1; abilityDuration2 = pabilityDuration2;
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
    //Since Cloud has 3 different and attacks, this method is made specifically for it
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
    //Increased speed
    void ability1(){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && hp>0){
            setSpeed((int)(speed*1.5f));
            startAbilityTimer1();
        }
    }
    //Increased damage
    void ability2(){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && hp>0){
            damage=(int)(damage*2f);
            startAbilityTimer2();
        }
    }
    
    //Method to check if the charactersis moving in a special way and move if he
    void moveSpecial(ArrayList<Character> other, int widthLimit, int heightLimit){
        checkForAttack(other);
        if(!attacking() && speedUpAfterAttack == true){
            if(Timer.time - abilityTimer1 > abilityDuration1){
                setSpeed(getBaseSpeed());
            }else{
                setSpeed((int)(getBaseSpeed() * 1.5f));
            }
            speedUpAfterAttack = false;
        }
        if(getClosestChar(other) != -1){
            
            //Move towars the closest character
            if(movingTowardsChar == true){
                moveTowardsChar(other.get(getClosestChar(other)));
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
    
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if((hp>0 && !isEnemy) || isEnemy){
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
                    ability1();
                }else if(r == 2){
                    ability2();
                }
            }
            moveSpecial(other, widthLimit, heightLimit);
            //IF the duration for the abilities passes, reset to default values
            if(Timer.time - abilityTimer1 > abilityDuration1){
                if(speedUpAfterAttack == false){
                    setSpeed(getBaseSpeed());
                }else{
                    setSpeed((int)(getBaseSpeed() * 0.7f));
                }
            }
            if(Timer.time - abilityTimer2 > abilityDuration2){
                damage = baseDamage;
            }
            super.move(widthLimit, heightLimit);
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
    public void paint(Graphics g){
        //Speed Image
        if(Timer.time - abilityTimer1 < abilityDuration1){
            g.drawImage(ability1Left, midpointX() - ability1Left.getWidth(null)/2, (int)(boundsMiddle.getY() + boundsMiddle.getHeight()) - ability1Left.getHeight(null), null);
        }
        //Damage image
        if(Timer.time - abilityTimer2 < abilityDuration2){
            g.drawImage(ability2Left, midpointX() - ability2Left.getWidth(null)/2, (int)(boundsMiddle.getY() + boundsMiddle.getHeight()) - ability2Left.getHeight(null), null);
        }
        super.paint(g);
    }
}