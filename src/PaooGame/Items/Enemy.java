package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Enemy extends Character
    \brief Implementeaza notiunea de inamic.

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Enemy extends Character {

    final float initialSpeed = this.GetSpeed();
    BufferedImage image;

    /*! \fn public Enemy(RefLinks refLink, float x, float y, int width, int height, float speed, int life)
        \brief Constructor de initializare al clasei Enemy

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pe axa X a inamicului.
        \param y Pozitia de start pe axa Y a inamicului.
        \param width Latimea imaginii inamicului.
        \param height Inaltimea imaginii inamicului.
        \param speed Viteza inamicului.
        \param life Viata (lifepoints) inamicului.
     */
    public Enemy(RefLinks refLink, float x, float y, int width, int height, float speed, int life) {
        super(refLink, x, y, width, height, speed, life);
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea inamicului.
     */
    @Override
    public void Update() {

    }

    /*! \fn public void Update(Hero hero)
        \brief Actualizeaza pozitia si imaginea inamicului in functie de pozitia eroului.
     */
    public void Update(Hero hero) {
        gettingKicked(hero);
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza inamicului in noua pozitie.

        \param g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {

        //vedem box-ul pentru coliziuni
//        g.setColor(Color.blue);
//        g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
//        vedem box-ul pentru attack
//        g.setColor(Color.black);
//        g.fillRect((int)(x + attackBounds.x), (int)(y + attackBounds.y), attackBounds.width, attackBounds.height);
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    /*! \fn private void giveDamage(Hero hero)
        \brief Functie care scade din viata eroului ca urmare a contactului intre acesta si monstru.

        \param hero Referinta catre eroul cu care monstrul face contact.
     */
    private void giveDamage(Hero hero) {
        if (hero.GetActualLife() > 0)
            hero.SetActualLife(hero.GetActualLife() - 1);
    }

    /*! \fn protected void followHero(Hero hero, BufferedImage Up, BufferedImage Down)
        \brief Functie care permite urmarirea eroului pe harta. (Simpla urmare a coordonatelor acestuia.)

        \param hero Referinta catre eroul pe care il urmareste monstrul
        \param Up Imaginea care portretizeaza inamicul cand merge inspre Nord
        \param Down [..] cand merge inspre Sud
     */
    protected void followHero(Hero hero, BufferedImage Up, BufferedImage Down) {
        float distance; // lungimea segmentului cu capetele player si monstru
        float modifier; // o valoarea calculata in functie de distanta dintre erou si monstru pentru a mari viteza acestuia daca se afla la
        // o distanta mare
        if (!hero.isDead()) { //DACA EROUL NU ESTE MORT
            this.SetXMove(0); //resetam xMove la fiecare update
            this.SetYMove(0); //resetam yMove la fiecare update
            distance = (float) (Math.sqrt(Math.pow(this.GetX() - hero.GetX(), 2) + Math.pow(this.GetY() - hero.GetY(), 2))); // calcul distanta intre 2 puncte
            modifier = initialSpeed + distance / 400f; // impartim la 400 pentru a obtine un modificator rezonabil
            this.SetSpeed(modifier); // viteza va deveni valoarea modificatorului
            // efectul este: viteza mare cand inamicul este la departare si viteza mica atunci cand este aproape de erou
            if (this.GetX() < hero.GetX()) { // daca x-ul monstrului este mai mic decat cel al eroului
                this.SetXMove(this.GetXMove() + this.GetSpeed()); //setam miscarea monstrului pe axa x cu distanta egala cu vitezei acestuia
            } else {
                this.SetXMove(this.GetXMove() - this.GetSpeed());
            }
            if (this.GetY() < hero.GetY()) { // daca y-ul monstrului este mai mic decat y-ul eroului
                this.SetYMove(this.GetYMove() + this.GetSpeed()); // setam miscarea monstrului pe axa y cu distanta egala vitezei acestuia
                image = Down;
            } else {
                this.SetYMove(this.GetYMove() - this.GetSpeed());
                image = Up;
            }
            Move(); // abia aici se va misca efectiv monstrul
        }
    }

    /*! \fn private void gettingKicked(Hero hero)
        \brief Functie care tine cont de evenimentul de atac al eroului.
               Daca utilizatorul apasa tasta "SPACE", monstrul este aruncat cu 50 de pixeli pe axa X si Y
               in aceeasi directie cu coltul ariei "attackBounds" a eroului.

        \param hero Referinta catre eroul de care poate fi lovit monstrul.
     */
    private void gettingKicked(Hero hero) {
        //cand spun colt ma refer specific la coltul din patratul attackBounds.
        if (!hero.isDead()) {
            if (hero.GetX() + hero.attackBounds.x > x + attackBounds.x && hero.GetX() + attackBounds.x < x + this.attackBounds.width) { // testam daca X-ul
                //coltului stang al eroului este intre limitarile de atac ale monstrului pe axa x
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height) { // testam daca Y-ul
                    //coltului stang al eroului este intre limitarile de atac ale monstrului pe axa y
                    if (Assets.secondElapsed()) // daca trece o secunda de suprapunere a coltului stang al eroului cu aria monstrului
                    {
                        giveDamage(hero); // eroul pierde viata
                    }
                    if (hero.getNrOfHits() > 0) {
                        if (refLink.GetKeyManager().space) { // daca apasam space cand coltul stang se afla in aria in care monstrul poate fi atacat
                            //este aruncat cu
                            this.SetYMove(-50);
                            MoveY(); // 50 pixeli pe axa y
                            this.SetXMove(-50);
                            MoveX(); // 50 50 pixeli pe axa x
                            // in directia stanga sus
                            hero.setNrOfHits(hero.getNrOfHits() - 1);
                        }
                    }
                }
                //aceeasi descriere pentru colt stanga jos
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height) {
                    if (Assets.secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (hero.getNrOfHits() > 0) {
                        if (refLink.GetKeyManager().space) {
                            this.SetYMove(50);
                            MoveY();
                            this.SetXMove(-50);
                            MoveX();
                            hero.setNrOfHits(hero.getNrOfHits() - 1);
                        }
                    }
                }
            }
            //colt dreapta sus
            if (hero.GetX() + hero.attackBounds.width > x + attackBounds.x && hero.GetX() + attackBounds.width < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height) {
                    if (Assets.secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (hero.getNrOfHits() > 0) {
                        if (refLink.GetKeyManager().space) {
                            this.SetYMove(-50);
                            MoveY();
                            this.SetXMove(50);
                            MoveX();
                            hero.setNrOfHits(hero.getNrOfHits() - 1);
                        }
                    }
                }
                //colt dreapta jos
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height) {
                    if (Assets.secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (hero.getNrOfHits() > 0) {
                        if (refLink.GetKeyManager().space) {
                            this.SetYMove(50);
                            MoveY();
                            this.SetXMove(50);
                            MoveX();
                            hero.setNrOfHits(hero.getNrOfHits() - 1);
                        }
                    }
                }
            }
        }
    }


}
