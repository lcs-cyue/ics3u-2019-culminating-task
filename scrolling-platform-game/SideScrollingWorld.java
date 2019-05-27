
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Template for a side-scrolling platform game.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class SideScrollingWorld extends World
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Tile size in pixels for world elements (blocks, clouds, etc)
    // TO STUDENTS: Modify if your game's tiles have different dimensions
    private static final int TILE_SIZE = 32;
    private static final int HALF_TILE_SIZE = TILE_SIZE / 2;

    // World size constants
    // TO STUDENTS: Modify only if you're sure
    //              Should be a resolution that's a multiple of TILE_SIZE
    private static final int VISIBLE_WIDTH = 640;
    private static final int VISIBLE_HEIGHT = 480;

    // Additional useful constants based on world size
    public static final int HALF_VISIBLE_WIDTH = VISIBLE_WIDTH / 2;
    private static final int HALF_VISIBLE_HEIGHT = VISIBLE_HEIGHT / 2;

    // Defining the boundaries of the scrollable world
    // TO STUDENTS: Modify SCROLLABLE_WIDTH if you wish to have a longer level
    public static final int SCROLLABLE_WIDTH = VISIBLE_WIDTH * 3;
    private static final int SCROLLABLE_HEIGHT = VISIBLE_HEIGHT;

    //Demon
    Demon theDemon;

    // Track whether game is on
    private boolean isGameOver;

    /**
     * Constructor for objects of class SideScrollingWorld.
     */
    public SideScrollingWorld()
    {    
        // Create a new world with 640x480 cells with a cell size of 1x1 pixels.
        // Final argument of 'false' means that actors in the world are not restricted to the world boundary.
        // See: https://www.greenfoot.org/files/javadoc/greenfoot/World.html#World-int-int-int-boolean-
        super(VISIBLE_WIDTH, VISIBLE_HEIGHT, 1, false);

        // Set up the starting scene
        setup();
        setBackground(new GreenfootImage("Castle2.png"));

        
        // Game on
        isGameOver = false;
    }

    /**
     * Set up the entire world.
     */
    private void setup()
    {
        // TO STUDENTS: Add, revise, or remove methods as needed to define your own game's world
        addLeftGround();
        addFences();
        addPlateSteps();
        addClouds();
        addRightGround();
        addHero();

        for (int i = 12; i <= 14; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - 2 *TILE_SIZE + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }
    }

    /**
     * Add blocks to create the ground to walk on at bottom-left of scrollable world.
     */
    private void addLeftGround()
    {
        // How many tiles will cover the bottom of the initial visible area of screen?
        final int tilesToCreate = getWidth() / TILE_SIZE;

        //Loop to create and add the Ground tile objects
        for (int i = 0; i <= 10; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 2 + HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }

        // Add GroundBelow blocks immediately below Ground object just made above
        for (int i = 0; i <= 10; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - TILE_SIZE + HALF_TILE_SIZE;

            // Create a ground tile
            GroundBelow groundTile = new GroundBelow(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }

        for (int i = 8; i <= 10; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 7 + HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }
    }

    /**
     * Add blocks to create the ground to walk on at top-right of scrollable world.
     */
    private void addRightGround()
    {
        // Constants to control dimensions of the ground at end of world
        final int COUNT_OF_GROUND = 8;
        final int GROUND_BELOW_COLUMNS = COUNT_OF_GROUND;
        final int GROUND_BELOW_ROWS = 6;
        final int COUNT_OF_GROUND_BELOW = GROUND_BELOW_COLUMNS * GROUND_BELOW_ROWS;

        // 1. Make ground at end of level (top layer)
        // for (int i = 0; i <= 10; i += 1)
        // {
        // for (int j = 0; j < GROUND_BELOW_ROWS; j += 1)
        // {
        // // Position in wider scrollable world
        // int x = i * TILE_SIZE + HALF_TILE_SIZE;
        // int y = getHeight() - TILE_SIZE + HALF_TILE_SIZE;

        // // Create object and add it
        // Ground ground = new Ground(x, y);
        // addObject(ground, x, y);
        // }
        // }

        // 2. Make sub-ground at end of level (below top layer)
        
    }

    /**
     * Add some fences at left and right side.
     */
    private void addFences()
    {
        // Three fences on left side of world
        int x = HALF_TILE_SIZE + TILE_SIZE * 13;
        int y = VISIBLE_HEIGHT - TILE_SIZE * 3;
        Fence fence1 = new Fence(x, y);
        addObject(fence1, x, y);

        // x = HALF_TILE_SIZE + TILE_SIZE * 6;
        // y = VISIBLE_HEIGHT - HALF_TILE_SIZE - TILE_SIZE;        
        // Fence fence2 = new Fence(x, y);
        // addObject(fence2, x, y);

        // x = HALF_TILE_SIZE + TILE_SIZE * 7;
        // y = VISIBLE_HEIGHT - HALF_TILE_SIZE - TILE_SIZE;
        // Fence fence3 = new Fence(x, y);
        // addObject(fence3, x, y);

        // Two fences on right side of world
        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 3;
        y = VISIBLE_HEIGHT / 2;
        Fence fence4 = new Fence(x, y);
        addObject(fence4, x, y);

        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 4;
        y = VISIBLE_HEIGHT / 2;
        Fence fence5 = new Fence(x, y);
        addObject(fence5, x, y);
    }

    /**
     * Add steps made out of metal plates leading to end of world.
     */
    private void addPlateSteps()
    {
        // How many plates total?
        final int COUNT_OF_METAL_PLATES = 20;
        final int PLATES_PER_GROUP = 3;

        // Add groups of plates
        for (int i = 0; i < COUNT_OF_METAL_PLATES / PLATES_PER_GROUP; i += 1)
        {
            // Group of four metal plates all at same y position
            int y = VISIBLE_HEIGHT - HALF_TILE_SIZE - i * TILE_SIZE * 3;

            // Add the individual plates in a given group
            for (int j = 0; j < PLATES_PER_GROUP; j += 1)
            {
                int x = VISIBLE_WIDTH + TILE_SIZE * 4 + TILE_SIZE * (i + j) + TILE_SIZE * 10 * i;
                Plate plate = new Plate(x, y);
                addObject(plate, x, y);
            }
        }

    }

    /**
     * Add a few clouds for the opening scene.
     */
    private void addClouds()
    {
        Cloud cloud1 = new Cloud(170, 80);
        addObject(cloud1, 170, 80);
        Cloud cloud2 = new Cloud(550, 175);
        addObject(cloud2, 550, 175);
        Cloud cloud3 = new Cloud(800, 50);
        addObject(cloud3, 800, 50);
    }

    /**
     * Act
     * 
     * This method is called approximately 60 times per second.
     */
    public void act()
    {
    }

    /**
     * Add the hero to the world.
     */
    private void addHero()
    {
        // Initial horizontal position
        int initialX = TILE_SIZE * 3;

        // Instantiate the hero object
        theDemon = new Demon(initialX);

        // Add hero in bottom left corner of screen
        addObject(theDemon, initialX, getHeight() / 4 * 3);
    }

    /**
     * Return an object reference to the hero.
     */
    public Demon getDemon()
    {
        return theDemon;
    }

    /**
     * Set game over
     */
    public void setGameOver()
    {
        isGameOver = true;
    }
}

