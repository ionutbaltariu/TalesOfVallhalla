package PaooGame.States;

import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class AboutState extends State
    \brief Implementeaza notiunea de credentiale (about)
 */
public class AboutState extends State
{
    BufferedImage background;
    /*! \fn public AboutState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public AboutState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        background= ImageLoader.LoadImage("/about.jpg");
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniu about.
     */
    @Override
    public void Update()
    {
        //daca apasam butonul BACK, ne vom intoarce in meniul principal
        //cordonate determinate experimental
        //pot exista mici incercitudini
        if(refLink.GetMouseManager().getMouseY()>=851 && refLink.GetMouseManager().getMouseY()<=968)
        {
            if(refLink.GetMouseManager().getMouseX()>=146 && refLink.GetMouseManager().getMouseX()<=465)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                    State.SetState(refLink.GetGame().getMenuState());
            }
            if(refLink.GetMouseManager().getMouseX()>=470 && refLink.GetMouseManager().getMouseX()<=816)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                    System.exit(0);
            }
            if(refLink.GetMouseManager().getMouseX()>=822 && refLink.GetMouseManager().getMouseX()<=1136)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                    State.SetState(refLink.GetGame().getPlayState());
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
        g.drawImage(background,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
    }

    @Override
    public void playMusic() {

    }
}
