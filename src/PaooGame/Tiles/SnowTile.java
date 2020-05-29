package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class SnowTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip zapada.
 */
public class SnowTile extends Tile
{
    /*! \fn public SnowTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public SnowTile(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.snow, id);
    }
}
