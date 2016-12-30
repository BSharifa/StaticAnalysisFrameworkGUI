package easeStaticAnalysis;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class easeAmandroid {

	private JFrame frame;
	private JTree tree;
	String filePath;
	private DefaultListModel<String> model;
	private ClosableTabbedPane tabbedPane;
	private JFileChooser dialog = new JFileChooser(
			System.getProperty("user.dir"));
	DefaultListModel<String> listModel;
	DefaultMutableTreeNode top = new DefaultMutableTreeNode(
			"Amandroid Analysis Result");
	String Jtreevar;
	Thread t;
	private JTextField findField;
	private JButton findButton;
	private JTextArea textArea;
	private int position = 0;
	Date date = new Date();
	long time;
	Timestamp ts;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					easeAmandroid window = new easeAmandroid();
					window.frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		});

	}

	public easeAmandroid() {

		initialize();

	}

	private void initialize() {

		frame = new JFrame("EaseAmandroid");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMinimumSize(new Dimension((int) (screenSize.width / 4),
				(int) (screenSize.height / 4)));
		int width = (int) (screenSize.width / 1.1);
		int height = (int) (screenSize.height / 1.1);
		frame.setSize(width, height);
		frame.setLocation((screenSize.width / 2) - (width / 2),
				(screenSize.height / 2) - (height / 2));

		Font f = new Font("Serif", Font.BOLD, 14);
		Font f1 = new Font("Serif", Font.PLAIN, 14);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(f);
		menuBar.add(mnFile);

		// tree model to open analysis results in GUI
		tree = new JTree(top);
		JScrollPane scrollPaneTree = new JScrollPane(tree);
		scrollPaneTree.setPreferredSize(new Dimension(300, 40050));
		scrollPaneTree.setMinimumSize(new Dimension(200, 700));
		frame.getContentPane().add(scrollPaneTree, BorderLayout.EAST);

		// jFileChooser for opening files from directory
		JMenuItem mntmOpenFiles = new JMenuItem("Open Amandroid Result Files..");
		mntmOpenFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mntmOpenFiles) {

					final JFileChooser jFileChooser = new JFileChooser();
					jFileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = jFileChooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = jFileChooser.getSelectedFile();
						tree.setModel(new FileSystemModel(file));
					}
				}
			}
		});
		mnFile.add(mntmOpenFiles);

		// Store directory path of opened apk files in a jlist
		JList<String> l = new JList<>(model = new DefaultListModel<String>());
		JScrollPane scrollPane1 = new JScrollPane(l);
		scrollPane1.setPreferredSize(new Dimension(230, 700));

		// Works like a console in GUI
		JTextArea textAreaCommandLinesOutput = new JTextArea(110, 60);
		JScrollPane scrollerTextArea = new JScrollPane(
				textAreaCommandLinesOutput);
		textAreaCommandLinesOutput.setEditable(false);

		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.LIGHT_GRAY);
		frame.add(toolBar, BorderLayout.NORTH);

		MyButton btnAdd = new MyButton("Open Apk Files") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnAdd.setBackground(Color.blue);
		btnAdd.setFont(f);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBorder(new LineBorder(Color.DARK_GRAY));
		btnAdd.setOpaque(true);
		btnAdd.setFocusPainted(false);

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.showOpenDialog(null);
				File[] selectedFiles = fc.getSelectedFiles();
				for (File f : selectedFiles) {
					model.addElement(f.getName());
					filePath = f.getAbsolutePath();
				}

			}

		});
		toolBar.add(btnAdd);
		toolBar.addSeparator();

		JButton btnRunAmandroid = new JButton("Run Amandroid") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnRunAmandroid.setBackground(Color.blue);
		btnRunAmandroid.setFont(f1);
		btnRunAmandroid.setForeground(Color.WHITE);
		btnRunAmandroid.setBorder(new LineBorder(Color.DARK_GRAY));
		btnRunAmandroid.setOpaque(true);
		btnRunAmandroid.setFont(f);
		btnRunAmandroid.setFocusPainted(false);

		btnRunAmandroid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaCommandLinesOutput.setText(null);
				String command[] = { "/Users/bsharifa/Documents/Sireum/sireum",
						"amandroid" };
				try {
					Process process = Runtime.getRuntime().exec(command);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						textAreaCommandLinesOutput.append(line);
						textAreaCommandLinesOutput.append("\n");
					}
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnRunAmandroid);
		toolBar.addSeparator();

		JButton btnRunTaintanalysis = new JButton("Taint Analysis") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnRunTaintanalysis.setBackground(Color.blue);
		btnRunTaintanalysis.setFont(f1);
		btnRunTaintanalysis.setForeground(Color.WHITE);
		btnRunTaintanalysis.setBorder(new LineBorder(Color.DARK_GRAY));
		btnRunTaintanalysis.setOpaque(true);
		btnRunTaintanalysis.setFont(f);
		btnRunTaintanalysis.setFocusPainted(false);

		btnRunTaintanalysis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				t = new Thread(new Runnable() {

					@Override
					public void run() {
						String fileName = null;

						if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

							fileName = dialog.getSelectedFile()
									.getAbsolutePath();
						textAreaCommandLinesOutput.setText(null);
						String apkFilePath = filePath;
						String outputFilePath = fileName;
						String command1[] = new String[8];
						command1[0] = "/Users/bsharifa/Documents/Sireum/sireum";
						command1[1] = "amandroid";
						command1[2] = "taintAnalysis";
						command1[3] = "-m";
						command1[4] = "4";
						command1[5] = "-o";
						command1[6] = outputFilePath;
						command1[7] = apkFilePath;

						try {

							ProcessBuilder builder = new ProcessBuilder(
									command1);
							builder.redirectErrorStream(true);
							Process process = builder.start();

							BufferedReader reader = new BufferedReader(
									new InputStreamReader(process
											.getInputStream()));
							String line1;

							JTextAreaOutputStream out = new JTextAreaOutputStream(
									textAreaCommandLinesOutput);
							System.setOut(new PrintStream(out));

							while ((line1 = reader.readLine()) != null) {

								System.out.println(line1);

							}
							time = date.getTime();
							ts = new Timestamp(time);
							System.out.println("Current Time Stamp: " + ts);

							reader.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				t.start();
			}
		});
		toolBar.add(btnRunTaintanalysis);
		toolBar.addSeparator();

		JButton btnRunDecompile = new JButton("Decompile") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnRunDecompile.setBackground(Color.blue);
		btnRunDecompile.setFont(f1);
		btnRunDecompile.setForeground(Color.WHITE);
		btnRunDecompile.setBorder(new LineBorder(Color.DARK_GRAY));
		btnRunDecompile.setOpaque(true);
		btnRunDecompile.setFont(f);
		btnRunDecompile.setFocusPainted(false);

		btnRunDecompile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {

				String fileName = null;
				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

					fileName = dialog.getSelectedFile().getAbsolutePath();
				textAreaCommandLinesOutput.setText(null);
				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command2[] = new String[5];
				command2[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command2[1] = "amandroid";
				command2[2] = "decompile";
				command2[3] = apkFilePath;
				command2[4] = outputFilePath;

				try {
					ProcessBuilder builder = new ProcessBuilder(command2);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader2 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					String line2;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));

					while ((line2 = reader2.readLine()) != null) {

						System.out.println(line2);

					}
					System.out.println("Done");

					time = date.getTime();
					ts = new Timestamp(time);
					System.out.println("Current Time Stamp: " + ts);

					reader2.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}

		});

		toolBar.add(btnRunDecompile);
		toolBar.addSeparator();

		JButton btnRunCryptoMisuse = new JButton("Crypto Misuse") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnRunCryptoMisuse.setBackground(Color.blue);
		btnRunCryptoMisuse.setFont(f1);
		btnRunCryptoMisuse.setForeground(Color.WHITE);
		btnRunCryptoMisuse.setBorder(new LineBorder(Color.DARK_GRAY));
		btnRunCryptoMisuse.setOpaque(true);
		btnRunCryptoMisuse.setFont(f);
		btnRunCryptoMisuse.setFocusPainted(false);

		btnRunCryptoMisuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {

				String fileName = null;

				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

					fileName = dialog.getSelectedFile().getAbsolutePath();
				textAreaCommandLinesOutput.setText(null);
				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command3[] = new String[8];
				command3[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command3[1] = "amandroid";
				command3[2] = "cryptoMisuse";
				command3[3] = "-m";
				command3[4] = "1";
				command3[5] = "-o";
				command3[6] = outputFilePath;
				command3[7] = apkFilePath;

				try {
					ProcessBuilder builder = new ProcessBuilder(command3);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader3 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					String line3;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));

					while ((line3 = reader3.readLine()) != null) {

						System.out.println(line3);

					}
					System.out.println("Done");

					time = date.getTime();
					ts = new Timestamp(time);
					System.out.println("Current Time Stamp: " + ts);

					reader3.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}

		});

		toolBar.add(btnRunCryptoMisuse);
		toolBar.addSeparator();

		JButton btnRunGenGraph = new JButton("Gen Graph") {
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		btnRunGenGraph.setBackground(Color.blue);
		btnRunGenGraph.setFont(f1);
		btnRunGenGraph.setForeground(Color.WHITE);
		btnRunGenGraph.setBorder(new LineBorder(Color.DARK_GRAY));
		btnRunGenGraph.setOpaque(true);
		btnRunGenGraph.setFont(f);
		btnRunGenGraph.setFocusPainted(false);

		btnRunGenGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {
				textAreaCommandLinesOutput.setText(null);
				String fileName = null;

				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

					fileName = dialog.getSelectedFile().getAbsolutePath();

				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command3[] = new String[8];
				command3[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command3[1] = "amandroid";
				command3[2] = "genGraph";
				command3[3] = "-m";
				command3[4] = "3";
				command3[5] = "-o";
				command3[6] = outputFilePath;
				command3[7] = apkFilePath;
				try {
					ProcessBuilder builder = new ProcessBuilder(command3);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader4 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					String line4;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));

					while ((line4 = reader4.readLine()) != null) {

						System.out.println(line4);

					}
					System.out.println("Done");

					time = date.getTime();
					ts = new Timestamp(time);
					System.out.println("Current Time Stamp: " + ts);

					reader4.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}

		});

		toolBar.add(btnRunGenGraph);
		toolBar.addSeparator();

		JButton mnBugReport = new JButton("Bug Report") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};

		mnBugReport.setBackground(Color.blue);
		mnBugReport.setFont(f1);
		mnBugReport.setForeground(Color.WHITE);
		mnBugReport.setBorder(new LineBorder(Color.DARK_GRAY));
		mnBugReport.setOpaque(true);
		mnBugReport.setFont(f);
		mnBugReport.setFocusPainted(false);

		mnBugReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "https://github.com/arguslab/Argus-SAF/issues";

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException | URISyntaxException e4) {
						e4.printStackTrace();
					}
				} else {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
					} catch (IOException e5) {
						e5.printStackTrace();
					}
				}

			}
		});

		toolBar.add(mnBugReport);
		toolBar.addSeparator();

		tabbedPane = new ClosableTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		// Specify the different position and size of GUI area
		int HORIZSPLIT = JSplitPane.HORIZONTAL_SPLIT;
		int VERTSPLIT = JSplitPane.VERTICAL_SPLIT;
		boolean continuousLayout = true;
		Dimension minimumSize = new Dimension(100, 50);

		JSplitPane splitPane = new JSplitPane(VERTSPLIT, continuousLayout,
				tabbedPane, scrollerTextArea);
		tabbedPane.setMinimumSize(minimumSize);
		scrollerTextArea.setMinimumSize(minimumSize);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(450);
		splitPane.setDividerSize(5);

		JSplitPane splitPane2 = new JSplitPane(HORIZSPLIT, splitPane,
				scrollPaneTree);
		scrollPaneTree.setMinimumSize(minimumSize);
		splitPane2.setOneTouchExpandable(true);
		splitPane2.setDividerLocation(700);
		splitPane2.setDividerSize(5);

		JSplitPane splitPane3 = new JSplitPane(HORIZSPLIT, scrollPane1,
				splitPane2);
		scrollPaneTree.setMinimumSize(minimumSize);
		frame.getContentPane().add(splitPane3, BorderLayout.CENTER);
		splitPane3.setOneTouchExpandable(true);
		splitPane3.setDividerLocation(175);
		splitPane3.setDividerSize(5);

		// define a class which will create a text area within a new tab and
		// open the file. this class will be called later
		class TextDemoPanel extends JPanel {

			private static final long serialVersionUID = 1L;

			public TextDemoPanel(String text) {

				textArea = new JTextArea();
				textArea.setBorder(new EmptyBorder(20, 10, 10, 10));
				textArea.append(text);
				textArea.append("\n");
				textArea.setFont(new Font("Verdana", Font.PLAIN, 14));

				textArea.setWrapStyleWord(true);
				textArea.setLineWrap(true);

				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setWheelScrollingEnabled(true);
				TextLineNumber tln = new TextLineNumber(textArea);
				scrollPane.setRowHeaderView(tln);
				this.setLayout(new BorderLayout());
				this.add(scrollPane, BorderLayout.CENTER);
			}
		}

		// Action listener for opening a file in new tab
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				t = new Thread(new Runnable() {

					@Override
					public void run() {

						Jtreevar = tree.getSelectionPath().toString()
								.replace(",", "/").replaceAll(" ", "")
								.replaceAll("\\[", "").replaceAll("\\]", "");
						int index = Jtreevar.lastIndexOf("/");
						String fileName = Jtreevar.substring(index + 1);

						BufferedReader reader;

						// StringBuilder builder = new StringBuilder();

						String fileContent1 = "";

						try {
							reader = new BufferedReader(
									new FileReader(Jtreevar));

							// reader = new BufferedReader(new
							// FileReader(path));
							// reader = new BufferedReader(new
							// FileReader(Jtreevar));
							// String fileContent1 = "";
							int c = 0;
							while ((c = reader.read()) != -1) {
								fileContent1 += (char) c;
							}

							// while ((fileContent1 = reader.readLine()) !=
							// null) {
							// builder.append(fileContent1);

							// }

							// String text = builder.toString();

							tabbedPane.add(fileName, new TextDemoPanel(
									fileContent1));

							reader.close();

						} catch (IOException e1) {

							e1.printStackTrace();
						}
					}
				});
				t.start();
			}
		});
 
		//findbutton is linked with findfield(a text filed). It searches word or sentences in a particular text area in GUI.
		findButton = new JButton("Search") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(150, 50);
				setMaximumSize(getSize());
			}
		};
		findButton.setBackground(Color.blue);
		findButton.setFont(f1);
		findButton.setForeground(Color.WHITE);
		findButton.setBorder(new LineBorder(Color.DARK_GRAY));
		findButton.setOpaque(true);
		findButton.setFont(f);
		findButton.setFocusPainted(false);

		toolBar.add(findButton);
		findField = new JTextField("") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSize(350, 50);
				setMaximumSize(getSize());
			}
		};
		toolBar.add(findField);
		toolBar.addSeparator();

		// Search mechanism

		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String find = findField.getText().toLowerCase();
				// Focus the text area in GUI to show up the highlighting 
				textArea.requestFocusInWindow();
				// Search term is valid or invalid
				if (find != null && find.length() > 0) {
					Document document = textArea.getDocument();
					int findLen = find.length();
					try {
						boolean found = false;
						// Rest the search position if we're at the end of the
						// document
						if (position + findLen > document.getLength()) {
							position = 0;
						}
						// While we haven't reached the end...
						// "<=" Correction
						while (position + findLen <= document.getLength()) {
							// Extract the text from the document
							String match = document.getText(position, findLen)
									.toLowerCase();
							// Check to see if it matches or request
							if (match.equals(find)) {
								found = true;
								break;
							}
							position++;
						}

						// if searched word found in document
						if (found) {
							// Get the rectangle of the where the text would be visible
							Rectangle view = textArea.modelToView(position);
							// Scroll to make the rectangle visible in GUI
							textArea.scrollRectToVisible(view);
							// Highlight the matched text
							textArea.setCaretPosition(position + findLen);
							textArea.moveCaretPosition(position);
							// Move the search position beyond to the current match
							position += findLen;
						}

					} catch (Exception exp) {
						exp.printStackTrace();
					}

				}
			}
		});

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnRun = new JMenu("Run");
		mnRun.setFont(f);
		menuBar.add(mnRun);

		//shows available plug-ins in Amandroid
		JMenuItem mntmRunAmandroid = new JMenuItem("Amandroid");
		mntmRunAmandroid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaCommandLinesOutput.setText(null);
				String command[] = { "/Users/bsharifa/Documents/Sireum/sireum",
						"amandroid" };
				try {
					Process process = Runtime.getRuntime().exec(command);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						textAreaCommandLinesOutput.append(line);
						textAreaCommandLinesOutput.append("\n");
					}
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnRun.add(mntmRunAmandroid);

		//Implementation of taint analysis
		JMenuItem mntmRunTaintanalysis = new JMenuItem("Taint Analysis");

		mntmRunTaintanalysis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				t = new Thread(new Runnable() {

					@Override
					public void run() {

						String fileName = null;

						if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

							fileName = dialog.getSelectedFile()
									.getAbsolutePath();
						textAreaCommandLinesOutput.setText(null);
						String apkFilePath = filePath;
						String outputFilePath = fileName;
						String command1[] = new String[8];
						command1[0] = "/Users/bsharifa/Documents/Sireum/sireum";
						command1[1] = "amandroid";
						command1[2] = "taintAnalysis";
						command1[3] = "-m";
						command1[4] = "4";
						command1[5] = "-o";
						command1[6] = outputFilePath;
						command1[7] = apkFilePath;

						try {
							ProcessBuilder builder = new ProcessBuilder(
									command1);
							builder.redirectErrorStream(true);
							Process process = builder.start();

							BufferedReader reader = new BufferedReader(
									new InputStreamReader(process
											.getInputStream()));
							String line1;

							JTextAreaOutputStream out = new JTextAreaOutputStream(
									textAreaCommandLinesOutput);
							System.setOut(new PrintStream(out));

							while ((line1 = reader.readLine()) != null) {

								System.out.println(line1);
							}

							reader.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				t.start();
			}
		});
		mnRun.add(mntmRunTaintanalysis);
        
		//Decompile apk files
		JMenuItem mntmDecompile = new JMenuItem("Decompile");
		mntmDecompile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {

				String fileName = null;
				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					fileName = dialog.getSelectedFile().getAbsolutePath();
				textAreaCommandLinesOutput.setText(null);
				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command2[] = new String[5];
				command2[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command2[1] = "amandroid";
				command2[2] = "decompile";
				command2[3] = apkFilePath;
				command2[4] = outputFilePath;
				
				try {
					ProcessBuilder builder = new ProcessBuilder(command2);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader2 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					String line2;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));

					while ((line2 = reader2.readLine()) != null) {

						System.out.println(line2);

					}
					System.out.println("Done");
					reader2.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}

		});
		mnRun.add(mntmDecompile);
        
		//Implementation of Crypto Misuse
		JMenuItem mnRunCryptoMisuse = new JMenuItem("Crypto Misuse");

		mnRunCryptoMisuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {
				String fileName = null;
				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)

					fileName = dialog.getSelectedFile().getAbsolutePath();
				textAreaCommandLinesOutput.setText(null);
				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command3[] = new String[8];
				command3[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command3[1] = "amandroid";
				command3[2] = "cryptoMisuse";
				command3[3] = "-m";
				command3[4] = "1";
				command3[5] = "-o";
				command3[6] = outputFilePath;
				command3[7] = apkFilePath;
				try {
					ProcessBuilder builder = new ProcessBuilder(command3);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader3 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					String line3;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));
					while ((line3 = reader3.readLine()) != null) {

						System.out.println(line3);

					}
					System.out.println("Done");
					reader3.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}

		});
		mnRun.add(mnRunCryptoMisuse);

		// Implementation of Gen Graph 

		JMenuItem mnRunGenGraph = new JMenuItem("Gen Graph");

		mnRunGenGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent s) {
				textAreaCommandLinesOutput.setText(null);
				String fileName = null;
				if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					fileName = dialog.getSelectedFile().getAbsolutePath();
				String apkFilePath = filePath;
				String outputFilePath = fileName;
				String command3[] = new String[8];
				command3[0] = "/Users/bsharifa/Documents/Sireum/sireum";
				command3[1] = "amandroid";
				command3[2] = "genGraph";
				command3[3] = "-m";
				command3[4] = "3";
				command3[5] = "-o";
				command3[6] = outputFilePath;
				command3[7] = apkFilePath;

				try {
					ProcessBuilder builder = new ProcessBuilder(command3);
					builder.redirectErrorStream(true);
					Process process = builder.start();
					BufferedReader reader4 = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line4;
					JTextAreaOutputStream out = new JTextAreaOutputStream(
							textAreaCommandLinesOutput);
					System.setOut(new PrintStream(out));

					while ((line4 = reader4.readLine()) != null) {
						System.out.println(line4);
					}
					System.out.println("Done");
					reader4.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}
		});
		mnRun.add(mnRunGenGraph);

		// help user to know more about Amandroid and also easeAmandroid GUI
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(f);
		menuBar.add(mnHelp);

		JMenuItem mntmAmandroidInstallation = new JMenuItem(
				"How to Install Amandroid");

		mntmAmandroidInstallation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "http://amandroid.sireum.org/software.html#";

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException | URISyntaxException e4) {
						e4.printStackTrace();
					}
				} else {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
					} catch (IOException e5) {
						e5.printStackTrace();
					}
				}
			}
		});
		mnHelp.add(mntmAmandroidInstallation);

		JMenuItem mntmAmandroid = new JMenuItem("Amandroid Documentation");
		mntmAmandroid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "http://amandroid.sireum.org/index.html";

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException | URISyntaxException e4) {
						e4.printStackTrace();
					}
				} else {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
					} catch (IOException e5) {
						e5.printStackTrace();
					}
				}
			}
		});
		mnHelp.add(mntmAmandroid);

		JMenuItem mntmAmandroidFuntionalities = new JMenuItem(
				"About Amandroid GUI");

		mntmAmandroidFuntionalities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FunctionalitiesofUserfriendlyAmandroid();

			}
		});
		mnHelp.add(mntmAmandroidFuntionalities);

	}
}
