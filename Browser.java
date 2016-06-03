import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;


public class Browser extends JFrame implements HyperlinkListener
{
    // These are the buttons for iterating through the page list.
    private JButton backButton, forwardButton, sourceButton;
    private JButton homeButton, refreshButton;

    // Page location text field.
    private JTextField addressField;

    // Editor pane for displaying pages.
    private JEditorPane page;

    // Browser's list of pages that have been visited.
    private ArrayList pageList = new ArrayList();
    
    //Users default setting and data
    private final Usersetting data;

    // Constructor for Mini Web Browser.
    public Browser(Usersetting set)    
    {
        // Set application title.
        super("Duke's Web Browser");
        
        data = set;
        
        // Set window size.
        setSize(960,640);

        // Handle closing events.
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                actionExit();
            }
        }
        );

        /**
        * MENU BAR AND MENU OPTIONS
        */
        
        // Set up file menu.
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenuItem fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        //option to set current page as homepage
        JMenuItem setHomepage = new JMenuItem("Set as homepage");
        setHomepage.addActionListener (new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
               data.setHomepage(page.getPage().toString());            
            }
        });
        fileMenu.add(setHomepage);
       
        //Option to View page source
        JMenuItem pageSource= new JMenuItem("Page Source");
        pageSource.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                System.out.println(page.getText());
            }
        }
        );
        fileMenu.add(pageSource);
          
        //Option to close the browser
        JMenuItem exitBrowser= new JMenuItem("Exit",KeyEvent.VK_X);
        exitBrowser.addActionListener(new ActionListener() 
        {
         
            public void actionPerformed(ActionEvent e)
            {
                actionExit();
            }
        });   
        fileMenu.add(exitBrowser);

        //Option to add Bookmarks and Favorites
        final JMenu bookmarkMenu = new JMenu("Bookmarks");
        menuBar.add(bookmarkMenu);
       
        //add Bookmark
        JMenuItem bookMark = new JMenuItem("Add Bookmark");
        bookMark.addActionListener(new ActionListener()
        {
            URL url = null;
            public void actionPerformed(ActionEvent event)
            {
                final String pagelocation = page.getPage().toString();
                
                final JMenuItem address = new JMenuItem(pagelocation);
                address.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                        try
                        {
                            url = new URL(pagelocation);
                            showPage(url,true);
                        }
                        catch (Exception e){}          
                    }
                });        
                bookmarkMenu.add(address);
            }
        });
        bookmarkMenu.add(bookMark);
        
        //Option to keep track of the History
        final JMenu historyMenu = new JMenu("History");
        menuBar.add(historyMenu);
        
        //Display The History
        JMenuItem showHistory = new JMenuItem("Display History");
        showHistory.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                data.showHistory();
            }
        });
        historyMenu.add(showHistory);
         
        //option to delete the History
        JMenuItem delHistory = new JMenuItem("Delete History");
        delHistory.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                data.deleteHistory();
            }
        });
        historyMenu.add(delHistory);
         
        //Option to give brief info about the browser
        JMenuItem helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        
        JMenuItem help= new JMenuItem("Help");
        help.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                data.displayHelp().showHelp();
            }
        });
        helpMenu.add(help);
        
        JMenuItem about= new JMenuItem("About Browser");
        about.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                data.displayAbout().showAbout();
            }
        });
        helpMenu.add(about);
        
        /**
        * TOP PANE, BUTTONS, ADDRESS BAR, SEARCH
        */
        
        // Set up button panel.
        JPanel buttonPanel = new JPanel();
        
        //Go to the prevoius Page
        ImageIcon backIcon = new ImageIcon("images/back.png");
        backButton = new JButton(backIcon);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                History address = new History(page.getPage().toString());
                data.addHistory(address);
                actionBack();
            }
        });  
        backButton.setEnabled(false);
        buttonPanel.add(backButton);
        
        //Go to the next Page
        ImageIcon forwardIcon = new ImageIcon("images/forward.png");
        forwardButton = new JButton(forwardIcon);
        forwardButton.setBorder(BorderFactory.createEmptyBorder());
        forwardButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                History address = new History(page.getPage().toString());
                data.addHistory(address);
                actionForward();
            }
        });
        forwardButton.setEnabled(false);
        buttonPanel.add(forwardButton);
        
        //Go to the home page
        ImageIcon homeIcon = new ImageIcon("images/go-home.png");
        homeButton = new JButton(homeIcon);
        homeButton.setBorder(BorderFactory.createEmptyBorder());
        homeButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {               
                String url = data.homepage();       
                try
                {
                    page.setPage(new URL(url));
                    addressField.setText(url);
                    History address = new History(page.getPage().toString());
                    data.addHistory(address);
                }
                catch(IOException ioe)
                {
                    System.out.println("Can't follow link to " + url + ": " + ioe);
                }
            }
        }
        );
        buttonPanel.add(homeButton);
          
        addressField = new JTextField(35);
        addressField.setText(data.homepage());
        addressField.addActionListener(new goToPage());
        buttonPanel.add(addressField);
        
        //Go to typed URL
        ImageIcon goIcon = new ImageIcon("images/go.png");
        JButton goButton = new JButton(goIcon);
        goButton.setBorder(BorderFactory.createEmptyBorder());
        goButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                History address = new History(page.getPage().toString());
                data.addHistory(address);
                actionGo();
            }
        }   
        );
        buttonPanel.add(goButton);
        
        //Refresh the web page
        ImageIcon refreshIcon = new ImageIcon("images/reload.png");
        JButton refreshButton = new JButton(refreshIcon);
        refreshButton.setBorder(BorderFactory.createEmptyBorder());
        refreshButton.addActionListener(new ActionListener() 
        {
            URL url =null;
            public void actionPerformed(ActionEvent e)
            {
                String address = page.getPage().toString();
                try 
                {
                    page.getDocument(); 
                    url = new URL(address);
                    Thread.sleep(1000);
                    addressField.setText(address);
                    page.setPage(url);
                } 
                catch(IOException ioe) 
                {
                    System.out.println("Can't follow link to " + url + ": " + ioe);
                } 
                catch (InterruptedException ie) {}
                showPage(url,true);
            }

        });
        buttonPanel.add(refreshButton);
        
        //Set up page display.
        page = new JEditorPane();
        page.setContentType("text/html");
        page.setEditable(false);
        page.addHyperlinkListener(this);
        
        String url = data.homepage();
        try 
        {
            page.setPage(url);
            addressField.setText(url);
            History address = new History(page.getPage().toString());
            data.addHistory(address);
        } 
        catch(IOException ioe) 
        {
            System.out.println("Can't follow link to " + url + ": " + ioe);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(page),
        BorderLayout.CENTER);
    }

    // Exit this program.
    private void actionExit() 
    {
        System.exit(0);
    }


    // Go back to the page viewed before the current page.
    private void actionBack() 
    {
        URL currentUrl = page.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try 
        {
            showPage(
            new URL((String) pageList.get(pageIndex - 1)), false);
        }
        catch (Exception e) {}
    }

    // Go forward to the page viewed after the current page.
    private void actionForward() 
    {
        URL currentUrl = page.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try 
        {
            showPage(
            new URL((String) pageList.get(pageIndex + 1)), false);
        }
        catch (Exception e) {}
    }

    // Reload and show the page specified in the location text field.
    private void actionGo()
    {
        URL verifiedUrl = verifyUrl(addressField.getText());
        if (verifiedUrl != null)
        {
            showPage(verifiedUrl, true);
            updateButtons();
        } 
        else 
        {
            showError("Invalid URL");
        }
    }

    // Show dialog box with error message.
    private void showError(String errorMessage) 
    {
        JOptionPane.showMessageDialog(this, errorMessage,
        "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Verify URL format.
    private URL verifyUrl(String url) 
    {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
        return null;

        // Verify format of URL.
        URL verifiedUrl = null;
        try 
        {
            verifiedUrl = new URL(url);
        } 
        catch (Exception e) 
        {
        return null;
        }
        
        // Update buttons based on the page being displayed.
        updateButtons();
        return verifiedUrl;
    }

    //Show the specified page and add it to the page list if specified
    private void showPage(URL pageUrl, boolean addToList)
    {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try 
        {
            // Get URL of page currently being displayed.
            URL currentUrl = page.getPage();

            // Load and display specified page.
            page.setPage(pageUrl);

            // Get URL of new page being displayed.
            URL newUrl = page.getPage();

            // Add page to list if specified.
            if (addToList)
            {
                int listSize = pageList.size();
                if (listSize > 0) 
                {
                    int pageIndex =
                    pageList.indexOf(currentUrl.toString());
                    if (pageIndex < listSize - 1)
                    {
                        for (int i = listSize - 1; i > pageIndex; i--) 
                        {
                            pageList.remove(i);
                        }
                    }
                }
                pageList.add(newUrl.toString());
            }
            
            // Update location text field with URL of current page.
            addressField.setText(newUrl.toString());

            // Update buttons based on the page being displayed.
            updateButtons();
            }
            catch (Exception e)
            {
            // Show error messsage.
            showError("Unable to load page");
        }
        finally
        {
        // Return to default cursor.
        setCursor(Cursor.getDefaultCursor());
        }
    }

    //Update back and forward buttons based on the page being displayed. 
    private void updateButtons() 
    {
        if (pageList.size() < 2) 
        {
            backButton.setEnabled(false);
            forwardButton.setEnabled(false);
        } 
        else 
        {
            URL currentUrl = page.getPage();
            int pageIndex = pageList.indexOf(currentUrl.toString());
            backButton.setEnabled(pageIndex > 0);
            forwardButton.setEnabled(
            pageIndex < (pageList.size() - 1));
        }
    }

    // Handle hyperlink's being clicked.
    public void hyperlinkUpdate(HyperlinkEvent event) 
    {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED)
        {
            if (event instanceof HTMLFrameHyperlinkEvent) 
            {
                HTMLFrameHyperlinkEvent linkEvent =
                (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document =
                (HTMLDocument) page.getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);
            } 
            else 
            {
                showPage(event.getURL(), true);
                History address = new History(page.getPage().toString());
                data.addHistory(address);
            }
        }
    }
    class goToPage implements ActionListener
    {
        String url;
         
        goToPage(){}
         
        //constructor that set the url to that passed by the event
        public goToPage(String url)
        {
            this.url = url;
        }
         
        public void actionPerformed(ActionEvent event)
        {
            //sets url appropriately if address is entered from the text field
            if (event.getSource() == addressField) 
            url = addressField.getText();     
            try 
            {
                page.setPage(new URL(url));
                addressField.setText(url);
                History address = new History(page.getPage().toString());
                data.addHistory(address);
                updateButtons();
            } 
            catch(IOException ioe) 
            {
                System.out.println("Can't follow link to " + url + ": " + ioe);
            }
        }
    }
}
