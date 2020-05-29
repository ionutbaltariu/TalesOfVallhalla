package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class CoinTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip banut.
 */
public class CoinTile extends Tile
{
    /*! \fn public Coinile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public CoinTile(int id)
    {
        super(Assets.coin, id);
    }

}