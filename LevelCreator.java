//To use buttons, text fields, etc
import java.awt.*;
//To use arraylists
import java.util.*;
//To use action listener
import java.awt.event.*;
//Used for saving
import java.io.*;
public class LevelCreator implements ActionListener{
    /*state: 
     * 0 = nothing is displayed
     * 1 = Name and enemy level up input
     * 2 = Enemy per round input
     */
    int state = 0;
    
    //Button to move back or go to the next step
    Button btnBack = new Button("Back");
    Button btnNext = new Button("Next");
        
        //Items used in state 1
    //The name of the level
    TextField txtName = new TextField("");
    //The enemies level up every 'txtEnemyLvlUp' rounds
    TextField txtEnemyLvlUp = new TextField("");
    
        //items used in state 2
    //The current round the user is editing and the maximum number of them
    int round = 1, max = 1;
    //An arrayList containing all available characters, 1 of each
    ArrayList<Character> ch;
    //The buttons to scroll up and down
    Button btnUp = new Button("Up"), btnDown = new Button("Down");
    //Labels and images to be drawn contaning the character information
    ArrayList<Image> charImage = new ArrayList<Image>();
    ArrayList<Label> charName = new ArrayList<Label>();
    //Amount of enemies
    ArrayList<TextField> txtCharNo = new ArrayList<TextField>();
    //button to delete this round
    Button delete = new Button("Delete Round");
    Button reset = new Button("Reset All");
    Button finish = new Button("Save and Finish");
    //Text box for user to enter round to jump to
    TextField txtRound = new TextField("");
    Button go = new Button("Go");
    //The array list to the number of characters per round
    ArrayList<int[]> charNo = new ArrayList<int[]>();
    //The enemy page(up and down)
    //maxPerPage is the maximum number of recores that fit in one page
    int currPage = 1, maxPages = 1, maxPerPage;
    //The file where the program is to save the custom levels in
    String filename;
    
    public static boolean playSound = true;
    
    public LevelCreator(ArrayList<Character> c, int widthLimit, int heightLimit, int maxInPage, String filname){
        //Set the positions of items
        btnBack.setBounds(3, heightLimit-40, 100, 40);
        btnBack.addActionListener(this);
        btnNext.setBounds(widthLimit - 100, heightLimit-40, 100, 40);
        btnNext.addActionListener(this);
            //state 1 items
        txtName.setBounds(widthLimit/2 - 100, 40, widthLimit/2 - 200, 30);
        txtEnemyLvlUp.setBounds(widthLimit/2 - 100, 90, 50, 30);
            //state 2 items
        //Set bounds of state 2 items
        btnUp.setBounds(widthLimit/2 - 50, 35 , 100, 30);
        btnUp.addActionListener(this);
        btnDown.setBounds(widthLimit/2 - 50, heightLimit - 80, 100, 30);
        btnDown.addActionListener(this);
        //Set the positions of the special buttons
        reset.setBounds(110, heightLimit-40, 100, 40);
        reset.addActionListener(this);
        finish.setBounds(widthLimit/2 - 100, heightLimit-40, 200, 40);
        finish.addActionListener(this);
        delete.setBounds(widthLimit - 270, heightLimit-40, 160, 40);
        delete.addActionListener(this);
        txtRound.setBounds(widthLimit - 160, heightLimit - 95, 80, 45);
        go.setBounds(widthLimit - 75, heightLimit - 90, 50, 35);
        go.addActionListener(this);
        
        //Set the images and labels of the characters
        for(int i = 0, x = 0; i < c.size(); i++){
            charImage.add(c.get(i).healthyImage);
            charName.add(new Label(c.get(i).name));
            txtCharNo.add(new TextField("0"));
        }
        //Set the positions of the labels
        for(int i = 0, x = 0; i < c.size(); i++, x++){
            if(x == maxInPage){ x = 0; }
            
            //vertical space allocated for labels
            int verticalSpace = 520;
            //label height
            int height = (verticalSpace - (maxInPage-1)*10)/maxInPage;
            //label width
            int width = widthLimit - 300;
            
            charName.get(i).setBounds(100, 80 + (height+10)*x, width, height);
            txtCharNo.get(i).setBounds(widthLimit - 190, 80 + (height+10)*x, 170, height);
        }
        
        maxPages = c.size()/maxInPage;
        if(c.size()%maxInPage != 0){
            maxPages++;
        }
        
        maxPerPage = maxInPage;
        
        filename = filname;
        
        hideAll();
    }
    
    void showState1(){
        btnNext.setVisible(true);
        btnBack.setVisible(true);
        txtName.setVisible(true);
        txtEnemyLvlUp.setVisible(true);
        state = 1;
    }
    void showState2(){
        for(int i = 0; i < charName.size(); i++){
            charName.get(i).setVisible(true);
            txtCharNo.get(i).setVisible(true);
        }
        delete.setVisible(true);
        reset.setVisible(true);
        finish.setVisible(true);
        txtRound.setVisible(true);
        go.setVisible(true);
        //Set the current page to 1
        currPage = 1;
        if(maxPages > 1){
            btnDown.setVisible(true);
        }
        state = 2;
    }
    
    void hideAll(){
        hideState1();
        hideState2();
        btnNext.setVisible(false);
        btnBack.setVisible(false);
        state = 0;
    }
    void hideState1(){
        txtName.setVisible(false);
        txtEnemyLvlUp.setVisible(false);
    }
    void hideState2(){
        for(int i = 0; i < charName.size(); i++){
            charName.get(i).setVisible(false);
            txtCharNo.get(i).setVisible(false);
        }
        delete.setVisible(false);
        reset.setVisible(false);
        finish.setVisible(false);
        txtRound.setVisible(false);
        go.setVisible(false);
        btnUp.setVisible(false);
        btnDown.setVisible(false);
        currPage = 1;
    }
    
    public void displayRound(){
        //Display round x
        int x = round-1;
        for(int i = 0; i < txtCharNo.size(); i++){
            txtCharNo.get(i).setText(charNo.get(x)[i] + "");
        }
    }
    
    //Show set of records that fit in a page
    public void displayPage(){
        int y = (currPage-1)*maxPerPage;
        for(int i = 0; i < txtCharNo.size(); i++){
            txtCharNo.get(i).setVisible(false);
            charName.get(i).setVisible(false);
        }
        for(int i = y; i < y+maxPerPage && i < txtCharNo.size(); i++){
            txtCharNo.get(i).setVisible(true);
            charName.get(i).setVisible(true);
        }
    }
    
    //Show the first 'maxPerPage' records in the page
    public void displayPage1(){
        currPage = 1;
        displayPage();
        btnUp.setVisible(false);
        if(currPage == maxPages){
            btnDown.setVisible(false);
        }else{
            btnDown.setVisible(true);
        }
    }
    
    //Save round in the arrylist
    public void saveRound(){
        //If round is max round, add a new array list entry
        if(round == max){
            charNo.add(new int[txtCharNo.size()]);
        }
        for(int i = 0; i < txtCharNo.size(); i++){
            try{
                charNo.get(round-1)[i] = Integer.parseInt(txtCharNo.get(i).getText());
            }catch(Exception ex){
                charNo.get(round-1)[i] = 0;
            }
        }
    }
    
    public void reset(){
        hideState2();
        showState1();
        charNo.clear();
        round = max = 1;
        for(int i = 0; i < txtCharNo.size(); i++){
            //reset the text field to 0
            txtCharNo.get(i).setText("0");
        }
        txtName.setText("");
        txtEnemyLvlUp.setText("");
    }
    
    public void paint(Graphics g){
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        if(state == 1){
            g.drawString("Enter level name:", 345, 60);
            g.drawString("Enemies level up every         levels",270, 110);
        }else if(state == 2){
            g.drawString("Round " + round + "/" + max, 35, 55);
            g.drawString("Go to round: ", 1010, 653);
            g.drawString("Amount", 1180, 73);
            g.drawLine(1167, 76, 1305, 76);
            
            int y = (currPage-1)*maxPerPage;
            for(int i = y; i < y+maxPerPage && i < txtCharNo.size(); i++){
                g.drawImage(charImage.get(i), charName.get(i).getX() - 70, charName.get(i).getY(), null);
            }
        }
    }
    
    void saveToFile(){
        try{
            BufferedReader fr = new BufferedReader(new FileReader(filename));
            //z is the amount of levels saved
            int z = 0;
            //y is the arraylist containing all previous lines
            ArrayList<String> y = new ArrayList<String>();
            try{ 
                z = Integer.parseInt(fr.readLine());
            }catch(Exception ex){}
            
            for(int i = 0; i < z; i++){
                //Read name of level
                y.add(fr.readLine());
                //Read enemy level up
                y.add(fr.readLine());
                //w is the amount of rounds
                int w = Integer.parseInt(fr.readLine());
                y.add(w + "");
                for(int i2 = 0; i2 < w; i2++){
                    //Read amount of different enemies
                    int u = Integer.parseInt(fr.readLine());
                    y.add(u + "");
                    for(int i3 = 0; i3 < u; i3++){
                        y.add(fr.readLine());
                    }
                }
            }
            //increment z by 1 since we are adding a new record
            z++;
            fr.close();
            
            FileWriter f = new FileWriter(filename);
            f.write(z +"\r\n");
            for(int i = 0; i < y.size(); i++){
                f.write(y.get(i) +"\r\n");
            }
            f.write(txtName.getText() +"\r\n");
            f.write(txtEnemyLvlUp.getText() +"\r\n");
            f.write(max +"\r\n");
            for(int i = 0; i < max; i++){
                f.write(charName.size() +"\r\n");
                for(int x = 0; x < charName.size(); x++){
                    f.write(charNo.get(i)[x] +"\r\n");
                }
            }
            f.close();
            
            y.clear();
            
            //Also inrement by 1 the number in the users
            BufferedReader f2 = new BufferedReader(new FileReader("Custom Level Users.txt"));
            
            int xyz;
            try{
                xyz = Integer.parseInt(f2.readLine());
            }catch(Exception ex){
                xyz = 0;
            }
            y.add((xyz+1) + "");
            
            for(int i2 = 0; i2 < xyz; i2++){
                try{
                    z = Integer.parseInt(f2.readLine());
                }catch(Exception ex){
                    z = 0;
                }
                y.add(z + "");
                for(int i4 = 0; i4 < z; i4++){
                    //read user name
                    y.add(f2.readLine());
                    //Read difficulty
                    y.add(f2.readLine());
                    //Read user round
                    y.add(f2.readLine());
                    //Read Allies
                    for(int i5 = 0; i5 < 3; i5++){
                        y.add(f2.readLine());
                    }
                    //Read abilities
                    for(int i7 = 0; i7 < 3; i7++){
                        for(int i6 = 0; i6 < 6; i6++){
                            y.add(f2.readLine());
                        }
                    }
                }
            }
            f2.close();
            
            FileWriter f3 = new FileWriter("Custom Level Users.txt");
            
            
            for(int i2 = 0; i2 < y.size(); i2++){
                f3.write(y.get(i2) + "\r\n");
            }
            
            f3.close();
        }catch(Exception ex){}
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnBack){
            if(state == 1){
                hideAll();
            }else if(state == 2){
                if(round == 1){
                    hideState2();
                    showState1();
                }else{
                    //If at the last page
                    boolean cont = false;
                    for(int i = 0; i < txtCharNo.size() && cont == false; i++){
                        if(Integer.parseInt(txtCharNo.get(i).getText()) > 0){
                            cont = true;
                        }
                    }
                    if(cont == true){
                        saveRound();
                        round--;
                        displayRound();
                        displayPage1();
                    }else{
                        playSound = false;
                    }
                }
            }
        }else if(e.getSource() == btnNext){
            if(state == 1){
                try{
                    int xyz = Integer.parseInt(txtEnemyLvlUp.getText());
                    hideState1();
                    showState2();
                }catch(Exception ex){
                    playSound = false;
                }
            }else if(state == 2){
                //If at the last page
                boolean cont = false;
                for(int i = 0; i < txtCharNo.size() && cont == false; i++){
                    if(Integer.parseInt(txtCharNo.get(i).getText()) > 0){
                        cont = true;
                    }
                }
                if(cont == true){
                    saveRound();
                    if(round == max){
                        //Increment max pages and current page
                        round = ++max;
                        for(int i = 0; i < txtCharNo.size(); i++){
                            //reset the text field to 0
                            txtCharNo.get(i).setText("0");
                        }
                    }else{
                        //Display current round
                        round++;
                        displayRound();
                    }
                    displayPage1();
                }else{
                    playSound = false;
                }
            }
        }else if(e.getSource() == go){
            //If at the last page
            boolean cont = false;
            for(int i = 0; i < txtCharNo.size() && cont == false; i++){
                if(Integer.parseInt(txtCharNo.get(i).getText()) > 0){
                    cont = true;
                }
            }
            if(cont == true){
                try{
                    int r = Integer.parseInt(txtRound.getText());
                    if(r > 0 && r <= max){
                        round = r;
                        displayRound();
                    }
                    displayPage1();
                }catch(Exception ex){
                    playSound = false;
                }
            }else{
                playSound = false;
            }
        }else if(e.getSource() == delete){
            if(round == 1){
                //If first record and max = 1
                if(max == 1){
                    //Reset the text fields
                    for(int i = 0; i < txtCharNo.size(); i++){
                        //reset the text field to 0
                        txtCharNo.get(i).setText("0");
                    }
                }else{
                    max--;
                    charNo.remove(round-1);
                    displayRound();
                }
                //If last record
            }else if(round == max){
                //move everything by -1
                round--; 
                max--;
                displayRound();
            }else{
                max--;
                charNo.remove(round-1);
                displayRound();
            }
            displayPage1();
        }else if(e.getSource() == reset){
            reset();
        }else if(e.getSource() == btnDown){
            currPage++;
            displayPage();
            btnUp.setVisible(true);
            if(currPage == maxPages){
                btnDown.setVisible(false);
            }
        }else if(e.getSource() == btnUp){
            currPage--;
            displayPage();
            btnDown.setVisible(true);
            if(currPage == 1){
                btnUp.setVisible(false);
            }
        }else if(e.getSource() == finish){
            saveRound();
            saveToFile();
            reset();
        }
    }
}