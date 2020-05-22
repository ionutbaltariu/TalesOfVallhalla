package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;

import java.awt.*;

import javax.swing.*;

import java.awt.image.BufferedImage;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map map;    /*!< Referinta catre harta curenta.*/
    boolean wasEscPressed;  /*!< Flag care sa indice daca a fost apasata tasta esc. Ne va folosi la popup-ul "Are you sure you want to exit[..]"*/

    BufferedImage suspend;

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
            ///Construieste harta jocului
        map = new Map(refLink);
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
            ///Construieste eroul
        hero = new Hero(refLink,32, 32);
        suspend= ImageLoader.LoadImage("/suspend.png");
    }


    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update() throws InterruptedException {
        map.Update();
        hero.Update();
        if(wasEscPressed)
        {
            if(refLink.GetMouseManager().getMouseY()>=575 && refLink.GetMouseManager().getMouseY()<=690)
            {
                if(refLink.GetMouseManager().getMouseX()>=253 && refLink.GetMouseManager().getMouseX()<=568)
                {
                    if(refLink.GetMouseManager().leftClickPressed())
                    {
                        wasEscPressed=false;
                        State.SetState(refLink.GetGame().getMenuState());
                        refLink.GetGameThread().sleep(100);
                    }
                }
                if(refLink.GetMouseManager().getMouseX()>=705 && refLink.GetMouseManager().getMouseX()<=1020)
                {
                    if(refLink.GetMouseManager().leftClickPressed())
                    {
                        wasEscPressed=false;
                    }
                }
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        map.Draw(g);
        hero.Draw(g);
        if(refLink.GetKeyManager().esc)
        {
            wasEscPressed=true;
        }
        if(wasEscPressed)
        {
            g.drawImage(suspend,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
            if(refLink.GetMouseManager().getMouseY()>=575 && refLink.GetMouseManager().getMouseY()<=690)
            {
                if(refLink.GetMouseManager().getMouseX()>=253 && refLink.GetMouseManager().getMouseX()<=568)
                {

                    g.drawImage(Assets.hoverElement,360,710,100,100,null);
                }
                if(refLink.GetMouseManager().getMouseX()>=705 && refLink.GetMouseManager().getMouseX()<=1020)
                {
                    g.drawImage(Assets.hoverElement,812,710,100,100,null);
                }
            }
        }

    }
}
