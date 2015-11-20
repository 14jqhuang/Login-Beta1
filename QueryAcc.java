package login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QueryAcc extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int clicktimes=0;
	int row = 0;
	int increase = 0;
	//键值对
	HashSet<String> set;//数字集合
	DataBaseconnection dbc=new DataBaseconnection();
	ResultSet res,res1,res2,res3;
	DefaultTableModel mo1=new DefaultTableModel(new String[]{"账号"},0);
	DefaultTableModel mo2=new DefaultTableModel(new String[]{"顺序","账号"},0);
	JLabel Qlabel,Qlabel1,Qlabel2;
	JButton order;
	JTable Qt2;
	JScrollPane jsp2;
	//设置内容居中显示
	DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
	//显示已选的账号排序
	
	public QueryAcc()
	{
		tcr.setHorizontalAlignment(JLabel.CENTER);//数据在表格居中
		Qlabel=new JLabel("亲 ! 请在表格中的顺序列修改(自动切换)账号顺序 :",JLabel.CENTER);
		Qlabel1=new JLabel("",JLabel.CENTER);
		Qlabel2=new JLabel("",JLabel.CENTER);
		Qlabel.setForeground(Color.MAGENTA);
		Qlabel.setFont(new Font("宋体",Font.BOLD, 20));
		Qlabel1.setFont(new Font("宋体", Font.BOLD, 18));
		Qlabel2.setFont(new Font("宋体", Font.BOLD, 20));
		Qlabel1.setForeground(Color.RED);
		Qlabel2.setForeground(Color.magenta);
		order = new DesignButton("排序");
		Qt2=new JTable(mo2);
		Qt2.setDefaultRenderer(Object.class,tcr);
		jsp2=new JScrollPane(Qt2);
		//设置组件大小
		Qlabel.setPreferredSize(new Dimension(500, 30));
		jsp2.setPreferredSize(new Dimension(250, 200));
		Qlabel1.setPreferredSize(new Dimension(500, 20));
		Qlabel2.setPreferredSize(new Dimension(400, 20));
		order.setPreferredSize(new Dimension(100, 30));
		//注册监听器
		order.addActionListener(this);
		add(Qlabel);add(jsp2);add(order);add(Qlabel1);add(Qlabel2);
		setLayout(new FlowLayout());
		setVisible(true);
		//Table可编辑
		Qt2.setEnabled(true);
		
		//显示已有切换账号顺序
		try 
		{
			res1=dbc.executeQuery("select * from stuacc");
			while (res1.next())
			{
				increase++;
				Vector currow=new Vector();
				//显示一行数据
				currow.addElement(increase);
				currow.addElement(res1.getString(1));
				mo2.addRow(currow);
			}
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==order)
		{
			//初始化set,用于判断是否有重复顺序
			set = new HashSet<String>();
			int row = Qt2.getRowCount();//获取行数
			//添加元素
			for (int i=0;i<row;i++)
			{
				set.add(Qt2.getValueAt(i, 0).toString());
			}
			//判断元素个数是否等于行数
			if (set.size()==row&&!set.contains(""))
			{
				//将数据库的id从10001开始重置
				res2 = dbc.executeQuery("select * from stuacc");
				try {
					int num = 10000;
					int incre = 0;
					while (res2.next())
					{
						num++;incre++;
						dbc.executeUpdate("update stuacc set id='"+num+"' where id ='"+incre+"'");
					}
					//用修改后的数据更新数据库
					for (int i=0;i<row;i++)
					{
						dbc.executeUpdate("update stuacc set id ='"+Qt2.getValueAt(i,0)+"' where account='"+Qt2.getValueAt(i,1)+"'");
					}
					//将排序后的账号更新到表格
					mo2.setRowCount(0);
					res3 = dbc.executeQuery("select * from stuacc");
					while (res3.next())
					{
						Vector currow=new Vector();
						//显示一行数据
						currow.addElement(res3.getString(3));
						currow.addElement(res3.getString(1));
						mo2.addRow(currow);
					}
					Qlabel2.setText("^_^!!!排序成功!!!^_^");
					Qlabel1.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			//顺序列不允许有空白
			else if (set.contains(""))
			{
				Qlabel1.setText("^_^亲，输入序号不能有空，请检查一下吧^_^");Qlabel2.setText("");
			}
			//不允许序号重复
			else{Qlabel1.setText("^_^亲，您输入的序号有重复哦，请检查修改一下吧^_^");Qlabel2.setText("");}		
		}
	}
}
