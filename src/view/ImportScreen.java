package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.TextSymbols;
import model.ImportSettings;
import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CSelectedFile;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ImportScreen extends Screen implements ActionListener {

	private List<CSelectedFile> files;
	private JScrollPane fileScrollPane;
	private JPanel filePanel, addFilePanel;

	public ImportScreen(Display display) {
		super(display);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.IMPORT;
	}

	@Override
	public void build() {
		super.build();

		filePanel = new JPanel();
		addFilePanel = new JPanel();
		fileScrollPane = new JScrollPane(filePanel);
		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		JButton cmdAddFile = new JButton("+");
		JButton btnImport = new JButton(TextSymbols.get(TextSymbols.IMPORT));

		fileScrollPane.setBorder(null);

		addFilePanel.add(cmdAddFile);

		setLayout(new BorderLayout(0, 0));

		cmdAddFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFileOption();
			}

		});

		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		verticalBox.add(Box.createVerticalGlue());

		verticalBox.add(fileScrollPane);
		addFileOption();

		verticalBox.add(Box.createVerticalStrut(40));

		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnImport.setMaximumSize(new Dimension(300, 75));
		btnImport.setMinimumSize(new Dimension(300, 75));
		btnImport.setPreferredSize(new Dimension(300, 75));
		btnImport.addActionListener(this);
		btnImport.setFont(btnImport.getFont().deriveFont(24f));
		btnImport.setIcon(new javax.swing.ImageIcon("media/img/arrow_up_32x32.png"));
		btnImport.setIconTextGap(20);
		verticalBox.add(btnImport);

		verticalBox.add(Box.createVerticalGlue());
		add(verticalBox);
	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display,
				AppScreen.SELECT_ACTION, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);
		
		CSwitchArrow forwardArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.RIGHT);
		forwardArrow.setEnabled(false);
		navbar.add(forwardArrow, BorderLayout.EAST);
		
	}

	public void addFileOption() {
		CSelectedFile next = new CSelectedFile(this);

		if (files == null) {
			files = new java.util.ArrayList<>();
		}

		if (!files.isEmpty()) {
			// after we pushed the first one in
			if (files.get(files.size() - 1).isEmpty()) {
				return;
			}
		}

		files.add(next);

		filePanel.remove(addFilePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		filePanel.add(next);
		filePanel.add(addFilePanel);

		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (files.size() == 1 && files.get(0).isEmpty()) {
			display.notice(MessageFatality.ERROR, TextSymbols.get(TextSymbols.IMPORT_NO_FILES));
			return;
		}
		
		ImportSettings settings = new ImportSettings(display.getControl().getSelectedDB());
		settings.setFiles(files);
		
		callback.accept(settings);

	}

	public void remove(CSelectedFile obj) {
		files.remove(obj);
		filePanel.remove(obj);

		if (files.size() == 0) {
			addFileOption();
		}

		revalidate();
	}

}
