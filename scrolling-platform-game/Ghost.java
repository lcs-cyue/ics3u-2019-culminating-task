import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ghost here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ghost extends Decoration
{
    /**
     * Act - do whatever the Ghost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage flyingRightImages[];
    private static final int COUNT_OF_FLYING_IMAGES = 7;
    private boolean isInWorld;
    private int frames;
    
    Ghost(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);

        flyingRightImages = new GreenfootImage[COUNT_OF_FLYING_IMAGES];

        // Load images from disk
        for (int i = 0; i < flyingRightImages.length; i++)
        {
            flyingRightImages[i] = new GreenfootImage("Ghost" + i + ".png");
            
        }
        
        isInWorld = true;
        frames = 0;
    }   

    public void act() 
    {
        //Slowly move to left
        setLocation (getX()-3, getY());  
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
    }      

    private void checkForRemoval()
    {
        // remove if touching demon
        if( isTouching(Bullet.class))
        {
            isInWorld = false;
            getWorld().showText("Ahhh",340,350);

        }

    } 
}
