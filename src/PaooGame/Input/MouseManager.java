package PaooGame.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*! \class MouseManager extends MouseAdapter
    \brief Gestioneaza intrarea (input-ul) de la mouse.

    Clasa citeste daca au fost apasat un click si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent click-ului de interes. Daca flagul respectiv este true inseamna
    ca click-ul respectiv a fost apasat, iar daca este false, nu a fost apasat.
 */
public class MouseManager extends MouseAdapter {
    private boolean leftPressed;  /*!< Flag pentru contorizarea apasarii clickului stang.*/
    private boolean rightPressed; /*!< Flag pentru contorizarea apasarii clickului drept.*/
    private int mouseX;           /*!< Locatia pe axa X a mouse-ului.*/
    private int mouseY;           /*!< Locatia pe axa Y a mouse-ului.*/

    /*! \fn public MouseManager
        \brief constructor nefolosit in stagiul curent al proiectului.
     */
    public MouseManager() {
        // nu avem ce initializa in constructor.
    }

    /*! \fn public void mousePressed(MouseEvent e)
        \brief Functie ce va fi apelata atunci cand este apasat un click pe mouse. (Nu conteaza ce click)
               De aceea gestionam apasarea click-ului stang sau drept in interiorul functiei.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //detectarea click-ului apasat (left/right)
        if (e.getButton() == MouseEvent.BUTTON1) //left click = BUTTON1
        {
            leftPressed = true;
            System.out.println(e.getX() + ", " + e.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON3) //right click = BUTTON3
            rightPressed = true;
    }

    /*! \fn public void mouseReleased(MouseEvent e)
         \brief Functie ce va fi apelata atunci cand click-ul nu mai este apasat. (Nu conteaza ce click)
                De aceea gestionam oprirea apasarii click-ului stang sau drept in interiorul functiei.
      */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
    }

    /*! \fn public void mouseMoved(MouseEvent e)
         \brief Functie ce memoreza locatia efectiva a cursorului pe ecran.
      */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /*! \fn public boolean leftClickPressed()
             \brief Prin intermediul acestei functii putem afla in proiectul nostru daca este apasat click-ul stang.
                    Valoarea leftPressed este modificata corespunzator in mousePressed si mouseReleased.
                    Functie folositoare atunci cand dam click pe un anumit element si vrem sa ne asiguram ca am folosit
                    click-ul stanga.

             \param leftPressed este un boolean care contorizeaza daca este apasat sau nu click-ul stang.
          */
    public boolean leftClickPressed() {
        return leftPressed;
    }

    /*! \fn public boolean leftClickPressed()
             \brief Prin intermediul acestei functii putem afla in proiectul nostru daca este apasat click-ul drept.
                    Valoarea rightPressed este modificata corespunzator in mousePressed si mouseReleased.

                    \param rightPressed este un boolean care contorizeaza daca este apasat sau nu click-ul drept.
          */
    public boolean rightClickPressed() {
        return rightPressed;
    }

    /*! \fn public int getMouseX()
        \brief Returneaza pozitia pe axa X a cursorului.
               Poate fi folosit pt debugging/determinare experimentala de coordonate.
     */
    public int getMouseX() {
        return mouseX;
    }

    /*! \fn public int getMouseY()
        \brief Returneaza pozitia pe axa Y a cursorului.
               Poate fi folosit pt debugging/determinare experimentala de coordonate.
     */
    public int getMouseY() {
        return mouseY;
    }
}
