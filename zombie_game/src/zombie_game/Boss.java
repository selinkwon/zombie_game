package zombie_game;

public class Boss extends Zombie {

	private int shield;

	public Boss(int travel, int hp, int max, String name, int shield) {
		super(travel, hp, max, name);
		this.shield = shield;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public void attack(Unit unit) {
		int damage = ran.nextInt(unit.getMax()/2) + 5;
		int critical = ran.nextInt(5);
		if (unit.getHp() > 0) {
			System.err.printf("\n⚔️%s의 공격!⚔️\n",super.getName());
			if (critical == 4) {
				critical = damage * 2;
				unit.setHp(unit.getHp() - critical);
				System.err.printf("%d의 치명적인 피해를 입혔다.\n", critical);
			} else {
				unit.setHp(unit.getHp() - damage);
				System.err.printf("%d의 피해를 입혔다.\n", damage);
			}
			if (unit.getHp() < 0) {
				unit.setHp(0);
				System.err.printf("%s의 체력 : %d/%d\n", unit.getName(), unit.getHp(), unit.getMax());				
				System.err.printf("\n%s 이(가) 죽었습니다.\n", unit.getName());
			}
			else {
				System.err.printf("%s의 체력 : %d/%d\n", unit.getName(), unit.getHp(), unit.getMax());				
			}
		}
	}
}
