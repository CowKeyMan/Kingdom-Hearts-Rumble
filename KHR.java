//Imported for using images
import java.awt.*;
//Imported for using events such as Mouse, Key and Action Listener
import java.awt.event.*;
//Imported in order to use double buffering
import java.awt.Graphics;
//Imported in order to use array lists
import java.util.ArrayList;
//Imported in ordder to use text and audio files
import java.io.*;
//Imported in order to get audio in our algorythm
import sun.audio.*;
import javax.swing.*;
public class KHR extends Frame implements ActionListener, MouseListener, Runnable, KeyListener{
    //To record what state the game is in
    /* 0 = opening screen
     * 1 = Main Menu
     * 2 = Custom LEvel selection
     * 3 = New/Load game
     * 4 = difficulty Selection
     * 5 = Character Selection
     * 6 = Name Entering
     * 7 = In game
     * 8 = Level Creator
     * 9 = User Selection
     * 10 = Round Finished
     * 11 = level up screen
     * 12 = KO/Death Screen
     * 13 = Pause Screen
     * 14 = ending screen
     * 15 = instructions
     * 16 = Credits
     */
    int state = 1;
    boolean isRemovinglstHit = false, isRunning = false, isPainting = false, isPaused = false;
    
    //The time for how much the thread will delay before runnning the next frame
    long threadSleepTime = 15;
    long threadSleepTimer = System.currentTimeMillis() + threadSleepTime;
    Button slow = new Button("Slow"), moderate = new Button("Moderate"), fast = new Button("Fast");
    
    String buttonClickName = "ButtonClick.wav";
    String errorButtonClickName = "ErrorButton.wav";
    
    //An array list which will hold an enemy of each type
    ArrayList<Character> charsStore;
    //Arraylist to store the ally characters
    ArrayList<Character> hijk = new ArrayList<Character>();
    
    //Items used in the double bufferring
    Image dbi;
    Graphics dbg;

    //A seperate thread for the program to run in
    Thread animeThread;

    //Toolkit to use images
    Toolkit tk = Toolkit.getDefaultToolkit();

    //The constant occuring background in our program. This changes every state
    Image background = tk.createImage("CelestialRed.jpg");
    
    //The array containing the backgrounds for the ui
    Image[] backgroundsArray = new Image[6];
    //The background which will be drawn in the UI
    Image UIBackground = tk.createImage("UI background.jpg");
    
    //The ArrayList for all the current characters on screen
    ArrayList<Character> chars = new ArrayList<Character>();

    //setting the button pressing keys to an array of integers
    ArrayList<Integer> keysUp, keysDown;
    
    
    //initialise Objects
    LevelAndUser lvl = new LevelAndUser(1344, 720, "Custom Level Users.txt", "Main Level Users.txt");
    Menus m = new Menus(1344, 720);
    LevelCreator lvlc;
    LevelSelection ls;
    CharacterSelection cs;
    UserSelection us;
    RoundFinish rf;
    LevelUpScreen lus;
    KOScreen kos;
    PauseScreen ps;
    SpecialisedSound ss = new SpecialisedSound();
    SlideShow instructions;
    SlideShow credits;
    
    String battleMusic = "Ants.wav";
    
    //Boolean to pause in the first few seconds before tarting the game
    boolean pause = true;
    
    //Boolean to see if sound may be played or not
    boolean canPlaySound = true;
    boolean canPlaySFX = true;
    Button btnCanPlaySound = new Button("Music:");
    Label lblCanPlaySound = new Label("On");
    Button btnCanPlaySFX = new Button("SFX:");
    Label lblCanPlaySFX = new Label("On");
    
    //Static Character variables
    Image pdamagedImage = tk.createImage("DamageStars.gif");
    
    //Sora stats
    //Movement images
    Image SorapupMove = tk.createImage("SoraUpMove.gif"); Image SorapdownMove = tk.createImage("SoraDownMove.gif"); Image SoraprightMove = tk.createImage("SoraRightMove.gif"); Image SorapleftMove = tk.createImage("SoraLeftMove.gif");
    Image SorapupLeftMove = tk.createImage("SoraUpLeftMove.gif"); Image SorapupRightMove = tk.createImage("SoraUpRightMove.gif"); Image SorapdownLeftMove = tk.createImage("SoraDownLeftMove.gif"); Image SorapdownRightMove = tk.createImage("SoraDownRightMove.gif");
    //Attacking images
    Image SorapupAttack = tk.createImage("SoraUpAttack1.gif"); Image SorapdownAttack = tk.createImage("SoraDownAttack1.gif"); Image SorapleftAttack = tk.createImage("SoraLeftAttack1.gif"); Image SoraprightAttack = tk.createImage("SoraRightAttack1.gif");
    Image SorapupLeftAttack = tk.createImage("SoraUpLeftAttack1.gif"); Image SorapupRightAttack = tk.createImage("SoraUpRightAttack1.gif"); Image SorapdownLeftAttack = tk.createImage("SoraDownLeftAttack1.gif"); Image SorapdownRightAttack = tk.createImage("SoraDownRightAttack1.gif");
    //Still images
    Image SorapupStill = tk.createImage("SoraUpStill.png"); Image SorapdownStill = tk.createImage("SoraDownStill.png"); Image SoraprightStill = tk.createImage("SoraRightStill.png"); Image SorapleftStill = tk.createImage("SoraLeftStill.png");
    Image SorapupLeftStill = tk.createImage("SoraUpLeftStill.png"); Image SorapupRightStill = tk.createImage("SoraUpRightStill.png"); Image SorapdownLeftStill = tk.createImage("SoraDownLeftStill.png"); Image SorapdownRightStill = tk.createImage("SoraDownRightStill.png");
    //Ability Images
    Image Sorapability1Left = tk.createImage("SoraLeftThrowKeyblade.gif"); Image Sorapability1Right = tk.createImage("SoraRightThrowKeyblade.gif"); Image Sorapability2Left = tk.createImage("SoraWindLeft.gif"); Image Sorapability2Right = tk.createImage("SoraWindRight.gif");
    //Knocked out image
    Image SorapkoImage = tk.createImage("SoraKO.gif");
    //A full 3D image used to show the character
    Image SorapbaseImage = tk.createImage("Sora.png");
    //Length and height of rectangle bounds
    int SorasideWidth = 30; int SoramidWidth = 25;
    int SorasideHeight = 24; int SoramidHeight = 45;
    //bullet radius
    int SorapbulletRadius = 25; int SorapmyBulletSpeed = 12;
    //bullet Image
    Image SorapbulletImage = tk.createImage("RotatingKeyblade.gif");
    //Timers to display animations
    long SoraphittingAnimation = 400; long Sorapability1AnimationTimer = 300; long Sorapability2AnimationTimer = 300;
    //Ability cooldown timers
    long SorapabilityCooldown1 = 6000; long SorapabilityCooldown2 = 12000;
    //Character stats
    int SorapmaxHP = 200; int SorapbaseSpeed = 4; int Soraplevel = 1; int SorapbaseDamage = 30; int SorapmaxToHitTime = 1200;
    //Images for UI
    Image SorapHealthy = tk.createImage("SoraHealthy.png"); Image SorapHurt = tk.createImage("SoraHurt.png");
    //Images for the ability icons
    Image SorapabilityImage1 = tk.createImage("KeybladeIcon.jpg"); Image SorapabilityImage2 = tk.createImage("WindIcon.jpg");
    //Images for this class
    Image SorapleftAttack2 = tk.createImage("SoraLeftAttack2.gif"); Image SorapleftAttack3 = tk.createImage("SoraLeftAttack3.gif"); Image SoraprightAttack2 = tk.createImage("SoraRightAttack2.gif"); Image SoraprightAttack3 = tk.createImage("SoraRightAttack3.gif");
    //Specific sprites
    Image SorapimageWind = tk.createImage("Wind.gif");
    Sprite Sorapwind;
    //Timer for wind
    long SorapwindDuration = 5000;

    //Riku stats
    //Movement images
    Image RikupupMove = tk.createImage("RikuUpMove.gif"); Image RikupdownMove = tk.createImage("RikuDownMove.gif"); Image RikuprightMove = tk.createImage("RikuRightMove.gif"); Image RikupleftMove = tk.createImage("RikuLeftMove.gif");
    Image RikupupLeftMove = tk.createImage("RikuUpLeftMove.gif"); Image RikupupRightMove = tk.createImage("RikuUpRightMove.gif"); Image RikupdownLeftMove = tk.createImage("RikuDownLeftMove.gif"); Image RikupdownRightMove = tk.createImage("RikuDownRightMove.gif");
    //Attacking images
    Image RikupupAttack = tk.createImage("RikuUpLeftAttack.gif"); Image RikupdownAttack = tk.createImage("RikuDownLeftAttack.gif"); Image RikupleftAttack = tk.createImage("RikuUpLeftAttack.gif"); Image RikuprightAttack = tk.createImage("RikuDownRightAttack.gif");
    Image RikupupLeftAttack = tk.createImage("RikuUpLeftAttack.gif"); Image RikupupRightAttack = tk.createImage("RikuUpRightAttack.gif"); Image RikupdownLeftAttack = tk.createImage("RikuDownLeftAttack.gif"); Image RikupdownRightAttack = tk.createImage("RikuDownRightAttack.gif");
    //Still images
    Image RikupupStill = tk.createImage("RikuUpStill.png"); Image RikupdownStill = tk.createImage("RikuDownStill.png"); Image RikuprightStill = tk.createImage("RikuRightStill.png"); Image RikupleftStill = tk.createImage("RikuLeftStill.png");
    Image RikupupLeftStill = tk.createImage("RikuUpLeftStill.png"); Image RikupupRightStill = tk.createImage("RikuUpRightStill.png"); Image RikupdownLeftStill = tk.createImage("RikuDownLeftStill.png"); Image RikupdownRightStill = tk.createImage("RikuDownRightStill.png");
    //Ability Images
    Image Rikupability1Left = null; Image Rikupability1Right = null; Image Rikupability2Left = tk.createImage("RikuDarkAuraLeft.gif"); Image Rikupability2Right = tk.createImage("RikuDarkAuraRight.gif");
    //Knocked out image
    Image RikupkoImage = tk.createImage("RikuKO.gif");
    //A full 3D image used to show the character
    Image RikupbaseImage = tk.createImage("Riku.png");
    //Length and height of rectangle bounds
    int RikusideWidth = 23; int RikumidWidth = 28;
    int RikusideHeight = 30; int RikumidHeight = 55;
    //bullet radius
    int RikupbulletRadius = 15; int RikupmyBulletSpeed = 15;
    //bullet Image
    Image RikupbulletImage = tk.createImage("DarkAuraBullet.png");
    //Timers to display animations
    long RikuphittingAnimation = 400; long Rikupability1AnimationTimer = 200; long Rikupability2AnimationTimer = 200;
    //Ability cooldown timers
    long RikupabilityCooldown1 = 3500; long RikupabilityCooldown2 = 5750;
    //Character stats
    int RikupmaxHP = 200; int RikupbaseSpeed = 4; int Rikuplevel = 1; int RikupbaseDamage = 40; int RikupmaxToHitTime = 1200;
    //Images for UI
    Image RikupHealthy = tk.createImage("RikuHealthy.png"); Image RikupHurt = tk.createImage("RikuHurt.png");
    //Images for the ability icons
    Image RikupabilityImage1 = tk.createImage("DarknessCorridorIcon.jpg"); Image RikupabilityImage2 = tk.createImage("DarkAuraIcon.jpg");
    //Specific Images
    Image RikupteleportImage = tk.createImage("Teleport.gif");
    //number of bullets for ability
    int RikupbulletAmount = 5;

    //Variables for Shadow Heartless
    //Movement images
    Image SHpupMove = tk.createImage("HeartlessUpLeftMove.gif"); Image SHpdownMove = tk.createImage("HeartlessDownRightMove.gif"); Image SHprightMove = tk.createImage("HeartlessUpRightMove.gif"); Image SHpleftMove = tk.createImage("HeartlessDownLeftMove.gif");
    Image SHpupLeftMove = tk.createImage("HeartlessUpLeftMove.gif"); Image SHpupRightMove = tk.createImage("HeartlessUpRightMove.gif"); Image SHpdownLeftMove = tk.createImage("HeartlessDownLeftMove.gif"); Image SHpdownRightMove = tk.createImage("HeartlessDownRightMove.gif");
    //Attacking images
    Image SHpupAttack = tk.createImage("HeartlessLeftAttack.gif"); Image SHpdownAttack = tk.createImage("HeartlessRightAttack.gif"); Image SHpleftAttack = tk.createImage("HeartlessLeftAttack.gif"); Image SHprightAttack = tk.createImage("HeartlessRightAttack.gif");
    Image SHpupLeftAttack = tk.createImage("HeartlessLeftAttack.gif"); Image SHpupRightAttack = tk.createImage("HeartlessRightAttack.gif"); Image SHpdownLeftAttack = tk.createImage("HeartlessLeftAttack.gif"); Image SHpdownRightAttack = tk.createImage("HeartlessRightAttack.gif");
    //Still images
    Image SHpupStill = tk.createImage("HeartlessUpLeftMove.gif"); Image SHpdownStill = tk.createImage("HeartlessDownRightMove.gif"); Image SHprightStill = tk.createImage("HeartlessUpRightMove.gif"); Image SHpleftStill = tk.createImage("HeartlessDownLeftMove.gif");
    Image SHpupLeftStill = tk.createImage("HeartlessUpLeftMove.gif"); Image SHpupRightStill = tk.createImage("HeartlessUpRightMove.gif"); Image SHpdownLeftStill = tk.createImage("HeartlessDownLeftMove.gif"); Image SHpdownRightStill = tk.createImage("HeartlessDownRightMove.gif");
    //Ability Images
    Image SHpability1Left = tk.createImage("HeartlessLeftSlide.png"); Image SHpability1Right = tk.createImage("HeartlessRightSlide.png"); Image SHpability2Left = null; Image SHpability2Right = null;
    //Knocked out image
    Image SHpkoImage = tk.createImage("HeartlessKO.gif");
    //A full 3D image used to show the character
    Image SHpbaseImage = tk.createImage("Shadow Heartless.png");
    //Length and height of rectangle bounds
    int SHsideWidth = 1; int SHmidWidth = 76;
    int SHsideHeight = 55; int SHmidHeight = 60;
    //bullet radius
    int SHpbulletRadius = 0; int SHpmyBulletSpeed = 0;
    //bullet Image
    Image SHpbulletImage = null;
    //Timers to display animations
    //Timer 2 is the duratio of the invulnerability
    long SHphittingAnimation = 402; long SHpability1AnimationTimer = 600; long SHpability2AnimationTimer = 3500;
    //Images for UI
    Image SHpHealthy = tk.createImage("ShadowHeartlessHealthy.png"); Image SHpHurt = tk.createImage("ShadowHeartlessHurt.png");
    //Images for the ability icons
    Image SHpabilityImage1 = tk.createImage("FlyingKickIcon.jpg"); Image SHpabilityImage2 = tk.createImage("PuddleIcon.jpg");
    //Ability cooldown timers
    long SHpabilityCooldown1 = 3500; long SHpabilityCooldown2 = 11000;
    //Character stats
    int SHpmaxHP = 200; int SHpbaseSpeed = 4; int SHplevel = 1; int SHpbaseDamage = 35; int SHpmaxToHitTime = 1200;
    //Images for this class
    Image SHpinvUpLeftMove = tk.createImage("HeartlessUpLeftSink.gif"); Image SHpinvUpRightMove = tk.createImage("HeartlessUpRightSink.gif"); Image SHpinvDownLeftMove = tk.createImage("HeartlessDownLeftSink.gif"); Image SHpinvDownRightMove = tk.createImage("HeartlessDownRightSink.gif");

    //Variables for Cloud
    //Movement images
    Image CloudpupMove = tk.createImage("CloudLeftMove.gif"); Image CloudpdownMove = tk.createImage("CloudRightMove.gif"); Image CloudprightMove = tk.createImage("CloudRightMove.gif"); Image CloudpleftMove = tk.createImage("CloudLeftMove.gif");
    Image CloudpupLeftMove = tk.createImage("CloudLeftMove.gif"); Image CloudpupRightMove = tk.createImage("CloudRightMove.gif"); Image CloudpdownLeftMove = tk.createImage("CloudLeftMove.gif"); Image CloudpdownRightMove = tk.createImage("CloudRightMove.gif");
    //Attacking images
    Image CloudpupAttack = tk.createImage("CloudAttack1Left.gif"); Image CloudpdownAttack = tk.createImage("CloudAttack1Right.gif"); Image CloudpleftAttack = tk.createImage("CloudAttack1Left.gif"); Image CloudprightAttack = tk.createImage("CloudAttack1Right.gif");
    Image CloudpupLeftAttack = tk.createImage("CloudAttack1Left.gif"); Image CloudpupRightAttack = tk.createImage("CloudAttack1Right.gif"); Image CloudpdownLeftAttack = tk.createImage("CloudAttack1Left.gif"); Image CloudpdownRightAttack = tk.createImage("CloudAttack1Right.gif");
    //Still images
    Image CloudpupStill = tk.createImage("CloudLeftMove.gif"); Image CloudpdownStill = tk.createImage("CloudRightMove.gif"); Image CloudprightStill = tk.createImage("CloudRightMove.gif"); Image CloudpleftStill = tk.createImage("CloudLeftMove.gif");
    Image CloudpupLeftStill = tk.createImage("CloudLeftMove.gif"); Image CloudpupRightStill = tk.createImage("CloudRightMove.gif"); Image CloudpdownLeftStill = tk.createImage("CloudLeftMove.gif"); Image CloudpdownRightStill = tk.createImage("CloudRightMove.gif");
    //Ability Images
    Image Cloudpability1Left = tk.createImage("GreenBoostAnimation.gif"); Image Cloudpability1Right = tk.createImage("GreenBoostAnimation.gif"); Image Cloudpability2Left = tk.createImage("RedBoostAnimation.gif"); Image Cloudpability2Right = tk.createImage("RedBoostAnimation.gif");
    //Knocked out image
    Image CloudpkoImage = tk.createImage("CloudKO.gif");
    //A full 3D image used to show the character
    Image CloudpbaseImage = tk.createImage("Cloud Strife.png");
    //Length and height of rectangle bounds
    int CloudsideWidth = 30; int CloudmidWidth = 70;
    int CloudsideHeight = 30; int CloudmidHeight = 40;
    //bullet radius
    int CloudpbulletRadius = 0; int CloudpmyBulletSpeed = 0;
    //bullet Image
    Image CloudpbulletImage = null;
    //Timers to display animations
    long CloudphittingAnimation = 360; long Cloudpability1AnimationTimer = 0; long Cloudpability2AnimationTimer = 0;
    //Ability cooldown timers
    long CloudpabilityCooldown1 = 12000; long CloudpabilityCooldown2 = 15000;
    //Character stats
    int CloudpmaxHP = 200; int CloudpbaseSpeed = 3; int Cloudplevel = 1; int CloudpbaseDamage = 35; int CloudpmaxToHitTime = 1200;
    //Images for UI
    Image CloudpHealthy = tk.createImage("CloudHealthy.png"); Image CloudpHurt = tk.createImage("CloudHurt.png");
    //Images for the ability icons
    Image CloudpabilityImage1 = tk.createImage("GreenRocketIcon.png"); Image CloudpabilityImage2 = tk.createImage("MusclesIcon.jpg");
    //Images for 2nd and 3rd attack
    Image CloudprightAttack2 = tk.createImage("CloudAttack2Right.gif"), CloudpleftAttack2 = tk.createImage("CloudAttack2Left.gif"), CloudprightAttack3 = tk.createImage("CloudAttack3Right.gif"), CloudpleftAttack3 = tk.createImage("CloudAttack3Left.gif");
    //Duration for abilities
    long CloudpabilityDuration1 = 4500, CloudpabilityDuration2 = 4500;

    //Variables for Donald
    //Movement images
    Image DonaldpupMove = tk.createImage("DonaldUpLeftMove.gif"); Image DonaldpdownMove = tk.createImage("DonaldDownRightMove.gif"); Image DonaldprightMove = tk.createImage("DonaldUpRightMove.gif"); Image DonaldpleftMove = tk.createImage("DonaldDownLeftMove.gif");
    Image DonaldpupLeftMove = tk.createImage("DonaldUpLeftMove.gif"); Image DonaldpupRightMove = tk.createImage("DonaldUpRightMove.gif"); Image DonaldpdownLeftMove = tk.createImage("DonaldDownLeftMove.gif"); Image DonaldpdownRightMove = tk.createImage("DonaldDownRightMove.gif");
    //Attacking images
    Image DonaldpupAttack = tk.createImage("DonaldAttackLeft.gif"); Image DonaldpdownAttack = tk.createImage("DonaldAttackRight.gif"); Image DonaldpleftAttack = tk.createImage("DonaldAttackLeft.gif"); Image DonaldprightAttack = tk.createImage("DonaldAttackRight.gif");
    Image DonaldpupLeftAttack = tk.createImage("DonaldAttackLeft.gif"); Image DonaldpupRightAttack = tk.createImage("DonaldAttackRight.gif"); Image DonaldpdownLeftAttack = tk.createImage("DonaldAttackLeft.gif"); Image DonaldpdownRightAttack = tk.createImage("DonaldAttackRight.gif");
    //Still images
    Image DonaldpupStill = tk.createImage("DonaldUpLeftStill.png"); Image DonaldpdownStill = tk.createImage("DonaldDownRightStill.png"); Image DonaldprightStill = tk.createImage("DonaldUpRightStill.png"); Image DonaldpleftStill = tk.createImage("DonaldDownLeftStill.png");
    Image DonaldpupLeftStill = tk.createImage("DonaldUpLeftStill.png"); Image DonaldpupRightStill = tk.createImage("DonaldUpRightStill.png"); Image DonaldpdownLeftStill = tk.createImage("DonaldDownLeftStill.png"); Image DonaldpdownRightStill = tk.createImage("DonaldDownRightStill.png");
    //Ability Images
    Image Donaldpability1Left = tk.createImage("DonaldThunderLeft.gif"); Image Donaldpability1Right = tk.createImage("DonaldThunderRight.gif"); Image Donaldpability2Left = tk.createImage("DonaldThunderLeft.gif"); Image Donaldpability2Right = tk.createImage("DonaldThunderRight.gif");
    //Knocked out image
    Image DonaldpkoImage = tk.createImage("DonaldKO.gif");
    //A full 3D image used to show the character
    Image DonaldpbaseImage = tk.createImage("Donald.png");
    //Length and height of rectangle bounds
    int DonaldsideWidth = 34; int DonaldmidWidth = 26;
    int DonaldsideHeight = 17; int DonaldmidHeight = 45;
    //bullet radius
    int DonaldpbulletRadius = 0; int DonaldpmyBulletSpeed = 0;
    //bullet Image
    Image DonaldpbulletImage = null;
    //Timers to display animations
    long DonaldphittingAnimation = 400; long Donaldpability1AnimationTimer = 400; long Donaldpability2AnimationTimer = 400;
    //Ability cooldown timers
    long DonaldpabilityCooldown1 = 4500; long DonaldpabilityCooldown2 = 10000;
    //Character stats
    int DonaldpmaxHP = 150; int DonaldpbaseSpeed = 4; int Donaldplevel = 1; int DonaldpbaseDamage = 38; int DonaldpmaxToHitTime = 1200;
    //Images for UI
    Image DonaldpHealthy = tk.createImage("DonaldHealthy.png"); Image DonaldpHurt = tk.createImage("DonaldHurt.png");
    //Images for the ability icons
    Image DonaldpabilityImage1 = tk.createImage("LeafIcon.jpg"); Image DonaldpabilityImage2 = tk.createImage("LightningBoltIcon.jpg");
    //Thunder and heal animation images
    Image DonaldpthunderImage = tk.createImage("Thunder.gif"), DonaldphealImage = tk.createImage("Heal.gif");
    int DonaldpthunderWidth = 135; int DonaldpThunderHeight = 156; int DonaldpthunderLength = 60;
    //The duration for the thunder image animation
    long DonaldpthunderDuration = 240;
    //The durations for the heal image animation
    long DonaldphealDuration = 1500;
    //The amount of times damage is multiplied to heal
    int DonaldphealToDamage = 1;

    //Variables for Goofy
    //Movement images
    Image GoofypupMove = tk.createImage("GoofyUpLeftMove.gif"); Image GoofypdownMove = tk.createImage("GoofyDownRightMove.gif"); Image GoofyprightMove = tk.createImage("GoofyUpRightMove.gif"); Image GoofypleftMove = tk.createImage("GoofyDownLeftMove.gif");
    Image GoofypupLeftMove = tk.createImage("GoofyUpLeftMove.gif"); Image GoofypupRightMove = tk.createImage("GoofyUpRightMove.gif"); Image GoofypdownLeftMove = tk.createImage("GoofyDownLeftMove.gif"); Image GoofypdownRightMove = tk.createImage("GoofyDownRightMove.gif");
    //Attacking images
    Image GoofypupAttack = tk.createImage("GoofyLeftSpin.gif"); Image GoofypdownAttack = tk.createImage("GoofyRightSpin.gif"); Image GoofypleftAttack = tk.createImage("GoofyLeftSpin.gif"); Image GoofyprightAttack = tk.createImage("GoofyRightSpin.gif");
    Image GoofypupLeftAttack = tk.createImage("GoofyLeftSpin.gif"); Image GoofypupRightAttack = tk.createImage("GoofyRightSpin.gif"); Image GoofypdownLeftAttack = tk.createImage("GoofyLeftSpin.gif"); Image GoofypdownRightAttack = tk.createImage("GoofyRightSpin.gif");
    //Still images
    Image GoofypupStill = tk.createImage("GoofyUpLeftStill.png"); Image GoofypdownStill = tk.createImage("GoofyDownRightStill.png"); Image GoofyprightStill = tk.createImage("GoofyUpRightStill.png"); Image GoofypleftStill = tk.createImage("GoofyDownLeftStill.png");
    Image GoofypupLeftStill = tk.createImage("GoofyUpLeftStill.png"); Image GoofypupRightStill = tk.createImage("GoofyUpRightStill.png"); Image GoofypdownLeftStill = tk.createImage("GoofyDownLeftStill.png"); Image GoofypdownRightStill = tk.createImage("GoofyDownRightStill.png");
    //Ability Images
    Image Goofypability1Left = tk.createImage("GoofySpin.gif"); Image Goofypability1Right = tk.createImage("GoofySpin.gif"); Image Goofypability2Left = tk.createImage("GoofyThrowShieldLeft.gif"); Image Goofypability2Right = tk.createImage("GoofyThrowShieldRight.gif");
    //Knocked out image
    Image GoofypkoImage = tk.createImage("GoofyKO.gif");
    //A full 3D image used to show the character
    Image GoofypbaseImage = tk.createImage("Goofy.png");
    //Length and height of rectangle bounds
    int GoofysideWidth = 28; int GoofymidWidth = 30;
    int GoofysideHeight = 1; int GoofymidHeight = 63;
    //bullet radius
    int GoofypbulletRadius = 25; int GoofypmyBulletSpeed = 15;
    //bullet Image
    Image GoofypbulletImage = tk.createImage("GoofyShield.gif");
    //Timers to display animations
    //timer 1 is the duration of the spin move
    long GoofyphittingAnimation = 396; long Goofypability1AnimationTimer = 5000; long Goofypability2AnimationTimer = 400;
    //Ability cooldown timers
    long GoofypabilityCooldown1 = 11500; long GoofypabilityCooldown2 = 5000;
    //Character stats
    int GoofypmaxHP = 270; int GoofypbaseSpeed = 4; int Goofyplevel = 1; int GoofypbaseDamage = 40; int GoofypmaxToHitTime = 1200;
    //Images for UI
    Image GoofypHealthy = tk.createImage("GoofyHealthy.png"); Image GoofypHurt = tk.createImage("GoofyHurt.png");
    //Images for the ability icons
    Image GoofypabilityImage1 = tk.createImage("SpiralIcon.jpg"); Image GoofypabilityImage2 = tk.createImage("ShieldIcon.jpg");

    //Variables for Blue Rhapsody
    //Movement images
    Image BRpupMove = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpdownMove = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRprightMove = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRpleftMove = tk.createImage("BlueRhapsodyLeftMove.gif");
    Image BRpupLeftMove = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpupRightMove = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRpdownLeftMove = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpdownRightMove = tk.createImage("BlueRhapsodyRightMove.gif");
    //Attacking images
    Image BRpupAttack = tk.createImage("BlueRhapsodyLeftAttack.gif"); Image BRpdownAttack = tk.createImage("BlueRhapsodyRightAttack.gif"); Image BRpleftAttack = tk.createImage("BlueRhapsodyLeftAttack.gif"); Image BRprightAttack = tk.createImage("BlueRhapsodyRightAttack.gif");
    Image BRpupLeftAttack = tk.createImage("BlueRhapsodyLeftAttack.gif"); Image BRpupRightAttack = tk.createImage("BlueRhapsodyRightAttack.gif"); Image BRpdownLeftAttack = tk.createImage("BlueRhapsodyLeftAttack.gif"); Image BRpdownRightAttack = tk.createImage("BlueRhapsodyRightAttack.gif");
    //Still images
    Image BRpupStill = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpdownStill = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRprightStill = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRpleftStill = tk.createImage("BlueRhapsodyLeftMove.gif");
    Image BRpupLeftStill = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpupRightStill = tk.createImage("BlueRhapsodyRightMove.gif"); Image BRpdownLeftStill = tk.createImage("BlueRhapsodyLeftMove.gif"); Image BRpdownRightStill = tk.createImage("BlueRhapsodyRightMove.gif");
    //Ability Images
    Image BRpability1Left = tk.createImage("BlueRhapsodyLeftShoot.gif"); Image BRpability1Right = tk.createImage("BlueRhapsodyRightShoot.gif"); Image BRpability2Left = tk.createImage("BlueRhapsodyLeftShoot.gif"); Image BRpability2Right = tk.createImage("BlueRhapsodyRightShoot.gif");
    //Knocked out image
    Image BRpkoImage = tk.createImage("BlueRhapsodyKO.gif");
    //A full 3D image used to show the character
    Image BRpbaseImage = tk.createImage("Blue Rhapsody.png");
    //Length and height of rectangle bounds
    int BRsideWidth = 21; int BRmidWidth = 16;
    int BRsideHeight = 12; int BRmidHeight = 28;
    //bullet radius
    int BRpbulletRadius = 10; int BRpmyBulletSpeed = 5;
    //bullet Image
    Image BRpbulletImage = tk.createImage("BlizzardBullet.png");
    //Timers to display animations
    //Timer 1 is the duration of beingranged
    long BRphittingAnimation = 360; long BRpability1AnimationTimer = 210    ; long BRpability2AnimationTimer = 210;
    //Ability cooldown timers
    long BRpabilityCooldown1 = 11000; long BRpabilityCooldown2 = 6000;
    //Character stats
    int BRpmaxHP = 200; int BRpbaseSpeed = 4; int BRplevel = 1; int BRpbaseDamage = 35; int BRpmaxToHitTime = 1200;
    //Images for UI
    Image BRpHealthy = tk.createImage("BlueRhapsodyHealthy.png"); Image BRpHurt = tk.createImage("BlueRhapsodyHurt.png");
    //Images for the ability icons
    Image BRpabilityImage1 = tk.createImage("GunIcon.jpg"); Image BRpabilityImage2 = tk.createImage("IceBoltIcon.jpg");
    //Image to be displayed whenicy blast hits
    Image BRpicyBlast = tk.createImage("Breaking Ice.gif");
    //The timer for how long the animation should be displayed for
    long BRpicyBlastDuration = 300;
    //Snowball Image
    Image BRpsnowballImage = tk.createImage("IcyBlastIceball.png");
    //Speed for the icyblasts and radius
    int BRpibSpeed = 6, BRpibRadius = 98, BRpsnowballRadius = 25;
    //Timer for when the character is to shoot
    long BRphittingFrequency = 350;
    //The duration for being ranged
    long BRprangedDuration = 6000;

    //Variables for Leon
    //Movement images
    Image LeonpupMove = tk.createImage("LeonUpLeftMove.gif"); Image LeonpdownMove = tk.createImage("LeonDownRightMove.gif"); Image LeonprightMove = tk.createImage("LeonUpRightMove.gif"); Image LeonpleftMove = tk.createImage("LeonDownLeftMove.gif");
    Image LeonpupLeftMove = tk.createImage("LeonUpLeftMove.gif"); Image LeonpupRightMove = tk.createImage("LeonUpRightMove.gif"); Image LeonpdownLeftMove = tk.createImage("LeonDownLeftMove.gif"); Image LeonpdownRightMove = tk.createImage("LeonDownRightMove.gif");
    //Attacking images
    Image LeonpupAttack = tk.createImage("LeonLeftAttack.gif"); Image LeonpdownAttack = tk.createImage("LeonRightAttack.gif"); Image LeonpleftAttack = tk.createImage("LeonLeftAttack.gif"); Image LeonprightAttack = tk.createImage("LeonRightAttack.gif");
    Image LeonpupLeftAttack = tk.createImage("LeonLeftAttack.gif"); Image LeonpupRightAttack = tk.createImage("LeonRightAttack.gif"); Image LeonpdownLeftAttack = tk.createImage("LeonLeftAttack.gif"); Image LeonpdownRightAttack = tk.createImage("LeonRightAttack.gif");
    //Still images
    Image LeonpupStill = tk.createImage("LeonUpLeftStill.png"); Image LeonpdownStill = tk.createImage("LeonDownRightStill.png"); Image LeonprightStill = tk.createImage("LeonUpRightStill.png"); Image LeonpleftStill = tk.createImage("LeonDownLeftStill.png");
    Image LeonpupLeftStill = tk.createImage("LeonUpLeftStill.png"); Image LeonpupRightStill = tk.createImage("LeonUpRightStill.png"); Image LeonpdownLeftStill = tk.createImage("LeonDownLeftStill.png"); Image LeonpdownRightStill = tk.createImage("LeonDownRightStill.png");
    //Ability Images
    Image Leonpability1Left = tk.createImage("LeonLeftShoot.gif"); Image Leonpability1Right = tk.createImage("LeonRightShoot.gif"); Image Leonpability2Left = tk.createImage("LeonLeftShoot.gif"); Image Leonpability2Right = tk.createImage("LeonRightShoot.gif");
    //Knocked out image
    Image LeonpkoImage = tk.createImage("LeonKO.gif");
    //A full 3D image used to show the character
    Image LeonpbaseImage = tk.createImage("Squall Leonhart.png");
    //Length and height of rectangle bounds
    int LeonsideWidth = 51; int LeonmidWidth = 26;
    int LeonsideHeight = 10; int LeonmidHeight = 80;
    //bullet radius
    int LeonpbulletRadius = 23; int LeonpmyBulletSpeed = 10;
    //bullet Image
    Image LeonpbulletImage = tk.createImage("FlamingFireball.gif");
    //Timers to display animations
    long LeonphittingAnimation = 400; long Leonpability1AnimationTimer = 400; long Leonpability2AnimationTimer = 400;
    //Ability cooldown timers
    long LeonpabilityCooldown1 = 8000; long LeonpabilityCooldown2 = 8000;
    //Character stats
    int LeonpmaxHP = 200; int LeonpbaseSpeed = 4; int Leonplevel = 0; int LeonpbaseDamage = 40; int LeonpmaxToHitTime = 1200;
    //Images for UI
    Image LeonpHealthy = tk.createImage("LeonHealthy.png"); Image LeonpHurt = tk.createImage("LeonHurt.png");
    //Images for the ability icons
    Image LeonpabilityImage1 = tk.createImage("TargetIcon.jpg"); Image LeonpabilityImage2 = tk.createImage("FireballIcon.jpg");
    //The duration that the bullet follows the other character
    long LeonpbulletDuration = 1300;
    //Duration of dot
    int LeonpdotAmount = 4;
    //Animation for the directed shot
    Image LeonpdirShot = tk.createImage("Puff of Smoke.gif");
    //Animation for the enemy taking dot
    Image LeonpdotAnim = tk.createImage("Animated Fire.gif");
    
    //Variables for Bob
    //Movement images
    Image BobpupMove = tk.createImage("BobUpMove.gif"); Image BobpdownMove = tk.createImage("BobDownMove.gif"); Image BobprightMove = tk.createImage("BobRightMove.gif"); Image BobpleftMove = tk.createImage("BobLeftMove.gif");
    Image BobpupLeftMove = tk.createImage("BobUpLeftMove.gif"); Image BobpupRightMove = tk.createImage("BobUpRightMove.gif"); Image BobpdownLeftMove = tk.createImage("BobDownLeftMove.gif"); Image BobpdownRightMove = tk.createImage("BobDownRightMove.gif");
    //Attacking images
    Image BobpupAttack = tk.createImage("BobUpAttack.gif"); Image BobpdownAttack = tk.createImage("BobDownAttack.gif"); Image BobpleftAttack = tk.createImage("BobLeftAttack.gif"); Image BobprightAttack = tk.createImage("BobRightAttack.gif");
    Image BobpupLeftAttack = tk.createImage("BobUpLeftAttack.gif"); Image BobpupRightAttack = tk.createImage("BobUpRightAttack.gif"); Image BobpdownLeftAttack = tk.createImage("BobDownLeftAttack.gif"); Image BobpdownRightAttack = tk.createImage("BobDownRightAttack.gif");
    //Still images
    Image BobpupStill = tk.createImage("BobUpStill.png"); Image BobpdownStill = tk.createImage("BobDownStill.png"); Image BobprightStill = tk.createImage("BobRightStill.png"); Image BobpleftStill = tk.createImage("BobLeftStill.png");
    Image BobpupLeftStill = tk.createImage("BobUpLeftStill.png"); Image BobpupRightStill = tk.createImage("BobUpRightStill.png"); Image BobpdownLeftStill = tk.createImage("BobDownLeftStill.png"); Image BobpdownRightStill = tk.createImage("BobDownRightStill.png");
    //Ability Images
    Image Bobpability1Left = tk.createImage("BobStarAttack.gif"); Image Bobpability1Right = tk.createImage("BobStarAttack.gif"); Image Bobpability2Left = tk.createImage("BobAbility2Left.gif"); Image Bobpability2Right = tk.createImage("BobAbility2Right.gif");
    //Knocked out image
    Image BobpkoImage = tk.createImage("BobKO.gif");
    //A full 3D image used to show the character
    Image BobpbaseImage = tk.createImage("Bob.png");
    //Length and height of rectangle bounds
    int BobsideWidth = 33; int BobmidWidth = 33;
    int BobsideHeight = 50; int BobmidHeight = 60;
    //bullet radius
    int BobpbulletRadius = 35; int BobpmyBulletSpeed = 10;
    //bullet Image
    Image BobpbulletImage = tk.createImage("3 Swords.gif");
    //Timers to display animations
    long BobphittingAnimation = 400; long Bobpability1AnimationTimer = 400; long Bobpability2AnimationTimer = 400;
    //Ability cooldown timers
    long BobpabilityCooldown1 = 6000; long BobpabilityCooldown2 = 12000;
    //Character stats
    int BobpmaxHP = 200; int BobpbaseSpeed = 4; int Bobplevel = 0; int BobpbaseDamage = 40; int BobpmaxToHitTime = 1200;
    //Images for UI
    Image BobpHealthy = tk.createImage("BobHealthy.png"); Image BobpHurt = tk.createImage("BobHurt.png");
    //Images for the ability icons
    Image BobpabilityImage1 = tk.createImage("StarAttackIcon.png"); Image BobpabilityImage2 = tk.createImage("FrisbeeIcon.png");
    //The duration for how long the bullet will travel before returning to the character
    long BobptravelTime = 1000;
    
    
    void addSora(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Sora(
                //Movement images
                SorapupMove, SorapdownMove, SoraprightMove, SorapleftMove,
                SorapupLeftMove, SorapupRightMove, SorapdownLeftMove, SorapdownRightMove,
                //Attacking images
                SorapupAttack, SorapdownAttack, SorapleftAttack, SoraprightAttack,
                SorapupLeftAttack, SorapupRightAttack, SorapdownLeftAttack, SorapdownRightAttack,
                //Still images
                SorapupStill, SorapdownStill, SoraprightStill, SorapleftStill,
                SorapupLeftStill, SorapupRightStill, SorapdownLeftStill, SorapdownRightStill,
                //Ability Images
                Sorapability1Left, Sorapability1Right, Sorapability2Left, Sorapability2Right,
                //Knocked out image
                SorapkoImage,
                //A full 3D image used to show the character
                SorapbaseImage,
                //Length and height of rectangle bounds
                SorasideWidth,  SoramidWidth,
                SorasideHeight,  SoramidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                SorapbulletRadius, SorapmyBulletSpeed,
                //bullet Image
                SorapbulletImage,
                //Timers to display animations
                SoraphittingAnimation,  Sorapability1AnimationTimer,  Sorapability2AnimationTimer,
                //Ability cooldown timers
                SorapabilityCooldown1,  SorapabilityCooldown2,
                //Character stats
                SorapmaxHP,  SorapbaseSpeed,  Soraplevel,  SorapbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                SorapmaxToHitTime,
                //Images for UI and their coordinates
                SorapHealthy, SorapHurt,
                1205, 642, 1198, 644,
                //Images for the ability icons
                SorapabilityImage1, SorapabilityImage2,
                //Specific Images for this class
                SorapleftAttack2, SorapleftAttack3, SoraprightAttack2, SoraprightAttack3,
                //Specific sprites
                SorapimageWind,
                Sorapwind,
                //Timer for wind
                SorapwindDuration
            ));
        ((Sora)chars.get(chars.size()-1)).wind.setWidth(37);
        ((Sora)chars.get(chars.size()-1)).wind.setHeight(50);
    }

    void addRiku(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Riku(
                //Movement images
                RikupupMove, RikupdownMove, RikuprightMove, RikupleftMove,
                RikupupLeftMove, RikupupRightMove, RikupdownLeftMove, RikupdownRightMove,
                //Attacking images
                RikupupAttack, RikupdownAttack, RikupleftAttack, RikuprightAttack,
                RikupupLeftAttack, RikupupRightAttack, RikupdownLeftAttack, RikupdownRightAttack,
                //Still images
                RikupupStill, RikupdownStill, RikuprightStill, RikupleftStill,
                RikupupLeftStill, RikupupRightStill, RikupdownLeftStill, RikupdownRightStill,
                //Ability Images
                Rikupability1Left, Rikupability1Left, Rikupability2Left, Rikupability2Right,
                //Knocked out image
                RikupkoImage,
                //A full 3D image used to show the character
                RikupbaseImage,
                //Length and height of rectangle bounds
                RikusideWidth,  RikumidWidth,
                RikusideHeight,  RikumidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                RikupbulletRadius, RikupmyBulletSpeed,
                //bullet Image
                RikupbulletImage,
                //Timers to display animations
                RikuphittingAnimation,  Rikupability1AnimationTimer,  Rikupability2AnimationTimer,
                //Ability cooldown timers
                RikupabilityCooldown1,  RikupabilityCooldown2,
                //Character stats
                RikupmaxHP,  RikupbaseSpeed,  Rikuplevel,  RikupbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                RikupmaxToHitTime,
                //Images for UI and their coordinates
                RikupHealthy, RikupHurt,
                1208, 656, 1210, 656,
                //Images for the ability icons
                RikupabilityImage1, RikupabilityImage2,
                //Specific Images for this class
                RikupteleportImage,
                //Specific variables
                RikupbulletAmount
            ));
    }

    void addSH(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new ShadowHeartless(
                //Movement images
                SHpupMove, SHpdownMove, SHprightMove, SHpleftMove,
                SHpupLeftMove, SHpupRightMove, SHpdownLeftMove, SHpdownRightMove,
                //Attacking images
                SHpupAttack, SHpdownAttack, SHpleftAttack, SHprightAttack,
                SHpupLeftAttack, SHpupRightAttack, SHpdownLeftAttack, SHpdownRightAttack,
                //Still images
                SHpupStill, SHpdownStill, SHprightStill, SHpleftStill,
                SHpupLeftStill, SHpupRightStill, SHpdownLeftStill, SHpdownRightStill,
                //Ability Images
                SHpability1Left, SHpability1Right, SHpability2Left, SHpability2Right,
                //Knocked out image
                SHpkoImage,
                //A full 3D image used to show the character
                SHpbaseImage,
                //Length and height of rectangle bounds
                SHsideWidth,  SHmidWidth,
                SHsideHeight,  SHmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                SHpbulletRadius, SHpmyBulletSpeed,
                //bullet Image
                SHpbulletImage,
                //Timers to display animations
                SHphittingAnimation,  SHpability1AnimationTimer,  SHpability2AnimationTimer,
                //Ability cooldown timers
                SHpabilityCooldown1,  SHpabilityCooldown2,
                //Character stats
                SHpmaxHP,  SHpbaseSpeed,  SHplevel,  SHpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                SHpmaxToHitTime,
                //Images for UI and their coordinates
                SHpHealthy, SHpHurt,
                1200, 661, 1202, 661,
                //Images for the ability icons
                SHpabilityImage1, SHpabilityImage2,
                //Images to be used when invulnerable
                SHpinvUpLeftMove, SHpinvUpRightMove, SHpinvDownLeftMove , SHpinvDownRightMove
            ));
    }

    void addCloud(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Cloud(
                //Movement images
                CloudpupMove, CloudpdownMove, CloudprightMove, CloudpleftMove,
                CloudpupLeftMove, CloudpupRightMove, CloudpdownLeftMove, CloudpdownRightMove,
                //Attacking images
                CloudpupAttack, CloudpdownAttack, CloudpleftAttack, CloudprightAttack,
                CloudpupLeftAttack, CloudpupRightAttack, CloudpdownLeftAttack, CloudpdownRightAttack,
                //Still images
                CloudpupStill, CloudpdownStill, CloudprightStill, CloudpleftStill,
                CloudpupLeftStill, CloudpupRightStill, CloudpdownLeftStill, CloudpdownRightStill,
                //Ability Images
                Cloudpability1Left, Cloudpability1Left, Cloudpability2Left, Cloudpability2Right,
                //Knocked out image
                CloudpkoImage,
                //A full 3D image used to show the character
                CloudpbaseImage,
                //Length and height of rectangle bounds
                CloudsideWidth,  CloudmidWidth,
                CloudsideHeight,  CloudmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                CloudpbulletRadius, CloudpmyBulletSpeed,
                //bullet Image
                CloudpbulletImage,
                //Timers to display animations
                CloudphittingAnimation,  Cloudpability1AnimationTimer,  Cloudpability2AnimationTimer,
                //Ability cooldown timers
                CloudpabilityCooldown1,  CloudpabilityCooldown2,
                //Character stats
                CloudpmaxHP,  CloudpbaseSpeed,  Cloudplevel,  CloudpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                CloudpmaxToHitTime,
                //Images for UI and their coordinates
                CloudpHealthy, CloudpHurt,
                1192, 643, 1203, 641,
                //Images for the ability icons
                CloudpabilityImage1, CloudpabilityImage2,
                //Images for 2nd and 3rd attack
                CloudprightAttack2, CloudpleftAttack2, CloudprightAttack3, CloudpleftAttack3,
                //Duration for abilities
                CloudpabilityDuration1, CloudpabilityDuration2

            ));
    }

    void addDonald(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Donald(
                //Movement images
                DonaldpupMove, DonaldpdownMove, DonaldprightMove, DonaldpleftMove,
                DonaldpupLeftMove, DonaldpupRightMove, DonaldpdownLeftMove, DonaldpdownRightMove,
                //Attacking images
                DonaldpupAttack, DonaldpdownAttack, DonaldpleftAttack, DonaldprightAttack,
                DonaldpupLeftAttack, DonaldpupRightAttack, DonaldpdownLeftAttack, DonaldpdownRightAttack,
                //Still images
                DonaldpupStill, DonaldpdownStill, DonaldprightStill, DonaldpleftStill,
                DonaldpupLeftStill, DonaldpupRightStill, DonaldpdownLeftStill, DonaldpdownRightStill,
                //Ability Images
                Donaldpability1Left, Donaldpability1Left, Donaldpability2Left, Donaldpability2Right,
                //Knocked out image
                DonaldpkoImage,
                //A full 3D image used to show the character
                DonaldpbaseImage,
                //Length and height of rectangle bounds
                DonaldsideWidth,  DonaldmidWidth,
                DonaldsideHeight,  DonaldmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                DonaldpbulletRadius, DonaldpmyBulletSpeed,
                //bullet Image
                DonaldpbulletImage,
                //Timers to display animations
                DonaldphittingAnimation,  Donaldpability1AnimationTimer,  Donaldpability2AnimationTimer,
                //Ability cooldown timers
                DonaldpabilityCooldown1,  DonaldpabilityCooldown2,
                //Character stats
                DonaldpmaxHP,  DonaldpbaseSpeed,  Donaldplevel,  DonaldpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                DonaldpmaxToHitTime,
                //Images for UI and their coordinates
                DonaldpHealthy, DonaldpHurt,
                1205, 648, 1205, 648,
                //Images for the ability icons
                DonaldpabilityImage1, DonaldpabilityImage2,
                //Thunder and heal animation images
                DonaldpthunderImage, DonaldphealImage,
                DonaldpthunderWidth, DonaldpThunderHeight, DonaldpthunderLength,
                //The duration for the thunder image animation
                DonaldpthunderDuration,
                //The durations for the heal image animation
                DonaldphealDuration,
                //The amount of times damage is multiplied to heal
                DonaldphealToDamage
            ));
    }

    void addGoofy(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Goofy(
                //Movement images
                GoofypupMove, GoofypdownMove, GoofyprightMove, GoofypleftMove,
                GoofypupLeftMove, GoofypupRightMove, GoofypdownLeftMove, GoofypdownRightMove,
                //Attacking images
                GoofypupAttack, GoofypdownAttack, GoofypleftAttack, GoofyprightAttack,
                GoofypupLeftAttack, GoofypupRightAttack, GoofypdownLeftAttack, GoofypdownRightAttack,
                //Still images
                GoofypupStill, GoofypdownStill, GoofyprightStill, GoofypleftStill,
                GoofypupLeftStill, GoofypupRightStill, GoofypdownLeftStill, GoofypdownRightStill,
                //Ability Images
                Goofypability1Left, Goofypability1Right, Goofypability2Left, Goofypability2Right,
                //Knocked out image
                GoofypkoImage,
                //A full 3D image used to show the character
                GoofypbaseImage,
                //Length and height of rectangle bounds
                GoofysideWidth,  GoofymidWidth,
                GoofysideHeight,  GoofymidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                GoofypbulletRadius, GoofypmyBulletSpeed,
                //bullet Image
                GoofypbulletImage,
                //Timers to display animations
                GoofyphittingAnimation,  Goofypability1AnimationTimer,  Goofypability2AnimationTimer,
                //Ability cooldown timers
                GoofypabilityCooldown1,  GoofypabilityCooldown2,
                //Character stats
                GoofypmaxHP,  GoofypbaseSpeed,  Goofyplevel,  GoofypbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                GoofypmaxToHitTime,
                //Images for UI and their coordinates
                GoofypHealthy, GoofypHurt,
                1204, 643, 1208, 642,
                //Images for the ability icons
                GoofypabilityImage1, GoofypabilityImage2
            ));
    }

    void addBR(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new BlueRhapsody(
                //Movement images
                BRpupMove, BRpdownMove, BRprightMove, BRpleftMove,
                BRpupLeftMove, BRpupRightMove, BRpdownLeftMove, BRpdownRightMove,
                //Attacking images
                BRpupAttack, BRpdownAttack, BRpleftAttack, BRprightAttack,
                BRpupLeftAttack, BRpupRightAttack, BRpdownLeftAttack, BRpdownRightAttack,
                //Still images
                BRpupStill, BRpdownStill, BRprightStill, BRpleftStill,
                BRpupLeftStill, BRpupRightStill, BRpdownLeftStill, BRpdownRightStill,
                //Ability Images
                BRpability1Left, BRpability1Right, BRpability2Left, BRpability2Right,
                //Knocked out image
                BRpkoImage,
                //A full 3D image used to show the character
                BRpbaseImage,
                //Length and height of rectangle bounds
                BRsideWidth,  BRmidWidth,
                BRsideHeight,  BRmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                BRpbulletRadius, BRpmyBulletSpeed,
                //bullet Image
                BRpbulletImage,
                //Timers to display animations
                BRphittingAnimation,  BRpability1AnimationTimer,  BRpability2AnimationTimer,
                //Ability cooldown timers
                BRpabilityCooldown1,  BRpabilityCooldown2,
                //Character stats
                BRpmaxHP,  BRpbaseSpeed,  BRplevel,  BRpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                BRpmaxToHitTime,
                //Images for UI and their coordinates
                BRpHealthy, BRpHurt,
                1203, 645, 1203, 649,
                //Images for the ability icons
                BRpabilityImage1, BRpabilityImage2,
                //Image to be displayed when icy blast hits
                BRpicyBlast,
                //The timer for how long the animation should be displayed for
                BRpicyBlastDuration,
                //Snowball Image
                BRpsnowballImage,
                //Speed for the icy blast and radius
                BRpibSpeed, BRpibRadius, BRpsnowballRadius,
                //Timer for when the character is to shoot
                BRphittingFrequency,
                //The duration for being ranged
                BRprangedDuration
            ));
    }

    void addLeon(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Leon(
                //Movement images
                LeonpupMove, LeonpdownMove, LeonprightMove, LeonpleftMove,
                LeonpupLeftMove, LeonpupRightMove, LeonpdownLeftMove, LeonpdownRightMove,
                //Attacking images
                LeonpupAttack, LeonpdownAttack, LeonpleftAttack, LeonprightAttack,
                LeonpupLeftAttack, LeonpupRightAttack, LeonpdownLeftAttack, LeonpdownRightAttack,
                //Still images
                LeonpupStill, LeonpdownStill, LeonprightStill, LeonpleftStill,
                LeonpupLeftStill, LeonpupRightStill, LeonpdownLeftStill, LeonpdownRightStill,
                //Ability Images
                Leonpability1Left, Leonpability1Right, Leonpability2Left, Leonpability2Right,
                //Knocked out image
                LeonpkoImage,
                //A full 3D image used to show the character
                LeonpbaseImage,
                //Length and height of rectangle bounds
                LeonsideWidth,  LeonmidWidth,
                LeonsideHeight,  LeonmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                LeonpbulletRadius, LeonpmyBulletSpeed,
                //bullet Image
                LeonpbulletImage,
                //Timers to display animations
                LeonphittingAnimation,  Leonpability1AnimationTimer,  Leonpability2AnimationTimer,
                //Ability cooldown timers
                LeonpabilityCooldown1,  LeonpabilityCooldown2,
                //Character stats
                LeonpmaxHP,  LeonpbaseSpeed,  Leonplevel,  LeonpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                LeonpmaxToHitTime,
                //Images for UI and their coordinates
                LeonpHealthy, LeonpHurt,
                1208, 656, 1208, 656,
                //Images for the ability icons
                LeonpabilityImage1, LeonpabilityImage2,
                //The duration that the bullet follows the other character
                LeonpbulletDuration,
                //Duration of dot
                LeonpdotAmount,
                //Animation for the directed shot
                LeonpdirShot,
                //Animation for the enemy taking dot
                LeonpdotAnim
            ));
    }
    
    void addBob(int posX, int posY, boolean pisenemy, boolean pisProtagonist){
        chars.add(new Bob(
                //Movement images
                BobpupMove, BobpdownMove, BobprightMove, BobpleftMove,
                BobpupLeftMove, BobpupRightMove, BobpdownLeftMove, BobpdownRightMove,
                //Attacking images
                BobpupAttack, BobpdownAttack, BobpleftAttack, BobprightAttack,
                BobpupLeftAttack, BobpupRightAttack, BobpdownLeftAttack, BobpdownRightAttack,
                //Still images
                BobpupStill, BobpdownStill, BobprightStill, BobpleftStill,
                BobpupLeftStill, BobpupRightStill, BobpdownLeftStill, BobpdownRightStill,
                //Ability Images
                Bobpability1Left, Bobpability1Right, Bobpability2Left, Bobpability2Right,
                //Knocked out image
                BobpkoImage,
                //A full 3D image used to show the character
                BobpbaseImage,
                //Length and height of rectangle bounds
                BobsideWidth,  BobmidWidth,
                BobsideHeight,  BobmidHeight,
                //Enemy or ally
                pisenemy,
                //Protagonist or not
                pisProtagonist,
                //bullet radius
                BobpbulletRadius, BobpmyBulletSpeed,
                //bullet Image
                BobpbulletImage,
                //Timers to display animations
                BobphittingAnimation,  Bobpability1AnimationTimer,  Bobpability2AnimationTimer,
                //Ability cooldown timers
                BobpabilityCooldown1,  BobpabilityCooldown2,
                //Character stats
                BobpmaxHP,  BobpbaseSpeed,  Bobplevel,  BobpbaseDamage,
                //Sprite postitions
                posX,  posY,
                //The time to hit the character
                BobpmaxToHitTime,
                //Images for UI and their coordinates
                BobpHealthy, BobpHurt,
                1204, 643, 1208, 642,
                //Images for the ability icons
                BobpabilityImage1, BobpabilityImage2,
                //The duration for how long the bullet will travel before returning to the character
                BobptravelTime
            ));
    }
    
    void levelUpEnemies(){
        //Add 1 to level
        Soraplevel += 1;
        Rikuplevel += 1;
        SHplevel += 1;
        Cloudplevel += 1;
        Donaldplevel += 1;
        Goofyplevel += 1;
        BRplevel += 1;
        Leonplevel += 1;
        Bobplevel += 1;
        
        int hpIncrease = 30;
        
        //Increase HP of each enemy
        SorapmaxHP += hpIncrease*lvl.difficulty;
        RikupmaxHP += hpIncrease*lvl.difficulty;
        SHpmaxHP += hpIncrease*lvl.difficulty;
        CloudpmaxHP += hpIncrease*lvl.difficulty;
        DonaldpmaxHP += hpIncrease*lvl.difficulty;
        GoofypmaxHP += hpIncrease*lvl.difficulty;
        BRpmaxHP += hpIncrease*lvl.difficulty;
        LeonpmaxHP += hpIncrease*lvl.difficulty;
        BobpmaxHP += hpIncrease*lvl.difficulty;
        
        int damageIncrease = 5;
        
        //Increase damage of each enemy
        SorapbaseDamage += damageIncrease*lvl.difficulty;
        RikupbaseDamage += damageIncrease*lvl.difficulty;
        SHpbaseDamage += damageIncrease*lvl.difficulty;
        CloudpbaseDamage += damageIncrease*lvl.difficulty;
        DonaldpbaseDamage += damageIncrease*lvl.difficulty;
        GoofypbaseDamage += damageIncrease*lvl.difficulty;
        BRpbaseDamage += damageIncrease*lvl.difficulty;
        LeonpbaseDamage += damageIncrease*lvl.difficulty;
        BobpbaseDamage += damageIncrease*lvl.difficulty;
        
        //Decrease ability 1's cooldown of each enemy
        SorapabilityCooldown1 -= (int)(SorapabilityCooldown1*0.05f*lvl.difficulty);
        RikupabilityCooldown1 -= (int)(RikupabilityCooldown1*0.05f*lvl.difficulty);
        SHpabilityCooldown1 -= (int)(SHpabilityCooldown1*0.05f*lvl.difficulty);
        CloudpabilityCooldown1 -= (int)(CloudpabilityCooldown1*0.05f*lvl.difficulty);
        DonaldpabilityCooldown1 -= (int)(DonaldpabilityCooldown1*0.05f*lvl.difficulty);
        GoofypabilityCooldown1 -= (int)(GoofypabilityCooldown1*0.05f*lvl.difficulty);
        BRpabilityCooldown1 -= (int)(BRpabilityCooldown1*0.05f*lvl.difficulty);
        LeonpabilityCooldown1 -= (int)(LeonpabilityCooldown1*0.05f*lvl.difficulty);
        BobpabilityCooldown1 -= (int)(BobpabilityCooldown1*0.05f*lvl.difficulty);
        
        //Decrease ability 2's cooldown of each enemy
        SorapabilityCooldown2 -= (int)(SorapabilityCooldown2*0.05f*lvl.difficulty);
        RikupabilityCooldown2 -= (int)(RikupabilityCooldown2*0.05f*lvl.difficulty);
        SHpabilityCooldown2 -= (int)(SHpabilityCooldown2*0.05f*lvl.difficulty);
        CloudpabilityCooldown2 -= (int)(CloudpabilityCooldown2*0.05f*lvl.difficulty);
        DonaldpabilityCooldown2 -= (int)(DonaldpabilityCooldown2*0.05f*lvl.difficulty);
        GoofypabilityCooldown2 -= (int)(GoofypabilityCooldown2*0.05f*lvl.difficulty);
        BRpabilityCooldown2 -= (int)(BRpabilityCooldown2*0.05f*lvl.difficulty);
        LeonpabilityCooldown2 -= (int)(LeonpabilityCooldown2*0.05f*lvl.difficulty);
        BobpabilityCooldown2 -= (int)(BobpabilityCooldown2*0.05f*lvl.difficulty);
        
        //Decrease the time the enemies take to attack
        SorapmaxToHitTime -= (int)(SorapmaxToHitTime*0.02f*lvl.difficulty);
        RikupmaxToHitTime -= (int)(RikupmaxToHitTime*0.02f*lvl.difficulty);
        SHpmaxToHitTime -= (int)(SHpmaxToHitTime*0.02f*lvl.difficulty);
        CloudpmaxToHitTime -= (int)(CloudpmaxToHitTime*0.02f*lvl.difficulty);
        DonaldpmaxToHitTime -= (int)(DonaldpmaxToHitTime*0.02f*lvl.difficulty);
        GoofypmaxToHitTime -= (int)(GoofypmaxToHitTime*0.02f*lvl.difficulty);
        BRpmaxToHitTime -= (int)(BRpmaxToHitTime*0.02f*lvl.difficulty);
        LeonpmaxToHitTime -= (int)(LeonpmaxToHitTime*0.02f*lvl.difficulty);
        BobpmaxToHitTime -= (int)(BobpmaxToHitTime*0.02f*lvl.difficulty);
        
        //Special items level up which would make them overpowered if they were levelled up frequently
        int y = lvl.enemyFrequency*3;
        
        if(SorapbaseSpeed < 7){
            //Increase the speed of eah character
            //Increase the duration of the wind ability
            if(Soraplevel % y == 0){
                SorapbaseSpeed += 1;
                SorapwindDuration += (int)(SorapwindDuration*0.07*lvl.difficulty);
            }
            
            //Incease the number of bullets shot at a time
            if(Rikuplevel % y == 0){
                SorapbaseSpeed += 1;
                RikupbulletAmount +=2;
            }
            
            //Increase the duration of time spent invulnerable
            if(SHplevel % y == 0){
                SHpbaseSpeed += 1;
                SHpability2AnimationTimer += (int)(SHpability2AnimationTimer*0.07*lvl.difficulty);
            }
            
            //Increase the duratio of both the extra speed and extra damage time
            if(Cloudplevel % y == 0){
                CloudpbaseSpeed += 1;
                CloudpabilityDuration1 += (int)(CloudpabilityDuration1*0.07*lvl.difficulty);
                CloudpabilityDuration2 += (int)(CloudpabilityDuration2*0.07*lvl.difficulty);
            }
            
            //Increase the heal amount
            if(Donaldplevel % y == 0){
                DonaldpbaseSpeed += 1;
                DonaldphealToDamage +=lvl.difficulty;
            }
            
            //Increase the amount of time spent spinning
            if(Goofyplevel % y == 0){
                GoofypbaseSpeed += 1;
                Goofypability1AnimationTimer += (int)(Goofypability1AnimationTimer*0.05*lvl.difficulty);
            }
            
            //Increase the ranged duration
            if(BRplevel % y == 0){
                BRpbaseSpeed += 1;
                BRprangedDuration += (int)(BRprangedDuration*0.05*lvl.difficulty);
            }
            
            //Increase the duration of Leon's bullet chasing the enemy
            if(Leonplevel % y == 0){
                LeonpbaseSpeed += 1;
                LeonpbulletDuration += (int)(LeonpbulletDuration*0.08*lvl.difficulty);
            }
            
            //Increase the duration of Bob's bullet chasing the enemy
            if(Bobplevel % y == 0){
                BobpbaseSpeed += 1;
                BobptravelTime += (int)(BobptravelTime * 0.075 * lvl.difficulty);
            }
        }
    }
    //Exact opposite of previous
    void delevelUpEnemies(){
        //Ability cooldown timers
        SorapabilityCooldown1 = 6000; SorapabilityCooldown2 = 12000;
        //Character stats
        SorapmaxHP = 200; SorapbaseSpeed = 4; Soraplevel = 1; SorapbaseDamage = 30; SorapmaxToHitTime = 1200;
        //Timer for wind
        SorapwindDuration = 5000;
        
        //Ability cooldown timers
        RikupabilityCooldown1 = 3500; RikupabilityCooldown2 = 5750;
        //Character stats
        RikupmaxHP = 200; RikupbaseSpeed = 4; Rikuplevel = 1; RikupbaseDamage = 40; RikupmaxToHitTime = 1200;
        //number of bullets for ability
        RikupbulletAmount = 5;
        
        //Timers to display animations
        //Timer 2 is the duratio of the invulnerability
        SHphittingAnimation = 402; SHpability1AnimationTimer = 600; SHpability2AnimationTimer = 3500;
        //Ability cooldown timers
        SHpabilityCooldown1 = 3500; SHpabilityCooldown2 = 11000;
        //Character stats
        SHpmaxHP = 200; SHpbaseSpeed = 4; SHplevel = 1; SHpbaseDamage = 35; SHpmaxToHitTime = 1200;
        
        //Ability cooldown timers
        CloudpabilityCooldown1 = 12000; CloudpabilityCooldown2 = 15000;
        //Character stats
        CloudpmaxHP = 200; CloudpbaseSpeed = 3; Cloudplevel = 1; CloudpbaseDamage = 35; CloudpmaxToHitTime = 1200;
        //Duration for abilities
        CloudpabilityDuration1 = 4500; CloudpabilityDuration2 = 4500;
        
        //Ability cooldown timers
        DonaldpabilityCooldown1 = 4500; DonaldpabilityCooldown2 = 10000;
        //Character stats
        DonaldpmaxHP = 150; DonaldpbaseSpeed = 4; Donaldplevel = 1; DonaldpbaseDamage = 38; DonaldpmaxToHitTime = 1200;
        //The amount of times damage is multiplied to heal
        DonaldphealToDamage = 1;
        
        //Timers to display animations
        //timer 1 is the duration of the spin move
        GoofyphittingAnimation = 396; Goofypability1AnimationTimer = 5000; Goofypability2AnimationTimer = 400;
        //Ability cooldown timers
        GoofypabilityCooldown1 = 11500; GoofypabilityCooldown2 = 5000;
        //Character stats
        GoofypmaxHP = 270; GoofypbaseSpeed = 4; Goofyplevel = 1; GoofypbaseDamage = 40; GoofypmaxToHitTime = 1200;
        
        //Ability cooldown timers
        BRpabilityCooldown1 = 11000; BRpabilityCooldown2 = 6000;
        //Character stats
        BRpmaxHP = 200; BRpbaseSpeed = 4; BRplevel = 1; BRpbaseDamage = 35; BRpmaxToHitTime = 1200;
        //The duration for being ranged
        BRprangedDuration = 6000;
        
        //Ability cooldown timers
        LeonpabilityCooldown1 = 6000; LeonpabilityCooldown2 = 7000;
        //Character stats
        LeonpmaxHP = 200; LeonpbaseSpeed = 4; Leonplevel = 0; LeonpbaseDamage = 40; LeonpmaxToHitTime = 1200;
        //The duration that the bullet follows the other character
        LeonpbulletDuration = 1300;
        
        //Ability cooldown timers
        BobpabilityCooldown1 = 6000; BobpabilityCooldown2 = 7000;
        //Character stats
        BobpmaxHP = 200; BobpbaseSpeed = 4; Bobplevel = 0; BobpbaseDamage = 40; BobpmaxToHitTime = 1200;
        //The duration for how long the bullet will travel before returning to the character
        BobptravelTime = 1000;
    }
    
    //Level up a specific character and specific item, will be used for the protagonist and allies
    void levelUpCharmaxHP(Character myChar){
        myChar.maxHP += 200;
    }
    void levelUpCharbaseDamage(Character myChar){
        myChar.damage += 15;
    }
    void levelUpCharabilityCooldown1(Character myChar){
        myChar.abilityCooldown1 -= (int)(myChar.abilityCooldown1*0.15f);
    }
    void levelUpCharabilityCooldown2(Character myChar){
        myChar.abilityCooldown2 -= (int)(myChar.abilityCooldown2*0.15f);
    }
    void levelUpCharspeed(Character myChar){
        myChar.setBaseSpeed(myChar.getBaseSpeed()+1);
    }
    void levelUpAbility(Character myChar){
        if(myChar instanceof Sora){
            ((Sora)myChar).windDuration += (int)(((Sora)myChar).windDuration*0.21f);
        }else if(myChar instanceof Riku){
            ((Riku)myChar).bulletAmount +=2;
        }else if(myChar instanceof ShadowHeartless){
            ((ShadowHeartless)myChar).ability2AnimationTimer += (int)(((ShadowHeartless)myChar).ability2AnimationTimer*0.21f);
        }else if(myChar instanceof Cloud){
            ((Cloud)myChar).abilityDuration1 += (int)(((Cloud)myChar).abilityDuration1*0.21f);
            ((Cloud)myChar).abilityDuration2 += (int)(((Cloud)myChar).abilityDuration2*0.21f);
        }else if(myChar instanceof Donald){
            ((Donald)myChar).healToDamage += 3;
        }else if(myChar instanceof Goofy){
            ((Goofy)myChar).ability1AnimationTimer += (int)(((Goofy)myChar).ability1AnimationTimer*0.15f);
        }else if(myChar instanceof BlueRhapsody){
            ((BlueRhapsody)myChar).rangedDuration += (int)(((BlueRhapsody)myChar).rangedDuration*0.15f);
        }else if(myChar instanceof Leon){
            ((Leon)myChar).bulletDuration += (int)(((Leon)myChar).bulletDuration*0.24);
        }else if(myChar instanceof Bob){
            ((Bob)myChar).travelTime += (int)(((Bob)myChar).travelTime*0.15);
        }
    }
    
    //The method to be made in the beginning in case of a loaded gaem
    void levelUpAllies(){
        for(int i = 0; i < 3; i++){
            for(int i2 = 0; i2 < 6; i2++){
                switch(i2){
                    case 0:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpCharmaxHP(chars.get(i));
                        }
                    break;
                    case 1:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpCharbaseDamage(chars.get(i));
                        }
                    break;
                    case 2:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpCharabilityCooldown1(chars.get(i));
                        }
                    break;
                    case 3:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpCharabilityCooldown2(chars.get(i));
                        }
                    break;
                    case 4:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpCharspeed(chars.get(i));
                        }
                    break;
                    case 5:
                        for(int i3 = 0; i3 < lvl.lvlup.get(i)[i2];i3++){
                            levelUpAbility(chars.get(i));
                        }
                    break;
                }
            }
        }
    }
    
    void addAllies(){
        delevelUpEnemies();
        Character.noOfAllies = 0;
        //Add allies
        if(chars.size() <= 0){
            for(int i = 0; i < 3; i++){
                if(i == 0){
                    switch(lvl.a.get(i)){
                        case 1:
                            addSora(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 2:
                            addDonald(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 3:
                            addGoofy(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 4:
                            addLeon(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 5:
                            addCloud(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 6:
                            addRiku(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 7:
                            addSH(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 8:
                            addBR(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                        case 9:
                            addBob(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, true);
                        break;
                    }
                }else{
                    switch(lvl.a.get(i)){
                        case 1:
                            addSora(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 2:
                            addDonald(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 3:
                            addGoofy(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 4:
                            addLeon(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 5:
                            addCloud(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 6:
                            addRiku(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 7:
                            addSH(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 8:
                            addBR(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                        case 9:
                            addBob(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150), false, false);
                        break;
                    }
                }
            }
        }
        for(int i = 0; i < 3; i++){
            levelUpCharmaxHP(chars.get(i));
            levelUpCharbaseDamage(chars.get(i));
            levelUpCharspeed(chars.get(i));
        }
        int xyz = (int)(lvl.round/lvl.enemyFrequency);
        for(int u = 0; u < xyz; u++){
            levelUpEnemies();
        }
    }
    
    
    void moveProtagonist(){
        //Down Right
        if(keysDown.contains(KeyEvent.VK_S) && keysDown.contains(KeyEvent.VK_D)){
            chars.get(0).moveDownRight(getWidth(), getHeight()-60);
            //System.out.println("moveDownRight");
        }
        //Up Right
        else if(keysDown.contains(KeyEvent.VK_W) && keysDown.contains(KeyEvent.VK_D)){
            chars.get(0).moveUpRight(getWidth());
            //System.out.println("moveUpRight");
        }
        //Down Left
        else if(keysDown.contains(KeyEvent.VK_S) && keysDown.contains(KeyEvent.VK_A)){
            chars.get(0).moveDownLeft(getHeight()-60);
            //System.out.println("moveDownLeft");
        }
        //Up Left
        else if(keysDown.contains(KeyEvent.VK_W) && keysDown.contains(KeyEvent.VK_A)){
            chars.get(0).moveUpLeft();
            //System.out.println("moveUpLeft");
        }
        //Up
        else if(keysDown.contains(KeyEvent.VK_W)){
            chars.get(0).moveUp();
            //System.out.println("moveUp");
        }
        //Left
        else if(keysDown.contains(KeyEvent.VK_A)){
            chars.get(0).moveLeft();
            //System.out.println("moveLeft");
        }
        //Down
        else if(keysDown.contains(KeyEvent.VK_S)){
            chars.get(0).moveDown();
            //System.out.println("moveDown");
        }
        //Right
        else if(keysDown.contains(KeyEvent.VK_D)){
            chars.get(0).moveRight();
            //System.out.println("moveRight");
        }
    }
    
    //The method to switch between charaters
    void cycleChars(){
        chars.get(0).isProtagonist = false;
        
        int n = Character.noOfAllies;
        
        Character t;
        
        for(int i = 1; i < n; i++){
            t = chars.get(i);
            chars.set(i, chars.get(i-1));
            chars.set(i-1, t);
        }
        
        int u[] = lvl.lvlup.get(0);
        lvl.lvlup.set(0, lvl.lvlup.get(1));
        lvl.lvlup.set(1, lvl.lvlup.get(2));
        lvl.lvlup.set(2, u);
            
        int xyz = lvl.a.get(0);
        lvl.a.set(0, lvl.a.get(1));
        lvl.a.set(1, lvl.a.get(2));
        lvl.a.set(2, xyz);
        
        chars.get(0).isProtagonist = true;
        chars.get(0).movingTowardsChar = false;
        chars.get(0).movingAwayFromChar = false;
        chars.get(0).movingAroundChar = false;
        
        for(int i = 0; i < chars.size(); i++){
            if((chars.get(i) instanceof Leon)){
                if(((Leon)chars.get(i)).charNo == 0){
                    ((Leon)chars.get(i)).charNo = 2;
                }else if(((Leon)chars.get(i)).charNo == 1){
                    ((Leon)chars.get(i)).charNo = 0;
                }else if(((Leon)chars.get(i)).charNo == 2){
                    ((Leon)chars.get(i)).charNo = 1;
                }
                for(int x = 0; x < ((Leon)chars.get(i)).dotChar.size(); x++){
                    if(((Leon)chars.get(i)).dotChar.get(x) == 0){
                        ((Leon)chars.get(i)).dotChar.set(x, 2);
                    }else if(((Leon)chars.get(i)).dotChar.get(x) == 1){
                        ((Leon)chars.get(i)).dotChar.set(x, 0);
                    }else if(((Leon)chars.get(i)).dotChar.get(x) == 2){
                        ((Leon)chars.get(i)).dotChar.set(x, 1);
                    }
                }
            }
        }
    }
    //Method for when game is over
    void checkGameOver(){
        if(chars.size() > 0){
            boolean go = false;
            if(chars.get(0).hp <= 0 && chars.get(1).hp <= 0 && chars.get(2).hp <= 0){
                state = 12;
                Character.noOfEnemies = 0;
                kos.showButtons(hijk);
                go = true;
            }else if(Character.noOfEnemies == 0 || chars.size() <= 3){
                Character.noOfEnemies = 0;
                if(lvl.round != lvl.maxRound){
                    state = 11;
                    loadhijk();
                    lus.showAll(1344, 720, hijk);
                    for(int i = 0; i < 3; i++){
                        for(int x = 0; x < lus.btn.get(i).length; x++){
                            add(lus.btn.get(i)[x]);
                            lus.btn.get(i)[x].addActionListener(this);
                        }
                    }
                    for(int i = 0; i < 3; i++){
                        if(chars.get(i).getBaseSpeed() >= 7){
                            lus.btn.get(i)[4].setEnabled(false);
                        }
                    }
                    lvl.round++;
                    delevelUpEnemies();
                    for(int i = 0; i <= lvl.round; i++){
                        if(i%lvl.enemyFrequency == 0){
                            levelUpEnemies();
                        }
                    }
                }else{
                    state = 14;
                    background = tk.createImage("End Background.png");
                    rf.exit.setVisible(true);
                }
                go = true;
            }
            if(go == true){
                pause = true;
                chars.clear();
                addAllies();
                levelUpAllies();
                btnCanPlaySound.setVisible(true);
                lblCanPlaySound.setVisible(true);
                btnCanPlaySFX.setVisible(true);
                lblCanPlaySFX.setVisible(true);
            }
        }
    }
    
    //Method
    void initialiseObjects(){
        //Set up backgrounds
        backgroundsArray[0] = tk.createImage("CelestialRed.jpg");
        backgroundsArray[1] = tk.createImage("CelestialGreen.jpg");
        backgroundsArray[2] = tk.createImage("SandyBackgroundBlue.jpg");
        backgroundsArray[3] = tk.createImage("SandyBackground.jpg");
        backgroundsArray[4] = tk.createImage("CrownBackground.jpg");
        backgroundsArray[5] = tk.createImage("HeartBackground.jpg");
        
        
        
        //Add an enemy of each type in the sharsStore
        addSora(0,0, true, true);
        addDonald(0,0, true, true);
        addGoofy(0,0, true, true);
        addLeon(0,0, true, true);
        addCloud(0,0, true, true);
        addRiku(0,0, true, true);
        addSH(0,0, true, true);
        addBR(0,0, true, true);
        addBob(0,0, true, true);
        Character.noOfEnemies = 0;
        charsStore = new ArrayList<Character>(chars);
        chars.clear();
        
        Character.chooseSound.add("SoraChooseSound.wav");
        Character.chooseSound.add("DonaldChooseSound.wav");
        Character.chooseSound.add("GoofyChooseSound.wav");
        Character.chooseSound.add("LeonChooseSound.wav");
        Character.chooseSound.add("CloudChooseSound.wav");
        Character.chooseSound.add("RikuChooseSound.wav");
        Character.chooseSound.add("ShadowHeartlessChooseSound.wav");
        Character.chooseSound.add("BlueRhapsodyChooseSound.wav");
        Character.chooseSound.add("BobChooseSound.wav");
        
        Character.deathSound.add("SoraDeathSound.wav");
        Character.deathSound.add("DonaldDeathSound.wav");
        Character.deathSound.add("GoofyDeathSound.wav");
        Character.deathSound.add("LeonDeathSound.wav");
        Character.deathSound.add("CloudDeathSound.wav");
        Character.deathSound.add("RikuDeathSound.wav");
        Character.deathSound.add("ShadowHeartlessDeathSound.wav");
        Character.deathSound.add("BlueRhapsodyDeathSound.wav");
        Character.deathSound.add("BobDeathSound.wav");
        
        Character.specialSound.add("Sora, Donald, Goofy.wav");
        Character.specialSound.add("Come on Sora I thought you were stronger than that.wav");
        Character.specialSound.add("What, you're fighting too.wav");
        Character.specialSound.add("Behold the power of darkness.wav");
        
        ss.maxDuration = 129000;
        
        //Add main menu buttons
        for(int i = 0; i < m.mm.btn.size(); i++){
            add(m.mm.btn.get(i));
            m.mm.btn.get(i).addActionListener(this);
        }
        for(int i = 0; i < m.ds.btn.size(); i++){
            add(m.ds.btn.get(i));
            m.ds.btn.get(i).addActionListener(this);
        }
        add(m.ds.btnBack);
        m.ds.btnBack.addActionListener(this);
        for(int i = 0; i < m.nl.btn.size(); i++){
            add(m.nl.btn.get(i));
            m.nl.btn.get(i).addActionListener(this);
        }
        add(m.nl.btnBack);
        m.nl.btnBack.addActionListener(this);
        
            //Add level creator buttons
        lvlc = new LevelCreator(charsStore, 1344, 720, 9, "Custom Levels.txt");
        add(lvlc.btnBack);
        lvlc.btnBack.addActionListener(this);
        add(lvlc.btnNext);
        lvlc.btnNext.addActionListener(this);
        add(lvlc.txtName);
        add(lvlc.txtEnemyLvlUp);
        add(lvlc.btnUp);
        lvlc.btnUp.addActionListener(this);
        add(lvlc.btnDown);
        lvlc.btnDown.addActionListener(this);
        for(int i = 0; i < lvlc.charName.size(); i++){
            add(lvlc.charName.get(i));
            add(lvlc.txtCharNo.get(i));
        }
        add(lvlc.delete);
        lvlc.delete.addActionListener(this);
        add(lvlc.reset);
        lvlc.reset.addActionListener(this);
        add(lvlc.finish);
        lvlc.finish.addActionListener(this);
        add(lvlc.txtRound);
        add(lvlc.go);
        lvlc.go.addActionListener(this);
        
            //Add level selection items
        ls = new LevelSelection(1344, 720, 9, "Custom Levels.txt");
        add(ls.back);
        ls.back.addActionListener(this);
        add(ls.up);
        ls.up.addActionListener(this);
        add(ls.down);
        ls.down.addActionListener(this);
        add(ls.txtPage);
        add(ls.go);
        ls.go.addActionListener(this);
        
            //Add character selection items
        cs = new CharacterSelection(1344, 720, 9, charsStore, 3);
        add(cs.back);
        cs.back.addActionListener(this);
        add(cs.next);
        cs.next.addActionListener(this);
        add(cs.up);
        cs.up.addActionListener(this);
        add(cs.down);
        cs.down.addActionListener(this);
        for(int i = 0; i < cs.lbl.size(); i++){
            add(cs.lbl.get(i));
            add(cs.select.get(i));
            cs.select.get(i).addActionListener(this);
            add(cs.deselect.get(i));
            cs.deselect.get(i).addActionListener(this);
        }
        add(cs.txtPage);
        add(cs.go);
        cs.go.addActionListener(this);
        
            //Add Level items
        add(lvl.txtName);
        add(lvl.back);
        lvl.back.addActionListener(this);
        add(lvl.next);
        lvl.next.addActionListener(this);
        
            //Add user selection items
        us = new UserSelection(1344, 720, 9, "Main Level Users.txt", "Custom Level Users.txt");
        add(us.back);
        us.back.addActionListener(this);
        add(us.up);
        us.up.addActionListener(this);
        add(us.down);
        us.down.addActionListener(this);
        add(us.txtPage);
        add(us.go);
        us.go.addActionListener(this);
        
            //Add round finish objects
        rf = new RoundFinish(1344, 720);
        add(rf.exit);
        rf.exit.addActionListener(this);
        add(rf.start);
        rf.start.addActionListener(this);
        rf.hideButtons();
        
            //Add LevelUpScreen objects
        lus = new LevelUpScreen();
        
            //Add KOScreen items
        kos = new KOScreen(1344, 720);
        add(kos.cont);
        kos.cont.addActionListener(this);
        add(kos.exit);
        kos.exit.addActionListener(this);
        
            //Add pasue screen items
        ps = new PauseScreen(1344, 720);
        add(ps.cont);
        ps.cont.addActionListener(this);
        add(ps.exit);
        ps.exit.addActionListener(this);
        
            //Add SlideShow instructions' items
        instructions = new SlideShow(1344, 720, new Image[]{
            tk.createImage("Instruction1.jpg"),
            tk.createImage("Instruction2.jpg"),
            tk.createImage("Instruction3.jpg"),
            tk.createImage("Instruction4.jpg"),
            tk.createImage("Instruction5.jpg"),
            tk.createImage("Instruction6.jpg"),
            tk.createImage("Instruction7.jpg"),
            tk.createImage("Instruction8.jpg"),
            tk.createImage("Instruction9.jpg"),
            tk.createImage("Instruction10.jpg"),
            tk.createImage("Instruction11.jpg"),
            tk.createImage("Instruction12.jpg"),
            tk.createImage("Instruction13.jpg"),
            tk.createImage("Instruction14.jpg")
        });
        add(instructions.btnPrevious);
        instructions.btnPrevious.setVisible(false);
        instructions.btnPrevious.addActionListener(this);
        add(instructions.btnNext);
        instructions.btnNext.setVisible(false);
        instructions.btnNext.addActionListener(this);
        add(instructions.btnBack);
        instructions.btnBack.setVisible(false);
        instructions.btnBack.addActionListener(this);
        
        //Add SlideShow credits' items
        credits = new SlideShow(1344, 720, new Image[]{
            tk.createImage("Credit1.jpg"),
            tk.createImage("Credit2.jpg"),
            tk.createImage("Credit3.jpg"),
            tk.createImage("Credit4.jpg"),
            tk.createImage("Credit5.jpg"),
            tk.createImage("Credit6.jpg"),
            tk.createImage("Credit7.jpg")
        });
        add(credits.btnPrevious);
        credits.btnPrevious.setVisible(false);
        credits.btnPrevious.addActionListener(this);
        add(credits.btnNext);
        credits.btnNext.setVisible(false);
        credits.btnNext.addActionListener(this);
        add(credits.btnBack);
        credits.btnBack.setVisible(false);
        credits.btnBack.addActionListener(this);
        
            //Add this class' items
        btnCanPlaySound.setBounds(1344-100, 25, 70, 30);
        add(btnCanPlaySound);
        btnCanPlaySound.addActionListener(this);
        
        lblCanPlaySound.setBounds(1344-30, 25, 30, 30);
        add(lblCanPlaySound);
        
        btnCanPlaySFX.setBounds(1344-200, 25, 70, 30);
        add(btnCanPlaySFX);
        btnCanPlaySFX.addActionListener(this);
        
        lblCanPlaySFX.setBounds(1344-130, 25, 30, 30);
        add(lblCanPlaySFX);
        
        
        slow.setBounds(445,555, 120, 30);
        add(slow);
        slow.addActionListener(this);
        slow.setVisible(false);
        
        moderate.setBounds(567,555, 120, 30);
        add(moderate);
        moderate.addActionListener(this);
        moderate.setVisible(false);
        
        fast.setBounds(689,555, 120, 30);
        add(fast);
        fast.addActionListener(this);
        fast.setVisible(false);
        fast.setEnabled(false);
    }
    
    void loadhijk(){
        hijk.clear();
        hijk = (ArrayList<Character>)chars.clone();
        for(int i = 0; hijk.size() > 3; i++){
            hijk.remove(3);
        }
    }
    
    int randomNo(int min, int max){
        int i = (max-min) + 1;
        return (int)(min + Math.random() * i);
    }
    
    void resetAllyPositions(){
        for(int i = 0; i < 3; i ++){
            chars.get(i).c.setPoints(randomNo(3, getWidth()/2), randomNo(25, getHeight()-150));
            chars.get(i).resetBounds();
            chars.get(i).hp = chars.get(i).maxHP;
            chars.get(i).abilityTimer1 = chars.get(i).abilityCooldown1;
            chars.get(i).abilityTimer2 = chars.get(i).abilityCooldown2;
            chars.get(i).bullet.clear();
        }
        Character.lstHit.clear();
        Character.lstAmount.clear();
    }
    
    public KHR(){
        chars.clear();
        setLayout(null);
        setTitle("Kingdom Hearts Rumble");
        setIconImage(tk.createImage("Sora's crown.png"));
        
        keysDown = new ArrayList<Integer>();
        keysUp = new ArrayList<Integer>();
        //These are done in order to prevent bugs
        //moving
        keysUp.add(87);//w
        keysUp.add(65);//a
        keysUp.add(83);//s
        keysUp.add(68);//d

        /*addSora(100, 50, false, true);
        addLeon(200, 50, true, false);
        addGoofy(400, 50, true, false);
        addCloud(400, 50, true, false);
        addSH(500, 50, true, false);
        addBR(600, 50, true, false);
        addDonald(700, 50, true, false);
        addRiku(700, 50, true, false);
        addBob(700, 50, true, false)*/
        
        //Set up the static variables
        Character.damagedImage = pdamagedImage;
        Character.revivalTime = 10000;
        Character.noOfEnemies = 0;
        Character.noOfAllies = 0;
        ArrayList<String> s = new ArrayList<String>();
        s.add("Hit1.wav"); s.add("Hit2.wav"); s.add("Hit3.wav"); s.add("Hit4.wav"); 
        Character.hitSounds = s;
        
        animeThread = new Thread(this);
        animeThread.start();
        
        state = 0;
        
        //Set the menu buttons
        initialiseObjects();
        
        animeThread = new Thread(this);
        animeThread.start();

        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        addMouseListener(this);
    }

    public void update(Graphics g){
        paint(g);
        if(state == 7 && isPaused == false){
            moveProtagonist();
        }
    }
    
    public void paint(Graphics g){
        //The double buffering is made and the paint is renamed to paint component
        dbi = createImage(getWidth(), getHeight());
        dbg = dbi.getGraphics();
        paintComponent(dbg);
        //The double bufering is drawn on screen
        g.drawImage(dbi,0,0,this);
    }

    public void paintComponent(Graphics g){
        if(isPainting == false){
            isPainting = true;
            try{
                //The background is always drawn first thin
                g.drawImage(background,0,25,null);
                m.paint(g);
                if(state == 2){
                    ls.paint(g);
                }else if(state == 5){
                    cs.paint(g);
                }else if(state == 6){
                    lvl.paint(g, getWidth(), getHeight());
                }else if(state == 7){
                    //Drawing all the characters on screen
                    for(int i = chars.size()-1; i >= 0; i--){
                        chars.get(i).paint(g);
                        chars.get(i).paint(g, chars);
                    }
                    drawUI(g);
                }else if(state == 8){
                    lvlc.paint(g);
                }else if(state == 9){
                    us.paint(g);
                }else if(state == 10){
                    g.setFont(new Font("Serif", Font.BOLD, 50));
                    rf.paint(g, charsStore, lvl.a, 1344, 720, lvl.round, lvl.maxRound);
                }else if(state == 11){
                    lus.paint(g);
                }else if(state == 12){
                    kos.paint(g, 1344);
                }else if(state == 13){
                    g.setFont(new Font("Serif", Font.BOLD, 50));
                    ps.paint(g);
                    g.drawString("Game Speed:", 145, 580);
                }else if(state == 15){
                    g.drawImage(instructions.background, 0, 25, null);
                    if(instructions.imgCounter != 0){
                        background = null;
                    }
                }else if(state == 16){
                    g.drawImage(credits.background, 0, 25, null);
                    if(credits.imgCounter != 0){
                        background = null;
                    }
                }
            }catch(Exception e){}
            isPainting = false;
        }
    }
    
    public void drawUI(Graphics g){
        //Draw theimage that appears behind the UI
        g.drawImage(UIBackground, -229, 657, null);
        //Draw the rectangles where the ability images will be displayed
        g.setColor(Color.BLACK);
        g.fillRect(118, 664, 46, 46);
        g.fillRect(118 + 46, 664, 46, 46);
        //Draw HealthBar outer layer
        g.drawRect(914, 676, 288, 26);
        //Draw the oval whereto putthe character image
        g.drawOval(1202, 660, 54, 54);
        g.drawRect(1256, 665, 41, 45);
        //draw the level of thechracter
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString(chars.get(0).level + "", 1260, 696);
        //draw healthbar inner layer
        g.setColor(new Color((int)((1 - chars.get(0).hp*1.0/chars.get(0).maxHP*1.0) * 255.0), (int)(chars.get(0).hp*1.0/chars.get(0).maxHP*1.0*255.0), 0));
        g.fillOval(1202, 660, 54, 54);
        g.fillRect((int)(915.0 + (287.0*(1.0 - chars.get(0).hp*1.0/chars.get(0).maxHP*1.0))), 677, 1202 - (int)(915.0 + (287.0*(1.0 - chars.get(0).hp*1.0/chars.get(0).maxHP*1.0))), 25);
        //915 + 287 = 1202
        //Draw theimage that appears behind the UI
        if(chars.get(0).hp*1.0/chars.get(0).maxHP*1.0 == 0 || Timer.time - chars.get(0).damagedTimer < 50){
            g.drawImage(chars.get(0).hurtImage, chars.get(0).hurtImagex, chars.get(0).hurtImagey, null);
        }else{
            g.drawImage(chars.get(0).healthyImage, chars.get(0).healthyImagex, chars.get(0).healthyImagey, null);
        }
        //Draw the ability images
        g.drawImage(chars.get(0).abilityImage1, 119, 665, 44, 44, null);
        g.drawImage(chars.get(0).abilityImage2, 119+46, 665, 44, 44, null);
        //Draw the ablity cooldown timers
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        if(Timer.time - chars.get(0).abilityTimer1 < chars.get(0).abilityCooldown1){
            g.drawString("" + (int)(((chars.get(0).abilityCooldown1*1.0 - (Timer.time*1.0 - chars.get(0).abilityTimer1*1.0))/1000.0)+1), 127, 696);
        }
        if(Timer.time - chars.get(0).abilityTimer2 < chars.get(0).abilityCooldown2){
            g.drawString("" + (int)(((chars.get(0).abilityCooldown2*1.0 - (Timer.time*1.0 - chars.get(0).abilityTimer2*1.0))/1000.0)+1), 127+46, 696);
        }
        //Draw the button needed to be pressed to activate an ability
        g.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 15));
        g.drawString("Q", 151, 705);
        g.drawString("E", 151+46, 708);
    }
    
    public void run(){
        while(true){
            if(isRunning == false){
                isRunning = true;
                repaint();
                try{
                    Thread.sleep(5);
                }catch(Exception e){
                }
                Timer.updateTimer();
                if(state == 0){
                    //setting the 1st image to be the background
                    background = tk.createImage("KHR Opening.jpg");
                    long x = Timer.time;
                    while(Timer.time - x < 4000){
                        Timer.updateTimer();
                        repaint();
                        try{
                            Thread.sleep(400);
                        }catch(Exception e){
                        }
                    }
                    state = 1;
                    
                    background = backgroundsArray[(int)(Math.random() * backgroundsArray.length)];
                    
                    m.showMainMenu();
                }
                //If in the game state
                else if(state == 7){
                    if(System.currentTimeMillis() >= threadSleepTimer){
                        threadSleepTimer = System.currentTimeMillis() + threadSleepTime;
                        ss.checkForLoop(canPlaySound);
                        if(pause == true){
                            isPaused = true;
                            long delay = System.currentTimeMillis();
                            while(System.currentTimeMillis() - delay < 1500){ repaint(); Timer.updateTimer(); }
                            
                            pause = false;
                            isPaused = false;
                        }
                        try{
                            //moveProtagonist();
                            ArrayList<Integer> toRemove = new ArrayList<Integer>();
                            //Move the characters
                            for(int i = 0; i < chars.size(); i++){
                                chars.get(i).resetBounds();
                                chars.get(i).move(chars, getWidth(), getHeight()-60);
                                chars.get(i).move(chars);
                            }
                            if(isRemovinglstHit == false){
                                isRemovinglstHit = true;
                                for(int i = 0; i < Character.lstAmount.size(); i++){
                                    //X is made to equal the character number of the current Hit
                                    int y = Character.lstAmount.get(i);
                                    int x = Character.lstHit.get(i);
                                    if(Character.lstAmount.get(i) < 0){
                                        //If he is healed, revive him and make sure his hp does not exceed his maximum hp
                                        if(chars.get(x).hp <= 0){
                                            chars.get(x).revivalTimer = Timer.time - Character.revivalTime;
                                            chars.get(x).setImage(chars.get(x).downStill);
                                        }
                                    }
                                    chars.get(x).takeDamage(Character.lstAmount.get(i), lvl.difficulty, canPlaySFX);
                                    if(chars.get(x).hp > chars.get(x).maxHP){
                                        chars.get(x).hp = chars.get(x).maxHP;
                                    }
                                    //If he is an enemy, remove him from screen
                                    //If he is an ally, let him stay on screen to be revived later on
                                    if(chars.get(x).hp <= 0){
                                        if(chars.get(x).isEnemy){
                                            y = toRemove.indexOf(x);
                                            if(y == -1){
                                                toRemove.add(x);
                                            }
                                        }else{
                                            chars.get(x).hp = 0;
                                            chars.get(x).revivalTimer = Timer.time;
                                            int randomNo = (int)(Math.random() * 100);
                                            if(randomNo > 30){
                                                Sound.playSound(Character.deathSound.get(lvl.a.get(x)-1), canPlaySFX);
                                            }
                                        }
                                    }
                                }
                                //Remove the damage holders
                                Character.lstHit.clear();
                                Character.lstAmount.clear();
                                isRemovinglstHit = false;
                            }
                            //Sort toRemove smallest to largest
                            for(int x = 0; x < toRemove.size(); x++){
                                for(int j = 0; j > toRemove.size() - 1; j++){
                                    if(toRemove.get(j) < toRemove.get(j+1)){
                                        int t = toRemove.get(j);
                                        toRemove.set(j, toRemove.get(j+1));
                                        toRemove.set(j+1, t);
                                    }
                                }
                            }
                            for(int i = 0; i < toRemove.size(); i++){
                                //k is the number of the character to be removed
                                int k = toRemove.get(i);
                                //Check all the characters then checkall Leons for this k
                                for(int x = 0; x < chars.size(); x++){
                                    //If the character is an instance of Leon check for possible things to remove
                                    if((chars.get(x) instanceof Leon)){
                                        //Reduce all the other's who are bigger than the dot itself by one
                                        for(int y = 0; y < ((Leon)chars.get(x)).dotChar.size(); y++){
                                            if(((Leon)chars.get(x)).dotChar.get(y) > k){
                                                ((Leon)chars.get(x)).dotChar.set(y, ((Leon)chars.get(x)).dotChar.get(y) - 1);
                                            }
                                            if(toRemove.get(i) == ((Leon)chars.get(x)).charNo){
                                                chars.get(x).bullet.clear();
                                            }else{
                                                if(toRemove.get(i) < ((Leon)chars.get(x)).charNo){
                                                    ((Leon)chars.get(x)).charNo--;
                                                }
                                            }
                                            //If the dot is one of Leons, remove it
                                            if(((Leon)chars.get(x)).dotChar.contains(k)){
                                                int h = ((Leon)chars.get(x)).dotChar.indexOf(k);
                                                ((Leon)chars.get(x)).dotCharLeft.remove(h);
                                                ((Leon)chars.get(x)).dotChar.remove(h);
                                                
                                                for(int z = 0; z < ((Leon)chars.get(x)).dotChar.size(); z++){
                                                    if(((Leon)chars.get(x)).dotChar.get(i) > k){
                                                        ((Leon)chars.get(x)).dotChar.set(i, ((Leon)chars.get(x)).dotChar.get(i)-1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                Character.noOfEnemies--;
                                toRemove.remove(i);
                                i--;
                                if(chars.size() > k){
                                    chars.remove(k);
                                }
                                //Reduce the Index of to Remove
                                for(int c = 0; c < toRemove.size(); c++){
                                    toRemove.set(c, toRemove.get(c) - 1);
                                }
                            }
                            checkGameOver();
                        }catch(Exception e){
                            System.err.println(e);
                            try{
                                for(int i = 0; i < chars.size(); i++){
                                    //In case of an error clear the bullets
                                    chars.get(i).bullet.clear();
                                }
                                //Remove the damage holders
                                Character.lstHit.clear();
                                Character.lstAmount.clear();
                                
                                for(int i = 0; i < chars.size(); i++){
                                    if(chars.get(i).isEnemy && chars.get(i).hp <= 0){
                                        chars.remove(i);
                                        i--;
                                    }else if(!chars.get(i).isEnemy && chars.get(i).hp <= 0){
                                        chars.get(i).hp = 0;
                                    }
                                }
                                
                                /*reset allies
                                int t[] = new int[9];
                                int y = 0;
                                for(int i2 = 0; i2 < 3; i2++){
                                    t[y] = Character.hp;y++;
                                    t[y] = Character.c.getX();y++;
                                    t[y] = Character.c.getY();y++;
                                    chars.remove(0);
                                }
                                
                                ArrayList<Character> chars2 = (ArrayList<Character>)chars.clone();
                                
                                chars.clear();
                                
                                delevelUpEnemies();
                                addAllies();
                                levelUpAllies();
                                
                                int xyz = (int)(lvl.round/lvl.enemyFrequency);
                                for(int u = 0; u < xyz; u++){
                                    levelUpEnemies();
                                }
                                
                                y = 0;
                                for(int i2 = 0; i2 < 3; i2++){
                                    chars.get(i2).hp = t[y];y++;
                                    chars.get(i2).c.setX(t[y]);y++;
                                    chars.get(i2).c.setY(t[y]);y++;
                                }
                                for(int i2 = 0; i2 < chars2.size(); i2++){
                                    chars.add(chars2.get(i2));
                                    System.out.println(chars2.get(i2).name);
                                }*/
                            }catch(Exception ex){}
                        }
                    }
                }else if(state >= 10 && state <= 14){
                    ss.checkForLoop(canPlaySound);
                }
                isRunning = false;
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        boolean canPlayButtonSound = true;
        boolean canPlayActualButtonSound = true;
            //Main Menu
        //If user presses 'main level'
        if(e.getSource() == m.mm.btn.get(0)){
            //go back to New/Load
            state = 3;
            lvl.custom = false;
            try{
                BufferedReader fr = new BufferedReader(new FileReader("Main Level.txt"));
                //Read name of level
                lvl.levelName = fr.readLine();
                //Read enemy level up
                lvl.enemyFrequency = Integer.parseInt(fr.readLine());
                //w is the amount of rounds
                int w = Integer.parseInt(fr.readLine());
                lvl.maxRound = w;
                lvl.c.clear();
                for(int i2 = 0; i2 < w; i2++){
                    //Read amount of different enemies
                    int u = Integer.parseInt(fr.readLine());
                    lvl.c.add(new int[u]);
                    for(int i3 = 0; i3 < u; i3++){
                        //Read enemy number
                        lvl.c.get(i2)[i3] = Integer.parseInt(fr.readLine());
                    }
                }
                fr.close();
            }catch(Exception ex){}
        }
        //If user presses 'custom level'
        else if(e.getSource() == m.mm.btn.get(1)){
            //Go to select a level
            state = 2;
            ls.showPage1();
            lvl.custom = true;
            //Add the labels and buttons
            for(int i = 0; i < ls.lbl.size(); i++){
                add(ls.lbl.get(i));
                add(ls.select.get(i));
                ls.select.get(i).addActionListener(this);
                add(ls.delete.get(i));
                ls.delete.get(i).addActionListener(this);
            }
        }
        //If user presses 'level creator'
        else if(e.getSource() == m.mm.btn.get(2)){
            lvlc.showState1();
            state = 8;
        }
        //If user chooses Instructions
        else if(e.getSource() == m.mm.btn.get(3)){
            instructions.showItems();
            state = 15;
            background = instructions.img[0];
        }
        //If user chooses to view credits
        else if(e.getSource() == m.mm.btn.get(4)){
            credits.showItems();
            state = 16;
            background = credits.img[0];
        }
        
        
            //Level Selection
        else if(e.getSource() == ls.back){
            state = 1;
            m.showMainMenu();
        }
        
        
            //New/Load
        //If user chooses to go to a new game
        else if(e.getSource() == m.nl.btn.get(0)){
            //go to difficulty Selection
            state = 4;
        }
        //If user chooses to load a game
        else if(e.getSource() == m.nl.btn.get(1)){
            us.showPage1(lvl.custom, lvl.levelNo);
            for(int i = 0; i < us.lbl.size(); i++){
                add(us.lbl.get(i));
                add(us.select.get(i));
                us.select.get(i).addActionListener(this);
                add(us.delete.get(i));
                us.delete.get(i).addActionListener(this);
            }
            state = 9;
        }
        //If user chooses to go back to the previous state
        else if(e.getSource() == m.nl.btnBack){
            if(lvl.custom == false){
                //go back to New/Load
                state = 1;
                m.showMainMenu();
            }else{
                //Go to select a level
                state = 2;
                ls.showPage1();
                //Add the labels and buttons
                for(int i = 0; i < ls.lbl.size(); i++){
                    add(ls.lbl.get(i));
                    add(ls.select.get(i));
                    ls.select.get(i).addActionListener(this);
                    add(ls.delete.get(i));
                    ls.delete.get(i).addActionListener(this);
                }
            }
        }
        
        
            //Level Creator
        else if((e.getSource() == lvlc.btnBack && lvlc.state == 0)){
            state = 1;
            m.showMainMenu();
        }
        
        
            //Difficulty Selection
        //If user chooses to pick:
        //beginner
        else if(e.getSource() == m.ds.btn.get(0)){
            lvl.difficulty = 1;
            cs.chars.clear();
            cs.showPage1();
            state = 5;
        } 
        //standard
        else if(e.getSource() == m.ds.btn.get(1)){
            lvl.difficulty = 2;
            cs.chars.clear();
            cs.showPage1();
            state = 5;
        } 
        //proud
        else if(e.getSource() == m.ds.btn.get(2)){
            lvl.difficulty = 3;
            cs.chars.clear();
            cs.showPage1();
            state = 5;
        }
        
        
            //Character Selection items
        //If user decides to go back to selecting a difficulty
        else if(e.getSource() == cs.back){
            m.showDifficultySelection();
            state = 3;
            Character.noOfEnemies = 0;
        }else if(e.getSource() == cs.next){
            if(cs.chars.size() == cs.maxChars){
                state = 6;
                lvl.showItems();
                lvl.a = (ArrayList<Integer>)cs.chars.clone();
                if(lvl.a.contains(1) && lvl.a.contains(1) && lvl.a.contains(3)){
                    Sound.stopSound();
                    Sound.playSound(Character.specialSound.get(0), canPlaySFX);
                }else if(lvl.a.contains(1) && lvl.a.contains(6)){
                    Sound.stopSound();
                    Sound.playSound(Character.specialSound.get(1), canPlaySFX);
                }else if(lvl.a.contains(4) && lvl.a.contains(5)){
                    Sound.stopSound();
                    Sound.playSound(Character.specialSound.get(2), canPlaySFX);
                }else if(lvl.a.contains(6) && lvl.a.contains(7) && lvl.a.contains(8)){
                    Sound.stopSound();
                    Sound.playSound(Character.specialSound.get(3), canPlaySFX);
                }
                addAllies();
            }
        }
        
        
            //User selection items
        else if(e.getSource() == us.back){
            state = 3;
            m.showNewLoad();
        }
        
        
            //Pause screen item
        else if(e.getSource() == ps.cont){
            btnCanPlaySound.setVisible(false);
            lblCanPlaySound.setVisible(false);
            btnCanPlaySFX.setVisible(false);
            lblCanPlaySFX.setVisible(false);
            slow.setVisible(false);
            moderate.setVisible(false);
            fast.setVisible(false);
            Timer.continueTimer();
            ps.hideAll();
            state = 7;
        }
        
            //Level items(User name entering)
        if(e.getSource() == lvl.back){
            cs.chars.clear();
            state = 5;
            cs.showPage1();
        }else if(e.getSource() == lvl.next){
            if(lvl.custom == false){
                try{
                    BufferedReader f = new BufferedReader(new FileReader("Main Level Users.txt"));
                    try{
                        lvl.userNo = Integer.parseInt(f.readLine()) + 1;
                    }catch(Exception ex){
                        lvl.userNo = 1;
                    }
                    f.close();
                }catch(Exception exc){}
            }else{
                try{
                    BufferedReader f = new BufferedReader(new FileReader("Custom Level Users.txt"));
                    //Read no of levels
                    int xyz;
                    try{
                        xyz = Integer.parseInt(f.readLine());
                    }catch(Exception ex){
                        xyz = 0;
                    }
                    for(int i = 0; i < xyz; i++){
                        int z;
                        if(lvl.levelNo == i){
                            try{
                                lvl.userNo = Integer.parseInt(f.readLine()) + 1;
                            }catch(Exception ex){
                                lvl.userNo = 1;
                            }
                            z = lvl.userNo - 1;
                        }else{
                            try{
                                z = Integer.parseInt(f.readLine());
                            }catch(Exception ex){
                                z = 0;
                            }
                        }
                        
                        //Read user records
                        for(int i2 = 0; i2 < z; i2++){
                            //read user name
                            f.readLine();
                            //Read difficulty
                            f.readLine();
                            //Read user round
                            f.readLine();
                            //Read Allies
                            for(int i4 = 0; i4 < 3; i4++){
                                f.readLine();
                            }
                            //Read abilities
                            for(int i7 = 0; i7 < 3; i7++){
                                for(int i6 = 0; i6 < 6; i6++){
                                    f.readLine();
                                }
                            }
                        }
                    }
                    f.close();
                }catch(Exception exc){}
            }
            lvl.round = 1;
            
            for(int i = 0; i < 3; i++){
                lvl.lvlup.add(new int[6]);
                for(int i2 = 0; i2 < 6; i2++){
                    lvl.lvlup.get(i)[i2] = 0;
                }
            }
            
            lvl.hideAll();
            
            state = 10;
            rf.showButtons();
            ss.stopSound();
            ss.playSound(battleMusic, canPlaySound);
        }
        
        
            //RoundFinish items
        else if(e.getSource() == rf.exit || e.getSource() == kos.exit || e.getSource() == ps.exit){
            slow.setVisible(false);
            moderate.setVisible(false);
            fast.setVisible(false);
            background = backgroundsArray[(int)(Math.random() * backgroundsArray.length)];
            if(state == 13){
                Timer.continueTimer();
            }
            int xyz = (int)(lvl.round/lvl.enemyFrequency);
            delevelUpEnemies();
            Character.noOfAllies = 0;
            Character.noOfEnemies = 0;
            Character.lstHit.clear();
            Character.lstAmount.clear();
            chars.clear();
            state = 1;
            rf.hideButtons();
            m.showMainMenu();
            ps.hideAll();
            pause = true;
            ss.stopSound();
        }else if(e.getSource() == rf.start){
            btnCanPlaySound.setVisible(false);
            lblCanPlaySound.setVisible(false);
            btnCanPlaySFX.setVisible(false);
            lblCanPlaySFX.setVisible(false);
            //Set up a random background
            background = backgroundsArray[(int)(Math.random() * backgroundsArray.length)];
            
            //Get enemies levelled up well
            delevelUpEnemies();
            for(int i = 0; i <= lvl.round; i++){
                if(i%lvl.enemyFrequency == 0){
                    levelUpEnemies();
                }
            }
            //Add enemies
            for(int i = 0; i < lvl.c.get(lvl.round-1).length; i++){
                switch(i){
                    case 0:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addSora(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 1:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addDonald(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 2:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addGoofy(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 3:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addLeon(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 4:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addCloud(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 5:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addRiku(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 6:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addSH(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 7:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addBR(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                    case 8:
                        for(int i2 = 0; i2 < lvl.c.get(lvl.round-1)[i]; i2++){
                            addBob(randomNo(getWidth()/2, getWidth()-100), randomNo(25, getHeight()-150), true, false);
                        }
                    break;
                }
            }
            //load hijk with the allies
            loadhijk();
            rf.hideButtons();
            resetAllyPositions();
            chars.get(0).stop(1344, 720);
            state = 7;
        }
        
        
        //KOScreen item
        else if(e.getSource() == kos.cont){
            while(chars.size() > 3){
                chars.remove(3);
            }
            state = 10;
            rf.showButtons();
        }
        
        //This class' buttons
        else if(e.getSource() == btnCanPlaySound){
            if(canPlaySound){
                lblCanPlaySound.setText("Off");
                canPlaySound = false;
                ss.stopSound();
                Sound.stopSound();
            }else{
                lblCanPlaySound.setText("On");
                canPlaySound = true;
                if(state == 7 || (state >= 10 && state <= 14)){
                    ss.playSound(battleMusic, canPlaySound);
                }
            }
        }else if(e.getSource() == btnCanPlaySFX){
            if(canPlaySFX){
                lblCanPlaySFX.setText("Off");
                canPlaySFX = false;
                Sound.stopSound();
            }else{
                lblCanPlaySFX.setText("On");
                canPlaySFX = true;
            }
        }else if(e.getSource() == slow){
            threadSleepTime = 25;
            slow.setEnabled(false);
            moderate.setEnabled(true);
            fast.setEnabled(true);
        }else if(e.getSource() == moderate){
            threadSleepTime = 20;
            slow.setEnabled(true);
            moderate.setEnabled(false);
            fast.setEnabled(true);
        }else if(e.getSource() == fast){
            threadSleepTime = 15;
            slow.setEnabled(true);
            moderate.setEnabled(true);
            fast.setEnabled(false);
        }
            
            //Add SlideShow instructions items
        else if(e.getSource() == instructions.btnBack){
            background = backgroundsArray[(int)(Math.random() * backgroundsArray.length)];
            state = 1;
            m.showMainMenu();
        }
            
            //Add SlideShow credits items
        else if(e.getSource() == credits.btnBack){
            background = backgroundsArray[(int)(Math.random() * backgroundsArray.length)];
            state = 1;
            m.showMainMenu();
        }
        
        
            //Character Selection buttons
        for(int x = 0; x < cs.select.size(); x++){
            if(e.getSource() == cs.select.get(x)){
                canPlayActualButtonSound = false;
                if(CharacterSelection.canPlaySound){
                    Sound.stopSound();
                    Sound.playSound(Character.chooseSound.get(x), canPlaySFX);
                }
            }
        }
        
        
            //Level selection select buttons
        for(int x = 0; x < ls.select.size(); x++){
            if(e.getSource() == ls.select.get(x)){
                lvl.levelNo = x;
                try{
                    BufferedReader fr = new BufferedReader(new FileReader("Custom Levels.txt"));
                    //z is the amount of levels saved
                    int z = 0;
                    //y is the arraylist containing all previous lines
                    try{ 
                        z = Integer.parseInt(fr.readLine());
                    }catch(Exception ex){}
                    
                    boolean cont = true;
                    for(int i = 0; i < z || cont == false; i++){
                        if(x != i){
                            //Read name of level
                            fr.readLine();
                            //Read enemy level up
                            fr.readLine();
                            //w is the amount of rounds
                            int w = Integer.parseInt(fr.readLine());
                            for(int i2 = 0; i2 < w; i2++){
                                //Read amount of different enemies
                                int u = Integer.parseInt(fr.readLine());
                                for(int i3 = 0; i3 < u; i3++){
                                    //Read enemy number
                                    fr.readLine();
                                }
                            }
                            cont = false;
                        }
                        //If it is not the required record just skip it
                        else{
                            //Read name of level
                            lvl.levelName = fr.readLine();
                            //Read enemy level up
                            lvl.enemyFrequency = Integer.parseInt(fr.readLine());
                            //w is the amount of rounds
                            int w = Integer.parseInt(fr.readLine());
                            lvl.maxRound = w;
                            lvl.c.clear();
                            for(int i2 = 0; i2 < w; i2++){
                                //Read amount of different enemies
                                int u = Integer.parseInt(fr.readLine());
                                lvl.c.add(new int[u]);
                                for(int i3 = 0; i3 < u; i3++){
                                    //Read enemy number
                                    lvl.c.get(i2)[i3] = Integer.parseInt(fr.readLine());
                                }
                            }
                        }
                    }
                    fr.close();
                }catch(Exception ex){}
                ls.hideAll();
                state = 3;
                m.showNewLoad();
            }
        }
        
            //Level selection delete buttons
        for(int i = 0; i < ls.delete.size(); i++){
            if(e.getSource() == ls.delete.get(i)){
                try{
                    BufferedReader f = new BufferedReader(new FileReader("Custom Level Users.txt"));
                    //The number of levels
                    int xyz;
                    ArrayList<String> y = new ArrayList<String>();
                    try{
                        xyz = Integer.parseInt(f.readLine());
                    }catch(Exception ex){
                        xyz = 0;
                    }
                    y.add((xyz-1) + "");
                    //z is the number of users for each level
                    int z;
                    
                    for(int i3 = 0; i3 < xyz; i3++){
                        if(i == i3){
                            try{
                                z = Integer.parseInt(f.readLine());
                            }catch(Exception ex){
                                z = 0;
                            }
                            for(int i2 = 0; i2 < z; i2++){
                                //read user name
                                f.readLine();
                                //Read difficulty
                                f.readLine();
                                //Read user round
                                f.readLine();
                                //Read Allies
                                for(int i4 = 0; i4 < 3; i4++){
                                    f.readLine();
                                }
                                //Read abilities
                                for(int i7 = 0; i7 < 3; i7++){
                                    for(int i6 = 0; i6 < 6; i6++){
                                        f.readLine();
                                    }
                                }
                            }
                        }else{
                            try{
                                z = Integer.parseInt(f.readLine());
                            }catch(Exception ex){
                                z = 0;
                            }
                            y.add(z +"");
                            for(int i2 = 0; i2 < z; i2++){
                                //read user name
                                String s = f.readLine();
                                y.add(s);
                                //Read difficulty
                                y.add(f.readLine());
                                //Read user round
                                y.add(f.readLine());
                                //Read Allies
                                for(int i4 = 0; i4 < 3; i4++){
                                    y.add(f.readLine());
                                }
                                //Read abilities
                                for(int i7 = 0; i7 < 3; i7++){
                                    for(int i6 = 0; i6 < 6; i6++){
                                        y.add(f.readLine());
                                    }
                                }
                            }
                        }
                    }
                    f.close();
                    
                    
                    FileWriter f2 = new FileWriter("Custom Level Users.txt");
                    
                    for(int i2 = 0; i2 < y.size(); i2++){
                        f2.write(y.get(i2) +"\r\n");
                    }
                    f2.close();
                    
                    y.clear();
                    
                    BufferedReader f3 = new BufferedReader(new FileReader("Custom Levels.txt"));
                    
                    //z is the amount of levels saved
                    z = 0;
                    //y is the arraylist containing all previous lines
                    try{ 
                        z = Integer.parseInt(f3.readLine());
                    }catch(Exception ex){}
                    
                    y.add(z-1 + "");
                    
                    for(int i2 = 0; i2 < z; i2++){
                        if(i2 == i){
                            //Read name of level
                            f3.readLine();
                            //Read enemy level up
                            f3.readLine();
                            //w is the amount of rounds
                            int w = Integer.parseInt(f3.readLine());
                            for(int i4 = 0; i4 < w; i4++){
                                //Read amount of different enemies
                                int u = Integer.parseInt(f3.readLine());
                                for(int i3 = 0; i3 < u; i3++){
                                    //Read enemy number
                                    f3.readLine();
                                }
                            }
                        }
                        //If it is not the required record just skip it
                        else{
                            //Read name of level
                            y.add(f3.readLine());
                            //Read enemy level up
                            y.add(f3.readLine());
                            //w is the amount of rounds
                            int w = Integer.parseInt(f3.readLine());
                            y.add(w + "");
                            lvl.c.clear();
                            for(int i4 = 0; i4 < w; i4++){
                                //Read amount of different enemies
                                int u = Integer.parseInt(f3.readLine());
                                y.add(u + "");
                                lvl.c.add(new int[u]);
                                for(int i3 = 0; i3 < u; i3++){
                                    //Read enemy number
                                    y.add(f3.readLine());
                                }
                            }
                        }
                    }
                    f3.close();
                    FileWriter f4 = new FileWriter("Custom Levels.txt");
                    
                    for(int i2 = 0; i2 < y.size(); i2++){
                        f4.write(y.get(i2) +"\r\n");
                    }
                    f4.close();
                    
                }catch(Exception ex){}
                ls.lbl.get(i).setVisible(false);
                ls.select.get(i).setVisible(false);
                ls.delete.get(i).setVisible(false);
                
                ls.lbl.remove(i);
                ls.select.remove(i);
                ls.delete.remove(i);
            }
        }
        
        for(int i = 0; i < us.lbl.size(); i++){
            if(e.getSource() == us.select.get(i)){
                ss.stopSound();
                ss.playSound(battleMusic, canPlaySound);
                try{
                    if(lvl.custom == false){
                        BufferedReader f = new BufferedReader(new FileReader("Main Level Users.txt"));
                        //read number of users
                        int z;
                        try{
                            z = Integer.parseInt(f.readLine());
                        }catch(Exception ex){
                            z = 0;
                        }
                        
                        for(int i3 = 0; i3 < z; i3++){
                            if(i3 != i){
                                //read user name
                                f.readLine();
                                //Read difficulty
                                f.readLine();
                                //Read user round
                                f.readLine();
                                //Read Allies
                                for(int i2 = 0; i2 < 3; i2++){
                                    f.readLine();
                                }
                                //Read abilities
                                for(int i7 = 0; i7 < 3; i7++){
                                    for(int i6 = 0; i6 < 6; i6++){
                                        f.readLine();
                                    }
                                }
                            }else{
                                //Set user no
                                lvl.userNo = i3+1;
                                //read user name
                                lvl.username = f.readLine();
                                //Read difficulty
                                lvl.difficulty = Integer.parseInt(f.readLine());
                                //Read user round
                                lvl.round = Integer.parseInt(f.readLine());
                                //Read Allies
                                lvl.a.clear();
                                for(int i2 = 0; i2 < 3; i2++){
                                    lvl.a.add(Integer.parseInt(f.readLine()));
                                }
                                //Read abilities
                                lvl.lvlup.clear();
                                for(int i7 = 0; i7 < 3; i7++){
                                    lvl.lvlup.add(new int[6]);
                                    for(int i6 = 0; i6 < 6; i6++){
                                        lvl.lvlup.get(i7)[i6] = Integer.parseInt(f.readLine());
                                    }
                                }
                            }
                        }
                        
                        f.close();
                    }else{
                        BufferedReader f = new BufferedReader(new FileReader("Custom Level Users.txt"));
                        //The number of levels
                        int xyz;
                        
                        try{
                            xyz = Integer.parseInt(f.readLine());
                        }catch(Exception ex){
                            xyz = 0;
                        }
                        
                        //z is the number of users for each level
                        int z;
                        
                        for(int i3 = 0; i3 < xyz; i3++){
                            try{
                                z = Integer.parseInt(f.readLine());
                            }catch(Exception ex){
                                z = 0;
                            }
                        
                            for(int i2 = 0; i2 < z; i2++){
                                if(i2 == i && lvl.levelNo == i3){
                                    //Set user no
                                    lvl.userNo = i2+1;
                                    //read user name
                                    lvl.username = f.readLine();
                                    //Read difficulty
                                    lvl.difficulty = Integer.parseInt(f.readLine());
                                    //Read user round
                                    lvl.round = Integer.parseInt(f.readLine());
                                    //Read Allies
                                    lvl.a.clear();
                                    for(int i4 = 0; i4 < 3; i4++){
                                        lvl.a.add(Integer.parseInt(f.readLine()));
                                    }
                                    //Read abilities
                                    lvl.lvlup.clear();
                                    for(int i7 = 0; i7 < 3; i7++){
                                        lvl.lvlup.add(new int[6]);
                                        for(int i6 = 0; i6 < 6; i6++){
                                            lvl.lvlup.get(i7)[i6] = Integer.parseInt(f.readLine());
                                        }
                                    }
                                }else{
                                    //read user name
                                    f.readLine();
                                    //Read difficulty
                                    f.readLine();
                                    //Read user round
                                    f.readLine();
                                    //Read Allies
                                    for(int i4 = 0; i4 < 3; i4++){
                                        f.readLine();
                                    }
                                    //Read abilities
                                    for(int i7 = 0; i7 < 3; i7++){
                                        for(int i6 = 0; i6 < 6; i6++){
                                            f.readLine();
                                        }
                                    }
                                }
                            }
                        }
                        f.close();
                    }
                }catch(Exception ex){}
                
                us.hideAll();
                
                //Show the round finish screen
                rf.showButtons();
                state = 10;
                
                addAllies();
                levelUpAllies();
                
                int xyz = (int)(lvl.round/lvl.enemyFrequency);
                for(int u = 0; u < xyz; u++){
                    levelUpEnemies();
                }
            }
        }
        
        
            //level up screen buttons
        for(int i = 0; i < lus.btn.size(); i++){
            for(int x = 0; x < lus.btn.get(i).length; x++){
                if(e.getSource() == lus.btn.get(i)[x]){
                    switch(x){
                        case 0:
                            //System.out.println(chars.get(i).name + "    " + chars.get(i).maxHP);
                            levelUpCharmaxHP(chars.get(i));
                        break;
                        case 1:
                            levelUpCharbaseDamage(chars.get(i));
                            //System.out.println(chars.get(i).name + "    " + chars.get(i).baseDamage);
                        break;
                        case 2:
                            levelUpCharabilityCooldown1(chars.get(i));
                            //System.out.println(chars.get(i).name + "    " + chars.get(i).abilityCooldown1);
                        break;
                        case 3:
                            levelUpCharabilityCooldown2(chars.get(i));
                            //System.out.println(chars.get(i).name + "    " + chars.get(i).abilityCooldown2);
                        break;
                        case 4:
                            levelUpCharspeed(chars.get(i));
                            //System.out.println(chars.get(i).name + "    " + chars.get(i).getBaseSpeed());
                        break;
                        case 5:
                            levelUpAbility(chars.get(i));
                        break;
                    }
                    lvl.lvlup.get(i)[x] ++;
                    lus.hideAll();
                    lvl.saveUser();
                    state = 10;
                    rf.showButtons();
                }
            }
        }
        
        if(!LevelCreator.playSound || CharacterSelection.canPlaySound == false || LevelSelection.canPlaySound == false || UserSelection.canPlaySound == false){
            canPlayButtonSound = false;
        }
        if(canPlayButtonSound){
            if(canPlayActualButtonSound){
                Sound.playSound(buttonClickName, canPlaySFX);
            }
        }else{
            Sound.playSound(errorButtonClickName, canPlaySFX);
            LevelCreator.playSound = true;
            CharacterSelection.canPlaySound = true;
            LevelSelection.canPlaySound = true;
            UserSelection.canPlaySound = true;
        }
        
    }

    public void mousePressed(java.awt.event.MouseEvent m) {
        //This is made to avoid keeping the focus on a button if you click on it then drag the cursor away from it.
        requestFocus();
    } 

    public void mouseReleased(java.awt.event.MouseEvent m){
        try{
            //If in the game state
            if(state == 7 && isPaused == false){
                //get the X and y coordinates of the click
                int px = m.getX();
                int py = m.getY();
                //Make p a new point
                Point p = new Point(px,py);
                //System.out.println(px + "          " + py);
                if(keysDown.contains(KeyEvent.VK_Q) || keysDown.contains(KeyEvent.VK_E)){
                    if(keysDown.contains(KeyEvent.VK_Q)){
                        chars.get(0).ability1();
                        chars.get(0).ability1(chars, px, py);
                        chars.get(0).ability1(px, py);
                        chars.get(0).ability1(px, py, getWidth(), getHeight()-60);
                        chars.get(0).ability1(chars, px, py, getWidth(), getHeight()-60);
                    }
                    if(keysDown.contains(KeyEvent.VK_E)){
                        chars.get(0).ability2();
                        chars.get(0).ability2(px, py);
                        chars.get(0).ability2(chars, px, py);
                        chars.get(0).ability2(px, py, getWidth(), getHeight()-60);
                        chars.get(0).ability2(chars, px, py, getWidth(), getHeight()-60);
                    }
                }else{
                    chars.get(0).attack(px, py);
                }
            }
        }catch(Exception e){}
    }

    public void mouseEntered(java.awt.event.MouseEvent m) {
        if(state == 7){
            requestFocus();
        }
    } 

    public void mouseExited(java.awt.event.MouseEvent m) {}

    public void mouseClicked(MouseEvent m) {}

    public void keyReleased(KeyEvent e){
        try{
            //When a key is released, it is removed from keysDown and added to keysUp
            keysDown.remove(new Integer(e.getKeyCode()));
            if(!keysUp.contains(e.getKeyCode())){
                keysUp.add(new Integer(e.getKeyCode()));
            }
            if(state == 7 && isPaused == false){
                //The timers are for stopping at diagonal directions
                if(e.getKeyCode() == 68){
                    chars.get(0).rightTimer = Timer.time;
                }
                if(e.getKeyCode() == 65){
                    chars.get(0).leftTimer = Timer.time;
                }
                if(e.getKeyCode() == 87){
                    chars.get(0).upTimer = Timer.time;
                }
                if(e.getKeyCode() == 83){
                    chars.get(0).downTimer = Timer.time;
                }
        
                if(keysUp.contains(68) && keysUp.contains(65) && keysUp.contains(87) && keysUp.contains(83)){
                    chars.get(0).stop(getWidth(), getHeight()-60);
                }
            }
        }catch(Exception ex){}
    }

    public void keyPressed(KeyEvent e){
        try{
            //When a key is pressed, it is removed from keysUp and added to keysDown
            keysUp.remove(new Integer(e.getKeyCode()));
            if(!keysDown.contains(e.getKeyCode())){
                keysDown.add(new Integer(e.getKeyCode()));
            }
            if(state == 7 && isPaused == false){
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    chars.get(0).attack(chars);
                }
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    chars.get(0).ability1();
                    chars.get(0).ability1(chars);
                }
                if(e.getKeyCode() == KeyEvent.VK_E){
                    chars.get(0).ability2();
                }
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    cycleChars();
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    btnCanPlaySound.setVisible(true);
                    lblCanPlaySound.setVisible(true);
                    btnCanPlaySFX.setVisible(true);
                    lblCanPlaySFX.setVisible(true);
                    slow.setVisible(true);
                    moderate.setVisible(true);
                    fast.setVisible(true);
                    Timer.pauseTimer();
                    state = 13;
                    loadhijk();
                    ps.showAll(getWidth(), getHeight(), hijk);
                    for(int i = 0; i < ps.lbl.size(); i++){
                        for(int x = 0; x < ps.lbl.get(i).length; x++){
                            add(ps.lbl.get(i)[x]);
                        }
                    }
                }
            }
        }catch(Exception ex){}
    }

    public void keyTyped(KeyEvent e){}

    public static void main(String args[]){
        KHR kh = new KHR();
        kh.setLocation(0,0);
        kh.setSize(1344,720);
        kh.setResizable(false);
        kh.setVisible(true);
        //This part of the code makes the X button in the top right of the screen exit the program
        kh.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            }
        );
    }
}