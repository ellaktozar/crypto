package safe;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class MyOptionPane
{
	JButton exitBtn = new JButton();
	JButton acceptBtn = new JButton();
	String[] labelText = new String[]
	{ "Слабый пароль", "Средний пароль", "Хороший пароль" };
	char[] symbol = new char[]
	{ '!', '?', '@', '#', '№', ',', '.', '$', '%', '&', '*' };
	Color[] labelColor = new Color[]
	{ Color.RED, Color.YELLOW, Color.GREEN };
	JPasswordField pin = new JPasswordField();
	String title;
	JLabel pinLabel = new JLabel();
	final JComponent[] inputs = new JComponent[]
	{ new JLabel("Код для БД:"), pin, pinLabel };

	MyOptionPane(String title)
	{
		this.title = title;
		pin.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changedUpdate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				String str = String.valueOf(pin.getPassword());
				char[] ch = str.toCharArray();
				if (ch.length >= 6)
				{
					if (checkChar(ch) && checkSymbol(ch))
					{
						pinLabel.setText(labelText[2]);
						pinLabel.setForeground(labelColor[2]);
					}
					else if (checkChar(ch) || checkSymbol(ch))
					{
						pinLabel.setText(labelText[1]);
						pinLabel.setForeground(labelColor[1]);
					}
				}
				else
				{
					pinLabel.setText(labelText[0]);
					pinLabel.setForeground(labelColor[0]);
				}

			}
		});
	}

	public String show()
	{
		JOptionPane.showMessageDialog(null, inputs, title,
				JOptionPane.NO_OPTION);
		return String.valueOf(pin.getPassword());
	}

	public boolean checkChar(char[] ch)
	{
		boolean f = false;
		for (int i = 0; i < ch.length; i++)
		{
			f = hasChar(ch[i], ch.length);
		}
		return f;
	}

	public boolean hasChar(char c, int leng)
	{
		int cnt = 0;
		for (int i = 0; i < 10; i++)
		{
			if (c == (char) i)
				cnt++;
		}
		if (cnt == leng)
			return false;
		else if (cnt > 0)
			return true;
		return false;
	}

	public boolean checkSymbol(char[] ch)
	{
		boolean f = false;
		for (int i = 0; i < ch.length; i++)
		{
			f = hasSymbol(ch[i], ch.length);
		}
		return f;
	}

	public boolean hasSymbol(char c, int leng)
	{
		int cnt = 0;
		for (int i = 0; i < symbol.length; i++)
		{
			if (c == symbol[i])
				cnt++;
		}
		if (cnt == leng)
			return false;
		else if (cnt > 0)
			return true;
		return false;
	}
}
