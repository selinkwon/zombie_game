package zombie_game;

import java.util.Random;
import java.util.Scanner;

class ZombieGame {
	private Random ran;
	private Scanner scan;
	public int travel;
	private int stage = 1;
	private int rNum;
	private int bossNum;
	private final int SIZE = 10;

	public static final Hero HERO = new Hero(0, 300, 300, "용사세린");
	private Zombie poketmon;
	private Boss boss;

	private final int PLAYER = 2;
	private final int WALL = 3;
	private final int ROAD = 0;
	private final int HOME = 1;
	private final int HOMESIZE = 5;
	private final int BED = 4;
	private final int TV = 6;
	private final int DOOR = 7;
	private final int CHAIR = 8;
	private final int PORTAL = 9;
	private final int BACKPORTAL = 10;
	private int log = -1;
	

	private int[][] map = new int[SIZE][SIZE];
	private int[][] home = new int[HOMESIZE][HOMESIZE];

	private int pY = 0;
	private int pX = 0;
	private int homePX = 0;
	private int homePY = 0;

	public ZombieGame() {
		this.ran = new Random();
		this.scan = new Scanner(System.in);
		this.bossNum = -1;
		this.rNum = -2;
	}

	public void heroSet() {
		System.out.println("내 캐릭터 정보");
		System.out.printf("이름 : %s\n체력 : %d/%d\n\n", HERO.getName(), HERO.getHp(), HERO.getMax());
	}

	public void monsterSet() {
		this.rNum = ran.nextInt(29) + 1;
		this.bossNum = ran.nextInt(29) + 1;
		while (this.rNum == this.bossNum) {
			this.bossNum = ran.nextInt(29) + 1;
		}
		this.poketmon = new Zombie(this.rNum, 140, 140, "꼬렛");
		this.boss = new Boss(this.bossNum, 300, 300, "뮤", 200);
	}

	private void setMap() {
		map[0][0] = HOME;
		map[SIZE-1][SIZE-1] = PORTAL;
		int wallCnt = ran.nextInt(SIZE)+WALL;
		while (wallCnt + 1 > 0) {
			int rY = ran.nextInt(SIZE);
			int rX = ran.nextInt(SIZE);

			if (map[rY][rX] == ROAD) {
				map[rY][rX] = WALL;

				if (wallCnt == 0) {
					map[rY][rX] = PLAYER;
					pY = rY;
					pX = rX;
				}
				wallCnt--;
			}
		}
	}

	private void printMap() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == PLAYER) {
					System.out.print("🐧");
				} else if (map[i][j] == WALL) {
					System.out.print("🌳");
				}else if(map[i][j] == HOME) {
					System.out.print("🛖");
				}else if(map[i][j] == PORTAL){
					System.out.print("🌀");
				}else {
					System.out.print("➖");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void monsterNewSet() {
		this.rNum = ran.nextInt(30) + 30;
		this.bossNum = ran.nextInt(30) + 30;
		while (this.rNum == this.bossNum) {
			this.bossNum = ran.nextInt(30) + 30;
		}
		this.poketmon = new Zombie(this.rNum, 100, 100, "잉어킹");
		this.boss = new Boss(this.bossNum, 300, 300, "갸라도스", 200);
	}
	
	private void setNewMap() {
		int wallCnt = 7;
		while (wallCnt + 1 > 0) {
			int rY = ran.nextInt(SIZE);
			int rX = ran.nextInt(SIZE);

			if (map[rY][rX] == ROAD) {
				map[rY][rX] = WALL;
				
				if (wallCnt == 0) {
					map[0][1] = PLAYER;
					pY = 0;
					pX = 1;
				}
				wallCnt--;
			}
		}
		this.travel = 30;
	}
	private void setNewUnit() {
		map[0][0] = BACKPORTAL;
		map[SIZE-1][SIZE-1] = PORTAL;
	}
	
	private void printNewMap() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == PLAYER) {
					System.out.print("🐧 ");
				} else if (map[i][j] == WALL) {
					System.out.print("🏝️ ");
				} else if(map[i][j] == PORTAL||map[i][j] == BACKPORTAL){
					System.out.print(" 🌀");
				} else {
					System.out.print("〰️ ");
				}
			}
			System.out.println();
		}
		System.out.println();		
	}

	private void printMenu() {
		System.out.println("1. 이동하기");
		System.out.println("2. 종료하기");
	}

	private int input() {
		int num = -1;
		System.out.print("선택 : ");
		String input = scan.next();
		
		try {
			num = Integer.parseInt(input);
		} catch (Exception e) {
			
		}
		return num;
	}

	private char inputmove() {
		System.out.print("a(←)s(↓)d(→)w(↑)\n : ");
		char dir = scan.next().charAt(0);
		return dir;
	}

	private void move() {		
		if(this.stage == 1)
			printMap();
		else if(this.stage == 2){
			setNewUnit();
			printNewMap();
		}

		char dir = inputmove();
		int y = this.pY;
		int x = this.pX;

		if (dir == 'a')
			x--;
		else if (dir == 'd')
			x++;
		else if (dir == 'w')
			y--;
		else if (dir == 's')
			y++;
		else {
			System.err.println("잘못된 입력입니다.");
			return;
		}

		if (y < 0 || y >= this.SIZE || x < 0 || x >= this.SIZE || this.map[y][x] == this.WALL) {
			System.err.println("더 이상 이동 할 수 없는 곳입니다.");
			return;
		}

		if(this.map[y][x]==this.HOME) {
			this.log = 1;
			rest();
		}
		if(this.map[y][x] == BACKPORTAL) {
			this.stage = 1;
		}
		if (this.map[y][x] == PORTAL&&this.stage == 1) {
			System.out.println("\n===================");
			System.out.println("새로운 지역으로 이동합니다.");
			System.out.println("===================\n");
			this.stage = 2;
			monsterNewSet();
			setNewMap();
		}
		else if(this.map[y][x] == PORTAL&&this.stage == 2) {
			System.out.println("\n===================");
			System.out.println("새로운 지역으로 이동합니다.");
			System.out.println("===================\n");
			this.stage = 3;
		}

		this.map[this.pY][this.pX] = this.ROAD;
		this.pY = y;
		this.pX = x;
		this.map[this.pY][this.pX] = this.PLAYER;

		this.travel++;
		System.out.printf("🐾현재위치 : %d🐾\n", this.travel);

		if (this.travel == this.rNum) {
			fight(this.poketmon);
		} else if (this.travel == this.bossNum) {
			fight(this.boss);
		} 
		if(this.travel>=30&&this.stage == 1) {
			this.travel = 0;
		}

	}
	
	private void rest() {
		System.out.println("집에 들어왔습니다.");
		while(this.log != -1) {
			homeSet();
			printHome();
			homeMove();			
		}		
	}

	private void homeSet() {
		home[this.homePY][this.homePX] = PLAYER;
		map[0][0] = HOME;
		home[3][0] = BED;
		home[1][4] = TV;
		home[4][4] = DOOR;
		home[0][4] = CHAIR;
	}

	private void printHome() {
		for (int i = 0; i < HOMESIZE; i++) {
			for (int j = 0; j < HOMESIZE; j++) {
				if (home[i][j] == PLAYER) {
					System.out.print("🐧");				
				} else if (home[i][j] == BED) {
					System.out.print("🛏️");
				} else if(home[i][j] == TV){
					System.out.print("📺");
				}else if(home[i][j] == DOOR){
					System.out.print("🕳️");
				}else if(home[i][j] == CHAIR){
					System.out.print("🪑");
				}else {
					System.out.print("➖");
				}
			}
			System.out.println();
		}
		System.out.println();		
	}
	
	private void homeMove() {
		char dir = inputmove();
		int y = this.homePY;
		int x = this.homePX;

		if (dir == 'a')
			x--;
		else if (dir == 'd')
			x++;
		else if (dir == 'w')
			y--;
		else if (dir == 's')
			y++;
		else {
			System.err.println("잘못된 입력입니다.");
			return;
		}

		if (y < 0 || y >= this.HOMESIZE || x < 0 || x >= this.HOMESIZE||this.home[y][x] == this.CHAIR) {
			System.err.println("더 이상 이동 할 수 없는 곳입니다!");
			return;
		}
		if(this.home[y][x] == this.DOOR) {
			this.log = -1;
			move();
		}
		else if(this.home[y][x] == this.BED) {
			recovery();
		}
		else if(this.home[y][x] == this.TV) {
			save();
		}
		

		this.home[this.homePY][this.homePX] = this.ROAD;
		this.homePY = y;
		this.homePX = x;
		this.home[this.homePY][this.homePX] = this.PLAYER;
	}
	
	private void save() {
		System.out.println("저장되었습니다.");
	}

	private void recovery() {
		while(HERO.getHp() < HERO.getMax()) {
			HERO.setHp(HERO.getHp()+10);
			System.out.println("휴식중🛏️...");
			try {
				Thread.sleep(300);
			} catch (Exception e) {
			}
			if(HERO.getHp()>=HERO.getMax())
				HERO.setHp(HERO.getMax());
			System.out.println("HP⬆️ : "+HERO.getHp());
		}
		System.out.println("체력을 모두 회복했다!");
	}

	private void fight(Zombie zom) {
		while (this.travel == this.rNum || this.travel == this.bossNum) {
			System.out.printf("야생의 '%s'을(를) 발견했다!\n", zom.getName());
			System.out.println("1. 싸우기");
			System.out.println("2. 무시하기");
			int sel = input();
			if (sel == 1) {
				while (this.travel == this.rNum || this.travel == this.bossNum) {
					if (HERO.getHp() > 0) {
						System.out.println("1. 공격하기");
						System.out.println("2. 회복하기");
						System.out.println("3. 도망가기");
						int input = input();
						if (input == 1) {
							attack(zom);
						} else if (input == 2) {
							HERO.recovery();
						} else if (input == 3) {
							this.travel++;
							break;
						}
						else {
							System.err.println("잘못된 입력입니다.");
						}
					} else {
						System.err.println("게임을 종료합니다.");
						return;
					}
				}
			} else if (sel == 2) {
				monsterSet();
				monsterNewSet();
				this.travel++;
			}
			else {
				System.err.println("잘못된 입력입니다.");
			}
		}
	}
	

	private void attack(Zombie zom) {
		if (HERO.getHp() > 0) {
			HERO.attack(zom);
		}
		if (zom.getHp() > 0) {
			zom.attack(HERO);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		} else {
			if(this.travel>30) 
				monsterNewSet();
			else
				monsterSet();				
			move();
		}
	}

	public void run() {
		heroSet();
		monsterSet();
		setMap();
		
		while(HERO.getHp()>0) {
			printMenu();
			int sel = input();
			if (sel == 1) {
				while (HERO.getHp() > 0) {
					homeSet();
					move();
				}
			} else if(sel == 2) {
				System.err.println("게임을 종료합니다.");
				break;
			}else {
				System.err.println("잘못된 입력입니다.");
			}
		}
	}

}
