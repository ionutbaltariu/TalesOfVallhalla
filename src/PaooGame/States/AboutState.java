package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class AboutState extends State
    \brief Implementeaza notiunea de credentiale .
 */
public class AboutState extends State
{
    /*! \fn public AboutState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public AboutState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a state-ului about.
     */
    @Override
    public void Update(){
        //daca apasam butonul BACK, ne vom intoarce in meniul principal
        //cordonate determinate experimental
        //pot exista mici incercitudini
        if(refLink.GetMouseManager().getMouseY()>=851 && refLink.GetMouseManager().getMouseY()<=968)
        {
            if(refLink.GetMouseManager().getMouseX()>=470 && refLink.GetMouseManager().getMouseX()<=816)
            {
                if(refLink.GetMouseManager().leftClickPressed()) {
                    Assets.buttonClick.setFramePosition(0);
                    Assets.buttonClick.start();
                    State.SetState(refLink.GetGame().getMenuState());
                    // oprim thread-ul pentru 0.5s pentru a nu se apasa butoanele in ambele state-uri.
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        System.err.println("Eroare la intreruperea thread-ului in AboutState->Update()");
                    }
                }
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(Assets.aboutBackground,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
    }
}
