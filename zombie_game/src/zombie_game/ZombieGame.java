package zombie_game;

import java.util.Random;
import java.util.Scanner;

class ZombieGame {
	private Random ran;
	private Scanner scan;
	public int travel;
	private int rNum;
	private int bossNum;

	public static final Hero HERO = new Hero(0, 200, 200, "용사세린");

	public ZombieGame() {
		this.ran = new Random();
		this.scan = new Scanner(System.in);
		this.bossNum = -1;
		this.rNum = -2;
	}

	public void heroSet() {
		this.rNum = ran.nextInt(10) + 1;
		this.bossNum = ran.nextInt(10) + 1;
		while (this.rNum == this.bossNum) {
			this.bossNum = ran.nextInt(10) + 1;
		}
		System.out.println("내 캐릭터 정보");
		System.out.printf("이름 : %s\n체력 : %d/%d\n\n", HERO.getName(), HERO.getHp(), HERO.getMax());
	}

	private void printMap() {
		final int SIZE = 10;

		final int BAZZI = 2;
		final int WALL = 3;
		final int ROAD = 0;

		int[][] map = new int[SIZE][SIZE];
		int[][] mark = new int[SIZE][SIZE];

		int pY = 0;
		int pX = 0;
		
		// 맵 세팅 (벽 개수 : 7~13) (플레이어 랜덤 위치 배치)
		int wallCnt = ran.nextInt(SIZE) + SIZE; // 7-13
		while (wallCnt + 1 > 0) {
			int rY = ran.nextInt(SIZE);
			int rX = ran.nextInt(SIZE);

			if (map[rY][rX] == ROAD) {
				map[rY][rX] = WALL;

				if (wallCnt == 0) {
					map[rY][rX] = BAZZI;
					pY = rY;
					pX = rX;
				}
				wallCnt--;
			}
		}
		// 플레이
		while (true) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (map[i][j] == BAZZI) {
						System.out.print("🐧");
					} else if (map[i][j] == WALL) {
						System.out.print("🌳");
					} else {
						System.out.print("➖");
					}
				}
				System.out.println();
			}
			System.out.println();

			// 입력
			
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

			if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || map[y][x] == WALL)
				continue;

			map[pY][pX] = ROAD;
			pY = y;
			pX = x;
			map[pY][pX] = BAZZI;

		}

	}

	private void printMenu() {
		System.out.println("1. 이동하기");
		System.out.println("2. 종료하기");
	}

	private int input() {
		System.out.print("선택 : ");
		int input = scan.nextInt();
		return input;
	}
	
	private char inputmove() {
		System.out.print("a(←)s(↓)d(→)w(↑)\n : ");
		char dir = scan.next().charAt(0);
		return dir;
	}

	private void move() {
		printMap();
		if (this.travel != this.rNum && this.travel != this.bossNum) {
			if (this.travel >= 10) {
				System.out.println("새로운 지역으로 이동합니다.");

			} else {
				this.travel++;
				System.out.printf("🐾현재위치 : %d🐾\n", this.travel);
			}
		} else {
			if (this.travel == this.rNum) {
				Zombie poketmon = new Zombie(this.rNum, 140, 140, "꼬렛");
				fight(poketmon);
			} else if (this.travel == this.bossNum) {
				Boss boss = new Boss(this.bossNum, 300, 300, "뮤", 200);
				fight(boss);
			}
		}

	}

	private void fight(Zombie zom) {
		System.out.printf("야생의 '%s'을(를) 발견했다!\n", zom.getName());
		System.out.println("1. 싸우기");
		System.out.println("2. 무시하고 가던길가기");
		int sel = input();
		if (sel == 1)
			attack(zom);
		else if (sel == 2)
			this.travel++;
	}

	private void attack(Zombie zom) {
		HERO.attack(zom);
		zom.attack(HERO);

	}

	public void run() {
		heroSet();
		while (HERO.getHp() > 0) {
			printMenu();
			int sel = input();
			if (sel == 1)
				move();
			else if (sel == 2)
				break;
		}

	}
}
