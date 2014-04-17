package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Server;
import client.Client;

public class ClientPanel extends JPanel implements ListSelectionListener, ActionListener {
	private DefaultListModel<String> clListModel, outputModel;//list to add string
	private Client clSocket;
	private JList<String> cList, oList;//for display count
	private JTextField input;
	private JScrollPane listWindow, outputWindow;
	private String myName;

	public ClientPanel(int port) {
		promptForName();
		clSocket = new Client(port, this);
		init();
	}
	
	private void promptForName(){

		String name = JOptionPane.showInputDialog("Enter Username: ");
		myName = name;
	}
	
	private void init(){
		setLayout(new BorderLayout());
		clSocket.sendName(myName);
		clListModel = new DefaultListModel<String>();
		outputModel = new DefaultListModel<String>();
		
		cList = new JList<String>(clListModel);
		cList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		cList.setVisibleRowCount(10);
		oList = new JList<String>(outputModel);
		oList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		oList.setVisibleRowCount(10);
		listWindow = new JScrollPane(cList);
		listWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		outputWindow = new JScrollPane(oList);
		outputWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new BorderLayout());
		ioPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		input = new JTextField();
		input.addActionListener(this);
		
		ioPanel.add(outputWindow, BorderLayout.CENTER);
		ioPanel.add(input, BorderLayout.SOUTH);
		add(ioPanel, BorderLayout.CENTER);
		add(listWindow, BorderLayout.EAST);
	}
	
	public void addUser(String nm){
		clListModel.addElement(nm);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Selection events
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input){
			outputModel.addElement(input.getText());
			clSocket.sendMessage(input.getText(), "");
			input.setText("");
		}
		
	}

}
