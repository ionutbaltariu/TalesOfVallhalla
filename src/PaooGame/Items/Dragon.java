package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;


/*! \class Dragon extends Enemy
    \brief Implementeaza inamicul "specializat" ce portretizeaza un dragon. - Baldur
 */
public class Dragon extends Enemy {
    /*! \fn public Dragon(RefLinks refLink, float x, float y, int width, int height, float speed, int life)
        \brief Constructor de initializare al clasei Enemy

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pe axa X a inamicului.
        \param y Pozitia de start pe axa Y a inamicului.
        \param width Latimea imaginii inamicului.
        \param height Inaltimea imaginii inamicului.
        \param speed Viteza inamicului.
        \param life Viata (lifepoints) inamicului.
     */
    public Dragon(RefLinks refLink, float x, float y, int width, int height, float speed, int life) {
        super(refLink, x, y, width, height, speed, life);
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 24;
        normalBounds.y = 0;
        normalBounds.width = 48;
        normalBounds.height = 96;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        ///Setam deocamdata sa aiba un attack range de 10 in toate directiile.
        attackBounds.x = 0;
        attackBounds.y = 0;
        attackBounds.width = 96;
        attackBounds.height = 96;
    }

    /*! \fn public void Update(Hero hero)
    \brief Actualizeaza pozitia si imaginea inamicului in functie de pozitia eroului.
           Functia suprascrie cea din clasa parinte pentru a da niste imagini concrete inamicului.
 */
    @Override
    public void Update(Hero hero) {
        super.Update(hero);
        followHero(hero, Assets.enemy2Up, Assets.enemy2Down);
    }


}
