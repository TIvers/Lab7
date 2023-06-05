
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PlayerChooserFrame extends JFrame {
	public PlayerChooserFrame() {
		super("Players List");
		setSize(500, 500);
		setLocation(200, 100);
		setLayout(new BorderLayout());
		
		
		
		createMenubar();
		createToolbar();
		createListsPanel();
		createToolbarListeners();
		createButtonsListeners();
	}

	private void createButtonsListeners() {
		takeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selection = playersList.getSelectedValuesList();
				for (String player : selection) {
					teamModel.addElement(player);
				}
				for (String player : selection) {
					playersModel.removeElement(player);
				}
			}		
		});
		
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selection = teamList.getSelectedValuesList();
				for (String player : selection) {
					playersModel.addElement(player);
				}
				for (String player : selection) {
					teamModel.removeElement(player);
				}
			}			
		});
		
		takeAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Enumeration<String> elements = playersModel.elements();
				while (elements.hasMoreElements()) {
					String next = elements.nextElement();
					teamModel.addElement(next);
				}
				playersModel.removeAllElements();
			}
		});
		
		returnAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					String next = elements.nextElement();
					playersModel.addElement(next);
				}
				teamModel.removeAllElements();
			}
		});
		
	}

	private void createToolbarListeners() {
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String all = "";
				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					all += elements.nextElement() + "\n";
				}
				JOptionPane.showMessageDialog(getParent(), all,
						"Выбраны следующие игроки: ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				teamModel.removeAllElements();
				playersModel.removeAllElements();
				for (String player : PlayersBase.getPlayers()) {
					playersModel.addElement(player);
				}
			}
		});
	}


	private JButton resetButton, saveButton;
	private void createToolbar() {
		saveButton = new JButton("Сохранить");
		resetButton = new JButton("Очистить");
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		toolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		toolbar.add(saveButton);
		toolbar.add(resetButton);
		add(toolbar, BorderLayout.NORTH);
	}

	private void createMenubar() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu fileMenu = new JMenu("Файл");
		menubar.add(fileMenu);
		JMenuItem exitItem = new JMenuItem("Выход");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(getParent(),
						"Вы уверены, что хотите выйти?", "Подтвердить",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null);
				if (option == JOptionPane.OK_OPTION) {
					System.exit(0);
				}				
			}			
		});
		fileMenu.add(exitItem);
	}
	
	
	private DefaultListModel<String> playersModel, teamModel;
	private JList<String> playersList, teamList;
	private JButton takeButton, returnButton, takeAllButton, returnAllButton;
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonsPanel.setLayout(new GridLayout(4, 0));
		takeButton = new JButton(">");
		takeButton.setToolTipText("Добавить выделенных игроков");
		takeAllButton = new JButton(">>");
		takeAllButton.setToolTipText("Добавить всех игроков");
		returnButton = new JButton("<");
		returnButton.setToolTipText("Вернуть выделенных игроков");
		returnAllButton = new JButton("<<");
		returnAllButton.setToolTipText("Вернуть всех игроков");
		buttonsPanel.add(takeButton);
		buttonsPanel.add(takeAllButton);
		buttonsPanel.add(returnButton);
		buttonsPanel.add(returnAllButton);
		
		return buttonsPanel;
	}

	private void createListsPanel() {
		playersModel = new DefaultListModel<String>();
		for (String player : PlayersBase.getPlayers()) {
			playersModel.addElement(player);
		}		
		teamModel = new DefaultListModel<String>();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		playersList = new JList<String>(playersModel);
		playersList.setToolTipText("Доступные игроки");
		playersList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamList = new JList<String>(teamModel);
		teamList.setToolTipText("Выбранные игроки");
		teamList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(playersList);
		panel.add(createButtonsPanel());
		panel.add(teamList);
		add(panel, BorderLayout.CENTER);
	}	
	
}








