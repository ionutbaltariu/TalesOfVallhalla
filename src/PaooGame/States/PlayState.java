package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.AudioLoader;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;

import java.awt.*;
import java.sql.SQLException;

/*! \class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private final Hero hero;                   /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private final Enemy enemyDeath;            /*!< Referinta catre inamicul Moarte.*/
    private final Enemy enemyDragon;           /*!< Referinta catre inamicul Dragon.*/
    private final Map map;                     /*!< Referinta catre harta de intrare.*/
    private final Map level1;                  /*!< Referinta catre harta nivelului 1.*/
    private final Map level2;                  /*!< Referinta catre harta nivelului 2.*/

    boolean wasEscPressed;                     /*!< Flag care sa indice daca a fost apasata tasta esc. Ne va folosi la popup-ul "Are you sure you want to exit[..]"*/
    boolean isRunning=false;                   /*!< Flag care ne ajuta sa controlam eficient muzica din PlayState.*/
    boolean isInStartMap;                      /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta de intrare.*/
    boolean isInLevel1Map;                     /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta nivelului 1.*/
    boolean isInLevel2Map;                     /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta nivelului 2.*/
    boolean playWithAutoRetry;




    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink) throws SQLException {
        ///Apel al constructorului clasei de baza
        super(refLink);
            ///Construieste harta jocului
        map    = new Map(refLink,0);
        level1 = new Map(refLink, 1);
        level2 = new Map(refLink,2);
        isInStartMap=true;
        isInLevel2Map=false;
        isInLevel1Map=false;
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
            ///Construieste eroul
        hero = Hero.getInstance(refLink,32,32); // cream hero folosind sablonul SINGLETON

        enemyDeath = new Enemy(refLink,640,512,64,64,1.8f,25,1);
        enemyDragon = new Enemy(refLink, 640, 512, 96, 96, 1.5f,40, 2);
        AudioLoader.setVolume(Assets.death,40);
        AudioLoader.setVolume(Assets.music,refLink.GetDatabase().getGameVolume());

    }


    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update() throws InterruptedException, SQLException {

        if(MenuState.getLoadButtonFlag())
        {
            loadFromDB();
            MenuState.setLoadButtonFlag(false);
        }
        playWithAutoRetry= refLink.GetDatabase().getAR() == 1;

        if(!isRunning) // pentru muzica
        {
            Assets.music.setFramePosition(0);
            Assets.music.start();
            isRunning=true;
        }

        if(isInStartMap)
            map.Update();

        if(isInLevel2Map)
        {
            level2.Update();
            enemyDragon.Update(hero);
        }
        if(isInLevel1Map)
        {
            level1.Update();
            enemyDeath.Update(hero);
        }
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
                        retry();
                        State.SetState(refLink.GetGame().getMenuState());
                        Thread.sleep(100);
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

        if(hero.isDead())
        {
            if(playWithAutoRetry)
            {
                retry();
            }
            Assets.music.stop();
            Assets.death.start();
            if(refLink.GetMouseManager().getMouseY()>=575 && refLink.GetMouseManager().getMouseY()<=690)
            {
                if(refLink.GetMouseManager().getMouseX()>=253 && refLink.GetMouseManager().getMouseX()<=568)
                {
                    if(refLink.GetMouseManager().leftClickPressed())
                    {
                        retry();
                    }
                }
                if(refLink.GetMouseManager().getMouseX()>=705 && refLink.GetMouseManager().getMouseX()<=1020)
                {
                    if(refLink.GetMouseManager().leftClickPressed())
                    {
                        System.exit(0);
                    }
                }
            }
        }

        if(hero.GetX()==1280) {
            if (hero.GetY() == 80) {
                hero.SetX(32);
                hero.SetY(80);
                isInStartMap = false;
                isInLevel2Map = true;
                refLink.SetMap(level2);
            }
            if (hero.GetY() == 881) {
                hero.SetX(32);
                hero.SetY(80);
                isInStartMap = false;
                isInLevel1Map = true;
                refLink.SetMap(level1);
            }
        }

        if(refLink.GetMouseManager().getMouseX()>=506 && refLink.GetMouseManager().getMouseX()<=776)
        {
            if(refLink.GetMouseManager().getMouseY()>=713 && refLink.GetMouseManager().getMouseY()<=809)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    Thread.sleep(100);
                    if(isInLevel1Map)
                        refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),1,0,0,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY());
                    if(isInLevel2Map)
                        refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),0,1,0,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY());
                    if(isInStartMap)
                        refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),0,0,1,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY());

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
        if(isInStartMap)
            map.Draw(g);
        if(isInLevel1Map) {
            level1.Draw(g);
            enemyDeath.Draw(g);
        }
        if(isInLevel2Map) {
            level2.Draw(g);
            enemyDragon.Draw(g);
        }
        hero.Draw(g);
        if(refLink.GetKeyManager().esc)
        {
            wasEscPressed=true;
        }

        if(hero.isDead())
        {
            g.drawImage(Assets.deadState,0,0, refLink.GetWidth(),refLink.GetHeight(),null);
            drawHoverObject(g);
        }
        else
        {
            if(wasEscPressed)
            {
                g.drawImage(Assets.suspend,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
                drawHoverObject(g);
            }
        }

        //pentru scor
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.PLAIN, 48));
        g.drawString("Score:",refLink.GetWidth()-220,refLink.GetHeight()-100);
        g.drawString(Integer.toString(hero.GetScore()),refLink.GetWidth()-220,refLink.GetHeight()-50);
    }

    /*! \fn private void retry()
        \brief Face posibila reluarea jocului dupa ce moare eroul. (Daca utilizatorul alege aceasta optiune.

     */
    private void retry()
    {
        hero.SetX(32);
        hero.SetY(32);
        hero.SetActualLife(hero.GetLife());
        hero.SetScore(0);
        isInLevel1Map=isInLevel2Map=false;
        isInStartMap=true;
        enemyDeath.SetX(640);
        enemyDeath.SetY(512);
        enemyDragon.SetX(640);
        enemyDragon.SetY(512);
        refLink.SetMap(map);
        Assets.death.setFramePosition(0);
        Assets.death.stop();
        Assets.music.setFramePosition(0);
        Assets.music.stop();
        isRunning=false;
    }

    /*! \fn private void drawHoverObject(Graphics g)
        \brief Functie creata pentru a evita duplicarea codului atunci cand dorim sa desenam elementul grafic
               ce dorim sa apara atunci cand tinem mouse-ul peste un buton.

     */
    private void drawHoverObject(Graphics g)
    {
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

        if(refLink.GetMouseManager().getMouseX()>=506 && refLink.GetMouseManager().getMouseX()<=776)
        {
            if(refLink.GetMouseManager().getMouseY()>=713 && refLink.GetMouseManager().getMouseY()<=809)
            {
                g.drawImage(Assets.hoverElement,587,590,100,100,null);
            }
        }
    }

    private void loadFromDB() throws SQLException {
        hero.SetX(refLink.GetDatabase().getHeroX());
        hero.SetY(refLink.GetDatabase().getHeroY());
        hero.SetActualLife(refLink.GetDatabase().getHeroLife());
        hero.SetScore(refLink.GetDatabase().getHeroScore());
        if(refLink.GetDatabase().getIsInLvl1()==1)
        {
            isInLevel1Map=true;
            isInLevel2Map=false;
            isInStartMap=false;
            refLink.SetMap(level1);
        }
        if(refLink.GetDatabase().getIsInLvl2()==1)
        {
            isInLevel1Map=false;
            isInLevel2Map=true;
            isInStartMap=false;
            refLink.SetMap(level2);
        }
        if(refLink.GetDatabase().getIsInStart()==1)
        {
            isInLevel1Map=false;
            isInLevel2Map=false;
            isInStartMap=true;
            refLink.SetMap(map);
        }
        enemyDeath.SetX(refLink.GetDatabase().getEnemy1X());
        enemyDeath.SetY(refLink.GetDatabase().getEnemy1Y());
        enemyDragon.SetX(refLink.GetDatabase().getEnemy2X());
        enemyDragon.SetY(refLink.GetDatabase().getEnemy2Y());
    }
}
