package safe;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class MyOptionPane
{
	boolean ret = false;
	JButton exitBtn = new JButton("Отмена");
	JButton acceptBtn = new JButton("Принять");
	String[] labelText = new String[]
	{ "Слабый пароль", "Средний пароль", "Хороший пароль" };
	char[] symbol = new char[]
	{ '!', '?', '@', '#', '№', ',', '.', '$', '%', '&', '*' };
	Color[] labelColor = new Color[]
	{ Color.RED, new Color(252, 162, 0), Color.GREEN };
	JPasswordField pin = new JPasswordField(10);
	JLabel pinLabel = new JLabel();
	JLabel label = new JLabel();
	JDialog jd;

	String user_pin = "";

	MyOptionPane(prog frame, boolean use_password_difficulty, String title)
	{
		jd = new JDialog(frame, title, Dialog.ModalityType.APPLICATION_MODAL);
		jd.setLocationRelativeTo(null);
		jd.setSize(300, 150);
		jd.getContentPane().setLayout(new FlowLayout());
		jd.setUndecorated(true);

		jd.getContentPane().add(label);
		jd.getContentPane().add(pin);
		jd.add(pinLabel);
		jd.getContentPane().add(Box.createHorizontalStrut(300));
		jd.getContentPane().add(acceptBtn);
		jd.getContentPane().add(exitBtn);

		if (!use_password_difficulty)
		{
			jd.getContentPane().remove(pinLabel);
			label.setText("Код для БД:");
		}
		else
		{
			label.setText("Придумайте код для БД:");
		}
		acceptBtn.addActionListener(e ->
		{
			user_pin = String.valueOf(pin.getPassword());
			System.out.println(user_pin);
			jd.setVisible(false);
		});
		exitBtn.addActionListener(e ->
		{
			System.exit(0);
		});
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
				boolean weak = true;
				if (ch.length >= 6)
				{
					if (checkChar(ch) && checkSymbol(ch))
					{
						weak = false;
						pinLabel.setText(labelText[2]);
						pinLabel.setForeground(labelColor[2]);
					}
					else if (checkChar(ch) || checkSymbol(ch))
					{
						weak = false;
						pinLabel.setText(labelText[1]);
						pinLabel.setForeground(labelColor[1]);
					}
					if (weak)
					{
						pinLabel.setText(labelText[0]);
						pinLabel.setForeground(labelColor[0]);
					}
				}
				else
				{
					pinLabel.setText(labelText[0]);
					pinLabel.setForeground(labelColor[0]);
				}
				if (pinLabel.getText() == "Слабый пароль")
				{
					acceptBtn.setEnabled(false);
				}
				else
				{
					acceptBtn.setEnabled(true);
				}
			}
		});
	}

	public void show()
	{
		jd.setVisible(true);
		user_pin = String.valueOf(pin.getPassword());
	}

	public boolean checkChar(char[] ch)
	{
		int cnt = 0;
		for (int i = 0; i < 10; i++)
		{
			if (arrayHasSymbol(ch, (char) ('0' + i)))
				cnt++;
		}
		System.out.println("char " + cnt);
		if (cnt == ch.length)
			return false;
		else if (cnt > 0)
			return true;
		return false;
	}

	public boolean checkSymbol(char[] ch)
	{
		int cnt = 0;
		for (int i = 0; i < symbol.length; i++)
		{
			if (arrayHasSymbol(ch, symbol[i]))
			{
				cnt++;
			}
		}
		System.out.println("symbol " + cnt);
		if (cnt == ch.length)
			return false;
		else if (cnt > 0)
			return true;
		return false;
	}

	public boolean arrayHasSymbol(char[] array, char c)
	{
		for (char c1 : array)
		{
			if (c1 == c)
			{
				return true;
			}
		}
		return false;
	}
}
