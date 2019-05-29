import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Skull here.
 * 
 * @author C. Yue
 * @version (a version number or a date)
 */
public class Skull extends Decoration
{
    private GreenfootImage animation[];
    private static final int COUNT_OF_ANIMATIONS = 4;
    private int Frames;
    private static final int ANIMATION_DELAY = 4;

    Skull(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
        setImage("Skull0.png");
        
        animation = new GreenfootImage[COUNT_OF_ANIMATIONS];

        for (int i = 0; i < animation.length; i++)
        {
            animation[i] = new GreenfootImage("Skull" + i + ".png");
        }
        Frames = 0;
    }

    private void Move()
    {
        // Track animation frames
        Frames += 1;

        // Get current animation stage
        int stage = Frames / ANIMATION_DELAY;

        if (stage < animation.length)
        {
            // Set image for this stage of the animation
            setImage(animation[stage]);

        }
        else
        {
            Frames = 0;
        }
     
    }
    
    public void act() 
        {
            Move();
        }   
    }
