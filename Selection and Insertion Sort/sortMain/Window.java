package sortMain;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.BorderLayout;

import javax.swing.JProgressBar;

/**
 * UI class created with WindowBuilder plugin.
 * @author Reed
 *
 */
public class Window {

	private JFrame frmCmscInsertionAnd;
	private JTextArea textArea;
	private JTextArea textArea2;
	private JPanel graphsPanel;
	private JProgressBar progressBar;

	/**
	 * Launch the application (not used)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmCmscInsertionAnd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		this.frmCmscInsertionAnd.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		frmCmscInsertionAnd = new JFrame();
		frmCmscInsertionAnd.setTitle("CMSC351 Insertion and Selection Sort");
		frmCmscInsertionAnd.setBounds(100, 100, 460, 600);
		frmCmscInsertionAnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getSelectedIndex() == 2) { // Graphs pane
					frmCmscInsertionAnd.setBounds(frmCmscInsertionAnd.getX(), frmCmscInsertionAnd.getY(), 800, 900);
				} else if (tabbedPane.getSelectedIndex() == 0 ||
						tabbedPane.getSelectedIndex() == 1) { //Text pane
					frmCmscInsertionAnd.setBounds(frmCmscInsertionAnd.getX(), frmCmscInsertionAnd.getY(), 460, 600);
				}
			}
		});
		frmCmscInsertionAnd.getContentPane().setLayout(new BorderLayout(0, 0));
		frmCmscInsertionAnd.getContentPane().add(tabbedPane);

		textArea = new JTextArea();
		textArea.setMargin(new Insets(2, 4, 2, 2));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textArea);
		tabbedPane.addTab("Output", null, scrollPane, null);

		textArea2 = new JTextArea();
		textArea2.setText("Wait for program to finish running!");
		textArea2.setMargin(new Insets(2, 4, 2, 2));
		textArea2.setLineWrap(true);
		textArea2.setEditable(false);
		textArea2.setFont(new Font("Courier New", Font.PLAIN, 12));
		JScrollPane scrollPane2 = new JScrollPane(textArea2);
		tabbedPane.addTab("Results", null, scrollPane2, null);
		tabbedPane.setEnabledAt(1, true);

		graphsPanel = new JPanel();
		tabbedPane.addTab("Graphs", null, graphsPanel, null);
		graphsPanel.setLayout(new BoxLayout(graphsPanel, BoxLayout.Y_AXIS));

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		frmCmscInsertionAnd.getContentPane().add(progressBar, BorderLayout.SOUTH);

	}

	public JTextArea getTextArea() {
		return textArea;
	}
	public JPanel getGraphsPanel() {
		return graphsPanel;
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JTextArea getTextArea2() {
		return textArea2;
	}
}
