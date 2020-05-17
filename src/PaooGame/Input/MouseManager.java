package PaooGame.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter {
    public boolean leftPressed,rightPressed;
    private int mouseX,mouseY;

    public MouseManager()
    {
        // nu avem ce initializa in constructor.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //detectarea click-ului apasat (left/right)
        if(e.getButton()==MouseEvent.BUTTON1) //left click = BUTTON1
        {
            System.out.println(e.getX()+", "+e.getY());
            leftPressed = true;
        }
        if(e.getButton()==MouseEvent.BUTTON3) //right click = BUTTON3
            rightPressed=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1)
            leftPressed=false;
        if(e.getButton()==MouseEvent.BUTTON3)
            rightPressed=false;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
    }

    public boolean leftClickPressed(){
        return leftPressed;
    }

    public boolean rightClickPressed(){
        return rightPressed;
    }
    public int getMouseX()
    {
        return mouseX;
    }
    public int getMouseY()
    {
        return mouseY;
    }
}
