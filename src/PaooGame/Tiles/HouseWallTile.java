package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

public class HouseWallTile extends Tile{
    /*! \fn public LavaTile(int id)
\brief Constructorul de initializare al clasei

\param id Id-ul dalei util in desenarea hartii.
*/
    public HouseWallTile(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.houseWall, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
     */
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
