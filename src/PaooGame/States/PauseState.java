package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.sql.SQLException;

/*! \class PauseState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class PauseState extends PlayState {

    /*! \fn public PauseState(RefLinks refLink)
        \brief Constructorul clasei.
        \param refLink o clasa folosita pentru a accesa variate elemente ale jocului in mod rapid.
     */
    public PauseState(RefLinks refLink) {
        super(refLink);
    }

    /*! \fn public PauseState(RefLinks refLink)
            \brief Constructorul clasei.
            \param refLink o clasa folosita pentru a accesa variate elemente ale jocului in mod rapid.
    */
    @Override
    public void Update() {
        if (refLink.GetMouseManager().getMouseY() >= 575 && refLink.GetMouseManager().getMouseY() <= 690) {
            if (refLink.GetMouseManager().getMouseX() >= 253 && refLink.GetMouseManager().getMouseX() <= 568) {
                if (refLink.GetMouseManager().leftClickPressed()) {
                    Assets.buttonClick.setFramePosition(0);
                    Assets.buttonClick.start();
                    Level2State.enemyDeath.SetX(640);
                    Level2State.enemyDeath.SetY(512);
                    Level2State.timer2 = 0;
                    Level2State.level2.LoadWorld(2);
                    Level1State.enemyDragon.SetX(640);
                    Level1State.enemyDragon.SetY(512);
                    Level1State.timer1 = 0;
                    Level1State.level1.LoadWorld(1);
                    retry();
                    State.SetState(refLink.GetGame().getMenuState());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println("Eroare thread in PauseState->Update");
                    }
                }
            }
            if (refLink.GetMouseManager().getMouseX() >= 705 && refLink.GetMouseManager().getMouseX() <= 1020) {
                if (refLink.GetMouseManager().leftClickPressed()) {
                    Assets.buttonClick.setFramePosition(0);
                    Assets.buttonClick.start();
                    State.SetState(State.GetPreviousState());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println("Eroare thread in PauseState->Update");
                    }
                }
            }
        }
        if (refLink.GetMouseManager().getMouseX() >= 506 && refLink.GetMouseManager().getMouseX() <= 776)
            if (refLink.GetMouseManager().getMouseY() >= 713 && refLink.GetMouseManager().getMouseY() <= 809)
                if (refLink.GetMouseManager().leftClickPressed()) {
                    Assets.buttonClick.setFramePosition(0);
                    Assets.buttonClick.start();
                    handleSave();
                }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.wallpaper, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        g.drawImage(Assets.suspend, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        drawHoverObject(g);
    }

    /*! \fn protected void handleSave()
    \brief Functie care faciliteaza tratarea situatiei in care se doreste salvarea jocului.
    */
    protected void handleSave() {

        int flagBoss1 = 0, flagBoss2 = 0;
        if (Level1State.wasBoss1Defeated) {
            flagBoss1 = 1;
        }
        if (Level2State.wasBoss2Defeated) {
            flagBoss2 = 1;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Eroare thread in PauseState->handleSave");
        }
        try {
            if (State.GetPreviousState() == refLink.GetGame().getPlayState()) {
                refLink.GetDatabase().saveSettings(hero.GetX(), hero.GetY(), hero.GetActualLife(), hero.GetScore(), 0, 0, 1, Level1State.enemyDragon.GetX(), Level1State.enemyDragon.GetY(), Level2State.enemyDeath.GetX(), Level2State.enemyDeath.GetY(), flagBoss1, flagBoss2, Level1State.timer1, Level2State.timer2);
            }
            if (State.GetPreviousState() == refLink.GetGame().getLevel1State()) {
                System.out.println("da");
                refLink.GetDatabase().saveSettings(hero.GetX(), hero.GetY(), hero.GetActualLife(), hero.GetScore(), 1, 0, 0, Level1State.enemyDragon.GetX(), Level1State.enemyDragon.GetY(), Level2State.enemyDeath.GetX(), Level2State.enemyDeath.GetY(), flagBoss1, flagBoss2, Level1State.timer1, Level2State.timer2);
                writeInFile("matrix.txt", Level1State.level1.tiles);
            }
            if (State.GetPreviousState() == refLink.GetGame().getLevel2State()) {
                refLink.GetDatabase().saveSettings(hero.GetX(), hero.GetY(), hero.GetActualLife(), hero.GetScore(), 0, 1, 0, Level1State.enemyDragon.GetX(), Level1State.enemyDragon.GetY(), Level2State.enemyDeath.GetX(), Level2State.enemyDeath.GetY(), flagBoss1, flagBoss2, Level1State.timer1, Level2State.timer2);
                writeInFile("matrix.txt", Level2State.level2.tiles);
            }
        } catch (SQLException e) {
            System.err.print("Eroare PlayState->Handle Save la incarcare din baza de date.");
        }
    }
}
