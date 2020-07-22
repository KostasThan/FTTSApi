package app.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.commands.external.AddTaskCommand;
import app.commands.external.ChangeRecorderStatusCommand;
import app.commands.external.CopyVoicePreferencesCommand;
import app.commands.external.CreateDocumentCommand;
import app.commands.external.CreateUserMacroCommand;
import app.commands.external.CustomSpeakDocumentCommand;
import app.commands.external.CustomSpeakLineCommand;
import app.commands.external.EncodeSelectedCommand;
import app.commands.external.EncodeTextAreaCommand;
import app.commands.external.ExternalCommand;
import app.commands.external.OpenFileCommand;
import app.commands.external.PlayRecorderCommand;
import app.commands.external.RunAfterConfirmationCommand;
import app.commands.external.RunSelectedMacroCommand;
import app.commands.external.SaveAsCommand;
import app.commands.external.SaveCommand;
import app.commands.external.SetVoiceValuesCommand;
import app.commands.external.SpeakDocumentCommand;
import app.commands.external.StopSpeakMethodCommand;
import app.commands.external.TuneVoiceCommand;
import app.commands.internal.InternalCommand;
import app.commands.internal.PanelSwitchCommand;
import app.commands.internal.RefreshDefaultListCommand;
import app.commands.internal.UpdateDocumentCommand;
import app.commands.internal.UpdateTextAreaCommand;
import app.model.encodes.EncodeFactory;
import app.model.myenums.EncodeEnums;
import app.model.myenums.UserCommandsEnums;
import app.model.myenums.VoiceValuesEnum;
import app.model.textNspeak.Document;
import app.model.textNspeak.TTSApi;
import app.model.textNspeak.TextAreaUtilities;
import app.model.usermacro.CommandRecorder;
import app.model.usermacro.MacroCommand;
import app.model.usermacro.UserCommandsFactory;
import app.model.usermacro.UserMacroHandler;

public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3796045091323384427L;

	private JButton playRecordingToolbarButton;
	private JButton toTextEditorButton;
	private JButton toMyTasksToolbarButton;
	private JButton toAddTasksToolbarButton;
	private JButton toAboutToolbarButton;
	private JButton createDocumentButton;
	private JButton openFileButton;
	private JButton atbashTextButton;
	private JButton rotTextButton;
	private JButton saveFileButton;
	private JButton saveFileAsButton;
	private JButton speakDocumentButton;
	private JButton customSpeakDocumentButton;
	private JButton speechSettingsButton;
	private JButton decreasePitchButton;
	private JButton increasePitchButton;
	private JButton decreaseRateButton;
	private JButton increaseRateButton;
	private JButton decreaseVolumeButton;
	private JButton increaseVolumeButton;
	private JButton playPreviewButton;
	private JButton applyButton;
	private JButton resetValuesButton;
	private JButton toEditorButton;
	private JButton createUserMacroButton;
	private JButton addCommandButton;
	private JButton clearAddedTasksButton;
	private JButton stopDocumentSpeakButton;
	private JButton runSelectedCommandButton;
	private JButton updatePreviewButton;
	private JToggleButton changeRecorderStatusToolbarButton;

	private MacroCommand changeRecorderStatusToolbarButtonCommands;
	private MacroCommand playRecordingToolbarButtonCommands;
	private MacroCommand toTextEditorToolbarButtonCommands;
	private MacroCommand toMyTasksToolbarButtonCommands;
	private MacroCommand toAddTasksToolbarButtonCommands;
	private MacroCommand toAboutToolbarButtonCommands;
	private MacroCommand createDocumentMacroCommands;
	private MacroCommand openFileMacroCommands;
	private MacroCommand atbashDocumentMacroCommands;
	private MacroCommand rotDocumentMacroCommands;
	private MacroCommand saveFileMacroCommands;
	private MacroCommand saveFileAsMacroCommands;
	private MacroCommand speakDocumentMacroCommands;
	private MacroCommand customSpeakDocumentMacroCommands;
	private MacroCommand customSpeakLineMacroCommands;
	private MacroCommand toSpeechSettingMacroCommands;
	private MacroCommand playPreviewButtonCommands;
	private MacroCommand decreasePitchMacroCommands;
	private MacroCommand increasePitchMacroCommands;
	private MacroCommand decreaseRateMacroCommands;
	private MacroCommand increaseRateMacroCommands;
	private MacroCommand decreaseVolumeMacroCommands;
	private MacroCommand increaseVolumeMacroCommands;
	private MacroCommand applyButtonCommands;
	private MacroCommand resetValuesCommands;
	private MacroCommand toTextEditorButtonCommands;
	private MacroCommand atbashHighlightedMacroCommands;
	private MacroCommand rotHighlightedMacroCommands;
	private MacroCommand addTaskMacroCommands;
	private MacroCommand createUserMacroCommands;
	private MacroCommand clearAddedCommandsMacroCommands;
	private MacroCommand stopDocumentSpeakingMacroCommands;
	private MacroCommand runSelectedCommandMacroCommands;
	private MacroCommand updatePreviewMacroCommands;

	private ExternalCommand decreasePitchCommand;
	private ExternalCommand increasePitchCommand;
	private ExternalCommand decreaseRateCommand;
	private ExternalCommand increaseRateCommand;
	private ExternalCommand decreaseVolumeCommand;
	private ExternalCommand increaseVolumeCommand;
	private ExternalCommand copyVoicePreferencesCommand;
	private ExternalCommand resetVoiceValuesCommand;
	private ExternalCommand atBashDocumentCommand;
	private ExternalCommand rotDocumentCommand;
	private ExternalCommand saveFileAsCommand;
	private ExternalCommand saveAsAfterSaveCommand;
	private ExternalCommand saveFileCommand;
	private ExternalCommand createDocumentCommand;
	private ExternalCommand createDocumentAfterGui;
	private ExternalCommand promtSaveCommand;
	private ExternalCommand speakDocumentCommand;
	private InternalCommand updateDocumentCommand;
	private ExternalCommand customSpeakDocumentCommand;
	private ExternalCommand openFileAfterConfirmationCommand;
	private ExternalCommand customSpeakLineCommand;
	private ExternalCommand atbashHighlightedCommand;
	private ExternalCommand rotHighlightedCommand;
	private ExternalCommand addTaskCommand;
	private ExternalCommand addTaskAfterConfirmation;
	private ExternalCommand createUserMacroCommand;
	private ExternalCommand stopDocumentSpeakingCommand;
	private ExternalCommand stopPreviewPlayerSpeakingCommand;
	private ExternalCommand runSelectedMacroCommand;
	private ExternalCommand switchToEditorAfterConfirmation;
	private ExternalCommand openFileCommand;
	private ExternalCommand speakPreviewPlayerCommand;
	private ExternalCommand changeRecorderStatusCommand;
	private ExternalCommand playRecorderCommand;

	private InternalCommand switchToTextEditorCommand;
	private InternalCommand switchToTextEditorMainPanelCommand;
	private InternalCommand switchToSpeechSettingsPanel;
	private InternalCommand switchToAddTasksCommand;
	private InternalCommand updateTextAreaCommand;
	private InternalCommand switchToMyTasksCommand;
	private RefreshDefaultListCommand<UserCommandsEnums> refreshAddedCommandsListCommand;
	private RefreshDefaultListCommand<String> refreshCreatedMacrosListCommand;
	private RefreshDefaultListCommand<UserCommandsEnums> refreshCreatedMacrosContentstListCommand;
	private JLabel pitchValueLabel;
	private JLabel rateValueLabel;
	private JLabel volumeValueLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_12;

	private JPanel toolbarOuterPanel;
	private JLayeredPane appLayeredPane;
	private JInternalFrame textEditorInternalFrame;

	private AtomicBoolean commandResult; // dummy variable used to get result from a command
	private TextAreaUtilities textEditorUtilities;

	private Document doc;

	private Document previewPlayer;

	private CommandRecorder recorder;

	private JPanel speechSettingsPanel;

	private JScrollPane toolbarScrollPane;
	private JLayeredPane textEditorLayeredPane;
	private JPanel textEditorMainPanel;
	private JPanel textEditorToolbar;
	private JPanel speechToolbarPanel;
	private JTextArea textEditorTextArea;
	private LineHighlightingSpinner lineSpinner;

	private String author;
	private String title;

	private JCheckBox reverseCheckBox;

	private JComboBox<EncodeEnums> ciphersComboBox_1;

	private File file;

	private JInternalFrame addTasksInternalFrame;
	private JScrollPane addedCommandsScrollPane;
	private JList<UserCommandsEnums> addedCommandsJList;
	private DefaultListModel<UserCommandsEnums> addedCommandsDefaultList;

	private JScrollPane availableCommandsScrollPane;
	private JList<UserCommandsEnums> availableCommandsJList;
	private DefaultListModel<UserCommandsEnums> availableCommandsDefaultList;
	private UserCommandsFactory userCommandsFactory;
	private JMenuItem atBashHighLightedMenuItem;
	private JPopupMenu textAreaPopupMenu;

	private JMenuItem rotHighlightedMenuItem;
	private JMenuItem speechSettingMenuItem;

	private UserMacroHandler userMacroHandler;

	private JTextField userMacroTitleTextArea;

	private JScrollPane scrollPane;

	private JInternalFrame myTasksInternalFrame;

	private JList<String> createdMacrosJList;

	private DefaultListModel<String> createdMacrosDefaultList;
	private JScrollPane scrollPane_2;

	private JList<UserCommandsEnums> createdMacrosContentsList;
	private DefaultListModel<UserCommandsEnums> createdMacrosContentsDefaultList;

	private JCheckBox changeWindowCheckBox;

	private JTextField previewTextField;

	private JTextArea previewSentenceTextArea;
	private JCheckBox showSameEncodeCommandAlreadyAddedCheckBox;
	private JCheckBox showSpeakCommandAlreadyAddedCheckBox;
	private JButton speakLineButton;

	
	private JLabel docVolumeLabel;
	private JLabel docWPMLabel;
	private JLabel docPitchLabel;

	private JScrollPane previewSentenceScrollPanel;
	private JButton stopPreviewPlayerButton;

	private MacroCommand stopPreviewPlayerMacroCommands;
	private JInternalFrame aboutInternalFrame;

	private PanelSwitchCommand switchToAboutCommand;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainView frame = new MainView();

					frame.setVisible(true);
					frame.setTitle("Text2SpeechEditor");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		

		//initialized all the components
		initComponents();
		
		//initialized all Classed that this class depends upon except the commands
		initFields();
		
		//initialize a macro for every button
		initMacros();
		
		//initialize the commands
		initCommands();
		
		//populate the factory
		populateCommandFactory();
		
		//populate the macros
		assingCommandsToMacros();
		
		//create all the events
		createEvents();
		
		
		//just to set the main frame.
		//dont mess with the layers of the layered pane
		switchToTextEditorMainPanelCommand.execute();

	}

	
	
	
	

	private void initComponents() {

		//initialize every frame
		initMainFrame();
		initToolbarPanel();
		initTextEditorPanel();
		initAddTasksPanel();
		initMyTasksPanel();
		
		

	}

	private void initMainFrame() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 250, 818, 801);
		getContentPane().setBounds(new Rectangle(0, 0, 0, 150));

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainView.class.getResource("/resources/icons/main frame icon.png")));
		setResizable(false);
	
		setTitle("Text2Speech Editor");
		getContentPane().setLayout(null);
		
		// initialize app layer pane
		appLayeredPane = new JLayeredPane();
		appLayeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		appLayeredPane.setBorder(null);
		appLayeredPane.setBounds(0, 74, 816, 684);
		getContentPane().add(appLayeredPane);
		appLayeredPane.setLayout(null);

	}



	private void initToolbarPanel() {

		
		toolbarOuterPanel = new JPanel();
		toolbarOuterPanel.setBounds(0, 0, 816, 74);
		getContentPane().add(toolbarOuterPanel);
		toolbarOuterPanel.setLayout(new GridLayout(1, 0, 0, 0));

		toolbarScrollPane = new JScrollPane();
		toolbarScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		toolbarOuterPanel.add(toolbarScrollPane);
		JPanel toolbarViewPortPanel = new JPanel();
		toolbarViewPortPanel.setFocusable(true);
		toolbarViewPortPanel.setBackground(Color.BLACK);
		toolbarViewPortPanel.setBounds(new Rectangle(0, 0, 495, 80));

		toTextEditorButton = new OvalButton(OvalButton.SHAPE_OVAL, OvalButton.HORIZONTAL);
		toTextEditorButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toTextEditorButton.setText("<html><b><u>EDITOR</u></b></html>");
		toTextEditorButton.setBounds(343, 0, 113, 59);

		toTextEditorButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		toMyTasksToolbarButton = new JButton("My Tasks");
		toMyTasksToolbarButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/my tasks icon.png")));
		toMyTasksToolbarButton.setBackground(Color.WHITE);
		toMyTasksToolbarButton.setBorder(null);
		toMyTasksToolbarButton.setBounds(109, 38, 99, 28);

		toMyTasksToolbarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		toAddTasksToolbarButton = new JButton("Add Tasks");
		toAddTasksToolbarButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/add icon.png")));
		toAddTasksToolbarButton.setBackground(Color.WHITE);
		toAddTasksToolbarButton.setBorder(null);
		toAddTasksToolbarButton.setBounds(109, 7, 99, 28);

		toAddTasksToolbarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		toAboutToolbarButton = new JButton("About");
		toAboutToolbarButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/help icon.png")));
		toAboutToolbarButton.setBorder(null);
		toAboutToolbarButton.setBackground(Color.WHITE);
		toAboutToolbarButton.setBounds(720, 11, 74, 50);
		toAboutToolbarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		changeRecorderStatusToolbarButton = new JToggleButton("Record");
		changeRecorderStatusToolbarButton.setOpaque(true);
		changeRecorderStatusToolbarButton
				.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/stop rec icon.png")));

		changeRecorderStatusToolbarButton.setBounds(2, 5, 99, 30);
		changeRecorderStatusToolbarButton.setBorder(null);
		changeRecorderStatusToolbarButton.setBackground(Color.WHITE);
		changeRecorderStatusToolbarButton.setForeground(Color.BLACK);

		playRecordingToolbarButton = new JButton();
		playRecordingToolbarButton.setText("Play");
		playRecordingToolbarButton.setBackground(Color.WHITE);
		playRecordingToolbarButton.setBorder(null);
		playRecordingToolbarButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/play icon.png")));
		playRecordingToolbarButton.setBounds(3, 38, 99, 28);

		toolbarScrollPane.setViewportView(toolbarViewPortPanel);
		toolbarViewPortPanel.setLayout(null);
		toolbarViewPortPanel.add(changeRecorderStatusToolbarButton);
		toolbarViewPortPanel.add(playRecordingToolbarButton);
		toolbarViewPortPanel.add(toTextEditorButton);
		toolbarViewPortPanel.add(toMyTasksToolbarButton);
		toolbarViewPortPanel.add(toAddTasksToolbarButton);
		toolbarViewPortPanel.add(toAboutToolbarButton);

	}

	private void initTextEditorPanel() {

		textEditorInternalFrame = new JInternalFrame("Text Editor");
		textEditorInternalFrame
				.setFrameIcon(new ImageIcon(MainView.class.getResource("/resources/icons/text icon.png")));
		appLayeredPane.setLayer(textEditorInternalFrame, 24);
		textEditorInternalFrame.setBounds(0, 0, 802, 691);
		appLayeredPane.add(textEditorInternalFrame);
		textEditorInternalFrame.getContentPane().setLayout(null);

		textEditorLayeredPane = new JLayeredPane();
		textEditorLayeredPane.setBounds(0, 0, 802, 661);
		textEditorInternalFrame.getContentPane().add(textEditorLayeredPane);

		textEditorMainPanel = new JPanel();
		textEditorLayeredPane.setLayer(textEditorMainPanel, 7);
		textEditorMainPanel.setBounds(0, 0, 792, 659);
		textEditorLayeredPane.add(textEditorMainPanel);
		textEditorInternalFrame.setVisible(true);
		textEditorMainPanel.setLayout(null);

		
		// ----TEXT TOOLBAR----
		textEditorToolbar = new JPanel();
		textEditorToolbar.setBounds(0, 0, 80, 659);
		textEditorToolbar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textEditorToolbar.setBackground(Color.BLACK);
		textEditorToolbar.setLayout(null);

		createDocumentButton = new JButton("");
		createDocumentButton.setBackground(Color.WHITE);
		createDocumentButton.setBorder(null);
		createDocumentButton.setBounds(10, 11, 59, 40);
		createDocumentButton
				.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/Create new file image.png")));
		createDocumentButton.setToolTipText("Create Document");

		openFileButton = new JButton("");
		openFileButton.setBackground(Color.WHITE);
		openFileButton.setBorder(null);
		openFileButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/open file image.jpg")));
		openFileButton.setBounds(10, 62, 59, 40);
		openFileButton.setToolTipText("Open file");

		atbashTextButton = new JButton("");
		atbashTextButton.setBackground(Color.WHITE);
		atbashTextButton.setBorder(null);
		atbashTextButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/atbash cipher image.jpg")));
		atbashTextButton.setBounds(10, 113, 59, 40);
		atbashTextButton.setToolTipText("Atbash Transform Text");

		saveFileButton = new JButton("");
		saveFileButton.setBackground(Color.WHITE);
		saveFileButton.setBorder(null);
		saveFileButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/save image.jpg")));
		saveFileButton.setBounds(10, 556, 59, 41);
		saveFileButton.setToolTipText("Save");

		saveFileAsButton = new JButton("");
		saveFileAsButton.setBackground(Color.WHITE);
		saveFileAsButton.setBorder(null);
		saveFileAsButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/save as image.jpg")));
		saveFileAsButton.setBounds(10, 608, 59, 40);
		saveFileAsButton.setToolTipText("Save As");

		rotTextButton = new JButton("");
		rotTextButton.setBackground(Color.WHITE);
		rotTextButton.setBorder(null);
		rotTextButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/rot13 cipher image.jpg")));
		rotTextButton.setBounds(10, 164, 59, 39);
		rotTextButton.setToolTipText("Rot13 Transform Text");

		textEditorToolbar.add(saveFileButton);
		textEditorToolbar.add(rotTextButton);
		textEditorToolbar.add(atbashTextButton);
		textEditorToolbar.add(createDocumentButton);
		textEditorToolbar.add(saveFileAsButton);
		textEditorToolbar.add(openFileButton);

		
		textEditorMainPanel.add(textEditorToolbar);
		

		scrollPane = new JScrollPane();
		scrollPane.setBounds(80, 0, 622, 659);
		textEditorMainPanel.add(scrollPane);

		textEditorTextArea = new JTextArea();

		textEditorTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(textEditorTextArea);

		
		textAreaPopupMenu = new JPopupMenu();
		addPopup(textEditorTextArea, textAreaPopupMenu);

		atBashHighLightedMenuItem = new JMenuItem("Atbash highlighted text");
		textAreaPopupMenu.add(atBashHighLightedMenuItem);

		rotHighlightedMenuItem = new JMenuItem("Rot highlighted text");
		textAreaPopupMenu.add(rotHighlightedMenuItem);

		speechSettingMenuItem = new JMenuItem("Speech settings");
		textAreaPopupMenu.add(speechSettingMenuItem);

		
		
		
		// ----SPEECH TOOLBAR----
		speechToolbarPanel = new JPanel();
		speechToolbarPanel.setBorder(null);
		speechToolbarPanel.setBounds(702, 0, 83, 659);
		speechToolbarPanel.setForeground(Color.BLACK);
		speechToolbarPanel.setBackground(Color.BLACK);

		textEditorMainPanel.add(speechToolbarPanel);
		speechToolbarPanel.setLayout(null);

		speakDocumentButton = new JButton("Speak");
		speakDocumentButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/speaker icon.png")));
		speakDocumentButton.setBorder(null);
		speakDocumentButton.setBackground(Color.WHITE);
		speakDocumentButton.setToolTipText("Convert the whole text to speech");
		speakDocumentButton.setBounds(6, 11, 74, 39);
		speechToolbarPanel.add(speakDocumentButton);

		reverseCheckBox = new JCheckBox("Reverse?");
		reverseCheckBox.setForeground(Color.WHITE);
		reverseCheckBox.setOpaque(false);
		reverseCheckBox.setBounds(2, 240, 78, 23);
		speechToolbarPanel.add(reverseCheckBox);

		ciphersComboBox_1 = new JComboBox<EncodeEnums>();
		ciphersComboBox_1.setForeground(Color.WHITE);
		ciphersComboBox_1.setOpaque(false);
		ciphersComboBox_1.setBorder(null);

		ciphersComboBox_1.setToolTipText("Use this dropdown to select a cipher method.");
		ciphersComboBox_1.setRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7380163054713243500L;

			@Override
			public void paint(Graphics g) {
				setBackground(Color.white);
				setForeground(Color.BLACK);
				super.paint(g);
				super.repaint();

			}
		});


		ciphersComboBox_1.setFont(new Font("Tahoma", Font.ITALIC, 11));

		customSpeakDocumentButton = new JButton("<html>Custom<br />Speak</html>");
		customSpeakDocumentButton
				.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/speaker icon.png")));
		customSpeakDocumentButton.setBackground(Color.WHITE);
		customSpeakDocumentButton.setBorder(null);
		customSpeakDocumentButton.setBounds(6, 61, 74, 39);
		speechToolbarPanel.add(customSpeakDocumentButton);

		ciphersComboBox_1.setModel(new DefaultComboBoxModel<EncodeEnums>(EncodeEnums.values()));
		ciphersComboBox_1.setSelectedIndex(0);
		ciphersComboBox_1.setBounds(6, 198, 74, 35);
		speechToolbarPanel.add(ciphersComboBox_1);

		lineSpinner = new LineHighlightingSpinner(textEditorTextArea);
		lineSpinner.setBounds(50, 167, 30, 20);
		speechToolbarPanel.add(lineSpinner);

		lblNewLabel_2 = new JLabel("Line:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(6, 169, 46, 14);
		speechToolbarPanel.add(lblNewLabel_2);

		speechSettingsButton = new JButton("Settings");
		speechSettingsButton.setBorder(null);
		speechSettingsButton.setBackground(Color.WHITE);
		speechSettingsButton.setBounds(6, 558, 74, 40);
		speechToolbarPanel.add(speechSettingsButton);

		stopDocumentSpeakButton = new JButton("<html> Stop <br> Speaker</html>");
		stopDocumentSpeakButton
				.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/stop speaker icon.png")));
		stopDocumentSpeakButton.setBackground(Color.WHITE);
		stopDocumentSpeakButton.setBorder(null);
		stopDocumentSpeakButton.setBounds(6, 609, 79, 39);
		speechToolbarPanel.add(stopDocumentSpeakButton);

		speakLineButton = new JButton("<html>Speak<br />Line</html>");
		speakLineButton.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/speaker icon.png")));
		speakLineButton.setToolTipText("Speaks the specified line according to the parametres.");
		speakLineButton.setBorder(null);
		speakLineButton.setBackground(Color.WHITE);
		speakLineButton.setBounds(6, 111, 74, 39);
		speechToolbarPanel.add(speakLineButton);

		// ------------------Settings----------
		speechSettingsPanel = new JPanel();
		speechSettingsPanel.setBackground(Color.WHITE);
		textEditorLayeredPane.setLayer(speechSettingsPanel, 8);

		speechSettingsPanel.setBounds(0, 0, 792, 659);
		textEditorLayeredPane.add(speechSettingsPanel);
		speechSettingsPanel.setLayout(null);

		JPanel previewPlayerPanel = new JPanel();
		previewPlayerPanel.setBackground(Color.BLACK);
		previewPlayerPanel.setForeground(Color.WHITE);
		previewPlayerPanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), "Preview Player",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		previewPlayerPanel.setBounds(0, 0, 415, 659);
		speechSettingsPanel.add(previewPlayerPanel);
		previewPlayerPanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Speech Pitch:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(13, 33, 75, 24);
		previewPlayerPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Speech Rate: ");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(13, 88, 75, 24);
		previewPlayerPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Volume: ");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(17, 138, 63, 20);
		previewPlayerPanel.add(lblNewLabel_5);

		resetValuesButton = new JButton("Reset");
		resetValuesButton.setBounds(301, 174, 75, 23);
		previewPlayerPanel.add(resetValuesButton);

		applyButton = new JButton("Apply");
		applyButton.setBounds(270, 208, 106, 23);
		previewPlayerPanel.add(applyButton);

		decreasePitchButton = new JButton("Decrease");
		decreasePitchButton.setBounds(91, 32, 89, 23);
		previewPlayerPanel.add(decreasePitchButton);

		increasePitchButton = new JButton("Increase");
	
		increasePitchButton.setBounds(283, 33, 89, 23);
		previewPlayerPanel.add(increasePitchButton);

		decreaseRateButton = new JButton("Decrease");
		decreaseRateButton.setBounds(91, 87, 89, 23);
		previewPlayerPanel.add(decreaseRateButton);

		increaseRateButton = new JButton("Increase");
		increaseRateButton.setBounds(283, 88, 89, 23);
		previewPlayerPanel.add(increaseRateButton);

		increaseVolumeButton = new JButton("Increase");
		increaseVolumeButton.setBounds(284, 136, 89, 23);
		previewPlayerPanel.add(increaseVolumeButton);

		decreaseVolumeButton = new JButton("Decrease");
		decreaseVolumeButton.setBounds(91, 135, 89, 23);
		previewPlayerPanel.add(decreaseVolumeButton);

		JLabel lblNewLabel_8 = new JLabel("wpm");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(238, 90, 35, 14);
		previewPlayerPanel.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("% Vol");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(233, 138, 41, 14);
		previewPlayerPanel.add(lblNewLabel_9);

		pitchValueLabel = new JLabel(VoiceValuesEnum.PitchStartingValue.toString());
		pitchValueLabel.setForeground(Color.WHITE);
		pitchValueLabel.setBounds(190, 32, 38, 20);
		previewPlayerPanel.add(pitchValueLabel);

		rateValueLabel = new JLabel(VoiceValuesEnum.RateStartingValue.toString());
		rateValueLabel.setForeground(Color.WHITE);
		rateValueLabel.setBounds(190, 90, 38, 14);
		previewPlayerPanel.add(rateValueLabel);

		volumeValueLabel = new JLabel(VoiceValuesEnum.VolumeStartingValue.toString());
		volumeValueLabel.setForeground(Color.WHITE);
		volumeValueLabel.setBounds(188, 138, 35, 14);
		previewPlayerPanel.add(volumeValueLabel);

		JLabel lblNewLabel_13 = new JLabel("Set a new preview sentence");
		lblNewLabel_13.setBounds(188, 433, 165, 14);
		previewPlayerPanel.add(lblNewLabel_13);

		JLabel lblNewLabel_1 = new JLabel("hertz");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(238, 31, 26, 23);
		previewPlayerPanel.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 1, true), "Current Speaker",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel.setBackground(Color.BLACK);
		panel.setBounds(13, 401, 210, 201);
		previewPlayerPanel.add(panel);
		panel.setLayout(null);

		docPitchLabel = new JLabel("100.0");
		docPitchLabel.setToolTipText("");
		docPitchLabel.setForeground(Color.WHITE);
		docPitchLabel.setBackground(Color.BLACK);
		docPitchLabel.setBounds(98, 36, 46, 14);
		panel.add(docPitchLabel);

		docWPMLabel = new JLabel("150.0");
		docWPMLabel.setToolTipText("");
		docWPMLabel.setForeground(Color.WHITE);
		docWPMLabel.setBackground(Color.BLACK);
		docWPMLabel.setBounds(98, 98, 46, 14);
		panel.add(docWPMLabel);

		JLabel lblHertz = new JLabel("hertz");
		lblHertz.setToolTipText("");
		lblHertz.setForeground(Color.WHITE);
		lblHertz.setBackground(Color.BLACK);
		lblHertz.setBounds(154, 36, 46, 14);
		panel.add(lblHertz);

		JLabel lblWpm = new JLabel("wpm");
		lblWpm.setToolTipText("");
		lblWpm.setForeground(Color.WHITE);
		lblWpm.setBackground(Color.BLACK);
		lblWpm.setBounds(154, 98, 46, 14);
		panel.add(lblWpm);

		docVolumeLabel = new JLabel("1.0");
		docVolumeLabel.setToolTipText("");
		docVolumeLabel.setForeground(Color.WHITE);
		docVolumeLabel.setBackground(Color.BLACK);
		docVolumeLabel.setBounds(98, 161, 46, 14);
		panel.add(docVolumeLabel);

		JLabel lblvol = new JLabel("%Vol");
		lblvol.setToolTipText("");
		lblvol.setForeground(Color.WHITE);
		lblvol.setBackground(Color.BLACK);
		lblvol.setBounds(154, 161, 46, 14);
		panel.add(lblvol);

		JLabel lblNewLabel_3_1 = new JLabel("Speech Pitch:");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setBounds(13, 31, 75, 24);
		panel.add(lblNewLabel_3_1);

		JLabel lblNewLabel_4_1 = new JLabel("Speech Rate: ");
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setBounds(13, 93, 75, 24);
		panel.add(lblNewLabel_4_1);

		JLabel lblNewLabel_5_1 = new JLabel("Volume: ");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		lblNewLabel_5_1.setBounds(13, 161, 63, 20);
		panel.add(lblNewLabel_5_1);
		textEditorTextArea.setLineWrap(true);
		textEditorTextArea.setWrapStyleWord(true);

		toEditorButton = new JButton("Back");
		toEditorButton.setBounds(649, 590, 106, 42);
		speechSettingsPanel.add(toEditorButton);

		previewTextField = new JTextField();
		previewTextField.setBounds(425, 191, 330, 20);
		speechSettingsPanel.add(previewTextField);
		previewTextField.setColumns(10);

		updatePreviewButton = new JButton("Update phrase");
		updatePreviewButton.setBounds(637, 222, 118, 23);
		speechSettingsPanel.add(updatePreviewButton);

		previewSentenceScrollPanel = new JScrollPane();
		previewSentenceScrollPanel.setBackground(Color.WHITE);
		previewSentenceScrollPanel.setBounds(428, 49, 327, 42);
		speechSettingsPanel.add(previewSentenceScrollPanel);
		previewSentenceScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		previewSentenceScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		previewSentenceScrollPanel.setBorder(null);

		previewSentenceTextArea = new JTextArea();
		previewSentenceTextArea.setBackground(Color.WHITE);
		previewSentenceScrollPanel.setViewportView(previewSentenceTextArea);
		previewSentenceTextArea.setToolTipText("Preview Sentence");
		previewSentenceTextArea.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		previewSentenceTextArea.setText("The quick brown fox jumped over the lazy dog");
		previewSentenceTextArea.setLineWrap(true);
		previewSentenceTextArea.setBorder(null);
		previewSentenceTextArea.setEditable(false);

		JLabel lblNewLabel_14 = new JLabel("Current phrase:");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_14.setBounds(432, 11, 168, 42);
		speechSettingsPanel.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Change current phrase:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_15.setBounds(425, 146, 286, 34);
		speechSettingsPanel.add(lblNewLabel_15);

		playPreviewButton = new JButton("Play Preview");
		playPreviewButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		playPreviewButton.setBackground(Color.WHITE);
		playPreviewButton.setBounds(521, 112, 106, 23);
		speechSettingsPanel.add(playPreviewButton);
		
		stopPreviewPlayerButton = new JButton("Stop Preview");
		stopPreviewPlayerButton.setBounds(637, 112, 118, 23);
		speechSettingsPanel.add(stopPreviewPlayerButton);
		


	}

	private void initAddTasksPanel() {
		addTasksInternalFrame = new JInternalFrame("Here you can create a MacroCommand");
		addTasksInternalFrame.setFrameIcon(
				new ImageIcon(MainView.class.getResource("/resources/icons/macro command frame icon.png")));
		addTasksInternalFrame.getContentPane().setBackground(Color.WHITE);
		appLayeredPane.setLayer(addTasksInternalFrame, 20);
		addTasksInternalFrame.setBounds(0, 0, 806, 699);
		appLayeredPane.add(addTasksInternalFrame);
		addTasksInternalFrame.getContentPane().setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("<html> <u>Commands  added to the macro</u></hmtl>");
		lblNewLabel_6.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(31, 41, 248, 53);
		addTasksInternalFrame.getContentPane().add(lblNewLabel_6);

		addedCommandsScrollPane = new JScrollPane();
		addedCommandsScrollPane.setBounds(31, 95, 321, 416);
		addTasksInternalFrame.getContentPane().add(addedCommandsScrollPane);

		addedCommandsDefaultList = new DefaultListModel<>();
		addedCommandsJList = new JList<>(addedCommandsDefaultList);
		addedCommandsJList.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addedCommandsJList.setForeground(Color.WHITE);
		addedCommandsJList.setBackground(Color.BLACK);

		addedCommandsScrollPane.setViewportView(addedCommandsJList);

		lblNewLabel_7 = new JLabel("<html> <u>Available commands</u></hmtl>");
		lblNewLabel_7.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(437, 53, 157, 29);
		addTasksInternalFrame.getContentPane().add(lblNewLabel_7);

		availableCommandsScrollPane = new JScrollPane();
		availableCommandsScrollPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		availableCommandsScrollPane.setBounds(437, 95, 321, 348);
		addTasksInternalFrame.getContentPane().add(availableCommandsScrollPane);

		availableCommandsDefaultList = new DefaultListModel<UserCommandsEnums>();
		Arrays.stream(UserCommandsEnums.values()).forEach(availableCommandsDefaultList::addElement);

		availableCommandsJList = new JList<UserCommandsEnums>(availableCommandsDefaultList);
		


		availableCommandsJList.setBackground(Color.BLACK);
		availableCommandsJList.setForeground(Color.WHITE);
		availableCommandsJList.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		availableCommandsScrollPane.setViewportView(availableCommandsJList);

		addCommandButton = new JButton("Add Command");
		addCommandButton.setBackground(Color.WHITE);
		addCommandButton.setBorder(new LineBorder(Color.BLACK, 1, true));
		addCommandButton.setBounds(615, 454, 135, 23);
		addTasksInternalFrame.getContentPane().add(addCommandButton);

		userMacroTitleTextArea = new JTextField();
		userMacroTitleTextArea.setBorder(new LineBorder(Color.BLACK, 2, true));
		userMacroTitleTextArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		userMacroTitleTextArea.setToolTipText("");
		userMacroTitleTextArea.setBounds(271, 11, 278, 29);
		addTasksInternalFrame.getContentPane().add(userMacroTitleTextArea);
		userMacroTitleTextArea.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("Enter a title for your macro:");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_10.setBounds(10, 15, 278, 20);
		addTasksInternalFrame.getContentPane().add(lblNewLabel_10);

		createUserMacroButton = new JButton("Create Macro");
		createUserMacroButton.setBackground(Color.WHITE);
		createUserMacroButton.setBorder(new LineBorder(Color.BLACK, 1, true));
		createUserMacroButton.setBounds(615, 608, 135, 23);
		addTasksInternalFrame.getContentPane().add(createUserMacroButton);

		clearAddedTasksButton = new JButton("Clear Commands");
		clearAddedTasksButton.setBackground(Color.WHITE);
		clearAddedTasksButton.setBorder(new LineBorder(Color.BLACK, 1, true));
		clearAddedTasksButton.setBounds(615, 488, 135, 23);
		addTasksInternalFrame.getContentPane().add(clearAddedTasksButton);

		showSameEncodeCommandAlreadyAddedCheckBox = new JCheckBox(
				"<html>Notify me if the same encode is already added</html>");
		showSameEncodeCommandAlreadyAddedCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		showSameEncodeCommandAlreadyAddedCheckBox.setOpaque(false);
		showSameEncodeCommandAlreadyAddedCheckBox.setSelected(true);
		showSameEncodeCommandAlreadyAddedCheckBox.setBounds(31, 518, 314, 39);
		addTasksInternalFrame.getContentPane().add(showSameEncodeCommandAlreadyAddedCheckBox);

		showSpeakCommandAlreadyAddedCheckBox = new JCheckBox(
				"<html>Notify me if a speak command is already added</html>");
		showSpeakCommandAlreadyAddedCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		showSpeakCommandAlreadyAddedCheckBox.setOpaque(false);
		showSpeakCommandAlreadyAddedCheckBox.setSelected(true);
		showSpeakCommandAlreadyAddedCheckBox.setBounds(31, 560, 314, 29);
		addTasksInternalFrame.getContentPane().add(showSpeakCommandAlreadyAddedCheckBox);

	}

	private void initMyTasksPanel() {

		myTasksInternalFrame = new JInternalFrame("Here you can see the created tasks");
		myTasksInternalFrame.setFrameIcon(new ImageIcon(MainView.class.getResource("/resources/icons/eye.png")));
		myTasksInternalFrame.getContentPane().setBackground(Color.WHITE);
		appLayeredPane.setLayer(myTasksInternalFrame, 21);
		myTasksInternalFrame.setBounds(0, 0, 806, 699);
		appLayeredPane.add(myTasksInternalFrame);
		myTasksInternalFrame.getContentPane().setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(53, 93, 646, 166);
		myTasksInternalFrame.getContentPane().add(scrollPane_1);

		createdMacrosDefaultList = new DefaultListModel<String>();

		createdMacrosJList = new JList<>(createdMacrosDefaultList);
		createdMacrosJList.setForeground(Color.WHITE);
		createdMacrosJList.setBackground(Color.BLACK);

		createdMacrosJList.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		scrollPane_1.setViewportView(createdMacrosJList);

		JLabel lblNewLabel_11 = new JLabel("<Html> <u>My Tasks </u></html>");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_11.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblNewLabel_11.setBounds(53, 49, 646, 33);
		myTasksInternalFrame.getContentPane().add(lblNewLabel_11);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		scrollPane_2.setBounds(53, 410, 646, 204);
		myTasksInternalFrame.getContentPane().add(scrollPane_2);

		createdMacrosContentsDefaultList = new DefaultListModel<UserCommandsEnums>();

		createdMacrosContentsList = new JList<UserCommandsEnums>(createdMacrosContentsDefaultList);
		createdMacrosContentsList.setForeground(Color.WHITE);
		createdMacrosContentsList.setBackground(new Color(0, 0, 0));
		createdMacrosContentsList.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		scrollPane_2.setViewportView(createdMacrosContentsList);

		lblNewLabel_12 = new JLabel("<Html> <u>Commands</u></html>");
		lblNewLabel_12.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_12.setBounds(53, 358, 646, 41);
		myTasksInternalFrame.getContentPane().add(lblNewLabel_12);

		runSelectedCommandButton = new JButton("Run Seleced Macro");
		runSelectedCommandButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		runSelectedCommandButton.setBackground(Color.WHITE);
		runSelectedCommandButton.setBounds(53, 270, 130, 33);
		myTasksInternalFrame.getContentPane().add(runSelectedCommandButton);

		changeWindowCheckBox = new JCheckBox("Change window after executing a command");
		changeWindowCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		changeWindowCheckBox.setOpaque(false);
		changeWindowCheckBox.setBounds(53, 310, 337, 23);
		myTasksInternalFrame.getContentPane().add(changeWindowCheckBox);
		
		aboutInternalFrame = new JInternalFrame("About");
		aboutInternalFrame.setFrameIcon(new ImageIcon(MainView.class.getResource("/resources/icons/help icon.png")));
		aboutInternalFrame.getContentPane().setBackground(Color.BLACK);
		appLayeredPane.setLayer(aboutInternalFrame, 23);
		aboutInternalFrame.setBounds(0, 0, 806, 699);
		appLayeredPane.add(aboutInternalFrame);
		aboutInternalFrame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Version: 1.0");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 217, 34);
		aboutInternalFrame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_16 = new JLabel("Release Date: 21/5/2020");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_16.setBounds(10, 45, 217, 34);
		aboutInternalFrame.getContentPane().add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Author: Thanasis Konstantinos");
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_17.setBounds(10, 80, 217, 34);
		aboutInternalFrame.getContentPane().add(lblNewLabel_17);
		aboutInternalFrame.setVisible(true);
		myTasksInternalFrame.setVisible(true);
		addTasksInternalFrame.setVisible(true);
		

	}

	
	
	private void initFields() {
		previewPlayer = new LabeledDocument(new TTSApi(), "The quick brown fox jumped over the lazy dog",
				pitchValueLabel, rateValueLabel, volumeValueLabel);
		doc = new LabeledDocument(new TTSApi(), "", docPitchLabel, docWPMLabel, docVolumeLabel);
		userCommandsFactory = new UserCommandsFactory();
		userMacroHandler = new UserMacroHandler(userCommandsFactory);
		recorder = new CommandRecorder();
		textEditorUtilities = new TextAreaUtilities(textEditorInternalFrame, textEditorTextArea, getDocument());
		commandResult = new AtomicBoolean(false);
		
	}

	private void initMacros() {

		//=============================
		//========MAIN TOOLBAR MACROS
		//=============================
		toTextEditorToolbarButtonCommands = new MacroCommand(recorder);
		toMyTasksToolbarButtonCommands = new MacroCommand(recorder);
		toAddTasksToolbarButtonCommands = new MacroCommand(recorder);
		
		toAboutToolbarButtonCommands = new MacroCommand(recorder);
		changeRecorderStatusToolbarButtonCommands = new MacroCommand();
		playRecordingToolbarButtonCommands = new MacroCommand();

		
		
		//=============================
		//==========TEXT TOOLBAR MACROS
		//=============================
	
		createDocumentMacroCommands = new MacroCommand(recorder);
		openFileMacroCommands = new MacroCommand(recorder);
		atbashDocumentMacroCommands = new MacroCommand(recorder);
		rotDocumentMacroCommands = new MacroCommand(recorder);
		saveFileMacroCommands = new MacroCommand(recorder);
		saveFileAsMacroCommands = new MacroCommand(recorder);

		
		
		
		//=====================================
		//==========TEXT AREA POPUP MENU MACROS
		//=====================================
		atbashHighlightedMacroCommands = new MacroCommand();
		rotHighlightedMacroCommands = new MacroCommand();

		
		
		//===============================
		//==========SPEECH TOOLBAR MACROS
		//===============================
		speakDocumentMacroCommands = new MacroCommand(recorder);
		customSpeakDocumentMacroCommands = new MacroCommand(recorder);
		customSpeakLineMacroCommands = new MacroCommand(recorder);
		toSpeechSettingMacroCommands = new MacroCommand(recorder);
		stopDocumentSpeakingMacroCommands = new MacroCommand(recorder);
	

		
		//=======================================
		//==========SPEECH SETTING TOOLBAR MACROS
		//=======================================
		decreasePitchMacroCommands = new MacroCommand(recorder);
		increasePitchMacroCommands = new MacroCommand(recorder);
		decreaseRateMacroCommands = new MacroCommand(recorder);
		increaseRateMacroCommands = new MacroCommand(recorder);
		decreaseVolumeMacroCommands = new MacroCommand(recorder);
		increaseVolumeMacroCommands = new MacroCommand(recorder);
		playPreviewButtonCommands = new MacroCommand(recorder);
		applyButtonCommands = new MacroCommand(recorder);
		resetValuesCommands = new MacroCommand(recorder);
		toTextEditorButtonCommands = new MacroCommand(recorder);
		updatePreviewMacroCommands = new MacroCommand(recorder);
		stopPreviewPlayerMacroCommands = new MacroCommand(recorder);

		
		//==========================
		//==========ADD TASKS MACROS
		//==========================
		
		addTaskMacroCommands = new MacroCommand();
		clearAddedCommandsMacroCommands = new MacroCommand();
		createUserMacroCommands = new MacroCommand();

		//=========================
		//==========MY TASKS MACROS
		//=========================
		runSelectedCommandMacroCommands = new MacroCommand();

	}

	private void initCommands() {

		
		//=====================================
		//========COMMANDS THAT CHANGE THE VIEW
		//====================================
			//change the app
		switchToTextEditorCommand = new PanelSwitchCommand(appLayeredPane, textEditorInternalFrame, textEditorTextArea);
		switchToAddTasksCommand = new PanelSwitchCommand(appLayeredPane, addTasksInternalFrame, userMacroTitleTextArea);
		switchToMyTasksCommand = new PanelSwitchCommand(appLayeredPane, myTasksInternalFrame);
		switchToAboutCommand = new PanelSwitchCommand(appLayeredPane,aboutInternalFrame);		
		
			//change inside the app
		switchToTextEditorMainPanelCommand = new PanelSwitchCommand(textEditorLayeredPane, textEditorMainPanel,textEditorTextArea);
		switchToSpeechSettingsPanel = new PanelSwitchCommand(textEditorLayeredPane, speechSettingsPanel);

		
		
		

		//=====================================
		//==========OTHER MAIN TOOLBAR COMMANDS
		//=====================================
		
		changeRecorderStatusCommand = new ChangeRecorderStatusCommand(recorder,
				() -> changeRecorderStatusToolbarButton.isSelected());
		playRecorderCommand = new PlayRecorderCommand(recorder);


		//===============================
		//==========TEXT EDITING COMMANDS
		//===============================
		
		
		createDocumentCommand = new CreateDocumentCommand(textEditorUtilities, this::getTitle, this::getAuthor);
		createDocumentAfterGui = new RunAfterConfirmationCommand(createDocumentCommand, this::showCreateFileMessage);

		openFileCommand = new OpenFileCommand(textEditorUtilities, () -> file);
		
			//open a file if a proper string is used and user presses ok on the gui
		openFileAfterConfirmationCommand = new RunAfterConfirmationCommand(openFileCommand, this::getFileLocation);

		atBashDocumentCommand = new EncodeTextAreaCommand(textEditorTextArea, () -> EncodeEnums.ATBASH);

		rotDocumentCommand = new EncodeTextAreaCommand(textEditorTextArea, () -> EncodeEnums.ROT13);

		saveFileAsCommand = new SaveAsCommand(textEditorUtilities, this::getSaveLocation, commandResult);
		
		saveFileCommand = new SaveCommand(textEditorUtilities, commandResult);
		
			//uses the save command. if the save couldn't save the file due to no preselected file
			//use the saveAs command
		saveAsAfterSaveCommand = new RunAfterConfirmationCommand(saveFileAsCommand, () -> !commandResult.get());
		
			//this command is added before any transformation of the text that we want 
			//the user to be prompted to save before making a change
			//this commands run's only if the current displayed text is not saved
		promtSaveCommand = new RunAfterConfirmationCommand(saveAsAfterSaveCommand, () -> {
			if (isSaved()) { // if its already saved don't ask the user to save
				return false;
			} else {
				return showWantToSaveMessage(); // if not ask for permision to save
			}

		});

		
		//======================================
		//==========TEXT ARE POPUP MENU COMMANDS
		//======================================
		atbashHighlightedCommand = new EncodeSelectedCommand(textEditorUtilities, EncodeEnums.ATBASH);
		rotHighlightedCommand = new EncodeSelectedCommand(textEditorUtilities, EncodeEnums.ROT13);		
		
		
		//=================================
		//==========SPEECH RELATED COMMANDS
		//=================================
		
			//used to keep in sync the text area and the document's text
		updateDocumentCommand = new UpdateDocumentCommand(textEditorTextArea, this::getDocument);

		speakDocumentCommand = new SpeakDocumentCommand(this::getDocument);
		customSpeakDocumentCommand = new CustomSpeakDocumentCommand(this::getDocument,
				() -> reverseCheckBox.isSelected());
		customSpeakLineCommand = new CustomSpeakLineCommand(this::getDocument, () -> lineSpinner.getLine(),
				() -> reverseCheckBox.isSelected());

		speakPreviewPlayerCommand = new SpeakDocumentCommand(() -> previewPlayer);
		
			//makes the text area show the speaked text if the Document is speaking
		updateTextAreaCommand = new UpdateTextAreaCommand(textEditorTextArea, this::getDocument);

		stopDocumentSpeakingCommand = new StopSpeakMethodCommand(this::getDocument);
		stopPreviewPlayerSpeakingCommand = new StopSpeakMethodCommand(() -> previewPlayer);
		
	

		//=============================================
		//==========VOICE PREFERENCSES RELATED COMMANDS
		//=============================================
		decreasePitchCommand = new TuneVoiceCommand((f) -> previewPlayer.setPitch(f), () -> previewPlayer.getPitch(),
				(-20), VoiceValuesEnum.PitchMinimumValue.getValue(), VoiceValuesEnum.PitchMaximumValue.getValue());

		increasePitchCommand = new TuneVoiceCommand((f) -> previewPlayer.setPitch(f), () -> previewPlayer.getPitch(),
				20, VoiceValuesEnum.PitchMinimumValue.getValue(), VoiceValuesEnum.PitchMaximumValue.getValue());

		decreaseRateCommand = new TuneVoiceCommand((f) -> previewPlayer.setRate(f), () -> previewPlayer.getRate(), -20,
				VoiceValuesEnum.RateMinimumValue.getValue(), VoiceValuesEnum.RateMaximumValue.getValue());

		increaseRateCommand = new TuneVoiceCommand((f) -> previewPlayer.setRate(f), () -> previewPlayer.getRate(), 20,
				VoiceValuesEnum.RateMinimumValue.getValue(), VoiceValuesEnum.RateMaximumValue.getValue());

		decreaseVolumeCommand = new TuneVoiceCommand((f) -> previewPlayer.setVolume(f), () -> previewPlayer.getVolume(),
				(float) -0.1, VoiceValuesEnum.VolumeMinimumAmount.getValue(),
				VoiceValuesEnum.VolumeMaximumValue.getValue());

		increaseVolumeCommand = new TuneVoiceCommand((f) -> previewPlayer.setVolume(f), () -> previewPlayer.getVolume(),
				(float) 0.1, VoiceValuesEnum.VolumeMinimumAmount.getValue(),
				VoiceValuesEnum.VolumeMaximumValue.getValue());

		copyVoicePreferencesCommand = new CopyVoicePreferencesCommand(() -> previewPlayer, this::getDocument);
		resetVoiceValuesCommand = new SetVoiceValuesCommand(() -> previewPlayer,
				VoiceValuesEnum.PitchStartingValue.getValue(), VoiceValuesEnum.RateStartingValue.getValue(),
				VoiceValuesEnum.VolumeStartingValue.getValue());



		//=================================================
		//==========COMMANDS RELATED TO USER MACRO CREATION
		//=================================================
		
		
		addTaskCommand = new AddTaskCommand(userMacroHandler, () -> availableCommandsJList.getSelectedValue());
		addTaskAfterConfirmation = new RunAfterConfirmationCommand(addTaskCommand, this::checkCommand);

		createUserMacroCommand = new CreateUserMacroCommand(textAreaPopupMenu, () -> userMacroTitleTextArea.getText(),
				userMacroHandler::generateMacro);

		refreshAddedCommandsListCommand = new RefreshDefaultListCommand<UserCommandsEnums>(addedCommandsDefaultList,
				() -> userMacroHandler.getCommandsAddedList());

		//==================================================================
		//===========COMMANDS RELATED TO VIEWING AND USING THE MACROS CREATED
		//===================================================================
		refreshCreatedMacrosListCommand = new RefreshDefaultListCommand<String>(createdMacrosDefaultList,
				() -> userMacroHandler.getMacrosCreatedList());

		refreshCreatedMacrosContentstListCommand = new RefreshDefaultListCommand<UserCommandsEnums>(
				createdMacrosContentsDefaultList, this::getMacrosContents);

		runSelectedMacroCommand = new RunSelectedMacroCommand(userMacroHandler,
				() -> createdMacrosJList.getSelectedIndex());
		switchToEditorAfterConfirmation = new RunAfterConfirmationCommand(switchToTextEditorCommand::execute,
				() -> changeWindowCheckBox.isSelected());


	}
	
	
	

	private void populateCommandFactory() {
		//internaly every user action is implemented by a series of commands aka a macro command
		//assing those macros to a UserCommandsEnums so the factory provides the same object
		//instead of creating an identical
		userCommandsFactory.addMacroCommand(UserCommandsEnums.OpenFile, openFileMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.CreateDocument, createDocumentMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.AtBash, atbashDocumentMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.Rot13, rotDocumentMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.Save, saveFileMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.SaveAs, saveFileAsMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.Speak, speakDocumentMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.CustomSpeak, customSpeakDocumentMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.SpeakLine, customSpeakLineMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.IncreasePitch, increasePitchMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.DecreasePitch, decreasePitchMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.IncreaseRate, increaseRateMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.DecreaseRate, decreaseRateMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.IncreaseVolume, increaseVolumeMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.DecreaseVolume, decreaseVolumeMacroCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.ApplyPreferences, applyButtonCommands);
		userCommandsFactory.addMacroCommand(UserCommandsEnums.ResetVoiceValues, resetValuesCommands);
		
	}

	private void assingCommandsToMacros() {

		//=========================
		//==========TOOLBAR MACROS
		//=========================
		changeRecorderStatusToolbarButtonCommands.addExternal(changeRecorderStatusCommand);
		playRecordingToolbarButtonCommands.addExternal(playRecorderCommand);
		toTextEditorToolbarButtonCommands.addInternal(switchToTextEditorCommand)
				.addInternal(switchToTextEditorMainPanelCommand);
		toAddTasksToolbarButtonCommands.addInternal(switchToAddTasksCommand);
		toMyTasksToolbarButtonCommands.addInternal(refreshCreatedMacrosListCommand).addInternal(switchToMyTasksCommand);
		toAboutToolbarButtonCommands.addInternal(switchToAboutCommand);
		
		
		//=============================
		//==========TEXT TOOLBAR MACROS
		//=============================		
		createDocumentMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(promtSaveCommand)
				.addExternal(createDocumentAfterGui);

		openFileMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(promtSaveCommand)
				.addExternal(openFileAfterConfirmationCommand);
		atbashDocumentMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(atBashDocumentCommand);
		rotDocumentMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(rotDocumentCommand);
		saveFileAsMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(saveFileAsCommand)
				.addExternal(() -> showSavedStatus());
		saveFileMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(saveFileCommand)
				.addExternal(saveAsAfterSaveCommand).addExternal(this::showSavedStatus);

		
		//=====================================
		//==========TEXT AREA POPUP MENU MACROS
		//=====================================
		
		atbashHighlightedMacroCommands.addInternal(switchToTextEditorMainPanelCommand)
				.addExternal(atbashHighlightedCommand);
		rotHighlightedMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addExternal(rotHighlightedCommand);

		
		
		//===============================
		//==========SPEECH TOOLBAR MACROS
		//===============================
		speakDocumentMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addInternal(updateDocumentCommand)
				.addExternal(speakDocumentCommand).addExternal(updateTextAreaCommand::execute);
		customSpeakDocumentMacroCommands.addInternal(switchToTextEditorMainPanelCommand)
				.addInternal(updateDocumentCommand).addExternal(customSpeakDocumentCommand)
				.addExternal(updateTextAreaCommand::execute);

		customSpeakLineMacroCommands.addInternal(switchToTextEditorMainPanelCommand).addInternal(updateDocumentCommand)
				.addExternal(customSpeakLineCommand).addExternal(updateTextAreaCommand::execute);
		toSpeechSettingMacroCommands.addInternal(switchToSpeechSettingsPanel);

		stopDocumentSpeakingMacroCommands.addInternal(switchToTextEditorMainPanelCommand)
				.addExternal(stopDocumentSpeakingCommand);

		
		
		//===============================
		//==========PEECH SETTINGS MACROS
		//===============================
		
		decreasePitchMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(decreasePitchCommand);
		increasePitchMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(increasePitchCommand);
		decreaseRateMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(decreaseRateCommand);
		increaseRateMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(increaseRateCommand);
		decreaseVolumeMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(decreaseVolumeCommand);
		increaseVolumeMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(increaseVolumeCommand);

		playPreviewButtonCommands.addInternal(switchToSpeechSettingsPanel).addExternal(speakPreviewPlayerCommand);
		applyButtonCommands.addInternal(switchToSpeechSettingsPanel).addExternal(copyVoicePreferencesCommand)
				.addExternal(() -> showApplyCompletedMessage());
		resetValuesCommands.addInternal(switchToSpeechSettingsPanel).addExternal(resetVoiceValuesCommand);

		toTextEditorButtonCommands.addInternal(switchToTextEditorMainPanelCommand)
				.addExternal(stopPreviewPlayerSpeakingCommand);

		updatePreviewMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(() -> {
				
				//if the previou text is not only white spaces
			if (!previewTextField.getText().strip().equals("")) {
				
				//change the previw sentence
				previewPlayer.setText(previewTextField.getText());
				
				//update the text area displaying the sentence
				previewSentenceTextArea.setText(previewTextField.getText());
				
				//clear the text area
				previewTextField.setText("");
			}
		});
		stopPreviewPlayerMacroCommands.addInternal(switchToSpeechSettingsPanel).addExternal(stopPreviewPlayerSpeakingCommand);
		
		//==========================
		//==========ADD TASKS MACROS
		//==========================
		addTaskMacroCommands.addInternal(switchToAddTasksCommand).addExternal(addTaskAfterConfirmation)
				.addExternal(refreshAddedCommandsListCommand::execute);

		createUserMacroCommands.addInternal(switchToAddTasksCommand).addExternal(createUserMacroCommand)
				.addExternal(() -> userMacroHandler.clear()).addExternal(refreshAddedCommandsListCommand::execute)
				.addExternal(() -> userMacroTitleTextArea.setText(""));

		clearAddedCommandsMacroCommands.addInternal(switchToAddTasksCommand).addExternal(() -> userMacroHandler.clear())
				.addExternal(refreshAddedCommandsListCommand::execute);
			
		
		//=========================
		//==========MY TASKS MACROS
		//=========================
		
		runSelectedCommandMacroCommands.addInternal(switchToMyTasksCommand).addExternal(switchToEditorAfterConfirmation)
				.addExternal(runSelectedMacroCommand);

	}

	private void createEvents() {
		
		
		//============================================================
		//============ASSIGNING MACROS TO THE SPECIFIC BUTTONS========
		//============================================================
		
		//===============================
		//==========MAIN TOOLBAR BUTTONS
		//===============================
		toTextEditorButton.addActionListener(toTextEditorToolbarButtonCommands);
		toMyTasksToolbarButton.addActionListener(toMyTasksToolbarButtonCommands);
		toAddTasksToolbarButton.addActionListener(toAddTasksToolbarButtonCommands);
		toAboutToolbarButton.addActionListener(toAboutToolbarButtonCommands);
		changeRecorderStatusToolbarButton.addActionListener(changeRecorderStatusToolbarButtonCommands);
		playRecordingToolbarButton.addActionListener(playRecordingToolbarButtonCommands);

		

		//=============================
		//==========TEXT EDITOR TOOLBAR
		//=============================
		createDocumentButton.addActionListener(createDocumentMacroCommands);
		openFileButton.addActionListener(openFileMacroCommands);
		atbashTextButton.addActionListener(atbashDocumentMacroCommands);
		rotTextButton.addActionListener(rotDocumentMacroCommands);
		saveFileButton.addActionListener(saveFileMacroCommands);
		saveFileAsButton.addActionListener(saveFileAsMacroCommands);

		//================================
		//==========TEXT EDITOR POPUP MENU
		//================================
		atBashHighLightedMenuItem.addActionListener(atbashHighlightedMacroCommands);
		rotHighlightedMenuItem.addActionListener(rotHighlightedMacroCommands);
		speechSettingMenuItem.addActionListener(toSpeechSettingMacroCommands);

		
		
		//========================
		//==========SPEECH TOOLBAR
		//========================
		speakDocumentButton.addActionListener(speakDocumentMacroCommands);
		customSpeakDocumentButton.addActionListener(customSpeakDocumentMacroCommands);
		speakLineButton.addActionListener(customSpeakLineMacroCommands);
		speechSettingsButton.addActionListener(toSpeechSettingMacroCommands);
		stopDocumentSpeakButton.addActionListener(stopDocumentSpeakingMacroCommands);

		
		
		
		//=======================
		//=========SPEECH SETTINGS 
		//========================
		decreasePitchButton.addActionListener(decreasePitchMacroCommands);
		increasePitchButton.addActionListener(increasePitchMacroCommands);
		decreaseRateButton.addActionListener(decreaseRateMacroCommands);
		increaseRateButton.addActionListener(increaseRateMacroCommands);
		decreaseVolumeButton.addActionListener(decreaseVolumeMacroCommands);
		increaseVolumeButton.addActionListener(increaseVolumeMacroCommands);

		playPreviewButton.addActionListener(playPreviewButtonCommands);
		applyButton.addActionListener(applyButtonCommands);
		resetValuesButton.addActionListener(resetValuesCommands);
		toEditorButton.addActionListener(toTextEditorButtonCommands);
		stopPreviewPlayerButton.addActionListener(stopPreviewPlayerMacroCommands);
		
		
		
		//===================
		//==========ADD TASKS
		//===================
		
		addCommandButton.addActionListener(addTaskMacroCommands);
		createUserMacroButton.addActionListener(createUserMacroCommands);
		clearAddedTasksButton.addActionListener(clearAddedCommandsMacroCommands);
		updatePreviewButton.addActionListener(updatePreviewMacroCommands);
		
		
		
		//===================
		//==========MY TASKS
		//===================

		runSelectedCommandButton.addActionListener(runSelectedCommandMacroCommands);

		
		
		//====================================================================================
		
		//===============================
		//==========OTHER EVENTS========
		//===============================
			
		
		
		//changes the encode of the document
		ciphersComboBox_1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				doc.setEncode(EncodeFactory.getEncodeMethod((EncodeEnums) ciphersComboBox_1.getSelectedItem()));
			}
		});
		
		
		
		
		//a scroll pane without the bars 
		//but scrollable through mouse wheel
		previewSentenceScrollPanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				JScrollBar scrollBar = previewSentenceScrollPanel.getVerticalScrollBar();
				int previousValue = scrollBar.getValue();
				int addAmount = 0;
				if (e.getWheelRotation() > 0) {
					addAmount = 6;
				} else {
					addAmount = -6;
				}
				scrollBar.setValue(previousValue + addAmount);
			}
		});
		
		
		
		
		
		
		//JList moves with up and down arrow and performs action upon double click or pressing enter
		availableCommandsJList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				  if (e.getClickCount() == 2) {
					  addCommandButton.doClick();
				  }
				
			}
		});
		
		availableCommandsJList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode()==10){
					 addCommandButton.doClick();
					 
				}else if(e.getExtendedKeyCode()==38) {
					selectPrevious();
					
				}else if(e.getExtendedKeyCode()==40) {
					selectNext();
				}
				
				availableCommandsJList.requestFocusInWindow();
			}
			
			
			void selectPrevious(){
				int currentValue = availableCommandsJList.getSelectedIndex();
				if(currentValue>=1) {
					
					availableCommandsJList.setSelectedIndex(currentValue);
					
				}
			}
			
			void selectNext() {
				int currentValue = availableCommandsJList.getSelectedIndex();
				if(currentValue<availableCommandsDefaultList.size()-1) {
					
					
					availableCommandsJList.setSelectedIndex(currentValue);
				}
			}
			
			
			
		});

		
		
		
		
		//refresing a JList upon selecting another object in another JLIst
		createdMacrosJList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int selectedIndex = createdMacrosJList.getSelectedIndex();
				if (selectedIndex >= 0) {

					refreshCreatedMacrosContentstListCommand.execute();

				}

			}
		});

		
		//change icon on the recorder button based on it's enabling or not
		changeRecorderStatusToolbarButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (changeRecorderStatusToolbarButton.isSelected()) {
					changeRecorderStatusToolbarButton
							.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/rec icon.png")));
					changeRecorderStatusToolbarButton.setText("Recording");
				} else {
					changeRecorderStatusToolbarButton
							.setIcon(new ImageIcon(MainView.class.getResource("/resources/icons/stop rec icon.png")));
					changeRecorderStatusToolbarButton.setText("Record");
				}
			}
		});

	}

	// ================METHODS==============

	/**
	 * 
	 * @return The current {@link Document}
	 */
	private Document getDocument() {
		return doc;
	}

	/**
	 * This method pops up a custom JDialog to prompt the user to enter an author and a title for the creation of the A {@link Document}
	 * @return True if ok was pressed on the JDialog
	 */
	public boolean showCreateFileMessage() {

		//TODO: extract class
		JTextField titleTextField = new JTextField();
		JTextField authorTextField = new JTextField();
		final JComponent[] inputs = new JComponent[] { new JLabel("Title:"), titleTextField, new JLabel("Author:"),
				authorTextField };

		// =============================================
		// A trick used to request focus in the text area
		// ==============================================
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
					
				}
				titleTextField.requestFocusInWindow();

			}
		}).start();

		// ===================================================

		
		int result = JOptionPane.showConfirmDialog(null, inputs, "Enter a Title and an Author", DISPOSE_ON_CLOSE,
				JOptionPane.YES_NO_OPTION, new ImageIcon(MainView.class.getResource("/resources/icons/author dialog.png")));
		if (result == JOptionPane.YES_OPTION) {
			title = titleTextField.getText();
			author = authorTextField.getText();
			return true;
		}
		return false;

	}

	// returns the author to be set as the author of a new document
	public String getAuthor() {
		return author;
	}

	// returns the title to be set as the title of a new document
	public String getTitle() {
		return title;
	}

	//returs true if the text area's current text was saved 
	public boolean isSaved() {
		return textEditorUtilities.isSaved();
	}

	
	//get's the save location from the user throught a FileChooser
	public String getSaveLocation() {
		String selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a folder");
		int response = fileChooser.showSaveDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			String tempPath = fileChooser.getSelectedFile().getAbsolutePath();
			if (!tempPath.contentEquals("")) {
				selectedFile = tempPath;

				while (!selectedFile.endsWith(".txt")) {
					selectedFile += ".txt";
				}

			}
		}
		return selectedFile;
	}



	//provides info on the user if the file was saved succcefully
	public void showSavedStatus() {
		if (commandResult.get()) {
			JOptionPane.showMessageDialog(null, "The file was saved succefully", "Save operation finished",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//asks for permision to save the current text area's text
	private Boolean showWantToSaveMessage() {
		int answer = JOptionPane.showConfirmDialog(null, "Do you want to save before proceeding?", "Save your work",
				JOptionPane.YES_NO_OPTION);
		return answer == JOptionPane.YES_OPTION ? true : false;
	}

	//asks the user for a path to open a txt from
	private boolean getFileLocation() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a File");
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// selectedFile new value
			file = fileChooser.getSelectedFile();
			return true;

		}
		return false;
	}

	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	
	//shows message about adding the same kind of command
	private boolean checkCommand() {
		if (showSpeakCommandAlreadyAddedCheckBox.isSelected()) {

			if (UserCommandsEnums.isSpeakCommand(availableCommandsJList.getSelectedValue())
					&& userMacroHandler.hasSpeakCommand()) {
				return showWantToAddOntherSpeakMessage();
			}

		}
		if (showSameEncodeCommandAlreadyAddedCheckBox.isSelected()) {

			if (availableCommandsJList.getSelectedValue().equals(UserCommandsEnums.AtBash)
					&& userMacroHandler.hasAtbashEcode()) {

				return showWantToAddSameEncodeMessage(UserCommandsEnums.AtBash);
			} else if (availableCommandsJList.getSelectedValue().equals(UserCommandsEnums.Rot13)
					&& userMacroHandler.hasRotEncode()) {

				return showWantToAddSameEncodeMessage(UserCommandsEnums.Rot13);

			}

		}
		return true;

	}

	
	//specified message for another speak command
	private boolean showWantToAddOntherSpeakMessage() {

		int result = JOptionPane.showConfirmDialog(null,
				"There is already a speak command added. This command will be added in a queue for narrating.\nDo you still want to add this command?",
				"Speak command already added", JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION ? true : false;

	}

	//specified message for another same encode command
	private boolean showWantToAddSameEncodeMessage(UserCommandsEnums encodeCommand) {
		String encoding = "";
		if (encodeCommand.equals(UserCommandsEnums.AtBash)) {
			encoding = EncodeEnums.ATBASH.toString();
		} else if (encodeCommand.equals(UserCommandsEnums.Rot13)) {
			encoding = EncodeEnums.ROT13.toString();
		}
		int result = JOptionPane.showConfirmDialog(null, encoding
				+ " encoding is already added. This will revert any changes.\nDo you still want to add this command?",
				"Same encoding command already added", JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION ? true : false;
	}

 
	private void showApplyCompletedMessage() {
		JOptionPane.showMessageDialog(null, "Voice prefereces have been set!", "Process completed",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private List<UserCommandsEnums> getMacrosContents() {
		return userMacroHandler.getMacro(createdMacrosJList.getSelectedIndex()).getCommands();
	}
}
