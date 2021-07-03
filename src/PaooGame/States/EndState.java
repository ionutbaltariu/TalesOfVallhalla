package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class EndState extends State
    \brief Implementeaza notiunea de settings pentru joc.

    Aici setarile vor trebui salvate/incarcate intr-un/dintr-un fisier/baza de date sqlite.
 */
public class EndState extends State {
    /*! \fn public SettingsState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public EndState(RefLinks refLink) {
        ///Apel al construcotrului clasei de baza.
        super(refLink);
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea state-ului final.
     */
    @Override
    public void Update() {
        if (refLink.GetMouseManager().getMouseY() >= 851 && refLink.GetMouseManager().getMouseY() <= 968) {
            if (refLink.GetMouseManager().getMouseX() >= 470 && refLink.GetMouseManager().getMouseX() <= 816) {
                if (refLink.GetMouseManager().leftClickPressed()) {
                    System.exit(0);
                }
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.PLAIN, 64));
        g.drawImage(Assets.endStateBackground, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        g.drawString(Integer.toString(Hero.getInstance(refLink, 24, 24).GetScore()), 570, 770);
    }
}
