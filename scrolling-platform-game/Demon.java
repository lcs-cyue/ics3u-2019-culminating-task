import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * This is the class for the "main character" in the action.
 * 
 * @author C. Yue
 * @version May 8, 2019
 */
public class Demon extends Actor
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Horizontal speed (change in horizontal position, or delta X)
    private int deltaX = 6;

    // Vertical speed (change in vertical position, or delta Y)
    private int deltaY = 4;

    // Acceleration for falls
    private int acceleration = 2;

    // Strength of a jump
    private int jumpStrength = -21;

    // Track current theoretical position in wider "scrollable" world
    private int currentScrollableWorldXPosition;

    // Track whether game is over or not
    private boolean isGameOver;

    // Constants to track vertical direction
    private static final String JUMPING_UP = "up";
    private static final String JUMPING_DOWN = "down";
    private String verticalDirection;

    // Constants to track horizontal direction
    private static final String FACING_RIGHT = "right";
    private static final String FACING_LEFT = "left";
    private String horizontalDirection;

    // For walking animation
    private GreenfootImage walkingRightImages[];
    private GreenfootImage walkingLeftImages[];
    private static final int WALK_ANIMATION_DELAY = 8;
    private static final int COUNT_OF_WALKING_IMAGES = 5;
    private int walkingFrames;
    private boolean isInWorld;

    private boolean hasKey;

    /**
     * Constructor
     * 
     * This runs once when the demon object is created.
     */
    Demon(int startingX)
    {
        // Set where demon begins horizontally
        currentScrollableWorldXPosition = startingX;

        // Game on
        isGameOver = false;

        hasKey = false;
        // First jump will be in 'down' direction
        verticalDirection = JUMPING_DOWN;

        // Facing right to start
        horizontalDirection = FACING_RIGHT;

        // Set image
        setImage("Demon0.png");

        // Initialize the 'walking' arrays
        walkingRightImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];
        walkingLeftImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];

        //The object is originally in the world
        isInWorld = true;

        // Load walking images from disk
        for (int i = 0; i < walkingRightImages.length; i++)
        {
            walkingRightImages[i] = new GreenfootImage("Demon" + i + ".png");

            // Create left-facing images by mirroring horizontally
            walkingLeftImages[i] = new GreenfootImage(walkingRightImages[i]);
            walkingLeftImages[i].mirrorHorizontally();
        }

        // Track animation frames for walking
        walkingFrames = 0;
    }

    /**
     * Act - do whatever the demon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeys();
        checkFall();
        foundKey();
        hasFoundKey();
        checkFire();

        if (isInWorld)
        {
            checkForRemoval();
        }

        //Allows the demon to rotate counter clockwise
        if (Greenfoot.isKeyDown("a"))
        { 
            setRotation(getRotation() - 5);
        }

        //Allows the demon to rotate clockwise
        if (Greenfoot.isKeyDown("d"))
        { 
            setRotation(getRotation() + 5);
        }

        //The method "isKeyDown" fires bullets as long as the 
        //key is pressed. Therefore, I changed it to the 
        //"getKey" method in order to fire only 1 bullet at a time

        //press "w" to fire bullets

        if ("w".equals(Greenfoot.getKey()))
        { 
            Fire();
        }

        if (!isGameOver)
        {
            checkGameOver();
        }

        //If demon doesn't have the key → (Everything else moves right so that it seems like) he goes back to the starting point 
        if (!isGameOver)
        {
            if( isTouching(InvisibleWall.class) && hasKey == false)
            {
                walkingFrames += 1;
                isInWorld = true;
                // Make the character appear to have moved back to the first plate
                moveEverythingBy(1170);
                Greenfoot.playSound("Failure.mp3");
                walkingFrames = 0; 
                
                if (walkingFrames == 90)
                {
                    walkingFrames += 1;
                    getWorld().showText("",100,200);
                }
            }
        }

        //score + 1 if gets star
        if (!isGameOver)
        {
            if(isTouching(Star.class))
            {
                removeTouching(Star.class);
                Greenfoot.playSound("Coin1.mp3");
                SideScrollingWorld world1 = (SideScrollingWorld)getWorld();
                Score score = world1.getScore();
                score.addScore();
            }
        }
    }

    private void checkFire()
    {
        if (Greenfoot.isKeyDown("w"))
        {
            Greenfoot.playSound("GunShot1.mp3");
        }
    }
     
    public boolean foundKey()
    {
        Actor Key = getOneObjectAtOffset(0, 0, Key.class);
        return Key != null;
    }

    /**
     * Check to see if demon has touched key for first time
     */
    public void hasFoundKey()
    {
        if (isTouching(Key.class) && hasKey == false)
        {
            hasKey = true;
            Greenfoot.playSound("Key.wav");
        }
    }
    
    /**
     * Move everything else by a given amount.
     */
    private void moveEverythingBy(int thisMuch)
    {
        // Set the demon;s position in the scrollable world
        currentScrollableWorldXPosition -= thisMuch;

        // Get world object reference
        SideScrollingWorld world = (SideScrollingWorld) getWorld();

        // Get a list of all platforms (objects that need to move
        // to make demon look like they are moving)
        List<Platform> platforms = world.getObjects(Platform.class);

        // Move all the platform objects to make it look like demon is moving
        for (Platform platform : platforms)
        {
            // Platforms move left to make demon appear to move right
            platform.moveRight(thisMuch);
        }

        // Get a list of all decorations (objects that need to move
        // to make demon look like they are moving)
        List<Decoration> decorations = world.getObjects(Decoration.class);

        // Move all the decoration objects to make it look like demon is moving
        for (Decoration decoration: decorations)
        {
            // Platforms move right to make demon appear to move left
            decoration.moveRight(thisMuch);
        }

        // Get a list of all farAwayItems (objects that need to move
        // to make demon look like they are moving)
        List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

        // Move all the tile objects to make it look like demon is moving
        for (FarAwayItem farAwayItem : farAwayItems)
        {
            // FarAwayItems move right to make demon appear to move left
            farAwayItem.moveRight(thisMuch / 4);
        }

    }

    /**
     * Fire the bullets
     */
    private void Fire()
    {
        Bullet bullet1 = new Bullet();
        getWorld().addObject(bullet1, getX(), getY());
        bullet1.setRotation(getRotation());
        bullet1.move(40.0);
    }

    /**
     * Respond to keyboard action from the user.
     */
    private void checkKeys()
    {
        // Walking keys
        if (Greenfoot.isKeyDown("left") && !isGameOver)
        {
            moveLeft();
        }
        else if (Greenfoot.isKeyDown("right") && !isGameOver)
        {
            moveRight();
        }
        else
        {
            // Standing still; reset walking animation
            walkingFrames = 0;
        }

        // Jumping
        if (Greenfoot.isKeyDown("space") && !isGameOver)
        {
            // Only able to jump when on a solid object
            if (onPlatform())
            {
                jump();
            }
        }
    }

    /**
     * Should the demon be falling right now?
     */
    public void checkFall()
    {
        if (onPlatform())
        {
            // Stop falling
            deltaY = 0;

            // Set image
            if (horizontalDirection == FACING_RIGHT && Greenfoot.isKeyDown("right") == false)
            {
                setImage("Demon-Right.png");
            }
            else if (horizontalDirection == FACING_LEFT && Greenfoot.isKeyDown("left") == false)
            {
                setImage("Demon-Left.png");
            }

            // Get a reference to any object that's created from a subclass of Platform,
            // that is below (or just below in front, or just below behind) the demon
            Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
            Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
            Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

            // Bump the demon back up so that they are not "submerged" in a platform object
            if (directlyUnder != null)
            {
                int correctedYPosition = directlyUnder.getY() - directlyUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (frontUnder != null)
            {
                int correctedYPosition = frontUnder.getY() - frontUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (rearUnder != null)
            {
                int correctedYPosition = rearUnder.getY() - rearUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
        }
        else
        {
            fall();
        }
    }

    /**
     * Is the demon currently touching a solid object? (any subclass of Platform)
     */
    public boolean onPlatform()
    {
        // Get an reference to a solid object (subclass of Platform) below the demon, if one exists
        Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
        Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
        Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

        // If there is no solid object below (or slightly in front of or behind) the demon...
        if (directlyUnder == null && frontUnder == null && rearUnder == null)
        {
            return false;   // Not on a solid object
        }
        else
        {
            return true;
        }
    }

    /**
     * Make the demon jump.
     */
    public void jump()
    {
        // Track vertical direction
        verticalDirection = JUMPING_UP;

        // Set image
        if (horizontalDirection == FACING_RIGHT)
        {
            setImage("Demon-Jump-Up-Right.png");
        }
        else
        {
            setImage("Demon-Jump-Up-Left.png");
        }

        // Change the vertical speed to the power of the jump
        deltaY = jumpStrength;

        // Make the character move vertically 
        fall();
    }

    /**
     * Make the demon fall.
     */
    public void fall()
    {
        // See if direction has changed
        if (deltaY > 0)
        {
            verticalDirection = JUMPING_DOWN;

            // Set image
            if (horizontalDirection == FACING_RIGHT)
            {
                setImage("Demon-Jump-Down-Right.png");
            }
            else
            {
                setImage("Demon-Jump-Down-Left.png");
            }
        }

        // Fall (move vertically)
        int newVisibleWorldYPosition = getY() + deltaY;
        setLocation(getX(), newVisibleWorldYPosition );

        // Accelerate (fall faster next time)
        deltaY = deltaY + acceleration;
    }

    /**
     * Animate walking
     */
    private void animateWalk(String direction)
    {
        // Track walking animation frames
        walkingFrames += 1;

        // Get current animation stage
        int stage = walkingFrames / WALK_ANIMATION_DELAY;

        // Animate
        if (stage < walkingRightImages.length)
        {
            // Set image for this stage of the animation
            if (direction == FACING_RIGHT)
            {
                setImage(walkingRightImages[stage]);
            }
            else
            {
                setImage(walkingLeftImages[stage]);
            }
        }
        else
        {
            // Start animation loop from beginning
            walkingFrames = 0;
        }
    }

    /**
     * Move the demon to the right.
     */
    public void moveRight()
    {
        // Track direction
        horizontalDirection = FACING_RIGHT;

        // Set image 
        if (onPlatform())
        {
            animateWalk(horizontalDirection);
        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("Demon-Jump-Up-Right.png");
            }
            else
            {
                setImage("Demon-Jump-Down-Right.png");
            }
        } 

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        if (isTouching(Door.class) && hasKey == true)
        {
            Win();
            Greenfoot.stop(); 
        }

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldXPosition < world.HALF_VISIBLE_WIDTH)
        {
            // demon IS WITHIN EXTREME LEFT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move to right in visible world
            int newVisibleWorldXPosition = getX() + deltaX;
            setLocation(newVisibleWorldXPosition, getY());

            // Track position in wider scrolling world
            currentScrollableWorldXPosition = getX();
        }
        else if (currentScrollableWorldXPosition + deltaX * 2 > world.SCROLLABLE_WIDTH - world.HALF_VISIBLE_WIDTH)
        {
            // demon IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Allow movement only when not at edge of world
            if (currentScrollableWorldXPosition < world.SCROLLABLE_WIDTH - this.getImage().getWidth() / 2)
            {
                // Move to right in visible world
                int newVisibleWorldXPosition = getX() + deltaX;
                setLocation(newVisibleWorldXPosition, getY());

                // Track position in wider scrolling world
                currentScrollableWorldXPosition += deltaX;
            }
            // else
            // {
            // isGameOver = true;
            // world.setGameOver();

            // // Tell the user game is over
            // world.showText("LEVEL COMPLETE", world.getWidth() / 2, world.getHeight() / 2);
            // }

        }
        else
        {
            // demon IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of demon moving

            // Track position in wider scrolling world
            currentScrollableWorldXPosition += deltaX;

            // Get a list of all platforms (objects that need to move
            // to make demon look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects to make it look like demon is moving
            for (Platform platform : platforms)
            {
                // Platforms move left to make demon appear to move right
                platform.moveLeft(deltaX);
            }

            // Get a list of all decorations (objects that need to move
            // to make demon look like they are moving)
            List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like demon is moving
            for (Decoration decoration: decorations)
            {
                // Platforms move left to make demon appear to move right
                decoration.moveLeft(deltaX);
            }

            // Get a list of all farAwayItems (objects that need to move
            // to make demon look like they are moving)
            List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the tile objects to make it look like demon is moving
            for (FarAwayItem farAwayItem : farAwayItems)
            {
                // FarAwayItems move left to make demon appear to move right
                farAwayItem.moveLeft(deltaX / 4);
            }

        }   

    }

    private void Win()
    {
        Greenfoot.playSound("Win1.wav");
        YouWin win1 = new YouWin(320, 240);
        getWorld().addObject(win1, 320, 240);
    }

    /**
     * Move the demon to the left.
     */
    public void moveLeft()
    {
        // Track direction
        horizontalDirection = FACING_LEFT;

        // Set image 
        if (onPlatform())
        {
            animateWalk(horizontalDirection);
        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("Demon-Jump-Up-Left.png");
            }
            else
            {
                setImage("Demon-Jump-Down-Left.png");
            }
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldXPosition - deltaX < world.HALF_VISIBLE_WIDTH)
        {
            // demon IS WITHIN EXTREME LEFT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Don't let demon go off left edge of scrollable world 
            // (Allow movement only when not at left edge)
            if (currentScrollableWorldXPosition > 0)
            {
                // Move left in visible world
                int newVisibleWorldXPosition = getX() - deltaX;
                setLocation(newVisibleWorldXPosition, getY());

                // Track position in wider scrolling world
                currentScrollableWorldXPosition = getX();
            }            
        }
        else if (currentScrollableWorldXPosition + deltaX * 2 > world.SCROLLABLE_WIDTH - world.HALF_VISIBLE_WIDTH)
        {
            // demon IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move left in visible world
            int newVisibleWorldXPosition = getX() - deltaX;
            setLocation(newVisibleWorldXPosition, getY());

            // Track position in wider scrolling world
            currentScrollableWorldXPosition -= deltaX;
        }        
        else
        {
            // demon IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of demon moving

            // Track position in wider scrolling world
            currentScrollableWorldXPosition -= deltaX;

            // Get a list of all platforms (objects that need to move
            // to make demon look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects at same speed as demon
            for (Platform platform : platforms)
            {
                // Platforms move right to make demon appear to move left
                platform.moveRight(deltaX);
            }

            // Get a list of all decorations (objects that need to move
            // to make demon look like they are moving)
            List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like demon is moving
            for (Decoration decoration: decorations)
            {
                // Platforms move right to make demon appear to move left
                decoration.moveRight(deltaX);
            }

            // Get a list of all items that are in the distance (far away items)
            List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the FarAwayItem objects at one quarter speed as demon to create depth illusion
            for (FarAwayItem farAwayItem : farAwayItems)
            {
                // FarAwayItems move right to make demon appear to move left
                farAwayItem.moveRight(deltaX / 4);
            }

        } 

    }

    private void checkForRemoval()
    {
        // remove if touching demon

        if( isTouching(Ghost.class))
        {
            isInWorld = false;
            displayGameOver();
        }
        
        if( isTouching(Skull.class))
        {
            isInWorld = false;
            displayGameOver();
        }

        if( isTouching(PullBack.class))
        {
            setLocation (getX()-150, getY());
            Greenfoot.playSound("Laughter.mp3");
        }

    } 

    private void displayGameOver () 
    {
        GameOver gameOver = new GameOver(320, 240);
        getWorld().addObject(gameOver,getWorld().getWidth()/2, getWorld().getHeight()/2);
        Greenfoot.playSound("Gameover.wav");
        Greenfoot.playSound("Laughter.mp3");
        Greenfoot.stop();
    }

    /**
     * When the demon falls off the bottom of the screen,
     * game is over. We must remove them.
     */
    public void checkGameOver()
    {
        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Vertical position where demon no longer visible
        int offScreenVerticalPosition = (world.getHeight() + this.getImage().getHeight() / 2);

        // Off bottom of screen?
        if (this.getY() > offScreenVerticalPosition)
        {
            // Remove the demon
            isGameOver = true;
            displayGameOver();
        }
    }
}
