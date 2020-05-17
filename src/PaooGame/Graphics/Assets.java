package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage heroLeft;
    public static BufferedImage heroRight;
    public static BufferedImage heroUp;
    public static BufferedImage heroDown;
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage water;
    public static BufferedImage rock;
    public static BufferedImage tree;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/hero.png"),24,32);
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheet.png"),32,32);

            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        grass = sheet2.crop(0, 0);
        rock = sheet2.crop(2, 0);
        water = sheet2.crop(4, 0);
        heroLeft = sheet.crop(0, 3);
        heroRight = sheet.crop(0, 1);
        heroUp = sheet.crop(0,0);
        heroDown = sheet.crop(0,2);

    }
}
