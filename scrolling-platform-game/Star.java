import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Star here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Star extends Decoration
{
    private boolean isInWorld;

    /**
     * Act - do whatever the Star wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Star(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);

        isInWorld = true;
    }

    public void act() 
    {
        if (isInWorld)
        {
            checkForRemoval();
        }
    }   

    private void checkForRemoval()
    {
        // remove if touching demon
        if( isTouching(Demon.class))
        {
            isInWorld = false;
            getWorld().showText("+1",550,140);
            getWorld().removeObject(this);
        }

    } 
}
