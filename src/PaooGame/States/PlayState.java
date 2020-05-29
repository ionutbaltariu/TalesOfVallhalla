package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.AudioLoader;
import PaooGame.Items.Character;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

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

    private boolean wasEscPressed;                     /*!< Flag care sa indice daca a fost apasata tasta esc. Ne va folosi la popup-ul "Are you sure you want to exit[..]"*/
    private boolean isRunning=false;                   /*!< Flag care ne ajuta sa controlam eficient muzica din PlayState.*/
    private boolean isInStartMap;                      /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta de intrare.*/
    private boolean isInLevel1Map;                     /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta nivelului 1.*/
    private boolean isInLevel2Map;                     /*!< Flag care ne ajuta sa tinem cont daca ne aflam pe harta nivelului 2.*/
    private boolean playWithAutoRetry;

    static boolean wasBoss1Defeated;
    static boolean wasBoss2Defeated;
    private final long boss1Timer=45000000000L;
    private final long boss2Timer=60000000000L;
    private static long iterator1=0;
    private static long iterator2=0;




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
        wasBoss2Defeated=false;
        wasBoss1Defeated=false;
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
            ///Construieste eroul
        hero = Hero.getInstance(refLink,32,32); // cream hero folosind sablonul SINGLETON

        enemyDeath = new Enemy(refLink,640,512,64,64, 2f+0.2f*refLink.GetDatabase().getDifficulty(),1,1);
        enemyDragon = new Enemy(refLink, 640, 512, 96, 96, 1.7f+0.2f*refLink.GetDatabase().getDifficulty(),1, 2);
        AudioLoader.setVolume(Assets.death,40);
        AudioLoader.setVolume(Assets.music,refLink.GetDatabase().getGameVolume());

    }


    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update() throws InterruptedException, SQLException, FileNotFoundException {

        if(MenuState.getLoadButtonFlag())
        {
            loadFromDB();
            readFromFile("matrix.txt");
            MenuState.setLoadButtonFlag(false);
        }

        if(!wasBoss1Defeated)
            if(isInLevel1Map)
                wasBoss1Defeated = isInFight(boss1Timer/1000000000);
        if(!wasBoss2Defeated)
            if(isInLevel2Map)
                wasBoss2Defeated = isInFight(boss2Timer/1000000000);
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
            if(!wasBoss2Defeated) {
                enemyDeath.Update(hero);
                if(level2.tiles[(int)(hero.GetX()/Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]==11)
                {
                    hero.SetScore(hero.GetScore()+100);
                    level2.tiles[(int)(hero.GetX()/Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]=3;
                }
            }
        }
        if(isInLevel1Map)
        {
            level1.Update();
            if(!wasBoss1Defeated) {
                enemyDragon.Update(hero);
                if(level1.tiles[(int)(hero.GetX()/Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]==11)
                {
                    hero.SetScore(hero.GetScore()+30);
                    level1.tiles[(int)(hero.GetX()/Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]=3;
                }
            }
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
                        isInLevel1Map=isInLevel2Map=false;
                        isInStartMap=true;
                            level2.LoadWorld(2);
                            level1.LoadWorld(1);
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
            if(refLink.GetMouseManager().getMouseX()>=506 && refLink.GetMouseManager().getMouseX()<=776)
            {
                if(refLink.GetMouseManager().getMouseY()>=713 && refLink.GetMouseManager().getMouseY()<=809)
                {
                    if(refLink.GetMouseManager().leftClickPressed())
                    {
                        int flagBoss1;
                        int flagBoss2;
                        if(wasBoss1Defeated)
                        {
                            flagBoss1=1;
                        }
                        else
                        {
                            flagBoss1=0;
                        }
                        if(wasBoss2Defeated)
                        {
                            flagBoss2=1;
                        }
                        else
                        {
                            flagBoss2=0;
                        }
                        Thread.sleep(100);
                        if(isInLevel1Map)
                            refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),1,0,0,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY(),flagBoss1,flagBoss2,iterator1,iterator2);
                        if(isInLevel2Map)
                            refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),0,1,0,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY(),flagBoss1,flagBoss2,iterator1,iterator2);
                        if(isInStartMap)
                            refLink.GetDatabase().saveSettings(hero.GetX(),hero.GetY(),hero.GetActualLife(),hero.GetScore(),0,0,1,enemyDeath.GetX(),enemyDeath.GetY(),enemyDragon.GetX(),enemyDragon.GetY(),flagBoss1,flagBoss2,iterator1,iterator2);
                        writeInFile("matrix.txt");
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
                        level2.LoadWorld(2);
                        level1.LoadWorld(1);
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
        if(isInStartMap) {
            if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 40) {
                if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 2) {
                    hero.SetX(32);
                    hero.SetY(80);
                    isInStartMap = false;
                    isInLevel2Map = true;
                    isInLevel1Map = false;
                    refLink.SetMap(level2);
                }
                if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 27) {
                    hero.SetX(32);
                    hero.SetY(881);
                    isInStartMap = false;
                    isInLevel1Map = true;
                    isInLevel2Map = false;
                    refLink.SetMap(level1);
                }
            }
        }
        if(isInLevel2Map) {
            if(wasBoss2Defeated) {
                if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 0) {
                    if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 2) {
                        hero.SetX(1250);
                        hero.SetY(80);
                        isInStartMap = true;
                        isInLevel2Map = false;
                        isInLevel1Map = false;
                        refLink.SetMap(map);
                    }
                }
            }
        }
        if(isInLevel1Map) {
            if(wasBoss1Defeated) {
                if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 0) {
                    if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 27) {
                        hero.SetX(1250);
                        hero.SetY(881);
                        isInStartMap = true;
                        isInLevel2Map = false;
                        isInLevel1Map = false;
                        refLink.SetMap(map);
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
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.PLAIN, 48));

        if(isInStartMap)
            map.Draw(g);
        if(isInLevel1Map) {
            level1.Draw(g);
            if(!wasBoss1Defeated) {
                g.drawString(Long.toString(boss1Timer/1000000000L-iterator1),80,refLink.GetHeight()-50);
                if(Character.secondElapsed())
                    iterator1+=1;
                enemyDragon.Draw(g);
            }
        }
        if(isInLevel2Map) {
            level2.Draw(g);
            if(!wasBoss2Defeated) {
                long swp=boss2Timer/1000000000L-iterator2;
                g.drawString(Long.toString(swp),80,refLink.GetHeight()-50);
                if(Character.secondElapsed())
                    iterator2+=1;
                enemyDeath.Draw(g);
            }
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
        wasBoss1Defeated=false;
        wasBoss2Defeated=false;
        level1.LoadWorld(1);
        level2.LoadWorld(2);
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

    /*! \fn private void loadFromDB() throws SQLException
        \brief Functie care incarca parametrii vitali ai jocului din baza de date pentru a facilita conceptul de "Load Game"

     */
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
        wasBoss1Defeated= refLink.GetDatabase().wasBoss1Defeated() == 1;
        wasBoss2Defeated= refLink.GetDatabase().wasBoss2Defeated() == 1;
        iterator1=refLink.GetDatabase().getIterator1();
        iterator2=refLink.GetDatabase().getIterator2();
    }

    /*! \fn private boolean isInFight( long fightTime)
    \brief Functie care intoarce true daca hero-ul este inca in conflict cu eroul.

    \param fightTime este timpul (nanosecunde) in care eroul trebuie sa supravietuiasca directei confruntari cu inamicul.

    */
    private boolean isInFight( long fightTime)
    {
        if(isInLevel1Map) {
            return fightTime - iterator1 < 0;
        }
        if(isInLevel2Map)
        {

            return fightTime - iterator2 < 0;
        }
        return false;
    }

    /*! \fn public static void resetIterator1()
    \brief Functie care reseteaza timer-ul fight-ului cu primul inamic.

    */
    public static void resetIterator1() {iterator1=0;}

    /*! \fn public static void resetIterator2)
        \brief Functie care reseteaza timer-ul fight-ului c al doilea inamic.

    */
    public static void resetIterator2() {iterator2=0;}

    /*! \fn public void writeInFile(String path)
        \brief Functie care faciliteaza scrierea in fisier a matricelor folosite la generarea hartilor nivelelor 1 si 2.

        \param path Reprezinta un string care indica adresa relativa a fisierului din care se doreste a se face scrierea.
    */
    public void writeInFile(String path)
    {
        FileWriter f;
        try {
            {
                f = new FileWriter(path);
                for(int i=0; i<40; ++i)
                {
                    for(int j=0;j<32;++j) {
                        f.write(level1.tiles[i][j] + " ");
                    }
                    f.write("\n");
                }
                for(int i=0; i<40; ++i)
                {
                    for(int j=0;j<32;++j) {
                        f.write(level2.tiles[i][j] + " ");
                    }
                    f.write("\n");
                }
                f.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*! \fn private void readFromFile(String path) throws FileNotFoundException
        \brief Functie care faciliteaza citirea din fisier a hartilor nivelelor 1 si 2 atunci cand apasam "Load".
               Am folosit fisiere in cazul hartilor pentru ca stocarea acesteia in baza de date ar presupune memorarea sub forma de String, apoi parsarea acestuia

        \param path Reprezinta un string care indica adresa relativa a fisierului din care se doreste a se citi.
    */
    private void readFromFile(String path) throws FileNotFoundException {
        Scanner input;
        input = new Scanner(new File(path));
        for(int i = 0; i < 40; ++i)
        {
            for(int j = 0; j < 32; ++j)
            {
                if(input.hasNextInt())
                {
                    level1.tiles[i][j] = input.nextInt();
                }
            }
        }
        for(int i = 0; i < 40; ++i)
        {
            for(int j = 0; j < 32; ++j)
            {
                if(input.hasNextInt())
                {
                    level2.tiles[i][j] = input.nextInt();
                }
            }
        }
    }

}
