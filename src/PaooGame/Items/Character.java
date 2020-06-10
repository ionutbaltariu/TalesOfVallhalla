package PaooGame.Items;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

/*! \class Character extends Item
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

    Notiunea este definita doar de viata, viteza de deplasare si distanta cu care trebuie sa se
    miste/deplaseze in urma calculelor.
 */
public abstract class Character extends Item
{
    public static final int DEFAULT_LIFE            = 10;   /*!< Valoarea implicita a vietii unui caracter.*/
    public static final float DEFAULT_SPEED         = 3.0f; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_HERO_WIDTH  = 48;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_HERO_HEIGHT = 48;   /*!< Inaltimea implicita a imaginii caracterului.*/

    protected int life;       /*!< Retine viata de inceput caracterului.*/
    protected int actualLife; /*!< Retine viata in timp real a caracterului.*/
    protected float speed;    /*!< Retine viteza de deplasare caracterului.*/
    protected float xMove;    /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;    /*!< Retine noua pozitie a caracterului pe axa Y.*/


    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pe axa X a caracterului.
        \param y Pozitia de start pe axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
     */
    public Character(RefLinks refLink, float x, float y, int width, int height)
    {
            ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height);
            //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        life    = DEFAULT_LIFE;
        actualLife=life;
        speed   = DEFAULT_SPEED;
        xMove   = 0;
        yMove   = 0;
    }

    public Character(RefLinks refLink, float x, float y, int width, int height, float speed, int life)
    {
        ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height);
        //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        this.life = life;
        actualLife=life;
        this.speed = speed;
        xMove   = 0;
        yMove   = 0;
    }


    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move()
    {
            ///Modifica pozitia caracterului pe axa X.
            ///Modifica pozitia caracterului pe axa Y.
        MoveX();
        MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX()
    {
        int tx;
            ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        if(xMove>0) // caracterul se misca la dreapta
        {
            tx=(int)(x+xMove+bounds.x+bounds.width) / Tile.TILE_WIDTH;
            if(!collision(tx,(int)(y + bounds.y) / Tile.TILE_HEIGHT)  && !collision(tx,(int)(y+bounds.y+bounds.height)/Tile.TILE_HEIGHT))
            {
                x+=xMove;
            }
        }
        else // caracterul se misca la stanga
        {
            tx=(int)(x+xMove+bounds.x) / Tile.TILE_WIDTH;
            if(!collision(tx,(int)(y + bounds.y) / Tile.TILE_HEIGHT)  && !collision(tx,(int)(y+bounds.y+bounds.height)/Tile.TILE_HEIGHT))
            {
                x+=xMove;
            }
        }
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY()
    {
        int ty;
            ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        if(yMove<0)
        {
            ty=(int)(y+yMove+bounds.y)/Tile.TILE_HEIGHT;
            if(!collision((int)(x+bounds.x)/Tile.TILE_WIDTH,ty) && !collision((int)(x+bounds.x+bounds.width)/Tile.TILE_WIDTH,ty))
            {
                y+=yMove;
            }
        }
        else if(yMove>=0)
        {
            ty=(int)(y+yMove+bounds.y+bounds.height)/Tile.TILE_HEIGHT;
            if(!collision((int)(x+bounds.x)/Tile.TILE_WIDTH,ty) && !collision((int)(x+bounds.x+bounds.width)/Tile.TILE_WIDTH,ty))
            {
                y+=yMove;
            }

        }
    }

    /*! \fn public boolean collision(int x, int y)
        \brief returneaza daca tile-ul de la coordonatele (x,y) este solid pentru a putea implementa coliziunile.
     */
    protected boolean collision(int x, int y)
    {
        return refLink.GetMap().GetTile(x,y).IsSolid();
    }


    /*! \fn public int GetLife()
        \brief Returneaza viata caracterului.
     */
    public int GetLife()
    {
        return life;
    }

    /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */
    public float GetSpeed()
    {
        return speed;
    }

    /*! \fn public void SetLife(int life)
        \brief Seteaza viata caracterului.
     */
    public void SetLife(int life)
    {
        this.life = life;
    }

    /*! \fn public void SetSpeed(float speed)
        \brief
     */
    public void SetSpeed(float speed) {
        this.speed = speed;
    }

    /*! \fn public float GetXMove()
        \brief Returneaza distanta in pixeli pe axa X cu care este actualizata pozitia caracterului.
     */
    public float GetXMove()
    {
        return xMove;
    }

    /*! \fn public float GetYMove()
        \brief Returneaza distanta in pixeli pe axa Y cu care este actualizata pozitia caracterului.
     */
    public float GetYMove()
    {
        return yMove;
    }

    /*! \fn public void SetXMove(float xMove)
        \brief Seteaza distanta in pixeli pe axa X cu care va fi actualizata pozitia caracterului.
     */
    public void SetXMove(float xMove)
    {
        this.xMove = xMove;
    }

    /*! \fn public void SetYMove(float yMove)
        \brief Seteaza distanta in pixeli pe axa Y cu care va fi actualizata pozitia caracterului.
     */
    public void SetYMove(float yMove)
    {
        this.yMove = yMove;
    }

    /*! \fn public void SetActualLife(int actualLife)
        \brief Seteaza viata in timp real a eroului.
     */
    public void SetActualLife(int actualLife) { this.actualLife = actualLife; }

    /*! \fn public int GetActualLife()
        \brief Returneaza viata in timp real a eroului.
     */
    public int GetActualLife() { return this.actualLife; }


}

