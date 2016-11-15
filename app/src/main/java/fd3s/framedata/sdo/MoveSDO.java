package fd3s.framedata.sdo;

/**
 * @author Waseem Suleman
 * 
 * Simple POJO for properties of a super art move.
 */
public class MoveSDO {
	public String name;
	public String startup;
	public String hit;
	public String recovery;
	public String block_advantage;
	public CancelableSDO cancel;
	public ParrySDO parry;
	public String throw_range;
	public String damage;
	public String stun;
}
