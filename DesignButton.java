package login;

import java.awt.Color;
import javax.swing.JButton;

class DesignButton extends JButton {
    private static final long serialVersionUID = 1965063150601339314L;
    public DesignButton(String text) 
    {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false); // 这一句非常重要, 否则父类还会绘制按钮的区域.  
        setFocusable(false);
        setBorderPainted(true);
        setForeground(new Color(0, 100, 255));
    }
}