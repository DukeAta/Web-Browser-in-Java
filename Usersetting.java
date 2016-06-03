/**
 * Class Usersetting.
 * Stores the user data and setting
 * 
 * @author ATA Ubong  
 * @version 17/12/2010
 */

import java.util.ArrayList;

public class Usersetting
{
    //homepage variable
    public String homepage = "http://www.ncl.ac.uk/";
    public String currentpage = "";
    About thisAbout = new About();
    Help thisHelp = new Help();
    
    //create an array lists to store user favorites and history
    public ArrayList<Bookmarks> favories = new ArrayList<Bookmarks>();
    public ArrayList<History> keepTrack = new ArrayList<History>();
    
    //create a new user data object
    public Usersetting(){}
    
    //return the homepage url
    public String homepage()
    {
        return homepage;
    }
    
    //set the homepage url
    public void setHomepage(String homepageSet)
    {
        homepage = homepageSet;
    }
    
    public String savePage (String save)
    {
        currentpage = save;
        return currentpage;
    }
    
    //add current page to history
    public void addHistory(History hist)
    {
        keepTrack.add(hist);
    }
    
    //display the history
    public void showHistory()
    {
        String trace="";
        for(int i=0; i<keepTrack.size(); i++)
        {
            trace = trace + keepTrack.get(i).toString()+"\n";
        }
        
        if(keepTrack.size()!=0)
        {
            System.out.println("CURRENT HISTORY" +"\n"+ trace);
        }
        else
        {
            System.out.println("NO HISTORY\n");
        }
    }
    
    //clear the history
    public void deleteHistory()
    {    
        boolean ok = true;
		while(ok!=false)
		{
			if(keepTrack.size()!=0)
			{ 
				int i=0;
				keepTrack.remove(i);
				i++;
			}
			else
			{
				break;
			}
		}
        System.out.println("HISTORY DELETED\n");
    }
    
    //display the about content
    public About displayAbout()
    {
        return thisAbout;
    }
    
    public Help displayHelp()
    {
        return thisHelp;
    }
}