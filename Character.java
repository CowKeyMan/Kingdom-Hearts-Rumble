//Imported to use images
import java.awt.*;
//Imported to use ArrayLists
import java.util.ArrayList;
//Imported in order to get audio in our algorythm
import java.io.*;
import sun.audio.*;
public class Character{
    //Name of the character
    String name;
    //The sprite
    Sprite c;
    //The character stats
    int maxHP, hp, level, baseDamage, damage, speedX, speedY, speed;
    private int baseSpeed;
    //Used for movingdiagonally
    int mmove;
    //Boolean to speed up after the attack has stopped as the character slows down when attacking
    boolean speedUpAfterAttack;
    //Boolean to check if the attack may get out
    boolean canAttack = false;
    //Images used for character movements
    Image upMove, downMove, rightMove, leftMove;
    Image upLeftMove, upRightMove, downLeftMove, downRightMove;
    //Images used for character attacks
    Image upAttack, downAttack, leftAttack, rightAttack;
    Image upLeftAttack, upRightAttack, downLeftAttack, downRightAttack;
    //Stopping Images
    Image upStill, downStill, rightStill, leftStill;
    Image upLeftStill, upRightStill, downLeftStill, downRightStill;
    //Ability Images
    Image ability1Left, ability1Right, ability2Left, ability2Right;
    //Image when knocked out as an ally
    Image koImage;
    //A full 3D image used to show the character
    Image baseImage;
    //Rectangles used for characters and weapon ranges
    Rectangle boundsUpLeft = new Rectangle(), boundsUp = new Rectangle(), boundsUpRight = new Rectangle();
    Rectangle boundsLeft = new Rectangle(), boundsMiddle = new Rectangle(), boundsRight = new Rectangle();
    Rectangle boundsDownLeft = new Rectangle(),boundsDown = new Rectangle(), boundsDownRight = new Rectangle();
    boolean isEnemy, isProtagonist = false;
    //Variables to do with the bullet
    ArrayList<Bullet> bullet = new ArrayList<Bullet>();
    int bulletRadius;
    int myBulletSpeed;
    Image bulletImage;
    //The time to hit the character
    long maxToHitTime, toHitTime;
    //Timers to display animations
    long hittingTimer, hittingAnimation, ability1AnimationTimer, ability2AnimationTimer;
    //The randomzer for the movement and its maximum
    static int ranMax = 100;
    int ranNo;
    //Integer which determines which enemies need to have their health depleted and by hom much
    static ArrayList<Integer> lstHit = new ArrayList<Integer>();
    static ArrayList<Integer> lstAmount = new ArrayList<Integer>();
    //Ability cooldown timers
    long abilityTimer1, abilityTimer2;
    long abilityCooldown1, abilityCooldown2;
    //The direction the character is facing out of 8
    int direction = 4;
    //Timer and random number to be used for moving
    int r;
    long moveTimer;
    boolean movingAroundChar = false, movingTowardsChar = false, movingAwayFromChar = false;
    //Timer for how long the character has been touching the enemy
    //and if the timer exceeds a certain amount he will be attacked
    long touchingTimer;
    //Timer for damaging animation to appear
    long damagedTimer;
    static Image damagedImage;
    //The place of the rectangle bounds related to the image from top left
    int boundsX, boundsY;
    //Timers for stoppind at diagonal directions
    long downTimer, upTimer, leftTimer, rightTimer;
    //Boolean to stop the character movements after he attacks
    boolean stopAfterAttacking = false;
    //The booleans to indicate the program to stop the ability animations
    boolean ability1Stop = false, ability2Stop = false;
    //The duration that an ally remains dead for until he automatically revives
    static long revivalTime;
    long revivalTimer;
    //The number of allies ocunter
    static int noOfAllies = 0, noOfEnemies = 0;
    //Images for the UI and their coordinates
    Image healthyImage, hurtImage;
    int healthyImagex, healthyImagey;
    int hurtImagex, hurtImagey;
    //Images for the ability icons
    Image abilityImage1, abilityImage2;
    
    //Sound items
    static ArrayList<String> hitSounds = new ArrayList<String>();
    static ArrayList<String> chooseSound = new ArrayList<String>();
    static ArrayList<String> deathSound = new ArrayList<String>();
    static ArrayList<String> specialSound = new ArrayList<String>();
    
    //Booleans for staility in program
    boolean isAddingDamage = false;
    boolean isMovingBullet = false;
    
    boolean canPerformAbility1 = false, canPerformAbility2 = false;
    
    Sound s = new Sound();
    
    //Default Constructor
    //public Character(){}
    public Character(//Movement images
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
                     //bullet Image
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
                     Image pabilityImage1, Image pabilityImage2){
        //Movement images
        upMove = pupMove; downMove = pdownMove; rightMove = prightMove; leftMove = pleftMove;
        upLeftMove = pupLeftMove; upRightMove = pupRightMove; downLeftMove = pdownLeftMove; downRightMove = pdownRightMove;
        //Attacking images
        upAttack = pupAttack; downAttack = pdownAttack; leftAttack = pleftAttack; rightAttack = prightAttack;
        upLeftAttack = pupLeftAttack; upRightAttack = pupRightAttack; downLeftAttack = pdownLeftAttack; downRightAttack = pdownRightAttack;
        //Still images
        upStill = pupStill; downStill = pdownStill; rightStill = prightStill; leftStill = pleftStill;
        upLeftStill = pupLeftStill; upRightStill = pupRightStill; downLeftStill = pdownLeftStill; downRightStill = pdownRightStill;
        //Ability Images
        ability1Left = pability1Left; ability1Right = pability1Right; ability2Left = pability2Left; ability2Right = pability2Right;
        //Knocked out image
        koImage = pkoImage;
        //A full 3D image used to show the character
        baseImage = pbaseImage;
        //Length and height of rectangle bounds
        boundsUpLeft.width = boundsUpRight.width = boundsLeft.width = boundsRight.width = boundsDownLeft.width = boundsDownRight.width = sideWidth;
        boundsUp.width = boundsDown.width = boundsMiddle.width = midWidth;
        boundsUpLeft.height = boundsUpRight.height = boundsUp.height = boundsDown.height = boundsDownLeft.height = boundsDownRight.height = sideHeight;
        boundsLeft.height = boundsRight.height = boundsMiddle.height = midHeight;
        //Enemy or ally
        isEnemy = pisEnemy;
        //Protagonist or not
        isProtagonist = pisProtagonist;
        //bullet radius
        bulletRadius = pbulletRadius; myBulletSpeed = pmyBulletSpeed;
        //bullet Image
        bulletImage = pbulletImage;
        //Timers to display animations
        hittingAnimation = phittingAnimation; ability1AnimationTimer = pability1AnimationTimer; ability2AnimationTimer = pability2AnimationTimer;
        //Ability cooldown timers
        abilityCooldown1 = pabilityCooldown1; abilityCooldown2 = pabilityCooldown2;
        //Rectangle positions relative to image
        boundsX = sideWidth; boundsY = sideHeight;
        
        //Character Stats
        maxHP = hp = pmaxHP;
        setBaseSpeed(pbaseSpeed);
        level = plevel;
        baseDamage = damage = pbaseDamage;
        
        //Set the sprite
        c = new Sprite(downStill, pcx, pcy, 2*sideWidth+midWidth, 2*sideHeight+midHeight);
        
        //Positions
        c.setX(pcx);
        c.setY(pcy);
        
        //The time to hit the character
        maxToHitTime = toHitTime = pmaxToHitTime;
        
        //Increase the number of allies counter
        if(!isEnemy){
            noOfAllies++;
        }else{
            noOfEnemies++;
        }
        
        //Images for the UI and their coordinates
        healthyImage = phealthyImage; hurtImage = phurtImage;
        healthyImagex = phealthyImagex; healthyImagey = phealthyImagey;
        hurtImagex = phurtImagex; hurtImagey = phurtImagey;
        
        //Images for the ability icons
        abilityImage1 = pabilityImage1; abilityImage2 = pabilityImage2;
    }
    
    void resetBounds(){
        boundsMiddle.setLocation(c.getX()+boundsX,c.getY()+boundsY);
        boundsUpLeft.setLocation(boundsMiddle.x - boundsUpLeft.width, boundsMiddle.y - boundsUpLeft.height);
        boundsUp.setLocation(boundsMiddle.x, boundsUpLeft.y);
        boundsUpRight.setLocation(boundsMiddle.x + boundsMiddle.width, boundsUp.y);
        boundsLeft.setLocation(boundsMiddle.x - boundsLeft.width, boundsMiddle.y);
        boundsRight.setLocation(boundsMiddle.x + boundsMiddle.width, boundsMiddle.y);
        boundsDownLeft.setLocation(boundsMiddle.x - boundsDownLeft.width,boundsMiddle.y + boundsMiddle.height);
        boundsDown.setLocation(boundsMiddle.x, boundsMiddle.y + boundsMiddle.height);
        boundsDownRight.setLocation(boundsMiddle.x + boundsMiddle.width, boundsMiddle.y + boundsMiddle.height);
    }
    
    //Changing the normal speed
    void setSpeed(int newSpeed){
        speed = newSpeed;
        //Used for moving diagonally
        double move = Math.round(Math.sqrt((Math.pow(speed,2)/2)));
        if((int)move+0.5>move){
            mmove = ((int)move);
        }else{
            mmove = ((int)move+1);
        }
    }
    int getSpeed(){
        return speed;
    }
    
    //Changing the speed
    void setBaseSpeed(int newSpeed){
        baseSpeed = speed = newSpeed;
        //Used for moving diagonally
        double move = Math.round(Math.sqrt((Math.pow(speed,2)/2)));
        if((int)move+0.5>move){
            mmove = ((int)move);
        }else{
            mmove = ((int)move+1);
        }
    }
    //Getting the value of the speed
    int getBaseSpeed(){
        return baseSpeed;
    }
    
    //Set the health
    void setHP(int health){
        maxHP = hp = health;
    }
    
    void moveRandomly(ArrayList<Character> other, int widthLimit, int heightLimit){
        if(Timer.time - moveTimer>2500){
            movingAroundChar = false;
            movingTowardsChar = false;
            movingAwayFromChar = false;
            moveTimer = Timer.time;
            //The movement of the character correspeonds to how 'angry' he is from being hit and not being able to hit back
            //and how much hit points he has
            r = (int)(1+Math.random()*(100 * ((toHitTime * 1.00f)/(maxToHitTime * 1.00f) + (hp * 1.00f)/(maxHP * 1.00f))/2));
            //Randomly
            if(r>=1 && r<=20){
                movingTowardsChar = true;
            }else if(r>=21 && r<=40){
                movingAwayFromChar = true;
            }else if(r>=50 && r<=60){
                movingAroundChar = true;
            }else if(r>=61 && r<=84){
                stop(widthLimit, heightLimit);
            }else if(r>=85 && r<=86){
                moveRight(other);
            }else if(r>=87 && r<=88){
                moveUpLeft();
            }else if(r>=89 && r<=90){
                moveUpRight(widthLimit);
            }else if(r>=91 && r<=92){
                moveDownLeft(heightLimit);
            }else if(r>=93 && r<=94){
                moveDownRight(widthLimit, heightLimit);
            }else if(r>=95 && r<=96){
                moveLeft(other);
            }else if(r>=97 && r<=98){
                moveDown(other);
            }else{
                moveUp(other);
            }
        }
    }
    
    //Setting the speeds to go towards the character
    void moveTowardsChar(Character other){
        //Get the midpoint of both sprites
        int otherX = other.midpointX();
        int otherY = other.midpointY();
        int myX = boundsMiddle.x+boundsMiddle.width/2;
        int myY = boundsMiddle.y+boundsMiddle.height/2;
        //Find the hypotenuse
        double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
        //divide the hypotenuse by the speed
        double dSpeed = hyp/speed;
        //divide the horizontal and vertical distances by the dSpeed
        double xx = Math.round(((Math.abs(otherX-myX))/dSpeed));
        double yy = Math.round((Math.abs(otherY-myY)/dSpeed));
        //round to the nearest integer
        if((int)xx+0.5>xx){
            speedX = (int)xx;
        }else{
            speedX = (int)xx++;
        }
        if((int)yy+0.5>yy){
            speedY = (int)yy;
        }else{
            speedY = (int)yy++;
        }
        //Set the speeds and images to face the character
        if(otherX<=myX && otherY<=myY){
            speedX = -(speedX);
            speedY = -(speedY);
        }else if(otherX<=myX && otherY>=myY){
            speedX = -(speedX);
        }else if(otherX>=myX && otherY<=myY){
            speedY = -(speedY);
        }
        //move down right
    }
    //Move towards a certain point
    void moveTowardsPoint(int px, int py){
        //Get the midpoint of the sprite and keep name the same as other method for readability purposes
        int otherX = px;
        int otherY = py;
        int myX = midpointX();
        int myY = midpointY();
        //Find the hypotenuse
        double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
        //divide the hypotenuse by the speed
        double dSpeed = hyp/speed;
        //divide the horizontal and vertical distances by the dSpeed
        double xx = Math.round(((Math.abs(otherX-myX))/dSpeed));
        double yy = Math.round((Math.abs(otherY-myY)/dSpeed));
        //round to the nearest integer
        if((int)xx+0.5>xx){
            speedX = (int)xx;
        }else{
            speedX = (int)xx++;
        }
        if((int)yy+0.5>yy){
            speedY = (int)yy;
        }else{
            speedY = (int)yy++;
        }
        //Set the speeds and images to face the character
        if(otherX<=myX){
            speedX = -(speedX);
        }
        if(otherY<=myY){
            speedY = -(speedY);
        }
        //move down right
    }
    //set the speeds to move directl away from the character
    void moveAwayFromChar(Character other){
        //Get the midpoint of both sprites
        int otherX = other.boundsMiddle.x + other.boundsMiddle.width/2;
        int otherY = other.boundsMiddle.y + other.boundsMiddle.height/2;
        int myX = boundsMiddle.x+boundsMiddle.width/2;
        int myY = boundsMiddle.y+boundsMiddle.height/2;
        //Find the hypotenuse
        double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
        //divide the hypotenuse by the speed
        double dSpeed = hyp/speed;
        //divide the horizontal and vertical distances by the dSpeed
        double xx = Math.round(((Math.abs(otherX-myX))/dSpeed));
        double yy = Math.round((Math.abs(otherY-myY)/dSpeed));
        //round to the nearest integer
        if((int)xx+0.5>xx){
            speedX = (int)xx;
        }else{
            speedX = (int)xx++;
        }
        if((int)yy+0.5>yy){
            speedY = (int)yy;
        }else{
            speedY = (int)yy++;
        }
        //Set the speeds and images to face away from the character
        if(otherX<=myX && otherY>=myY){
            speedY = -(speedY);
        }else if(otherX>=myX && otherY<=myY){
            speedX = -(speedX);
        }else if(otherX>=myX && otherY>=myY){
            speedX = -(speedX);
            speedY = -(speedY);
        }
        //move down right
    }
    
    void dodgeBulletChar(Character other){
        //Double the speed
        speed*=2;
        //Get the midpoint of both sprites
        int otherX = other.midpointX();
        int otherY = other.midpointY();
        int myX = boundsMiddle.x+boundsMiddle.width/2;
        int myY = boundsMiddle.y+boundsMiddle.height/2;
        //Find the hypotenuse
        double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
        //divide the hypotenuse by the speed
        double dSpeed = hyp/speed;
        //divide the horizontal and vertical distances by the dSpeed
        double xx = Math.round(((Math.abs(otherX-myX))/dSpeed));
        double yy = Math.round((Math.abs(otherY-myY)/dSpeed));
        //round to the nearest integer
        if((int)xx+0.5>xx){
            speedX = (int)xx;
        }else{
            speedX = (int)xx++;
        }
        if((int)yy+0.5>yy){
            speedY = (int)yy;
        }else{
            speedY = (int)yy++;
        }
        //Set the speeds and images to face the character
        if(otherX<=myX && otherY<=myY){
            speedY = -(speedY);
        }else if(otherX>=myX && otherY<=myY){
            speedX = -(speedX);
            speedY = -(speedY);
        }else if(otherX>=myX && otherY>=myY){
            speedX = -(speedX);
        }
        //move down right
    }
    //Get the closest enemy character from current position
    int getClosestChar(ArrayList<Character> other){
        int selectedChar = -1;
        double myHyp = Double.POSITIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if((isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true) && other.get(i).hp >0){
                //Get the midpoint of both sprites
                int otherX = other.get(i).midpointX();
                int otherY = other.get(i).midpointY();
                int myX = boundsMiddle.x+boundsMiddle.width/2;
                int myY = boundsMiddle.y+boundsMiddle.height/2;
                //Find the hypotenuse
                double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
                if(hyp < myHyp){
                    myHyp = hyp;
                    selectedChar = i;
                }
            }
        }
        return selectedChar;
    }
    //Get the furthest enemy character from current position
    int getFurthestChar(ArrayList<Character> other){
        int selectedChar = -1;
        double myHyp = Double.NEGATIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if((isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true) && other.get(i).hp >0){
                //Get the midpoint of both sprites
                int otherX = other.get(i).midpointX();
                int otherY = other.get(i).midpointY();
                int myX = boundsMiddle.x+boundsMiddle.width/2;
                int myY = boundsMiddle.y+boundsMiddle.height/2;
                //Find the hypotenuse
                double hyp = Math.sqrt(Math.pow(otherX-myX,2)+Math.pow(otherY-myY,2));
                if(hyp > myHyp){
                    myHyp = hyp;
                    selectedChar = i;
                }
            }
        }
        return selectedChar;
    }
    //Return the array number of the enemy with the least hp percentage
    int getEnemyWithLeastHP(ArrayList<Character> other){
        int selectedChar = -1;
        double myHP = Double.NEGATIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if(isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true){
                if(other.get(i).hp == 0){
                    return i;
                }else{
                    double charHP = (double)other.get(i).maxHP/(double)other.get(i).hp;
                    if(charHP > myHP){
                        myHP = charHP;
                        selectedChar = i;
                    }
                }
            }
        }
        return selectedChar;
    }
    //Return the array number of the enemy with the least hp percentage
    int getAllyWithLeastHP(ArrayList<Character> other){
        int selectedChar = -1;
        double myHP = Double.NEGATIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if(!(isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true)){
                if(other.get(i).hp == 0){
                    return i;
                }else{
                    double charHP = (double)other.get(i).maxHP/(double)other.get(i).hp;
                    if(charHP > myHP){
                        myHP = charHP;
                        selectedChar = i;
                    }
                }
            }
        }
        return selectedChar;
    }
    //Return the array number of the enemy with the most hp percentage
    int getEnemyWithMostHP(ArrayList<Character> other){
        int selectedChar = -1;
        double myHP = Double.POSITIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if(isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true){
                if(other.get(i).hp == other.get(i).maxHP){
                    return i;
                }else{
                    double charHP = (double)other.get(i).maxHP/(double)other.get(i).hp;
                    if(charHP < myHP){
                        myHP = charHP;
                        selectedChar = i;
                    }
                }
            }
        }
        return selectedChar;
    }
    //Return the array number of the enemy with the most hp percentage
    int getAllyWithMostHP(ArrayList<Character> other){
        int selectedChar = -1;
        double myHP = Double.POSITIVE_INFINITY;
        for(int i = 0; i<other.size();i++){
            if(!(isEnemy && other.get(i).isEnemy == false || !isEnemy && other.get(i).isEnemy == true)){
                if(other.get(i).hp == other.get(i).maxHP){
                    return i;
                }else{
                    double charHP = (double)other.get(i).maxHP/(double)other.get(i).hp;
                    if(charHP < myHP){
                        myHP = charHP;
                        selectedChar = i;
                    }
                }
            }
        }
        return selectedChar;
    }
    //get the protagonist number
    int getProtagonist(ArrayList<Character> other){
        int selectedChar = -1;
        for(int i = 0; i<other.size();i++){
            if(other.get(i).isProtagonist){
                selectedChar = i;
            }
        }
        return selectedChar;
    }
    //Move horizontal and vertical
    //In case there are monsters
    void moveUp(ArrayList<Character> other){
        if(!attacking()){
            speedX = 0;
            speedY = -speed;
        }
    }
    //In case there are no monsters
    void moveUp(){
        if(!attacking()){
            speedX = 0;
            speedY = -speed;
        }
    }
    //In case there are monsters
    void moveDown(ArrayList<Character> other){
        if(!attacking()){
            speedX = 0;
            speedY = speed;
        }
    }
    //In case there are no monsters
    void moveDown(){
        if(!attacking()){
            speedX = 0;
            speedY = speed;
        }
    }
    //In case there are monsters
    void moveLeft(ArrayList<Character> other){
        if(!attacking()){
            speedX = -speed;
            speedY = 0;
        }
    }
    //In case there are no monsters
    void moveLeft(){
        if(!attacking()){
            speedX = -speed;
            speedY = 0;
        }
    }
    //In case there are monsters
    void moveRight(ArrayList<Character> other){
        if(!attacking()){
            speedX = speed;
            speedY = 0;
        }
    }
    //In case there are no monsters
    void moveRight(){
        if(!attacking()){
            speedX = speed;
            speedY = 0;
        }
    }
    //MoveDiagonally
    void moveUpLeft(){
        if(!attacking()){
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
        if(!attacking()){
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
        if(!attacking()){
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
        if(!attacking()){
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
    //stop moving
    void stop(int widthLimit, int heightLimit){
        if((!attacking() && !performingAbility1() && !performingAbility2()) || (boundsMiddle.x+boundsMiddle.getWidth()+speed > widthLimit || boundsMiddle.y+boundsMiddle.getHeight()+speed > heightLimit || boundsMiddle.x-speedX < 3 || boundsMiddle.y-speedY < 25)){
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
    
    //Move in a circle around the character, even when he is moving, constantly takes values
    void moveAroundChar(Character other){
        //Get midpoints of sprites
        int x1 = (int)other.midpointX();
        int y1 = (int)(other.midpointY());
        int x2 = (int)(midpointX());
        int y2 = (int)(midpointY());
        
        //Get distance between points
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        
        //Get the speed squared divided by the distances squared and square root all
        double t = Math.sqrt(Math.pow(speed,2)/(Math.pow(w,2) + Math.pow(h,2)));
        
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
        if(moveH > speed){
            moveH = speed;
        }
        if(moveD > speed){
            moveD = speed;
        }
        //Depending on the direction the other is relative to the character
        //set the directions of speedX and speedY
        if(x1<=x2 && y1<=y2){
            speedX = -moveH;
            speedY = moveD;
        }else if(x1<=x2 && y1>=y2){
            speedX = moveH;
            speedY = moveD;
        }else if(x1>=x2 && y1<=y2){
            speedX = -moveH;
            speedY = -moveD;
        }else if(x1>=x2 && y1>=y2){
            speedX = moveH;
            speedY = -moveD;
        }
    }
    
    
    //Move the character IF it does not go out of the screen and change it's image
    void move(int widthLimit, int heightLimit){
        if(speedX > 0 && speedY > 0){
            direction = 3;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit && boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                setImage(downRightMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX > 0 && speedY < 0){
            direction = 1;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit && boundsMiddle.y-speedY > 25){
                setImage(upRightMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY < 0){
            direction = 7;
            if(boundsMiddle.x-speedX > 3 && boundsMiddle.y-speedY > 25){
                setImage(upLeftMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY > 0){
            direction = 5;
            if(boundsMiddle.x-speedX > 3 && boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                setImage(downLeftMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX == 0 && speedY > 0){
            direction = 4;
            if(boundsMiddle.y+boundsMiddle.getHeight()+speedY < heightLimit){
                setImage(downMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX == 0 && speedY < 0){
            direction = 0;
            if(boundsMiddle.y-speedY > 25){
                setImage(upMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX > 0 && speedY == 0){
            direction = 2;
            if(boundsMiddle.x+boundsMiddle.getWidth()+speedX < widthLimit){
                setImage(rightMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }else if(speedX < 0 && speedY == 0){
            direction = 6;
            if(boundsMiddle.x-speedX > 3){
                setImage(leftMove);
            }else{
                stop(widthLimit, heightLimit);
            }
        }
        //Speeds are optimized to be +ve, -ve and 0 for this reason
        c.moveRight(speedX);
        c.moveDown(speedY);
    }
    
    //Method to check if the charactersis moving in a special way and move if he
    void moveSpecial(ArrayList<Character> other, int widthLimit, int heightLimit){
        checkForAttack(other);
        if(!attacking() && speedUpAfterAttack == true){
            setSpeed(getBaseSpeed());
            speedUpAfterAttack = false;
        }
        if(getClosestChar(other) != -1){
            
            //Move towars the closest character
            if(movingTowardsChar == true ){
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
    
    //Shoot to 2 points
    void shootBullet(int x, int y){
        bullet.add(new Bullet(bulletImage, midpointX(), midpointY(), bulletRadius, myBulletSpeed, x, y));
    }
    void shootBullet(int x, int y, int radius){
        bullet.add(new Bullet(bulletImage, midpointX(), midpointY(), radius, myBulletSpeed, x, y));
    }
    //Set a specific bullet's directions towards a particular point
    void setBulletDirectionsToward(int x, int y, int bulletNo){
        //The length between the points is calculated
        double hyp = Math.sqrt(Math.pow(x-bullet.get(bulletNo).midpointX,2)+Math.pow(y-bullet.get(bulletNo).midpointY,2));
        //The hyp is divided by the bullet speed and the answer is stored in dSpeed
        double dSpeed = hyp/myBulletSpeed;
        //This is then used to divide the length between vertical and horizontal distances by it
        //to determine how many points the bullet must travel in each frame
        double xx = (x-bullet.get(bulletNo).midpointX)/dSpeed;
        double yy = (y-bullet.get(bulletNo).midpointY)/dSpeed;
        //The speed is then rounded to the nearest integer. This is unfortunate as the angles may not be perfect
        //and may miss if the target is very thin.
        if(xx<0){
            xx = -xx;
        }
        if(yy<0){
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
        if(y<=bullet.get(bullet.size()-1).midpointY){
            bullet.get(bulletNo).bulletSpeedY= -y2;
        }else{
            bullet.get(bulletNo).bulletSpeedY= y2;
        }
        if(x<=bullet.get(bullet.size()-1).midpointX){
            bullet.get(bulletNo).bulletSpeedX= -x2;
        }else{
            bullet.get(bulletNo).bulletSpeedX= x2;
        }
    }
    //Set a specific bullet's directions away from a particular point
    void setBulletDirectionsAwayFrom(int x, int y, int bulletNo){
        //The length between the points is calculated
        double hyp = Math.sqrt(Math.pow(x-bullet.get(bulletNo).midpointX,2)+Math.pow(y-bullet.get(bulletNo).midpointY,2));
        //The hyp is divided by the bullet speed and the answer is stored in dSpeed
        double dSpeed = hyp/bullet.get(bulletNo).bulletSpeed;
        //This is then used to divide the length between vertical and horizontal distances by it
        //to determine how many points the bullet must travel in each frame
        double xx = (x-bullet.get(bulletNo).midpointX)/dSpeed;
        double yy = (y-bullet.get(bulletNo).midpointY)/dSpeed;
        //The speed is then rounded to the nearest integer. This is unfortunate as the angles may not be perfect
        //and may miss if the target is very thin.
        if(xx<0){
            xx = -xx;
        }
        if(yy<0){
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
        if(y<=bullet.get(bullet.size()-1).midpointY){
            bullet.get(bulletNo).bulletSpeedY= y2;
        }else{
            bullet.get(bulletNo).bulletSpeedY= -y2;
        }
        if(x<=bullet.get(bullet.size()-1).midpointX){
            bullet.get(bulletNo).bulletSpeedX= x2;
        }else{
            bullet.get(bulletNo).bulletSpeedX= -x2;
        }
    }
    //Add a new bullet and shoot it towards a specific character
    void shootStraightTowardsChar(Character other){
        //get the character midpoints
        int otherX = other.midpointX();
        int otherY = other.midpointY();
        //Shoot the bullet at character midpoints
        shootBullet(otherX, otherY, bulletRadius);
    }
    
    //Setting the speeds to go towards the character
    void moveBulletTowardsChar(Character other2){
        for(int i = 0; i < bullet.size(); i++){
            //Get the midpoint of both sprites
            int otherX = other2.boundsMiddle.x + other2.boundsMiddle.width/2;
            int otherY = other2.boundsMiddle.y + other2.boundsMiddle.height/2;
            //Find the hypotenuse
            double hyp = Math.sqrt(Math.pow(otherX-bullet.get(i).midpointX,2)+Math.pow(otherY-bullet.get(i).midpointY,2));
            //divide the hypotenuse by the speed
            double dSpeed = hyp/myBulletSpeed;
            //divide the horizontal and vertical distances by the dSpeed
            double xx = Math.round(((Math.abs(otherX-bullet.get(i).midpointX))/dSpeed));
            double yy = Math.round((Math.abs(otherY-bullet.get(i).midpointY)/dSpeed));
            //round to the nearest integer
            if((int)xx+0.5>xx){
                bullet.get(i).bulletSpeedX = (int)xx;
            }else{
                 bullet.get(i).bulletSpeedX = (int)xx;
            }
            if((int)yy+0.5>yy){
                bullet.get(i).bulletSpeedY = (int)yy;
            }else{
                bullet.get(i).bulletSpeedY = (int)yy;
            }
            //Set the speeds and images to face the character
            if(otherX<=bullet.get(i).midpointX && otherY<=bullet.get(i).midpointY){
                bullet.get(i).bulletSpeedX = -bullet.get(i).bulletSpeedX;
                bullet.get(i).bulletSpeedY = -bullet.get(i).bulletSpeedY;
            }else if(otherX<=bullet.get(i).midpointX && otherY>=bullet.get(i).midpointY){
                bullet.get(i).bulletSpeedX = -bullet.get(i).bulletSpeedX;
            }else if(otherX>=bullet.get(i).midpointX && otherY<=bullet.get(i).midpointY){
                bullet.get(i).bulletSpeedY = -bullet.get(i).bulletSpeedY;
            }
            //move down right
        }   
    }
    
    //Move the bullet and stop when it comes into contact with a wall
    void moveBullet(int widthLimit, int heightLimit, ArrayList<Character> other){
        for(int i = 0; i<bullet.size();i++){
            bullet.get(i).moveRight(bullet.get(i).bulletSpeedX);
            bullet.get(i).moveDown(bullet.get(i).bulletSpeedY);
            for(int x = 0;x < other.size();x++){
                if(bullet.get(i).hasCollidedWith(other.get(x).boundsMiddle) && (!isEnemy && other.get(x).isEnemy || isEnemy && other.get(x).isEnemy == false) && other.get(x).hp > 0){
                    //Damage the enemy
                    damageChar(x, ((int)(damage*0.8)), other);
                }
            }
            //Remove the bullet if it hits something
            if(bullet.get(i).midpointX > widthLimit || bullet.get(i).midpointX < 0 || bullet.get(i).midpointY > heightLimit || bullet.get(i).midpointY < 25){
                bullet.remove(i);
                i--;
            }
        }
    }
    //Remove the bullet if it collides with an enemy as well as a wall
    void moveBullet2(int widthLimit, int heightLimit, ArrayList<Character> other){
        /*while(bullet.size() > bulletSpeedX.size()){
            bulletSpeed.add(myBulletSpeed);
            bulletSpeedX.add(10000);
            bulletSpeedY.add(100000);
        }
        while(bulletSpeed.size() > bullet.size()){
            bulletSpeed.remove(bulletSpeed.size()-1);
            bulletSpeedX.remove(bulletSpeedX.size()-1);
            bulletSpeedY.remove(bulletSpeedY.size()-1);
        }*/
        if(isMovingBullet == false){
            isMovingBullet = true;
            
            boolean cont = true;
            for(int i = 0; i<bullet.size() && cont == true; i++){
                //System.out.println(bullet.size() + "       " + bulletSpeed.size() + "       " + bulletSpeedX.size() + "       " + bulletSpeedY.size() + "       " + i);
                bullet.get(i).moveRight(bullet.get(i).bulletSpeedX);
                bullet.get(i).moveDown(bullet.get(i).bulletSpeedY);
                boolean remove = false;
                for(int x = 0;x < other.size() && remove == false;x++){
                    if(bullet.get(i).hasCollidedWith(other.get(x).boundsMiddle) && (!isEnemy && other.get(x).isEnemy || isEnemy && other.get(x).isEnemy == false) && other.get(x).hp > 0){
                        //Damage the enemy
                        damageChar(x, ((int)(damage*0.8)), other);
                        remove = true;
                    }
                }
                //Remove the bullet if it hits something
                if(bullet.get(i).midpointX > widthLimit || bullet.get(i).midpointX < 0 || bullet.get(i).midpointY > heightLimit || bullet.get(i).midpointY < 25 || remove == true){
                    bullet.remove(i);
                    remove = false;
                    cont = false;
                }
            }
            isMovingBullet = false;
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
    void attack(ArrayList<Character> other){
        if(hp > 0){
            //Set the hit to false
            //Only attack after the previous attack has finished
            if(!attacking() && !performingAbility1() && !performingAbility2()){
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
    
    //Method to damgage a character
    void damageChar(int characNo, int damageAmount, ArrayList<Character> other){
        if(isAddingDamage == false){
            isAddingDamage = true;
            if((other.get(characNo).hp > 0 && damageAmount > 0) || damageAmount < 0){
                //Add which character needs to be damaged
                lstHit.add(characNo);
                //List the amount of damage needed to be taken
                lstAmount.add(damageAmount);
                //Reset the to hit timer
                toHitTime = maxToHitTime;
                
                while(lstAmount.size() > lstHit.size()){
                    lstAmount.remove(lstAmount.size()-1);
                }
            }
            isAddingDamage = false;
        }
    }
    //Checkif character is attacking or not
    boolean attacking(){
        if(Timer.time - hittingTimer < hittingAnimation){
            return true;
        }else{
            return false;
        }
    }
    //A restriction is set for the changing of the image in certain cases
    void setImage(Image y){
        if(!attacking()){
            c.setImage(y);
        }
    }
    //Method to take damage. This is to reduce damage taken if there is armor for example
    void takeDamage(int dmg, int difficulty, boolean canPlaySound){
        //Start the animation timer for the other character
        if(dmg > 0){
            damagedTimer = Timer.time;
            s.playSoundWithTimeRestriction(hitSounds, canPlaySound);
        }
        hp -= dmg;
        //Reduce the to hit time
        toHitTime = (int)((1-0.05f*difficulty) * toHitTime);
    }
    //Restart ability timer 1
    void startAbilityTimer1(){
        abilityTimer1 = Timer.time;
        ability1Stop = true;
        canPerformAbility1 = true;
    }
    //Restart ability timer 2
    void startAbilityTimer2(){
        abilityTimer2 = Timer.time;
        ability2Stop = true;
        canPerformAbility2 = true;
    }
    //Checks if chaacter is in the middle of ability 1
    boolean performingAbility1(){
        if(Timer.time - abilityTimer1 < ability1AnimationTimer && ability1Stop == true){
            return true;
        }else{
            return false;
        }
    }
    //Checks if chaacter is in the middle of ability 1
    boolean performingAbility2(){
        if(Timer.time - abilityTimer2 < ability2AnimationTimer && ability2Stop == true){
            return true;
        }else{
            return false;
        }
    }
    //Get the midpoints of the character
    int midpointX(){
        return boundsMiddle.x + boundsMiddle.width/2;
    }
    int midpointY(){
        return boundsMiddle.y + boundsMiddle.height/2;
    }
    //Method to stop an animation after it finishes
    void stopAnim(int widthLimit, int heightLimit){
        if(ability1Stop == true && Timer.time - abilityTimer1 > ability1AnimationTimer){
            stop(widthLimit, heightLimit);
            ability1Stop = false;
        }
        if(ability2Stop == true && Timer.time - abilityTimer2 > ability2AnimationTimer){
            stop(widthLimit, heightLimit);
            ability2Stop = false;
        }
        
        if(!attacking() && stopAfterAttacking == true){
            stop(widthLimit, heightLimit);
            stopAfterAttacking = false;
        }
    }
    
    //Make sure that the character is not out of the screen, and if so, bring himback in
    void respectBounds(int widthLimit, int heightLimit){
        if(boundsMiddle.x < 3){
            c.setX(3 + boundsLeft.width);
        }else if(boundsMiddle.x + boundsMiddle.width > widthLimit){
            c.setX(widthLimit - boundsRight.width - boundsMiddle.width);
        }
        if(boundsMiddle.y < 25){
            c.setY(3 + boundsUp.height);
        }else if(boundsMiddle.y + boundsMiddle.height > heightLimit){
            c.setY(heightLimit - boundsDown.height - boundsMiddle.height);
        }
    }
    
    void respectBulletBounds(int widthLimit, int heightLimit){
        for(int i = 0; i < bullet.size(); i++){
            if(bullet.get(i).midpointX < 3){
                bullet.get(i).setX(3 + bullet.get(i).radius);
            }else if(bullet.get(i).midpointX > widthLimit){
                bullet.get(i).setX(widthLimit - bullet.get(i).radius);
            }
            if(bullet.get(i).midpointY < 25){
                bullet.get(i).setY(3 + bullet.get(i).radius);
            }else if(bullet.get(i).midpointY > heightLimit){
                bullet.get(i).setY(heightLimit - bullet.get(i).radius);
            }
        }
    }
    
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect((int)(boundsMiddle.getX() + boundsMiddle.getWidth()/2) - 26, (int)(boundsMiddle.getY() - 8), 51, 6);
        if(isProtagonist){
            g.setColor(Color.YELLOW);
        }else{
            if(isEnemy){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.GREEN);
            }
        }
        g.fillOval(midpointX() - 10, (int)(boundsDown.getY() - 3), 20, 7);
        c.paint(g);
        if(isProtagonist){
            g.setColor(Color.YELLOW);
        }else{
            if(isEnemy){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.GREEN);
            }
        }
        g.fillRect((int)(boundsMiddle.getX() + boundsMiddle.getWidth()/2) - 25, (int)(boundsMiddle.getY() - 7), (int)(50*((double)hp/(double)maxHP)), 5);
        g.setFont(new Font("Times New Roman", Font.BOLD, 15));
        g.drawString(hp + "/" + maxHP, (int)(boundsMiddle.getX() + boundsMiddle.getWidth()/2) - 20, (int)(boundsMiddle.getY() - 20));
        if(Timer.time - damagedTimer < 50){
            g.drawImage(damagedImage, midpointX() - damagedImage.getWidth(null)/2, midpointY() - damagedImage.getHeight(null)/2, null);
        }
        
        /*
        g.fillRect(boundsMiddle.x, boundsMiddle.y, (int)boundsMiddle.getWidth(), (int)boundsMiddle.getHeight());
        g.setColor(Color.RED);
        g.fillRect(boundsUpLeft.x, boundsUpLeft.y, (int)boundsUpLeft.getWidth(), (int)boundsUpLeft.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(boundsUp.x, boundsUp.y, (int)boundsUp.getWidth(), (int)boundsUp.getHeight());
        g.setColor(Color.RED);
        g.fillRect(boundsUpRight.x, boundsUpRight.y, (int)boundsUpRight.getWidth(), (int)boundsUpRight.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(boundsLeft.x, boundsLeft.y, (int)boundsLeft.getWidth(), (int)boundsLeft.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(boundsRight.x, boundsRight.y, (int)boundsRight.getWidth(), (int)boundsRight.getHeight());
        g.setColor(Color.RED);
        g.fillRect(boundsDownLeft.x, boundsDownLeft.y, (int)boundsDownLeft.getWidth(), (int)boundsDownLeft.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(boundsDown.x, boundsDown.y, (int)boundsDown.getWidth(), (int)boundsDown.getHeight());
        g.setColor(Color.RED);
        g.fillRect(boundsDownRight.x, boundsDownRight.y, (int)boundsDownRight.getWidth(), (int)boundsDownRight.getHeight());
        g.setColor(Color.RED);*/
    }
    public void paint(Graphics g, ArrayList<Character> other){}
    
    void attack(){}
    void attack(int x, int y){}
    void ability1(){}
    void ability1(ArrayList<Character> other){}
    void ability1(int x, int y){}
    void ability1(ArrayList<Character> other, int x, int y){}
    void ability1(int widthLimit, int heightLimit, ArrayList<Character> other){}
    void ability1(int x, int y, int widthLimit, int heightLimit){}
    void ability1(ArrayList<Character> other, int x, int y, int widthLimit, int heightLimit){}
    void ability2(){}
    void ability2(int x, int y){}
    void ability2(ArrayList<Character> other, int x, int y){}
    void ability2(int widthLimit, int heightLimit, ArrayList<Character> other){}
    void ability2(int x, int y, int widthLimit, int heightLimit){}
    void ability2(ArrayList<Character> other, int x, int y, int widthLimit, int heightLimit){}
    void moveAI(){}
    void move(ArrayList<Character> other){}
    void move(ArrayList<Character> other, int widthLimit, int heightLimit){}
    void moveProtagonist(){}
    void checkForAbility1(){}
    void checkForAbility2(){}
    void checkForAbility1(ArrayList<Character> other){}
    void checkForAbility2(ArrayList<Character> other){}
    
    
    
    void printDot(){}
}