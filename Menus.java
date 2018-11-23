//To use buttons
import java.awt.*;
//To use action listener
import java.awt.event.*;
//To use arraylists
import java.util.*;
public class Menus implements ActionListener{
    //Parameters for button placements
    int screenWidth, screenHeight;
    
    //The current buttons being
    //state: 0 = Nothing, 1 = Main Menu, 2 = Difficulty Selection, 3 = New/Load Game
    int state = 0;
    
    //Main Menu
    Menu mm = new Menu();
    //Difficulty Selection
    Menu ds = new Menu();
    //Choose between New/Load game
    Menu nl = new Menu();
    //The filename where the main level is stored
    String mainFilename;
    
    //Buttons used for scrolling up or down during selection
    Button btnUp = new Button("UP");
    Button btnDown = new Button("DOWN");
    
    //Items used for level selection
    ArrayList<Label> lblLevelName = new ArrayList<Label>();
    
    //Items for saved user selection
    ArrayList<Label> lblUserName = new ArrayList<Label>();
    
    //Buttons used for deleting and choosing a saved level/User
    ArrayList<Button> btnChoose = new ArrayList<Button>();
    ArrayList<Button> btnDelete = new ArrayList<Button>();
    
    public Menus(int widthLimit, int heightLimit){
        //Set the names of the main menu buttons
        mm.addButton("Main Level");
        mm.addButton("Custom Level");
        mm.addButton("Create Level");
        mm.addButton("Instructions");
        mm.addButton("Credits");
        for(int i = 0; i < mm.btn.size(); i++){
            mm.btn.get(i).addActionListener(this);
        }
        //Set the names of the Difficulty Selection buttons
        ds.addButton("Beginner");
        ds.addButton("Standard");
        ds.addButton("Proud");
        ds.addBackButton(heightLimit, 100, 40);
        for(int i = 0; i < ds.btn.size(); i++){
            ds.btn.get(i).addActionListener(this);
        }
        ds.btnBack.addActionListener(this);
        //Set the Loading selection buttons
        nl.addButton("New Game");
        nl.addButton("Load Game");
        nl.addBackButton(heightLimit, 100, 40);
        for(int i = 0; i < nl.btn.size(); i++){
            nl.btn.get(i).addActionListener(this);
        }
        nl.btnBack.addActionListener(this);
        
        //Give a value to screen width and height
        screenWidth = widthLimit;
        screenHeight = heightLimit;
        
        hideMainMenu();
    }
    
    void showMainMenu(){
        mm.showButtons(screenWidth, screenHeight, 175, 50, 100, 30);
        state = 1;
    }
    void hideMainMenu(){
        mm.hideButtons();
        state = 0;
    }
    
    void showDifficultySelection(){
        ds.showButtons(screenWidth, screenHeight, 175, 50, 100, 30);
        state = 2;
    }
    void hideDifficultySelection(){
        ds.hideButtons();
        state = 0;
    }
    
    void showNewLoad(){
        nl.showButtons(screenWidth, screenHeight, 175, 50, 100, 30);
        state = 3;
    }
    void hideNewLoad(){
        nl.hideButtons();
        state = 0;
    }
    
    public void paint(Graphics g){
        g.setFont(new Font("Times New Roman", Font.BOLD, 90));
        if(state == 1){
            g.drawString("MAIN MENU", 400, 130);
        }else if(state == 2){
            g.setFont(new Font("Times New Roman", Font.BOLD, 50));
            g.drawString("CHOOSE A DIFFICULTY", 410, 150);
        }else if(state == 3){
            g.setFont(new Font("Times New Roman", Font.BOLD, 50));
            g.drawString("NEW OR RETURNING PLAYER?", 320, 150);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        hideMainMenu();
        hideDifficultySelection();
        hideNewLoad();
        if(e.getSource() == mm.btn.get(0)){
            showNewLoad();
        }else if(e.getSource() == nl.btn.get(0)){
            showDifficultySelection();
        }else if(e.getSource() == ds.btnBack){
            showNewLoad();
        }
    }
}