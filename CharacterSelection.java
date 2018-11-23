//To use buttons and labels
import java.awt.*;
//To use events
import java.awt.event.*;
//To use array lists
import java.util.*;
//To read from file
import java.io.*;
public class CharacterSelection implements ActionListener{
    //Labels to display information
    ArrayList<Label> lbl = new ArrayList<Label>();
    //Button to select current selection
    ArrayList<Button> select = new ArrayList<Button>();
    //Button to deselect current selection
    ArrayList<Button> deselect = new ArrayList<Button>();
    
    //Buttons to scroll up and down
    Button up = new Button("Up");
    Button down = new Button("Down");
    
    //Text box for user to jump to a page
    TextField txtPage = new TextField();
    //Button to go to page in text field
    Button go = new Button("GO");
    //Button to exit selection
    Button back = new Button("Back");
    //Button to move forward
    Button next = new Button("Next");
    
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
    
    //The integer to indicate the page
    int page = 1;
    int maxPage = 1;
    
    //The integer to indicate how many ecords may appear on one page
    int max;
    
    //The current amount of allies and the maximum amount of allies
    int maxChars;
    //The arrayList to store the characters selected
    ArrayList<Integer> chars = new ArrayList<Integer>();
    
    //Arraylist to store character images when selecting
    ArrayList<Image> img = new ArrayList<Image>();
    
    //The name of the file where records will be loaded from
    String filename;
    
    static boolean canPlaySound = true;
    
    public CharacterSelection(int widthLimit, int heightLimit, int max2, ArrayList<Character> c, int maxC){
            //Set the positions of items
            back.setBounds(3, heightLimit-40, 100, 40);
            back.addActionListener(this);
            next.setBounds(widthLimit-100, heightLimit-40, 100, 40);
            next.addActionListener(this);
            up.setBounds(widthLimit/2 - 50, 35 , 100, 30);
            up.addActionListener(this);
            down.setBounds(widthLimit/2 - 50, heightLimit - 80, 100, 30);
            down.addActionListener(this);
            txtPage.setBounds(widthLimit - 160, heightLimit - 95, 80, 45);
            go.setBounds(widthLimit - 75, heightLimit - 90, 50, 35);
            go.addActionListener(this);
            
            for(int i = 0; i < c.size(); i++){
                addLabel(c.get(i).name);
                img.add(c.get(i).healthyImage);
            }
            
            //Set the positions of the labels
            for(int i = 0, x = 0; i < lbl.size(); i++, x++){
                if(x == max2){ x = 0; }
                
                //vertical space allocated for labels
                int verticalSpace = 520;
                //label height
                height = (verticalSpace - (max2-1)*10)/max2;
                //label width
                width = widthLimit - 500;
                
                lbl.get(i).setBounds(100, 80 + (height+10)*x, width, height);
                select.get(i).setBounds(100 + width + 10, 80 + (height+10)*x, 140, height);
                select.get(i).addActionListener(this);
                deselect.get(i).setBounds(100 + width + 20 + 140, 80 + (height+10)*x, 140, height);
                deselect.get(i).addActionListener(this);
            }
            
            //make everything invisible
            hideAll();
            
            maxPage = lbl.size()/max2;
            if(lbl.size()%max2 != 0){
                maxPage++;
            }
            
            max = max2;
            maxChars = maxC;
        }
    
    void addLabel(String s){
        lbl.add(new Label(s));
        select.add(new Button("Select"));
        deselect.add(new Button("Deselect"));
    }
    
    void hideLabels(){
        for(int i = 0; i < lbl.size(); i++){
            lbl.get(i).setVisible(false);
            select.get(i).setVisible(false);
            deselect.get(i).setVisible(false);
        }
    }
    
    void hideAll(){
        hideLabels();
        up.setVisible(false);
        down.setVisible(false);
        back.setVisible(false);
        next.setVisible(false);
        go.setVisible(false);
        txtPage.setVisible(false);
    }
    
    //The method to first display everything
    void showPage1(){
        displayPage();
        up.setVisible(false);
        if(page != maxPage){
            down.setVisible(true);
        }
        back.setVisible(true);
        next.setVisible(true);
        go.setVisible(true);
        txtPage.setVisible(true);
        for(int i = 0; i < lbl.size() && i < max;i++){
            lbl.get(i).setVisible(true);
            select.get(i).setVisible(true);
            deselect.get(i).setVisible(true);
            if(chars.contains(i)){
                select.get(i).setEnabled(false);
                deselect.get(i).setEnabled(true);
            }else{
                select.get(i).setEnabled(true);
                deselect.get(i).setEnabled(false);
            }
        }
    }
    
    public void displayPage(){
        int y = (page-1)*max;
        hideLabels();
        for(int i = y; i < y+max && i < lbl.size(); i++){
            lbl.get(i).setVisible(true);
            select.get(i).setVisible(true);
            deselect.get(i).setVisible(true);
            if(chars.contains(i)){
                select.get(i).setEnabled(false);
                deselect.get(i).setEnabled(true);
            }else{
                select.get(i).setEnabled(true);
                deselect.get(i).setEnabled(false);
            }
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
        g.drawString("Choose THREE Characters ", 250, 55);
        g.drawString("Page " + page + "/" + maxPage, 35, 55);
        g.drawString("Go to page: ", 1010, 653);
        int y = (page-1)*max;
        for(int i = y, x = 0; i < y+max && i < img.size(); i++, x++){
            g.drawImage(img.get(i), 50, 80 + (height+10)*x, null);
        }
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
            chars.clear();
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
        }else if(e.getSource() == next){
            if(chars.size() == maxChars){
                hideAll();
            }else{
                canPlaySound = false;
            }
        }
        for(int i = 0; i < select.size(); i++){
            if(e.getSource() == select.get(i)){
                if(chars.size() < 3){
                    select.get(i).setEnabled(false);
                    deselect.get(i).setEnabled(true);
                    chars.add((i+1));
                }else{
                    canPlaySound = false;
                }
            }
        }
        for(int i = 0; i < deselect.size(); i++){
            if(e.getSource() == deselect.get(i)){
                deselect.get(i).setEnabled(false);
                select.get(i).setEnabled(true);
                chars.remove(chars.indexOf(i+1));
            }
        }
    }
}