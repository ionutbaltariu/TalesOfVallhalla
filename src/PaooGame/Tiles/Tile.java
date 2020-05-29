package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 32;
    public static Tile[] tiles          = new Tile[NO_TILES];       /*!< Vector de referinte de tipuri de dale.*/

    private final static TileFactory tileFactory = new TileFactory();

        /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
        /// o singura data in memorie
    public static Tile grassTile        = tileFactory.createTile(0);
    public static Tile rockTile         = tileFactory.createTile(1);
    public static Tile waterTile        = tileFactory.createTile(2);
    public static Tile soilTile         = tileFactory.createTile(3);
    public static Tile lavaTile         = tileFactory.createTile(4);
    public static Tile magmaTile        = tileFactory.createTile(5);
    public static Tile snowTile         = tileFactory.createTile(6);
    public static Tile iceTile          = tileFactory.createTile(9);
    public static Tile houseWallTile    = tileFactory.createTile(7);
    public static Tile houseRoofTile    = tileFactory.createTile(8);
    public static Tile houseWindowTile  = tileFactory.createTile(12);
    public static Tile houseDoorTile    = tileFactory.createTile(10);
    public static Tile coinTile         = tileFactory.createTile(11);


    public static final int TILE_WIDTH  = 32;                       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 32;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/

    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics g, int x, int y)
    {
            /// Desenare dala
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public int GetId()
        \brief Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }
}
