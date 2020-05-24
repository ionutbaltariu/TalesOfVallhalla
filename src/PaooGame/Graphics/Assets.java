package PaooGame.Graphics;

import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;

/*! \class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    public static BufferedImage hoverElement;
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage heroLeft;
    public static BufferedImage heroRight;
    public static BufferedImage heroUp;
    public static BufferedImage heroDown;

    public static BufferedImage enemyLeft;
    public static BufferedImage enemyRight;
    public static BufferedImage enemyUp;
    public static BufferedImage enemyDown;

    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage water;
    public static BufferedImage rock;
    public static BufferedImage lava;
    public static BufferedImage treeTrunk;
    public static BufferedImage treeLeaves;

    public static AudioInputStream menuBackgroundMusic;
    public static AudioInputStream buttonClickSound;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet characterSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/hero.png"),24,32);
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheet.png"),32,32);
        SpriteSheet enemy1 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy1.png"),50,48);

        menuBackgroundMusic = AudioLoader.LoadAudio("/m.wav");
        buttonClickSound = AudioLoader.LoadAudio("/menuSelect.wav");

        hoverElement=ImageLoader.LoadImage("/optionHover.png");

        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        /// Cropam imaginile necesare pentru dalele folosite in crearea hartii.
        grass      = tileSheet.crop(2, 3);
        rock       = tileSheet.crop(2, 0);
        water      = tileSheet.crop(4, 0);
        soil       = tileSheet.crop(1,0);
        lava       = tileSheet.crop(2,1);
        treeTrunk  = tileSheet.crop(0,3);
        treeLeaves = tileSheet.crop(3,3);

        ///Cropam imaginile necesare pentru caracterul principal.
        heroLeft = characterSheet.crop(0, 3);
        heroRight = characterSheet.crop(0, 1);
        heroUp = characterSheet.crop(0,0);
        heroDown = characterSheet.crop(0,2);

        ///Cropam imaginile necesare pentru inamicul 1.
        enemyLeft  = enemy1.crop(0,1);
        enemyRight = enemy1.crop(0,2);
        enemyUp    = enemy1.crop(0,3);
        enemyDown  = enemy1.crop(0,0);
    }
}
