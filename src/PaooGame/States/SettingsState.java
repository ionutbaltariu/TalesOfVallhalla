package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.AudioLoader;
import PaooGame.RefLinks;

import java.awt.*;
import java.sql.SQLException;

/*! \class SettingsState extends State
    \brief Implementeaza notiunea de settings pentru joc.

    Aici setarile vor trebui salvate/incarcate intr-un/dintr-un fisier/baza de date sqlite.
 */
public class SettingsState extends State
{
    /*! \fn public SettingsState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public SettingsState(RefLinks refLink){
            ///Apel al construcotrului clasei de baza.
        super(refLink);
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea setarilor.
     */
    @Override
    public void Update() throws SQLException, InterruptedException {
        if(refLink.GetMouseManager().getMouseY()>=851 && refLink.GetMouseManager().getMouseY()<=908)
        {
            if(refLink.GetMouseManager().getMouseX()>=146 && refLink.GetMouseManager().getMouseX()<=465)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                    State.SetState(refLink.GetGame().getMenuState());
            }
            if(refLink.GetMouseManager().getMouseX()>=822 && refLink.GetMouseManager().getMouseX()<=1136)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                    System.exit(0);
            }
        }

        if(refLink.GetMouseManager().getMouseX()>=940 && refLink.GetMouseManager().getMouseX()<=1080)
        {
            //changing difficulty
            if(refLink.GetMouseManager().getMouseY()>=424 && refLink.GetMouseManager().getMouseY()<=474)
            {
                if(refLink.GetMouseManager().leftClickPressed()) {
                    if (refLink.GetDatabase().getDifficulty() == 3) {
                        refLink.GetDatabase().updateDifficulty(1);
                    } else {
                        refLink.GetDatabase().updateDifficulty(refLink.GetDatabase().getDifficulty() + 1);
                    }
                    Thread.sleep(100);
                }
            }
            if(refLink.GetMouseManager().getMouseY()>=500 && refLink.GetMouseManager().getMouseY()<=550)
            {
                if(refLink.GetMouseManager().leftClickPressed()) {
                    if (refLink.GetDatabase().getMenuVolume() == 100) {
                        refLink.GetDatabase().updateMenuMusicVolume(0);
                    } else {
                        refLink.GetDatabase().updateMenuMusicVolume(refLink.GetDatabase().getMenuVolume() + 10);
                    }
                    AudioLoader.setVolume(Assets.menuMusic,refLink.GetDatabase().getMenuVolume());
                    Thread.sleep(100);
                }
            }
            if(refLink.GetMouseManager().getMouseY()>=582 && refLink.GetMouseManager().getMouseY()<=632)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    if(refLink.GetDatabase().getGameVolume()==100)
                    {
                        refLink.GetDatabase().updateGameMusicVolume(0);
                    }
                    else
                    {
                        refLink.GetDatabase().updateGameMusicVolume(refLink.GetDatabase().getGameVolume()+10);
                    }
                    AudioLoader.setVolume(Assets.music,refLink.GetDatabase().getGameVolume());
                    Thread.sleep(100);
                }
            }
            if(refLink.GetMouseManager().getMouseY()>=662 && refLink.GetMouseManager().getMouseY()<=712)
            {
                if(refLink.GetMouseManager().leftClickPressed())
                {
                    if(refLink.GetDatabase().getAR()==1)
                    {
                        refLink.GetDatabase().updateAutoRetry(0);
                    }
                    else
                    {
                        refLink.GetDatabase().updateAutoRetry(1);
                    }
                    Thread.sleep(100);
                }
            }
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran setarile.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    @Override
    public void Draw(Graphics g) throws SQLException {
        g.drawImage(Assets.settingsBackground,0,0,refLink.GetWidth(),refLink.GetHeight(),null);
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.PLAIN, 48));
        if(refLink.GetDatabase().getDifficulty()==1)
        {
            g.drawString("Easy",670,465);
        }
        else if(refLink.GetDatabase().getDifficulty()==2)
        {
            g.drawString("Medium",670,465);
        }
        else if(refLink.GetDatabase().getDifficulty()==3)
        {
            g.drawString("Hard",670,465);
        }
        g.drawString(Integer.toString(refLink.GetDatabase().getMenuVolume()),740,545);
        g.drawString(Integer.toString(refLink.GetDatabase().getGameVolume()),720,625);
        if(refLink.GetDatabase().getAR()==0)
            g.drawString("False",640,710);
        else
        {
            g.drawString("True",640,710);
        }
    }
}
