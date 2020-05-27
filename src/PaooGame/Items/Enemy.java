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
public class Enemy  extends Character{

    BufferedImage image;
    boolean flag=false;
    long now;
    long then;
    int type;

    /*! \fn public Enemy(RefLinks refLink, float x, float y, int width, int height, float speed, int life, int type)
        \brief Constructor de initializare al clasei Enemy

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pe axa X a inamicului.
        \param y Pozitia de start pe axa Y a inamicului.
        \param width Latimea imaginii inamicului.
        \param height Inaltimea imaginii inamicului.
        \param speed Viteza inamicului.
        \param life Viata (lifepoints) inamicului.
        \param type Tipul eroului. Fie 1, fie 2. 1-Death, 2-Dragon.
     */
    public Enemy(RefLinks refLink, float x, float y, int width, int height, float speed, int life, int type) {
        super(refLink, x, y, width, height,speed,life);
        this.type=type;
        if(this.type==1) {
            image = Assets.enemy1Down;
            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
            normalBounds.x = 20;
            normalBounds.y = 16;
            normalBounds.width = 24;
            normalBounds.height = 40;

            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
            ///Setam deocamdata sa aiba un attack range de 10 in toate directiile.
            attackBounds.x = 0;
            attackBounds.y = 0;
            attackBounds.width = 64;
            attackBounds.height = 64;
        }
        if(this.type==2)
        {
            image = Assets.enemy2Down;
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
    }
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea inamicului.
     */
    @Override
    public void Update() {

    }

    public void Update(Hero hero)
    {
        followHero(hero);
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
        //vedem box-ul pentru attack
//        g.setColor(Color.black);
//        g.fillRect((int)(x + attackBounds.x), (int)(y + attackBounds.y), attackBounds.width, attackBounds.height);
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    /*! \fn private boolean secondElapsed()
        \brief Functie care contorizeaza trecerea unei secunde.
     */
    private boolean secondElapsed()
    {
        if(!flag)
        {
            then= System.nanoTime();
            flag=true;
        }
        now=System.nanoTime();
        if(now-then>=1000000000)
        {
            flag=false;
            return true;
        }
        return false;
    }

    /*! \fn private void giveDamage(Hero hero)
        \brief Functie care scade din viata eroului ca urmare a contactului intre acesta si monstru.

        \param hero Referinta catre eroul cu care monstrul face contact.
     */
    private void giveDamage(Hero hero)
    {
        if(hero.GetActualLife()>0)
            hero.SetActualLife(hero.GetActualLife()-1);
    }

    /*! \fn private void followHero(Hero hero)
        \brief Functie care permite urmarirea eroului pe harta. (Simpla urmare a coordonatelor acestuia.)

        \param hero Referinta catre eroul pe care il urmareste monstrul
     */
    private void followHero(Hero hero)
    {
        if(!hero.isDead()) { //DACA EROUL NU ESTE MORT
            this.SetXMove(0); //resetam xMove la fiecare update
            this.SetYMove(0); //resetam yMove la fiecare update
            if (this.GetX() < hero.GetX()) { // daca x-ul monstrului este mai mic decat cel al eroului
                this.SetXMove(this.GetXMove() + this.GetSpeed()); //setam miscarea monstrului pe axa x cu distanta egala cu vitezei acestuia
                if (this.GetY() < hero.GetY()) { // daca y-ul monstrului este mai mic decat y-ul eroului
                    this.SetYMove(this.GetYMove() + this.GetSpeed()); // setam miscarea monstrului pe axa y cu distanta egala vitezei acestuia
                    if(this.type==1)
                        image = Assets.enemy1Down; // de asemenea, monstrul se afla mai sus decat eroul in acest caz, dam update la imagine
                    if(this.type==2)
                        image= Assets.enemy2Down;
                    Move(); // abia aici se va misca efectiv monstrul
                }
                else {
                    this.SetYMove(this.GetYMove() - this.GetSpeed());
                    if(this.type==1)
                        image = Assets.enemy1Up;
                    if(this.type==2)
                        image = Assets.enemy2Up;
                    Move();
                }
            }
            else {
                this.SetXMove(this.GetXMove() - this.GetSpeed());
                if (this.GetY() < hero.GetY()) {
                    this.SetYMove(this.GetYMove() + this.GetSpeed());
                    if(this.type==1)
                        image = Assets.enemy1Down;
                    if(this.type==2)
                        image = Assets.enemy2Down;
                    Move();
                }
                else {
                    this.SetYMove(this.GetYMove() - this.GetSpeed());
                    if(this.type==1)
                        image = Assets.enemy1Up;
                    if(this.type==2)
                        image = Assets.enemy2Up;
                    Move();
                }
            }
        }
    }

    /*! \fn private void gettingKicked(Hero hero)
        \brief Functie care tine cont de evenimentul de atac al eroului.
               Daca utilizatorul apasa tasta "SPACE", monstrul este aruncat cu 50 de pixeli pe axa X si Y
               in aceeasi directie cu coltul ariei "attackBounds" a eroului.

        \param hero Referinta catre eroul de care poate fi lovit monstrul.
     */
    private void gettingKicked(Hero hero)
    {
        //cand spun colt ma refer specific la coltul din patratul attackBounds.
        if(!hero.isDead()) {
            if (hero.GetX() + hero.attackBounds.x > x + attackBounds.x && hero.GetX() + attackBounds.x < x + this.attackBounds.width) { // testam daca X-ul
                //coltului stang al eroului este intre limitarile de atac ale monstrului pe axa x
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height) { // testam daca Y-ul
                    //coltului stang al eroului este intre limitarile de atac ale monstrului pe axa y
                    if (secondElapsed()) // daca trece o secunda de suprapunere a coltului stang al eroului cu aria monstrului
                    {
                        giveDamage(hero); // eroul pierde viata
                    }
                    if (refLink.GetKeyManager().space) { // daca apasam space cand coltul stang se afla in aria in care monstrul poate fi atacat
                        //este aruncat cu
                        this.SetYMove(-50);
                        MoveY(); // 50 pixeli pe axa y
                        this.SetXMove(-50);
                        MoveX(); // 50 50 pixeli pe axa x
                        // in directia stanga sus
                        hero.SetScore(hero.GetScore()+10);
                    }
                }
                //aceeasi descriere pentru colt stanga jos
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height) {
                    if (secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (refLink.GetKeyManager().space) {
                        this.SetYMove(50);
                        MoveY();
                        this.SetXMove(-50);
                        MoveX();
                        hero.SetScore(hero.GetScore()+10);
                    }
                }
            }
            //colt dreapta sus
            if (hero.GetX() + hero.attackBounds.width > x + attackBounds.x && hero.GetX() + attackBounds.width < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height) {
                    if (secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (refLink.GetKeyManager().space) {
                        this.SetYMove(-50);
                        MoveY();
                        this.SetXMove(50);
                        MoveX();
                        hero.SetScore(hero.GetScore()+10);
                    }
                }
                //colt dreapta jos
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height) {
                    if (secondElapsed()) {
                        giveDamage(hero);
                    }
                    if (refLink.GetKeyManager().space) {
                        this.SetYMove(50);
                        MoveY();
                        this.SetXMove(50);
                        MoveX();
                        hero.SetScore(hero.GetScore()+10);
                    }
                }
            }
        }
    }




}
