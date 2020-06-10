package PaooGame.Tiles;

/*! \class TileFactory
    \brief Clasa ce permite crearea obiectelor extinse din Tile folosind sablonul de proiectare "Factory"
 */
public class TileFactory {

    /*! \fn public Tile createTile(int id)
        \brief Functia returneaza tipul de dala dorit in functie de id-ul introdus. Procedura standard a sablonului "Factory".

        \param id Id-ul dalei.
     */
    public Tile createTile(int id)
    {
        return switch (id) {
            case 0 -> new GrassTile(0);
            case 1 -> new RockTile(1);
            case 2 -> new WaterTile(2);
            case 3 -> new SoilTile(3);
            case 4 -> new LavaTile(4);
            case 5 -> new MagmaTile(5);
            case 7 -> new HouseWallTile(7);
            case 8 -> new HouseRoofTile(8);
            case 10-> new HouseDoorTile(10);
            case 11-> new CoinTile(11);
            case 12-> new HouseWindowTile(12);
            default -> null;
        };
    }

}
