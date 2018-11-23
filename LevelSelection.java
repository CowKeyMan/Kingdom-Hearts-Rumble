//To use buttons and labels
import java.awt.*;
//To use events
import java.awt.event.*;
//To use array lists
import java.util.*;
//To read from file
import java.io.*;
public class LevelSelection implements ActionListener{
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
    //The limits of the screen
    int widthLimit, heightLimit;
    
    //The integer to indicate the page
    int page = 1;
    int maxPage = 1;
    
    //The integer to indicate how many ecords may appear on one page
    int max;
    
    //The name of the file where records will be loaded from
    String filename;
    
    public static boolean canPlaySound = true;
    
    public LevelSelection(int widthLimit2, int heightLimit2, int max2, String filName){
            widthLimit = widthLimit2;
            heightLimit =heightLimit2;
            
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
            
            filename = filName;
            
            max = max2;
            
            loadLabels();
            
            //make everything invisible
            hideAll();
        }
    
    void addLabel(String s){
        lbl.add(new Label(s));
        select.add(new Button("Select"));
        delete.add(new Button("Delete"));
    }
        
    void loadLabels(){
        try{
            BufferedReader fr = new BufferedReader(new FileReader(filename));
            //z is the amount of levels saved
            int z = 0;
            //y is the arraylist containing all previous lines
            try{ 
                z = Integer.parseInt(fr.readLine());
            }catch(Exception ex){}
            
            for(int i = 0; i < z; i++){
                //Read name of level
                addLabel(fr.readLine());
                //Read enemy level up
                fr.readLine();
                //w is the amount of rounds
                int w = Integer.parseInt(fr.readLine());
                for(int i2 = 0; i2 < w; i2++){
                    //Read amount of different enemies
                    int u = Integer.parseInt(fr.readLine());
                    for(int i3 = 0; i3 < u; i3++){
                        fr.readLine();
                    }
                }
            }
            fr.close();
        }catch(Exception e){}
        
        //Set the positions of the labels
        for(int i = 0, x = 0; i < lbl.size(); i++, x++){
            if(x == max){ x = 0; }
            
            //vertical space allocated for labels
            int verticalSpace = 520;
            //label height
            int height = (verticalSpace - (max-1)*10)/max;
            //label width
            int width = widthLimit - 500;
            
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
    void showPage1(){
        lbl.clear();
        select.clear();
        delete.clear();
        
        loadLabels();
        
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
        g.drawString("Choose a Level ", 250, 55);
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
    }
}