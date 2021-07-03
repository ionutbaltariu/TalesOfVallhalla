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
public class Assets {
    public static BufferedImage hoverElement;        /*!< Imagine care va aparea cand tinem mouse-ul peste un buton.*/
    public static BufferedImage heroLeft;            /*!< Imagine pentru mersul in stanga al eroului*/
    public static BufferedImage heroRight;           /*!< Imagine pentru mersul in dreapta al eroului*/
    public static BufferedImage heroUp;              /*!< Imagine pentru mersul in sus al eroului*/
    public static BufferedImage heroDown;            /*!< Imagine pentru mersul in jos al eroului*/
    public static BufferedImage enemy1Left;          /*!< Imagine pentru mersul in stanga al inamicului 1*/
    public static BufferedImage enemy1Right;         /*!< Imagine pentru mersul in dreapta al inamicului 1*/
    public static BufferedImage enemy1Up;            /*!< Imagine pentru mersul in sus al inamicului 1*/
    public static BufferedImage enemy1Down;          /*!< Imagine pentru mersul in jos al inamicului 1*/
    public static BufferedImage enemy2Left;          /*!< Imagine pentru mersul in stanga al inamicului 2*/
    public static BufferedImage enemy2Right;         /*!< Imagine pentru mersul in dreapta al inamicului 2*/
    public static BufferedImage enemy2Up;            /*!< Imagine pentru mersul in sus al inamicului 2*/
    public static BufferedImage enemy2Down;          /*!< Imagine pentru mersul in jos al inamicului 2*/
    public static BufferedImage soil;                /*!< Dala de tip pamant.*/
    public static BufferedImage grass;               /*!< Dala de tip iarba .*/
    public static BufferedImage water;               /*!< Dala de tip apa.*/
    public static BufferedImage rock;                /*!< Dala de tip piatra.*/
    public static BufferedImage lava;                /*!< Dala de tip lava.*/
    public static BufferedImage magma;               /*!< Dala de tip magma.*/
    public static BufferedImage coin;                /*!< Dala de tip banut.*/
    public static BufferedImage houseWall;           /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage houseDoor;           /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage houseRoof;           /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage houseWindow;         /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage wallpaper;
    public static BufferedImage background;          /*!< Background-ul efectiv al meniului(facut in Photoshop).*/
    public static BufferedImage suspend;             /*!< Obiect de tip "BufferedImage" in care incarcam popup-ul care apare cand apasam ESC.*/
    public static BufferedImage deadState;           /*!< Obiect de tip "BufferedImage" in care incarcam popup-ul care apare cand moare eroul.*/
    public static BufferedImage settingsBackground;  /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage aboutBackground;     /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage hpBackground;        /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage hpForeground;        /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage hpBorder;            /*!< Background-ul efectiv din abouState.*/
    public static BufferedImage endStateBackground;  /*!< Background-ul efectiv din abouState.*/
    public static AudioInputStream menuBackgroundMusic;         /*!< Background-ul efectiv din abouState.*/
    public static AudioInputStream playStateBackgroundMusic;    /*!< Background-ul efectiv din abouState.*/
    public static AudioInputStream buttonClickSound;            /*!< Background-ul efectiv din abouState.*/
    public static AudioInputStream deathSound;                  /*!< Background-ul efectiv din abouState.*/
    public static Clip music;                                   /*!< Obiect de tip "Clip" in care putem incarca un element audio. In acest caz este muzica din PlayState.*/
    public static Clip death;                                   /*!< Obiect de tip "Clip" in care putem incarca un element audio. In acest caz este sunetul din momentul mortii.*/
    public static Clip menuMusic;                               /*!< Melodia care se aude in menuState.*/
    public static Clip buttonClick;                             /*!< Sunetul care se aude cand apasam un buton din meniu.*/
    static boolean flag = false;
    static long now;
    static long then;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet characterSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/hero.png"), 24, 32);
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheet.png"), 32, 32);
        SpriteSheet enemy1 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy1.png"), 50, 48);
        SpriteSheet enemy2 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy2.png"), 96, 96);
        SpriteSheet house = new SpriteSheet(ImageLoader.LoadImage("/textures/houseSprite.png"), 32, 32);

        background = ImageLoader.LoadImage("/menu.jpg");
        suspend = ImageLoader.LoadImage("/suspend.png");
        deadState = ImageLoader.LoadImage("/deadState.png");
        settingsBackground = ImageLoader.LoadImage("/settings.png");
        aboutBackground = ImageLoader.LoadImage("/aboutState.png");
        wallpaper = ImageLoader.LoadImage("/wallpaper.jpg");
        hpBackground = ImageLoader.LoadImage("/healthbarBackground.png");
        hpForeground = ImageLoader.LoadImage("/healthbarForeground.png");
        hpBorder = ImageLoader.LoadImage("/healthbarBorder.png");
        endStateBackground = ImageLoader.LoadImage("/endState.jpg");

        menuBackgroundMusic = AudioLoader.LoadAudio("/menuStateMusic.wav");
        buttonClickSound = AudioLoader.LoadAudio("/menuSelect.wav");
        playStateBackgroundMusic = AudioLoader.LoadAudio("/playStateMusic.wav");
        deathSound = AudioLoader.LoadAudio("/deadSound.wav");

        try {
            music = AudioSystem.getClip();
            music.open(playStateBackgroundMusic);
            death = AudioSystem.getClip();
            death.open(deathSound);
            menuMusic = AudioSystem.getClip();
            menuMusic.open(menuBackgroundMusic);
            buttonClick = AudioSystem.getClip();
            buttonClick.open(buttonClickSound);
        } catch (LineUnavailableException | IOException e) {
            System.err.println("Eroare la incarcarea sunetelor in Assets.");
        }

        hoverElement = ImageLoader.LoadImage("/optionHover.png");

        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        /// Cropam imaginile necesare pentru dalele folosite in crearea hartii.
        grass = tileSheet.crop(0, 0);
        rock = tileSheet.crop(2, 0);
        water = tileSheet.crop(4, 0);
        soil = tileSheet.crop(1, 0);
        lava = tileSheet.crop(2, 1);
        magma = tileSheet.crop(3, 1);
        coin = ImageLoader.LoadImage("/textures/coin.png");

        houseDoor = house.crop(1, 0);
        houseRoof = house.crop(0, 3);
        houseWall = house.crop(0, 0);
        houseWindow = house.crop(2, 1);

        ///Cropam imaginile necesare pentru caracterul principal.
        heroLeft = characterSheet.crop(0, 3);
        heroRight = characterSheet.crop(0, 1);
        heroUp = characterSheet.crop(0, 0);
        heroDown = characterSheet.crop(0, 2);

        ///Cropam imaginile necesare pentru inamicul 1.
        enemy1Left = enemy1.crop(0, 1);
        enemy1Right = enemy1.crop(0, 2);
        enemy1Up = enemy1.crop(0, 3);
        enemy1Down = enemy1.crop(0, 0);

        ///Cropam imaginile necesare pentru inamicul 2.
        enemy2Left = enemy2.crop(0, 1);
        enemy2Right = enemy2.crop(0, 2);
        enemy2Up = enemy2.crop(0, 3);
        enemy2Down = enemy2.crop(0, 0);

    }

    /*! \fn public static boolean secondElapsed()
        \brief Functie ce contorizeaza trecerea unei secunde.
    */
    public static boolean secondElapsed() {
        if (!flag) {
            then = System.nanoTime();
            flag = true;
        }
        now = System.nanoTime();
        if (now - then >= 1000000000) {
            flag = false;
            return true;
        }
        return false;
    }
}
