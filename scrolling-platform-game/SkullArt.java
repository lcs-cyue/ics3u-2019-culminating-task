import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SkullArt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SkullArt extends Decoration
{
    int counter = 0;
    /**
     * Act - do whatever the SkullArt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    SkullArt(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
    }
    
    public void act() 
    {
        counter = counter + 1;
        
        if(counter % 60 == 0)
        {
           setImage("Skull233.png");
        }
        else
        {
            setImage("Skull666.png");
        }
    } 
}
