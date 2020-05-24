package PaooGame;

import PaooGame.GameWindow.GameWindow;


/*! \class Main
    \brief Clasa main care trebuie sa fie continuta de orice proiect.
           De aici se porneste efectiv jocul.
 */
public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = new Game("PaooGame", 1280, 1024);
        paooGame.StartGame();
    }
}
