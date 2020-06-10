package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyManager;
import PaooGame.Input.MouseManager;
import PaooGame.States.*;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.sql.SQLException;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    00 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     10.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    private final GameWindow      Window;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private final DBHandler database;

    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at
    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics        g;          /*!< Referinta catre un context grafic.*/

        ///Available states
    private State playState;            /*!< Referinta catre joc.*/
    private State menuState;            /*!< Referinta catre menu.*/
    private State settingsState;        /*!< Referinta catre setari.*/
    private State aboutState;           /*!< Referinta catre about.*/
    private State pauseState;           /*!< Referinta catre pause State.*/
    private State level1State;          /*!< Referinta catre state-ul nivelului 1.*/
    private State level2State;          /*!< Referinta catre state-ul nivelului 2.*/
    private State endState;             /*!< Referinta catre state-ul de final*/

    private final KeyManager keyManager;      /*!< Referinta catre obiectul care gestioneaza intrarile de la tastatura din partea utilizatorului.*/
    private final MouseManager mouseManager;  /*!< Referinta catre obiectul care gestioneaza intrarile mouse din partea utilizatorului.*/
    private RefLinks refLink;                 /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/


    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height) {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        Window = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
            ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
        mouseManager= new MouseManager();
        database = new DBHandler();
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame(){
            /// Este construita fereastra grafica.
        Window.BuildGameWindow();
            ///Sa ataseaza ferestrei managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        Window.GetWindowFrame().addKeyListener(keyManager);
        Window.GetWindowFrame().addMouseListener(mouseManager);
        Window.GetWindowFrame().addMouseMotionListener(mouseManager);
        Window.GetCanvas().addMouseListener(mouseManager);
        Window.GetCanvas().addMouseMotionListener(mouseManager);
            ///Se incarca toate elementele grafice (dale)
        Assets.Init();
            ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
            ///Definirea starilor programului
        playState       = new PlayState(refLink);
        menuState       = new MenuState(refLink);
        settingsState   = new SettingsState(refLink);
        aboutState      = new AboutState(refLink);
        pauseState      = new PauseState(refLink);
        level1State     = new Level1State(refLink);
        level2State     = new Level2State(refLink);
        endState        = new EndState(refLink);
        ///Seteaza starea implicita cu care va fi lansat programul in executie
        State.SetState(menuState);
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {

            /// Initializeaza obiectul game
        InitGame();

        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000f / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void StartGame()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(!runState)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
    }
    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update(){
            ///Determina starea tastelor
        keyManager.Update();
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null)
        {
                ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
            Window.GetWindowFrame().requestFocusInWindow(); // atlfel nu mergeau si KeyManager si MouseManager
            // sper sa nu fie redundant
            // "Requests that this Component gets the input focus."
            // aparent se pierdea la primul click dat focusul pt KeyListener
            // https://stackoverflow.com/questions/286727/unresponsive-keylistener-for-jframe
            // ultimul comment far downvotes.
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = Window.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                Window.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, Window.GetWindowWidth(), Window.GetWindowHeight());

        /// operatie de desenare
            ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
            if(State.GetState() != null)
            {
                ///Actualizez starea curenta a jocului daca exista.
                State.GetState().Draw(g);
            }
        /// end operatie de desenare

            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth()
    {
        return Window.GetWindowWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */
    public int GetHeight()
    {
        return Window.GetWindowHeight();
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return keyManager;
    }
    /*! \fn public MouseManager GetMouseManager()
      \brief Returneaza obiectul care gestioneaza comenzile de la mouse.
     */
    public MouseManager GetMouseManager(){ return mouseManager;}
    /*! \fn public State getPlayState()
          \brief Returneaza playState pentru a fi putea folosit in alte clase.
                 Util atunci cand schimbam state-ul in menuState.
       */
    public State getPlayState()
    {
        return playState;
    }
    /*! \fn public State getMenuState()
          \brief Returneaza menuState pentru a fi putea folosit in alte clase.
                 Util atunci cand schimbam state-ul in aboutState.
       */
    public State getMenuState()
    {
        return menuState;
    }
    /*! \fn public State getAboutState()
          \brief Returneaza aboutState pentru a fi putea folosit in alte clase.
                 Util atunci cand schimbam state-ul in menuState.
       */

    public State getAboutState() { return aboutState; }
    /*! \fn public State getSettingsState()
          \brief Returneaza setttingsState pentru a fi putea folosit in alte clase.
                 Util atunci cand schimbam state-ul in settingsState.
       */

    public State getSettingsState() { return settingsState; }

    /*! \fn public State getPauseState()
      \brief Returneaza pause State pentru a fi putea folosit in alte clase.
             Util atunci cand schimbam state-ul in settingsState.
   */

    public State getPauseState() { return pauseState; }

    /*! \fn public State getLevel1State()
  \brief Returneaza level 1 State pentru a fi putea folosit in alte clase.
    */

    public State getLevel1State() { return level1State; }

    /*! \fn public State getLevel2State()
    \brief Returneaza level 2 State pentru a fi putea folosit in alte clase.
    */

    public State getLevel2State() { return level2State; }

    /*! \fn public State getEndState()
    \brief Returneaza state-ul final pentru a fi putea folosit in alte clase.
           Util cand se termina jocul.
    */

    public State getEndState() { return endState; }

    /*! \fn public DBHandler getDatabase()
        \brief Returneaza DBHandler-ul pe care-l folosim pentru a incarca/schimba setari sau salvari.
     */
    public DBHandler getDatabase() { return database; }
}

