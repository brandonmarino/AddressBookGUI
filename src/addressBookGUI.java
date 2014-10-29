import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class addressBookGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JFrame addFrame;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField numberField;
    private AddressBook book;
    private JList<buddyInfo> bookList;
    private buddyInfo currentSelected = null;
    private ArrayList<JMenuItem> disabledItems;
    /**
     * Start program
     */
    public static void main(String[] args) {
        //this will not be able to read an addressbook right now
        addressBookGUI gui = new addressBookGUI();
    }
    /****************************************************************************************************
     *                                          GUI Components
     ****************************************************************************************************/
    /**
     * Will draw up the frame
     */
    public addressBookGUI() {
        disabledItems = new ArrayList<JMenuItem>();
        frame = new CloseableFrame("AddressBook");
        frame.setSize(800, 600);
        JMenuBar menuBar = new JMenuBar( ){};
        JMenuItem item;
        setJMenuBar( menuBar );
        JMenu bookMenu = new JMenu( "Address Book" );
            item = new JMenuItem ( "New" );
            item.addActionListener(this);
            bookMenu.add(item);
            item = new JMenuItem ( "Open" );
            item.addActionListener( this );
            bookMenu.add(item);
            item = new JMenuItem ( "Save" );
            item.setEnabled(false);
            item.addActionListener( this );
            disabledItems.add(item);
            bookMenu.add(item);
            menuBar.add( bookMenu );
        JMenu buddyMenu = new JMenu( "Buddy" );
            item = new JMenuItem ( "Add" );
            item.setEnabled(false);
            item.addActionListener(this);
            disabledItems.add(item);
            buddyMenu.add(item);
            item = new JMenuItem ( "Remove" );
            item.setEnabled(false);
            item.addActionListener(this);
            disabledItems.add(item);
            buddyMenu.add(item);
            item = new JMenuItem ( "Edit" );
            item.setEnabled(false);
            item.addActionListener( this );
            disabledItems.add(item);
            buddyMenu.add(item);
            menuBar.add( buddyMenu );
        createAddressBook();
        createListWindow();
        frame.add(menuBar, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    /**
     * Will create the list window and show it to the user
     */
    private void createListWindow() {
        if (bookList != null)
            frame.remove(bookList);
        bookList = new JList<>(book.getList());
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    currentSelected = bookList.getSelectedValue();
                }
            }
        });
        frame.add(new JScrollPane(bookList));
        bookList.setVisible(true);
        frame.setVisible(true);
    }
    /**
     * Add buddy Panel
     */
    private void addeditPanel(String operation){
        JButton button;
        JLabel label;
        JPanel addPanel = new JPanel();
            addPanel.setLayout(new GridLayout(3, 2));
            label = new JLabel("Name");
            addPanel.add(label);
            nameField = new JTextField();
            addPanel.add(nameField);
            label = new JLabel("Address");
            addPanel.add(label);
            addressField = new JTextField();
            addPanel.add(addressField);
            label = new JLabel("Phone Number");
            addPanel.add(label);
            numberField = new JTextField();
            addPanel.add(numberField);
        button = new JButton(operation + " Buddy");
            button.addActionListener(this);
            button.setSize(400,100);
        addFrame = new JFrame(operation + " Buddy");
            addFrame.setSize(400, 300);
            addFrame.add(addPanel);
            addFrame.add(button, BorderLayout.SOUTH);
            addFrame.setVisible(true);
    }
    /**
     * Alert the user to some message
     * @param message some message to allter to user
     * @param title title of messagebox
     */
    public void messageBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Ask user for filename, supply a suggested name
     * @param message Some message of what is required from the user
     */
    public String optionBox(String message)
    {
        return JOptionPane.showInputDialog(null, message, "addressBook");
    }

    /****************************************************************************************************
     *                                  Background Functions
     ****************************************************************************************************/

    /**
     * Will return an empty address book
     */
    private void createAddressBook() {
        book = new AddressBook();
    }
    /**
     * Print the AddressBook to a file
     */
    private void saveBook(String filename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < book.getSize(); i++)
            sb.append(book.getBuddy(i).toString() + "\n" );
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {    //file rights not given
            messageBox("Could not access the file!", "File Error");
            return;
        }
    }
    /**
     * Read contents of a book file and give it to an Addressbook
     */
    private void openBook(String filename){
        ArrayList<String[]> buddies = new ArrayList<String[]>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            for (String next, line = in.readLine(); line != null; line = next) {
                next = in.readLine();
                buddies.add(line.split("\\s+"));    //add buddy Array
            }
            for(String[] buddyString: buddies){
                book.addBuddy(new buddyInfo(buddyString[0], buddyString[1], Integer.parseInt(buddyString[2])));
            }
        }catch (NumberFormatException ex) {
            //some number parsing did not work, file must be corrupt
            messageBox("File is corrupted!", "File Error");
        }catch (IOException e) {
            //file rights not given
            messageBox("Could not access the file!", "File Error");
        }
    }

    /****************************************************************************************************
     *                          ListItem and Button Actions and their active functions
     ****************************************************************************************************/

    /**
     * Action Listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Menu items
        if (e.getSource() instanceof JMenuItem){
            JMenuItem item = (JMenuItem)e.getSource();
            //AddressBook SubMenu
            if (item.getText()=="New")
                createItem();
            else if (item.getText()=="Save")
                saveItem();
            else if (item.getText()=="Open")
                openItem();
            //Buddy SubMenu
            else if (item.getText()=="Add")
                addItem();
            else if (item.getText()=="Remove")
                removeItem();
            else if (item.getText()=="Edit")
                editItem();
        //Buttons
        }else if(e.getSource() instanceof JButton){
            JButton button = (JButton)e.getSource();
            if (button.getText().equals("Add Buddy"))
                addButton();
            else if (button.getText().equals("Edit Buddy"))
                editButton();
        }
    }
    /**
     * Create button pressed, create a new address book
     */
    private void createItem(){
        int bookSize = book.getSize();
        for(int i = 0; i < bookSize; i++){
            book.removeBuddy(book.getBuddy(0));
        }
        for (JMenuItem item: disabledItems)
            item.setEnabled(true);
    }
    /**
     * The add menu item has been selected
     */
    private void addItem(){
        addeditPanel("Add");
    }
    /**
     * Button for adding a buddy to the list
     */
    private void addButton(){
        String name = nameField.getText();
        String address = addressField.getText();
        String number = numberField.getText();
        try {
            if ((name.isEmpty()) || (address.isEmpty()) || number.isEmpty())
                messageBox("Enter all fields please!", "Data Entry Error");
            else {
                buddyInfo buddy = new buddyInfo(name, address, Integer.parseInt(number));
                if(!book.contains(buddy))
                    book.addBuddy(buddy);
                addFrame.setVisible(false);
            }
        } catch ( NumberFormatException ex) {
            messageBox("Not a valid phone number!", "Data Entry Error");
        }
        createListWindow();
    }
    /**
     * Will delete current entry for this buddy and load it's information into the editor
     */
    private void editItem(){
        if (currentSelected == null)
            return;
        addeditPanel("Edit");
        nameField.setText(currentSelected.getName());
        addressField.setText(currentSelected.getAddress());
        numberField.setText(currentSelected.getPhoneNumber() + "");
    }
    /**
     *  User wants to edit a buddy at some location
     */
    private void editButton(){
        String name = nameField.getText();
        String address = addressField.getText();
        String number = numberField.getText();
        try {
            if ((name.isEmpty()) || (address.isEmpty()) || number.isEmpty())
                messageBox("Enter all fields please!", "Data Entry Error");
            else {
                buddyInfo buddy = new buddyInfo(name, address, Integer.parseInt(number));
                book.editBuddy(currentSelected, buddy);
                book.editBuddy(currentSelected, buddy);
                book.editBuddy(currentSelected, buddy);
                addFrame.setVisible(false);
            }
        } catch ( NumberFormatException ex) {
            messageBox("Not a valid phone number!", "Data Entry Error");
        }
    }
    /**
     * Open a given file, if file does not exist tell user
     */
    private void openItem(){
        String input = optionBox("Please enter the name of the file");
        if (input != null && !input.isEmpty()){
            createItem();
            openBook(input);
        }else{
            messageBox("That file is not valid!","Input Error");
        }
    }
    /**
     * save Address Book
     */
    private void saveItem(){
        String input = optionBox("Please enter the name of the file");
        if (input != null && !input.isEmpty()){
            saveBook(input);
        }else{
            messageBox("That file is not valid!", "Input Error");
        }
    }
    /**
     * remove the buddy all together
     */
    private void removeItem(){
        book.removeBuddy(currentSelected);
     }
}
