import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame {

	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontLabel;
	JSpinner fontSizeSpinner;
	JButton fontColorButton;
	JComboBox fontBox;
	JRadioButton italicRadio;
	JRadioButton boldRadio;
//	JRadioButton underlineRadio;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	TextEditor() {
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Serif", Font.PLAIN, 20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(550, 550));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontLabel = new JLabel("Font:");
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), textArea.getFont().getStyle(), (int)fontSizeSpinner.getValue()));
				
			}
			
		});
		
		italicRadio = new JRadioButton("I");
		italicRadio.setFont(new Font("Serif", Font.ITALIC, 12));
		italicRadio.addActionListener(e -> {
			if(textArea.getFont().getStyle() == Font.BOLD) {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), textArea.getFont().getStyle() + Font.ITALIC , textArea.getFont().getSize()));

			}
			else if (textArea.getFont().getStyle() == Font.PLAIN){
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.ITALIC , textArea.getFont().getSize()));

			}
			else if (textArea.getFont().getStyle() == Font.BOLD + Font.ITALIC){
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.BOLD , textArea.getFont().getSize()));
				
			} 
			else {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN , textArea.getFont().getSize()));
				
			}
		});
		
		boldRadio = new JRadioButton("B");
		boldRadio.setFont(new Font("Serif", Font.BOLD, 12));
		boldRadio.addActionListener(e -> {
			if(textArea.getFont().getStyle() == Font.ITALIC) {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), textArea.getFont().getStyle() + Font.BOLD , textArea.getFont().getSize()));

			}
			else if (textArea.getFont().getStyle() == Font.PLAIN){
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.BOLD , textArea.getFont().getSize()));

			}
			else if (textArea.getFont().getStyle() == Font.BOLD + Font.ITALIC) {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.ITALIC , textArea.getFont().getSize()));
				
			}
			else {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN , textArea.getFont().getSize()));
				
			}
		});
		
//		underlineRadio = new JRadioButton("U");
//		underlineRadio.setFont(new Font("Serif", Font.UNDERLINE, 12));
		
		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(e -> {
			
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			
			textArea.setForeground(color);
			
		});
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(e -> {
			
			textArea.setFont(new Font((String)fontBox.getSelectedItem(), textArea.getFont().getStyle(), textArea.getFont().getSize()));
			
		});
		fontBox.setSelectedItem("Serif");
		
		// -------- MenuBar --------
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(e -> {
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
								String line = fileIn.nextLine() + "\n";
								textArea.append(line);
						}
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileIn.close();
				}
				
			}
			
		});
		
		saveItem.addActionListener(e -> {
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				PrintWriter fileOut =  null;
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileOut.close();
				}
			};
			
		});
		
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		// -------- MenuBar --------
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(italicRadio);
		this.add(boldRadio);
//		this.add(underlineRadio);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setSize(600, 600);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
}
