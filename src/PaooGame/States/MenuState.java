package PaooGame.States;

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
    private BufferedImage hoverElement;
    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public MenuState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        background=ImageLoader.LoadImage("/menu.jpg");
        hoverElement=ImageLoader.LoadImage("/optionHover.png");
//        if(State.GetState()==refLink.GetGame().getMenuState())
//            this.playMusic();
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {

        //daca apasam pe butonul PlayGame (asa cum este vazut in menu.jpg) atunci, state-ul se schimba la PlayState
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812) //cordonate calculate experimental folosind MousePressed + printuri
        {
            if(refLink.GetMouseManager().getMouseY()>=576 && refLink.GetMouseManager().getMouseY()<=693)
            {
                if(refLink.GetMouseManager().leftPressed)
                {
                    this.MenuSelection();
                    State.SetState(refLink.GetGame().getPlayState());
                }
            }

            if(refLink.GetMouseManager().getMouseY()>=709 && refLink.GetMouseManager().getMouseY()<=823)
            {
                //asta o sa fie settings state.
                if(refLink.GetMouseManager().leftPressed)
                {
                    this.MenuSelection();
                    State.SetState(refLink.GetGame().getPlayState());
                }

            }

            // butonul EXIT - la apasarea acestuia se iese instant din aplicatie
            if(refLink.GetMouseManager().getMouseY()>=842 && refLink.GetMouseManager().getMouseY()<=956)
            {
                if(refLink.GetMouseManager().leftPressed)
                {
                    this.MenuSelection();
                    System.exit(0);
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
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812) //cordonate calculate experimental folosind MousePressed + printuri
        {
            if(refLink.GetMouseManager().getMouseY()>=576 && refLink.GetMouseManager().getMouseY()<=693)
            {
                g.drawImage(hoverElement,832,576,114,114,null);
            }

            if(refLink.GetMouseManager().getMouseY()>=709 && refLink.GetMouseManager().getMouseY()<=823)
            {
                //asta o sa fie settings state.
                g.drawImage(hoverElement,832,709,114,114,null);
            }

            // butonul EXIT - la apasarea acestuia se iese instant din aplicatie
            if(refLink.GetMouseManager().getMouseY()>=842 && refLink.GetMouseManager().getMouseY()<=956)
            {
                g.drawImage(hoverElement,832,842,114,114,null);
            }
        }
    }

    //Functie care permite implementarea muzicii in aplicatie ( preluata )
    @Override
    public void playMusic() {
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(MenuState.class.getResource("/m.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.getMessage();
        }
    }

    public void MenuSelection()
    {
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(MenuState.class.getResource("/menuSelect.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.getMessage();
        }
    }
}
