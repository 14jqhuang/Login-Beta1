package login;

 import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class WinTest4 {

	/**
	 * @param args
	 */
	public WinTest4()
	{
		JPanel top = new JPanel(){  
			private static final long serialVersionUID = 1L;  

			protected void paintComponent(Graphics g) {  
				Graphics2D g2 = (Graphics2D) g;  
				super.paintComponent(g);  
				// 绘制渐变  
				g2.setPaint(new GradientPaint(0, 0, new Color(116, 149, 226), getWidth(),  getHeight(), new Color(199, 212, 255)));  
				g2.fillRect(0, 0, getWidth(), getHeight());  
			}  
		}; 
	}
}
