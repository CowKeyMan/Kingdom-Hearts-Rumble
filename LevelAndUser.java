//To use arraylists
import java.util.*;
//To use GUI items
import java.awt.*;
//To use action lsitener
import java.awt.event.*;
//Import items for file saving and loading
import java.io.*;
//class to hold level related variables
public class LevelAndUser implements ActionListener{
    //Custom or main level?
    boolean custom;
    //The level number from a list of levels(not used if custom level)
    int levelNo;
    //The name of the level
    String levelName;
    //The enemy level up frequency
    int enemyFrequency;
    //The arraylist containing the amount of enemies per round
    ArrayList<int[]> c = new ArrayList<int[]>();
    //The arraylist containing allies
    ArrayList<Integer> a = new ArrayList<Integer>();
    //ally Level ups
    ArrayList<int[]> lvlup = new ArrayList<int[]>();
    //The current round and final round
    int round, maxRound;
    //The difficulty of the level
    int difficulty;
    //The user number in the list of records and the name
    int userNo;
    String username;
    String customFilename, mainFilename;
    
    
    //ITems to provide an intrrface for the user to ype his name in
    TextField txtName = new TextField("No name");
    Button back = new Button("Back");
    Button next = new Button("Next");
    
    public LevelAndUser(int widthLimit, int heightLimit, String customFilename2, String mainFilename2){
        //Set the positions of items
        txtName.setBounds(100, heightLimit/2, widthLimit-200, 30);
        back.setBounds(3, heightLimit-40, 100, 40);
        back.addActionListener(this);
        next.setBounds(widthLimit-100, heightLimit-40, 100, 40);
        next.addActionListener(this);
        
        hideAll();
        
        customFilename = customFilename2;
        mainFilename = mainFilename2;
    }
    
    void showItems(){
        txtName.setVisible(true);
        back.setVisible(true);
        next.setVisible(true);
    }
    void hideAll(){
        txtName.setVisible(false);
        back.setVisible(false);
        next.setVisible(false);
    }
    
    //Print all the variales, for testing purposes
    void printAll(){
        System.out.println(custom);
        System.out.println(levelNo);
        System.out.println(levelName);
        System.out.println(enemyFrequency);
        System.out.println(round);
        System.out.println(maxRound);
        System.out.println(difficulty);
        System.out.println(userNo);
        System.out.println(username);
        
        for(int i = 0; i < c.size(); i++){
            for(int x = 0; x < c.get(i).length; x++){
                System.out.println(c.get(i)[x]);
            }
        }
        System.out.println("------");
        for(int i = 0; i < a.size(); i++){
            System.out.println(a.get(i));
        }
        System.out.println("-------");
        for(int i7 = 0; i7 < 3; i7++){
            for(int i6 = 0; i6 < 6; i6++){
                System.out.println(lvlup.get(i7)[i6]);
            }
        }
    }
    
    void saveUser(){
        //If the user is new or overwriting a previously saved user
        boolean newUser = false;
        try{
            if(custom == false){
                ArrayList<String> y = new ArrayList<String>();
                BufferedReader f = new BufferedReader(new FileReader(mainFilename));
                //read number of users
                int z;
                try{
                    z = Integer.parseInt(f.readLine());
                }catch(Exception ex){
                    z = 0;
                }
                
                for(int i = 0; i < z; i++){
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
                    for(int i2 = 0; i2 < 3; i2++){
                        for(int i6 = 0; i6 < 6; i6++){
                            y.add(f.readLine());
                        }
                    }
                }
                
                f.close();
                
                int x = 0;
                
                FileWriter f2 = new FileWriter(mainFilename);
                
                if(userNo > z){ z++; newUser = true;}
                
                f2.write(z +"\r\n");
                for(int i = 0; i < z; i++){
                    if(userNo == i+1){
                         //write user name
                        f2.write(username +"\r\n"); 
                        //write difficulty
                        f2.write(difficulty +"\r\n");
                        //write user round
                        f2.write(round +"\r\n");
                        //write Allies
                        for(int i2 = 0; i2 < 3; i2++){
                            f2.write(a.get(i2) +"\r\n");
                        }
                        //Write abilities
                        for(int i2 = 0; i2 < 3; i2++){
                            for(int i6 = 0; i6 < 6; i6++){
                                f2.write(lvlup.get(i2)[i6] +"\r\n");
                            }
                        }
                        if(newUser == false){
                            x += 24;
                        }
                    }else{
                        //write user name
                        f2.write(y.get(x) +"\r\n"); x++;
                        //write difficulty
                        f2.write(y.get(x) +"\r\n"); x++;
                        //write user round
                        f2.write(y.get(x) +"\r\n"); x++;
                        //write Allies
                        for(int i2 = 0; i2 < 3; i2++){
                            f2.write(y.get(x) +"\r\n"); x++;
                        }
                        //Read abilities
                        for(int i2 = 0; i2 < 3; i2++){
                            for(int i6 = 0; i6 < 6; i6++){
                                f2.write(y.get(x) +"\r\n"); x++;
                            }
                        }
                    }
                }
                f2.close();
            }else{
                BufferedReader f = new BufferedReader(new FileReader(customFilename));
                //The number of levels
                int xyz;
                
                try{
                    xyz = Integer.parseInt(f.readLine());
                }catch(Exception ex){
                    xyz = 0;
                }
                
                //z is the number of users for each level
                int z[] = new int[xyz];
                //y contains user records
                ArrayList<String> y = new ArrayList<String>();
                
                for(int i = 0; i < xyz; i++){
                    try{
                        z[i] = Integer.parseInt(f.readLine());
                    }catch(Exception ex){
                        z[i] = 0;
                    }
                
                    for(int i2 = 0; i2 < z[i]; i2++){
                        //read user name
                        y.add(f.readLine());
                        //Read difficulty
                        y.add(f.readLine());
                        //Read user round
                        y.add(f.readLine());
                        //Read Allies
                        for(int i3 = 0; i3 < 3; i3++){
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
                f.close();
                
                FileWriter f2 = new FileWriter(customFilename);
                
                f2.write(xyz +"\r\n");
                
                int x = 0;
                
                for(int i = 0; i < xyz; i++){
                    
                    if(levelNo == i && userNo > z[i]){ z[i]++; newUser = true;}
                    
                    f2.write(z[i] +"\r\n");
                    for(int i2 = 0; i2 < z[i]; i2++){
                        if(levelNo == i && userNo == i2+1){
                             //write user name
                            f2.write(username +"\r\n");
                            //write difficulty
                            f2.write(difficulty +"\r\n");
                            //write user round
                            f2.write(round +"\r\n");
                            //write Allies
                            for(int i3 = 0; i3 < 3; i3++){
                                f2.write(a.get(i3) +"\r\n");
                            }
                            //Read abilities
                            for(int i7 = 0; i7 < 3; i7++){
                                for(int i6 = 0; i6 < 6; i6++){
                                    f2.write(lvlup.get(i7)[i6] +"\r\n");
                                }
                            }
                            if(newUser == false){
                                x += 24;
                            }
                        }else{
                            //write user name
                            f2.write(y.get(x) +"\r\n"); x++;
                            //write difficulty
                            f2.write(y.get(x) +"\r\n"); x++;
                            //write user round
                            f2.write(y.get(x) +"\r\n"); x++;
                            //write Allies
                            for(int i3 = 0; i3 < 3; i3++){
                                f2.write(y.get(x) +"\r\n"); x++;
                            }
                            //Write abilities
                            for(int i7 = 0; i7 < 3; i7++){
                                for(int i6 = 0; i6 < 6; i6++){
                                    f2.write(y.get(x) +"\r\n"); x++;
                                }
                            }
                        }
                    }
                }
                f2.close();
            }
        }catch(Exception e){}
    }
    
    public void paint(Graphics g, int widthLimit, int heightLimit){
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Enter name here:", 100, heightLimit/2 - 20);
    }
    
    public void actionPerformed(ActionEvent e){
        hideAll();
        if(e.getSource() == next){
            username = txtName.getText();
        }
    }
}