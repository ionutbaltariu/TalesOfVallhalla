package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.AudioLoader;
import PaooGame.Items.Hero;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/*! \class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State {
    public static Map map;                     /*!< Referinta catre harta de intrare.*/
    protected final Hero hero;                   /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private boolean isRunning = false;                   /*!< Flag care ne ajuta sa controlam eficient muzica din PlayState.*/


    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map(refLink, 0);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        ///Construieste eroul
        hero = Hero.getInstance(refLink, 32, 32); // cream hero folosind sablonul SINGLETON
        AudioLoader.setVolume(Assets.death, 40);
        try {
            AudioLoader.setVolume(Assets.music, refLink.GetDatabase().getGameVolume());
        } catch (SQLException e) {
            System.err.println("Eroare in PlayState la incarcarea din baza de date a GameVolume.");
        }
    }

    /*! \fn protected void writeInFile(String path)
        \brief Functie care faciliteaza scrierea in fisier a matricelor folosite la generarea hartilor nivelelor 1 si 2.

        \param path Reprezinta un string care indica adresa relativa a fisierului din care se doreste a se face scrierea.
    */
    protected static void writeInFile(String path, int[][] matrix) {
        FileWriter f;
        try {
            f = new FileWriter(path);
            for (int i = 0; i < 40; ++i) {
                for (int j = 0; j < 32; ++j) {
                    f.write(matrix[i][j] + " ");
                }
                f.write("\n");
            }
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*! \fn protected void readFromFile(String path) throws FileNotFoundException
        \brief Functie care faciliteaza citirea din fisier a hartilor nivelelor 1 si 2 atunci cand apasam "Load".
               Am folosit fisiere in cazul hartilor pentru ca stocarea acesteia in baza de date ar presupune memorarea sub forma de String, apoi parsarea acestuia

        \param path Reprezinta un string care indica adresa relativa a fisierului din care se doreste a se citi.
    */
    protected static void readFromFile(String path, int[][] matrix) {
        Scanner input;
        try {
            input = new Scanner(new File(path));
            for (int i = 0; i < 40; ++i) {
                for (int j = 0; j < 32; ++j) {
                    if (input.hasNextInt()) {
                        matrix[i][j] = input.nextInt();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Eroare la citirea fisierului.");
        }
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update() {
        if (!isRunning) // pentru muzica
        {
            Assets.music.setFramePosition(0);
            Assets.music.start();
            isRunning = true;
        }

        map.Update();
        hero.Update();

        if (refLink.GetKeyManager().esc) {
            State.SetState(refLink.GetGame().getPauseState());
        }
        //aici testam sa vedem daca a ajuns pe dala care ar trebui sa schimbe nivelul
        if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 40) {
            if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 2) {
                hero.SetX(32);
                hero.SetY(80);
                State.SetState(refLink.GetGame().getLevel2State());
                refLink.SetMap(Level2State.level2);
            }
            if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 27) {
                hero.SetX(32);
                hero.SetY(881);
                State.SetState(refLink.GetGame().getLevel1State());
                refLink.SetMap(Level1State.level1);
            }
        }
        if (Level1State.wasBoss1Defeated && Level2State.wasBoss2Defeated) {
            hero.SetScore(hero.GetScore() + hero.getNrOfHits() * 50); // de aceea spuneam ca e de preferat sa nu folosim excesiv hit-urile.
            // scorul creste in functie de cate hit-uri nu a folosit eroul.
            map.setTile(7, 22, 11);
            if ((int) (hero.GetX() / Tile.TILE_WIDTH + 1) == 7 && (int) (hero.GetY() / Tile.TILE_HEIGHT + 1) == 22) {
                Assets.music.stop();
                State.SetState(refLink.GetGame().getEndState());
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.PLAIN, 48));

        map.Draw(g);
        hero.Draw(g);

        //pentru scor
        g.drawString("Score:", refLink.GetWidth() - 220, refLink.GetHeight() - 100);
        g.drawString(Integer.toString(hero.GetScore()), refLink.GetWidth() - 220, refLink.GetHeight() - 50);
    }

    /*! \fn protected void retry()
        \brief Face posibila reluarea jocului dupa ce moare eroul. (Daca utilizatorul alege aceasta optiune.

     */
    protected void retry() {
        hero.SetX(32);
        hero.SetY(32);
        hero.SetActualLife(hero.GetLife());
        hero.SetScore(0);
        hero.setNrOfHits(Hero.DEFAULT_NR_OF_HITS);
        refLink.SetMap(map);
        Assets.death.setFramePosition(0);
        Assets.death.stop();
        Assets.music.setFramePosition(0);
        Assets.music.stop();
        isRunning = false;
    }

    /*! \fn private void drawHoverObject(Graphics g)
        \brief Functie creata pentru a evita duplicarea codului atunci cand dorim sa desenam elementul grafic
               ce dorim sa apara atunci cand tinem mouse-ul peste un buton.

     */
    protected void drawHoverObject(Graphics g) {
        if (refLink.GetMouseManager().getMouseY() >= 575 && refLink.GetMouseManager().getMouseY() <= 690) {
            if (refLink.GetMouseManager().getMouseX() >= 253 && refLink.GetMouseManager().getMouseX() <= 568) {
                g.drawImage(Assets.hoverElement, 360, 710, 100, 100, null);
            }
            if (refLink.GetMouseManager().getMouseX() >= 705 && refLink.GetMouseManager().getMouseX() <= 1020) {
                g.drawImage(Assets.hoverElement, 812, 710, 100, 100, null);
            }
        }

        if (refLink.GetMouseManager().getMouseX() >= 506 && refLink.GetMouseManager().getMouseX() <= 776) {
            if (refLink.GetMouseManager().getMouseY() >= 713 && refLink.GetMouseManager().getMouseY() <= 809) {
                g.drawImage(Assets.hoverElement, 587, 590, 100, 100, null);
            }
        }
    }

    /*! \fn protected void loadFromDB() throws SQLException
        \brief Functie care incarca parametrii vitali ai jocului din baza de date pentru a facilita conceptul de "Load Game"

     */
    protected void loadFromDB() {
        try {
            hero.SetX(refLink.GetDatabase().getHeroX());
            hero.SetY(refLink.GetDatabase().getHeroY());
            hero.SetActualLife(refLink.GetDatabase().getHeroLife());
            hero.SetScore(refLink.GetDatabase().getHeroScore());
        } catch (SQLException e) {
            System.err.print("Eroare in PlayState->LoadFromDB");
        }
    }

    /*! \fn protected void died(Map map, int mapNr) throws SQLException
    \brief Functie care faciliteaza selectarea optiunilor aparute pe ecran o data cu moartea caracterului.

    \param map Harta in care a murit eroul si care trebuie resetata.
    \param mapNr identificator pentru harta necesar in clasa Map
    */
    protected void died(Map map, int mapNr) {
        try {
            if (refLink.GetDatabase().getAR() == 1) {
                retry();
                map.LoadWorld(mapNr);
            }
        } catch (SQLException e) {
            System.err.println("Eroare in `died->PlayState` la incarcarea AR din baza de date.");
        }
        Assets.music.stop();
        Assets.death.start();
        if (refLink.GetMouseManager().getMouseY() >= 575 && refLink.GetMouseManager().getMouseY() <= 690) {
            if (refLink.GetMouseManager().getMouseX() >= 253 && refLink.GetMouseManager().getMouseX() <= 568) {
                if (refLink.GetMouseManager().leftClickPressed()) {
                    retry();
                    map.LoadWorld(mapNr);
                    State.SetState(refLink.GetGame().getPlayState());
                }
            }
            if (refLink.GetMouseManager().getMouseX() >= 705 && refLink.GetMouseManager().getMouseX() <= 1020) {
                if (refLink.GetMouseManager().leftClickPressed()) {

                    System.exit(0);
                }
            }
        }
    }
}
