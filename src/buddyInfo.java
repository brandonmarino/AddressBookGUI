
public class buddyInfo {
	private String name;
	private String addr;
	private int phoneNumber;
	public buddyInfo(){
		this.name = "";
		this.addr = "";
		this.phoneNumber = 0;
	}
	public buddyInfo(String name, String address, int phoneNumber){
		this.name = name;
		this.addr = address;
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return addr;
	}

	public void setAddress(String address) {
		this.addr = addr;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString(){
		return (getName() + " " + getAddress() + " " + getPhoneNumber());
	}
}
