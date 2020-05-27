package PaooGame.Graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    public static BufferedImage enemy1Left;
    public static BufferedImage enemy1Right;
    public static BufferedImage enemy1Up;
    public static BufferedImage enemy1Down;

    public static BufferedImage enemy2Left;
    public static BufferedImage enemy2Right;
    public static BufferedImage enemy2Up;
    public static BufferedImage enemy2Down;

    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage water;
    public static BufferedImage rock;
    public static BufferedImage lava;

    public static BufferedImage background;    /*!< Background-ul efectiv al meniului(facut in Photoshop).*/
    public static BufferedImage suspend;       /*!< Obiect de tip "BufferedImage" in care incarcam popup-ul care apare cand apasam ESC.*/
    public static BufferedImage deadState;     /*!< Obiect de tip "BufferedImage" in care incarcam popup-ul care apare cand moare eroul.*/
    public static BufferedImage settingsBackground;
    public static BufferedImage aboutBackground; /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage hpBackground;
    public static BufferedImage hpForeground;
    public static BufferedImage hpBorder;

    public static AudioInputStream menuBackgroundMusic;
    public static AudioInputStream playStateBackgroundMusic;
    public static AudioInputStream buttonClickSound;
    public static AudioInputStream deathSound;

    public static Clip music;                  /*!< Obiect de tip "Clip" in care putem incarca un element audio. In acest caz este muzica din PlayState.*/
    public static Clip death;                  /*!< Obiect de tip "Clip" in care putem incarca un element audio. In acest caz este sunetul din momentul mortii.*/
    public static Clip menuMusic;           /*!< Melodia care se aude in menuState.*/
    public static Clip buttonClick;         /*!< Sunetul care se aude cand apasam un buton din meniu.*/

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() throws LineUnavailableException, IOException {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet characterSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/hero.png"),24,32);
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheet.png"),32,32);
        SpriteSheet enemy1 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy1.png"),50,48);
        SpriteSheet enemy2 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy2.png"),96,96);

        background=ImageLoader.LoadImage("/menu.jpg");
        suspend=ImageLoader.LoadImage("/suspend.png");
        deadState=ImageLoader.LoadImage("/deadState.png");
        settingsBackground=ImageLoader.LoadImage("/settings.png");
        aboutBackground=ImageLoader.LoadImage("/about.jpg");
        hpBackground = ImageLoader.LoadImage("/healthbarBackground.png");
        hpForeground = ImageLoader.LoadImage("/healthbarForeground.png");
        hpBorder     = ImageLoader.LoadImage("/healthbarBorder.png");
        menuBackgroundMusic      = AudioLoader.LoadAudio("/menuStateMusic.wav");
        buttonClickSound         = AudioLoader.LoadAudio("/menuSelect.wav");
        playStateBackgroundMusic = AudioLoader.LoadAudio("/playStateMusic.wav");
        deathSound               = AudioLoader.LoadAudio("/deadSound.wav");

        music= AudioSystem.getClip();
        music.open(playStateBackgroundMusic);
        death = AudioSystem.getClip();
        death.open(deathSound);
        menuMusic=AudioSystem.getClip();
        menuMusic.open(menuBackgroundMusic);
        buttonClick=AudioSystem.getClip();
        buttonClick.open(buttonClickSound);

        hoverElement=ImageLoader.LoadImage("/optionHover.png");

        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        /// Cropam imaginile necesare pentru dalele folosite in crearea hartii.
        grass      = tileSheet.crop(2, 3);
        rock       = tileSheet.crop(2, 0);
        water      = tileSheet.crop(4, 0);
        soil       = tileSheet.crop(1,0);
        lava       = tileSheet.crop(2,1);

        ///Cropam imaginile necesare pentru caracterul principal.
        heroLeft = characterSheet.crop(0, 3);
        heroRight = characterSheet.crop(0, 1);
        heroUp = characterSheet.crop(0,0);
        heroDown = characterSheet.crop(0,2);

        ///Cropam imaginile necesare pentru inamicul 1.
        enemy1Left  = enemy1.crop(0,1);
        enemy1Right = enemy1.crop(0,2);
        enemy1Up    = enemy1.crop(0,3);
        enemy1Down  = enemy1.crop(0,0);

        ///Cropam imaginile necesare pentru inamicul 2.
        enemy2Left  = enemy2.crop(0,1);
        enemy2Right = enemy2.crop(0,2);
        enemy2Up    = enemy2.crop(0,3);
        enemy2Down  = enemy2.crop(0,0);

    }
}
