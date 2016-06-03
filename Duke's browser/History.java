/**
 * Class History .
 * Construction of the history
 *
 * @author Ata Ubong 
 * @version 17/12/2010
 */
public class History
{
    
    private String url; //url of the current page visited
	
    public History(String url)
    {
        this.url = url;
    }
    
	//return the address of the page into string
    public String toString()
    {
        return url;
    }
}
