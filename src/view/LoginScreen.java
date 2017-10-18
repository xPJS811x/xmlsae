package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;

import control.Connection;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CPasswordField;
import view.atoms.CTextField;
import view.atoms.KeyHandler;
import view.atoms.KeyHandler.HandleTarget;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;

@SuppressWarnings("serial")
public class LoginScreen extends Screen implements ActionListener {

	private Consumer<Object> callback;
	private CTextField tfUser;
	private CPasswordField tfPassword;

	public LoginScreen(Display parent) {
		super(parent);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.LOGIN;
	}

	@Override
	public void build() {
		super.build();

		KeyHandler keyHandler = new KeyHandler().handle(HandleTarget.TYPED, e -> {
			// getKeyCode doesn't seem to work on my keyboard sooo...
			if (e.getKeyChar() == '\n') {
				this.actionPerformed(null);
			}
		});
		
		setLayout(new BorderLayout(0, 0));
		
		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		verticalBox.add(Box.createVerticalGlue());
		
		tfUser = new CTextField("user@host...");
		tfUser.setColumns(16);
		tfUser.addKeyListener(keyHandler);
		tfUser.setMinimumSize(new java.awt.Dimension(120, 30));
		tfUser.setMaximumSize(new java.awt.Dimension(120, 30));
		verticalBox.add(tfUser);
		
		verticalBox.add(Box.createVerticalStrut(20));
		
		tfPassword = new CPasswordField("password...");
		tfPassword.setColumns(16);
		tfPassword.addKeyListener(keyHandler);
		tfPassword.setMinimumSize(new java.awt.Dimension(120, 30));
		tfPassword.setMaximumSize(new java.awt.Dimension(120, 30));
		verticalBox.add(tfPassword);

		verticalBox.add(Box.createVerticalStrut(20));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.addActionListener(this);
		verticalBox.add(btnLogin);
		
		verticalBox.add(Box.createVerticalGlue());
		
		add(verticalBox);
	}

	@Override
	public void setCallback(Consumer<Object> action) {
		callback = action;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// e could be null if this was called via an 'enter' hit
		try {
			String pw = new String(tfPassword.getPassword());
			final Connection con = new Connection(tfUser.getText(), pw);
			callback.accept(con);
		} catch (SQLException e) {
			display.notice(MessageFatality.ERROR, "Connection couldn't be established", e.getMessage());
		}

	}

}