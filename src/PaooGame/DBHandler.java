package PaooGame;

import java.sql.*;

public class DBHandler {
    Connection c;
    Statement stmt;
    ResultSet rs;
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

    public void updateDifficulty(int difficulty) throws SQLException {
        String instruction="UPDATE SETTINGS set DIFFICULTY ="+difficulty+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateMenuMusicVolume(int volume) throws  SQLException {
        String instruction="UPDATE SETTINGS set MENU_MUSIC_VOLUME ="+volume+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateGameMusicVolume(int volume) throws  SQLException {
        String instruction="UPDATE SETTINGS set GAME_MUSIC_VOLUME ="+volume+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateAutoRetry(int AR) throws  SQLException {
        String instruction="UPDATE SETTINGS set AUTO_RETRY ="+AR+" WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public int getDifficulty() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        rs.getInt("DIFFICULTY");
        return rs.getInt("DIFFICULTY");
    }

    public int getMenuVolume() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("MENU_MUSIC_VOLUME");
    }

    public int getGameVolume() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("GAME_MUSIC_VOLUME");
    }

    public int getAR() throws SQLException {
        rs=stmt.executeQuery("SELECT * FROM SETTINGS;");
        return rs.getInt("AUTO_RETRY");
    }

    public void saveSettings(float heroX, float heroY, int heroLife, int heroScore, int lev1, int lev2, int start, float enemy1X, float enemy1Y, float enemy2X, float enemy2Y) throws SQLException {
        String instruction = "INSERT INTO SAVEGAMES (HERO_X,HERO_Y,HERO_LIFE,HERO_SCORE,IS_IN_LEVEL1,IS_IN_LEVEL2,IS_IN_START,ENEMY1_X,ENEMY1_Y,ENEMY2_X,ENEMY2_Y)" +
                " VALUES ("+heroX+", "+heroY+", "+heroLife+", "+heroScore+", "+lev1+", "+lev2+", "+start+", "+enemy1X+", "+enemy1Y+", "+enemy2X+", "+enemy2Y+");";
        stmt.executeUpdate(instruction);
    }
    public float getHeroX() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("HERO_X");
        }
        return x;
    }
    public float getHeroY() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("HERO_Y");
        }
        return y;
    }
    public float getEnemy1X() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("ENEMY1_X");
        }
        return x;
    }
    public float getEnemy1Y() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("ENEMY1_Y");
        }
        return y;
    }
    public float getEnemy2X() throws SQLException {
        float x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getFloat("ENEMY2_X");
        }
        return x;
    }
    public float getEnemy2Y() throws SQLException {
        float y = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            y=rs.getFloat("ENEMY2_Y");
        }
        return y;
    }

    public int getHeroLife() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("HERO_LIFE");
        }
        return x;
    }
    public int getHeroScore() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("HERO_SCORE");
        }
        return x;
    }

    public int getIsInLvl1() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_LEVEL1");
        }
        return x;
    }

    public int getIsInLvl2() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_LEVEL2");
        }
        return x;
    }

    public int getIsInStart() throws SQLException {
        int x = 0;
        rs=stmt.executeQuery("SELECT * FROM SAVEGAMES;");
        while(rs.next())
        {
            x=rs.getInt("IS_IN_START");
        }
        return x;
    }




}
