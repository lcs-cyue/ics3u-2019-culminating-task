  
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author C. Yue
 * @version May 8, 2019
 */
public class SideScrollingWorld extends World
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    

    GreenfootSound backgroundMusic;

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
    private int frames;
    
    Score score = new Score();

    /**
     * Constructor for objects of class SideScrollingWorld.
     */
    public SideScrollingWorld()
    {    
        // Create a new world with 640x480 cells with a cell size of 1x1 pixels.
        // Final argument of 'false' means that actors in the world are not restricted to the world boundary.
        // See: https://www.greenfoot.org/files/javadoc/greenfoot/World.html#World-int-int-int-boolean-
        super(VISIBLE_WIDTH, VISIBLE_HEIGHT, 1, false);

        //Play background music
        backgroundMusic = new GreenfootSound("TheSupper.mp3");
        backgroundMusic.playLoop();
        
        prepare();

        // Set up the starting scene
        setup();

        // Change the background
        setBackground(new GreenfootImage("Castle2.png"));

        // Game on
        isGameOver = false;
        
        //add score into the world
        addObject(score, 575, 10);

        
    }

    /**
     * Set up the entire world.
     */
    private void setup()
    {
        addClouds();
        addLeftGround();
        Start();
        addTowers();
        addPlateSteps();
        addRightGround();
        addHero();
        addStar();
        addPullBack();
        addGhost();

    }

    private void addGhost()
    {   

    }

    private void addStar()
    {

        int x = HALF_TILE_SIZE + TILE_SIZE * 17;
        int y = VISIBLE_HEIGHT - TILE_SIZE * 3;
        Star star1 = new Star(x, y);
        addObject(star1, x, y);
        
        x = TILE_SIZE * 23;
        y = TILE_SIZE * 3;
        Star star2 = new Star(x, y);
        addObject(star2, x, y);
        
        x = TILE_SIZE * 26 - HALF_TILE_SIZE;
        y = VISIBLE_HEIGHT - TILE_SIZE * 2;
        Star star3 = new Star(x, y);
        addObject(star3, x, y);
        
        x = TILE_SIZE * 37 - HALF_TILE_SIZE;
        y = TILE_SIZE * 9;
        Star star4 = new Star(x, y);
        addObject(star4, x, y);


        x = TILE_SIZE * 46 - HALF_TILE_SIZE;
        y = TILE_SIZE * 8;
        Star star5 = new Star(x, y);
        addObject(star5, x, y);

    }

    private void addPullBack()
    {

        int x = HALF_TILE_SIZE + TILE_SIZE * 19;
        int y = VISIBLE_HEIGHT - TILE_SIZE * 3;
        PullBack arrow = new PullBack(x, y);
        addObject(arrow, x, y);

    }

    /**
     * Add blocks to create the ground to walk on at bottom-left of scrollable world.
     */
    private void addLeftGround()
    {
        // How many tiles will cover the bottom of the initial visible area of screen?
        final int tilesToCreate = getWidth() / TILE_SIZE;

        //Loop to create and add the Ground tile objects
        for (int i = 0; i <= 9; i += 1)
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
        for (int i = 0; i <= 9; i += 1)
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

        for (int i = 26; i <= 36; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 5 + HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }

        for (int i = 36; i <= 37; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = TILE_SIZE * 4 + HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }
        
        for (int i = 55; i <= 58; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 6 + HALF_TILE_SIZE;

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
     * Add some Towers at left and right side.
     */
    private void addTowers()
    {
        // Add a tower into the world
        int x = HALF_TILE_SIZE + TILE_SIZE;
        int y = VISIBLE_HEIGHT - TILE_SIZE * 3;
        Tower Tower1 = new Tower(x, y);
        addObject(Tower1, x, y);

        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 3;
        y = VISIBLE_HEIGHT / 2 + HALF_TILE_SIZE;
        Tower Tower4 = new Tower(x, y);
        addObject(Tower4, x, y);

        x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - TILE_SIZE * 4;
        y = VISIBLE_HEIGHT / 2 + HALF_TILE_SIZE;
        Tower Tower5 = new Tower(x, y);
        addObject(Tower5, x, y);

        x = TILE_SIZE * 25 - HALF_TILE_SIZE;
        y = TILE_SIZE * 13;
        Tower Tower6 = new Tower(x, y);
        addObject(Tower6, x, y);

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
        for (int i = 15; i <= 17; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - 2 *TILE_SIZE + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 15; i <= 16; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = TILE_SIZE * 5 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 9; i <= 11; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 6 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 21; i <= 22; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = TILE_SIZE * 4 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 23; i <= 24; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - TILE_SIZE + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 32; i < 34; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = TILE_SIZE * 6 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 40; i < 42; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 2 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 47; i < 49; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 2 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
        }

        for (int i = 51; i < 52; i += 1)
        {
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = getHeight() - TILE_SIZE * 4 + HALF_TILE_SIZE;

            Plate plate = new Plate (x,y);
            addObject(plate,x,y);
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
        frames += 1;

        //Add a ghost every 5 seconds
        if (frames % 300 == 0)
        {
            int x = HALF_TILE_SIZE + TILE_SIZE * 20;
            int y = TILE_SIZE * 3;
            Ghost ghost1 = new Ghost(x, y);

            addObject(ghost1, x, y);
        }
        //Every 60 frames, update the time
        if ((frames % 60) == 0)
        {
            String timeElapsed = Integer.toString(frames / 60);
            showText(timeElapsed, 35, 50);

        }

    }
    
    public Score getScore()
    {
        return score;
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

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void Start()
    {
        Start start = new Start(160, 350);
        addObject(start,95,150);
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Key key = new Key(1190, 70);
        addObject(key, 1190, 70);
        Skull skull = new Skull(1100,290);
        addObject(skull,1100,290);
        Careful text1 = new Careful(1300, 60);
        addObject(text1,1300, 60);
        InvisibleWall wall1 = new InvisibleWall(1900, 240);
        addObject(wall1, 1700, 240);
        Door door1 = new Door(1870, 244);
        addObject(door1, 1870, 244);
    }
}

