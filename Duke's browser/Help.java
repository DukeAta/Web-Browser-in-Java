import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
/**
 * Write a description of class Help here.
 * 
 * @author ATA Ubong
 * @version 17/12/2010
 */
public class Help
{
    Scanner read;
    ArrayList helpList = new ArrayList();
    public Help()
    {
        try
        {
            FileReader DataFile = new FileReader("help.txt"); //read the file which content the data
            read = new Scanner(DataFile);
            while(read.hasNextLine())
            {
                String S = read.nextLine() + "\n";
                helpList.add(S); 
            }
        }
        catch(IOException e){}
    }
    
    
    public void showHelp()
    {
        System.out.println(helpList);
    }
}
