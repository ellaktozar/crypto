package safe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class pin
{

	// private String pinFileText = "";
	int pinIn;
	int pinTrue;
	private boolean showAddBtn;
	String tmp_1;
	String inPin;
	String strPin = null;
	crypt cr = new crypt();

	public boolean createNewPin()
	{
		boolean ret = false;
		try
		{
			Class.forName("org.h2.Driver").newInstance();
			strPin = JOptionPane
					.showInputDialog(null, "ВВедите пин-код:", "Новый пароль",
							1).toString().trim();
			this.inPin = strPin;
			if (inPin.length() > 0)
			{
				Connection conn = DriverManager.getConnection("jdbc:h2:"
						+ System.getProperty("user.dir") + "/safe/safe", "sa",
						inPin);
				Statement st = null;
				st = conn.createStatement();
				st.execute("CREATE TABLE IF NOT EXISTS passwords (id INT(10) PRIMARY KEY AUTO_INCREMENT, res VARCHAR(255), pass VARCHAR(100))");
				st.close();
				conn.close();
				ret = true;
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return ret;
	}

	Integer checkQuery()
	{
		Integer i = null;
		try
		{
			Class.forName("org.h2.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:h2:" + System.getProperty("user.dir") + "/safe/safe",
					"sa", inPin);
			Statement st = null;
			st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT COUNT(*) FROM passwords");
			i = -1;
			if (rs.next())
			{
				i = rs.getInt(1);
			}
			st.close();
			conn.close();
		}
		catch (Exception exp)
		{
		}
		return i;
	}

	Boolean checkRow(String str1, String str2)
	{
		boolean a = false;
		try
		{
			Class.forName("org.h2.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:h2:" + System.getProperty("user.dir") + "/safe/safe",
					"sa", inPin);
			Statement st = null;
			st = conn.createStatement();

			st.execute("INSERT INTO passwords (res, pass) VALUES ('"
					+ cr.encrypt(str1) + "','" + cr.encrypt(str2) + "')");
			st.close();
			conn.close();
			a = true;
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return a;
	}

	Object[][] drawTbl()
	{
		Object[][] mas;
		try
		{
			this.showAddBtn = true;
			mas = new Object[checkQuery()][3];
			Class.forName("org.h2.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:h2:" + System.getProperty("user.dir") + "/safe/safe",
					"sa", inPin);
			Statement st = null;
			st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM passwords");

			int counter = 0;
			while (rs.next())
			{
				mas[counter][0] = rs.getString("id");
				mas[counter][1] = cr.decrypt(rs.getString("res"));
				mas[counter][2] = cr.decrypt(rs.getString("pass"));
				counter++;
			}
			st.close();
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			mas = new Object[][]
			{
			{ "Ошибка", "Ошибка", "Ошибка" }, };
			this.showAddBtn = false;
		}
		return mas;
	}

	public boolean checkshowAddBtn()
	{
		boolean a = false;

		if (this.showAddBtn)
		{
			a = true;
		}
		return a;
	}

	boolean DeleteRow(int[] id)
	{
		boolean result = false;
		if (id.length > 0)
		{
			String ids = "";

			for (int i = 0; i < id.length; i++)
			{
				ids += "" + id[i] + "";
				if ((i + 1) != id.length)
				{
					ids += ",";
				}
			}

			try
			{
				Class.forName("org.h2.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:h2:"
						+ System.getProperty("user.dir") + "/safe/safe", "sa",
						inPin);
				Statement st = null;
				st = conn.createStatement();
				st.execute("DELETE FROM passwords WHERE id IN (" + ids + ")");
				st.close();
				conn.close();
				result = true;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		return result;
	}
}
