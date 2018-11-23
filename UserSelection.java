//To use buttons and labels
import java.awt.*;
//To use events
import java.awt.event.*;
//To use array lists
import java.util.*;
//To read from file
import java.io.*;
public class UserSelection implements ActionListener{
    //Labels to display information
    ArrayList<Label> lbl = new ArrayList<Label>();
    //Button to select current selection
    ArrayList<Button> select = new ArrayList<Button>();
    //Button to delete current selection
    ArrayList<Button> delete = new ArrayList<Button>();
    
    //Buttons to scroll up and down
    Button up = new Button("Up");
    Button down = new Button("Down");
    
    //Text box for user to jump to a page
    TextField txtPage = new TextField();
    //Button to go to page in text field
    Button go = new Button("GO");
    //Button to exit selection
    Button back = new Button("Back");
    
    //Button properties
    //The starting part in the vertical part
    int verticalStart;
    //The separation between label y coordinates
    int verticalSep;
    //The starting point horizontally of the labels
    int widthStart;
    //The label height
    int height;
    //Width allocated for labels and buttons
    int width;
    //The width and height of the screen
    int widthLimit, heightLimit;
    
    //The integer to indicate the page
    int page = 1;
    int maxPage = 1;
    
    //The integer to indicate how many ecords may appear on one page
    int max;
    
    //The name of the file where records will be loaded from
    String mainFilename, customFilename;
    
    //The level number of the current labels being displayed(only applies to custom levels)
    int levelNumber;
    
    //Boolean to check if the level is a custom one or not
    boolean lvlcustom;
    
    public static boolean canPlaySound = true;
    
    public UserSelection(int widthLimit2, int heightLimit2, int max2, String mainName, String customName){
            //Set the max amount of records that can fit in the screen at one time
            max = max2;
            
            //Set the file names
            mainFilename = mainName;
            customFilename = customName;
            
            //Set the width and height of the screen
            widthLimit = widthLimit2;
            heightLimit = heightLimit2;
            
            //Set the positions of items
            back.setBounds(3, heightLimit-40, 100, 40);
            back.addActionListener(this);
            up.setBounds(widthLimit/2 - 50, 35 , 100, 30);
            up.addActionListener(this);
            down.setBounds(widthLimit/2 - 50, heightLimit - 80, 100, 30);
            down.addActionListener(this);
            txtPage.setBounds(widthLimit - 160, heightLimit - 95, 80, 45);
            go.setBounds(widthLimit - 75, heightLimit - 90, 50, 35);
            go.addActionListener(this);
            
            //make everything invisible
            hideAll();
        }
    
    void addLabel(String s){
        lbl.add(new Label(s));
        select.add(new Button("Select"));
        delete.add(new Button("Delete"));
    }
        
    void loadLabels(boolean custom, int levelNo){
        lbl.clear();
        select.clear();
        delete.clear();
        try{
            if(custom == false){
                BufferedReader f = new BufferedReader(new FileReader(mainFilename));
                int z;
                try{
                    z = Integer.parseInt(f.readLine());
                }catch(Exception ex){
                    z = 0;
                }
                
                for(int i = 0; i < z; i++){
                    //read username
                    addLabel(f.readLine());
                    //Read difficulty
                    f.readLine();
                    //Read user round
                    f.readLine();
                    //Read Allies
                    for(int i3 = 0; i3 < 3; i3++){
                        f.readLine();
                    }
                    //Read abilities
                    for(int i7 = 0; i7 < 3; i7++){
                        for(int i6 = 0; i6 < 6; i6++){
                            f.readLine();
                        }
                    }
                }
                
                f.close();
            }else{
                levelNumber = levelNo;
                lvlcustom = true;
                
                BufferedReader f = new BufferedReader(new FileReader(customFilename));
                int xyz;
                try{
                    xyz = Integer.parseInt(f.readLine());
                }catch(Exception ex){
                    xyz = 0;
                }
                for(int i2 = 0; i2 < xyz; i2++){
                    int z;
                    try{
                        z = Integer.parseInt(f.readLine());
                    }catch(Exception ex){
                        z = 0;
                    }
                    
                    for(int i = 0; i < z; i++){
                        if(levelNo == i2){
                            //read username
                            addLabel(f.readLine());
                        }else{
                            f.readLine();
                        }
                        //Read difficulty
                        f.readLine();
                        //Read user round
                        f.readLine();
                        //Read Allies
                        for(int i3 = 0; i3 < 3; i3++){
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
            }
        }catch(Exception e){}
        
        //Set the positions of the labels
        for(int i = 0, x = 0; i < lbl.size(); i++, x++){
            if(x == max){ x = 0; }
            
            //vertical space allocated for labels
            int verticalSpace = 520;
            //label height
            height = (verticalSpace - (max-1)*10)/max;
            //label width
            width = widthLimit - 500;
            
            lbl.get(i).setBounds(100, 80 + (height+10)*x, width, height);
            select.get(i).setBounds(100 + width + 10, 80 + (height+10)*x, 140, height);
            delete.get(i).setBounds(100 + width + 20 + 140, 80 + (height+10)*x, 140, height);
        }
        
        maxPage = lbl.size()/max;
        if(lbl.size()%max != 0){
            maxPage++;
        }
    }
    
    void hideLabels(){
        for(int i = 0; i < lbl.size(); i++){
            lbl.get(i).setVisible(false);
            select.get(i).setVisible(false);
            delete.get(i).setVisible(false);
        }
    }
    
    void hideAll(){
        hideLabels();
        up.setVisible(false);
        down.setVisible(false);
        back.setVisible(false);
        go.setVisible(false);
        txtPage.setVisible(false);
    }
    
    //The method to first display everything
    void showPage1(boolean custom, int levelNo){
        page = 1;
        lbl.clear();
        select.clear();
        delete.clear();
        loadLabels(custom, levelNo);
        displayPage();
        if(lbl.size() == 0){
            page = 0;
            down.setVisible(false);
        }
        up.setVisible(false);
        if(page != maxPage){
            down.setVisible(true);
        }
        back.setVisible(true);
        go.setVisible(true);
        txtPage.setVisible(true);
        for(int i = 0; i < lbl.size() && i < max;i++){
            lbl.get(i).setVisible(true);
            select.get(i).setVisible(true);
            delete.get(i).setVisible(true);
        }
    }
    
    public void displayPage(){
        int y = (page-1)*max;
        hideLabels();
        for(int i = y; i < y+max && i < lbl.size(); i++){
            lbl.get(i).setVisible(true);
            select.get(i).setVisible(true);
            delete.get(i).setVisible(true);
            delete.get(i).addActionListener(this);
        }
        if(page == 1){
            up.setVisible(false);
        }else{
            up.setVisible(true);
        }
        if(page == maxPage || maxPage == 1){
            down.setVisible(false);
        }else{
            down.setVisible(true);
        }
    }
    
    public void paint(Graphics g){
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Choose a Save File ", 250, 55);
        g.drawString("Page " + page + "/" + maxPage, 35, 55);
        g.drawString("Go to page: ", 1010, 653);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == up){
            page--;
            displayPage();
        }else if(e.getSource() == down){
            page++;
            displayPage();
        }else if(e.getSource() == back){
            hideAll();
        }else if(e.getSource() == go){
            try{
                int r = Integer.parseInt(txtPage.getText());
                if(r > 0 && r <= maxPage){
                    page = r;
                    displayPage();
                }else{
                    canPlaySound = false;
                }
            }catch(Exception ex){
                canPlaySound = false;
            }
        }
        
        for(int i = 0; i < lbl.size(); i++){
            if(e.getSource() == delete.get(i)){
                try{
                    if(lvlcustom == false){
                        ArrayList<String> y = new ArrayList<String>();
                        BufferedReader f = new BufferedReader(new FileReader(mainFilename));
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
                                y.add(f.readLine());
                                //Read difficulty
                                y.add(f.readLine());
                                //Read user round
                                y.add(f.readLine());
                                //Read Allies
                                for(int i2 = 0; i2 < 3; i2++){
                                    y.add(f.readLine());
                                }
                                //Read abilities
                                for(int i7 = 0; i7 < 3; i7++){
                                    for(int i6 = 0; i6 < 6; i6++){
                                        y.add(f.readLine());
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
                                for(int i2 = 0; i2 < 3; i2++){
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
                        
                        z--;
                        
                        FileWriter f2 = new FileWriter(mainFilename);
                        f2.write(z  +"\r\n");
                        for(int i2 = 0; i2 < y.size(); i2++){
                            f2.write(y.get(i2) +"\r\n");
                        }
                        f2.close();
                    }else{
                        ArrayList<String> y = new ArrayList<String>();
                        BufferedReader f = new BufferedReader(new FileReader(customFilename));
                        
                        //The amount of levels
                        int xyz = Integer.parseInt(f.readLine());
                        
                        for(int i2 = 0; i2 < xyz; i2++){
                            int z = Integer.parseInt(f.readLine());
                            if(levelNumber == i2){
                                y.add((z-1) + "");
                            }else{
                                y.add(z + "");
                            }
                            
                            for(int i3 = 0; i3 < z; i3++){
                                if(i3 == i && levelNumber == i2){
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
                                }else{
                                    //read user name
                                    y.add(f.readLine());
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
                        
                        FileWriter f2 = new FileWriter(customFilename);
                        f2.write(xyz  +"\r\n");
                        for(int i2 = 0; i2 < y.size(); i2++){
                            f2.write(y.get(i2) +"\r\n");
                        }
                        f2.close();
                    }
                }catch(Exception ex){}
                
                lbl.get(i).setVisible(false);
                select.get(i).setVisible(false);
                delete.get(i).setVisible(false);
                
                lbl.remove(i);
                select.remove(i);
                delete.remove(i);
                
                displayPage();
            }
        }
    }
}