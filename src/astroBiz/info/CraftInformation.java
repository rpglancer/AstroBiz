package astroBiz.info;

public class CraftInformation {
	public static enum CI_DEVCO{
		X1 ("X-1", 2200, 2260, 0.05, 10, 65, 7200);
		
		private final String name;
		private final int introduced;
		private final int retired;
		private final double range;
		private final int capacity;
		private final int fuelefficiency;
		private final int cost;
		
		CI_DEVCO(String name, int introduced, int retired, double range, int capacity, int fuele, int cost){
			this.name = name;
			this.introduced = introduced;
			this.retired = retired;
			this.range = range;
			this.capacity = capacity;
			this.fuelefficiency = fuele;
			this.cost = cost;
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
		
		public int getCost(){
			return this.cost;
		}
		
	}
}
