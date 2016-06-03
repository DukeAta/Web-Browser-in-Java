/**
 * Class About.
 * This class provides the information about the web browser.
 *
 * @author ATA Ubong 
 * @version 17/12/2010
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class About extends JWindow {
    
    private JButton exitButton;
  
    public About() {}
    
    // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor
    public void showAbout() 
    {
        
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.yellow);
        
        // Set the window's bounds, centering the window
        int width = 350;
        int height =150;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        
        // Build the splash screen
        JLabel label = new JLabel();
        JLabel copyright = new JLabel
        ("Copyright 2010", JLabel.CENTER);
        copyright.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label,BorderLayout.CENTER);
        content.add(copyright, BorderLayout.SOUTH);
        
         //create an exit button
         ImageIcon exitIcon = new ImageIcon("images/from.png");
         exitButton = new JButton(exitIcon);
         exitButton.setBorder(BorderFactory.createEmptyBorder());
         exitButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
                 setVisible(false);       
            }
         }
         );
        content.add(exitButton);
         setVisible(true);
    }
}
