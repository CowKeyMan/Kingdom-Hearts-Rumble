import java.util.ArrayList;
import java.awt.*;
public class Leon extends Character{
    //The character number that the bullet must follow
    int charNo;
    //The duration that the bullet follows the other character and the timer for the duration the dot has been on the character
    long bulletDuration;
    //Duration of dot
    int dotAmount;
    //An arra list to list all the current characters being damaged over time and the amount of times left to dot
    ArrayList<Integer> dotChar = new ArrayList<Integer>();
    ArrayList<Integer> dotCharLeft = new ArrayList<Integer>();
    long dotTimer;
    //Animation for the directed shot
    Image dirShot;
    int dx, dy;
    //Animation for the enemy taking dot
    Image dotAnim;
    
    //Constructor
    public Leon(//Movement images
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
                 int pbulletRadius, int pmybulletSpeed,
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
                 //The duration that the bullet follows the other character
                 long pbulletDuration,
                 //Duration of dot
                 int pdotAmount,
                 //Animation for the directed shot
                 Image pdirShot,
                 //Animation for the enemy taking dot
                 Image pdotAnim
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
                             pbulletRadius, pmybulletSpeed,
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
                       //The duration that the bullet follows the other character
                       bulletDuration = pbulletDuration;
                       //Duration of dot
                       dotAmount = pdotAmount;
                       //Animation for the directed shot
                       dirShot = pdirShot;
                       //Animation for the enemy taking dot
                       dotAnim = pdotAnim;
                       name = "Leon";
    }
    
    void printDot(){
        for(int i = 0; i < dotChar.size(); i++){
            System.out.println(dotChar.get(i));
        }
        System.out.println("----------");
        
        for(int i = 0; i < dotCharLeft.size(); i++){
            System.out.println(dotCharLeft.get(i));
        } 
    }
    
    //Sure hit shot on enemy character
    void ability1(ArrayList<Character> other, int x, int y){
        if(Timer.time - abilityTimer1 > abilityCooldown1 && hp > 0 && !attacking()){
            dx = x;
            dy = y;
            //Restart timer and set the image
            startAbilityTimer1();
            if(x < c.getMidpointX()){
                c.setImage(ability1Left);
            }else{
                c.setImage(ability1Right);
            }
        }
    }
    void checkForAbility1(ArrayList<Character> other){
        if(performingAbility1() && Timer.time - abilityTimer1 > ability1AnimationTimer/2 && canPerformAbility1 == true){
            for(int i = 0; i<other.size();i++){
                if(!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false){
                    //If he hits the other character, damage him
                    if(other.get(i).c.hasCollidedWith(dx,dy)){
                        damageChar(i, damage*3, other);
                    }
                }
            }
            canPerformAbility1 = false;
        }
    }
    //Overload ability 2 to use it as an AI too(input character)
    //Shoot a bullet which follows an enemy and if it hits it applies damage over time
    void ability2(ArrayList<Character> other, int x, int y){
        if(Timer.time - abilityTimer2 > abilityCooldown2 && !performingAbility1() && bullet.isEmpty() && dotChar.isEmpty() && hp > 0 && !attacking()){
            
            boolean cont = true;
            
            for(int i = 0; i<other.size();i++){
                if(other.get(i).c.hasCollidedWith(x,y) && (!isEnemy && other.get(i).isEnemy || isEnemy && other.get(i).isEnemy == false) && other.get(i).hp != 0 && cont == true){
                    cont = false;
                    
                    //Restart timer and set the image
                    startAbilityTimer2();
                    if(x < c.getMidpointX()){
                        c.setImage(ability2Left);
                    }else{
                        c.setImage(ability2Right);
                    }
                    charNo = i;
                }
            }
        }
    }
    void checkForAbility2(ArrayList<Character> other){
        if(performingAbility2() && Timer.time - abilityTimer2 > ability2AnimationTimer/2 && canPerformAbility2 == true){
            shootBullet(other.get(charNo).midpointX(), other.get(charNo).midpointY());
            canPerformAbility2 = false;
        }
    }
    //Make the bullet follow the other character for an amount of time
    void moveBullet(ArrayList<Character> other, int widthLimit, int heightLimit){
        for(int i = 0; i<bullet.size();i++){
            if(!bullet.isEmpty()){
                int j = charNo;
                if(j != -1){
                    if(Timer.time - abilityTimer2 < bulletDuration){
                        moveBulletTowardsChar(other.get(j));
                    }
                    if(Timer.time - abilityTimer2 < bulletDuration){
                        moveBulletTowardsChar(other.get(j));
                    }
                    //Move the bullet
                    bullet.get(i).moveRight(bullet.get(i).bulletSpeedX);
                    bullet.get(i).moveDown(bullet.get(i).bulletSpeedY);
                    for(int x = 0;x < other.size();x++){
                        if(bullet.get(i).hasCollidedWith(other.get(x).boundsMiddle) && (isEnemy && other.get(x).isEnemy == false || !isEnemy && other.get(x).isEnemy == true) && other.get(x).hp > 0){
                            //Set the damage over time to damage this character
                            if(!dotChar.contains(x)){
                                dotChar.add(x);
                                dotCharLeft.add(dotAmount);
                            }
                        }
                    }
                    if(Timer.time - abilityTimer2 > bulletDuration || bullet.get(i).hasCollidedWith(other.get(j).boundsMiddle)){
                        bullet.clear();
                    }
                }
            }
        }
    }
    //The method to make the character move and what is to be controlled automatically
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){
        if((hp>0 && !isEnemy) || isEnemy){
            if(!performingAbility1() && !performingAbility2()){
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
                         ability1(other, other.get(getEnemyWithMostHP(other)).midpointX(), other.get(getEnemyWithMostHP(other)).midpointY());
                    }else if(r == 2){
                        if(isEnemy && other.get(getProtagonist(other)).hp > 0){
                            int r = (int)(Math.random()*100);
                            if(r > 50){
                                ability2(other, other.get(getProtagonist(other)).midpointX(), other.get(getProtagonist(other)).midpointY());
                            }else{
                                ability2(other, other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY());
                            }
                        }else{
                            ability2(other, other.get(getFurthestChar(other)).midpointX(), other.get(getFurthestChar(other)).midpointY());
                        }
                    }
                }
                moveSpecial(other, widthLimit, heightLimit);
                
                super.move(widthLimit, heightLimit);
                stopAnim(widthLimit, heightLimit);
            }else{
                checkForAbility1(other);
                checkForAbility2(other);
            }
        }else{
            stop(widthLimit, heightLimit);
            setImage(koImage);
            if(Timer.time - revivalTimer > revivalTime){
                hp = maxHP/2;
                setImage(downStill);
            }
        }
        this.moveBullet(other, widthLimit, heightLimit);
        if(Timer.time - dotTimer > 1000){
            dotTimer = Timer.time;
            for(int i = 0; i<dotChar.size();i++){
                damageChar(dotChar.get(i), (int)(damage * 0.25), other);
                dotCharLeft.set(i, dotCharLeft.get(i) - 1);
                if(dotCharLeft.get(i) <= 0){
                    dotCharLeft.remove(i);
                    dotChar.remove(i);
                    i--;
                }
            }
        }
    }
    //3. animation for sure hit
    public void paint(Graphics g, ArrayList<Character> other){
        super.paint(g);
        if(Timer.time - abilityTimer1 - ability1AnimationTimer/2 < ability1AnimationTimer && Timer.time - abilityTimer1 - ability1AnimationTimer/2 > 0){
            g.drawImage(dirShot, dx, dy, null);
        }
        
        paintBullets(g, other);
    }
    public void paintBullets(Graphics g, ArrayList<Character> other){
        for(int i = 0; i<dotChar.size();i++){
            g.drawImage(dotAnim,other.get(dotChar.get(i)).midpointX() - dotAnim.getWidth(null)/2, other.get(dotChar.get(i)).midpointY() - dotAnim.getHeight(null), null);
        }
        for(int i = 0; i<bullet.size(); i++){
            bullet.get(i).paint(g);
        }
    }
}