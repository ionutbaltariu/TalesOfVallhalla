package PaooGame;

/*! \class Main
    \brief Clasa main care trebuie sa fie continuta de orice proiect.
           De aici se porneste efectiv jocul.
 */
public class Main {
    public static void main(String[] args) {
        Game paooGame = new Game("Tales of Valhalla", 1280, 1024);
        paooGame.StartGame();
    }
}
