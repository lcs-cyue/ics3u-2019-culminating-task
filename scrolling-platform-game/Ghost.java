import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ghost here.
 * 
 * @author C. Yue
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
    private int Frames;
    private static final int ANIMATION_DELAY = 8;
    
    Ghost(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
        setImage("Ghost1.png");

        flyingRightImages = new GreenfootImage[COUNT_OF_FLYING_IMAGES];

        // Load images from disk
        for (int i = 0; i < flyingRightImages.length; i++)
        {
            flyingRightImages[i] = new GreenfootImage("Ghost" + i + ".png");
            
        }
        
        isInWorld = true;
        Frames = 0;
    }   

     private void Move()
    {
        // Track animation frames
        Frames += 1;

        // Get current animation stage
        int stage = Frames / ANIMATION_DELAY;

        if (stage < flyingRightImages.length)
        {
            // Set image for this stage of the animation
            setImage(flyingRightImages[stage]);

        }
        else
        {
            Frames = 0;
        }
     
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
            Frames += 1;
            setImage("Star-invisible.png");
            // Now after 30 frames remove the text
            if (Frames == 60)
            {
                getWorld().showText("", 100, 350);
            }

        }
        
        Move();
    }      

    private void checkForRemoval()
    {
        // remove if touching demon
        if( isTouching(Bullet.class))
        {
            Greenfoot.playSound("Death1.wav");
            isInWorld = false;
            getWorld().removeObject(this);
        }
        

    } 
}
