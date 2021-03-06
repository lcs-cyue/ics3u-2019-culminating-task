import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author C. Yue 
 * @version (a version number or a date)
 */
public class Instructions extends World
{

    private GreenfootSound backgroundMusic;


    public Instructions()
    {    
        // Create a new world with 649x480 cells with a cell size of 1x1 pixels.
        super(640, 480, 1); 

        //Play background music 
        backgroundMusic = new GreenfootSound("Instructions.mp3");
        backgroundMusic.playLoop();

        prepare();
    }

    public void prepare() 
    {
        InstructionsText text= new InstructionsText(getWidth()/2, getHeight()/2);
        addObject(text, getWidth()/2, getHeight()/2);
        SkullArt skull1= new SkullArt(575, 63);
        addObject(skull1, 575, 63);
    }

    public void act()
    {
        change();
    }

    private void change()
    {
        //set the SideScrollingWorld after pressing enter, stop the current background music
        if (Greenfoot.isKeyDown ("enter"))
        {
            backgroundMusic.stop();
            Greenfoot.setWorld(new SideScrollingWorld());
        }
    }
}
