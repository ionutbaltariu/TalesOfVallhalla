package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Death;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.sql.SQLException;

/*! \class Level2State extends State
    \brief Implementeaza nivelul 2.
 */
public class Level2State extends PlayState {

    public static Map level2;
    public static boolean wasBoss2Defeated;
    public static Death enemyDeath;           /*!< Referinta catre inamicul Dragon.*/
    public static int timer2 = 0;
    private final long boss2Timer = 45000000000L;

    /*! \fn public Level2State(RefLinks refLink)
            \brief Constructorul de initializare al clasei

            \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
         */
    public Level2State(RefLinks refLink) {
        super(refLink);
        level2 = new Map(refLink, 2);
        wasBoss2Defeated = false;
        try {
            enemyDeath = new Death(refLink, 640, 512, 64, 64, 2f + 0.2f * refLink.GetDatabase().getDifficulty(), 1);
        } catch (SQLException e) {
            System.err.println("Eroare in Level2State->Constructor.");
        }
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update() {
        if (State.GetPreviousState() == refLink.GetGame().getMenuState()) {
            if (MenuState.getLoadButtonFlag()) {
                loadFromDB();
            }
            MenuState.setLoadButtonFlag(false);
            Assets.music.setFramePosition(0);
            Assets.music.start();
        }
        System.out.println(hero.GetX() + ", " + hero.GetY());
        hero.Update();
        wasBoss2Defeated = isInFight(boss2Timer / 1000000000);
        if (!wasBoss2Defeated) {
            enemyDeath.Update(hero);
            if (level2.tiles[(int) (hero.GetX() / Tile.TILE_WIDTH) + 1][(int) (hero.GetY() / Tile.TILE_HEIGHT) + 1] == 11) {
                try {
                    hero.SetScore(hero.GetScore() + 60 * refLink.GetDatabase().getDifficulty());
                } catch (SQLException e) {
                    System.err.println("Eroare in Level2State->Update()->getDifficulty.");
                }
                level2.tiles[(int) (hero.GetX() / Tile.TILE_WIDTH) + 1][(int) (hero.GetY() / Tile.TILE_HEIGHT) + 1] = 3;
            }
        }

        if (wasBoss2Defeated) {
            if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 0) {
                if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 2) {
                    hero.SetX(1250);
                    hero.SetY(80);
                    State.SetState(refLink.GetGame().getPlayState());
                    refLink.SetMap(PlayState.map);
                }
            }
        }
        if (refLink.GetKeyManager().esc) {
            State.SetState(refLink.GetGame().getPauseState());
        }
        if (isInFight(timer2)) {
            wasBoss2Defeated = true;
        }
        if (hero.isDead()) {
            died(level2, 2);
            enemyDeath.SetX(640);
            enemyDeath.SetY(512);
            timer2 = 0;
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

        level2.Draw(g);
        if (!wasBoss2Defeated) {
            g.drawString(Long.toString(boss2Timer / 1000000000L - timer2), 80, refLink.GetHeight() - 50);
            if (Assets.secondElapsed())
                timer2 += 1;
            enemyDeath.Draw(g);
            g.drawString(Long.toString(boss2Timer / 1000000000L - timer2), 80, refLink.GetHeight() - 50);
        }
        hero.Draw(g);

        g.drawString("Score:", refLink.GetWidth() - 220, refLink.GetHeight() - 100);
        g.drawString(Integer.toString(hero.GetScore()), refLink.GetWidth() - 220, refLink.GetHeight() - 50);

        if (hero.isDead()) {
            g.drawImage(Assets.deadState, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
            drawHoverObject(g);
        }
    }

    /*! \fn rivate boolean isInFight( long fightTime)
        \brief Functie care faciliteaza determinarea starii conflictului dintre erou si inamic.
               Returneaza true daca s-a terminat batalia.

     */
    private boolean isInFight(long fightTime) {
        return fightTime - timer2 < 0;
    }

    /*! \fn protected void retry()
        \brief Face posibila reluarea jocului dupa ce moare eroul. (Daca utilizatorul alege aceasta optiune)
               Suprascrisa pentru a reincarca parametrii vitali din nivelul curent.

     */
    @Override
    public void retry() {
        super.retry();
        enemyDeath.SetX(640);
        enemyDeath.SetY(512);
        timer2 = 0;
    }

    /*! \fn protected void loadFromDB() throws SQLException
        \brief Functie care incarca parametrii vitali ai jocului din baza de date pentru a facilita conceptul de "Load Game"
               Functia este suprascrisa pentru a memora si parametrii din nivelul din care se apeleaza.

     */
    @Override
    protected void loadFromDB() {
        super.loadFromDB();
        try {
            enemyDeath.SetX(refLink.GetDatabase().getEnemy2X());
            enemyDeath.SetY(refLink.GetDatabase().getEnemy2Y());
            timer2 = (int) refLink.GetDatabase().getIterator2();
            wasBoss2Defeated = refLink.GetDatabase().wasBoss2Defeated() == 1;
        } catch (SQLException e) {
            System.err.println("Eroare in Level2State->LoadFromDB.");
        }
        readFromFile("matrix.txt", level2.tiles);
    }
}
