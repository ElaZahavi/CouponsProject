package ela.johnbryce.CouponsProjectSpring.Web;

import ela.johnbryce.CouponsProjectSpring.facades.ClientFacade;

public class OurSession {
	
	private long lastAccessTime;
	private ClientFacade facade;
	
	public OurSession(long lastAcvessTime, ClientFacade facade) {
		super();
		this.lastAccessTime = lastAcvessTime;
		this.facade = facade;
	}

	public ClientFacade getFacade() {
		return facade;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}	

}
