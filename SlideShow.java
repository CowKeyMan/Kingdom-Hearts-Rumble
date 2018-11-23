//Imported for using butons and images
import java.awt.*;
//To use action listener
import java.awt.event.*;
public class SlideShow implements ActionListener{
    //The buttons to be used in this class
    Button btnPrevious = new Button("Previous"), btnNext = new Button("Next"), btnBack = new Button("Exit");
    //The images to be viewed as slideshows
    Image img[];
    //The caption to be displayed with each image
    String caption[];
    //The current image to be displayed(the array number)
    int imgCounter = 0;
    //The image which changes and which is to be displayed on the screen
    Image background;
    
    //Constructor to set the variables
    public SlideShow(int widthLimit, int heightLimit, Image image[]){
        //Set button position and implement action listener
        btnPrevious.setBounds(0, heightLimit/2, 100, 35);
        btnPrevious.addActionListener(this);
        btnNext.setBounds(widthLimit-100, heightLimit/2, 100, 35);
        btnNext.addActionListener(this);
        btnBack.setBounds(0, heightLimit-35, 100, 35);
        btnBack.addActionListener(this);
        
        //Set the images for the slideshow
        img = image;
    }
    
    //Display the items
    void showItems(){
        btnPrevious.setVisible(true);
        //The previous button is initially blurred out since there is no previous image
        btnPrevious.setEnabled(false);
        btnNext.setVisible(true);
        if(img.length <= 1){
            btnNext.setEnabled(false);
        }else{
            btnNext.setEnabled(true);
        }
        btnBack.setVisible(true);
    }
    
    //Hide the items
    void hideItems(){
        btnBack.setVisible(false);
        btnNext.setVisible(false);
        btnPrevious.setVisible(false);
    }
    
    //The action listener
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnPrevious){
            //Reduce the image counter
            imgCounter--;
            background = img[imgCounter];
            //If there are no images before it, this button is hidden
            if(imgCounter == 0){
                btnPrevious.setEnabled(false);
            }
            btnNext.setEnabled(true);
        }else if(e.getSource() == btnNext){
            //increase the image counter
            imgCounter++;
            background = img[imgCounter];
            //If there are no images after it, this button is hidden
            if(imgCounter == img.length-1){
                btnNext.setEnabled (false);
            }
            btnPrevious.setEnabled(true);
        }else if(e.getSource() == btnBack){
            //Hide everything and reset the image counter
            hideItems();
            imgCounter = 0;
            background = img[imgCounter];
        }
    }
}