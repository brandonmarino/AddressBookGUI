import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class AddressBook {
    private DefaultListModel<buddyInfo> buddies;

    public AddressBook() {
        buddies = new DefaultListModel();
    }

    public void addBuddy(buddyInfo buddy) {
        buddies.addElement(buddy);
    }

    public boolean removeBuddy(buddyInfo buddy) {
        return buddies.removeElement(buddy);
    }

    public buddyInfo getBuddy(int index) {
        if (index < buddies.size())
            return buddies.get(index);
        return null;
    }
    public void editBuddy(buddyInfo buddy, buddyInfo newbuddy) {
        for(int i = 0; i < buddies.size(); i++){
            if (buddies.get(i) == buddy){
                buddies.remove(i);
                buddies.add(i, newbuddy);
            }
        }
    }

    public Boolean contains(buddyInfo buddy){
        return buddies.contains(buddy);
    }

    public int getSize() {
        return buddies.size();
    }

    public DefaultListModel<buddyInfo> getList() {
        return buddies;
    }
}
