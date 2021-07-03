package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class RockTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip piatra.
 */
public class RockTile extends Tile {
    /*! \fn public RockTile(int id)
   \brief Constructorul de initializare al clasei

   \param id Id-ul dalei util in desenarea hartii.
    */
    public RockTile(int id) {
        /// Apel al constructorului clasei de baza
        super(Assets.rock, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
     */
    @Override
    public boolean IsSolid() {
        return true;
    }
}
