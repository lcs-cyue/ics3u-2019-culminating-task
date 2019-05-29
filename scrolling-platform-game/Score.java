import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Counter here.
 * 
 * @author C. Yue 
 * @version (a version number or a date)
 */   
public class Score extends Actor
{
    int score = 0;
    //add 1 point if Demon gets a star 
    /**
     * Act - do whatever the Counter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setImage(new GreenfootImage("Score : " + score, 24, greenfoot.Color.BLACK, greenfoot.Color.WHITE));
    }  
    
    public void addScore()
    {
        score++;
    }
    
}
