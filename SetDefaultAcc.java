package login;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
//自动登陆模块
public class SetDefaultAcc implements Runnable
{
	//单独的线程
	DataBaseconnection dbc=new DataBaseconnection();
	ResultSet res,res1;
	String pass;
	boolean corr,yes;
	public SetDefaultAcc(String temp)
	{
		this.pass=temp;
	}
	public void run()
	{
		res=dbc.executeQuery("select * from stuacc where defaultacc='yes'");
		res1=dbc.executeQuery("select * from stuacc where account='"+pass+"'");
		try {
			yes=res.next();
			corr=res1.next();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try
		{
			if (!corr){JOptionPane.showMessageDialog(null, "Sorry!!!您的添加列表中没有此账号，请先添加再来吧^_^");}
			if (yes&&corr)
			{
				String acc=res.getString(1);
				dbc.executeUpdate("update stuacc set defaultacc='' where account='"+acc+"'");
				dbc.executeUpdate("update stuacc set defaultacc='yes' where account='"+pass+"'");
			}
			else 
			{
				dbc.executeUpdate("update stuacc set defaultacc='yes' where account='"+pass+"'");
			}
		}
		catch(Exception e){}
	}
}