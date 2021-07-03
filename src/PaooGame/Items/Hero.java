package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character {
    public static int DEFAULT_NR_OF_HITS;
    private static Hero instance = null;
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private int score;
    private int hitNumber;

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    private Hero(RefLinks refLink, float x, float y) {
        ///Apel al constructorului clasei de baza
        super(refLink, x, y, Character.DEFAULT_HERO_WIDTH, Character.DEFAULT_HERO_HEIGHT);

        score = 0;
        ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft;
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 30;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 0;
        attackBounds.y = 0;
        attackBounds.width = 53;
        attackBounds.height = 58;
        try {
            hitNumber = 15 / refLink.GetDatabase().getDifficulty();
            DEFAULT_NR_OF_HITS = 15 / refLink.GetDatabase().getDifficulty();
        } catch (java.sql.SQLException e) {
            System.err.println("Eroare la baza de date. (getDifficulty)");
        }
    }

    public static Hero getInstance(RefLinks refLink, float x, float y) {
        if (instance == null) {
            instance = new Hero(refLink, x, y);
        }
        return instance;
    }


    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update() {
        System.out.println((int) (this.GetX() / Tile.TILE_WIDTH + 1) + ", " + (int) (this.GetY() / Tile.TILE_HEIGHT + 1));
        if (!isDead()) { // cat timp nu e mort eroul, se va updata pozitia lui pe harta.
            ///Verifica daca a fost apasata o tasta
            GetInput();
            ///Actualizeaza pozitia
            Move();
            ///Actualizeaza imaginea
            if (refLink.GetKeyManager().left) {
                image = Assets.heroLeft;
            }
            if (refLink.GetKeyManager().right) {
                image = Assets.heroRight;
            }
            if (refLink.GetKeyManager().up) {
                image = Assets.heroUp;
            }
            if (refLink.GetKeyManager().down) {
                image = Assets.heroDown;
            }
        }
//        System.out.println(this.GetX()+","+this.GetY());
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput() {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        ///Verificare apasare tasta "sus"
        if (refLink.GetKeyManager().up) {
            yMove = -speed;
        }
        ///Verificare apasare tasta "jos"
        if (refLink.GetKeyManager().down) {
            yMove = speed;
        }
        ///Verificare apasare tasta "left"
        if (refLink.GetKeyManager().left) {
            xMove = -speed;
        }
        ///Verificare apasare tasta "dreapta"
        if (refLink.GetKeyManager().right) {
            xMove = speed;
        }
    }


    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {
        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
        //g.setColor(Color.red);
        //g.fillRect((int)(x + attackBounds.x), (int)(y + attackBounds.y), attackBounds.width, attackBounds.height);
        if (!this.isDead()) { // cat timp nu este mort eroul, va fi desenat.
            g.drawImage(image, (int) x, (int) y, width, height, null);
            g.drawImage(Assets.hpBackground, (int) x, (int) y, attackBounds.width, attackBounds.y + 8, null);
            g.drawImage(Assets.hpForeground, (int) x, (int) y, (attackBounds.width * actualLife * 10) / (life * 10), attackBounds.y + 8, null);
            g.drawImage(Assets.hpBorder, (int) x, (int) y, attackBounds.width, attackBounds.y + 8, null);
        }
    }

    /*! \fn public boolean isDead()
        \brief Functie de tip boolean care face posibila urmarirea starii eroului ( mort/ in viata)
               Returneaza true ( e mort ) daca viata in timp real a acestuia a scazut sub 0.
     */
    public boolean isDead() {
        return actualLife <= 0;
    }

    /*! \fn public int GetScore()
        \brief Functie care returneaza scorul obtinut pana in momentul de fata de catre player.
     */
    public int GetScore() {
        return this.score;
    }

    /*! \fn public void SetScore(int score)
        \brief Functie care modifica scorul obtinut pana in momentul de fata de catre player.
               Utila cand acesta a savarasit o actiune demna de o recompensa numerica.
     */
    public void SetScore(int score) {
        this.score = score;
    }

    /*! \fn public int getNrOfHits()
        \brief Getter pentru numarul de hit-uri pe care le are eroul.
     */
    public int getNrOfHits() {
        return this.hitNumber;
    }

    /*! \fn public void setNrOfHits()
        \brief Setter pentru numarul de hit-uri pe care le are eroul.
     */
    public void setNrOfHits(int nrOfHits) {
        this.hitNumber = nrOfHits;
    }
}
