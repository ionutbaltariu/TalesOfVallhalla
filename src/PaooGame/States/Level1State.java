package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Dragon;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.sql.SQLException;

/*! \class Level1State extends State
    \brief Implementeaza nivelul 1.
 */
public class Level1State extends PlayState{

    public  static Map level1;
    public static boolean wasBoss1Defeated;
    private final long boss1Timer=30000000000L;
    public static Dragon enemyDragon;           /*!< Referinta catre inamicul Dragon.*/
    public static int timer1=0;

    /*! \fn public Level1State(RefLinks refLink)
            \brief Constructorul de initializare al clasei

            \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
         */
    public Level1State(RefLinks refLink) {
        super(refLink);
        level1 = new Map(refLink, 1);
        wasBoss1Defeated=false;
        try {
            enemyDragon = new Dragon(refLink, 640, 512, 96, 96, 1.7f + 0.2f * refLink.GetDatabase().getDifficulty(), 1);
        }
        catch( SQLException e)
        {
            System.err.println("Eroare in Level1State->Constructor.");
        }
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update(){
        if(State.GetPreviousState()==refLink.GetGame().getMenuState())
        {
            if(MenuState.getLoadButtonFlag())
            {
                loadFromDB();
            }
            MenuState.setLoadButtonFlag(false);
            Assets.music.setFramePosition(0);
            Assets.music.start();
        }
        MenuState.setLoadButtonFlag(false);
        System.out.println(hero.GetX()+", "+hero.GetY());
        hero.Update();
        wasBoss1Defeated = isInFight(boss1Timer/1000000000);
        if(!wasBoss1Defeated) {
            enemyDragon.Update(hero);
            if(level1.tiles[(int)(hero.GetX()/ Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]==11)
            {
                try {
                    hero.SetScore(hero.GetScore() + 30 * refLink.GetDatabase().getDifficulty());
                }
                catch (SQLException e)
                {
                    System.err.println("Eroare in Level1State->Update->[..].getDifficulty()");
                }
                level1.tiles[(int)(hero.GetX()/Tile.TILE_WIDTH)+1][(int)(hero.GetY()/Tile.TILE_HEIGHT)+1]=3;
            }
        }

        if(wasBoss1Defeated) {
            if ((int) (hero.GetX() / Tile.TILE_WIDTH) == 0) {
                if ((int) (hero.GetY() / Tile.TILE_HEIGHT) == 27) {
                    hero.SetX(1250);
                    hero.SetY(881);
                    State.SetState(refLink.GetGame().getPlayState());
                    refLink.SetMap(PlayState.map);
                }
            }
        }
        if(refLink.GetKeyManager().esc) {
            State.SetState(refLink.GetGame().getPauseState());
        }

        if(isInFight(timer1))
        {
            wasBoss1Defeated=true;
        }
        if(hero.isDead()) {
            died(level1, 1);
            enemyDragon.SetX(640);
            enemyDragon.SetY(512);
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

        level1.Draw(g);
        if(!wasBoss1Defeated) {
            g.drawString(Long.toString(boss1Timer/1000000000L-timer1),80,refLink.GetHeight()-50);
            if(Assets.secondElapsed())
                timer1+=1;
            enemyDragon.Draw(g);
            g.drawString(Long.toString(boss1Timer/1000000000L-timer1),80,refLink.GetHeight()-50);
        }
        hero.Draw(g);

        g.drawString("Score:",refLink.GetWidth()-220,refLink.GetHeight()-100);
        g.drawString(Integer.toString(hero.GetScore()),refLink.GetWidth()-220,refLink.GetHeight()-50);

        if(hero.isDead())
        {
            g.drawImage(Assets.deadState,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
            drawHoverObject(g);
        }
    }

    /*! \fn rivate boolean isInFight( long fightTime)
        \brief Functie care faciliteaza determinarea starii conflictului dintre erou si inamic.
               Returneaza true daca s-a terminat batalia.

     */
    private boolean isInFight( long fightTime)
    {
            return fightTime - timer1< 0;
    }

    /*! \fn protected void retry()
        \brief Face posibila reluarea jocului dupa ce moare eroul. (Daca utilizatorul alege aceasta optiune)
               Suprascrisa pentru a reincarca parametrii vitali din nivelul curent.

     */
    @Override
    public void retry() {
        super.retry();
        enemyDragon.SetX(640);
        enemyDragon.SetY(512);
        timer1=0;
    }

    /*! \fn protected void loadFromDB() throws SQLException
        \brief Functie care incarca parametrii vitali ai jocului din baza de date pentru a facilita conceptul de "Load Game"
               Functia este suprascrisa pentru a memora si parametrii din nivelul din care se apeleaza.

     */
    @Override
    protected void loadFromDB(){
        super.loadFromDB();
        try {
            enemyDragon.SetX(refLink.GetDatabase().getEnemy1X());
            enemyDragon.SetX(refLink.GetDatabase().getEnemy1Y());
            timer1 = (int) refLink.GetDatabase().getIterator1();
            wasBoss1Defeated = refLink.GetDatabase().wasBoss1Defeated() == 1;
        }
        catch (SQLException e)
        {
            System.err.println("Eroare in Level1State->LoadFromDB.");
        }
        readFromFile("matrix.txt",level1.tiles);
    }
}
