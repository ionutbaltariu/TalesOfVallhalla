package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Year;

//import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;

/*! \class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
//    private BufferedImage hpBackground;
//    private BufferedImage hpForeground;
//    private BufferedImage hpBorder;

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_HERO_WIDTH, Character.DEFAULT_HERO_HEIGHT);
            ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft;
//        hpBackground = ImageLoader.LoadImage("/healthbarBackground.png");
//        hpForeground = ImageLoader.LoadImage("/healthbarForeground.png");
//        hpBorder     = ImageLoader.LoadImage("/healthbarBorder.png");
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
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
            ///Verifica daca a fost apasata o tasta
        GetInput();
            ///Actualizeaza pozitia
        Move();
            ///Actualizeaza imaginea
        if(refLink.GetKeyManager().left)
        {
            image = Assets.heroLeft;
        }
        if(refLink.GetKeyManager().right) {
            image = Assets.heroRight;
        }
        if(refLink.GetKeyManager().up) {
            image = Assets.heroUp;
        }
        if(refLink.GetKeyManager().down) {
            image = Assets.heroDown;
        }
        //System.out.println((GetX()+this.attackBounds.x) +", "+(GetX()+attackBounds.width)+", "+(GetY()+attackBounds.y)+", "+(GetY()+attackBounds.height));
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up)
        {
            yMove = -speed;
        }
            ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down)
        {
            yMove = speed;
        }
            ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left)
        {
            xMove = -speed;
        }
            ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right)
        {
            xMove = speed;
        }
    }


    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        
            ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
        g.setColor(Color.red);
        g.fillRect((int)(x + attackBounds.x), (int)(y + attackBounds.y), attackBounds.width, attackBounds.height);
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }
}
