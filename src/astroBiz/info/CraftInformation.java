package astroBiz.info;

import astroBiz.view.VM;


/**
 * Contains all the information to be loaded into a SpaceCraft object.
 * @author Matt Bangert
 *
 */
public class CraftInformation implements VM {
	public static enum CI{
//		enumval (symbol, name, intro, retire, speed, range, capacity, fuelE, maintR, cost, col, row		//
		DCO_X1 		("DCO", "X-1", 2200, 2260, 100, 0.05, 10, 65, 80, 7200, 1, 1),
		DCO_X2 		("DCO", "X-2", 2200, 2260, 100, 0.10, 25, 70, 75, 9500, 1, 1),
		GHI_LW1		("GHI", "LW-1", 2200, 2260, 85, 0.10, 30, 60, 85, 10000, 1, 1);
		
		private final String mfg;
		private final String name;
		private final int introduced;
		private final int retired;
		private final int speed;
		private final double range;
		private final int capacity;
		private final int fuelefficiency;
		private final int maintenancerating;
		private final int cost;
		private final int spriteCol;
		private final int spriteRow;
		
		CI(String mfg, String name, int introduced, int retired, int speed, double range, int capacity, int fuele, int maintr, int cost, int spriteCol, int spriteRow){
			this.mfg = mfg;
			this.name = name;
			this.introduced = introduced;
			this.retired = retired;
			this.speed = speed;
			this.range = range;
			this.capacity = capacity;
			this.fuelefficiency = fuele;
			this.maintenancerating = maintr;
			this.cost = cost;
			this.spriteCol = spriteCol;
			this.spriteRow = spriteRow;
		}
		
		public String getMfg(){
			return this.mfg;
		}
		
		public String getName(){
			return this.name;
		}
		
		public int getIntro(){
			return this.introduced;
		}
		
		public int getRetire(){
			return this.retired;
		}
		
		public double getRange(){
			return this.range;
		}
		
		public int getCapacity(){
			return this.capacity;
		}
		
		public int getFuelE(){
			return this.fuelefficiency;
		}

		public int getMaintR(){
			return this.maintenancerating;
		}
		
		public int getCost(){
			return this.cost;
		}

		public int getSpeed(){
			return this.speed;
		}
		
		public int getSpriteCol(){
			return this.spriteCol;
		}
		
		public int getSpriteRow(){
			return this.spriteRow;
		}
		
	}
}
