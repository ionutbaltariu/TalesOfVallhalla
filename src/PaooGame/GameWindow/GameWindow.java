package PaooGame.GameWindow;

import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;

/*! \class GameWindow
    \brief Implementeaza notiunea de fereastra a jocului.

    Membrul WindowFrame este un obiect de tip JFrame care va avea utilitatea unei
    ferestre grafice si totodata si cea a unui container (toate elementele
    grafice vor fi continute de fereastra).
 */
public class GameWindow
{
    private JFrame  WindowFrame;       /*!< fereastra principala a jocului*/
    private String  WindowTitle;       /*!< titlul ferestrei*/
    private int     WindowWidth;       /*!< latimea ferestrei in pixeli*/
    private int     WindowHeight;      /*!< inaltimea ferestrei in pixeli*/

    private Canvas  canvas;         /*!< "panza/tablou" in care se poate desena*/

    /*! \fn GameWindow(String title, int width, int height)
            \brief Constructorul cu parametri al clasei GameWindow

            Retine proprietatile ferestrei proprietatile (titlu, latime, inaltime)
            in variabilele membre deoarece vor fi necesare pe parcursul jocului.
            Crearea obiectului va trebui urmata de crearea ferestrei propriuzise
            prin apelul metodei BuildGameWindow()

            \param title Titlul ferestrei.
            \param width Latimea ferestrei in pixeli.
            \param height Inaltimea ferestrei in pixeli.
         */
    public GameWindow(String title, int width, int height){
        WindowTitle    = title;    /*!< Retine titlul ferestrei.*/
        WindowWidth    = width;    /*!< Retine latimea ferestrei.*/
        WindowHeight   = height;   /*!< Retine inaltimea ferestrei.*/
        WindowFrame    = null;     /*!< Fereastra nu este construita.*/
    }

    /*! \fn private void BuildGameWindow()
        \brief Construieste/creaza fereastra si seteaza toate proprietatile
        necesare: dimensiuni, pozitionare in centrul ecranului, operatia de
        inchidere, invalideaza redimensionarea ferestrei, afiseaza fereastra.

     */
    public void BuildGameWindow()
    {
            /// Daca fereastra a mai fost construita intr-un apel anterior
            /// se renunta la apel
        if(WindowFrame != null)
        {
            return;
        }
            /// Aloca memorie pentru obiectul de tip fereastra si seteaza denumirea
            /// ce apare in bara de titlu
        WindowFrame = new JFrame(WindowTitle);
            /// Seteaza dimensiunile ferestrei in pixeli
        WindowFrame.setSize(WindowWidth, WindowHeight);
            /// Operatia de inchidere (fereastra sa poata fi inchisa atunci cand
            /// este apasat butonul x din dreapta sus al ferestrei). Totodata acest
            /// lucru garanteaza ca nu doar fereastra va fi inchisa ci intregul
            /// program
        WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            /// Avand in vedere ca dimensiunea ferestrei poate fi modificata
            /// si corespunzator continutul actualizat (aici ma refer la dalele
            /// randate) va recomand sa constrangeti deocamdata jucatorul
            /// sa se joace in fereastra stabilitata de voi. Puteti reveni asupra
            /// urmatorului apel ulterior.
        WindowFrame.setResizable(false);
            /// Recomand ca fereastra sa apara in centrul ecranului. Pentru orice
            /// alte pozitie se va apela "WindowFrame.setLocation(x, y)" etc.
        WindowFrame.setLocationRelativeTo(null);
            /// Implicit o fereastra cand este creata nu este vizibila motiv pentru
            /// care trebuie setata aceasta proprietate
        WindowFrame.setVisible(true);
            /// Creaza obiectul de tip canvas (panza) pe care se poate desena.
        canvas = new Canvas();
            /// In aceeasi maniera trebuiesc setate proprietatile pentru acest obiect
            /// canvas (panza): dimensiuni preferabile, minime, maxime etc.
            /// Urmotorul apel de functie seteaza dimensiunea "preferata"/implicita
            /// a obiectului de tip canvas.
            /// Functia primeste ca parametru un obiect de tip Dimension ca incapsuleaza
            /// doua proprietati: latime si inaltime. Cum acest obiect nu exista
            /// a fost creat unul si dat ca parametru.
        canvas.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
            /// Avand in vedere ca elementele unei ferestre pot fi scalate atunci cand
            /// fereastra este redimensionata
        canvas.setMaximumSize(new Dimension(WindowWidth, WindowHeight));
        canvas.setMinimumSize(new Dimension(WindowWidth, WindowHeight));
            /// Avand in vedere ca obiectul de tip canvas, proaspat creat, nu este automat
            /// adaugat in fereastra trebuie apelata metoda add a obiectul WindowFrame
        WindowFrame.add(canvas);
            /// Urmatorul apel de functie are ca scop eventuala redimensionare a ferestrei
            /// ca tot ce contine sa poate fi afisat complet
        WindowFrame.pack();
    }

    /*! \fn public int GetWindowWidth()
        \brief Returneaza latimea ferestrei.
     */
    public int GetWindowWidth()
    {
        return WindowWidth;
    }

    /*! \fn public int GetWindowWidth()
        \brief Returneaza inaltimea ferestrei.
     */
    public int GetWindowHeight()
    {
        return WindowHeight;
    }

    /*! \fn public int GetCanvas()
        \brief Returneaza referinta catre canvas-ul din fereastra pe care se poate desena.
     */
    public Canvas GetCanvas()
    {
        return canvas;
    }

    /*! \fn public int GetCanvas()
        \brief Returneaza referinta catre canvas-ul din fereastra pe care se poate desena.
     */
    public JFrame GetWindowFrame()
    {
        return WindowFrame;
    }
}
