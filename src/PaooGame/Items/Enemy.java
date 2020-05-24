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

    BufferedImage image ;

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
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT,speed,life);
        image = Assets.enemyDown;
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
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea inamicului.
     */
    @Override
    public void Update() {

    }

    public void Update(Hero hero)
    {
        this.SetXMove(0);
        this.SetYMove(0);
        if(this.GetX()<hero.GetX())
        {
            this.SetXMove(this.GetXMove()+this.GetSpeed());
            if(this.GetY()<hero.GetY())
            {
                this.SetYMove(this.GetYMove()+this.GetSpeed());
                image=Assets.enemyDown;
                Move();
            }
            else
            {
                this.SetYMove(this.GetYMove() - this.GetSpeed());
                image = Assets.enemyUp;
                Move();
            }
        }
        else
        {
            this.SetXMove(this.GetXMove()-this.GetSpeed());
            if(this.GetY()<hero.GetY())
            {
                this.SetYMove(this.GetYMove()+this.GetSpeed());
                image=Assets.enemyDown;
                Move();
            }
            else
            {
                this.SetYMove(this.GetYMove() - this.GetSpeed());
                image = Assets.enemyUp;
                Move();
            }
        }


        if(refLink.GetKeyManager().space) {
            if (hero.GetX() + hero.attackBounds.x > x + attackBounds.x && hero.GetX() + attackBounds.x < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height)
                {
                    this.SetYMove(-50);
                    MoveY();
                    this.SetXMove(-50);
                    MoveX();
                }
            }
            if (hero.GetX() + hero.attackBounds.x > x + attackBounds.x && hero.GetX() + attackBounds.x < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height)
                {
                    this.SetYMove(50);
                    MoveY();
                    this.SetXMove(-50);
                    MoveX();
                }
            }
            if (hero.GetX() + hero.attackBounds.width > x + attackBounds.x && hero.GetX() + attackBounds.width < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.y > y + attackBounds.y && hero.GetY() + attackBounds.y < y + this.attackBounds.height)
                {
                    this.SetYMove(-50);
                    MoveY();
                    this.SetXMove(50);
                    MoveX();
                }
            }
            if (hero.GetX() + hero.attackBounds.width > x + attackBounds.x && hero.GetX() + attackBounds.width < x + this.attackBounds.width) {
                if (hero.GetY() + hero.attackBounds.height > y + attackBounds.y && hero.GetY() + attackBounds.height < y + this.attackBounds.height)
                {
                    this.SetYMove(50);
                    MoveY();
                    this.SetXMove(50);
                    MoveX();
                }
            }
        }
    }


    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza inamicului in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {

        //vedem box-ul pentru coliziuni
//      g.setColor(Color.blue);
//      g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
        //vedem box-ul pentru attack
        g.setColor(Color.black);
        g.fillRect((int)(x + attackBounds.x), (int)(y + attackBounds.y), attackBounds.width, attackBounds.height);
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

}
