package zombie_game;

public class Hero extends Unit{
	
	private int power;
	private int potion;
	
	public Hero(int travel, int hp, int max, String name) {
		super(travel, hp, max,name);
		this.potion = 5;
	}

	
	public void attack(Unit unit) {
		if(unit instanceof Boss) {			
			Boss boss = (Boss) unit;
			this.power = ran.nextInt(boss.getMax())+1;
			if(boss.getHp()>0) {
				if(boss.getShield()>0) {
					boss.setShield(boss.getShield()-this.power);
				}
				else {
					boss.setHp(boss.getHp()-this.power);
				}				
				System.err.printf("%d의 피해를 입혔다.\n",this.power);
				System.err.printf("%s의 실드 : %d\n체력 : %d/%d\n",boss.getName(),boss.getShield(),boss.getHp(),boss.getMax());
			}
			else {
				System.out.printf("'%s'을(를) 잡았다!\n",boss.getName());
			}
		}
		else {
			this.power = ran.nextInt(unit.getMax())+1;
			if(unit.getHp()>0) {
				unit.setHp(unit.getHp()-this.power);
				System.err.printf("%d의 피해를 입혔다.\n",this.power);
				System.err.printf("%s의 체력 : %d/%d\n",unit.getName(),unit.getHp(),unit.getMax());
			}
			else {
				System.out.printf("'%s'을(를) 잡았다!\n",unit.getName());
			}
		}
	}
	
	public void recovery() {
		if(this.potion>0) {
			super.setHp(super.getHp()+100);
			this.potion --;
			System.out.printf("치유완료\nHP +100\n현재 HP : %d/%d",super.getHp(),super.getMax());
		}
		else {
			System.out.println("사용할 포션이 없습니다.");
		}
	}
	
}
