package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.AudioLoader;
import PaooGame.RefLinks;

import java.awt.*;
import java.sql.SQLException;

/*! \class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{

    private static boolean loadButtonClicked=false;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */

    public MenuState(RefLinks refLink) {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        try {
            AudioLoader.setVolume(Assets.menuMusic, refLink.GetDatabase().getMenuVolume());
        }
        catch (SQLException e)
        {
            System.err.println("Eroare MenuState->Constructor");
        }

    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update(){
        if(!Assets.menuMusic.isRunning()) { // daca Clipul audio nu este deja pornit
            Assets.menuMusic.setFramePosition(0);  // il setam sa inceapa de la frameul 0 ( folositor cand revenim in meniu din playState )
            Assets.menuMusic.start(); // pornim clipul audio
        }
        //daca apasam pe butonul PlayGame (asa cum este vazut in menu.jpg) atunci, state-ul se schimba la PlayState
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812) //cordonate calculate experimental folosind MousePressed + printuri
        {

            if(refLink.GetMouseManager().getMouseY()>=441 && refLink.GetMouseManager().getMouseY()<=558) //cordonate calculate experimental folosind MousePressed + printuri
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    Assets.buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    Assets.buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    Assets.menuMusic.stop();
                    refLink.SetMap(PlayState.map);
                    State.SetState(refLink.GetGame().getPlayState());
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        System.err.println("Eroare la thread in update.");
                    }
                }
            }

            if(refLink.GetMouseManager().getMouseY()>=577 && refLink.GetMouseManager().getMouseY()<=692) //cordonate calculate experimental folosind MousePressed + printuri
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    Assets.buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    Assets.buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    loadButtonClicked=true;
                    Assets.menuMusic.stop();
                    try {
                        if (refLink.GetDatabase().getIsInLvl1() == 1) {
                            State.SetState(refLink.GetGame().getLevel1State());
                            refLink.SetMap(Level1State.level1);
                            Thread.sleep(500);
                        }
                        if (refLink.GetDatabase().getIsInLvl2() == 1) {
                            State.SetState(refLink.GetGame().getLevel2State());
                            refLink.SetMap(Level2State.level2);
                            Thread.sleep(500);
                        }
                        if (refLink.GetDatabase().getIsInStart() == 1) {
                            State.SetState(refLink.GetGame().getPlayState());
                            refLink.SetMap(PlayState.map);
                            Thread.sleep(500);
                        }
                    }
                    catch (SQLException e )
                    {
                        System.err.println("Eroare in MenuState->Update->Baza de date.");
                    }
                    catch (InterruptedException e)
                    {
                        System.err.println("Eroare in MenuState->Update->Thread.");
                    }
                }
            }


            if(refLink.GetMouseManager().getMouseY()>=709 && refLink.GetMouseManager().getMouseY()<=823) //cordonate calculate experimental folosind MousePressed + printuri
            {
                //asta o sa fie settings state.
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    Assets.buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    Assets.buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
                    State.SetState(refLink.GetGame().getSettingsState());
                }

            }

            // butonul EXIT - la apasarea acestuia se iese instant din aplicatie
            if(refLink.GetMouseManager().getMouseY()>=842 && refLink.GetMouseManager().getMouseY()<=956) //cordonate calculate experimental folosind MousePressed + printuri
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    Assets.buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    Assets.buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
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
                    Assets.buttonClick.setFramePosition(0); // un soi de "reincarcare" a sunetului. mai degraba o setare a timeline-ului pe momentul 0
                    Assets.buttonClick.start(); //sunet pentru a dinamiza experienta de parcurgere a meniului
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
        g.drawImage(Assets.background,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
        //folosim aceasta imbricare de if-uri pentru a printa un png de 114x114 pixeli care sa sugereze grafic butonul din meniu pe care
        if(refLink.GetMouseManager().getMouseX()>=495 && refLink.GetMouseManager().getMouseX()<=812)
        {
            if(refLink.GetMouseManager().getMouseY()>=441 && refLink.GetMouseManager().getMouseY()<=558)
            {
                g.drawImage(Assets.hoverElement,832,441,114,114,null);
            }
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

    /*! \fn public static boolean getLoadButtonFlag()
        \brief Functie statica ce indica valoarea flagului care tine cont daca a fost apasat butonul de Load.
               Statica si publica pentru a putea fi accesata din PlayState pentru a incarca efectiv datele din baza de date.
     */
    public static boolean getLoadButtonFlag()
    {
        return loadButtonClicked;
    }

    /*! \fn public static void setLoadButtonFlag(boolean flag)
        \brief Functie statica ce modifica valoarea flagului care tine cont daca a fost apasat butonul de Load.
               Statica si publica pentru a putea fi accesata din PlayState ca sa fie resetat flagul.
        \param flag boolean ce reprezinta valoarea flagului ce tine cont de apasarea pe butonul de "Load"
     */
    public static void setLoadButtonFlag(boolean flag)
    {
        loadButtonClicked=flag;
    }

}
