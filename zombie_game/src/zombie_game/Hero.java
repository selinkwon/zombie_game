package zombie_game;

public class Hero extends Unit{
	
	private int power;
	private int potion;
	private int count;
	
	public Hero(int travel, int hp, int max, String name) {
		super(travel, hp, max,name);
		this.potion = 5;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void attack(Unit unit) {
		if(unit instanceof Boss) {			
			Boss boss = (Boss) unit;
			int shieldMax = boss.getShield();
			this.power = ran.nextInt(boss.getMax())+1;
			if(boss.getHp()>0) {
				if(boss.getShield()>0) {
					boss.setShield(boss.getShield()-this.power);
					if(boss.getShield()<0) {
						boss.setShield(0);	
						boss.setHp(boss.getHp()-(this.power-shieldMax));
					}
				}
				else {
					boss.setHp(boss.getHp()-this.power);
				}
				System.err.printf("\n⚔️%s의 공격!⚔️\n",super.getName());
				System.err.printf("%d의 피해를 입혔다.\n",this.power);
				if(boss.getHp()<0) {	
					boss.setHp(0);
					System.err.printf("%s의 실드 : %d\n체력 : %d/%d\n",boss.getName(),boss.getShield(),boss.getHp(),boss.getMax());
					System.out.printf("\n'%s'을(를) 잡았다!\n",boss.getName());
					System.out.println("도감에 추가합니다.");
					this.count ++;
					System.out.printf("잡은 몬스터 수 : %d\n",this.count);
				}
				else {
					System.err.printf("%s의 실드 : %d\n체력 : %d/%d\n",boss.getName(),boss.getShield(),boss.getHp(),boss.getMax());					
				}
			}
		}
		else {
			this.power = ran.nextInt(unit.getMax())+1;
			if(unit.getHp()>0) {
				unit.setHp(unit.getHp()-this.power);
				System.err.printf("\n⚔️%s의 공격!⚔️\n",super.getName());
				System.err.printf("%d의 피해를 입혔다.\n",this.power);
				if(unit.getHp()<0) {
					unit.setHp(0);
					System.err.printf("%s의 체력 : %d/%d\n",unit.getName(),unit.getHp(),unit.getMax());
					System.out.printf("\n'%s'을(를) 잡았다!\n",unit.getName());
					System.out.println("도감에 추가합니다.");
					this.count ++;
					System.out.printf("잡은 몬스터 수 : %d\n",this.count);
				}
				else {
					System.err.printf("%s의 체력 : %d/%d\n",unit.getName(),unit.getHp(),unit.getMax());					
				}
			}
		}
	}
	
	public void recovery() {
		if(this.potion>0) {
			super.setHp(super.getHp()+100);
			this.potion --;
			System.out.printf("치유완료\nHP +100\n현재 HP : %d/%d\n",super.getHp(),super.getMax());
			if(super.getHp()==super.getMax()) 
				super.setHp(super.getMax());
			
		}
		else {
			System.out.println("사용할 포션이 없습니다.");
		}
	}
	
}
