package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */


public class MenuState extends State
{
    private BufferedImage background;
    private Clip menuMusic;
    private Clip buttonClick;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public MenuState(RefLinks refLink) throws LineUnavailableException, IOException {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        background=ImageLoader.LoadImage("/menu.jpg");

        menuMusic=AudioSystem.getClip();
        menuMusic.open(Assets.menuBackgroundMusic);

        buttonClick=AudioSystem.getClip();
        buttonClick.open(Assets.buttonClickSound);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        if(!menuMusic.isRunning()) { // daca Clipul audio nu este deja pornit
            menuMusic.setFramePosition(0);  // il setam sa inceapa de la frameul 0 ( folositor cand revenim in meniu din playState )
            menuMusic.start(); // pornim clipul audio
        }
        //daca apasam pe butonul PlayGame (asa cum este vazut in menu.jpg) atunci, state-ul se schimba la PlayState
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812) //cordonate calculate experimental folosind MousePressed + printuri
        {
            if(refLink.GetMouseManager().getMouseY()>=576 && refLink.GetMouseManager().getMouseY()<=693) //cordonate calculate experimental folosind MousePressed + printuri
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    menuMusic.stop();
                    State.SetState(refLink.GetGame().getPlayState());
                }
            }

            if(refLink.GetMouseManager().getMouseY()>=709 && refLink.GetMouseManager().getMouseY()<=823) //cordonate calculate experimental folosind MousePressed + printuri
            {
                //asta o sa fie settings state.
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                }

            }

            // butonul EXIT - la apasarea acestuia se iese instant din aplicatie
            if(refLink.GetMouseManager().getMouseY()>=842 && refLink.GetMouseManager().getMouseY()<=956) //cordonate calculate experimental folosind MousePressed + printuri
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    System.exit(0);
                }
            }
        }
        // about button ( de reimplementat in viitor )
        else if(refLink.GetMouseManager().getMouseX()>=31 && refLink.GetMouseManager().getMouseX()<=102)
        {
            if(refLink.GetMouseManager().getMouseY()>=738 && refLink.GetMouseManager().getMouseY()<=779)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    State.SetState(refLink.GetGame().getAboutState());
                }
            }
        }

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(background,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
        //folosim aceasta imbricare de if-uri pentru a printa un png de 114x114 pixeli care sa sugereze grafic butonul din meniu pe care
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812)
        {
            if(refLink.GetMouseManager().getMouseY()>=576 && refLink.GetMouseManager().getMouseY()<=693)
            {
                g.drawImage(Assets.hoverElement,832,576,114,114,null);
            }

            if(refLink.GetMouseManager().getMouseY()>=709 && refLink.GetMouseManager().getMouseY()<=823)
            {
                //asta o sa fie settings state.
                g.drawImage(Assets.hoverElement,832,709,114,114,null);
            }

            // butonul EXIT - la apasarea acestuia se iese instant din aplicatie
            if(refLink.GetMouseManager().getMouseY()>=842 && refLink.GetMouseManager().getMouseY()<=956)
            {
                g.drawImage(Assets.hoverElement,832,842,114,114,null);
            }
        }
    }
}
