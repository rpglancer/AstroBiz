package astroBiz.info;


@Deprecated
/**
 * Contains all the information to be loaded into a SpaceCraft object.
 * @author Matt Bangert
 *
 */
public class CraftInformation{
	@Deprecated
	public static enum CI{
//		enumval (symbol, name, intro, retire, speed, range, capacity, fuelE, maintR, cost, col, row, description		//
		DCO_X1 		("DCO", "X-1", 2200, 2260, 100, 0.05, 10, 65, 80, 7200, 1, 1, "Description"),
		DCO_X2 		("DCO", "X-2", 2200, 2260, 100, 0.10, 25, 70, 75, 9500, 1, 1, "Description"),
		GHI_LW1		("GHI", "LW-1", 2200, 2260, 85, 0.10, 30, 60, 85, 10000, 1, 1, "Description");
		
		private final String mfg;
		private final String name;
		private final String desc;
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
		
		CI(String mfg, String name, int introduced, int retired, int speed, double range, int capacity, int fuele, int maintr, int cost, int spriteCol, int spriteRow, String desc){
			this.mfg = mfg;
			this.name = name;
			this.desc = desc;
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
		
		public int getCapacity(){
			return this.capacity;
		}
		
		public int getCost(){
			return this.cost;
		}
		
		public String getDesc(){
			return this.desc;
		}

		public String getMfg(){
			return this.mfg;
		}
		
		public int getFuelE(){
			return this.fuelefficiency;
		}
		
		public int getIntro(){
			return this.introduced;
		}
				
		public int getRetire(){
			return this.retired;
		}
		
		public int getMaintR(){
			return this.maintenancerating;
		}
		
		public String getName(){
			return this.name;
		}
		
		public double getRange(){
			return this.range;
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
