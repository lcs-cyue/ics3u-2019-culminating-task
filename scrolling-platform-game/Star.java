import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Star here.
 * 
 * @author C. Yue 
 * @version (a version number or a date)
 */
public class Star extends Decoration
{
    private boolean isInWorld;
    private int frames;

    /**
     * Act - do whatever the Star wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Star(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);

        isInWorld = true;
        frames = 0;
    }

    public void act() 
    {

        if (isInWorld)
        {
            checkForRemoval();
        }
        else
        {
            frames += 1;
            setImage("Star-invisible.png");
            
            // Now after 30 frames remove the text
            if (frames == 30)
            {
                getWorld().showText("", 340, 350);
                
                
            }

        }
        
        //Twinkling star
        frames = frames + 1;
        if(frames % 30 == 0)
        {
           setImage("Star-invisible.png");
        }
        else
        {
            setImage("Star.png");
        }
    }   

    private void checkForRemoval()
    {
        // remove if touching demon
        if( isTouching(Demon.class))
        {
            isInWorld = false;
            getWorld().showText("Good Job!",340,350);

        }

    } 
}
