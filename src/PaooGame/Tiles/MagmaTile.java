package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class MagmaTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip lava.
 */
public class MagmaTile extends Tile{
    /*! \fn public MagmaTile(int id)
   \brief Constructorul de initializare al clasei

   \param id Id-ul dalei util in desenarea hartii.
    */
    public MagmaTile(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.magma, id);
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
