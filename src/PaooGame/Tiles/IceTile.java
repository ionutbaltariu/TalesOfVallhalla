package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class IceTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip gheata.
 */
public class IceTile extends Tile
{
    /*! \fn public IceTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public IceTile(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.ice, id);
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
