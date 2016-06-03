/**
 * Class Test.
 * Run a sample Test of the program
 *
 * @author Ubong ATA
 * @version 17/12/2010
 */
public class Test
{
    // Run the Browser.
    public static void main(String[] args) 
    {
        Usersetting data = new Usersetting();
        Browser browser = new Browser(data);
        browser.show();
    }
}
