package login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteAcc extends JPanel implements ActionListener
{	
	JPanel panel2,panel3;
	JLabel deAcc;
	JTextField deText;
	JButton deBu,debuall,deinput;
	DataBaseconnection dbc=new DataBaseconnection();
	ResultSet res;
	public DeleteAcc()
	{
		//panel1= new JPanel();
		panel2= new JPanel();
		panel3= new JPanel();
		//panel1.setPreferredSize(new Dimension(500,200));
		panel2.setPreferredSize(new Dimension(300,100));
		panel3.setPreferredSize(new Dimension(300,100));
		
		//label=new JLabel(new ImageIcon("E:/images/qq1.gif"));
		deAcc=new JLabel("请输入您要删除的账号名 ：",JLabel.RIGHT);
		deText=new JTextField(10);
		deBu=new DesignButton("确定");
		debuall=new DesignButton("删除所有");
		deinput=new DesignButton("删除输入历史");
		//panel1.add(label);
		panel2.add(deAcc);panel2.add(deText);panel2.add(deBu);
		panel3.add(debuall);
		//注册监听器
		deBu.addActionListener(this);
		debuall.addActionListener(this);
		//add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.CENTER);
		add(panel3,BorderLayout.SOUTH);
		setVisible(true);
		setSize(100,100);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand()=="确定")
		{
			//输入不能为空，注意==和equals()的区别：equals比较的是对象的值，==用来普安短是否为同一对象
			if (deText.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "!!!输入不能为空!!!");
			}
			else{
			res=dbc.executeQuery("select * from stuacc where account='"+deText.getText()+"'");
			try 
			{
				if (res.next())
				{
					dbc.executeUpdate("delete from stuacc where account='"+deText.getText()+"'");
					JOptionPane.showMessageDialog(null,"删除成功","Successful",JOptionPane.OK_OPTION);
					deText.setText("");
				}
				
				else 
				{
					JOptionPane.showMessageDialog(null,"Sorry!暂无该账号的信息，请确认好后重新输入","Wrong Usernumber",JOptionPane.ERROR_MESSAGE);
				}
				}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			}
		}
		
		else if (e.getSource()==debuall)
		{
			dbc.executeUpdate("delete from stuacc");
			JOptionPane.showMessageDialog(null, "Deleted All");
		}
	}
}
