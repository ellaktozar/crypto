package safe;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class prog extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	int pinIn;
	int pinTrue;
	String str;
	boolean b;
	int tmp_pswrd;
	String s = "%&";
	String s1 = "    ";
	String spinTrue1;
	private JTable table;
	Object[][] mas;
	pin pin1 = new pin();

	private JFrame writeFrame = new JFrame();
	private JPanel writePane = new JPanel();
	private JTextField resourseField = new JTextField(), passwordField = new JTextField();
	private JButton saveButton = new JButton("Сохранить");
	private JLabel res = new JLabel("Введите ресурс");
	private JLabel pas = new JLabel("Введите пароль");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				prog frame = new prog();
				frame.runOnStart(frame);
			}
		});
	}

	public void runOnStart(prog frame)
	{
		File nf = new File(System.getProperty("user.dir") + "/safe/safe.mv.db");
		if (!nf.exists())
		{
			System.out.println("creating new db");
			if (!pin1.createNewPin())
			{
				System.out.println("cannot create pin");
				JOptionPane.showMessageDialog(null, "Пароль не введен", "Ошибка", 0);
				System.exit(0);
			}
			else
			{
				System.out.println("pin successfully created");
			}
		}
		else
		{
			System.out.println("using existing db");
			pin1.inPin = JOptionPane.showInputDialog(null, "Запуск", "Введите пин-код", 1).toString().trim();
		}

		frame.setVisible(true);

		progSt();
	}

	/**
	 * Create the frame.
	 */
	public void progSt()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(255, 255, 255));

		JButton deleteBtn = new JButton("Удалить"); // Кнопка удаления ряда
		setTitle("Сейф паролей");
		deleteBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int[] selectedRows = table.getSelectedRows();

				if (selectedRows.length > 0)
				{
					int[] rowsForDelete = new int[selectedRows.length];
					for (int i = 0; i < selectedRows.length; i++)
					{
						rowsForDelete[i] = Integer.parseInt(table.getValueAt(selectedRows[i], 0).toString());
					}
					if (pin1.DeleteRow(rowsForDelete))
					{
						printTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Ошибка записи", "Ошибка", 0);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Выделите строчки", "Ошибка", 0);
				}
			}
		});
		deleteBtn.setBounds(55, 225, 89, 23);

		saveButton.addActionListener(new ActionListener()
		{
			// Кнопка для сохранения новых данных
			public void actionPerformed(ActionEvent arg0)
			{

				String resourceName = resourseField.getText().trim(), resourcePassword = passwordField.getText().trim();

				if (resourceName.length() > 0 && resourcePassword.length() > 0)
				{
					if (pin1.checkRow(resourceName, resourcePassword))
					{
						writeFrame.setVisible(false);
						printTable();
					}
					else
					{
						JOptionPane.showMessageDialog(writeFrame, "Ошибка записи!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(writeFrame, "Все поля должны быть заполнены!");
				}
			}
		});

		JButton btnWrite = new JButton(// Кнопка для добавления новых данных
				"\u0417\u0430\u043F\u0438\u0441\u0430\u0442\u044C");
		btnWrite.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				writeFrame.setBounds(10, 10, 300, 260);
				writePane.setLayout(null);
				writePane.setBorder(new EmptyBorder(5, 5, 5, 5));
				writePane.setBackground(Color.WHITE);
				writeFrame.setContentPane(writePane);
				writeFrame.setTitle("Добавить новую запись");

				resourseField.setBounds(10, 45, 265, 30);
				resourseField.setBorder(new LineBorder(new Color(154, 172, 186)));
				resourseField.setBackground(new Color(181, 207, 227));
				passwordField.setBounds(10, 115, 265, 30);
				passwordField.setBorder(new LineBorder(new Color(154, 172, 186)));
				passwordField.setBackground(new Color(181, 207, 227));
				saveButton.setBounds(10, 155, 264, 30);
				res.setBounds(10, 10, 265, 30);
				pas.setBounds(10, 80, 265, 30);

				writePane.add(resourseField);
				writePane.add(passwordField);
				writePane.add(saveButton);
				writePane.add(res);
				writePane.add(pas);
				writeFrame.setVisible(true);

			}
		});
		btnWrite.setBounds(284, 225, 89, 23);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 16, 400, 200);

		table = new JTable();
		printTable();

		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);

		if (pin1.checkshowAddBtn() == true)
		{
			contentPane.add(btnWrite);
			contentPane.add(deleteBtn);
		}

	}

	private void printTable()
	{
		table.setModel(new DefaultTableModel(pin1.drawTbl(), new String[]
		{ "id", "Ресурс", "Пароль" }));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setBounds(55, 32, 318, 149);
		table.setBackground(new Color(123, 182, 208));
	}
}
