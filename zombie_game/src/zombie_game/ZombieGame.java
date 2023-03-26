package zombie_game;

import java.util.Random;
import java.util.Scanner;

class ZombieGame {
	private Random ran;
	private Scanner scan;
	public int travel=29;
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

	private int[][] map = new int[SIZE][SIZE];

	private int pY = 0;
	private int pX = 0;

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
		int wallCnt = ran.nextInt(SIZE) + SIZE;
		while (wallCnt + 1 > 0) {
			int rY = ran.nextInt(SIZE);
			int rX = ran.nextInt(SIZE);

			if (map[rY][rX] == ROAD) {
				map[rY][rX] = WALL;

				if (wallCnt == 0) {
					map[rY][rX-1] = HOME;
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

			
				wallCnt--;
			}
		}
	}
	
	private void printNewMap() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == PLAYER) {
					System.out.print("🐧 ");
				} else if (map[i][j] == WALL) {
					System.out.print("🏝️ ");
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
		System.out.println(this.rNum);
		System.out.println(this.bossNum);
		if(this.travel < 30)
			printMap();
		else {
			if(this.travel == 30) {
				System.out.println("\n===================");
				System.out.println("새로운 지역으로 이동합니다.");
				System.out.println("===================\n");		
				monsterNewSet();
			}
			printNewMap();
		}

		char dir = inputmove();
		int y = pY;
		int x = pX;

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

		if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || map[y][x] == WALL) {
			System.err.println("더 이상 이동 할 수 없는 곳입니다.");
			return;
		}

		map[pY][pX] = ROAD;
		pY = y;
		pX = x;
		map[pY][pX] = PLAYER;

		this.travel++;
		System.out.printf("🐾현재위치 : %d🐾\n", this.travel);

		if (this.travel == this.rNum) {
			fight(this.poketmon);
		} else if (this.travel == this.bossNum) {
			fight(this.boss);
		}

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
					} else {
						System.out.println("플레이할 캐릭터가 사망했습니다.");
						System.out.println("게임을 종료합니다.");
						return;
					}
				}
			} else if (sel == 2)
				this.travel++;
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
			monsterSet();
			move();
		}
	}

	public void run() {
		heroSet();
		monsterSet();
		setMap();
		setNewMap();
		while(true) {
			printMenu();
			int sel = input();
			if (sel == 1) {
				while (HERO.getHp() > 0) {
					move();
				}
			} else if(sel == 2) {
				System.err.println("종료합니다.");
				break;
			}else {
				System.err.println("잘못된 입력입니다.");
			}
		}
	}

}
