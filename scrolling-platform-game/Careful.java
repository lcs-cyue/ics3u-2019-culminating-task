import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Careful here.
 * 
 * @author C. Yue 
 * @version (a version number or a date)
 */
public class Careful extends Decoration
{
    int counter = 0;

    /**
     * Act - do whatever the Careful wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Careful(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
    }

    public void act() 
    {
        counter = counter + 1;
        
        if(counter % 60 == 0)
        {
           setImage("carefulw.png");
        }
        else
        {
            setImage("carefulp.png");
        }
    }
}
