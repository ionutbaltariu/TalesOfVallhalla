package PaooGame;

import java.sql.*;

/*! \class DBHandler
    \brief Clasa care ne permite crearea obiectelor ce faciliteaza conectarea la o baza de date SQLite
           Metodele acesteia se rezuma doar la citit si scris din/in baza de date "settings.db".
 */
public class DBHandler {
    Connection c;
    Statement stmt;
    ResultSet rs;

    /*! \fn public DBHandler()
        \brief Constructorul de initializare al clasei DBHandler in care se indica driverul si baza de date la care sa ne conectam.
     */
    public DBHandler()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:settings.db");
            stmt = c.createStatement();
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    /*! \fn public void updateDifficulty(int difficulty) throws SQLException
        \brief Functie ce scrie in baza de date dificultatea aleasa de utilizator in meniul "Settings".

        \param difficulty Este un int care ia valori de la 1 la 3 si reprezinta dificultatea jocului. Folosit pentru a "scala" viteza inamicilor/etc.
     */
    public void updateDifficulty(int difficulty) throws SQLException {
        String instruction="UPDATE SETTINGS set DIFFICULTY ="+difficulty+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    /*! \fn public void updateMenuMusicVolume(int volume) throws  SQLException
        \brief Functie ce scrie in baza de date volumul muzicii din MenuState pe care-l doreste utilizatorul(Selectand din setari).

        \param volume este intregul care reprezinta procentajul la care utilizatorul doreste sa se auda muzica.
     */
    public void updateMenuMusicVolume(int volume) throws  SQLException {
        String instruction="UPDATE SETTINGS set MENU_MUSIC_VOLUME ="+volume+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    /*! \fn public void updateGameMusicVolume(int volume) throws  SQLException
        \brief Functie ce scrie in baza de date volumul muzicii din PlayState pe care-l doreste utilizatorul(Selectand din setari).

        \param volume este intregul care reprezinta procentajul la care utilizatorul doreste sa se auda muzica.
     */
    public void updateGameMusicVolume(int volume) throws  SQLException {
        String instruction="UPDATE SETTINGS set GAME_MUSIC_VOLUME ="+volume+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    /*! \fn public void updateAutoRetry(int AR) throws  SQLException
        \brief Functie ce scrie in baza de date flag-ul folosit pentru a determina daca utilizatorul doreste sa reincerce automat jocul atunci cand moare.

        \param AR un flag, daca valoarea este 1 (sqlite neavand boolean), autoretry la moarte va fi pornit.
     */
    public void updateAutoRetry(int AR) throws  SQLException {
        String instruction="UPDATE SETTINGS set AUTO_RETRY ="+AR+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    /*! \fn public int getDifficulty() throws SQLException
        \brief Functie de extragere a dificultatii din baza de date pentru ne folosi de ea in joc.
     */
    public int getDifficulty() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        rs.getInt("DIFFICULTY");
        return rs.getInt("DIFFICULTY");
    }

    /*! \fn public int getMenuVolume() throws SQLException
        \brief Functie de extragere a volumului din meniu din baza de date pentru a-l modifica in timp real.
     */
    public int getMenuVolume() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("MENU_MUSIC_VOLUME");
    }

    /*! \fn public int getGameVolume() throws SQLException
        \brief Functie de extragere a volumului din joc din baza de date pentru a-l modifica in timp real.
     */
    public int getGameVolume() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("GAME_MUSIC_VOLUME");
    }

    /*! \fn public int getAR() throws SQLException
       \brief Functie de extragere a flagului de autoretry din baza de date pentru a-l modifica in timp real.
    */
    public int getAR() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("AUTO_RETRY");
    }

    /*! \fn public ublic void saveSettings(float heroX, float heroY, int heroLife, int heroScore, int lev1, int lev2, int start, float enemy1X, float enemy1Y, float enemy2X, float enemy2Y, int boss1Defeated, int boss2Defeated, long timer1, long timer2) throws SQLException
       \brief Functie de salvare a parametrilor vitali din joc pentru a-i incarca la apasarea butonului "Load".

       \param heroX pozitia eroului pe harta pe axa X.
       \param heroY pozitia eroului pe harta pe axa Y.
       \param heroLife viata in timp real a eroului.
       \param heroScore scorul in timp real al eroului.
       \param lev1 flag care ne spune daca suntem in harta corespunzatoare nivelului 1.
       \param lev2 flag care ne spune daca suntem in harta corespunzatoare nivelului 2.
       \param start flag care ne spune daca suntem in harta corespunzatoare intrarii in joc.
       \param enemy1X pozitia inamicului 1(dragon) pe harta pe axa X.
       \param enemy1Y pozitia inamicului 1(dragon) pe harta pe axa Y.
       \param enemy2X pozitia inamicului 2(moarte) pe harta pe axa X.
       \param enemy2Y pozitia inamicului 2(moarte) pe harta pe axa Y.
       \param boss1Defeated flag care ne spune daca inamicul 1 a fost invins.
       \param boss2Defeated flag care ne spune daca inamicul 2 a fost invins.
       \param timer1 reprezinta momentul de timp in care se afla eroul in momentul salvarii in harta corespunzatoare inamicului 1.
       \param timer2 reprezinta momentul de timp in care se afla eroul in momentul salvarii in harta corespunzatoare inamicului 2.
    */
    public void saveSettings(float heroX, float heroY, int heroLife, int heroScore, int lev1, int lev2, int start, float enemy1X, float enemy1Y,
                             float enemy2X, float enemy2Y, int boss1Defeated, int boss2Defeated, long timer1, long timer2) throws SQLException {
        String instruction = "INSERT INTO SAVEGAMES (HERO_X,HERO_Y,HERO_LIFE,HERO_SCORE,IS_IN_LEVEL1,IS_IN_LEVEL2,IS_IN_START,ENEMY1_X,ENEMY1_Y,ENEMY2_X,ENEMY2_Y, BOSS1_DEFEATED, BOSS2_DEFEATED, TIMER1, TIMER2)" +
                " VALUES ("+heroX+", "+heroY+", "+heroLife+", "+heroScore+", "+lev1+", "+lev2+", "+start+", "+enemy1X+", "+enemy1Y+", "+enemy2X+", "+enemy2Y+", "+boss1Defeated+", "+boss2Defeated+", "+timer1+", "+timer2+");";
        stmt.executeUpdate(instruction);
    }

    /*! \fn public float getHeroX() throws SQLException
        \brief Functie de extragere a coordonatei pe axa X a eroului din baza de date.
     */
    public float getHeroX() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("HERO_X");
        }
        return x;
    }

    /*! \fn public float getHeroY() throws SQLException
        \brief Functie de extragere a coordonatei pe axa Y a eroului din baza de date.
     */
    public float getHeroY() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("HERO_Y");
        }
        return y;
    }

    /*! \fn public float getEnemy1X() throws SQLException
        \brief Functie de extragere a coordonatei pe axa X a inamicului 1 din baza de date.
     */
    public float getEnemy1X() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("ENEMY1_X");
        }
        return x;
    }

    /*! \fn public float getEnemy1Y() throws SQLException
        \brief Functie de extragere a coordonatei pe axa Y a inamicului 1 din baza de date.
     */
    public float getEnemy1Y() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("ENEMY1_Y");
        }
        return y;
    }

    /*! \fn public float getEnemy2X() throws SQLException
        \brief Functie de extragere a coordonatei pe axa X a inamicului 2 din baza de date.
     */
    public float getEnemy2X() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("ENEMY2_X");
        }
        return x;
    }

    /*! \fn public float getEnemy2Y() throws SQLException
        \brief Functie de extragere a coordonatei pe axa Y a inamicului 2 din baza de date.
     */
    public float getEnemy2Y() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("ENEMY2_Y");
        }
        return y;
    }

    /*! \fn public int getHeroLife() throws SQLException
        \brief Functie de extragere a vietii eroului din baza de date.
     */
    public int getHeroLife() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("HERO_LIFE");
        }
        return x;
    }

    /*! \fn public int getHeroScore() throws SQLException
        \brief Functie de extragere a scorului eroului din baza de date.
     */
    public int getHeroScore() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("HERO_SCORE");
        }
        return x;
    }

    /*! \fn public int getIsInLvl1() throws SQLException
        \brief Functie de extragere a flagului pentru aflarea in harta nivelului 1 din baza de date.
     */
    public int getIsInLvl1() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_LEVEL1");
        }
        return x;
    }

    /*! \fn public int getIsInLvl2() throws SQLException
        \brief Functie de extragere a flagului pentru aflarea in harta nivelului 2 din baza de date.
     */
    public int getIsInLvl2() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_LEVEL2");
        }
        return x;
    }

    /*! \fn public int getIsInStart() throws SQLException
        \brief Functie de extragere a flagului pentru aflarea in harta de intrare din baza de date.
     */
    public int getIsInStart() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_START");
        }
        return x;
    }

    /*! \fn public int wasBoss1Defeated() throws SQLException
        \brief Functie de extragere a flagului care tine cont daca a murit inamicul 1 din baza de date.
     */
    public int wasBoss1Defeated() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("BOSS1_DEFEATED");
        }
        return x;
    }

    /*! \fn public int wasBoss2Defeated() throws SQLException
        \brief Functie de extragere a flagului care tine cont daca a murit inamicul 2 din baza de date.
     */
    public int wasBoss2Defeated() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("BOSS2_DEFEATED");
        }
        return x;
    }

    /*! \fn public long getIterator1() throws SQLException
        \brief Functie de extragere a momentului de timp in care se afla eroului in harta 1 din baza de date.
     */
    public long getIterator1() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("TIMER1");
        }
        return x;
    }

    /*! \fn public long getIterator2() throws SQLException
        \brief Functie de extragere a momentului de timp in care se afla eroului in harta 2 din baza de date.
     */
    public long getIterator2() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("TIMER2");
        }
        return x;
    }




}
