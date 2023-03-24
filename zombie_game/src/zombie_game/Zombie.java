package zombie_game;

public class Zombie extends Unit{
	
	private int power;
	
	public Zombie(int travel, int hp, int max, String name) {
		super(travel, hp, max, name);
	}

	
	public void attack(Unit unit) {
		if(unit instanceof Hero) {
			this.power = ran.nextInt(unit.getMax())+1;
			
			if(unit.getHp()>0) {
				unit.setHp(unit.getHp()-this.power);
				System.err.printf("%d의 피해를 입혔다.\n",this.power);
				System.err.printf("%s의 체력 : %d/%d\n",unit.getName(),unit.getHp(),unit.getMax());
			}
			else {
				System.err.printf("%s 이(가) 죽었습니다.\n",unit.getName());
			}			
		}
	}
}
