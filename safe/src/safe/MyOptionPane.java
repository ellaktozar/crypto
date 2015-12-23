package safe;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
	String title;
	JLabel pinLabel = new JLabel();
	final JComponent[] inputs = new JComponent[]
	{ new JLabel("Код для БД:"), pin, pinLabel, acceptBtn, exitBtn };
	JFrame jf = new JFrame();

	MyOptionPane(String title)
	{
		acceptBtn.addActionListener(e -> {
			ret = true;
			jf.setVisible(false);
		});
		exitBtn.addActionListener(e -> {
			System.exit(0);
		});
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
				boolean weak = true;
				if (ch.length > 6)
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
			}
		});
	}

	public void show()
	{
		// JOptionPane.showMessageDialog(null, inputs, title,
		// JOptionPane.NO_OPTION);

		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
		jf.setSize(300,300);
		jf.setLayout(new FlowLayout());
		for (JComponent k : inputs)
			jf.getContentPane().add(k);
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
