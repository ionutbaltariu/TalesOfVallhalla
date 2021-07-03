package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class HouseWindowTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip geam.
 */
public class HouseWindowTile extends Tile {
    /*! \fn public HouseWindowTile(int id)
\brief Constructorul de initializare al clasei

\param id Id-ul dalei util in desenarea hartii.
*/
    public HouseWindowTile(int id) {
        /// Apel al constructorului clasei de baza
        super(Assets.houseWindow, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
     */
    @Override
    public boolean IsSolid() {
        return true;
    }
}
